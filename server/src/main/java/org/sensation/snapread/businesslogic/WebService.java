package org.sensation.snapread.businesslogic;

import org.sensation.snapread.businesslogic.classification.ClassificationController;
import org.sensation.snapread.businesslogic.consts.Wechat;
import org.sensation.snapread.businesslogic.consts.Zhihu;
import org.sensation.snapread.data.ArticleCenter;
import org.sensation.snapread.data.DataService;
import org.sensation.snapread.po.ArticlePO;
import org.sensation.snapread.po.TagPO;
import org.sensation.snapread.util.CompareText;
import org.sensation.snapread.util.ResultMessage;
import org.sensation.snapread.vo.ArticleOverviewVO;
import org.sensation.snapread.vo.ArticleVO;
import org.sensation.snapread.vo.ResponseVO;
import org.sensation.snapread.vo.TagVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sensation.snapread.data.ArticleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;


/**
 *
 * Created by nians on 2016/10/7.
 */

@RestController
@RequestMapping("/api")
public class WebService
{
    @Autowired
    DataService dataService = new ArticleData();

    ArticleCenter articleCenter = ArticleCenter.getInstance();

    @RequestMapping("/getPostList")
    public ResponseVO getPostList(@RequestParam("user_id") String userID){
        System.out.println("getPostList: " + userID);

        Iterator<ArticlePO> articles = dataService.getArticles(userID);
        LinkedList<ArticleOverviewVO> result = new LinkedList<>();

        while (articles.hasNext())
        {
            ArticlePO articlePO = articles.next();
            articlePO.setContent(articleCenter.getFile(articlePO.getContent()));
            result.add(new ArticleOverviewVO(articlePO));
        }

        return ResponseVO.getSuccessResponse(result.iterator());
    }

    @RequestMapping("/getPost")
    public ResponseVO getPost(@RequestParam("post_id") String postID) {
        System.out.println("getPost: " + postID);

        ArticlePO articlePO = dataService.findArticle(postID);
        articlePO.setContent(articleCenter.getFile(articlePO.getContent()));

        return ResponseVO.getSuccessResponse(new ArticleVO(articlePO));
    }

    @RequestMapping("/searchPost")
    public ResponseVO searchPost(@RequestParam("keyword") String keyword) {

        System.out.println("received keywords are : " + keyword);
        boolean useWechat;
        boolean useZhihu;

        ArticlePO wechat = null;
        ArticlePO zhihu = null;

        // avoid oversize keyword
        keyword = keyword.length() > 40 ? keyword.substring(0, 40) : keyword;

        try
        {
            wechat = WebCrawler.searchInSogou(keyword, new Wechat());
            useWechat = true;
        }
        catch (Exception e)
        {
            System.out.println("fail to recognize wechat");
            useWechat = false;
        }

        try
        {
            zhihu = WebCrawler.searchInSogou(keyword, new Zhihu());
            useZhihu = true;
        }
        catch (Exception e)
        {
            System.out.println("fail to recognize zhihu");
            useZhihu = false;
        }

        ClassificationController classifier = new ClassificationController();

        // both fail
        if (!useWechat && !useZhihu)
        {
            System.out.println("returning null");
            return new ResponseVO("", "", "", null);
        }

        if (useWechat && useZhihu)
        {
            if (CompareText.compare(keyword, wechat.getText()) > CompareText.compare(keyword, zhihu.getText()))
            {
                useWechat = true;
                useZhihu = false;
            }
            else
            {
                useZhihu = true;
                useWechat = false;
            }
        }

        if (useWechat)
        {
            wechat.setType(classifier.getClassification(wechat.getText()));
            return new ResponseVO("", "", "", new ArticleVO(wechat));
        }
        else
        {
            zhihu.setType(classifier.getClassification(zhihu.getText()));
            return new ResponseVO("", "", "", new ArticleVO(zhihu));
        }
    }

    @RequestMapping("/editPost")
    public ResponseVO editPost(@RequestParam("user_id") String userID,
                                  @RequestParam("post_id") String postID,
                                  @RequestParam("title") String title,
                                  @RequestParam("content") String content,
                                  @RequestParam("post_url") String postUrl,
                                  @RequestParam("img_url") String postImg,
                                  @RequestParam("description") String desc,
                                  @RequestParam("type") String type) {
        // pre process the content to avoid too long the value for a column in database
        String contentPath = articleCenter.putFile(content);

        ArticlePO editPO = new ArticlePO(userID, title, contentPath, postUrl, postImg, type);
        editPO.setDescription(desc);

        ResultMessage addResult = dataService.addArticle(editPO);
        if (!addResult.isSuccess())
        {
            return ResponseVO.getSuccessResponse(dataService.updateArticle(editPO));
        }
        else
        {
            return ResponseVO.getSuccessResponse(addResult);
        }
    }

    @RequestMapping("/deletePost")
    public ResponseVO deletePost(@RequestParam("post_id") String[] postID) {
        return ResponseVO.getSuccessResponse(dataService.deleteArticles(postID));
    }

    @RequestMapping("/getTag")
    public ResponseVO getTag(@RequestParam("user_id") String userID) {
        Iterator<TagPO> tagPO = dataService.getTag(userID);
        LinkedList<TagVO> result = new LinkedList<>();

        while (tagPO.hasNext())
        {
            result.add(new TagVO(tagPO.next()));
        }

        return ResponseVO.getSuccessResponse(result.iterator());
    }

    @RequestMapping(value = "/addTag", method = RequestMethod.GET )
    public ResponseVO addTag(@RequestParam("user_id") String tagImg,
                                @RequestParam("tag_name") String userID,
                                @RequestParam("description") String tagName,
                                @RequestParam("tag_img") String description) {
        System.err.println(userID);
        return ResponseVO.getSuccessResponse(dataService.addTag(userID, tagName, description, tagImg));
    }

    private void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    @RequestMapping("/deleteTag")
    public ResponseVO deleteTag(@RequestParam("tag_id") String[] tagID) {
        return ResponseVO.getSuccessResponse(dataService.deleteTags(tagID));
    }

    @RequestMapping("/getPostRecommend")
    public ResponseVO getPostRecommend(@RequestParam("post_id") String postID) {
        return ResponseVO.getSuccessResponse(null);
    }

    @RequestMapping("/getUserRecommend")
    public ResponseVO getUserRecommend(@RequestParam("user_id") String userID) {
        return ResponseVO.getSuccessResponse(null);
    }

}

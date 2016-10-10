package org.sensation.snapread.businesslogic;

import org.sensation.snapread.businesslogic.classification.ClassificationController;
import org.sensation.snapread.businesslogic.consts.Wechat;
import org.sensation.snapread.businesslogic.consts.Zhihu;
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

    @RequestMapping("/getPostList")
    public Iterator<ArticleOverviewVO> getPostList(@RequestParam("user_id") String userID){
        Iterator<ArticlePO> articles = dataService.getArticles(userID);
        LinkedList<ArticleOverviewVO> result = new LinkedList<>();

        while (articles.hasNext())
        {
            result.add(new ArticleOverviewVO(articles.next()));
        }

        return result.iterator();
    }

    @RequestMapping("/getPost")
    public ArticleVO getPost(@RequestParam("post_id") String postID) {
        ArticlePO articlePO = dataService.findArticle(postID);

        return new ArticleVO(articlePO);
    }

    @RequestMapping("/searchPost")
    public ResponseVO searchPost(@RequestParam("keyword") String keyword) {
        boolean useWechat = false;
        boolean useZhihu = false;

        ArticlePO wechat = null;
        ArticlePO zhihu = null;

        try
        {
            wechat = WebCrawler.searchInSogou(keyword, new Wechat());
        }
        catch (Exception e)
        {
            useZhihu = true;
        }

        try
        {
            zhihu = WebCrawler.searchInSogou(keyword, new Zhihu());
        }
        catch (Exception e)
        {
            useWechat = true;
        }

        ClassificationController classifier = new ClassificationController();

        if (!useWechat && !useZhihu)
        {
            if (CompareText.compare(keyword, wechat.getText()) > CompareText.compare(keyword, zhihu.getText()))
            {
                useWechat = true;
            }
            else
            {
                useZhihu = true;
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
    public ResultMessage editPost(@RequestParam("user_id") String userID,
                                  @RequestParam("post_id") String postID,
                                  @RequestParam("title") String title,
                                  @RequestParam("content") String content,
                                  @RequestParam("post_url") String postUrl,
                                  @RequestParam("post_img") String postImg,
                                  @RequestParam("type") String type) {
        ArticlePO editPO = new ArticlePO(userID, title, content, postUrl, postImg, type);

        ResultMessage addResult = dataService.addArticle(editPO);
        if (!addResult.isSuccess())
        {
            return dataService.updateArticle(editPO);
        }
        else
        {
            return addResult;
        }
    }

    @RequestMapping("/deletePost")
    public ResultMessage deletePost(@RequestParam("post_id") String[] postID) {
        return dataService.deleteArticles(postID);
    }

    @RequestMapping("/getTag")
    public Iterator<TagVO> getTag(@RequestParam("user_id") String userID) {
        Iterator<TagPO> tagPO = dataService.getTag(userID);
        LinkedList<TagVO> result = new LinkedList<>();

        while (tagPO.hasNext())
        {
            result.add(new TagVO(tagPO.next()));
        }

        return result.iterator();
    }

    @RequestMapping(value = "/addTag", method = RequestMethod.POST )
    public ResultMessage addTag(@RequestParam("user_id") String userID,
                         @RequestParam("tag_name") String tagName,
                         @RequestParam("description") String description,
                         @RequestParam("tag_img") String tagImg) {
        return dataService.addTag(userID, tagName, description, tagImg);
    }

    @RequestMapping("/deleteTag")
    public ResultMessage deleteTag(@RequestParam("tag_id") String[] tagID) {
        return dataService.deleteTags(tagID);
    }

    @RequestMapping("/getPostRecommend")
    public String getPostRecommend(@RequestParam("post_id") String postID) {
        return null;
    }

    @RequestMapping("/getUserRecommend")
    public String getUserRecommend(@RequestParam("user_id") String userID) {
        return null;
    }

}

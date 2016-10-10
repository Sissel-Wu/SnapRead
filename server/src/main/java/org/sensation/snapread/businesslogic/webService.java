package org.sensation.snapread.businesslogic;

import org.json.JSONObject;
import org.sensation.snapread.businesslogic.consts.Wechat;
import org.sensation.snapread.data.DataService;
import org.sensation.snapread.data.DataStub;
import org.sensation.snapread.po.ArticlePO;
import org.sensation.snapread.po.TagPO;
import org.sensation.snapread.vo.ArticleOverviewVO;
import org.sensation.snapread.vo.ArticleVO;
import org.sensation.snapread.vo.TagVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sensation.snapread.data.ArticleData;
import org.sensation.snapread.data.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by nians on 2016/10/7.
 */

@RestController
@RequestMapping("/api")
public class WebService
{
    @Autowired
    ArticleData articleData;
    DataService dataService = new DataStub();

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
        ArticlePO article = dataService.getArticle(postID);

        return new ArticleVO(article);
    }

    @RequestMapping("/searchPost")
    public String searchPost(@RequestParam("keyword") String keyword) {
        ArticlePO result = WebCrawler.searchInSogou(keyword, new Wechat());

        // TODO: 2016/10/9 add type and post_id(userid)

        return result.toString();
    }

    @RequestMapping("/editPost")
    public String editPost(@RequestParam("user_id") String userID,
                           @RequestParam("post_id") String postID,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("post_url") String postUrl,
                           @RequestParam("post_img") byte[] postImg,
                           @RequestParam("type") String type) {
        ArticlePO editArticle = dataService.editArticle(userID, postID, title, content, postUrl, postImg, type);

        return "success";
    }

    @RequestMapping("/deletePost")
    public String deletePost(@RequestParam("post_id") String[] postID) {
        dataService.deleteArticles(postID);

        // TODO: 2016/10/9
        return "success";
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
    public String addTag(@RequestParam("user_id") String userID,
                         @RequestParam("tag_name") String tagName,
                         @RequestParam("description") String description,
                         @RequestParam("tag_img") String tagImg) {

        dataService.addTag(userID, tagName, description, tagImg);

        return "success";
    }

    @RequestMapping("/deleteTag")
    public String deleteTag(@RequestParam("tag_id") String[] tagID) {
        dataService.deleteTags(tagID);

        return "success";
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

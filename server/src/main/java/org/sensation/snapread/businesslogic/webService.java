package org.sensation.snapread.businesslogic;

import org.json.JSONObject;
import org.sensation.snapread.businesslogic.consts.Wechat;
import org.sensation.snapread.data.DataService;
import org.sensation.snapread.data.DataStub;
import org.sensation.snapread.po.ArticlePO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * Created by nians on 2016/10/7.
 */

@RestController
@RequestMapping("/api")
public class WebService
{
    DataService dataService = new DataStub();

    @RequestMapping("/getPostList")
    public Iterator<ArticlePO> getPostList(@RequestParam("user_id") String userID){
        Iterator<ArticlePO> articles = dataService.getArticles(userID);

        return articles;
    }

    @RequestMapping("/getPost")
    public String getPost(@RequestParam("post_id") String postID) {
        return null;
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
        return null;
    }

    @RequestMapping("/deletePost")
    public String deletePost(@RequestParam("post_id") String[] postID) {
        return null;
    }

    @RequestMapping("/getTag")
    public String getTag(@RequestParam("user_id") String userID) {

        return null;
    }

    @RequestMapping("/addTag")
    public String getTag(@RequestParam("user_id") String userID,
                         @RequestParam("tag_name") String tagName,
                         @RequestParam("description") String description,
                         @RequestParam("tag_img") byte[] tagImg) {
        return null;
    }

    @RequestMapping("/deleteTag")
    public String deleteTag(@RequestParam("tag_id") String tagID) {
        return null;
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

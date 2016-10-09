package org.sensation.snapread.businesslogic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nians on 2016/10/7.
 */

@RestController
@RequestMapping("/api")
public class webService {

    @RequestMapping("/getPostList")
    public String getPostList(@RequestParam("user_id") String userID){
        return null;
    }

    @RequestMapping("/getPost")
    public String getPost(@RequestParam("post_id") String postID) {
        return null;
    }

    @RequestMapping("/searchPost")
    public String searchPost(@RequestParam("keyword") String keyword) {
        return null;
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

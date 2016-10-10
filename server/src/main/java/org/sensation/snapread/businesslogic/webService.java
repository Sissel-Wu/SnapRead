package org.sensation.snapread.businesslogic;

import org.json.JSONObject;
import org.sensation.snapread.businesslogic.consts.Wechat;
import org.sensation.snapread.data.ArticleData;
import org.sensation.snapread.data.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;

/**
 * Created by nians on 2016/10/7.
 */

@RestController
@RequestMapping("/api")
public class WebService
{
    @Autowired
    ArticleData articleData;

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
        JSONObject result = WebCrawler.searchInSogou(keyword, new Wechat());

        // TODO: 2016/10/9 add type and post_id

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

    @RequestMapping(value = "/addTag", method = RequestMethod.POST )
    public String getTag(@RequestParam("user_id") String userID,
                         @RequestParam("tag_name") String tagName,
                         @RequestParam("description") String description,
                         @RequestBody byte[] tagImg) {

        this.byte2image(tagImg, "src/main/resources/static/p.jpg");

        return tagImg.length+"";
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

package org.sensation.snapread.vo;

/**
 *
 * Created by sissel on 2016/12/8.
 */
public class ArticleRequestVO {
    public String user_id;
    public String post_id;
    public String title;
    public String content;
    public String description;
    public String post_url;
    public String img_url;
    public String type;

    public ArticleRequestVO()
    {}

    @Override
    public String toString() {
        return "user_id: " + user_id + "/n" +
                "post_id: " + post_id + "/n" +
                "title: " + title + "/n" +
                "content: " + content + "/n" +
                "description: " + description + "/n" +
                "post_url: " + post_url + "/n" +
                "img_url: " + img_url + "/n" +
                "type: " + type + "/n";
    }
}

package sensation.snapread.model.vopo;

/**
 * Created by Alan on 2016/10/10.
 */
public class ContentPO {
    String title;
    String content;
    String post_url;
    String post_img;
    String type;

    public ContentPO(String title, String content, String post_url, String post_img, String type) {

        this.title = title;
        this.content = content;
        this.post_url = post_url;
        this.post_img = post_img;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

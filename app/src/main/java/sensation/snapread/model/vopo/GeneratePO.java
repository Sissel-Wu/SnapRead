package sensation.snapread.model.vopo;

import org.litepal.crud.DataSupport;

/**
 * Created by Alan on 2016/9/29.
 */
public class GeneratePO extends DataSupport {
    String post_id;
    String title;
    String type;
    String content;
    String description;
    String post_img;
    String post_url;

    public GeneratePO(String post_id, String title, String type, String content, String description, String post_img, String post_url) {
        this.post_id = post_id;
        this.title = title;
        this.type = type;
        this.content = content;
        this.description = description;
        this.post_img = post_img;
        this.post_url = post_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public GenerateVO toVO() {
        return new GenerateVO(post_id, title, type, content, description, post_img, post_url);
    }
}

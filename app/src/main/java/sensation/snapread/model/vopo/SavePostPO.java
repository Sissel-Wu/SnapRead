package sensation.snapread.model.vopo;

/**
 * Created by Alan on 2016/12/9.
 */

public class SavePostPO {
    String user_id;
    String post_id;
    String title;
    String content;
    String description;
    String post_url;
    String img_url;
    String type;

    public SavePostPO() {
    }

    public SavePostPO(String user_id, String post_id, String title, String content, String description, String post_url, String img_url, String type) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.description = description;
        this.post_url = post_url;
        this.img_url = img_url;
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

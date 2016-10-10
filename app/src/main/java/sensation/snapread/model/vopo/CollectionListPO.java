package sensation.snapread.model.vopo;

/**
 * 文章
 * Created by Alan on 2016/9/28.
 */
public class CollectionListPO {
    String post_id;
    String title;
    String type;
    String description;
    String post_img;
    String post_url;

    public CollectionListPO(String post_id, String title, String type, String description,
                            String post_img, String post_url) {
        this.post_id = post_id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.post_img = post_img;
        this.post_url = post_url;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getPost_img() {
        return this.post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost_id() {
        return this.post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public CollectionListItemVO toVO() {
        return new CollectionListItemVO(post_id, type, title, description, post_url, post_img);
    }
}

package sensation.snapread.model.vopo;

/**
 * 文章
 * Created by Alan on 2016/9/28.
 */
public class CollectionListPO {
    String postID;
    String title;
    String type;
    String description;
    String imgUrl;
    String url;

    public CollectionListPO(String postID, String title, String type, String description,
                            String imgUrl, String url) {
        this.postID = postID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getPostID() {
        return this.postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}

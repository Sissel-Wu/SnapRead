package sensation.snapread.model.vopo;

import org.litepal.crud.DataSupport;

/**
 * Created by Alan on 2016/9/30.
 */
public class PostPO extends DataSupport {
    String postID;
    String title;
    String type;
    String description;
    String content;
    String imgUrl;
    String url;

    public PostPO() {
    }

    public PostPO(String postID, String title, String type, String content, String description, String imgUrl, String url) {
        this.postID = postID;
        this.title = title;
        this.type = type;
        this.content = content;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CollectionListPO toListPO() {
        return new CollectionListPO(postID, title, type, description, imgUrl, url);
    }
}

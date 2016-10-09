package sensation.snapread.model.vopo;

/**
 * Created by Alan on 2016/9/30.
 */
public class PostVO {
    String postID;
    String title;
    String type;
    String content;
    String imgUri;
    String url;

    public PostVO(String postID, String title, String type, String content, String imgUri, String url) {
        this.postID = postID;
        this.title = title;
        this.type = type;
        this.content = content;
        this.imgUri = imgUri;
        this.url = url;
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

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

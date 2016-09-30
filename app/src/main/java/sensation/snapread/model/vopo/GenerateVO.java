package sensation.snapread.model.vopo;

/**
 * Created by Alan on 2016/9/29.
 */
public class GenerateVO {
    String title;
    String type;
    String content;
    String imgUrl;
    String url;

    public GenerateVO(String title, String type, String content, String imgUrl, String url) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.imgUrl = imgUrl;
        this.url = url;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

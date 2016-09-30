package sensation.snapread.model.vopo;

/**
 * 收藏的列表展示
 * Created by Alan on 2016/9/8.
 */
public class CollectionListItemVO {
    String collectionID;
    String type;
    String title;
    String description;
    String url;
    String imgUrl;

    public CollectionListItemVO(String collectionID, String type, String title, String description, String url, String imgUrl) {
        this.collectionID = collectionID;
        this.type = type;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean equals(CollectionListItemVO vo) {
        return vo.getCollectionID().equals(collectionID);
    }
}

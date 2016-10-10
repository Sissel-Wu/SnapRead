package sensation.snapread.model.vopo;

/**
 * Created by Alan on 2016/10/7.
 */
public class TypeItemVO {
    String typeID;
    String imageUrl;
    String title;
    String description;

    public TypeItemVO(String typeID, String imageUrl, String title, String description) {
        this.typeID = typeID;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}

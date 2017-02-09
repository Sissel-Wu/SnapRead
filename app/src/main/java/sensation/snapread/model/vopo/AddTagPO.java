package sensation.snapread.model.vopo;

/**
 * Created by Alan on 2016/12/9.
 */
public class AddTagPO {
    String user_id;
    String tag_name;
    String description;
    String tag_img;

    public AddTagPO() {
    }

    public AddTagPO(String user_id, String tag_name, String description, String tag_img) {
        this.user_id = user_id;
        this.tag_name = tag_name;
        this.description = description;
        this.tag_img = tag_img;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag_img() {
        return tag_img;
    }

    public void setTag_img(String tag_img) {
        this.tag_img = tag_img;
    }
}

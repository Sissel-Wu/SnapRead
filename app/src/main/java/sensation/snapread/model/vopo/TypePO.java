package sensation.snapread.model.vopo;

import org.litepal.crud.DataSupport;

/**
 * Created by Alan on 2016/10/9.
 */
public class TypePO extends DataSupport {

    String tag_id;
    String tag_img;
    String tag_name;
    String description;

    public TypePO(String tag_id, String tag_img, String tag_name, String description) {

        this.tag_id = tag_id;
        this.tag_img = tag_img;
        this.tag_name = tag_name;
        this.description = description;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_img() {
        return tag_img;
    }

    public void setTag_img(String tag_img) {
        this.tag_img = tag_img;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String title) {
        this.tag_name = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeItemVO toVO() {
        return new TypeItemVO(tag_id, tag_img, tag_name, description);
    }
}

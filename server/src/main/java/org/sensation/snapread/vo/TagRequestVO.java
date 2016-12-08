package org.sensation.snapread.vo;

/**
 * Created by sissel on 2016/12/8.
 */
public class TagRequestVO {
    public String user_id;
    public String tag_name;
    public String description;
    public String tag_img;

    public TagRequestVO()
    {}

    @Override
    public String toString() {
        return "user_id: " + user_id + "/n"
                + "tag_name: " + tag_name + "/n"
                + "description: " + description + "/n"
                + "tag_img: " + tag_img + "/n";
    }
}

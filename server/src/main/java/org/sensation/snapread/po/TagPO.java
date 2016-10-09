package org.sensation.snapread.po;

/**
 * Created by nians on 2016/10/9.
 */
public class TagPO {
    private String user_id;
    private String tag_name;

    public TagPO() {
    }

    public TagPO(String user_id, String tag_name) {
        this.user_id = user_id;
        this.tag_name = tag_name;
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
}

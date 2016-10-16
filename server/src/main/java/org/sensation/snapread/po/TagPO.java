package org.sensation.snapread.po;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nians on 2016/10/9.
 */
public class TagPO implements Serializable{
    private String tag_id;
    private String user_id;
    private String tag_name;
    private String tag_description;
    private String tag_img;

    public TagPO() {
    }

    public TagPO(String user_id, String tag_name, String tag_description, String tag_img) {
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMddHHmmssSSSS");

        this.setUser_id(user_id.substring(0,10));
        this.tag_id = user_id + myFmt.format(new Date());
        this.tag_name = tag_name;
        this.tag_description = tag_description;
        this.tag_img = tag_img;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id.substring(0,10);
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_id()
    {
        return tag_id;
    }

    public void setTag_id(String tag_id)
    {
        this.tag_id = tag_id;
    }

    public String getTag_description()
    {
        return tag_description;
    }

    public void setTag_description(String tag_description)
    {
        this.tag_description = tag_description;
    }

    public String getTag_img()
    {
        return tag_img;
    }

    public void setTag_img(String tag_img)
    {
        this.tag_img = tag_img;
    }
}

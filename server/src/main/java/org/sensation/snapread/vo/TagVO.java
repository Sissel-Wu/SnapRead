package org.sensation.snapread.vo;

import org.sensation.snapread.po.TagPO;

/**
 * Tag
 * Created by Sissel on 2016/10/10.
 */
public class TagVO
{
    public String tag_id;
    public String tag_name;
    public String description;
    public String tag_img;

    public TagVO(TagPO tagPO)
    {
        this.tag_id = tagPO.getTag_id();
        this.tag_name = tagPO.getTag_name();
        this.description = tagPO.getTag_description();
        this.tag_img = tagPO.getTag_img();
    }
}

package org.sensation.snapread.vo;

import org.sensation.snapread.po.ArticlePO;

/**
 *
 * Created by Sissel on 2016/10/9.
 */
public class ArticleVO
{
    public String post_id;
    public String title;
    public String content;
    public String description;
    public String type;
    public String post_url;
    public String post_img;

    public ArticleVO(ArticlePO articlePO)
    {
        this.post_id = articlePO.getPost_id();
        this.title = articlePO.getTitle();
        this.content = articlePO.getContent();
        this.description = articlePO.getDescription();
        this.type = articlePO.getType();
        this.post_url = articlePO.getPost_url();
        this.post_img = articlePO.getPost_img();
    }
}

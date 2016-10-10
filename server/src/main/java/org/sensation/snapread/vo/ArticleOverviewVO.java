package org.sensation.snapread.vo;

import org.sensation.snapread.po.ArticlePO;

/**
 *
 * Created by Sissel on 2016/10/9.
 */
public class ArticleOverviewVO
{
    public String post_id;
    public String title;
    public String description;
    public String type;
    public String post_url;
    public String post_img;

    public ArticleOverviewVO(ArticlePO articlePO)
    {
        this.post_id = articlePO.getPost_id();
        this.title = articlePO.getTitle();
        this.description = articlePO.getSummary();
        this.type = articlePO.getType();
        this.post_url = articlePO.getPost_url();
        this.post_img = articlePO.getPost_img();
    }
}

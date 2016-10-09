package org.sensation.snapread.vo;

import org.sensation.snapread.po.ArticlePO;

/**
 *
 * Created by Sissel on 2016/10/9.
 */
public class ArticleVO
{
    public String title;
    public String content;
    public String type;
    public String post_url;
    public String post_img;

    public ArticleVO(ArticlePO articlePO)
    {
        this.title = articlePO.getTitle();
        this.content = articlePO.getContent();
        this.type = articlePO.getType();
        this.post_url = articlePO.getPost_url();
        this.post_img = articlePO.getPost_img();
    }
}

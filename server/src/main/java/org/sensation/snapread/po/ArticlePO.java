package org.sensation.snapread.po;

import net.minidev.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文章的PO
 * Created by Sissel on 2016/10/9.
 */
public class ArticlePO implements Serializable
{
    private String post_id;
    private String user_id;
    private String title;
    private String content;
    private String description;
    private String type;
    private String post_url;
    private String post_img;
    private String feature;

    public ArticlePO()
    {
    }

    public ArticlePO(String user_id, String title, String description, String content, String post_url, String post_img, String type)
    {
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        this.user_id = user_id == null ? null : user_id.substring(0,10);
        this.post_id = user_id + myFmt.format(new Date());
        this.title = title;
        this.description = description;
        this.content = content;
        this.type = type;
        this.post_url = post_url;
        this.post_img = post_img;
    }

    /**
     * 从content中提出文字
     */
    public String getText()
    {
        Document document = Jsoup.parseBodyFragment(content);

        Element current = document.body();

        return current.text();
    }

    public String getSummary()
    {
        String text = getText();

        return text.length() > 50 ? text.substring(0, 50) : text;
    }

    @Override
    public String toString()
    {
        return "post_id:" + post_id + " title:" + title + " summary:" + getText();
    }

    /*
     * below are all getters and setters
     */
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPost_url()
    {
        return post_url;
    }

    public void setPost_url(String post_url)
    {
        this.post_url = post_url;
    }

    public String getPost_img()
    {
        return post_img;
    }

    public void setPost_img(String post_img)
    {
        this.post_img = post_img;
    }

    public String getPost_id()
    {
        return post_id;
    }

    public void setPost_id(String post_id)
    {
        this.post_id = post_id;
    }

    public String getFeature()
    {
        return feature;
    }

    public void setFeature(String feature)
    {
        this.feature = feature;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id.substring(0,10);
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}

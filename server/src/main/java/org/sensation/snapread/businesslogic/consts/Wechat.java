package org.sensation.snapread.businesslogic.consts;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 微信版本
 * Created by Sissel on 2016/10/8.
 */
public class Wechat extends Vendor
{
    private static final String search_0_ID = "sogou_vr_11002601_title_0";
    private static final String img_0_ID = "sogou_vr_11002601_img_0";
    private static final String title_ID = "activity-name";
    private static final String mainBody_ID = "js_content";
    private static final String search_template = "http://weixin.sogou.com/weixin?type=2&query={key_word}";

    @Override
    public String getSearch_0_ID()
    {
        return search_0_ID;
    }

    @Override
    public String getImg_0_ID()
    {
        return img_0_ID;
    }

    @Override
    public String getSearchTemplate()
    {
        return search_template;
    }

    @Override
    public String getTitle(Document document)
    {
        Element titleElement = document.getElementById(title_ID);
        return titleElement.ownText();
    }

    private Element extractMainBody(Document document)
    {
        Element mainBody = document.getElementById(mainBody_ID);
        return mainBody;
    }

    @Override
    public String getMainBody(String uri, Document document)
    {
        return extractMainBody(document).toString();
    }

    @Override
    public String getPostImage(String uri, Document document)
    {
        Element mainBody = extractMainBody(document);

        Element imageElement = mainBody.getElementsByTag("img").first();

        String src = imageElement.attr("src");
        return "".equals(src) ?
                imageElement.attr("org.sensation.snapread.data-src") : src;
    }
}

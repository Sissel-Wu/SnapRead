package businesslogic;

import businesslogic.consts.Vendor;
import businesslogic.consts.Wechat;
import businesslogic.consts.Zhihu;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.client.RestTemplate;

/**
 * 爬取页面
 * Created by Sissel on 2016/10/7.
 */
public class webCrawler
{
    public static JSONObject searchInSogou(String keyWords, Vendor vendor)
    {
        RestTemplate rt = new RestTemplate();
        String uri = vendor.getSearchTemplate();

        String searchResult = rt.getForObject(uri, String.class, keyWords);

        JSONObject json = new JSONObject();

        Document searchResultDoc = Jsoup.parse(searchResult);

        String contentUri = searchResultDoc.getElementById(vendor.getSearch_0_ID()).attr("href");
        String content = new RestTemplate().getForObject(contentUri, String.class);

        Document contentDoc = Jsoup.parse(content);
        String pictureUri = vendor.getPostImage(contentUri, contentDoc);
        String title = vendor.getTitle(contentDoc);
        String mainBody = vendor.getMainBody(contentUri, contentDoc);

        json.put("title", title)
                .put("content", mainBody)
                .put("post_url", contentUri)
                .put("post_img", pictureUri);

        return json;
    }

    // stockade project
    private static String extractPicture(String html, Vendor vendor)
    {
        Document doc = Jsoup.parse(html);

        Element picFather = doc.getElementById(vendor.getImg_0_ID());
        Element pic = picFather.child(0);
        String pictureUri = pic.attr("src");

        return pictureUri;
    }

    public static void main(String[] args)
    {
        System.out.println(searchInSogou("我们都老了", new Zhihu()));
        System.out.println(searchInSogou("电车难题看到最后哈哈哈哈", new Wechat()));
    }
}

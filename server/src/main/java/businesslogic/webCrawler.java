package businesslogic;

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
    public static JSONObject searchInSogou(String keyWords)
    {
        RestTemplate rt = new RestTemplate();
        String url = "http://weixin.sogou.com/weixin?type=2&query={key_word}";

        String searchResult = rt.getForObject(url, String.class, keyWords);

        JSONObject json = new JSONObject();

        Document searchResultDoc = Jsoup.parse(searchResult);

        String contentUri = searchResultDoc.getElementById("sogou_vr_11002601_title_0").attr("href");
        String content = new RestTemplate().getForObject(contentUri, String.class);

        Document contentDoc = Jsoup.parse(content);
        String pictureUri = extractPicture(searchResult);
        String title = extractTitle(contentDoc);
        String mainBody = extractMainBody(contentDoc);

        json.put("title", title)
                .put("content", mainBody)
                .put("post_url", contentUri)
                .put("post_img", pictureUri);

        return json;
    }

    private static String extractPicture(String html)
    {
        Document doc = Jsoup.parse(html);

        Element picFather = doc.getElementById("sogou_vr_11002601_img_0");
        Element pic = picFather.child(0);
        String pictureUri = pic.attr("src");

        return pictureUri;
    }

    private static String extractMainBody(Document contentDoc)
    {
        Element mainBody = contentDoc.getElementById("js_content");
        return mainBody.toString();
    }

    private static String extractTitle(Document contentDoc)
    {
        Element titleElement = contentDoc.getElementById("activity-name");
        return titleElement.ownText();
    }

    public static void main(String[] args)
    {
        System.out.println(searchInSogou("我们都老了"));
    }
}

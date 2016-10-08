package businesslogic;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 * 爬取页面
 * Created by Sissel on 2016/10/7.
 */
public class webCrawler
{
    public static void searchInSogou(String keyWords)
    {
        RestTemplate rt = new RestTemplate();
        String url = "http://weixin.sogou.com/weixin?type=2&query={key_word}";
        String result = rt.getForObject(url, String.class, keyWords);



        System.out.println(result);
    }

    public static void main(String[] args)
    {
        searchInSogou("小道消息");
    }
}

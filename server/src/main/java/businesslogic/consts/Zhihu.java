package businesslogic.consts;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 知乎版本
 * Created by Sissel on 2016/10/8.
 */
public class Zhihu extends Vendor
{
    private static final String search_0_ID = "zhihu_qa_title_0";
    private static final String img_0_ID = "zhihu_qa_img_0";
    private static final String search_template = "http://zhihu.sogou.com/zhihu?query={key_word}";

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
        Element titleElement = document.getElementById("zh-question-title");

        return titleElement.child(0).child(0).ownText();
    }

    @Override
    public String getMainBody(String uri, Document document)
    {
        // 提取特定答案(#answer-id)
        Pattern pattern = Pattern.compile(".*#(answer-\\d+)");
        Matcher matcher = pattern.matcher(uri);

        if(matcher.find())
        {
            String answerName = matcher.group(1);

            Elements hintElement = document.getElementsByAttributeValue("name", answerName);
            Element answerElement = hintElement.get(0).nextElementSibling().nextElementSibling().nextElementSibling();

            return answerElement.toString();
        }
        else
        {
            return "";
        }
    }

}

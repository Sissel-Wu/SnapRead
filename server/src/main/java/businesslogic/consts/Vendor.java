package businesslogic.consts;

import org.jsoup.nodes.Document;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * 为不同的网站的不同限制（如id值不同）给一个统一接口
 * Created by Sissel on 2016/10/8.
 */
public abstract class Vendor
{
    protected String search_0_ID;
    protected String img_0_ID;

    public abstract String getSearch_0_ID();

    public abstract String getImg_0_ID();

    public abstract String getSearchTemplate();

    public abstract String getTitle(Document document);

    public abstract String getMainBody(String uri, Document document);
}

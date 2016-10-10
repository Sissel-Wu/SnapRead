package org.sensation.snapread.data;

import org.sensation.snapread.po.ArticlePO;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sissel on 2016/10/9.
 */
public class DataStub
{

    public Iterator<PassagePO> getCategoryPassage(String cateName)
    {
        return null;
    }


    public Iterator<ArticlePO> getArticles(String userID)
    {
        ArrayList<ArticlePO> list = new ArrayList<>();
        ArticlePO articlePO1 = new ArticlePO("id1", "title", "ddd", "post_url", "post_img", "123;ddd;");
        ArticlePO articlePO2 = new ArticlePO("id2", "title", "ddd", "post_url", "post_img", "123;ddd;");
        list.add(articlePO1);
        list.add(articlePO2);

        return list.iterator();
    }
}

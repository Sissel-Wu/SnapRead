package org.sensation.snapread.data;

import org.sensation.snapread.po.ArticlePO;

import java.util.Iterator;

/**
 * Created by nians on 2016/10/9.
 */
public interface DataService {
    public Iterator<PassagePO> getCategoryPassage(String cateName);

    Iterator<ArticlePO> getArticles(String userID);

    ArticlePO getArticle(String postID);

    boolean deleteArticles(String[] postID);
}

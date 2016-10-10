package org.sensation.snapread.data;

import org.sensation.snapread.po.ArticlePO;
import org.sensation.snapread.po.TagPO;

import java.util.Iterator;

/**
 * Created by nians on 2016/10/9.
 */
public interface DataService {
    public Iterator<PassagePO> getCategoryPassage(String cateName);

    Iterator<ArticlePO> getArticles(String userID);

    ArticlePO getArticle(String postID);

    boolean deleteArticles(String[] postID);

    Iterator<TagPO> getTag(String userID);

    boolean addTag(String userID, String tagName, String description, String tagImg);

    boolean deleteTags(String[] tagID);
}

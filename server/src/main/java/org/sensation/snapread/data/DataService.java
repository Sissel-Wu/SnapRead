package org.sensation.snapread.data;

import org.sensation.snapread.po.ArticlePO;
import org.sensation.snapread.po.TagPO;
import org.sensation.snapread.util.ResultMessage;

import java.util.Iterator;

/**
 * Created by nians on 2016/10/9.
 */
public interface DataService {
    Iterator<ArticlePO> getArticles(String userID);

    ArticlePO findArticle(String postID);

    ResultMessage deleteArticles(String[] postID);

    ResultMessage addArticle(ArticlePO articlePO);

    Iterator<TagPO> getTag(String userID);

    ResultMessage addTag(String userID, String tagName, String description, String tagImg);

    ResultMessage deleteTags(String[] tagID);

    ResultMessage updateArticle(ArticlePO articlePO);
}

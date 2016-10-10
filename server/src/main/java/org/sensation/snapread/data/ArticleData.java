package org.sensation.snapread.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.sensation.snapread.po.ArticlePO;
import org.sensation.snapread.po.TagPO;
import org.sensation.snapread.util.ResultMessage;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Article访问控制
 * Created by Sissel on 2016/10/9.
 */

@Component
public class ArticleData implements DataService
{
    Session session;

    public ArticleData()
    {
        this.session = MySessionFactory.getSessionFactory().openSession();
    }

    public ResultMessage addArticle(ArticlePO articlePO)
    {
        if(findArticle(articlePO.getPost_id())!=null)
        {
            return new ResultMessage(false,"this article already exists!");
        }
        else
        {
            session.beginTransaction();
            session.save(articlePO);
            session.getTransaction().commit();
            return new ResultMessage(true,"success");
        }
    }

    public ResultMessage deleteArticles(String[] postIDs)
    {
        for (String postID : postIDs)
        {
            if(findArticle(postID)==null)
            {
                return new ResultMessage(false,"this article doesn't exist!");
            }
            else
            {
                session.beginTransaction();
                ArticlePO tmp = new ArticlePO();
                tmp.setPost_id(postID);
                session.clear();
                session.delete(tmp);
                session.getTransaction().commit();
            }
        }
        return new ResultMessage(true,"success");
    }

    @Override
    public Iterator<TagPO> getTag(String userID)
    {
        return null;
    }

    @Override
    public ResultMessage addTag(String userID, String tagName, String description, String tagImg)
    {
        return null;
    }

    @Override
    public ResultMessage deleteTags(String[] tagID)
    {
        return null;
    }

    public ResultMessage updateArticle(ArticlePO articlePO)
    {
        if(findArticle(articlePO.getPost_id())==null)
        {
            return new ResultMessage(false,"this article doesn't exist!");
        }
        else
        {
            session.beginTransaction();
            session.clear();
            session.update(articlePO);
            session.getTransaction().commit();
            return new ResultMessage(true,"success");
        }
    }

    @Override
    public Iterator<ArticlePO> getArticles(String userID)
    {
        return null;
    }

    public ArticlePO findArticle(String post_id)
    {
        session.beginTransaction();
        Criteria cri = session.createCriteria(ArticlePO.class);
        cri.add(Restrictions.eq("post_id", post_id));

        return cri.list().isEmpty() ? null : (ArticlePO) cri.list().get(0);
    }

    public Iterator<ArticlePO> getAllArticle()
    {
        session.beginTransaction();
        Criteria cri = session.createCriteria(ArticlePO.class);
        return (Iterator<ArticlePO>)cri.list().iterator();
    }
}

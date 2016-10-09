package data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import po.ArticlePO;
import util.ResultMessage;

import java.util.Iterator;

/**
 * Article访问控制
 * Created by Sissel on 2016/10/9.
 */
public class ArticleData
{
    Session session;

    public ArticleData()
    {
        this.session = MySessionFactory.sessionFactory.openSession();
    }

    public ResultMessage addArticle(ArticlePO memo)
    {
        if(findArticle(memo.getPost_id())!=null)
        {
            return new ResultMessage(false,"this article already exists!");
        }
        else
        {
            session.beginTransaction();
            session.save(memo);
            session.getTransaction().commit();
            return new ResultMessage(true,"success");
        }
    }

    public ResultMessage deleteArticle(String memoID)
    {
        if(findArticle(memoID)==null)
        {
            return new ResultMessage(false,"this article doesn't exist!");
        }
        else
        {
            session.beginTransaction();
            ArticlePO tmp = new ArticlePO();
            tmp.setPost_id(memoID);
            session.clear();
            session.delete(tmp);
            session.getTransaction().commit();
            return new ResultMessage(true,"success");
        }
    }

    public ResultMessage updateArticle(ArticlePO memo)
    {
        if(findArticle(memo.getPost_id())==null)
        {
            return new ResultMessage(false,"this article doesn't exist!");
        }
        else
        {
            session.beginTransaction();
            session.clear();
            session.update(memo);
            session.getTransaction().commit();
            return new ResultMessage(true,"success");
        }
    }

    public ArticlePO findArticle(String post_id)
    {
        session.beginTransaction();
        Criteria cri = session.createCriteria(ArticlePO.class);
        cri.add(Restrictions.eq("post_id", post_id));

        return cri.list().isEmpty() ? null : (ArticlePO) cri.list().get(0);
    }

    @SuppressWarnings("unchecked")
    public Iterator<ArticlePO> getArticleList(String userID)
    {
        session.beginTransaction();
        Criteria cri = session.createCriteria(ArticlePO.class);
        cri.add(Restrictions.eq("userID", userID));
        return (Iterator<ArticlePO>)cri.list().iterator();
    }
}

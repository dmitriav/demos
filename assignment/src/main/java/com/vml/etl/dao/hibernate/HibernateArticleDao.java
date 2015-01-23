package com.vml.etl.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.vml.etl.dao.ArticleDao;
import com.vml.etl.dto.Article;

/**
 * @author Dmitri Avdejev
 * @since 21/11/2012
 */
@Repository
public class HibernateArticleDao implements ArticleDao {

	private SessionFactory sessionFactory;


	@Override
	public int deleteAll() {
		String queryString = "delete ArticleEntity";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryString);

		int deletedEntities = query.executeUpdate();
		return deletedEntities;
	}

	@Override
	public Integer save(Article article) {
		if (article == null) {
			return null;
		}

		Session session = sessionFactory.getCurrentSession();

		ArticleEntity articleEntity = new ArticleEntity();
		articleEntity.update(article);

		Integer id = (Integer) session.save(articleEntity);
		return id;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

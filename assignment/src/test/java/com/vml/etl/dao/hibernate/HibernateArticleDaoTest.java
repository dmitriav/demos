package com.vml.etl.dao.hibernate;

import javax.annotation.Resource;
import javax.sql.DataSource;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.vml.etl.dto.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration({"/beans-common-test.xml", "/beans-article.xml"})
public class HibernateArticleDaoTest {

	@Resource
	private DataSource testDataSource;

	@Resource
	private HibernateArticleDao articleDao;

	@Resource
	private SessionFactory sessionFactory;

	private Session session;


	@Before
	public void setUp() throws Exception {
		JdbcTemplate template = new JdbcTemplate(testDataSource);
		JdbcTestUtils.deleteFromTables(template, "DATA");

		session = sessionFactory.getCurrentSession();
	}


	@Test
	public void deleteAll() {
		int count = articleDao.deleteAll();
		Assert.assertEquals(0, count);
	}

	@Test
	public void save() {
		Article article = new Article();
		article.setArticleId(2);
		article.setAttribute("Attribute");
		article.setValue("Value");
		article.setLanguage(3);
		article.setType(4);

		Integer id = articleDao.save(article);
		Assert.assertNotNull(id);

		session.flush();
		session.clear();

		ArticleEntity articleEntity = (ArticleEntity) session.get(
				ArticleEntity.class, id);
		Assert.assertNotNull(articleEntity);

		Integer actualId = articleEntity.getId();
		Assert.assertEquals(id, actualId);

		Integer articleId = articleEntity.getArticleId();
		Assert.assertEquals((Integer) 2, articleId);

		String attribute = articleEntity.getAttribute();
		Assert.assertEquals("Attribute", attribute);

		String value = articleEntity.getValue();
		Assert.assertEquals("Value", value);

		Integer language = articleEntity.getLanguage();
		Assert.assertEquals((Integer) 3, language);

		Integer type = articleEntity.getType();
		Assert.assertEquals((Integer) 4, type);
	}
}

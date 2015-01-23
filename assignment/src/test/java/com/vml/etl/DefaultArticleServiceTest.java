package com.vml.etl;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
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
public class DefaultArticleServiceTest {

	@Resource
	private DataSource testDataSource;

	@Resource
	private DefaultArticleService articleService;

	@Resource
	private SessionFactory sessionFactory;

	private JdbcTemplate jdbcTemplate;
	private Session session;


	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(testDataSource);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "DATA");

		session = sessionFactory.getCurrentSession();
	}


	@Test
	public void importFromFile() throws Exception {
		String filePath = FileUtils.getFilePath("test.csv");
		articleService.importFromFile(filePath);

		session.flush();
		session.clear();

		int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DATA");
		Assert.assertEquals(69, count);
	}

	@Test
	public void getArticles() throws Exception {
		String filePath = FileUtils.getFilePath("test.csv");
		List<Article> articles = articleService.getArticles(filePath);
		Assert.assertNotNull(articles);
		int count = articles.size();
		Assert.assertEquals(69, count);

		Article article = articles.get(68);
		Article expectedArticle = new Article();
		expectedArticle.setId(26088);
		expectedArticle.setArticleId(5561);
		expectedArticle.setAttribute("title");
		expectedArticle.setValue("Cadence");
		expectedArticle.setLanguage(null);
		expectedArticle.setType(1);
		Assert.assertEquals(expectedArticle, article);
	}

	@Test
	public void getArticlesWrongNumberOfColumns() throws Exception {
		String filePath = FileUtils.getFilePath("test-bad-1.csv");
		List<Article> articles = articleService.getArticles(filePath);
		Assert.assertNotNull(articles);
		int count = articles.size();
		Assert.assertEquals(1, count);
	}

	@Test
	public void getArticlesNoRecords() throws Exception {
		String filePath = FileUtils.getFilePath("test-bad-2.csv");
		List<Article> articles = articleService.getArticles(filePath);
		Assert.assertNull(articles);
	}

	@Test
	public void getArticlesEmptyFile() throws Exception {
		String filePath = FileUtils.getFilePath("test-bad-3.csv");
		List<Article> articles = articleService.getArticles(filePath);
		Assert.assertNull(articles);
	}
}

package com.vml.etl;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.vml.etl.dao.ArticleDao;
import com.vml.etl.dto.Article;

/**
 * Default service implementation
 * 
 * @author Dmitri Avdejev
 * @since 21/11/2012
 */
@Transactional(readOnly = true)
public class DefaultArticleService implements ArticleService {

	private static Log log = LogFactory.getLog(DefaultArticleService.class);

	private ArticleDao articleDao;


	@Override
	@Transactional(readOnly = false)
	public void importFromFile(String fileName) throws Exception {
		List<Article> articles = getArticles(fileName);

		int deletedCount = articleDao.deleteAll();
		log.debug(deletedCount + " old article(s) deleted");

		if (articles == null || articles.size() == 0) {
			log.debug("No new articles imported");
		} else {
			int count = articles.size();
			log.debug("Importing " + count + " article(s)...");
			for (Article article : articles) {
				articleDao.save(article);
			}
			log.debug("Done");
		}
	}

	/**
	 * Parses file using Super CSV library. 
	 * If record can't be parsed then it's skipped to the next one.
	 * 
	 * @param fileName a file to parse
	 * @return collection of extracted articles
	 * @throws Exception if file not found or can't be parsed
	 */
	protected List<Article> getArticles(String fileName) throws Exception {
		log.debug("About to load articles from: " + fileName);

		String [] columns = {"id", "articleId", "attribute", "value", 
				"language", "type"};

		CellProcessor parseInt = new ParseInt();
		CellProcessor optionalParseInt = new Optional(parseInt);
		CellProcessor optional = new Optional();
		CellProcessor [] processors = { 
				optionalParseInt,
				optionalParseInt,
				optional,
				optional,
                optionalParseInt,
                optionalParseInt
        };

		CsvBeanReader csvBeanReader = null;
		List<Article> articles = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			csvBeanReader = new CsvBeanReader(fileReader, 
					CsvPreference.STANDARD_PREFERENCE);

			// Skip the header...
			csvBeanReader.getHeader(true);

			// If row can't be parsed then skips to the next one... 
			while (true) {
				Article article = null;
				try {
					article = csvBeanReader.read(
							Article.class, columns, processors);
					if (article == null) {
						break;
					}

					log.debug(article);

					if (articles == null) {
						articles = new ArrayList<Article>();
					}
					articles.add(article);

				} catch (SuperCsvException exception) {
					log.warn(exception);
					continue;
				}
			}
		} finally {
			if (csvBeanReader != null) {
				csvBeanReader.close();
			}
		}

		return articles;
	}


	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
}

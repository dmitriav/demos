package com.vml.etl.dao;

import com.vml.etl.dto.Article;

/**
 * @author Dmitri Avdejev
 * @since 21/11/2012
 */
public interface ArticleDao {

	/**
	 * Deletes all articles
	 * 
	 * @return number of articles deleted
	 */
	int deleteAll();

	/**
	 * Saves a new article record
	 * 
	 * @param article a new article
	 * @return article id
	 */
	Integer save(Article article);
}

package com.vml.etl;

/**
 * Service for article management
 * 
 * @author Dmitri Avdejev
 * @since 21/11/2012
 */
public interface ArticleService {

	/**
	 * Imports articles from the specified file name into database
	 * 
	 * @throws Exception thrown when the data import failed.
	 */
	void importFromFile(String filePath) throws Exception;
}

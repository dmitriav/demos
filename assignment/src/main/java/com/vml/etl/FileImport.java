package com.vml.etl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * This solution demonstrates the use of Maven, Spring Framework, Hibernate, 
 * Log4J and JUnit since it's a most common stack for real-life projects. 
 * 
 * Alternatively it is also possible to use Apache Camel for data loading 
 * or just have a simple JDBC code to execute "LOAD DATA INFILE" statement.
 * 
 * @author Dmitri Avdejev
 * @since 21/11/2012
 */
public class FileImport {

	private static Log log = LogFactory.getLog(FileImport.class);


	public static void main(String [] arguments) throws Exception {
		if (arguments == null || arguments.length == 0) {
			log.error("Please specify a database dump file path");
			return;
		}

		String filePath = arguments[0];

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans-common.xml", "beans-article.xml");
		ArticleService articleService = context.getBean("articleService", 
				ArticleService.class);
		articleService.importFromFile(filePath);
	}
}

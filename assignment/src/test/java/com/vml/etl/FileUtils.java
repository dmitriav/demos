package com.vml.etl;

import java.io.File;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Dmitri Avdejev
 * @since 21/11/2012
 */
public class FileUtils {

	/**
	 * Returns a full path for the file from a class path
	 */
	public static String getFilePath(String classPath) throws Exception {
		ClassPathResource resource = new ClassPathResource(classPath);
		File file = resource.getFile();
		if (file == null) {
			return null;
		}

		String filePath = file.getPath();
		return filePath;
	}
}

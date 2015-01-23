package com.vml.etl;

import org.junit.Test;

public class FileImportTest {

	@Test
	public void mainTest() throws Exception {
		String filePath = FileUtils.getFilePath("test.csv");
		String [] arguments = {filePath};
		FileImport.main(arguments);

		// Should not throw any exception!
	}
}

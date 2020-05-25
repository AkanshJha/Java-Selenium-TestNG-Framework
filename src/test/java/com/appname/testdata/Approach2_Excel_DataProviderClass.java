package com.appname.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.appname.testcases.BaseClass;
import com.appname.utilities.ReadPropertiesUtils;

public class Approach2_Excel_DataProviderClass {
	Logger log = LogManager.getLogger(Approach2_Excel_DataProviderClass.class.getName());

	@DataProvider(name = "DataSupplier")
	public Object[] dataSupplier(Method m) {
		// declaring the local variables
		Object[] result = null;
		String propertiesFilePath = BaseClass.currentDirectory + "\\configurations\\ApplicationData.properties";
		Properties properties = ReadPropertiesUtils.loadPropertiesFile(propertiesFilePath);
		String testDataFilePath = ReadPropertiesUtils.getStringPropertyValue(properties, "test_data_excel_file");
		String testCaseName = m.getName(); // Current Method Name is Test Case Name
		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		XSSFCell cell = null;
		int lastRowNumber = 0;
		int startRowIndex = 0;
		int lastRowIndex = 0;
		int lastCellIndex = 0;
		int resultArraySize = 0;
		int currentIndexOfArray = 0;
		DataFormatter dataFormatter = null;
		Map<Object, Object> dataMap = null;

		// Initializing the variables
		dataFormatter = new DataFormatter();
		try {
			file = new FileInputStream(new File(testDataFilePath));
		} catch (FileNotFoundException e) {
			log.error("File is not found on location '" + testDataFilePath + "'.");
		}

		try {
			workbook = new XSSFWorkbook(file);
			// workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (NotOfficeXmlFileException e) {
			log.error(
					"Format of the test data file seems to be invalid. Please make sure, you are using \".xlsx\" format file to retrieve the Test Data.\nExecution is terminated.",
					e);
			System.exit(0);
		}
		/*
		 * catch(InvalidFormatException e) { log.error(
		 * "Format of the test data file seems to be invalid. Please make sure, you are using \".xlsx\" format file to retrieve the Test Data.\nExecution is terminated."
		 * , e); System.exit(0); }
		 */
		catch (FileNotFoundException e) {
			log.error(
					"Please make sure the file, to be read, is not open. Please close it if it is open.\nExecution is terminated.",
					e);
			System.exit(0);
		} catch (IOException e) {
			log.error(
					"Please make sure the file, to be read, is not open. Please close it if it is open. Or the file is corrupted. Please check.\nExecution is terminated.",
					e);
			System.exit(0);
		}

		sheet = workbook.getSheetAt(0); // Fetching the sheet at index '0'
		lastRowNumber = sheet.getLastRowNum();
		log.debug("Workbook, sheet objects are created. Last Row Index in the sheet is '" + lastRowNumber + "'.");

		// Searching for the Test Case Name, in the first column of each row
		int i = 0;
		for (; i < lastRowNumber; i++) {
			cell = sheet.getRow(i).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if (dataFormatter.formatCellValue(cell).equals(testCaseName)) {
				startRowIndex = cell.getRowIndex(); // Index of our required Row.
				lastCellIndex = sheet.getRow(startRowIndex).getLastCellNum(); // Number of cells in our required row.
				// getting the last row index for this test case
				int j = i + 1;

				while (dataFormatter.formatCellValue(sheet.getRow(j).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK))
						.equals("") && j <= lastRowNumber) {
					if (j == lastRowNumber) {
						lastRowIndex = j;
						resultArraySize = j - startRowIndex;
						break;
					} else {
						j++;
						lastRowIndex = j - 1;
						resultArraySize = (j - startRowIndex) - 1;
					}
				}
				break; // breaking, because once the index found, we do not want search it further.

			}
		}
		// Terminating the execution if No Test Data for the test case is found.
		if (i == lastRowNumber) {
			log.error("No Test Data is found for the Test Case '" + testCaseName
					+ "'. Please update the test data sheet with test data for this test case.\nTerminating the execution.");
			System.exit(0);
		}

		// Initializing the Result Array
		result = new Object[resultArraySize];
		// Iterating through the Test Case Related Test Data
		int r = startRowIndex + 1;
		for (cell = sheet.getRow(r).getCell(0,
				MissingCellPolicy.CREATE_NULL_AS_BLANK); (dataFormatter.formatCellValue(cell).equals(""))
				&& r <= lastRowNumber && r <= lastRowIndex; r++) {
			dataMap = new HashMap<Object, Object>();
			// Iterating the cells of current row
			for (int c = 1; c < lastCellIndex; c++) {
				Object keyName = dataFormatter.formatCellValue(
						sheet.getRow(startRowIndex).getCell(c, MissingCellPolicy.CREATE_NULL_AS_BLANK));
				Object valueForKey = dataFormatter
						.formatCellValue(sheet.getRow(r).getCell(c, MissingCellPolicy.CREATE_NULL_AS_BLANK));
				dataMap.put(keyName, valueForKey); // each Row Data is store in this Map object
			}
			result[currentIndexOfArray++] = dataMap; // each Map object is assigned in array on various Index.
		}

		// closing the workBook object.
		if (workbook != null) {
			log.debug("WorkBook object is being closed.");
			try {
				workbook.close();
				log.debug("WorkBook object has been closed.");
			} catch (IOException e) {
				log.error("Some Error Occured while closing the workbook Object.");
			}
		}
		// returning the result Array Object
		return result;
	}

}
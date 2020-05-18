package com.appname.testdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.DataProvider;

import com.appname.testcases.BaseClass;
import com.appname.utilities.ExcelUtils;

public class Excel_DataProviderClass {
	private static Logger log = LogManager.getLogger();
	
	@DataProvider(name = "userName-password")
	public String[][] getData(){
		log.debug("Reading the Excel file using the DataProviderClass.");
		String workBookPath = BaseClass.currentDirectory + "\\src\\test\\java\\com\\appname\\testdata\\UserNames - Password Test Data.xlsx";
		System.out.println(workBookPath);
		int sheetIndex = 0;
		int rowCount = 0;
		int colCount = 0;
		XSSFSheet sheet = ExcelUtils.getSheetByIndex(workBookPath, sheetIndex);
		String[][] result = null;
		
		
		rowCount = ExcelUtils.getRowsCountInSheet(sheet);
		colCount = ExcelUtils.getColumnCountInSheet(sheet);
		
		result = new String[rowCount][colCount];
		
		for(int row = 1; row<=rowCount; row++) {
			for(int column = 0; column<colCount; column++) {
				result[row-1][column] = ExcelUtils.getCellData(sheet, row, column);
			}
		}
		log.debug("Excel has been read successfully.");
		
		return result;		
	}

}

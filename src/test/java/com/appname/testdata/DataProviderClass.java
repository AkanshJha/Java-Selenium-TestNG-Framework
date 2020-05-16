package com.appname.testdata;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.DataProvider;

import com.appname.testcases.BaseClass;
import com.appname.utilities.ExcelUtils;

public class DataProviderClass {
	
	@DataProvider(name = "userName-password")
	public String[][] getData(){
		String workBookPath = BaseClass.currentDirectory + "\\src\\test\\java\\com\\appname\\testdata\\UserNames - Password Test Data.xlsx";
		System.out.println(workBookPath);
		int sheetIndex = 0;
		int rowCount = 0;
		int colCount = 0;
		XSSFSheet sheet = ExcelUtils.getSheetByIndex(workBookPath, sheetIndex);
		String[][] result = null;
		
		rowCount = ExcelUtils.getRowsCountInSheet(sheet);
		colCount = ExcelUtils.getColumnCountInSheet(sheet);
		
		for(int row = 1; row<=rowCount; row++) {
			for(int column = 0; column<colCount; column++) {
				result[row-1][column] = ExcelUtils.getCellData(sheet, row, column);
			}
		}
		
		return result;		
	}

}

package com.appname.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.appname.testcases.SampleClass;

public class ExcelUtils {
	private static Logger log = LogManager.getLogger(SampleClass.class.getName());
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	
	public XSSFSheet getSheetBySheetName(String workBookPath, String sheetName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(workBookPath);
		}
		catch(FileNotFoundException e) {
			log.error("No Excel file available on given path, '"+workBookPath+"'. Please verify and provide the correct path.", e);
		}
		catch(IOException e) {
			log.error("Some error occured while reading the workbook. Please verify.", e);
		}
		catch(Exception e){
			log.error("Some unexpected error occured. Please verify.", e);
		}
		
		try {
			workbook = new XSSFWorkbook(fis);
			log.debug("Workbook object is created.");
		}
		catch(NullPointerException e) {
			log.error("NullPointerException occured while reading the excel file. Please check.", e);
		}
		catch(Exception e) {
			log.error("Error occured while reading the Excel file.", e);
		}
		
		try {
			sheet = workbook.getSheet(sheetName);
		}
		catch(NullPointerException e) {
			log.error("NullPointerException occured while getting the sheet name. Please check.", e);
		}
		catch(Exception e) {
			log.error("No sheet available with name '"+sheetName+"' in the execel file. Please verify.", e);
			
		}
		return sheet;
	}
	
	
	public XSSFSheet getSheetByIndex(String workBookPath, int index) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(workBookPath);
		}
		catch(FileNotFoundException e) {
			log.error("No Excel file available on given path, '"+workBookPath+"'. Please verify and provide the correct path.", e);
		}
		catch(IOException e) {
			log.error("Some error occured while reading the workbook. Please verify.", e);
		}
		catch(Exception e){
			log.error("Some unexpected error occured. Please verify.", e);
		}
		
		try {
			workbook = new XSSFWorkbook(fis);
			log.debug("Workbook object is created.");
		}
		catch(NullPointerException e) {
			log.error("NullPointerException occured while reading the excel file. Please check.", e);
		}
		catch(Exception e) {
			log.error("Error occured while reading the Excel file.", e);
		}
		
		try {
			sheet = workbook.getSheetAt(index);
		}
		catch(NullPointerException e) {
			log.error("NullPointerException occured while getting the sheet name. Please check.", e);
		}
		catch(Exception e) {
			log.error("No sheet available with on index '"+index+"' in the execel file. Please verify.", e);	
		}
		return sheet;
	}
	
	public int getRowsCountInSheet(XSSFSheet sheetObject) {
		int rowNumbers = 0;
		rowNumbers = sheetObject.getLastRowNum();
		return rowNumbers;
	}
	
	public int getColumnCountInSheet(XSSFSheet sheetObject) {
		int columnNumbers = 0;
		columnNumbers = sheetObject.getRow(0).getLastCellNum();
		return columnNumbers;
	}

}

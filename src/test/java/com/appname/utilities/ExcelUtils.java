package com.appname.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.appname.testcases.SampleClass;

public class ExcelUtils {
	private static Logger log = LogManager.getLogger(SampleClass.class.getName());
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;

	/**
	 * 
	 * @param workBookPath - Absolute path of the Workbook/Excel file.
	 * @param sheetName    - Name of the sheet in given workbook
	 * @return XSSFSheet object, obtained using given sheet name.
	 */
	public static XSSFSheet getSheetBySheetName(String workBookPath, String sheetName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(workBookPath);
		} catch (FileNotFoundException e) {
			log.error("No Excel file available on given path, '" + workBookPath
					+ "'. Please verify and provide the correct path.", e);
		} catch (IOException e) {
			log.error("Some error occured while reading the workbook. Please verify.", e);
		} catch (Exception e) {
			log.error("Some unexpected error occured. Please verify.", e);
		}

		try {
			workbook = new XSSFWorkbook(fis);
			log.debug("Workbook object is created.");
		} catch (NullPointerException e) {
			log.error("NullPointerException occured while reading the excel file. Please check.", e);
		} catch (Exception e) {
			log.error("Error occured while reading the Excel file.", e);
		}

		try {
			sheet = workbook.getSheet(sheetName);
		} catch (NullPointerException e) {
			log.error("NullPointerException occured while getting the sheet name. Please check.", e);
		} catch (Exception e) {
			log.error("No sheet available with name '" + sheetName + "' in the execel file. Please verify.", e);

		}
		return sheet;
	}

	/**
	 * 
	 * @param workBookPath - Absolute path of the Workbook/Excel file.
	 * @param sheetName    - Index of the sheet(starting from 0) in given workbook.
	 * @return XSSFSheet object, obtained using given sheet index.
	 */
	public static XSSFSheet getSheetByIndex(String workBookPath, int sheetIndex) {
		FileInputStream fis = null;
		OPCPackage opc = null;
		try {
			 fis = new FileInputStream(new File(workBookPath));
			// fis = new FileInputStream(workBookPath);
			
		} catch (FileNotFoundException e) {
			log.error("No Excel file available on given path, '" + workBookPath
					+ "'. Please verify and provide the correct path.", e);
		} catch (IOException e) {
			log.error("Some error occured while reading the workbook. Please verify.", e);
		} catch (Exception e) {
			log.error("Some unexpected error occured. Please verify.", e);
		}
		
		try {
			if (fis != null) {
				workbook = new XSSFWorkbook(fis);
				log.debug("Workbook object is created.");
			}
			else
			{
				log.error("FinleInputStream object is null.");
			}
		} catch (NullPointerException e) {
			log.error("NullPointerException occured while reading the excel file. Please check.", e);
		} catch (Exception e) {
			log.error("Error occured while reading the Excel file.", e);
		}

		try {
			sheet = workbook.getSheetAt(sheetIndex);
		} catch (NullPointerException e) {
			log.error("NullPointerException occured while getting the sheet name. Please check.", e);
		} catch (Exception e) {
			log.error("No sheet available with on index '" + sheetIndex + "' in the execel file. Please verify.", e);
		}
		return sheet;
	}

	/**
	 * 
	 * @param sheetObject - XSSFSheet object.
	 * @return the count of Rows in the sheet.
	 */
	public static int getRowsCountInSheet(XSSFSheet sheetObject) {
		int rowNumbers = 0;
		rowNumbers = sheetObject.getLastRowNum();
		return rowNumbers;
	}

	/**
	 * 
	 * * @param sheetObject - XSSFSheet object.
	 * 
	 * @return the count of columns in the sheet.
	 */
	public static int getColumnCountInSheet(XSSFSheet sheetObject) {
		int columnNumbers = 0;
		columnNumbers = sheetObject.getRow(0).getLastCellNum();
		return columnNumbers;
	}

	public static String getCellData(XSSFSheet sheetObject, int rowNum, int colNum) {
		String result = "";
		XSSFRow row = sheetObject.getRow(rowNum);
		XSSFCell cell = row.getCell(colNum);
		if (cell.getCellType() == CellType.STRING) {
			result = cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			result = String.valueOf(cell.getNumericCellValue());
		} else {
			result = cell.getStringCellValue();
		}
		return result;
	}
}

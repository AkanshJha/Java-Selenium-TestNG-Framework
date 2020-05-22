package com.appname.testcases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDriven_Sample2 {

	@DataProvider(name = "dp")
	public Object[][] createData(Method m) {
		System.out.println(m.getName());
		return new Object[][] { new Object[] { "Cedric" } };
	}

	@Test(dataProvider = "Data")
	public void sampleTC_001(Map<Object, Object> map) {
		System.out.println("========================  Test Case 001 is being executed  =========================");
		System.out.println(map.get("Password"));
		System.out.println(map.get("userName"));

	}

	@Test(dataProvider = "Data")
	public void sampleTC_002(Map<Object, Object> map) {
		System.out.println("========================  Test Case 002 is being executed  =========================");
		System.out.println(map.get("Password"));
		System.out.println(map.get("userName"));

	}

	// @SuppressWarnings("unchecked")
	@DataProvider(name = "Data")
	public Object[] dataSupplier(Method m) throws IOException, InvalidFormatException {
		File file = new File(
				BaseClass.currentDirectory + "\\src\\test\\java\\com\\appname\\testdata\\Approach2_TestData.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);

		System.out.println(m.getName() + " is being executed.");

		int lastRowNumber = sheet.getLastRowNum();

		System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());

		Object[] obj = null;
		XSSFCell cell = null;
		int startRowIndex = 0;
		int lastRowIndex = 0;
		int lastCellIndex = 0;
		int resultArraySize = 0;
		int currentIndexOfAArray = 0;
		DataFormatter fmt = new DataFormatter();
		System.out.println("Last Row Index = " + lastRowNumber);
		for (int i = 0; i < lastRowNumber; i++) {
			cell = sheet.getRow(i).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			// System.out.println("");
			if (cell.getStringCellValue().equals(m.getName())) {
				startRowIndex = cell.getRowIndex();
				System.out.println("Start Row Index is " + startRowIndex + ".");
				lastCellIndex = sheet.getRow(startRowIndex).getLastCellNum();
				System.out.println("End Cell Index is " + lastCellIndex + ".");
				int j = i + 1;
				System.out.println("Inside If statement and value j = " + j + " and value is " + sheet.getRow(j)
				.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals(""));
				cell = sheet.getRow(j).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				while ((sheet.getRow(j).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue()
						.equals("")) && j <= lastRowNumber)
					// while(fmt.formatCellValue(cell).equals("") && j <= lastRowNumber)
				{
					System.out.println("Inside while and j = " + j);
					/*
					 * if (j == lastRowNumber) { break; } else { j++; }
					 */
					if (j == lastRowNumber) {
						lastRowIndex = j;
						resultArraySize = j - startRowIndex;
						break;
					} else {
						j++;
						lastRowIndex = j - 1;
						resultArraySize = (j - startRowIndex) - 1;
					}
					// endRowIndex = j - 1;
				}

				/*
				 * if (j == lastRowNumber) { lastRowIndex = j; System.out.println(j + "-" +
				 * startRowIndex + " = "); resultArraySize = (j - startRowIndex); } else {
				 * lastRowIndex = j - 1; System.out.println(j + "-" + startRowIndex + "-1 = ");
				 * resultArraySize = (j - startRowIndex) - 1; }
				 */
				System.out.println("End Row Index is " + lastRowIndex);
				// resultArraySize = endRowIndex - startRowIndex;
				break;
			}

		}
		System.out.println("Result Array Size ====> " + resultArraySize);
		obj = new Object[resultArraySize];
		System.out.println("one Dimenstional Array of size [" + obj.length + "] is created.");
		// System.exit(0);
		// cell = sheet.getRow(1).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		// System.out.println("Trying to print, what blank cell prints ===
		// >'"+cell.getStringCellValue()+"'.");
		int r = startRowIndex + 1;

		Map<Object, Object> dataMap = null;
		for (cell = sheet.getRow(r).getCell(0,
				MissingCellPolicy.CREATE_NULL_AS_BLANK); (cell.getStringCellValue().equals("")) && r <= lastRowNumber
				&& r <= lastRowIndex; r++) {
			dataMap = new HashMap<Object, Object>();
			// currentIndexOfAArray = 0;
			for (int c = 1; c < lastCellIndex; c++) {
				System.out.println("Array's current Index value is " + currentIndexOfAArray);
				Object keyName = sheet.getRow(startRowIndex).getCell(c, MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue();
				// Object valueOnKey = sheet.getRow(r).getCell(c,
				// MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				Object valueOnKey = fmt
						.formatCellValue(sheet.getRow(r).getCell(c, MissingCellPolicy.CREATE_NULL_AS_BLANK));
				System.out.println("dataMap.put(" + keyName + "," + valueOnKey + ");");
				dataMap.put(keyName, valueOnKey);
			}
			obj[currentIndexOfAArray++] = dataMap;
			System.out.println("Array's current Index - " + (currentIndexOfAArray - 1) + "'s value : "
					+ obj[currentIndexOfAArray - 1]);

			System.out.println("One Map Object is created..");
			for (Object name : dataMap.keySet()) {
				// search for value
				Object values = dataMap.get(name);
				System.out.println("Key = " + name + ", Value = " + values);
			}
			// System.gc();
		}
		// System.exit(0);

		for (int i = 0; i < obj.length; i++) {
			System.out.println(obj[i]);
		}

		/*
		 * for (int i = 0; i < obj.length; i++) {
		 * System.out.println("Map details of index " + i);
		 * System.out.println("========================================"); Map<Object,
		 * Object> singleMap = ((HashMap<Object, Object>) obj[i]); for (Object name :
		 * singleMap.keySet()) { // search for value Object values = ((Map<Object,
		 * Object>) obj[i]).get(name); System.out.println("Key = " + name + ", Value = "
		 * + values); }
		 * 
		 * System.out.println("\n\n"); }
		 */

		// System.exit(0);

		/*
		 * for (int i = 0; i < lastRowNumber; i++) { Map<Object, Object> dataMap = new
		 * HashMap<Object, Object>(); for (int j = 0; j < lastColNumber; j++) { //
		 * dataMap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i +
		 * 1).getCell(j).toString()); System.out.println( "Row i = " + i + " and j = " +
		 * j + " ===>" + sheet.getRow(0).getCell(j).getStringCellValue());
		 * System.out.println("Row i = " + (i + 1) + " and j = " + (j + 1) + " ===>" +
		 * sheet.getRow(i + 1).getCell(j).getStringCellValue());
		 * dataMap.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i +
		 * 1).getCell(j).getStringCellValue()); } obj[i][0] = dataMap; }
		 */
		wb.close();
		return obj;
	}

	@Test(dataProvider = "Data")
	public void sampleTC_003(Map<Object, Object> map) {
		System.out.println("========================  Test Case 003 is being executed  =========================");

		System.out.println(map.get("userName"));
		System.out.println(map.get("Password"));
		System.out.println(map.get("name"));
		System.out.println(map.get("relation"));

	}

	@Test(dataProvider = "Data")
	public void sampleTC_004(Map<Object, Object> map) {
		System.out.println("========================  Test Case 004 is being executed  =========================");
		System.out.println(map.get("userName"));
		System.out.println(map.get("Password"));
		System.out.println(map.get("name"));
		System.out.println(map.get("relation"));
	}
}

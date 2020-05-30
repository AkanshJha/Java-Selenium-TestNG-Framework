package com.appname.testcases;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.appname.utilities.TestNG_Listener;

public class SampleClass3 extends BaseClass {

	private static Logger log = LogManager.getLogger(SampleClass3.class.getName());

	// SampleLogInPO login = new SampleLogInPO(driver);
	@Test(dataProvider = "DataSupplier", dataProviderClass = com.appname.testdata.Approach2_Excel_DataProviderClass.class)
	public void sampleTestCase_06(Map<Object, Object> map) {		
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);

		//Adding this to Test Report, in the beginning of details of the test case
		TestNG_Listener.getExtentTestObject().info("<i>For test Data</i> <b>'" + map + "'</b>");

		String testDatUserName = (String) map.get("userName");
		String testDataPWD = (String) map.get("password");
		login.setSearchBoxValue(userName);
		System.out.println(testDatUserName);
		System.out.println(testDataPWD);
		
		

		if (testDatUserName.equalsIgnoreCase("Akansh")) {
			assertTrue(false);
		}
		// System.out.println(map.get("name"));
		// System.out.println(map.get("relation"));

		// throw new SkipException("Skipping this Test");

	}
}

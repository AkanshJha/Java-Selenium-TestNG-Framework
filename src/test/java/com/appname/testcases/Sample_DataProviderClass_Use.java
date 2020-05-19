package com.appname.testcases;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class Sample_DataProviderClass_Use extends BaseClass {

	private static Logger log = LogManager.getLogger(Sample_DataProviderClass_Use.class.getName());

	// SampleLogInPO login = new SampleLogInPO(driver);
	
	@Test(dataProvider = "userName-password", dataProviderClass = com.appname.testdata.Excel_DataProviderClass.class)
	public void executingTestUsingDataProvider(String userName, String password) {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(this.userName);
		System.out.println(userName+" === "+password);

		assertTrue(true);

	}
}

package com.appname.testcases;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.testng.annotations.Test;



public class SampleClass2 extends BaseClass {

	private static Logger log = LogManager.getLogger(SampleClass2.class.getName());
	// SampleLogInPO login = new SampleLogInPO(driver);
	@Test(groups = {"smoke"})
	public void sampleTestCase_05() {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		
		assertTrue(false);
		
		
		
	}
}

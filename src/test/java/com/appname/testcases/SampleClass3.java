package com.appname.testcases;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.testng.ITestListener;
import org.testng.SkipException;
import org.testng.annotations.Test;



public class SampleClass3 extends BaseClass {

	private static Logger log = LogManager.getLogger(SampleClass3.class.getName());
	// SampleLogInPO login = new SampleLogInPO(driver);
	@Test
	public void sampleTestCase_06() {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		throw new SkipException("Skipping this Test");
		
		
	}
}
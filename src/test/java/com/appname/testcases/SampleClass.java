package com.appname.testcases;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.testng.annotations.Test;



public class SampleClass extends BaseClass {

	private static Logger log = LogManager.getLogger(SampleClass.class.getName());
	// SampleLogInPO login = new SampleLogInPO(driver);
	@Test
	public void sampleTestCase_01() {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		log.debug("Test Case 01 has been executed successfully.");
		
		assertTrue(false);
		log.debug("Test Case 01 is failed.");
	}
	@Test
	public void sampleTestCase_02() {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		log.debug("Test Case 02 has been executed successfully.");
		
		assertTrue(true);
		log.debug("Test Case 02 is Passed.");
	}
	@Test
	public void sampleTestCase_03() {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		log.debug("Test Case 03 has been executed successfully.");
		
		assertTrue(false);
		log.debug("Test Case 03 is failed.");	
	}
	@Test
	public void sampleTestCase_04() {
		com.appname.pageobjects.SampleLogInPO login = new com.appname.pageobjects.SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		log.debug("Test Case 04 has been executed successfully.");
		
		assertTrue(true);
		log.debug("Test Case 04 is Passed.");	
	}
}

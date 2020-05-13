package com.appname.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.appname.pageobjects.SampleLogInPO;

public class SampleClass extends BaseClass {

	public static Logger log = LogManager.getLogger(BaseClass.class.getName());
	@Test
	public void sampleTestCase_01() {
		SampleLogInPO login = new SampleLogInPO(driver);
		driver.get(applicationURL);
		login.setSearchBoxValue(userName);
		log.info("Test Case has been executed successfully.");
		
	}
}

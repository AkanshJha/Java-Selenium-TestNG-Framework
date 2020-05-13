package com.appname.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class SampleClass extends BaseClass {

	@Test
	public void sampleTestCase_01() {
		driver.get(prop.getProperty("url"));
		
	}
}

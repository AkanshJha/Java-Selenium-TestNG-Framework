package com.appname.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SampleLogInPO {
	// similarly, we can create Page Object classes for other application Pages.
	WebDriver driver;
	
	
	public SampleLogInPO(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name = "q")
	WebElement searchBoxGoogleHome; 
	
	//Similarly we can add other object values on login page
	
	// Now we will be creating the methods to set the values.
	public void setSearchBoxValue(String userName) {
		searchBoxGoogleHome.sendKeys(userName);
	}

}

package com.appname.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.appname.testcases.BaseClass;
import com.aventstack.extentreports.Status;

public class BasicOperationsOnElements extends BaseClass {

	Logger log = LogManager.getLogger(BasicOperationsOnElements.class.getClass());

	/**
	 * @param inputBox : WebElement of input Box, you want to set data in.
	 * @param dataToSet : Data to be set in input box
	 */
	public void setData(WebElement inputBox, String dataToSet) {
		try {
			if (inputBox.isDisplayed()) {
				inputBox.clear();
				inputBox.sendKeys(dataToSet);
				writeToReport(Status.PASS,
						"Input Box is cleared and data '" + dataToSet + "' is set to input box '" + getWebElementDetails(inputBox) + "'.");
			} else {
				writeToReport(Status.FAIL, "Input box '" + getWebElementDetails(inputBox) + "' is not displayed.");
			}
		} catch (NoSuchElementException e) {
			writeToReport(Status.FAIL, "Could not locate the input box with '" + getWebElementDetails(inputBox) + "'.");
		}
	}
	
	/**
	 * @param radioButton : WebElement of RadioButton, we want to select.
	 */
	public void selectRadioButton(WebElement radioButton) {
		try {
			if(radioButton.isDisplayed()) {
				radioButton.click();
				writeToReport(Status.PASS, "Radio Button is selected with '"+getWebElementDetails(radioButton)+"'.");
			}
			else {
				writeToReport(Status.FAIL, "Radio Button with '"+getWebElementDetails(radioButton)+"' is not displayed.");
			}
		}
		catch(NoSuchElementException e) {
			writeToReport(Status.FAIL, "Could not locate the radio button with '" + getWebElementDetails(radioButton) + "'.");
		}
	}

	/**
	 * @param element : the element, for which we want to pick the locator value details.
	 * @return : returns the locator value.
	 */
	public String getWebElementDetails(WebElement element) {
		String details = "";
		String elementToString = element.toString();
		String[] arrOfString = elementToString.split("-> ");
		elementToString = arrOfString[1];
		details = elementToString.substring(0, elementToString.length()-1);
		return "<b>"+details+"</b>";
	}

}

package com.appname.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.appname.testcases.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/**
 * 
 * @author Akansh Jha
 *
 */
public class TestNG_Listener implements ITestListener {
	private Logger log = LogManager.getLogger(TestNG_Listener.class.getName());
	ExtentReports extent = null;
	ExtentTest test;
	Object[] param = null;
	Map<Object, Object> map = null;

	public void onTestStart(ITestResult result) {
		// ITestListener.super.onTestStart(result);
		// test = extent.createTest(result.getMethod().getMethodName());
		param = result.getParameters();
		if (param.length > 0) {
			map = (Map<Object, Object>) param[0];
			// test = extent.createTest(result.getMethod().getMethodName());
			test = extent.createTest(result.getMethod().getMethodName() + " for data " + map);
		} else {
			test = extent.createTest(result.getMethod().getMethodName());
		}
		System.out.println(map);
		log.debug("Report of the currently executing test case '" + result.getName() + "' is being created.");
	}

	public void onTestSuccess(ITestResult result) {
		String testCaseName = result.getName();
		// writing these logs to execution report

		test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
		if (param.length > 0) {
			test.log(Status.PASS, "for test data, " + map);
		}
		test.log(Status.PASS, "Test case '" + result.getName() + "' is Passed.");

		// writing these log to the log file.
		log.debug(testCaseName + " Status : PASS");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailure(result);
		String testCaseName = result.getMethod().getMethodName();
		String screenshotPath = "";
		File f = null;
		// result.getName() == result.getMethod().getMethodName()
		// writing these logs to the Execution Report
		test.log(Status.FAIL, MarkupHelper.createLabel(testCaseName, ExtentColor.RED));
		if (param.length > 0) {
			test.log(Status.FAIL, "for test data, " + map);
		}
		test.fail(result.getThrowable());

		// writing these logs to the log file
		log.debug(testCaseName + " Status : FAIL");
		log.debug("As test case '" + testCaseName + "' is failed, taking a screenshot of it.");
		try {
			screenshotPath = BaseClass.getScreenshotForGivenTestCase(testCaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error occured while taking a screenshot. Please check the stack trace below : ", e);
		}
		f = new File(screenshotPath);
		if (f.exists()) {
			try {
				test.fail("Screenshot is attcahed below:"+ test.addScreenCaptureFromPath(screenshotPath, testCaseName));

			} catch (IOException e) {
				log.error("This screenshot does not exist/corrupt to attach. Please check the stack trace below : ", e);
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		String testCaseName = result.getName();
		// TODO Auto-generated method stub
		// ITestListener.super.onTestSkipped(result);
		// writing these logs to the Execution Report
		test.log(Status.SKIP, MarkupHelper.createLabel(testCaseName, ExtentColor.ORANGE));
		test.skip("This Test is skipped.");

		// writing these logs to the log file
		log.debug(testCaseName + " Status : SKIP");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailedWithTimeout(result);
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		// ITestListener.super.onStart(context);
		if (extent == null) {
			extent = ExtentReporterNG.getExtentReport();
			log.debug("ExtentReports object has been retrieved. Report creation has been started.");
		}
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		// ITestListener.super.onFinish(context);
		extent.flush();
		log.debug("Extent Report has been flushed.");
	}

}

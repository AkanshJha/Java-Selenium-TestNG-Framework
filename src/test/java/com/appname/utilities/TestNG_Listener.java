package com.appname.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.appname.testcases.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestNG_Listener implements ITestListener {
	private Logger log = LogManager.getLogger(TestNG_Listener.class.getName());
	ExtentReports extent = null;
	ExtentTest test;

	public void onTestStart(ITestResult result) {
		// ITestListener.super.onTestStart(result);
		test = extent.createTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestSuccess(result);
		test.log(Status.PASS, "Test is Passed.");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestFailure(result);
		String testCaseName = result.getMethod().getMethodName();
		String screenshotPath = "";
		File f = null;
		// result.getName() == result.getMethod().getMethodName()
		test.fail(result.getThrowable());
		log.debug("As test case is failed, taking a screenshot.");
		try {
			screenshotPath = BaseClass.getScreenshotForGivenTestCase(testCaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f = new File(screenshotPath);
		if (f.exists()) {
			try {
				test.fail("Screenshot is attcahed below:" + test.addScreenCaptureFromPath(screenshotPath, result.getName()));
	
			} catch (IOException e) {
				log.error("This screenshot does not exist to attach.");
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		// ITestListener.super.onTestSkipped(result);
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
		}
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		// ITestListener.super.onFinish(context);
		extent.flush();
	}

}

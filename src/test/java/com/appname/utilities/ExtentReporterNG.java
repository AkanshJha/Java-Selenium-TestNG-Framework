package com.appname.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appname.testcases.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG{
	private static Logger log = LogManager.getLogger(ExtentReporterNG.class.getName());
	public static ExtentReports extentReports = null;
	

	public static ExtentReports getExtentReport() {
		
		String timeStamp = new SimpleDateFormat("dd-mm-yyyy-HH-mm-ss").format(new Date());// time stamp
		String reportFilePath = BaseClass.currentDirectory+"\\reports\\Test-Execution-Report-"+timeStamp+".html";
		System.out.println(reportFilePath);
		/*
		 * if(extentSparkReporter == null) { extentSparkReporter = new
		 * ExtentSparkReporter(reportFilePath);
		 * log.debug("Extent Spark Reporter has been instantiated."); } else {
		 * log.error("Extent Spark Reporter could not get instantiated."); }
		 */
		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportFilePath);
		log.debug("Extent Spark Reporter has been instantiated.");

		extentSparkReporter.config().setReportName("Automation Execution Report");
		extentSparkReporter.config().setDocumentTitle("Execution Report");

		extentReports =  new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
		extentReports.setSystemInfo("Host name", "localhost");
		extentReports.setSystemInfo("Environemnt", "Production");
		extentReports.setSystemInfo("user", "Akansh");
		extentReports.setSystemInfo("Tester Name", "Akansh Jha");
		// extentSparkReporter.config().setTheme(Theme.DARK);

		return extentReports;
	}

}

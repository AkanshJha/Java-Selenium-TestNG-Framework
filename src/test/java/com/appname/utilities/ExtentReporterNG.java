package com.appname.utilities;

import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.appname.testcases.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * 
 * @author Akansh Jha
 * @since May 14th, 2020
 *
 */
public class ExtentReporterNG {
	private static Logger log = LogManager.getLogger(ExtentReporterNG.class.getName());
	public static ExtentReports extentReports = null;
	public static ExtentSparkReporter extentSparkReporter = null;

	public static ExtentReports getExtentReport() {

		// String timeStamp = new SimpleDateFormat("dd-mm-yyyy-HH-mm-ss").format(new
		// Date());// time stamp
		// String reportFilePath = BaseClass.currentDirectory +
		// "\\reports\\Test-Execution-Report-" + timeStamp + ".html";
		// Path, where you want to store the execution report
		String reportFilePath = BaseClass.currentDirectory + "\\reports\\Test-Execution-Report.html";

		// Path, where you Execution_Report_Configuration.properties file is located
		String confiReportFilePath = BaseClass.currentDirectory
				+ "\\configurations\\Execution_Report_configurations.properties";

		// Path, where the Execution_Report_System_Info.properties file is located
		String reportPropertiesFilePath = BaseClass.currentDirectory
				+ "\\configurations\\Execution_Report_System_Info.properties";

		// Loading the Execution_Report_Configuration.properties file
		Properties reportConfigProp = ReadPropertiesUtils.loadPropertiesFile(confiReportFilePath);

		// Instantiating Extent Spark Reporter Object
		if (extentSparkReporter == null) {
			extentSparkReporter = new ExtentSparkReporter(reportFilePath);
			log.debug("ExtentSparkReporter has been instantiated.");
		} else {
			log.error("ExtentSparkReporter is already instantiated.");
		}

		// Setting the Report Name
		extentSparkReporter.config()
				.setReportName(ReadPropertiesUtils.getStringPropertyValue(reportConfigProp, "report_name"));

		// Setting the Report Title
		extentSparkReporter.config()
				.setDocumentTitle(ReadPropertiesUtils.getStringPropertyValue(reportConfigProp, "report_tile"));

		// Setting the report theme
		if (ReadPropertiesUtils.getStringPropertyValue(reportConfigProp, "theme").equalsIgnoreCase("DARK")) {
			extentSparkReporter.config().setTheme(Theme.DARK);
			log.debug("Theme is set to DARK Theme in the execution report.");
		} else if (ReadPropertiesUtils.getStringPropertyValue(reportConfigProp, "theme").equalsIgnoreCase("STANDARD")) {
			extentSparkReporter.config().setTheme(Theme.STANDARD);
			log.debug("Theme is set to STANDARD Theme in the execution report.");
		}
		else {
			extentSparkReporter.config().setTheme(Theme.STANDARD);
			log.warn("Default theme is set in the report.");
		}
		
		// Instatiating the ExtentReports object
		extentReports = new ExtentReports();
		log.debug("ExtentReports object has been instantiated.");

		// Attaching the ExtentSparkReporter to the ExtentReports
		extentReports.attachReporter(extentSparkReporter);

		// Setting the System info in the report from
		// Execution_Report_System_Info,properties file
		setSystemInfoFromPropetiesFile(reportPropertiesFilePath);

		return extentReports;
	}

	/**
	 *  It sets the properties in the Execution Report.
	 * @param propertiesFilePath : Path of the Properties file, you want to load.
	 */
	private static void setSystemInfoFromPropetiesFile(String propertiesFilePath) {
		Properties prop = ReadPropertiesUtils.loadPropertiesFile(propertiesFilePath);
		Set<Object> propertiesKeys = null;
		if (propertiesKeys == null) {
			propertiesKeys = ReadPropertiesUtils.getAllPropertiesKeys(prop);
		}
		String keyName = "";

		for (Object key : propertiesKeys) {
			keyName = (String) key;
			// setting system info in extentReports object
			try {
				extentReports.setSystemInfo(keyName, ReadPropertiesUtils.getStringPropertyValue(prop, keyName));
			} catch (NullPointerException e) {
				e.printStackTrace();
				log.error("Error occured while setting system info to the report. Please check stack trace below : ",
						e);
				System.exit(0);
			}
		}
		log.debug("System Info is set to report file, given in '" + propertiesFilePath + "' file successfully.");
	}
}
package com.appname.testcases;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.appname.utilities.ReadPropertiesUtils;
import com.appname.utilities.TestNG_Listener;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Akansh Jha
 *
 */
public class BaseClass {

	public static WebDriver driver;
	public static Properties prop = new Properties();
	public String applicationURL = "";
	public String userName = "";
	public String password = "";
	public static String currentDirectory = System.getProperty("user.dir");
	WebDriverWait explitic_wait;
	// FileInputStream fis;
	private static Logger log = LogManager.getLogger(BaseClass.class.getName());

	/**
	 * 
	 * @throws IOException
	 */
	//@BeforeClass
	@BeforeTest
	public void setUp() throws IOException {
		String propFilePath = currentDirectory + "\\configurations\\ApplicationData.properties";
		prop = ReadPropertiesUtils.loadPropertiesFile(propFilePath);
		String requiredBrowser = prop.getProperty("browser");
		int implicitWaitTime = 5; // Default Value
		int explicitWaitTime = 10; // Default Value

		// invoking the required browser
		invokeRequiredBrowser(requiredBrowser);

		// maximizing browser window
		driver.manage().window().maximize();
		log.debug("The browser window is maximized.");

		implicitWaitTime = ReadPropertiesUtils.getIntegerPropertyValue(prop, "implicit_wait_time");
		if (implicitWaitTime != -1) {
			log.debug("Setting Implicit wait to " + implicitWaitTime + " seconds for the execution.");
		} else {
			implicitWaitTime = 5;
			log.warn("Setting Implicit wait to default value " + implicitWaitTime
					+ " seconds for the execution. Please update the properties file if you want to update this time.");
		}
		// setting implicit wait value
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

		explicitWaitTime = ReadPropertiesUtils.getIntegerPropertyValue(prop, "explicit_wait_time");
		if (explicitWaitTime != -1) {
			log.debug("Setting Explicit wait to " + explicitWaitTime + " seconds for the execution.");
		} else {
			explicitWaitTime = 10;
			log.warn("Setting Explicit wait to default value " + explicitWaitTime
					+ " seconds for the execution. Please update the properties file if you want to update this time.");
		}
		// setting explicit wait
		explitic_wait = new WebDriverWait(driver, explicitWaitTime);

		applicationURL = ReadPropertiesUtils.getStringPropertyValue(prop, "application_url");
		if (applicationURL.equals("") && applicationURL == null && applicationURL.isEmpty()) {
			log.error(
					"Application URL is Blank/Null. Please provide the URL correctly in .properties file.\nTerminating the execution.");
			System.exit(0);
		}
		userName = ReadPropertiesUtils.getStringPropertyValue(prop, "userName");
		password = ReadPropertiesUtils.getStringPropertyValue(prop, "password");

		// setting explicit wait
		explitic_wait = new WebDriverWait(driver, explicitWaitTime);

	}

	/*
	 * This method will be called after each test case class is executed. This
	 * method is used to kill the driver instantiation. This method will be closing
	 * all the opened browser windows.
	 */
	//@AfterClass
	@AfterTest
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		log.debug("Tearing Down the Class.");
		log.debug("Closing all the opened browsers.");
		try {
			driver.quit();
		} catch (Exception e) {
			log.error("Error occured while closing all the browsers.");
			log.debug("Execution finished.");
		}
	}

	/**
	 * @author Akansh Jha This method will be opening the given required browser
	 * @param browserName : It is the browser name, for which the driver will be
	 *                    instantiated.
	 */
	private void invokeRequiredBrowser(String browserName) {
		try {
			if (browserName.equals("chrome")) {
				// System.setProperty("webdriver.chrome.driver",currentDirectory+"\\driver\\chromedriver.exe");
				log.debug("Fetching the Chrome driver from WebDriverManager.");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Instantiating the Chrome Driver.");
			} else if (browserName.equals("firefox")) {
				// System.setProperty("webdriver.gecko.driver",
				// currentDirectory+"\\driver\\geckodriver.exe");
				log.debug("Fetching the Firefox driver from WebDriverManager.");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("Instantiating the Firefox Driver.");
			} else if (browserName.equals("ie")) {
				// System.setProperty("webdriver.ie.driver",
				// currentDirectory+"\\driver\\iedriverserver.exe");
				log.debug("Fetching the Internet Explorer driver from WebDriverManager.");
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				log.debug("Instantiating the Internet Explorer Driver.");

			} else {
				log.warn(
						"Browser value is not given or incorrect in properties file. Please check.\nRunning on Chrome Browser by default.");
				// System.setProperty("webdriver.chrome.driver",currentDirectory+"\\driver\\chromedriver.exe");
				log.debug("Fetching the Chrome driver from WebDriverManager.");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Instantiating the Chrome Driver.\n");
			}
		} catch (Exception e) {
			log.error("Error occured while fetching/instantiating the browser driver. Please check.", e);
			log.fatal("Terminating the whole execution.\n");
			System.exit(0);
		}
	}

	
	/**
	 * 
	 * @param testCaseName : Name of the testcase for which we are going to take this screenshot.
	 * @return the path, where this screenshot is being stored.
	 * @throws Exception
	 */
	public static String getScreenshotForGivenTestCase(String testCaseName) throws Exception {
		TakesScreenshot sc = (TakesScreenshot) driver;
		File source = sc.getScreenshotAs(OutputType.FILE);
		String destination = currentDirectory+"//reports//screenshots//"+testCaseName+".png";
		FileUtils.copyFile(source, new File(destination));
		return destination;
	}
	
	/**
	 * 
	 * @param status : Status you wanna pass, Status.PASS o Status.FAIL
	 * @param message : message you want to attach to the report for the test case.
	 */
	public static void wrtieToReport(Status status, String message) {
		ExtentTest test = TestNG_Listener.getExtentTestObject();
		test.log(status,message);
		log.debug("message has been added to the extent report from the test case.");
	}
}

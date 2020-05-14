package com.appname.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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
	public String currentDirectory = System.getProperty("user.dir");
	WebDriverWait explitic_wait;
	// FileInputStream fis;
	private static Logger log = LogManager.getLogger(BaseClass.class.getName());
	

	/**
	 * 	
	 * @throws IOException
	 */
	@BeforeClass
	public void setUp() throws IOException {
		loadPropertiesFile();
		String requiredBrowser = prop.getProperty("browser");
		int implicitWaitTime = Integer.valueOf(prop.getProperty("implicit_Wait_value"));
		int explicitWaitTime = Integer.valueOf(prop.getProperty("explicit_wait"));
		applicationURL = prop.getProperty("url");
		userName = prop.getProperty("userName");
		password = prop.getProperty("password");
		
		
		//invoking the required browser
		invokeRequiredBrowser(requiredBrowser);
		
		//maximizing browser window
		driver.manage().window().maximize();
		log.debug("The browser window is maximized.");
		
		//setting implicit wait value
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		log.debug("Implicit wait is set to "+implicitWaitTime+" seconds for the execution.");

		// setting explicit wait
		explitic_wait = new WebDriverWait(driver, explicitWaitTime);
		log.debug("Explicit wait is set to "+explicitWaitTime+" seconds for the execution.");
	}

	/*
	 * This method will be called after each test case class is executed.
	 * This method is used to kill the driver instantiation.
	 * This method will be closing all the opened browser windows.
	 */
	@AfterClass
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		log.debug("Tearing Down the Class.");
		log.debug("Closing all the opened browsers.");
		try {
		driver.quit();
		}
		catch(Exception e) {
			log.error("Error occured while closing all the browsers.");
			log.debug("Execution finished.");
		}
	}
	
	/**
	 * @author Akansh Jha
	 * This method will be opening the given required browser
	 * @param browserName : It is the browser name, for which the driver will be instantiated.
	 */
	private void invokeRequiredBrowser(String browserName) {
		try {
		if(browserName.equals("chrome")) {	
			//System.setProperty("webdriver.chrome.driver",currentDirectory+"\\driver\\chromedriver.exe");
			log.debug("Fetching the Chrome driver from WebDriverManager.");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.debug("Instantiating the Chrome Driver.");
		}
		else if(browserName.equals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", currentDirectory+"\\driver\\geckodriver.exe");
			log.debug("Fetching the Firefox driver from WebDriverManager.");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			log.debug("Instantiating the Firefox Driver.");
		}
		else if(browserName.equals("ie")) {
			//System.setProperty("webdriver.ie.driver", currentDirectory+"\\driver\\iedriverserver.exe");
			log.debug("Fetching the Internet Explorer driver from WebDriverManager.");
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			log.debug("Instantiating the Internet Explorer Driver.");

		}
		else {
			log.warn("Browser value is not given or incorrect in properties file. Please check.\nRunning on Chrome Browser by default.");
			//System.setProperty("webdriver.chrome.driver",currentDirectory+"\\driver\\chromedriver.exe");
			log.debug("Fetching the Chrome driver from WebDriverManager.");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.debug("Instantiating the Chrome Driver.\n");
		}
		}
		catch(Exception e) {
			log.error("Error occured while fetching/instantiating the browser driver. Please check.", e);
			log.fatal("Terminating the whole execution.\n");
			System.exit(0);
		}
	}
	
	private void loadPropertiesFile() {
		try {
			FileInputStream fis = new FileInputStream(currentDirectory+"\\configurations\\data.properties");
			prop.load(fis);
		}
		catch (FileNotFoundException e) {
			log.debug(e.getMessage());
			log.error("Properties File is not available in '"+currentDirectory+"/configurations/' folder. Please place the file in this location.", e);
		}
		catch(IOException e) {
			log.debug(e.getMessage());
			log.error("Could not read the file. Please make sure properties file is ready to be used.", e);
		}
		catch(Exception e) {
			log.fatal("Some unexpected error occured. Please make sure properties file is ready to be used.", e);
		}
		
		
	}
}

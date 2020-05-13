package com.appname.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

/* This class may contain the Basic elements for the test cases,
 * which may be used by all of the test cases. * 
 */
public class BaseClass {
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public String applicationURL = "";
	public String userName = "";
	public String password = "";
	public String currentDirectory = System.getProperty("user.dir");
	WebDriverWait explitic_wait;
	FileInputStream fis;

	/*
	 * This method will be call before every Test Case class is called.
	 * This method will be invoking the required browser as given in properties file.
	 */
	@BeforeClass
	public void setUp() throws IOException {
		fis = new FileInputStream(currentDirectory+"\\configurations\\data.properties");
		prop.load(fis);
		String requiredBrowser = prop.getProperty("browser");
		int implicitWaitTime = Integer.valueOf(prop.getProperty("implicit_Wait_value"));
		int explicitWaitTime = Integer.valueOf(prop.getProperty("explicit_wait"));
		
		//invoking the required browser
		invokeRequiredBrowser(requiredBrowser);
		
		//maximizing browser window
		driver.manage().window().maximize();
		
		//setting implicit wait value
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

		// setting explicit wait
		explitic_wait = new WebDriverWait(driver, explicitWaitTime);
	}

	/*
	 * This method will be called after each test case class is executed.
	 * This method is used to kill the driver instantiation.
	 * This method will be closing all the opened browser windows.
	 */
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	private void invokeRequiredBrowser(String browserName) {
		if(browserName.equals("chrome")) {	
			//System.setProperty("webdriver.chrome.driver",currentDirectory+"\\driver\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", currentDirectory+"\\driver\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		}
		else if(browserName.equals("ie")) {
			//System.setProperty("webdriver.ie.driver", currentDirectory+"\\driver\\iedriverserver.exe");
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();

		}
		else {
			System.out.println("Browser value is not given or incorrect i properties file. Please check.\nRunning on Chrome Browser by default.");
			//System.setProperty("webdriver.chrome.driver",currentDirectory+"\\driver\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
	}
}

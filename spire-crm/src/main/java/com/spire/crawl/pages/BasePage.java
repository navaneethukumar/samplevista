package com.spire.crawl.pages;

import static com.spire.base.driver.web.BrowserConfig.downloadChProfile;
import static com.spire.base.driver.web.BrowserConfig.downloadFxProfile;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.common.FileReader;

public class BasePage extends WebPageHelper {

	public WebDriver driver = null;
	InputStream input = null;
	public static String LOADING_TEXT = "Loading Data..";
	Logger logger = Logger.getLogger(BasePage.class);
	long TIMEOUT_S = 60;
	
	/**********************************************************************
	 * Profile Headers
	 * 
	 *********************************************************************/	
	public By profileHeader = By.xpath("//div[@id='HeaderProfDiv']");
	public By logout=By.linkText("Logout");

	public BasePage(WebDriver driver) {
		this.driver = getDriver(driver);
		this.driver.manage().timeouts().implicitlyWait(TIMEOUT_S, TimeUnit.SECONDS);
	}

	public WebDriver getDriver(WebDriver driver) {

		try {


			Properties prop = new FileReader()
					.loadPropertiesFile("/properties/browser-config.properties");

			if (driver == null) {
				String browserName = ContextManager.getThreadContext()
						.getBrowser();
				String host = ContextManager.getThreadContext().getUIHostAddress();
				String webRunMode=ContextManager.getThreadContext().getSeleniumGrid();
				
				logger.info("HOST ------------->>" + host);
				logger.info("Browser ----------->>"+browserName);
				logger.info("Webrunmode ----------->>"+webRunMode);

				if (webRunMode!=null && !webRunMode.equalsIgnoreCase("false") && webRunMode.equalsIgnoreCase("true")) {
					
					URL url=new URL("http://172.16.0.87:4444/wd/hub");
					
					if (browserName.equalsIgnoreCase("*Firefox")) {
						System.setProperty("webdriver.gecko.driver",
				                "C:\\selenium-grid-setup\\geckodriver.exe");
						DesiredCapabilities cap=DesiredCapabilities.firefox();
						 // Set the platform where we want to run our test- we can use MAC and Linux and other platforms as well
						cap.setPlatform(Platform.WINDOWS);
						// Create driver with hub address and capability
						driver=new RemoteWebDriver(url, cap);						
					}else if (browserName.equalsIgnoreCase("Chrome")) {
						System.setProperty("webdriver.chrome.driver",
				                "C:\\selenium-grid-setup\\chromedriver.exe");
						DesiredCapabilities cap=DesiredCapabilities.chrome();
						 // Set the platform where we want to run our test- we can use MAC and Linux and other platforms as well
						cap.setPlatform(Platform.WINDOWS);
						// Create driver with hub address and capability
						driver=new RemoteWebDriver(url, cap);							
					}else if (browserName.equalsIgnoreCase("IE")) {
						System.setProperty("webdriver.ie.driver",
				                "C:\\selenium-grid-setup\\IEDriver.exe");
						DesiredCapabilities cap=DesiredCapabilities.internetExplorer();
						 // Set the platform where we want to run our test- we can use MAC and Linux and other platforms as well
						cap.setPlatform(Platform.WINDOWS);
						// Create driver with hub address and capability
						driver=new RemoteWebDriver(url, cap);								
					}	
					
					
				}else{
				
					if (browserName.equalsIgnoreCase("*Firefox")) {
						ContextManager.getGlobalContext().setOpenReportInBrowser(
								"firefox");
						driver = new FirefoxDriver(downloadFxProfile());
					} else if (browserName.equalsIgnoreCase("Chrome")) {
						ContextManager.getGlobalContext().setOpenReportInBrowser(
								"chrome");
						System.setProperty("webdriver.chrome.driver",
								prop.getProperty("ChromeDriverPath"));
						driver = new ChromeDriver(downloadChProfile());
					} else if (browserName.equalsIgnoreCase("IE")) {
						ContextManager.getGlobalContext().setOpenReportInBrowser(
								"chrome");
						System.setProperty("webdriver.ie.driver",
								prop.getProperty("IEDriverPath"));
						driver = new InternetExplorerDriver();
					} else {
						driver = new HtmlUnitDriver();
					}
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logging.log("Exception thrown while intializing the driver "
					+ e.getMessage());
		}

		System.out.println("getDriver method completed");

		return driver;

	}

	public boolean isElementPreset(By object) {

		try {
			waitForElementToBeVisible(driver, object);
			return this.driver.findElement(object).isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}
	
	public void logout() {
		driver.findElement(profileHeader).click();
		waitForElementToBeVisible(this.driver,logout);
		driver.findElement(logout).click();
		WebPageHelper.sleep(5000);
	}

}
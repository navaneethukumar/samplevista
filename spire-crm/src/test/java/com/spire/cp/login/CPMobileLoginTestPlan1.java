package com.spire.cp.login;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CPMobileLoginTestPlan1 {
	
	public static void main(String[] args) throws Exception {
		
		@SuppressWarnings("rawtypes")
		AndroidDriver<WebElement> driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", "Galaxy J2");
		capabilities.setCapability("platformVersion", "5.1.1");
		capabilities.setCapability("platformName", "Android");
		//capabilities.setCapability("appPackage", "io.selendroid.testapp");
		//capabilities.setCapability("appActivity", ".HomeScreenActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
				capabilities);

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.findElement(
				By.xpath("//android.widget.EditText[@text='Email address']"))
				.sendKeys("LAASYA");

		driver.findElement(
				By.xpath("//android.widget.EditText[@content-desc='passwordInput']"))
				.sendKeys("SAI");

				
		driver.findElement(
				By.xpath("//android.view.View[@content-desc='signInButton']")).click();
		
		
		Assert.assertNotNull(driver.findElement(By.xpath("//android.widget.TextView[@text='Forgot password?']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//android.widget.TextView[@text='New user?']")));
		
		
		driver.findElement(
				By.xpath("//android.view.View[@content-desc='signInButton']")).click();
		
		
		
				
		
		
		
	/*	
	 * Initial approach
	 * 
	 * driver.findElement(
				By.xpath("//android.widget.EditText[@text='Email address']"))
				.sendKeys("LAASYA");

		driver.findElement(
				By.xpath("//android.widget.EditText[@password='true']"))
				.sendKeys("SAI");

				
		driver.findElement(
				By.xpath("//android.widget.TextView[@text='Sign in']")).click();
				
		driver.findElement(
				By.xpath("//android.widget.TextView[@text='Sign in']")).click();*/
		
		
		/*TouchAction touchAction = new TouchAction((MobileDriver<WebElement>)driver);
		
		touchAction.longPress(element).perform();*/
				
		
		Thread.sleep(10000);

		driver.quit();
		
	}

}

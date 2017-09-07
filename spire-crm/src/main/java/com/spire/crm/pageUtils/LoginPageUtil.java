package com.spire.crm.pageUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.JobsPage;
import com.spire.crm.pages.LoginPage;

public class LoginPageUtil extends LoginPage {

	public LoginPageUtil(WebDriver driver, boolean openurl) {
		super(driver, openurl);
	}
	
	public LoginPageUtil(WebDriver driver, String openurl) {
		super(driver, openurl);
	}

	/*
	 * 
	 * Login to the CRM VISTA application..
	 */

	public void login() {

		String userid = "crm@test";
		String userPwd = "spire@123";
		Logging.log("Logging in");
		this.driver.manage().window().maximize();
		if(!isElementVisiable(driver, userName)){
			waitForElementToBeVisible(driver, loginWithCredentials);
			clicElement(driver, loginWithCredentials);
		}
		waitForElementToBeVisible(driver, userName);
		this.driver.findElement(userName).clear();
		WebPageHelper.waitForElementToBeVisible(driver, userName);
		this.driver.findElement(userName).sendKeys(userid);
		this.driver.findElement(password).sendKeys(userPwd);
		this.driver.findElement(loginBtn).click();

	}

	public void loginToAcqura() throws InterruptedException {
		Logging.log("calling launchApplication: ");
		this.driver.get("http://192.168.2.65/Talent-UI-Layer/src/app/index.html#/Login");
		this.driver.findElement(By.id("userName")).sendKeys("tester@logica.com");
		this.driver.findElement(By.id("password")).sendKeys("spire@123");
		this.driver.findElement(JobsPage.logInBtn).click();
		// clicElement(driver, JobsPage.logInBtn);
		this.driver.manage().window().maximize();
	}

	public void login(String userId, String pwd) {
		Logging.log("calling login method with params");
		enterText(driver, userName, userId);
		enterText(driver, password, pwd);
		clicElement(driver, loginBtn);
		waitForElementToBeVisible(driver, spireLOGO);

	}

	public void federationLogin(String userId, String pwd) {
		Logging.log("federationLogin page !!!");
		Logging.log("USERNAME >>" + userId);
		Logging.log("PWD >>" + pwd);
		System.out.println("USERNAME >>" + userId);
		System.out.println("PWD >>" + pwd);
		driver.manage().window().maximize();
		if(!isElementVisiable(driver, feduserName)){
			waitForElementToBeVisible(driver, loginWithCredentials);
			clicElement(driver, loginWithCredentials);
		}
		enterText(driver, feduserName, userId);
		enterText(driver, fedpassword, pwd);
		clicElement(driver, fedloginBtn);
		waitForElementToBeVisible(driver, spireLOGO);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebDriver launchApplication(String URL) throws InterruptedException {
		Logging.log("calling launchApplication: " + URL);
		driver.get(URL);
		driver.manage().window().maximize();
		Thread.sleep(1000);
		return driver;

	}

}

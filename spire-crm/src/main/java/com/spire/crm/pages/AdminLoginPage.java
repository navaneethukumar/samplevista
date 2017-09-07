package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.helper.SpireProperties;
import com.spire.base.helper.WebPageHelper;
import com.spire.crawl.pages.BasePage;

public class AdminLoginPage extends BasePage {

	public AdminLoginPage(WebDriver driver, boolean openurl) {

		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress()
					+ SpireProperties.loadEndPointProperties().getProperty("CRM_ADMIN_UI");
			this.driver.get(ui_host);
		}
	}

	public void login() {

		String userid = "batchUser@dell.com";
		String userPwd = "spire@123";
		Logging.log("Logging in");
		try {
			WebPageHelper.waitForElementToBeVisible(driver, userName);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, userName);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}
		this.driver.findElement(userName).clear();
		this.driver.findElement(userName).sendKeys(userid);
		this.driver.findElement(password).clear();
		this.driver.findElement(password).sendKeys(userPwd);
		
		
		this.driver.findElement(loginBtn).click();

	}

	public By userName = By.id("username");
	public By password = By.id("password");
	public By loginBtn = By.id("kc-login");
	public By spireLOGO = By.xpath("//a/img");

}

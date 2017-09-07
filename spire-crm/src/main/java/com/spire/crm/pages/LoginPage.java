package com.spire.crm.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.html.HTMLElement;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.helper.SpireProperties;
import com.spire.crawl.pages.BasePage;

public class LoginPage extends BasePage {

	static final Logger logger = Logging.getLogger(HTMLElement.class);

	public LoginPage(WebDriver driver, boolean openurl) {

		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getThreadContext().getUIHostAddress();					
			this.driver.get(ui_host);
			logger.info("URL >>" + ui_host);
			Logging.info("URL >>" + ui_host);

		}
	}

	public LoginPage(WebDriver driver, String openurl) {
		super(driver);
		this.driver.get(openurl);
		sleep(2000);
	}
	
	public By userName = By.id("username");
	public By password = By.id("password");
	public By loginBtn = By.id("kc-login");
	public By spireLOGO = By.xpath("//a/img");
	
	public By feduserName = By.id("username");
	public By fedpassword = By.id("password");
	public By fedloginBtn = By.id("kc-login");
	
	public By loginWithCredentials=By.xpath("//a/span[contains(text(),'Login with Credentials')]");

}

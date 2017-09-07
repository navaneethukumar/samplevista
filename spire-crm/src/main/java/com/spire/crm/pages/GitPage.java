package com.spire.crm.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.html.HTMLElement;

import com.spire.base.controller.Logging;
import com.spire.crawl.pages.BasePage;

public class GitPage extends BasePage {

	static final Logger logger = Logging.getLogger(HTMLElement.class);
	
	public By userName = By.id("user_login");
	public By password = By.id("user_password");
	public By loginBtn = By.name("commit");
	public By branchName=By.id("branch_name");
	public By createFrom=By.id("ref");
	public By createBranch=By.name("button");
	public By selectBranchDropdown=By.xpath("//span[contains(text(),'Select branch')]");
	public By protect=By.xpath("//input[@value='Protect']");
	
	public void selectBranchName(String branchName){
		By branchNameSelect=By.xpath("//ul[@role='listbox']//div[contains(text(),'"+branchName+"')]");
		clicElement(driver, branchNameSelect);
	}
	
	public GitPage(WebDriver driver, boolean openurl,String URL) {
		super(driver);
		if (!openurl) {
			this.driver.get(URL);
			this.driver.manage().window().maximize();
			logger.info("URL >>" + URL);
			Logging.info("URL >>" + URL);
		}
	}
	
	public void loginGit(String username,String Password){
		driver.findElement(userName).sendKeys(username);
		driver.findElement(password).sendKeys(Password);
		clicElement(driver, loginBtn);
	}
  
}

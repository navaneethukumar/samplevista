package com.spire.acqura.search.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class UploadCandidatesPage extends BasePage {

	public UploadCandidatesPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_host);
		}
	}
	
	public UploadCandidatesPage(WebDriver driver, String openurl) {

		super(driver);
		this.driver.get(openurl);
		
	}
	
	public By header = By.xpath(".//div[@id='uploadProf1']/section[1]/span");
	
	public String returnHeaderText(){
		return this.driver.findElement(header).getText();
	}
}

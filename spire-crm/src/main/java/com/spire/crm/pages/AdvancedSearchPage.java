package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class AdvancedSearchPage extends BasePage {

	public static String url = "/UI/src/app/index.html#/Advanced-Search";

	public AdvancedSearchPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext()
					.getUIHostAddress() + url;
			this.driver.get(ui_url);
		}

	}

	public By advancedSearch = By
			.xpath(".//*[@id='crmPipelinePanelHeader']/div");
	public By skillsTextBox = By.id("skill");
	public By locationTextBox = By.id("location");
	public By roleTextBox = By.id("role");
	public By companyTextBox = By.id("company");
	public By yearsFromTextBox = By
			.xpath(".//*[@id='ngViewDiv']//div[1]/div/div[1]/select");
	public By monthsFromTextBox = By
			.xpath(".//*[@id='ngViewDiv']//div[1]/div/div[2]/select");
	public By yearsToTextBox = By
			.xpath(".//*[@id='ngViewDiv']//div[2]/div/div[1]/select");
	public By monthsToTextBox = By
			.xpath(".//*[@id='ngViewDiv']//div[2]/div/div[2]/select");
	public By qualificationTextBox = By.id("qualification");
	public By instituteTextBox = By.id("institute");
	public By stageType = By.xpath(".//*[@id='ngViewDiv']//div[8]/div/select");
	public By fromTextBox = By
			.xpath(".//*[@id='ngViewDiv']//div[9]/div[1]/div/div[2]/input");
	public By toTextBox = By
			.xpath(".//*[@id='ngViewDiv']//div[2]/div/div[2]/input");
	public By engScoreFrm=By.xpath(".//*[@id='ngViewDiv']//div[9]/div[1]//div[2]/input");
	public By engScoreTo =By.xpath(".//*[@id='ngViewDiv']//div[9]/div[2]//div[2]/input");
	
	public By searchBtn = By
			.xpath(".//*[@id='ngViewDiv']//div[10]/div/button[1]");
	public By restetBtn = By
			.xpath(".//*[@id='ngViewDiv']//div[10]/div/button[2]");

}


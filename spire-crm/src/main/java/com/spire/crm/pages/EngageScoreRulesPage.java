package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class EngageScoreRulesPage extends BasePage {

	public static String url = "/UI/src/app/index.html#/Engage/Engage-Score-Rules";

	public EngageScoreRulesPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext()
					.getUIHostAddress() + url;
			this.driver.get(ui_url);
		}

	}

	public By searchTxtBox = By.id("engRuleSearch");
	public By searchTBtn = By
			.xpath(".//*[@id='rulesPanelHeader']/div[2]/div/span");
public By newRule=By.xpath("//table//tr//td[1]");
}

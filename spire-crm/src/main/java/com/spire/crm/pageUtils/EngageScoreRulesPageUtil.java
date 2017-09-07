package com.spire.crm.pageUtils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.EngageScoreRulesPage;

public class EngageScoreRulesPageUtil extends EngageScoreRulesPage {

	public EngageScoreRulesPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
	}

	/**
	 * Go to Engage_EngmtScoreRules[click EngageTag, select EngmtScoreRules]
	 * 
	 * @throws Exception
	 */
	public void engage_EngmtScoreRules() throws Exception {

		Logging.log("Navigating to Engage_EngmtScoreRules");
		WebPageHelper.waitForElementToBeVisible(driver, searchTxtBox);
		Assert.assertTrue(
				WebPageHelper.isElementVisiable(driver, searchTxtBox),
				"Search text box is not displayed");

		WebPageHelper.waitForElementToBeVisible(driver, searchTBtn);

		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, searchTBtn),
				"Search button is not displayed");
	}

}
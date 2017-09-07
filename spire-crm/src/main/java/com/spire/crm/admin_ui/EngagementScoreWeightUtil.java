package com.spire.crm.admin_ui;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.Admin_UiPage;

public class EngagementScoreWeightUtil extends Admin_UiPage {

	public EngagementScoreWeightUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);

	}

	public void verifyEngagementWeightFields() {
		driver.manage().window().maximize();

		waitForElementToBeVisible(driver, engagementScoreWeightTab);
		Assert.assertTrue(isElementPreset(engagementScoreWeightTab), "engagementScoreWeightTab is missing!!");
		WebPageHelper.clicElement(driver, engagementScoreWeightTab);
		waitForElementToBeVisible(driver, engagementScoreWeightBox);
		Assert.assertTrue(isElementPreset(engagementScoreWeightBox), "engagementScoreWeightBox is missing!!");
		waitForElementToBeVisible(driver, engagementScoreWeightText);
		Assert.assertTrue(isElementPreset(engagementScoreWeightText), "engagementScoreWeightText is missing!!");
		waitForElementToBeVisible(driver, editBtn);
		Assert.assertTrue(isElementPreset(editBtn), "editBtn is missing!!");
		waitForElementToBeVisible(driver, tenantId);
		Assert.assertTrue(isElementPreset(tenantId), "tenantId is missing!!");
		waitForElementToBeVisible(driver, compatibilityTest);
		Assert.assertTrue(isElementPreset(compatibilityTest), "compatibility is missing!!");
		waitForElementToBeVisible(driver, connectFactorsText);
		Assert.assertTrue(isElementPreset(connectFactorsText), "connectFactrs is missing!!");
		waitForElementToBeVisible(driver, awarenessText);
		Assert.assertTrue(isElementPreset(awarenessText), "awareness is missing!!");
		waitForElementToBeVisible(driver, personalCommText);
		Assert.assertTrue(isElementPreset(personalCommText), "personalCommText is missing!!");
		waitForElementToBeVisible(driver, interestLevelText);
		Assert.assertTrue(isElementPreset(interestLevelText), "interestLevelText is missing!!");
		waitForElementToBeVisible(driver, broadcastCommText);
		Assert.assertTrue(isElementPreset(broadcastCommText), "interestLevel is missing!!");

	}

	public void updateWeight(EngagementWeight weight) {
		driver.manage().window().maximize();
		WebPageHelper.clicElement(driver, engagementScoreWeightTab);
		WebPageHelper.clicElement(driver, editBtn);
		enterText(driver, compatibility, weight.getCompatibility());
		enterText(driver, connectFactrs, weight.getConnectFactrs());
		enterText(driver, awareness, weight.getAwareness());
		WebPageHelper.waitForElementToBeVisible(driver, personalComm);
		enterText(driver, personalComm, weight.getPersonalComm());
		WebPageHelper.waitForElementToBeVisible(driver, table);
		WebPageHelper.clicElement(driver, table);
	

		Actions actionObject = new Actions(driver);
		actionObject.sendKeys(Keys.ARROW_DOWN);

		WebPageHelper.waitForElementToBeVisible(driver, broadcastComm);
		enterText(driver, broadcastComm, weight.getBroadcastComm());
		WebPageHelper.clicElement(driver, engagementWeightSaveBtn);
		
	
		
	}


}

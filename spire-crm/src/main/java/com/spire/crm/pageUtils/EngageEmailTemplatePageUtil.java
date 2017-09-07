package com.spire.crm.pageUtils;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.EngageEmailTemplatePage;

public class EngageEmailTemplatePageUtil extends EngageEmailTemplatePage {

	public EngageEmailTemplatePageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
	}

	/**
	 * 
	 * @param search
	 *            tempName
	 */
	public void searchingEmailTemp(String tempName) {
		/*
		 * email.searchTemp(tempName); email.clickSearchBtn();
		 */

		WebPageHelper.waitForElementToBeVisible(driver, searchTextField);

		Assert.assertEquals(driver.findElement(searchTextField).getText().toString(), tempName,
				"Location name is not updated to :" + tempName);
	}

	/**
	 * Create new email template.
	 * 
	 * @throws Exception
	 */
	public void engage_EmailTemplates() {

		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, listOfEmailTemp), "listOfEmailTemp not displayed");
		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, listOfEditTemBtn), "listOfEditTemBtn not displayed");
		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, listOfDeleteTemBtn),
				"listOfDeleteTemBtn not displayed");
		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, newBtn), "New buttonis not displayed");
		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, searchTextField), "searchTextField not displayed");

		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, searchBtn), "searchBtn not displayed");

		Assert.assertTrue(WebPageHelper.isElementVisiable(driver, modifiedOnText), "modifiedOnText not displayed");

	}

	/**
	 * creating template
	 * 
	 * @param nameofTheTemplate
	 * @param subject
	 * @return 
	 */
	public String createNewUpdateEmailTemplate(String nameofTheTemplate, String subject) {
		clicElement(driver, newBtn);
		String template = nameofTheTemplate+UUID.randomUUID().toString().substring(0, 5);
		enterText(driver, templateName, template);
		enterText(driver, templateSubject, subject);
		clicElement(driver, tempalteSaveBtn);
		
		verifyTextAndWaitTillItDisappers(driver, templateSuccessMsg);
//		isTextPresent(driver, templateSuccessMsg);
//		waitForTextToBeDisappeared(driver, templateSuccessMsg);
		
		
		clicElement(driver, templateEditBtn);
		String updatedTemplate = template+"_Update";
		enterText(driver, templateName, updatedTemplate);
		clicElement(driver, tempalteSaveBtn);
		
//		isTextPresent(driver, templateSuccessMsg);
//		waitForTextToBeDisappeared(driver, templateSuccessMsg);
		
		verifyTextAndWaitTillItDisappers(driver, templateSuccessMsg);
		
		waitForElementToBeClickable(driver, templateCloseBtn);
		clicElement(driver, templateCloseBtn);
		engage_EmailTemplates();
		return updatedTemplate;

	}
	/**
	 * delete template
	 * @param templateName
	 */
	public void deleteEmailTemplate(String templateName) {
		By deleteTemplate = By
				.xpath("//strong[text()='" + templateName + "']/../../preceding-sibling::td//span[@title='Delete']");
		clicElement(driver, deleteTemplate);
		clicElement(driver, deleteTemplatePopup);
		
		verifyTextAndWaitTillItDisappers(driver, emailTempalteDeletedSuccessMsg);
		
//		isTextPresent(driver, emailTempalteDeletedSuccessMsg);
//		waitForTextToBeDisappeared(driver, emailTempalteDeletedSuccessMsg);
		engage_EmailTemplates();
	}
}

package com.spire.crm.email;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.common.ProfileHelper;
import com.spire.crm.pageUtils.EngageCandidateDeatilsPageUtils;
import com.spire.crm.pages.EngageEmailTemplatePage;

import spire.talent.entity.profileservice.beans.CandidateBean;

public class EmailsUtil extends EngageEmailTemplatePage {

	public EmailsUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);

	}

	public void VerifyElementsPresent() {
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		Assertion.assertTrue(isElementPreset(searchTextField), "searchTextField is missing");
		Assertion.assertTrue(isElementPreset(searchBtn), "searchBtn is missing");
		Assertion.assertTrue(isElementPreset(newBtn), "newBtn is missing");
		Assertion.assertTrue(isElementPreset(tableHeader), "tableHeader is missing");
	}
	
	public String CreateNewTemplate(EmailBean email) {
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		clicElement(driver, newBtn);
		String TemplateName = "spireTemplate" + System.currentTimeMillis();
		enterText(driver, emailTemplateName, TemplateName);
		enterText(driver, emailSub, "Testing");
		clicElement(driver, tempSave);
		String expected = getElementText(driver, createdPopup);
		String actual = "Email Template Saved Successfully";

		Assert.assertEquals(actual, expected, "Email template is created successfuly");

		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		enterText(driver, searchTextField, TemplateName);
		clicElement(driver, searchBtn);
		String newlyCreatedTemplate = getElementText(driver, firstTemplate);

		Assert.assertEquals(newlyCreatedTemplate, TemplateName, "Email template is created successfuly");

		return TemplateName;

	}

	public String CreateFeedbackTemplate() throws InterruptedException {
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		clicElement(driver, newBtn);
		String TemplateName = "Feedback" + System.currentTimeMillis();
		enterText(driver, emailTemplateName, TemplateName);
		enterText(driver, emailSub, "TestingFeedback");
		
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(tempSave)).build().perform();
		WebElement element = driver.findElement(emailScrollBar);
		builder.moveToElement(element).clickAndHold().moveByOffset(0, 286).release().perform();
		
		clicElement(driver, feedback);
		clicElement(driver, firstQuestion);
		String feedbackQuestion = getElementText(driver, firstQuestion);
		clicElement(driver, addFeedback);
		
		builder.moveToElement(driver.findElement(tempSave)).build().perform();
		
		builder.moveToElement(element).clickAndHold().moveByOffset(286, 0).release().perform();
		Thread.sleep(1000);
		clicElement(driver, tempSave);
		String actual = getElementText(driver, createdPopup);
		String expected = "Email Template Saved Successfully";

		Assert.assertEquals(actual, expected, "Email template is created successfuly");

		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		enterText(driver, searchTextField, TemplateName);
		clicElement(driver, searchBtn);
		String newlyCreatedTemplate = getElementText(driver, firstTemplate);

		Assert.assertEquals(newlyCreatedTemplate, TemplateName, "Email template is created successfuly");

		return feedbackQuestion;
	}

	public String CreateRichTextTemplateTemplate(EmailBean email) {
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		clicElement(driver, newBtn);
		String TemplateName = "spireTemplate" + System.currentTimeMillis();
		enterText(driver, emailTemplateName, TemplateName);
		enterText(driver, emailSub, "testing");
		WebElement element = driver.findElement(body);
		String filePath = System.getProperty("user.dir")
				+ "/src/main/resources/properties/richTextForEmailTemplate.txt";
		element.sendKeys(filePath);
		clicElement(driver, tempSave);
		String expected = getElementText(driver, createdPopup);
		String actual = "Email Template Saved Successfully";

		Assert.assertEquals(actual, expected, "Email template is created successfuly");

		return expected;
	}

	public String EditTemplate(EmailBean email) {

		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		clicElement(driver, newBtn);
		String TemplateName = "spireautomationlabel" + System.currentTimeMillis();
		enterText(driver, emailTemplateName, TemplateName);
		enterText(driver, emailSub, "testing");
		clicElement(driver, tempSave);
		String expected = getElementText(driver, createdPopup);
		String actual = "Email Template Saved Successfully";

		Assert.assertEquals(actual, expected, "Email template is created successfuly");

		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		driver.navigate().refresh();
enterText(driver, searchTextField, TemplateName);
		clicElement(driver, searchBtn);
		clicElement(driver, listOfEditTemBtn);
		String afterEdit = "EditedTemplate"+ System.currentTimeMillis();
		enterText(driver, emailTemplateName, afterEdit);
		clicElement(driver, tempSave);
		String expected1 = getElementText(driver, editedSuccessfullyPopup);
		String actual1 = "Email Template Saved Successfully";

		Assert.assertEquals(actual1, expected1, "Email template is created successfuly");
		Assert.assertNotEquals(TemplateName, afterEdit, "Email template is created successfuly");

		return afterEdit;
	}

	public void SendEmail(EmailBean email) throws InterruptedException {
		
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("test@spire2grow.com");
		String candidateID = ProfileHelper.createProfile(candidateBean);
		
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID);
		WebPageHelper.waitForSeconds(driver, 5);
		
		Thread.sleep(10000);
		clicElement(driver, emailIconInDetailsPage);
		clicElement(driver, templatesDropdown);
		WebPageHelper.waitForElementToBeVisible(driver, templatesDropdown);
		WebElement templateList = driver.findElement(templatesDropdown);
		Select select = new Select(templateList);
		select.selectByIndex(2);
		waitForElementToBeVisible(driver, sendEmail);
		clicElement(driver, sendEmail);
		String popUp = getElementText(driver, sendEmailPopup);
		String expected1 = "Email sent successfully";
		Assert.assertEquals(popUp, expected1, "Email sent successfully");
		Thread.sleep(10000);
		clicElement(driver, activityTab);
		Thread.sleep(10000);
		clicElement(driver, refreshIcon);
		Thread.sleep(10000);
		//clicElement(driver, emailLink);
		Assert.assertNotNull(emailLink, "email activity is not logged");
	}

	public void SendEmailWithFeedback(EmailBean email) {

	}

	public void DeleteTemplate(EmailBean email) throws InterruptedException {
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		clicElement(driver, newBtn);
		String TemplateName = "spireTemplate" + System.currentTimeMillis();
		enterText(driver, emailTemplateName, TemplateName);
		enterText(driver, emailSub, "testing");
		clicElement(driver, tempSave);
		String expected = getElementText(driver, createdPopup);
		String actual = "Email Template Deleted Successfully";

		Assert.assertNotEquals(actual, expected, "Email template is created successfuly");

		driver.navigate().refresh();
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		waitForElementToBeVisible(driver, searchTextField);
		enterText(driver, searchTextField, TemplateName);
		clicElement(driver, searchBtn);
		waitForElementToBeVisible(driver, firstDeleteBtn);
		clicElement(driver, firstDeleteBtn);
		/*
		 * Thread.sleep(2000); driver.switchTo().frame("dialog_4");
		 * Thread.sleep(2000); driver.switchTo().defaultContent();
		 * Thread.sleep(2000); clicElement(driver, deleteConfirmation);
		 */
		String currentWindow = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String windowHandle = it.next().toString();
			driver.switchTo().window(windowHandle);
			Thread.sleep(2000);
			clicElement(driver, deleteConfirmation);

		}
		driver.switchTo().window(currentWindow);
		String expected1 = getElementText(driver, deletedPopup);
		String actual1 = "Email Template Deleted Successfully";
		Assert.assertEquals(actual1, expected1, "Email template is not deleted successfuly");
	}

	public void ValidateDynamicFieldOfEmail(EmailBean email) {
		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		clicElement(driver, newBtn);
		String TemplateName = "spireTemplate" + System.currentTimeMillis();
		enterText(driver, emailTemplateName, TemplateName);
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(driver.findElement(title))
				.moveToElement(driver.findElement(templateeBody)).release(driver.findElement(templateeBody)).build();
		dragAndDrop.perform();
		clicElement(driver, tempSave);
		String expected = getElementText(driver, createdPopup);
		String actual = "Email Template Saved Successfully";

		Assert.assertEquals(actual, expected, "Email template is not deleted successfuly");

		clicElement(driver, engageTab);
		clicElement(driver, engage_EmailTemplateTab);
		enterText(driver, searchTextField, TemplateName);
		clicElement(driver, searchBtn);
		String newlyCreatedTemplate = getElementText(driver, firstTemplate);
		Assert.assertEquals(newlyCreatedTemplate, TemplateName, "Email template is not deleted successfuly");

	}

	public void sentEmailNverifyES(EmailBean email) throws InterruptedException {
	
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("test@spire2grow.com");
		String candidateID = ProfileHelper.createProfile(candidateBean);
		
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID);
		WebPageHelper.waitForSeconds(driver, 5);
		
		String oldES=getElementText(driver, engagementScore);
		Thread.sleep(10000);
		clicElement(driver, emailIconInDetailsPage);
		clicElement(driver, templatesDropdown);
		WebPageHelper.waitForElementToBeVisible(driver, templatesDropdown);
		WebElement templateList = driver.findElement(templatesDropdown);
		Select select = new Select(templateList);
		select.selectByIndex(2);
		waitForElementToBeVisible(driver, sendEmail);
		clicElement(driver, sendEmail);
		String popUp = getElementText(driver, sendEmailPopup);
		String expected1 = "Email sent successfully";
		Assert.assertEquals(popUp, expected1, "Email sent successfully");
		Thread.sleep(10000);
		clicElement(driver, activityTab);
		Thread.sleep(10000);
		clicElement(driver, refreshIcon);
		Thread.sleep(10000);
		String emailActivity=getElementText(driver, emailLink);
		Assert.assertEquals(emailActivity, "email", "Email sent successfully");
		Thread.sleep(10000);
		driver.navigate().refresh();
		Thread.sleep(10000);
		
		String newES=getElementText(driver, engagementScore);
		Assert.assertNotEquals(oldES, newES, "Engagement score increased after sending email");
		
	}
	
	public void scheduleEmail(EmailBean email) throws InterruptedException
	{
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("test@spire2grow.com");
		String candidateID = ProfileHelper.createProfile(candidateBean);
		
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID);
		WebPageHelper.waitForSeconds(driver, 5);
		Thread.sleep(10000);
		clicElement(driver, emailIconInDetailsPage);
		clicElement(driver, templatesDropdown);
		WebPageHelper.waitForElementToBeVisible(driver, templatesDropdown);
		WebElement templateList = driver.findElement(templatesDropdown);
		Select select = new Select(templateList);
		select.selectByIndex(2);
		clicElement(driver, calnder);
		
		String currentWindow = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String windowHandle = it.next().toString();
			driver.switchTo().window(windowHandle);
			Thread.sleep(2000);
			clicElement(driver, date);
		
		}
		
		//clicElement(driver, date);
		clicElement(driver, sendEmail);
		String popUp = getElementText(driver, scheduleEmailPopup);
		String expected1 = "Email scheduled";
		Assert.assertEquals(popUp, expected1, "Email not scheduled successfully");
		/*Thread.sleep(10000);
		clicElement(driver, emailIconInDetailsPage);
		clicElement(driver, scheduledList);*/
	}
}

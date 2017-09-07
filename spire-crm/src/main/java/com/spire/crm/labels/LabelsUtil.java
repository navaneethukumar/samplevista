package com.spire.crm.labels;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.common.ProfileHelper;
import com.spire.crm.pages.TagCloudPage;

public class LabelsUtil extends TagCloudPage {

	public LabelsUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
		// TODO Auto-generated constructor stub
	}

	public void validateElementPresent() throws InterruptedException{
		Thread.sleep(2000);
		//waitForElementToBeVisible(driver, viewAllLink);
		clicElement(driver, viewAllLink);
		Assert.assertTrue(isElementPreset(popularTagsHeader), "popularTagsHeader is missing");
		Assert.assertTrue(isElementPreset(tagCloudBtn), "tagCloudBtn is missing");
		//Assert.assertTrue(isElementPreset(previousBtn), "createdOn is missing");
		//Assert.assertTrue(isElementPreset(nextBtn), "tagCloudBtn is missing");
		clicElement(driver, tagCloudBtn);
		Assert.assertTrue(isElementPreset(createdOn), "createdOn is missing");
		Assert.assertTrue(isElementPreset(actions), "actions is missing");
		Assert.assertTrue(isElementPreset(createdBy), "createdBy is missing");
		Assert.assertTrue(isElementPreset(createdOnBtn), "createdOnBtn is missing");
		Assert.assertTrue(isElementPreset(labelName), "labelName is missing");
		Assert.assertTrue(isElementPreset(users), "users is missing");
		Assert.assertTrue(isElementPreset(count), "count is missing");
		Assert.assertTrue(isElementPreset(countBtn), "countBtn is missing");
	}

	/*public void createLabelAndCheckInHomePage() {
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelName = newLabelSanityValidation.createLabelForUi();
		clicElement(driver, viewAllLink);
		clicElement(driver, tagCloudBtn);
		clicElement(driver, createdOnBtn);
		String newlyCreatedLabelDisplyedInUI = getElementText(driver, newlyCreatedLabel);
		Assert.assertEquals(newlyCreatedLabelDisplyedInUI, labelName, "newlycreated label is displayed in UI");

	}*/

	public void attattachLabelInDetailsPage() throws InterruptedException {
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		waitForElementToBeVisible(driver, tagBtn);
		clicElement(driver, tagBtn);
//		waitForElementToBeVisible(driver, enterLabelName);
		String labelName = "spirelabel" + System.currentTimeMillis();
		enterText(driver, enterLabelName, labelName);
		Thread.sleep(10000);
		driver.findElement(enterLabelName).sendKeys(Keys.ENTER);
		waitForElementToBeVisible(driver, saveLabelBtn);
		clicElement(driver, saveLabelBtn);
		waitForElementToBeVisible(driver, labelSavedPopUp);
		String expected1 = getElementText(driver, labelSavedPopUp);
		String actual1 = "Tags added successfully!!";
		System.out.println("expected1" + expected1);
		Assert.assertEquals(actual1, expected1, "failed to attach label!!!!!!!!!");

	}

	public void createLabelAttachInHomePage() {
		clicElement(driver, homePageTagBtn);

	}

	public void bulkAttachLabelInEngagedPage() throws InterruptedException {
		
		clicElement(driver, homePageEngage);
		Thread.sleep(10000);

		clicElement(driver, selectAll);
		clicElement(driver, tagIconInSelectAllEngagedpage);
		waitForElementToBeVisible(driver, enterTagNameSelectAll);
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		enterText(driver, enterTagNameSelectAll, labelName);
		Thread.sleep(10000);
		driver.findElement(enterTagNameSelectAll).sendKeys(Keys.ENTER);
		waitForElementToBeVisible(driver, saveLabelBtn);
		clicElement(driver, saveLabelBtn);
		waitForElementToBeVisible(driver, labelSavedPopUp);
		String expected1 = getElementText(driver, labelSavedPopUp);
		String actual1 = "Tags added successfully!!";
		System.out.println("expected1" + expected1);
		Assert.assertEquals(actual1, expected1, "failed to attach label!!!!!!!!!");

	}

	public void attachLabelInEngagedPage() throws InterruptedException {
		clicElement(driver, homePageEngage);
		clicElement(driver, engagetagIcon);	
		String labelName = "spirelabel" + System.currentTimeMillis();
		enterText(driver, enterLabelNameInEngaedPag, labelName);
		Thread.sleep(2000);
		driver.findElement(enterLabelNameInEngaedPag).sendKeys(Keys.ENTER);
		waitForElementToBeVisible(driver, saveLabel);
		clicElement(driver, saveLabel);
		waitForElementToBeVisible(driver, labelSavedPopUp);
		String actual1 = getElementText(driver, labelSavedPopUp);
		String expected1 = "Tags added successfully!!";
		System.out.println("expected1" + expected1);
		Assert.assertEquals(actual1, expected1, "failed to attach label!!!!!!!!!");

	}

	public void attachLabelInTagCouldPage() throws InterruptedException  {
		//Thread.sleep(2000);
		waitForElementToBeVisible(driver, viewAllLink);
		clicElement(driver, viewAllLink);
		clicElement(driver, tagCloudBtn);
		clicElement(driver, newlyCreatedLabel);
		String ActualPageName=driver.getTitle();
		String ecpectedPageName="Engage-Candidates";
		Assert.assertEquals(ActualPageName, ecpectedPageName, "failed to attach label!!!!!!!!!");
	}

	public void attachLabelInSearchedPage() throws InterruptedException {
		clicElement(driver, searchBtn);
		clicElement(driver, freeTextBox);
		clicElement(driver, enterText);
		enterText(driver, enterText, "java");
		clicElement(driver, clickSearchBtn);
		Thread.sleep(10000);
		clicElement(driver, engagetagIcon);	
		String labelName = "spirelabel" + System.currentTimeMillis();
		enterText(driver, enterLabelNameInEngaedPag, labelName);
		Thread.sleep(2000);
		driver.findElement(enterLabelNameInEngaedPag).sendKeys(Keys.ENTER);
		waitForElementToBeVisible(driver, saveLabel);
		clicElement(driver, saveLabel);
		waitForElementToBeVisible(driver, labelSavedPopUp);
		String expected1 = getElementText(driver, labelSavedPopUp);
		String actual1 = "Tags added successfully!!";
		System.out.println("expected1" + expected1);
		Assert.assertEquals(actual1, expected1, "failed to attach label!!!!!!!!!");

	}
	public void clickTagInHomepage()
	{
		clicElement(driver, firstTagInHomepage);
		String pageTitle=driver.getTitle();
		String actual1 = "Engage-Candidates";
		Assert.assertEquals(actual1, pageTitle, "Engage-Candidates page is not displayed!!");
		
	}
}

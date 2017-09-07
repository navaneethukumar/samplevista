package com.spire.crm.pageUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.common.ProfileHelper;
import com.spire.crm.pages.EngageCampaignPage;

import spire.talent.entity.profileservice.beans.CandidateBean;

public class EngageCampaignPageUtil extends EngageCampaignPage {

	public EngageCampaignPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);

	}

	/**
	 * Go to Engage_Campaigns[click EngageTag, select Campaigns]
	 * 
	 * @throws Exception
	 */
	public void validateCampaignsData() {

		Logging.log("Navigating to Engage_Campaigns");

		Assert.assertTrue(isElementPreset(createNewCampBtn),
				"createNewCampBtn is showing in campaigns page");

		Assert.assertTrue(isElementPreset(searchTextfield),
				"searchTextfield is showing in campaigns page");

		Assert.assertTrue(isElementPreset(searchTextfieldBtn),
				"searchTextfieldBtn is showing in campaigns page");

		// Assert.assertTrue(isElementPreset(campaignNameList),
		// "campaignNameList is showing in campaigns page");

	}

	/**
	 * Go to Engage_Campaigns[click EngageTag, select Campaigns]
	 * 
	 * @throws Exception
	 */
	public void verifyCampaignsListElements() {

		Logging.log("Navigating to Engage_Campaigns");

		Assert.assertTrue(isElementPreset(editIcon),
				"edit campaign is not showing in campaigns list page <<>> Element is "
						+ editIcon);

		Assert.assertTrue(isElementPreset(deleteIcon),
				"delete campaign is not showing in campaigns list page <<>> Element is "
						+ deleteIcon);

		Assert.assertTrue(isElementPreset(cloneIcon),
				"searchTextfieldBtn is showing in campaigns page <<>> Element is "
						+ cloneIcon);

		Assert.assertTrue(isElementPreset(campaignNameList),
				"campaignNameList is showing in campaigns page <<>> Element is "
						+ campaignNameList);

		Assert.assertTrue(isElementPreset(numberOfCandidatesCount),
				"campaignNameList is showing in campaigns page <<>> Element is "
						+ numberOfCandidatesCount);

	}

	/**
	 * creating campaign
	 * 
	 * @param campaignName
	 * @param campaignDes
	 */
	public void createCampaign_sendEmail(String campaignName,
			String campaignDes, String sendEmailName, String emailTemplate) {
		clicElement(driver, createNewCampBtn);
		enterText(driver, campaignNameTextField, campaignName);
		enterText(driver, descriptionTextBox, campaignDes);
		clicElement(driver, campaignPublishedIcon);
		clicElement(driver, campaignSavePopupBtn);
		dragAndDropBy(driver, sendEmailDrag, 164, 164);

		// dragAndDrop(driver, sendEmailDrag, sendEmailDrop);
		enterText(driver, sendEmailNameFld, sendEmailName);
		selectdropdownBytext(driver, selectEmailTemplate, emailTemplate);
		clicElement(driver, sendEmailAddBtn);
		dragAndDrop(driver, startNodeStartingPoint, sendEmailStartingPoint);
		clicElement(driver, campaignSaveBtn);
	}

	public int[] getElementLocation() {
		clicElement(driver, createNewCampBtn);
		int x = driver.findElement(By.xpath("//span[@id='campModifoedvalue']"))
				.getLocation().getX();
		int y = driver.findElement(By.xpath("//span[@id='campModifoedvalue']"))
				.getLocation().getX();
		int[] locations = new int[2];
		locations[0] = x;
		locations[0] = y;
		return locations;
	}

	public void cloneCampaign(String newCampName) {
		Logging.log("Clone a campaign if records found");

		if (isElementPreset(cloneIcon)) {
			clicElement(driver, cloneIcon);
			enterText(driver, cloneCampaignName, newCampName);
			clicElement(driver, cloneSavebtn);
			String messageAfterClone = getElementText(driver, savedmsg);
			Logging.log("Clone campaign status message " + messageAfterClone);
			Assert.assertEquals("Campaign saved successfully!",
					messageAfterClone,
					"found discrepancy in clone campaign message");
		} else {

			Logging.log("No records found to clone the campaign");
		}

	}

	public void viewCampaign() {

		Logging.log("view a campaign if records found");

		if (isElementPreset(campaignNameList)) {
			clicElement(driver, campaignNameList);

			validateCampaignViewPage();

		} else {

			Logging.log("No records found to v the campaign");
		}
	}

	private void validateCampaignViewPage() {

		Logging.log("Navigating to Engage_Campaigns");

		Assert.assertTrue(isElementPreset(campaginFullviewBtn),
				"full view  campaign button is not displayed in view page <<>> Element is "
						+ campaginFullviewBtn);

		Assert.assertTrue(isElementPreset(editCampaignAfterView),
				"eidt campaign button is not displayed in view page  <<>> Element is "
						+ editCampaignAfterView);

		Assert.assertTrue(isElementPreset(campaignPublishedIcon),
				"publish campaign button is not displayed in view page <<>> Element is "
						+ campaignPublishedIcon);

		Assert.assertTrue(
				isElementPreset(numberOfCandidatesCountText),
				"number Of Candidates Coun tText campaign button is not displayed in view page  <<>> Element is "
						+ numberOfCandidatesCountText);

	}

	public void verifyMsgInHomePage() throws InterruptedException {
		clicElement(driver, homeTab);
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("");

		String candidateID1 = ProfileHelper.createProfile(candidateBean);

		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		
		
		clicElement(driver, activityStreamTab);
		
		waitForElementToBeVisible(driver, createActivity);
		clicElement(driver, createActivity);
		clicElement(driver, videoCall);
		clicElement(driver, saveActivity);
		Thread.sleep(10000);
		clicElement(driver, homeTab);
		// waitForElementToBeVisible(driver, campIcon);
		clicElement(driver, campIcon);

		String expected = getElementText(driver, hmPgErrMsg);
		String actual = "Please note that this candidate does not have Email Id";
		Assert.assertEquals(actual, expected, "Proper Error msg is displayed");
	}

	public void VerifyMsgInSearchedResultPage() throws Exception {
	
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("");
		candidateBean.setCandidateSkillMapBean(ProfileHelper.addSkill("camperrmsg"));
		candidateBean.setFirstName("Pradeep");
		String candidateID = ProfileHelper.createProfile(candidateBean);
			Thread.sleep(10000);
		clicElement(driver, seachBar);
		enterText(driver, seachField, "camperrmsg");
		Thread.sleep(10000);
		clicElement(driver, dropDown);
		clicElement(driver, searchBtn);
		Thread.sleep(10000);
		clicElement(driver, selectAll);
		Thread.sleep(10000);
		clicElement(driver, searchCamp);

		String expected = getElementText(driver, hmPgErrMsg);
		String actual = "Please note that some of the candidates do not have Email Id";
		Assert.assertEquals(actual, expected, "Proper Error msg is displayed");
		
	}

	public void VerifyMsgInEngagedPage() throws InterruptedException {
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("");

		String candidateID1 = ProfileHelper.createProfile(candidateBean);

		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
			
		clicElement(driver, changeStage);
	clicElement(driver, engagedStage);
	Thread.sleep(10000);
	clicElement(driver, homeTab);
	clicElement(driver, homeEngaged);
	Thread.sleep(10000);
	clicElement(driver, engagedAll);
	clicElement(driver, enagedCamp);
	String expected = getElementText(driver, hmPgErrMsg);
	String actual = "Please note that some of the candidates do not have Email Id";
	Assert.assertEquals(actual, expected, "Proper Error msg is displayed");
	}

	public void VerifyMsgInProfilePage() throws InterruptedException {
		clicElement(driver, homeTab);
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setPrimaryEmailId("");

		String candidateID1 = ProfileHelper.createProfile(candidateBean);

		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		
		waitForElementToBeVisible(driver, profileCamp);
		clicElement(driver, profileCamp);
		String expected = getElementText(driver, hmPgErrMsg);
		String actual = "Please note that this candidate does not have Email Id";
		Assert.assertEquals(actual, expected, "Proper Error msg is displayed");
	}
}

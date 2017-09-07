package com.spire.crm.pageUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.common.ProfileHelper;
import com.spire.crm.pages.EngageCandidatesDetailsPage;

public class EngageCandidateDeatilsPageUtils extends EngageCandidatesDetailsPage {

	public EngageCandidateDeatilsPageUtils(WebDriver driver, Boolean openurl, String CandidateId) {
		super(driver, openurl, CandidateId);
	}

	/*String candidateID1 = ProfileHelper.createProfile();*/
	
	public void validateStateChange(String state){
		this.driver.navigate().refresh();
		Assert.assertTrue(isElementPreset(By.xpath("//span[@title='Rejected (Reason not mentioned)']")));
	}

	public void validateAllElementPresent() throws InterruptedException {
		clicElement(driver, homeTab);
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		//waitForElementToBeVisible(driver, skill);
		// clicElement(driver, skill);
		// Assert.assertTrue(isElementPreset(skill), "skill is missing");
		Assert.assertTrue(isElementPreset(imageIcon), "imageIcon is missing");
		Assert.assertTrue(isElementPreset(emailIcon), "emailIcon is missing");
		Assert.assertTrue(isElementPreset(phoneIcon), "phoneIcon is missing");

		Assert.assertTrue(isElementPreset(shareProfileUrl), "shareProfileUrl is missing");
		// Assert.assertTrue(isElementPreset(addCamp), "addCamp is missing");
		Assert.assertTrue(isElementPreset(editLabels), "editLabels is missing");
		Assert.assertTrue(isElementPreset(changeStage), "changeStage is missing");
		Assert.assertTrue(isElementPreset(engagementScoreIcon), "engagementScoreIcon is missing");
		Assert.assertTrue(isElementPreset(remindMeLater), "remindMeLater is missing");
		Assert.assertTrue(isElementPreset(goBackLink), "goBackLink is missing");
		Assert.assertTrue(isElementPreset(downLoadCV), "downLoadCV is missing");
		Assert.assertTrue(isElementPreset(attachments), "attachments is missing");
		clicElement(driver, attachments);
		Assert.assertTrue(isElementPreset(attachmentsRefresh), "attachmentsRefresh is missing");
		Assert.assertTrue(isElementPreset(attachmentNewBtn), "attachmentNewBtn is missing");
	}

	public void validatemessages(String msg) {
		WebPageHelper.waitForElementToBeVisible(driver, StageSavePopup);
		String popupmsg = WebPageHelper.getElementText(driver, StageSavePopup);
		Assert.assertEquals(popupmsg, "Saved Successfully", "pop Up message is " + popupmsg);

	}

	public void attachAttachment() throws InterruptedException {
		clicElement(driver, homeTab);
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		clicElement(driver, attachments);
		clicElement(driver, attachmentNewBtn);
		clicElement(driver, browse);
		clicElement(driver, browse);
		String filePath = ("D://JobPost.txt");
		WebElement element = driver.findElement(browse);
		element.sendKeys(filePath);
		clicElement(driver, isResume);
		clicElement(driver, attachmentSave);
		String popup = getElementText(driver, attachmentSavePopup);
		Assertion.assertEquals(popup, "Attachment uploaded successfully.", "File uploaded successfully");
		
		clicElement(driver, homeTab);
		Thread.sleep(10000);
		driver.navigate().refresh();
		Thread.sleep(10000);
		String fileAct = getElementText(driver, fileActivityInHomepage);
		Assertion.assertEquals(fileAct, "file", "File attached activity is got created in homepgae");
		
		
		
	}
public void validateDownloadCV()
{
	}
	public void createRemindMeLater() throws InterruptedException {
		clicElement(driver, homeTab);
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		clicElement(driver, remindMeLater);
		enterText(driver, enterReminder, "Remind this candidate later");
		// clicElement(driver, saveReminder);

		String currentWindow = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String windowHandle = it.next().toString();
			driver.switchTo().window(windowHandle);
			Thread.sleep(2000);
			clicElement(driver, saveReminder);
		}
		driver.switchTo().window(currentWindow);
		String actual = getElementText(driver, reminderSavePopup);
		String expected = "Saved Successfully";
		Assert.assertEquals(actual, expected, "Reminder not created successfully......!!!!");

	}

	public void validateAlreadyViewed() throws InterruptedException {
		clicElement(driver, homeTab);
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);

		Thread.sleep(10000);
		Assert.assertFalse(isElementPreset(alreadyViewed), "alreadyViewed is  missing");
		driver.navigate().refresh();
		Assert.assertTrue(isElementPreset(alreadyViewed), "alreadyViewed is missing");

	}

	public void validateActivity(String activityType) throws InterruptedException {

		clicElement(driver, activityStreamTab);

		String beforeactivtycount = getActivityCount(activityType);
		if (activityType != "SendEmail") {
			selectdropdownBytext(driver, selectActivity, activityType);
			clicElement(driver, rating5);
			clicElement(driver, saveActivity);
		}
		Thread.sleep(2000);
		driver.navigate().refresh();
		clicElement(driver, activityStreamTab);
		String afteractivtycount = getActivityCount(activityType);
		Assert.assertNotEquals(beforeactivtycount, afteractivtycount, " activty not logged for " + activityType);
		validateActivitiesLog(activityType);

	}

	public void validateEnagementScore(String oldscore) {
		String newScore = "";
		for (int i = 0; i < 30; i++) {
			driver.navigate().refresh();
			newScore = getElementText(driver, engagementScore);
			if (oldscore.equals(newScore)) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i == 29) {
					Assert.assertTrue(false, "Mautic Score is not increased after 5 mins ");
				}

			} else {

				Assert.assertTrue(true, "Mautic Score is updated");
				i = 30;
			}

		}

	}

	public String getActivityCount(String activityType) {
		String count = null;
		try {

			WebPageHelper.waitForElementToBeVisible(driver, activitycntlist);

			List<WebElement> activitycountlist = driver.findElements(activitycntlist);

			switch (activityType) {

			case "Video call":
				count = activitycountlist.get(2).getText();
				break;

			case "Instance Message":
				count = activitycountlist.get(3).getText();
				break;
			case "In-person Meeting":
				count = "activitycountlist.get(3).getText()";
				break;

			case "Voice call made":
				count = activitycountlist.get(2).getText();
				break;

			case "Voice call received":
				count = activitycountlist.get(2).getText();
				break;
			case "SendEmail":
				count = activitycountlist.get(2).getText();
				break;

			}

			return count;

		} catch (Exception e) {

			switch (activityType) {

			case "Video call":
				count = "Calls ( 0 )";
				break;

			case "Instance Message":
				count = "SMS ( 0 )";
				break;
			case "In-person Meeting":
				count = "activitycountlist.get(3).getText()";
				break;

			case "Voice call made":
				count = "Calls ( 0 )";
				break;

			case "Voice call received":
				count = "Calls ( 0 )";
				break;
			case "SendEmail":
				count = "Email ( 0 )";
				break;

			}

			return count;
		}

	}

	public void validateActivitiesLog(String activityType) {

		switch (activityType) {

		case "Video call":
			searchActivityLog("call");
			break;

		case "Instance Message":
			searchActivityLog("SMS");
			break;
		case "In-person Meeting":
			searchActivityLog("call");
			break;

		case "Voice call made":
			searchActivityLog("call");
			break;

		case "Voice call received":
			searchActivityLog("call");
			break;
		case "SendEmail":
			searchActivityLog("email");
			break;

		}

	}

	/**
	 * 
	 * @param stageName
	 * 
	 *            Candidate stage will be changed to input stage
	 * 
	 */

	public void searchActivityLog(String searchKey) {

		WebPageHelper.enterText(driver, activitySearchBox, searchKey);
		WebPageHelper.clicElement(driver, activitySearchbtn);
		String logText = WebPageHelper.getElementText(driver, activitylog1);
		Assert.assertTrue(logText.contains(searchKey), "Search Log is not loggged");
	}

	/**
	 * 
	 * @param stageName
	 * 
	 *            Candidate stage will be changed to input stage
	 * @throws InterruptedException
	 * 
	 */

	public void changeStageTo(String stageName) throws InterruptedException {
		clicElement(driver, homeTab);
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);

		clicElement(driver, changeStageIcon);
		Thread.sleep(10000);
		switch (stageName) {
		case "Applicant":
			WebPageHelper.waitForElementToBeVisible(this.driver, applicantStage);
			clicElement(driver, applicantStage);

			break;

		case "Engaged":
			WebPageHelper.waitForElementToBeVisible(this.driver, engagedStage);
			clicElement(driver, engagedStage);
			break;
		case "Hold":
			WebPageHelper.waitForElementToBeVisible(this.driver, holdStage);
			clicElement(driver, holdStage);
			break;
		case "Lead":
			WebPageHelper.waitForElementToBeVisible(this.driver, leadStage);
			clicElement(driver, leadStage);
			break;
		case "Rejected":
			WebPageHelper.waitForElementToBeVisible(this.driver, rejectedStage);
			clicElement(driver, rejectedStage);
			break;
		default:

			Logging.log("stage name is  the stage to :" + stageName);

		}

		WebPageHelper.clicElement(driver, saveStageBtn);
		String savedmsg = WebPageHelper.getElementText(driver, StageSavePopup);
		Assert.assertEquals(savedmsg, "Saved Successfully", " failed saving and message displayed is : " + savedmsg);

	}

	public void sendEmail() {
		WebPageHelper.clicElement(driver, sendEmail);
		WebPageHelper.waitForElementToBeVisible(driver, setectTemaplateDropdown);
		WebElement templateList = driver.findElement(setectTemaplateDropdown);
		Select select = new Select(templateList);
		select.selectByIndex(2);
		WebPageHelper.enterText(driver, sujectField, "Hi Candidate");
		WebPageHelper.waitForElementToBeVisible(driver, bodyField);
		WebPageHelper.enterText(driver, bodyField, "Hi Candidate");
		WebPageHelper.waitForElementToBeVisible(driver, clickSendMail);
		WebPageHelper.clicElement(driver, clickSendMail);

	}

	public String getCandidateName() {
		WebPageHelper.waitForElementToBeVisible(this.driver, firstNameTextField);
		String candidateName = WebPageHelper.getElementText(driver, firstNameTextField);
		return candidateName;
	}

	public void crmStage() {
		WebPageHelper.waitForElementToBeVisible(this.driver, changeStage);
		WebPageHelper.clicElement(driver, changeStage);
		WebPageHelper.waitForElementToBeVisible(this.driver, leadStageCheckBox);
		WebPageHelper.clicElement(driver, leadStageCheckBox);
		WebPageHelper.waitForElementToBeVisible(this.driver, clickStageSaveBtn);
		WebPageHelper.clicElement(driver, clickStageSaveBtn);
	}

	public void validateStreamElementsPresent() throws InterruptedException {
		clicElement(driver, homeTab);
		
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		Assert.assertTrue(isElementPreset(activityStreamTab), "createActivity is missing");
		WebPageHelper.clicElement(driver, activityStreamTab);
		Thread.sleep(10000);
		Assert.assertTrue(isElementPreset(createActivity), "createActivity is missing");
		Assert.assertTrue(isElementPreset(activitySearchBox), "SearchTextfield is missing");
		Assert.assertTrue(isElementPreset(activitySearchbtn), "activitySearchbtn is missing");
		Assert.assertTrue(isElementPreset(sortBy), "sortBy is missing");
		Assert.assertTrue(isElementPreset(timePeriod), "timePeriod is missing");
		Assert.assertTrue(isElementPreset(selectCheckBox), "selectCheckBox is missing");
		Assert.assertTrue(isElementPreset(selectCampa), "smsChechBox is missing");
		Assert.assertTrue(isElementPreset(allActivitiesCheckBoxes), "smsChechBox is missing");
		Assert.assertTrue(isElementPreset(refreshIcon), "smsChechBox is missing");
	}
	public void createActivity() throws InterruptedException
	{
		clicElement(driver, homeTab);
		String candidateID1 = ProfileHelper.createProfile();
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		clicElement(driver, activityStreamTab);
		clicElement(driver, createActivity);
		clicElement(driver, videoCall);
		clicElement(driver, saveActivity);
			String expected=getElementText(driver, activityCreatedSuccessfullyPopup);
		String actual="Activity created successfully";
		Assert.assertEquals(actual, expected, "Activity created successfully msg is displayed");
		clicElement(driver, homeTab);
		
	
		Thread.sleep(10000);
		driver.navigate().refresh();
		Thread.sleep(10000);
		String videocal = getElementText(driver, callActivityInHomepage);
		Assertion.assertEquals(videocal, "call", "videoCall activity is got created in homepgae");
		
	}

}

package com.spire.crm.activitytream;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.CandidateProfilePage;
import com.spire.crm.pages.HomePage;

/**
 * @author Santosh C
 *
 */
public class ValidateActivityStream extends HomePage {

	public ValidateActivityStream(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
	}

	/**
	 * Validate all ActivityStream elements are present[icons,dropdowns]
	 */
	public void validateActivityStream(String candidateId) {
		waitForElementToBeVisible(driver, activityExpand);
		Assert.assertTrue(isElementPreset(activityExpand), "ActivityExpand icon is missing !!");
		driver.findElement(activityExpand).click();
		Assert.assertTrue(isElementPreset(activitySearchTextBox), "ActivitySearchTextbox is missing !!");
		Assert.assertTrue(isElementPreset(sortByDropdown), "SortByDropdown is missing !!");
		Assert.assertTrue(isElementPreset(dateRangeDropdown), "DateRangeDropdown is missing !!");
		Assert.assertTrue(isElementPreset(selectAllActivity), "SelectAllActivityTypes checkbox is missing !!");
		Assert.assertTrue(isElementPreset(activityTypeCheckboxes), "activityTypeCheckboxes are missing !!");

		driver.get(driver.getCurrentUrl() + "Profile/" + candidateId);
		WebPageHelper.waitForSeconds(driver, 5);

		CandidateProfilePage candidateProfilePage = new CandidateProfilePage(this.driver, false, null);
		candidateProfilePage.clickActivityStreamTab();
		Assert.assertTrue(isElementPreset(candidateProfilePage.selectActivityDropdown),
				"Create Activity dropdown is missing !!");
		Assert.assertTrue(isElementPreset(candidateProfilePage.activitySearchTextBox),
				"Activity Search textbox is missing !!");
		Assert.assertTrue(isElementPreset(candidateProfilePage.activitySearchIcon), "activitySearchIcon is missing !!");
		Assert.assertTrue(isElementPreset(candidateProfilePage.sortByDropdown), "sortByDropdown is missing !!");
		Assert.assertTrue(isElementPreset(candidateProfilePage.timePeriodDropdown), "timePeriodDropdown is missing !!");
		Assert.assertTrue(isElementPreset(candidateProfilePage.selectAllActivities),
				"selectAllActivities checkbox is missing !!");
		Assert.assertTrue(isElementPreset(candidateProfilePage.selectActivityCheckBox),
				"selectActivityCheckBox is missing !!");
	}

	/**
	 * Validate created activity in Candidate details page and HomePage
	 */
	public void validateCreatedActivity(String activityType) {

		CandidateProfilePage candidateDetailsPage = new CandidateProfilePage(this.driver, false, null);
		waitForElementToBeVisible(driver, candidateDetailsPage.activitylog1);
		String activityLog1 = driver.findElement(candidateDetailsPage.activitylog1).getText();
		validateActivity(activityType, activityLog1);

		driver.navigate().refresh();
		waitForSeconds(driver, 8);
		clicElement(driver, candidateDetailsPage.activityStreamTab);
		waitForElementToBeVisible(driver, candidateDetailsPage.activitylog1);
		String activityLog2 = driver.findElement(candidateDetailsPage.activitylog1).getText();
		validateActivity(activityType, activityLog2);

		driver.findElement(homeTab).click();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForElementToBeVisible(driver, firstCandidateNameLink);
		Assert.assertNotNull(getElementText(driver, firstCandidateNameLink),
				"CandidateName is coming null in HomePage ActivityStream !!");
		waitForElementToBeVisible(driver, listOfActivity);
		String activityLog = driver.findElement(listOfActivity).getText();
		validateActivity(activityType, activityLog);
	}

	/**
	 * Validates particular text in created activity
	 * 
	 * @param activityType
	 * @param textToValidate
	 */
	public void validateActivity(String activityType, String text) {

		CandidateProfilePage candidateDetailsPage = new CandidateProfilePage(this.driver, false, null);
		String activityLog = null;
		try {
			waitForElementToBeVisible(driver, candidateDetailsPage.activitylog1);
			activityLog = driver.findElement(candidateDetailsPage.activitylog1).getText();
		} catch (Exception e) {
			waitForElementToBeVisible(driver, listOfActivity);
			activityLog = driver.findElement(listOfActivity).getText();
		}

		if (activityType.equals("Video call") || activityType.equals("Voice call made")
				|| activityType.equals("Voice call received")) {
			Logging.log("Looking for 'call' ActivityType in Log");
			Assert.assertTrue(activityLog.contains("call"), "Created activity is not showing log !!!");
		}

		if (activityType.equals("Instance Message")) {
			Logging.log("Looking for 'sms' ActivityType in Log");
			Assert.assertTrue(activityLog.contains("SMS"), "Created activity[SMS] is not showing log !!!");
		}
		if (activityType.equals("Notes")) {
			Logging.log("Looking for 'notes' ActivityType in Log");
			Assert.assertTrue(activityLog.contains("notes"), "Created activity[Notes] is not showing log !!!");
		}
	}

	/**
	 * Create activity
	 * 
	 * @param candidateId
	 * @param activityType
	 * @param ratings
	 * @return activityType
	 */
	public String createActivity(String candidateId, String activityType, int ratings) {

		waitForElementToBeVisible(driver, listOfActivity);
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateId);
		WebPageHelper.waitForSeconds(driver, 5);
		waitForElementToBeVisible(driver, activityStreamTab);
		clicElement(driver, activityStreamTab);

		CandidateProfilePage candidateProfilePage = new CandidateProfilePage(this.driver, false, null);
		clicElement(driver, candidateProfilePage.activityStreamTab);
		WebPageHelper.waitForSeconds(driver, 5);
		selectdropdownBytext(driver, candidateProfilePage.selectActivityDropdown, activityType);
		if (ratings != 0) {
			selectRatings(ratings);
		}

		if (activityType.equals("Notes")) {
			driver.findElement(candidateProfilePage.notesTitle).sendKeys("Behaviour");
			driver.findElement(candidateProfilePage.notesDescription).sendKeys("Good");
		}

		clicElement(driver, candidateProfilePage.saveButton);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return activityType;
	}

	/**
	 * Selects all activityType ratings while creating activity
	 * 
	 * @param ratingCount
	 */
	public void selectRatings(int ratingCount) {
		selectBenefitRatings(ratingCount);
		selectAwareNessRatings(ratingCount);
		selectInterestRatings(ratingCount);
		selectFitmentRatings(ratingCount);
	}

	public void selectBenefitRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asBenifitLevel']/i[" + ratingCount + "]")).click();
	}

	public void selectAwareNessRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asCompanyLevel']/i[" + ratingCount + "]")).click();
	}

	public void selectInterestRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asInterestLevel']/i[" + ratingCount + "]")).click();
	}

	public void selectFitmentRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asFitmentLevel']/i[" + ratingCount + "]")).click();
	}

	/**
	 * Filter activities by selecting checkbox[Call/SMS/Notes]
	 * 
	 * @param candidateId
	 * @param activityCheckBox
	 */
	public void filterCandidatePageActivities(String candidateId, String activityCheckBox) {
		waitForElementToBeVisible(driver, listOfActivity);
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateId);
		WebPageHelper.waitForSeconds(driver, 5);

		CandidateProfilePage candidateProfilePage = new CandidateProfilePage(this.driver, false, null);
		waitForElementToBeVisible(driver, candidateProfilePage.activityStreamTab);
		clicElement(driver, candidateProfilePage.activityStreamTab);
		WebPageHelper.waitForSeconds(driver, 5);
		if (activityCheckBox.equals("call")) {
			waitForElementToBeVisible(driver, candidateProfilePage.activityCheckBox);
			candidateProfilePage.selectActivity("calls");
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			validateActivity("Video call", activityCheckBox);
		}
		if (activityCheckBox.equals("SMS")) {
			waitForElementToBeVisible(driver, candidateProfilePage.smsCheckBox);
			candidateProfilePage.selectActivity("sms");
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			validateActivity("Instance Message", activityCheckBox);
		}
		if (activityCheckBox.equals("Notes")) {
			waitForElementToBeVisible(driver, candidateProfilePage.notesCheckBox);
			candidateProfilePage.selectActivity("notes");
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			validateActivity("Notes", activityCheckBox);
		}
	}

	/**
	 * filterHomePageActivities
	 * 
	 * @param activityCheckBox
	 */
	public void filterHomePageActivities(String activityCheckBox) {

		waitForElementToBeVisible(driver, listOfActivity);
		clicElement(driver, activityExpand);

		if (activityCheckBox.equals("call")) {
			selectActivity("calls");
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			validateActivity("Video call", activityCheckBox);
		}
		if (activityCheckBox.equals("SMS")) {
			selectActivity("sms");
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			validateActivity("Instance Message", activityCheckBox);
		}
		if (activityCheckBox.equals("Notes")) {
			selectActivity("notes");
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			validateActivity("Notes", activityCheckBox);
		}
	}

	/**
	 * Search for a text in Activities; validate the text in all displayed
	 * activities
	 * 
	 * @param textToSearch
	 */
	public void searchTextInActivities(String textToSearch) {

		clicElement(driver, activityExpand);
		driver.findElement(activitySearchTextBox).sendKeys("call");
		clicElement(driver, activitySearchIcon);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> log = driver.findElements(listOfActivity);
		for (WebElement webElement : log) {
			Assert.assertTrue(webElement.getText().contains(textToSearch), "Search results is giving wrong results !!");
		}
	}
}

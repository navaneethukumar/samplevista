package com.spire.crm.pageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.HomePage;

public class HomePageUtil extends HomePage {

	public HomePageUtil(WebDriver driver, Boolean openurl) {

		super(driver, openurl);

	}
	
	public HomePageUtil(WebDriver driver, String openurl) {

		super(driver, openurl);

	}

	public void checkRuleName(String ruleName) {

		WebDriver driver = new FirefoxDriver();
		LoginPageUtil obj2 = new LoginPageUtil(driver, true);
		obj2.login();
		WebPageHelper.waitForElementToBeVisible(driver, engageTab);
		WebPageHelper.clicElement(driver, engageTab);
		WebPageHelper.waitForElementToBeVisible(driver, engage_engaementScoreRule);
		WebPageHelper.clicElement(driver, engage_engaementScoreRule);
		ValidateNewRule(ruleName);
	}

	public By searchBox = By.id("engRuleSearch");
	public By searchBtn = By.xpath(".//*[@id='rulesPanelHeader']/div[2]/div/span");
	public By firstRuleName = By.xpath(".//*[@id='DataTables_Table_0']/tbody/tr/td[1]/span");
	public By pipelineCount = By.xpath("//span[@id='HGraphASpan']");

	public void ValidateNewRule(String ruleName) {
		WebPageHelper.waitForElementToBeVisible(driver, searchBox);
		WebPageHelper.enterText(driver, searchBox, ruleName);
		WebPageHelper.waitForElementToBeVisible(driver, searchBtn);
		WebPageHelper.clicElement(driver, searchBtn);
		WebPageHelper.waitForElementToBeVisible(driver, firstRuleName);
		String newlyCreatedRule = WebPageHelper.getElementText(driver, firstRuleName);

		ruleName.equals(newlyCreatedRule);
	}

	/**
	 * Check for elements in HomePage(Search Textfield, NotificationIcon etc)
	 * 
	 * @throws Exception
	 */
	public void validateHomePageElements() throws Exception {

		Thread.sleep(15000);
		waitForElementToBeVisible(driver, searchTextfield);
		Assert.assertTrue(isElementPreset(searchTextfield), "SearchTextfield is missing");
		Assert.assertTrue(isElementPreset(spireLOGO), "SPIRE LOGO IS MISSING");
		Assert.assertTrue(isElementPreset(notificationIcon), "NotificationIcon missing");
		Assert.assertTrue(isElementPreset(crmPipeline), "CRMPipeline_missing");
		Assert.assertTrue(isElementPreset(activityStreamText), "ActivityStream missing");
		// waitForElementToBeVisible(driver, homeTab);
		// Assert.assertTrue(isElementPreset(homeTab), "HomeTab missing");
		Assert.assertTrue(isElementPreset(engageTab), "EngageTab missing");
		Assert.assertTrue(isElementPreset(jobsTab), "JobsTab missing");
		Assert.assertTrue(isElementPreset(reportsTab), "ReportsTab missing");
		Assert.assertTrue(getElements(driver, activityStreamList).size() > 0, "Seems ActivityStream is down !!!!");

	}

	/**
	 * Validate dropdowns are present and are able to select
	 * 
	 * @throws Exception
	 */
	public void validateDropdowns() {

		selectActivityDate("The Past 1 day");
		selectPipeLineDate("The Past 1 day");
		selectActivityDate("The Past 1 week");
		selectPipeLineDate("The Past 1 week");

	}

	/**
	 * Validate pipeline data
	 */
	public void validatePipeLineData() {

		WebPageHelper.waitForElementToBeVisible(driver, applicant);

		Assert.assertTrue(isElementPreset(applicant), "Applicant field not showing in Pipeline");

		WebPageHelper.waitForElementToBeVisible(driver, engage);
		Assert.assertTrue(isElementPreset(engage), "Engage field not showing in Pipeline");

		WebPageHelper.waitForElementToBeVisible(driver, lead);
		Assert.assertTrue(isElementPreset(lead), "Lead field not showing in Pipeline");

		WebPageHelper.waitForElementToBeVisible(driver, graphButton);
		Assert.assertTrue(isElementPreset(graphButton), "GraphButton is missing!!");

		Assert.assertTrue(Integer.parseInt(getStageCount("Lead")) >= 0, "Not showing total Lead count");
		Assert.assertTrue(Integer.parseInt(getStageCount("Applicant")) >= 0, "Not showing total Applicants count");
		Assert.assertTrue(Integer.parseInt(getStageCount("Engaged")) >= 0, "Not showing total Engaged count");

	}

	public HomePageUtil validateEngageList() {

		Logging.log("Navigating to Engage_EngmtScoreRules");

		WebPageHelper.waitForElementToBeVisible(driver, engage);

		this.driver.findElement(engage).click();

		WebPageHelper.waitForElementToBeVisible(driver, engagelist);

		List<WebElement> engageListItems = driver.findElements(engagelist);

		String engageItem = null;

		List<String> engageItems = Arrays.asList("Candidates", "Campaigns", "Email Templates",
				"Engagement Score Rules");

		for (WebElement webElement : engageListItems) {

			engageItem = webElement.getText().toString();
			Assert.assertEquals(true, engageItems.contains(engageItem),
					"found invalid name in engagement list " + engageItem);

		}

		return this;

	}

	/**
	 * Validate popular tags data
	 */
	public void validatePopularTagsData() {

		Assert.assertTrue(getPopularTags().size() > 0, "Tags are not showing in PopularTags field");

		Assert.assertTrue(getTagScores().size() > 0, "TagScores are not showing in PopularTags field");

	}

	public void ValidateActivityData() throws Exception {
		validateActivityLogs();
		validateActivityIcons();
	}

	/**
	 * Validate ActivityLog is displaying
	 */
	public void validateActivityLogs() {

		Assert.assertTrue(getActivityLog1().size() >= 0, "ActivityLog1 not displaying!!");

		Assert.assertTrue(getActivityLog2().size() >= 0, "ActivityLog2 not displaying!!");

	}

	/**
	 * Validate all the icons are present(Mobile,Favourite,Email,Tag)
	 * 
	 * @throws Exception
	 */
	public void validateActivityIcons() {
		Assert.assertTrue(isElementPreset(activityExpand), "EmailIcon is missing");
		clicElement(driver, activityExpand);
		Assert.assertTrue(isElementPreset(refreshIcon), "refreshIcon is missing");
		Assert.assertTrue(isElementPreset(activityTypeCheckboxes), "activityTypeCheckboxes is missing");
		Assert.assertTrue(isElementPreset(callsCheckBox), "callsCheckBox is missing");
		Assert.assertTrue(isElementPreset(smsCheckBox), "smsCheckBox is missing");
		Assert.assertTrue(isElementPreset(notesCheckBox), "notesCheckBox is missing");
		Assert.assertTrue(isElementPreset(selectAllActivities), "selectAllActivities is missing");
		Assert.assertTrue(isElementPreset(sortByDropdown), "sortByDropdown is missing");
		Assert.assertTrue(isElementPreset(dateRangeDropdown), "dateRangeDropdown is missing");
		Assert.assertTrue(isElementPreset(activitySearchTextBox), "activitySearchTextBox is missing");
		Assert.assertTrue(isElementPreset(emailIcon), "emailIcon is missing");
		Assert.assertTrue(isElementPreset(tagIcon), "tagIcon is missing");
		Assert.assertTrue(isElementPreset(campaignIcon), "campaignIcon is missing");
Assert.assertTrue(isElementPreset(candidatteImageIcon), "candidatteImageIcon is missing");
		
	}

	/**
	 * Validate RecentPost field(Role and Applicants)
	 */
	public void validateRecentPostData() {

		Assert.assertTrue(getRecentPostData().size() >= 0, "Not showing RecentPostData");

		Assert.assertTrue(getRecentPostApplicants().size() >= 0, "Not showing Applicants in JOBS field");

	}

	public void validateOpenPositionsData() {

		Assert.assertTrue(getOpenPositions().size() >= 0, "Not showing any openPositions in OpenPositions field");

	}

	public void enterData(String stringToSearch) {

		Logging.log("Entering " + stringToSearch + " into Search textfield");
		this.driver.findElement(searchTextfield).sendKeys(stringToSearch);
	}

	public void clickHome() {
		Logging.log("Clicking Home Tab");
		this.driver.findElement(homeTab).click();
	}

	public void clickJob() {
		Logging.log("Clicking Jobs Tab");
		this.driver.findElement(jobsTab).click();

	}

	public void selectPipeLineDate(String text) {

		Logging.log("Selecting Date");
		try {
			new Select(driver.findElement(By.id("pipelineDateFilter"))).selectByVisibleText(text);
		} catch (Exception e) {
			Assert.fail("Not able to select PipeLine Dropdown");
		}

	}

	public void clickEngage() {
		Logging.log("Clicking Engage");
		this.driver.findElement(engage).click();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void minimizeCrmStages() {
		Logging.log("Minimizing CRM Statges");
		this.driver.findElement(crmStatgesMinimize).click();

	}

	public void selectActivityDate(String text) {

		Logging.log("Selecting Date");
		try {
			WebPageHelper.waitForElementToBeVisible(driver, activityStreamDateFilter);
			Select dropdown = new Select(this.driver.findElement(activityStreamDateFilter));

			dropdown.selectByVisibleText(text);
		} catch (Exception e) {
			Assert.fail("Not able to select Activity date range");
		}

	}

	/**
	 * Gets 1st line of ActivityLog from ActivityStream
	 * 
	 * @return activityLog
	 */
	public List<String> getActivityLog1() {

		List<String> activityLog = new ArrayList<String>();
		Logging.log("Get Activity Log");
		WebPageHelper.waitForElementToBeVisible(driver, activitylog1);
		List<WebElement> logMsg = this.driver.findElements(activitylog1);

		for (WebElement webElement : logMsg) {

			activityLog.add(webElement.getText());

		}

		return activityLog;
	}

	/**
	 * Gets 2nd line of ActivityLog from ActivityStream
	 * 
	 * @return activityLog
	 */
	public List<String> getActivityLog2() {

		List<String> activityLog = new ArrayList<String>();
		Logging.log("Get Activity Log");
		WebPageHelper.waitForElementToBeVisible(driver, activitylog2);
		List<WebElement> logMsg = this.driver.findElements(activitylog2);

		for (WebElement webElement : logMsg) {

			activityLog.add(webElement.getText());

		}

		return activityLog;
	}

	public void favouriteIcon() {
		this.driver.findElement(favouriteIcon);
	}

	public void clickEmail() {
		Logging.log("Clicking Email Icon");
		this.driver.findElement(emailIcon).click();
	}

	public void clickMobile() {
		Logging.log("Clicking Mobile Icon");
		this.driver.findElement(mobileIcon).click();
	}

	public void clickTag() {
		Logging.log("Clicking Tag Icon");
		this.driver.findElement(tagIcon).click();
	}

	/**
	 * Get TagScores from PopularTags
	 * 
	 * @return tagScores
	 */
	public List<String> getTagScores() {

		List<String> tagScores = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(tagScore);

		for (WebElement webElement : tags) {

			tagScores.add(webElement.getText());

		}

		return tagScores;
	}

	/**
	 * Get count Number of Applicant,Lead and Engaged from CRM Pipeline
	 * 
	 * @return totalApplicatnts
	 */
	public String getStageCount(String stageName) {

		String count = "";
		switch (stageName) {

		case "Lead":
			count = WebPageHelper.getElementText(driver, leadCount);
			break;
		case "Applicant":
			count = WebPageHelper.getElementText(driver, leadCount);
			break;
		case "Engaged":
			return WebPageHelper.getElementText(driver, leadCount);

		}
		return count;

	}

	/**
	 * Get Total Number of EngageCandidates from CRM Pipeline
	 * 
	 * @return totalEngageCandidates
	 */
	public int getTotalEngageCandidates() {
		List<WebElement> engage = this.driver.findElements(pipelineCounts);
		int totalEngageCandidates = Integer.parseInt(engage.get(1).getText());
		Logging.log("Total Number of EngageCandidates: " + totalEngageCandidates);
		return totalEngageCandidates;
	}

	/**
	 * Get Total Number of HoldCandidates from CRM Pipeline
	 * 
	 * @return totalHoldCandidates
	 */
	public int getTotalHoldCandidates() {
		List<WebElement> hold = this.driver.findElements(pipelineCounts);
		int totalHoldCandidates = Integer.parseInt(hold.get(2).getText());
		Logging.log("Total Number of HoldCandidates: " + totalHoldCandidates);
		return totalHoldCandidates;
	}

	/**
	 * Get Total Number of LeadCandidates from CRM Pipeline
	 * 
	 * @return totalLeadCandidates
	 */
	public int getTotalLeadCandidates() {
		List<WebElement> lead = this.driver.findElements(pipelineCounts);
		int totalLeadCandidates = Integer.parseInt(lead.get(1).getText());
		Logging.log("Total Number of LeadCandidates: " + totalLeadCandidates);
		return totalLeadCandidates;
	}

	/**
	 * Get RecentPost data
	 * 
	 * @return
	 */
	public List<String> getRecentPostData() {

		List<String> recentPostData = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(this.recentPostData);

		for (WebElement webElement : tags) {

			recentPostData.add(webElement.getText());

		}

		return recentPostData;
	}

	/**
	 * Get RecentPostApplicants(In Jobs field)
	 * 
	 * @return recentPostApplicants
	 */
	public List<String> getRecentPostApplicants() {

		List<String> recentPostApplicants = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(recentPost_Applicants);

		for (WebElement webElement : tags) {

			recentPostApplicants.add(webElement.getText());

		}

		return recentPostApplicants;
	}

	/**
	 * Get OpenPositions(In OpenPositions field)
	 * 
	 * @return openPositions_Roles
	 */
	public List<String> getOpenPositions() {

		List<String> openPositions_Roles = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(openPosition_Roles);

		for (WebElement webElement : tags) {

			openPositions_Roles.add(webElement.getText());

		}

		return openPositions_Roles;
	}

	public void search(String enterSkill) {
		Logging.log("Entering sendEmailToAddress");
		this.driver.findElement(searchTextfield).sendKeys(enterSkill);

	}

	public void clicksearchBtn() {
		Logging.log("Clicking searchBtn");
		this.driver.findElement(searchBtn).click();
	}

	public void clicksearchDropdownBtn() {
		Logging.log("Clicking searchBtn");
		this.driver.findElement(searchDropdownBtn).click();
	}

	public void clickHomeTab() {
		Logging.log("Clicking HomeTab");
		this.driver.findElement(homeTab).click();
	}

	public void leadScore() {
		Logging.log("Getting leadScore");
		this.driver.findElement(leadScore).getText();

	}

	public void engagedScore() {
		Logging.log("Getting engagedScore");
		this.driver.findElement(engagedScore).getText();

	}

	public void applicantScore() {
		Logging.log("Getting applicantScore");
		this.driver.findElement(applicantScore).getText();

	}

	public void clickEngageTab() {
		Logging.log("Clicking EngageTab");
		this.driver.findElement(engageTab).click();

	}

	public void clickJobsTab() {
		Logging.log("Clicking jobsTab");
		this.driver.findElement(jobsTab).click();
	}

	public void clickReports() {
		Logging.log("Clicking Reports Tab");
		this.driver.findElement(reportsTab).click();

	}

	public void clickNotificatIonIcon() {
		Logging.log("Clicking NotificationIcon");
		this.driver.findElement(notificationIcon).click();

	}

	public void clickprofileImage() {
		Logging.log("Clicking ProfileImage");
		this.driver.findElement(profileImage).click();

	}

	public void clickadvancedLink() {
		Logging.log("Clicking advancedLink");
		this.driver.findElement(advancedLink).click();

	}

	public void selectPipeLineDate(int dateIndex) {

		Logging.log("Selecting Date");
		try {
			new Select(driver.findElement(By.id("pipelineDateFilter"))).selectByIndex(dateIndex);
		} catch (Exception e) {
			Assert.fail("Not able to select PipeLine Dropdown");
		}

	}

	public void clickGraphButton() {
		Logging.log("Clicking GraphButton");
		this.driver.findElement(graphButton).click();

	}

	public void clickLead() {
		Logging.log("Clicking lead");
		this.driver.findElement(lead).click();

	}

	public void clickEngaged() {
		Logging.log("Clicking Engaged");
		this.driver.findElement(engaged).click();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clickApplicant() {
		Logging.log("Clicking applicant");
		this.driver.findElement(applicant).click();

	}

	public void clickPopularTags() {
		Logging.log("Clicking popularTags");
		this.driver.findElement(popularTags).click();

	}

	/**
	 * Get PopularTags
	 * 
	 * @return activityLog
	 */
	public List<String> getPopularTags() {

		List<String> popularTaglist = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		WebPageHelper.waitForElementToBeVisible(driver, popularTags);
		List<WebElement> tags = this.driver.findElements(popularTags);

		for (WebElement webElement : tags) {

			popularTaglist.add(webElement.getText());

		}

		return popularTaglist;
	}

	public void clickPopularTagsViewAllLink() {
		Logging.log("Clicking popularTagsViewAllLink");
		this.driver.findElement(popularTagsViewAllLink).click();

	}

	public void activityStreamDateFilter(int dateIndex) {

		Logging.log("Selecting Date");
		try {
			new Select(driver.findElement(By.id("activityStreamDateFilter"))).selectByIndex(dateIndex);
		} catch (Exception e) {
			Assert.fail("Not able to select activityStream Dropdown");
		}

	}

	public void listOfActivity() {
		Logging.log("Clicking listOfActivity");
		this.driver.findElement(listOfActivity).click();

	}

	public void EmailIconlist() {
		Logging.log("Clicking EmailIconlist");
		this.driver.findElement(EmailIconlist).click();

	}

	public void sendEmailToAddress(String toAddress) {
		Logging.log("Entering sendEmailToAddress");
		this.driver.findElement(sendEmailToAddress).sendKeys(toAddress);

	}

	public void emailSub(String subject) {
		Logging.log("Entering emailSub");
		this.driver.findElement(emailSub).sendKeys(subject);

	}

	public void selectemailTem(int templateIndex) {

		Logging.log("Selecting Date");
		try {
			new Select(driver.findElement(By.id("emailTem"))).selectByIndex(templateIndex);
		} catch (Exception e) {
			Assert.fail("Not able to select templateIndex Dropdown");
		}

	}

	public void emailBody(String write_msg) {
		Logging.log("Entering emailBody");
		this.driver.findElement(emailBody).sendKeys(write_msg);

	}

	public void clickSendEmailBtn() {
		Logging.log("Clicking sendEmailBtn");
		this.driver.findElement(sendEmailBtn).click();

	}

	public void clickAttachTagIconlist() {
		Logging.log("Clicking AttachTagIconlist");
		this.driver.findElement(AttachTagIconlist).click();

	}

	public void enterTagText(String enterTag) {
		Logging.log("Entering TagText");
		this.driver.findElement(tagTextField).sendKeys(enterTag);

	}

	public void clicksaveTag() {
		Logging.log("Clicking saveTag");
		this.driver.findElement(saveTag).click();

	}

	public void jobs() {
		Logging.log("Getting jobs");
		this.driver.findElement(jobs).getText();

	}

	public void recentPosts() {
		Logging.log("Getting recentPostsText");
		this.driver.findElement(recentPostsText).getText();

	}

	public void openPositions() {
		Logging.log("Getting jobsText");
		this.driver.findElement(openPositionsText).getText();

	}

	public void clickFirstPageLink() {
		Logging.log("Clicking firstPageLink");
		this.driver.findElement(firstPageLink).click();

	}

	public void clickpreviousPgeLink() {
		Logging.log("Clicking previousPgeLink");
		this.driver.findElement(previousPgeLink).click();

	}

	public void clicknextPageLink() {
		Logging.log("Clicking nextPageLink");
		this.driver.findElement(nextPageLink).click();

	}

	public void clicklastPageLink() {
		Logging.log("Clicking lastPageLink");
		this.driver.findElement(lastPageLink).click();

	}

	/**
	 * Get RecentPost data(Roles)
	 * 
	 * @return
	 */
	public List<String> getRecentPosts_Roles() {

		List<String> recentPostData = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(this.recentPosts_Roles);

		for (WebElement webElement : tags) {

			recentPostData.add(webElement.getText());

		}

		return recentPostData;
	}

	/**
	 * Get RecentPost data(Applicants)
	 * 
	 * @return
	 */
	public List<String> getRecentPostsApplicantCount() {

		List<String> recentPostData = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(this.recentPostsApplicantCount);

		for (WebElement webElement : tags) {

			recentPostData.add(webElement.getText());

		}

		return recentPostData;
	}

	/**
	 * Get RecentPostApplicants(In Jobs field)
	 * 
	 * @return recentPostApplicants
	 */
	public List<String> openPositionApplicants() {

		List<String> applicants = new ArrayList<String>();
		Logging.log("Get Popular Tags");
		List<WebElement> tags = this.driver.findElements(openPositonsApplicantCount);

		for (WebElement webElement : tags) {

			applicants.add(webElement.getText());

		}

		return applicants;
	}

	/**
	 * To get the count of each CRM-Pipeline stage
	 * 
	 * @param stage
	 * @return
	 * @throws InterruptedException
	 */
	public Map<String, String> getCRMPipelineCount(String[] stage) throws InterruptedException {
		clickHomeTab();
		Thread.sleep(1000);
		waitForElementToBeVisible(driver, pipelineCount);
		List<WebElement> listElemnts = driver.findElements(pipelineCount);
		Map<String, String> pipelineCount = new HashMap<String, String>();
		for (int i = 0; i < listElemnts.size(); i++) {
			pipelineCount.put(stage[i], listElemnts.get(i).getText());
			Logging.log(stage[i] + "--->\t" + listElemnts.get(i).getText());
		}
		if (pipelineCount != null && !pipelineCount.isEmpty()) {
			return pipelineCount;
		} else {
			Assert.fail("after adding -UI pipeline count is failing");
			return null;
		}

	}

	/**
	 * Based on the name it will click on the tag link
	 * 
	 * @param tagName
	 * @return
	 */
	public boolean clickOnATag(String tagName) {
		clicElement(driver, homeTab);
		List<WebElement> listElmnts = getElements(driver, tagLinks);
		Logging.log("List of TAG's" + listElmnts.size());
		for (WebElement element : listElmnts) {
			String tag = element.getText();
			if (tagName.equalsIgnoreCase(tag)) {
				Logging.log("TAG link clicked -->" + tag);
				element.click();
				waitForTextToBeDisappeared(driver, LOADING_TEXT);
				return true;
			}
		}
		return false;
	}

	/**
	 * To verify count of profiles loaded correct on TaggedEntity page.
	 * 
	 * @param noOfProfiles
	 * @return
	 */
	public boolean verifyProfileCountOnTaggedEntityPage(int noOfProfiles) {
		List<WebElement> profileElemnts = getElements(driver, profileCountOnTaggedEntity);
		if (profileElemnts.size() == noOfProfiles) {
			Logging.log("No.of profiles got loaded are -->" + noOfProfiles);
			return true;
		} else {
			Logging.log("Profiles loading mismatch");
			return false;
		}
	}

	/**
	 * Click on ViewAll link and check the page is loaded .
	 * 
	 * @return
	 */
	public boolean clickOnViewAllLink() {
		if (!isElementPreset(viewAllLink))
			return true;
		clicElement(driver, viewAllLink);
		waitForTextToBeDisappeared(driver, LOADING_TEXT);
		Assert.assertNotNull(getElements(driver, viewAllCount), "View All page is not loadeed !!!");
		return true;
	}

	/**
	 * Verify Campaigns page loaded
	 */
	public void clickOnCampaigns() {
		clicElement(driver, homeTab);
		clicElement(driver, engage);
		clicElement(driver, campaigns);
		Assert.assertNotNull(getElements(driver, countOfCampaigns).size(), "Count of Campaigns null ");
		Assert.assertTrue(getElements(driver, countOfCampaigns).size() > 0, "Count of Campaigns size 0 ");
	}

	public void clickOnEmailTemplates() {
		clicElement(driver, homeTab);
		clicElement(driver, engage);
		clicElement(driver, emailTemplt);
		Assert.assertNotNull(getElements(driver, countOfEmailTmplt).size(), "Email Templates null ");
		Assert.assertTrue(getElements(driver, countOfEmailTmplt).size() > 0, "Email Templates size 0 ");
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}
	
	public void verifyNotifications() {
		waitForElementToBeVisible(driver, notificationIcon);
		clicElement(driver, notificationIcon);
		Assert.assertTrue(isElementPreset(notificationDetail),
				"Notification details not showing after clicking on Notification icon in HomePage!!");
	}

	public void validateLoginSuccess() {
		Assert.assertEquals(this.driver.getTitle(), "Home", "LoginPage not navigating to HomePage !!");
		Assert.assertTrue(isElementPreset(lead), "CRM Stage Lead not showing in HomePage");
		Assert.assertTrue(isElementPreset(engaged), "CRM Stage Engaged not showing in HomePage");
		Assert.assertTrue(isElementPreset(applicant), "CRM Stage Applicant not showing in HomePage");
		Assert.assertTrue(isElementPreset(rejected), "CRM Stage Rejected not showing in HomePage");
	}
}

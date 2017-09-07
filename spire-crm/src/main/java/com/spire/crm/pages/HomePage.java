package com.spire.crm.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class HomePage extends BasePage {

	public static String url = "/UI/src/app/index.html#/";
	public By spireLOGO = By.xpath("//a/img");
	public By tagLinks = By.xpath("//span[@id='crmPopularTagsBadge']");
	public static String LOADING_TEXT = "Loading Data..";
	public By profileCountOnTaggedEntity = By.xpath("//div[@id='candName']");
	public By viewAllLink = By.xpath("//span[text()='View All']");
	public By viewAllCount = By.xpath("//span[starts-with(@id,'cloud-dynamic_word')]");
	
	
	public HomePage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_host);
		}
	}
	
	public HomePage(WebDriver driver, String openurl) {

		super(driver);
		this.driver.get(openurl);
		
	}
	
	/**********************************************************************
	 * CRM PipeLine Elements
	 * 
	 *********************************************************************/
	public By pipelineCounts = By.id("crmPipelineBadgeDiv");
	public By leadCount = By.xpath(".//*[@id='crmPipelineListItem_0']//*[@id='HGraphASpan']");
	public By engagedCount = By.xpath(".//*[@id='crmPipelineListItem_1']//*[@id='HGraphASpan']");
	public By applicantCount = By.xpath(".//*[@id='crmPipelineListItem_2']//*[@id='HGraphASpan']");
	public By holdCount = By.xpath(".//*[@id='HGraphA_3']//*[@id='HGraphASpan']");
	public By rejectedCount = By.xpath(".//*[@id='HGraphA_4']//*[@id='HGraphASpan']");
	public By paginationInEngagedPage = By.id("candPagination");
	public By nextBtn = By.xpath(".//*[@id='candPagination']/li[6]/a");
	public By lead = By.id("HGraphA_1");
	public By engaged = By.id("HGraphA_4");
	public By applicant = By.id("HGraphA_0");
	public By hold = By.id("HGraphA_2");
	public By rejected = By.id("HGraphA_3");
	public By leadScore = By.xpath(".//*[@id='crmPipelineListItem_0']//*[@id='HGraphASpan']");
	public By engagedScore = By.xpath(".//*[@id='crmPipelineListItem_1']//*[@id='HGraphASpan']");
	public By applicantScore = By.xpath(".//*[@id='ngViewDiv']/ui-view/div/div[3]/div/div[3]/div");
	public By scrollBarInEngagePage = By.xpath(".//*[@id='ngViewDiv']/ui-view/div/div[3]/div/div[3]");
	public By crmPipeline = By.xpath(".//*[@id='crmPipelinePanelHeader']/div");
	public By pipelineDateFilter = By.id("pipelineDateFilter");
	public By graphButton = By.cssSelector(".md-bar");
	public By graphLead = By.id(".//*[@id='raphael-paper-127']/g[4]/path[3]");
	public By graphEngaged = By.xpath(".//*[@id='raphael-paper-127']/g[4]/path[2]");
	public By graphApplicant = By.xpath(".//*[@id='raphael-paper-127']/g[4]/path[1]");
	public By engage_candidate = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li[1]/a");
	public By engage_campaigns = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li[2]/a");
	public By engage_emailTemplates = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li[3]/a");
	public By engage_engaementScoreRule = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li[4]/a");
	public By dateRage = By.id("pipelineDateFilter");
	public By listOfEngageLeadCandidate = By.xpath(".//*[@id='candProLi']/div[2]");
	public By listOfEngageEngagedCandidates = By.xpath(".//*[@id='candProLi']/div[2]");
	public By listOfEngageApplicantCandidates = By.xpath(".//*[@id='candProLi']/div[2]");
	public By listOfHoldCandidates = By.xpath(".//*[@id='candProLi']/div[2]");
	public By listOfRejectedCandidates = By.xpath(".//*[@id='candProLi']/div[2]");
	public By stageIcon = By
			.xpath(".//*[@id='candProLi']/div[2]/div[2]/div[1]//*[@id='candProCandStage crmStage']/span");
	public By leadStageChecked = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[1]/a/span[1]");
	public By engagedStageChecked = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[2]/a/span[1]");
	public By applicantStageChecked = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[3]/a/span[1]");

	public static By openPositionPostButton = By.cssSelector("span.glyphicon.glyphicon-plus-sign.post-job-btn");

	public By holdStageChecked = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[4]/a/span[1]");
	public By rejectedStageChecked = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[5]/a/span[1]");

	public By minimizeBtn = By.xpath(".//*[@id='candFacetedH4']/span");

	/**********************************************************************
	 * Notification elements
	 * 
	 *********************************************************************/
	public By notificationIcon = By.id("notificationIconSpan");
	public By notificationCount = By.xpath(".//*[@id='action-bar-top-div']//div[3]/div[1]/span");
	public By notificationDetail = By.xpath(".//*[@id='notification']/div[1]");

	/**********************************************************************
	 * RecentPosts elements
	 * 
	 *********************************************************************/

	public By recentPosts = By.id("crmRecentPostsHeading");
	public By recentPostData = By.id("crmRecentPostsReqTitle");
	public By recentPost_Applicants = By.id("crmRecentPostsReqCount");
	public By recentPostsText = By.xpath(".//*[@id='crmRecentPostsHeading'][1]");
	public By recentPosts_Roles = By.xpath(".//*[@id='crmRecentPostsReqTitle']/strong");
	public By recentPostsApplicantCount = By.id("crmRecentPostsReqCount");

	/**********************************************************************
	 * openPositions elements
	 * 
	 *********************************************************************/

	public By openPositionsText = By.xpath("//div[text()='Open Positions']");
	public By openPositions = By.id("crmRecentPostsHeading");
	public By popularTagsText = By.xpath(".//*[@id='tagsPanelHeader']/div");
	public By popularTags = By.id("crmPopularTagsBadge");
	public By popularTagScore = By.id("crmPopularTagsInnerBadge");
	public By popularTagsViewAllLink = By.id("detProTagCloud");

	public By openPosition_Roles = By.xpath(".//*[@id='openPositionsDesc']/a[1]/strong");
	public By openPositonsApplicantCount = By.id(".//*[@id='openPositionsDesc']/a[2]");

	/**********************************************************************
	 * activityStream elements
	 * 
	 *********************************************************************/
	public By activityStreamTab = By.xpath(".//a[text()='Activity Stream']");
	public By activityStreamList = By.xpath("//div[@id='crmActivityStream']//li");
	public By activityStream = By.xpath(".//*[@id='activityStreamPanelHeader']/div/div[1]/label[2]");
	public By firstCandidateNameLink = By
			.xpath(".//*[@id='crmActivityStreamListItem_0']//*[@id='HActivityStrong']/span[3]");
	public By allCandidateList = By.xpath(".//*[@id='HActivityStrong']/span[1]");
	public By firstCandidateEmailLink = By.xpath(".//*[@id='HActivityStrong']/span[2]");
	public By crmStatgesMinimize = By.className("collapse-icon glyphicon glyphicon-minus");
	public By popTags = By.id("crmPopularTagsBadge");
	public By activitylog1 = By.xpath(".//*[@id='crmActivityStreamListItem_0']//*[@id='HActivityStrong']");
	public By activitylog2 = By.xpath(".//*[@id='crmActivityStreamListItem_1']//*[@id='HActivityStrong']");
	public By favouriteIcon = By.id("crmActivityActionFav");
	public By emailIcon = By.id("HEmailIconA");
	public By campaignIcon = By.xpath("//span[@data-ng-click='openDropdown();']");
	public By mobileIcon = By.id("crmActivityActionIM");
	public By tagIcon = By.id("tagOverLayImg");
	public By candidatteImageIcon = By.id("crmCandidateImg");
	public By tagScore = By.id("crmPopularTagsInnerBadge");
	public By activityStreamText = By.xpath("//div[@id='crmActivityStream']/li");
	public By viewActivities = By.id("filteredActivityStream");
	public By activityStreamDateFilter = By.xpath(".//*[@id='activityStreamDateFilter']/option[2]");
	public By listOfActivity = By.id("HActivityStrong");
	public By firstPageLink = By.xpath(".//*[@id='hPaginationDiv']/ul/li[1]/a");
	public By previousPgeLink = By.xpath(".//*[@id='hPaginationDiv']/ul/li[2]/a");
	public By nextPageLink = By.xpath(".//*[@id='hPaginationDiv']/ul/li[9]/a");
	public By lastPageLink = By.xpath(".//*[@id='hPaginationDiv']/ul/li[10]/a");
	public By activityLog1 = By.xpath(".//*[@id='crmActivityStreamListItem_0']//*[@id='HActivityStrong']");
	public By activityLog2 = By.xpath(".//*[@id='crmCandidiateActivityDiv_1']//*[@id='HActivityStrong']");
	public By paginationBtn = By.xpath(".//*[@id='hPaginationDiv']/ul");
	public By lastPage = By.xpath(".//*[@id='hPaginationDiv']/ul/li[8]/a");
	public By nextPageBtn = By.xpath(".//*[@id='hPaginationDiv']/ul/li[9]/a");
	public By paginationBtnAllPages = By.xpath(".//*[@id='hPaginationDiv']/ul/li/a");
	public By listOfImage = By.id("crmCandidateImg");
	public By activityExpand = By.id("filteredActivityStream");
	public By activitySearchTextBox = By.id("asSearch");
	public By activitySearchIcon = By.id("asSearchIcon");
	public By sortByDropdown = By.id("sortBy");
	public By dateRangeDropdown = By.id("activityStreamDateFilter");
	public By selectAllActivity = By.id("asBoldTxtLabel");
	public By activityTypeCheckboxes = By.id("asCbLabel");
	public By refreshIcon=By.xpath(".//*[@id='activityStreamPanelHeader']/div[1]/div[1]/div[1]/div[1]/div[2]/div");
	public By callsCheckBox = By.xpath(".//*[@id='asCbList_1']/div");
	public By smsCheckBox = By.xpath(".//*[@id='asCbList_2']/div");
	public By notesCheckBox = By.xpath(".//*[@id='asCbList_3']/div");
	public By selectAllActivities = By.id("asBoldTxtLabel");
	public By activityCheckBox = By.xpath(".//*[@id='asCbLabel']/span");
	public By campIcon = By.id("CampaignIconSpan");

	/**********************************************************************
	 * Other elements
	 * 
	 *********************************************************************/

	public By user = By.xpath(".//*[@id='action-bar-top-div']/div/div/div[3]/div[2]");
	public By engagelist = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li/a");
	public By engage1 = By.xpath("//a[contains(.,'Engage')]");
	public By noRecordsFoundMsg = By.id("crmcandidateProfilesStatus");
	public By engage = By.id("simple-dropdown");
	public By homeTab = By.xpath("//a[contains(text(),'Home')]");
	public By engageTab = By.id("simple-dropdown");
	public By jobsTab = By.xpath("//a[contains(.,'Jobs')]");
	public By reportsTab = By.xpath(".//*[@id='reports-dropdown']");
	public By searchTextfield = By.id("button-template-url");
	public By searchBtn = By.id("HeaderSearchBtn");
	public By searchDropdownBtn = By.id("single-button");
	public By advancedLink = By.xpath(".//*[@id='action-bar-top-div']/div/div/div[2]/div[2]/a");
	public By profileImage = By.id("HeaderProfImg");
	public By profileHeaderSpan = By.id("HeaderProfSpan");
	public By jobsTex = By.xpath(".//*[@id='jobsPanelHeader']/div");
	public By EmailIconlist = By.id("HEmailIconSpn sendEmailDirective");
	public By sendEmailToAddress = By.id("sendEmailTo");
	public By emailSub = By.id("sub ng-binding");
	public By emailBody = By.id("body ng-binding");
	public By emailTem = By.id("SendMailSelect");
	public By sendEmailBtn = By.id("sendEmailSendBtn");
	public By cancelEmailBtn = By.id("sendEmailBtnCancel");
	public By sendMailCancelTab = By.id("SendMailCancel");
	public By AttachTagIconlist = By.id("tagOverLayImg");
	public By tagTextField = By.id("labelsTypeAhead");
	public By saveTag = By.id("dropdownSelCloseButton");
	public By imageIconlist = By.id("crmCandidateImg");
	public By jobs = By.xpath(".//*[@id='jobsPanelHeader']/div/div[1]/label[2]");

	public By ruleList = By.xpath("//table//tr//td[1]");
	public By clickOnEngage = By.xpath("//a[text()='Engage']");
	public By campaigns = By.xpath("//a[text()='Campaigns' and @ng-show='config.mautic==false']");
	public By countOfCampaigns = By.xpath("//ul[@class='rich-list']/child::li");
	public By emailTemplt = By.xpath("//a[text()='Email Templates']");
	public By countOfEmailTmplt = By.xpath("//tbody//child::a");

	public By freeTextSearchIcon = By.xpath(".//*[@id='tagMdSwitch']/div[1]/div[1]");
	public By saveSearchIcon = By.xpath(".//*[@id='action-bar-top-div']/div/div/div[3]/ul/li[4]/span/span[1]");

	/**
	 * Select activityType Checkbox
	 * 
	 * @param activityType[email,calls,
	 *            sms,notes]
	 */
	public void selectActivity(String activityType) {
		List<WebElement> element = driver.findElements(activityCheckBox);

		for (WebElement webElement : element) {
			webElement.click();
		}
		if (activityType.equals("email")) {
			element.get(0).click();
		}
		if (activityType.equals("calls")) {
			element.get(1).click();
		}
		if (activityType.equals("sms")) {
			element.get(2).click();
		}
		if (activityType.equals("notes")) {
			element.get(3).click();
		}
	}

}

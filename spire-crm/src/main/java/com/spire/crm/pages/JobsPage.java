package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class JobsPage extends BasePage {
	public static String url = "/UI/src/app/index.html#/Jobs";

	public JobsPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext().getUIHostAddress() + url;
			this.driver.get(ui_url);
		}

	}

	public By jobsTab = By.xpath(".//a[@ui-sref='jobs']");
	public By homePage = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[1]/a");
	public By analyticsText = By.xpath("//div[@title='Analytics']");
	public By postAJobText = By.id("post-job-header");
	public By jobBoards = By.id("jobPostFetchData");
	public By jobPostsText = By.id("jobPostHeader");
	public By firstRequisitionInOpenposition = By.xpath(".//*[@id='openPositionsDesc_0']/div[1]");
	public By jobTitleInJobsPageOpenPosiion = By.xpath(".//*[@id='openPositionsDesc_0']/div[2]/span[1]/strong");
	public By plusIconInOpenPositions = By.xpath(".//*[@id='openPositionsDesc_0']/div[2]/span[2]");
	public By homePageOpenPositionsJobTitle = By.xpath(".//*[@id='openPositionsDesc_0']/div[2]/span[1]/strong");
	public By homePageRecentJobPOst=By.xpath(".//*[@id='crmRecentPostsReqTitle']/strong");
	public By homepagePlusIconOpenPosition = By.xpath(".//*[@id='openPositionsDesc_0']/div[2]/span[2]");
	public By homePageRequisitionId = By.xpath(".//*[@id='openPositionsDesc_0']/div[1]");
	public By searchedResults = By.xpath(".//*[@id='ngViewDiv']//div[1]/div[2]/div[1]/h4");
	public By fillJobDetailsText=By.xpath(".//*[@id='tagsPanelHeader']/div");
	public By postFromUrl = By.id("postFromURLSpan");
	public By uploadFile = By.id("postInlineSpan");
	public By uploadBtn = By.id("jobPostUpload");
	public By uploadedFilePopup = By
			.xpath(".//*[@id='jobs-column-right-sidebar']/div/div[1]/div[2]/div[2]/div[5]/div[1]");
	public By previewBtn = By.id("jobPostPreview");
	public By edit = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div[2]/div[1]/div/a[2]");
	public By postBtn1 = By.xpath(".//*[@id='ngViewDiv']//div[2]//div[2]/div[6]/div[2]/button[2]");
	public By createNewJobPost = By.id("postVisibleSpan");
	public By openPositionsText = By.id("crmRecentPostsHeading");
	public By allJobs = By.id("jobPostAllJobs");
	public By browseFile = By.id("jobPostsUploaderInput");
	public By postFromUrlTextField = By.id("jobPostUrl");
	public By fetchBtn = By.id("fetchingFromUrlDiv");
	public By preview = By.id("urlDataFetchedInput");
	public By editLinkURL = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div[2]/div[1]/div/a[2]");
	public By dataFetchedPopUp = By.id("urlDataFetchedDiv");
	public By resetBtn = By.xpath(".//*[@id='ngViewDiv']/div/div[3]/div[1]/div[2]/div[1]/form/input[4]");
	public By jobDetailsTab = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/ul/li[1]/a");
	public By titleTextBox = By.xpath("//input[@placeholder='Enter Job Title']");
	public By companyTextBox = By.xpath("//input[@placeholder='Enter Company Name']");
	public By educationTextField = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[1]/form/div[1]/div[3]/div[2]/select");
	public By descriptionTextArea = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[1]/form/div[1]/div[4]/div[2]/textarea");
	public By skillsTextField = By.xpath("//input[@placeholder='Enter a skill and press enter']");
	public By backBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[1]/form/div[2]/button[1]");
	public By nextBtn1 = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[1]/form/div[2]/button[2]");
	public By newBtn2 = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[2]/form/div[2]/button[2]");
	public By newBtn3 = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[3]/form/div[2]/button[2]");
	public By countryTextField = By.xpath(".//*[@id='ngViewDiv']//div[2]//div[2]/form/div[1]/div[1]/div[2]/select");
	public By stateTextField = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[2]/form/div[1]/div[2]/div[2]/select");
	public By cityTextField = By.xpath("//input[@placeholder='Enter City']");
	public By addressLane = By.xpath(".//*[@id='ngViewDiv']//div[2]//div[2]/form/div[1]/div[4]/div[2]/input");
	public By postalCodeTextField = By.xpath("//input[@placeholder='Enter Postal Code']");
	public By nameTextField = By.xpath(".//*[@id='ngViewDiv']//div[2]//div[3]/form/div[1]/div[1]/div[2]/input");
	public By emailTextField = By.xpath("//input[@type='email']");
	public By previewJobBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div/div[4]/form/div[3]/button");
	public By resetBtnInPreview = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div[2]/div[6]/div/button[1]");
	public By postBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div[2]/div[6]/div[2]/button[2]");
	public By editLink = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div[2]/div[1]/div/a");
	public By editBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div/div[2]/div[1]/div");
	public By recentlypostedJobTitleInCenterOfTheJobsPage = By
			.xpath(".//*[@id='jobListsUL']/ul/li[1]/div/div[1]/strong");
	public By listOfOpenPositions = By.xpath("//div[@class='col-xs-3 disp-id ng-binding']");
	public By recentPostsLabel = By.xpath("//div[@class='header-text ng-binding'][contains(text(),'Recent Posts')]");
	public By openOpsitionsLabel= By.xpath("//div[@class='header-text ng-binding'][contains(text(),'Open Positions')]");
	public By jdTitleLabel = By.xpath("//div/span[@ng-bind='navpaneTabs.jobDetails.values.title']");
	public By jdSkillLabel = By.xpath("//span[@ng-repeat='skill in navpaneTabs.jobDetails.values.skills']/span[@ng-bind='skill | catitalCase']");
	public By jdCityLabel = By.xpath("//div/span[@ng-bind='navpaneTabs.jobLocation.values.city']");
	public By seeDetailsLink = By.id("jobPostSeeDetailsA");
	public By jobDetailsDownArrow = By.cssSelector("span.menu-btn.glyphicon.glyphicon-menu-down");
	public By jobDetailsSeeAllDetailsPage = By.xpath("//div[@class='col-xs-12 req-details ng-binding in']");
	public By jobTitleInSeeAllDetailsPage = By.cssSelector("div.col-xs-12.req-title h5 strong.ng-binding");
	public By paginationIcon = By.xpath("//li[@class='pagination-page ng-scope']");
	public By secondPageInPagination = By.xpath("//li[@class='pagination-page ng-scope'][1]/a");
	public By listOFJobInJPPage = By.xpath("//li[@ng-repeat='jobPost in jobPostsList.list']");
	public By listOFJPInHomePage = By.xpath("//span[@id='crmRecentPostsReqTitle']/strong");
	public By listOFCreatedTimeInHomePage = By.xpath("//span[@class='time-ago col-xs-6 emphasis-text']");
	public By listOfAppliedCountInHomePage = By.xpath("//span[@id='crmRecentPostsReqCount']");
	public By jPErrorMsg = By.cssSelector("div.ng-scope > div.posting-job.ng-binding");
	
	
	//------------Acquara UI Elements---------------------------
	
		public static By logInBtn = By.id("loginSubmit");
		public By reqManagementMenu = By.xpath("//span[contains(text(),'Requisition Management ')]");
		public By uploadOption = By.xpath("//li[@class='sub-menu ng-scope']/a[contains(text(),'Upload')]");
		public By skilBtn = By.xpath("//div/button[contains(text(),'Skip')]");
		public By jobTitleField = By.id("jobTitle");
		public By essentialSkillLink = By.id("essentialSkills");
	    public By essentialSkillField =	By.id("essentialSkill");
		public By firstOptionFromEssentlSkill= By.xpath("//li[@role='option']/a/strong[1]");
		public By locationLink= By.id("locationsAdd");
		public By locationField= By.xpath("//div/input[@id='locations']");
		public By firstOptionFromEssentlLocation = By.xpath("//ul[@class='dropdown-menu ng-isolate-scope'][1]/li[@ng-repeat='match in matches track by $index']/a/strong");
		public By statusDropDown = By.id("status");
		public By saveAndPreviewBtn = By.xpath("//div/input[@value='Save & Preview']");
		public By reqIdValueACQ = By.id("finalPreviewPreviewReqId");
		public By skillValACQ = By.id("finalPreviewEssentialSkill_0");
		public By statusValACQ = By.id("finalPreviewStatus");
		public By reqSubmitBtnACQ = By.id("previewSubmitBtn");
		public By reqCreatedMessage = By.id("reqCreationMsg");

}

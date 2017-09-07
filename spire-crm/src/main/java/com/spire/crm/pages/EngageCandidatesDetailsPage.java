package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class EngageCandidatesDetailsPage extends BasePage {

	public static String url = "/UI/src/app/index.html#/Profile/";

	public EngageCandidatesDetailsPage(WebDriver driver, Boolean openurl, String CandidateId) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext().getUIHostAddress() + url + CandidateId;
			this.driver.get(ui_url);
		}

	}
public By candidateName=By.id("candFirstLastNameId");
	public By editName = By.xpath("//span[@id='detProNameEdit']");
	public By editEmailId=By.xpath("//input[@name='detProEmail']");
	public By editLocation = By.xpath(".//*[@id='detailProfileColDiv']/div/div/div[2]/div[2]/div[2]/span[2]");
	public By commonSave = By.id("dropdownSelCloseButton");
	public By commonCancel = By.id("dropdownSelCancelButton");
	public By shareProfileCopyUrlText = By
			.xpath(".//*[@id='detailProfileColDiv']/div/div/div[2]/div[3]/div/div[1]/div/div[2]/div[1]/b");
	public By sendEmail = By.xpath(".//*[@id='detailProfileColDiv']//div[1]//div[3]/ul/li[1]/span/span");
	public By setectTemaplateDropdown = By.id("SendMailSelect");
	public By selectDropdownCustm=By.xpath(".//*[@id='SendMailSelect']/optgroup[1]/option");
	public By clickSendMail = By.xpath("//button[@id='emailSendBtn']");
	public By engagementScore = By.xpath(".//*[@id='detailProfileColDiv']//span/b");
	public By editSkills = By.id("skillsEditIcon");
	public By firstNameTextField = By
			.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[2]/div[2]/div[1]/strong");
	public By lastNameTextField = By.xpath(".//*[@id='form.name']/div/div[2]/input");
	public By saveName = By.id("detProNameSave");
	public By canceSaveName = By.xpath(".//*[@id='form.name']/div/span/span[2]");
	public By locationTextField = By.id("locTypeAhead");
	public By saveLocName = By.xpath(".//*[@id='form.loc']/div/span/span[1]");
	public By cancelLocName = By.xpath(".//*[@id='form.loc']/div/span/span[2]");
	public By mailToAddress = By.xpath("html/body/div[6]/div/div/div[2]/form/div/div[1]");
	public By mailSub = By.xpath("html/body/div[6]/div/div/div[2]/form/div/span/div[1]");
	public By sujectField=By.id("sendEmailSubject");
	public By bodyField=By.xpath("html/body");
	public By mailSendBtn = By.xpath("html/body/div[6]/div/div/div[3]/button[1]");
	public By mailCancelBtn = By.xpath("html/body/div[6]/div/div/div[3]/button[2]");
	public By selectTemplate = By.xpath("html/body/div[6]/div/div/div[2]/div/div/select");
	public By skillsTextField = By.id("skillsTypeAhead");
	public By skillsEditSave = By.id("skillsEditSave");
	public By skillsEditCancel = By.id("skillsEditCancel");
	public By labelsTextField = By.id("labelsTypeAhead");
	public By selectActivity = By.name("selectActivity");
	public By changeStage = By.xpath(".//*[@id='detProStage crmStage']/span");
	public By leadStageCheckBox = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[1]/a/span[1]");
	public By clickStageSaveBtn = By.id("crmStageOverlaySave");
	public By savedmsg1 = By.cssSelector("span.ng-binding");
	public By savestage = By.xpath("(//button[@id='dropdownSelCloseButton'])[4]");
	public By applicant = By.xpath("//a[contains(.,'Applicant')]");
	public By engage1 = By.xpath("//a[contains(.,'Engage')]");
	public By hold = By.xpath("//a[contains(.,'Hold')]");
	public By lead = By.xpath("//a[contains(.,'Lead')]");

	public By rating5 = By.xpath(".//*[@id='detailProfileColDiv']//div[1]/span/i[5]");

	public By saveActivity = By.id("asCreateActivitySave");
	
	public By activitycntlist = By.xpath(".//*[@id='detailProfileColDiv']//li/div/label");
public By activityCreatedSuccessfullyPopup=By.xpath("html/body/md-toast");
	public By editNameBtn = By.id("detProNameEdit");
	public By enterFirstName = By.id("detProfs");
	public By enterLastName = By.id("detProls");
	public By nameSaveBtn = By.id("detProNameSave");
	public By nameCancelBtn = By.id("detProNameCancel");

	public By editLocationBtn = By.id("detProLocToggle");
	public By enterLocation = By.id("locTypeAhead");
	public By editLocationSaveBtn = By.id("detProLocSave");
	public By editLocationCancelBtn = By.id("detProLocCancel");
public By skill=By.xpath("//*[contains(text(),'Skills')]");
	public By editSkillsBtn = By.id("skillsEditIcon");
	public By enterSkills = By.id("skillsTypeAhead");
	public By editSkillsSaveBtn = By.id("skillsEditSave");
	public By editSkillsCancelBtn = By.id("skillsEditCancel");
public By imageIcon=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[2]/div[1]/img");
	public By changeStageIcon = By.xpath(".//*[@id='detProStage crmStage']/span");
	public By rejectedStage=By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[6]/a");
	public By holdStage=By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[4]/a/span[2]");
	public By applicantStage = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[4]/a/span[2]");
	public By engagedStage = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[3]/a/span[2]");
	public By leadStage = By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[2]/a/span[2]");
	public By saveStageBtn = By.id("crmStageOverlaySave");
	public By cancelStageBtn = By.id("crmStageOverlayCancel");

	public By summary = By.xpath(".//*[@id='detailProfileColDiv']//div[4]/div/div/ul/li[1]/a");// link
	public By positions = By
			.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[5]/div/div/div/div[1]/div[2]/div[1]/div");
	public By editPosition = By.id("detProPlus");
	public By designationTextField = By.id("designationTypeAhead");
	public By employerTextField = By.id("companyTypeAhead");
	public By startDateTextField = By.id("detProStartDate");
	public By startDateBtn = By.id("detProEditBtn");
	public By endDateTextField = By.id("detProEndDate");
	public By endDateBtn = By.id("detProEndDateBtn");
	public By savePositionBtn = By.id("detProSaveBtn");
	public By cancelPositionBtn = By.id("detProCancelBtn");

	public By education = By.xpath(".//*[@id='detailProfileColDiv']//div[4]//div/div[1]/div[3]/div[1]/div[1]");
	public By editEducation = By.id("detProEduEdit");
	public By degreeNameTextField = By.id("degreeTypeAhead");
	public By instituteName = By.id("instituteTypeAhead");
	public By degreeStartDateTextField = By.id("detProEduStartDate");
	public By degreeStartDateBtn = By.id("detProCal");
	public By degreeEndDateTextField = By.xpath("detProEduInput");
	public By degreeEndDateBtn = By.id("detProEduBtn");
	public By savedegree = By.id("detProEditSave");
	public By canceldegre = By.xpath(".//*[@id='form.edu']/div/div[2]/span/span[2]");

	public By addCandidatesToCampIcon = By.id("CampaignIconSpan");
	public By searchInList = By.id("dropdownSearchText");
	public By stagingCam = By.id("CampaignOptNameSpan");
	public By saveCandiadateToCam = By.id("dropdownSelCloseButton");
	public By cancelCandidateToCam = By.id("dropdownSelCancelButton");

	public By shareProfile = By.id("ShareUrlSpan");
	public By shareProfileUrl = By.id("ShareUrlSpan");
	public By closeshareProfile = By.id("dropdownSelCloseButton");
	public By saveShareProfile = By.id("dropdownSelCloseButton");
	public By cancelShareProfile = By.id("dropdownSelCancelButton");
	public By attachTags = By.id("tagOverLayImg");
	public By enterTag = By.id("labelsTypeAhead");
	public By saveTag = By.id("dropdownSelCloseButton");
	public By cancelTag = By.id("dropdownSelCancelButton");
	public By engagementScoreIcon = By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[2]/div[3]/div/span[1]");
	public By remindMeLater=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[2]/div[3]/div/span[2]");
	public By enterReminder=By.id("remindMessage");
	public By saveReminder=By.xpath("//button[@ng-disabled='reminderForm.$invalid']");
	public By emailIcon = By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[3]/ul/li[1]/span/span[2]");
	public By phoneIcon=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[3]/ul/li[2]/div");
	public By alreadyViewed=By.xpath(".//*[@id='alreadyViewed']/span");
	public By emailToAddress = By.id("sendEmailTo");
	public By emailSub = By.className("sub ng-binding");
	public By mailBody = By.className("body ng-binding");
	public By emailTem = By.id("SendMailSelect");
	public By sendEmailBtn = By.id("sendEmailSendBtn");
	public By cancelEmailBtn = By.id("sendEmailBtnCancel");
	public By cancelTab = By.id("SendMailCancel");

	public By activityStreamTab = By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[5]/div/div/ul/li[2]/a");
	public By createActivity = By.id("selectActivity");
	public By videoCall=By.id("asCreaActOption_0");
	public By dropdownSelActivity = By.id("asCreaActOption_0");
	public By goBackLink = By.xpath(".//*[@id='detProGoBackB']/span");
	public By downLoadCV=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[4]/div/button");
public By attachments=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[5]/div/div/ul/li[3]/a");
public By attachmentsRefresh=By.xpath(".//*[@id='detailProfileColDiv']//div[5]//div[3]//div[1]/div[1]/span[2]");
public By attachmentNewBtn=By.id("attachmentsCreateBtn");
public By browse=By.xpath(".//input[@type='file']");
public By isResume=By.id("categorySpan");
public By attachmentSave=By.xpath(".//*[@id='detailProfileColDiv']//div[1]//div[5]//div/div[3]//div[1]/div[1]/form/div[2]/div[1]/div[3]/button[1]");
public By attachmentSavePopup=By.xpath("html/body/md-toast/span");
public By fileActivityInHomepage=By.xpath(".//*[@id='crmCandidiateActivityDiv_0']//*[@id='HActivityStrong']/a[4]");
public By callActivityInHomepage=By.xpath(".//*[@id='crmActivityStreamListItem_0']//*[@id='HActivityStrong']/a[4]");
public By reminderSavePopup=By.xpath("html/body/md-toast/span");
public By socialNetworklable = By
			.xpath(".//*[@id='detailProfileColDiv']//div[4]/div/div/div/div[1]/div[2]/div[2]/div[1]");
	public By interestsGroups = By
			.xpath(".//*[@id='detailProfileColDiv']//div[4]/div/div/div/div[1]/div[3]/div[2]/div[1]");

	public By addCamp = By.xpath("id('CampaignIconSpan')");
	public By editLabels = By.id("tagOverLayImg");
	public By name = By.xpath(".//*[@id='detailProfileColDiv']//div[2]/div[2]/div[1]/strong");
	public By labelsEditSave = By.id("labelsEditSave");
	public By labelsEditCancel = By.id("labelsEditCancel");

	public By StageSavePopup = By.xpath("html/body/md-toast");
public By refreshIcon=By.xpath(".//*[@id='activityStreamCollapse']/div[1]/div[2]");
	public By activitySearchBox = By.id("asSearch");
	public By activitySearchbtn = By.id("asSearchIcon");
	public By sortBy=By.id("sortBy");
	public By timePeriod=By.id("asTimePeriod");
	public By selectCheckBox=By.id("asBoldTxtSpan");
	public By emailCheckBox=By.id("asCbLabel");
	public By callsChechBox=By.id("asCbLabel");
	public By smsChechBox=By.id("asCbLabel");
	public By selectCampa=By.id("selectCamp");
	public By allActivitiesCheckBoxes=By.xpath(".//*[@id='asCbLabel']");
	public By activityDropdown=By.xpath(".//*[@id='asCreaActOption']");
	public By activitylog1 = By.xpath(".//*[@id='asViewList_0']/div[1]");
	public By candidateNameLink = By.xpath(".//*[@id='HActivityStrong']/span[1]");
	public By homeTab=By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[1]/a");
	public By campIcon=By.xpath(".//*[@id='crmCandidiateActivityDiv_0']//*[@id='CampaignIconSpan']");
	public By hmPgErrMsg=By.xpath("html/body/md-toast/span");
}

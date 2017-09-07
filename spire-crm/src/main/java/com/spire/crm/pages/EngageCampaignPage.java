package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class EngageCampaignPage extends BasePage {

	public static String TEST_URL = "Engage/Campaign";
	public static String PROD_URL = "/UI/src/app/index.html#/Engage/Campaign";
	public static String URL = null;

	public EngageCampaignPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {
			if (ContextManager.getGlobalContext().getAttribute("ENVIRONMENT").toString()
					.equalsIgnoreCase("production")) {
				URL = ContextManager.getGlobalContext().getUIHostAddress() + PROD_URL;
			} else {
				URL = ContextManager.getGlobalContext().getUIHostAddress() + TEST_URL;
			}
			this.driver.get(URL);
			this.driver.navigate();
			// waitForTextToBeDisappeared(driver, LOADING_TEXT);
		}

	}

	public By createNewCampBtn = By.id("campCreateBtn");

	public By campaignNameTextField = By.id("campaignName");
	public By goBackLink = By.xpath(".//*[@id='ngViewDiv']//a/b");
	public By saveBtn = By.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[1]/button[2]");
	public By cancelBtn = By.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[1]/button[3]");
	public By cancelBtnAfterView = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[1]/button[2]");
	public By editIcon = By.xpath("(//span[@id='campEditBtn'])[2]");
	public By deleteIcon = By.xpath(".//*[@id='campListView_']/div[2]/div[1]/button[1]");
	public By cloneIcon = By.xpath(".//*[@id='campListView_']/div[2]/div[1]/button[2]");
	public By campaignTitle = By.className("ng-binding");
	public By searchTextfield = By.xpath(".//input[@placeholder='Search']");
	public By searchTextfieldBtn = By.xpath(".//*[@id='activityStreamPanelHeader']/div[2]/div[1]/span");

	public By campaignNameList = By.xpath(".//*[@id='campListView_']/div[2]/div[2]/span");

	public By modifiedOnDate = By.xpath(".//*[@id='campListView_']/div[3]/div[1]");
	public By modifiedByDate = By.xpath(".//*[@id='campListView_']/div[3]/div[2]");
	public By createdByText = By.xpath(".//*[@id='387']/div[2]");
	public By createdByName = By.xpath(".//*[@id='387']/div[2]/b");
	public By noOfCandidatesText = By.xpath(".//*[@id='387']/div[3]");
	public By numberOfCandidatesCount = By.xpath(".//*[@id='campListView_']/div[3]/div[1]");
	public By editCampaignBtn = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/div[2]/div/div/div/div/ul/li[1]/div/div/div[1]/span[1]");
	public By deleteCampaignBtn = By.xpath("//li[@id='campListView_']/div[2]/div/button");
	public By newBtn = By.id("campCreateBtn");

	public By enterNewCampName = By.xpath("campaignName");
	public By avtiveBtn = By.className("md-bar");
	public By numberOfCandidatesCountText = By.id("campBuildCandidatesLabel");
	public By numberOfCandidatesCountNew = By.id("campBuildCandidatesSpan");
	public By descriptionTextBox = By.id("campDescTxtArea");
	public By campIcon=By.xpath(".//*[@id='crmCandidiateActivityDiv_0']//*[@id='candRichDD campaignOverlay']");
	public By hmPgErrMsg=By.xpath("html/body/md-toast/span");
	public By newSaveCampBtn = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[1]/button[2]");
	public By newCancelBtn = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[1]/button[3]");
	public By actionslabel = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[2]/div/p/b");
	public By adjustLeadPoints = By.id("panel_16");// remove id
	public By changeCampaigns = By.id("panel_17");// remove
	public By sendMail = By.id("panel_18");// remove
	public By decisions = By.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/form/div[2]/div[2]/div/div[3]/div/p/b");
	public By openMail = By.id("panel_19");
	public By editCampaignAfterView = By.id("campaginEditBtn");
	public By campaignPublishedIcon = By.xpath("//span[@id='campActive']/md-switch/div/div");
	public By activityStreamTab = By.xpath("//*[contains(text(),'Activity Stream')]");
	public By changeStage=By.xpath(".//*[@id='detProStage crmStage']/span");
	public By engagedStage=By.xpath(".//*[@id='crmStageOverlay']/ul/ul[1]/li[3]/a/span[2]");
	public By createActivity = By.xpath(".//select[@id='selectActivity']");
	public By seachBar=By.id("button-template-url");
	public By dropDown=By.xpath(".//span[@title='camperrmsg']");
	public By searchBtn=By.id("HeaderSearchBtn");
	public By selectAll=By.xpath(".//*[@id='ngViewDiv']/div/div[3]/div[1]/div[1]/div[2]/div[2]/div/label/span");
	public By searchCamp=By.xpath(".//*[@id='ngViewDiv']/div/div[3]/div[1]/div[1]/div[2]/div[2]/div//*[@id='campaignOverlay']");
	public By enagedCamp=By.xpath(".//*[@id='ngViewDiv']/ui-view/div/div[3]/div/div[1]/div[1]/div[2]//div[@id='candRichDD campaignOverlay']");
	public By profileCamp=By.id("detProRichDD campaignOverlay");
	public By seachField=By.xpath(".//*[@id='SearchDropDown']/div/div/div/input");
	public By videoCall=By.id("asCreaActOption_0");
	public By saveActivity = By.id("asCreateActivitySave");
	public By homeTab=By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[1]/a");
	public By homeEngaged=By.id("HGraphA_1");
	public By engagedAll=By.id("candSelectAllSpan");
	public By campaignSavePopupBtn = By
			.xpath("//h4[text()='Publish / Unpublish Campaign']/../../child::div/child::button[text()='Save']");
	public By campaignCancelBtn = By
			.xpath("//h4[text()='Publish / Unpublish Campaign']/../../child::div/child::button[text()='Cancel']");
	public By sendEmailDrag = By.xpath("//div[@id='campBuildPanel']/child::div[1]");
	public By sendEmailDrop = By.xpath("//div[@class='campaign-editor']/div[2]");
	// div[ends-with[@class='ui-droppable']]
	public By sendEmailNameFld = By.xpath("//input[@id='sendEmailName']");
	public By selectEmailTemplate = By.xpath("//select[@id='emailTemplateSelect']");
	public By sendEmailAddBtn = By.xpath("//form[@name='sendEmailForm']//button[1]");
	public By startNodeStartingPoint = By.xpath("//div[@class='campaign-editor']//div/child::circle[1]");
	public By sendEmailStartingPoint = By.xpath("//div[@class='campaign-editor']//div/child::circle[2]");
	public By campaignSaveBtn = By.xpath("//button[@id='campBuildSaveBtn']");
	public By cloneCampaignbtn = By.xpath("//li[@id='campListView_']/div[2]/div/button[2]");
	public By cloneCampaignName = By.id("cloneCamp");
	public By deleteCampaignbtn = By.xpath("//li[@id='campListView_']/div[2]/div/button");
	public By deleteConfirmationbtn = By.xpath("//md-dialog/div/button[2]");
	public By cloneSavebtn = By.xpath("//div[2]/button");
	public By savedmsg = By.xpath("html/body/md-toast/span");
	public By campaginFullviewBtn = By.id("campaginFSBtn");
	
}
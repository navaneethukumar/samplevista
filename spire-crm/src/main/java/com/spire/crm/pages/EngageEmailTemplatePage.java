package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class EngageEmailTemplatePage extends BasePage {

	public static String TEST_URL = "/UI/src/app/index.html#/Engage/Email-Templates";
	public static String PROD_URL = "/UI/src/app/index.html#/Engage/Email-Templates";
	public static String URL = null;

	public By templateName = By.xpath("//input[@id='templateName']");
	public By templateSubject = By.xpath("//input[@id='templateSubject']");
	public By tempalteSaveBtn = By.xpath("//div/button[@type='submit']");
	public By templateEditBtn = By.xpath("//button[text()='Edit']");
	public By templateCloseBtn = By.xpath("//button[text()='Close']");
	public By deleteTemplatePopup = By.xpath("//div[@class='md-actions']//span[text()='Delete']");
	public final String templateSuccessMsg = "Email Template Saved Successfully";
	public final String emailTempalteDeletedSuccessMsg = "Email Template Deleted Successfully";

	public EngageEmailTemplatePage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			if (openurl) {
				if (ContextManager.getGlobalContext().getAttribute("ENVIRONMENT").toString()
						.equalsIgnoreCase("production")) {
					URL = ContextManager.getGlobalContext().getUIHostAddress() + PROD_URL;
				} else {
					URL = ContextManager.getGlobalContext().getUIHostAddress() + PROD_URL;
				}
				this.driver.get(URL);
				// waitForTextToBeDisappeared(driver, LOADING_TEXT);
			}
		}

	}

	public By engageTab = By.id("simple-dropdown");
	public By engage_EmailTemplateTab = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li[4]/a");
	public By searchTextField = By.id("emailTempSearch");
	public By searchBtn = By.xpath(".//*[@id='emailsManage']//div[2]/div[1]/span");
	public By newBtn = By.id("createEmailTempNew");
	public By tableHeader = By.xpath(".//*[@id='emailsManage']/div/div[2]/div[1]/div/table/thead/tr");
	public By modifiedOnText = By.xpath("//tbody//tr//td[3]");
	public By firstTemplate = By.xpath("//table/tbody/tr[1]/td[2]");
	public By secondTemplate = By.xpath(".//*[@id='createEmailTempList_1']//*[@id='createEmailTempName']");
	public By listOfEmailTemp = By.xpath(".//tbody//tr//td[2]");
	public By listOfEditTemBtn = By.id("emailEditBtn");
	public By firstEditButton = By.xpath(".//*[@id='createEmailTempList_0']//*[@id='createEmailGetTemp']");
	public By secondEditButton = By.xpath(".//*[@id='createEmailTempList_1']//*[@id='createEmailGetTemp']");
	public By listOfDeleteTemBtn = By.id("emailEditBtn");
	public By firstDeleteBtn = By
			.xpath(".//*[@id='emailsManage']/div/div[2]/div[1]/div/table/tbody/tr[1]/td[1]/button");
	public By secondDeleteBtn = By.xpath(".//*[@id='createEmailTempList_1']//*[@id='createEmailTempBtn']");
	public By pagination = By.id("emailPagination");
	public By firstPagination = By.xpath(".//*[@id='emailPagination']/li[1]/a");
	public By previousPaginationBtn = By.xpath(".//*[@id='emailPagination']/li[2]/a");
	public By nextPaginationBtn = By.xpath(".//*[@id='emailPagination']/li[2]/a");
	public By lastPaginationBtn = By.xpath(".//*[@id='emailPagination']/li[7]/a");
	public By createdOnText = By.xpath(".//*[@id='276']/div[1]/span[1]");
	public By createdByText = By.xpath(".//*[@id='276']/div[2]/span[1]");
	public By emailSentText = By.xpath(".//*[@id='276']/div[3]/span[1]");
	public By mailReadText = By.xpath(".//*[@id='276']/div[4]/span[1]");
	public By emailIcon = By.id("createEmailOpenView");
	public By emailTemplateName = By.id("templateName");
	public By emailSub = By.id("templateSubject");
	public By emailScrollBar=By.xpath(".//*[@id='emailsManage']/div/div/div[3]/div");
	public By body = By.xpath("html/body/div[1]");
	public By title = By.xpath(".//*[@id='candField_0']/div[1]");
	public By templateeBody = By.xpath(".//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']");
	public By firstName = By.xpath(".//*[@id='candField_1']/div[1]");
	public By lastName = By.xpath(".//*[@id='candField_2']/div");
	public By mobile = By.xpath(".//*[@id='candField_3']/div[1]");
	public By assets = By.id("assetIconSpan");
	public By templateBodyPar1 = By.id("taTextElement8374750895849032");
	public By templateEdit = By.id("createEmailTempEdit");
	public By templateSave = By.id("createEmailTempSave");
	public By createdPopup = By.xpath("html/body/md-toast/span");
	public By editedSuccessfullyPopup = By.xpath("html/body/md-toast");
	public By deleteConfirmation = By.xpath("//button[@ng-click='dialog.hide()']");
	public By deletedPopup = By.xpath("html/body/md-toast/span");
	public By templateClose = By.id("createEmailTempClose");
	public By templateBody = By.id("taTextElement3430894115009130");
	public By templateSignature = By.id("taTextElement2615435715338458");
	public By tempSave = By.xpath("//button[@ng-disabled='savingTemplate']");
	public By tempclose = By.id("createEmailTemplateClose");
public By feedback=By.id("queDropdown");
public By firstQuestion=By.xpath(".//*[@id='queDropdown']/option[2]");
public By addFeedback=By.xpath(".//*[@id='feedbackQue']/div/button");
public By emailIconInDetailsPage=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[3]/ul/li[1]/span/span[2]");
public By templatesDropdown=By.id("SendMailSelect");
public By allTempaltes=By.xpath(".//*[@id='SendMailSelect']/optgroup[2]");

public By sendEmail=By.id("emailSendBtn");
public By scheduledList=By.xpath(".//button[@class='scheduled-mails']");
public By scheduleEmailPopup=By.xpath("html/body/md-toast/span");
public By sendEmailPopup=By.xpath("html/body/md-toast/span");
public By activityTab=By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[5]/div/div/ul/li[2]/a");
public By refreshIcon=By.xpath(".//*[@id='activityStreamCollapse']/div[1]/div[2]");
public By emailLink=By.xpath(".//*[@id='asViewList_0']/div[1]/a[2]/b");
public By engagementScore=By.xpath(".//*[@id='detProEngScoB']/span");

public By calnder=By.xpath(".//*[@id='tempaltesFieldset']/div[2]/div[2]/div/i");
public By date=By.xpath("//div[@class='adp-day selectable ng-binding ng-scope selected today']");

}

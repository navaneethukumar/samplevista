package com.spire.crm.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class Admin_UiPage extends BasePage {

	public static String url = "/UI/src/app/index.html#/Advanced-Search";

	public Admin_UiPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext().getUIHostAddress() + url;
			this.driver.get(ui_url);
		}
	}
	
	/*********************
	 * engagementRule
	 ********************/
	public By engagementRuleTab = By.xpath("//a[text()='Engagement  Rule']");
	public By engagementScoreWeight = By.xpath("//a[@class='active']");
	public By headerProfileImage = By.id("HeaderProfImg");
	public By userName = By.xpath(".//*[@id='HeaderProfDD']/span[1]");
	public By userNameDropdown = By.id("HeaderProfSpan");
	public By ListOfRuleForTenantTest = By.xpath(".//*[@id='ngViewDiv']//div[1]/span");
	public By searchTextBox = By.xpath("//input[@type='text']");
	public By searchBtn = By.id("engScoreSearchSpan");

	public By newBtn = By.xpath("//input[@type='button']");
	public By ruleNameText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[1]/th[1]");
	public By whenText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[1]/th[2]");
	public By fieldText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[2]/th[1]");
	public By operatorText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[2]/th[2]");
	public By valuetext = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[2]/th[3]");
	public By thenText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[1]/th[3]");
	public By actionNameText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[2]/th[4]");
	public By propertiesText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[2]/th[5]");
	public By getProperty = By.xpath("//table/tbody/tr[1]/td[6]/span");
	public By editText = By.xpath(".//*[@id='ngViewDiv']//table/thead/tr[1]/th[4]");

	public By ruleName = By.xpath("//input[@name='rule_name']");
	public By field = By.xpath("//select[contains(@name,'field')]");
	public By engagementScoreDropDown = By.xpath(".//option[@value='string:ES']");
	public By operator = By.xpath(".//select[@name='operator']");
	public By value1 = By.xpath(".//input[@name='value_0']");
	public By value2 = By.xpath(".//input[@name='value_1']");
	public By actionName = By.xpath("//select[@name='action_name']");
	public By firstOprt = By.xpath(".//*[@id='ngViewDiv']//table/tbody/tr[1]/td[3]/span");// greaterThan
	public By properties = By.xpath("//select[@name='action_value']");

	public By greaterThan = By.xpath(".//*[@id='ngViewDiv']//table/tbody/tr[1]/td[3]/span[2]/div/select/option[2]");
	public By lessThanOperatr = By.xpath(".//*[@id='ngViewDiv']//td[3]/span[2]/div/select/option[3]");
	public By equalsOperatr = By.xpath(".//*[@id='ngViewDiv']//td[3]/span[2]/div/select/option[4]");
	public By betweenOperatr = By.xpath(".//*[@id='ngViewDiv']//td[3]/span[2]/div/select/option[5]");
	public By saveBtn = By.xpath(".//*[@id='ngViewDiv']//div[2]/div/div[1]/div[1]/table/tbody/tr[1]/td[8]/form/button[1]");

	public By getOldValue = By.xpath("//table/tbody/tr[1]/td[4]/span");
	public By valueDropDownselect1 = By.xpath(".//*[@id='ngViewDiv']//span[2]/div/select/option[1]");
	public By actionNameDropdown = By.xpath("//select[@name='action_name']");
	public By changeCRMstage = By.xpath("//option[@label='CHANGE_CRM_STAGE']");
	public By assignCampaign = By.xpath(".//*[@id='ngViewDiv']//td[5]/span[2]/div/select/option[3]");
	public By createCRMsubStage = By.xpath(".//*[@id='ngViewDiv']//td[5]/span[2]/div/select/option[4]");

	public By saveSuccessfully = By.xpath("html/body/md-toast/span");
	public By cancelBtn = By.xpath(".//*[@id='ngViewDiv']//tr[1]/td[7]/form/button[2]");
	public By deleteSuccessfully = By.xpath("html/body/md-toast/span");
	public By newlyCreatedRule = By.xpath("//table//tr/td[1]");
	public By rulesList = By.xpath("//table/tbody/tr/td[1]/span");

	public By firstRuleName = By.xpath(".//span[@e-name='rule_name']");
	public By firstDeleteBtn = By.xpath("//table/tbody/tr[1]/td[8]/div/button[2]");
	public By allDeleteBtn = By.xpath(".//*[@id='ngViewDiv']//table/tbody/tr/td/div/button[2]");

	public By firstEditBtn = By.xpath(".//table/tbody/tr[1]/td[8]/div/button[1]");
	public By tenant = By.xpath("//div[@class='col-sm-4 col-xs-12']");
	public By listOfEditBtn = By.xpath(".//*[@id='ngViewDiv']//table/tbody/tr/td[7]/div/button[1]");
	/*********************
	 * engagementWeight
	 ********************/
	public By engagementScoreWeightTab = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/a");
	public By engagementScoreWeightBox = By.xpath("//div[@class='col-md-12 col-lg-12  ng-scope']");
	public By engagementScoreWeightText = By.xpath("//b");
	public By editBtn = By.xpath("//span[@class='glyphicon glyphicon-pencil']");
	public By tenantId = By.xpath(".//*[@id='ngViewDiv']//table/tbody/tr[1]/td[1]");
	public By compatibilityTest = By.xpath("//div/table/tbody/tr[2]/td[1]");
	public By table = By.xpath(".//td[@class='col-sm-6 ng-binding']");
	public By connectFactorsText = By.xpath("//table/tbody/tr[3]/td[2]");
	public By awarenessText = By.xpath("//table/tbody/tr[4]/td[2]");
	public By personalCommText = By.xpath("//table/tbody/tr[5]/td[2]");
	public By interestLevelText = By.xpath("//table/tbody/tr[6]/td[2]");
	public By broadcastCommText = By.xpath("//table/tbody/tr[7]/td[2]");
	public By compatibility = By.xpath("//input[@name='compatability']");
	public By connectFactrs = By.xpath("//input[@name='connect_factors']");
	public By awareness = By.xpath("//input[@name='awareness']");
	public By personalComm = By.xpath("//input[@name='personal_communication']");
	public By interestLevel = By.xpath("//input[@name='interest_level']");
	public By broadcastComm = By.xpath("//input[@name='broadcast_communication']");
	public By engagementWeightCancelBtn = By.xpath("//button[@class='glyphicon glyphicon-ok']");
	public By engagementWeightSaveBtn = By.xpath("//button[@class='glyphicon glyphicon-remove']");
	public By draggablePartOfScrollbar = By.xpath("//div[@class='ps-scrollbar-y']");

}

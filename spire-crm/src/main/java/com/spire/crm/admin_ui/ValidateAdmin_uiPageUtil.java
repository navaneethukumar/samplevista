package com.spire.crm.admin_ui;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pageUtils.LoginPageUtil;
import com.spire.crm.pages.Admin_UiPage;

public class ValidateAdmin_uiPageUtil extends Admin_UiPage {

	EngagementRule ruleToDelete = null;
	String ruleNamestr = null;
	String storeCreatedRule = null;

	public ValidateAdmin_uiPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);

	}

	/*
	 * check whether all the elements like
	 * engagementRuleTab,userName,userNameDropdown,searchBtn,editText are
	 * displayed in admin_ui page o not.
	 */
	public void ValidateElementsPresent() {
		driver.manage().window().maximize();

		try {
			WebPageHelper.waitForElementToBeVisible(driver, engagementRuleTab);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, engagementRuleTab);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}

		WebPageHelper.waitForElementToBeVisible(driver, engagementRuleTab);

		waitForElementToBeVisible(driver, engagementRuleTab);

		Assert.assertTrue(isElementPreset(engagementRuleTab), "engagementRuleTab is missing!!");
		waitForElementToBeVisible(driver, engagementScoreWeightTab);
		Assert.assertTrue(isElementPreset(engagementScoreWeightTab), "engagementScoreWeightTab is missing!!");
		waitForElementToBeVisible(driver, headerProfileImage);
		Assert.assertTrue(isElementPreset(headerProfileImage), "headerProfileImage is missing!!");
		waitForElementToBeVisible(driver, userName);
		Assert.assertTrue(isElementPreset(userName), "userName is missing!!");
		waitForElementToBeVisible(driver, userNameDropdown);
		Assert.assertTrue(isElementPreset(userNameDropdown), "userNameDropdown is missing!!");
		waitForElementToBeVisible(driver, ListOfRuleForTenantTest);
		Assert.assertTrue(isElementPreset(ListOfRuleForTenantTest), "ListOfRuleForTenantTest is missing!!");
		waitForElementToBeVisible(driver, searchTextBox);
		Assert.assertTrue(isElementPreset(searchTextBox), "searchTextBox is missing!!");
		waitForElementToBeVisible(driver, searchBtn);
		Assert.assertTrue(isElementPreset(searchBtn), "searchBtn is missing!!");
		waitForElementToBeVisible(driver, newBtn);
		Assert.assertTrue(isElementPreset(newBtn), "newBtn is missing!!");
		waitForElementToBeVisible(driver, ruleNameText);
		Assert.assertTrue(isElementPreset(ruleNameText), "ruleNameText is missing!!");
		waitForElementToBeVisible(driver, whenText);
		Assert.assertTrue(isElementPreset(whenText), "whenText is missing!!");
		waitForElementToBeVisible(driver, fieldText);
		Assert.assertTrue(isElementPreset(fieldText), "fieldText is missing!!");
		waitForElementToBeVisible(driver, operatorText);
		Assert.assertTrue(isElementPreset(operatorText), "operatorText is missing!!");
		waitForElementToBeVisible(driver, thenText);
		Assert.assertTrue(isElementPreset(thenText), "thenText is missing!!");
		waitForElementToBeVisible(driver, actionNameText);
		Assert.assertTrue(isElementPreset(actionNameText), "actionName is missing!!");
		waitForElementToBeVisible(driver, propertiesText);
		Assert.assertTrue(isElementPreset(propertiesText), "propertiesText is missing!!");
		waitForElementToBeVisible(driver, editText);
		Assert.assertTrue(isElementPreset(editText), "editText is missing!!");

	}

	/*
	 * create new rule with action change stage
	 */
	public String createNewRule(EngagementRule rule) {
		driver.manage().window().maximize();
		
		
		try {
			WebPageHelper.waitForElementToBeVisible(driver, ruleName);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, ruleName);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}

		WebPageHelper.waitForElementToBeVisible(driver, ruleName);
		
		
		
		this.ruleToDelete = rule;

		clicElement(driver, newBtn);
		waitForElementToBeVisible(driver, ruleName);
		ruleNamestr = rule.getRuleName() + RuleName();
		waitForElementToBeVisible(driver, ruleName);
		enterText(driver, ruleName, ruleNamestr);
		selectdropdownBytext(driver, field, rule.getField());
		selectdropdownBytext(driver, operator, rule.getOperator());
		enterText(driver, value1, rule.getValue());
		WebPageHelper.clicElement(driver, actionName);
		selectdropdownBytext(driver, actionNameDropdown, rule.getActionName());
		waitForElementToBeVisible(driver, properties);
		WebPageHelper.selectdropdownBytext(driver, properties, rule.getProperties());
		clicElement(driver, saveBtn);

		driver.navigate().refresh();

		String newlyCreatedRule = getElementText(driver, firstRuleName);

		if (newlyCreatedRule.equals(ruleNamestr)) {
			Logging.log(" Rule is created successully !!!" + ruleNamestr);
		} else {
			Logging.log(" Seems ruleName is not created !!!!" + "RuleName is " + ruleNamestr);
		}
		waitForElementToBeVisible(driver, searchTextBox);
		waitForElementToBeVisible(driver, firstRuleName);
		enterText(driver, searchTextBox, ruleNamestr);
		clicElement(driver, searchBtn);
		return newlyCreatedRule;
	}
	/*
	 * create new rule with action Assign campaign.
	 */

	public String createNewRuleCampaign(EngagementRule rule) {
		driver.manage().window().maximize();
		try {
			WebPageHelper.waitForElementToBeVisible(driver, newBtn);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, newBtn);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}	WebPageHelper.waitForElementToBeVisible(driver, newBtn);
		this.ruleToDelete = rule;
		waitForElementToBeVisible(driver, newBtn);
		clicElement(driver, newBtn);
		waitForElementToBeVisible(driver, ruleName);
		ruleNamestr = rule.getRuleName() + RuleName();
		enterText(driver, ruleName, ruleNamestr);
		selectdropdownBytext(driver, field, rule.getField());
		selectdropdownBytext(driver, operator, rule.getOperator());
		enterText(driver, value1, rule.getValue());
		WebPageHelper.clicElement(driver, actionName);
		selectdropdownBytext(driver, actionNameDropdown, rule.getActionName());
		waitForElementToBeVisible(driver, properties);
		WebPageHelper.selectdropdownByindex(driver, properties, 1);
		clicElement(driver, saveBtn);

		driver.navigate().refresh();

		if (newlyCreatedRule.equals(ruleNamestr)) {
			Logging.log(" Rule is created successully !!!" + ruleNamestr);
		} else {
			Logging.log(" Seems ruleName is not created !!!!" + "RuleName is " + ruleNamestr);
		}
		waitForElementToBeVisible(driver, searchTextBox);
		enterText(driver, searchTextBox, ruleNamestr);
		clicElement(driver, searchBtn);
		waitForElementToBeVisible(driver, firstRuleName);
		String newlyCreatedRule = getElementText(driver, firstRuleName);

		return newlyCreatedRule;
	}

	/*
	 * create rule with operator between(value1 and value 2).
	 */
	public String createNewRuleBW(EngagementRule rule) {
		driver.manage().window().maximize();
		try {
			WebPageHelper.waitForElementToBeVisible(driver, newBtn);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, newBtn);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}WebPageHelper.waitForElementToBeVisible(driver, newBtn);
		this.ruleToDelete = rule;

		clicElement(driver, newBtn);
		waitForElementToBeVisible(driver, ruleName);
		ruleNamestr = rule.getRuleName() + RuleName();
		waitForElementToBeVisible(driver, ruleName);
		enterText(driver, ruleName, ruleNamestr);
		selectdropdownBytext(driver, field, rule.getField());
		selectdropdownBytext(driver, operator, rule.getOperator());
		enterText(driver, value1, rule.getValue());
		enterText(driver, value2, rule.getValue2());
		WebPageHelper.clicElement(driver, actionName);
		selectdropdownBytext(driver, actionNameDropdown, rule.getActionName());
		waitForElementToBeVisible(driver, properties);
		selectdropdownByindex(driver, properties, 1);
		waitForElementToBeVisible(driver, saveBtn);
		clicElement(driver, saveBtn);

		String actualPopup = getElementText(driver, saveSuccessfully);
		String expected = "Record added successfully";
		Assert.assertEquals(actualPopup, expected);
		Logging.log(" Rule is created successully");

		driver.navigate().refresh();

		enterText(driver, searchTextBox, ruleNamestr);
		clicElement(driver, searchBtn);
		waitForElementToBeVisible(driver, firstRuleName);
		String newlyCreatedRule = getElementText(driver, firstRuleName);

		if (newlyCreatedRule.equals(ruleNamestr)) {
			Logging.log(" Rule is created successully !!!" + ruleNamestr);
		} else {
			Logging.log(" Seems ruleName is not created !!!!" + "RuleName is " + ruleNamestr);
		}

		return newlyCreatedRule;
	}

	/*
	 * Validate newly created rule name is displayed in CRM
	 * EngagementScoreRulePage.
	 * 
	 */

	public By engageTabCRM = By.id("simple-dropdown");
	public By engage_engaementScoreRule = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[2]/span/ul/li[6]/a");
	public By searchBoxCRM = By.id("engRuleSearch");
	public By searchBtnCRM = By.xpath(".//*[@id='rulesPanelHeader']/div[2]/div/span");
	public By firstRuleNameCRM = By.xpath(".//*[@id='DataTables_Table_0']/tbody/tr/td[1]/span");

	/*
	 * Delete Rule
	 */

	public void deleteRule() {

		driver.manage().window().maximize();
		try {
			WebPageHelper.waitForElementToBeVisible(driver, firstDeleteBtn);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, firstDeleteBtn);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}
		
		WebPageHelper.waitForElementToBeVisible(driver, firstDeleteBtn);
		
		
		clicElement(driver, firstDeleteBtn);
		String actualPopup = getElementText(driver, saveSuccessfully);
		String expected = "Record deleted successfully";
		Assert.assertEquals(actualPopup, expected);
		Logging.log(" Rule is deleted successully");
		driver.navigate().refresh();

	}

	/*
	 * edit Rule
	 */
	public void EditRule(EngagementRule rule) {
		driver.manage().window().maximize();
		try {
			WebPageHelper.waitForElementToBeVisible(driver, firstEditBtn);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, firstEditBtn);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}
		WebPageHelper.waitForElementToBeVisible(driver, firstEditBtn);
		
		
		clicElement(driver, firstEditBtn);
		clicElement(driver, tenant);
		WebPageHelper.waitForElementToBeVisible(driver, operator);
		selectdropdownBytext(driver, operator, rule.getOperator());
		enterText(driver, value1, rule.getValue());
		clicElement(driver, saveBtn);

		// driver.navigate().refresh();

		String actualPopup = getElementText(driver, saveSuccessfully);
		String expected = "Record updated successfully";
		Assert.assertEquals(actualPopup, expected);
		Logging.log(" Rule is updated successully");

	}

	/*
	 * Create different new Rule name
	 */
	public String RuleName() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();

		Logging.log("Random UUID String = " + randomUUIDString);
		return randomUUIDString.substring(0, 5);
	}

	/*
	 * Create NewRule and Verify In CRM
	 */
	public void createNewRuleNVerifyInCRM(EngagementRule rule) {
		driver.manage().window().maximize();
		try {
			WebPageHelper.waitForElementToBeVisible(driver, ruleName);
		} catch (Exception e) {
			driver.navigate().refresh();

			try {

				WebPageHelper.waitForElementToBeVisible(driver, ruleName);

			} catch (Exception ee) {
				driver.navigate().refresh();

			}

		}
		WebPageHelper.waitForElementToBeVisible(driver, ruleName);
		
		
		this.ruleToDelete = rule;

		clicElement(driver, newBtn);
		waitForElementToBeVisible(driver, ruleName);
		ruleNamestr = rule.getRuleName() + RuleName();
		waitForElementToBeVisible(driver, ruleName);
		enterText(driver, ruleName, ruleNamestr);
		selectdropdownBytext(driver, field, rule.getField());
		selectdropdownBytext(driver, operator, rule.getOperator());
		enterText(driver, value1, rule.getValue());
		WebPageHelper.clicElement(driver, actionName);
		selectdropdownBytext(driver, actionNameDropdown, rule.getActionName());
		waitForElementToBeVisible(driver, properties);
		WebPageHelper.selectdropdownBytext(driver, properties, rule.getProperties());
		clicElement(driver, saveBtn);
		waitForElementToBeVisible(driver, searchTextBox);
		driver.navigate().refresh();

		enterText(driver, searchTextBox, ruleNamestr);
		clicElement(driver, searchBtn);
		waitForElementToBeVisible(driver, firstRuleName);
		String newlyCreatedRule = getElementText(driver, firstRuleName);

		if (newlyCreatedRule.equals(ruleNamestr)) {
			Logging.log(" Rule is created successully !!!" + ruleNamestr);
		} else {
			Logging.log(" Seems ruleName is not created !!!!" + "RuleName is " + ruleNamestr);
		}

		WebDriver driver = new ChromeDriver();
		LoginPageUtil obj2 = new LoginPageUtil(driver, true);
		obj2.login();
		driver.manage().window().maximize();
		WebPageHelper.waitForElementToBeVisible(driver, engageTabCRM);
		WebPageHelper.clicElement(driver, engageTabCRM);
		WebPageHelper.waitForElementToBeVisible(driver, engage_engaementScoreRule);
		WebPageHelper.clicElement(driver, engage_engaementScoreRule);
		WebPageHelper.waitForElementToBeVisible(driver, searchBoxCRM);
		WebPageHelper.enterText(driver, searchBoxCRM, ruleNamestr);
		WebPageHelper.clicElement(driver, searchBtnCRM);
		driver.navigate().refresh();
		WebPageHelper.waitForElementToBeVisible(driver, firstRuleNameCRM);
		String newlyCreatedRule1 = WebPageHelper.getElementText(driver, firstRuleNameCRM);

		if (ruleNamestr.equals(newlyCreatedRule1)) {
			Logging.log(" Rule is created successully in CRM !!!" + ruleNamestr);
		} else {
			Logging.log(" Seems ruleName is not appearing in CRM appln !!!!" + "RuleName is " + ruleNamestr);
		}
	}

	public void ValidateNewRule(String ruleName) {
		driver.manage().window().maximize();
		WebPageHelper.waitForElementToBeVisible(driver, searchBoxCRM);
		WebPageHelper.enterText(driver, searchBoxCRM, ruleName);
		WebPageHelper.waitForElementToBeVisible(driver, searchBtn);
		WebPageHelper.clicElement(driver, searchBtn);
		WebPageHelper.waitForElementToBeVisible(driver, firstRuleName);
		String newlyCreatedRule = WebPageHelper.getElementText(driver, firstRuleName);

		ruleName.equals(newlyCreatedRule);
	}
}
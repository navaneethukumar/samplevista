package com.spire.crm.admin_ui.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.admin_ui.EngagementRule;
import com.spire.crm.admin_ui.ValidateAdmin_uiPageUtil;
import com.spire.crm.pages.AdminLoginPage;

/**
 * 
 * @author Sangeeta
 *
 */
@Test(groups = { "Sanity" })
public class CRM_AdminUITestPlan extends TestPlan {

	@DataProvider(name = "CRM_AdminUITestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/admin_ui/test/CRM_AdminUITestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("EngagementRule", EngagementRule.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(CRM_AdminUITestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "ValidateElementsPresent" }, dataProvider = "CRM_AdminUITestData")
	public void ValidateElementsPresent(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.ValidateElementsPresent();

	}

	@Test(groups = { "createNewRule" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRule(rule);

	}

	@Test(groups = { "createNewRule2" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule2(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRule(rule);

	}

	@Test(groups = { "createNewRule3" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule3(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRule(rule);

	}

	@Test(groups = { "createNewRule4" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule4(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRuleCampaign(rule);

	}

	@Test(groups = { "createNewRule5" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule5(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRuleCampaign(rule);

	}

	@Test(groups = { "createNewRule6" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule6(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRuleCampaign(rule);

	}

	@Test(groups = { "createNewRule7" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRule7(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRuleBW(rule);

	}

	/*
	 * @Test(groups = { "checkRuleNameInCRM" }, dataProvider =
	 * "CRM_AdminUITestData",priority=3) public void
	 * checkRuleNameInCRM(SpireTestObject testObject,EngagementRule rule) {
	 * 
	 * // AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);
	 * 
	 * //initializeDriver(loginPageUtil.driver, testObject);
	 * 
	 * //loginPageUtil.login(); LoginPageUtil loginPageUtil = new
	 * LoginPageUtil(null, true);
	 * 
	 * initializeDriver(loginPageUtil.driver, testObject);
	 * 
	 * loginPageUtil.login();
	 * 
	 * 
	 * ValidateAdmin_uiPageUtil admin_ui = new
	 * ValidateAdmin_uiPageUtil(this.driver, false);
	 * 
	 * 
	 * admin_ui.checkRuleNameInCRM(rule);
	 * 
	 * }
	 */
	@Test(groups = { "createNewRuleNVerifyInCRM" }, dataProvider = "CRM_AdminUITestData")
	public void createNewRuleNVerifyInCRM(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.createNewRuleNVerifyInCRM(rule);

	}

	@Test(groups = { "deleteRule" }, dataProvider = "CRM_AdminUITestData")
	public void deleteRule(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);
		admin_ui.deleteRule();

	}

	@Test(groups = { "EditRule" }, dataProvider = "CRM_AdminUITestData")
	public void EditRule(SpireTestObject testObject, EngagementRule rule) {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();
		driver.navigate().refresh();
		ValidateAdmin_uiPageUtil admin_ui = new ValidateAdmin_uiPageUtil(this.driver, false);

		admin_ui.EditRule(rule);

	}

	// @Test(groups = { "EditRule", "Sanity" }, dataProvider =
	// "CRM_AdminUITestData")
	// public void EditRule(SpireTestObject testObject,EngagementRule rule) {

	// AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

	// initializeDriver(loginPageUtil.driver, testObject);

	// loginPageUtil.login();

	// ValidateAdmin_uiPageUtil admin_ui = new
	// ValidateAdmin_uiPageUtil(this.driver, false);

	// admin_ui.EditRule(rule);

	// }

}

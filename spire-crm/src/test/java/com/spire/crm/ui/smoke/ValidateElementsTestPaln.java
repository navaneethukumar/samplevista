package com.spire.crm.ui.smoke;

/**
 * @author Santosh C
 */

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.EngageCampaignPageUtil;
import com.spire.crm.pageUtils.EngageEmailTemplatePageUtil;
import com.spire.crm.pageUtils.EngageScoreRulesPageUtil;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;

@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ValidateElementsTestPaln extends UITestPlan {

	@DataProvider(name = "validateElementsData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/ui/smoke/ValidateElementsTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ValidateElementsTestPaln.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Steps: Login and verify Home page elements
	 * 
	 * Validation : After login all the elements like SearchTextfield,
	 * NotificationIcon, LoggedInUserIcon, 4Tabs(Home, Engage, Jobs, Reports), 5
	 * fields like CRM Pipeline, ActivityStream, Jobs, PopularTags,
	 * OpenPositions
	 * 
	 * @throws Exception
	 */

	@Test(groups = { "validateHomePageElements", "Sanity" }, dataProvider = "validateElementsData")
	public void validateHomePageElements(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validateHomePageElements();

	}

	/**
	 * Steps: 1. Login 2. Select values from pipeline date filter and
	 * activityStream date filter Expected: Both dropdowns should be present and
	 * should be able to select values from dropdowns
	 * 
	 */
	@Test(groups = { "validateDropdowns", "Sanity" }, dataProvider = "validateElementsData")
	public void validateDropdowns(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);


		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validateDropdowns();

	}

	/**
	 * Steps: 1. Login 2. check for pipeline data like Applicant, Engage, Hold
	 * and Lead Expected: 4stages of CRM pipeline should be displayed with
	 * number of applicants, number of leads etc
	 */
	@Test(groups = { "validatePipeLineData", "Sanity" }, dataProvider = "validateElementsData")
	public void validatePipeLineData(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);


		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validatePipeLineData();

	}

	/**
	 * Steps: 1. Login 2. check for pipeline data like Applicant, Engage, Hold
	 * and Lead Expected: 4stages of CRM pipeline should be displayed with
	 * number of applicants, number of leads etc
	 */
	@Test(groups = { "validatePopularTagsData", "Sanity" }, dataProvider = "validateElementsData")
	public void validatePopularTagsData(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validatePopularTagsData();

	}

	/**
	 * Steps: 1. Login 2. Check for ActivityData Expected: In ActivityStream
	 * field, activities should be displayed
	 * 
	 * @throws Exception
	 */

	@Test(groups = { "validateActivityData", "Sanity" }, dataProvider = "validateElementsData")
	public void validateActivityData(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.ValidateActivityData();

	}

	/**
	 * Steps: 1. Login 2. Check for icons like Mobile, Email, favourite icons in
	 * ActivityStream Expected: All the icons should be present
	 */
	@Test(groups = { "validateActivityIcons", "Sanity" }, dataProvider = "validateElementsData")
	public void validateActivityIcons(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validateActivityIcons();

	}

	/**
	 * Steps: 1. Login 2. Check for Recent Posts data Expected: Recent post
	 * details should be displayed
	 */
	@Test(groups = { "validateRecentPostData", "Sanity" }, dataProvider = "validateElementsData")
	public void validateRecentPostData(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);


		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validateRecentPostData();

	}

	/**
	 * Steps: 1. Login 2. Check for Open Positions data Expected: Open positions
	 * should be displayed in Open Positions field
	 */
	@Test(groups = { "validateOpenPositionsData", "Sanity" }, dataProvider = "validateElementsData")
	public void validateOpenPositionsData(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);

		homeUtil.validateOpenPositionsData();

	}

	/**
	 * Steps: 1. Login 2. Click EngageTab, Validate elements in EngagePage
	 * Expected: Verify that EngagePage has 4tabs like
	 * Home,Campaigns,EmailTemplates,EngmtScoreRules. 4CRM Stages, Minimize
	 * stages icon, engmtscore increase/decrease icon, LastEngmtTime dropdown,
	 * selectAll icon, message icon, tag icon, email icon
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateEngageList", "Sanity" }, dataProvider = "validateElementsData")
	public void validateEngageList(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.validateEngageList();

	}

	/**
	 * Steps: Validate Engage ActivityData Expected: Verify that in
	 * EngagePageActivity all the elements are present(ProfileNames, Current
	 * stage of candidate, role, experience, last engaged, candidate icons and
	 * score of the candidate)
	 * 
	 * @throws Exception
	 */
	
	

	/**
	 * Steps: Validate Engage ActivityData Expected: Verify that in
	 * EngagePageActivity all the elements are present(ProfileNames, Current
	 * stage of candidate, role, experience, last engaged, candidate icons and
	 * score of the candidate)
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateCampaignsData", "Sanity" }, dataProvider = "validateElementsData")
	public void validateCampaignsData(SpireTestObject testObject) throws Exception {
		new HomePageUtil(loginPageUtil.driver, false);


		EngageCampaignPageUtil homeUtil = new EngageCampaignPageUtil(loginPageUtil.driver, true);

		homeUtil.validateCampaignsData();

	}

	@Test(groups = { "validateEmailTemplateData", "Sanity" }, dataProvider = "validateElementsData")
	public void validateEmailTemplateData(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		EngageEmailTemplatePageUtil engageEmailTemplatePageUtil = new EngageEmailTemplatePageUtil(loginPageUtil.driver, true);

		engageEmailTemplatePageUtil.engage_EmailTemplates();

	}

	@Test(groups = { "validateEngmtScoreRulesData", "Sanity" }, dataProvider = "validateElementsData")
	public void validateEngmtScoreRulesData(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);


		EngageScoreRulesPageUtil engageScoreRulesPageUtil = new EngageScoreRulesPageUtil(loginPageUtil.driver, true);

		engageScoreRulesPageUtil.engage_EngmtScoreRules();

	}

}

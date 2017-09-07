package com.spire.crm.campaign.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.EngageCampaignPageUtil;
import com.spire.crm.pageUtils.HomePageUtil;

/**
 * 
 * @author Manikanta
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CampaignUISanityTestPlan extends UITestPlan {
	@DataProvider(name = "CAMPAIGN_DP")
	public static Iterator<Object[]> getCampaignTestData(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/campaign/test/CampaignAutomationTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CampaignUISanityTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Login and verify Campaign List page elements like New btn,Search,
	 * 
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "validateListPageElements", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void validateListPageElements(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, true);

		campaignPageUtil.validateCampaignsData();

	}

	/**
	 * Login and verify Campaign List is displayed.
	 * 
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "campaignlistIsDisplayed", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void campaignlistIsDisplayed(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, true);

		campaignPageUtil.verifyCampaignsListElements();

	}
	
	/**
	 * Login and Clone a Campapign If records found...
	 * 
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifycloneCampaign", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void verifycloneCampaign(SpireTestObject testObject) {
		Logging.log("Test cases descriptuion is >>> " + testObject.getDescription());
		new HomePageUtil(loginPageUtil.driver, true);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, true);

		String cloneCampName= "UIAutomationClone"+UUID.randomUUID();
		campaignPageUtil.cloneCampaign(cloneCampName);

	}
	
	
	/**
	 * Login and view  Campapign If records found in list 
	 *  
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyviewCampaign", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void verifyviewCampaign(SpireTestObject testObject) {
		Logging.log("Test cases descriptuion is >>> " + testObject.getDescription());
		new HomePageUtil(loginPageUtil.driver, true);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, true);
		campaignPageUtil.viewCampaign();

	}
	
	
	
	
	
	

}

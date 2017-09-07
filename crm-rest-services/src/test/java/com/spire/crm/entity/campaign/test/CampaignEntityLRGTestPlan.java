package com.spire.crm.entity.campaign.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.DBHelper;
import com.spire.common.TestData;
import com.spire.crm.biz.campaign.test.CampaignServiceBizSanityTestPlan;

/**
 * @author Manikhanta Y
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CampaignEntityLRGTestPlan extends TestPlan {

	CampaignEntityLRGValidation campaignEntityLRGValidation = new CampaignEntityLRGValidation();

	@DataProvider(name = "CAMPAIGN_DP")
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/entity/campaign/test/CampaignServiceEntit_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CampaignServiceBizSanityTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}
	
	/**
	 * 
	 * Clean up any existing data 
	 */
	@AfterClass(alwaysRun = true)
	public void cleanUp(){
		
		DBHelper obj = new DBHelper();
		obj.deleteAutomationData("CAMPAIGNS", "NAME like \"SpireEntityCampaign%\"");
		obj.deleteAutomationData("TEMPLATE", "TEMPLATE_NAME like \"SpireEntityCampaign%\"");
		
	}
	
	/*
	 * 
	 * Mark campaign event as replied
	 */

	@Test(groups = { "updateEmailReadEvent_LRG", "LRG" }, dataProvider = "CAMPAIGN_DP")
	public void updateEmailReadEvent_LRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> markReplied_LRG !!!"
				+ data.getTestSteps());

		campaignEntityLRGValidation.verifyupdateEvent(testObject, data);
	}

	/*
	 * 
	 * updateFeedBackEvent
	 */

	@Test(groups = { "updateFeedBackEvent_LRG", "LRG" }, dataProvider = "CAMPAIGN_DP")
	public void updateFeedBackEvent_LRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> markReplied_LRG !!!"
				+ data.getTestSteps());

		campaignEntityLRGValidation.verifyupdateEvent(testObject, data);
	}

	/*
	 * 
	 * Mark campaign event as replied
	 */

	@Test(groups = { "updateFormSubmitEvent_LRG", "LRG" }, dataProvider = "CAMPAIGN_DP")
	public void updateFormSubmitEvent_LRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> markReplied_LRG !!!"
				+ data.getTestSteps());

		campaignEntityLRGValidation.verifyupdateEvent(testObject, data);
	}

}

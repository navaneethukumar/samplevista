package com.spire.crm.biz.campaign.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

/**
 * @author Santosh C
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CampaignServiceBizLRGTestPlan extends TestPlan {

	CampaignBizLRGValidation campaignServiceLRGValidation = new CampaignBizLRGValidation();

	@DataProvider(name = "CAMPAIGN_DP")
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/campaign/test/CampaignServiceBiz_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CampaignServiceBizLRGTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}



	/*
	 * 
	 * Mark campaign event as replied 
	 */

	@Test(groups = { "markReplied_LRG", "LRG" }, dataProvider = "CAMPAIGN_DP")
	public void markReplied_LRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> markReplied_LRG !!!"
				+ data.getTestSteps());

		campaignServiceLRGValidation.verifymarkReplied(testObject, data);
	}
	
	/*
	 * 
	 * Mark campaign event as replied 
	 */

	@Test(groups = { "markRead_LRG", "LRG" }, dataProvider = "CAMPAIGN_DP")
	public void markRead_LRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> markReplied_LRG !!!"
				+ data.getTestSteps());
		
		campaignServiceLRGValidation.verifymarkReplied(testObject, data);

	}
	
		
		
}

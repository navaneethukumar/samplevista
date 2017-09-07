package com.spire.crm.campaign_error_msg.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.EngageCampaignPageUtil;
import com.spire.crm.pageUtils.HomePageUtil;

@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CampErrorMsgTestPlan extends UITestPlan {

@DataProvider(name = "CampErrorMsgTestData")
public static Iterator<Object[]> getCandidateInfo(Method method) {

	Iterator<Object[]> objectsFromCsv = null;

	try {
		String fileName = "./src/test/java/com/spire/crm/campaign_error_msg/test/CampErrorMsgTestData.csv";
		LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
		LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
		methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
		entityClazzMap.put("SpireTestObject", SpireTestObject.class);
		
		objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(CampErrorMsgTestPlan.class, entityClazzMap, fileName, null,
				methodFilter);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return objectsFromCsv;
}
	@Test(groups = { "verifyMsgInHomePage" }, dataProvider = "CampErrorMsgTestData")
	public void verifyMsgInHomePage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, false);
		campaignPageUtil.verifyMsgInHomePage();
	}
	
	@Test(groups = { "VerifyMsgInSearchedResultPage" }, dataProvider = "CampErrorMsgTestData")
	public void VerifyMsgInSearchedResultPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, false);
		campaignPageUtil.VerifyMsgInSearchedResultPage();
	}
	@Test(groups = { "VerifyMsgInEngagedPage" }, dataProvider = "CampErrorMsgTestData")
	public void VerifyMsgInEngagedPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, false);
		campaignPageUtil.VerifyMsgInEngagedPage();
	}
	@Test(groups = { "VerifyMsgInProfilePage" }, dataProvider = "CampErrorMsgTestData")
	public void VerifyMsgInProfilePage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, true);
		EngageCampaignPageUtil campaignPageUtil = new EngageCampaignPageUtil(
				loginPageUtil.driver, false);
		campaignPageUtil.VerifyMsgInProfilePage();
	}
}

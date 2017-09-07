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
 * @author Manikanta Y
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CampaignServiceBizSanityTestPlan extends TestPlan {

	CampaignBizSanityValidation campaignServiceBizValidation = new CampaignBizSanityValidation();

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
					CampaignServiceBizSanityTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}

	@DataProvider(name = "LISTCAMPAIGN_DP")
	public static Iterator<Object[]> getListCampaignInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/campaign/test/CampaignServiceBiz_TestData.csv";
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

	/*
	 * 
	 * List Campaign Sanity
	 */

	@Test(groups = { "createCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void createCampaign_Sanity(SpireTestObject testObject, TestData data) {
		
		Logging.log("Test Case Execution started >>> createCampaign_Sanity !!!"
				+ data.getTestSteps());
		campaignServiceBizValidation.verifycreateCampaign(testObject, data);
		

	}

	@Test(groups = { "eidtCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void eidtCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> eidtCampaign_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.verifyeditCampaign(testObject, data);

	}

	/*
	 * 
	 * List Campaign Sanity
	 */

	@Test(groups = { "listCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void listCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> listCampaign_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.verifyListCampaign(testObject, data);

	}

	/*
	 * delete Campaign By giving single id
	 */

	@Test(groups = { "deleteCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void deleteCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> listCampaign_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.verifyDeleteCampaign(data);

	}

	/*
	 * delete bulk Campaign By giving array of ids
	 */

	@Test(groups = { "deleteBulkCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void deleteBulkCampaign_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> listCampaign_Sanity !!!"
				+ data.getTestSteps());

		Integer bulkcount = Integer.parseInt(testObject.getTestData());

		campaignServiceBizValidation.verifyDeleteBulkCampaign(data, bulkcount);

	}

	/*
	 * Get Campaign By id
	 */

	@Test(groups = { "getCampaignById_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void getCampaignById_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getCampaignById_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.verifygetCampaignByID(data, testObject);

	}

	/**
	 * Add campaign to No Stage candidate
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "addCamptoNoStage_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void addCamptoNoStage_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> addCamptoNoStage_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.addCampToNoStageCandidate(data,
				testObject, null, null);

	}

	/**
	 * Verify Remove Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "removeCampfromCandidate_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void removeCampfromCandidate_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> addCamptoNoStage_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.removeCampfromCandidat(data, testObject);

	}

	/**
	 * Verify Candidates added to the Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "campaignCandidates_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void campaignCandidates_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> campaignCandidates_Sanity !!!"
				+ data.getTestSteps());
		/*
		 * try { campaignServiceBizValidation.verifyCampaignCandidates(data,
		 * testObject); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); Assert.fail("Run time exception"); }
		 */

	}

	/**
	 * Verify the campaigns added to Candidates by giving the candidate ID 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "candidateCampaigns_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void candidateCampaigns_Sanity(SpireTestObject testObject,
			TestData data) {

		
		 Logging.log("Test Case Execution started >>> candidateCampaigns_Sanity !!!" + data.getTestSteps());
		  campaignServiceBizValidation.addCampToNoStageCandidate(data, testObject, null, null);
		 

	}

	/**
	 * Verify Clone Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "cloneCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void cloneCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> cloneCampaign_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.verifyCloneCampaign(data, testObject);

	}

		/**
	 * Verify Campaign Reports
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "campaignReports_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void campaignReports_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> campaignReports_Sanity !!!"
				+ data.getTestSteps());

		campaignServiceBizValidation.verifyCampaignReports(data, testObject);

	}
	
	
	/**
	 * Verify batch execution of campaign 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "batchexecution_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void batchexecution_Sanity(SpireTestObject testObject,
			TestData data) {
		
		campaignServiceBizValidation.verifyBatchExecution(data, testObject);
	}
	
}

package com.spire.crm.biz.campaign.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
@Test(groups = { "SRG" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CampaignServiceBizSRGTestPlan extends TestPlan {

	CampaignBizSRGValidation campaignBizSRGValidation = new CampaignBizSRGValidation();

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
					CampaignServiceBizSRGTestPlan.class, entityClazzMap,
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
					CampaignServiceBizSRGTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}

	/**
	 * Verify Remove Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "removeCampfromCandidate_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void removeCampfromCandidate_SRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> addCamptoNoStage_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.removeCampfromCandidat(data, testObject);

	}

	/**
	 * Verify Remove Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "changeStage_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void changeStage_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> addCamptoNoStage_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyChangeStage(data, testObject);

	}

	/**
	 * Verify Sent Activity is created
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "sentActivity_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void sentActivity_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> sentActivity_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifySentActivity(data, testObject);

	}

	/**
	 * Verify MultiLevel Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "readEmailDecisionCampaign_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void readEmailDecisionCampaign_SRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> readEmailDecisionCampaign_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyOneDecisionCampaign(data, testObject);

	}

	/**
	 * Verify MultiLevel Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "repliedEmailDecisionCampaign_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void repliedEmailDecisionCampaign_SRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> readEmailDecisionCampaign_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyOneDecisionCampaign(data, testObject);

	}

	/**
	 * Verify MultiLevel no decision of Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "ReadEmailExecution_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void ReadEmailExecution_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> readEmailDecisionCampaign_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyCampaignExecution(data, testObject);

	}

	/**
	 * Verify MultiLevel no decision of Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "repliedEmailExecution_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void repliedEmailExecution_SRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> readEmailDecisionCampaign_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyCampaignExecution(data, testObject);

	}

	/**
	 * Verify MultiLevel Yes decision of Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "readYesNodeExecution_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void readYesNodeExecution_SRG(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> readYesNodeExecution_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.decisionYesExecution(data, testObject);

	}

	/**
	 * Verify Candidates added to the Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "campaignCandidates_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void campaignCandidates_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> campaignCandidates_SRG !!!"
				+ data.getTestSteps());

		try {
			campaignBizSRGValidation.verifyCampaignCandidates(data, testObject);
		} catch (IOException e) {

			e.printStackTrace();
			Assert.fail("Run time exception");
		}

	}
	
	
	
	/**
	 * Verify  user is able to get the candidates at each node
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "candidatesAtNode_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void candidatesAtNode_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> campaignCandidates_SRG !!!"
				+ data.getTestSteps());

		try {
			campaignBizSRGValidation.verifyCampaignCandidates(data, testObject);
		} catch (IOException e) {

			e.printStackTrace();
			Assert.fail("Run time exception");
		}

	}
	
	
	
	
	/**
	 * Verify Clone Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "candidateCampaigns_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void candidateCampaigns_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> candidateCampaigns_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.addCampToNoStageCandidate(data, testObject,
				null, null);

	}

	/**
	 * Verify Campaigns under Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "cloneCampaign_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void cloneCampaign_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> cloneCampaign_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyCloneCampaign(data, testObject);

	}

	/**
	 * Verify Campaign Reports
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "campaignReports_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void campaignReports_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> campaignReports_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyCampaignReports(data, testObject);

	}

	/**
	 * Verify add candidate to Campaign (single call )
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "addcandidatetoCamp_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void addcandidatetoCamp_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> addcandidatetoCamp_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyaddLeadToCampaign(data, testObject);

	}

	/**
	 * Verify freeze Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "freezeCampaign_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void freezeCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> freezeCampaign_Sanity !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyFrezeCampaign(data, testObject);

	}

	/**
	 * Verify un freeze Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "unfreezeCampaign_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void unfreezeCampaign_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> freezeCampaign_Sanity !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.verifyFrezeCampaign(data, testObject);

	}
	
	/**
	 * Verify Get leads for campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getLeadsForCampaign_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void getLeadsForCampaign_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> candidateCampaigns_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.addCampToNoStageCandidate(data, testObject,
				null, null);

	}
	
	/**
	 * Verify Get leads for campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getExecutedNodes_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void getExecutedNodes_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> candidateCampaigns_SRG !!!"
				+ data.getTestSteps());

		campaignBizSRGValidation.addCampToNoStageCandidate(data, testObject,
				null, null);

	}
	
	
	/**
	 * Verify campaign change stage is created by added user 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "changeStageUserName_SRG", "SRG" }, dataProvider = "CAMPAIGN_DP")
	public void changeStageUserName_SRG(SpireTestObject testObject, TestData data) {
		Logging.log("Test Case Execution started >>> changeStageUserName_SRG !!!"
				+ data.getTestSteps());		
		CampaignBizSRGValidation helper = new CampaignBizSRGValidation();		
		try {
			helper.changeStageUserName(data, testObject);	
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run time exception");
		}
	}
	
	
	
	
	

	@AfterClass(alwaysRun = true)
	public void validateAndClearDB() {
		Logging.log(" start of clear DB and validation");
		// campaignBizSRGValidation.executeCampaignEvents();
		// campaignBizSRGValidation.validateStageNames();
		campaignBizSRGValidation.deleteCampaigns();
		campaignBizSRGValidation.deleteTemplate();

	}

}

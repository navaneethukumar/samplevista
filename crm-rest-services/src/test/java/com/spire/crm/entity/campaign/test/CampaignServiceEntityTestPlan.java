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
public class CampaignServiceEntityTestPlan extends TestPlan {

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
	
	/**
	 * createLabel_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void listCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> listCampaign_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		
		campaignServiceEntitValidation.verifyListCampaign(data);

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
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation
				.verifyDeleteBulkCampaign(data, bulkcount);

	}

	/*
	 * Get Campaign By id
	 */

	@Test(groups = { "getCampaignById_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void getCampaignById_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getCampaignById_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifygetCampaignByID(data, testObject);

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
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.removeCampfromCandidat(data, testObject);

	}

	/**
	 * Verify Get schedule events
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "sheduleEvents_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void sheduleEvents_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> sheduleEvents_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifyScheduledEvents(data, testObject);

	}
	
	/**
	 * Verify Get get Leads For Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getLeadsForCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void getLeadsForCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> sheduleEvents_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifygetLeadsForCampaign(data, testObject);

	}
	

	/**
	 * Verify Edit Campaign
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "editCampaign_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void editCampaign_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> editCampaign_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifyEditCampaign(data, testObject);

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
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifyCloneCampaign(data, testObject);

	}
	
	/*
	 * Get bulk Campaign By id
	 */

	@Test(groups = { "getBulkCampaignById_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void getBulkCampaignById_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getBulkCampaignById_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifygetBulkCampaignByID(data, testObject);

	}
		
	
	/*
	 * Get bulk Campaign By id
	 */

	@Test(groups = { "getCampaignForLead_Sanity", "Sanity" }, dataProvider = "CAMPAIGN_DP")
	public void getCampaignForLead_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getCampaignForLead_Sanity !!!"
				+ data.getTestSteps());
		CampaignServiceEntitValidation campaignServiceEntitValidation = new CampaignServiceEntitValidation();
		campaignServiceEntitValidation.verifygetCampaignForLead(data, testObject);

	}
	
	
	
}

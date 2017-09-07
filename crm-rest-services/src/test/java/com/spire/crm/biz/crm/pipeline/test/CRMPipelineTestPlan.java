package com.spire.crm.biz.crm.pipeline.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

import crm.pipeline.beans.CRMFilter;
import crm.pipeline.beans.CreateCRM;



@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CRMPipelineTestPlan extends TestPlan {

	@DataProvider(name = "pipelineTestData")
	public static Iterator<Object[]> getpipelineInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/pipeline/test/CRMPipelineTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("CreateCRM", CreateCRM.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CRMPipelineTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}
	
	
	@DataProvider(name = "FILTER_DP")
	public static Iterator<Object[]> getFilterInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/pipeline/test/ListProfilesByFiltersTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("CRMFilter", CRMFilter.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CRMPipelineTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	/**
	 * List Data Ranges service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listDateRanges_SRG", "SRG" }, dataProvider = "pipelineTestData")
	public void listDateRanges_SRG(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CRMPipelineValidation.verifyListDateRanges();

	}

	/**
	 * get Rating Config Info
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "getRatingConfigInfo_SRG", "SRG" }, dataProvider = "pipelineTestData")
	public void getRatingConfigInfo_SRG(SpireTestObject testObject,
			TestData data, CreateCRM crmProfile) {

		try {
			CRMPipelineValidation.verifygetRatingConfigInfo();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create Pipeline Profile
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "createProfile_Sanity", "Sanity" }, dataProvider = "pipelineTestData")
	public void createProfile_Sanity(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {

		try {
			CRMPipelineValidation.verifyCreateProfile(crmProfile);
		} catch (InterruptedException e) {
			Assert.fail("Run time Exception");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Create Pipeline Profile
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "createProfile_SRG", "Sanity" }, dataProvider = "pipelineTestData")
	public void createProfile_SRG(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {

		try {
			CRMPipelineValidation.verifyCreateProfile(crmProfile);
		} catch (InterruptedException e) {
			Assert.fail("Run time Exception"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Update Pipeline Profile
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "updateProfile_Sanity", "Sanity" }, dataProvider = "pipelineTestData")
	public void updateProfile_Sanity(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {

		try {
			CRMPipelineValidation.verifyUpdateProfile(crmProfile, testObject,data);
		} catch (InterruptedException e) {
			Assert.fail("Run time Exception");
			e.printStackTrace();
		}
	}

	/**
	 * Update Pipeline Profile
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "updateStageName_Sanity", "Sanity" }, dataProvider = "pipelineTestData")
	public void updateStageName_Sanity(SpireTestObject testObject,
			TestData data, CreateCRM crmProfile) {

		try {
		
			CRMPipelineValidation.verifyUpdateProfile(crmProfile, testObject,data);
		} catch (InterruptedException e) {
			Assert.fail("Run time Exception");
			e.printStackTrace();
		}
	}
	
	/**
	 * Update Pipeline Profile
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "updateStageName_LRG", "LRG" }, dataProvider = "pipelineTestData")
	public void updateStageName_LRG(SpireTestObject testObject,
			TestData data, CreateCRM crmProfile) {

		try {
		
			CRMPipelineValidation.verifyUpdateProfile(crmProfile, testObject,data);
		} catch (InterruptedException e) {
			Assert.fail("Run time Exception");
			e.printStackTrace();
		}
	}
	/**
	 * get Pipeline Profile by Date Range
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "listStages_Sanity", "Sanity" }, dataProvider = "pipelineTestData")
	public void listStages_Sanity(SpireTestObject testObject,
			TestData data, CreateCRM crmProfile) {

		CRMPipelineValidation.verifyPipelineList(crmProfile, testObject);
	}
	
	/**
	 * POST /pipeline/_bulkCreateCRMProfiles
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "bulkCreateProfile_Sanity", "Sanity" }, dataProvider = "pipelineTestData")
	public void bulkCreateProfile_Sanity(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {

		try {
			CRMPipelineValidation.verifyBulkCreateProfile(crmProfile,data);
		} catch (InterruptedException e) {
			Assert.fail("Run time Exception");
			e.printStackTrace();
		}
	}
	
	/**
	 * GET /pipeline/_list
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "getStageCount_Sanity", "Sanity" }, dataProvider = "pipelineTestData")
	public void getStageCount_Sanity(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {

		
			try {
				CRMPipelineValidation.verifygetStageCount(crmProfile,data);
			} catch (IOException e) {
				Assert.fail("Run Time exception "+e.getMessage());
				e.printStackTrace();
			}
		
	}
	
	/**
	 * GET /pipeline/_list
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "getProfileByIDS_SRG", "SRG" }, dataProvider = "pipelineTestData")
	public void getProfileByIDS_SRG(SpireTestObject testObject, TestData data,
			CreateCRM crmProfile) {
		
			CRMPipelineValidation.verifygetProfilesByIDs(crmProfile,data);
		
	}
	
	/**
	 * GET /pipeline/_list
	 * 
	 * @param testObject
	 * @param data
	 * @param crmProfile
	 */
	@Test(groups = { "ListProfilesByfiltersTest_Sanity", "Sanity" }, dataProvider = "FILTER_DP")
	public void ListProfilesByfiltersTest_Sanity(SpireTestObject testObject, TestData data,
			CRMFilter crmFilter) {
			CRMPipelineValidation.verifyPipelineFilter(crmFilter,data);
		
	}
	
	

}
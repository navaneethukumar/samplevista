package com.spire.crm.biz.crm.profile.test;

/**
 * @author Manikanta.Y
 */

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.core.Response;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.crm.profiles.entity.EngagementProfile;
import spire.crm.profiles.entity.Filter;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;

@Test(retryAnalyzer = TestRetryAnalyzer.class)
public class CRMProfilesTestPlan extends CRMTestPlan {

	@DataProvider(name = "ProfilesTestData")
	public static Iterator<Object[]> getProfileInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/profile/test/CRMProfilesTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("EngagementProfile", EngagementProfile.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CRMProfilesTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	@DataProvider(name = "profileFilter")
	public static Iterator<Object[]> getFilterInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/profile/test/CRMProfilesFiltersTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("Filter", Filter.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CRMProfilesTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "createCRMProfile_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void createCRMProfile_Sanity(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_Create(request);
	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "createCRMProfile_LRG", "LRG" }, dataProvider = "ProfilesTestData")
	public void createCRMProfile_LRG(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_Create(request);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "createCRMProfile_SRG", "SRG" }, dataProvider = "ProfilesTestData")
	public void createCRMProfile_SRG(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_Create(request);

	}
	
	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "createCRMProfileBulk_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void createCRMProfileBulk_Sanity(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_CreateBulk(request);
	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "listEngagementProfilesNoCommunication", "Sanity" }, dataProvider = "ProfilesTestData")
	public void listEngagementProfilesNoCommunication(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		Response response=CRMProfileValidation.validate_Create(request);
		String crmId=response.readEntity(String.class);
		CRMProfileValidation.validateNoBroadcastCommunication(crmId);
	}
	
	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "updateProfile_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void updateProfile_Sanity(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_Update(request);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "listByCandidateIDs_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void listByCandidateIDs_Sanity(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_listByCandidateIDs(request);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "listByCandidateIDs_SRG", "SRG" }, dataProvider = "ProfilesTestData")
	public void listByCandidateIDs_SRG(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_listByCandidateIDs(request);

	}

	/**
	 * Geting count of canidates in crm Candidate ID field
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "listByCandidateIDs_LRG", "LRG" }, dataProvider = "ProfilesTestData")
	public void listByCandidateIDs_LRG(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);

		List<String> candidateIds = new ArrayList<String>();

		for (int i = 0; i < Integer.parseInt(request.getCrmCandidateId()); i++) {

			candidateIds.add("test" + i);
		}

		CRMProfileValidation.verify_listByCandidateIDs(candidateIds);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "listEngagementTrend_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void listEngagementTrend_Sanity(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_listEngagementTrend(request);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "listEngagementTrend_LRG", "LRG" }, dataProvider = "ProfilesTestData")
	public void listEngagementTrend_LRG(SpireTestObject testObject,
			TestData data, EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.verify_Trend(request);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "getProfileByID_LRG", "LRG" }, dataProvider = "ProfilesTestData")
	public void getProfileByID_LRG(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.validate_getProfileByID(request);

	}

	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "profileFilter_Sanity", "Sanity" }, dataProvider = "profileFilter")
	public void profileFilter_Sanity(SpireTestObject testObject, TestData data,
			Filter requestFilter) {
		Logging.log(data.TestSteps);
		CRMProfileValidation.setUpFilter(testObject,data, requestFilter);
		CRMProfileValidation.verifyFilter(requestFilter);

	}
	
	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "profileFilter_SRG", "SRG" }, dataProvider = "profileFilter")
	public void profileFilter_SRG(SpireTestObject testObject, TestData data,
			Filter requestFilter) {
		Logging.log(data.TestSteps);
		requestFilter = CRMProfileValidation.setUpFilter(testObject,data, requestFilter);
		CRMProfileValidation.verifyFilter(requestFilter);

	}
	
	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "reportsSmokeTest_Sanity", "SRG" }, dataProvider = "ProfilesTestData")
	public void reportsSmokeTest_Sanity(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log("<<<< Test steps>>>> "+data.TestSteps);
		CRMProfileValidation.verifycrmReportsResponseStatus(testObject,data);

	}
	
	
	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "downloadCRMReport_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void downloadCRMReport_Sanity(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log("<<<< Test steps>>>> "+data.TestSteps);
		CRMProfileValidation.verifyDownloadReports(testObject,data);

	}
	
	/**
	 * 
	 * @param testObject
	 * @param data
	 * @param request
	 */
	@Test(groups = { "downloadlabelReport_Sanity", "Sanity" }, dataProvider = "ProfilesTestData")
	public void downloadlabelReport_Sanity(SpireTestObject testObject, TestData data,
			EngagementProfile request) {
		Logging.log("<<<< Test steps>>>> "+data.TestSteps);
		CRMProfileValidation.verifyDownloadReports(testObject,data);

	}
	
	
	
	

}

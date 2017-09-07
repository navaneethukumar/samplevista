package com.spire.crm.entity.social.test;

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
import com.spire.crm.restful.entity.consumers.SocialEntityServiceConsumer;

/**
 * @author Santosh C
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class SocialServiceEntityTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;
	SocialEntityServiceConsumer socialServiceEntityConsumer = null;

	final static String DATAPROVIDER_NAME = "SOCIAL";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/entity/social/test/";
	final static String CSV_FILENAME = "SocialServiceEntity_TestData.csv";
	final static String CSV_PATH = CSV_DIR + CSV_FILENAME;

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = CSV_PATH;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(SocialServiceEntityTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Create Social profile
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createSocialProfile", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createSocialProfile(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> createSocialProfile !!!");

		SocialServiceValidation.createSocialProfile(data);

	}

	/**
	 * Create Basic Social profile[By giving few fields]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createBasicSocialProfile", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createBasicSocialProfile(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> createBasicSocialProfile !!!");

		SocialServiceValidation.createBasicSocialProfile(data);

	}

	/**
	 * Create MiniSocialProfile[By giving single field]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createMiniSocialProfile", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createMiniSocialProfile(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> createMiniSocialProfile !!!");

		SocialServiceValidation.createSocialProfile(data);

	}

	/**
	 * GetSocialProfile by giving candidateId
	 * 
	 * Validate projectionTypes[full, basic, intermediate]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getSocialProfile", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void getSocialProfile(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getSocialProfile !!!");

		SocialServiceValidation.getSocialProfile(data);

	}

	/**
	 * SocialService LRG tests[Try to create duplicate social profile to same
	 * candidateId,, GetByInvalid candidateId]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "socialProfile_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void socialProfile_LRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getSocialProfile !!!");

		SocialServiceValidation.socialProfile_LRG(data);

	}

	/**
	 * Create BulkSocialProfiles
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createBulkSocialProfiles", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createBulkSocialProfiles(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> createBulkSocialProfiles !!!");

		SocialServiceValidation.createBulkSocialProfiles(data);

	}

	/**
	 * Get BulkSocialProfiles
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getBulkSocialProfiles", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getBulkSocialProfiles(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> getBulkSocialProfiles !!!");

		SocialServiceValidation.createBulkSocialProfiles(data);
	}

	/**
	 * Create Social profile and Patch
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "patchSocialProfile", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void patchSocialProfile(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> patchSocialProfile !!!");

		SocialServiceValidation.patchSocialProfile(data);
	}
}

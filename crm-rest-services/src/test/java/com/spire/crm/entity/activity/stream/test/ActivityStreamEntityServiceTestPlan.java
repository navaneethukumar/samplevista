package com.spire.crm.entity.activity.stream.test;

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
import com.spire.crm.restful.entity.consumers.ActivityStreamEntityServiceConsumer;

import spire.commons.activitystream.Activity;

/**
 * @author Santosh C
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ActivityStreamEntityServiceTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;
	ActivityStreamEntityServiceConsumer activityStreamEntityServiceConsumer = null;

	final static String DATAPROVIDER_NAME = "ACTIVITY-STREAM_ENTITY";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/entity/activity/stream/test/";
	final static String CSV_FILENAME = "ActivityStreamEntityService_TestData.csv";
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
			entityClazzMap.put("Activity", Activity.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ActivityStreamEntityServiceTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * createActivity_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createActivity_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createActivity_Sanity(SpireTestObject testObject, TestData data, Activity activityRequest) {

		Logging.log("Test Case Execution started >>> createActivity_Sanity !!!");
		ActivityStreamEntityServiceValidation.createActivityTest(data, activityRequest);
	}

	/**
	 * createActivity_SRG
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createActivity_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createActivity_SRG(SpireTestObject testObject, TestData data, Activity activityRequest) {

		Logging.log("Test Case Execution started >>> createActivity_SRG !!!");
		ActivityStreamEntityServiceValidation.createActivityTest(data, activityRequest);
	}

	/**
	 * createActivity_LRG
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createActivity_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createActivity_LRG(SpireTestObject testObject, TestData data, Activity activityRequest) {

		Logging.log("Test Case Execution started >>> createActivity_LRG !!!");
		ActivityStreamEntityServiceValidation.createActivityTest(data, activityRequest);
	}

	/**
	 * createCustomActivity_SRG
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createCustomActivity_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createCustomActivity_SRG(SpireTestObject testObject, TestData data, Activity activityRequest) {

		Logging.log("Test Case Execution started >>> createCustomActivity_SRG !!!");
		ActivityStreamEntityServiceValidation.createCustomActivityTest(data, activityRequest);
	}

	/**
	 * listActivity_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listActivity_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void listActivity_Sanity(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> listActivity_Sanity !!!");
		ActivityStreamEntityServiceValidation.createActivity_List(data, act);

	}

	/**
	 * listActivitySearchText_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listActivitySearchText_Sanity", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void listActivitySearchText_Sanity(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> listActivitySearchText_Sanity !!!");
		ActivityStreamEntityServiceValidation.list_SearchText(data, act);

	}

	/**
	 * listActivitySearchText_SRG
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listActivitySearchText_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void listActivitySearchText_SRG(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> listActivitySearchText_SRG !!!");
		ActivityStreamEntityServiceValidation.list_SearchText(data, act);

	}

	/**
	 * createBulkActivity_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createBulkActivity_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createBulkActivity_Sanity(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> createBulkActivity_Sanity !!!");
		ActivityStreamEntityServiceValidation.createBulkActivity(data, act);

	}

	/**
	 * invalidSearch_SRG
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "invalidSearch_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void invalidSearch_SRG(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> invalidSearch_SRG !!!");
		ActivityStreamEntityServiceValidation.invalidSearch(data, act);
	}

	/**
	 * createActivity_Meta_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createActivity_Meta_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createActivity_Meta_Sanity(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> createActivity_Meta_Sanity !!!");
		ActivityStreamEntityServiceValidation.createActivity_Meta(data, act);
	}

	/**
	 * listActivitiesByMeta_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listActivitiesByMeta_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void listActivitiesByMeta_Sanity(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> listActivitiesByMeta_Sanity !!!");
		ActivityStreamEntityServiceValidation.listActivitiesByMeta(data, act);
	}

	/**
	 * updateAnActivity_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateAnActivity_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void updateAnActivity_Sanity(SpireTestObject testObject, TestData data, Activity act) {

		Logging.log("Test Case Execution started >>> updateAnActivity_Sanity !!!");
		ActivityStreamEntityServiceValidation.validateUpdateActivity(data, act);
	}

}
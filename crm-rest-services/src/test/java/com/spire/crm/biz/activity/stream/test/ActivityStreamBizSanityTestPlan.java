package com.spire.crm.biz.activity.stream.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;

import crm.activitystream.beans.ActivitySearchFilter;
import crm.activitystream.beans.CRMCreateActivity;
import crm.activitystream.beans.CRMHomeActivities;
import spire.commons.config.entities.SpireConfiguration;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ActivityStreamBizSanityTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;
	ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = null;
	private static Logger logger = LoggerFactory.getLogger(ActivityStreamBizSanityTestPlan.class);
	SpireConfiguration spireConfiguration = null;
	String id = null;
	final static String DATAPROVIDER_NAME = "ACTIVITY_STREAM";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/biz/activity/stream/test/";
	final static String CSV_FILENAME = "ActivityStreamBizService_TestData.csv";
	final static String CSV_PATH = CSV_DIR + CSV_FILENAME;

	String candidateId = null;

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = CSV_PATH;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("data", TestData.class);
			entityClazzMap.put("CRMCreateActivity", CRMCreateActivity.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ActivityStreamBizSanityTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Passing endPointURL and Service name from testng.xml
	 * 
	 * @param endPointURL
	 * @param candidateId
	 */
	@BeforeClass(alwaysRun = true)
	public void setUp() {
		activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();
	}

	/**
	 * Creating service
	 * 
	 * @param testObject
	 * @param data
	 */

	@Test(groups = { "verify_listTimePeriod", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_listTimePeriod(SpireTestObject testObject, TestData data, CRMCreateActivity crmCreateActivity) {
		logger.info("Test Case Execution started >>> verifyAddNewService !!!");
		List<String> timePeriods = null;
		try {
			timePeriods = activityStreamBizServiceConsumer._listTimePeriod();
			if (timePeriods == null) {
				throw new RuntimeException("createActivityInfo is null ");
			}
			ActivityStreamBizWebServiceValidation.validate_listTimePeriod(timePeriods);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verify_listTimePeriod -> failed", e);
		}
	}

	@Test(groups = { "verify_createActivity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_createActivity(SpireTestObject testObject, TestData data, CRMCreateActivity crmCreateActivity) {
		logger.info("Test Case Execution started >>> verify_createActivity !!!");
		try {
			CRMCreateActivity createActivity = new CRMCreateActivity();
			// get the candidate id by createProfile call
			String CANDIDATE_ID = ProfileHelper.createProfile();
			createActivity.setCandidateId(CANDIDATE_ID);
			createActivity.setActivityTypeInfo(crmCreateActivity.getActivityTypeInfo());
			createActivity.setBenifitLevel(crmCreateActivity.getBenifitLevel());
			createActivity.setCompanyLevel(crmCreateActivity.getCompanyLevel());
			createActivity.setInterestLevel(crmCreateActivity.getInterestLevel());
			createActivity.setFitmentLevel(crmCreateActivity.getFitmentLevel());
			createActivity.setNotes("_createActivity_Automation");
			if (activityStreamBizServiceConsumer._createActivity(createActivity) == null) {
				throw new RuntimeException("verify_createActivity is null ");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verify_createActivity -> failed", e);
		}
	}

	@Test(groups = { "verify_homeactivities", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_homeactivities(SpireTestObject testObject, TestData data, CRMCreateActivity crmCreateActivity) {

		ActivitySearchFilter activitySearchFilter = new ActivitySearchFilter();
		activitySearchFilter.setTimePeriod("null");

		CRMHomeActivities activityFilterResponse = activityStreamBizServiceConsumer
				._homeactivities(activitySearchFilter, "0", "50");

		Gson gson = new Gson();

		Logging.log("HOME: " + activityFilterResponse);
		Logging.log("PESPONSE: " + gson.toJson(activityFilterResponse));

	}
}
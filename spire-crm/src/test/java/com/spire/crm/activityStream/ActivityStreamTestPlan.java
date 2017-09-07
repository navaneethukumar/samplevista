package com.spire.crm.activityStream;

/**
 * @author Santosh C
 */

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
import com.spire.common.ProfileHelper;
import com.spire.common.UITestPlan;
import com.spire.crm.activitytream.ValidateActivityStream;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;

@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ActivityStreamTestPlan extends UITestPlan {

	// public String candidateId = "6002:6005:dc5c82f175b44a6fa4c3787608520562";
	public String candidateId = ProfileHelper.createProfile();

	@DataProvider(name = "ActivityStreamTests")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/regression/ActivityStreamTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ActivityStreamTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "validateActivityData", "SRG" }, dataProvider = "ActivityStreamTests")
	public void validateActivityData(SpireTestObject testObject) throws Exception {
		new HomePageUtil(loginPageUtil.driver, false);

		ValidateActivityStream activityStream = new ValidateActivityStream(loginPageUtil.driver, false);
		activityStream.validateActivityStream(candidateId);
	}

	@Test(groups = { "validateCreatedActivity", "Sanity" }, dataProvider = "ActivityStreamTests")
	public void validateCreatedActivity(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		ValidateActivityStream activityStream = new ValidateActivityStream(loginPageUtil.driver, false);
		String testData[] = testObject.getTestData().split(",");
		activityStream.createActivity(candidateId, testData[0], Integer.parseInt(testData[1]));
		activityStream.validateCreatedActivity(testData[0]);
	}

	@Test(groups = { "filterActivities_CandidateDetailsPage", "Sanity" }, dataProvider = "ActivityStreamTests")
	public void filterActivities_CandidateDetailsPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		ValidateActivityStream activityStream = new ValidateActivityStream(loginPageUtil.driver, false);
		activityStream.createActivity(candidateId, "Video call", 5);
		this.driver.findElement(activityStream.homeTab).click();
		activityStream.createActivity(candidateId, "Instance Message", 5);
		this.driver.findElement(activityStream.homeTab).click();
		activityStream.createActivity(candidateId, "Notes", 0);
		this.driver.findElement(activityStream.homeTab).click();
		activityStream.filterCandidatePageActivities(candidateId, testObject.getTestData());
	}

	@Test(groups = { "filterActivities_HomePage", "Sanity" }, dataProvider = "ActivityStreamTests")
	public void filterActivities_HomePage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		ValidateActivityStream activityStream = new ValidateActivityStream(loginPageUtil.driver, false);
		activityStream.filterHomePageActivities(testObject.getTestData());
	}

	@Test(groups = { "textSearchInActivities", "Sanity" }, dataProvider = "ActivityStreamTests")
	public void textSearchInActivities(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		ValidateActivityStream activityStream = new ValidateActivityStream(loginPageUtil.driver, false);
		activityStream.searchTextInActivities("call");
	}
}

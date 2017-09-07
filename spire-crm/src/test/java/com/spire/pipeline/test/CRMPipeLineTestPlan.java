package com.spire.pipeline.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.pipeline.ValidatePipeLineDataUtil;

/**
 * 
 * @author Sangeeta
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CRMPipeLineTestPlan extends UITestPlan {
	@DataProvider(name = "validateElementsData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/pipeline/test/ValidateElementsTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CRMPipeLineTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Login and verify CRM_PipeLine elements like lead,engaged,applicant and
	 * graph button.
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "validateElementPresent" }, dataProvider = "validateElementsData")
	public void validateElementPresent(SpireTestObject testObject)
			throws Exception {

		new HomePageUtil(loginPageUtil.driver, true);
		ValidatePipeLineDataUtil PipeLineUtil = new ValidatePipeLineDataUtil(
				loginPageUtil.driver, false);

		PipeLineUtil.validateElementPresent();

	}

	/**
	 * Login and verify only lead candidates are displaying or not and count is
	 * matching or not.
	 * 
	 * @param testObject
	 */

	@Test(groups = { "validateLeadCandidates" }, dataProvider = "validateElementsData")
	public void validateLeadCandidates(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);

		ValidatePipeLineDataUtil PipeLineUtil = new ValidatePipeLineDataUtil(
				loginPageUtil.driver, false);

		PipeLineUtil.validateLeadCandidates();

	}

	/**
	 * Login and verify only engaged candidates are displaying or not and count
	 * is matching or not.
	 * 
	 * @param testObject
	 */
	@Test(groups = { "validateEngageCandidates" }, dataProvider = "validateElementsData")
	public void validateEngageCandidates(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);
		ValidatePipeLineDataUtil PipeLineUtil = new ValidatePipeLineDataUtil(
				loginPageUtil.driver, false);

		PipeLineUtil.validateEngageCandidates();

	}

	/**
	 * Login and verify only applicant candidates are displaying or not and
	 * count is matching or not.
	 * 
	 * @param testObject
	 */

	@Test(groups = { "validateApplicantCandidates" }, dataProvider = "validateElementsData")
	public void validateApplicantCandidates(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);

		ValidatePipeLineDataUtil PipeLineUtil = new ValidatePipeLineDataUtil(
				loginPageUtil.driver, false);

		PipeLineUtil.validateApplicantCandidates();

	}

	/**
	 * Login and verify only applicant candidates are displaying or not and
	 * count is matching or not.
	 * 
	 * @param testObject
	 */

	@Test(groups = { "validateHoldCandidates" }, dataProvider = "validateElementsData")
	public void validateHoldCandidates(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);

		ValidatePipeLineDataUtil PipeLineUtil = new ValidatePipeLineDataUtil(
				loginPageUtil.driver, false);

		PipeLineUtil.validateHoldCandidates();

	}

	/**
	 * Login and verify only rejected candidates are displaying or not and count
	 * is matching or not.
	 * 
	 * @param testObject
	 */

	/*
	 * @Test(groups = { "validateRejectedCandidates" }, dataProvider =
	 * "validateElementsData") public void
	 * validateRejectedCandidates(SpireTestObject testObject) {
	 * 
	 * new HomePageUtil(loginPageUtil.driver, true); ValidatePipeLineDataUtil
	 * PipeLineUtil = new ValidatePipeLineDataUtil(loginPageUtil.driver, false);
	 * 
	 * PipeLineUtil.validateRejectedCandidates();
	 * 
	 * }
	 */
	/**
	 * Login and verify candidates created on that date are displaying or not
	 * and count is matching or not.
	 * 
	 * @param testObject
	 */
	/*
	 * @Test(groups = { "validateDateRangeFilter"}, dataProvider =
	 * "validateElementsData") public void
	 * validateDateRangeFilter(SpireTestObject testObject) {
	 * 
	 * LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);
	 * 
	 * initializeDriver(loginPageUtil.driver, testObject);
	 * 
	 * loginPageUtil.login();
	 * 
	 * ValidatePipeLineDataUtil PipeLineUtil = new
	 * ValidatePipeLineDataUtil(this.driver, false);
	 * 
	 * PipeLineUtil.validateDateRangeFilter();
	 * 
	 * }
	 */
	/**
	 * Login and verify minimize Button "+" is working or not.
	 * 
	 * @param testObject
	 */
	@Test(groups = { "validateMinimizeStageBtn" }, dataProvider = "validateElementsData")
	public void validateMinimizeStageBtn(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, true);
		ValidatePipeLineDataUtil PipeLineUtil = new ValidatePipeLineDataUtil(
				loginPageUtil.driver, false);

		PipeLineUtil.validateMinimizeStageBtn();

	}

}

package com.spire.crm.entity.cache.lib.test;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.commons.data.exceptions.DataServiceException;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CacheLibjarTestPlan extends TestPlan {

	CacheLibjaHelper helper = new CacheLibjaHelper();

	@DataProvider(name = "CACHELIB_DP")
	public static Iterator<Object[]> getCachelibInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/entity/cache/lib/test/CacheLibjaTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CacheLibjarTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	@BeforeClass(alwaysRun = true)
	public void setupConnections() {

		helper.getConections();
	}

	@AfterClass
	public void closeConnections() {

		helper.closeConections();
	}

	/**
	 * Verify user is able to fetch one candidate
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyFetchCandidate_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyFetchCandidate_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());

		try {
			helper.validateFetch(testObject, data);
		} catch (DataServiceException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify user is able to fetch one candidate ,when redis is down
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyFetchWhenRedisDown_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyFetchWhenRedisDown_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());
		try {
			helper.verifyFetchWhenRedisDown(testObject, data);
		} catch (DataServiceException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify user is able to fetch one candidate ,when redis is down
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyRequisitionFetch_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyRequisitionFetch_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());
		try {
			helper.verifyRequisitionFetch(testObject, data);
		} catch (DataServiceException | SQLException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify user is able to fetch one candidate ,when redis is down
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyFlushAndFetch_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyFlushAndFetch_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());
		try {
			helper.verifyFlushFetch(testObject, data);
		} catch (DataServiceException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify user is able to fetch one candidate
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyFetchMultipleCandidate_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyFetchMultipleCandidate_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());

		try {
			helper.validateMultipleFetch(testObject, data);
		} catch (DataServiceException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify user is able to fetch one candidate
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyPartialFetch_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyPartialFetch_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());

		try {
			helper.validateMultipleFetch(testObject, data);
		} catch (DataServiceException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify getting 1000 candidates fetch
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyBulkFetchCandidate_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyBulkFetchCandidate_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());

		try {
			helper.validateBulkFetch(testObject, data);
		} catch (DataServiceException | InterruptedException | SQLException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

	/**
	 * Verify getting 1000 candidates fetch
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyFetchInvalidCandidate_Sanity", "Sanity" }, dataProvider = "CACHELIB_DP")
	public void verifyFetchInvalidCandidate_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Steps >>" + data.getTestSteps());

		try {
			helper.validateInvalidFetch(testObject, data);
		} catch (DataServiceException | InterruptedException e) {
			e.printStackTrace();
			Assert.fail("Run Time Excpetion");
		}

	}

}

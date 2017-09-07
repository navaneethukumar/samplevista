package com.spire.crm.biz.crm.rule.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

import spire.crm.biz.rules.entites.WeightageRuleEntity;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CrmWeightTestPlan extends TestPlan {

	@DataProvider(name = "CRMWEIGHT_DP")
	public static Iterator<Object[]> getblobStoreInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/rule/test/CrmWeightTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap
					.put("WeightageRuleEntity", WeightageRuleEntity.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CrmWeightTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}
	/**
	 * @param testObject
	 * @param data
	 */
	@BeforeClass(alwaysRun = true)
	public static void beforeClass() {
		CrmWeightValidation.DeleteWeight();

	}

	/**
	 * Verify Create and Delete Weight
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createDeleteWeight_Sanity", "Sanity" }, dataProvider = "CRMWEIGHT_DP")
	public void createDeleteWeight_Sanity(SpireTestObject testObject,
			TestData data, WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyCreateWeight(weight, testObject);

	}

	/**
	 * Verify Create Weight .Negative scenarios
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createWeight_SRG", "Sanity" }, dataProvider = "CRMWEIGHT_DP")
	public void createWeight_SRG(SpireTestObject testObject, TestData data,
			WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyCreateWeight(weight, testObject);

	}

	/**
	 * Verify Creating a duplicate rule
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDuplicateWeight_LRG", "LRG" }, dataProvider = "CRMWEIGHT_DP")
	public void verifyDuplicateWeight_LRG(SpireTestObject testObject,
			TestData data, WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyCreateWeight(weight, testObject);

	}

	/**
	 * Verify Create and Delete
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifygetAllWeight_SRG", "SRG" }, dataProvider = "CRMWEIGHT_DP")
	public void verifygetAllWeight_SRG(SpireTestObject testObject,
			TestData data, WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyCreateWeight(weight, testObject);

	}

	/**
	 * Verify Update Weight
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateWeight_Sanity", "Sanity" }, dataProvider = "CRMWEIGHT_DP")
	public void updateWeight_Sanity(SpireTestObject testObject, TestData data,
			WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyCreateWeight(weight, testObject);

	}

	/**
	 * Verify Update Weight
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateWeight_SRG", "SRG" }, dataProvider = "CRMWEIGHT_DP")
	public void updateWeight_SRG(SpireTestObject testObject, TestData data,
			WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyUpdateWeight(weight, testObject);

	}

	/**
	 * get Weight
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getWeight_SRG", "SRG" }, dataProvider = "CRMWEIGHT_DP")
	public void getWeight_SRG(SpireTestObject testObject, TestData data,
			WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmWeightValidation.verifyWeightById(weight, testObject);

	}

	/**
	 * Verify Delete Weight
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "deleteWeight_LRG", "LRG" }, dataProvider = "CRMWEIGHT_DP")
	public void deleteWeight_LRG(SpireTestObject testObject, TestData data,
			WeightageRuleEntity weight) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		weight.setTenantId(2);
		CrmWeightValidation.verifyDeleteWeight(weight, testObject);

	}

}
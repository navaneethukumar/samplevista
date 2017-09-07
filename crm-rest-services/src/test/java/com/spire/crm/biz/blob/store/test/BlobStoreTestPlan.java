package com.spire.crm.biz.blob.store.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.helper.SpireProperties;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

import spire.crm.entity.blob.store.entity.beans.BlobStoreEntity;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class BlobStoreTestPlan extends TestPlan {

	@DataProvider(name = "BLOBSTORE_DP")
	public static Iterator<Object[]> getblobStoreInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/blob/store/test/BlobStoreTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("BlobStoreEntity", BlobStoreEntity.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					BlobStoreTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	/**
	 * Verify Create and Delete
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreateDeleteRule_Sanity", "Sanity" }, dataProvider = "BLOBSTORE_DP")
	public void verifyCreateDeleteRule_Sanity(SpireTestObject testObject,
			TestData data, BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		BlobStoreValidation.verifyCreateRule(rule, testObject);

	}
	
	/*@BeforeClass(alwaysRun = true)
	public void setUPAuthentication() {
		
		UserHelper.authentication();
		
	}*/

	/**
	 * Verify Create and Delete
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDuplicateRule_LRG", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void verifyDuplicateRule_LRG(SpireTestObject testObject,
			TestData data, BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		BlobStoreValidation.verifyCreateRule(rule, testObject);

	}

	/**
	 * Verify Create rule by giving the invalid data
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreatRule_LRG", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void verifyCreatRule_LRG(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.verifyCreateRule(rule, testObject);

	}

	/**
	 * UpDate the rule
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateRule_Sanity", "Sanity" }, dataProvider = "BLOBSTORE_DP")
	public void updateRule_Sanity(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.verifyCreateUpdateRule(rule, testObject);

	}

	/**
	 * UpDate the rule
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateRule_SRG", "SRG" }, dataProvider = "BLOBSTORE_DP")
	public void updateRule_SRG(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.verifyCreateUpdateRule(rule, testObject);

	}

	/**
	 * UpDate the rule
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateRule_LRG", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void updateRule_LRG(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.validateUpdateRule(rule, testObject);

	}

	/**
	 * get the rules by Tenant Test
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getRuleByTenant_Sanity", "Sanity" }, dataProvider = "BLOBSTORE_DP")
	public void getRuleByTenant_Sanity(SpireTestObject testObject,
			TestData data, BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.verifygetRuleByTenant(rule, testObject);

	}

	/**
	 * get the rules by Tenant Test
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getRuleByTenant_LRG", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void getRuleByTenant_LRG(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.validateRulesByID(rule, testObject);

	}

	/**
	 * get the rules by Tenant Test
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getRuleByName_ID_LRG", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void getRuleByName_ID_LRG(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.verifyRuleByName_Tenant(rule, testObject);

	}

	/**
	 * Verify delete rule by giving the invalid data
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteRule_LRG1", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void verifyDeleteRule_LRG1(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		BlobStoreValidation.deleteRuleByName_Tenant(rule, testObject);

	}
	
	/**
	 * Verify profiles
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteRule_LRG", "LRG" }, dataProvider = "BLOBSTORE_DP")
	public void verifyDeleteRule_LRG(SpireTestObject testObject, TestData data,
			BlobStoreEntity rule) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		String propertiesfilePath = "./src/main/resources/services-endpoints.properties";
		Logging.log("config test :"
				+ SpireProperties.loadProperties(propertiesfilePath)
						.getProperty("BLOB_STORE_ENTITY"));

	}

}
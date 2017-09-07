package com.spire.crm.biz.newLabels.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import junit.framework.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

/**
 * @author Manikanta Y
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class NewLabelServiceSanityTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;

	final static String DATAPROVIDER_NAME = "NEW_LABELS_BIZ";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/biz/newLabels/test/";
	final static String CSV_FILENAME = "NewLabelServiceBiz_TestData.csv";
	final static String CSV_PATH = CSV_DIR + CSV_FILENAME;
	NewLabelSanityValidation labelvalidator = new NewLabelSanityValidation();

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
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(NewLabelServiceSanityTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}

	/**
	 * createLabel_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "createLabel_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createLabel_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> createLabel_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.createLabel(testObject, data);
		} catch (IOException e) {

			e.printStackTrace();
			Assert.fail("Run time Exception >>>" + e.getMessage());
		}

	}

	/**
	 * createLabels_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "createLabels_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createLabels_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> createLabels_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.createBulkLabel(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run time Exception >>>" + e.getMessage());
		}

	}

	/**
	 * attachLabelsToEntity_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "attachLabelsToEntity_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void attachLabelsToEntity_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> attachLabelsToEntity_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.attachLabelToEntity(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run time Exception >>>" + e.getMessage());
		}

	}

	/**
	 * attachLabelsToEntities_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "attachLabelsToEntities_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void attachLabelsToEntities_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> attachLabelsToEntities_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.attachLabelsToEntities(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run time Exception >>>" + e.getMessage());
		}

	}

	/**
	 * detachLabelsToEntity_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "detachLabelsToEntity_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void detachLabelsToEntity_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> detachLabelsToEntity_Sanity !!!" + data.getTestSteps());
		try {

			labelvalidator.detachLabelToEntity(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run time Exception >>>" + e.getMessage());
		}

	}

	/**
	 * detachLabelsToEntities_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "detachLabelsToEntities_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void detachLabelsToEntities_Sanity(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		Logging.log("Test Case Execution started >>> detachLabelsToEntities_Sanity !!!" + data.getTestSteps());
		// labelvalidator.detachLabelToEntities(data);

	}

	/**
	 * listLabels_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "listLabels_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void listLabels_Sanity(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		Logging.log("Test Case Execution started >>> listLabels_Sanity !!!" + data.getTestSteps());
		// labelvalidator.createLabel(data);

	}

	/**
	 * filterLabels_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "filterLabels_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void filterLabels_Sanity(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		Logging.log("Test Case Execution started >>> filterLabels_Sanity !!!" + data.getTestSteps());
		labelvalidator.filterLabels(testObject, data);

	}

	/**
	 * listProfiles_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "listProfiles_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void listProfiles_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> listProfiles_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.validateListProfiles(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * deleteLabel_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "deleteLabel_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void deleteLabel_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> deleteLabel_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.deleteLabel(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * deleteLabel_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "deleteLabels_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void deleteLabels_Sanity(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		Logging.log("Test Case Execution started >>> deleteLabels_Sanity !!!" + data.getTestSteps());
		labelvalidator.deleteLabels(testObject, data);

	}

	/**
	 * renameLabel_Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "renameLabel_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void renameLabel_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> listProfiles_Sanity !!!" + data.getTestSteps());
		try {
			labelvalidator.renameLabel(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	@Test(groups = { "shareTheLabel_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void shareTheLabel_Sanity(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> listProfiles_Sanity !!!" + data.getTestSteps());
		try {
	labelvalidator.shareLabel_ByLabelId(testObject, data);
			
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void clearDB() {

		Logging.log(" Deleting Lables Created By Automation !!!");

		labelvalidator.clearAutomationLabel();

	}

}

package com.spire.crm.entity.assets.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.entity.consumers.AssetsEntityConsumer;

import spire.commons.config.entities.SpireConfiguration;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class AssetsEntityTestPlan extends TestPlan {
	AssetsEntityConsumer comServiceEntityConsumer = null;

	SpireConfiguration spireConfiguration = null;
	String SERVICE_NAME = null;
	final static String DATAPROVIDER_NAME = "COM_SERVICE_ENTITY";
	String USER_ID = null;
	String ENTITY_ID = null;
	String NOTE_ID = null;

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/entity/assets/test/AssetsEntity_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(AssetsEntityTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * @param testObject
	 * @param data
	 */
	@AfterMethod(alwaysRun = true)
	public void afterTestMethod(Method method) {
		AssetsEntityValidation.deleteAssets();

	}

	/**
	 * verifyCreateAsset Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyCreateAsset_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyCreateAsset_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyCreateAsset_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.createAsset(data);
	}

	/**
	 * verifyCreateAsset SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyCreateAsset_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyCreateAsset_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyCreateAsset_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.createAsset(data);
	}

	/**
	 * verifyListAsset Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyListAsset_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyListAsset_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyListAsset_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.listAsset(data);
	}

	/**
	 * verifyListAsset SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyListAsset_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyListAsset_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyListAsset_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.listAsset(data);
	}

	/**
	 * verifyListAsset LRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyListAsset_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyListAsset_LRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyListAsset_LRG !!!" + data.getTestSteps());
		AssetsEntityValidation.listAsset(data);
	}

	/**
	 * verifyModifyAsset Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyModifyAsset_Sanity", "Sanity" }, priority = 0, dataProvider = DATAPROVIDER_NAME)
	public void verifyModifyAsset_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyModifyAsset_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.modifyAsset(data);
	}

	/**
	 * verifyDeleteAsset Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDeleteAsset_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteAsset_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDeleteAsset_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.deleteAsset(data);
	}

	/**
	 * verifyDeleteAsset SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDeleteAsset_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteAsset_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDeleteAsset_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.deleteAsset(data);
	}

	/**
	 * verifyDeleteAsset LRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDeleteAsset_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteAsset_LRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDeleteAsset_LRG !!!" + data.getTestSteps());
		AssetsEntityValidation.deleteAsset(data);
	}

	/**
	 * verifyAttachAssets Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyAttachAssets_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyAttachAssets_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyAttachAssets_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.attachMultipleAssets(data);
	}

	/**
	 * verifyAttachAssets SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyAttachAssets_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyAttachAssets_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyAttachAssets_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.attachMultipleAssets(data);
	}

	/**
	 * verifyDetachAssets Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDetachAssets_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDetachAssets_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDetachAssets_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.detachMultipleAssets(data);
	}

	/**
	 * verifyDetachAssets SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDetachAssets_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDetachAssets_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDetachAssets_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.detachMultipleAssets(data);
	}

	/**
	 * verifyGetAsset Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyGetAsset_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetAsset_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyGetAsset_Sanity !!!" + data.getTestSteps());
		AssetsEntityValidation.getAssets(data);
	}

	/**
	 * verifyGetAsset LRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyGetAsset_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetAsset_LRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyGetAsset_LRG !!!" + data.getTestSteps());
		AssetsEntityValidation.getAssets(data);
	}

	/**
	 * verifyAttachSingleAsset SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyAttachSingleAsset_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyAttachSingleAsset_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyAttachSingleAsset_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.attachSingleAsset(data);
	}

	/**
	 * verifyCreateAsset LRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyAttachSingleAsset_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyAttachSingleAsset_LRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyCreateAsset_LRG !!!" + data.getTestSteps());
		AssetsEntityValidation.attachSingleAsset(data);
	}

	/**
	 * verifyDetachSingleAsset SRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDetachSingleAsset_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDetachSingleAsset_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDetachSingleAsset_SRG !!!" + data.getTestSteps());
		AssetsEntityValidation.detachSingleAsset(data);
	}

}
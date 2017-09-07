package com.spire.crm.entity.attachments.test;

import java.io.IOException;
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
import com.spire.crm.restful.entity.consumers.AssetsEntityConsumer;

import spire.commons.config.entities.SpireConfiguration;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class AttachmentsEntityTestPlan extends TestPlan {
	AssetsEntityConsumer comServiceEntityConsumer = null;

	SpireConfiguration spireConfiguration = null;
	final static String DATAPROVIDER_NAME = "ATTACHMENT_SERVICE_ENTITY";

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/entity/attachments/test/AttachmentsEntity_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(AttachmentsEntityTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * verifyCreateAttachment_Sanity 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyCreateAttachment_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyCreateAttachment_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyCreateAttachment_Sanity !!!" + data.getTestSteps());
		AttachmentsEntityValidation.createAttachment(data);
	}

	
	/**
	 * verifyListAttachment_Sanity 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyListAttachment_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyListAttachment_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyListAttachment_Sanity !!!" + data.getTestSteps());
		AttachmentsEntityValidation.listAttachmment(data);
	}

	
	/**
	 * verifyUpdateAttachment_Sanity 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyUpdateAttachment_Sanity", "Sanity" }, priority = 0, dataProvider = DATAPROVIDER_NAME)
	public void verifyUpdateAttachment_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyUpdateAttachment_Sanity !!!" + data.getTestSteps());
		AttachmentsEntityValidation.updateAttachment(data);
	}

	/**
	 * verifyDeleteAttachment_Sanity Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyDeleteAttachment_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteAttachment_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDeleteAttachment_Sanity !!!" + data.getTestSteps());
		AttachmentsEntityValidation.deleteAttachment(data);
	}

	
	/**
	 * verifyGetAttachmentById_Sanity 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyGetAttachmentById_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetAttachmentById_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyGetAttachmentById_Sanity !!!" + data.getTestSteps());
		AttachmentsEntityValidation.getAttachmentsById(data);
	}
	
	/**
	 * verifyGetAttachmentByLink_Sanity 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */
	@Test(groups = { "verifyGetAttachmentById_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetAttachmentByLink_Sanity(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyGetAttachmentByLink_Sanity !!!" + data.getTestSteps());
		AttachmentsEntityValidation.getAttachmentsByLink(data);
	}

}
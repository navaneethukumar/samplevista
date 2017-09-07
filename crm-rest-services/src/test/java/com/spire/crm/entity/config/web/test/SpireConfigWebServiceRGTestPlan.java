package com.spire.crm.entity.config.web.test;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.SpireConfigWebServiceConsumer;

import spire.commons.config.entities.CollectionEntity;
import spire.commons.config.entities.ErrorEntity;
import spire.commons.config.entities.SpireConfiguration;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

@Test(groups = { "RG" }, retryAnalyzer = TestRetryAnalyzer.class)
public class SpireConfigWebServiceRGTestPlan extends TestPlan {
	SpireConfigWebServiceConsumer spireConfigWebServiceConsumer = null;
	private static Logger logger = LoggerFactory.getLogger(SpireConfigWebServiceRGTestPlan.class);
	SpireConfiguration spireConfiguration = null;
	String id = null;
	String SERVICE_NAME = null;
	final static String DATAPROVIDER_NAME = "SPIRECONFIGWEB";
	String DUPLICATE_SERVICE_ERR_MSG = "Service already exists. Unable to create new.";
	String ERR_CODE = "INVALID_PARAMETER";
	String INVALID_SERVICE_ERR_MSG = "No service found for the name :";
	String NON_EXISTED_SERVICE = "NON_EXISTED_SERVICES";
	String INVALID_TENANT_ID = "4000";

	/**
	 * Passing endPointURL and Service name from testng.xml
	 * 
	 * @param endPointURL
	 * @param serviceName
	 */
	@Parameters({ "SERVICE_NAME" })
	@BeforeClass(alwaysRun = true)
	public void setUp(String serviceName) {
		this.SERVICE_NAME = serviceName;
		spireConfigWebServiceConsumer = new SpireConfigWebServiceConsumer();
	}

	/**
	 * verifyErrorMsgForDuplicateServiceCreation service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyErrorMsgForDuplicateServiceCreation",
			"RG" }, priority = 0, dataProvider = DATAPROVIDER_NAME, dataProviderClass = SpireConfigWebServiceSanityTestPlan.class)
	public void verifyErrorMsg_DuplicateService_Creation(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyErrorMsgForDuplicateService !!!");
		Response response = null;
		try {
			spireConfigWebServiceConsumer.addNewService(SERVICE_NAME);
			response = spireConfigWebServiceConsumer.addNewService(SERVICE_NAME);
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			Assert.assertEquals(errorEntity.getMessage(), DUPLICATE_SERVICE_ERR_MSG);
			Assert.assertEquals(errorEntity.getCode(), ERR_CODE);
			Assert.assertTrue(true, "TestCase verifyErrorMsgForDuplicateServiceCreation execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyErrorMsgForDuplicateServiceCreation -> failed", e);
		}
	}

	/**
	 * verifyErrorMsgForNotExistedServiceNameDeletion service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyErrorMsg_NotExistedServiceName_Deletion",
			"RG" }, priority = 1, dataProvider = DATAPROVIDER_NAME, dataProviderClass = SpireConfigWebServiceSanityTestPlan.class)
	public void verifyErrorMsg_NotExistedServiceName_Deletion(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyErrorMsg_NotExistedServiceName_Deletion !!!");
		Response response = null;
		try {
			response = spireConfigWebServiceConsumer.deleteService(NON_EXISTED_SERVICE);
			if (response.getStatus() == 400) {
				ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
				logger.info("ErrorEntity Code ::" + errorEntity.getCode());
				logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
				Assert.assertEquals(errorEntity.getCode(), ERR_CODE);
				Assert.assertTrue(errorEntity.getMessage().contains(INVALID_SERVICE_ERR_MSG), "Error message is wrong");
			} else {
				Assert.fail("TestCase >>> verifyErrorMsg_NotExistedServiceName_Deletion -> failed");
			}
			Assert.assertTrue(true, "TestCase verifyErrorMsg_NotExistedServiceName_Deletion execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyErrorMsg_NotExistedServiceName_Deletion -> failed", e);
		}
	}

	/**
	 * verifyErrorMsgForNotExistedServiceAddingConfig service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyErrorMsgForNotExistedServiceAddingConfig",
			"RG" }, priority = 2, dataProvider = DATAPROVIDER_NAME, dataProviderClass = SpireConfigWebServiceSanityTestPlan.class)
	public void verifyErrorMsg_NotExistedService_AddingConfig(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyErrorMsgForNotExistedServiceAddingConfig !!!");
		SpireConfiguration spireConfiguration = null;
		spireConfiguration = new SpireConfiguration();
		spireConfiguration.setIpAddress("test");
		spireConfiguration.setTenant("test");
		spireConfiguration.setKey("test");
		spireConfiguration.setValue("test");
		try {
			spireConfiguration = spireConfigWebServiceConsumer.addNewConfiguration(NON_EXISTED_SERVICE,
					spireConfiguration);
			if (spireConfiguration == null)
				Assert.assertTrue(true,
						"TestCase verifyErrorMsgForNotExistedServiceAddingConfig execution success !!!");
			else
				Assert.fail("TestCase >>> verifyErrorMsgForNotExistedServiceAddingConfig -> failed");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyErrorMsgForNotExistedServiceAddingConfig -> failed", e);
		}
	}

	/**
	 * verifyErrorMsgForNotExistedServiceAddingConfig service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyErrorMsg_EnteringNonExistedService_ForGetAllConfigForAService",
			"RG" }, priority = 3, dataProvider = DATAPROVIDER_NAME, dataProviderClass = SpireConfigWebServiceSanityTestPlan.class)
	public void verifyErrorMsg_EnteringNonExistedService_ForGetAllConfigForAService(SpireTestObject testObject,
			TestData data) {
		logger.info(
				"Test Case Execution started >>> verifyErrorMsg_EnteringNonExistedService_ForGetAllConfigForAService !!!");
		CollectionEntity<SpireConfiguration> configs = null;
		try {
			configs = spireConfigWebServiceConsumer.getAllConfigsForService(NON_EXISTED_SERVICE);
			if (configs == null)
				Assert.assertTrue(true,
						"TestCase verifyErrorMsg_EnteringNonExistedService_ForGetAllConfigForAService execution success !!!");
			else
				Assert.fail(
						"TestCase >>> verifyErrorMsg_EnteringNonExistedService_ForGetAllConfigForAService -> failed");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyErrorMsg_EnteringNonExistedService_ForGetAllConfigForAService -> failed",
					e);
		}
	}

	/**
	 * verifyErrorMsgForNotExistedServiceAddingConfig service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyErrorMsgForNotExistedServiceAddingConfig",
			"RG" }, priority = 4, dataProvider = DATAPROVIDER_NAME, dataProviderClass = SpireConfigWebServiceSanityTestPlan.class)
	public void verifyErrorMsg_EnteringExistedService_InvalidID_ForGetAConfig(SpireTestObject testObject,
			TestData data) {
		logger.info("Test Case Execution started >>> verifyErrorMsgForNotExistedServiceAddingConfig !!!");
		SpireConfiguration spireConfiguration = null;
		Response response = null;
		try {
			spireConfiguration = spireConfigWebServiceConsumer.getConfigurations(SERVICE_NAME, "120911");
			if (spireConfiguration == null)
				Assert.assertTrue(true,
						"TestCase verifyErrorMsgForNotExistedServiceAddingConfig execution success !!!");
			else
				Assert.fail("TestCase >>> verifyErrorMsgForNotExistedServiceAddingConfig -> failed");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyErrorMsgForNotExistedServiceAddingConfig -> failed", e);
		}
		try {
			response = spireConfigWebServiceConsumer.deleteService(SERVICE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response == null) {
			logger.info("tearDown >>> Successfull !!!");
		} else {
			logger.info("tearDown >>> failed !!!");
		}
	}
	/**
	 * Verify for non-existed tenant id testing
	 * @param testObject
	 * @param data
	 */
	@Test(groups = {
			"verifyInvalidTenantSpecificConfigurations" }, priority = 5, dataProvider = DATAPROVIDER_NAME, dataProviderClass = SpireConfigWebServiceSanityTestPlan.class)
	public void verifyInvalidTenantSpecificConfigurations(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyTenantSpecificConfigurations !!!");
		try {
			CollectionEntity<SpireConfiguration> spireConfig = spireConfigWebServiceConsumer
					.getTenantIdSpecificConfigurations(INVALID_TENANT_ID);
			Assert.assertTrue(spireConfig.getTotalResults() == 0, "Total results should be zero only !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyTenantSpecificConfigurations -> failed", e);
		}
	}
}
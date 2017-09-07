package com.spire.crm.entity.config.web.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.SpireConfigWebServiceConsumer;

import spire.commons.config.entities.CollectionEntity;
import spire.commons.config.entities.SpireConfiguration;

@Test(groups = { "SANITY" })
public class SpireConfigWebServiceSanityTestPlan extends TestPlan {
	SpireConfigWebServiceConsumer spireConfigWebServiceConsumer = null;
	String id = null;
	String SERVICE_NAME = null;
	String SERVICE_NAME_FALLBACK = null;
	final static String DATAPROVIDER_NAME = "SPIRECONFIGWEB";
	final String IP = "6.2.400";
	final String TENANT_ID = "400";
	final String KEY = "CRM-QE";
	final String VALUE = "1108";

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/entity/config/web/test/SpireConfigWebService_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("data", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(SpireConfigWebServiceSanityTestPlan.class, entityClazzMap,
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
	 * @param serviceName
	 */
	@Parameters({ "SERVICE_NAME" })
	@BeforeClass(alwaysRun = true)
	public void setUp(String serviceName) {
		this.SERVICE_NAME = serviceName.split(",")[0];
		this.SERVICE_NAME_FALLBACK = serviceName.split(",")[1];
		spireConfigWebServiceConsumer = new SpireConfigWebServiceConsumer();
		SpireConfigWebServiceValidation.preRequisiteToDeleteExistedService(spireConfigWebServiceConsumer);
		SpireConfigWebServiceValidation.createGlobalAndLocalDataPoints(spireConfigWebServiceConsumer);

	}

	/**
	 * Creating service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyAddNewService" }, priority = 0, dataProvider = DATAPROVIDER_NAME)
	public void verifyAddNewService(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyAddNewService !!!");
		try {
			spireConfigWebServiceConsumer.addNewService(SERVICE_NAME);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyAddNewService -> failed", e);
		}
	}

	/**
	 * adding configs to the service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyAddNewConfiguration" }, priority = 1, dataProvider = DATAPROVIDER_NAME)
	public void verifyAddNewConfiguration(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyAddNewConfiguration !!!");
		SpireConfiguration spireConfig = null;
		try {
			// Giving all config values
			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration("test", "test", "test", "test");
			spireConfig = spireConfigWebServiceConsumer.addNewConfiguration(SERVICE_NAME, spireConfig);

			this.id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
			Logging.info("Service Name >>>" + SERVICE_NAME + "Service Id>>>>" + id);
			Assert.assertTrue(true, "TestCase verifyAddNewConfiguration execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyAddNewConfiguration -> failed", e);
		}
	}

	/**
	 * fetching all services`
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetAllServices" }, priority = 2, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetAllServices(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyGetAllServices !!!");
		CollectionEntity<String> collectionEntity = null;
		try {
			collectionEntity = spireConfigWebServiceConsumer.getAllServices();
			// Validating getAllServices...
			SpireConfigWebServiceValidation.validate_VerifyGetAllServices(collectionEntity);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetAllServices -> failed", e);
		}
	}

	/**
	 * fetching all the configurations are assigned to the particular service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetAllConfigsForService" }, priority = 3, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetAllConfigsForService(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyGetAllConfigsForService !!!");
		CollectionEntity<SpireConfiguration> spireConfiguration = null;
		try {
			spireConfiguration = spireConfigWebServiceConsumer.getAllConfigsForService(SERVICE_NAME);
			// Validating getAllConfigsForService...
			SpireConfigWebServiceValidation.validate_VerifyGetAllConfigsForService(id, spireConfiguration);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetAllConfigsForService -> failed", e);
		}
	}

	/**
	 * fetching configurations by passing service name and config id
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetConfiguration" }, priority = 4, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetConfiguration(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyGetConfiguration !!!");
		SpireConfiguration spireConfiguration = null;
		try {
			spireConfiguration = spireConfigWebServiceConsumer.getConfigurations(SERVICE_NAME, id);
			// Validating getConfigurations...
			SpireConfigWebServiceValidation.validate_VerifyGetConfiguration(id, spireConfiguration);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetConfiguration -> failed", e);
		}
	}

	/**
	 * updating configurations of a service by passing service name & id
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyUpdateConfigurations" }, priority = 5, dataProvider = DATAPROVIDER_NAME)
	public void verifyUpdateConfigurations(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyUpdateConfigurations !!!");
		SpireConfiguration spireConfig = null;
		try {
			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration("updated_test", "updated_test",
					"updated_test", "updated_test");
			spireConfig = spireConfigWebServiceConsumer.updateConfigurations(SERVICE_NAME, id, spireConfig);
			// Validating verifyUpdateConfigurations...
			SpireConfigWebServiceValidation.validate_VerifyUpdateConfigurations(id, spireConfig);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyUpdateConfigurations -> failed", e);
		}
	}

	/**
	 * deleting service configurations by passing service name & config id
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteConfiguration" }, priority = 6, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteConfiguration(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyDeleteConfiguration !!!");
		try {
			spireConfigWebServiceConsumer.deleteConfiguration(SERVICE_NAME, id);
			// Validating service configurations are deleted !!!
			SpireConfigWebServiceValidation
					.validate_VerifyGetAllServices(spireConfigWebServiceConsumer.getAllServices());
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyDeleteConfiguration -> failed", e);
		}
	}

	/**
	 * deleting service by passing service name.
	 *
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteService" }, priority = 7, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteService(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyDeleteService !!!");
		Response response;
		try {
			response = spireConfigWebServiceConsumer.deleteService(SERVICE_NAME);
			if (response != null && response.getStatus() != 400)
				throw new RuntimeException("Service is not deleted successfully !!!!");
			Logging.info("Service deleted successfully !!!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyDeleteService -> failed", e);
		}
	}

	/**
	 * Tenant specific get configurations
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyTenantSpecificConfigurations" }, priority = 8, dataProvider = DATAPROVIDER_NAME)
	public void verifyTenantSpecificConfigurations(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyTenantSpecificConfigurations !!!");
		try {
			CollectionEntity<SpireConfiguration> spireConfig = spireConfigWebServiceConsumer
					.getTenantIdSpecificConfigurations(TENANT_ID);
			Assert.assertNotNull(spireConfig);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyTenantSpecificConfigurations -> failed", e);
		}
	}

	/**
	 * tenant specific fallback=true; tenantId=400; global = false
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyTenantSpecificFallbackConfigurations" }, priority = 9, dataProvider = DATAPROVIDER_NAME)
	public void verifyFallbackRuleForTenantSpecific(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyTenantSpecificFallbackConfigurations !!!");
		try {
			CollectionEntity<SpireConfiguration> spireConfig = spireConfigWebServiceConsumer
					.getServiceConfigurationsOnFallback(SERVICE_NAME_FALLBACK, true, false, TENANT_ID);
			Assert.assertNotNull(spireConfig);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyTenantSpecificFallbackConfigurations -> failed", e);
		}
	}

	/**
	 * tenant specific fallback=true; tenantId=400; global = true
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = {
			"verifyTenantSpecificFallbackWithGlobalConfigurations" }, priority = 10, dataProvider = DATAPROVIDER_NAME)
	public void verifyFallbackRuleForTenantSpecificWithGlobalConfigurations(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyTenantSpecificFallbackWithGlobalConfigurations !!!");
		try {
			CollectionEntity<SpireConfiguration> spireConfig = spireConfigWebServiceConsumer
					.getServiceConfigurationsOnFallback(SERVICE_NAME_FALLBACK, true, true, TENANT_ID);
			Assert.assertNotNull(spireConfig);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyTenantSpecificFallbackWithGlobalConfigurations -> failed", e);
		}
	}

	/**
	 * fallback = true ; global =false
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = {
			"verifyTenantSpecificFallbackWithGlobalConfigurations" }, priority = 11, dataProvider = DATAPROVIDER_NAME)
	public void verifyFallbackRuleForService(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyTenantSpecificFallbackWithGlobalConfigurations !!!");
		try {
			CollectionEntity<SpireConfiguration> spireConfig = spireConfigWebServiceConsumer
					.getServiceConfigurationsOnFallback(SERVICE_NAME_FALLBACK, true, true, TENANT_ID);
			Assert.assertNotNull(spireConfig);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyTenantSpecificFallbackWithGlobalConfigurations -> failed", e);
		}
	}

	/**
	 * fallback = true ; global =true
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = {
			"verifyTenantSpecificFallbackWithGlobalConfigurations" }, priority = 12, dataProvider = DATAPROVIDER_NAME)
	public void verifyFallbackRuleForServiceWithGlobalConfigurations(SpireTestObject testObject, TestData data) {
		Logging.info("Test Case Execution started >>> verifyFallbackRuleForServiceWithGlobalConfigurations !!!");
		try {
			CollectionEntity<SpireConfiguration> spireConfig = spireConfigWebServiceConsumer
					.getServiceConfigurationsOnFallback(SERVICE_NAME_FALLBACK, true, true, TENANT_ID);
			Assert.assertNotNull(spireConfig);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyFallbackRuleForServiceWithGlobalConfigurations -> failed", e);
		}
	}
}
package com.spire.crm.entity.config.web.test;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.biz.consumers.SpireConfigWebServiceConsumer;

import spire.commons.config.entities.CollectionEntity;
import spire.commons.config.entities.ErrorEntity;
import spire.commons.config.entities.Link;
import spire.commons.config.entities.SpireConfiguration;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

public class SpireConfigWebServiceValidation {
	private static Logger logger = LoggerFactory.getLogger(SpireConfigWebServiceValidation.class);
	static String services = (String) ContextManager.getGlobalContext().getAttribute("SERVICE_NAME");
	static String SERVICE_NAME = services.split(",")[0];
	static final String IP = "6.2.400";
	static final String TENANT_ID = "400";
	static final String KEY = "CRM-QE";
	static final String VALUE = "1108";
	static String id = null;

	/**
	 * pre-requisite ,it checks the service name already existed then delete all
	 * configs then deletes service name.
	 */
	public static void preRequisiteToDeleteExistedService(SpireConfigWebServiceConsumer spireConfigWebServiceConsumer) {
		for (String service : services.split(",")) {
			Logging.log("Service ::" + service);
			deleteExistedServices(spireConfigWebServiceConsumer, service);
		}

	}

	/**
	 * To create global service configurations & local service configurations to
	 * verify fallback mechanism.
	 * 
	 * @param spireConfigWebServiceConsumer
	 */
	public static void createGlobalAndLocalDataPoints(SpireConfigWebServiceConsumer spireConfigWebServiceConsumer) {
		SpireConfiguration spireConfig = null;
		String SERVICE2 = services.split(",")[1];
		try {

			// Giving all config values
			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration(IP, TENANT_ID, KEY, VALUE);
			spireConfig = spireConfigWebServiceConsumer.addGlobalConfigurations(spireConfig);
			if (spireConfig != null) {
				id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
				Logging.info("Service Name >>>" + "global" + "Service Id>>>>" + id);
			} else {
				Logging.log("global service with all the configs already there !!");
			}

			// making ip =x
			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration("x", TENANT_ID, KEY, VALUE);
			spireConfig = spireConfigWebServiceConsumer.addGlobalConfigurations(spireConfig);
			if (spireConfig != null) {
				id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
				Logging.info("Service Name >>>" + "global" + "Service Id>>>>" + id);
			} else {
				Logging.log("global service with x,tenant configs already there !!");
			}

			// making ip & tenantId=x
			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration("x", "x", KEY, VALUE);
			spireConfig = spireConfigWebServiceConsumer.addGlobalConfigurations(spireConfig);
			if (spireConfig != null) {
				id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
				Logging.info("Service Name >>>" + "global" + "Service Id>>>>" + id);
			} else {
				Logging.log("global service with all configs already there !!");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyCreateGlobalServiceConfigurations -> failed", e);
		}

		try {
			// Giving all config values
			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration(IP, TENANT_ID, KEY, VALUE);
			spireConfig = spireConfigWebServiceConsumer.addNewConfiguration(SERVICE2, spireConfig);
			if (spireConfig != null) {
				id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
				Logging.info("Service Name >>>" + "global" + "Service Id>>>>" + id);
			} else {
				Logging.log("global service with x,x configs already there !!");
			}

			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration("x", TENANT_ID, KEY, VALUE);
			spireConfig = spireConfigWebServiceConsumer.addNewConfiguration(SERVICE2, spireConfig);
			if (spireConfig != null) {
				id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
				Logging.info("Service Name >>>" + "global" + "Service Id>>>>" + id);
			} else {
				Logging.log("global service with x,x configs already there !!");
			}

			spireConfig = SpireConfigWebServiceValidation.getSpireConfiguration("x", "x", KEY, VALUE);
			spireConfig = spireConfigWebServiceConsumer.addNewConfiguration(SERVICE2, spireConfig);
			if (spireConfig != null) {
				id = SpireConfigWebServiceValidation.validate_VerifyAddNewConfiguration(spireConfig);
				Logging.info("Service Name >>>" + "global" + "Service Id>>>>" + id);
			} else {
				Logging.log("global service with x,x configs already there !!");
			}

		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyAddMultipleConfiguration -> failed", e);
		}
	}

	/**
	 * Spire configuraiton creation
	 * 
	 * @param ip
	 * @param tenantId
	 * @param key
	 * @param value
	 */
	public static SpireConfiguration getSpireConfiguration(String ip, String tenantId, String key, String value) {
		Logging.info("SpireConfiguration creation !!");
		SpireConfiguration spireConfiguration = null;
		ZonedDateTime currentDateTime = ZonedDateTime.now();
		Timestamp timestamp = new Timestamp(currentDateTime.toInstant().getEpochSecond() * 1000L);
		spireConfiguration = new SpireConfiguration();
		spireConfiguration.setIpAddress(ip);
		spireConfiguration.setTenant(tenantId);
		spireConfiguration.setKey(key);
		spireConfiguration.setValue(value);
		//commemnted code to fix build failures 
		/*spireConfiguration.setCreatedBy("admin");
		spireConfiguration.setModifiedBy("admin");
		spireConfiguration.setCreatedOn(timestamp);
		spireConfiguration.setModifiedOn(timestamp);*/
		return spireConfiguration;
	}

	public static void validate_VerifyGetAllServices(CollectionEntity<String> collectionEntity) {
		try {
			if (collectionEntity == null) {
				throw new RuntimeException("verifyGetAllServices instance >> collectionEntity is null");
			}
			Collection<String> listItems = collectionEntity.getItems();
			if (listItems.size() > 0 && !listItems.isEmpty()) {
				logger.info("list of services -->" + listItems.size());
				for (String item : listItems) {
					logger.info("service -->" + item);
					if (item.startsWith(SERVICE_NAME)) {
						Assert.assertTrue(true);
						return;
					}

				}
				Assert.fail(
						"TestCase >>> verifyGetAllServices -> failed service name starts with \'TestSpireConfig\' not existed");
			} else {
				logger.info("list of services -->" + listItems.size());
				Assert.fail("TestCase >>> verifyGetAllServices -> failed");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetAllServices -> failed", e);
		}
	}

	public static void validate_VerifyGetAllConfigsForService(String id,
			CollectionEntity<SpireConfiguration> spireConfiguration) {
		try {
			if (spireConfiguration == null) {
				throw new RuntimeException("VerifyGetAllConfigsForService instance >> spireConfiguration is null");
			}
			Collection<SpireConfiguration> spireConfigs = spireConfiguration.getItems();
			if (spireConfigs.size() > 0 && !spireConfigs.isEmpty()) {
				logger.info("list of services -->" + spireConfigs.size());
				for (SpireConfiguration spire : spireConfigs) {
					if (spire.getTenant().startsWith("test") && spire.getValue().startsWith("test")
							&& spire.getIpAddress().startsWith("test") && spire.getKey().startsWith("test")) {
						Assert.assertTrue(true);
						return;
					} else {
						Assert.fail("TestCase >>> validate_VerifyGetAllConfigsForService -> failed");
					}
				}
				Assert.fail("TestCase >>> validate_VerifyGetAllConfigsForService -> failed");
			} else {
				logger.info("list of services -->" + spireConfigs.size());
				Assert.fail("TestCase >>> validate_VerifyGetAllConfigsForService -> failed");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> validate_VerifyGetAllConfigsForService -> failed", e);
		}
	}

	public static String validate_VerifyAddNewConfiguration(SpireConfiguration spireConfiguration) {
		try {
			if (spireConfiguration == null) {
				throw new RuntimeException("VerifyGetAllConfigsForService instance >> spireConfiguration is null");
			}
			List<Link> spireConfigs = spireConfiguration.getLinks();
			if (spireConfigs.size() > 0 && !spireConfigs.isEmpty()) {
				logger.info("list of configs -->" + spireConfigs.size());
				String id = spireConfiguration.getId();
				if (id != null && !id.isEmpty()) {
					Assert.assertTrue(true, "TestCase validate_VerifyAddNewConfiguration execution success !!!");
					return id;
				} else {
					Assert.fail("TestCase >>> validate_VerifyAddNewConfiguration -> failed");
				}
			} else {
				logger.info("list of services -->" + spireConfigs.size());
				Assert.fail("TestCase >>> validate_VerifyAddNewConfiguration -> failed");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> validate_VerifyAddNewConfiguration -> failed", e);
		}
		return null;
	}

	public static String validate_VerifyGetConfiguration(String id, SpireConfiguration spireConfiguration) {
		try {
			if (spireConfiguration == null) {
				throw new RuntimeException("VerifyGetAllConfigsForService instance >> spireConfiguration is null");
			}
			List<Link> spireConfigs = spireConfiguration.getLinks();
			if (spireConfigs.size() > 0 && !spireConfigs.isEmpty()) {
				logger.info("list of configs -->" + spireConfigs.size());
				Assert.assertTrue(true, "TestCase VerifyGetAllConfigsForService execution success !!!");
				if (id != null && spireConfiguration.getId() != null && id.equalsIgnoreCase(spireConfiguration.getId()))
					return id;
				else {
					Assert.fail("TestCase >>> validate_VerifyGetConfigurations -> failed");
				}
			} else {
				logger.info("list of services -->" + spireConfigs.size());
				Assert.fail("TestCase >>> VerifyGetAllConfigsForService -> failed");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> VerifyGetAllConfigsForService -> failed", e);
		}
		return null;
	}

	public static String validate_VerifyUpdateConfigurations(String id, SpireConfiguration spireConfiguration) {
		try {
			if (spireConfiguration == null) {
				throw new RuntimeException("VerifyGetAllConfigsForService instance >> spireConfiguration is null");
			}
			List<Link> spireConfigs = spireConfiguration.getLinks();
			if (spireConfigs.size() > 0 && !spireConfigs.isEmpty()) {
				logger.info("list of configs -->" + spireConfigs.size());
				Assert.assertTrue(true, "TestCase VerifyGetAllConfigsForService execution success !!!");
				if (id != null && spireConfiguration.getId() != null && id.equalsIgnoreCase(spireConfiguration.getId()))
					if (spireConfiguration.getTenant().startsWith("updated_test")
							&& spireConfiguration.getValue().startsWith("updated_test")
							&& spireConfiguration.getIpAddress().startsWith("updated_test")
							&& spireConfiguration.getKey().startsWith("updated_test"))
						return id;
					else {
						Assert.fail("TestCase >>> validate_VerifyGetConfigurations -> failed");
					}
			} else {
				logger.info("list of services -->" + spireConfigs.size());
				Assert.fail("TestCase >>> VerifyGetAllConfigsForService -> failed");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> VerifyGetAllConfigsForService -> failed", e);
		}
		return null;
	}

	public static void deleteExistedServices(SpireConfigWebServiceConsumer spireConfigWebServiceConsumer,
			String serviceName) {
		Response response = null;
		String SERVICE_NAME = serviceName;
		try {
			Collection<SpireConfiguration> deleteConfigIds = null;
			CollectionEntity<SpireConfiguration> deleteConfigId = spireConfigWebServiceConsumer
					.getAllConfigsForService(SERVICE_NAME);
			if (deleteConfigId != null) {
				deleteConfigIds = deleteConfigId.getItems();
				if (deleteConfigIds.size() > 0) {
					for (SpireConfiguration deleteId : deleteConfigIds) {
						try {
							response = spireConfigWebServiceConsumer.deleteConfiguration(SERVICE_NAME,
									deleteId.getId());
							logger.info("Deleting Configs for existed service >>>" + SERVICE_NAME
									+ "Config id for the service >>>" + deleteId.getId());
						} catch (Exception e) {
							throw new RuntimeException(" Deleting config exception !!!");

						}
					}
				}

			} else {
				logger.info("Before Test Execution  >>>The service is not existed >> !!!" + SERVICE_NAME);
				return;
			}
			if (deleteConfigIds.size() == 0) {
				logger.info("Before Test Execution  >>>No configs existed to the service >> !!!" + SERVICE_NAME);
				try {
					response = spireConfigWebServiceConsumer.deleteService(SERVICE_NAME);
					if (response == null) {
						logger.info("Before Test Execution  >>>service deleted successfully !!!");
						return;
					}
				} catch (Exception e) {
					throw new RuntimeException(" Deleting service exception !!!");
				}
			}
			if (response != null && response.getStatus() == 400) {
				ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
				String errorMsg = "No service found for the name :TALENT-VISTA";
				if (errorEntity != null && errorEntity.getMessage().equalsIgnoreCase(errorMsg)) {
					logger.info("Before Test Execution started >>> No service found for the name :" + SERVICE_NAME);
					return;
				} else
					throw new RuntimeException(
							"------BeforeTest failed ....service is not up !! check the service URL !!!");
			} else {
				throw new RuntimeException(
						"------BeforeTest failed ....service is not up !! check the service URL !!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

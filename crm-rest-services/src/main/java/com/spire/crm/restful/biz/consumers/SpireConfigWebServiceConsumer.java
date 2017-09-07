package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.config.entities.CollectionEntity;
import spire.commons.config.entities.ErrorEntity;
import spire.commons.config.entities.SpireConfiguration;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

/**
 * http://192.168.2.124:8085/spire-config-web/api/swagger.json
 * 
 * @author Pradeep
 *
 */
public class SpireConfigWebServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory.getLogger(SpireConfigWebServiceConsumer.class);
	Response response = null;
//	String endPointURL = "http://192.168.2.124:8082/spire-config-web/api/v1/config/services";
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("SPIRE_CONFIG_BIZ");
	
	
	
	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services
	 * 
	 * @param URL
	 */
	public SpireConfigWebServiceConsumer() {
		Logging.log("EndPoint URL >>> " + endPointURL);
		// this.endPointURL = URL;
		super.REQ_HEADERS = false;

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * find all services
	 * 
	 * @return
	 * @throws Exception
	 */
	public CollectionEntity<String> getAllServices() throws Exception {
		Logging.log("EndPoint URL >>> " + endPointURL);
		response = executeGET(this.endPointURL, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<String> services = response.readEntity(new GenericType<CollectionEntity<String>>() {
			});
			return services;
		} else {
			logger.info("------ErrorEntity----------");
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			return null;
		}

	}

	/**
	 * To fetch all the configuratons of a service by passing service name.
	 * Sample URL Pattern :
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations
	 * 
	 * @return
	 * @throws Exception
	 */
	public CollectionEntity<SpireConfiguration> getAllConfigsForService(String serviceName) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName + "/configurations";
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeGET(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<SpireConfiguration> configs = response
					.readEntity(new GenericType<CollectionEntity<SpireConfiguration>>() {
					});
			return configs;
		} else {
			return null;
		}

	}
	
	
	/**
	 * To fetch all the configuratons of a service by passing service name.
	 * Sample URL Pattern :
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations
	 * 
	 * @return
	 * @throws Exception
	 */
	public Response getAllConfigsForService(String serviceName,String serviceEndPoint ) throws Exception {
		serviceEndPoint = serviceEndPoint + "/" + serviceName + "/configurations";
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeGET(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			
			
			/*CollectionEntity<SpireConfiguration> configs = response
					.readEntity(new GenericType<CollectionEntity<SpireConfiguration>>() {
					});*/
			return response;
		} else {
			return null;
		}

	}
	
	

	/**
	 * To fetch configuration for a service by passing service name & id
	 * 
	 * Sample URL Pattern
	 * :http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations/<ID>
	 * 
	 * @return
	 * @throws Exception
	 */
	public SpireConfiguration getConfigurations(String serviceName, String id) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName + "/configurations" + "/" + id;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeGET(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			SpireConfiguration config = response.readEntity(SpireConfiguration.class);
			return config;
		} else {
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			return null;
		}

	}

	/**
	 * get tenantId specific Configurations
	 * 
	 * @param tenantId
	 * @return
	 * @throws Exception
	 */
	public CollectionEntity<SpireConfiguration> getTenantIdSpecificConfigurations(String tenantId) throws Exception {
		String serviceEndPoint = this.endPointURL + "/tenant" + tenantId + "/configurations";
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeGET(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<SpireConfiguration> configs = response
					.readEntity(new GenericType<CollectionEntity<SpireConfiguration>>() {
					});
			return configs;
		} else {
			return null;
		}
	}

	/**
	 * fallback , global , tenatId verification
	 * 
	 * @param serviceName
	 * @param fallback
	 * @param global
	 * @param tenantId
	 * @return
	 * @throws Exception
	 */
	public CollectionEntity<SpireConfiguration> getServiceConfigurationsOnFallback(String serviceName, boolean fallback,
			boolean global, String tenantId) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName +"/configurations?"
				+ "fallback=" + fallback + "&global=" + global + "&tenantId=" + tenantId;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		executeGET(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<SpireConfiguration> configs = response
					.readEntity(new GenericType<CollectionEntity<SpireConfiguration>>() {
					});
			return configs;
		} else {
			return null;
		}
	}
	/** -------------- POST Operations ----------------------------- **/

	/**
	 * Adding new Configurations to the service by passing serivce name and
	 * spireConfiguration instance.
	 * 
	 * Sample URL pattern
	 * :http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations
	 * 
	 * @param spireConfiguration
	 * @return
	 * @throws Exception
	 */
	public SpireConfiguration addNewConfiguration(String serviceName, SpireConfiguration spireConfiguration)
			throws Exception {
		SpireConfiguration resultConfig = null;
		String serviceEndPoint = this.endPointURL + "/" + serviceName + "/configurations";
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executePOST(serviceEndPoint, false, Entity.entity(spireConfiguration, MediaType.APPLICATION_JSON));
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201) {
			resultConfig = response.readEntity(SpireConfiguration.class);
			return resultConfig;
		} else if (response.getStatus() == 400) {
			return resultConfig;
		} else {
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			Assert.fail("addNewConfiguration service is failing !!!!");
			return null;
		}

	}
	
	/** -------------- POST Operations ----------------------------- **/

	/**
	 * Adding new Configurations to the service by passing serivce name and
	 * spireConfiguration instance.
	 * 
	 * Sample URL pattern
	 * :http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations
	 * 
	 * @param spireConfiguration
	 * @return
	 * @throws Exception
	 */
	public SpireConfiguration addNewConfiguration(String serviceName, SpireConfiguration spireConfiguration,String serviceEndPoint)
			throws Exception {
		Gson gson = new Gson();
		SpireConfiguration resultConfig = null;
		serviceEndPoint = serviceEndPoint + "/" + serviceName + "/configurations";
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		Logging.log("Request Input  URL >>> " + gson.toJson(spireConfiguration));
		response = executePOST(serviceEndPoint, false, Entity.entity(spireConfiguration, MediaType.APPLICATION_JSON));
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201) {
			resultConfig = response.readEntity(SpireConfiguration.class);
			return resultConfig;
		} else if (response.getStatus() == 400) {
			return resultConfig;
		} else {
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			Assert.fail("addNewConfiguration service is failing !!!!");
			return null;
		}

	}
	
	

	/**
	 * adding configurations to global service
	 * 
	 * @param spireConfiguration
	 * @return
	 * @throws Exception
	 */
	public SpireConfiguration addGlobalConfigurations(SpireConfiguration spireConfiguration) throws Exception {
		SpireConfiguration resultConfig = null;
		String serviceEndPoint = this.endPointURL + "/global/configurations";
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executePOST(serviceEndPoint, false, Entity.entity(spireConfiguration, MediaType.APPLICATION_JSON));
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201) {
			resultConfig = response.readEntity(SpireConfiguration.class);
			return resultConfig;
		} else if (response.getStatus() == 400) {
			return resultConfig;
		} else {
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			Assert.fail("addGlobalConfigurations Service is failing !!!");
			return resultConfig;
		}

	}

	/**
	 * It creates new service.
	 * 
	 * URL pattern :
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>
	 * 
	 * @return
	 * @throws Exception
	 */
	public Response addNewService(String serviceName) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		Entity<String> serviceEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
		response = executePOST(serviceEndPoint, false, serviceEntity);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201) {
			Assert.assertTrue(true);
			return response;
		} else {
			//Assert.fail("addNewService failid !! service not created !!!");
			return response;
		}
	}
	
	
	/**
	 * It creates new service.
	 * 
	 * URL pattern :
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>
	 * 
	 * @return
	 * @throws Exception
	 */
	public Response addNewService(String serviceName,String serviceEndPoint) throws Exception {
		serviceEndPoint = serviceEndPoint + "/" + serviceName;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		Entity<String> serviceEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
		response = executePOST(serviceEndPoint, false, serviceEntity);
		logger.info("response code >>>" + response.getStatus());
		return response;
	}

	/** --------------------PUT Operation -------------------------- **/

	/**
	 * To update configuration by passing service name , id and the
	 * SpireConfiguration contains the configurations details to update.
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations/<id>
	 * 
	 * @param args
	 * @throws Exception
	 */
	public SpireConfiguration updateConfigurations(String serviceName, String id, SpireConfiguration config)
			throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName + "/configurations" + "/" + id;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		Entity<SpireConfiguration> serviceEntity = Entity.entity(config, MediaType.APPLICATION_JSON);
		response = executePUT(serviceEndPoint, false, serviceEntity);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			SpireConfiguration spireConfigsUpdated = response.readEntity(SpireConfiguration.class);
			return spireConfigsUpdated;
		} else {
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			logger.info("ErrorEntity Code ::" + errorEntity.getCode());
			logger.info("ErrorEntity Message ::" + errorEntity.getMessage());
			return null;
		}

	}

	/** -------------------- DELETE operations -------------------------- **/

	/**
	 * To delete configuration of a serivce by passing service name and id
	 * 
	 * Sample URL pattern
	 * >>>http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations/<id>
	 * 
	 * @param args
	 * @throws Exception
	 */
	public Response deleteConfiguration(String serviceName, String id) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName + "/configurations" + "/" + id;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeDELETE(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 204) {
			Response deleted = response.readEntity(Response.class);
			return deleted;
		} else {
			return response;
		}
	}
	
	
	
	/** -------------------- DELETE operations -------------------------- **/

	/**
	 * To delete configuration of a serivce by passing service name and id, Service end point 
	 * 
	 * Sample URL pattern
	 * >>>http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>/configurations/<id>
	 * 
	 * @param args
	 * @throws Exception
	 */
	public Response deleteConfiguration(String serviceName, String id,String serviceEndPoint) throws Exception {
		serviceEndPoint = serviceEndPoint + "/" + serviceName + "/configurations" + "/" + id;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeDELETE(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 204) {
			Response deleted = response.readEntity(Response.class);
			return deleted;
		} else {
			return response;
		}
	}
	
	/**
	 * To delete the service
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>
	 * 
	 * @return
	 * @throws Exception
	 */
	public Response deleteService(String serviceName,String serviceEndPoint) throws Exception {
		serviceEndPoint = serviceEndPoint + "/" + serviceName;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeDELETE(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
	   return response;
		
	}
	
	/**
	 * To delete the service
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/spire-config-web/api/v1/config/services/
	 * <serviceName>
	 * 
	 * @return
	 * @throws Exception
	 */
	public Response deleteService(String serviceName) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + serviceName;
		Logging.log("EndPoint URL >>> " + serviceEndPoint);
		response = executeDELETE(serviceEndPoint, false);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 204) {
			Response newService = response.readEntity(Response.class);
			return newService;
		} else {
			return response;
		}
	}

	public static void main(String[] args) throws Exception {
		SpireConfiguration config = new SpireConfiguration();
		config.setIpAddress("x");
		config.setTenant("teststs");
		config.setKey("testststs");
		config.setValue("testststst");

		SpireConfigWebServiceConsumer spire = new SpireConfigWebServiceConsumer();

		spire.getAllServices();
		// spire.addNewService("raghavpradeep_service");
		// spire.addNewConfiguration("raghavpradeep_service", config);
		// spire.getAllConfigsForService("raghavpradeep_service");
		//
		// spire.updateConfigurations("raghavpradeep_service", "", config);
		// spire.deleteConfiguration("raghavpradeep_service", "");
		// spire.deleteService("testing");

		// spire.getConfigurations("raghavpradeep_service",
		// "075724c7-33ea-4451-8292-f8cec26c4270");
	}

}

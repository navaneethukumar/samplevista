package com.spire.common;

import java.util.Collection;

import javax.ws.rs.core.Response;

import spire.commons.config.entities.CollectionEntity;
import spire.commons.config.entities.SpireConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.biz.consumers.SpireConfigWebServiceConsumer;

public class ConfigHelper extends BaseServiceConsumerNew {

	public static String CONFIG_BASE_PATH = "http://192.168.2.75:8182/spire-biz/spire-config-web/api/v1/config/services";
	SpireConfigWebServiceConsumer client = new SpireConfigWebServiceConsumer();

	public CollectionEntity<SpireConfiguration> getAllConfig(String serviceName)
			throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		// get all the config
		Response response = client.getAllConfigsForService(serviceName,
				CONFIG_BASE_PATH);

		String strResponse = response.readEntity(String.class);

		mapper.registerModule((Module) new JavaTimeModule());

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		Logging.log("strResponse" + strResponse);

		CollectionEntity<SpireConfiguration> configsForService = mapper
				.readValue(
						strResponse,
						new TypeReference<CollectionEntity<SpireConfiguration>>() {
						});

		return configsForService;

	}

	public void deleteCofigService(String serviceName) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		// get all the config
		CollectionEntity<SpireConfiguration> configsForService = getAllConfig(serviceName);

		// delete all the keys from service

		Collection<SpireConfiguration> items = configsForService.getItems();

		for (SpireConfiguration configuration : items) {

			client.deleteConfiguration(serviceName, configuration.getId(),
					CONFIG_BASE_PATH);
		}

		// get all the config
		CollectionEntity<SpireConfiguration> configsAfterDelete = getAllConfig(serviceName);

		if (configsAfterDelete.getTotalResults()==0) {
			
			
			System.out.println("Delete configuration from " + serviceName
					+ " Successfully");
			
			
			// Delete the service

			Response deleteResponse = client.deleteService(serviceName,CONFIG_BASE_PATH);

			if (deleteResponse.getStatus() == 204) {
				
				System.out.println("Delete service" + serviceName
						+ " Successfully");
			} else {

				System.out.println("Delete service " + serviceName + " Failed");
			}
		} else {
			
			System.out.println("Delete configuration from " + serviceName
					+ " Failed");
		}

	}

	public String getconfig(String serviceName, String key) throws Exception {

		SpireConfigWebServiceConsumer client = new SpireConfigWebServiceConsumer();
		ObjectMapper mapper = new ObjectMapper();

		// / get all the config
		CollectionEntity<SpireConfiguration> configListResponse = client
				.getAllConfigsForService(serviceName);

		Collection<SpireConfiguration> configList = configListResponse
				.getItems();

		for (SpireConfiguration config : configList) {

			if (config.getKey().equals(key)) {
				return config.getValue();
			}

		}

		return null;

	}

	public static void main(String[] args) throws Exception {

		String serviceName = "profiles-entity";
		String sourceHost = "http://192.168.2.75:8182/spire-config-web/api/v1/config/services";
		String destinationHost = "http://192.168.2.183:8082/spire-config-web/api/v1/config/services";
		SpireConfigWebServiceConsumer client = new SpireConfigWebServiceConsumer();

		ObjectMapper mapper = new ObjectMapper();
		// / get all the config
		Response response = client.getAllConfigsForService(serviceName,
				sourceHost);

		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		Logging.log("strResponse" + strResponse);

		CollectionEntity<SpireConfiguration> configsForService = mapper
				.readValue(
						strResponse,
						new TypeReference<CollectionEntity<SpireConfiguration>>() {
						});

		// create the given service name
		SpireConfiguration spireConfiguration;
		client.addNewService(serviceName, destinationHost);

		Collection<SpireConfiguration> items = configsForService.getItems();

		for (SpireConfiguration configuration : items) {

			client.addNewConfiguration(serviceName, configuration,
					destinationHost);
		}

		System.out.println("Done________________--");

	}

}

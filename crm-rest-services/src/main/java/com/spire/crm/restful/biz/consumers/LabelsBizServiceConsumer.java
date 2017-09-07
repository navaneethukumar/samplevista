package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.commons.labels.beans.Label;
import spire.commons.labels.common.CollectionEntity;
import spire.crm.labels.biz.bean.EntityLabel;
import spire.crm.labels.biz.bean.PopularLabel;
import spire.crm.profiles.bean.Profile;

public class LabelsBizServiceConsumer extends BaseServiceConsumerNew {

	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("LABEL_WEB_BIZ");
	Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8081/labels-biz-web/api/labels
	 * 
	 * @param URL
	 */
	public LabelsBizServiceConsumer() {
		Logging.log("Service end point URL >>>" + this.endPointURL);
	}

	/**
	 * Create Label
	 * 
	 * @param labelName
	 * @return labelResponse
	 */
	public String createLabel(String labelName) {

		String serviceEndPoint = this.endPointURL + "/_create" + "?labelName=" + labelName;
		Logging.log(" CreateLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(200, response.getStatus(), "Labels throwing status code: " + response.getStatus());
		String labelResponse = response.readEntity(String.class);
		return labelResponse;

	}

	/**
	 * Create Label with EntityLabel response.
	 * 
	 * @param labelName
	 * @return labelResponse
	 */
	public EntityLabel createLabel_(String labelName) {

		String serviceEndPoint = this.endPointURL + "/_create" + "?labelName=" + labelName;
		Logging.log(" CreateLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(200, response.getStatus(), "Labels throwing status code: " + response.getStatus());
		EntityLabel labelResponse = response.readEntity(EntityLabel.class);
		return labelResponse;

	}

	/**
	 * Attach Label
	 * 
	 * @param profileId
	 * @param labelId
	 * @return labelResponse
	 */
	public Label attachLabel(String profileId, String labelId) {

		String serviceEndPoint = this.endPointURL + "/_attach?profileid=" + profileId + "&labelid=" + labelId;
		Logging.log("AttachLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (response.getStatus() == 200) {
			Label labelResponse = response.readEntity(Label.class);
			return labelResponse;
		} else {
			return null;
		}

	}

	/**
	 * Detach Label
	 * 
	 * @param profileId
	 * @param labelId
	 * @return labelResponse
	 */
	public void detachLabel(String profileId, String labelId) {

		String serviceEndPoint = this.endPointURL + "/_detach?profileid=" + profileId + "&labelid=" + labelId;
		Logging.log("DetachLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getProfilesByLabelId
	 * 
	 * @param labelId
	 * @return labelResponse
	 */
	public CollectionEntity<Profile> getProfilesByLabelId(String labelId, String limit, String offset) {

		String serviceEndPoint = this.endPointURL + "/_profilesByLabelId/" + labelId + "?limit=" + limit + "&offset="
				+ offset;
		Logging.log("getProfilesByLabelId endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		String strResponse = response.readEntity(String.class);
		System.out.println("strResponse: " + strResponse);
		mapper.registerModule((Module) new JavaTimeModule());
		CollectionEntity<Profile> labelDetails = null;
		try {
			labelDetails = mapper.readValue(strResponse, new TypeReference<CollectionEntity<Profile>>() {
			});
		} catch (Exception e) {
			Assert.fail("Error in reading from response !! " + e);
		}

		return labelDetails;

	}

	/**
	 * Get Labels by profileId
	 * 
	 * @param profileId
	 * @return labelResponse
	 */
	public CollectionEntity<EntityLabel> getLabelsByProfileId(String profileId) {

		String serviceEndPoint = this.endPointURL + "/_labelsByProfileId/" + profileId;
		Logging.log("getLabelsByProfileId endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		// false);
		CollectionEntity<EntityLabel> labelDetails = null;
		if (response.getStatus() == 200) {
			try {
				Logging.log("strResponse#" + strResponse);

				labelDetails = mapper.readValue(strResponse, new TypeReference<CollectionEntity<EntityLabel>>() {
				});
			} catch (Exception e) {
				Assert.fail("Error in reading from response !! " + e);
			}
			return labelDetails;
		} else {
			return null;
		}

	}

	/**
	 * List PopularLabels
	 * 
	 * @param limit
	 * @param offset
	 * @return labelResponse
	 */
	public CollectionEntity<PopularLabel> listPopularLabels(String limit, String offset) {

		String serviceEndPoint = this.endPointURL + "/_popularlabels?limit=" + limit + "&offset=" + offset;
		Logging.log("listPopularLabels endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		CollectionEntity<PopularLabel> labelDetails = null;
		try {
			labelDetails = mapper.readValue(strResponse, new TypeReference<CollectionEntity<PopularLabel>>() {
			});
		} catch (Exception e) {
			Assert.fail("Error in reading from response !! " + e);
		}
		return labelDetails;

	}

	/**
	 * Attach bulk Labels
	 * 
	 * @param label
	 * @return labelResponse
	 */
	public Label attachBulkLabels(Label label) {

		String serviceEndPoint = this.endPointURL + "/_attachbulk";
		Logging.log("AttachLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(label, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Label labelResponse = response.readEntity(Label.class);
		return labelResponse;

	}

	/**
	 * Detach Bulk Labels
	 * 
	 * @param label
	 * @return labelResponse
	 */
	public void detachBulkLabels(Label label) {

		String serviceEndPoint = this.endPointURL + "/_detachbulk";
		Logging.log("DetachLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		executePOST(serviceEndPoint, true, Entity.entity(label, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

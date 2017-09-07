package com.spire.crm.restful.entity.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

import spire.commons.labels.beans.Label;
import spire.commons.labels.beans.LabelDetails;
import spire.commons.labels.beans.LabelFilter;
import spire.commons.labels.beans.LabelFilterResponse;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

public class LabelsEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory.getLogger(LabelsEntityServiceConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("LABEL_WEB_ENTITY");
	public static Gson gson = new Gson();

	public LabelsEntityServiceConsumer() {
		logger.info(Key.METHOD, "LabelsEntityServiceConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + this.endPointURL);
	}

	/**
	 * Create Label
	 * 
	 * @param labelDetails
	 * @return labelResponse
	 */
	public Label createLabel(Label labelDetails) {
		String serviceEndPoint = this.endPointURL + "_create";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" createLabel endPointURL  >>>" + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" createLabel Request  >>>" + gson.toJson(labelDetails));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelDetails, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 201) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		Label labelResponse = response.readEntity(Label.class);
		return labelResponse;
	}

	/**
	 * Remove Label
	 * 
	 * @param labelDetails
	 * @return labelResponse
	 */
	public Response removeLabel(Label labelDetails) {
		String serviceEndPoint = this.endPointURL + "_remove";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("removeLabel endPointURL  >>>" + serviceEndPoint);
		Logging.log("removeLabel Request  >>>" + gson.toJson(labelDetails));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelDetails, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			return response;
		}
		return null;
	}

	/**
	 * List Labels
	 * 
	 * @param labelDetails
	 * @return labelResponse
	 */
	public Label listLabels(Label labelDetails) {
		String serviceEndPoint = this.endPointURL + "_list";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("listLabels Request  >>>" + gson.toJson(labelDetails));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelDetails, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		Label labelResponse = response.readEntity(Label.class);
		return labelResponse;
	}

	/**
	 * List Labels with isAllLabels flag
	 * 
	 * @param labelDetails
	 * @return labelResponse
	 */
	public Label listLabels_isAllLabels(Label labelDetails, String isAllLabels, String limit, String offset) {
		String serviceEndPoint = this.endPointURL + "_list?isAllLabels=" + isAllLabels + "&limit=" + limit + "&offset="
				+ offset;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("listLabels Request  >>>" + gson.toJson(labelDetails));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelDetails, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		Label labelResponse = response.readEntity(Label.class);
		return labelResponse;
	}

	/**
	 * List Labels with isAllUsers flag
	 * 
	 * @param labelDetails
	 * @return labelResponse
	 */
	public Label listLabels_isAllUsers(Label labelDetails, String isAllUsers, String limit, String offset) {
		String serviceEndPoint = this.endPointURL + "_list?isAllUsers=" + isAllUsers + "&limit=" + limit + "&offset="
				+ offset;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("listLabels Request  >>>" + gson.toJson(labelDetails));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelDetails, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		Label labelResponse = response.readEntity(Label.class);
		return labelResponse;
	}

	/**
	 * List Labels with isAllUsers flag
	 * 
	 * @param labelDetails
	 * @return labelResponse
	 */
	public Label listLabels_Sort(Label labelDetails, String sort, String limit, String offset) {
		String serviceEndPoint = this.endPointURL + "_list?sort=" + sort + "&limit=" + limit + "&offset=" + offset;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("listLabels Request  >>>" + gson.toJson(labelDetails));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelDetails, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		Label labelResponse = response.readEntity(Label.class);
		return labelResponse;
	}

	/**
	 * List Labels by filters
	 * 
	 * @param labelFilterRequest
	 * @return labelFilterResponse
	 */
	public LabelFilterResponse listLabelsByFilters(LabelFilter labelFilterRequest) {
		String serviceEndPoint = this.endPointURL + "_listbyfilters";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("listLabels Request  >>>" + gson.toJson(labelFilterRequest));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(labelFilterRequest, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		LabelFilterResponse labelFilterResponse = response.readEntity(LabelFilterResponse.class);
		return labelFilterResponse;
	}

	/**
	 * getTypeAheadLabelNames
	 * 
	 * @param labelName
	 * @return labelDetails
	 */
	public List<LabelDetails> getTypeAheadLabelNames(String labelName) {
		String serviceEndPoint = this.endPointURL + "_listTypeAheadNames?labelName="+labelName;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("listLabels Request  >>>" + gson.toJson(labelName));
		Response response = executeGET(serviceEndPoint, true);
		if (response.getStatus() != 200) {
			Assert.fail("Throwing status: " + response.getStatus());
		}
		List<LabelDetails> labelDetails = response.readEntity(new GenericType<List<LabelDetails>>() {
		});
		return labelDetails;

	}

}

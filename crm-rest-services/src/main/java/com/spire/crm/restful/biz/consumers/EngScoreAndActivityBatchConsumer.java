package com.spire.crm.restful.biz.consumers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.util.CRMEngagementBatchRequest;

import spire.commons.config.entities.ErrorEntity;

/**
 * http://192.168.2.124:8085/spire-config-web/api/swagger.json
 * 
 * @author Pradeep
 *
 */
public class EngScoreAndActivityBatchConsumer extends BaseServiceConsumerNew {

	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("BATCH");
	List<com.spire.crm.restful.util.CRMEngagementBatchRequest> CRMEngagementBatchRequest = null;

	public EngScoreAndActivityBatchConsumer() {
		Logging.info("Service end point URL >>>" + endPointURL);
		super.REQ_HEADERS = false;

	}

	/**
	 * 
	 * @param lastMinute
	 * @param CRMEngagementBatchRequest
	 * @return
	 * @throws Exception
	 */
	public Response engagementScoreBatchLastMinute(String lastMinutes) {
		String serviceEndPoint = this.endPointURL + "/crm-engagementscore-batch/api/engagementscorebatch?lastMinutes="
				+ lastMinutes;
		Logging.info("Service EndPoint URL >>>" + serviceEndPoint);
		response = executePOST(serviceEndPoint, false,
				Entity.entity(createCRMEngagementBatchRequest(), MediaType.APPLICATION_JSON));
		Logging.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			Assert.assertTrue(true);
			return response;
		} else {
			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);
			Logging.info("Error Code >>" + errorEntity.getCode());
			Logging.info("Error Message >>" + errorEntity.getMessage());
			return null;
		}

	}

	public List<com.spire.crm.restful.util.CRMEngagementBatchRequest> createCRMEngagementBatchRequest() {
		List<CRMEngagementBatchRequest> listCRMEngagementBatchRequest = new ArrayList<CRMEngagementBatchRequest>();
		String userName = readingProperties().getProperty("loginId");
		String password = "spire@123";
		CRMEngagementBatchRequest crmEngagementBatchRequest = new CRMEngagementBatchRequest();
		crmEngagementBatchRequest.setUserName(userName);
		crmEngagementBatchRequest.setPassword(password);
		listCRMEngagementBatchRequest.add(crmEngagementBatchRequest);
		return listCRMEngagementBatchRequest;

	}
}

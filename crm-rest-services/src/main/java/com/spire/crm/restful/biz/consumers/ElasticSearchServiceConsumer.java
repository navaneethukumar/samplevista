package com.spire.crm.restful.biz.consumers;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

public class ElasticSearchServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(ElasticSearchServiceConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("ES_SEARCH");
	String index=ReadingServiceEndPointsProperties.getServiceEndPoint("INDEX_NAME");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/crm-search-biz-web/api/profiles/_search
	 * 
	 * @param URL
	 */
	public ElasticSearchServiceConsumer() {
		logger.info(Key.METHOD, "CrmNotificationWebBizServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);
	

	}


	/**
	 * Delete candidate in ES by candidate ID
	 */
	public Response deleteCandidate(String candidateId) {

		String URL = endPointURL + index + "/candidate/" + candidateId;
		System.out.println(" Search endPointURL  >>>" + URL);
		Logging.log(" Search endPointURL  >>>" + URL);
		Response response = executeDELETE(URL, true);
		System.out.println(response.getStatus());
		return response;

	}
	
	/**
	 * Delete candidate in ES by candidate ID
	 */
	public Response getCandidate(String candidateId) {

		String URL = endPointURL+index+"/candidate/"+candidateId;
		
		Logging.log(" Search endPointURL  >>>" + endPointURL);
		Response response = executeGET(URL, true);
		return response;

	}
	
	/**
	 * Update candidate in ES by candidate ID
	 */
	public Response updateCandidate(String candidateId,String jsonSearchRequest) {

		String URL = endPointURL+index+"/candidate/"+candidateId;
		
		Logging.log(" Search endPointURL  >>>" + endPointURL);
		Response response = executePOST(URL, true, Entity.entity(jsonSearchRequest, MediaType.APPLICATION_JSON));
		if(response.getStatus()==200){
			Assert.assertEquals(response.getStatus(),200,"throwing status "+response.getStatus());
		}else {
			Assert.fail("throwing status "+response.getStatus());
		}
		return response;

	}
	
	/**
	 * Delete candidate in ES by candidate ID
	 */
	public Response getCandidatesBySearch(String jsonSearchRequest) {

		String URL = endPointURL+index+"/candidate/_search";
		Logging.log(" Search endPointURL  >>>" + endPointURL);
		Response response = executePOST(URL, true, Entity.entity(jsonSearchRequest, MediaType.APPLICATION_JSON));
		if(response.getStatus()==200){
			Assert.assertEquals(response.getStatus(), 200 ,"throwing wrong status");
		}else{
			Assert.fail();
		}
		System.out.println(response.getStatus());
		return response;
	}


	public static void main(String[] args) {
		
		ElasticSearchServiceConsumer consumer = new ElasticSearchServiceConsumer();
		Response response=consumer.getCandidate("6000:6005:a2ba4e8a259347418c015c7769005f30");
		
		String profile = response.readEntity(String.class);
		
		System.out.println(profile);
		
	}
	



}

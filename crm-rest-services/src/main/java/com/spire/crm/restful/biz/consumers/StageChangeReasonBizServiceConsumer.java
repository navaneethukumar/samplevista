package com.spire.crm.restful.biz.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

/**
 * 
 * http://192.168.2.69:8181/crm-biz/rules/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class StageChangeReasonBizServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(StageChangeReasonBizServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("STAGE_CHANGE_REASON");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.69:8181/crm-biz/rules/api/stage_change_reasons
	 * 
	 * @param URL
	 */
	public StageChangeReasonBizServiceConsumer() {

		logger.info(Key.METHOD,
				"StageChangeReasonBizServiceConsumer constructor");

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * Add Reasons 
	 *  
	 * @return
	 */
	public Response addReason(List<String> reasonList) {
		Logging.log(" Add New Reason ");
		String serviceEndPoint = this.endPointURL + "/add";
		Logging.log("Service end point is >>>" + serviceEndPoint);
		Logging.log(" Request input for add Reason is >>> "
				+ gson.toJson(reasonList));
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(reasonList, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * get the reasons 
	 * 
	 * @return
	 */
	public Response getAllReasons() {
		Logging.log(" get All Reasons ");
		String serviceEndPoint = this.endPointURL + "/list";
		Logging.log("Service end point is >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * remove reasons 
	 * 
	 * @param createRule
	 * @return
	 */
	public Response removeReasons(List<String> reasonList) {

		Logging.log(" remove Reasons ");
		String serviceEndPoint = this.endPointURL + "/remove";
		Logging.log("Service end point is >>>" + serviceEndPoint);
		Logging.log(" Request input for add Reason is >>> "
				+ gson.toJson(reasonList));
		Response response = executePOST(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity(reasonList, MediaType.APPLICATION_JSON));
		return response;

	}

}

package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.user.service.beans.CRMUserDetailsBean;


/**
 * 
 * http://192.168.2.185:8085/blob-store-web/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class UserServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(UserServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("USER_SERVICE_BIZ");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>> http://192.168.2.124:8080//crm-user-service/api
	 * 
	 * @param URL
	 */
	public UserServiceConsumer() {
		logger.info(Key.METHOD, "UserServiceConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + endPointURL);

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * get all the Rules under Tenant
	 * 
	 * @return
	 */
	public Response getUserDetails(String userId) {
		Logging.log("getUserDetails");
		String serviceEndPoint = this.endPointURL + "/" + userId;
		Logging.log("serviceEndPoint   >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * get the Rules By Tenant and Rule Name
	 * 
	 * @return
	 */
	public Response createUpdate(CRMUserDetailsBean userDetailsBean) {
		Logging.log("createUpdate ");
		String serviceEndPoint = this.endPointURL;
		Logging.log("serviceEndPoint   >>>" + serviceEndPoint);
		Logging.log("Update input   >>>" + gson.toJson(userDetailsBean));
		
		Response response = executePOST(serviceEndPoint, true, 
				Entity.entity(userDetailsBean, MediaType.APPLICATION_JSON));
		return response;

	}
}

package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.user.service.beans.CRMUserDetailsBean;


/**
 * 
 * http://192.168.2.65:8083/user-service-web/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class TalentUserServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(TalentUserServiceConsumer.class);
	Response response = null;
	String endPointURL = null;
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>> http://192.168.2.65:8083/user-service-web/api/authorization
	 * 
	 * @param URL
	 */
	public TalentUserServiceConsumer(String endpoint) {
		logger.info(Key.METHOD, "Talent UserServiceConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + endpoint);
		
		this.endPointURL =endpoint;

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * get all the Rules under Tenant
	 * 
	 * @return
	 */
	public Response getAllTenants() {
		Logging.log("getUserDetails");
		String serviceEndPoint = this.endPointURL + "/getalltenants";
		Logging.log("serviceEndPoint   >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, 
				Entity.entity(null, MediaType.APPLICATION_JSON));
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

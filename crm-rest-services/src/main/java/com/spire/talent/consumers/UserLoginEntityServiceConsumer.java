package com.spire.talent.consumers;

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
import spire.commons.userservice.bean.LoginRequestBean;

/**
 * 
 * http://192.168.2.65:8083/user-service-web/api/authentication/login
 * 
 * @author Manikanta
 *
 */
public class UserLoginEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(UserLoginEntityServiceConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("TALENT_USER_SERVICE");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.65:8083/user-service-web/api/authentication/login
	 * 
	 * @param URL
	 */
	public UserLoginEntityServiceConsumer() {
		logger.info(Key.METHOD, "BlobStoreEntityServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);
		

	}

	/** ------------------------Generate Token ---------------------- **/

	
	/**
	 * Authentication 
	 * 
	 * @param createRule
	 * @return
	 */
	public Response getToken(LoginRequestBean loginRequestBean) {

		Logging.log(" Authentication endPointURL  >>>" + endPointURL);
		Logging.log(" Authentication Request  >>>" + gson.toJson(loginRequestBean));
		Response response = executePOST(endPointURL, true,
				Entity.entity(loginRequestBean, MediaType.APPLICATION_JSON));
		return response;

	}


}

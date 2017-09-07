package com.spire.crm.restful.biz.consumers;

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
 * http://192.168.2.75:8181/crm-proxy-service/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class CRMProxyServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CRMProxyServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_PROXY_SERVICE");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>> http://192.168.2.75:8181/crm-proxy-service/api
	 * 
	 * @param URL
	 */
	public CRMProxyServiceConsumer() {
		logger.info(Key.METHOD, "BlobStoreEntityServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + endPointURL);

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * get all the Rules under Tenant
	 * 
	 * @return
	 */
	public Response markEmailAsRead(String messageId, String TenantId,
			String userID) {
		Logging.log("Getting the  list of Date Ranges ");
		String serviceEndPoint = this.endPointURL + "/email/markasread/"
				+ messageId + "/" + TenantId + "/" + userID + ".png";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}
}

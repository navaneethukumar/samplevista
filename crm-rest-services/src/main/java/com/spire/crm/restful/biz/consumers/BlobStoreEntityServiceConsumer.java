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
import spire.crm.entity.blob.store.entity.beans.BlobStoreEntity;

/**
 * 
 * http://192.168.2.185:8085/blob-store-web/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class BlobStoreEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(BlobStoreEntityServiceConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("BLOB_STORE_ENTITY");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.185:8085/blob-store-web/api/blob_store
	 * 
	 * @param URL
	 */
	public BlobStoreEntityServiceConsumer(String URL) {
		logger.info(Key.METHOD, "BlobStoreEntityServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + URL);
		//this.endPointURL = URL;

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * get all the Rules under Tenant
	 * 
	 * @return
	 */
	public Response getRuleByTenant(int tenantID) {
		Logging.log("Getting the  list of Date Ranges ");
		String serviceEndPoint = this.endPointURL + "?tenant_id" + tenantID;
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * get the Rules By Tenant and Rule Name
	 * 
	 * @return
	 */
	public Response getARule(int tenantId, String ruleName) {
		Logging.log("Getting the  rule By Tenant ID and RuleName ");
		String serviceEndPoint = this.endPointURL + "/" + ruleName
				+ "?tenant_id=" + tenantId;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Create Blob Store Rule
	 * 
	 * @param createRule
	 * @return
	 */
	public Response createRule(BlobStoreEntity rule) {

		Logging.log(" createProfile endPointURL  >>>" + endPointURL);
		Logging.log(" createProfile Request  >>>" + gson.toJson(rule));
		Response response = executePOST(endPointURL, true,
				Entity.entity(rule, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Update Blob Store Rule
	 * 
	 * @param rule
	 * @return
	 */
	public Response updateRule(BlobStoreEntity rule) {

		Logging.log(" createProfile endPointURL  >>>" + endPointURL);
		Logging.log(" createProfile Request  >>>" + gson.toJson(rule));
		Response response = executePUT(endPointURL, true,
				Entity.entity(rule, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Delete Blob Store Rule
	 * 
	 * @param rule
	 * @return
	 */
	public Response deleteRule(int tenantId, String ruleName) {
		Logging.log("Delete the  rule By Tenant ID and RuleName ");
		String serviceEndPoint = this.endPointURL + "/" + ruleName
				+ "?tenant_id=" + tenantId;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Logging.log(" createProfile endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, true);
		return response;

	}

}

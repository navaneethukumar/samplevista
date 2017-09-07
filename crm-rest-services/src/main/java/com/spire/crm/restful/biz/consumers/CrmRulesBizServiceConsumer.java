package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.biz.rules.entites.EngagementRuleEntity;
import spire.crm.biz.rules.entites.WeightageRuleEntity;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

/**
 * 
 * http://192.168.2.185:8085/crm-rules/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class CrmRulesBizServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory.getLogger(CrmRulesBizServiceConsumer.class);
	Response response = null;
	String endPointURL = null;
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>> http://192.168.2.124:8085/crm-rules/api/es_weights
	 * 
	 * @param URL
	 */
	public CrmRulesBizServiceConsumer(String URL) {
		logger.info(Key.METHOD, "CrmRulesBizServiceConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + URL);
		this.endPointURL = URL;

	}

	/** ------------------------Weights Operations ---------------------- **/

	/**
	 * list all the Weights
	 * 
	 * @return
	 */
	public Response getWeights() {
		Logging.log("Getting the  list of weights ");
		String serviceEndPoint = this.endPointURL + "/_list";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * get the rule By Id
	 * 
	 * @return
	 */
	public Response getWeightByID(int tenantID) {
		Logging.log("Getting the  list of weights by Tenant ID ");
		String serviceEndPoint = null;
		serviceEndPoint = this.endPointURL + "/" + tenantID;
		System.out.println("weight: " + serviceEndPoint);
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Create CRM Weight
	 * 
	 * @param createProfile
	 * @return
	 */
	public Response createWeight(WeightageRuleEntity weight) {
		Logging.log(" createProfile endPointURL  >>>" + this.endPointURL);
		Logging.log(" createProfile Request  >>>" + gson.toJson(weight));
		Response response = executePOST(this.endPointURL, true, Entity.entity(weight, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * delete CRM Weight
	 * 
	 * @param createProfile
	 * @return
	 */
	public Response deleteWeight(int tenantID) {
		String serviceEndPoint = this.endPointURL + "/" + tenantID;
		Logging.log(" createProfile endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, true);
		return response;

	}

	/**
	 * Update CRM Weight
	 * 
	 * @param weight
	 * @return
	 */
	public Response updateWeight(WeightageRuleEntity weight) {

		String serviceEndPoint = this.endPointURL + "/" + weight.getTenantId();
		Logging.log(" createProfile endPointURL  >>>" + serviceEndPoint);
		Logging.log(" createProfile Request  >>>" + gson.toJson(weight));
		Response response = executePUT(serviceEndPoint, true, Entity.entity(weight, MediaType.APPLICATION_JSON));
		return response;

	}

	/** ------------------------Rule Operations ---------------------- **/

	/**
	 * http://192.168.2.124:8085/crm-rules/api/swagger.json
	 * 
	 * 
	 */

	/**
	 * list all the Rules
	 * 
	 * @return
	 */
	public Response getRuleACtions() {
		Logging.log("Getting the  list of Rules ");
		String serviceEndPoint = this.endPointURL + "/_actions";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * list all the Rules
	 * 
	 * @return
	 */
	public Response getRuleByTenantID(int tenantID) {
		Logging.log("Getting the Rule By TenantID");
		String serviceEndPoint = this.endPointURL + "/_list?tenant_id=" + tenantID;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * list all the Rules
	 * 
	 * @return
	 */
	public Response getRuleByNameID(int tenantID, String ruleName) {
		Logging.log("Getting the Rule By TenantID and Name");
		String serviceEndPoint = this.endPointURL + "/" + ruleName + "?tenant_id=" + tenantID;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Delete all the Rules
	 * 
	 * @return
	 */
	public Response deleteRule(int tenantID, String ruleName) {
		Logging.log("delete the Rule By TenantID");
		String serviceEndPoint = this.endPointURL + "/" + ruleName + "?tenant_id=" + tenantID;
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, true);
		return response;

	}

	/**
	 * Update CRM rule
	 * 
	 * @param rule
	 * @return
	 */
	public Response updateRule(EngagementRuleEntity rule) {

		String serviceEndPoint = this.endPointURL + "/" + rule.getTenantId();
		Logging.log(" Update Rule  endPointURL  >>>" + serviceEndPoint);
		Logging.log(" Update Rule Request  >>>" + gson.toJson(rule));
		Response response = executePUT(serviceEndPoint, true,
				Entity.entity(serviceEndPoint, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * create CRM rule
	 * 
	 * @param rule
	 * @return
	 */
	public Response createRule(EngagementRuleEntity rule) {

		Logging.log(" create Rule endPointURL  >>>" + this.endPointURL);
		Logging.log(" create Rule Request  >>>" + gson.toJson(rule));
		Response response = executePOST(this.endPointURL, true, Entity.entity(rule, MediaType.APPLICATION_JSON));
		return response;

	}

}

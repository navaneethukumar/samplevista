package com.spire.crm.restful.biz.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.entity.campaign.beans.CampaignLeadVO;
import spire.crm.entity.campaign.beans.PublishCampaignDetails;
import spire.crm.entity.campaign.entities.Campaign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

/**
 * 
 * @author Manikanta Y
 *
 */
public class CampaignBizServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CampaignBizServiceConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("CAMPAIGN_SERVICE_BIZ");
	String reportsEndPointURL = getServiceEndPoint("CAMPAIGN_REPORTS_BIZ");
	static Gson gson = new Gson();
	ObjectMapper mapper = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule((Module) new JavaTimeModule());

	/**
	 * Need to pass service base URL.
	 * 
	 * Swagger URL pattern >>>
	 * http://192.168.2.124:8085/crm-campaign-biz/api/swagger.json
	 * 
	 * @param URL
	 */
	public CampaignBizServiceConsumer() {
		logger.info(Key.METHOD, "CampaignBizServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);

	}

	public CampaignBizServiceConsumer(String username, String password) {
		Logging.log("CampaignBiz Service Consumer constructor with username "
				+ username + " and password " + password);		
		getUserToken(username, password);
	}

	/*
	 * 
	 * Campaign Resource client methods
	 */

	/**
	 * Create campaign
	 *
	 */
	public Response createCampaign(Campaign campaign) {

		String serviceEndPoint = this.endPointURL;
		Logging.log("createCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("createCampaign Request input  >>>" + gson.toJson(campaign));

		logger.info("createCampaign endPointURL  >>>" + serviceEndPoint);
		logger.info("Request input  >>>" + gson.toJson(campaign));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaign, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * Update campaign
	 *
	 */

	public Response updateCampaign(Campaign campaign) {

		String serviceEndPoint = this.endPointURL;
		Logging.log("updateCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("Request input  >>>" + gson.toJson(campaign));

		Response response = executePUT(serviceEndPoint, true,
				Entity.entity(campaign, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * campaign delete
	 *
	 */

	public Response deleteCampaignById(String campaignId) {

		String serviceEndPoint = this.endPointURL + "/" + campaignId;
		Logging.log("delete Campaign endPointURL  >>>" + serviceEndPoint);

		Response response = executeDELETE(serviceEndPoint, true);

		return response;

	}

	/**
	 * campaign bulk delete
	 *
	 */

	public Response deleteBulkCampaignById(List<String> campaignIds) {

		String serviceEndPoint = this.endPointURL + "/_remove";
		Logging.log("bulk delete Campaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("bulk delete input request  >>>" + gson.toJson(campaignIds));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignIds, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * getCampaign
	 *
	 */

	public Response getCampaignList(String limit, String offSet,
			String excludeUnpublish) {

		String serviceEndPoint = this.endPointURL;

		if (limit != null && offSet != null && excludeUnpublish != null) {

			serviceEndPoint = serviceEndPoint + this.endPointURL
					+ "?excludeUnpublish=" + excludeUnpublish + "&limit="
					+ limit + "&offset=" + offSet;
		} else {

			if (limit != null && offSet != null && excludeUnpublish == null) {

				serviceEndPoint = serviceEndPoint + this.endPointURL
						+ "?limit=" + limit + "&offset=" + offSet;

			} else {

				if (limit == null && offSet == null && excludeUnpublish != null) {

					serviceEndPoint = serviceEndPoint + "?excludeUnpublish="
							+ excludeUnpublish;

				}

			}

		}

		Logging.log("getCampaign endPointURL  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;

	}

	/**
	 * Clone Campaign
	 *
	 */

	public Response cloneCampaign(String campaignId, String newCampaignName,
			String flowID) {

		String serviceEndPoint = this.endPointURL + "/_clone/" + campaignId
				+ "/" + newCampaignName + "/" + flowID;
		Logging.log("getCampaign endPointURL  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;

	}

	/**
	 * List Campaign
	 *
	 */

	public Response listCampaign(String excludeUnpublish, String offset,
			String limit) {

		String serviceEndPoint = this.endPointURL;

		if (limit != null && offset != null && excludeUnpublish != null) {

			serviceEndPoint = serviceEndPoint + this.endPointURL
					+ "?excludeUnpublish=" + excludeUnpublish + "&limit="
					+ limit + "&offset=" + offset;
		} else {

			if (limit != null && offset != null && excludeUnpublish == null) {

				serviceEndPoint = serviceEndPoint + this.endPointURL
						+ "?limit=" + limit + "&offset=" + offset;

			} else {

				if (limit == null && offset == null && excludeUnpublish != null) {

					serviceEndPoint = serviceEndPoint + "?excludeUnpublish="
							+ excludeUnpublish;

				}

			}

		}

		Logging.log("getCampaign endPointURL  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;

	}

	/**
	 * Publish Campaign
	 *
	 */

	public Response publishCampaign(
			List<PublishCampaignDetails> publishCampaigns) {

		String serviceEndPoint = this.endPointURL + "/_publish";
		Logging.log("publishCampaign endPointURL  >>>" + serviceEndPoint);

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(publishCampaigns, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * unpublish Campaign
	 *
	 */

	public Response unPublishCampaign(
			List<PublishCampaignDetails> unpublishCampaigns) {

		String serviceEndPoint = this.endPointURL;
		Logging.log("unPublishCampaign endPointURL  >>>" + serviceEndPoint);

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(unpublishCampaigns, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * get Campaign by ID
	 *
	 */

	public Response getCampaignID(String campaignId, String projection) {

		String serviceEndPoint = this.endPointURL + "/" + campaignId
				+ "?projection=" + projection;
		Logging.log("getCampaignID endPointURL  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;

	}

	/*
	 * 
	 * Campaign Lead Resource
	 */

	/*
	 * 
	 * bulkAddLeadToCampaigns
	 */

	public Response bulkAddLeadToCampaigns(CampaignLeadVO campaignLeadVO) {

		String serviceEndPoint = this.endPointURL + "/_addLeads";
		Logging.log("bulkAddLeadToCampaigns endPointURL  >>>" + serviceEndPoint);
		Logging.log("bulkAddLeadToCampaigns Request input  >>>"
				+ gson.toJson(campaignLeadVO));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignLeadVO, MediaType.APPLICATION_JSON));

		return response;
	}

	public Response addLeadToCampaigns(String campaignId, String candidateID) {

		String serviceEndPoint = this.endPointURL + "/_addLead/" + candidateID
				+ "/" + campaignId;
		Logging.log("AddLeadToCampaigns endPointURL  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;
	}

	public Response getCampaignForLead(String candidateId) {

		String serviceEndPoint = this.endPointURL + "/_list/" + candidateId;

		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	public Response getLeadsForCampaign(String candidateId, String limit,
			String offSet) {
		String serviceEndPoint = this.endPointURL + "/" + candidateId
				+ "/leads?limit=" + limit + "&offset=" + offSet;
		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/*
	 * 
	 * 
	 */

	public Response getLeadsAtNode(String candidateId, String nodeId,
			String limit, String offSet) {
		String serviceEndPoint = null;
		if (nodeId == null) {
			serviceEndPoint = this.endPointURL + "/" + candidateId
					+ "/leads?limit=" + limit + "&offset=" + offSet;
		} else {
			serviceEndPoint = this.endPointURL + "/" + candidateId
					+ "/leads?limit=" + limit + "&offset=" + offSet
					+ "&nodeId=" + nodeId;
		}

		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	public Response bulkRemoveLeadsFromCampaigns(CampaignLeadVO campaignLeadVO) {
		String serviceEndPoint = this.endPointURL + "/_removeLeads";
		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Logging.log(" bulkRemoveLeadsFromCampaigns Request input  >>>"
				+ gson.toJson(campaignLeadVO));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignLeadVO, MediaType.APPLICATION_JSON));

		return response;

	}

	public Response getCampaignReport() {
		String serviceEndPoint = this.reportsEndPointURL
				+ "/reports/getreports";
		Logging.log("get Campaign Report endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Freeze campaign
	 *
	 */
	public Response freezeCampaign(List<String> campaignIDs) {

		String serviceEndPoint = this.endPointURL + "/_freeze";
		Logging.log("freezeCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("createCampaign Request input  >>>"
				+ gson.toJson(campaignIDs));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignIDs, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * Un Freeze campaign
	 *
	 */
	public Response unFreezeCampaign(List<String> campaignIDs) {

		String serviceEndPoint = this.endPointURL + "/_unfreeze";
		Logging.log("freezeCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("createCampaign Request input  >>>"
				+ gson.toJson(campaignIDs));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignIDs, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * 
	 * GET /campaigns/getExecutedNodes/{campaignId}/{leadId}
	 * 
	 * @return
	 */
	public Response getExecutedNodes(String campaignId, String leadId) {
		String serviceEndPoint = this.endPointURL + "/getExecutedNodes/"
				+ campaignId + "/" + leadId;
		Logging.log("get Campaign Report endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * 
	 * _replyCampaignEvent
	 * 
	 * @return
	 */
	public Response replyCampaignEvent(String messageID) {
		String serviceEndPoint = this.endPointURL + "/_replyCampaignEvent/"
				+ messageID;

		Logging.log("reply CampaignEvent endPointURL  >>>" + serviceEndPoint);
		Response response = executePUT(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity("null", MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * 
	 * _readCampaignEvent
	 * 
	 * @return
	 */
	public Response readCampaignEvent(String messageID) {
		String serviceEndPoint = this.endPointURL + "/_readCampaignEvent/"
				+ messageID;

		Logging.log("read CampaignEvent endPointURL  >>>" + serviceEndPoint);
		Response response = executePUT(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity("null", MediaType.APPLICATION_JSON));
		return response;

	}

}

package com.spire.crm.restful.entity.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.entity.campaign.beans.CampaignLeadVO;
import spire.crm.entity.campaign.beans.PublishCampaignDetails;
import spire.crm.entity.campaign.entities.Campaign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

/**
 * 
 * @author Manikanta Y
 *
 */
public class CampaignEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CampaignEntityServiceConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("CAMPAIGN_SERVICE_ENTITY");

	String excuterEndPointURL = getServiceEndPoint("CAMPAIGN_EXECUTER_SERVICE");
	static Gson gson = new Gson();

	public ObjectMapper mapper = new ObjectMapper();

	/**
	 * Need to pass service base URL .
	 * 
	 * Swagger URL pattern >>>
	 * http://192.168.2.69:8081/crm-campaign-services/api/swagger.json
	 * 
	 * @param URL
	 */
	public CampaignEntityServiceConsumer() {
		logger.info(Key.METHOD, "CampaignEntityServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);

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

		String serviceEndPoint = this.endPointURL + "/campaigns";
		Logging.log("CreateCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("Create Campaign Request input  >>>"
				+ gson.toJson(campaign));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaign, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * Update campaign
	 *
	 */

	public Response updateCampaign(Campaign campaign) {

		String serviceEndPoint = this.endPointURL + "/campaigns";
		Logging.log("updateCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("Request input  >>>" + gson.toJson(campaign));

		Response response = executePUT(serviceEndPoint, true,
				Entity.entity(campaign, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * campaign bulk delete
	 *
	 */

	public Response deleteBulkCampaign(List<String> campaignIds) {

		String serviceEndPoint = this.endPointURL + "/campaigns/_bulkDelete";
		Logging.log("deleteBulkCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("delete Request input  >>>" + gson.toJson(campaignIds));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignIds, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * getCampaign
	 *
	 */

	public Response getCampaign(String[] campaignlist, String Projection) {

		String serviceEndPoint = this.endPointURL + "/campaigns";
		Logging.log("getCampaign endPointURL  >>>" + serviceEndPoint);
		Logging.log("Request input  >>>" + gson.toJson(campaignlist));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignlist, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * Clone Campaign
	 *
	 */

	public Response cloneCampaign(String campaignId, String newCampaignName,
			String flowID) {

		String serviceEndPoint = this.endPointURL + "/campaigns" + "/_clone/"
				+ campaignId + "/" + newCampaignName + "/" + flowID;
		;
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

		String serviceEndPoint = this.endPointURL + "/campaigns/_list";

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

		String serviceEndPoint = this.endPointURL + "/campaigns";
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

		String serviceEndPoint = this.endPointURL + "/campaigns";
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

		String serviceEndPoint = this.endPointURL + "/campaigns/" + campaignId;

		if (projection != null) {
			serviceEndPoint = serviceEndPoint + "?projection=" + projection;
		}

		Logging.log("unPublishCampaign endPointURL  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;

	}

	/**
	 * Execute the campaign events
	 *
	 */

	public Response executeCampaignEvents() {

		String serviceEndPoint = this.excuterEndPointURL
				+ "/campaigns/_execute";
		Logging.log("Execute CampaignEvents End Point >>>" + serviceEndPoint);

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(null, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			return response;
		} else {
			Assert.fail("Execution of campaign is failed and status code is : "
					+ response.getStatus());
			return response;
		}

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

		String serviceEndPoint = this.endPointURL
				+ "/campaign_leads/addLeadsToCampaigns";
		Logging.log("bulkAddLeadToCampaigns endPointURL  >>>" + serviceEndPoint);
		Logging.log("bulkAddLeadToCampaigns Request input  >>>"
				+ gson.toJson(campaignLeadVO));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignLeadVO, MediaType.APPLICATION_JSON));

		return response;
	}

	public Response getCampaignForLead(String candidateId) {

		String serviceEndPoint = this.endPointURL
				+ "/campaign_leads/getCampaignsForLead/" + candidateId;
		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(null, MediaType.APPLICATION_JSON));
		return response;

	}

	public Response getLeadsForCampaign(String candidateId, String limit,
			String offSet) {
		String serviceEndPoint = this.endPointURL
				+ "/campaign_leads/getLeadsForCampaign/" + candidateId;
		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	public Response bulkRemoveLeadsFromCampaigns(CampaignLeadVO campaignLeadVO) {
		String serviceEndPoint = this.endPointURL
				+ "/campaign_leads/removeLeadsFromCampaigns";
		Logging.log("getCampaignForLead endPointURL  >>>" + serviceEndPoint);
		Logging.log(" bulkRemoveLeadsFromCampaigns Request input  >>>"
				+ gson.toJson(campaignLeadVO));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignLeadVO, MediaType.APPLICATION_JSON));

		return response;

	}

	/*
	 * 
	 * Campaign Event Resource
	 */

	public Response getScheduledEvents() {

		String serviceEndPoint = this.endPointURL
				+ "/campaign_events/getScheduledEvents";
		Logging.log("getScheduledEvents endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	public Response bulkGetCampaigns(List<String> campaignIds, String projection) {
		String serviceEndPoint = this.endPointURL
				+ "/campaigns/_bulkget?projection=" + projection;
		Logging.log("bulkGetCampaigns endPointURL  >>>" + serviceEndPoint);
		Logging.log(" bulkGetCampaigns Request input  >>>"
				+ gson.toJson(campaignIds));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(campaignIds, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * 
	 * _updateEmailReadEvent
	 * 
	 * @return
	 */
	public Response updateEmailReadEvent(String messageID) {
		String serviceEndPoint = this.endPointURL
				+ "/campaign_events/_updateEmailEvent/" + messageID;

		Logging.log("update Email Read Event endPointURL  >>>"
				+ serviceEndPoint);
		Response response = executePUT(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity("null", MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * 
	 * PUT /campaign_events/_updateFeedBackEvent/{messageId}/{sentiment}
	 * 
	 * @return
	 */
	public Response updateFeedBackEvent(String messageID,String sentiment) {
		String serviceEndPoint = this.endPointURL
				+ "/campaign_events/_updateFeedBackEvent/" + messageID+"/"+sentiment;

		Logging.log("update Email Read Event endPointURL  >>>"
				+ serviceEndPoint);
		Response response = executePUT(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity("null", MediaType.APPLICATION_JSON));
		return response;

	}
	
	/**
	 * 
	 * PUT /campaign_events/_updateFormSubmitEvent/{messageId}
	 * 
	 * @return
	 */
	public Response updateFormSubmitEvent(String messageID) {
		String serviceEndPoint = this.endPointURL
				+ "/campaign_events/_updateFormSubmitEvent/" + messageID;

		Logging.log("update Email Read Event endPointURL  >>>"
				+ serviceEndPoint);
		Response response = executePUT(serviceEndPoint, DEFAULT_HEADERS,
				Entity.entity("null", MediaType.APPLICATION_JSON));
		return response;

	}
	
}

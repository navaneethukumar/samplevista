package com.spire.crm.restful.entity.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

import spire.social.entity.Social;

/**
 * @author Santosh C
 *
 */
public class SocialEntityServiceConsumer extends BaseServiceConsumerNew {

	Response response = null;
	public static Gson gson = new Gson();
	String endPointURL = getServiceEndPoint("SOCIAL_WEB_ENTITY");

	public SocialEntityServiceConsumer() {

	}

	/**
	 * Create SocialProfile
	 * 
	 * @param candidateSocial
	 * @return socialId
	 */
	public String createSocialProfile(Social candidateSocial) {

		String serviceEndPoint = this.endPointURL;
		Logging.log(" CreateSocialProfile endPointURL  >>>" + serviceEndPoint);
		Logging.log(" CreateSocialProfile Request  >>>" + gson.toJson(candidateSocial));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(candidateSocial, MediaType.APPLICATION_JSON));
		String socialId = response.readEntity(String.class);

		return socialId;

	}

	/**
	 * Get Social details by giving candidateId
	 * 
	 * @return
	 */
	public Social getSocialProfile(String candidateId, String projectionType) {

		Logging.log("Get Social details of the candidate ");
		String serviceEndPoint = this.endPointURL + "?candidateId=" + candidateId + "&projection=" + projectionType;
		Logging.log("serviceEndPoint  >>>" + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);
		Social socialResponse = response.readEntity(Social.class);

		return socialResponse;

	}

	/**
	 * Patch SocialProfile
	 * 
	 * @param candidateId
	 * @param candidateSocial
	 * @return
	 */
	public Response patchSocialProfile(String candidateId, Social candidateSocial) {

		String serviceEndPoint = this.endPointURL + "?candidateId=" + candidateId;
		Logging.log(" PatchSocialProfile endPointURL  >>>" + serviceEndPoint);
		Logging.log(" Patching to candidateId  >>>" + candidateId);
		Logging.log(" PatchSocialProfile Request  >>>" + gson.toJson(candidateSocial));
		Response response = executePATCH(serviceEndPoint, true,
				Entity.entity(candidateSocial, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * CreateBulk Social profiles
	 * 
	 * @param socialRequestList
	 * @return List of candidateIds
	 */
	public List<String> createBulkSocialProfiles(List<Social> socialRequestList) {

		String serviceEndPoint = this.endPointURL + "/_bulk";
		Logging.log(" CreateBulkSocialProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log(" CreateBulkSocialProfiles Request  >>>" + gson.toJson(socialRequestList));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(socialRequestList, MediaType.APPLICATION_JSON));

		List<String> socialIds = response.readEntity(new GenericType<List<String>>() {
		});
		Assert.assertEquals(response.getStatus(), 201, "Unable to create bulk social profile !!");
		return socialIds;
	}

	/**
	 * GetBulk SocialProfiles
	 * 
	 * @param candidateIds
	 * @return socialDetails
	 */
	public List<Social> getBulkSocialProfiles(List<String> candidateIds) {

		String serviceEndPoint = this.endPointURL + "&projection=full";
		Logging.log("getBulkSocialProfiles endPointURL  >>>" + serviceEndPoint);

		Response response = executePOST(serviceEndPoint, true, Entity.entity(candidateIds, MediaType.APPLICATION_JSON));
		List<Social> socialDetails = response.readEntity(new GenericType<List<Social>>() {
		});
		Logging.log("Bulk GET Social Response Request  >>>" + gson.toJson(socialDetails));
		return socialDetails;
	}
}

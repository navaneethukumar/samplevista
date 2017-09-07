package com.spire.crm.restful.biz.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.profiles.entity.DateInput;
import spire.crm.profiles.entity.EngagementProfile;
import spire.crm.profiles.entity.EngagementTrendInput;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

/**
 * 
 * http://192.168.2.69:8085/crm-profiles/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class CrmProfileEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CrmProfileEntityServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_PROFILE_ENTITY");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.69:8085/crm-profiles/api/engagementprofiles
	 * 
	 * @param URL
	 */
	public CrmProfileEntityServiceConsumer() {
		Logging.log("CrmProfileEntityServiceConsumer constructor Service and  end point URL >>>"
				+ this.endPointURL);

	}

	/** ------------------------GET Operations ---------------------- **/

	public Response getProfilebyIds(String profileID) {
		Logging.log("Getting the  list of Date Ranges ");
		String serviceEndPoint = this.endPointURL
				+ "engagementprofiles?candidateId=" + profileID;
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * To create Profile
	 * 
	 * @param createProfile
	 * @return
	 */
	public Response createProfile(EngagementProfile createProfile) {

		String serviceEndPoint = this.endPointURL + "engagementprofiles";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Logging.log("Create Profile Request >>>" + gson.toJson(createProfile));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(createProfile, MediaType.APPLICATION_JSON));

		return response;

	}
	
	/**
	 * To create Profile bulk
	 * 
	 * @param createProfileBulk
	 * @return
	 */
	public Response createProfileBulk(List<EngagementProfile> engagementProfiles) {
		String serviceEndPoint=endPointURL+"/_bulk";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Logging.log("Create Profile Request >>>" + gson.toJson(engagementProfiles));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(engagementProfiles, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * To Update Profile
	 * 
	 * @param updateProfile
	 * @return
	 */
	public Response updateProfile(EngagementProfile updateProfile) {
		String serviceEndPoint = this.endPointURL + "engagementprofiles";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Logging.log("updateProfile  Request >>>" + gson.toJson(updateProfile));
		Response response = executePATCH(serviceEndPoint, true,
				Entity.entity(updateProfile, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * To Get the Get Engagement score history trend with candidate Id and dates
	 * 
	 * @param listEngagementTrend
	 * @return
	 */
	public Response listEngagementTrend(
			EngagementTrendInput engagementTrendInput) {

		String serviceEndPoint = this.endPointURL
				+ "engagementprofiles/_listEngagementTrend";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Logging.log("Create Profile Request >>>"
				+ gson.toJson(engagementTrendInput));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(engagementTrendInput, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * Get Engagement Profiles with summary by filter
	 * 
	 * @param listByFilter
	 * @return
	 */
	public Response listByFilter(String filterInput) {
		String serviceEndPoint = this.endPointURL
				+ "engagementprofiles/_listByFilter";
		logger.info("listByFilter endPointURL  >>>" + serviceEndPoint);
		Logging.log("listByFilter endPointURL  >>>" + serviceEndPoint);
		Logging.log("Filter Profile Request >>>" + gson.toJson(filterInput));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(filterInput, MediaType.APPLICATION_JSON));
		return response;

	}
	
	/**
	 * List Engagement profiles which do not have broadcast communication
	 * 
	 * @param listProfilesWithNoCommunication
	 * @return
	 */
	public Response listProfilesWithNoCommunication(DateInput input) {
		String serviceEndPoint = this.endPointURL + "/_listProfilesWithNoCommunication";
		logger.info("listByFilter endPointURL  >>>" + serviceEndPoint);
		Logging.log("listByFilter endPointURL  >>>" + serviceEndPoint);
		Logging.log("listProfilesWithNoCommunication input >>>" + gson.toJson(input));
		System.out.println(gson.toJson(input));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(input, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Get Engagement Profiles by candidateIDs
	 * 
	 * POST /engagementprofiles/_listByCandidateIds
	 * 
	 * @param listByCandidateIds
	 * @return
	 */
	public Response listByCandidateIds(List<String> candidateIds) {
		String serviceEndPoint = this.endPointURL
				+ "engagementprofiles/_listByCandidateIds";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Logging.log("Create Profile Request >>>" + gson.toJson(candidateIds));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(candidateIds, MediaType.APPLICATION_JSON));

		return response;

	}

	/**
	 * Get crm reports
	 * 
	 * POST /crm-profile/api/reports/crmreports
	 * 
	 * @param getcrmReports
	 * @return
	 */
	public Response getcrmReports() {
		String serviceEndPoint = this.endPointURL + "reports/crmreports";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Get label reports
	 * 
	 * POST /crm-profile/api/reports/labelreports
	 * 
	 * @param getLabelReports
	 * @return
	 */
	public Response getLabelReports() {
		String serviceEndPoint = this.endPointURL + "reports/labelreports";
		Logging.log("endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Download Label Reports
	 * 
	 * @return response
	 */
	public Response downloadLabelreports() {
		String serviceEndPoint = this.endPointURL
				+ "reports/downloadLabelreports";
		Logging.log(" downloadLabelreports endPointURL  >>>" + serviceEndPoint);
		readingProperties();
		setPropertyValue("Accept", "application/octet-stream");
		Response response = executeGET(serviceEndPoint, true);
		setPropertyValue("Accept", "application/json");
		return response;
	}

	/**
	 * Download CRM Reports
	 * 
	 * @return response
	 */
	public Response downloadcrmReports() {
		String serviceEndPoint = this.endPointURL
				+ "reports/downloadCRMreports";
		Logging.log(" downloadLabelreports endPointURL  >>>" + serviceEndPoint);
		readingProperties();
		setPropertyValue("Accept", "application/octet-stream");
		Response response = executeGET(serviceEndPoint, true);
		setPropertyValue("Accept", "application/json");
		return response;
	}

}

package com.spire.crm.restful.biz.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import crm.pipeline.beans.CRMFilter;
import crm.pipeline.beans.CreateCRM;

/**
 * 
 * http://192.168.2.124:8085/crm-pipeline/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class CrmPipeLineBizServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CrmPipeLineBizServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_PIPELINE_BIZ");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/crm-pipeline/api/pipeline
	 * 
	 * @param URL
	 */
	public CrmPipeLineBizServiceConsumer() {
		logger.info(Key.METHOD, "ActivityStreamBizServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * get Rating ConfigInfo for CRM Pipeline
	 * 
	 * @return
	 */
	public Response getRatingConfigInfo() {
		Logging.log("Getting the  list of Date Ranges ");
		String serviceEndPoint = this.endPointURL + "/_getRatingConfigInfo";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * _list Date Ranges for CRM Pipeline
	 * 
	 * @return
	 */
	public Response listDateRanges() {
		Logging.log("Getting the  list of Date Ranges ");
		String serviceEndPoint = this.endPointURL + "/_listDateRanges";
		Logging.log("endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Get crm profile by ID
	 * 
	 * @param getProfileByID
	 * @return
	 */
	public Response getProfileByID(String profileID) {

		String serviceEndPoint = this.endPointURL + "/" + profileID;
		Logging.log(" getProfileByID endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * Get pipelines by date range
	 * 
	 * @param getProfileByDateRange
	 * @return
	 */
	public Response getProfileByDateRange(String dateRange) {

		String serviceEndPoint = this.endPointURL + "/_list?dateRange="
				+ dateRange;
		Logging.log(" getProfileByID endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}

	/**
	 * list crm stage count
	 * 
	 * @param getProfileByDateRange
	 * @return
	 */
	public Response getCrmStageList() {

		String serviceEndPoint = this.endPointURL + "/_list";
		Logging.log(" getCrmStageList endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			return response;
		} else {
			Assert.fail("get CRM Stage List failed !!! " + response.getStatus());
			return null;
		}

	}

	/**
	 * List profiles by filters
	 * 
	 * @param listProfilesByFilters
	 * @return
	 */
	public Response listProfilesByFilters(CRMFilter filter) {

		String serviceEndPoint = this.endPointURL
				+ "/_getProfiles?limit=100&offset=0";
		Logging.log(" getProfileByID endPointURL  >>>" + serviceEndPoint);
		Logging.log(" listProfilesByFilters Request  >>>" + gson.toJson(filter));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(gson.toJson(filter), MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Create CRM Pipeline profile
	 * 
	 * @param createProfile
	 * @return
	 */
	public Response createProfile(CreateCRM crmProfile) {

		String serviceEndPoint = this.endPointURL + "/_createCRMProfile";
		Logging.log(" createProfile endPointURL  >>>" + serviceEndPoint);
		Logging.log(" createProfile Request  >>>" + gson.toJson(crmProfile));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(crmProfile, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Get crm profiles by IDs
	 * 
	 * @param getProfileByIDs
	 * @return
	 */
	public Response getProfileByIDs(List<String> candidateIDs) {

		String serviceEndPoint = this.endPointURL + "/_listProfiles";
		Logging.log(" getProfileByID endPointURL  >>>" + serviceEndPoint);
		Logging.log(" listProfilesByFilters Request  >>>"
				+ gson.toJson(candidateIDs));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(candidateIDs, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {

			return response;
		} else {
			Assert.fail("get Profile By IDs is failed and status code is :"
					+ response.getStatus());
			return null;

		}

	}

	/**
	 * Update CRM Pipeline profile
	 * 
	 * @param updatedProfile
	 * @return
	 */
	public Response updatedProfile(CreateCRM crmProfile) {

		String serviceEndPoint = this.endPointURL + "/_updateCRMProfile";
		Logging.log(" updatedProfile endPointURL  >>>" + serviceEndPoint);
		Logging.log(" updatedProfile Request  >>>" + gson.toJson(crmProfile));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(crmProfile, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * bulk Create CRM Pipeline profile
	 * 
	 * @param bulkCreateCRMProfiles
	 * @return
	 */
	public Response bulkCreateCRMProfiles(List<CreateCRM> bulkcreateList) {

		String serviceEndPoint = this.endPointURL + "/_bulkCreateCRMProfiles";
		Logging.log(" bulkCreateCRMProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log(" bulkCreateCRMProfiles Request  >>>"
				+ gson.toJson(bulkcreateList));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(bulkcreateList, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Chnage the CRM Pipeline profile Stage Name
	 * 
	 * @param bulkCreateCRMProfiles
	 * @return
	 */
	public Response updateProfileStageName(String profileId,
			String newStageName, String OldStageName) {

		String serviceEndPoint = this.endPointURL + "/_changeStatus/"
				+ profileId;
		if (newStageName != null && OldStageName != null) {
			serviceEndPoint = serviceEndPoint + "?currentStatus="
					+ OldStageName + "&newStatus=" + newStageName;
		} else if (newStageName != null) {

			serviceEndPoint = serviceEndPoint + "?&newStatus=" + newStageName;
		} else if (OldStageName != null) {

			serviceEndPoint = serviceEndPoint + "?currentStatus="
					+ OldStageName;
		}

		if (newStageName.equals("Hold") | newStageName.equals("Rejected")) {
			serviceEndPoint = serviceEndPoint + "&statusChangeReason="
					+ "Spire+Automation+Rejected";
		}

		Logging.log(" updateProfileStageName endPointURL  >>>"
				+ serviceEndPoint);
		Response response = executePUT(serviceEndPoint, true,
				Entity.entity("", MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {

			return response;
		} else if (response.getStatus() == 400) {
			if (!newStageName.equals("invalid")) {
				Assert.fail("Stage Name Update failed and status code is "
						+ response.getStatus());

			}

			return null;

		} else {

			Assert.fail("Stage Name Update failed and status code is "
					+ response.getStatus());
			return null;

		}

	}

}

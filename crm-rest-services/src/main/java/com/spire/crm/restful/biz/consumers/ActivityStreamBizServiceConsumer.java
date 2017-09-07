package com.spire.crm.restful.biz.consumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import crm.activitystream.beans.ActivitySearchFilter;
import crm.activitystream.beans.CRMCreateActivity;
import crm.activitystream.beans.CRMHomeActivities;
import crm.activitystream.beans.CreateActivityInfo;

/**
 * http://192.168.2.124:8085/spire-config-web/api/swagger.json
 * 
 * @author Pradeep
 *
 */
public class ActivityStreamBizServiceConsumer extends BaseServiceConsumerNew {

	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("ACTIVITY_STREAM_BIZ");
	Gson gson = new Gson();

	// /**
	// * Need to pass service base URL .
	// *
	// * Sample URL pattern >>>
	// * http://192.168.2.124:8085/spire-config-web/api/v1/config/services
	// *
	// * @param URL
	// */
	public ActivityStreamBizServiceConsumer() {

	}

	/** ------------------------GET Operations ---------------------- **/

	public CreateActivityInfo _getActivityRatingTypes() {
		Logging.log("Getting Home activities for User: ");
		String serviceEndPoint = this.endPointURL + "/"
				+ "_getActivityRatingTypes";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CreateActivityInfo createActivityInfo = response
					.readEntity(CreateActivityInfo.class);
			return createActivityInfo;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> _listTimePeriod() {
		String serviceEndPoint = this.endPointURL + "/" + "_listTimePeriod";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			List<String> timePeriods = response.readEntity(ArrayList.class);
			return timePeriods;
		} else {
			return null;
		}

	}

	/**
	 * To create activities
	 * 
	 * @param createActivity
	 * @return
	 */
	public Response getCandidateActivities(String candidateId,ActivitySearchFilter activitySearchFilter) {
		
		String serviceEndPoint = this.endPointURL
				+ "/"
				+ "_filter?candidateId="+candidateId+"&limit=500&offset=0&sort=desc";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		
		Logging.log("Request Input >>>: " + gson.toJson(activitySearchFilter));
		
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(activitySearchFilter, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else {
			return null;
		}

	}
	
	/**
	 * To create activities
	 * 
	 * @param createActivity
	 * @return
	 */
	public Response _createActivity(CRMCreateActivity createActivity) {
		Logging.log("serviceEndPoint: " + endPointURL);
		Response response = executePOST(endPointURL, true,
				Entity.entity(createActivity, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			return response;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param activitySearchFilter
	 * @param offset
	 * @param limit
	 * @return
	 */
	public CRMHomeActivities _homeactivities(
			ActivitySearchFilter activitySearchFilter, String offset,
			String limit) {

		String serviceEndPoint = this.endPointURL + "/"
				+ "_homeactivities?offset=" + offset + "&limit=" + limit;
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(activitySearchFilter, MediaType.APPLICATION_JSON));

		Logging.log("response code >>>" + response.getStatus());

		String stringResponse = response.readEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();

		CRMHomeActivities activityFilterResponse = null;
		try {
			activityFilterResponse = mapper.readValue(stringResponse,
					CRMHomeActivities.class);
			System.out.println("activityFilterResponse: "
					+ activityFilterResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return activityFilterResponse;
	}

	public com.spire.crm.activity.biz.pojos.CRMHomeActivities getHomeEmailActivities() {

		String serviceEndPoint = this.endPointURL
				+ "/_homeactivities?limit=50&offset=0";
		Logging.log("serviceEndPoint: " + serviceEndPoint);

		ActivitySearchFilter activitySearchFilter = new ActivitySearchFilter();
		activitySearchFilter.setTimePeriod("null");
		List<String> activityTypes = new ArrayList<String>();
		activityTypes.add("Email");
		activitySearchFilter.setActivityTypes(activityTypes);
		
		Logging.log("getHomeEmailActivities request input >> "
				+ gson.toJson(activitySearchFilter));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(activitySearchFilter, MediaType.APPLICATION_JSON));

		Logging.log("response code >>>" + response.getStatus());

		String stringResponse = response.readEntity(String.class);

		System.out.println(stringResponse);

		ObjectMapper mapper = new ObjectMapper().configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule((Module) new JavaTimeModule());

		com.spire.crm.activity.biz.pojos.CRMHomeActivities homeActivityList = null;

		try {
			homeActivityList = mapper.readValue(stringResponse,
					com.spire.crm.activity.biz.pojos.CRMHomeActivities.class);
		} catch (IOException e) {

			e.printStackTrace();
			Assert.fail("Run Time Exception");
		}

		return homeActivityList;
	}

}

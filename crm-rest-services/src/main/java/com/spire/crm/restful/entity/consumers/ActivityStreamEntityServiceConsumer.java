package com.spire.crm.restful.entity.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.activitystream.Activity;
import spire.commons.activitystream.resources.MetaMapper;
import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.talent.common.beans.CollectionEntity;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.common.TestData;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

import crm.activitystream.beans.InputContainer;

/**
 * 
 * @author Santosh C
 *
 */
public class ActivityStreamEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(ActivityStreamEntityServiceConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("ACTIVITY_STREAM_ENTITY");
	static Gson gson = new Gson();

	public static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * 
	 * @param URL
	 */
	public ActivityStreamEntityServiceConsumer() {
		logger.info(Key.METHOD,
				"ActivityStreamEntityServiceConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + this.endPointURL);
		// this.endPointURL = URL;
	}

	/**
	 * getActivitiesByPerson
	 * 
	 * @param personId
	 * @param offset
	 * @param limit
	 * @return listOfActivities
	 */
	public CollectionEntity<Activity> getActivitiesByPerson(String personId,
			String offset, String limit, TestData data) {
		String serviceEndPoint = this.endPointURL + "/_getByPerson?personId="
				+ personId + "&offset=" + offset + "&limit=" + limit;
		Logging.log("Testcase Description: " + data.getTestSteps());
		Logging.log("getActivitiesByPerson endPointURL  >>>" + serviceEndPoint);
		Logging.log("Getting Activities by personId: " + personId);
		Response response = executeGET(serviceEndPoint, true);

		CollectionEntity<Activity> activities = new CollectionEntity<>();
		if (data.getException().equals("EXCEPTION")) {
			Assert.assertTrue(response.getStatus() > 200,
					"Not throwing Exception !!");
		} else {
			String strResponse = response.readEntity(String.class);
			mapper.registerModule((Module) new JavaTimeModule());
			CollectionEntityPojo listOfActivities = null;
			try {
				listOfActivities = mapper.readValue(strResponse,
						CollectionEntityPojo.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("listOfActivities: " + listOfActivities);

			for (Activity activity : listOfActivities.getItems()) {
				activities.addItem(activity);
			}
		}
		Logging.log("getActivitiesByPerson RESPONSE: "
				+ gson.toJson(activities));
		return activities;
	}

	/**
	 * getActivitiesByActivityId
	 * 
	 * @param activityId
	 * @return activityDetail
	 */
	public Activity getActivitiesByActivityId(String activityId, TestData data) {
		String serviceEndPoint = this.endPointURL + "/" + activityId;
		Logging.log("Testcase Description: " + data.getTestSteps());
		Logging.log("getActivitiesByPerson endPointURL  >>>" + serviceEndPoint);
		Logging.log("Getting Activities by activityId: " + activityId);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Response response = executeGET(serviceEndPoint, true);
		Logging.log("Get by candidateId response code: " + response.getStatus());
		Activity activityResponse = null;
		if (data.getException().equals("EXCEPTION")) {
			Assert.assertTrue(response.getStatus() > 200,
					"Not throwing Exception !!");
		} else {
			String strResponse = response.readEntity(String.class);
			mapper.registerModule((Module) new JavaTimeModule());

			try {
				activityResponse = mapper
						.readValue(strResponse, Activity.class);
			} catch (Exception e) {
				Assert.fail("Error in reading from response !!");
			}
		}
		Logging.log("getActivitiesByActivityId RESPONSE: "
				+ gson.toJson(activityResponse));
		return activityResponse;
	}

	/**
	 * Create Activity
	 * 
	 * @param data
	 * @param activityDetails
	 * @return activityResponse
	 */
	public Activity createActivity(TestData data, Activity activityDetails) {
		String serviceEndPoint = this.endPointURL;
		Logging.log("createActivity endPointURL  >>>" + serviceEndPoint);
		Logging.log("createActivity Request  >>>"
				+ gson.toJson(activityDetails));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(activityDetails, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (data.getData().equalsIgnoreCase("EXCEPTION")) {

			Assert.assertTrue(response.getStatus() > 200,
					"Exception is expected but it returned StatusCode "
							+ response.getStatus());
		}
		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		Activity activityResponse = null;
		try {
			activityResponse = mapper.readValue(strResponse, Activity.class);
		} catch (Exception e) {
			Assert.fail("Error in reading response, Exeption thrown --> "
					+ e.getMessage());
		}
		Logging.log("createActivity RESPONSE: " + gson.toJson(activityResponse));
		return activityResponse;
	}

	/**
	 * Create BulkActivity
	 * 
	 * @param bulkActivityRequest
	 * @return bulkActivityResponse
	 */
	public CollectionEntityPojo createBulkActivity(
			List<Activity> bulkActivityRequest) {
		String serviceEndPoint = this.endPointURL + "/_bulk";
		Logging.log("createBulkActivity endPointURL  >>>" + serviceEndPoint);
		Logging.log("createBulkActivity Request  >>>"
				+ gson.toJson(bulkActivityRequest));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(bulkActivityRequest, MediaType.APPLICATION_JSON));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		CollectionEntityPojo listOfActivities = null;
		try {
			listOfActivities = mapper.readValue(strResponse,
					CollectionEntityPojo.class);
		} catch (Exception e) {
			Assert.fail("Error in reading from response " + e);
		}
		Logging.log("createBulkActivity RESPONSE: "
				+ gson.toJson(listOfActivities));
		return listOfActivities;
	}

	/**
	 * List Activities
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param offsetStr
	 * @param limitStr
	 * @param summary
	 * @param sortingFlag
	 * @param request
	 * @return listActivityResponse
	 */
	public CollectionEntityPojo listActivities(QueryParamPojo params,
			InputContainer requestBody, TestData data) {

		String serviceEndPoint = this.endPointURL + "/_list?"
				+ params.getEndPoint();
		Logging.log("Testcase Description: " + data.getTestSteps());
		Logging.log("listActivities endPointURL  >>>" + serviceEndPoint);
		Logging.log("listActivities Request  >>>" + gson.toJson(requestBody));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(requestBody, MediaType.APPLICATION_JSON));
		CollectionEntityPojo listOfActivities = null;
		if (data.getException().equals("EXCEPTION")) {
			Assert.assertTrue(response.getStatus() > 200,
					"Not throwing Exception !!");
		} else {
			String strResponse = response.readEntity(String.class);
			mapper.registerModule((Module) new JavaTimeModule());

			try {
				listOfActivities = mapper.readValue(strResponse,
						CollectionEntityPojo.class);
			} catch (Exception e) {
				Assert.fail("Error in reading from response !!");
			}
		}
		Logging.log("listActivities RESPONSE: " + gson.toJson(listOfActivities));
		return listOfActivities;
	}

	/**
	 * listActivitiesByMeta
	 * 
	 * @return Collection of Activities
	 */
	public CollectionEntity<Activity> listActivitiesByMeta(MetaMapper meatMapper) {

		String serviceEndPoint = this.endPointURL + "/_listByMeta";
		Logging.log("listActivitiesByMeta endPointURL  >>>" + serviceEndPoint);
		Logging.log("REQUEST: " + gson.toJson(meatMapper));

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(meatMapper, MediaType.APPLICATION_JSON));
		CollectionEntity<Activity> collectionEntity = null;
		try {
			collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<Activity>>() {
					});

		} catch (Exception e) {
			e.printStackTrace();

		}
		Logging.log("RESPONSE: " + gson.toJson(collectionEntity));
		if (response.getStatus() != 200) {
			Assert.fail("Failed to get listActivitiesByMeta, status code: "
					+ response.getStatus());
		}
		return collectionEntity;
	}

	/**
	 * listActivitiesByMeta
	 * 
	 * @return Collection of Activities
	 */
	public Activity updateActivity(Activity activity) {

		String serviceEndPoint = this.endPointURL + "/_update";
		Logging.log("listActivitiesByMeta endPointURL  >>>" + serviceEndPoint);
		Logging.log("REQUEST: " + gson.toJson(activity));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(activity, MediaType.APPLICATION_JSON));

		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		Activity activityResponse = null;
		try {
			activityResponse = mapper.readValue(strResponse, Activity.class);
		} catch (Exception e) {
			Assert.fail("Error in reading response, Exeption thrown --> "
					+ e.getMessage());
		}
		Logging.log("RESPONSE: " + gson.toJson(activityResponse));
		if (response.getStatus() != 200) {
			Assert.fail("Failed to update activity, status code: "
					+ response.getStatus());
		}
		return activityResponse;
	}
}

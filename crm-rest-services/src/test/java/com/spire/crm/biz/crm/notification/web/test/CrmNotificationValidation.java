package com.spire.crm.biz.crm.notification.web.test;

/**
 * @author Manikanta.Y
 */

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.Context;
import spire.commons.activitystream.Action;
import spire.commons.activitystream.ActivityType;
import spire.commons.activitystream.Actor;
import spire.commons.activitystream.CollectionEntity;
import spire.commons.activitystream.ObjectType;
import spire.commons.activitystream.Target;
import spire.commons.utils.ContextUtil;
import spire.crm.notification.beans.Notification;
import spire.crm.notification.beans.NotificationBean;
import spire.crm.notification.beans.NotificationType;
import spire.crm.notification.enums.NotificationStatus;
import spire.crm.notification.exceptions.NotificationRestClientException;
import spire.crm.notification.services.client.NotificationClient;
import spire.crm.notification.services.client.NotificationClientImpl;
import spire.crm.notification.services.param.BulkUpdateNotificationRequest;
import spire.crm.notification.services.param.CreateNotificationRequest;
import spire.crm.notification.services.param.ListNotificationRequest;
import spire.crm.notification.services.param.UpdateNotificationRequest;
import spire.crm.notification.services.utils.NotificationConfig;
import spire.crm.notification.web.exception.NotificationErrMsg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

public class CrmNotificationValidation extends BaseServiceConsumerNew {

	public Gson gson = new Gson();
	public ObjectMapper mapper = new ObjectMapper();
	public String propertiesfilePath = "./src/main/resources/services-endpoints.properties";
	public static String endpointUrl = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_NOTIFICATION_BIZ");

	public static String tenantID = ReadingServiceEndPointsProperties
			.getServiceEndPoint("tenantId");

	public static String realmName = ReadingServiceEndPointsProperties
			.getServiceEndPoint("realmName");

	public static String userId = ReadingServiceEndPointsProperties
			.getServiceEndPoint("userId");

	public static String Authorization = ReadingServiceEndPointsProperties
			.getServiceEndPoint("Authorization");

	public static String loginId = ReadingServiceEndPointsProperties
			.getServiceEndPoint("loginId");

	public void verifyCreateNotification(NotificationBean notification,
			SpireTestObject testObject, TestData data) {
		setUPContext();
		setUPNotificationRequest(notification, data);
		NotificationConfig config = setUPconfig();

		NotificationClient client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();
		CreateNotificationRequest createrequest = new CreateNotificationRequest(
				notification);
		createrequest.setContext(context);

		Logging.info("Create Notification Request Input is >>"
				+ gson.toJson(createrequest));
		try {
			Response createResponse = client.createNotifications(createrequest);
			Thread.sleep(2000);
			if (createResponse.getStatus() == 200) {

				NotificationBean createdNotification = createResponse
						.readEntity(NotificationBean.class);

				Logging.info("Created Notification Response >>"
						+ gson.toJson(createdNotification));
				if (testObject.getTestTitle().equals(
						"verifyUpdateNotification_Sanity")) {

					Thread.sleep(1000);

					if (createdNotification.getNotficationReadStatus().equals(
							"read")) {
						notification.setNotficationReadStatus("unread");
						validateUpdateNotification(NotificationStatus.UN_READ,
								createdNotification, notification);

						verifygetNotification(NotificationStatus.UN_READ,
								createdNotification, notification);
					} else {
						notification.setNotficationReadStatus("read");
						validateUpdateNotification(NotificationStatus.READ,
								createdNotification, notification);

						verifygetNotification(NotificationStatus.READ,
								createdNotification, notification);
					}

				} else {

					verifygetNotification(NotificationStatus.ALL,
							createdNotification, notification);
				}

			} else {
				Assert.fail(" Create Notification failed and status code is : "
						+ createResponse.getStatus());
			}
		} catch (NotificationRestClientException | InterruptedException e) {

			Assert.fail(" NotificationRestClientException " + e.getMessage());
		}

	}

	public void validateUpdateNotification(
			NotificationStatus readStatus,
			NotificationBean createdNotification, NotificationBean notification) throws InterruptedException {

		Thread.sleep(10000);
		setUPContext();
		NotificationConfig config = setUPconfig();

		NotificationClient client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();

		UpdateNotificationRequest updateNotificationRequest = new UpdateNotificationRequest(
				createdNotification.getNotficationId(),
				notification.getNotficationReadStatus());
		updateNotificationRequest.setContext(context);
		Logging.info("Update Notification Request Input is >>"
				+ gson.toJson(updateNotificationRequest));
		try {

			Response getResponse = client
					.updateNotifications(updateNotificationRequest);

			if (getResponse.getStatus() == 200) {

				Boolean status = getResponse.readEntity(Boolean.class);
				Assert.assertTrue(status,
						" Given 200 status but the status message is : "
								+ status);

			} else {
				NotificationErrMsg errormsg = getResponse
						.readEntity(NotificationErrMsg.class);

				Assert.fail("Update Notification failed Error message is : "
						+ errormsg.getMessage() + "and error code is : "
						+ getResponse.getStatus());

			}

		} catch (NotificationRestClientException e) {
			Assert.fail(" NotificationRestClientException " + e.getMessage());
		}

	}

	public void verifygetNotification(NotificationStatus readStatus,
			NotificationBean createdNotification, NotificationBean notification) throws InterruptedException {

		Thread.sleep(10000);
		setUPContext();
		NotificationConfig config = setUPconfig();

		NotificationClient client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();

		ListNotificationRequest listNotificationRequest = new ListNotificationRequest();
		listNotificationRequest.setContext(context);
		listNotificationRequest.setReadStatus(readStatus);
		Logging.info("get Notification Request Input is >>"
				+ gson.toJson(listNotificationRequest));
		try {

			Response getResponse = client
					.getNotifications(listNotificationRequest);

			if (getResponse.getStatus() == 200) {

				CollectionEntity<NotificationBean> listNotificationBean = new CollectionEntity<NotificationBean>();
				listNotificationBean = getResponse
						.readEntity(new GenericType<CollectionEntity<NotificationBean>>() {
						});
				Logging.info("get Notification response >>"
						+ gson.toJson(listNotificationBean));
				verifyNotificationResults(listNotificationBean,
						createdNotification, notification);

			} else if (getResponse.getStatus() == 404) {
				NotificationErrMsg errormsg = getResponse
						.readEntity(NotificationErrMsg.class);

				Assert.fail("Create Notification failed Error message is : "
						+ errormsg.getMessage() + "and error code is : "
						+ getResponse.getStatus());

			} else {
				Assert.fail("Create Notification failed and error code is : "
						+ getResponse.getStatus());
			}

		} catch (NotificationRestClientException e) {
			Assert.fail(" NotificationRestClientException " + e.getMessage());
		}

	}

	private static void verifyNotificationResults(
			CollectionEntity<NotificationBean> listNotificationBean,
			NotificationBean createdNotification, NotificationBean notification) {
		Boolean flag = false;

		Assert.assertTrue(listNotificationBean.getItems().size() > 0,
				"Notification total count is not > 0 : count is "
						+ listNotificationBean.getItems());
		List<NotificationBean> notificationList = new ArrayList<NotificationBean>(
				listNotificationBean.getItems());
		for (NotificationBean notificationBean : notificationList) {

			if (notificationBean.getNotficationId().equals(
					createdNotification.getNotficationId())) {

				Assert.assertEquals(notificationBean.getNotficationType(),
						notification.getNotficationType(),
						"Found discrepancy in Notification Type ");

				Assert.assertEquals(
						notificationBean.getNotficationReadStatus(),
						notification.getNotficationReadStatus(),
						"Found discrepancy in ReadStatus Type ");
				/*
				 * Assert.assertEquals( notificationBean.getActivityType(),
				 * notification.getActivityType(),
				 * "Found discrepancy in Activity Type ");
				 */
				List<Actor> actors = notificationBean.getActors();

				Assert.assertNotNull(actors, "Actor is null");
				for (Actor actor : actors) {

					Assert.assertEquals(actor.getId(), userId,
							"Found discrepancy in user Id in Actor");

					/*
					 * Assert.assertEquals( actor.getName(),loginId,
					 * "Found discrepancy in user Id  ");
					 */

				}

				List<Target> targets = notificationBean.getTargets();
				List<Target> inputtargets = notification.getTargets();
				String inputtaget = null;
				for (Target target : inputtargets) {
					inputtaget = target.getId();
				}
				for (Target target : targets) {

					Assert.assertEquals(target.getId(), inputtaget,
							"Found discrepancy in user Id in Taget ");

					Assert.assertEquals(target.getName(), loginId,
							"Found discrepancy in user Id  ");

				}

				flag = true;

			}

		}
		Assert.assertTrue(flag, "Created notification not found in List ");

	}

	private static NotificationConfig setUPconfig() {
		NotificationConfig config = new NotificationConfig();
		Logging.log("Notification Hostname:port >>>> " + endpointUrl);
		String[] host = endpointUrl.split(":");
		config.setHostName(host[0]);
		config.setPortNumber(Integer.parseInt(host[1]));
		config.setProtocolScheme("http");
		return config;

	}

	public static int ordinalIndexOf(String str, char c, int n) {
		int pos = str.indexOf(c, 0);
		while (n-- > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}

	private static void setUPContext() {
		Context context = new Context();

		context.setRealmName(realmName);
		context.setTenantId(tenantID);
		context.setUserId(userId);
		context.setTokenId(Authorization);

		ContextUtil.setContext(context);
	}

	private static void setUPNotificationRequest(NotificationBean notification,
			TestData data) {

		switch (data.getEnumData()) {
		case "SENT":
			notification.setNotficationType(Action.SENT);
			break;
		case "JOBPOST":
			notification.setNotficationType(Action.JOBPOST);
			break;
		default:
			notification.setNotficationType(Action.JOBPOST);
			break;
		}

		notification.setActivityType(ActivityType.COMMUNICATION);
		List<Target> targets = new ArrayList<Target>();
		Target target = new Target();
		target.setId(userId);
		target.setName(loginId);
		targets.add(target);
		notification.setTargets(targets);

		List<Actor> actors = new ArrayList<Actor>();

		Actor actor = new Actor();
		actor.setId(userId);
		actor.setName(loginId);
		actors.add(actor);
		notification.setActors(actors);
		spire.commons.activitystream.Object object = new spire.commons.activitystream.Object();
		object.setId("6000:6003:a36a897a985c460091403e6517dfeac8");
		object.setObjectType(ObjectType.EMAIL);
		notification.setObject(object);

	}

	public void verifyBulkUpdate(NotificationBean notification,
			SpireTestObject testObject, TestData data) {

		String bulkCount = data.getData().substring(
				data.getData().indexOf(":") + 1, data.getData().length());
		setUPContext();
		setUPNotificationRequest(notification, data);
		NotificationConfig config = setUPconfig();

		NotificationClient client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();
		CreateNotificationRequest createrequest = new CreateNotificationRequest(
				notification);
		createrequest.setContext(context);
		List<Notification> bulkUpdateList = new ArrayList<Notification>();
		Notification tempNotification = new Notification();

		for (int i = 0; i < Integer.parseInt(bulkCount); i++) {

			Logging.info("Create Notification Request Input is >>"
					+ gson.toJson(createrequest));
			try {

				Response createResponse = client
						.createNotifications(createrequest);
				
				WebPageHelper.sleep(10000);

				if (createResponse.getStatus() == 200) {

					NotificationBean createdNotification = createResponse
							.readEntity(NotificationBean.class);

					Logging.info("Bulk Update Created Notification Response >>"
							+ gson.toJson(createdNotification));
					tempNotification.setNotficationId(createdNotification
							.getNotficationId());

					if (createdNotification.getNotficationReadStatus().equals(
							"read")) {
						tempNotification.setNotficationReadStatus("unread");

					} else {

						tempNotification.setNotficationReadStatus("read");

					}

					bulkUpdateList.add(tempNotification);

				} else {
					Assert.fail(" Bulk Update Created Notificationfailed and status code is : "
							+ createResponse.getStatus());
				}

			} catch (NotificationRestClientException e) {

				Assert.fail(" NotificationRestClientException "
						+ e.getMessage());
			}
		}

		try {

			BulkUpdateNotificationRequest bulkUpdateNotificationRequest = new BulkUpdateNotificationRequest(
					bulkUpdateList);
			bulkUpdateNotificationRequest.setContext(context);

			Response bulkResponse = client
					.bulkUpdateNotifications(bulkUpdateNotificationRequest);
			Thread.sleep(2000);

			if (bulkResponse.getStatus() == 200) {

				Boolean bulkstatus = bulkResponse.readEntity(Boolean.class);

				Assert.assertTrue(bulkstatus);
				Logging.info("Bulk Update Notification Response >>"
						+ gson.toJson(bulkstatus));
				Thread.sleep(2000);

				ListNotificationRequest listNotificationRequest = new ListNotificationRequest();
				listNotificationRequest.setContext(context);
				listNotificationRequest.setReadStatus(NotificationStatus.ALL);

				try {

					Response getResponse = client
							.getNotifications(listNotificationRequest);

					if (getResponse.getStatus() == 200) {
						
						WebPageHelper.sleep(10000);

						CollectionEntity<NotificationBean> listNotificationBean = new CollectionEntity<NotificationBean>();
						listNotificationBean = getResponse
								.readEntity(new GenericType<CollectionEntity<NotificationBean>>() {
								});
						Logging.info("Bulk Update get Notification response >>"
								+ gson.toJson(listNotificationBean));

						verifyBulkResults(listNotificationBean, bulkUpdateList);

					} else if (getResponse.getStatus() == 404) {
						NotificationErrMsg errormsg = getResponse
								.readEntity(NotificationErrMsg.class);

						Assert.fail("Bulk Update get Notification failed Error message is : "
								+ errormsg.getMessage()
								+ "and error code is : "
								+ getResponse.getStatus());

					} else {
						Assert.fail("Bulk Update get Notification failed and error code is : "
								+ getResponse.getStatus());
					}

				} catch (NotificationRestClientException e) {
					Assert.fail(" NotificationRestClientException "
							+ e.getMessage());
				}

			} else {
				Assert.fail(" Bulk Update Notificationfailed and status code is : "
						+ bulkResponse.getStatus());
			}

		} catch (NotificationRestClientException | InterruptedException e) {

			Assert.fail(" NotificationRestClientException " + e.getMessage());
		}

	}

	private static void verifyBulkResults(
			CollectionEntity<NotificationBean> listNotificationBean,
			List<Notification> bulkUpdateList) {
		Boolean flag = false;
		int Count = 0;

		Assert.assertTrue(listNotificationBean.getItems().size() > 0,
				"Notification total count is not > 0 : count is "
						+ listNotificationBean.getItems());
		List<NotificationBean> notificationList = new ArrayList<NotificationBean>(
				listNotificationBean.getItems());

		for (Notification bulknotification : bulkUpdateList) {

			for (NotificationBean notificationBean : notificationList) {

				if (notificationBean.getNotficationId().equals(
						bulknotification.getNotficationId())) {
					Assert.assertEquals(
							notificationBean.getNotficationReadStatus(),
							bulknotification.getNotficationReadStatus(),
							"Found discrepancy in ReadStatus Type ");

					flag = true;
					Count++;

				}

			}
		}
		Assert.assertTrue(flag,
				"<Method : verifyBulkResults>Created notification not found in List ");
		Assert.assertEquals(Count, bulkUpdateList.size());

	}
	
	
	public Response getNotificationByType() {
		TestData data=  new TestData();
		data.setEnumData("SENT");
		NotificationBean notification = new NotificationBean();
		setUPContext();
		setUPNotificationRequest(notification,data);
		NotificationConfig config = setUPconfig();

		NotificationClient client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();
		ListNotificationRequest request = new ListNotificationRequest();
		request.setContext(context);
		NotificationType notificationType = new NotificationType();
		List<Action> notificationTypes = new ArrayList<Action>();		
		notificationType.setNotificationTypes(notificationTypes);
		request.setNotificationType(notificationType);
		request.setReadStatus(NotificationStatus.ALL);
		Logging.info("Verify the status of Get Notification by Status >>"
				+ gson.toJson(request));
		Response response =null;
		try {
			response = client.getNotificationsByTypes(request);
			if (response.getStatus() != 200) {
				Assert.fail("Get notification by Type is failed and status code is :"
						+ response.getStatus());
			}
			Thread.sleep(2000);

		} catch (NotificationRestClientException | InterruptedException e) {
			
			e.printStackTrace();
			Assert.fail("Run Time Exception");
		}
		return response;

	}

	public void verifyStausOfNotificationByType(
			NotificationBean notification, SpireTestObject testObject,
			TestData data) {
		setUPContext();
		setUPNotificationRequest(notification, data);
		NotificationConfig config = setUPconfig();

		NotificationClient client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();
		ListNotificationRequest request = new ListNotificationRequest();

		request.setContext(context);
		NotificationType notificationType = new NotificationType();
		List<Action> notificationTypes = new ArrayList<Action>();
		


		notificationTypes.add(Action.JOBPOST);
		notificationTypes.add(Action.SENT);

		notificationType.setNotificationTypes(notificationTypes);
		request.setNotificationType(notificationType);
		request.setReadStatus(NotificationStatus.ALL);
		Logging.info("Verify the status of Get Notification by Status >>"
				+ gson.toJson(request));

		try {
			Response response = client.getNotificationsByTypes(request);
			if (response.getStatus() != 200) {
				Assert.fail("Get notification by Type is failed and status code is :"
						+ response.getStatus());
			}
			Thread.sleep(2000);

		} catch (NotificationRestClientException | InterruptedException e) {

			e.printStackTrace();
		}

	}
}

package com.spire.common;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.helper.SpireProperties;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.commons.Context;
import spire.commons.activitystream.CollectionEntity;
import spire.commons.utils.ContextUtil;
import spire.crm.notification.beans.NotificationBean;
import spire.crm.notification.enums.NotificationStatus;
import spire.crm.notification.exceptions.NotificationRestClientException;
import spire.crm.notification.services.client.NotificationClient;
import spire.crm.notification.services.client.NotificationClientImpl;
import spire.crm.notification.services.param.ListNotificationRequest;
import spire.crm.notification.services.utils.NotificationConfig;

public class NotificationHelper {

	public Gson gson = new Gson();
	public ObjectMapper mapper = new ObjectMapper();
	public NotificationClient client;
	public String propertiesfilePath = "./src/main/resources/default_headers.properties";
	public String endpointUrl = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_NOTIFICATION_BIZ");

	public String tenantID = SpireProperties.loadProperties(propertiesfilePath)
			.getProperty("tenantId", "6000");

	public String realmName = SpireProperties
			.loadProperties(propertiesfilePath)
			.getProperty("realmName", "6000");

	public String userId = SpireProperties.loadProperties(propertiesfilePath)
			.getProperty("userId", "6000");

	public String Authorization = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("Authorization", "6000");

	public  String loginId = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("loginId", "6000");

	public CollectionEntity<NotificationBean> getNotification() {

		setUPContext();
		NotificationConfig config = setUPconfig();

		NotificationClientImpl client = new NotificationClientImpl(config);
		Context context = ContextUtil.getContext();

		ListNotificationRequest listNotificationRequest = new ListNotificationRequest();
		listNotificationRequest.setContext(context);

		listNotificationRequest.setReadStatus(NotificationStatus.ALL);
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
				return listNotificationBean;
			} else {

				Assert.fail("Get Notification failed and error code is : "
						+ getResponse.getStatus());
				return null;
			}

		} catch (NotificationRestClientException e) {
			Assert.fail(" NotificationRestClientException " + e.getMessage());
			return null;
		}

	}

	private NotificationConfig setUPconfig() {
		NotificationConfig config = new NotificationConfig();

		String[] host = endpointUrl.split(":");
		config.setHostName(host[0]);
		config.setPortNumber(Integer.parseInt(host[1]));
		config.setProtocolScheme("http");
		return config;
	}

	private void setUPContext() {
		Context context = new Context();

		context.setRealmName(realmName);
		context.setTenantId(tenantID);
		context.setUserId(userId);
		context.setTokenId(Authorization);

		ContextUtil.setContext(context);

	}
}

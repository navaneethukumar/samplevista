package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.notification.beans.Notification;
import spire.crm.notification.beans.NotificationBean;

/**
 * 
 * http://192.168.2.124:8085/crm-notification-web/api/v1/swagger.json
 * 
 * @author Manikanta
 *
 */
public class CrmNotificationWebBizServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CrmNotificationWebBizServiceConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("CRM_NOTIFICATION_BIZ");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * /crm-notification-web/api/v1/notify/notifications
	 * 
	 * @param URL
	 */
	public CrmNotificationWebBizServiceConsumer(String URL) {
		logger.info(Key.METHOD, "CrmNotificationWebBizServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + URL);
		//this.endPointURL = URL;

	}

	/** ------------------------GET Operations ---------------------- **/

	/**
	 * get Notification By giving the notification type as ALL/Read/Unread
	 * 
	 * @return
	 */
	public Response getNotifications(String notificationsType) {
		Logging.log("Getting the  list Notifications ");
		String serviceEndPoint = this.endPointURL + "/" + notificationsType;
		Logging.log(" getNotifications endPointURL  >>>" + endPointURL);
		Response response = executeGET(serviceEndPoint, true);
		return response;

	}


	/**
	 * Create a Notification
	 * 
	 * @param createRule
	 * @return
	 */
	public Response createNotification(NotificationBean notification) {

		Logging.log(" createProfile endPointURL  >>>" + endPointURL);
		Logging.log(" createProfile Request  >>>" + gson.toJson(notification));
		Response response = executePOST(endPointURL, true,
				Entity.entity(notification, MediaType.APPLICATION_JSON));
		return response;

	}

	/**
	 * Update a Notification Read Status
	 * 
	 * @param rule
	 * @return
	 */
	public Response updateNotificationReadStatus(String notificationId,String readStatus) {

		String serviceEndPoint = this.endPointURL + "/update?notificationId=" + notificationId+"&readStatus="+readStatus;
		Logging.log(" updateNotificationReadStatus endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity("", MediaType.APPLICATION_JSON));
		return response;

	}
	

	/**
	 * Update a Bulk Notification
	 * 
	 * @param createRule
	 * @return
	 */
	public Response UpdateBulkNotification(Notification[] notifications) {

		Logging.log(" createProfile endPointURL  >>>" + endPointURL);
		Logging.log(" createProfile Request  >>>" + gson.toJson(notifications));
		Response response = executePOST(endPointURL, true,
				Entity.entity(notifications, MediaType.APPLICATION_JSON));
		return response;

	}


}

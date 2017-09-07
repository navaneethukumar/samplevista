package com.spire.crm.restful.biz.consumers;

import java.util.Calendar;
import java.util.HashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.biz.com.service.beans.SendCustomEmailVO;
import spire.crm.biz.com.service.beans.SendEmailWithTemplateVO;
import spire.crm.entity.com.entities.Template;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.activity.biz.pojos.ScheduleNotificationRequest;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

public class EmailsBizConsumer extends BaseServiceConsumerNew {

	String createTemplateMsg = "All records successfully inserted. Please refer ids generated for the corresponding template name";
	static String beforeSubject = "WehaveOpening";
	static String AfterSubject = "IGotchanged";
	ActivityStreamBizServiceConsumer activities = null;
	HashMap<String, String> templateValuesMap = new HashMap<>();
	private static Logger logger = LoggerFactory
			.getLogger(EmailsBizConsumer.class);
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_EMAILS_BIZ");

	String spire_biz_url = ReadingServiceEndPointsProperties
			.getServiceEndPoint("SPIRE_BIZ");

	public static Gson gson = new Gson();

	public ObjectMapper mapper = new ObjectMapper()
			.registerModule((Module) new JavaTimeModule());

	public EmailsBizConsumer() {
		// Logging.log(Key.METHOD, "EmailsEntity constructor", Key.MESSAGE,
		// "Service end point URL >>>");

	}

	public Response createEmailTemplate(Template template) {

		Logging.log("started the method >>> createEmailTemplate  ");

		String serviceEndPoint = this.endPointURL + "/crm-templates/create";

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Logging.log("Request Input is for create template   >>> "
				+ gson.toJson(template));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(template, MediaType.APPLICATION_JSON));

		return response;

	}

	public Response listEmailTemplate(String limit, String offset) {

		Logging.log("started the method >>> listEmailTemplate  ");

		String serviceEndPoint = this.endPointURL
				+ "/crm-templates/list?limit=" + limit + "&offset=" + offset;

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;
	}

	public Response deleteTemp(String tempId) {

		Logging.log("started the method >>> deleteTemplate  ");

		String serviceEndPoint = this.endPointURL + "/crm-templates/" + tempId;

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Response response = executeDELETE(serviceEndPoint, true);

		return response;

	}

	public Response editEmailTemplate(Template template) {

		Logging.log("started the method >>> editEmailTemplate  ");

		String serviceEndPoint = this.endPointURL + "/crm-templates";

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Logging.log(" Request Input is for edit template   >>> "
				+ gson.toJson(template));

		Response response = executePUT(serviceEndPoint, true,
				Entity.entity(template, MediaType.APPLICATION_JSON));

		return response;

	}

	public Response getEmailTemplateByID(String templateID) {

		Logging.log("started the method >>> getEmailTemplateByID  ");

		String serviceEndPoint = this.endPointURL + "/crm-templates/"
				+ templateID;

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Response response = executeGET(serviceEndPoint, true);

		return response;
	}

	public Response sendEmailWithTemplate(SendEmailWithTemplateVO sendValues) {

		Logging.log("started the method >>> sendEmailWithTemplate  ");

		String serviceEndPoint = this.endPointURL + "/crm-emails/send";

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Logging.log("Request input for send email with template >>>>  >>> "
				+ gson.toJson(sendValues));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(sendValues, MediaType.APPLICATION_JSON));

		return response;
	}

	public Response sendEmailCustom(SendCustomEmailVO sendValues) {

		Logging.log("started the method >>> sendEmailWithTemplate  ");

		String serviceEndPoint = this.endPointURL + "/crm-emails/custom/send";

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Logging.log("Request input for send email with template >>>>  >>> "
				+ gson.toJson(sendValues));

		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(sendValues, MediaType.APPLICATION_JSON));

		return response;
	}

	public static String getSeconds() {
		Calendar calendar = Calendar.getInstance();
		int seconds = calendar.get(Calendar.SECOND);

		return String.valueOf(seconds);

	}

	public Response getDataByMessageID(String messageID) {

		Logging.log("started the method >>> getDataByMessageID  ");

		String serviceEndPoint = this.endPointURL + "/crm-emails/" + messageID;

		Logging.log("Service end point  >>> " + serviceEndPoint);

		Logging.log("Request input for message deatils  >>>>  message ID is  >>> "
				+ messageID);

		Response response = executeGET(serviceEndPoint, true);

		return response;
	}

	public Response remindMe(ScheduleNotificationRequest scheduleNotificationRequest) {
		Logging.log("started the method >>> remindMeIO  ");
		String serviceEndPoint = this.spire_biz_url + "/notify/remained";
		Logging.log("Service end point  >>> " + serviceEndPoint);
		Logging.log("Request input for remindMe is  >>> " + gson.toJson(scheduleNotificationRequest));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(scheduleNotificationRequest, MediaType.APPLICATION_JSON));
		return response;
	}

}

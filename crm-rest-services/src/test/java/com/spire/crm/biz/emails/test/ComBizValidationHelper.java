package com.spire.crm.biz.emails.test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.commons.activitystream.Actor;
import spire.commons.activitystream.CollectionEntity;
import spire.crm.biz.com.service.beans.SendCustomEmailVO;
import spire.crm.biz.com.service.beans.SendEmailWithTemplateVO;
import spire.crm.biz.com.service.entities.Email;
import spire.crm.entity.com.entities.ResponseEntity;
import spire.crm.entity.com.entities.Template;
import spire.crm.entity.com.entities.TemplateSummary;
import spire.crm.notification.beans.NotificationBean;
import spire.crm.user.service.beans.CRMUserDetailsBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.EmailQueryParamPojo;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.activity.biz.pojos.CRMHomeActivities;
import com.spire.crm.activity.biz.pojos.CRMHomeActivity;
import com.spire.crm.activity.biz.pojos.ScheduleNotificationRequest;
import com.spire.crm.biz.crm.notification.web.test.CrmNotificationValidation;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.EmailsBizConsumer;
import com.spire.crm.restful.biz.consumers.UserServiceConsumer;
import com.spire.crm.restful.entity.consumers.EmailsConsumer;

public class ComBizValidationHelper {

	public static Gson gson = new Gson();
	public ObjectMapper mapper = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	DataFactory factory = new DataFactory();
	Random randomGenerator = new Random();
	String userId = ReadingServiceEndPointsProperties
			.getServiceEndPoint("userId");
	String dbHost = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBHOST");
	String dbSchema = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBSCHEMA");

	public void setUpTemplate(Template template) {
		String userId = ReadingServiceEndPointsProperties
				.getServiceEndPoint("userId");
		template.setName("SpireCampaign" + factory.getRandomWord()
				+ randomGenerator.nextInt(10000));
		template.setCreatedBy(userId);
		template.setModifiedBy(userId);
	}

	public void setUpEditTemplate(Template editTemplate) {
		editTemplate.setContentHeader(factory.getRandomChars(100));
		editTemplate.setContentBody(factory.getRandomChars(100));
		editTemplate.setContentSignature(factory.getRandomChars(100));
		editTemplate.setName("SpireCampaign" + factory.getRandomWord()
				+ randomGenerator.nextInt(10000));
	}

	public String createEmailTemplate(Template template) {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		Response createResponse = emailsBizConsumer
				.createEmailTemplate(template);
		if (createResponse.getStatus() == 201) {
			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String templateId = createResponseEntity.getSuccess().get(
					template.getName());
			template.setId(templateId);
			return templateId;
		} else {
			Assert.fail(" template creation failed and staus code is : "
					+ createResponse.getStatus());
		}
		return null;
	}

	public Template getEmailTemplate(String templateId,
			SpireTestObject testObject) {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		Response getResponse = emailsBizConsumer
				.getEmailTemplateByID(templateId);
		if (getResponse.getStatus() == 200) {
			if (!testObject.getTestTitle().equals("verify_deleteTemplate")) {
				Template gettemplate = getResponse.readEntity(Template.class);
				return gettemplate;
			} else {
				Boolean deleteflag = false;
				ResponseEntity getResponseEntity = getResponse
						.readEntity(ResponseEntity.class);
				List<String> msgDeleted = getResponseEntity.getFailure();
				Assert.assertEquals(getResponseEntity.getCode(), 400,
						"Status code in response is not expected ");
				Assert.assertEquals(getResponseEntity.getMessage(),
						"No records could be deleted.",
						"message in response is not expected ");
				for (String tId : msgDeleted) {
					if (!tId.equals(templateId)) {
						deleteflag = true;
					}
				}
				Assert.assertTrue(deleteflag,
						"Deleted template id is not found in the failuer list");
			}
		} else if (getResponse.getStatus() == 404) {
			if (!testObject.getTestTitle().equals("verify_deleteTemplate")) {
				Assert.fail(" get template failed and staus code is : "
						+ getResponse.getStatus());
			}

		} else {
			Assert.fail(" get template failed and staus code is : "
					+ getResponse.getStatus());
		}
		return null;

	}

	public Template deleteTemplate(String templateId) {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		Response deleteResponse = emailsBizConsumer.deleteTemp(templateId);
		if (deleteResponse.getStatus() == 200) {
			ResponseEntity responseEntity = deleteResponse
					.readEntity(ResponseEntity.class);
			String msgDeleted = responseEntity.getSuccess().get(templateId);
			Assert.assertEquals(responseEntity.getCode(), 201,
					"Status code in response is not expected ");
			Assert.assertEquals(responseEntity.getMessage(),
					"All records successfully deleted.",
					"message in response is not expected ");
			Assert.assertEquals("deleted", msgDeleted,
					"deleted message is expected in success map");
		} else {
			Assert.fail(" delete template failed and staus code is : "
					+ deleteResponse.getStatus());
		}
		return null;

	}

	public void compareTemplates(Template createTemplate, Template gettemplate) {
		Assert.assertEquals(createTemplate.getName(), gettemplate.getName(),
				"Found discrepancy in Template Name");
		Assert.assertEquals(createTemplate.getContentBody(),
				gettemplate.getContentBody(),
				"Found discrepancy in getContentBody");
		Assert.assertEquals(createTemplate.getContentHeader(),
				gettemplate.getContentHeader(),
				"Found discrepancy in Template getContentHeader");
		Assert.assertEquals(createTemplate.getContentSignature(),
				gettemplate.getContentSignature(),
				"Found discrepancy in Template getContentSignature");
		Assert.assertEquals(createTemplate.getCreatedBy(),
				gettemplate.getCreatedBy(),
				"Found discrepancy in Template getCreatedBy");
		Assert.assertEquals(createTemplate.getCreatedByName(),
				gettemplate.getCreatedByName(),
				"Found discrepancy in Template getCreatedByName");
		Assert.assertEquals(createTemplate.getId(), gettemplate.getId(),
				"Found discrepancy in Template getId");
		Assert.assertEquals(createTemplate.getFlowId(),
				gettemplate.getFlowId(),
				"Found discrepancy in Template getFlowId");
		Assert.assertEquals(createTemplate.getReadCount(),
				gettemplate.getReadCount(),
				"Found discrepancy in Template getReadCount");
		Assert.assertEquals(createTemplate.getSendCount(),
				gettemplate.getSendCount(),
				"Found discrepancy in Template getSendCount");
		Assert.assertEquals(createTemplate.getSubject(),
				gettemplate.getSubject(),
				"Found discrepancy in Template getSubject");
		Assert.assertEquals(createTemplate.getSubject(),
				gettemplate.getSubject(),
				"Found discrepancy in Template getSubject");
		Assert.assertEquals(createTemplate.getSubject(),
				gettemplate.getSubject(),
				"Found discrepancy in Template getSubject");
		Assert.assertEquals(createTemplate.getSubject(),
				gettemplate.getSubject(),
				"Found discrepancy in Template getSubject");

	}

	public void validateEmailTemplate(Template createTemplate,
			SpireTestObject testObject) {
		setUpTemplate(createTemplate);
		createEmailTemplate(createTemplate);
		Template gettemplate = getEmailTemplate(createTemplate.getId(),
				testObject);
		compareTemplates(createTemplate, gettemplate);

	}

	public void validateDeleteTemplate(Template createTemplate,
			SpireTestObject testObject) {
		setUpTemplate(createTemplate);
		createEmailTemplate(createTemplate);
		deleteTemplate(createTemplate.getId());
		getEmailTemplate(createTemplate.getId(), testObject);

	}

	public void validatelistAllTemplate(Template createTemplate,
			SpireTestObject testObject) {
		Boolean listFlag = false;
		setUpTemplate(createTemplate);
		createEmailTemplate(createTemplate);
		TemplateSummary templateSummary = getALLEmailTemplate(testObject);
		List<Template> templatelist = templateSummary.getTemplates();
		for (Template template : templatelist) {
			if (createTemplate.getId().equals(template.getId())) {
				compareTemplates(createTemplate, template);
				listFlag = true;
			}
		}
		Assert.assertTrue(listFlag, "Created template is not found in the list");
	}

	private TemplateSummary getALLEmailTemplate(SpireTestObject testObject) {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		Response listResponse = emailsBizConsumer.listEmailTemplate("100", "0");
		if (listResponse.getStatus() == 200) {
			TemplateSummary templateSummary = listResponse
					.readEntity(TemplateSummary.class);
			Logging.log("List all templates response is :>>>"
					+ gson.toJson(templateSummary));
			return templateSummary;
		} else {
			Assert.fail(" delete template failed and staus code is : "
					+ listResponse.getStatus());
		}
		return null;
	}

	public void validateEditTemplate(Template createTemplate,
			SpireTestObject testObject) {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		Template editTemplate = null;
		setUpTemplate(createTemplate);
		createEmailTemplate(createTemplate);
		setUpEditTemplate(createTemplate);
		editTemplate = createTemplate;
		emailsBizConsumer.editEmailTemplate(editTemplate);
		Template gettemplate = getEmailTemplate(editTemplate.getId(),
				testObject);
		editTemplate.setSendCount(gettemplate.getSendCount());
		compareTemplates(editTemplate, gettemplate);
	}

	public String sendEmailSingle(Template createTemplate,
			EmailQueryParamPojo emailQueryParam,
			SendEmailWithTemplateVO sendValues) {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		sendValues.setFrom(factory.getEmailAddress());
		String candidateId = ProfileHelper
				.createProfile("crmvista.services@gmail.com");
		setUpTemplate(createTemplate);
		String templateID = createEmailTemplate(createTemplate);
		List<String> candidateIDs = new ArrayList<String>();
		candidateIDs.add(candidateId);
		sendValues.setTo(candidateIDs);
		sendValues.setTemplateId(templateID);
		Response sendResponse = emailsBizConsumer
				.sendEmailWithTemplate(sendValues);
		if (sendResponse.getStatus() == 202) {
		} else {
			Assert.fail(" Send email failed and staus code is : "
					+ sendResponse.getStatus());
		}
		return null;
	}

	public void sendCustomEmail(Template template,
			EmailQueryParamPojo emailQueryParam,
			SendEmailWithTemplateVO sendEmailInput) {
		SendCustomEmailVO customemailRequest = new SendCustomEmailVO();
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		customemailRequest.setFrom(factory.getEmailAddress());
		String candidateId = ProfileHelper
				.createProfile("crmvista.services@gmail.com");
		List<String> candidateIDs = new ArrayList<String>();
		candidateIDs.add(candidateId);
		customemailRequest.setTo(candidateIDs);
		customemailRequest.setContentHeader("Custom Send email "
				+ factory.getRandomChars(100) + candidateIDs.get(0));

		customemailRequest.setContentBody("Custom Send email "
				+ factory.getRandomChars(100) + candidateIDs.get(0));

		customemailRequest.setContentSignature("Custom Send email "
				+ factory.getRandomChars(100) + candidateIDs.get(0));
		customemailRequest.setSubject("send email");

		Response sendResponse = emailsBizConsumer
				.sendEmailCustom(customemailRequest);

		if (sendResponse.getStatus() == 202) {

		} else {
			Assert.fail(" Send custom  email failed and staus code is : "
					+ sendResponse.getStatus());
		}

	}

	public void verifyRecruiterFields(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException,
			InterruptedException {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		String candidateId = ProfileHelper
				.createProfile("crmvista.services@gmail.com");
		List<String> candidateIDs = new ArrayList<String>();
		candidateIDs.add(candidateId);
		Template template = new Template();
		template.setSubject("Dynamic Recruiter fields Template");
		template.setContentBody("@lead.userFirstName");
		template.setContentHeader("@lead.userLastName");
		template.setContentSignature("@lead.userSignature");
		SendEmailWithTemplateVO sendIO = setUpSendEmailTemplate(template,
				candidateIDs);
		Response sendResponse = emailsBizConsumer.sendEmailWithTemplate(sendIO);
		if (sendResponse.getStatus() == 202) {
			UserServiceConsumer userServiceConsumer = new UserServiceConsumer();
			Response userServiceRsp = userServiceConsumer
					.getUserDetails(userId);
			if (userServiceRsp.getStatus() == 200) {
				String strResponse = userServiceRsp.readEntity(String.class);
				CRMUserDetailsBean userDetails = mapper.readValue(strResponse,
						CRMUserDetailsBean.class);
				Logging.log("User deatils response >>>> "
						+ gson.toJson(userDetails));
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				CRMHomeActivity createdEmailActivity = verifyActivityStream(candidateId);
				String messageId = null;
				try {
					messageId = createdEmailActivity.getActivity().getObject()
							.getId();
				} catch (NullPointerException ee) {
					Thread.sleep(5000);
					createdEmailActivity = verifyActivityStream(candidateId);

					try {
						messageId = createdEmailActivity.getActivity()
								.getObject().getId();
					} catch (NullPointerException ne) {

						Assert.fail("Unable to create message activity");

					}

				}
				Logging.log("Message id is >>> " + messageId);
				Logging.info("Message id is >>> " + messageId);
				Response msgResponse = emailsBizConsumer
						.getDataByMessageID(messageId);
				Email messagedetails = msgResponse.readEntity(Email.class);
				Logging.log("Email Message Response is >>> "
						+ gson.toJson(messagedetails));
				if (userDetails.getUserDetails().getFirstName() != null)
					Assert.assertTrue(
							messagedetails.getContentBody()
									.contains(
											userDetails.getUserDetails()
													.getFirstName()),
							"Fail to replace recruiter First name not found in the email ");
				if (userDetails.getUserDetails().getLastName() != null)
					Assert.assertTrue(
							messagedetails.getContentBody().contains(
									userDetails.getUserDetails().getLastName()),
							"Fail to replace recruiter Last name not found in the email ");

				Assert.assertTrue(
						messagedetails.getContentBody().contains(
								userDetails.getUserPreference()
										.getEmailSignature()),
						"Fail to replace recruiter signature name not found in the email");

			} else {
				Assert.fail("Get User Details is failed  : "
						+ userServiceRsp.getStatus());
			}

		} else {
			Assert.fail(" Send email failed and staus code is : "
					+ sendResponse.getStatus());
		}
	}

	private SendEmailWithTemplateVO setUpSendEmailTemplate(Template template,
			List<String> candidateIDs) {

		template.setName("Spire" + factory.getRandomWord()
				+ randomGenerator.nextInt(20000));
		template.setCreatedBy(userId);
		template.setCreatedByName("TestAutomcation");
		template.setModifiedBy(userId);
		template.setModifiedByName("TestAutomcation");
		SendEmailWithTemplateVO sendValues = new SendEmailWithTemplateVO();
		sendValues.setTo(candidateIDs);
		sendValues.setTemplateId(createEmailTemplate(template));
		sendValues.setFrom("tester@logica.com");
		return sendValues;

	}

	private CRMHomeActivity verifyActivityStream(String candidateID)
			throws JsonParseException, JsonMappingException, IOException {

		Boolean activityFlag = true;
		ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();
		CRMHomeActivities homeActivitiesList = activityStreamBizServiceConsumer
				.getHomeEmailActivities();
		List<com.spire.crm.activity.biz.pojos.CRMHomeActivity> crmHomeActivity = homeActivitiesList
				.getHomeActivities();
		for (CRMHomeActivity activity : crmHomeActivity) {
			String toId = activity.getActivity().getObject().getDetail()
					.getTo();
			if (toId != null && toId.equals(candidateID)) {
				activityFlag = true;
				Logging.log("Found the activity for " + candidateID);
				return activity;
			}
		}
		Logging.log("Not Found the activity for " + candidateID);
		Assert.assertTrue(activityFlag, "Activity not created for send email");
		return null;

	}

	public void validateNotification() throws SQLException,
			InterruptedException {

		EmailsConsumer client = new EmailsConsumer();
		Boolean notificationFlag = false;
		String iD = UUID.randomUUID().toString().replaceAll("-", "");
		String fullEmailID = client.unsubscribeCandidate(iD.substring(0, 9)
				+ "@gmail.com");

		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		String candidateId = ProfileHelper.createProfile(fullEmailID);
		List<String> candidateIDs = new ArrayList<String>();
		candidateIDs.add(candidateId);
		Template template = new Template();
		template.setSubject("Dynamic Recruiter fields Template");
		template.setContentBody("@lead.userFirstName");
		template.setContentHeader("@lead.userLastName");
		template.setContentSignature("@lead.userSignature");
		SendEmailWithTemplateVO sendIO = setUpSendEmailTemplate(template,
				candidateIDs);
		Response sendResponse = emailsBizConsumer.sendEmailWithTemplate(sendIO);
		if (sendResponse.getStatus() == 202) {
			Thread.sleep(10000);
			CrmNotificationValidation notificationHelper = new CrmNotificationValidation();
			Response notificationResponse = notificationHelper
					.getNotificationByType();
			CollectionEntity<NotificationBean> listNotificationBean = new CollectionEntity<NotificationBean>();
			listNotificationBean = notificationResponse
					.readEntity(new GenericType<CollectionEntity<NotificationBean>>() {
					});
			Logging.info("get Notification response >>"
					+ gson.toJson(listNotificationBean));
			List<NotificationBean> notificationList = new ArrayList<NotificationBean>(
					listNotificationBean.getItems());
			for (NotificationBean notificationBean : notificationList) {
				List<Actor> actors = notificationBean.getActors();
				Assert.assertNotNull(actors, "Actor is null");
				for (Actor actor : actors) {
					if (actor.getId().equals(candidateId)) {
						notificationFlag = true;
						break;
					}
				}
				if (notificationFlag) {
					break;
				}
			}
			Assert.assertTrue(notificationFlag,
					"Notification is not created for Unsubscribed candidate >>> "
							+ candidateId);

		} else {
			Assert.fail("Send email failed and status code is >> "
					+ sendResponse.getStatus());
		}

	}

	public void validateRemindMe() {
		EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
		String candidateId = ProfileHelper.createProfile();
		ScheduleNotificationRequest remindMeIO = new ScheduleNotificationRequest();
		remindMeIO.setUserId(candidateId);
		remindMeIO.setUserName(factory.getFirstName());
		ZonedDateTime triggerTime = ZonedDateTime
				.parse("2017-02-26T10:31:15.723Z");
		remindMeIO.setTriggerTime(triggerTime);
		remindMeIO.setContent("Spire Automation is created reminded me ");
		Response response = emailsBizConsumer.remindMe(remindMeIO);
		if (response.getStatus() == 200) {				
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Assert.assertEquals(responseEntity.getCode(),0,"Expected code is 0 ,but found discripancy ");			
			Map<String, String>  successResponse = responseEntity.getSuccess();			 
			String scheduleID = successResponse.get(candidateId);
			Assert.assertNotNull(scheduleID);
			List<String> failuerResponse = responseEntity.getFailure();
			System.out.println("failuerResponse" + failuerResponse);
		} else {
			String errorResponse = response.readEntity(String.class);
			Assert.fail("Set reminder for candidate is failed and status code is >> "
					+ response.getStatus()
					+ " and response is >> "
					+ errorResponse);
		}

	}

}

package com.spire.crm.restful.entity.consumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.activitystream.Activity;
import spire.crm.entity.com.entities.Asset;
import spire.crm.entity.com.entities.Email;
import spire.crm.entity.com.entities.ResponseEntity;
import spire.crm.entity.com.entities.SendCustomEmailInputVO;
import spire.crm.entity.com.entities.SendEmailInputVO;
import spire.crm.entity.com.entities.Template;
import spire.crm.entity.com.entities.TemplateSummary;
import spire.talent.common.beans.CollectionEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.common.EmailQueryParamPojo;
import com.spire.common.ProfileHelper;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;

import crm.activitystream.beans.ActivitySearchFilter;
import crm.activitystream.beans.CRMHomeActivities;

public class EmailsConsumer extends BaseServiceConsumerNew {
	String createTemplateMsg = "All records successfully inserted. Please refer ids generated for the corresponding template name";
	static String beforeSubject = "WehaveOpening";
	static String AfterSubject = "IGotchanged";
	String emailId2 = "santosh.c@spire2grow.com";
	ArrayList<String> messageId = new ArrayList<String>();
	HashMap<String, String> templateValuesMap = new HashMap<>();
	ActivityStreamBizServiceConsumer Activities = null;

	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("CRM_EMAILS_ENTITY");
	public static Gson gson = new Gson();

	public ObjectMapper mapper = new ObjectMapper();

	/**
	 * Need to pass service base URL . * @param URL
	 */
	public EmailsConsumer() {
		// Logging.log(Key.METHOD, "EmailsEntity constructor", Key.MESSAGE,
		// "Service end point URL >>>");

	}

	public TemplateSummary listEmailTemplate(String limit, String offset) {
		TemplateSummary tem = null;

		String serviceEndPoint = this.endPointURL + "/_list?limit=" + limit + "&offset=" + offset;
		try {
			response = executeGET(serviceEndPoint, true);
			Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint, true);
			Logging.log("response code >>>" + response.getStatus());

			if (response.getStatus() == 200) {
				String strResponse = response.readEntity(String.class);
				mapper.registerModule((Module) new JavaTimeModule());
				Logging.log("strResponse" + strResponse);

				tem = mapper.readValue(strResponse, TemplateSummary.class);
				Logging.log("Response >>>" + gson.toJson(tem));
				Assert.assertTrue(true, "Listed email templates");
			} else {
				Logging.log("### Failed to get Success message ,Got Resposne code as :" + response.getStatus());
				Assert.fail("Failed to get Success message. Resposne code is :" + response.getStatus());
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.log("Exception thrown while mapping values");
		}
		return tem;
	}

	public void verifyTemplateCreated(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary) {
		boolean isPresent = true;
		String limit = emailQueryParam.getLimit();
		String offset = emailQueryParam.getOffset();
		Logging.log("limit:" + limit + "offset:" + offset);
		templateSummary = listEmailTemplate(limit, offset);
		for (int i = 0; i < templateSummary.getTotalCount(); i++) {

			java.util.List<Template> templates = templateSummary.getTemplates();
			if (templates.get(i).getName().contains(template.getName())) {
				isPresent = true;
				Logging.log("Name matches");
				Logging.log("was able to  Verify ListTemplates !!!");
				Assert.assertTrue(true, "Created template did  found in list " + template.getName() + "");
				break;

			} else {
				isPresent = false;
			}
		}
		if (isPresent == false) {
			Assert.fail("Created template did not found in list");
			Logging.log("Created template did not found in list");
		}
	}

	public int gen() {
	    Random r = new Random(System.currentTimeMillis());
	    return 1000000000 + r.nextInt(2000000000);
	}
	
	public String createEmailTemplate(Template template) {
		String serviceEndPoint = this.endPointURL;
		String templateID = "";
		TemplateSummary tempSummary = new TemplateSummary();
		ResponseEntity responsejson = null;
		String tempName = String.valueOf(gen())+"_TestTemp";
		template.setName(tempName);
		templateValuesMap.put("TempName", tempName);

		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(template, MediaType.APPLICATION_JSON));
			responsejson = response.readEntity(ResponseEntity.class);

			if (response.getStatus() == 200 && responsejson.getMessage().contains(createTemplateMsg)) {
				Logging.log("Template created successfully with status code :" + response.getStatus());
				Assert.assertTrue(true, "Template created successfully");
				templateID = responsejson.getSuccess().get(tempName);
			} else if (responsejson.getMessage().contains("Template already exists")) {
				Logging.log("Template name Already Exists So Deleting and Creating new template.");
				String tempId = getTemplateID(tempName, "1000", "0", tempSummary);
				justDeleteTemp(tempId);
				response = executePOST(serviceEndPoint, true, Entity.entity(template, MediaType.APPLICATION_JSON));
				responsejson = response.readEntity(ResponseEntity.class);

				if (response.getStatus() == 200 && responsejson.getMessage().contains(createTemplateMsg)) {
					templateID = responsejson.getSuccess().get(tempName);
					Logging.log("Template created success with status code :" + response.getStatus());
					Assert.assertTrue(true, "Template created success");
				} else {
					Logging.log("Template created failed with status code :" + response.getStatus());
					Assert.fail("Template created failed");
				}
			} else {
				Logging.log("Template created failed with status code :" + response.getStatus());
				Assert.fail("Template created failed");

			}

		} catch (Exception e) {
			Logging.log("In catch block of  createEmailTemplate with exception and resposnse code :"
					+ response.getStatus() + " And Messsage" + responsejson.getMessage());
			String failureResposne = responsejson.getFailure().get(0);
			Logging.log("Failed to create template , response is :" + failureResposne);
			e.printStackTrace();
			Assert.fail("Failed to create template , In catch block");
		}
		return templateID;

	}

	public void justDeleteTemp(String tempId) {
		ResponseEntity responseEntity = null;
		String serviceEndPoint = this.endPointURL + "?template_id=" + tempId;

		try {
			waitTwoSec();
			waitTwoSec();
			response = executeDELETE(serviceEndPoint, true);
			responseEntity = response.readEntity(ResponseEntity.class);
			String message = responseEntity.getSuccess().get(tempId);
			
			if (response.getStatus() == 200 && message.equals("deleted")) {

				Logging.log("Deleted Successfully");
				Logging.log("Template " + tempId + " got deleted successfully");
			} else {
				Logging.log("Template " + tempId + " got deleted successfully");
				Assert.fail("Not able to delete template");
			}
		} catch (Exception e) {
			Logging.log("In catch block of justDeleteTemp");
			String failMessge = responseEntity.getFailure().get(0);
			Logging.log("Got exception justDeleteTemp error message: " + failMessge);
			Assert.fail("Failing testcase due to exception.");
			e.printStackTrace();
		}
	}

	public String editTemplate(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary) {

		ResponseEntity resposneEntity = null;
		template.setSubject(beforeSubject);
		String tempId = createEmailTemplate(template);

		waitTwoSec();
		String serviceEndPoint = this.endPointURL;
		template.setSubject(AfterSubject);
		waitTwoSec();
		template.setId(tempId);
		templateValuesMap.put("TempId", tempId);
		List<Asset> assets = new ArrayList<>();
		template.setAssets(assets);
		
		try {
			response = executePUT(serviceEndPoint, true, Entity.entity(template, MediaType.APPLICATION_JSON));
			resposneEntity = response.readEntity(ResponseEntity.class);
			waitTwoSec();

			if (response.getStatus() == 200 && resposneEntity.getSuccess().get(template.getName()).equals(tempId)) {
				Logging.log("Got success resposne " + response.getStatus());
				Assert.assertTrue(true, "Got success resposne");
			} else {
				Logging.log("Did not get success resposne " + response.getStatus());
				Assert.fail("Did not get success resposne");
			}
		} catch (Exception e) {
			Logging.log("Got Exception During Put call , In catch Block :" + resposneEntity.getMessage());
			Assert.fail("Got Exception During Put call , In catch Block");
			e.printStackTrace();

		}
		return template.getName();
	}

	public String verifyEdit(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary,
			String tempName) {
		String changedTempSub = getTemplateSubject(tempName, emailQueryParam.getLimit(), emailQueryParam.getOffset(),
				templateSummary);
		waitTwoSec();
		Logging.log("ChangedName : " + changedTempSub);

		if (changedTempSub.equalsIgnoreCase(AfterSubject)) {

			Logging.log("successfully changed Subject");
			Assert.assertTrue(true, "successfully changed Subject");
		} else {
			Logging.log("Before change of subject and after change Not matching");
			Assert.fail("Before change of subject and after change Not matching");
		}

		return templateValuesMap.get("TempId");
	}

	public static String getSeconds() {
		Calendar calendar = Calendar.getInstance();
		int seconds = calendar.get(Calendar.SECOND);

		return String.valueOf(seconds);

	}

	public String getTemplateID(String templateName, String limit, String offset, TemplateSummary tempSummary) {
		String tempID = "";
		tempSummary = listEmailTemplate(limit, offset);
		waitTwoSec();
		for (int i = 0; i <= tempSummary.getTotalCount(); i++) {

			if (templateName.equals(tempSummary.getTemplates().get(i).getName())) {

				tempID = tempSummary.getTemplates().get(i).getId();
				break;

			}
		}
		return tempID;

	}

	public String getTemplateSubject(String templateName, String limit, String offset, TemplateSummary tempSummary) {
		String tempSubject = "";
		tempSummary = listEmailTemplate(limit, offset);
		waitTwoSec();
		for (int i = 0; i <= tempSummary.getTotalCount(); i++) {

			if (templateName.equals(tempSummary.getTemplates().get(i).getName())) {

				tempSubject = tempSummary.getTemplates().get(i).getSubject();
				break;

			}
		}
		return tempSubject;

	}

	public long getSentcountOfTemplate(String tempName, String limit, String offset, TemplateSummary tempSummary) {
		long sentCount = 0;

		tempSummary = listEmailTemplate(limit, offset);
		waitTwoSec();
		for (int i = 0; i <= tempSummary.getTotalCount(); i++) {

			if (tempName.equals(tempSummary.getTemplates().get(i).getName())) {

				sentCount = tempSummary.getTemplates().get(i).getSendCount();
				break;

			}
		}
		return sentCount;
	}

	public void waitTwoSec() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
	}
	
	public void veryfyUnsubscribe(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary, SendEmailInputVO sendValues){
		String msgId1 = "";
		ResponseEntity responsejson = null;
		templateValuesMap.put("TempContent", template.getContentBody());
		
		String profileID1 = ProfileHelper.createProfile(sendValues.getFromEmail());
		templateValuesMap.put("ProfileId", profileID1);

		String tempId = createEmailTemplate(template);
		templateValuesMap.put("TempId", "tempId");
		Logging.log("Created ID is :" + tempId);
		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails/_sendEmails");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);

		sendValues.setLeadIds(Values);
		sendValues.setTemplateId(tempId);
		String reqjson = gson.toJson(sendValues);
		Logging.log("Request Json is : "+reqjson);

		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
			responsejson = response.readEntity(ResponseEntity.class);
			Logging.log("Message displayed in resposne :" + responsejson.getMessage());

			if (response.getStatus() == 200 && responsejson.getMessage().contains("Successfully send emails;")) {
				msgId1 = responsejson.getSuccess().get(profileID1);
				templateValuesMap.put("MessageId", msgId1);
				Logging.log("Message Id is :" + msgId1);
				waitTwoSec();
				waitTwoSec();
				Logging.log("Got success message for eail ID we have unsubscribed with message : "+responsejson.getMessage());
				Assert.fail( "Got success message for eail ID we have unsubscribed , hence failing");
				justDeleteTemp(tempId);

			} else {
				Assert.assertTrue(true, "Got Failure message for eail ID we have unsubscribed , hence Passing");
				Logging.log("Message displayed in resposne for unsubscribed Email :" + responsejson.getMessage());
			}
		} catch (Exception e) {
			justDeleteTemp(tempId);
			Logging.log("Got Exception for send mail call with resposne message " + responsejson.getMessage()
					+ ", In catch block");
			Assert.fail("Message displayed in resposne :" + responsejson.getMessage() + ", So Failing Test Case");
			e.printStackTrace();
		}
	}

	/**
	 * Send email , It will call Create Profile , Create Template , List
	 * Template ,Checks Send Count , Delete Created Template
	 * 
	 * @param template
	 * @param emailQueryParam
	 * @param templateSummary
	 * @param sendValues
	 */
	public String sendEmailSingle(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary, SendEmailInputVO sendValues) {
		String msgId1 = "";
		ResponseEntity responsejson = null;
		templateValuesMap.put("TempContent", template.getContentBody());
		
		String profileID1 = ProfileHelper.createProfile(sendValues.getFromEmail());
		templateValuesMap.put("ProfileId", profileID1);

		String tempId = createEmailTemplate(template);
		long initialCount = getSentCount(templateValuesMap.get("TempName"), emailQueryParam.getLimit(),
				emailQueryParam.getOffset(), templateSummary);

		templateValuesMap.put("TempId", "tempId");
		Logging.log("Created ID is :" + tempId);
		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails/_sendEmails");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);

		sendValues.setLeadIds(Values);
		sendValues.setTemplateId(tempId);
		String reqjson = gson.toJson(sendValues);
		Logging.log(reqjson);

		try {

			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
			responsejson = response.readEntity(ResponseEntity.class);
			Logging.log("Message displayed in resposne :" + responsejson.getMessage());

			msgId1 = responsejson.getSuccess().get(profileID1);
			templateValuesMap.put("MessageId", msgId1);
			Logging.log("Message Id is :" + msgId1);
			if (response.getStatus() == 200 && responsejson.getMessage().contains("Successfully send emails;")) {
				waitTwoSec();
				waitTwoSec();
				Logging.log("Got success message for Send mail Call");

				long afterSendCount = getSentCount(templateValuesMap.get("TempName"), emailQueryParam.getLimit(),
						emailQueryParam.getOffset(), templateSummary);

				if (afterSendCount == initialCount + 1) {

					Logging.log("Sent Count Succesfully Increased");
					Assert.assertTrue(true, "Sent Count Succesfully Increased:" + afterSendCount + "");

				} else {

					Logging.log("Sent Count Not Increased");
					Assert.fail("Sent Count Not Increased, So Failing Test Case");
				}

				justDeleteTemp(tempId);

			} else {
				Logging.log("Message displayed in resposne :" + responsejson.getMessage());
				Assert.fail("Message displayed in resposne :" + responsejson.getMessage() + ", So Failing Test Case");

			}
		} catch (Exception e) {

			Logging.log("Got Exception for send mail call with resposne message " + responsejson.getMessage()
					+ ", In catch block");
			Assert.fail("Message displayed in resposne :" + responsejson.getMessage() + ", So Failing Test Case");
			e.printStackTrace();
		}

		return msgId1;

	}

	public void sendEmailBulk(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary,
			SendEmailInputVO sendValues) {
		ResponseEntity responsejson = null;
		String profileID1 = ProfileHelper.createProfile(sendValues.getFromEmail());
		String profileID2 = ProfileHelper.createProfile(emailId2);
		Logging.log("Profile ID:" + profileID2);

		String tempId = createEmailTemplate(template);
		Logging.log("Created Template Id is :" + tempId);

		long initialCount = getSentCount(templateValuesMap.get("TempName"), emailQueryParam.getLimit(),
				emailQueryParam.getOffset(), templateSummary);

		Logging.log("Created ID is :" + tempId);
		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails/_sendEmails");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);
		Values.add(profileID2);
		sendValues.setLeadIds(Values);
		sendValues.setTemplateId(tempId);
		String reqjson = gson.toJson(sendValues);
		Logging.log(reqjson);

		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
			responsejson = response.readEntity(ResponseEntity.class);

			String msgId1 = responsejson.getSuccess().get(profileID1);
			String msgId2 = responsejson.getSuccess().get(profileID2);
			messageId.add(msgId1);
			messageId.add(msgId2);

			if (response.getStatus() == 200 && responsejson.getMessage().contains("Successfully send emails;")) {
				waitTwoSec();
				Logging.log("Got success message for Send mail Call");

				long afterSendCount = getSentCount(templateValuesMap.get("TempName"), emailQueryParam.getLimit(),
						emailQueryParam.getOffset(), templateSummary);

				if (afterSendCount == initialCount + 2) {

					Logging.log("Sent Count Succesfully Increased");
					Assert.assertTrue(true, "Sent Count Succesfully Increased:" + afterSendCount + "");

				} else {

					Logging.log("Sent Count Not Increased");
					Assert.fail("Sent Count Not Increased, So Failing Test Case");
				}

				justDeleteTemp(tempId);

			} else {
				Logging.log("Message displayed in resposne :" + responsejson.getMessage());
				Assert.fail("Message displayed in resposne :" + responsejson.getMessage() + ", So Failing Test Case");
				justDeleteTemp(tempId);
			}
		} catch (Exception e) {
			justDeleteTemp(tempId);
			Logging.log("Got Exception for send mail call with resposne message " + responsejson.getMessage()
					+ ", In catch block");
			Assert.fail("Message displayed in resposne :" + responsejson.getMessage() + ", So Failing Test Case");
			e.printStackTrace();
		}
		

	}

	public void sendCustomEmail(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary,
			SendCustomEmailInputVO sendValues) {
		ResponseEntity responseEntity = null;
		String profileID1 = ProfileHelper.createProfile(sendValues.getFromEmail());
		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails/_sendCustomEmails");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);
		sendValues.setLeadIds(Values);

		String reqjson = gson.toJson(sendValues);
		Logging.log(reqjson);
		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
			responseEntity = response.readEntity(ResponseEntity.class);

			Logging.log("Message displayed in resposne :" + responseEntity.getMessage());

			String msgId1 = responseEntity.getSuccess().get(profileID1);

			if (response.getStatus() == 200 && responseEntity.getMessage().contains("Successfully send emails;")) {
				Logging.log("Send CustomEmail Successfull");
			} else {

				Logging.log("Send CustomEmail failed with message :" + responseEntity.getMessage() + "with status:"
						+ response.getStatus());
				Assert.fail("Send CustomEmail failed");
			}

		} catch (Exception e) {

			Logging.log("Message displayed in resposne :" + responseEntity.getMessage());
			Assert.fail("Message displayed in resposne :" + responseEntity.getMessage() + ", So Failing Test Case");
			e.printStackTrace();
		}
	}

	public void sendCustomEmailBulk(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary, SendCustomEmailInputVO sendValues) {
		ResponseEntity responseEntity = null;
		String profileID1 = ProfileHelper.createProfile(sendValues.getFromEmail());
		String profileID2 = ProfileHelper.createProfile(emailId2);
		Logging.log("Profile ID:" + profileID2);

		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails/_sendCustomEmails");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);
		Values.add(profileID2);

		sendValues.setLeadIds(Values);

		String reqjson = gson.toJson(sendValues);
		Logging.log(reqjson);
		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
			responseEntity = response.readEntity(ResponseEntity.class);

			String msgId1 = responseEntity.getSuccess().get(profileID1);
			String msgId2 = responseEntity.getSuccess().get(profileID2);

			if (response.getStatus() == 200 && responseEntity.getMessage().contains("Successfully send emails;")) {
				Logging.log("Send CustomEmail Successfull");
			} else {

				Logging.log("Send CustomEmail failed with message :" + responseEntity.getMessage() + "with status:"
						+ response.getStatus());
				Assert.fail("Send CustomEmail failed");
			}

		} catch (Exception e) {
			Logging.log("Message displayed in resposne :" + responseEntity.getMessage());
			Assert.fail("Message displayed in resposne :" + responseEntity.getMessage() + ", So Failing Test Case");
			e.printStackTrace();
		}
	}

	public long getSentCount(String tempName, String limit, String offset, TemplateSummary tempSummary) {
		long countIs = getSentcountOfTemplate(tempName, limit, offset, tempSummary);
		return countIs;
	}

	public void getActivity() {

		ActivityStreamEntityServiceConsumer consumer = new ActivityStreamEntityServiceConsumer();
		CollectionEntity<Activity> activities = consumer.getActivitiesByPerson("dsdsd", "0", "100", null);
		activities.getItems();
	}

	public void homeActivity(ActivitySearchFilter activitySearchFilter) {
		CRMHomeActivities act = Activities._homeactivities(activitySearchFilter, "0", "10");
		act.getHomeActivities().get(1).getActivity().getAction();
	}

	/**
	 * TO get Template
	 * 
	 * @param template
	 * @param emailQueryParam
	 * @param templateSummary
	 */
	public void getTemplate(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary) {
		String tempIdToVerify = "";
		Template temp = null;
		TemplateSummary templates = listEmailTemplate(emailQueryParam.getLimit(), emailQueryParam.getOffset());

		if (templates.getTotalCount() > 0) {
			Logging.log("Template count is Greater than 0");
			verifyGetTemplate(tempIdToVerify, templates, temp);
		} else {
			Logging.log("Template count is less than 0 , So Creating new template");
			String tempId = createEmailTemplate(template);
			verifyGetTemplate(tempId, templates, temp);
			justDeleteTemp(tempId);
		}
	}

	/**
	 * Method to Verify Get Template Service
	 * 
	 * @param tempId
	 * @param templates
	 * @param temp
	 */
	public void verifyGetTemplate(String tempId, TemplateSummary templates, Template temp) {
		tempId = templates.getTemplates().get(0).getId();

		String serviceEndPoint = this.endPointURL + "?template_id=" + tempId;
		Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint, true);

		response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());

		if (response.getStatus() == 200) {
			Assert.assertTrue(true, "get Success message code:" + response.getStatus());
		} else {
			Logging.log("response code >>>" + response.getStatus());
			Assert.fail("Did not get Success message code:" + response.getStatus());
		}

		try {

			String strResponse = response.readEntity(String.class);
			mapper.registerModule((Module) new JavaTimeModule());
			temp = mapper.readValue(strResponse, Template.class);
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		if (temp.getId().equals(tempId)) {
			Logging.log("Input Template ID " + tempId + " and Response template ID " + temp.getId() + " matches");
			Assert.assertTrue(true,
					"Input Template ID " + tempId + " and Response template " + temp.getId() + " matches");

		} else {
			Logging.log(
					"Input Template ID " + tempId + " and Response template ID " + temp.getId() + " does not matches");
			Assert.fail("Input Template ID and Response template ID does not matches");
		}
	}

	/**
	 * This method will get MessageId and check content
	 * 
	 * @param template
	 * @param emailQueryParam
	 * @param templateSummary
	 * @param sendValues
	 */
	public Email getMessage(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary,
			SendEmailInputVO sendValues, String msgId) {
		Email email = null;

		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails") +"/"+msgId;
		Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint, true);
		response = executeGET(serviceEndPoint, true);

		try {
			if (response.getStatus() == 200) {
				Logging.log("Got success resposne code as : " + response.getStatus());

				String strResponse = response.readEntity(String.class);
				mapper.registerModule((Module) new JavaTimeModule());
				
					email = mapper.readValue(strResponse, Email.class);


			} else {
				Assert.fail("Not able to get success resposne code " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logging.log("Failed to mapp with mapper class, In catch block");
			Assert.fail("Failed to mapp with mapper class, In catch block");
			
		}

		return email;
	}

	public void messageContentValidation(Email email) {

		String tempContentMap = templateValuesMap.get("TempContent");
		String tempContent = email.getContent();

		if (tempContent.contains(tempContentMap) && templateValuesMap.get("ProfileId").equals(email.getToId())) {
			Logging.log(" Fetched Message Id " + email.getMessageId() + "conatains proper details ");
			Assert.assertTrue(true, "Fetched Message Id contains proper details");
		} else {
			Logging.log(" Fetched Message Id " + email.getMessageId() + "does not conatains proper details ");
			Assert.fail("Fetched Message Id does not contains proper details");
		}
	}

	public void markEmailAsRead(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary,
			SendEmailInputVO sendValues) {
		ResponseEntity responseEntity = null;

		Email email = null;
		String msgID = sendEmailSingle(template, emailQueryParam, templateSummary, sendValues);

		templateValuesMap.put("MessageId", msgID);

		String serviceEndPoint = this.endPointURL.replace("email-templates", "emails/markread/") + msgID;

		Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint, true);
		try {
			response = executeGET(serviceEndPoint, true);
			responseEntity = response.readEntity(ResponseEntity.class);
			if (response.getStatus() == 200) {
				Logging.log("Got Success message code:" + response.getStatus());

				email = getMessage(template, emailQueryParam, templateSummary, sendValues, msgID);

				if (email.getIsRead() == true && responseEntity.getMessage().contains("Record successfully updated")) {
					Logging.log("Is read parameter truned to true after call");
					Assert.assertTrue(true, "Is read parameter truned to true after call");

				} else {
					Assert.fail("IsRead is not true. After mark read call");
					Logging.log("IsRead is not true. After mark read call" + email.getIsRead());

				}

			} else {
				Logging.log("Send Emails Successfull , Got response code :" + response.getStatus());
				Assert.fail("Didn't get success message.");
			}

		} catch (Exception e) {
			Logging.log("Got Exception with resposne Message :" + responseEntity.getMessage());
			Assert.fail("Got Exception with resposne Message");
			e.printStackTrace();
		}
	}

	public String unsubscribeCandidate(String emailId) {
		ResponseEntity responseEntity = null;
		String serviceEndPoint = this.endPointURL.replace("email-templates", "subscriptions/unsubscribeEmail/") + emailId;
				
		response = executePOST(serviceEndPoint, true, Entity.entity(true, MediaType.APPLICATION_JSON));
		Logging.log("Unsubscribe URL is :" + serviceEndPoint);
		responseEntity = response.readEntity(ResponseEntity.class);
		if (response.getStatus() == 202) {
			Logging.log("Got Success message code" + response.getStatus());
			Assert.assertTrue(true,
					"Got Success message code And Candidate " + "candidateID" + " successfully Unsubscribe");
		} else {
			Logging.log("Unsubscribe failed, Got response code :" + response.getStatus() + " with message :"
					+ responseEntity.getMessage());
			Assert.fail("Didn't get success message.");
		}
		return emailId;

	}
	
	
	public String subscribeCandidate(String emailId) {
		ResponseEntity responseEntity = null;
		String serviceEndPoint = this.endPointURL.replace("email-templates", "subscriptions/subscribeEmail/") + emailId;
				
		response = executePOST(serviceEndPoint, true, Entity.entity(true, MediaType.APPLICATION_JSON));
		Logging.log("Unsubscribe URL is :" + serviceEndPoint);
		responseEntity = response.readEntity(ResponseEntity.class);
		if (response.getStatus() == 202) {
			Logging.log("Got Success message code" + response.getStatus());
			Assert.assertTrue(true,
					"Got Success message code And Candidate " + "candidateID" + " successfully subscribe");
		} else {
			Logging.log("subscribe failed, Got response code :" + response.getStatus() + " with message :"
					+ responseEntity.getMessage());
			Assert.fail("Didn't get success message.");
		}
		return emailId;

	}
	

}

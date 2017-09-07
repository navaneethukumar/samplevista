package com.spire.crm.restful.biz.consumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.common.EmailQueryParamPojo;
import com.spire.common.NotificationHelper;
import com.spire.common.ProfileHelper;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.entity.consumers.ActivityStreamEntityServiceConsumer;

import crm.activitystream.beans.ActivitySearchFilter;
import crm.activitystream.beans.CRMHomeActivities;
import spire.commons.activitystream.Activity;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.biz.com.service.beans.SendCustomEmailVO;
import spire.crm.biz.com.service.beans.SendEmailWithTemplateVO;
import spire.crm.entity.com.entities.Asset;
import spire.crm.entity.com.entities.ResponseEntity;
import spire.crm.entity.com.entities.Template;
import spire.crm.entity.com.entities.TemplateSummary;
import spire.crm.notification.beans.NotificationBean;
import spire.talent.common.beans.CollectionEntity;

public class ComEmailsBizConsumer extends BaseServiceConsumerNew {
	
	String createTemplateMsg = "All records successfully inserted. Please refer ids generated for the corresponding template name";
	static String beforeSubject = "WehaveOpening";
	static String AfterSubject = "IGotchanged";
	ActivityStreamBizServiceConsumer activities = null;
	HashMap<String, String> templateValuesMap = new HashMap<>();
	private static Logger logger = LoggerFactory.getLogger(ComEmailsBizConsumer.class);
	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("CRM_EMAILS_BIZ");
	public static Gson gson = new Gson();

	public ObjectMapper mapper = new ObjectMapper().registerModule((Module) new JavaTimeModule());

	public ComEmailsBizConsumer() {
		//Logging.log(Key.METHOD, "EmailsEntity constructor", Key.MESSAGE, "Service end point URL >>>");

	}

	public String createEmailTemplate(Template template) {
		String serviceEndPoint = this.endPointURL + "/crm-templates/create";
		String templateID = "";
		TemplateSummary tempSummary = new TemplateSummary();
		ResponseEntity responsejson = null;
		String tempName = String.valueOf(getSeconds());
		template.setName(tempName);
		templateValuesMap.put("TempName", tempName);

		try {
			Logging.log("-------------------Creating Template------------------------");
			response = executePOST(serviceEndPoint, true, Entity.entity(template, MediaType.APPLICATION_JSON));
			responsejson = response.readEntity(ResponseEntity.class);
              waitTwoSec();
			if (response.getStatus() == 201 && responsejson.getMessage().contains(createTemplateMsg)) {
				templateID = responsejson.getSuccess().get(tempName);
				Logging.log("Template created successfully with status code :" + response.getStatus());
				Assert.assertTrue(true, "Template created successfully");
			} else if (responsejson.getMessage().contains("Template already exists")) {
				Logging.log("Template name Already Exists So Deleting and Creating new template.");
				String tempId = getTemplateID(tempName, "1000", "0", tempSummary);
				justDeleteTemp(tempId);
				Logging.log("-------------------Creating Template------------------------");
				response = executePOST(serviceEndPoint, true, Entity.entity(template, MediaType.APPLICATION_JSON));
				responsejson = response.readEntity(ResponseEntity.class);

				if (response.getStatus() == 200 && responsejson.getMessage().contains(createTemplateMsg)) {
					waitTwoSec();
					templateID = responsejson.getSuccess().get(tempName);
					Logging.log("Template created success with status code :" + response.getStatus());
					Assert.assertTrue(true, "Template created success");
				} else {
					Logging.log("Template created failed with status code :" + response.getStatus());
					Assert.fail("Template created failed");
				}
			} else {
				Logging.log("Template created failed with status code :" + response.getStatus()+"and Messsage "+responsejson.getMessage());
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

	public static String getSeconds() {
		Calendar calendar = Calendar.getInstance();
		int seconds = calendar.get(Calendar.SECOND);

		return String.valueOf(seconds);

	}

	public TemplateSummary listEmailTemplate(String limit, String offset) {
		TemplateSummary tempSummary = new TemplateSummary();
		String serviceEndPoint = this.endPointURL + "/list?limit=" + limit + "&offset=" + offset;
		try {
			response = executeGET(serviceEndPoint, true);
			Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint);
			Logging.log("response code >>>" + response.getStatus());
			waitTwoSec();
			if (response.getStatus() == 200) {
				String strResponse = response.readEntity(String.class);
				mapper.registerModule((Module) new JavaTimeModule());
				tempSummary = mapper.readValue(strResponse, TemplateSummary.class);
				Logging.log("Response JSON >>>" + gson.toJson(tempSummary));
				Assert.assertTrue(true, "Listed email templates");
			} else {

				Logging.log("### Failed to get Success message ,Got Resposne code as :" + response.getStatus());
				Assert.fail("Failed to get Success message. Resposne code is :" + response.getStatus());
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.log("Exception thrown while mapping values");
		}
		return tempSummary;
	}

	public void verifyTemplateCreated(Template template, EmailQueryParamPojo emailQueryParam) {
		boolean isPresent = true;
		String limit = emailQueryParam.getLimit();
		String offset = emailQueryParam.getOffset();
		System.out.println("limit:" + limit + "offset:" + offset);
		TemplateSummary templateSummary = listEmailTemplate(limit, offset);
		waitTwoSec();
		for (int i = 0; i < templateSummary.getTotalCount(); i++) {

			java.util.List<Template> templates = templateSummary.getTemplates();
			if (templates.get(i).getName().contains(template.getName())) {
				isPresent = true;
				System.out.println("Name matches");
				Logging.log("was able to  Verify ListTemplates !!!");
				Assert.assertTrue(true, "Created template found in list");
				break;

			} else {
				isPresent = false;
				Assert.fail("Created template did not found in list");
				Logging.log("Created template did not found in list");
			}
		}
		

	}

	public void justDeleteTemp(String tempId) {
		ResponseEntity responseEntity = null;
		waitTwoSec();

		String serviceEndPoint = this.endPointURL + "/" + tempId;

		try {
			response = executeDELETE(serviceEndPoint, true);
			responseEntity = response.readEntity(ResponseEntity.class);
			String message = responseEntity.getSuccess().get(tempId);
			
			if (response.getStatus() == 200 && responseEntity.getMessage().contains("deleted")) {

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

	public void waitTwoSec() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
	}

	public String sendEmailSingle(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary, SendEmailWithTemplateVO sendValues) {
		String msgId = "";
		templateValuesMap.put("TempContent", template.getContentBody());
		
		String profileID1 = ProfileHelper.createProfile(sendValues.getFrom());
		templateValuesMap.put("ProfileId", profileID1);
		
		String tempId = createEmailTemplate(template);
		templateValuesMap.put("TempName", template.getName());
		System.out.println("Created Template Name is :" + template.getName());
		
     	long initialCount = getSentCount(template.getName(), emailQueryParam.getLimit(), emailQueryParam.getOffset());
		templateValuesMap.put("TempId", tempId);
		sendValues.setTemplateId(tempId);
		System.out.println("Created ID is :" + tempId);

		String serviceEndPoint = this.endPointURL.replace("crm-templates", "crm-emails/send");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);

		sendValues.setTo(Values);
		// sendValues.
		String reqjson = gson.toJson(sendValues);
		System.out.println(reqjson);
	//	System.out.println("Total Notifications:" + getNotifications());

		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
				if (response.getStatus() == 202) {
					
					waitTwoSec();
					waitTwoSec();
					
				Logging.log("Send Emails Successfull");
                String tempName = template.getName();
				long afterSendCount = getSentCount(tempName, emailQueryParam.getLimit(), emailQueryParam.getOffset()
				);
             
				if (afterSendCount == initialCount + 1) {

					Logging.log("Sent Count Succesfully Increased");
					Assert.assertTrue(true, "Sent Count Succesfully Increased:" + afterSendCount + "");

				} else {
					
					Logging.log("Sent Count Not Increased");
					Assert.fail("Sent Count Not Increased, So Failing Test Case");
				}

				justDeleteTemp(tempId);

			}
		} catch (Exception e) {
			int afterVal = 	getNotifications();
            System.out.println("after: "+afterVal);
			Logging.log("Failed to create template , In catch block");
			e.printStackTrace();
		}

		return msgId;

	}
	
	public String sendMailSynchronous(Template template, EmailQueryParamPojo emailQueryParam,
			TemplateSummary templateSummary, SendEmailWithTemplateVO sendValues){
		
		String msgId = "";
		ResponseEntity responseEntity =null;
         templateValuesMap.put("TempContent", template.getContentBody());
		String profileID1 = ProfileHelper.createProfile(sendValues.getFrom());
		templateValuesMap.put("ProfileId", profileID1);
		
		String tempId = createEmailTemplate(template);
		templateValuesMap.put("TempName", template.getName());
		System.out.println("Created Template Name is :" + template.getName());
		
     	long initialCount = getSentCount(template.getName(), emailQueryParam.getLimit(), emailQueryParam.getOffset());
		templateValuesMap.put("TempId", tempId);
		sendValues.setTemplateId(tempId);
		System.out.println("Created ID is :" + tempId);

		String serviceEndPoint = this.endPointURL.replace("crm-templates", "crm-emails/send/_synchronous");

		ArrayList Values = new ArrayList();
		Values.add(profileID1);

		sendValues.setTo(Values);
		String reqjson = gson.toJson(sendValues);
		System.out.println(reqjson);
	try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendValues, MediaType.APPLICATION_JSON));
				if (response.getStatus() == 200) {
					
					waitTwoSec();
					waitTwoSec();
					responseEntity = response.readEntity(ResponseEntity.class);
					msgId = 	responseEntity.getSuccess().get(profileID1);
				Logging.log("Send Emails Successfull");
                String tempName = template.getName();
				long afterSendCount = getSentCount(tempName, emailQueryParam.getLimit(), emailQueryParam.getOffset()
				);
             
				if (afterSendCount == initialCount + 1) {

					Logging.log("Sent Count Succesfully Increased");
					Assert.assertTrue(true, "Sent Count Succesfully Increased:" + afterSendCount + "");

				} else {
					
					Logging.log("Sent Count Not Increased");
					Assert.fail("Sent Count Not Increased, So Failing Test Case");
				}

				justDeleteTemp(tempId);

			}else{
				justDeleteTemp(tempId);
				Logging.log("Did not receive Success Resposnse code , Code is :"+response.getStatus() +"with message : "+responseEntity.getFailure().get(0));
				Assert.fail("Failing due to not success message");
			}
		} catch (Exception e) {
			
			Logging.log("Failed to create template , In catch block");
			e.printStackTrace();
		}
		return msgId;
	}

	public long getSentCount(String tempName, String limit, String offset) {
		long countIs = getSentcountOfTemplate(tempName, limit, offset);
		return countIs;
	}

	public long getSentcountOfTemplate(String tempName, String limit, String offset) {
		long sentCount = 0;
		TemplateSummary tempSummary = new TemplateSummary();
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

	public void getActivity(String msgId) {

		ActivityStreamEntityServiceConsumer consumer = new ActivityStreamEntityServiceConsumer();
		CollectionEntity<Activity> activities = consumer.getActivitiesByPerson("dsdsd", "0", "100", null);
		activities.getItems();
	}

	public void markMessageAsRead(String msgId){
		
    	String serviceEndPoint = this.endPointURL.replace("crm-templates", "crm-emails")+"/"+msgId;
		Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint, true);
		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
				Logging.log("Got Success message for mark email as Read code:" + response.getStatus());
				Assert.assertTrue(true, "Mark Email is Successfull");

			} else {
				Logging.log("Got fail message for mark email as Read :" + response.getStatus());
				Assert.fail("Didn't get success message.");
			}

		} catch (Exception e) {
			Logging.log("Got Exception with resposne Message :");
			Assert.fail("Got Exception ");
			e.printStackTrace();
		}
	}
	
	public void getHomeActivity(String msgId, String activityType) {
		waitTwoSec();
		waitTwoSec();
		ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();
		com.spire.crm.activity.biz.pojos.CRMHomeActivities homeActivitiesList = activityStreamBizServiceConsumer
				.getHomeEmailActivities();
		try {

			String actMsgId = homeActivitiesList.getHomeActivities().get(0).getActivity().getObject().getDetail()
					.getMessageId();
			String status = homeActivitiesList.getHomeActivities().get(0).getActivity().getObject().getDetail()
					.getStatus();
			if (actMsgId.equals(msgId) && status.equals(activityType)) {
				Logging.log(
						"Msg ID for sent Mail :" + actMsgId + " and staus of Activity :" + status + "are as Expected");
				Assert.assertTrue(true, "Sent Msg ID and Activity type are  matching");
			} else {
				Logging.log("Sent Msg ID and Activity type are not matching");
				Assert.fail("Sent Msg ID and Activity type are not matching");
			}
		} catch (Exception e) {
			Logging.log("Got Exception during getting Activity , In Catch Block");
			e.printStackTrace();
		}
	}
	
	
	
	

	/**
	 * Send Custom Email
	 * 
	 * @param sendCustomValues
	 */
	public void sendCustomEmail(Template template,SendCustomEmailVO sendCustomValues,EmailQueryParamPojo emailQueryParam) {
		String profileID1 = ProfileHelper.createProfile(sendCustomValues.getFrom());
		templateValuesMap.put("ProfileId", profileID1);
		templateValuesMap.put("EmailId", sendCustomValues.getFrom());
		templateValuesMap.put("TempContent", sendCustomValues.getContentBody());
		String serviceEndPoint = this.endPointURL.replace("crm-templates", "crm-emails/custom/send");
		Logging.log("End Point is :" + serviceEndPoint);

		ArrayList toValues = new ArrayList();
		toValues.add(profileID1);
		sendCustomValues.setTo(toValues);

		String reqjson = gson.toJson(sendCustomValues);
		System.out.println("Resposne:" + reqjson);
		try {
			response = executePOST(serviceEndPoint, true, Entity.entity(sendCustomValues, MediaType.APPLICATION_JSON));
			if (response.getStatus() == 202) {
							
			Logging.log("Send custom email Emails Successfull , with resposne code " +response.getStatus());
			Assert.assertTrue(true, "Sent Custom Email Successfull Increased:");
			
		}else{
			Logging.log("Failed to send custom email ,Resposne code is :"+response.getStatus());
			Assert.fail("Failed to send custom email");
		}
		} catch (Exception e) {
			Logging.log("Failed to send custom email  , In catch block");
			Assert.fail("Failed to send custom email , In catch block");
			e.printStackTrace();
		}
	}

	public CRMHomeActivities _homeactivities(CRMHomeActivities activitySearchFilter, String offset, String limit,
			ActivitySearchFilter actFilter) {

		String serviceEndPoint = "http://192.168.2.183:8082/crm-activitystream/api/activity" + "/"
				+ "_homeactivities?offset=" + offset + "&limit=" + limit;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);

		Logging.log("serviceEndPoint: " + gson.toJson(activitySearchFilter));
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		response = executePOST(serviceEndPoint, true, Entity.entity(actFilter, MediaType.APPLICATION_JSON));

		Logging.log("response code >>>" + response.getStatus());

		String stringResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		System.out.println("stringResponse: " + stringResponse);

		CRMHomeActivities activityFilterResponse = new CRMHomeActivities();
		try {
			activityFilterResponse = mapper.readValue(stringResponse, CRMHomeActivities.class);
			System.out.println("activityFilterResponse: " + gson.toJson(activityFilterResponse));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return activityFilterResponse;
	}

	
	/*  public String _homeactivities(CRMHomeActivities activitySearchFilter,
	 String offset, String limit,ActivitySearchFilter actFilter) {
	 
	 String serviceEndPoint =
	 "http://192.168.2.183:8082/crm-activitystream/api/activity" + "/" +
	 "_homeactivities?offset=" + offset + "&limit=" + limit;
	 System.out.println("serviceEndPoint: "+serviceEndPoint); Logging.log(
	 "serviceEndPoint: " + serviceEndPoint);
	 System.out.println( "serviceEndPoint: " + serviceEndPoint);
	 Response response =
	 executePOST(serviceEndPoint, true, Entity.entity(actFilter,
	 MediaType.APPLICATION_JSON));
	 
	 Logging.log("response code >>>" + response.getStatus());
	 
	 String stringResponse = response.readEntity(String.class);
	 System.out.println("Resposne:"+stringResponse);
	 
	 return stringResponse; }*/
	 

	public void deleteTemplate(Template tempId) {
		ResponseEntity responseEntity = null;
		waitTwoSec();

		String serviceEndPoint = this.endPointURL + "/" + tempId;

		try {
			response = executeDELETE(serviceEndPoint, true);
			responseEntity = response.readEntity(ResponseEntity.class);
			String message = responseEntity.getSuccess().get(tempId);
			waitTwoSec();
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
		
		List<Asset> assets = new ArrayList<Asset>();
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
		System.out.println("ChangedName : " + changedTempSub);

		if (changedTempSub.equalsIgnoreCase(AfterSubject)) {
			Assert.assertTrue(true, "successfully changed Subject");
			Logging.log("successfully changed Subject");
			System.out.println("successfully changed Subject");
		} else {
			Logging.log("Tried to Subject, new name " + changedTempSub + " not matches with expected" + AfterSubject);
			Assert.fail("Not able to update Template name");
		}

		return templateValuesMap.get("TempId");
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

	public void verifyGetTemplate(String tempId, TemplateSummary templates, Template temp) {
		tempId = templates.getTemplates().get(0).getId();

		String serviceEndPoint = this.endPointURL + "/" + tempId;
		Logging.log(" GetEmailTemplate endPointURL  >>>" + serviceEndPoint);

		response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		String strResponse = response.readEntity(String.class);
		mapper.registerModule((Module) new JavaTimeModule());

		try {
			temp = mapper.readValue(strResponse, Template.class);
		} catch (JsonParseException e) {
			Logging.log("Mapper exception");
			e.printStackTrace();
			
		} catch (JsonMappingException e) {
			Logging.log("Mapper exception");
			e.printStackTrace();
			
		} catch (IOException e) {
			Logging.log("Mapper exception");
			e.printStackTrace();
		}

		if (temp.getId().equals(tempId)) {
			Logging.log("Input Template ID and Response template ID matches");
			Assert.assertTrue(true, "Input Template ID and Response template ID matches");

		} else {
			Logging.log("Input Template ID and Response template ID does not matches");
			Assert.fail("Input Template ID and Response template ID does not matches");
		}
	}

	public void getTemplate(Template template, EmailQueryParamPojo emailQueryParam, TemplateSummary templateSummary) {
		String tempIdToVerify = "";
		Template temp = null;
		TemplateSummary templates = listEmailTemplate(emailQueryParam.getLimit(), emailQueryParam.getOffset());

		if (templates.getTotalCount() > 0) {
			Logging.log("Template count is Greater than 0");
			verifyGetTemplate(tempIdToVerify, templates, temp);
		} else {
			Logging.log("Template count is less than 0 , So Creating new template");
			String tempName = createEmailTemplate(template);
			String tempId = getTemplateID(tempName, emailQueryParam.getLimit(), emailQueryParam.getOffset(),
					templateSummary);
			verifyGetTemplate(tempId, templates, temp);
			
			justDeleteTemp(tempId);
		}

	}
	
	public int  getNotifications(){
		NotificationHelper notifications = new NotificationHelper();
		spire.commons.activitystream.CollectionEntity<NotificationBean> Notifications = notifications.getNotification();
		System.out.println("Count is : "+Notifications.getItems().size());
		
		return Notifications.getItems().size();
		//Collection<NotificationBean> noti = Notifications.getItems();
	}
	
	

}

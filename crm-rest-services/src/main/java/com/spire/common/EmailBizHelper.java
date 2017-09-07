package com.spire.common;

import java.util.Random;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.crm.entity.com.entities.Email;
import spire.crm.entity.com.entities.ResponseEntity;
import spire.crm.entity.com.entities.Template;

public class EmailBizHelper extends BaseServiceConsumerNew {

	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_EMAILS_BIZ");
	Gson gson = new Gson();
	Random randomGenerator = new Random();
	ObjectMapper mapper = new ObjectMapper();
	DataFactory datafactory = new DataFactory();

	public String createEmailTemplate() {

		Template template = new Template();

		template.setName("SpireCampaignTemplate"
				+ randomGenerator.nextInt(10000));
		template.setSubject("Campaign Template");
		template.setContentBody("SpireCampaignTemplate");
		template.setContentHeader("SpireCampaignTemplate");
		template.setContentSignature("SpireCampaignTemplate");
		template.setCreatedByName(datafactory.getFirstName());
		template.setModifiedByName(datafactory.getFirstName());

		String serviceEndPoint = this.endPointURL + "/crm-templates/create";
		Logging.log("Create email tempalte service endpint >>>"
				+ serviceEndPoint);
		Logging.log("Create email tempalte service Request >>>"
				+ gson.toJson(template));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(template, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {

			ResponseEntity responseEntity = response
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(
					responseEntity.getMessage(),
					"All records successfully inserted. Please refer ids generated for the corresponding template name",
					"Invalid error message ");

			Logging.log("Template created successfully and response is :"
					+ gson.toJson(responseEntity));

			return responseEntity.getSuccess().get(template.getName());

		} else {

			Assert.fail("Failed to create template and status code is : "
					+ response.getStatus());
			return null;
		}

	}

	public Template getTemplateByID(String templateID) {

		String serviceEndPoint = this.endPointURL + "/crm-templates/"
				+ templateID;

		Response response = executeGET(serviceEndPoint, true);

		if (response.getStatus() == 201) {

			Template template = response.readEntity(Template.class);

			Logging.log("Template created successfully and response is :"
					+ gson.toJson(template));

			return template;

		} else {

			Assert.fail("Failed to create template and status code is : "
					+ response.getStatus());
			return null;
		}

	}

	public Email getMessageDeatils(String messageID) {

		String serviceEndPoint = this.endPointURL + "/crm-emails/" + messageID;
		Logging.log(" Get message details end point >> " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);

		if (response.getStatus() == 200) {

			Email emailMessage = response.readEntity(Email.class);

			Logging.log("Message deatils >>>>>  :" + gson.toJson(emailMessage));

			return emailMessage;

		} else {

			Assert.fail("Failed to get message deatails and status code is : "
					+ response.getStatus());
			return null;
		}

	}

	public Email markEmailasRead(String messageID) {

		String serviceEndPoint = this.endPointURL + "/crm-emails/" + messageID;
		Logging.log(" mark Email as Read  end point >> " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(null, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {

			Email emailMessage = response.readEntity(Email.class);

			Logging.log("Message deatils >>>>>  :" + gson.toJson(emailMessage));

			return emailMessage;

		} else {

			Assert.fail("Failed to get message deatails and status code is : "
					+ response.getStatus());
			return null;
		}

	}

}

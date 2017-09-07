package com.spire.crm.restful.entity.consumers;

import java.io.File;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.testng.Assert;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.profiles.entity.Attachment;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

public class AttachmentsEntityConsumer extends BaseServiceConsumerNew {
	
	private static Logger logger = LoggerFactory.getLogger(AttachmentsEntityConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("ATTACHMENT_SERVICE_ENTITY");
	public static Gson gson = new Gson();
	public AttachmentsEntityConsumer() {
		logger.info(Key.METHOD, "AttachmentsEntityConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + this.endPointURL);
	}

	/**
	 * createAttachment
	 * 
	 * @param fileName AND candidateId
	 * @return attachment
	 */
	/*-------------------------Get OPERATION----------------------------*/
	public Attachment createAttachment(String fileName, String candidateId,MultiPart multiPart) {
		String serviceEndPoint = this.endPointURL + "/" + fileName +"/"+ candidateId+"?category=resume";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" createAttachment endPointURL  >>>" + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA),
				true);
		if (response.getStatus() == 200) {
			Attachment attachment=response.readEntity(Attachment.class);
			Logging.log("Attachment Response----->"+gson.toJson(attachment));
			return attachment;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * listAttachments
	 * 
	 * @param candidateId
	 * @return response
	 */
	/*-------------------------Get OPERATION----------------------------*/
	public List<Attachment> listAttachments(String candidateId) {
		String serviceEndPoint = this.endPointURL + "/_list?candidateId="
				+ candidateId;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listAttachments endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			List<Attachment> attachments = response
					.readEntity(new GenericType<List<Attachment>>() {
					});
			return attachments;
		}
		return null;
	}

	/**
	 * updateAttachment
	 * 
	 * @param attachment
	 * @return response
	 */
	/*-------------------------PUT OPERATION----------------------------*/
	public Response  updateAttachment(Attachment attachment) {
		String serviceEndPoint = this.endPointURL;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("updateAttachment endPointURL  >>>" + serviceEndPoint);
		Logging.log("updateAttachment Request  >>>" + gson.toJson(attachment));
		Response response = executePUT(serviceEndPoint, true, Entity.entity(attachment, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			return response;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * deleteAttachment
	 * 
	 * @param attachmentId
	 * @return response
	 */
	/*-------------------------DELETE OPERATION----------------------------*/
	public Response deleteAttachment(String attachmentId) {
		String serviceEndPoint = this.endPointURL + "?attachmentId="+attachmentId;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("deleteAttachment endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			return response;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * getAttachmentById
	 * 
	 * @param attachmentId
	 * @return response
	 */
	/*-------------------------GET OPERATION----------------------------*/
	public  File getAttachmentById(String attachmentId) {
		String serviceEndPoint = this.endPointURL+"/"+attachmentId;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" getAttachmentById endPointURL  >>>" + serviceEndPoint);
		setPropertyValue("Accept", "application/octet-stream");
		Response response = executeGET(serviceEndPoint, true);
		setPropertyValue("Accept", "application/json");
		if (response.getStatus() == 200) {
			File attachments = response.readEntity(File.class);
			return attachments;
		}
		return null;
	}
	/**
	 * getAttachmentByLink
	 * 
	 * @param link
	 * @return response
	 */
	/*-------------------------GET OPERATION----------------------------*/
	public  File getAttachmentByLink(String link) {
		String serviceEndPoint = this.endPointURL+"?link="+link;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" getAttachmentByLink endPointURL  >>>" + serviceEndPoint);
		setPropertyValue("Accept", "application/octet-stream");
		Response response = executeGET(serviceEndPoint, true);
		setPropertyValue("Accept", "application/json");
		if (response.getStatus() == 200) {
			File attachments = response.readEntity(File.class);
			return attachments;
		}
		return null;
	}

}



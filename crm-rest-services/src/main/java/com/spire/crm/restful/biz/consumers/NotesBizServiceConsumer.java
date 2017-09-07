package com.spire.crm.restful.biz.consumers;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.crm.notes.beans.NoteBean;
import spire.crm.notes.beans.NoteVO;
import spire.crm.notes.beans.Notes;
import spire.crm.notes.entity.CollectionEntity;

/**
 * 
 * @author manaswini
 *
 */
public class NotesBizServiceConsumer extends BaseServiceConsumerNew {

	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("NOTES_SERVICE_BIZ");
	Gson gson = new Gson();

	public NotesBizServiceConsumer() {

	}

	/** ------------------------POST Operation ---------------------- **/

	public NoteBean createNoteForTheCandidate(NoteBean noteObj) {
		Logging.log("start::createNoteForTheCandidate");
		String serviceEndPoint = this.endPointURL+"notes";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("Request:"+gson.toJson(noteObj));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(noteObj, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 201) {
			NoteBean note = response.readEntity(NoteBean.class);
			Assert.assertNotNull(note.getId(),"note is not created");
			return note;
		} else {
			return null;
		}

	}

	/** ------------------------POST Operation ---------------------- **/
	public boolean updateNoteForTheCandidate(NoteVO notes) {
		Logging.log("start::updateNoteForTheCandidate");
		String serviceEndPoint = this.endPointURL + "notes/update";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(notes, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}

	}

	/** ------------------------POST Operation ---------------------- **/
	public boolean updateBulkNotesForTheCandidate(List<NoteVO> notes) {
		Logging.log("start::updateBulkNotesForTheCandidate");
		String serviceEndPoint = this.endPointURL + "notes/updatebulk";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(notes, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}

	}

	/** ------------------------POST Operation ---------------------- **/
	public CollectionEntity<NoteBean> getNotesForTheCandidateByNoteId(Notes notes) {
		Logging.log("start::getNotesForTheCandidateByNoteId");
		String serviceEndPoint = this.endPointURL + "notes/bynoteids";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(notes, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<NoteBean> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<NoteBean>>() {
					});
			Assert.assertNotNull(collectionEntity.getItems(),"Not showing the details of NoteIds");
			return collectionEntity;
		} else {
			return null;
		}

	}

	/** ------------------------GET Operation ---------------------- **/
	public CollectionEntity<NoteBean> getNotesForTheCandidateByEntityId(String entityId) {
		Logging.log("start::getNotesForTheCandidateByEntityId");
		String serviceEndPoint = this.endPointURL + "notes/byentityid?entityId=" + entityId;
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<NoteBean> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<NoteBean>>() {
					});
			Assert.assertNotNull(collectionEntity.getItems(),"Not showing the details of EntityId");
			return collectionEntity;
		} else {
			return null;
		}

	}

	/** ------------------------GET Operation ---------------------- **/
	public CollectionEntity<NoteBean> getNotesForTheCandidateByUserId(String userId) {
		Logging.log("start::getNotesForTheCandidateByUserId");
		String serviceEndPoint = this.endPointURL + "users/me/notes/" + userId;
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<NoteBean> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<NoteBean>>() {
					});
			Assert.assertNotNull(collectionEntity.getItems(),"Not showing the details of UserId");
			return collectionEntity;
		} else {
			return null;
		}

	}

	/** ------------------------POST Operation ---------------------- **/

	public CollectionEntity<NoteBean> getNotesForTheCandidateByMultipleNoteIds(Notes notes) {
		Logging.log("start::getNotesForTheCandidateByMultipleNoteIds");
		String serviceEndPoint = this.endPointURL + "notes/bynoteids";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(notes, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<NoteBean> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<NoteBean>>() {
					});
			return collectionEntity;
		} else {
			return null;
		}

	}

	/** ------------------------POST Operation ---------------------- **/
	public boolean deleteNoteForTheCandidate(String noteId) {
		Logging.log("start::deleteNoteForTheCandidate");
		String serviceEndPoint = this.endPointURL + "notes/delete?noteId=" + noteId;
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(noteId, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}

	}

	/** ------------------------POST Operation ---------------------- **/
	public boolean deleteBulkNotesForTheCandidate(Notes notes) {
		Logging.log("start::deleteBulkNotesForTheCandidate");
		String serviceEndPoint = this.endPointURL + "notes/deletebulk";
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(notes, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}

	}

}

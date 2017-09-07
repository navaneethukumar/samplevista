package com.spire.crm.biz.notes.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.Assert;

import com.spire.crm.restful.biz.consumers.NotesBizServiceConsumer;

import spire.crm.notes.beans.NoteBean;
import spire.crm.notes.beans.NoteVO;
import spire.crm.notes.entity.CollectionEntity;

public class NotesServiceValidation {

	NotesBizServiceConsumer notesBizServiceConsumer = null;

	/**
	 * validateCreateNotes
	 * 
	 * @param noteBean
	 * @param responseNote
	 */
	public void validateCreateNotes(NoteBean noteBean, NoteBean responseNote) {
		Assert.assertNotNull(responseNote.getId(), "Note is not created");
		Assert.assertTrue(noteBean.getNotesMessage().equals(responseNote.getNotesMessage()), "Invalid NoteMessage");
		Assert.assertTrue(noteBean.getNotesTitle().equals(responseNote.getNotesTitle()), "Invalid NoteTitle");
		Assert.assertTrue(noteBean.getEntityId().equals(responseNote.getEntityId()), "Invalid NoteMessage");
		Assert.assertTrue(noteBean.getUserId().equals(responseNote.getUserId()), "Invalid NoteTitle");
		Assert.assertTrue(true, "TestCase verifyCreateNote execution success !!!");
	}

	/**
	 * validateUpdateNotes
	 * 
	 * @param responseNote
	 * @param status
	 */
	public void validateUpdateNotes(NoteBean responseNote, Boolean status,NoteVO notevo,CollectionEntity<NoteBean> noteDetails) {
		Assert.assertNotNull(responseNote.getId(), "Note is not created");
		Assert.assertTrue(status.equals(true), "BulkNotes are not updated");
		Assert.assertTrue(true, "TestCase verifyUpdateBulkNotes execution success !!!");
		Collection<NoteBean> collection=noteDetails.getItems();
		NoteBean noteBean=collection.iterator().next();
		Assert.assertEquals(notevo.getNotesMessage(), noteBean.getNotesMessage());
		Assert.assertEquals(notevo.getNotesTitle(), noteBean.getNotesTitle());
	}

	/**
	 * validateGetNotesByNoteId
	 * 
	 * @param noteIds
	 * @param noteDetails
	 */
	public void validateGetNotesByNoteId(List<String> noteIds, CollectionEntity<NoteBean> noteDetails) {
		Collection<NoteBean> noteId = noteDetails.getItems();
		List<String> ids = new ArrayList<String>();
		for (NoteBean noteBean : noteId) {
			ids.add(noteBean.getId());
		}
		Assert.assertTrue(ids.equals(noteIds));
		Assert.assertTrue(true, "TestCase verifyGetNotesByNoteId execution success !!!");
	}

	/**
	 * validateGetNotesByEntityId
	 * 
	 * @param ENTITY_ID
	 * @param collectionEntity
	 */
	public void validateGetNotesByEntityId(String ENTITY_ID, CollectionEntity<NoteBean> collectionEntity) {
		Collection<NoteBean> entityId = collectionEntity.getItems();
		List<String> ids = new ArrayList<String>();
		for (NoteBean noteBean : entityId) {
			ids.add(noteBean.getEntityId());
		}
		Assert.assertTrue(ids.contains(ENTITY_ID), "showing wrong note details");
		Assert.assertTrue(true, "TestCase verifyGetNotesByEntityId execution success !!!");
	}

	/**
	 * validateGetNotesByEntityId
	 * 
	 * @param ENTITY_ID
	 * @param collectionEntity
	 */
	public void validateGetNotesByUserId(String userId, CollectionEntity<NoteBean> collectionEntity) {
		Collection<NoteBean> userID = collectionEntity.getItems();
		List<String> ids = new ArrayList<String>();
		for (NoteBean noteBean : userID) {
			ids.add(noteBean.getUserId());
		}
		Assert.assertTrue(ids.contains(userId), "showing wrong note details");
		Assert.assertTrue(true, "TestCase verifyGetNotesByEntityId execution success !!!");
	}
	/**
	 * validateDeleteNotes
	 * 
	 * @param status
	 */
	public void validateDeleteNotes(Boolean status,NoteBean responseNote,CollectionEntity<NoteBean> noteDetails) {
		
		Assert.assertTrue(status.equals(true));
		Assert.assertTrue(true, "TestCase verifyDeleteNotesByNoteIds execution success !!!");
		Assert.assertEquals(noteDetails, null,"showing wrong note details");
	}

}

package com.spire.crm.biz.notes.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.NotesBizServiceConsumer;

import spire.commons.config.entities.SpireConfiguration;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.notes.beans.NoteBean;
import spire.crm.notes.beans.NoteVO;
import spire.crm.notes.beans.Notes;
import spire.crm.notes.entity.CollectionEntity;


@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class NotesServiceSanityTestPlan extends TestPlan {
	NotesBizServiceConsumer notesBizServiceConsumer = new NotesBizServiceConsumer();
	NotesServiceValidation notesServiceValidation = new NotesServiceValidation();
	private static Logger logger = LoggerFactory.getLogger(NotesServiceSanityTestPlan.class);
	SpireConfiguration spireConfiguration = null;
	NoteBean responseNote = null;
	String SERVICE_NAME = null;
	final static String DATAPROVIDER_NAME = "NOTES_SERVICE";
	public static String userId ="6000:6003:9672a6d2edb4430ca9857f440536de6b";
	String entityId = null;
	String noteId = null;

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/notes/test/NotesService_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(NotesServiceSanityTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}


	/**
	 * Creating service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreateNote", "Sanity" }, priority = 0, dataProvider = DATAPROVIDER_NAME)
	public void verifyCreateNote(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyCreateNote !!!");
		try {
			entityId = ProfileHelper.createProfile();
			NoteBean noteBean = new NoteBean();
			noteBean.setUserId(userId);
			noteBean.setEntityId(entityId);
			noteBean.setNotesMessage("Intrested in Java");
			noteBean.setNotesTitle("java");
			noteBean.setCreatedBy(null);
			noteBean.setModifiedBy(null);
			noteBean.setCreatedOn(null);
			noteBean.setModifiedOn(null);
			responseNote = notesBizServiceConsumer.createNoteForTheCandidate(noteBean);
			notesServiceValidation.validateCreateNotes(noteBean, responseNote);

		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyCreateNote -> failed", e);
		}
	}

	/**
	 * Updating service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyUpdateNote", "Sanity" }, priority = 1, dataProvider = DATAPROVIDER_NAME)
	public void verifyUpdateNote(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyUpdateNote !!!");
		try {
			NoteVO notevo = new NoteVO();
			noteId = responseNote.getId();
			notevo.setId(noteId);
			notevo.setNotesMessage("intrested in JavaScript");
			notevo.setNotesTitle("JavaScript");
			Boolean status = notesBizServiceConsumer.updateNoteForTheCandidate(notevo);
			Notes note = new Notes();
			List<String> noteIds = new ArrayList<>();
			noteIds.add(responseNote.getId());
			note.setIds(noteIds);
			CollectionEntity<NoteBean> noteDetails = notesBizServiceConsumer.getNotesForTheCandidateByNoteId(note);
			notesServiceValidation.validateUpdateNotes(responseNote, status, notevo, noteDetails);
			notesServiceValidation.validateGetNotesByNoteId(noteIds, noteDetails);

		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyUpdateNote -> failed", e);
		}
	}

	/**
	 * UpdatingBulk service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyUpdateBulkNotes", "Sanity" }, priority = 2, dataProvider = DATAPROVIDER_NAME)
	public void verifyUpdateBulkNotes(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyUpdateBulkNotes !!!");
		try {
			List<NoteVO> listnotes = new ArrayList<NoteVO>();
			NoteVO notevo = new NoteVO();
			noteId = responseNote.getId();
			notevo.setId(noteId);
			notevo.setNotesMessage("intrested in oracle");
			notevo.setNotesTitle("oracle");
			listnotes.add(notevo);
			Boolean status = notesBizServiceConsumer.updateBulkNotesForTheCandidate(listnotes);
			Notes note = new Notes();
			List<String> noteIds = new ArrayList<>();
			noteIds.add(responseNote.getId());
			note.setIds(noteIds);
			CollectionEntity<NoteBean> noteDetails = notesBizServiceConsumer.getNotesForTheCandidateByNoteId(note);
			notesServiceValidation.validateUpdateNotes(responseNote, status, notevo, noteDetails);
			notesServiceValidation.validateGetNotesByNoteId(noteIds, noteDetails);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyUpdateBulkNotes -> failed", e);
		}
	}

	/**
	 * GetttingNotes By NoteId service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetNotesByNoteId", "Sanity" }, priority = 3, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetNotesByNoteId(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyGetNotesByNoteID !!!");
		try {
			Notes notes = new Notes();
			List<String> noteIds = new ArrayList<>();
			noteId = responseNote.getId();
			noteIds.add(noteId);
			notes.setIds(noteIds);
			CollectionEntity<NoteBean> noteDetails = notesBizServiceConsumer.getNotesForTheCandidateByNoteId(notes);
			notesServiceValidation.validateGetNotesByNoteId(noteIds, noteDetails);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetNotesByNoteId -> failed", e);
		}
	}

	/**
	 * GetttingNotes By EntityId service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetNotesByEntityId", "Sanity" }, priority = 4, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetNotesByEntityId(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyGetNotesByEntityId !!!");
		try {
			CollectionEntity<NoteBean> collectionEntity = notesBizServiceConsumer
					.getNotesForTheCandidateByEntityId(entityId);
			notesServiceValidation.validateGetNotesByEntityId(entityId, collectionEntity);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetNotesByEntityId -> failed", e);
		}
	}

	/**
	 * GetttingNotes By Multiple NoteIds service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetNotesByMultipleNoteIds", "Sanity" }, priority = 5, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetNotesByMultipleNoteIds(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyGetNotesByMultipleNoteIds !!!");
		try {
			Notes notes = new Notes();
			List<String> noteIds = new ArrayList<>();
			noteId = responseNote.getId();
			noteIds.add(noteId);
			notes.setIds(noteIds);
			CollectionEntity<NoteBean> noteDetails = notesBizServiceConsumer.getNotesForTheCandidateByNoteId(notes);
			notesServiceValidation.validateGetNotesByNoteId(noteIds, noteDetails);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetNotesByMultipleNoteIds -> failed", e);
		}
	}

	/**
	 * Delete Notes By NoteIds service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteNotesByNoteIds", "Sanity" }, priority = 6, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteNotesByNoteIds(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyGetNotesByMultipleNoteIds !!!");
		try {
			noteId = responseNote.getId();
			Boolean status = notesBizServiceConsumer.deleteNoteForTheCandidate(noteId);
			Notes notes = new Notes();
			List<String> noteIds = new ArrayList<>();
			noteIds.add(responseNote.getId());
			notes.setIds(noteIds);
			CollectionEntity<NoteBean> noteDetails = notesBizServiceConsumer.getNotesForTheCandidateByNoteId(notes);
			notesServiceValidation.validateDeleteNotes(status, responseNote, noteDetails);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyDeleteNotesByNoteIds -> failed", e);
		}
	}

	/**
	 * DeleteBulk Notes By NoteIds service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteBulkNotesByNoteIds", "Sanity" }, priority = 7, dataProvider = DATAPROVIDER_NAME)
	public void verifyDeleteBulkNotesByNoteIds(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyDeleteBulkNotesByNoteIds !!!");
		try {
			Notes notes = new Notes();
			List<String> noteIds = new ArrayList<>();
			noteId = responseNote.getId();
			noteIds.add(noteId);
			notes.setIds(noteIds);
			Boolean status = notesBizServiceConsumer.deleteBulkNotesForTheCandidate(notes);
			CollectionEntity<NoteBean> noteDetails = notesBizServiceConsumer.getNotesForTheCandidateByNoteId(notes);
			notesServiceValidation.validateDeleteNotes(status, responseNote, noteDetails);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyDeleteBulkNotesByNoteIds -> failed", e);
		}
	}

	/**
	 * GetttingNotes By EntityId service
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetNotesByUserId", "Sanity" }, priority = 4, dataProvider = DATAPROVIDER_NAME)
	public void verifyGetNotesByUserId(SpireTestObject testObject, TestData data) {
		logger.info("Test Case Execution started >>> verifyGetNotesByEntityId !!!");
		try {
			CollectionEntity<NoteBean> collectionEntity = notesBizServiceConsumer
					.getNotesForTheCandidateByUserId(userId);
			notesServiceValidation.validateGetNotesByUserId(userId, collectionEntity);
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetNotesByUserId -> failed", e);
		}
	}

}
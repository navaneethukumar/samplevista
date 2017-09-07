package com.spire.crm.biz.newLabels.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.commons.labels.beans.DateRange;
import spire.commons.labels.beans.LabelUsers;
import spire.commons.labels.beans.LabelVO;
import spire.commons.labels.beans.LabelsFilter;
import spire.commons.labels.beans.Scope;
import spire.commons.labels.beans.User;
import spire.commons.labels.common.CollectionEntity;
import spire.crm.labels.biz.entities.LabelFilterPageInfo;
import spire.crm.labels.biz.entities.LabelNameEntity;
import spire.crm.labels.biz.entities.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.activity.biz.pojos.LabelBean;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.NewLabelsBizServiceConsumer;

public class LabelSRGvalidation {
	static DataFactory factory = new DataFactory();
	// static String labelName = "Java";
	public static List<String> candidateIDs = new ArrayList<String>();
	public static List<String> labelIDs = new ArrayList<String>();
	static Gson gson = new Gson();
	final static NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();

	static NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();

	public static String CreateDuplicateLabel(TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		String labelname = null;
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		ResponseEntity createResponse = client.createLabel(labelName);

		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);
		if (createResponse.getCode() == 200) {

			labelname = validateCreatSuccessResponse(createResponse, labelNames);
		} else {
			Assert.fail("share label is failed");

		}
		ResponseEntity response = client.createLabel(labelname);

		if (response.getCode() != 400) {
			Logging.log("Duplicate label didnot created");
			Assert.fail("share label is failed");
		}
		return labelname;
	}

	private static String validateCreatSuccessResponse(ResponseEntity createResponse, List<String> labelNames)
			throws JsonParseException, JsonMappingException, IOException {
		// Assert.assertEquals(actual, expected);
		String labelname = null;
		Assert.assertEquals(createResponse.getCode(), 200, "Found discrepancy in Response code");
		Assert.assertEquals(createResponse.getMessage(), "all labels successfully created",
				"Found discrepancy in Response Message");

		Map<String, String> successMap = createResponse.getSuccess();

		for (Entry<String, String> entry : successMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			Assert.assertEquals(value, "success", "found discrepancy in success message");

			for (String labelName : labelNames) {

				labelname = newLabelSanityValidation.getValue(key, 3);

			}

		}
		return labelname;
	}

	public static void Attach_OldLabel_To_ProfileID(TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = CreateDuplicateLabel(data);
		LabelNameEntity labelNameEntities = new LabelNameEntity();
		LabelVO labelVO = new LabelVO();
		labelVO.setLabelName(labelName);
		labelVO.setScope(Scope.PRIVATE);
		List<String> entityIds = new ArrayList<String>();
		List<LabelVO> labelVOs = new ArrayList<LabelVO>();
		labelVOs.add(labelVO);

		String candidateId = ProfileHelper.createProfile();
		candidateIDs.add(candidateId);
		entityIds.add(candidateId);

		labelNameEntities.setEntityIds(entityIds);
		labelNameEntities.setLabelVOs(labelVOs);
		ResponseEntity response = client.attachLabelsToEntities(data.getData(), labelNameEntities);
		validateattachLabelToEntityResponse(response, labelNameEntities);

	}

	private static void validateattachLabelToEntityResponse(ResponseEntity createResponse,
			LabelNameEntity labelNameEntities) throws JsonParseException, JsonMappingException, IOException {

		Assert.assertEquals(createResponse.getCode(), 200, "Found discrepancy in Response code");
		Assert.assertEquals(createResponse.getMessage(), "all labels successfully attached",
				"Found discrepancy in Response Message of attached entity");
	}

	public static void Attach_SingleLabel_To_MultipleProfileIDs(TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = "spireautomationlabe" + System.currentTimeMillis();
		LabelNameEntity labelNameEntities = new LabelNameEntity();
		LabelVO labelVO = new LabelVO();
		labelVO.setLabelName(labelName);
		labelVO.setScope(Scope.PRIVATE);
		List<String> entityIds = new ArrayList<String>();
		List<LabelVO> labelVOs = new ArrayList<LabelVO>();
		labelVOs.add(labelVO);

		String candidateId1 = ProfileHelper.createProfile();
		String candidateId2 = ProfileHelper.createProfile();
		String candidateId3 = ProfileHelper.createProfile();
		candidateIDs.add(candidateId1);
		entityIds.add(candidateId1);
		candidateIDs.add(candidateId2);
		entityIds.add(candidateId2);
		candidateIDs.add(candidateId3);
		entityIds.add(candidateId3);
		labelNameEntities.setEntityIds(entityIds);
		labelNameEntities.setLabelVOs(labelVOs);
		ResponseEntity response = client.attachLabelsToEntities(data.getData(), labelNameEntities);

		labelIDs.add(newLabelSanityValidation.getLabelID(labelName));
		validateattachLabelToEntityResponse(response, labelNameEntities);

	}

	public static String Attach_DuplicateLabel(TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		LabelNameEntity labelNameEntities = new LabelNameEntity();
		LabelVO labelVO = new LabelVO();
		labelVO.setLabelName(labelName);
		labelVO.setScope(Scope.PRIVATE);
		List<String> entityIds = new ArrayList<String>();
		List<LabelVO> labelVOs = new ArrayList<LabelVO>();
		labelVOs.add(labelVO);

		String candidateId = ProfileHelper.createProfile();
		candidateIDs.add(candidateId);
		entityIds.add(candidateId);

		labelNameEntities.setEntityIds(entityIds);

		labelNameEntities.setLabelVOs(labelVOs);

		ResponseEntity createResponse = client.attachLabelsToEntity(candidateId, data.getData(), labelVOs);
		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);

		/*
		 * if (createResponse.getCode() == 200) {
		 * 
		 * NewLabelSanityValidation.validateattachLabelToEntityResponse(
		 * createResponse, labelNameEntities);
		 * 
		 * }
		 */
		ResponseEntity createResponse1 = client.attachLabelsToEntity(candidateId, data.getData(), labelVOs);

		return labelName;
	}

	public static void Detach_DuplicateLabel(TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		LabelNameEntity labelNameEntities = new LabelNameEntity();
		LabelVO labelVO = new LabelVO();
		labelVO.setLabelName(labelName);
		labelVO.setScope(Scope.PRIVATE);
		List<String> entityIds = new ArrayList<String>();
		List<LabelVO> labelVOs = new ArrayList<LabelVO>();
		labelVOs.add(labelVO);

		String candidateId = ProfileHelper.createProfile();
		entityIds.add(candidateId);

		labelNameEntities.setEntityIds(entityIds);

		labelNameEntities.setLabelVOs(labelVOs);

		client.attachLabelsToEntity(candidateId, data.getData(), labelVOs);
		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);

		ResponseEntity detachResponse = client.detachLabelsToEntities("CANDIDATE", labelNameEntities);

		if (detachResponse.getCode() == 200) {
			Logging.log("Label detachLabelsToEntity_Successfully !!!");

		}
		ResponseEntity detachResponse1 = client.detachLabelsToEntities("CANDIDATE", labelNameEntities);
		if (detachResponse1.getCode() == 400) {
			Logging.log("none of the labels detached!!!");

		}
	}

	public static void Detach_DuplicateLabel_Shared(TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		LabelNameEntity labelNameEntities = new LabelNameEntity();
		LabelVO labelVO = new LabelVO();
		labelVO.setLabelName(labelName);
		labelVO.setScope(Scope.SHARED);
		List<String> entityIds = new ArrayList<String>();
		List<LabelVO> labelVOs = new ArrayList<LabelVO>();
		labelVOs.add(labelVO);

		String candidateId = ProfileHelper.createProfile();
		entityIds.add(candidateId);

		labelNameEntities.setEntityIds(entityIds);

		labelNameEntities.setLabelVOs(labelVOs);

		client.attachLabelsToEntity(candidateId, data.getData(), labelVOs);
		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);

		ResponseEntity detachResponse = client.detachLabelsToEntities("CANDIDATE", labelNameEntities);

		if (detachResponse.getCode() == 200) {
			Logging.log("Label detachLabelsToEntity_Successfully !!!");

		}
		ResponseEntity detachResponse1 = client.detachLabelsToEntities("CANDIDATE", labelNameEntities);
		if (detachResponse1.getCode() == 400) {
			Logging.log("none of the labels detached!!!");

		}
	}

	public static void RemoveLabel_LRG(TestData data) {

	}

	public static void ListLabels_SRG(TestData data) throws JsonParseException, JsonMappingException, IOException {
		DateRange createdDateRange = null;
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();

		CollectionEntity<LabelBean> createResponse = client.listLabelswithParms(2, 0, "PRIVATE", createdDateRange);
		Collection<LabelBean> collectionOfLabels = createResponse.getItems();
		Assert.assertEquals(collectionOfLabels.size(), 2, "more number of label details than limit given");
	}

	public static void ListProfiles_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		ResponseEntity responseEntity = newLabelSanityValidation.attachLabelToEntity(testObject, data);
		String labelId = responseEntity.getSuccess().keySet().iterator().next();
		String label[] = labelId.split(" ");
		String createdLabelId = label[6];

		newLabelsBizServiceConsumer.listProfiles(createdLabelId);
	}

	public static void ListProfiles_By_InvalidID_SRG() {
		String labelId = "9e7e5cb";
		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();
		newLabelsBizServiceConsumer.listProfilesForInvalidID(labelId);
	}

	public static void filter_Label_By_LabelID_SRG(TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		List<String> entityIds = new ArrayList<String>();

		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		client.createLabel(labelName);
		String returnedLabelID = newLabelSanityValidation.getLabelID(labelName);

		labelIDs.add(returnedLabelID);
		entityIds.add(returnedLabelID);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelIds(entityIds);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabel = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabel.getItems();
		String newLabelName = null;
		for (LabelBean labelBean : labelBeans) {
			newLabelName = labelBean.getLabelName();
		}

		Assert.assertTrue(newLabelName.equals(labelName),
				"Newly careted label is displayed in the filted list_of_labels");

	}

	public static void filter_Label_By_Labelname_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		List<String> labelNames = new ArrayList<String>();

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelName = newLabelSanityValidation.createSingleLabelReturnLabelName(testObject, data);

		labelNames.add(labelName);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelNames(labelNames);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabel = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabel.getItems();
		String newLabelName = null;
		for (LabelBean labelBean : labelBeans) {
			newLabelName = labelBean.getLabelName();
		}

		Assert.assertTrue(newLabelName.equals(labelName),
				"Newly careted label is displayed in the filted list_of_labels");

	}

	public static void filter_Labels_By_LabelIds_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		List<String> listOfNewlyCreatedLabels = newLabelSanityValidation.createBulkLabel(testObject, data);

		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelIds(listOfNewlyCreatedLabels);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabelList = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabelList.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(listOfNewlyCreatedLabels.contains(labelBean.getLabelId()),
					"All the newly labels are present in list");
		}
	}

	public static void filter_Labels_By_LabelIds_with_one_invalidID_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		List<String> listOfNewlyCreatedLabels = newLabelSanityValidation.createBulkLabel(testObject, data);
		listOfNewlyCreatedLabels.add("6000:6060:97976b7623cc4668a21d0caf");
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelIds(listOfNewlyCreatedLabels);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabelList = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabelList.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(listOfNewlyCreatedLabels.contains(labelBean.getLabelId()),
					"All the newly labels are present in list");
		}
	}

	public static void filter_Labels_By_Labelnames_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		List<String> listOfNewlyCreatedLabels = newLabelSanityValidation.createBulkLabelReturnLabelName(testObject,
				data);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelNames(listOfNewlyCreatedLabels);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabelList = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabelList.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(listOfNewlyCreatedLabels.contains(labelBean.getLabelName()),
					"All the newly labels are present in list");
		}

	}

	public static void filter_Labels_By_Labelnames_with_one_invalidName_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		List<String> listOfNewlyCreatedLabels = newLabelSanityValidation.createBulkLabel(testObject, data);
		listOfNewlyCreatedLabels.add("invalidName");
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelNames(listOfNewlyCreatedLabels);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabelList = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabelList.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(listOfNewlyCreatedLabels.contains(labelBean.getLabelName()),
					"All the newly labels are present in list");
		}

	}

	public static void remove_Label_By_InvalidId_SRG(TestData data) {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelId = "6000:6060:1b1b4c4181cf4ac8b320596729c6";
		client.deleteInvalidLabel(labelId);
	}

	public static void bulk_remove_with_oneInvalidId_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		List<String> labelIds = newLabelSanityValidation.createBulkLabel(testObject, data);
		String invalidId = "6000:6060:28d1641d320643e2b7ef5f5d0f9415";
		labelIds.add(invalidId);
		ResponseEntity response = client.deleteLabels(labelIds);
		Map<String, String> successMap = response.getSuccess();

		/*
		 * for (Entry<String, String> entry : successMap.entrySet()) {
		 * 
		 * String key = entry.getKey(); String value = entry.getValue();
		 * Assert.assertEquals(value, "success",
		 * "found discrepancy in failure message"); String labelID =
		 * newLabelSanityValidation.getValue(key, 6);
		 * Assert.assertTrue(labelIds.contains(labelID),"Deleted valid labels");
		 * }
		 */

		/*
		 * Map<String, String> failureMap = response.getFailure();
		 * 
		 * for (Entry<String, String> entry : failureMap.entrySet()) {
		 * 
		 * String key = entry.getKey(); String value = entry.getValue();
		 * Assert.assertEquals(value, "failure",
		 * "found discrepancy in failure message"); String labelID =
		 * newLabelSanityValidation.getValue(key, 6);
		 * Assert.assertEquals(invalidId.equals(labelID),
		 * "invalidId is not able to delete"); }
		 */
	}

	public static void filterSharedLabelByLabelId_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		List<String> labelIds = new ArrayList<String>();
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelId = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);

		labelIds.add(labelId);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelIds(labelIds);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabel = client.filterLabels(labelFilterPageInfo);

		Collection<LabelBean> labelBeans = newLabel.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(labelId.equals(labelBean.getLabelId()), "All the newly labels are present in list");
		}
	}

	public static void filterSharedLabelsByLabelIds_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		List<String> entityIds = new ArrayList<String>();
		String labelId1 = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);
		String labelId2 = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);
		String labelId3 = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);
		entityIds.add(labelId1);
		entityIds.add(labelId2);
		entityIds.add(labelId3);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelIds(entityIds);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabel = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabel.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(entityIds.contains(labelBean.getLabelId()), "All the newly labels are present in list");
		}

	}

	public static void deleteSharedLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelId = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);
		client.deleteLabelSharedLabel(labelId);
		

	}

	public static void createSameLabelForTwoUsers(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		//NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
	//	String labelName = newLabelSanityValidation.createLabel(testObject, data);
		
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		String user1Label=returnLabelNameUser1(labelName);
		String user2Label=returnLabelNameUser2(labelName);
Assert.assertEquals(user1Label, user2Label, "Same label is created for two users");
	}

	public static void createNShareUser2(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelName1 = "spireautomationlabel" + System.currentTimeMillis();
		newLabelSanityValidation.shareLabel_ByLabelId(testObject,data);
		
		user2ListLabels(labelName1);
	}
	
	public static void user2ListLabels(String labelName) throws JsonParseException, JsonMappingException, IOException
	{
		
		String password = ReadingServiceEndPointsProperties.getServiceEndPoint("password");
		String username = ReadingServiceEndPointsProperties.getServiceEndPoint("loginId2");
		// String username="batchUser@dell.com"; String password="spire@123";
		 NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer(username,password);

			CollectionEntity<LabelBean> labelsList = client.listLabels();

			List<LabelBean> labels = (List<LabelBean>) labelsList.getItems();

			for (LabelBean label : labels) {

				if (labelName.equals(label.getLabelName())) {
					String name= label.getLabelName();
					
					Assert.assertEquals(name, labelName, "Shared label is displayedin user2 list labels");
				}

			}
	}
	
	public static String returnLabelNameUser1(String labelName)
	{
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		
		ResponseEntity createResponse = client.createLabel(labelName);

		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);
		if (createResponse.getCode() == 200) {

			Logging.log(" Label created succssflly");

		} else {

			Logging.log(" Failed to create label");
		}
		return labelName;
	}
	
	public static String returnLabelNameUser2(String labelName)
	{

		  String username="batchUser@dell.com"; String password="spire@123";
		 
		{
			NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer(username,password);
			ResponseEntity createResponse = client.createLabel(labelName);

			List<String> labelNames = new ArrayList<String>();
			labelNames.add(labelName);
			if (createResponse.getCode() == 200) {

				Logging.log(" Label created succssflly");

			} else {

				Logging.log(" Failed to create label");
			}
			return labelName;
		  
		}}
	
	public static void createMultipleLabelsNShareUser2(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelName1 = "spireautomationlabel" + System.currentTimeMillis();
		newLabelSanityValidation.shareLabel_ByLabelId(testObject,data);
		
		user2ListLabels(labelName1);
		
	}
	public static void filterSharedLabelByLabelName_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		List<String> labelIds = new ArrayList<String>();
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelId = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);

		labelIds.add(labelId);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelIds(labelIds);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabel = client.filterLabels(labelFilterPageInfo);

		Collection<LabelBean> labelBeans = newLabel.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(labelId.equals(labelBean.getLabelId()), "All the newly labels are present in list");
		}
	}

	public static void filterLabelsByLabelIdsNlabelNames_SRG(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		List<String> listOfNewlyCreatedLabels = newLabelSanityValidation.createBulkLabelReturnLabelName(testObject,
				data);
		List<String> listOfLabelids = newLabelSanityValidation.getLabelIDs(listOfNewlyCreatedLabels);
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setLabelNames(listOfNewlyCreatedLabels);
		labelFilter.setLabelIds(listOfLabelids);
		labelFilterPageInfo.setLabelFilter(labelFilter);
		CollectionEntity<LabelBean> newLabelList = client.filterLabels(labelFilterPageInfo);
		Collection<LabelBean> labelBeans = newLabelList.getItems();

		for (LabelBean labelBean : labelBeans) {
			Assert.assertTrue(listOfNewlyCreatedLabels.contains(labelBean.getLabelName()),
					"All the newly labels are present in list");
		}
	}

	public static void shareLabelTwoTimes(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		String labelId = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);

		newLabelSanityValidation.shareLabelTwice(testObject, data, labelId);

	}

	public static void renameSharedLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();

		String labelId = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);

		renameLabel(labelId);
	}

	public static void renameLabel(String labelId) {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String newlabelName = "spireautomationlabel" + System.currentTimeMillis();
		ResponseEntity responseEntity = client.renameSharedLabel(labelId, newlabelName);
		Logging.log("Response entity of Shared Label is " + gson.toJson(responseEntity));
		if (responseEntity.getCode() == 0) {

			Assert.assertNotNull(responseEntity.getFailure(), "Share label is failed,");

			Assert.assertEquals(responseEntity.getSuccess().toString(), "{}", "Share label is failed");

		} else {
			Assert.fail("share label is failed");
		}

	}

	public static void removeUsers(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		NewLabelSanityValidation newLabelSanityValidation = new NewLabelSanityValidation();
		LabelUsers labelUsers = new LabelUsers();
		List<User> users = new ArrayList<User>();
		User user = new User();
		String userId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("userId2");
		String loginId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("loginId2");
		user.setUserId(userId2);
		user.setUserName(loginId2);
		users.add(user);
		labelUsers.setUsers(users);
		String labelId = newLabelSanityValidation.shareLabel_ByLabelId(testObject, data);
		//labelUsers.setLabelId(labelId);
		client.removeUsers(labelId, users);
	}
}

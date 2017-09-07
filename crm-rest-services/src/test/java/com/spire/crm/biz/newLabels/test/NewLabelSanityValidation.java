package com.spire.crm.biz.newLabels.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.commons.labels.beans.LabelUsers;
import spire.commons.labels.beans.LabelVO;
import spire.commons.labels.beans.LabelsFilter;
import spire.commons.labels.beans.Namespace;
import spire.commons.labels.beans.Scope;
import spire.commons.labels.beans.User;
import spire.commons.labels.common.CollectionEntity;
import spire.crm.labels.biz.entities.LabelFilterPageInfo;
import spire.crm.labels.biz.entities.LabelNameEntity;
import spire.crm.labels.biz.entities.ResponseEntity;
import spire.crm.profiles.bean.Profile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.activity.biz.pojos.LabelBean;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.NewLabelsBizServiceConsumer;

/**
 * @author ManiKanta
 *
 */
public class NewLabelSanityValidation extends CRMTestPlan {

	Gson gson = new Gson();
	DataFactory factory = new DataFactory();
	static String labelTobeCreated = "Java";
	public static List<String> labelIDs = new ArrayList<String>();
	public static List<String> listOfLabelIds = new ArrayList<String>();

	public static List<String> candidateIDs = new ArrayList<String>();

	public static String ownerID = ReadingServiceEndPointsProperties.getServiceEndPoint("userId");

	/**
	 * createSingleLabel
	 * 
	 * @param data
	 * @return createdLabelId
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public String createSingleLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();

		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		ResponseEntity responseEntity = newLabelsBizServiceConsumer.createLabel(labelName);
		String labelId = responseEntity.getSuccess().keySet().iterator().next();
		String label[] = labelId.split(" ");
		String createdLabelId = label[6];
		String createdLabelName = label[3];
		Assert.assertEquals(labelName, createdLabelName, "created wrong label");
		Assert.assertNotNull(responseEntity.getSuccess(), "label is not created");
		return createdLabelId;

	}

	public String createSingleLabelReturnLabelName(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();

		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		ResponseEntity responseEntity = newLabelsBizServiceConsumer.createLabel(labelName);
		String labelId = responseEntity.getSuccess().keySet().iterator().next();
		String label[] = labelId.split(" ");
		String createdLabelId = label[6];
		String createdLabelName = label[3];
		Assert.assertEquals(labelName, createdLabelName, "created wrong label");
		Assert.assertNotNull(responseEntity.getSuccess(), "label is not created");
		return createdLabelName;

	}

	public List<String> createBulkLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();
		Random ran = new Random();
		int bulkLabelCount = ran.nextInt(3) + 4;
		List<String> createdLabelIds = new ArrayList<String>();
		for (int i = 0; i < bulkLabelCount; i++) {

			String labelName = "spireautomationlabel" + System.currentTimeMillis();
			ResponseEntity responseEntity = newLabelsBizServiceConsumer.createLabel(labelName);
			String labelId = responseEntity.getSuccess().keySet().iterator().next();
			String label[] = labelId.split(" ");
			String createdLabelId = label[6];
			String createdLabelName = label[3];
			Assert.assertEquals(labelName, createdLabelName, "created wrong label");
			Assert.assertNotNull(responseEntity.getSuccess(), "label is not created");
			createdLabelIds.add(createdLabelId);

		}

		return createdLabelIds;

	}

	public List<String> createBulkLabelReturnLabelName(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();
		Random ran = new Random();
		int bulkLabelCount = ran.nextInt(3) + 4;
		List<String> createdLabelNames = new ArrayList<String>();
		for (int i = 0; i < bulkLabelCount; i++) {

			String labelName = "spireautomationlabel" + System.currentTimeMillis();
			ResponseEntity responseEntity = newLabelsBizServiceConsumer.createLabel(labelName);
			String labelId = responseEntity.getSuccess().keySet().iterator().next();
			String label[] = labelId.split(" ");
			String createdLabelId = label[6];
			String createdLabelName = label[3];
			Assert.assertEquals(labelName, createdLabelName, "created wrong label");
			Assert.assertNotNull(responseEntity.getSuccess(), "label is not created");
			createdLabelNames.add(createdLabelName);

		}

		return createdLabelNames;

	}

	/**
	 * createMultipleLabels
	 * 
	 * @param data
	 * @return responseEntity
	 */
	public static ResponseEntity createMultipleLabels(TestData data) {
		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();
		String labelName1 = "java" + System.currentTimeMillis();
		String labelName2 = "jdbc" + System.currentTimeMillis();
		String labelName3 = "javascript" + System.currentTimeMillis();
		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName1);
		labelNames.add(labelName2);
		labelNames.add(labelName3);
		ResponseEntity responseEntity = newLabelsBizServiceConsumer.createBulkLabels(labelNames);
		return responseEntity;

	}

	/**
	 * create Multiple Labels
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public void createLabels(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		Random ran = new Random();
		int bulkLabelCount = ran.nextInt(3) + 10;

		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();

		List<String> labelNames = new ArrayList<String>();

		for (int i = 0; i < bulkLabelCount; i++) {
			labelNames.add("spiresutomationlabel" + System.currentTimeMillis());

		}

		ResponseEntity createResponse = newLabelsBizServiceConsumer.createBulkLabels(labelNames);

		if (createResponse.getCode() == 200) {

			validateCreatSuccessResponse(createResponse, labelNames);

		} else {

			validateCreateFailureResponse(createResponse, labelNames);
		}

	}

	/**
	 * CreateLabel
	 * 
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public String createLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		ResponseEntity createResponse = client.createLabel(labelName);

		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);
		if (createResponse.getCode() == 200) {

			validateCreatSuccessResponse(createResponse, labelNames);

		} else {

			validateCreateFailureResponse(createResponse, labelNames);
		}
		return labelName;
	}

	private void validateCreateFailureResponse(ResponseEntity createResponse, List<String> labelNames) {

		Assert.fail("Creation of label is failed");

	}

	private void validateCreatSuccessResponse(ResponseEntity createResponse, List<String> labelNames)
			throws JsonParseException, JsonMappingException, IOException {
		// Assert.assertEquals(actual, expected);

		Assert.assertEquals(createResponse.getCode(), 200, "Found discrepancy in Response code");
		Assert.assertEquals(createResponse.getMessage(), "all labels successfully created",
				"Found discrepancy in Response Message");

		Map<String, String> successMap = createResponse.getSuccess();

		for (Entry<String, String> entry : successMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			Assert.assertEquals(value, "success", "found discrepancy in success message");

			for (String labelName : labelNames) {

				LabelBean input = new LabelBean();

				String labelID = getValue(key, 6);
				setUpLabel(input);
				input.setLabelId(labelID);
				input.setLabelName(labelName);
				verifyCreatedLabelByID(input);

			}

		}

	}

	public String getLabelID(String labelName) throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();

		CollectionEntity<LabelBean> labelsList = client.listLabels();

		List<LabelBean> labels = (List<LabelBean>) labelsList.getItems();

		for (LabelBean label : labels) {

			if (labelName.equals(label.getLabelName())) {
				return label.getLabelId();
			}

		}
		return null;

	}

	public List<String> getLabelIDs(List<String> labelName)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();

		CollectionEntity<LabelBean> labelsList = client.listLabels();

		List<LabelBean> labels = (List<LabelBean>) labelsList.getItems();

		for (LabelBean label : labels) {

			if (labelName.equals(label.getLabelName())) {
				listOfLabelIds.add(label.getLabelId());

				return listOfLabelIds;
			}

		}
		return null;

	}

	private void setUpLabel(LabelBean input) {
		input.setNamespace(Namespace.VISTA);
		input.setScope(Scope.PRIVATE);
		input.setOwnerId(ownerID);

		/*
		 * input.setOwnerId("ownerId"); input.setOwnerName(ownerName);
		 * input.setScope("");
		 */
	}

	private void verifyCreatedLabelByID(LabelBean input) throws JsonParseException, JsonMappingException, IOException {

		Boolean flag = false;

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();

		CollectionEntity<LabelBean> labelsList = client.listLabels();

		List<LabelBean> labels = (List<LabelBean>) labelsList.getItems();

		String labelID = null;
		for (LabelBean createdlabeln : labels) {

			labelID = createdlabeln.getLabelId();
			if (labelID.equals(input.getLabelId())) {

				flag = true;

				Assert.assertEquals(createdlabeln.getLabelName(), input.getLabelName(),
						"Label is not created with give Name");

				Assert.assertEquals(createdlabeln.getScope().toString(), input.getScope().toString(),
						"Label is not created with give Scope");

				Assert.assertEquals(createdlabeln.getOwnerId(), input.getOwnerId(),
						"Label is not created for the owner");

			}

		}

		Assert.assertTrue(flag, "Expected Lable ID is not found in the list");

	}

	/*
	 * It will restun label id for 6 and 3 for label name ...
	 */

	public String getValue(String fullString, int nth) {

		String result[] = fullString.split(" ");

		return result[nth];

	}

	public ResponseEntity attachLabelToEntity(SpireTestObject testObject, TestData data)
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

		String candidateId = ProfileHelper.createProfile();
		candidateIDs.add(candidateId);
		entityIds.add(candidateId);

		labelNameEntities.setEntityIds(entityIds);
		labelNameEntities.setLabelVOs(labelVOs);
		ResponseEntity responseEntity = client.attachLabelsToEntities(data.getData(), labelNameEntities);

		labelIDs.add(getLabelID(labelName));
		return responseEntity;

	}

	private void validateattachLabelToEntityResponse(ResponseEntity createResponse, LabelNameEntity labelNameEntities)
			throws JsonParseException, JsonMappingException, IOException {

		Assert.assertEquals(createResponse.getCode(), 200, "Found discrepancy in Response code");
		Assert.assertEquals(createResponse.getMessage(), "all labels successfully attached",
				"Found discrepancy in Response Message of attached entity");

		Map<String, String> successMap = createResponse.getSuccess();

		for (Entry<String, String> entry : successMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			Assert.assertEquals(value, "success", "found discrepancy in success message");

			for (LabelVO labelVO : labelNameEntities.getLabelVOs()) {
				String candidateID = getValue(key, 6);
				verifyCreatedLabelByName(labelNameEntities);

			}

		}

	}

	private void verifyCreatedLabelByName(LabelNameEntity labelNameEntities)
			throws JsonParseException, JsonMappingException, IOException {

		Boolean flag = false;

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();

		CollectionEntity<LabelBean> labelsList = client.listLabels();

		List<LabelBean> labels = (List<LabelBean>) labelsList.getItems();

		String labelName = null;

		List<String> candidateIDs = labelNameEntities.getEntityIds();

		Integer entityCount = candidateIDs.size();
		for (LabelVO labelVO : labelNameEntities.getLabelVOs()) {

			for (LabelBean createdlabeln : labels) {

				labelName = createdlabeln.getLabelName();
				if (labelName.equals(labelVO.getLabelName())) {

					flag = true;

					Assert.assertEquals(createdlabeln.getScope().toString(), labelVO.getScope().toString(),
							"Label is not created with give Name");

					Assert.assertEquals(createdlabeln.getAttachedEntityCount(), entityCount,
							"Label is not created with given entity");

					Assert.assertEquals(createdlabeln.getOwnerId(), ownerID, "Label is not created for the owner");

				}

			}

		}

		Assert.assertTrue(flag, "Expected Lable ID is not found in the list");

	}

	public void attachLabelsToEntities(SpireTestObject testObject, TestData data)
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

		if (createResponse.getCode() == 200) {

			validateattachLabelToEntityResponse(createResponse, labelNameEntities);

		} else {

			validateCreateFailureResponse(createResponse, labelNames);
		}

	}

	public void detachLabelToEntity(SpireTestObject testObject, TestData data)
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

		/*
		 * if (testObject.getTestData().equals("SHARED")) {
		 * 
		 * // shareTheLabel(labelName); // labelIDs.add(getLabelID(labelName));
		 * labelVO.setScope(Scope.SHARED);
		 * 
		 * }
		 */

		ResponseEntity detachResponse = client.detachLabelsToEntities("CANDIDATE", labelNameEntities);

		if (detachResponse.getCode() == 200) {

			verifyDetchLabelFromEntity(testObject, data, labelNameEntities);

		} else {
			Assert.fail("status code is not expected" + detachResponse.getCode());
		}

	}

	public String shareLabelName(String lableName) throws JsonParseException, JsonMappingException, IOException {

		String labelID = getLabelID(lableName);
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();

		LabelUsers labelUsers = new LabelUsers();
		List<User> users = new ArrayList<User>();
		User user = new User();
		String userId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("userId2");
		String loginId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("loginId2");
		user.setUserId(userId2);
		user.setUserName(loginId2);
		users.add(user);

		labelUsers.setUsers(users);
		labelUsers.setLabelId(labelID);

		ResponseEntity responseEntity = client.shareLabel(labelUsers);
		Logging.log("Response entity of Shared Label is " + gson.toJson(responseEntity));
		if (responseEntity.getCode() == 0) {

			Assert.assertNotNull(responseEntity.getSuccess(), "Share label is failed,");

			Assert.assertEquals(responseEntity.getFailure().toString(), "{}", "Share label is failed");

		} else {
			Assert.fail("share label is failed");
		}
return lableName;
	}

	private void verifyDetchLabelFromEntity(SpireTestObject testObject, TestData data,
			LabelNameEntity labelNameEntities) {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
		LabelsFilter labelFilter = new LabelsFilter();
		labelFilter.setEntityId(labelNameEntities.getEntityIds());

		labelFilterPageInfo.setLabelFilter(labelFilter);

		CollectionEntity<LabelBean> labelsList = client.filterLabels(labelFilterPageInfo);

		List<LabelBean> labels = (List<LabelBean>) labelsList.getItems();

		Assert.assertTrue(labels.isEmpty(), "Attched enties should be empty all the labels are detached");

	}

	public void filterLabels(SpireTestObject testObject, TestData data) {

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

		client.attachLabelsToEntity(candidateId, data.getData(), labelVOs);
		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);

		ResponseEntity detachResponse = client.detachLabelsToEntities("CANDIDATE", labelNameEntities);

		if (detachResponse.getCode() == 200) {

			LabelFilterPageInfo labelFilterPageInfo = new LabelFilterPageInfo();
			LabelsFilter labelFilter = new LabelsFilter();
			labelFilter.setEntityId(entityIds);
			labelFilterPageInfo.setLabelFilter(labelFilter);
			client.filterLabels(labelFilterPageInfo);

		} else {
			Assert.fail("status code is not expected" + detachResponse.getCode());
		}

	}

	public void clearAutomationLabel() {

		NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = new NewLabelsBizServiceConsumer();
		if (!labelIDs.isEmpty())
			newLabelsBizServiceConsumer.clearAutomationLabel(labelIDs);

	}

	public void deleteLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelId = createSingleLabel(testObject, data);
		client.deleteLabel(labelId);

	}

	public void deleteLabels(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		List<String> labelIds = createBulkLabel(testObject, data);
		client.deleteLabels(labelIds);

	}

	public void validateListProfiles(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String projection = testObject.getTestData();
		String labelName = "spireautomationlabel" + System.currentTimeMillis();
		LabelVO labelVO = new LabelVO();
		labelVO.setLabelName(labelName);
		labelVO.setScope(Scope.PRIVATE);
		List<LabelVO> labelVOs = new ArrayList<LabelVO>();
		LabelNameEntity labelNameEntities = new LabelNameEntity();
		labelVOs.add(labelVO);

		if (candidateIDs.isEmpty()) {
			candidateIDs.add(ProfileHelper.createProfile());
			candidateIDs.add(ProfileHelper.createProfile());
			candidateIDs.add(ProfileHelper.createProfile());
		} else {
			if (candidateIDs.size() < 2) {
				candidateIDs.add(ProfileHelper.createProfile());
				candidateIDs.add(ProfileHelper.createProfile());
			}

		}

		labelNameEntities.setEntityIds(candidateIDs);

		labelNameEntities.setLabelVOs(labelVOs);

		ResponseEntity createResponse = client.attachLabelsToEntities(data.getData(), labelNameEntities);

		List<String> labelNames = new ArrayList<String>();
		labelNames.add(labelName);

		if (createResponse.getCode() == 200) {
			String labelID = getLabelID(labelName);
			// labelIDs.add(labelID);
			if (projection.equals("FULL")) {
				List<Profile> labelProfiles = client.listProfiles(labelID);
				Assert.assertEquals(labelProfiles.size(), candidateIDs.size());

				List<String> actualCandiateIds = new ArrayList<String>();

				for (Profile profile : labelProfiles) {

					actualCandiateIds.add(profile.getCandidate().getId());

				}

				Assert.assertTrue(candidateIDs.containsAll(actualCandiateIds),
						"Add profile ids and get profile ids are not same");
			} else {

				@SuppressWarnings("unchecked")
				List<String> actualCandiateIds = (List<String>) client.listProfilesGeneric(labelID, projection);
				Assert.assertEquals(actualCandiateIds.size(), candidateIDs.size());
				Logging.log("actual candidateIDs : " + actualCandiateIds);
				Logging.log("Expeced candidateIDs : " + candidateIDs);
				Assert.assertTrue(candidateIDs.containsAll(actualCandiateIds),
						"Add profile ids and get profile ids are not same");

			}

		} else {

			Assert.fail("No profiles fetched from Label");
		}

	}

	public void renameLabel(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		String labelId = createSingleLabel(testObject, data);
		String newlabelName = "spireautomationlabel" + System.currentTimeMillis();
		ResponseEntity responseEntity = client.renameLabel(labelId, newlabelName);

		Assert.assertNotNull(responseEntity.getSuccess(), "Rename Label is failed & No data found in success");
		Assert.assertEquals(responseEntity.getFailure().toString().toString(), "{}",
				"Rename Label is failed & data found in failuer");
		LabelBean labelBean = new LabelBean();

		labelBean.setLabelName(newlabelName);
		labelBean.setLabelId(labelId);
		labelBean.setOwnerId(ownerID);
		labelBean.setScope(Scope.PRIVATE);
		verifyCreatedLabelByID(labelBean);

	}

	public String shareLabel_ByLabelId(SpireTestObject testObject, TestData data)
			throws JsonParseException, JsonMappingException, IOException {
		
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer(
				);
		LabelUsers labelUsers = new LabelUsers();
		List<User> users = new ArrayList<User>();
		User user = new User();
		String userId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("userId2");
		String loginId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("loginId2");
		user.setUserId(userId2);
		user.setUserName(loginId2);
		
		users.add(user);
		String createdlabelName = createLabel(testObject, data);
		String labelID = getLabelID(createdlabelName);
		labelUsers.setUsers(users);
		labelUsers.setLabelId(labelID);

		ResponseEntity responseEntity = client.shareLabel(labelUsers);

		Logging.log("Response entity of Shared Label is " + gson.toJson(responseEntity));
		if (responseEntity.getCode() == 0) {

			Assert.assertNotNull(responseEntity.getSuccess(), "Share label is failed,");

			Assert.assertEquals(responseEntity.getFailure().toString(), "{}", "Share label is failed");

		} else {
			Assert.fail("share label is failed");
		}
		return labelID;

	}

	public void shareLabelTwice(SpireTestObject testObject, TestData data, String labelID)
			throws JsonParseException, JsonMappingException, IOException {
		NewLabelsBizServiceConsumer client = new NewLabelsBizServiceConsumer();
		LabelUsers labelUsers = new LabelUsers();
		List<User> users = new ArrayList<User>();
		User user = new User();
		String userId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("userId2");
		String loginId2 = ReadingServiceEndPointsProperties.getServiceEndPoint("loginId2");
		user.setUserId(userId2);
		user.setUserName(loginId2);
		users.add(user);

		labelUsers.setUsers(users);
		labelUsers.setLabelId(labelID);

		ResponseEntity responseEntity = client.shareDuplicateLabel(labelUsers);

		Logging.log("Response entity of Shared Label is " + gson.toJson(responseEntity));
		if (responseEntity.getCode() == 0) {

			Assert.assertNotNull(responseEntity.getFailure(), "Share label is failed,");

			Assert.assertEquals(responseEntity.getSuccess().toString(), "{}", "Share label is failed");
			
		} else {
			Assert.fail("share label is failed");
		}
	}
}

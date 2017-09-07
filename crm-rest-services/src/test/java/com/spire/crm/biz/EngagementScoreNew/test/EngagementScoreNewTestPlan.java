package com.spire.crm.biz.EngagementScoreNew.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.helper.WebPageHelper;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.CampaignHelper;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.biz.campaign.test.CampaignBizSanityValidation;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.CampaignBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.CrmProfileEntityServiceConsumer;
import com.spire.crm.restful.biz.consumers.CrmRulesBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.EmailsBizConsumer;
import com.spire.crm.restful.biz.consumers.NotesBizServiceConsumer;

import crm.activitystream.beans.CRMCreateActivity;
import spire.crm.biz.com.service.beans.SendCustomEmailVO;
import spire.crm.biz.rules.entites.WeightageRuleEntity;
import spire.crm.entity.campaign.beans.CampaignLeadVO;
import spire.crm.entity.campaign.entities.Campaign;
import spire.crm.entity.campaign.entities.Node;
import spire.crm.entity.campaign.entities.ResponseEntity;
import spire.crm.notes.beans.NoteBean;
import spire.crm.profiles.entity.EngagementProfile;

/**
 * Testing Engagement Score New Service
 * 
 * @author Manaswini
 *
 */
@Test(retryAnalyzer = TestRetryAnalyzer.class)
public class EngagementScoreNewTestPlan {

	ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();
	NotesBizServiceConsumer notesBizServiceConsumer = new NotesBizServiceConsumer();
	CampaignHelper campaignHelper = new CampaignHelper();
	CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();
	CampaignBizServiceConsumer campaignBizServiceConsumer = new CampaignBizServiceConsumer();
	EmailsBizConsumer emailsBizConsumer = new EmailsBizConsumer();
	SendCustomEmailVO sendCustomEmailVO = new SendCustomEmailVO();
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("CRM_WEIGHT_BIZ");
	CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(endPointURL);
	CRMCreateActivity createActivity = new CRMCreateActivity();
	Gson gson = new Gson();
	DataFactory factory = new DataFactory();
	NoteBean notes = new NoteBean();
	String profileId = null;
	List<String> profiles = new ArrayList<>();
	String totEngagementScore = null;
	String ratings = null;
	Float value = null;
	int expectedEngageValue;
	int actualTotalEngagementScore;
	public static String tenantID = ReadingServiceEndPointsProperties.getServiceEndPoint("tenantId");

	@DataProvider(name = "EngagementScoreNew")
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/EngagementScoreNew/test/EngagementScoreNewTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ESPojo", ESPojo.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(EngagementScoreNewTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@BeforeTest(alwaysRun = true)
	public WeightageRuleEntity engagementScoreWeight() {
		Response response = crmRulesBizServiceConsumer.getWeightByID(Integer.parseInt(tenantID));
		WeightageRuleEntity weightageRuleEntity = response.readEntity(WeightageRuleEntity.class);
		return weightageRuleEntity;
	}

	@Test(groups = { "createActivityBenfitLevel", "Sanity" }, priority = 0, dataProvider = "EngagementScoreNew")
	public void createActivityBenfitLevel(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		if (testdata.getData().equals("one rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getBenefitAwareness() * (createActivity.getBenifitLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("two rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getBenefitAwareness() * (createActivity.getBenifitLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("three rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getBenefitAwareness() * (createActivity.getBenifitLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("four rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getBenefitAwareness() * (createActivity.getBenifitLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("five rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getBenefitAwareness() * (createActivity.getBenifitLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("Email Send")) {
			profileId = ProfileHelper.createProfile("crmvista.services@gmail.com");
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getBenefitAwareness() * (createActivity.getBenifitLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			profiles.add(profileId);
			sendCustomEmailVO.setFrom(factory.getEmailAddress());
			sendCustomEmailVO.setTo(profiles);
			sendCustomEmailVO.setSubject("email send");
			sendCustomEmailVO.setContentHeader("Custom Send email " + factory.getRandomChars(100) + profileId);
			sendCustomEmailVO.setContentBody("Custom Send email " + factory.getRandomChars(100) + profileId);
			sendCustomEmailVO.setContentSignature("Custom Send email " + factory.getRandomChars(100) + profileId);
			value = value + 1;
			expectedEngageValue = (int) Math.ceil(value);
			Response response1 = emailsBizConsumer.sendEmailCustom(sendCustomEmailVO);
			Assert.assertEquals(response1.getStatus(), 202, "failed to send email");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}

	}

	@Test(groups = { "createActivityCompanyLevelLRG", "LRG" }, priority = 1, dataProvider = "EngagementScoreNew")
	public void createActivityCompanyLevelLRG(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		if (testdata.getData().equals("one rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getCompanyAwareness() * (createActivity.getCompanyLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("two rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getCompanyAwareness() * (createActivity.getCompanyLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("three rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getCompanyAwareness() * (createActivity.getCompanyLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("four rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getCompanyAwareness() * (createActivity.getCompanyLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}
	}

	@Test(groups = { "createActivityCompanyLevelSanity", "Sanity" }, priority = 1, dataProvider = "EngagementScoreNew")
	public void createActivityCompanyLevelSanity(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		if (testdata.getData().equals("five rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getCompanyAwareness() * (createActivity.getCompanyLevel()) / 5)
							* weightageRuleEntity.getAwareness() / 100);
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}

	}

	@Test(groups = { "createActivityInterestLevelLRG", "LRG" }, priority = 2, dataProvider = "EngagementScoreNew")
	public void createActivityInterestLevelLRG(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		if (testdata.getData().equals("one rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getInterestLevel() * (createActivity.getInterestLevel()) / 5));
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("two rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getInterestLevel() * (createActivity.getInterestLevel()) / 5));
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("three rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getInterestLevel() * (createActivity.getInterestLevel()) / 5));
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("four rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getInterestLevel() * (createActivity.getInterestLevel()) / 5));
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}
	}

	@Test(groups = { "createActivityInterestLevelSanity", "Sanity" }, priority = 2, dataProvider = "EngagementScoreNew")
	public void createActivityInterestLevelSanity(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		if (testdata.getData().equals("five rating")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1
					+ ((weightageRuleEntity.getInterestLevel() * (createActivity.getInterestLevel()) / 5));
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}
	}

	@Test(groups = { "createActivitiesWithoutRating", "Sanity" }, priority = 3, dataProvider = "EngagementScoreNew")
	public void createActivitiesWithoutRating(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		if (testdata.getData().equals("Video Call")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1;
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("Instance Message")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1;
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("Voice Call Made")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1;
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("Voice Call Received")) {
			profileId = ProfileHelper.createProfile();
			createActivity = getCRMCreateActivityData(espojo);
			value = weightageRuleEntity.getConnectFactors() + 1;
			expectedEngageValue = (int) Math.ceil(value);
			createActivity.setCandidateId(profileId);
			Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
			Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
			Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		} else if (testdata.getData().equals("Notes")) {
			profileId = ProfileHelper.createProfile();
			expectedEngageValue = 0;
			notes.setEntityId(profileId);
			notes.setNotesTitle("Java");
			notes.setNotesMessage("intrested in java");
			NoteBean note = notesBizServiceConsumer.createNoteForTheCandidate(notes);
			Assert.assertNotNull(note.getId(), "note is not created");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}
	}

	@Test(groups = { "createAllActivitiesWithFullRatings",
			"Sanity" }, priority = 6, dataProvider = "EngagementScoreNew")
	public void createAllActivitiesWithFullRatings(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		profileId = ProfileHelper.createProfile("crmvista.services@gmail.com");
		value = weightageRuleEntity.getConnectFactors() + 1;
		sendCustomEmailVO.setFrom(factory.getEmailAddress());
		sendCustomEmailVO.setTo(profiles);
		sendCustomEmailVO.setSubject("email send");
		sendCustomEmailVO.setContentHeader("Custom Send email " + factory.getRandomChars(100) + profileId);
		sendCustomEmailVO.setContentBody("Custom Send email " + factory.getRandomChars(100) + profileId);
		sendCustomEmailVO.setContentSignature("Custom Send email " + factory.getRandomChars(100) + profileId);
		Response response5 = emailsBizConsumer.sendEmailCustom(sendCustomEmailVO);
		Assert.assertEquals(response5.getStatus(), 202, "failed to send email");
		createActivity = getCRMCreateActivityData(espojo);
		createActivity.setCandidateId(profileId);
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
		createActivity.setActivityTypeInfo("Instance Message");
		value = value + 1;
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response1 = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response1.getStatus(), 201, "failed to create activity");
		createActivity.setActivityTypeInfo("Voice call made");
		value = value + 1;
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response2 = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response2.getStatus(), 201, "failed to create activity");
		createActivity.setActivityTypeInfo("Voice call received");
		value = value + 1;
		expectedEngageValue = (int) Math.ceil(value);
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response3 = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response3.getStatus(), 201, "failed to create activity");
		notes.setEntityId(profileId);
		notes.setNotesTitle("Java");
		notes.setNotesMessage("intrested in java");
		NoteBean note = notesBizServiceConsumer.createNoteForTheCandidate(notes);
		Assert.assertNotNull(note.getId(), "note is not created");
		waitMethod();
		actualTotalEngagementScore = validateEngagementScore(profileId);
		Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
	}

	@Test(groups = { "createAllActivitiesWithoutRating", "Sanity" }, priority = 5, dataProvider = "EngagementScoreNew")
	public void createAllActivitiesWithoutRating(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();
		profileId = ProfileHelper.createProfile();
		createActivity = getCRMCreateActivityData(espojo);
		createActivity.setCandidateId(profileId);
		value = weightageRuleEntity.getConnectFactors() + 1;
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response.getStatus(), 201, "failed to create activity");
		createActivity.setActivityTypeInfo("Instance Message");
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response1 = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response1.getStatus(), 201, "failed to create activity");
		createActivity.setActivityTypeInfo("Voice call made");
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response2 = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response2.getStatus(), 201, "failed to create activity");
		createActivity.setActivityTypeInfo("Voice call received");
		Logging.log("CreateActivityRequest json: " + gson.toJson(createActivity));
		Response response3 = activityStreamBizServiceConsumer._createActivity(createActivity);
		Assert.assertEquals(response3.getStatus(), 201, "failed to create activity");
		expectedEngageValue = (int) Math.ceil(value);
		notes.setEntityId(profileId);
		notes.setNotesTitle("Java");
		notes.setNotesMessage("intrested in java");
		NoteBean note = notesBizServiceConsumer.createNoteForTheCandidate(notes);
		Assert.assertNotNull(note.getId(), "note is not created");
		waitMethod();
		actualTotalEngagementScore = validateEngagementScore(profileId);
		Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
	}

	@Test(groups = { "assignCandidateToCamapign", "Sanity" }, priority = 4, dataProvider = "EngagementScoreNew")
	public void assignCandidateToCamapign(SpireTestObject testObject, ESPojo espojo, TestData testdata) {
		WeightageRuleEntity weightageRuleEntity = engagementScoreWeight();

		if (testdata.getData().equals("campaign")) {
			CampaignBizSanityValidation campaignServiceBizValidation = new CampaignBizSanityValidation();
			profileId = ProfileHelper.createProfile();
			profiles.add(profileId);
			value = weightageRuleEntity.getConnectFactors() + 1;
			expectedEngageValue = (int) Math.ceil(value);
			List<Node> nodeLIst = new ArrayList<Node>();
			Node changesatgeNode = campaignServiceBizValidation.setUpSendEmailNode(null, null, null);
			nodeLIst.add(changesatgeNode);
			Campaign campaign = campaignServiceBizValidation.setUpCampaign(true);
			campaign.setNodes(nodeLIst);
			Response createResponse = campaignBizServiceConsumer.createCampaign(campaign);
			ResponseEntity createResponseEntity = createResponse.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(campaign.getName());
			List<String> campaignIds = new ArrayList<>();
			campaignIds.add(campaignId);
			Assert.assertNotNull(campaignId, "camapign is not created");
			CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
			campaignLeadVO.setLeadIds(profiles);
			campaignLeadVO.setCampaignIds(campaignIds);
			Response res = campaignBizServiceConsumer.bulkAddLeadToCampaigns(campaignLeadVO);
			Assert.assertEquals(res.getStatus(), 200, "candidate is not assigned to campaign");
			waitMethod();
			actualTotalEngagementScore = validateEngagementScore(profileId);
			Assert.assertEquals(actualTotalEngagementScore, expectedEngageValue, "giving wrong engagement score");
		}
	}

	public int validateEngagementScore(String profileId) {
		int totalEngagementScore=0,i=0;
		
		while (totalEngagementScore==0){		
			
			Response response = crmProfileEntityServiceConsumer.getProfilebyIds(profileId);
			EngagementProfile engagementProfile = response.readEntity(EngagementProfile.class);
			Logging.log("EngagementScoreResponse json: " + gson.toJson(engagementProfile));
			totalEngagementScore = engagementProfile.getTotalEngagementScore();
			Logging.log("Actual:" + totalEngagementScore);
			i++;
			WebPageHelper.sleep(30000);
			
			if (i==5)
				break;
			
		}
				
		return totalEngagementScore;

	}

	public void waitMethod() {
		/*try {
			Thread.sleep(120000);
		} catch (Exception e) {

		}*/

	}

	/*
	 * @AfterTest(alwaysRun = true) // , dependsOnMethods = { //
	 * "createActivityToCandidate" }) public void waitMethod() { try {
	 * Thread.sleep(120000); } catch (Exception e) {
	 * 
	 * } int j = 0; for (int i = 0; i < 100; i += 2) { if (i <
	 * (map.values().size()) * 2) { ArrayList<String> valiadteValue =
	 * map.values().iterator().next(); String profileId1 = valiadteValue.get(i);
	 * Logging.log(profileId1); Response response =
	 * crmProfileEntityServiceConsumer.getProfilebyIds(profileId1);
	 * EngagementProfile engagementProfile =
	 * response.readEntity(EngagementProfile.class); Logging.log(
	 * "EngagementScoreResponse json: " + gson.toJson(engagementProfile)); int
	 * totalEngagementScore = engagementProfile.getTotalEngagementScore();
	 * String totEngagementScore = Integer.toString(totalEngagementScore);
	 * Logging.log(valiadteValue.get(i + 1));
	 * Logging.log(totEngagementScore); try {
	 * Assert.assertEquals(totEngagementScore, valiadteValue.get(i + 1),
	 * "giving wrong engagement score for testcaseId : " + ids.get(j)); ++j; }
	 * catch (Exception e) { Logging.log("Assertion error" + e); } ;
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * 
	 * @Test(dependsOnGroups = { "wait" }) public String
	 * validateEngagementscore(TestData data, SpireTestObject spireTestObject) {
	 * 
	 * EngagementProfile engagementProfile =
	 * engagementScoreNewConsumer.getEngagementScoreByProfileId(profileId); int
	 * totalEngagementScore = engagementProfile.getTotalEngagementScore();
	 * String totEngagementScore = Integer.toString(totalEngagementScore);
	 * ArrayList<String> valiadteValue = map.values().iterator().next();
	 * Assert.assertEquals(valiadteValue.get(1), totEngagementScore,
	 * "giving wrong engagement score"); return data.getData();
	 * 
	 * }
	 * 
	 */
	public CRMCreateActivity getCRMCreateActivityData(ESPojo espojo) {

		CRMCreateActivity crmCreateActivity = new CRMCreateActivity();
		crmCreateActivity.setActivityTypeInfo(espojo.getActivityTypeInfo());
		crmCreateActivity.setBenifitLevel(Integer.parseInt(espojo.getBenifitLevel()));
		crmCreateActivity.setCompanyLevel(Integer.parseInt(espojo.getCompanyLevel()));
		crmCreateActivity.setFitmentLevel(Integer.parseInt(espojo.getFitmentLevel()));
		crmCreateActivity.setInterestLevel(Integer.parseInt(espojo.getInterestLevel()));
		return crmCreateActivity;

	}
}

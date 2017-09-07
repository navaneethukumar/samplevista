package com.spire.crm.biz.crm.profile.test;

/**
 * @author Manikanta.Y
 */

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.utils.IdUtils;
import spire.crm.profiles.entity.DateInput;
import spire.crm.profiles.entity.EngagementProfile;
import spire.crm.profiles.entity.EngagementProfilesSummary;
import spire.crm.profiles.entity.EngagementTrend;
import spire.crm.profiles.entity.EngagementTrendInput;
import spire.crm.profiles.entity.ErrorEntity;
import spire.crm.profiles.entity.Filter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.biz.consumers.CrmProfileEntityServiceConsumer;

public class CRMProfileValidation extends CRMTestPlan {

	public static Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();
	public static EngagementProfile profile = new EngagementProfile();
	public static List<EngagementProfile> engagementProfiles = new ArrayList<EngagementProfile>();

	static CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

	/*
	 * Get profile by giving the candidate ID
	 */

	public static void validate_getProfileByID(EngagementProfile request) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		Response response = crmProfileEntityServiceConsumer
				.getProfilebyIds(request.getCrmCandidateId());

		if (response.getStatus() == 200) {
			String profile = response.readEntity(String.class);
			Logging.log(" Get Profile response >>>" + profile);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			mapper.registerModule((Module) new JavaTimeModule());

			EngagementProfile getProfile;
			try {

				getProfile = mapper.readValue(profile, EngagementProfile.class);

				compareProfiles(getProfile, request);

			} catch (JsonParseException e) {
				Logging.log("JsonParseException " + e.getMessage());
				Assert.fail("JsonParseException");
			} catch (JsonMappingException e) {
				Logging.log("JsonMappingException " + e.getMessage());
				Assert.fail("JsonMappingException");
			} catch (IOException e) {
				Logging.log("IOException " + e.getMessage());
				Assert.fail("IOException");
			}

		} else if (response.getStatus() == 400) {

			ErrorEntity errorBody = response.readEntity(ErrorEntity.class);
			Logging.log("error Response  >>" + gson.toJson(errorBody));

			Assert.assertEquals(errorBody.getCode(), "INVALID_PARAMETER",
					" invalid error code");
			Assert.assertEquals(errorBody.getMessage(),
					"not valid candidateIds",
					" not valid candidateId error message is not displayed");

		} else {

			Assert.fail("getting the profile is failed and status code is :"
					+ response.getStatus());

		}

	}

	/**
	 * 
	 * Create a profile by giving the Profile object
	 * 
	 */
	public static Response validate_Create(EngagementProfile createProfile) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		setUPEngagementProfile(createProfile);

		Response response = crmProfileEntityServiceConsumer
				.createProfile(createProfile);

		if (response.getStatus() == 201) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Logging.log("InterruptedException " + e.getMessage());
				Assert.fail("InterruptedException");
			}
			validate_getProfileByID(createProfile);

		} else if (response.getStatus() == 400) {

			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);

			Assert.assertEquals(errorEntity.getCode(), "INVALID_PARAMETER",
					"Invalid parameter exception should throw");
			Assert.assertEquals(errorEntity.getMessage(), "stageName invalid",
					"Invalid parameter exception should throw,when stageName is invalid");

		} else {

			Assert.fail(" Profile is not Created and status code is :"
					+ response.getStatus());
		}
		return response;

	}

	/**
	 * 
	 * Create a profile by giving the Profile object
	 * 
	 */
	public static Response validate_CreateBulk(EngagementProfile createProfile) {
		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();
		setUPEngagementProfile(createProfile);
		engagementProfiles.add(setUPEngagementProfile(createProfile));
		profile.setAwarenessScore(0);
		profile.setInterestLevelScore(0);
		profile.setPersonalCommunicationScore(0);
		profile.setCompatibilityScore(0);
		profile.setBroadcastCommunicationScore(0);
		profile.setConnectFactorScore(0);
		profile.setCrmId("6001:6003");
		profile.setCrmCandidateId("CandidateID");
		profile.setStageName("LEAD");
		profile.setTotalEngagementScore(7);
		engagementProfiles.add(setUPEngagementProfile(profile));
		Response response = crmProfileEntityServiceConsumer
				.createProfileBulk(engagementProfiles);

		if (response.getStatus() == 201) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Logging.log("InterruptedException " + e.getMessage());
				Assert.fail("InterruptedException");
			}
			validate_getProfileByID(createProfile);
			validate_getProfileByID(profile);

		} else if (response.getStatus() == 400) {

			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);

			Assert.assertEquals(errorEntity.getCode(), "INVALID_PARAMETER",
					"Invalid parameter exception should throw");
			Assert.assertEquals(errorEntity.getMessage(), "stageName invalid",
					"Invalid parameter exception should throw,when stageName is invalid");

		} else {

			Assert.fail(" Profile is not Created and status code is :"
					+ response.getStatus());
		}
		return response;

	}

	/**
	 * 
	 * Compare the two profile
	 * 
	 */
	private static void compareProfiles(EngagementProfile getProfile,
			EngagementProfile createProfile) {

		Assert.assertEquals(getProfile.getCrmCandidateId(),
				createProfile.getCrmCandidateId(), "Candidate Id are not equal");
		Assert.assertEquals(getProfile.getTotalEngagementScore(),
				createProfile.getTotalEngagementScore(),
				"Engagement Score are not equal");

		Assert.assertEquals(getProfile.getStageName(),
				createProfile.getStageName(), "Stage Name are not equal");
		Assert.assertEquals(getProfile.getAwarenessScore(),
				createProfile.getAwarenessScore(),
				"Awareness Score are not equal");
		Assert.assertEquals(getProfile.getBroadcastCommunicationScore(),
				createProfile.getBroadcastCommunicationScore(),
				"BroadcastCommunication Score are not equal");
		Assert.assertEquals(getProfile.getConnectFactorScore(),
				createProfile.getConnectFactorScore(),
				"ConnectFactor Score are not equal");
		Assert.assertEquals(getProfile.getCompatibilityScore(),
				createProfile.getCompatibilityScore(),
				"Compatibility Score are not equal");

		Assert.assertEquals(getProfile.getCompatibilityScore(),
				createProfile.getCompatibilityScore(),
				"Compatibility Score are not equal");
		Assert.assertEquals(getProfile.getCompatibilityScore(),
				createProfile.getCompatibilityScore(),
				"Compatibility Score are not equal");
		Assert.assertEquals(getProfile.getCompatibilityScore(),
				createProfile.getCompatibilityScore(),
				"Compatibility Score are not equal");
		Assert.assertEquals(getProfile.getCompatibilityScore(),
				createProfile.getCompatibilityScore(),
				"Compatibility Score are not equal");
	}

	/**
	 * 
	 * Generating Candidate id and setting to Request Object
	 * 
	 */

	private static EngagementProfile setUPEngagementProfile(
			EngagementProfile createProfile) {

		String candidateID = IdUtils.generateID("6001", "6005");
		createProfile.setCrmCandidateId(candidateID);
		return createProfile;
	}

	/**
	 * 
	 * Create a profile by giving the Profile object
	 * 
	 */
	public static void validate_Update(EngagementProfile createProfile) {
		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		setUPEngagementProfile(createProfile);

		Response response = crmProfileEntityServiceConsumer
				.createProfile(createProfile);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Logging.log("InterruptedException " + e.getMessage());
			Assert.fail("InterruptedException");
		}

		if (response.getStatus() == 201) {

			verify_Update(createProfile);

		} else {

			Assert.fail(" Profile is not Updated and status code is :"
					+ response.getStatus());
		}

	}

	public static void verify_Update(EngagementProfile updateProfile) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		setupdateProfile(updateProfile);

		Response response = crmProfileEntityServiceConsumer
				.updateProfile(updateProfile);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Logging.log("InterruptedException " + e.getMessage());
			Assert.fail("InterruptedException");
		}

		if (response.getStatus() == 200) {

			validate_getProfileByID(updateProfile);

		} else {

			Assert.fail(" Profile is not Created and status code is :"
					+ response.getStatus());
		}

	}

	private static void setupdateProfile(EngagementProfile createProfile) {

		createProfile.setAwarenessScore(0);
		createProfile.setBroadcastCommunicationScore(20);
		createProfile.setCompatibilityScore(7);
		createProfile.setConnectFactorScore(8);
		createProfile.setInterestLevelScore(3);
		createProfile.setPersonalCommunicationScore(10);
		createProfile.setTotalEngagementScore(5);
		createProfile.setStageName("APPLICANT");

	}

	public static void validate_listByCandidateIDs(
			EngagementProfile createProfile) {

		String CandidateCount = createProfile.getCrmCandidateId();

		List<String> candidateIds = new ArrayList<String>();
		int createStatusCode = 0;

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		for (int i = 0; i <= Integer.parseInt(CandidateCount); i++) {

			setUPEngagementProfile(createProfile);

			Response response = crmProfileEntityServiceConsumer
					.createProfile(createProfile);

			createStatusCode = response.getStatus();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Logging.log("InterruptedException " + e.getMessage());
				Assert.fail("InterruptedException");
			}
			if (createStatusCode == 201) {
				candidateIds.add(createProfile.getCrmCandidateId());
			} else {
				Assert.fail(" Profile is not Created and status code is :"
						+ response.getStatus());
			}
		}

		if (createStatusCode == 201) {

			verify_listByCandidateIDs(candidateIds);

		} else {

			Assert.fail(" Profile is not Created and status code is :"
					+ createStatusCode);
		}

	}

	static void verify_listByCandidateIDs(List<String> candidateIds) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		Response response = crmProfileEntityServiceConsumer
				.listByCandidateIds(candidateIds);

		if (response.getStatus() == 200) {

			String candidateList = response.readEntity(String.class);

			/*
			 * String candidateList = response.readEntity(String.class); for
			 * (int i = 0; i < candidateIds.size(); i++) {
			 * Assert.assertTrue(candidateList.contains(candidateIds.get(i)),
			 * " candidte id not foung in list");
			 * 
			 * }
			 */
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			mapper.registerModule((Module) new JavaTimeModule());
			EngagementProfile[] candidatesList = null;
			try {
				candidatesList = mapper.readValue(candidateList,
						EngagementProfile[].class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (response.getStatus() == 400) {

			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);

			Assert.assertEquals(errorEntity.getCode(), "INVALID_PARAMETER",
					"Invalid parameter exception should throw");
			Assert.assertEquals(errorEntity.getMessage(),
					"not valid candidateIds",
					"not valid candidateIds error message not displayed");

		} else {

			Assert.fail("List By CandidateIds failed and status code is :"
					+ response.getStatus());
		}

	}

	public static void validate_listEngagementTrend(
			EngagementProfile createProfile) {

		int createStatusCode = 0;

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		setUPEngagementProfile(createProfile);

		Response response = crmProfileEntityServiceConsumer
				.createProfile(createProfile);

		createStatusCode = response.getStatus();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Logging.log("InterruptedException " + e.getMessage());
			Assert.fail("InterruptedException");
		}

		if (createStatusCode == 201) {

			verify_Trend(createProfile);

		} else {
			Assert.fail(" Profile is not Created and status code is :"
					+ response.getStatus());
		}

	}

	public static void verify_Trend(EngagementProfile createProfile) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		EngagementTrendInput engagementTrendInput = new EngagementTrendInput();

		if (!createProfile.getCrmCandidateId().equalsIgnoreCase("invalid")) {
			engagementTrendInput.setCandidateId(createProfile
					.getCrmCandidateId());
		} else {
			engagementTrendInput.setCandidateId(IdUtils.generateID("6000",
					"6603"));
		}

		ZonedDateTime zoneTime = ZonedDateTime.now();
		// ofInstant(calculateFromDate.toInstant(), ZoneId.systemDefault());
		// .parse("2000-12-22T09:13:55.988Z");
		// Date fromDateObj = Date.from(zoneTime.toInstant());

		ZonedDateTime zoneTime2 = ZonedDateTime.now(ZoneId.systemDefault());
		// ZonedDateTime
		// .parse("9999-12-31T09:13:55.988Z");
		// Date toDateObj = Date.from(zoneTime2.toInstant());

		engagementTrendInput.setFromDate(zoneTime);

		engagementTrendInput.setToDate(zoneTime2);

		Response response = crmProfileEntityServiceConsumer
				.listEngagementTrend(engagementTrendInput);

		if (response.getStatus() == 200) {
			EngagementTrend engagementTrend = response
					.readEntity(EngagementTrend.class);

			Logging.log("Trend Response : " + gson.toJson(engagementTrend));
			Assert.assertTrue(
					engagementTrend.getEngagementScores().get(0) == 7,
					"Discrepancy in displayed enagegment score ");

			Assert.assertTrue(engagementTrend.getEngagementTimes().size() == 1,
					"Discrepancy in displayed Engagement Times ");
		} else if (response.getStatus() == 400) {

			ErrorEntity errorEntity = response.readEntity(ErrorEntity.class);

			Assert.assertEquals(errorEntity.getCode(), "INVALID_PARAMETER",
					"Invalid parameter exception should throw");
			Assert.assertEquals(errorEntity.getMessage(),
					"Invalid input candidate Id is not present",
					"Invalid input candidate Id is not present error message not displayed");

		} else {
			Assert.fail("get Engagement Trend is failed and status code is :"
					+ response.getStatus());
		}

	}

	public static void verifyFilter(Filter filter) {

		String filterInput = gson.toJson(filter);

		Response response = crmProfileEntityServiceConsumer
				.listByFilter(filterInput);

		if (response.getStatus() == 200) {

			String summary = response.readEntity(String.class);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			mapper.registerModule((Module) new JavaTimeModule());
			EngagementProfilesSummary engagementProfilesSummary = null;
			try {
				engagementProfilesSummary = mapper.readValue(summary,
						EngagementProfilesSummary.class);
			} catch (IOException e) {

				Assert.fail("Run time exception in verifyFilter "
						+ e.getMessage());
				e.printStackTrace();
			}

			validateFilterResults(engagementProfilesSummary, filter);

		}

		else {

			Assert.fail("Filter is failed and status code is :"
					+ response.getStatus());
		}

	}

	private static void validateFilterResults(
			EngagementProfilesSummary engagementProfilesSummary, Filter filter) {

		if (engagementProfilesSummary.getTotalCount() == 0) {

			if (!engagementProfilesSummary.getProfiles().isEmpty()) {
				Assert.fail(" Toatal count is zero but displayed profiles");
			}
		} else {

			Map<String, Integer> stageSummary = engagementProfilesSummary
					.getStageSummary();

			Set<String> stageNames = stageSummary.keySet();

			List<EngagementProfile> profiles = engagementProfilesSummary
					.getProfiles();

			Set<String> filterStageNames = filter.getStageNames();

			if (!stageNames.equals(filterStageNames)) {

				Assert.fail(" Satge names are not displayed in results of list by filter"
						+ gson.toJson(filterStageNames));
			}

			Boolean stageFlag = false;

			for (EngagementProfile engagementProfile : profiles) {

				for (String stageName : filterStageNames) {

					if (engagementProfile.getStageName().equalsIgnoreCase(
							stageName)) {

						stageFlag = true;
					}

				}

				if (stageFlag) {
					stageFlag = false;
				} else {
					Assert.fail("Found candidate with a stage name,that is not in filter"
							+ engagementProfile.getCrmCandidateId());

				}

				verifyProfile(engagementProfile, filter);

			}

			verifyOrder(profiles, filter);
		}

	}

	private static void verifyOrder(List<EngagementProfile> profiles,
			Filter filter) {

		String orderBy = filter.getOrderBy();
		String sortOrder = filter.getSort();

		if (orderBy.equals("ENGAGEMENT SCORE")) {

			for (int i = 1; i < profiles.size(); i++) {
				int ES2 = profiles.get(i).getTotalEngagementScore();
				int ES1 = profiles.get(i - 1).getTotalEngagementScore();
				if (sortOrder.equals("ASCENDING")) {
					if (ES1 > ES2) {
						Assert.fail(" Profile are not order by ASCENDING and ENGAGEMENT SCORE ");

					}
				} else {

					if (ES1 < ES2) {

						Assert.fail(" Profile are not order by DESCENDING and ENGAGEMENT SCORE ");

					}

				}

			}

		}

	}

	private static void verifyProfile(EngagementProfile profile, Filter filter) {

		for (int i = 0; i < filter.getOffset(); i++) {

			Assert.assertTrue(
					profile.getTotalEngagementScore() <= filter
							.getToEngagementScore(),
					"fetched invalid engagement score "
							+ profile.getTotalEngagementScore());

			Assert.assertTrue(
					profile.getTotalEngagementScore() >= filter
							.getFromEngagementScore(),
					"fetched invalid engagement score "
							+ profile.getTotalEngagementScore());

		}
	}

	private static EngagementProfile setUPFilterProfile(Filter requestFilter) {

		EngagementProfile filterProfie = new EngagementProfile();

		String stageName = null;

		for (String stage : requestFilter.getStageNames()) {
			stageName = stage;
		}

		String candidateID = IdUtils.generateID("6001", "6005");
		filterProfie.setCrmCandidateId(candidateID);
		filterProfie.setStageName(stageName);
		filterProfie.setTotalEngagementScore(17);
		return filterProfie;
	}

	public static void verifyDateRangeFilter() {

	}

	public static Filter setUpFilter(SpireTestObject testObject, TestData data,
			Filter requestFilter) {
		Filter tempFilter = new Filter();
		String testTags = testObject.getTestTags();
		requestFilter.setFromDate(null);
		requestFilter.setToDate(null);

		Set<String> stageNames = new HashSet<String>();

		stageNames.add("LEAD");
		stageNames.add("APPLICANT");
		stageNames.add("ENGAGED");
		stageNames.add("HOLD");
		stageNames.add("REJECTED");
		tempFilter.setStageNames(stageNames);

		switch (testTags) {
		case "Sanity":
			break;

		case "CandidateIDs":
			tempFilter.setFromDate(null);
			tempFilter.setToDate(null);

			String candidateID = ProfileHelper.createProfile();

			List<String> candidateIds = new ArrayList<String>();

			candidateIds.add(candidateID);
			tempFilter.setCandidateIds(candidateIds);
			return tempFilter;

		}

		return requestFilter;

	}

	public static void verifycrmReportsResponseStatus(
			SpireTestObject testObject, TestData data) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();

		Response crmresponse = crmProfileEntityServiceConsumer.getcrmReports();

		if (crmresponse.getStatus() != 201) {

			Assert.fail("CRM Reports are failed and status code is >>>"
					+ crmresponse.getStatus());

		}

		Response labelresponse = crmProfileEntityServiceConsumer
				.getLabelReports();

		if (labelresponse.getStatus() != 201) {

			Assert.fail(" Lable Reports are failed and status code is >>>"
					+ labelresponse.getStatus());

		}

	}

	public static void verifyDownloadReports(SpireTestObject testObject,
			TestData data) {

		CrmProfileEntityServiceConsumer crmProfileEntityServiceConsumer = new CrmProfileEntityServiceConsumer();
		Response response = null;
		switch (testObject.getTestTitle()) {
		case "downloadCRMReport_Sanity":
			response = crmProfileEntityServiceConsumer.downloadcrmReports();

			if (response.getStatus() == 200) {
				verifyCRMReport(testObject, data, response);
			} else {
				Assert.fail("Dwonload crm report is failed>>>>"
						+ response.getStatus());
			}

			break;
		case "downloadlabelReport_Sanity":

			response = crmProfileEntityServiceConsumer.downloadLabelreports();

			if (response.getStatus() == 200) {

				verifyLabelReport(testObject, data, response);

			} else {
				Assert.fail("Dwonload label report is failed>>>>"
						+ response.getStatus());
			}

			break;

		default:
			break;
		}

	}

	private static void verifyCRMReport(SpireTestObject testObject,
			TestData data, Response response) {

		String crmResponse = response.readEntity(String.class);
		Logging.log("Crm Report Response " + crmResponse);

		Assert.assertTrue(
				crmResponse
						.contains("UserId,UserName,StageName,ReasonName,Count"),
				"Expected header for report is not found  #Expeced#- UserId,UserName,StageName,ReasonName,Count ");

	}

	public static void validateNoBroadcastCommunication(String crmId) {
		DateInput input = new DateInput();
		Response response = crmProfileEntityServiceConsumer
				.listProfilesWithNoCommunication(input);
		List<EngagementProfile> engagementProfiles = response
				.readEntity(new GenericType<List<EngagementProfile>>() {
				});
		List<String> crmIds = new ArrayList<String>();
		for (EngagementProfile engagementProfile : engagementProfiles) {
			crmIds.add(engagementProfile.getCrmId());
		}
		Assert.assertTrue(crmIds.contains(crmId),
				"Displaying profiles which do not have broadcast communication");

	}

	private static void verifyLabelReport(SpireTestObject testObject,
			TestData data, Response response) {

		String labelResponse = response.readEntity(String.class);
		Logging.log("Label Report Response " + labelResponse);

		Assert.assertTrue(
				labelResponse
						.contains("LabelId,LabelName,LabelOwner,StageName,Count"),
				"Expected header for report is not found #Expeced#-LabelId,LabelName,LabelOwner,StageName,Count ()");

	}
}

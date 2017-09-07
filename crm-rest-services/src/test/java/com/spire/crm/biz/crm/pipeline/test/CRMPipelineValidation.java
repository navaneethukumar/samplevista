package com.spire.crm.biz.crm.pipeline.test;

/**
 * @author Manikanta.Y
 */

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.commons.utils.IdUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.biz.consumers.CrmPipeLineBizServiceConsumer;

import crm.pipeline.beans.CRM;
import crm.pipeline.beans.CRMFilter;
import crm.pipeline.beans.CRMFilterResponse;
import crm.pipeline.beans.CreateCRM;
import crm.pipeline.beans.EngagementRuleConfig;
import crm.pipeline.entities.CollectionEntity;

public class CRMPipelineValidation extends CRMTestPlan {

	public static Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();

	static CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

	public static void verifyListDateRanges() {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();
		Response dateRanges = null;

		dateRanges = crmPipeLineBizServiceConsumer.listDateRanges();

		if (dateRanges.getStatus() == 200) {

			/*
			 * CollectionEntity<String> dateRange = dateRanges
			 * .readEntity(CollectionEntity.class);
			 */
			String response = dateRanges.readEntity(String.class);
			Logging.log("ListDateRanges Response >> " + response);

			Assert.assertTrue(response.contains("The Past 1 day"),
					"The Past 1 day is not displayed");
			Assert.assertTrue(response.contains("The Past 1 week"),
					"The Past 1 week is not displayed");
			Assert.assertTrue(response.contains("The Past 2 week"),
					"The Past 2 week is not displayed");
			Assert.assertTrue(response.contains("The Past 4 week"),
					"The Past 4 week is not displayed");
			Assert.assertTrue(response.contains("The Beginning of Time"),
					"The Beginning of Time is not displayed");

		} else {
			Assert.fail(" Get List Date Ranges is failed and status code is :"
					+ dateRanges.getStatus());
		}

	}

	public static void verifygetRatingConfigInfo() throws InterruptedException {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

		Response ratingConfig = null;
		Thread.sleep(2000);
		ratingConfig = crmPipeLineBizServiceConsumer.getRatingConfigInfo();

		if (ratingConfig.getStatus() == 200) {

			EngagementRuleConfig[] ruleConfigList = ratingConfig
					.readEntity(EngagementRuleConfig[].class);

			Assert.assertEquals(ruleConfigList.length, 6,
					"configuration details of rating types are not displayed");

			Assert.assertEquals(ruleConfigList[0].getLabel(),
					"Broadcast Communication",
					"rating types is Broadcast Communication not displayed ");

			Assert.assertEquals(ruleConfigList[1].getLabel(), "Compatibility",
					"rating types is Compatibility not displayed ");

			Assert.assertEquals(ruleConfigList[2].getLabel(),
					"Connect Factors",
					"rating types is Connect Factors not displayed ");

			Assert.assertEquals(ruleConfigList[3].getLabel(), "Awareness",
					"rating types is Awareness not displayed ");

			Assert.assertEquals(ruleConfigList[4].getLabel(),
					"Personal Communication",
					"rating types is Personal Communication not displayed ");

			Assert.assertEquals(ruleConfigList[5].getLabel(),
					"Interest Levels",
					"rating types is Interest Levels not displayed ");
			int totalScore = 0;
			for (int i = 0; i < ruleConfigList.length; i++) {

				totalScore = totalScore + ruleConfigList[i].getValue();
			}

			Assert.assertEquals(totalScore, 100, "Total score is not 100");

		} else {
			Assert.fail(" Get Rating info is failed and status code is :"
					+ ratingConfig.getStatus());
		}
	}

	public static void verifyCreateProfile(CreateCRM crmProfile)
			throws InterruptedException {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

		setUpCreateProfile(crmProfile);
		Response createResponse = null;
		Logging.info("Request Input is >>" + gson.toJson(crmProfile));
		createResponse = crmPipeLineBizServiceConsumer
				.createProfile(crmProfile);
		if (createResponse.getStatus() == 200) {

			verifyGetByProfileID(crmProfile);

		} else if (createResponse.getStatus() == 501) {

			Assert.assertNull(crmProfile.getStatusName(),
					"Profile status name is not null,but giving status >> "
							+ createResponse.getStatus());

		} else {
			Assert.fail(" Create Profile is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	private static void setUpCreateProfile(CreateCRM crmProfile) {
		crmProfile.setCandidateId(ProfileHelper.createProfileWithoutCRM());
		DataFactory datafactory = new DataFactory();
		crmProfile.setFirstName(datafactory.getFirstName());
		crmProfile.setLastName(datafactory.getLastName());
		crmProfile.setEmail(datafactory.getEmailAddress());
		crmProfile.setFirstName(datafactory.getFirstName());

		if ((crmProfile.getStatusName() != null)
				&& (crmProfile.getStatusName().equals("Hold") | crmProfile
						.getStatusName().equals("Rejected"))) {
			crmProfile.setStatusChangeReason("Spire Automation "
					+ crmProfile.getStatusName() + "Candidates");
		}

	}

	private static void verifyGetByProfileID(CreateCRM crmProfile)
			throws InterruptedException {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

		Response getResponse = null;

		Thread.sleep(3000);

		getResponse = crmPipeLineBizServiceConsumer.getProfileByID(crmProfile
				.getCandidateId());

		if (getResponse.getStatus() == 200) {

			CRM getProfile = getResponse.readEntity(CRM.class);

			compareProfiles(crmProfile, getProfile);

		} else {
			Assert.fail(" Create Profile is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	private static void compareProfiles(CreateCRM createProfile, CRM getProfile) {

		if (createProfile.getStatusName() == null) {
			createProfile.setStatusName("Lead");
		}
		Assert.assertEquals(createProfile.getCandidateId(),
				getProfile.getCandidateId(),
				"Descrpance betweenn Get and Created profile Ids");
		Assert.assertEquals(createProfile.getStatusName(),
				getProfile.getStatusName(),
				"Descrpance betweenn Get and Created Stage Name");

		int es = getProfile.getEngagementScore();
		Assert.assertEquals(es, 0,
				"Default Profile engagement score is not Zero");

	}

	public static void verifyUpdateProfile(CreateCRM crmProfile,
			SpireTestObject testObject, TestData data)
			throws InterruptedException {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

		setUpCreateProfile(crmProfile);
		Response createResponse = null;
		Thread.sleep(2000);
		createResponse = crmPipeLineBizServiceConsumer
				.createProfile(crmProfile);
		if (createResponse.getStatus() == 200) {

			if (!testObject.getTestTitle().equals("updateStageName_Sanity")) {
				setUPUpdateProfile(crmProfile, data);
				Thread.sleep(2000);
				Response response = crmPipeLineBizServiceConsumer
						.updatedProfile(crmProfile);

				if (response.getStatus() != 200) {
					Assert.fail("Update of pipeline profile is failed for the candidate "
							+ crmProfile.getCandidateId());
				}

			} else {

				crmPipeLineBizServiceConsumer.updateProfileStageName(
						crmProfile.getCandidateId(), data.getData(),
						crmProfile.getStatusName());
				crmProfile.setStatusName(data.getData());
			}
			verifyGetByProfileID(crmProfile);

		} else {
			Assert.fail(" Create Profile is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	private static void setUPUpdateProfile(CreateCRM crmProfile, TestData data) {

		DataFactory datafactory = new DataFactory();
		crmProfile.setFirstName(datafactory.getFirstName());
		crmProfile.setLastName(datafactory.getLastName());
		crmProfile.setEmail(datafactory.getEmailAddress());

	}

	public static void verifyBulkCreateProfile(CreateCRM crmProfile,
			TestData data) throws InterruptedException {

		String[] bulkCount = data.getData().split(":");

		int count = Integer.parseInt(bulkCount[1]);
		List<CreateCRM> bulkcreateList = new ArrayList<CreateCRM>();

		for (int i = 0; i < count; i++) {

			bulkcreateList.add(setUpBulkCreateProfile(crmProfile));

		}

		Response bulkResponse = crmPipeLineBizServiceConsumer
				.bulkCreateCRMProfiles(bulkcreateList);

		if (bulkResponse.getStatus() == 201) {

			for (int i = 0; i < count; i++) {

				verifyGetByProfileID(bulkcreateList.get(i));

			}

		} else {
			Assert.fail(" Bulk Create Profile is failed and status code is :"
					+ bulkResponse.getStatus());

		}

	}

	private static CreateCRM setUpBulkCreateProfile(CreateCRM crmProfile) {

		CreateCRM profile = new CreateCRM();
		DataFactory datafactory = new DataFactory();
		profile.setCandidateId(IdUtils.generateID("6001", "6005"));
		profile.setFirstName(datafactory.getFirstName());
		profile.setLastName(datafactory.getLastName());
		profile.setEmail(datafactory.getEmailAddress());
		profile.setStatusName(crmProfile.getStatusName());

		return profile;

	}

	public static void verifygetStageCount(CreateCRM crmProfile, TestData data)
			throws JsonParseException, JsonMappingException, IOException {

		Response getResponse = crmPipeLineBizServiceConsumer
				.getProfileByDateRange(data.getData());

		if (getResponse.getStatus() == 200) {

			String strResponse = getResponse.readEntity(String.class);
			Logging.log("Response of list is >>>" + strResponse);
			Assert.assertTrue(strResponse.contains("Lead"),
					"Lead is not found in the list filter Response");
			Assert.assertTrue(strResponse.contains("Applicant"),
					"Applicant is not found in the list filter Response");
			Assert.assertTrue(strResponse.contains("Engaged"),
					"Engaged is not found in the list filter Response");
			Assert.assertTrue(strResponse.contains("Hold"),
					"Hold is not found in the list filter Response");
			Assert.assertTrue(strResponse.contains("Rejected"),
					"Rejected is not found in the list filter Response");

			Assert.assertTrue(strResponse.contains("pipelinesSummary"),
					"pipelinesSummary is not found in the list filter Response");

			Assert.assertTrue(strResponse.contains("reasonsSummary"),
					"reasonsSummary is not found in the list filter Response");

			/*
			 * CRMFilterResponse pipelines = mapper.readValue(strResponse,
			 * CRMFilterResponse.class);
			 * 
			 * 
			 * List<Pipeline> listPipeline = (List<Pipeline>)
			 * pipelines.getSummary().getPipelinesSummary().getItems();
			 * 
			 * for (Pipeline pipeline : listPipeline) {
			 * 
			 * if (pipeline.getStatusName().equals("Lead")) {
			 * 
			 * Assert.assertTrue(pipeline.getCandidateCount() >= 0,
			 * "Lead count is not valid one ");
			 * 
			 * } else if (pipeline.getStatusName().equals("Engaged")) {
			 * Assert.assertTrue(pipeline.getCandidateCount() >= 0,
			 * "Lead count is not valid one ");
			 * 
			 * } else if (pipeline.getStatusName().equals("Applicant")) {
			 * 
			 * Assert.assertTrue(pipeline.getCandidateCount() >= 0,
			 * "Lead count is not valid one ");
			 * 
			 * } else if (pipeline.getStatusName().equals("Hold")) {
			 * 
			 * Assert.assertTrue(pipeline.getCandidateCount() >= 0,
			 * "Lead count is not valid one ");
			 * 
			 * } else if (pipeline.getStatusName().equals("Rejected")) {
			 * 
			 * Assert.assertTrue(pipeline.getCandidateCount() >= 0,
			 * "Lead count is not valid one ");
			 * 
			 * } else {
			 * 
			 * Assert.fail(" invalid stage name found  :" +
			 * pipeline.getStatusName());
			 * 
			 * }
			 */

		} else {
			Assert.fail(" get Stage count is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	public static void verifyPipelineFilter(CRMFilter crmFilter, TestData data) {

		setUPFilter(crmFilter, data);
		Response filterResponse = crmPipeLineBizServiceConsumer
				.listProfilesByFilters(crmFilter);

		if (filterResponse.getStatus() == 200) {

			CRMFilterResponse filteredProfiles = filterResponse
					.readEntity(CRMFilterResponse.class);

			verifyFilter(filteredProfiles, crmFilter);
			System.out.println(" <<<<>>>>" + filteredProfiles.getTotalCount());
		} else {
			Assert.fail(" Filter Profile is failed and status code is :"
					+ filterResponse.getStatus());

		}

	}

	private static void verifyFilter(CRMFilterResponse filteredProfiles,
			CRMFilter crmFilter) {

		if (filteredProfiles.getTotalCount() == 0) {
			Assert.assertNull(filteredProfiles.getCrmProfiles().getItems(),
					" count is zero but candidates are dispalyed in details ");
		} else {

			Assert.assertNotNull(filteredProfiles.getCrmProfiles().getItems(),
					" count is not zero, but candidates are not dispalyeds ");

			CollectionEntity<CRM> crmProfiles = filteredProfiles
					.getCrmProfiles();
			
			List<CRM> crmCandidates =  new ArrayList<CRM>(crmProfiles.getItems());
			
			
			for (CRM candiate : crmCandidates) {

				verifyCanidate(candiate, crmFilter);

			}
			verifyOrder(crmCandidates, crmFilter.getOrderIn());

		}

	}

	private static void verifyOrder(List<CRM> crmCandidates, String orderIn) {

		for (int i = 1; i < crmCandidates.size(); i++) {
			if (crmCandidates.get(i - 1).getDatemodified() != null) {
				ZonedDateTime zoneTime0 = ZonedDateTime.parse(crmCandidates
						.get(i - 1).getDatemodified());
				ZonedDateTime zoneTime1 = ZonedDateTime.parse(crmCandidates
						.get(i).getDatemodified());

				if (orderIn.equals("asc")) {

					if (zoneTime0.isAfter(zoneTime0)) {
						Assert.fail(" Candidates are not in asseding order ");

					}

				} else {

					if (zoneTime0.isBefore(zoneTime0)) {

						Assert.fail(" Candidates are not in asseding order ");

					}
				}
			}
		}

	}

	private static void verifyCanidate(CRM candiate, CRMFilter crmFilter) {

		Integer toScore = Integer.parseInt(crmFilter.getEngagementScoreFilter()
				.getScoreTo());
		Integer fromScrore = Integer.parseInt(crmFilter
				.getEngagementScoreFilter().getScroreFrom());

		Assert.assertTrue(fromScrore <= candiate.getEngagementScore()
				&& candiate.getEngagementScore() <= toScore,
				"Canidate engagement score is not between the given filter");

		CollectionEntity<String> stageNamesCollectionEntity  = crmFilter.getStatusNames();
		
		List<String> stageNames = new ArrayList<String>(stageNamesCollectionEntity.getItems());
		
		
		Boolean stageFlag = false;
		for (String stage : stageNames) {

			if (candiate.getStatusName().equals(stage)) {

				stageFlag = true;
			}

		}
		Assert.assertTrue(stageFlag,
				"Candidate found with a satge name that is not given in Filter"
						+ candiate.getCandidateId());

	}

	private static void setUPFilter(CRMFilter crmFilter, TestData data) {

		CollectionEntity<String> stageNames = new CollectionEntity<String>();

		String[] stastuNames = data.getData().split(",");

		for (String stage : stastuNames) {
			stageNames.addItem(stage);

		}

		crmFilter.setStatusNames( stageNames);

	}

	public static void verifygetProfilesByIDs(CreateCRM crmProfile,
			TestData data) {

		String[] spiltData = data.getData().split(":");

		int count = Integer.parseInt(spiltData[1]);
		List<String> candidateIDs = new ArrayList<String>();

		Response filterResponse = crmPipeLineBizServiceConsumer
				.listProfilesByFilters(null);

		if (filterResponse.getStatus() == 200) {
			String strResponse = filterResponse.readEntity(String.class);

			System.out.println("strResponse" + strResponse);

			CRMFilterResponse filteredProfiles = null;
			try {
				filteredProfiles = mapper.readValue(strResponse,
						CRMFilterResponse.class);
			} catch (IOException e) {

				e.printStackTrace();
			}

			CollectionEntity<CRM> crmCandidates = filteredProfiles
					.getCrmProfiles();

			List<CRM> crmprofiles = new ArrayList<CRM>(crmCandidates.getItems());

			for (int i = 0; i < count; i++) {

				if (crmprofiles.size() >= i) {
					candidateIDs.add(crmprofiles.get(i).getCandidateId());
				}

			}
			if (crmCandidates != null) {

				Response response = crmPipeLineBizServiceConsumer
						.getProfileByIDs(candidateIDs);

				List<CRM> getCandidates = response
						.readEntity(new GenericType<List<CRM>>() {
						});

				verifyProfilesByIDsResults(getCandidates, candidateIDs);

			}

		} else {
			Assert.fail(" Filter Profile is failed and status code is :"
					+ filterResponse.getStatus());

		}
	}

	private static void verifyProfilesByIDsResults(List<CRM> getCandidates,
			List<String> candidateIDs) {

		Assert.assertEquals(getCandidates.size(), candidateIDs.size(),
				"get count is not same as input count");

		Boolean candidateFlag = false;
		for (String id : candidateIDs) {

			for (CRM candidate : getCandidates) {

				if (candidate.getCandidateId().equals(id)) {
					candidateFlag = true;
				}

			}
			if (candidateFlag) {
				candidateFlag = false;
			} else {
				Assert.fail("Candiadte is not found in list :" + id);
			}

		}
	}

	public static void verifyPipelineList(CreateCRM crmProfile,
			SpireTestObject testObject) {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

		Response response = crmPipeLineBizServiceConsumer.getCrmStageList();

	}
}

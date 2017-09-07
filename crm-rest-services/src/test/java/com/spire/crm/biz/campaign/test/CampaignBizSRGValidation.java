package com.spire.crm.biz.campaign.test;

import java.io.IOException;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.commons.utils.IdUtils;
import spire.crm.biz.campaigns.beans.LeadCampaignVO;
import spire.crm.biz.campaigns.beans.ProfileCurrentNodeVO;
import spire.crm.biz.reports.beans.CRMCampaignReport;
import spire.crm.entity.campaign.beans.CampaignDetails;
import spire.crm.entity.campaign.beans.CampaignLeadVO;
import spire.crm.entity.campaign.beans.CampaignListSummary;
import spire.crm.entity.campaign.beans.CampaignNameVO;
import spire.crm.entity.campaign.entities.Campaign;
import spire.crm.entity.campaign.entities.CampaignEvent;
import spire.crm.entity.campaign.entities.Node;
import spire.crm.entity.campaign.entities.ResponseEntity;
import spire.crm.entity.com.entities.Email;
import spire.crm.profiles.bean.Profile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ConfigHelper;
import com.spire.common.EmailBizHelper;
import com.spire.common.PipeLineHelper;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.activity.biz.pojos.ActivityFilterResponse;
import com.spire.crm.activity.biz.pojos.ActivityTypeCount;
import com.spire.crm.activity.biz.pojos.CRMActivity;
import com.spire.crm.activity.biz.pojos.CRMActivityDetails;
import com.spire.crm.activity.biz.pojos.CRMHomeActivities;
import com.spire.crm.activity.biz.pojos.CRMHomeActivity;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.CampaignBizServiceConsumer;
import com.spire.crm.restful.entity.consumers.CampaignEntityServiceConsumer;

import crm.activitystream.beans.ActivitySearchFilter;

/**
 * @author Manikhanta Y
 *
 */
// extends CampaignHelper
public class CampaignBizSRGValidation {

	Gson gson = new Gson();
	DataFactory factory = new DataFactory();
	Random randomGenerator = new Random();
	CampaignBizServiceConsumer campaignBizServiceConsumer = new CampaignBizServiceConsumer();
	CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
	PipeLineHelper pipeLineHelper = new PipeLineHelper();
	EmailBizHelper emailBizHelper = new EmailBizHelper();
	Map<String, String> stageChangeMap = new HashMap<String, String>();

	public static String userId = ReadingServiceEndPointsProperties
			.getServiceEndPoint("userId");
	String userID2 = ReadingServiceEndPointsProperties
			.getServiceEndPoint("userId2");
	public static List<String> campaignIdsList = new ArrayList<String>();
	public static List<String> candidateIdsList = new ArrayList<String>();

	public static List<String> templateIdsList = new ArrayList<String>();

	ObjectMapper mapper = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule((Module) new JavaTimeModule());

	public Node setUpChangeStageNode(String parentNodeId, String properties,
			String decisionPath) {

		Node node = new Node();
		node.setId(IdUtils.generateUUID());
		node.setName("Change stage");
		node.setDescription("Change stage");
		node.setNodeType("Action");
		node.setEventType("stage_change");
		node.setParentNodeId(parentNodeId);
		node.setDecisionPath(decisionPath);
		node.setWaitTime(0);

		switch (properties) {
		case "Lead":
			node.setProperties("{\"fromStatus\":\"\",\"toStatus\":\"Lead\"}");
			break;

		case "Applicant":
			node.setProperties("{\"fromStatus\":\"\",\"toStatus\":\"Applicant\"}");
			break;
		case "Engaged":
			node.setProperties("{\"fromStatus\":\"\",\"toStatus\":\"Engaged\"}");
			break;
		}
		return node;

	}

	/*
	 * 
	 */
	public Node setUpReadEmailNode(String parentNodeId, int waitTime) {
		Node node = new Node();
		node.setId(IdUtils.generateUUID());
		node.setName("Read email");
		node.setDescription("Read email");
		node.setNodeType("Decision");
		node.setEventType("email_read");
		node.setParentNodeId(parentNodeId);
		node.setDecisionPath(null);
		node.setWaitTime(waitTime);
		return node;

	}

	public Node setUpRepliedEmailNode(String parentNodeId, int waitTime) {
		Node node = new Node();
		node.setId(IdUtils.generateUUID());
		node.setName("Replied email");
		node.setDescription("Replied email");
		node.setNodeType("Decision");
		node.setEventType("email_replied");
		node.setParentNodeId(parentNodeId);
		node.setDecisionPath(null);
		node.setWaitTime(waitTime);
		return node;

	}

	public Node setUpSendEmailNode(String parentNodeId, String decisionPath,
			String templateID) {

		Node node = new Node();
		node.setId(IdUtils.generateUUID());
		node.setName("Send email");
		node.setDescription("Send email");
		node.setNodeType("Action");
		node.setEventType("email_send");
		node.setParentNodeId(parentNodeId);
		node.setDecisionPath(decisionPath);
		node.setWaitTime(0);

		if (templateID == null) {

			if (templateIdsList.isEmpty()) {
				templateID = emailBizHelper.createEmailTemplate();
			} else {
				templateID = templateIdsList.get(0);
			}
		}

		templateIdsList.add(templateID);

		node.setProperties("{\"templateId\":\"" + templateID + "\"}");
		return node;

	}

	public Node setUpChangeCampaignsNode(String parentNodeId,
			String decisionPath, String properties) {

		Node node = new Node();
		node.setId(IdUtils.generateUUID());
		node.setName("Change campaigns");
		node.setDescription("Change campaigns");
		node.setNodeType("Action");
		node.setEventType("change_campaigns");
		node.setParentNodeId(parentNodeId);
		node.setDecisionPath(decisionPath);
		node.setWaitTime(0);
		node.setProperties(properties);
		return node;

	}

	public Campaign setUpCampaign(Boolean isPublished) {

		Campaign campaign = new Campaign();
		campaign.setFlowId(IdUtils.generateUUID());
		campaign.setName("SpireCampaign" + factory.getRandomWord()
				+ randomGenerator.nextInt(10000));
		campaign.setDescription("setUpCampaign");

		if (isPublished) {

			campaign.setIsPublished(isPublished);
			ZonedDateTime publishedFrom = ZonedDateTime.now(ZoneOffset.UTC)
					.minus(Period.ofDays(4));
			campaign.setPublishedFrom(publishedFrom);
			ZonedDateTime publishedTill = publishedFrom.plus(Period.ofYears(4));
			campaign.setPublishedTill(publishedTill);

		} else {
			campaign.setIsPublished(false);

		}
		campaign.setModifiedByName(factory.getFirstName());
		campaign.setCreatedByName(factory.getFirstName());

		return campaign;
	}

	public void validateCampaign(Campaign campaign, String projection) {

		Response getRespponse = campaignBizServiceConsumer.getCampaignID(
				campaign.getId(), projection);

		String strCampaign = getRespponse.readEntity(String.class);

		Campaign getCampaign = null;
		try {
			getCampaign = mapper.readValue(strCampaign, Campaign.class);
		} catch (IOException e) {

			e.printStackTrace();
		}

		compareCampaigns(campaign, getCampaign, null);

	}

	private void compareCampaigns(Campaign getCampaign, Campaign campaign,
			String projection) {

		Assert.assertTrue(campaign.getId().equals(getCampaign.getId()),
				"Found discrepancy in campaign id ");
		Assert.assertTrue(campaign.getName().equals(getCampaign.getName()),
				"Found discrepancy in campaign Name ");
		Assert.assertEquals(campaign.getCreatedByName(),
				getCampaign.getCreatedByName(),
				"Found discrepancy in Created By Name ");

		if (!projection.equals("editCampaign")) {
			Assert.assertEquals(campaign.getModifiedByName(),
					getCampaign.getModifiedByName(),
					"Found discrepancy in Modified By Name ");
		} else {

			Assert.assertNotEquals(campaign.getModifiedByName(),
					getCampaign.getModifiedByName(),
					"Found discrepancy in Modified By Name ");

		}

		Assert.assertEquals(campaign.getFlowId(), getCampaign.getFlowId(),
				"Found discrepancy in campaign flow id ");

		Assert.assertTrue(
				campaign.getNodes().size() == (getCampaign.getNodes().size()),
				"Found discrepancy in campaign id ");

		if (projection.equals("FULL")) {

			List<Node> inputNodes = campaign.getNodes();
			List<Node> ouputNodes = getCampaign.getNodes();

			int nodecount = 0;

			for (Node inputnode : inputNodes) {

				for (Node outputnode : ouputNodes) {

					if (IdUtils.getUUID(outputnode.getId()).equals(
							inputnode.getId().replace("-", ""))) {

						compareNodes(inputnode, outputnode);
						nodecount++;

					}

				}
			}

			Assert.assertTrue(nodecount == campaign.getNodes().size(),
					"few node id are not found in get campaign");

		}

	}

	private void compareNodes(Node inputnode, Node outputnode) {

		Logging.log("Comparing the nodes >>>" + inputnode.getId() + "and "
				+ outputnode.getId());

		Assert.assertTrue(
				inputnode.getEventType().equals(outputnode.getEventType()),
				"Found discrepancy in Event type ");

		Assert.assertTrue(inputnode.getName().equals(outputnode.getName()),
				"Found discrepancy in Name of the Node");

		if (inputnode.getParentNodeId() != null) {
			Assert.assertTrue(IdUtils.getUUID(outputnode.getParentNodeId())
					.equals(inputnode.getParentNodeId().replace("-", "")),
					"Found discrepancy in parent node id ");
		} else {

			if (outputnode.getParentNodeId() != null)
				Assert.fail("Found discrepancy in parent node id >>> one both are not same ");

		}

		if (inputnode.getProperties() != null) {
			Assert.assertTrue(
					inputnode.getProperties()
							.equals(outputnode.getProperties()),
					"Found discrepancy in Properties of node ");
		} else {
			if (outputnode.getProperties() != null)
				Assert.fail("Found discrepancy in parent node id >>>  both are not same ");

		}

		Assert.assertTrue(inputnode.getWaitTime() == outputnode.getWaitTime(),
				"Found discrepancy in tWaitTime of node ");

		if (inputnode.getDecisionPath() != null) {
			Assert.assertEquals(
					inputnode.getDecisionPath().compareTo(
							outputnode.getDecisionPath()), 0,
					"Both the decision value are not same");
		} else {
			if (outputnode.getDecisionPath() != null)
				Assert.fail("Found discrepancy in decision  >>>  both are not same ");

		}

		Assert.assertNull(outputnode.getLeadsAssociated(),
				"Leads associated is not null,at the time of creation of campaign ");

		Assert.assertNull(outputnode.getLeadsAssociatedList(),
				"Leads associated List is not null,at the time of creation of campaign ");

	}

	/*
	 * 
	 * Verifying the list campaign,we need to create a campaign
	 */
	public Campaign createCampaignWithAllNodes(Boolean isPublished,
			String stageName) {

		List<Node> nodeLIst = new ArrayList<Node>();
		// Stage change Node
		Node sendEmailNode01 = setUpSendEmailNode(null, null, null);
		nodeLIst.add(sendEmailNode01);

		// Read email node
		Node readEmailNode = setUpReadEmailNode(sendEmailNode01.getId(), 5);
		nodeLIst.add(readEmailNode);

		Node sendEmailNodeyes = setUpSendEmailNode(readEmailNode.getId(), "1",
				null);
		nodeLIst.add(sendEmailNodeyes);

		Node sendEmailNodeNo = setUpSendEmailNode(readEmailNode.getId(), "0",
				null);
		nodeLIst.add(sendEmailNodeNo);

		// replied Email Node
		Node repliedEmailNode = setUpRepliedEmailNode(sendEmailNodeyes.getId(),
				7);
		nodeLIst.add(repliedEmailNode);

		String properties = "\"{\"addTo\":[\"6000:6040:7122353ebaee40f9b1116d3a8d81346a\"],"
				+ "\"removeFrom\":[\"6000:6040:7122353ebaee40f9b1116d3a8d81346a\"]}\"";

		// Change campaignl Node
		Node changeCampNode = setUpChangeCampaignsNode(
				repliedEmailNode.getId(), "1", properties);
		nodeLIst.add(changeCampNode);

		// Stage change Node
		Node changesatgeNode = setUpChangeStageNode(repliedEmailNode.getId(),
				stageName, "0");
		nodeLIst.add(changesatgeNode);

		Campaign campaign = setUpCampaign(isPublished);
		campaign.setNodes(nodeLIst);
		Response createResponse = campaignBizServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());
			campaignIdsList.add(campaignId);
			campaign.setId(campaignId);
			return campaign;

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
		}
		return campaign;

	}

	/*
	 * 
	 * Verifying the list campaign,we need to create a campaign
	 */
	public String createCampaign(Boolean isPublished, String stageName) {
		List<Node> nodeLIst = new ArrayList<Node>();
		Node changesatgeNode = setUpChangeStageNode(null, stageName, null);
		nodeLIst.add(changesatgeNode);
		Campaign campaign = setUpCampaign(isPublished);
		campaign.setNodes(nodeLIst);
		Response createResponse = campaignBizServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());
			campaignIdsList.add(campaignId);
			Logging.log("Created Campaign ID is >>> " + campaignId);
			return campaignId;

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
		}
		return null;

	}

	public void verifyListCampaign(SpireTestObject testObject, TestData data) {

		Response listResponse = null;
		CampaignListSummary campaignListSummary = null;
		Boolean publishFlag = false;
		if (testObject.getTestData().equals("published")) {

			publishFlag = true;
		}

		String campaignID = createCampaign(publishFlag, "Lead");

		switch (data.getData()) {
		case "All":
			listResponse = campaignBizServiceConsumer.listCampaign(null, null,
					null);

			break;

		}

		if (listResponse.getStatus() == 200) {

			String strListSummary = listResponse.readEntity(String.class);

			try {

				campaignListSummary = mapper.readValue(strListSummary,
						CampaignListSummary.class);

				Assert.assertTrue(campaignListSummary.getTotalCount() > 0,
						"Campaign  list is zero expecetd is greatee than 0");

				validateListHelper(campaignListSummary, campaignID,
						data.getData());

			} catch (IOException e) {

				e.printStackTrace();
			}

		} else {
			Assert.fail("List campaigns is failed and status code is :"
					+ listResponse.getStatus());
		}

	}

	private void validateListHelper(CampaignListSummary campaignListSummary,
			String campaignID, String data) {

		Boolean campaignIdFlag = false;

		List<CampaignDetails> campaignDetailslist = campaignListSummary
				.getCampaignDetails();

		for (CampaignDetails campaignDetails : campaignDetailslist) {

			if (campaignDetails.getCampaignId().equals(campaignID)) {
				campaignIdFlag = true;
			}

		}

		Assert.assertTrue(campaignIdFlag,
				"Created campaignid is not displayed in campaign list");

	}

	public void verifyDeleteCampaign(TestData data) {

		String campaignID = null;

		if (data.getData().equals("Published")) {

			campaignID = createCampaign(true, "Lead");

		} else {
			campaignID = createCampaign(false, "Lead");
		}

		Response deleteResponse = campaignBizServiceConsumer
				.deleteCampaignById(campaignID);

		if (deleteResponse.getStatus() == 200) {

			ResponseEntity response = deleteResponse
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(response.getMessage(),
					"campaigns successfully deleted", " Invalid error message");

			Assert.assertEquals(response.getSuccess().get(campaignID),
					"Success", "Success message not display for campaign id ");

		} else {

			Assert.fail("Delete Campaign is failed and status code is :"
					+ deleteResponse.getStatus());
		}

	}

	public void verifyDeleteBulkCampaign(TestData data, Integer bulkcount) {

		List<String> campaignIds = new ArrayList<String>();
		String campaignID = null;

		if (data.getData().equals("Published")) {

			for (int i = 0; i < bulkcount; i++) {
				campaignID = createCampaign(true, "Lead");
				campaignIds.add(campaignID);
			}

		} else {

			for (int i = 0; i < bulkcount; i++) {
				campaignID = createCampaign(false, "Lead");
				campaignIds.add(campaignID);
			}
		}

		Response deleteResponse = campaignBizServiceConsumer
				.deleteBulkCampaignById(campaignIds);

		if (deleteResponse.getStatus() == 200) {

			ResponseEntity response = deleteResponse
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(response.getMessage(),
					"campaigns successfully deleted", " Invalid error message");

			Assert.assertEquals(response.getSuccess().get(campaignID),
					"Success", "Success message not display for campaign id ");

		} else {

			Assert.fail("Delete Campaign is failed and status code is :"
					+ deleteResponse.getStatus());
		}

	}

	public void verifygetCampaignByID(TestData data, SpireTestObject testObject) {

		String campaignId = null;
		String projection = testObject.getTestData();

		if (data.getData().equals("Published")) {

			campaignId = createCampaign(true, "Lead");

		} else {
			campaignId = createCampaign(true, "Lead");
		}
		Response geteResponse = campaignBizServiceConsumer.getCampaignID(
				campaignId, projection);

		if (geteResponse.getStatus() == 200) {

			String strResponse = geteResponse.readEntity(String.class);

			Campaign getCampaign = null;
			try {
				getCampaign = mapper.readValue(strResponse, Campaign.class);
			} catch (IOException e) {

				Assert.fail("Run time exception in Get campaign "
						+ e.getMessage());
				e.printStackTrace();
			}

			Assert.assertEquals(getCampaign.getId(), campaignId,
					"Created and get campaign are not samae");

			if (projection.equals("FULL")) {

				Assert.assertNotNull(getCampaign.getNodes());
			} else if (projection.equals("BASIC")) {

				Assert.assertNull(getCampaign.getNodes());
			}

		} else {

			Assert.fail("Get Campaign by id and projection is failed and status code is "
					+ geteResponse.getStatus());
		}

	}

	public void addCampToNoStageCandidate(TestData data,
			SpireTestObject testObject, String campaignId, String candidateID) {

		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();

		if (campaignId == null) {
			if (candidateIdsList.isEmpty()) {
				campaignId = createCampaign(true, "Lead");
				campaignIdsList.add(campaignId);
			} else {
				campaignId = campaignIdsList.get(0);
			}
		}

		if (candidateID == null) {

			candidateID = ProfileHelper.createProfile();

		}

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignBizServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			ResponseEntity response = addresponse
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(response.getMessage(),
					"All leads successfully added", " Invalid error message");

			Assert.assertEquals(data.getData(),
					pipeLineHelper.getstageName(candidateID),
					"Stage is not changed after adding the campaign node for candidate "
							+ candidateID);
			// stageChangeMap.put(campaignId, "Lead");

		} else {

			Assert.fail("Add no stage lead to a campaign is is failed and status code is :"
					+ addresponse.getStatus());
		}

		if (testObject.getTestTitle().equals("candidateCampaigns_Sanity")) {

			verifyCampaignUnderCandidate(data, testObject, campaignId,
					candidateID);

		}

		if (testObject.getTestTitle().equals("getLeadsForCampaign_SRG")) {

			verifyGetLeadsForCampaign(data, testObject, campaignId, candidateID);

		}

		if (testObject.getTestTitle().equals("getExecutedNodes_SRG")) {

			Response getexecutedResponse = campaignBizServiceConsumer
					.getExecutedNodes(candidateID, campaignId);

			String strResponse = getexecutedResponse.readEntity(String.class);

			Logging.log("Response  of get Executed Nodes>>> " + strResponse);
			if (getexecutedResponse.getStatus() != 302) {
				Assert.fail("get executed node is failed");
			}

		}

	}

	private void verifyGetLeadsForCampaign(TestData data,
			SpireTestObject testObject, String campaignId, String candidateID) {

		Response getLeadsResponse = campaignBizServiceConsumer.getLeadsAtNode(
				campaignId, null, "60", "0");

		if (getLeadsResponse.getStatus() == 200) {
			String str_Leads = getLeadsResponse.readEntity(String.class);

			Logging.log("getLeadsResponse Response is >>>> " + str_Leads);
			LeadCampaignVO leads = null;
			try {
				leads = mapper.readValue(str_Leads, LeadCampaignVO.class);
			} catch (IOException e) {

				e.printStackTrace();
				Assert.fail("Run time exception >>" + e.getStackTrace());
			}
			List<ProfileCurrentNodeVO> profile = leads.getCurrentNodeVOs();

			for (ProfileCurrentNodeVO profileCurrentNodeVO : profile) {
				Profile candidateProfile = profileCurrentNodeVO.getProfile();
				Assert.assertNotNull(candidateProfile.getCrm(),
						"CRM should not be null after adding camapaign to a Profile");
				Assert.assertNotNull(candidateProfile.getCandidate(),
						"Candidaet should not be null after adding camapaign to a Profile");
				Assert.assertEquals(candidateProfile.getCandidate().getId(),
						candidateID,
						"Added candidate is not found under campaign");
			}

			Assert.assertTrue(leads.getTotalCount() > 0,
					"Added candidate to the campaign but  count is not > 0");

		} else {
			Assert.fail(" get candidate under Campaign is failed and staus code is : "
					+ getLeadsResponse.getStatus());
		}

	}

	public void removeCampfromCandidat(TestData data, SpireTestObject testObject) {

		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaignWithAllNodes(true, "Lead").getId();

		String candidateID = null;
		if (candidateIdsList.isEmpty()) {
			candidateID = ProfileHelper.createProfile();
		} else {
			candidateID = candidateIdsList.get(0);
		}
		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignBizServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			ResponseEntity response = addresponse
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(response.getMessage(),
					"All leads successfully added", " Invalid error message");

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			Response removeResponse = campaignBizServiceConsumer
					.bulkRemoveLeadsFromCampaigns(campaignLeadVO);
			if (removeResponse.getStatus() == 200) {

				ResponseEntity response01 = removeResponse
						.readEntity(ResponseEntity.class);

				Assert.assertEquals(response01.getMessage(),
						"All leads successfully removed",
						" Invalid error message for remove candidate from campaign");

			} else {

				Assert.fail(" Remove  campaigns from Lead is failed and status code is :"
						+ removeResponse.getStatus());

			}

		} else {

			Assert.fail("Add no stage lead to a campaign is is failed and status code is :"
					+ addresponse.getStatus());
		}

	}

	public void verifyaddLeadToCampaign(TestData data,
			SpireTestObject testObject) {

		String campaignId = createCampaign(true, data.getData());
		String candidateID = ProfileHelper.createProfile();//
															// "6002:6005:7b15d113964e49879327475eeb3ddacb";

		Response addresponse = campaignBizServiceConsumer.addLeadToCampaigns(
				campaignId, candidateID);

		if (addresponse.getStatus() == 200) {
			ResponseEntity response = addresponse
					.readEntity(ResponseEntity.class);
			Assert.assertEquals(response.getCode(), 200, "Invalida status code");
			Assert.assertEquals(response.getMessage(),
					"All leads successfully added", " Invalid error message");

			executeCampaignEvents();

			Assert.assertEquals(data.getData(),
					pipeLineHelper.getstageName(candidateID),
					"Stage is not changed after excuting the campaign node for candidate "
							+ candidateID);

			// stageChangeMap.put(candidateID,"asd");// data.getData());

		} else {
			Assert.fail("Add candidate to campaign is failed and status code is : "
					+ addresponse.getStatus());

		}

	}

	public void verifyChangeStage(TestData data, SpireTestObject testObject) {

		String campaignId = createCampaign(true, data.getData());
		String candidateID = ProfileHelper.createProfile();//
															// "6002:6005:7b15d113964e49879327475eeb3ddacb";

		Response addresponse = campaignBizServiceConsumer.addLeadToCampaigns(
				campaignId, candidateID);

		if (addresponse.getStatus() == 200) {

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Assert.fail("Run time exception for Sleep ");
				e.printStackTrace();
			}

			executeCampaignEvents();

			Assert.assertEquals(data.getData(),
					pipeLineHelper.getstageName(candidateID),
					"Stage is not changed after excuting the campaign node for candidate "
							+ candidateID);
			// saving into hasmap and validate at the end
			// stageChangeMap.put(candidateID, data.getData());

		} else {
			Assert.fail("Add candidate to campaign is failed and status code is : "
					+ addresponse.getStatus());

		}

	}

	/*
	 * Campaign executor
	 * 
	 * Read the value from config and wait....
	 */

	public void executeCampaignEvents() {
		int waitTime = 2;// Time in min
		ConfigHelper config = new ConfigHelper();
		try {
			/*
			 * String cron_expression =
			 * config.getconfig("crm-biz","cron.expression"); waitTime = (int)
			 * cron_expression.charAt(4) - 48;
			 */
			waitTime = 1;
		} catch (Exception e) {
			waitTime = 2; // taking default value when config is down
		}
		Logging.log("campaign batch will execute every " + waitTime + " min");
		for (int i = 0; i <= waitTime * 30; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void verifyOneDecisionCampaign(TestData data,
			SpireTestObject testObject) {

		List<Node> nodeLIst = new ArrayList<Node>();

		Node node1SendEmail = setUpSendEmailNode(null, null, "templateID");

		String node2PaparentNodeId = node1SendEmail.getId();

		String node3PaparentNodeId = null;
		Node node2decision = null;
		switch (testObject.getTestData()) {

		case "RepliedEmail":
			node2decision = setUpReadEmailNode(node2PaparentNodeId, 7);

			break;
		default:
			node2decision = setUpReadEmailNode(node2PaparentNodeId, 5);

			break;

		}

		node3PaparentNodeId = node2decision.getId();
		Node node3yesSendEmail = setUpSendEmailNode(node3PaparentNodeId, "1",
				"templateID");

		String node4PaparentNodeId = node2decision.getId();

		Node node4NoSendEmail = setUpSendEmailNode(node4PaparentNodeId, "0",
				"templateID");

		nodeLIst.add(node1SendEmail);
		nodeLIst.add(node2decision);
		nodeLIst.add(node3yesSendEmail);
		nodeLIst.add(node4NoSendEmail);

		Campaign campaign = setUpCampaign(Boolean.valueOf(data.getData()));
		campaign.setNodes(nodeLIst);

		Response createResponse = campaignBizServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());

			campaign.setId(campaignId);

			/*
			 * get the created campaign as FULL
			 */

			Response geteResponse = campaignBizServiceConsumer.getCampaignID(
					campaignId, "FULL");

			if (geteResponse.getStatus() == 200) {

				String strResponse = geteResponse.readEntity(String.class);

				Campaign getCampaignObj = null;
				try {
					getCampaignObj = mapper.readValue(strResponse,
							Campaign.class);
				} catch (IOException e) {

					e.printStackTrace();
				}

				Assert.assertEquals(getCampaignObj.getId(), campaignId,
						"Created and get campaign are not samae");

				Assert.assertTrue(getCampaignObj.getNodes().size() == 4,
						"campaign is not created with 4 nodes");

				compareCampaigns(getCampaignObj, campaign, "FULL");

			} else {
				Assert.fail(" Campaign creation failed and staus code is : "
						+ createResponse.getStatus());
			}

		}
	}

	public Campaign createExecutionCampaign(TestData data,
			SpireTestObject testObject) {

		int waitTime = 0;
		List<Node> nodeLIst = new ArrayList<Node>();

		Node node1SendEmail = setUpSendEmailNode(null, null,
				"6002:6031:e8791c6cbbd345e1af681bc3c0424209");

		String node2PaparentNodeId = node1SendEmail.getId();

		String node3PaparentNodeId = null;
		Node node2decision = null;

		if (testObject.getTestTitle().equals("readYesNodeExecution_Sanity")
				|| testObject.getTestTitle().equals(
						"repliedYesNodeExecution_Sanity")) {
			waitTime = 5;
		}

		switch (testObject.getTestData()) {

		case "RepliedEmail":
			node2decision = setUpRepliedEmailNode(node2PaparentNodeId, waitTime);

			break;
		default:
			node2decision = setUpReadEmailNode(node2PaparentNodeId, waitTime);

			break;

		}

		node3PaparentNodeId = node2decision.getId();
		Node node3yesSendEmail = setUpSendEmailNode(node3PaparentNodeId, "1",
				"templateID");

		String node4PaparentNodeId = node3yesSendEmail.getId();

		Node node4NoSendEmail = setUpSendEmailNode(node3PaparentNodeId, "0",
				"templateID");

		nodeLIst.add(node1SendEmail);
		nodeLIst.add(node2decision);
		nodeLIst.add(node3yesSendEmail);
		nodeLIst.add(node4NoSendEmail);

		Campaign campaign = setUpCampaign(true);
		campaign.setNodes(nodeLIst);

		Response createResponse = campaignBizServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());
			campaign.setId(campaignId);
			return campaign;

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
			return null;
		}

	}

	public void verifyCampaignExecution(TestData data,
			SpireTestObject testObject) {
		Campaign campaign = createExecutionCampaign(data, testObject);
		String candidateID = ProfileHelper.createProfile();// "6002:6005:0a90bf69b07048c885ef5a392be8dc96";

		addCampToCandidate(data, testObject, campaign.getId(), candidateID);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		List<Node> campaignNodes = campaign.getNodes();

		// for (Node node : campaignNodes) {

		for (int i = 0; i < 3; i++) {

			executeCampaignNode(campaign, candidateID);

		}

	}

	private void addCampToCandidate(TestData data, SpireTestObject testObject,
			String campaignId, String candidateID) {

		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignBizServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			ResponseEntity response = addresponse
					.readEntity(ResponseEntity.class);

			Assert.assertEquals(response.getMessage(),
					"All leads successfully added", " Invalid error message");

			Assert.assertEquals(pipeLineHelper.getstageName(candidateID),
					"Lead", "Success message not display for campaign id ");

		} else {

			Assert.fail("Add no stage lead to a campaign is is failed and status code is :"
					+ addresponse.getStatus());
		}

	}

	private List<CampaignEvent> getScheduledEvents() {

		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();

		Response eventsResponse = campaignEntityServiceConsumer
				.getScheduledEvents();

		if (eventsResponse.getStatus() == 302) {

			String strEvents = eventsResponse.readEntity(String.class);
			List<CampaignEvent> scheduledEvents = null;

			try {
				scheduledEvents = mapper.readValue(

				strEvents, new TypeReference<List<CampaignEvent>>() {
				});
			} catch (IOException e) {

				e.printStackTrace();
			}

			return scheduledEvents;

		} else {

			Assert.fail("expected campaign event is not found in the list");
		}
		return null;

	}

	private void yesPathExecuteCampaignNode(Campaign campaign,
			String candidateId) {

		Boolean executionFalg = true;

		List<CampaignEvent> beforescheduledEvents = getScheduledEvents();

		Assert.assertTrue(beforescheduledEvents.size() > 0,
				"Expected sheduled events,but not found");

		for (CampaignEvent campaignEvent : beforescheduledEvents) {

			if (campaign.getId().equals(campaignEvent.getCampaignId())) {

				String nodeid = campaignEvent.getNodeId();
				String metadata = campaignEvent.getMetadata();
				campaignEntityServiceConsumer.executeCampaignEvents();
				executionFalg = true;
				verifyActivityStream(candidateId, metadata);

			}

		}
		Assert.assertTrue(executionFalg,
				"expected campaign event is not found in the list");

	}

	private void verifyActivityStream(String candidateID, String metadata) {

		Boolean activityFlag = true;
		String messageId = null;

		ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();
		CRMHomeActivities homeActivitiesList = activityStreamBizServiceConsumer
				.getHomeEmailActivities();
		List<com.spire.crm.activity.biz.pojos.CRMHomeActivity> crmHomeActivity = homeActivitiesList
				.getHomeActivities();

		for (CRMHomeActivity activity : crmHomeActivity) {

			String toId = activity.getActivity().getObject().getDetail()
					.getTo();

			if (toId != null && toId.equals(candidateID)) {
				activityFlag = true;
				messageId = activity.getActivity().getObject().getId();
				Logging.log("Message id is >>> " + messageId);
				Logging.info("Message id is >>> " + messageId);
				if (messageId.equals(metadata)) {
					// emailBizHelper.markEmailasRead(messageId);
				} else {

					verifyMessageDeatils(candidateID, messageId, metadata);
				}

				break;

			}

		}

		Assert.assertTrue(activityFlag, "Activity not created for send email");

	}

	private void verifyMessageDeatils(String candidateID, String messageId,
			String metadata) {

		Email messagedetails = emailBizHelper.getMessageDeatils(messageId);

		Assert.assertEquals(messagedetails.getEmailTo(), candidateID,
				"Found descripancy in candidate id and to id");

		if (!metadata.contains(messagedetails.getEmailTemplateId())) {

			Assert.fail("Sent template id is not found in message details");

		} else {

			emailBizHelper.markEmailasRead(messageId);
		}

	}

	private void executeCampaignNode(Campaign campaign, String candidateId) {

		Boolean executionFalg = true;

		List<CampaignEvent> beforescheduledEvents = getScheduledEvents();

		Assert.assertTrue(beforescheduledEvents.size() > 0,
				"Expected sheduled events,but not found");

		for (CampaignEvent campaignEvent : beforescheduledEvents) {

			if (campaign.getId().equals(campaignEvent.getCampaignId())) {

				String nodeid = campaignEvent.getNodeId();
				String metadata = campaignEvent.getMetadata();
				campaignEntityServiceConsumer.executeCampaignEvents();

				List<CampaignEvent> afterExecutionEvents = getScheduledEvents();

				for (CampaignEvent campaignEvent2 : afterExecutionEvents) {

					if (nodeid.equals(campaignEvent2.getNodeId())) {

						Assert.fail("Node is not executed");
						executionFalg = false;
					} else {
						if (metadata != null) {

							verifyActivityStream(candidateId, metadata);
						}
					}

				}

				if (metadata != null) {

					verifyActivityStream(candidateId, metadata);
				}

			} else {

				Assert.fail("expected campaign event is not found in the list");
			}

		}
	}

	public void decisionYesExecution(TestData data, SpireTestObject testObject) {
		Campaign campaign = createExecutionCampaign(data, testObject);
		String candidateID = ProfileHelper.createProfile();// "6002:6005:0a90bf69b07048c885ef5a392be8dc96";

		addCampToCandidate(data, testObject, campaign.getId(), candidateID);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		List<Node> campaignNodes = campaign.getNodes();

		// for (Node node : campaignNodes) {

		for (int i = 0; i < 3; i++) {

			yesPathExecuteCampaignNode(campaign, candidateID);

		}

	}

	public void verifySentActivity(TestData data, SpireTestObject testObject) {

		List<Node> nodeLIst = new ArrayList<Node>();
		Node sendEmail = setUpSendEmailNode(null, null, null);
		nodeLIst.add(sendEmail);
		Campaign campaign = setUpCampaign(true);
		campaign.setNodes(nodeLIst);

		Response createResponse = campaignBizServiceConsumer
				.createCampaign(campaign);

		String campaignId = null;
		if (createResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());
			campaign.setId(campaignId);
			campaignIdsList.add(campaignId);

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
		}

		String candidateID = ProfileHelper
				.createProfile("crmvista.services@gmail.com");
		candidateIdsList.add(candidateID);

		addCampToCandidate(data, testObject, campaignId, candidateID);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		executeCampaignEvents();

		verifyCandidateActivityStream(candidateID);

	}

	void verifyCandidateActivityStream(String candidateID) {

		ActivitySearchFilter activitySearchFilter = new ActivitySearchFilter();
		activitySearchFilter.setTimePeriod("null");
		List<String> activityTypes = new ArrayList<String>();
		activityTypes.add("Email");
		activitySearchFilter.setActivityTypes(activityTypes);

		Logging.log("getHomeEmailActivities request input >> "
				+ gson.toJson(activitySearchFilter));

		ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();

		Response response = activityStreamBizServiceConsumer
				.getCandidateActivities(candidateID, activitySearchFilter);

		String stringResponse = response.readEntity(String.class);

		System.out.println("stringResponse" + stringResponse);
		ObjectMapper mapper = new ObjectMapper().configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule((Module) new JavaTimeModule());

		ActivityFilterResponse candidateActivityList = null;

		try {
			candidateActivityList = mapper.readValue(stringResponse,
					ActivityFilterResponse.class);
		} catch (IOException e) {
			Assert.fail("Run time exception >>> " + e.getMessage());
			e.printStackTrace();
		}
		List<ActivityTypeCount> activityTypeCountSummary = candidateActivityList
				.getActivityTypeCountSummary();
		for (ActivityTypeCount activityTypeCount : activityTypeCountSummary) {

			if (activityTypeCount.getActivityTypeName().equals("Email")) {

				Assert.assertNotEquals(activityTypeCount.getCount(), 0,
						"Activity count is not expected as zero for type "
								+ activityTypeCount.getActivityTypeName());

			} else {
				Assert.assertEquals(activityTypeCount.getCount(), 0,
						"Activity count is expected as zero for type "
								+ activityTypeCount.getActivityTypeName());
			}
		}

		List<CRMActivity> emailActivities = candidateActivityList
				.getCrmActivities();

		for (CRMActivity crmActivity : emailActivities) {

			Assert.assertEquals(crmActivity.getObjectInfo().getObjectType()
					.toString(), "EMAIL", "Object type is not expected");
			String messageID = crmActivity.getObjectInfo().getId();
			Logging.log("Message ID for created email activity is " + messageID);

			List<CRMActivityDetails> crmActivityDetails = crmActivity
					.getActivityDetailsList();

			for (CRMActivityDetails activityDetails : crmActivityDetails) {

				if (activityDetails.getAction().toString().equals("SENT")) {
					Assert.assertEquals(activityDetails.getTarget().getId(),
							candidateID, "Target is not candidate ID");

					Assert.assertEquals(activityDetails.getActor().getId(),
							userId, "Actor is not batch user ID");
				} else if (activityDetails.getAction().toString()
						.equals("READ")) {

					Assert.assertEquals(activityDetails.getActor().getId(),
							candidateID, "Actor is not candidate ID");

					Assert.assertEquals(activityDetails.getTarget().getId(),
							userId, "Target is not batch user ID");
				} else {
					Assert.fail("Fount invalid Action Type"
							+ activityDetails.getAction());
				}

			}

		}

	}

	public void verifyCampaignCandidates(TestData data,
			SpireTestObject testObject) throws JsonParseException,
			JsonMappingException, IOException {

		String nodeId = null;
		String campaignId = createCampaign(true, "Lead");
		String candidateId = ProfileHelper.createProfile();
		addCampToCandidate(data, testObject, campaignId, candidateId);
		Response getLeadsResponse = campaignBizServiceConsumer
				.getLeadsForCampaign(campaignId, "10", "0");

		if (getLeadsResponse.getStatus() == 200) {
			String str_Leads = getLeadsResponse.readEntity(String.class);

			Logging.log("getLeadsResponse Response is >>>> " + str_Leads);
			LeadCampaignVO leads = mapper.readValue(str_Leads,
					LeadCampaignVO.class);
			List<ProfileCurrentNodeVO> profile = leads.getCurrentNodeVOs();

			for (ProfileCurrentNodeVO profileCurrentNodeVO : profile) {
				Profile candidateProfile = profileCurrentNodeVO.getProfile();
				nodeId = profileCurrentNodeVO.getNodeDetails().getId();
				Assert.assertNotNull(candidateProfile.getCrm(),
						"CRM should not be null after adding camapaign to a Profile");
				Assert.assertNotNull(candidateProfile.getCandidate(),
						"Candidaet should not be null after adding camapaign to a Profile");
				Assert.assertEquals(candidateProfile.getCandidate().getId(),
						candidateId,
						"Added candidate is not found under campaign");
			}

			Assert.assertTrue(leads.getTotalCount() > 0,
					"Added candidate to the campaign but  count is not > 0");

		} else {
			Assert.fail(" get candidate under Campaign is failed and staus code is : "
					+ getLeadsResponse.getStatus());
		}

		if (testObject.getTestTitle().equals("candidatesAtNode_SRG")) {

			verifyCandidatesAtNode(campaignId, nodeId, candidateId);

		}

	}

	private void verifyCandidatesAtNode(String campaignId, String nodeId,
			String candidateId) throws JsonParseException,
			JsonMappingException, IOException {

		Response getLeadsResponse = campaignBizServiceConsumer.getLeadsAtNode(
				campaignId, nodeId, "60", "0");

		if (getLeadsResponse.getStatus() == 200) {
			String str_Leads = getLeadsResponse.readEntity(String.class);

			Logging.log("getLeadsResponse Response is >>>> " + str_Leads);
			LeadCampaignVO leads = mapper.readValue(str_Leads,
					LeadCampaignVO.class);
			List<ProfileCurrentNodeVO> profile = leads.getCurrentNodeVOs();

			for (ProfileCurrentNodeVO profileCurrentNodeVO : profile) {
				Profile candidateProfile = profileCurrentNodeVO.getProfile();
				nodeId = profileCurrentNodeVO.getNodeDetails().getId();
				Assert.assertNotNull(candidateProfile.getCrm(),
						"CRM should not be null after adding camapaign to a Profile");
				Assert.assertNotNull(candidateProfile.getCandidate(),
						"Candidaet should not be null after adding camapaign to a Profile");
				Assert.assertEquals(candidateProfile.getCandidate().getId(),
						candidateId,
						"Added candidate is not found under campaign");
			}

			Assert.assertTrue(leads.getTotalCount() > 0,
					"Added candidate to the campaign but  count is not > 0");

		} else {
			Assert.fail(" get candidate under Campaign is failed and staus code is : "
					+ getLeadsResponse.getStatus());
		}

	}

	public void verifyCloneCampaign(TestData data, SpireTestObject testObject) {

		boolean publishFlag = false;

		if (testObject.getTestData().equals("published")) {

			publishFlag = true;
		}

		String campaignId = createCampaignWithAllNodes(publishFlag, "Lead")
				.getId();

		String cloneCampaignName = "SpireCampaign" + factory.getRandomWord()
				+ randomGenerator.nextInt(10000);
		String flowID = cloneCampaignName;
		Response cloneResponse = campaignBizServiceConsumer.cloneCampaign(
				campaignId, cloneCampaignName, flowID);

		if (cloneResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = cloneResponse
					.readEntity(ResponseEntity.class);
			campaignId = createResponseEntity.getSuccess().get(
					cloneCampaignName);
			Logging.log(" Cloned campaign Id >> " + campaignId);
			Assert.assertEquals(createResponseEntity.getMessage(),
					"successfully created campaign " + cloneCampaignName,
					"Invalid error message displayed in Respomnse");
			Assert.assertEquals(createResponseEntity.getCode(), 201,
					"Invalid error code displayed in Respomnse");

			Response geteResponse = campaignBizServiceConsumer.getCampaignID(
					campaignId, "FULL");

			if (geteResponse.getStatus() == 200) {

				String strResponse = geteResponse.readEntity(String.class);

				Campaign getCampaign = null;
				try {
					getCampaign = mapper.readValue(strResponse, Campaign.class);
				} catch (IOException e) {

					e.printStackTrace();
				}

				ZonedDateTime currentdate = ZonedDateTime.now(ZoneOffset.UTC)
						.plusMinutes(5);

				Assert.assertEquals(getCampaign.getId(), campaignId,
						"Created and get campaign are not samae");
				Assert.assertTrue(
						getCampaign.getPublishedFrom().isBefore(currentdate),
						"PublishedFrom date is not expected "
								+ getCampaign.getPublishedFrom());
				Assert.assertTrue(
						getCampaign.getPublishedTill().isBefore(currentdate),
						"PublishedTill date is not expected "
								+ getCampaign.getPublishedTill());

				Assert.assertNotNull(getCampaign.getNodes());
				Assert.assertFalse(getCampaign.getIsPublished(),
						"Cloned campaign should be unpublished");

				verifyNodePercenatage(getCampaign);

			} else {

				Assert.fail("Get Campaign by id and projection is failed and status code is "
						+ geteResponse.getStatus());
			}

		} else {
			Assert.fail(" get candidate under Campaign is failed and staus code is : "
					+ cloneResponse.getStatus());
		}

	}

	public CRMCampaignReport verifyCampaignReports(TestData data,
			SpireTestObject testObject) {

		Response reportResponse = campaignBizServiceConsumer
				.getCampaignReport();
		CRMCampaignReport reportSummary = null;
		if (reportResponse.getStatus() == 200) {

			reportSummary = reportResponse.readEntity(CRMCampaignReport.class);
			Logging.log("Report Response is >>>>" + gson.toJson(reportSummary));
			Assert.assertNotNull(reportSummary);
			return reportSummary;

		} else {

			Assert.fail("Get campaign Reports is failed and status code is "
					+ reportResponse.getStatus());
			return reportSummary;

		}

	}

	public void deleteCampaigns() {

		// campaignBizServiceConsumer.deleteBulkCampaignById(campaignIdsList);

	}

	public void verifycreateCampaign(SpireTestObject testObject, TestData data) {

		String projection = testObject.getTestData();
		Campaign createdCampaign = null;

		if (data.getData().equals("Published")) {

			createdCampaign = createCampaignWithAllNodes(true, "Lead");

		} else {
			createdCampaign = createCampaignWithAllNodes(false, "Lead");
		}
		Response geteResponse = campaignBizServiceConsumer.getCampaignID(
				createdCampaign.getId(), projection);

		if (geteResponse.getStatus() == 200) {

			String strResponse = geteResponse.readEntity(String.class);

			Campaign getCampaign = null;
			try {
				getCampaign = mapper.readValue(strResponse, Campaign.class);
			} catch (IOException e) {

				Assert.fail("Run time exception in Get campaign "
						+ e.getMessage());
				e.printStackTrace();
			}

			compareCampaigns(getCampaign, createdCampaign, projection);
			verifyNodePercenatage(getCampaign);
		} else {

			Assert.fail("Get Campaign by id and projection is failed and status code is "
					+ geteResponse.getStatus());
		}

	}

	private void verifyNodePercenatage(Campaign getCampaign) {
		Logging.log(">>>>Started Node Percentage verification<<<<");
		List<Node> nodeList = getCampaign.getNodes();
		for (Node node : nodeList) {

			Logging.log(">>>> Node ID  verification<<<<" + node.getId());
			if (node.getEventType().equals("stage_change"))
				Assert.assertEquals(node.getPositionPercentage(), "100",
						"for stage change leaf node completion % is not 100");

			if (node.getEventType().equals("change_campaigns"))
				Assert.assertEquals(node.getPositionPercentage(), "100",
						"For  change campaigns leaf node completion % is not 100");

			if (node.getEventType().equals("email_send"))

				try {

					if (node.getDecisionPath() == "1") {

						Assert.assertEquals(node.getPositionPercentage(),
								"60.0",
								"For  send email middele node completion % is not 60.0");
					} else {
						if (node.getDecisionPath() == "0") {
							Assert.assertEquals(node.getPositionPercentage(),
									"100",
									"For  send email leaf node completion % is not 100");

						}
					}

				} catch (NullPointerException e) {

					Assert.assertEquals(node.getPositionPercentage(), "20.0",
							"start send email node completion % is not 20");

				}

		}

	}

	public Campaign editCampaign(SpireTestObject testObject, TestData data) {

		String projection = testObject.getTestData();

		Campaign getCampaign = null;
		Campaign createdCampaign = null;

		if (data.getData().equals("Published")) {

			createdCampaign = createCampaignWithAllNodes(true, "Lead");

		} else {
			createdCampaign = createCampaignWithAllNodes(false, "Lead");
		}
		Response geteResponse = campaignBizServiceConsumer.getCampaignID(
				createdCampaign.getId(), projection);

		if (geteResponse.getStatus() == 200) {

			String strResponse = geteResponse.readEntity(String.class);

			try {
				getCampaign = mapper.readValue(strResponse, Campaign.class);
			} catch (IOException e) {

				Assert.fail("Run time exception in Get campaign "
						+ e.getMessage());
				e.printStackTrace();
			}

			editCampaignWithAllNodes(createdCampaign);

			return createdCampaign;
		} else {

			Assert.fail("Get Campaign by id and projection is failed and status code is "
					+ geteResponse.getStatus());

		}

		return getCampaign;
	}

	public void verifyeditCampaign(SpireTestObject testObject, TestData data) {
		String projection = testObject.getTestData();
		Campaign editCampaign = editCampaign(testObject, data);

		Response editResponse = campaignBizServiceConsumer
				.updateCampaign(editCampaign);

		if (editResponse.getStatus() == 200) {

			ResponseEntity responseEntity = editResponse
					.readEntity(ResponseEntity.class);
			String campaignId = responseEntity.getSuccess().get(
					editCampaign.getName());
			Logging.log("edit ResponseEntity >>> "
					+ gson.toJson(responseEntity));
			Assert.assertEquals(campaignId, editCampaign.getId(),
					"After edit campaign id is changed");

			Assert.assertEquals(responseEntity.getMessage(),
					"successfully updated campaign " + editCampaign.getName(),
					"After edit campaign id is changed");

			Response geteResponse = campaignBizServiceConsumer.getCampaignID(
					editCampaign.getId(), projection);

			if (geteResponse.getStatus() == 200) {

				String strResponse = geteResponse.readEntity(String.class);

				Campaign getCampaign = null;
				try {
					getCampaign = mapper.readValue(strResponse, Campaign.class);
				} catch (IOException e) {

					Assert.fail("Run time exception in Get campaign "
							+ e.getMessage());
					e.printStackTrace();
				}

				compareCampaigns(getCampaign, editCampaign, "editCampaign");
				verifyNodePercenatage(getCampaign);
			} else {

				Assert.fail("Get Campaign by id and projection is failed and status code is "
						+ geteResponse.getStatus());
			}

		} else {

			Assert.fail("edit Campaign is failed and status code is "
					+ editResponse.getStatus());

		}

	}

	private void editCampaignWithAllNodes(Campaign editCampaign) {

		editCampaign.setFlowId(IdUtils.generateUUID());
		editCampaign.setName("SpireCampaign" + "Edit" + factory.getRandomWord()
				+ randomGenerator.nextInt(10000));
		String changeParentId = null;
		List<Node> nodeList = editCampaign.getNodes();
		for (Node node : nodeList) {

			if (node.getEventType().equals("stage_change"))

				node.setDecisionPath("1");

			if (node.getEventType().equals("change_campaigns"))
				node.setDecisionPath("0");

			if (node.getEventType().equals("email_send"))

				try {

					if (node.getDecisionPath() == "1") {
						node.setDecisionPath("0");

					} else {
						if (node.getDecisionPath() == "0") {
							node.setDecisionPath("1");
							changeParentId = node.getId();

						}
					}

				} catch (NullPointerException e) {

					Assert.assertTrue(true,
							"Expected null pointet exception for boolean value");

				}

		}

		for (Node node : nodeList) {

			if (node.getEventType().equals("email_replied")) {

				node.setParentNodeId(changeParentId);
			}

		}
	}

	public void deleteTemplate() {

	}

	public void validateStageNames() {

		for (Entry<String, String> entry : stageChangeMap.entrySet()) {

			String expectedStageName = entry.getValue();

			String candidateId = entry.getKey();
			Logging.log("Validated stage name for the candidate" + candidateId
					+ "and satge name is " + expectedStageName);
			Assert.assertEquals(expectedStageName,
					pipeLineHelper.getstageName(candidateId),
					"Stage is not changed after excuting the campaign node for candidate "
							+ candidateId);

		}

	}

	public void verifyCampaignUnderCandidate(TestData data,
			SpireTestObject testObject, String campaignId, String candidateID) {

		Response getCampaignsresponse = campaignBizServiceConsumer
				.getCampaignForLead(candidateID);

		Boolean status = false;

		if (getCampaignsresponse.getStatus() == 200) {

			String strResponse = getCampaignsresponse.readEntity(String.class);

			Logging.log("Response of getCampaignForLead is >>> " + strResponse);

			try {
				List<CampaignNameVO> response = mapper.readValue(strResponse,
						new TypeReference<List<CampaignNameVO>>() {
						});

				for (CampaignNameVO campaignNameVO : response) {
					if (campaignNameVO.getCampaignId().equals(campaignId))
						status = true;

				}
			} catch (IOException e) {
				Assert.fail("Run time IO Exception " + e.getMessage());
				e.printStackTrace();
			}

			Assert.assertTrue(status,
					"expected campaign is not Found for candidate :"
							+ candidateID + "campaign is >>" + campaignId);

		} else {

			Assert.fail("Get campaigns for candidate is failed and status code is :"
					+ getCampaignsresponse.getStatus());
		}

	}

	public void verifyFrezeCampaign(TestData data, SpireTestObject testObject) {

		boolean publishFlag = false;

		if (testObject.getTestData().equals("published")) {

			publishFlag = true;
		}

		String campaignId = createCampaignWithAllNodes(publishFlag, "Lead")
				.getId();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		Response freezeResponse = campaignBizServiceConsumer
				.freezeCampaign(campaignIds);

		if (freezeResponse.getStatus() == 200) {

			ResponseEntity freezeResponseEntity = freezeResponse
					.readEntity(ResponseEntity.class);

			Logging.log(" Response of freeze campaign is  >> "
					+ gson.toJson(freezeResponseEntity));

			Assert.assertEquals(freezeResponseEntity.getMessage(),
					"campaigns successfully updated",
					"Invalid error message displayed in Respomnse");
			Assert.assertEquals(freezeResponseEntity.getCode(), 200,
					"Invalid error code displayed in Respomnse");

			Response geteResponse = campaignBizServiceConsumer.getCampaignID(
					campaignId, "BASIC");

			if (geteResponse.getStatus() == 200) {

				String strResponse = geteResponse.readEntity(String.class);

				Campaign getCampaign = null;
				try {
					getCampaign = mapper.readValue(strResponse, Campaign.class);
				} catch (IOException e) {

					e.printStackTrace();
				}

				Assert.assertTrue(getCampaign.getIsFreeze(),
						"Campaign is not freezed after freezing");

				if (testObject.getTestTitle().equals("unfreezeCampaign_Sanity")) {
					verifyunfreezeCampaign(campaignIds);
				}

			} else {

				Assert.fail("Get Campaign by id and projection Basic is failed and status code is "
						+ geteResponse.getStatus());
			}

		} else {
			Assert.fail(" Freeze Campaign is failed and staus code is : "
					+ freezeResponse.getStatus());
		}

	}

	private void verifyunfreezeCampaign(List<String> campaignIds) {

		Response unfreezeResponse = campaignBizServiceConsumer
				.unFreezeCampaign(campaignIds);

		if (unfreezeResponse.getStatus() == 200) {

			ResponseEntity unfreezeResponseEntity = unfreezeResponse
					.readEntity(ResponseEntity.class);

			Logging.log(" Response of unfreeze campaign is  >> "
					+ gson.toJson(unfreezeResponseEntity));

			Assert.assertEquals(unfreezeResponseEntity.getMessage(),
					"campaigns successfully updated",
					"Invalid error message displayed in Respomnse");
			Assert.assertEquals(unfreezeResponseEntity.getCode(), 200,
					"Invalid error code displayed in Respomnse");

			Response geteResponse = campaignBizServiceConsumer.getCampaignID(
					campaignIds.get(0), "BASIC");

			if (geteResponse.getStatus() == 200) {

				String strResponse = geteResponse.readEntity(String.class);

				Campaign getCampaign = null;
				try {
					getCampaign = mapper.readValue(strResponse, Campaign.class);
				} catch (IOException e) {

					e.printStackTrace();
				}

				Assert.assertFalse(getCampaign.getIsFreeze(),
						"Campaign is not un freezed after un freezing");

			} else {

				Assert.fail("Get Campaign by id and projection Basic is failed and status code is "
						+ geteResponse.getStatus());
			}

		} else {
			Assert.fail(" Un Freeze Campaign is failed and staus code is : "
					+ unfreezeResponse.getStatus());
		}

	}

	public void changeStageUserName(TestData data, SpireTestObject testObject)
			throws JsonParseException, JsonMappingException, IOException {
		String userName = ReadingServiceEndPointsProperties
				.getServiceEndPoint("loginId2");
		String password = ReadingServiceEndPointsProperties
				.getServiceEndPoint("password");
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaign(true, "Applicant");
		String candidateID = ProfileHelper.createProfile();
		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);
		CampaignBizServiceConsumer client = new CampaignBizServiceConsumer(
				userName, password);
		Response addresponse = client.bulkAddLeadToCampaigns(campaignLeadVO);
		if (addresponse.getStatus() == 200) {
			executeCampaignEvents();
			List<String> activityTypes = new ArrayList<String>();
			activityTypes.add("CRM Stages");
			getCandidateActivites(candidateID, activityTypes);
		} else {
			Assert.fail("Add candidate to campaign is failed and status code is >> "
					+ addresponse.getStatus());
		}
	}

	public void getCandidateActivites(String candidateId,
			List<String> activityTypes) throws JsonParseException,
			JsonMappingException, IOException {
		ActivityStreamBizServiceConsumer client = new ActivityStreamBizServiceConsumer();
		ActivitySearchFilter activitySearchFilter = new ActivitySearchFilter();
		activitySearchFilter.setTimePeriod("null");
		activitySearchFilter.setActivityTypes(activityTypes);
		Response response = client.getCandidateActivities(candidateId,
				activitySearchFilter);
		if (response.getStatus() == 200) {
			String stringResponse = response.readEntity(String.class);
			Logging.log("activity stream response   " + stringResponse);
			ObjectMapper mapper = new ObjectMapper().configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.registerModule((Module) new JavaTimeModule());
			ActivityFilterResponse candidateActivityList = null;
			candidateActivityList = mapper.readValue(stringResponse,
					ActivityFilterResponse.class);
			List<CRMActivity> stageChangeActivities = candidateActivityList
					.getCrmActivities();
			List<ActivityTypeCount> summary = candidateActivityList
					.getActivityTypeCountSummary();
			ActivityTypeCount ActivityTypeCount = summary.get(11);
			Assert.assertEquals(ActivityTypeCount.getActivityTypeName(),
					"CRM Stages", "Activity type should be stages ");
			Assert.assertTrue(ActivityTypeCount.getCount() >= 1,
					"Activity count should be greater than 1 ");

			for (CRMActivity crmActivity : stageChangeActivities) {
				Assert.assertEquals(crmActivity.getObjectInfo().getObjectType()
						.toString(), "CRM_STAGES",
						"Object type is not expected");
				List<CRMActivityDetails> crmActivityDetails = crmActivity
						.getActivityDetailsList();
				for (CRMActivityDetails activityDetails : crmActivityDetails) {
					if (activityDetails.getAction().toString()
							.equals("CHANGED")) {
						Assert.assertEquals(
								activityDetails.getTarget().getId(),
								candidateId, "Target is not candidate ID");
						Assert.assertEquals(activityDetails.getActor().getId(),
								userID2,
								"Actor is not the user added to campaign");
					} else {
						Assert.fail("Fount invalid Action Type"
								+ activityDetails.getAction());
					}
				}
			}
		}
	}
}

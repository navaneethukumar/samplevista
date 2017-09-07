package com.spire.crm.entity.campaign.test;

import java.io.IOException;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.commons.utils.IdUtils;
import spire.crm.entity.campaign.beans.CampaignDetails;
import spire.crm.entity.campaign.beans.CampaignLeadVO;
import spire.crm.entity.campaign.beans.CampaignListSummary;
import spire.crm.entity.campaign.entities.Campaign;
import spire.crm.entity.campaign.entities.CampaignEvent;
import spire.crm.entity.campaign.entities.Node;
import spire.crm.entity.campaign.entities.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.PipeLineHelper;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.entity.consumers.CampaignEntityServiceConsumer;

import crm.pipeline.beans.CRM;

/**
 * @author Manikanta Y
 *
 */
public class CampaignServiceEntitValidation extends CRMTestPlan {

	Gson gson = new Gson();
	DataFactory factory = new DataFactory();
	Random randomGenerator = new Random();	
	PipeLineHelper pipeLineHelper = new PipeLineHelper();
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
		campaign.setName("SpireEntityCampaign" + IdUtils.generateUUID().replaceAll("-", ""));
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
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		Response getRespponse = campaignEntityServiceConsumer.getCampaignID(
				campaign.getId(), projection);

		String strCampaign = getRespponse.readEntity(String.class);

		Campaign getCampaign = null;
		try {
			getCampaign = mapper.readValue(strCampaign, Campaign.class);

			Logging.log("validateCampaign method Response   >>>"
					+ gson.toJson(getCampaign));

		} catch (IOException e) {
			
			e.printStackTrace();
			Assert.fail("Run time excption");
		}

		compareCampaigns(campaign, getCampaign);

	}

	private void compareCampaigns(Campaign campaign, Campaign getCampaign) {

		Assert.assertTrue(campaign.getId().equals(getCampaign.getId()),
				"Found discrepancy in campaign id ");
		Assert.assertTrue(campaign.getName().equals(getCampaign.getName()),
				"Found discrepancy in campaign Name ");
		Assert.assertTrue(
				campaign.getCreatedByName().equals(
						getCampaign.getCreatedByName()),
				"Found discrepancy in Created By Name ");
		Assert.assertTrue(
				campaign.getModifiedByName().equals(
						getCampaign.getModifiedByName()),
				"Found discrepancy in Modified By Name ");

		Assert.assertTrue(campaign.getFlowId().equals(getCampaign.getFlowId()),
				"Found discrepancy in campaign flow id ");

		Assert.assertTrue(
				campaign.getNodes().size() == (getCampaign.getNodes().size()),
				"Found discrepancy in campaign id ");

	}

	/*
	 * 
	 * Verifying the list campaign,we need to create a campaign
	 */
	public String createCampaign(Boolean isPublished, String stageName) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		List<Node> nodeLIst = new ArrayList<Node>();
		Node changesatgeNode = setUpChangeStageNode(null, stageName, null);
		nodeLIst.add(changesatgeNode);
		Campaign campaign = setUpCampaign(isPublished);
		campaign.setNodes(nodeLIst);
		Response createResponse = campaignEntityServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 201) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			Logging.log("createResponseEntity Response   >>>"
					+ gson.toJson(createResponseEntity));
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());
			Logging.log("Created Campaign ID is >>> " + campaignId);
			return campaignId;

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
			return null;
		}
		

	}

	public void verifyListCampaign(TestData data) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		Response listResponse = null;
		CampaignListSummary campaignListSummary = null;
		String campaignID = createCampaign(true, "Lead");

		switch (data.getData()) {
		case "All":
			listResponse = campaignEntityServiceConsumer.listCampaign(null,
					null, null);

			break;

		}

		if (listResponse.getStatus() == 200) {

			String strListSummary = listResponse.readEntity(String.class);

			try {

				campaignListSummary = mapper.readValue(strListSummary,
						CampaignListSummary.class);

				Assert.assertTrue(campaignListSummary.getTotalCount() > 0,
						"Campaign  list is zero expecetd is greatee than 0");
				Logging.log("listResponse Response   >>>"
						+ gson.toJson(campaignListSummary));
				validateListHelper(campaignListSummary, campaignID,
						data.getData());

			} catch (IOException e) {

				Assert.fail("Exception in verifyListCampaign" + e.getMessage());
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

	public void verifyDeleteBulkCampaign(TestData data, Integer bulkcount) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
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

		Response deleteResponse = campaignEntityServiceConsumer
				.deleteBulkCampaign(campaignIds);

		if (deleteResponse.getStatus() == 200) {

			ResponseEntity response = deleteResponse
					.readEntity(ResponseEntity.class);

			Logging.log("delete Response   >>>" + gson.toJson(response));

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
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		String campaignId = null;
		String projection = testObject.getTestData();

		if (data.getData().equals("Published")) {

			campaignId = createCampaign(true, "Lead");

		} else {
			campaignId = createCampaign(false, "Lead");
		}
		Response geteResponse = campaignEntityServiceConsumer.getCampaignID(
				campaignId, projection);

		if (geteResponse.getStatus() == 302) {

			String strResponse = geteResponse.readEntity(String.class);

			Campaign getCampaign = null;
			try {
				getCampaign = mapper.readValue(strResponse, Campaign.class);

			} catch (IOException e) {

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
			SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaign(true, "Lead");
		String candidateID = ProfileHelper.createProfile();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			ResponseEntity response = addresponse
					.readEntity(ResponseEntity.class);

			Logging.log("addCampToNoStageCandidate  Response   >>>"
					+ gson.toJson(response));

			Assert.assertEquals(response.getMessage(),
					"All leads successfully added", " Invalid error message");

		} else {

			Assert.fail("Add no stage lead to a campaign is is failed and status code is :"
					+ addresponse.getStatus());
		}

	}

	public void removeCampfromCandidat(TestData data, SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaign(true, "Lead");
		String candidateID = ProfileHelper.createProfile();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			ResponseEntity response = addresponse
					.readEntity(ResponseEntity.class);
			Logging.log("removeCampfromCandidat  Response   >>>"
					+ gson.toJson(response));
			Assert.assertEquals(response.getMessage(),
					"All leads successfully added", " Invalid error message");

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			Response removeResponse = campaignEntityServiceConsumer
					.bulkRemoveLeadsFromCampaigns(campaignLeadVO);
			if (removeResponse.getStatus() == 200) {

				ResponseEntity response01 = removeResponse
						.readEntity(ResponseEntity.class);
				Logging.log("bulkRemoveLeadsFromCampaigns  Response   >>>"
						+ gson.toJson(response01));
				Assert.assertEquals(response01.getMessage(),
						"All leads successfully removed",
						" Invalid error message");

			} else {

				Assert.fail(" Remove  campaigns from Lead is failed and status code is :"
						+ removeResponse.getStatus());

			}

		} else {

			Assert.fail("Add no stage lead to a campaign is is failed and status code is :"
					+ addresponse.getStatus());
		}

	}

	public void verifyChangeStage(TestData data, SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		String campaignId = createCampaign(true, data.getData());
		CRM crm = new CRM();
		crm.setStatusName("Lead");
		String candidateID = ProfileHelper.createProfile(crm);// "6002:6005:3437582c12cd4045842e40ec9c0deb68";//

		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			ResponseEntity response01 = addresponse
					.readEntity(ResponseEntity.class);

			Logging.log("bulkRemoveLeadsFromCampaigns  Response   >>>"
					+ gson.toJson(response01));

			Assert.assertEquals(response01.getMessage(),
					"All leads successfully added", " Invalid error message");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			if (response01.getCode() == 200) {
				campaignEntityServiceConsumer.executeCampaignEvents();
			} else {

				Assert.fail(" Execution is not happend,because of add campaign is failed");
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			Assert.assertEquals(data.getData(),
					pipeLineHelper.getstageName(candidateID),
					"Stage is not changed after excuting the campaign node");

		} else {

			Assert.fail("Add lead to campaign is failed and status is  :"
					+ addresponse.getStatus());
		}

	}

	public void verifyOneDecisionCampaign(TestData data,
			SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
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

		String node4PaparentNodeId = node3yesSendEmail.getId();

		Node node4NoSendEmail = setUpSendEmailNode(node4PaparentNodeId, "0",
				"templateID");

		nodeLIst.add(node1SendEmail);
		nodeLIst.add(node2decision);
		nodeLIst.add(node3yesSendEmail);
		nodeLIst.add(node4NoSendEmail);

		Campaign campaign = setUpCampaign(Boolean.valueOf(data.getData()));
		campaign.setNodes(nodeLIst);

		Response createResponse = campaignEntityServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 201) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());

			campaign.setId(campaignId);

			/*
			 * get the created campaign as FULL
			 */

			Response geteResponse = campaignEntityServiceConsumer
					.getCampaignID(campaignId, "FULL");

			if (geteResponse.getStatus() == 302) {

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
				Assert.fail(" Get  campaign failed and staus code is : "
						+ geteResponse.getStatus());
			}

		} else {

			Assert.fail("Campaign creation is failed and status code is : "
					+ createResponse.getStatus());
		}
	}

	private void compareCampaigns(Campaign getCampaign, Campaign campaign,
			String projection) {

		Assert.assertTrue(campaign.getId().equals(getCampaign.getId()),
				"Found discrepancy in campaign id ");
		Assert.assertTrue(campaign.getName().equals(getCampaign.getName()),
				"Found discrepancy in campaign Name ");
		Assert.assertTrue(
				campaign.getCreatedByName().equals(
						getCampaign.getCreatedByName()),
				"Found discrepancy in Created By Name ");
		Assert.assertTrue(
				campaign.getModifiedByName().equals(
						getCampaign.getModifiedByName()),
				"Found discrepancy in Modified By Name ");

		Assert.assertTrue(campaign.getFlowId().equals(getCampaign.getFlowId()),
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

	}

	public void verifyCampaignExecution(TestData data,
			SpireTestObject testObject) {
		Campaign campaign = createExecutionCampaign(data, testObject);
		String candidateID = ProfileHelper.createProfile(); // "6002:6005:0a90bf69b07048c885ef5a392be8dc96"

		addCampToCandidate(data, testObject, campaign.getId(), candidateID);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}


		for (int i = 0; i < 2; i++) {

			executeCampaignNode(campaign);

		}

	}

	private void executeCampaignNode(Campaign campaign) {
		Boolean executionFalg = true;
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		List<CampaignEvent> beforescheduledEvents = getScheduledEvents();

		Assert.assertTrue(beforescheduledEvents.size() > 0,
				"Expected sheduled events,but not found");

		for (CampaignEvent campaignEvent : beforescheduledEvents) {

			if (campaign.getId().equals(campaignEvent.getCampaignId())) {

				String nodeid = campaignEvent.getNodeId();

				campaignEntityServiceConsumer.executeCampaignEvents();

				List<CampaignEvent> afterExecutionEvents = getScheduledEvents();

				for (CampaignEvent campaignEvent2 : afterExecutionEvents) {

					if (nodeid.equals(campaignEvent2.getNodeId())) {

						Assert.fail("Node is not executed");
						executionFalg = false;
					}

				}

			} else {

				Assert.fail("expected campaign event is not found in the list");
			}

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

				Logging.log("Get Scheduled Events Response  >>>> "
						+ gson.toJson(scheduledEvents));
			} catch (IOException e) {

				e.printStackTrace();
			}

			return scheduledEvents;

		} else {

			Assert.fail("expected campaign event is not found in the list");
		}
		return null;
	}

	private void addCampToCandidate(TestData data, SpireTestObject testObject,
			String campaignId, String candidateID) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
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

	private Campaign createExecutionCampaign(TestData data,
			SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		List<Node> nodeLIst = new ArrayList<Node>();

		Node node1SendEmail = setUpSendEmailNode(null, null,
				"6002:6031:e8791c6cbbd345e1af681bc3c0424209");

		String node2PaparentNodeId = node1SendEmail.getId();

		String node3PaparentNodeId = null;
		Node node2decision = null;
		switch (testObject.getTestData()) {

		case "RepliedEmail":
			node2decision = setUpRepliedEmailNode(node2PaparentNodeId, 0);

			break;
		default:
			node2decision = setUpReadEmailNode(node2PaparentNodeId, 0);

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

		Response createResponse = campaignEntityServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 201) {

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

	public void verifyScheduledEvents(TestData data, SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaign(true, "Lead");
		String candidateID = ProfileHelper.createProfile();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			Response eventsResponse = campaignEntityServiceConsumer
					.getScheduledEvents();
			if (eventsResponse.getStatus() == 302) {

				String strEvents = eventsResponse.readEntity(String.class);
				List<CampaignEvent> scheduledEvents = null;
				Logging.log("Shcedule events response is >> " + strEvents);
				try {
					scheduledEvents = mapper.readValue(strEvents,
							new TypeReference<List<CampaignEvent>>() {
							});
				} catch (IOException e) {

					e.printStackTrace();
					Assert.fail("Run time exception " + e.getMessage());
				}

				Boolean scheduleFalg = false;
				for (CampaignEvent event : scheduledEvents) {
					if (campaignId.equals(event.getCampaignId())) {
						scheduleFalg = true;
					}

				}

				Assert.assertTrue(
						scheduleFalg,
						"Expected Campaign events are not found in the schedule List and campaign  ID is>> "
								+ campaignId);

			} else {

				Assert.fail("Get schedule event is failed and status code is "
						+ eventsResponse.getStatus());

			}
		} else {
			Assert.fail("Generate schedule event is failed and status code is "
					+ addresponse.getStatus());
		}

	}

	public void verifyEditCampaign(TestData data, SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		List<Node> nodeLIst = new ArrayList<Node>();
		Node changesatgeNode = setUpChangeStageNode(null, "Lead", null);
		nodeLIst.add(changesatgeNode);
		Campaign campaign = setUpCampaign(true);
		campaign.setNodes(nodeLIst);
		Response createResponse = campaignEntityServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 201) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			Logging.log("createResponseEntity Response   >>>"
					+ gson.toJson(createResponseEntity));
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());
			campaign.setId(campaignId);
			Response editResponse = campaignEntityServiceConsumer
					.updateCampaign(campaign);
			if (editResponse.getStatus() == 200) {

				ResponseEntity editCampaignResponse = editResponse
						.readEntity(ResponseEntity.class);
				Logging.log("Edit Response is >>> "
						+ gson.toJson(editCampaignResponse));
				Assert.assertEquals(editCampaignResponse.getCode(), 200,
						"Invalida status code in response");
				Assert.assertTrue(
						editCampaignResponse.getMessage().contains(
								"successfully updated campaign"),
						"found discrepancy in Message");
				Assert.assertEquals(
						editCampaignResponse.getSuccess().get(
								campaign.getName()), campaignId,
						"found discrepancy in Campaign ID");

			} else {

				Assert.fail("Edit Campaign Is failed and status code is >>>"
						+ editResponse.getStatus());
			}

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
		}

	}

	public void verifygetLeadsForCampaign(TestData data,
			SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaign(true, "Lead");
		String candidateID = ProfileHelper.createProfile();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			Response response = campaignEntityServiceConsumer
					.getLeadsForCampaign(campaignId, "60", "0");

			String strResponse = response.readEntity(String.class);
			Logging.log("Response of the get leads of campaign is >>"
					+ strResponse);
			Assert.assertTrue(strResponse.contains(candidateID),
					"Added candidate is not present under the campaign");
			Assert.assertTrue(strResponse.contains("\"count\":1"),
					"Count of the candidate is as expected");

		} else {
			Assert.fail("add campaign is failed and status code is >> "+ addresponse.getStatus() );
		}

	}

	public void verifyCloneCampaign(TestData data, SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		boolean publishFlag = false;

		if (testObject.getTestData().equals("published")) {

			publishFlag = true;
		}

		String campaignId = createCampaign(publishFlag, "Lead");

		String cloneCampaignName = "SpireCampaign" + factory.getRandomWord()
				+ randomGenerator.nextInt(10000);
		String flowID = cloneCampaignName;
		Response cloneResponse = campaignEntityServiceConsumer.cloneCampaign(
				campaignId, cloneCampaignName, flowID);

		if (cloneResponse.getStatus() == 302) {

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

			Response geteResponse = campaignEntityServiceConsumer
					.getCampaignID(campaignId, "FULL");

			if (geteResponse.getStatus() == 302) {

				String strResponse = geteResponse.readEntity(String.class);

				Logging.log("Get response is " + strResponse);
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

			} else {

				Assert.fail("Get Campaign by id and projection is failed and status code is "
						+ geteResponse.getStatus());
			}

		} else {
			Assert.fail(" Clone Campaign is failed and status code is : "
					+ cloneResponse.getStatus());
		}

	}

	public void verifygetBulkCampaignByID(TestData data,
			SpireTestObject testObject) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		String campaignId = null;
		String projection = testObject.getTestData();

		if (data.getData().equals("Published")) {

			campaignId = createCampaign(true, "Lead");

		} else {
			campaignId = createCampaign(false, "Lead");
		}

		List<String> campaignIds = new ArrayList<String>();

		campaignIds.add(campaignId);

		Response geteResponse = campaignEntityServiceConsumer.bulkGetCampaigns(
				campaignIds, projection);

		if (geteResponse.getStatus() == 302) {

			String strResponse = geteResponse.readEntity(String.class);
			Logging.log("Response of bulk get is >>" + strResponse);
			List<Campaign> getCampaigns = null;
			try {
				getCampaigns = mapper.readValue(strResponse,
						new TypeReference<List<Campaign>>() {
						});

			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail("Run time exception ");

			}

			for (Campaign getCampaign : getCampaigns) {

				Assert.assertEquals(getCampaign.getId(), campaignId,
						"Created and get campaign are not samae");
				if (projection.equals("FULL")) {
					Assert.assertNotNull(getCampaign.getNodes());
				} else if (projection.equals("BASIC")) {

					Assert.assertNull(getCampaign.getNodes());
				}

			}
		} else {

			Assert.fail("Get Campaign by id and projection is failed and status code is "
					+ geteResponse.getStatus());
		}

	}

	public void verifygetCampaignForLead(TestData data,
			SpireTestObject testObject) {
		
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		CampaignLeadVO campaignLeadVO = new CampaignLeadVO();
		String campaignId = createCampaign(true, "Lead");
		String candidateID = ProfileHelper.createProfile();

		List<String> campaignIds = new ArrayList<String>();
		campaignIds.add(campaignId);
		campaignLeadVO.setCampaignIds(campaignIds);
		List<String> leadIds = new ArrayList<String>();
		leadIds.add(candidateID);
		campaignLeadVO.setLeadIds(leadIds);

		Response addresponse = campaignEntityServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);

		if (addresponse.getStatus() == 200) {

			Response response = campaignEntityServiceConsumer
					.getCampaignForLead(candidateID);

			String strResponse = response.readEntity(String.class);
			Logging.log("Response of the get Campaign of Leads is >>"
					+ strResponse);
			Assert.assertTrue(strResponse.contains(campaignId),
					"Added campaoign is not present under the candidate");
			Assert.assertTrue(strResponse.contains("percentageCompleted"),
					"percentageCompleted is not found is response ");
			
			Assert.assertTrue(strResponse.contains("campaignName"),
					"campaignName is not found is response ");

		} else {
			Assert.fail("add campaign is failed and status code is >> "+ addresponse.getStatus() );
		}
		
		
		
		
		
	}
}

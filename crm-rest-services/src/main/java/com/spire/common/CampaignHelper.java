package com.spire.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import spire.crm.entity.campaign.beans.CampaignLeadVO;
import spire.crm.entity.campaign.beans.PublishCampaignDetails;
import spire.crm.entity.campaign.entities.Campaign;
import spire.crm.entity.campaign.entities.Node;
import spire.crm.entity.campaign.entities.ResponseEntity;

import com.google.gson.Gson;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.CampaignBizServiceConsumer;

public class CampaignHelper {
	DataFactory factory = new DataFactory();
	Random randomGenerator = new Random();
	CampaignBizServiceConsumer campaignBizServiceConsumer = new CampaignBizServiceConsumer();
	EmailBizHelper emailBizHelper = new EmailBizHelper();
	ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();

	public boolean addCandidatesToCampaigns(CampaignLeadVO campaignLeadVO) {

		CampaignBizServiceConsumer campaignBizServiceConsumer = new CampaignBizServiceConsumer();
		Response addresponse = campaignBizServiceConsumer
				.bulkAddLeadToCampaigns(campaignLeadVO);
		if (addresponse.getStatus() == 200) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Create campaign having 3 send emails and one Decision
	 */

	public String createCampign(Boolean isPublished, String DecisionType) {

		List<Node> nodeLIst = new ArrayList<Node>();

		Node node1SendEmail = setUpSendEmailNode(null, null,
				emailBizHelper.createEmailTemplate());

		String node2PaparentNodeId = node1SendEmail.getId();

		String node3PaparentNodeId = null;
		Node node2decision = null;
		switch (DecisionType) {

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

		Campaign campaign = setUpCampaign(isPublished);
		campaign.setNodes(nodeLIst);

		Response createResponse = campaignBizServiceConsumer
				.createCampaign(campaign);
		if (createResponse.getStatus() == 200) {

			ResponseEntity createResponseEntity = createResponse
					.readEntity(ResponseEntity.class);
			String campaignId = createResponseEntity.getSuccess().get(
					campaign.getName());

			return campaignId;

		} else {
			Assert.fail(" Campaign creation failed and staus code is : "
					+ createResponse.getStatus());
		}
		return null;

	}

	private Campaign setUpCampaign(Boolean isPublished) {
		Campaign campaign = new Campaign();
		campaign.setFlowId(IdUtils.generateUUID());
		campaign.setName(factory.getRandomWord()
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

	private Node setUpReadEmailNode(String parentNodeId, int waitTime) {
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

	private Node setUpSendEmailNode(String parentNodeId, String decisionPath,
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

	public void publishBulkCampaign(
			List<PublishCampaignDetails> publishCampaigns) {

		campaignBizServiceConsumer.publishCampaign(publishCampaigns);

	}

	public void cloneCamp() {

		String campaignId = "6002:6040:03ea83d9739348f1bb3e97ceeb29e6c7";

		String cloneCampaignName = null;

		for (int i = 3; i <= 100; i++) {

			if (i < 10) {
				cloneCampaignName = "PT_Camp00" + i;
			} else if (i < 99) {
				cloneCampaignName = "PT_Camp0" + i;
			} else {
				cloneCampaignName = "PT_Camp" + i;
			}

			String flowID = cloneCampaignName;

			campaignBizServiceConsumer.cloneCampaign(campaignId,
					cloneCampaignName, flowID);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	/*
	 * public static void main(String args[]) {
	 * 
	 * CampaignHelper obj = new CampaignHelper();
	 * 
	 * obj.cloneCamp();
	 * 
	 * String candidateIDs = null; String emailID = null;
	 * 
	 * PrintWriter writer = null;
	 * 
	 * try { writer = new PrintWriter("E:\\Project_Docs\\CandidateIDs.csv",
	 * "UTF-8"); } catch (FileNotFoundException | UnsupportedEncodingException
	 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
	 * 
	 * for (int i = 1132; i <= 10000; i++) {
	 * 
	 * System.out.println("candidates created :>>"+i);
	 * 
	 * if (i < 1000) {
	 * 
	 * emailID = "s.manikhanta@spire2grow.com"; } else if (i < 3000) { emailID =
	 * "nimesh.agarwal@spire2grow.com";
	 * 
	 * } else if (i < 5000) {
	 * 
	 * emailID = "siddharth.kumar@spire2grow.com";
	 * 
	 * } else if (i < 7000) {
	 * 
	 * emailID = "roshan.deshpande@spire2grow.com";
	 * 
	 * } else if (i < 8000) {
	 * 
	 * emailID = "chandan.patro@spire2grow.com";
	 * 
	 * } else if (i <= 10000) {
	 * 
	 * emailID = "supreet.totagi@spire2grow.com";
	 * 
	 * } String candidateID = ProfileHelper.createProfile(emailID); candidateIDs
	 * = candidateIDs + "," + candidateID;
	 * 
	 * writer.println(candidateID + "," + emailID);
	 * 
	 * }
	 * 
	 * writer.close(); System.out.println("Done creation"); try {
	 * Thread.sleep(2000); } catch (InterruptedException e) {
	 * 
	 * // TODOAuto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * } }
	 */

	/*
	 * 
	 * 
	 * generate bulk publish input from excel and publish
	 * 
	 * public static void main(String args[]) {
	 * 
	 * CampaignHelper obj = new CampaignHelper(); List<PublishCampaignDetails>
	 * publishCampaigns = new ArrayList<PublishCampaignDetails>();
	 * 
	 * String campFile = "E:\\Project_Docs\\PT_campIDs.csv"; BufferedReader br =
	 * null; String line = ""; String cvsSplitBy = ",";
	 * 
	 * try {
	 * 
	 * 
	 * br = new BufferedReader(new FileReader(campFile)); while ((line =
	 * br.readLine()) != null) {
	 * 
	 * PublishCampaignDetails publishCampaignDetails = new
	 * PublishCampaignDetails(); // use comma as separator String[] data =
	 * line.split(cvsSplitBy);
	 * 
	 * publishCampaignDetails.setCampaignId(data[0]);
	 * publishCampaignDetails.setFromTime(ZonedDateTime.now(
	 * ZoneOffset.UTC).minus(Period.ofDays(4)));
	 * publishCampaignDetails.setToTime(publishCampaignDetails
	 * .getFromTime().plus(Period.ofYears(4)));
	 * 
	 * 
	 * publishCampaigns.add(publishCampaignDetails); //
	 * System.out.println("candidateID " + data[0] + // " , Email = ID " +
	 * data[1] + "]");
	 * 
	 * }
	 * 
	 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } finally { if (br != null) { try
	 * { br.close(); } catch (IOException e) { e.printStackTrace(); } } }
	 * 
	 * System.out.println("Done");
	 * 
	 * Gson gson = new Gson(); System.out.println("List is " +
	 * gson.toJson(publishCampaigns));
	 * obj.publishBulkCampaign(publishCampaigns);
	 * 
	 * }
	 */

	// generate bulk add campaign Request
	public static void main(String args[])

	{
		spire.crm.entity.campaign.beans.CampaignLeadVO campaignLeadVO = new spire.crm.entity.campaign.beans.CampaignLeadVO();

		List<String> candidateIDslist = new ArrayList<String>();
		List<String> campaignIdslist = new ArrayList<String>();

		String csvFile = "E:\\Project_Docs\\CandidateIDs.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new java.io.FileReader(csvFile));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				candidateIDslist.add(data[0]);
				// System.out.println("candidateID " + data[0] + //
				// " , Email = ID " + data[1] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		String campFile = "E:\\Project_Docs\\PT_campIDs.csv";

		try {

			br = new BufferedReader(new FileReader(campFile));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				campaignIdslist.add(data[0]);
				// System.out.println("candidateID " + data[0] + //
				// " , Email = ID " + data[1] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		campaignLeadVO.setCampaignIds(campaignIdslist);
		campaignLeadVO.setLeadIds(candidateIDslist);

		Gson gson = new Gson();
		System.out.println("List is " + gson.toJson(campaignLeadVO));

	}

}

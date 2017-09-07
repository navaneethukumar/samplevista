package com.spire.crm.biz.campaign.test;

import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.CampaignBizServiceConsumer;

/**
 * @author Manikhanta Y
 *
 */
// extends CampaignHelper
public class CampaignBizLRGValidation {

	Gson gson = new Gson();
	DataFactory factory = new DataFactory();
	CampaignBizServiceConsumer campaignBizServiceConsumer = new CampaignBizServiceConsumer();

	ObjectMapper mapper = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule((Module) new JavaTimeModule());

	public void verifymarkReplied(SpireTestObject testObject, TestData data) {

		Response response = null;

		switch (testObject.getTestTitle()) {
		case "markReplied_LRG":
			response = campaignBizServiceConsumer
					.replyCampaignEvent("ccfac089-d3e2-435b-bf5d-af624bf56b27");
			break;
		case "markRead_LRG":
			response = campaignBizServiceConsumer
					.readCampaignEvent("ccfac089-d3e2-435b-bf5d-af624bf56b27");
			break;

		default:
			break;
		}

		if (response.getStatus() != 400) {
			Assert.fail("reply Campaign Event is failed and stats code is"
					+ response.getStatus());
		}
	}

}

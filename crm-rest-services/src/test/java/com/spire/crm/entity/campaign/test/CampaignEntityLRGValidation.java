package com.spire.crm.entity.campaign.test;

import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.entity.consumers.CampaignEntityServiceConsumer;

/**
 * @author Manikanta Y
 *
 */
public class CampaignEntityLRGValidation extends CRMTestPlan {

	Gson gson = new Gson();
	DataFactory factory = new DataFactory();

	public void verifyupdateEvent(SpireTestObject testObject, TestData data) {
		CampaignEntityServiceConsumer campaignEntityServiceConsumer = new CampaignEntityServiceConsumer();
		Response response = null;

		switch (testObject.getTestTitle()) {
		case "updateEmailReadEvent_LRG":
			response = campaignEntityServiceConsumer
					.updateEmailReadEvent("ccfac089-d3e2-435b-bf5d-af624bf56b27");
			
			if (response.getStatus() != 400) {
				Assert.fail("reply Campaign Event is failed and stats code is"
						+ response.getStatus());
			}
			
			break;
		case "updateFeedBackEvent_LRG":
			response = campaignEntityServiceConsumer
					.updateFeedBackEvent("ccfac089-d3e2-435b-bf5d-af624bf56b27","yes");
			
			if (response.getStatus() != 304) {
				Assert.fail("reply Campaign Event is failed and stats code is"
						+ response.getStatus());
			}
			
			break;
		case "updateFormSubmitEvent_LRG":
			response = campaignEntityServiceConsumer
					.updateFormSubmitEvent("ccfac089-d3e2-435b-bf5d-af624bf56b27");
			
			if (response.getStatus() != 304) {
				Assert.fail("reply Campaign Event is failed and stats code is"
						+ response.getStatus());
			}			
			break;
		default:
			break;
		}

		
	}
}

package com.spire.crm.biz.crm.rule.test;

/**
 * @author Manikanta.Y
 */

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.biz.consumers.StageChangeReasonBizServiceConsumer;

public class CRMStageChangeReasonValidation extends CRMTestPlan {

	public Gson gson = new Gson();
	public ObjectMapper mapper = new ObjectMapper();

	StageChangeReasonBizServiceConsumer client = new StageChangeReasonBizServiceConsumer();

	public void verifyAddReason(SpireTestObject testObject, TestData data) {

		List<String> reasonList = new ArrayList<String>();

		String testData[] = data.getData().split(":");

		int count = Integer.parseInt(testData[1]);

		for (int i = 0; i <= count; i++) {

			reasonList.add("SpireAutomationStageChangeReason"
					+ UUID.randomUUID());
		}

		Response response = client.addReason(reasonList);

		if (response.getStatus() == 200) {

			Response removeReason = client.removeReasons(reasonList);

			if (removeReason.getStatus() != 200) {

				Assert.fail("Remove reason is failed and status code is >>"
						+ response.getStatus());
			}

		} else {

			Assert.fail("Add reason is failed and status code is >>"
					+ response.getStatus());
		}

	}

	public void verifyListReason(SpireTestObject testObject, TestData data) {
	
		Response listReason = client.getAllReasons();

		if (listReason.getStatus() != 200) {

			Assert.fail("Remove reason is failed and status code is >>"
					+ listReason.getStatus());
		}
		
	}

}

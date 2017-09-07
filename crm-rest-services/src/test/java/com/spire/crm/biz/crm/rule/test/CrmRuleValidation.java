package com.spire.crm.biz.crm.rule.test;

/**
 * @author Manikanta.Y
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.crm.biz.rules.beans.Action;
import spire.crm.biz.rules.beans.Condition;
import spire.crm.biz.rules.entites.CollectionEntity;
import spire.crm.biz.rules.entites.EngagementRuleEntity;
import spire.crm.biz.rules.entites.ErrorEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.CrmRulesBizServiceConsumer;

public class CrmRuleValidation extends CRMTestPlan {

	public static Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();
	public static String endPoint = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_RULES_BIZ");

	public static void verifyCreateRule(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyCreateRule Request Input is >>" + gson.toJson(rule));

		Response createResponse = null;
		createResponse = crmRulesBizServiceConsumer.createRule(rule);
		if (createResponse.getStatus() == 201) {
			if (testObject.getTestTitle().equals("verifyDuplicateWeight_LRG")) {
				testObject.setTestTitle("verifyDuplicateWeight");
				verifyCreateRule(rule, testObject);

			} else if (testObject.getTestTitle().equals(
					"verifyGetByTenantID_Sanity")) {

				validateGetAllRules(rule, testObject);

				verifyDeleteRule(rule, testObject);

			} else if (testObject.getTestTitle().equals(
					"verifyGetByTenantID_Sanity")) {

				verifyUpdateRule(rule, testObject);
				verifyRuleById(rule, testObject);

				verifyDeleteRule(rule, testObject);

			} else {
				verifyRuleByIdName(rule, testObject);
			}

		} else if (createResponse.getStatus() == 400) {

			ErrorEntity error = createResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "400");
			String errorMessage = error.getMessage();
			if (!errorMessage.contains("null"))

				Assert.fail(" invalid error message :" + errorMessage);

		} else {
			Assert.fail(" Create Rule is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	public static void validateGetAllRules(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);
		Logging.info("validateGetAllRules Request Input is >>"
				+ gson.toJson(rule));
		Response getResponse = crmRulesBizServiceConsumer
				.getRuleByTenantID(rule.getTenantId());
		if (getResponse.getStatus() == 200) {
			CollectionEntity<EngagementRuleEntity> ruleList = new CollectionEntity<EngagementRuleEntity>();

			ruleList = getResponse
					.readEntity(new GenericType<CollectionEntity<EngagementRuleEntity>>() {
					});

			List<EngagementRuleEntity> ruleEntity = new ArrayList<EngagementRuleEntity>(
					ruleList.getItems());

			int listCount = ruleEntity.size();
			Assert.assertTrue(listCount > 0, "rule not found list legnth is :"
					+ listCount);
			Boolean flag = false;

			for (int i = 0; i < listCount; i++) {

				{
					if (ruleEntity.get(i).getTenantId() == rule.getTenantId())

					{
						compareRule(rule, ruleEntity.get(i));

					}
					flag = true;
				}

			}
			Assert.assertTrue(flag, "rule  not found ..");

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "NOT_FOUND");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ rule.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get rule by Tenant ID failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	static void verifyRuleByIdName(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyRuleById Request Input is >>" + gson.toJson(rule));

		Response getResponse = crmRulesBizServiceConsumer.getRuleByNameID(
				rule.getTenantId(), rule.getRuleName());
		if (getResponse.getStatus() == 200) {

			EngagementRuleEntity getrule = getResponse
					.readEntity(EngagementRuleEntity.class);
			compareRule(getrule, rule);
			verifyDeleteRule(rule, testObject);

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ rule.getTenantId() + ":" + rule.getRuleName())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else if (getResponse.getStatus() == 400) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Invalid tenent id:" + rule.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get Weight is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	static void verifyRuleById(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyRuleById Request Input is >>" + gson.toJson(rule));

		Response getResponse = crmRulesBizServiceConsumer
				.getRuleByTenantID(rule.getTenantId());
		if (getResponse.getStatus() == 200) {

			CollectionEntity<EngagementRuleEntity> ruleList = new CollectionEntity<EngagementRuleEntity>();

			ruleList = getResponse
					.readEntity(new GenericType<CollectionEntity<EngagementRuleEntity>>() {
					});

			if (!testObject.getTestTitle().equals("verifyGetByTenantID_LRG")) {
				List<EngagementRuleEntity> ruleEntity = new ArrayList<EngagementRuleEntity>(
						ruleList.getItems());
				compareRule(ruleEntity.get(0), rule);
				verifyDeleteRule(rule, testObject);
			} else {

				Assert.assertNull(ruleList.getItems());
			}

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ rule.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else if (getResponse.getStatus() == 400) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Invalid tenent id:" + rule.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get Weight is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	public static void verifyDeleteRule(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyDeleteRule Request Input is >>" + gson.toJson(rule));

		Response getResponse = crmRulesBizServiceConsumer.deleteRule(
				rule.getTenantId(), rule.getRuleName());
		if (getResponse.getStatus() == 200) {

			String response = getResponse.readEntity(String.class);
			Assert.assertEquals(response, rule.getRuleName() + " Deleted",
					"Invalid error message ");

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ rule.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else if (getResponse.getStatus() == 400) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage
					.equals("RInvalid tenent id:" + rule.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" Delete rule is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	private static void compareRule(EngagementRuleEntity getRule,
			EngagementRuleEntity rule) {
		Assert.assertEquals(getRule.getThen().getActionName(), rule.getThen()
				.getActionName(), " Discrepancy found in Action Name ");

		Assert.assertEquals(getRule.getThen().getActionValue(), rule.getThen()
				.getActionValue(), " Discrepancy found in Action Value ");

		Assert.assertEquals(getRule.getWhen().getField(), rule.getWhen()
				.getField(), " Discrepancy found in when's field ");

		Assert.assertEquals(getRule.getWhen().getOperator(), rule.getWhen()
				.getOperator(), " Discrepancy found in when's Operator ");
		Assert.assertEquals(getRule.getWhen().getValue(), rule.getWhen()
				.getValue(), " Discrepancy found in when's Value ");

	}

	static void setUpCreateRule(EngagementRuleEntity rule, Action action,
			Condition condition, SpireTestObject testObject) {

		Random ran = new Random();

		Integer tenantId = ran.nextInt(10000) + 8999;
		try {
			tenantId = (int) new Date().getTime();
		} catch (Exception e) {

			tenantId = ran.nextInt(10000) + 8999;
		}

		float[] value;

		if (!condition.getOperator().toString().equals("BW")) {
			value = new float[1];
			value[0] = 10;
		} else {
			value = new float[2];
			value[0] = 10;
			value[1] = 20;

		}

		condition.setValue(value);
		rule.setTenantId((int)(System.currentTimeMillis()%12354));
		rule.setThen(action);
		rule.setWhen(condition);

	}

	public static void verifylistRuleActions(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyDeleteWeight Request Input is >>"
				+ gson.toJson(rule));

		Response getResponse = crmRulesBizServiceConsumer.getRuleACtions();
		if (getResponse.getStatus() == 200) {

			String response = getResponse.readEntity(String.class);
			Assert.assertTrue(response.contains("CHANGE_CRM_STAGE"),
					" CHANGE_CRM_STAGE not foung in actions ");
			Assert.assertTrue(response.contains("ASSIGN_CAMPAIGN"),
					" ASSIGN_CAMPAIGN not foung in actions ");
			Assert.assertTrue(response.contains("CREATE_CRM_SUB_STAGE"),
					" CREATE_CRM_SUB_STAGE not foung in actions ");

		} else {
			Assert.fail(" Delete rule is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	public static void verifyUpdateRule(EngagementRuleEntity rule,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyDeleteWeight Request Input is >>"
				+ gson.toJson(rule));

		Response getResponse = crmRulesBizServiceConsumer.updateRule(rule);
		if (getResponse.getStatus() == 200) {
			String response = getResponse.readEntity(String.class);
			Assert.assertNotNull(response,
					"No response message after deleting the rule");
		} else {
			Assert.fail(" Delete rule is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

}

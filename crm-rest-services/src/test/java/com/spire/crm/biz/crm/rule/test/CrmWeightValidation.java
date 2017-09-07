package com.spire.crm.biz.crm.rule.test;

/**
 * @author Manikanta.Y
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.Context;
import spire.commons.config.SpireConfigManager;
import spire.commons.config.impl.DynamicConfigurationManager;
import spire.commons.utils.ContextUtil;
import spire.crm.biz.rules.entites.CollectionEntity;
import spire.crm.biz.rules.entites.ErrorEntity;
import spire.crm.biz.rules.entites.WeightageRuleEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.helper.SpireProperties;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.CrmRulesBizServiceConsumer;

public class CrmWeightValidation extends CRMTestPlan {

	public static Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();
	public static String endPoint = ReadingServiceEndPointsProperties
			.getServiceEndPoint("CRM_WEIGHT_BIZ");
	public static String propertiesfilePath = "./src/main/resources/default_headers.properties";

	public static String tenantID = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("tenantId", "6000");

	public static String realmName = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("realmName", "6000");

	public static String userId = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("userId", "6000");

	public static String Authorization = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("Authorization", "6000");

	public static String loginId = SpireProperties.loadProperties(
			propertiesfilePath).getProperty("loginId", "6000");

	public static void verifyCreateWeight(WeightageRuleEntity weight,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);
		setUpCreateWeight(weight, testObject);

		Logging.info("verifyCreateWeight Request Input is >>"
				+ gson.toJson(weight));

		Response createResponse = null;
		createResponse = crmRulesBizServiceConsumer.createWeight(weight);
		if (createResponse.getStatus() == 201) {

			if (testObject.getTestTitle().equals("verifyDuplicateWeight_LRG")) {
				testObject.setTestTitle("verifyDuplicateWeight");
				verifyCreateWeight(weight, testObject);

			} else if (testObject.getTestTitle().equals(
					"verifygetAllWeight_SRG")) {

				validateGetAlleWeight(weight, testObject);

				verifyDeleteWeight(weight, testObject);

			} else if (testObject.getTestTitle().equals("updateWeight_Sanity")) {

				verifyUpdateWeight(weight, testObject);

			} else {
				verifyWeightById(weight, testObject);
			}

		} else if (createResponse.getStatus() == 409) {
			if (testObject.getTestTitle().equals("verifyDuplicateWeight")) {

				ErrorEntity error = createResponse
						.readEntity(ErrorEntity.class);
				Assert.assertEquals(error.getCode(), "409");
				verifyDeleteWeight(weight, testObject);
				String errorMessage = error.getMessage();

				if (!errorMessage
						.equals("Create failed.Record already exists: "
								+ weight.getTenantId() + ":ES_WEIGHT_RULE"))

					Assert.fail(" invalid error message :" + errorMessage);

			} else {
				Assert.fail(" Create Weight is failed and status code is :"
						+ createResponse.getStatus());
			}

		} else if (createResponse.getStatus() == 400) {

			ErrorEntity error = createResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "400");
			String errorMessage = error.getMessage();
			if (!errorMessage.equals("Sum of weights not equal to 100%"))
				if (!errorMessage.equals("Invalid awareness : -10.0")) {
					Assert.fail(" Invalid error message :" + errorMessage);
				}

		} else {
			Assert.fail(" Create Weight is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	public static void validateGetAlleWeight(WeightageRuleEntity weight,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);
		Logging.info("validateRulesByID Request Input is >>"
				+ gson.toJson(weight));
		Response getResponse = crmRulesBizServiceConsumer.getWeights();
		if (getResponse.getStatus() == 200) {
			CollectionEntity<WeightageRuleEntity> weightList = new CollectionEntity<WeightageRuleEntity>();

			weightList = getResponse
					.readEntity(new GenericType<CollectionEntity<WeightageRuleEntity>>() {
					});

			List<WeightageRuleEntity> weights = new ArrayList<WeightageRuleEntity>(
					weightList.getItems());

			int listCount = weights.size();
			Assert.assertTrue(listCount > 0, "rule not found list legnth is :"
					+ listCount);
			Boolean flag = false;

			for (int i = 0; i < listCount; i++) {

				{
					if (weights.get(i).getTenantId() == weight.getTenantId())

					{
						compareWeight(weight, weights.get(i));

					}
					flag = true;
				}

			}
			Assert.assertTrue(flag, "weight  not found ..");

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "NOT_FOUND");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ weight.getTenantId())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get weight by Tenant ID failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	public static void verifyWeightById(WeightageRuleEntity weight,
			SpireTestObject testObject) {
		if (!testObject.getTestTitle().equals("updateWeight_Sanity")) {
			setUpCreateWeight(weight, testObject);
		}
		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyWeightById Request Input is >>"
				+ gson.toJson(weight));

		Response getResponse = crmRulesBizServiceConsumer.getWeightByID(weight
				.getTenantId());
		if (getResponse.getStatus() == 200) {

			WeightageRuleEntity getWeight = getResponse
					.readEntity(WeightageRuleEntity.class);

			if (testObject.getTestTags().equalsIgnoreCase("default")) {

				compareWeight(getWeight, getDefaultWeight());

			} else {

				compareWeight(getWeight, weight);
			}

			verifyDeleteWeight(weight, testObject);

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ weight.getTenantId() + ":ES_WEIGHT_RULE")) {
				Assert.fail(" Invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get Weight is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	public static void verifyDeleteWeight(WeightageRuleEntity weight,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);

		Logging.info("verifyWeightById Request Input is >>"
				+ gson.toJson(weight));

		Response getResponse = crmRulesBizServiceConsumer.deleteWeight(weight
				.getTenantId());
		if (getResponse.getStatus() == 200) {

			String response = getResponse.readEntity(String.class);
			Assert.assertEquals(response, "ES_WEIGHT_RULE Deleted",
					"Invalid error message ");

		} else if (getResponse.getStatus() == 404) {

			ErrorEntity error = getResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "404");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ weight.getTenantId() + ":ES_WEIGHT_RULE")) {
				Assert.fail(" Invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" Delete Weight is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	private static void compareWeight(WeightageRuleEntity getWeight,
			WeightageRuleEntity weight) {
		float totalScore = weight.getAwareness()
				+ weight.getBroadcastCommunication()
				+ weight.getCompatability() + weight.getConnectFactors()
				+ weight.getInterestLevel() + weight.getPersonalCommunication();

		Assert.assertEquals(weight.getAwareness(), getWeight.getAwareness(),
				" Awareness score is not equal ");
		Assert.assertEquals(weight.getBroadcastCommunication(),
				getWeight.getBroadcastCommunication(),
				" BroadcastCommunication score is not equal ");
		Assert.assertEquals(weight.getCompatability(),
				getWeight.getCompatability(),
				" Compatability score is not equal ");
		Assert.assertEquals(weight.getConnectFactors(),
				getWeight.getConnectFactors(),
				" ConnectFactors score is not equal ");
		Assert.assertEquals(weight.getInterestLevel(),
				getWeight.getInterestLevel(),
				" InterestLevel score is not equal ");
		Assert.assertEquals(weight.getPersonalCommunication(),
				getWeight.getPersonalCommunication(),
				" PersonalCommunication score is not equal ");

		Assert.assertEquals(totalScore, (float) 100.0,
				"Total score is not 100 % ");

	}

	private static void setUpCreateWeight(WeightageRuleEntity weight,
			SpireTestObject testObject) {
		int tenantId = (int) new Date().getTime();
		weight.setTenantId((int)(System.currentTimeMillis()%1234));
		weight.setAwareness(10);
		weight.setBroadcastCommunication(20);
		weight.setCompatability(30);
		weight.setConnectFactors(10);
		weight.setInterestLevel((float) 10.5);
		weight.setPersonalCommunication((float) 19.5);

		if (testObject.getTestTags().equals("above100")) {
			weight.setAwareness(16);
		} else if (testObject.getTestTags().equals("below100")) {
			weight.setCompatability(5);
		} else if (testObject.getTestTags().equals("NegativeScore")) {

			weight.setAwareness(-10);
			weight.setBroadcastCommunication(15);
		}
	}

	public static void verifyUpdateWeight(WeightageRuleEntity weight,
			SpireTestObject testObject) {

		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);
		setUpUpdateWeight(weight, testObject);

		Logging.info("verifyUpdateWeight Request Input is >>"
				+ gson.toJson(weight));

		Response updateResponse = crmRulesBizServiceConsumer
				.updateWeight(weight);
		if (updateResponse.getStatus() == 200) {

			verifyWeightById(weight, testObject);

		} else if (updateResponse.getStatus() == 400) {

			ErrorEntity error = updateResponse.readEntity(ErrorEntity.class);
			Assert.assertEquals(error.getCode(), "400");
			String errorMessage = error.getMessage();
			if (!errorMessage.equals("Sum of weights not equal to 100%"))
				if (!errorMessage.equals("Invalid awareness : -10.0")) {
					Assert.fail(" Invalid error message :" + errorMessage);
				}

		} else {

			Assert.fail(" Update Weight is failed and status code is :"
					+ updateResponse.getStatus());

		}

	}

	public static WeightageRuleEntity getDefaultWeight() {

		WeightageRuleEntity weightageRuleEntity = new WeightageRuleEntity();

		setUPContext();

		DynamicConfigurationManager configurationManager = SpireConfigManager
				.getInstance();

		weightageRuleEntity.setAwareness(configurationManager.getIntProperty(
				"default.awareness.weight", 0));
		weightageRuleEntity.setBroadcastCommunication(configurationManager
				.getIntProperty("default.broadcast.com.weight", 0));
		weightageRuleEntity.setCompatability(configurationManager
				.getIntProperty("default.compatibility.weight", 0));
		weightageRuleEntity.setConnectFactors(configurationManager
				.getIntProperty("default.connect.factors.weight", 0));
		weightageRuleEntity.setInterestLevel(configurationManager
				.getIntProperty("default.interest.level.weight", 50));
		weightageRuleEntity.setPersonalCommunication(configurationManager
				.getIntProperty("default.personal.com.weight", 50));
		return weightageRuleEntity;
	}

	private static void setUPContext() {
		Context context = new Context();

		context.setRealmName(realmName);
		context.setTenantId(tenantID);
		context.setUserId(userId);
		context.setTokenId(Authorization);
		context.setServiceName("crm_engagementrules_service");
		ContextUtil.setContext(context);
	}

	private static void setUpUpdateWeight(WeightageRuleEntity weight,
			SpireTestObject testObject) {
		weight.setAwareness(0);
		weight.setBroadcastCommunication(5);
		weight.setCompatability(35);
		weight.setConnectFactors(20);
		weight.setInterestLevel((float) 20.5);
		weight.setPersonalCommunication((float) 19.5);

		if (testObject.getTestTags().equals("above100")) {
			weight.setAwareness(10);
		} else if (testObject.getTestTags().equals("below100")) {
			weight.setCompatability(5);
		} else if (testObject.getTestTags().equals("NegativeScore")) {

			weight.setAwareness(-10);
			weight.setBroadcastCommunication(15);
		}

	}

	public static void DeleteWeight() {

		int tenantId = (int) new Date().getTime();
		CrmRulesBizServiceConsumer crmRulesBizServiceConsumer = new CrmRulesBizServiceConsumer(
				endPoint);
		crmRulesBizServiceConsumer.deleteWeight(tenantId);
	}
}

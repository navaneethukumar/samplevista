package com.spire.crm.biz.blob.store.test;

/**
 * @author Manikanta.Y
 */

import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.utils.IdUtils;
import spire.crm.entity.blob.store.entity.beans.BlobStoreEntity;
import spire.crm.entity.blob.store.entity.beans.ErrorEntiry;
import spire.crm.entity.blob.store.entity.beans.ErrorEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.BlobStoreEntityServiceConsumer;

public class BlobStoreValidation  {

	public static Gson gson = new Gson();
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static String endpointUrl=ReadingServiceEndPointsProperties.getServiceEndPoint("BLOB_STORE_ENTITY");

	public static void verifyCreateRule(BlobStoreEntity rule,
			SpireTestObject testObject) {

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);
		setUpCreateProfile(rule, testObject);

		Response createResponse = null;

		Logging.info("verifyCreateRule Request Input is >>" + gson.toJson(rule));
		createResponse = blobStoreEntityServiceConsumer.createRule(rule);
		if (createResponse.getStatus() == 201) {
			if (testObject.getTestTitle().equals("verifyDuplicateRule_LRG")) {
				verifyCreatingDuplicate(rule, testObject);

			} else {
				verifyRuleByName_Tenant(rule, testObject);
			}

		} else if (createResponse.getStatus() == 400) {
			if (testObject.getTestTitle().equals("verifyCreatRule_LRG")) {

				ErrorEntity error = createResponse
						.readEntity(ErrorEntity.class);
				Assert.assertEquals(error.getCode(), "INVALID_PARAMETER");
				String errorMessage = error.getMessage();
				if (!errorMessage.equals("Inavlid or missing rule_name"))
					if (!errorMessage.equals("Inavlid or missing rule_value")) {
						Assert.fail(" invalid error message :" + errorMessage);
					}

			} else {
				Assert.fail(" Create rule is failed and status code is :"
						+ createResponse.getStatus());
			}

		} else {
			Assert.fail(" Create rule is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	public static void verifyCreatingDuplicate(BlobStoreEntity rule,
			SpireTestObject testObject) {
		

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);

		Logging.info(" verifyCreatingDuplicate Request Input is >>"
				+ gson.toJson(rule));

		Response createResponse = blobStoreEntityServiceConsumer
				.createRule(rule);
		if (createResponse.getStatus() == 409) {

			deleteRuleByName_Tenant(rule, testObject);

		} else if (createResponse.getStatus() == 201) {

			Assert.fail(" Duplicate Rule Created " + createResponse.getStatus());
		} else {
			Assert.fail(" Create rule is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	public static void verifyCreateUpdateRule(BlobStoreEntity rule,
			SpireTestObject testObject) {

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);
		setUpCreateProfile(rule, testObject);
		Response createResponse = null;
		Logging.info("verifyCreateRule Request Input is >>" + gson.toJson(rule));
		createResponse = blobStoreEntityServiceConsumer.createRule(rule);
		if (createResponse.getStatus() == 201) {
			validateUpdateRule(rule, testObject);

		} else {
			Assert.fail(" Create rule is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	public static void validateUpdateRule(BlobStoreEntity rule,
			SpireTestObject testObject) {
		


		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);
		setUpUpdateProfile(rule, testObject);
		Response updateResponse = null;
		Logging.info("validateUpdateRule Request Input is >>"
				+ gson.toJson(rule));
		updateResponse = blobStoreEntityServiceConsumer.updateRule(rule);
		if (updateResponse.getStatus() == 200) {

			verifyRuleByName_Tenant(rule, testObject);

		} else if (updateResponse.getStatus() == 404) {

			ErrorEntiry error = updateResponse.readEntity(ErrorEntiry.class);
			Assert.assertEquals(error.getCode(), "NOT_FOUND");
			String errorMessage = error.getMessage();

			if (!errorMessage.contains("Unable to update. Record not found :")) {
				Assert.fail(" invalid error message :" + errorMessage);
			}
		} else {
			Assert.fail(" Update rule is failed and status code is :"
					+ updateResponse.getStatus());

		}

	}

	private static void setUpUpdateProfile(BlobStoreEntity rule,
			SpireTestObject testObject) {

		if (testObject.getTestTitle().equals("updateRule_LRG")) {
			if (!rule.getRuleValue().equals("Static")) {
				rule.setTenantID(7878);
				rule.setRuleName(IdUtils.generateUUID());
				rule.setRuleValue("{\"candidateid\":10}");
			}
		} else {
			if (testObject.getTestTitle().equals("updateRule_SRG")) {
				rule.setRuleName(IdUtils.generateUUID());
			}
			rule.setRuleValue("{\"candidateid\":10}");
		}

	}

	public static void verifyRuleByName_Tenant(BlobStoreEntity rule,
			SpireTestObject testObject) {
		

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);

		Logging.info(" verifyRuleByName_Tenant Request Input is >>"
				+ gson.toJson(rule));

		Response getResponse = blobStoreEntityServiceConsumer.getARule(
				rule.getTenantID(), rule.getRuleName());
		if (getResponse.getStatus() == 200) {

			BlobStoreEntity getRule = getResponse
					.readEntity(BlobStoreEntity.class);
			compareRules(rule, getRule);

			deleteRuleByName_Tenant(rule, testObject);

		} else if (getResponse.getStatus() == 404) {

			ErrorEntiry error = getResponse.readEntity(ErrorEntiry.class);
			Assert.assertEquals(error.getCode(), "NOT_FOUND");
			String errorMessage = error.getMessage();

			if (!errorMessage.contains("Record not found for: "
					+ rule.getTenantID())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get rule is failed and status code is :"
					+ getResponse.getStatus());

		}

	}

	public static void deleteRuleByName_Tenant(BlobStoreEntity rule,
			SpireTestObject testObject) {
		

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);
		Response deleteResponse = null;
		deleteResponse = blobStoreEntityServiceConsumer.deleteRule(
				rule.getTenantID(), rule.getRuleName());
		if (deleteResponse.getStatus() == 200) {

			verifyRuleByName_Tenant(rule, testObject);
		} else if (deleteResponse.getStatus() == 404) {

			ErrorEntiry error = deleteResponse.readEntity(ErrorEntiry.class);
			Assert.assertEquals(error.getCode(), "NOT_FOUND");
			String errorMessage = error.getMessage();

			if (!errorMessage.contains("Record not found for: "
					+ rule.getTenantID())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" delete rule is failed and status code is :"
					+ deleteResponse.getStatus());

		}

	}

	private static void compareRules(BlobStoreEntity rule,
			BlobStoreEntity getRule) {
		Assert.assertEquals(rule.getTenantID(), getRule.getTenantID(),
				"Discrepancy fount in Tenant ID");
		Assert.assertEquals(rule.getRuleValue(), getRule.getRuleValue(),
				"Discrepancy fount in Rule Value");
		Assert.assertEquals(rule.getRuleName(), getRule.getRuleName(),
				"discrepancy fount in Rule Name");

	}

	private static void setUpCreateProfile(BlobStoreEntity rule,
			SpireTestObject testObject) {
		if (testObject.getTestTitle().equals("verifyCreatRule_LRG")) {
			rule.setTenantID(6868);
			if (!rule.getRuleValue().equals("Static")) {
				rule.setRuleName(null);
				rule.setRuleValue("{\"candidateid\":10}");
			}
		} else {
			rule.setTenantID(7878);
			rule.setRuleName(IdUtils.generateUUID());
			rule.setRuleValue(gson.toJson(rule).toString());
		}

	}

	public static void verifygetRuleByTenant(BlobStoreEntity rule,
			SpireTestObject testObject) {

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);
		setUpCreateProfile(rule, testObject);
		Response createResponse = null;
		Logging.info("verifyCreateRule Request Input is >>" + gson.toJson(rule));
		createResponse = blobStoreEntityServiceConsumer.createRule(rule);
		if (createResponse.getStatus() == 201) {
			validateRulesByID(rule, testObject);

		} else {
			Assert.fail(" Create rule is failed and status code is :"
					+ createResponse.getStatus());

		}

	}

	public static void validateRulesByID(BlobStoreEntity rule,
			SpireTestObject testObject) {
		

		BlobStoreEntityServiceConsumer blobStoreEntityServiceConsumer = new BlobStoreEntityServiceConsumer(
				endpointUrl);
		Response getResponse = null;
		Logging.info("validateRulesByID Request Input is >>"
				+ gson.toJson(rule));
		getResponse = blobStoreEntityServiceConsumer.getRuleByTenant(rule
				.getTenantID());
		if (getResponse.getStatus() == 200) {
			BlobStoreEntity[] rulesList = getResponse
					.readEntity(BlobStoreEntity[].class);
			int listLength = rulesList.length;
			Assert.assertTrue(listLength > 0, "rule not found list legnth is :"
					+ listLength);
			Boolean flag = false;
			for (int i = 0; i < rulesList.length; i++) {
				if (rulesList[i].getRuleName().equals(rule.getRuleName())) {
					compareRules(rule, rulesList[i]);
					flag = true;
				}

			}
			Assert.assertTrue(flag, "rule not found ..");

		} else if (getResponse.getStatus() == 404) {

			ErrorEntiry error = getResponse.readEntity(ErrorEntiry.class);
			Assert.assertEquals(error.getCode(), "NOT_FOUND");
			String errorMessage = error.getMessage();

			if (!errorMessage.equals("Record not found for: "
					+ rule.getTenantID())) {
				Assert.fail(" invalid error message :" + errorMessage);
			}

		} else {
			Assert.fail(" get rules by Tenant ID failed and status code is :"
					+ getResponse.getStatus());

		}

	}
}

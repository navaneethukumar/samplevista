package com.spire.crm.biz.crm.rule.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

import spire.crm.biz.rules.beans.Action;
import spire.crm.biz.rules.beans.Condition;
import spire.crm.biz.rules.entites.EngagementRuleEntity;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CrmRulesTestPlan extends TestPlan {

	@DataProvider(name = "CRMRULES_DP")
	public static Iterator<Object[]> getblobStoreInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/rule/test/CrmRuleTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("EngagementRuleEntity",
					EngagementRuleEntity.class);
			entityClazzMap.put("Action",
					Action.class);
			entityClazzMap.put("Condition",
					Condition.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CrmRulesTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
		
	}
	
	/**
	 * List Rule Actions
	 *  
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "listRuleActions_SRG", "SRG" }, dataProvider = "CRMRULES_DP")
	public void listRuleActions_SRG(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmRuleValidation.verifylistRuleActions(rule, testObject);

	}

	/**
	 * Verify Create and Delete Rule
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createDeleteRule_Sanity", "Sanity" }, dataProvider = "CRMRULES_DP")
	public void createDeleteRule_Sanity(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmRuleValidation.setUpCreateRule(rule,action,condition,testObject);
		CrmRuleValidation.verifyCreateRule(rule, testObject);

	}
	
	
	/**
	 * Verify Create
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createRule_LRG", "LRG" }, dataProvider = "CRMRULES_DP")
	public void createRule_LRG(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmRuleValidation.verifyCreateRule(rule, testObject);

	}
	
	/**
	 * get Rule by tenant ID
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetByTenantID_Sanity", "Sanity" }, dataProvider = "CRMRULES_DP")
	public void verifyGetByTenantID_Sanity(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmRuleValidation.setUpCreateRule(rule,action,condition,testObject);
		CrmRuleValidation.verifyCreateRule(rule, testObject);

	}
	
	/**
	 * get Rule by tenant ID
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetByTenantID_LRG", "LRG" }, dataProvider = "CRMRULES_DP")
	public void verifyGetByTenantID_LRG(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		rule.setTenantId(13);
		CrmRuleValidation.verifyRuleById(rule, testObject);

	}
	
	/**
	 * get Rule by tenant ID
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetByID_Name_LRG", "LRG" }, dataProvider = "CRMRULES_DP")
	public void verifyGetByID_Name_LRG(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmRuleValidation.setUpCreateRule(rule,action,condition,testObject);
		CrmRuleValidation.verifyRuleByIdName(rule, testObject);

	}
	
	/**
	 * Update the Rule 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "updateRule_Sanity", "Sanity" }, dataProvider = "CRMRULES_DP")
	public void updateRule_Sanity(SpireTestObject testObject,
			TestData data, EngagementRuleEntity rule,Action action,Condition condition) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		CrmRuleValidation.setUpCreateRule(rule,action,condition,testObject);
		CrmRuleValidation.verifyCreateRule(rule, testObject);

	}

}
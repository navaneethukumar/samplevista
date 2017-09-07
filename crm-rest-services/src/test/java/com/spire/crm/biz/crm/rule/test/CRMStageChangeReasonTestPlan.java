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

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CRMStageChangeReasonTestPlan extends TestPlan {

	@DataProvider(name = "STAGEREASON_DP")
	public static Iterator<Object[]> getblobStoreInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/rule/test/CRMStageChangeReasonData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CRMStageChangeReasonTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;

	}

	/**
	 *Add reasons 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "adddReason_Sanity", "Sanity" }, dataProvider = "STAGEREASON_DP")
	public void adddReason_Sanity(SpireTestObject testObject, TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		CRMStageChangeReasonValidation validator = new CRMStageChangeReasonValidation();
		validator.verifyAddReason(testObject, data);

	}
	
	@Test(groups = { "listReason_Sanity", "SRG" }, dataProvider = "STAGEREASON_DP")
	public void listReason_Sanity(SpireTestObject testObject, TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());

		CRMStageChangeReasonValidation validator = new CRMStageChangeReasonValidation();
		validator.verifyListReason(testObject, data);

	}

}
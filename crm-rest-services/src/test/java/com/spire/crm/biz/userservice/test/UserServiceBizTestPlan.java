package com.spire.crm.biz.userservice.test;

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

/**
 * @author Manikhanta Y
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class UserServiceBizTestPlan extends TestPlan {

	UserServiceBizValidation userServiceBizValidation = new UserServiceBizValidation();

	@DataProvider(name = "USER_DP")
	public static Iterator<Object[]> getblobStoreInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/userservice/test/UserServiceBiz_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					UserServiceBizTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}
	
	

	/*
	 *
	 *
	 *
	 */

	@Test(groups = { "verfiyGetUserDetails_Sanity", "Sanity" }, dataProvider = "USER_DP")
	public void verfiyGetUserDetails_Sanity(SpireTestObject testObject,
			TestData data) {

		Logging.log("Test Case Execution started >>> listCampaign_Sanity !!!"
				+ data.getTestSteps());

		userServiceBizValidation.validateUpdateUserDetails(data, 12);

	}

}

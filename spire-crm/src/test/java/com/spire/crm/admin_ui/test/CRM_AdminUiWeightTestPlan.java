package com.spire.crm.admin_ui.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.admin_ui.EngagementScoreWeightUtil;
import com.spire.crm.admin_ui.EngagementWeight;
import com.spire.crm.pages.AdminLoginPage;


@Test(groups = { "SRG" }, retryAnalyzer = TestRetryAnalyzer.class)
public class CRM_AdminUiWeightTestPlan extends TestPlan {
	@DataProvider(name = "CRM_AdminUIWeightTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/admin_ui/test/CRM_AdminUIWeightTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("EngagementWeight", EngagementWeight.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(CRM_AdminUiWeightTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}


	@Test(groups = { "verifyEngagementWeightFields"}, dataProvider = "CRM_AdminUIWeightTestData",priority=12)
	public void verifyEngagementWeightFields(SpireTestObject testObject,EngagementWeight weight)  {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		EngagementScoreWeightUtil admin_ui = new EngagementScoreWeightUtil(this.driver, false);

		admin_ui.verifyEngagementWeightFields();

	}
	@Test(groups = { "updateWeight"}, dataProvider = "CRM_AdminUIWeightTestData",priority=13)
	public void updateWeight(SpireTestObject testObject,EngagementWeight weight)  {

		AdminLoginPage loginPageUtil = new AdminLoginPage(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		EngagementScoreWeightUtil admin_ui = new EngagementScoreWeightUtil(this.driver, false);

		admin_ui.updateWeight(weight);

	}
	
}

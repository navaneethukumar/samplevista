package com.spire.crm.commons.config;

/**
 * @author Santosh.C
 */

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.config.ConfigHelperNew;
import com.spire.config.ConfigPojo;
import com.spire.crawl.helper.CRMTestPlan;

@Test(groups = { "configTests" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ConfigTestPlan extends CRMTestPlan {

	@DataProvider(name = "configNewTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/config/ConfigTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ConfigPojo", ConfigPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					ConfigTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "configTest_Sanity", "Sanity" }, dataProvider = "configNewTestData")
	public void configTest_Sanity(SpireTestObject testObject, ConfigPojo config)
			throws Exception {

		ConfigHelperNew.configUtil(testObject, config);

	}

	@Test(groups = { "configTest_SRG", "SRG" }, dataProvider = "configNewTestData")
	public void configTest_SRG(SpireTestObject testObject, ConfigPojo config)
			throws Exception {

		ConfigHelperNew.configUtil(testObject, config);

	}

	@Test(groups = { "configTest_LRG", "LRG" }, dataProvider = "configNewTestData")
	public void configTest_LRG(SpireTestObject testObject, ConfigPojo config)
			throws Exception {

		ConfigHelperNew.configUtil(testObject, config);

	}

}

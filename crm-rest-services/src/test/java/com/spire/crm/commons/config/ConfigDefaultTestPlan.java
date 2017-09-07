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
import com.spire.config.ConfigHelper;
import com.spire.config.ConfigPojo;
import com.spire.crawl.helper.CRMTestPlan;

@Test(groups = { "configTests" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ConfigDefaultTestPlan extends CRMTestPlan {

	@DataProvider(name = "configTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/config/ConfigDefaultTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ConfigPojo", ConfigPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					ConfigDefaultTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "configDefault_Sanity", "Sanity" }, dataProvider = "configTestData")
	public void configDefault_Sanity(SpireTestObject testObject,
			ConfigPojo config) throws Exception {

		ConfigHelper.configDefaultUtil(testObject, config);

	}

	@Test(groups = { "configDefault_SRG", "SRG" }, dataProvider = "configTestData")
	public void configDefault_SRG(SpireTestObject testObject, ConfigPojo config)
			throws Exception {

		ConfigHelper.configDefaultUtil(testObject, config);

	}

	@Test(groups = { "configDefault_LRG", "LRG" }, dataProvider = "configTestData")
	public void configDefault_LRG(SpireTestObject testObject, ConfigPojo config)
			throws Exception {

		ConfigHelper.configDefaultUtil(testObject, config);

	}

}

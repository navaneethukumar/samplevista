package com.spire.crm.commons.config;

/**
 * @author Santosh.C
 */

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.config.ConfigPojo;
import com.spire.crawl.helper.CRMTestPlan;

import spire.commons.Context;
import spire.commons.config.SpireConfigManager;
import spire.commons.utils.ContextUtil;

@Test(groups = { "configTests" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ConfigNegative extends CRMTestPlan {

	private static boolean isConfigInitialzed = true;

	@DataProvider(name = "configTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/config/ConfigNegativeTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ConfigPojo", ConfigPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					ConfigNegative.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "interchange_Initialization", "LRG" }, dataProvider = "configTestData")
	public void interchange_Initialization(SpireTestObject testObject,
			ConfigPojo config) throws Exception {

		if (isConfigInitialzed) {
			//System.setProperty("deploy-env", "test");
			Context context = new Context();
			context.setServiceName(config.getServiceName());
			context.setTenantId(config.getAppName());

			ContextUtil.setContext(context);
			// isConfigInitialzed = false;
		}
		if (config.getPropertyType().equalsIgnoreCase("String")) {

			String value = SpireConfigManager.getInstance().getStringProperty(
					config.getParameter(), config.getDefaultValue());
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", "\""));
			Assertion.assertTrue(
					value.trim().equalsIgnoreCase(
							config.getOutputValue().trim()
									.replace("\"\"", "\"")),
					"validation failed");

		}

	}

	@Test(groups = { "blank_Initialization", "LRG" }, dataProvider = "configTestData")
	public void blank_Initialization(SpireTestObject testObject,
			ConfigPojo config) throws Exception {

		try {
			//System.setProperty("deploy-env", "test");
			Context context = new Context();
			context.setServiceName("");
			context.setTenantId("");

			ContextUtil.setContext(context);

			if (config.getPropertyType().equalsIgnoreCase("String")) {

				String value = SpireConfigManager.getInstance()
						.getStringProperty(config.getParameter(),
								config.getDefaultValue());
				Logging.log("value from DB is :" + value
						+ " && value expected is:"
						+ config.getOutputValue().replace("\"\"", "\""));
				Assertion.assertTrue(
						value.trim().equalsIgnoreCase(
								config.getOutputValue().trim()
										.replace("\"\"", "\"")),
						"validation failed");
			}

			Assertion
					.assertTrue(
							false,
							"Exception should throw when we initialize with blank field , but it did not thrown any exception");
		} catch (Exception e) {

			Logging.log("Exception should throw when we initialize with blank field : ");

		}

	}

	@Test(groups = { "validate_RetrieveFormat", "Sanity" }, dataProvider = "configTestData")
	public void validate_RetrieveFormat(SpireTestObject testObject,
			ConfigPojo config) throws Exception {
		try {
			if (isConfigInitialzed) {
				//System.setProperty("deploy-env", "test");
				Context context = new Context();
				context.setServiceName(config.getServiceName());
				context.setTenantId(config.getAppName());

				ContextUtil.setContext(context);
				isConfigInitialzed = false;
			}
			ConfigNegative con = new ConfigNegative();
			con.checkRetrieveFormat(config);

		} catch (Exception e) {
			if (!config.getException().equalsIgnoreCase("true")) {
				Logging.log("Test Case failed due to exception");
				Assertion.assertTrue(
						false,
						"Test Case failed due to exception :: "
								+ e.getMessage());
			}
		}
	}

	@Test(groups = { "initializeGlobal", "Sanity" }, dataProvider = "configTestData")
	public void initializeGlobal(SpireTestObject testObject, ConfigPojo config)
			throws Exception {
		try {
			if (isConfigInitialzed) {
				//System.setProperty("deploy-env", "test");
				Context context = new Context();
				context.setServiceName(config.getServiceName());
				context.setTenantId(config.getAppName());

				ContextUtil.setContext(context);
				isConfigInitialzed = false;
			}
			ConfigNegative con = new ConfigNegative();
			con.checkRetrieveFormat(config);

		} catch (Exception e) {
			if (!config.getException().equalsIgnoreCase("true")) {
				Logging.log("Test Case failed due to exception");
				Assertion.assertTrue(
						false,
						"Test Case failed due to exception :: "
								+ e.getMessage());
			}
		}
	}

	private void checkRetrieveFormat(ConfigPojo config) {
		if (config.getPropertyType().equalsIgnoreCase("INT")) {

			int value = SpireConfigManager.getInstance().getIntProperty(
					config.getParameter(),
					Integer.parseInt(config.getDefaultValue()));
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", ""));

			Assertion.assertTrue(
					value == Integer.parseInt(config.getOutputValue().replace(
							"\"\"", "")), "validation failed");

		} else if (config.getPropertyType().equalsIgnoreCase("String")) {

			String value = SpireConfigManager.getInstance().getStringProperty(
					config.getParameter(), config.getDefaultValue());
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", ""));
			Assertion
					.assertTrue(
							value.trim().equalsIgnoreCase(
									config.getOutputValue().trim()
											.replace("\"\"", "")),
							"validation failed");
		} else if (config.getPropertyType().equalsIgnoreCase("Long")) {

			long value = SpireConfigManager.getInstance().getLongProperty(
					config.getParameter(),
					Long.parseLong(config.getDefaultValue()));
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", ""));
			Assertion.assertTrue(
					value == Long.parseLong(config.getOutputValue().replace(
							"\"\"", "")), "validation failed");

		} else if (config.getPropertyType().equalsIgnoreCase("Float")) {

			float value = SpireConfigManager.getInstance().getFloatProperty(
					config.getParameter(),
					Float.parseFloat(config.getDefaultValue().replace("\"\"",
							"")));
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", ""));
			Assertion.assertTrue(
					value == Float.parseFloat(config.getOutputValue().replace(
							"\"\"", "")), "validation failed");

		} else if (config.getPropertyType().equalsIgnoreCase("Double")) {

			double value = SpireConfigManager.getInstance().getDoubleProperty(
					config.getParameter(),
					Double.parseDouble(config.getDefaultValue()));
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", ""));
			Assertion
					.assertTrue(value == Double.parseDouble(config
							.getOutputValue().replace("\"\"", "")),
							"validation failed");

		} else if (config.getPropertyType().equalsIgnoreCase("Boolean")) {

			boolean value = SpireConfigManager.getInstance()
					.getBooleanProperty(config.getParameter(),
							Boolean.parseBoolean(config.getDefaultValue()));
			Logging.log("value from DB is :" + value + " && value expected is:"
					+ config.getOutputValue().replace("\"\"", ""));
			Assertion
					.assertTrue(value == Boolean.parseBoolean(config
							.getOutputValue().replace("\"\"", "")),
							"validation failed");

		}
	}

}

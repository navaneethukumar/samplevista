package com.spire.config;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;

import spire.commons.Context;
import spire.commons.config.SpireConfigManager;
import spire.commons.config.impl.DynamicConfigurationManager;
import spire.commons.utils.ContextUtil;

public class ConfigHelperNew {

	private static boolean isConfigInitialzed = true;

	public static void logInfo(SpireTestObject testObject, ConfigPojo config) {

		String[] tokens = config.getTestSteps().split("#");

		for (int i = 0; i < tokens.length; i++) {

			if (i == 1) {

				System.out
						.println("----------------------------------------------------");

			}

			Logging.log(tokens[i]);

			if (i == tokens.length - 1) {

				System.out
						.println("----------------------------------------------------");
				Logging.log("");

			}
		}

		Logging.log("Application name used :" + config.getAppName());
		Logging.log("Service name used :" + config.getServiceName());
		Logging.log("IP address used :" + config.getIpAddress());
		Logging.log("Parameter is used :" + config.getParameter());

	}

	public static void configUtil(SpireTestObject testObject, ConfigPojo config) {
		try {
			ConfigHelperNew.logInfo(testObject, config);

			if (isConfigInitialzed) {
				System.setProperty("deploy-env", "test");
				Context context = new Context();
				context.setServiceName(config.getServiceName());
				context.setTenantId(config.getAppName());

				ContextUtil.setContext(context);
			}

			if (config.getPropertyType().equalsIgnoreCase("INT")) {

				DynamicConfigurationManager mgr = SpireConfigManager
						.getInstance();
				int value = SpireConfigManager.getInstance().getIntProperty(
						config.getParameter());
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());

				Assertion.assertTrue(
						value == Integer.parseInt(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("String")) {

				String value = SpireConfigManager.getInstance()
						.getStringProperty(config.getParameter());
				Logging.log("value from DB is :" + value
						+ " && value expected is:"
						+ config.getOutputValue().replace("\"\"", "\""));

				if (config.getOutputValue().equalsIgnoreCase("space")) {
					Assertion.assertTrue(value.trim().equalsIgnoreCase(""),
							"validation failed");
				} else {
					Assertion.assertTrue(
							value.trim().equalsIgnoreCase(
									config.getOutputValue().trim()
											.replace("\"\"", "\"")),
							"validation failed");
				}

			} else if (config.getPropertyType().equalsIgnoreCase("Long")) {

				long value = SpireConfigManager.getInstance().getLongProperty(
						config.getParameter());
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Long.parseLong(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("Float")) {

				float value = SpireConfigManager.getInstance()
						.getFloatProperty(config.getParameter());
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Float.parseFloat(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("Double")) {

				double value = SpireConfigManager.getInstance()
						.getDoubleProperty(config.getParameter());
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Double.parseDouble(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("Boolean")) {

				boolean value = SpireConfigManager.getInstance()
						.getBooleanProperty(config.getParameter());
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Boolean.parseBoolean(config.getOutputValue()),
						"validation failed");

			}
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
}

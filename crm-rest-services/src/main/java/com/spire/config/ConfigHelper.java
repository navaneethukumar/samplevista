package com.spire.config;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;

import spire.commons.Context;
import spire.commons.config.SpireConfigManager;
import spire.commons.config.impl.DynamicConfigurationManager;
import spire.commons.utils.ContextUtil;


public class ConfigHelper {

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

	public static void configDefaultUtil(SpireTestObject testObject,
			ConfigPojo config) {
		try {
			ConfigHelper.logInfo(testObject, config);

			if (isConfigInitialzed) {

				Context context = new Context();
				context.setServiceName(config.getServiceName());
				context.setTenantId(config.getAppName());

				ContextUtil.setContext(context);
			}
			

			if (config.getPropertyType().equalsIgnoreCase("INT")) {

				DynamicConfigurationManager mgr = SpireConfigManager
						.getInstance();
				int value = SpireConfigManager.getInstance().getIntProperty(
						config.getParameter(),
						Integer.parseInt(config.getDefaultValue()));
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				System.out.println(value);

				Assertion.assertTrue(
						value == Integer.parseInt(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("String")) {

				String value = SpireConfigManager.getInstance()
						.getStringProperty(config.getParameter(),
								config.getDefaultValue());
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
						config.getParameter(),
						Long.parseLong(config.getDefaultValue()));
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Long.parseLong(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("Float")) {

				float value = SpireConfigManager.getInstance()
						.getFloatProperty(config.getParameter(),
								Float.parseFloat(config.getDefaultValue()));
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Float.parseFloat(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("Double")) {

				double value = SpireConfigManager.getInstance()
						.getDoubleProperty(config.getParameter(),
								Double.parseDouble(config.getDefaultValue()));
				Logging.log("value from DB is :" + value
						+ " && value expected is:" + config.getOutputValue());
				Assertion.assertTrue(
						value == Double.parseDouble(config.getOutputValue()),
						"validation failed");

			} else if (config.getPropertyType().equalsIgnoreCase("Boolean")) {

				boolean value = SpireConfigManager.getInstance()
						.getBooleanProperty(config.getParameter(),
								Boolean.parseBoolean(config.getDefaultValue()));
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

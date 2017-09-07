package com.spire.crm.biz.crm.notification.web.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.crm.notification.beans.NotificationBean;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

@Test(groups = { "SANITY" },retryAnalyzer = TestRetryAnalyzer.class)
public class CrmNotificationTestPlan extends TestPlan {
	CrmNotificationValidation helper = new CrmNotificationValidation();
	@DataProvider(name = "NOTIFICATION_DP")
	public static Iterator<Object[]> getNotificationInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/crm/notification/web/test/CrmNotificationTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("NotificationBean", NotificationBean.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					CrmNotificationTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	/**
	 * Verify Create Notification
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreateNotification_Sanity", "Sanity" }, dataProvider = "NOTIFICATION_DP")
	public void verifyCreateNotification_Sanity(SpireTestObject testObject,
			TestData data, NotificationBean notification) {
		
		Logging.log("Test Steps >>" + data.getTestSteps());
		helper.verifyCreateNotification(notification, testObject,data);

	}
	
	/**
	 * Verify Update Notification
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyUpdateNotification_Sanity", "Sanity" }, dataProvider = "NOTIFICATION_DP")
	public void verifyUpdateNotification_Sanity(SpireTestObject testObject,
			TestData data, NotificationBean notification) {
		
		Logging.log("Test Steps >>" + data.getTestSteps());
		helper.verifyCreateNotification(notification, testObject,data);

	}
	
	/**
	 * Bulk Updated Notification
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyBulkUpdateNotification_Sanity", "Sanity" }, dataProvider = "NOTIFICATION_DP")
	public void verifyBulkUpdateNotification_Sanity(SpireTestObject testObject,
			TestData data, NotificationBean notification) {
		
		Logging.log("Test Steps >>" + data.getTestSteps());
		helper.verifyBulkUpdate(notification, testObject,data);

	}
	
	@Test(groups = { "verifyNotificationByType_Sanity", "Sanity" }, dataProvider = "NOTIFICATION_DP")
	public void verifyNotificationByType_Sanity(SpireTestObject testObject,
			TestData data, NotificationBean notification) {
		
		Logging.log("Test Steps >>" + data.getTestSteps());
		helper.verifyStausOfNotificationByType(notification, testObject,data);

	}
	
}
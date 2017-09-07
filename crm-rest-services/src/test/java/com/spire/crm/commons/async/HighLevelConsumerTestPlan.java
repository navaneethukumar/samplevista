package com.spire.crm.commons.async;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.async.HighLevelKafkaApplication;
import com.spire.async.ProdConsPojo;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crawl.helper.CRMTestPlan;

public class HighLevelConsumerTestPlan extends CRMTestPlan {

	@DataProvider(name = "KafkaTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/async/asyncSendRecMsgTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ProdConsPojo", ProdConsPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					AsyncMsgSendReceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Create topic, send msgs and read all messages and few, verify that
	 * consumed message is same as produced message
	 */
	@Test(groups = { "testHighLevelConsumer" }, dataProvider = "KafkaTestData")
	public void testHighLevelConsumer(SpireTestObject testObject,
			ProdConsPojo data) throws Exception {

		HighLevelKafkaApplication highlevel = new HighLevelKafkaApplication();

		highlevel.testHighLevelConsumer(data);

	}

}

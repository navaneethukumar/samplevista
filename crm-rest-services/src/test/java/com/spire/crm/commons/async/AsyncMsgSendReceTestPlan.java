package com.spire.crm.commons.async;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.async.KafkaApplication;
import com.spire.async.ProdConsPojo;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crawl.helper.CRMTestPlan;

import spire.commons.async.Consumer;

@Test(retryAnalyzer = TestRetryAnalyzer.class)
public class AsyncMsgSendReceTestPlan extends CRMTestPlan {

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
	 * Create topic send msgs, consume and get offset and lag
	 */
	@Test(groups = { "testProducerConsumer", "Sanity" }, dataProvider = "KafkaTestData")
	public void testProducerConsumer(SpireTestObject testObject,
			ProdConsPojo data) throws Exception {

		KafkaApplication.setUp();

		KafkaApplication.sendMessages(data);

		Consumer consume = KafkaApplication.readMessages(data);

		Logging.log("Expected offset: " + data.getExpectedOffset()
				+ ",,Displayed: " + consume.getOffset());
		Logging.log("Expected Lag: " + data.getExpectedLag() + ",,Displayed: "
				+ consume.lag());
		if (!data.getExpectedOffset().equals("null")) {
			Assert.assertEquals(data.getExpectedOffset(), consume.getOffset()
					.toString(), "Showing wrong Offset!!");
			Assert.assertEquals(Long.parseLong(data.getExpectedLag()),
					consume.lag(), "Showing wrong Lag!!");
		}
	}

	/**
	 * Get Current Lead broker
	 */
	@Test(groups = { "getCurrentLeadBroker", "LRG" }, dataProvider = "KafkaTestData")
	public void getCurrentLeadBroker(SpireTestObject testObject,
			ProdConsPojo data) throws Exception {

		KafkaApplication.setUp();

		KafkaApplication.sendMessages(data);
		Consumer consume = KafkaApplication.readMessages(data);

		Assert.assertTrue(
				consume.getCurrentLeadBroker().toString().length() > 0,
				"Not showing Lead Broker!!");

		// Assert.assertTrue(
		// consume.getCurrentLeadBroker().toString()
		// .contains("host:192.168.2.45,port:9092"),
		// "Not showing Lead Broker!!");
	}

	/**
	 * Send message in Map
	 */
	@Test(groups = { "sendMsg_KeyValue", "Sanity" }, dataProvider = "KafkaTestData")
	public void sendMsg_KeyValue(SpireTestObject testObject, ProdConsPojo data)
			throws Exception {

		KafkaApplication.setUp();

		KafkaApplication.sendMessages_Key_Map(data);

		Consumer consume = KafkaApplication.readMessages(data);

		Assert.assertEquals(consume.getOffset().toString(), data
				.getExpectedOffset().toString(), "Showing wrong Offset!!");
		Assert.assertEquals(consume.lag(),
				Long.parseLong(data.getExpectedLag()), "Showing wrong Lag!!");
		Thread.sleep(20000);
	}

	/**
	 * Send message in Map
	 */
	@Test(groups = { "sendMsg_KeyValueList", "Sanity" }, dataProvider = "KafkaTestData")
	public void sendMsg_KeyValueList(SpireTestObject testObject,
			ProdConsPojo data) throws Exception {

		KafkaApplication.setUp();

		try {
			KafkaApplication.sendMessages_Key_MapList(data);
		} catch (Exception e) {
			Assert.fail("Not able to send message as Key and List!!");
		}
		Consumer consume = KafkaApplication.readMessages(data);

		Assert.assertEquals(consume.getOffset().toString(), data
				.getExpectedOffset().toString(), "Showing wrong Offset!!");
		Assert.assertEquals(consume.lag(),
				Long.parseLong(data.getExpectedLag()), "Showing wrong Lag!!");
	}
}

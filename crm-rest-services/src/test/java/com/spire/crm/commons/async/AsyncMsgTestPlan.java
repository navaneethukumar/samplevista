package com.spire.crm.commons.async;

/**
 * @author Santosh.C
 */

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.async.Async;
import com.spire.async.KafkaApplication;
import com.spire.base.controller.Logging;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crawl.helper.CRMTestPlan;

import spire.commons.async.TopicManager;
import spire.commons.async.exception.TopicException;

public class AsyncMsgTestPlan extends CRMTestPlan {

	@DataProvider(name = "KafkaTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/async/asyncMsgTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("Async", Async.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					AsyncMsgTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Create a topic and list the topics, Created topic should come in the list
	 */
	@Test(groups = { "createTopic_List", "Sanity" }, dataProvider = "KafkaTestData")
	public void createTopic_List(SpireTestObject testObject, Async async)
			throws Exception {
		String topicName = async.getTopicName() + System.currentTimeMillis();

		KafkaApplication.setUp();
		
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName, Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);

		boolean isTopicExist = false;
		List<String> list = topic.listTopics();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {

			String string = (String) iterator.next();
			if (string.contains(topicName)) {
				isTopicExist = true;
				break;
			}

		}

		Assert.assertTrue(isTopicExist, "Created topic is not showing in List");

	}

	/**
	 * Create a topic and Describe the topic, Given topic should be described as
	 * Topic name,Leader,Replicas, In Sync Replicas
	 */
	@Test(groups = { "createTopic_Describe", "Sanity" }, dataProvider = "KafkaTestData")
	public void createTopic_Describe(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		
		String topicName = async.getTopicName() + System.currentTimeMillis();
		int partitions = Integer.parseInt(async.getPaartitions());
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + partitions
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName, partitions,
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);
		List<String> describe = topic.describeTopic(topicName);
		Logging.log("Describe: " + describe);
		Assert.assertTrue(
				describe.toString().trim()
						.contains("Partition: " + (partitions - 1)),
				"Describe topic is not showing all the partitions!!");
	}

	/**
	 * Create a topic by giving blank topic name, Expected: It should throw
	 * exception
	 */
	@Test(groups = { "createTopic_Blank", "LRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void createTopic_Blank(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + async.getTopicName()
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(async.getTopicName().replace("blank", ""),
				Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
	}

	/**
	 * Describe a topic by giving invalid topic name, Expected: It should throw
	 * exception
	 */
	@Test(groups = { "describeInvalid", "SRG" }, dataProvider = "KafkaTestData")
	public void describeInvalid(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		Logging.log("Trying to describe by invalid topic name: "
				+ async.getTopicName());
		TopicManager topic = KafkaApplication.createTopic();

		List<String> describe = topic.describeTopic("test");
		Logging.log("Describe invalid: " + describe);
		Logging.log("Describe invalid size: " + describe.size());

		List<String> describe1 = topic.describeTopic("test");
		Logging.log("Describe Valid: " + describe1);
		Logging.log("Describe Valid size: " + describe1.size());
		// Assert.assertEquals(0, describe.size(),
		// "Describing by invalid Topic name is showing description!!");
	}

	/**
	 * Create a duplicate topic (with existing topic name), Expected: It should
	 * throw Topic Exception
	 */
	@Test(groups = { "createTopic_Duplicate", "SRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void createTopic_Duplicate(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		TopicManager topic = KafkaApplication.createTopic();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());
		topic.createTopic(async.getTopicName(),
				Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);
		System.out
				.println("Again Creating topic with same details: Topic Name: "
						+ topicName + " Number of Partitions: "
						+ async.getPaartitions() + " Number of Replicas: "
						+ async.getReplicas());
		topic.createTopic(topicName, Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
	}

	/**
	 * Create a Topic with zero partition, Expected: It should throw Topic
	 * Exception
	 */
	@Test(groups = { "createTopic_Negative", "LRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void createTopic_Negative(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + async.getTopicName()
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());
		if (async.getTopicName().equals("null") == true) {
			topic.createTopic(null, Integer.parseInt(async.getPaartitions()),
					Integer.parseInt(async.getReplicas()));
		} else {
			topic.createTopic(
					async.getTopicName() + System.currentTimeMillis(),
					Integer.parseInt(async.getPaartitions()),
					Integer.parseInt(async.getReplicas()));
		}
	}

	/**
	 * Create a topic with more partitions and describe the topic and get the
	 * number of Partitions,, Expected: It should give number of partitions
	 */
	@Test(groups = { "describeTopic_Partitions", "Sanity" }, dataProvider = "KafkaTestData")
	public void describeTopic_Partitions(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName, Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(3000);
		List<String> describe = topic.describeTopic(topicName);
		Assert.assertEquals(describe.size(),
				Integer.parseInt(async.getPaartitions()),
				"Showing wrong number of partitions when we describe a topic");
	}

	/**
	 * Create a topic with more partitions and describe the topic and get the
	 * number of Partitions,, Expected: It should throw exception
	 */
	@Test(groups = { "createTopic_MoreReplicas", "SRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void createTopic_MoreReplicas(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName + System.currentTimeMillis(),
				Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
	}

	/**
	 * Describe the topic and check the parameters displayed,, Expected: "It
	 * should describe the topic as Partition Id, Leader, Replicas in correct
	 * format"
	 */
	@Test(groups = { "describeValidate", "Sanity" }, dataProvider = "KafkaTestData")
	public void describeValidate(SpireTestObject testObject, Async async)
			throws Exception {

		KafkaApplication.setUp();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		int partitions = Integer.parseInt(async.getPaartitions());
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName, partitions,
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);
		List<String> describe = topic.describeTopic(topicName);
		Assert.assertTrue(
				describe.toString().trim()
						.contains("Partition: " + (partitions - 1)),
				"Describe topic is not showing all the partitions!!");

	}

	/**
	 * amend topic by giving zero partitions,, Expected:
	 * "It should throw exception"
	 */
	@Test(groups = { "amend_ZeroPartition", "LRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void amend_ZeroPartition(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName + System.currentTimeMillis(),
				Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);
		topic.amendTopic(topicName, 0);
	}

	/**
	 * Amend Partitions(Increase number of partitions),, Expected:
	 * "It should give amended number of partitions"
	 */
	@Test(groups = { "amend_IncreaseValidate", "Sanity" }, dataProvider = "KafkaTestData")
	public void amend_IncreaseValidate(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName, Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);
		topic.amendTopic(topicName, 4);
		Thread.sleep(5000);
		List<String> describe = topic.describeTopic(topicName);
		Assert.assertEquals(describe.size(), 4, "Amend is not happening!!");

	}

	/**
	 * Amend Partitions(Decrease number of partitions),, Expected:
	 * "It should give amended number of partitions"
	 */
	@Test(groups = { "amend_DecreaseValidate", "LRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void amend_DecreaseValidate(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		String topicName = async.getTopicName() + System.currentTimeMillis();
		TopicManager topic = KafkaApplication.createTopic();
		Logging.log("Creating topic with: Topic Name: " + topicName
				+ " Number of Partitions: " + async.getPaartitions()
				+ " Number of Replicas: " + async.getReplicas());

		topic.createTopic(topicName + System.currentTimeMillis(),
				Integer.parseInt(async.getPaartitions()),
				Integer.parseInt(async.getReplicas()));
		Thread.sleep(2000);
		topic.amendTopic(topicName, 2);

	}

	/**
	 * Try to amend the topic by giving invalid topic name,, Expected:
	 * "It should give proper exception message by saying given topic does not exist"
	 */
	@Test(groups = { "amend_InvalidTopicName", "SRG" }, dataProvider = "KafkaTestData", expectedExceptions = TopicException.class)
	public void amend_InvalidTopicName(SpireTestObject testObject, Async async)
			throws Exception {
		
		KafkaApplication.setUp();
		TopicManager topic = KafkaApplication.createTopic();
		topic.amendTopic(async.getTopicName(), 4);
	}
}

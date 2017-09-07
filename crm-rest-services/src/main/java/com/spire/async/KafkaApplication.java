package com.spire.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.curator.test.TestingServer;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.spire.base.controller.Logging;

import kafka.server.KafkaServerStartable;
import spire.commons.Context;
import spire.commons.async.ConnectionFactory;
import spire.commons.async.Consumer;
import spire.commons.async.Message;
import spire.commons.async.Producer;
import spire.commons.async.TopicManager;
import spire.commons.async.exception.ConnectionException;
import spire.commons.async.exception.ConsumerException;
import spire.commons.async.exception.ProducerException;
import spire.commons.async.exception.PropertiesException;
import spire.commons.async.exception.TopicException;
import spire.commons.async.impl.KafkaConsumer;
import spire.commons.async.impl.KafkaProducer;
import spire.commons.async.impl.KafkaTopicManager;
import spire.commons.async.utils.AdminSettings;
import spire.commons.async.utils.ConsumerSettings;
import spire.commons.async.utils.EventCallback;
import spire.commons.async.utils.ProducerSettings;
import spire.commons.utils.ContextUtil;

public class KafkaApplication {

	public static void setUp() {

		Context context = new Context();
		context.setServiceName("async-events");
		context.setTenantId("6099");
		ContextUtil.setContext(context);

	}

	public static void main(String[] args) throws TopicException,
			ConnectionException, PropertiesException, ConsumerException,
			ProducerException, InterruptedException {

		Context context = new Context();
		context.setServiceName("async-events");
		context.setTenantId("6099");
		ContextUtil.setContext(context);

		AdminSettings adminSettings = loadAdminSettings();
		ProducerSettings producerSettings = loadProducerSettings(adminSettings);
		ConsumerSettings consumerSettings = loadConsumerSettings();

		TopicManager topicManager = new KafkaTopicManager(adminSettings);

		Scanner sc = new Scanner(System.in);
		String topic = sc.next();
		Integer partitions = sc.nextInt();
		Integer replica = sc.nextInt();
		consumerSettings.setTopic(topic);
		producerSettings.setTopic(topic);

		topicManager.createTopic(topic, partitions, replica);

		Producer producer = new KafkaProducer(producerSettings);
		Consumer consumer = new KafkaConsumer(consumerSettings);

		boolean flag = true;
		int i = 1;

		while (flag) {

			String cont = sc.next();
			if (cont.toLowerCase().equals("yes")) {
				String operation = sc.next();
				Integer num = sc.nextInt();
				if (operation.toLowerCase().equals("send")) {
					producer.connect();
					for (int j = 0; j < num; j++) {
						Message event = new Message("message number:" + i,
								"MESSAGE");
						producer.send(event, new EventCallback() {

							@Override
							public void onComplete(RecordMetadata metadata,
									Exception exception) {
								System.out.println("call back completed "
										+ exception);
							}
						});
						i++;
					}
					producer.close();
				} else {
					consumer.connect();
					Thread.sleep(1000);
					consumer.close();
				}
			} else {
				flag = false;
			}
		}

		sc.close();
	}

	public static ConsumerSettings loadConsumerSettings() {
		ConsumerSettings consumerSettings = new ConsumerSettings();
		consumerSettings
				.setBootstrapServers(Arrays.asList("192.168.2.75:9092"));
		consumerSettings.setPartitionId(0);
		consumerSettings.setGroupId("demoGroup");
		consumerSettings.setClientId("test");
		consumerSettings.setSeedBrokers(Arrays.asList("192.168.2.75"));
		consumerSettings.setZookeeperHost("192.168.2.75");
		consumerSettings.setZookeeperPort(9092);
		return consumerSettings;
	}

	public static ProducerSettings loadProducerSettings(
			AdminSettings adminSettings) {
		ProducerSettings producerSettings = new ProducerSettings();
		producerSettings.setBrokerList(Arrays.asList("192.168.2.75:9092",
				"192.168.2.75:9093"));
		producerSettings
				.setBootstrapServers(Arrays.asList("192.168.2.75:9092"));
		producerSettings.setAdminSettings(adminSettings);
		producerSettings.setProducerId("test-sample-producer-1");
		return producerSettings;
	}

	public static AdminSettings loadAdminSettings() {
		AdminSettings adminSettings = new AdminSettings();
		adminSettings.setZookeeperServer("192.168.2.75:2181");
		adminSettings.setClientId("test");
		return adminSettings;
	}

	public void tearDown(KafkaServerStartable kafkaServerStartable,
			TestingServer zkTestServer) {

		kafkaServerStartable.shutdown();
		try {
			zkTestServer.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendMessages(ProdConsPojo data)
			throws NumberFormatException, TopicException, ConnectionException,
			PropertiesException, ProducerException, Exception {

		String topic = data.getTopic() + System.currentTimeMillis();
		data.setTopic(topic);

		AdminSettings adminSettings = loadAdminSettings();
		ProducerSettings producerSettings = loadProducerSettings(adminSettings);
		ConsumerSettings consumerSettings = loadConsumerSettings();

		TopicManager topicManager = ConnectionFactory
				.createTopicManager(adminSettings);

		consumerSettings.setTopic(topic);
		producerSettings.setTopic(topic);

		Logging.log("Creating topic with Topic Name: " + topic
				+ " Number of partitions: " + data.getPartitions()
				+ " Number of Replicas: " + data.getReplica());
		topicManager.createTopic(topic, Integer.parseInt(data.getPartitions()),
				Integer.parseInt(data.getReplica()));
		Thread.sleep(2000);

		Producer producer = ConnectionFactory.createProducer(producerSettings);

		producer.connect();

		Message event;
		List<Message> messageList = null;

		EventCallback eventCallback = new EventCallback() {

			@Override
			public void onComplete(RecordMetadata metadata, Exception exception) {
				System.out.println("call back completed " + exception);
			}
		};
		if (data.getMessages().equals("blank")) {
			event = new Message("message number:", "");
			producer.send(event, eventCallback);
			producer.close();
			return;
		}

		if (data.getMessages().equals("null")) {
			event = new Message("message number:", null);
			producer.send(event, eventCallback);
			producer.close();
			return;
		}
		if (data.getResend().equalsIgnoreCase("yes")) {
			event = new Message("message number:", data.getMessages());
			producer.send(event, eventCallback);
			Thread.sleep(5000);
			producer.send(event, eventCallback);
			producer.close();
			return;
		}

		String[] messageData = data.getMessages().split("#");

		if (messageData.length == 1) {
			event = new Message("message number:", messageData[0]);
			producer.send(event, eventCallback);
			producer.close();

		} else {
			messageList = new ArrayList<Message>();
			for (int i = 0; i < messageData.length; i++) {
				messageList.add(new Message("message number:", messageData[i]));
			}

			producer.send(messageList, eventCallback);
			producer.close();
		}

	}

	public static TopicManager createTopic() throws ConnectionException {

		AdminSettings adminSettings = loadAdminSettings();
		return ConnectionFactory.createTopicManager(adminSettings);

	}

	public static Consumer readMessages(ProdConsPojo data)
			throws NumberFormatException, TopicException, ConnectionException,
			PropertiesException, ProducerException, ConsumerException {

		String topic = data.getTopic();

		AdminSettings adminSettings = loadAdminSettings();
		ConsumerSettings consumerSettings = loadConsumerSettings();

		TopicManager topicManager = ConnectionFactory
				.createTopicManager(adminSettings);

		consumerSettings.setTopic(topic);

		Consumer consumer = ConnectionFactory.createConsumer(consumerSettings);

		consumer.connect();

		if (Integer.parseInt(data.getReadDataCount()) == 0) {

			consumer.read();
			consumer.close();

		} else {

			consumer.read(Long.parseLong(data.getReadDataCount()));
			consumer.close();

		}
		return consumer;
	}

	public static void sendMessages_Key_Map(ProdConsPojo data)
			throws NumberFormatException, TopicException, ConnectionException,
			PropertiesException, ProducerException, Exception {

		String topic = data.getTopic() + System.currentTimeMillis();
		data.setTopic(topic);

		AdminSettings adminSettings = loadAdminSettings();
		ProducerSettings producerSettings = loadProducerSettings(adminSettings);
		ConsumerSettings consumerSettings = loadConsumerSettings();

		TopicManager topicManager = ConnectionFactory
				.createTopicManager(adminSettings);

		consumerSettings.setTopic(topic);
		producerSettings.setTopic(topic);

		Logging.log("Creating topic with Topic Name: " + topic
				+ " Number of partitions: " + data.getPartitions()
				+ " Number of Replicas: " + data.getReplica());
		topicManager.createTopic(topic, Integer.parseInt(data.getPartitions()),
				Integer.parseInt(data.getReplica()));
		Thread.sleep(2000);

		Producer producer = ConnectionFactory.createProducer(producerSettings);

		producer.connect();
		Message event;
		EventCallback eventCallback = new EventCallback() {

			@Override
			public void onComplete(RecordMetadata metadata, Exception exception) {
				System.out.println("call back completed " + exception);
			}
		};
		String[] messageData = data.getMessages().split(",");
		event = new Message("message number:", messageData[1]);
		producer.send(messageData[0], event, eventCallback);
		producer.close();

	}

	public static void sendMessages_Key_MapList(ProdConsPojo data)
			throws NumberFormatException, TopicException, ConnectionException,
			PropertiesException, ProducerException, Exception {

		String topic = data.getTopic() + System.currentTimeMillis();

		data.setTopic(topic);

		AdminSettings adminSettings = loadAdminSettings();
		ProducerSettings producerSettings = loadProducerSettings(adminSettings);
		ConsumerSettings consumerSettings = loadConsumerSettings();

		TopicManager topicManager = ConnectionFactory
				.createTopicManager(adminSettings);

		consumerSettings.setTopic(topic);
		producerSettings.setTopic(topic);

		Logging.log("Creating topic with Topic Name: " + topic
				+ " Number of partitions: " + data.getPartitions()
				+ " Number of Replicas: " + data.getReplica());
		topicManager.createTopic(topic, Integer.parseInt(data.getPartitions()),
				Integer.parseInt(data.getReplica()));
		Thread.sleep(2000);

		Producer producer = ConnectionFactory.createProducer(producerSettings);

		producer.connect();
		Message event;
		EventCallback eventCallback = new EventCallback() {

			@Override
			public void onComplete(RecordMetadata metadata, Exception exception) {
				System.out.println("call back completed " + exception);
			}
		};

		Map<String, List<Message>> map = new HashMap<String, List<Message>>();

		List<Message> messsages = new ArrayList<Message>();
		List<Message> messsages1 = new ArrayList<Message>();

		messsages.add(new Message("message number:", "spire-1"));
		messsages.add(new Message("message number:", "spire -2"));

		messsages1.add(new Message("message number:", "grow -1"));
		messsages1.add(new Message("message number:", "grow -2"));

		map.put("spire", messsages);
		map.put("grow", messsages1);

		producer.send(map, eventCallback);

		producer.close();

	}

}

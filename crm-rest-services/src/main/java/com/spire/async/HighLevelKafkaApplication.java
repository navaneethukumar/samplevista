package com.spire.async;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.curator.test.TestingServer;
import org.testng.Assert;

import com.google.gson.Gson;

import kafka.network.BlockingChannel;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import spire.commons.Context;
import spire.commons.async.Message;
import spire.commons.async.exception.ConnectionException;
import spire.commons.async.exception.ConsumerException;
import spire.commons.async.exception.ProducerException;
import spire.commons.async.exception.PropertiesException;
import spire.commons.async.exception.TopicException;
import spire.commons.async.impl.HighLevelConsumerImpl;
import spire.commons.async.impl.KafkaProducer;
import spire.commons.async.impl.KafkaTopicManager;
import spire.commons.async.utils.AdminSettings;
import spire.commons.async.utils.ConsumerSettings;
import spire.commons.async.utils.ProducerSettings;
import spire.commons.utils.ContextUtil;

public class HighLevelKafkaApplication {

	private TestingServer zkTestServer;
	private Properties props;
	private KafkaServerStartable kafkaServerStartable;
	private ProducerSettings producerSettings;
	private ConsumerSettings consumerSettings;
	private AdminSettings adminSettings;

	public void setUp(String filePath) throws Exception {
		Context context = new Context();
		context.setServiceName("async-events");
		context.setTenantId("6099");
		ContextUtil.setContext(context);

		// mock zookeeper
		zkTestServer = new TestingServer(2181);
		
		String topicName = "my-topic-"+System.currentTimeMillis();
		
		// /tmp/tmp_kafka_dir
		props = new Properties();
		props.put("broker.id", "10");
		props.put("host.name", "localhost");
		props.put("port", "9092");
		props.put("log.dir", filePath);
		props.put("zookeeper.connect", zkTestServer.getConnectString());
		props.put("replica.socket.timeout.ms", "1500");
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("batch.size", "30000");
		props.put("producer.type", "async");
		props.put("retries", "3");
		props.put("linger.ms", "5");

		props.put("topic.name", topicName);
		props.put("partition.id", "0");
		props.put("group.id", "demoGroup");
		props.put("client.id", "test");
		props.put("max.message.size", "2000");
		props.put("client.id", "test");
		props.put("seed.brokers", "localhost");
		props.put("partition.assignment.strategy",
				"example.producer.SimplePartitioner");
		props.put("so.timeout", "10000");
		props.put("buffer.size", "30000");
		props.put("correlation.id", "0");
		props.put("version.id", "0");

		props.put("zookeeper.server", "localhost:2181");
		props.put("connection.timeout", "10000");
		props.put("session.timeout", "10000");
		props.put("client.id", "test");
		props.put("buffer.size", "30000");

		adminSettings = new AdminSettings();
		adminSettings.setZookeeperServer("localhost:2181");
		adminSettings.setConnectionTimeout(10000);
		adminSettings.setSessionTimeout(10000);
		adminSettings.setClientId("test");
		adminSettings.setBufferSize(30000);

		producerSettings = new ProducerSettings();
		producerSettings.setProducerId("test-ng-pro-1");
		producerSettings.setBrokerList(Arrays.asList("localhost:9092",
				"localhost:9093"));
		producerSettings.setSerializer("kafka.serializer.StringEncoder");
		producerSettings
				.setPartitionClass("spire.commons.async.utils.SimplePartitioner");
		producerSettings.setWaitMsec(2000L);
		producerSettings.setBatchSize(100);
		producerSettings.setBootstrapServers(Arrays.asList("localhost:9092"));
		producerSettings
				.setKeySerializer("org.apache.kafka.common.serialization.StringSerializer");
		producerSettings
				.setValueSerializer("org.apache.kafka.common.serialization.StringSerializer");
		producerSettings.setProducerType("async");
		producerSettings.setRetryCount(3);
		producerSettings.setTopic(props.getProperty("topic.name"));
		producerSettings.setAdminSettings(adminSettings);

		consumerSettings = new ConsumerSettings();
		consumerSettings.setBootstrapServers(Arrays.asList("localhost:9092"));
		consumerSettings.setPartitionId(0);
		consumerSettings.setGroupId("demoGroup");
		consumerSettings.setClientId("test");
		consumerSettings.setMaxMessageSize(4096);
		consumerSettings.setSeedBrokers(Arrays.asList("localhost"));
		consumerSettings.setSoTimeOut(10000);
		consumerSettings.setBufferSize(30000);
		consumerSettings.setCorrelationId(0);
		consumerSettings.setVersionId((short) 0);
		consumerSettings.setZookeeperHost("localhost");
		consumerSettings.setZookeeperPort(2181);
		consumerSettings.setTopic(props.getProperty("topic.name"));
		consumerSettings.setNumberOfStreams(1);
		consumerSettings.setAutoCommitEnable("true");
		consumerSettings.setAutoOffsetReset("smallest");
		consumerSettings.setConsumerTimeout("10");
		consumerSettings.setBufferSize(2);

		KafkaConfig config = new KafkaConfig(props);

		kafkaServerStartable = new KafkaServerStartable(config);
		kafkaServerStartable.startup();

		// mock other servers

	}

	public void tearDown() throws Exception {

		kafkaServerStartable.shutdown();
		zkTestServer.stop();

	}

	public void testHighLevelConsumer(ProdConsPojo data) throws Exception {

		String filePath = "/tmp/tmp_kafka_dir";
		
		setUp(filePath);   

		KafkaTopicManager topicManager = new KafkaTopicManager(adminSettings);

		Thread.sleep(1000);

		topicManager.createTopic(props.getProperty("topic.name"),
				Integer.parseInt(data.getPartitions()),
				Integer.parseInt(data.getReplica()));

		Thread.sleep(1000);

		String[] messages = data.getMessages().split(",");

		Message event = new Message(messages[0], "MESSAGE");
		Message event1 = new Message(messages[1], "MESSAGE");
		Gson gson = new Gson();

		List<Message> messagesSentToProducer = Arrays.asList(event, event1);

		BlockingChannel channel = new BlockingChannel("localhost", 9092,
				BlockingChannel.UseDefaultBufferSize(),
				BlockingChannel.UseDefaultBufferSize(), 10000);

		HighLevelConsumerImpl kafkaConsumer = mock(HighLevelConsumerImpl.class);

		KafkaProducer kafkaProducer = mock(KafkaProducer.class);

		setCommonVariables(event, event1, gson, messagesSentToProducer,
				kafkaConsumer, kafkaProducer);

		kafkaConsumer.connect();

		kafkaConsumer.close();
		kafkaProducer.connect();

		kafkaProducer.send(messagesSentToProducer, null);

		if (data.getTopic().equals("readFew")) {

			List<Message> messagesConsumed = kafkaConsumer.read(1L);

			Assert.assertTrue(messagesConsumed.size() == 1,
					"Showing wrong message consumed!!");

			kafkaConsumer.close();   

			tearDown();

		} else {

			List<Message> messagesConsumed = kafkaConsumer.read();
			
					kafkaConsumer.close();

			tearDown();
			
			Thread.sleep(2000);  

			List<String> contentSentToProducer = Arrays.asList(
					messagesSentToProducer.get(0).getContent(),
					messagesSentToProducer.get(1).getContent());

			List<String> contentConsumedByConsumer = Arrays.asList(
					messagesConsumed.get(0).getContent(),
					messagesConsumed.get(1).getContent());

			Assert.assertEquals(contentSentToProducer,   
					contentConsumedByConsumer, "Showing wrong content");
			
			
			try{
				
				File file = new File(filePath); 
				
				HighLevelKafkaApplication.deleteFile(file);
				
			}catch(Exception e){  
				
			}

		}
	}

	private void setCommonVariables(Message event, Message event1, Gson gson,
			List<Message> messagesSentToProducer,
			HighLevelConsumerImpl kafkaConsumer, KafkaProducer kafkaProducer)
			throws ConsumerException, ConnectionException, PropertiesException,
			ProducerException, TopicException {

		when(kafkaProducer.getAdminSettings()).thenReturn(adminSettings);
		when(kafkaProducer.getProducerSettings()).thenReturn(producerSettings);
		when(kafkaConsumer.getConsumerSettings()).thenReturn(consumerSettings);

//		kafkaConsumer.setConsumerSettings(consumerSettings);
		when(kafkaProducer.getGson()).thenReturn(gson);

		when(kafkaConsumer.read()).thenCallRealMethod();
		when(kafkaConsumer.read(anyLong())).thenCallRealMethod();
		when(kafkaConsumer.getConsumer()).thenCallRealMethod();
		when(kafkaConsumer.connect()).thenCallRealMethod();

		when(kafkaProducer.connect()).thenCallRealMethod();
		when(kafkaProducer.send(messagesSentToProducer, null))
				.thenCallRealMethod();
		when(kafkaProducer.send("", event, null)).thenCallRealMethod();
		when(kafkaProducer.send("", event1, null)).thenCallRealMethod();

		when(kafkaProducer.getPartitionId("", 0)).thenReturn(0);
	}
	
	public static void deleteFile(File element) {
		
	    if (element.isDirectory()) {  
	    	
	        for (File sub : element.listFiles()) {
	        	
	            deleteFile(sub);
	        }
	    }
	    
	    element.delete();
	    
	}

}

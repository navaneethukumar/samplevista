package com.spire.crm.entity.cache.lib.test;

/**
 * @author Manikanta.Y
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import redis.clients.jedis.Jedis;
import spire.commons.cache.service.CacheProvider;
import spire.commons.cache.service.CacheServer;
import spire.commons.cache.service.providers.RedisCacheProvider;
import spire.commons.cache.service.providers.RedisCacheServer;
import spire.commons.cache.utils.Connection;
import spire.commons.cache.utils.ElasticServerConnection;
import spire.commons.cache.utils.Response;
import spire.commons.data.exceptions.DataServiceException;
import spire.commons.data.service.DataProvider;
import spire.commons.data.service.DataServer;
import spire.commons.data.service.handlers.AbstractDataServiceHandler;
import spire.commons.data.service.providers.ElasticSearchDataServer;
import spire.commons.data.service.providers.ElasticSerachDataProvider;
import spire.commons.utils.IdUtils;
import spire.match.entities.CandidateExt;
import spire.talent.entity.demandservice.beans.RequisitionBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.database.DBModule;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

public class CacheLibjaHelper {

	public Gson gson = new Gson();
	public ObjectMapper mapper = new ObjectMapper();
	public String esHost = "192.168.2.175";
	public Integer esPort = 9300;
	public String esClusterName = "spire_demo";
	public String index = ReadingServiceEndPointsProperties
			.getServiceEndPoint("INDEX_NAME");

	public String cacheHost = "192.168.2.75";
	public Integer cachePort = 6379;
	public Jedis jedis = new Jedis("192.168.2.75");
	public String[] types = new String[] { "candidate" };

	public String dbHost = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBHOST");
	public String dbSchema = ReadingServiceEndPointsProperties
			.getServiceEndPoint("DBSCHEMA");

	DataProvider dataProvider =null;
	CacheProvider cacheProvider =null;
	
	public void getConections(){
		
		dataProvider = getDataProvider(esHost, esPort,
				esClusterName, types);
		cacheProvider = getCacheProvider(cacheHost, cachePort);
		
	}
	
	public void closeConections(){		
		((ElasticSerachDataProvider)dataProvider).close();		
	}
	
	public DataProvider getDataProvider(String esHost, Integer esPort,
			String esClusterName, String[] types) {
		ElasticServerConnection connection = new ElasticServerConnection(
				esHost, esPort, esClusterName, 10000);
		DataServer server = new ElasticSearchDataServer(connection);
		String indices[] = new String[] { index };
		DataProvider dataProvider = new ElasticSerachDataProvider<>(server,
				indices, types);
		return dataProvider;
	}

	public CacheProvider getCacheProvider(String cacheHost, Integer cachePort) {
		Connection connection = new Connection(cacheHost, cachePort, 2000);
		CacheServer server = new RedisCacheServer(connection);
		CacheProvider provider = new RedisCacheProvider(server, index);
		return provider;
	}

	public void validateFetch(SpireTestObject testObject, TestData data)
			throws DataServiceException, InterruptedException {
		AbstractDataServiceHandler handler = new CandidateAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = new ArrayList<>();
		String candidateID = ProfileHelper.createProfile();
		Logging.log("created candidate is >>> " + candidateID);
		Thread.sleep(2000);
		ids.add(candidateID);
		Response response = handler.fetch(ids, CandidateExt.class);
		Map<String, CandidateExt> successResponse = response.getSuccess();
		CandidateExt candidate = successResponse.get(candidateID);
		Assert.assertTrue(candidate.getId().equals(candidateID));
		Assert.assertNotNull(response);
		Thread.sleep(3000);
		String redisCandidate = jedis.get(index + ":" + candidateID);
		Assert.assertTrue(redisCandidate.contains(candidateID));
		Logging.log("Candidate fetched for redis is >>>>> " + redisCandidate);
	}

	public void verifyFetchWhenRedisDown(SpireTestObject testObject,
			TestData data) throws DataServiceException, InterruptedException {
		String candidateID = ProfileHelper.createProfile("s.mani@gmail.com");
		Thread.sleep(3000);
		CacheProvider cacheProvider = getCacheProvider("192.162.3.75",
				cachePort);
		AbstractDataServiceHandler handler = new CandidateAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = new ArrayList<>();
		ids.add(candidateID);
		Response response = handler.fetch(ids, CandidateExt.class);
		Map<String, CandidateExt> successResponse = response.getSuccess();
		CandidateExt candidate = successResponse.get(candidateID);
		Assert.assertTrue(candidate.getPrimaryEmailId().equals(
				"s.mani@gmail.com"));
		cacheHost = "192.162.2.75";

	}

	public void verifyRequisitionFetch(SpireTestObject testObject, TestData data)
			throws DataServiceException, SQLException {
		String types[] = new String[] { "requisition" };
		DataProvider dataProvider = getDataProvider(esHost, esPort,
				esClusterName, types);
		AbstractDataServiceHandler handler = new RequistionsAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = get1000CandidatesfromDB("requisition", "1", "0");
		Response response = handler.fetch(ids, RequisitionBean.class);
		Map<String, RequisitionBean> successResponse = response.getSuccess();
		RequisitionBean requisition = successResponse.get(ids.get(0));
		Assert.assertEquals(requisition.getId(), ids.get(0),
				"Given reqruistion id is not present");
		System.out.println("requisition >>>> " + requisition.getId());
		Assert.assertNotNull(response);

	}

	public void verifyFlushFetch(SpireTestObject testObject, TestData data)
			throws InterruptedException, DataServiceException {
		jedis.flushAll();
		AbstractDataServiceHandler handler = new CandidateAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = new ArrayList<>();
		String candidateID = ProfileHelper.createProfile();
		Logging.log("created candidate is >>> " + candidateID);
		Thread.sleep(2000);
		ids.add(candidateID);
		Response response = handler.fetch(ids, CandidateExt.class);
		Map<String, CandidateExt> successResponse = response.getSuccess();
		CandidateExt candidate = successResponse.get(candidateID);
		Assert.assertTrue(candidate.getId().equals(candidateID));
		Assert.assertNotNull(response);
		Thread.sleep(3000);
		String redisCandidate = jedis.get(index + ":" + candidateID);
		Assert.assertTrue(redisCandidate.contains(candidateID));
		Logging.log("Candidate fetched for redis is >>>>> " + redisCandidate);

	}

	public void validateMultipleFetch(SpireTestObject testObject, TestData data)
			throws InterruptedException, DataServiceException {
		Random random = new Random();
		int randomnumber = random.nextInt(5);
		AbstractDataServiceHandler handler = new CandidateAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = new ArrayList<>();
		for (int i = 0; i <= 1; i++) {
			String candidateID = ProfileHelper.createProfile();
			ids.add(candidateID);
		}
		String candidateID = ProfileHelper.createProfile();
		Thread.sleep(2000);
		ids.add(candidateID);
		Logging.log("created candidate are >>> " + ids);
		Response response = handler.fetch(ids, CandidateExt.class);
		if (testObject.getTestTitle().equals("verifyPartialFetch_Sanity")) {
			for (int i = 0; i <= 1; i++) {
				candidateID = ProfileHelper.createProfile();
				ids.add(candidateID);
			}
			Logging.log("Added few candidates to list,they  are >>> " + ids);
			Thread.sleep(4000);
			DataProvider dataProvider2 = getDataProvider(esHost, esPort,
					esClusterName, types);
			CacheProvider cacheProvider2 = getCacheProvider(cacheHost,
					cachePort);
			AbstractDataServiceHandler handler2 = new CandidateAbstractServiceHandler(
					cacheProvider2, dataProvider2);
			response = handler2.fetch(ids, CandidateExt.class);
		}

		Map<String, CandidateExt> successResponse = response.getSuccess();
		for (String candidateId : ids) {
			CandidateExt candidate = successResponse.get(candidateId);
			Assert.assertTrue(candidate.getId().equals(candidateId));
			Assert.assertNotNull(response);
		}
		Thread.sleep(3000);
		for (String candidateId : ids) {
			String redisCandidate = jedis.get(index + ":" + candidateId);
			Assert.assertTrue(redisCandidate.contains(candidateId));
			Logging.log("Candidate fetched for redis is >>>>> "
					+ redisCandidate);
		}

	}

	public List<String> get1000CandidatesfromDB(String tableName, String Limit,
			String offset) throws SQLException {

		List<String> candidateids = new ArrayList<String>();
		String dbUrl = "jdbc:mysql://" + dbHost + "/";
		java.sql.Connection c = DBModule.getCommonsDBConnection("root", "root",
				dbUrl);
		String sql = "SELECT ID as ID FROM " + dbSchema + "." + tableName
				+ " ORDER BY ID DESC LIMIT " + Limit + " OFFSET " + offset;
		ResultSet rs = c.createStatement().executeQuery(sql);
		while (rs.next()) {
			candidateids.add(IdUtils.toIdStr(rs.getBytes("ID")));
		}
		Logging.log("DB candidatte IDS are >>> " + candidateids);
		c.close();
		return candidateids;

	}

	public void validateBulkFetch(SpireTestObject testObject, TestData data)
			throws InterruptedException, DataServiceException, SQLException {

		AbstractDataServiceHandler handler = new CandidateAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = get1000CandidatesfromDB("candidate", "1000", "0");
		Response response = handler.fetch(ids, CandidateExt.class);
		Map<String, CandidateExt> successResponse = response.getSuccess();
		List failuerResponse = response.getFailures();

		for (String candidateId : ids) {

			CandidateExt candidate = successResponse.get(candidateId);
			if (candidate != null) {
				try{
				Assert.assertTrue(candidate.getId().equals(candidateId));
				}catch( NullPointerException e){
					System.out.println("Candidate ID for null point is " +candidateId);
				}
					
				
				Assert.assertNotNull(response);
			}
		}
		Thread.sleep(3000);
		for (String candidateId : ids) {
			String redisCandidate = jedis.get(index + ":" + candidateId);
			if (redisCandidate != null) {
				Assert.assertTrue(redisCandidate.contains(candidateId));
				Logging.log("Candidate fetched for redis is >>>>> "
						+ redisCandidate);
			}
		}

	}

	public void validateInvalidFetch(SpireTestObject testObject, TestData data)
			throws DataServiceException, InterruptedException {
		
		AbstractDataServiceHandler handler = new CandidateAbstractServiceHandler(
				cacheProvider, dataProvider);
		List<String> ids = new ArrayList<String>();
		String candidateID = IdUtils.generateID("6102", "6023");
		ids.add(candidateID);
		Response response = handler.fetch(ids, CandidateExt.class);
		List failuerResponse = response.getFailures();
		Assert.assertTrue(failuerResponse.contains(ids),
				"Invalid candidate ID is not present in Failuers");
		
		
		

	}
}

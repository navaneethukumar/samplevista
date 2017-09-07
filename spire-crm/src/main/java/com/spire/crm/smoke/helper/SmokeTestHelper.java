package com.spire.crm.smoke.helper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.search.response.SearchResponse;
import spire.commons.userservice.bean.LoginRequestBean;
import spire.commons.userservice.bean.LoginResponseBean;
import spire.crm.profiles.bean.Profile;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;

/**
 * 
 * @author Santosh C
 *
 */

public class SmokeTestHelper {

	LoginResponseBean loginResponse;
	static Properties serviceEndPointsProp;
	String PROPFILENAME = "services-endpoints.properties";
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("TALENT_USER_SERVICE");

	/**
	 * Read the file and convert to json
	 * 
	 * @param fileName
	 * @return profileJson
	 */
	public static Profile getProfileJson(String fileName) {

		Profile profileJson = null;
		String filePath = "./src/main/resources/" + fileName;
		File profilesJson = new File(filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			profileJson = objectMapper.readValue(profilesJson, new TypeReference<Profile>() {
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return profileJson;
	}

	/**
	 * @param dbHost
	 * @param user
	 * @param password
	 * @param schemaName
	 * @param tableName
	 * @return totalCountInDB
	 * @throws SQLException
	 */
	public static int getTotalCountFromDB(String dbHost, String user, String password, String schemaName,
			String tableName) {

		System.out.println("Connecting to DB Host: " + dbHost + " User: " + user + ", Password: " + password
				+ ", SchemaName: " + schemaName + ", TableName: " + tableName);

		int totalCountInDB = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":3306/" + schemaName, user, password);

			stmt = con.createStatement();
			rs = stmt.executeQuery("select count(*) from " + tableName);

			while (rs.next()) {
				totalCountInDB = rs.getInt(1);
				System.out.println("Total count in DB: " + totalCountInDB);
			}

		} catch (Exception e) {

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return totalCountInDB;
	}

	/**
	 * getCandidateDetailsFromES
	 * 
	 * @return candidateData
	 */
	public String getCandidateDetailsFromES() {

		return null;
	}

	public static void createCandidate_Validate(String userName, String password, String dbHost, String user,
			String dbPassword, String schemaName, String search_and_Match, String tableName, String cid) {

		if (cid == null) {

			int initialCount = SmokeTestHelper.getTotalCountFromDB(dbHost, user, dbPassword, schemaName, tableName);
			int countAferCandidateCreation = SmokeTestHelper.getTotalCountFromDB(dbHost, user, dbPassword, schemaName,
					tableName);

			Assert.assertTrue(countAferCandidateCreation > initialCount, "Created candidate not storing in DB");
		}
	}

	/**
	 * Validate candidate details in ES
	 * 
	 * @param profileRequest
	 * @param esData
	 */
	public static void validateESData(Profile profileRequest, SearchResponse esResponse) {

		Assert.assertNotNull(esResponse, "ES response is coming as null!!");
		Assert.assertNotNull(esResponse.getCandidateResponse(), "ES candidate-response is coming as null!!");
		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities(),
				"ES candidate-response is coming as null!!");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getFirstName(),
				"CandidateFirstName is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCandidate().getFirstName(),
				esResponse.getCandidateResponse().getEntities().get(0).getFirstName(),
				"Showing wrong FirstName in ES response");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getLastName(),
				"CandidateLastName is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCandidate().getLastName(),
				esResponse.getCandidateResponse().getEntities().get(0).getLastName(),
				"Showing wrong LastName in ES response");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getLocation(),
				"CandidateLocation is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCandidate().getLocationName(),
				esResponse.getCandidateResponse().getEntities().get(0).getLocation(),
				"Showing wrong Location in ES response");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getContact(),
				"ContactNumber is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCandidate().getPrimaryContactNumber(),
				esResponse.getCandidateResponse().getEntities().get(0).getContact(),
				"Showing wrong ContactNumber in ES response");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getEmail(),
				"EmailId is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCandidate().getPrimaryEmailId(),
				esResponse.getCandidateResponse().getEntities().get(0).getEmail(),
				"Showing wrong EmailId in ES response");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getCrm_stage(),
				"CRM stage is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCrm().getStatusName().toLowerCase(),
				esResponse.getCandidateResponse().getEntities().get(0).getCrm_stage().toLowerCase(),
				"Showing wrong CRM_Stage in ES response");

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getSkills(),
				"CRM stage is coming null in ES response!!");

		Collection<CandidateSkillMapBean> skills = profileRequest.getCandidate().getCandidateSkillMapBean().getItems();
		List<String> skil = new ArrayList<String>();
		for (CandidateSkillMapBean candidateSkillMapBean : skills) {
			skil.add(candidateSkillMapBean.getSkill());
		}

		System.out.println("ES SKILLS: " + esResponse.getCandidateResponse().getEntities().get(0).getSkills());

		for (String skillName : skil) {
			Assert.assertTrue(esResponse.getCandidateResponse().getEntities().get(0).getSkills().contains(skillName),
					"Skill " + skillName + " not found in ES !!");
		}

		Assert.assertNotNull(esResponse.getCandidateResponse().getEntities().get(0).getExperience(),
				"Experience is coming null in ES response!!");
		Assert.assertEquals(profileRequest.getCandidate().getTotalExperienceMnth().toString(),
				esResponse.getCandidateResponse().getEntities().get(0).getExperience(),
				"Showing wrong TotalExp in ES response");
	}

	public static String createCandidate(String userName, String password, String name, String skillName) {

		Profile request1 = SmokeTestHelper.getProfileJson("candidate1.json");
		CandidateBean candidate = new CandidateBean();
		candidate.setFirstName(name);
		candidate.setSourceType("Spire-Test");
		candidate.setSourceName("Spire-Test");
		CollectionEntity<CandidateSkillMapBean> skillMapBean = new CollectionEntity<CandidateSkillMapBean>();
		CandidateSkillMapBean candidateSkillMapBean = new CandidateSkillMapBean();
		candidateSkillMapBean.setSkill(skillName);
		skillMapBean.addItem(candidateSkillMapBean);
		candidate.setCandidateSkillMapBean(skillMapBean);

		request1.setCandidate(candidate);

		ProfileBizServiceConsumer consumer = new ProfileBizServiceConsumer(userName, password);
		consumer.HEADERS = false;

		return consumer.createProfile(request1);
	}

	public void validateUserService(String userName, String password, String realm) {

		String endpoint = endPointURL + "?realmName=" + realm;
		Logging.log("ENDPOINT: " + endpoint);
		Logging.log("USER: " + userName);
		Logging.log("PASSWORD: " + password);

		System.out.println(endpoint);

		LoginRequestBean loginRequestBean = new LoginRequestBean();
		loginRequestBean.setUserId(userName);
		loginRequestBean.setPassword(password);
		WebTarget target = ClientBuilder.newClient().target(endpoint);
		Response response = target.request().post(Entity.entity(loginRequestBean, MediaType.APPLICATION_JSON));

		Assert.assertEquals(response.getStatus(), 200, "Authentication Failing with ENDPOINT: " + endPointURL
				+ " USER: " + userName + " PASSWORD: " + password);

		loginResponse = response.readEntity(LoginResponseBean.class);

		Assert.assertNotNull(loginResponse.getUserId(), "USERID is coming null in User-Service Response !!");
		Assert.assertNotNull(loginResponse.getTenantId(), "TOKENID is coming null in User-Service Response !!");
		Assert.assertNotNull(loginResponse.getRealmName(), "REALM_NAME is coming null in User-Service Response !!");
		Assert.assertNotNull(loginResponse.getTokenId(), "TOKENID is coming null in User-Service Response !!");

	}

	public static String getServiceEndPoint(String name) {
		if (serviceEndPointsProp == null) {
			// throw new
			// RuntimeException(" properties instance is not invoked");
			new ReadingServiceEndPointsProperties();
		}
		String value = serviceEndPointsProp.getProperty(name);
		return value;

	}
}

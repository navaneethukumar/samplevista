package com.spire.common;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fluttercode.datafactory.impl.DataFactory;

import spire.commons.utils.IdUtils;
import spire.crm.profiles.bean.Profile;
import spire.social.entity.Connect;
import spire.social.entity.Social;
import spire.social.entity.SocialExt;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidatePatchBean;
import spire.talent.entity.profileservice.beans.CandidateProjectMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;
import spire.talent.entity.profileservice.beans.PatchObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;

import crm.pipeline.beans.CRM;

public class ProfileHelper {

	static ProfileBizServiceConsumer profileBizServiceConsumer = null;

	/**
	 * Add Education details
	 * 
	 * @param degreeName
	 * @param instituteName
	 * @return candidateEducationMapBean
	 */
	public static CollectionEntity<CandidateEducationMapBean> addEducation(String degreeName, String instituteName) {
		CollectionEntity<CandidateEducationMapBean> candidateEducationMapBean = new CollectionEntity<CandidateEducationMapBean>();
		CandidateEducationMapBean education = new CandidateEducationMapBean();
		education.setDegreeName(degreeName);
		education.setInstituteName(instituteName);
		candidateEducationMapBean.addItem(education);
		return candidateEducationMapBean;
	}

	/**
	 * Add Education details
	 * 
	 * @param degreeName
	 * @param instituteName
	 * @return candidateEducationMapBean
	 */
	public static void addEducation(CandidateEducationMapBean candidateEducationMapBean) {
		CollectionEntity<CandidateEducationMapBean> collenEntity = new CollectionEntity<CandidateEducationMapBean>();
		CandidateEducationMapBean education = new CandidateEducationMapBean();
		education.setDegreeName(candidateEducationMapBean.getDegreeName());
		education.setInstituteName(candidateEducationMapBean.getInstituteName());
		education.setInstituteName(candidateEducationMapBean.getInstituteName());
		collenEntity.addItem(education);
	}

	/**
	 * Add Employer details
	 * 
	 * @param designationName
	 * @param companyName
	 * @return candidateEmployerMapBean
	 */
	public static CollectionEntity<CandidateEmployerMapBean> addEmployer(String designationName, String companyName) {
		CollectionEntity<CandidateEmployerMapBean> candidateEmployerMapBean = new CollectionEntity<CandidateEmployerMapBean>();
		CandidateEmployerMapBean employer = new CandidateEmployerMapBean();
		employer.setDesignationName(designationName);
		employer.setEmployerName(companyName);
		SimpleDateFormat simpledateform = new SimpleDateFormat("YYYY-MM-DD");

		java.util.Date now = null;
		try {
			now = simpledateform.parse("2010-01-01");
		} catch (ParseException e) {

			e.printStackTrace();
		}
		java.sql.Date startDate = new java.sql.Date(now.getTime());

		employer.setStartDate(startDate);

		candidateEmployerMapBean.addItem(employer);
		return candidateEmployerMapBean;
	}

	/**
	 * Add Project details
	 * 
	 * @param projectName
	 * @param companyName
	 * @return candidateProjectMapBean
	 */
	public static CollectionEntity<CandidateProjectMapBean> addProject(String projectName, String companyName) {
		CollectionEntity<CandidateProjectMapBean> candidateProjectMapBean = new CollectionEntity<CandidateProjectMapBean>();
		CandidateProjectMapBean project = new CandidateProjectMapBean();
		project.setProjectName(projectName);
		project.setClientName("Accenture");
		project.setEmployerName(companyName);
		candidateProjectMapBean.addItem(project);
		return candidateProjectMapBean;
	}

	/**
	 * Add Skill details
	 * 
	 * @param skillName
	 * @return candidateSkillMapBean
	 */
	public static CollectionEntity<CandidateSkillMapBean> addSkill(String skillName) {
		CollectionEntity<CandidateSkillMapBean> candidateSkillMapBean = new CollectionEntity<CandidateSkillMapBean>();
		CandidateSkillMapBean skill = new CandidateSkillMapBean();
		skill.setSkill(skillName);
		candidateSkillMapBean.addItem(skill);
		return candidateSkillMapBean;
	}

	/**
	 * Add Candidate basic details
	 * 
	 * @return candidateBean
	 */
	public static CandidateBean addCandidateBasicDetails() {
		DataFactory dataFactory = new DataFactory();
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setFirstName(dataFactory.getFirstName());
		candidateBean.setLastName(dataFactory.getLastName());
		candidateBean.setLocationName(dataFactory.getCity());
		candidateBean.setPrimaryEmailId("crmvista.services@gmail.com");
		candidateBean.setTotalExperienceMnth((short) 8);
		return candidateBean;
	}

	/**
	 * Add Candidate basic details
	 * 
	 * @return candidateBean
	 */
	public static CandidateBean addCandidateBasicDetails(String location, String emailId) {
		DataFactory dataFactory = new DataFactory();
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setFirstName(dataFactory.getFirstName());
		candidateBean.setLastName(dataFactory.getLastName());
		candidateBean.setLocationName(location);
		candidateBean.setPrimaryEmailId(emailId);
		candidateBean.setTotalExperienceMnth((short) 8);
		return candidateBean;
	}

	/**
	 * Add social details
	 * 
	 * @return social object
	 */
	public static Social setSocial() {

		Social createSocialData = new Social();

		Set<String> hobbies = new HashSet<String>();
		hobbies.add("CRICKET");
		hobbies.add("HOCKEY");
		createSocialData.setHobbies(hobbies);

		Set<String> interests = new HashSet<String>();
		interests.add("LISTENING MUSIC");
		interests.add("WATCHING MOVIES");
		createSocialData.setInterests(interests);

		List<Connect> followers = new ArrayList<Connect>();
		Connect connectFollowers = new Connect();
		connectFollowers.setConnectName("Spire");
		connectFollowers.setConnectURL("http://facebook.com");
		connectFollowers.setCreatedBy("Spire");
		connectFollowers.setEmail("abcd@spire.com");
		connectFollowers.setMobileNumber("1234567890");
		connectFollowers.setModifiedBy("Spire");
		followers.add(connectFollowers);
		createSocialData.setFollowers(followers);

		List<Connect> following = new ArrayList<Connect>();
		Connect connectFollowing = new Connect();
		connectFollowing.setConnectName("Spire");
		connectFollowing.setConnectURL("http://facebook.com");
		connectFollowing.setCreatedBy("Spire");
		connectFollowing.setEmail("abcd@spire.com");
		connectFollowing.setMobileNumber("1234567890");
		connectFollowing.setModifiedBy("Spire");
		following.add(connectFollowing);
		createSocialData.setFollowing(following);

		SocialExt socialExtRequest = new SocialExt();
		socialExtRequest.setStackoverflowBadgeCount(10);
		socialExtRequest.setAskUbuntuUserId("100");
		socialExtRequest.setGithubFollowersCount(90);
		socialExtRequest.setGithubFollowersCount(50);
		socialExtRequest.setGithubFollowingCount(88);
		socialExtRequest.setGithubPublicRepos(78);
		socialExtRequest.setLinkedInConnectionsCount(360);
		socialExtRequest.setOriginalGithubRepoCount(422);
		socialExtRequest.setStackoverflowBadgeCount(58);
		socialExtRequest.setStackoverflowReputationPoints(789);
		socialExtRequest.setStackoverflowUserId("stack-21");
		createSocialData.setSocialExt(socialExtRequest);

		return createSocialData;

	}

	/**
	 * Add CRM details
	 * 
	 * @return crm object
	 */
	public static CRM setCRM() {
		CRM crm = new CRM();
		crm.setStatusName("Lead");		
		return crm;
	}

	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param Profile
	 *            object
	 */
	public static Profile createProfile(TestData data) {

		Profile createProfileData = new Profile();

		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();

		candidateBean
				.setCandidateEducationMapBean(ProfileHelper.addEducation("BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer("Test Automation Engineer", "Intuit"));
		candidateBean.setCandidateProjectMapBean(ProfileHelper.addProject("Acqura", "Accenture"));
		candidateBean.setCandidateSkillMapBean(ProfileHelper.addSkill("WebService Automation"));
		createProfileData.setCandidate(candidateBean);

		createProfileData.setSocial(ProfileHelper.setSocial());
		createProfileData.setCrm(ProfileHelper.setCRM());

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		System.out.println("candidateId " + candidateId);

		return createProfileData;
	}
	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param Profile
	 *            object
	 */
	public static String createProfile1(TestData data) {
		Profile createProfileData = new Profile();
		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();

		createProfileData.setCandidate(candidateBean);
		createProfileData.setSocial(ProfileHelper.setSocial());
		createProfileData.setCrm(ProfileHelper.setCRM());

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		Logging.log("candidateId: " + candidateId);		

		return candidateId;
	}
	
	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param Profile
	 *            object
	 */
	public static String createProfileWithoutCRM() {
		Profile createProfileData = new Profile();
		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();

		createProfileData.setCandidate(candidateBean);
		createProfileData.setSocial(ProfileHelper.setSocial());
		
		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		Logging.log("candidateId: " + candidateId);		

		return candidateId;
	}
	
	
	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param String
	 */
	public static String createFullProfile(TestData data) {

		Profile createProfileData = new Profile();

		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();

		candidateBean
				.setCandidateEducationMapBean(ProfileHelper.addEducation("BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer("Test Automation Engineer", "Intuit"));
		candidateBean.setCandidateProjectMapBean(ProfileHelper.addProject("Acqura", "Accenture"));
		candidateBean.setCandidateSkillMapBean(ProfileHelper.addSkill("WebService Automation"));
		createProfileData.setCandidate(candidateBean);

		createProfileData.setSocial(ProfileHelper.setSocial());
		createProfileData.setCrm(ProfileHelper.setCRM());

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		Logging.log("candidateId: " + candidateId);		

		return candidateId;
	}


	/**
	 * Create profile with full candidate details, with Social and CRM
	 * 
	 * @return candidateId
	 */
	public static String createProfile() {

		Profile createProfileData = ProfileHelper.getProfileJson("candidate1.json");

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		String candidateId = null;

		candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		if (candidateId != null) {

			return candidateId;

		} else {
			return IdUtils.generateID("6002", "6009");
		}

	}

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

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		CandidateBean candidate = new CandidateBean();
		DataFactory dataFactory = new DataFactory();
		candidate.setFirstName(dataFactory.getFirstName());
		candidate.setLastName(dataFactory.getLastName());
		// profileJson.setCandidate(candidate);
		return profileJson;
	}

	/**
	 * Create profile with full candidate details, with Social by taking inputs
	 * 
	 * @return candidateId
	 */
	public static String createProfile(CandidateBean candidateBean) {

		Profile createProfileData = new Profile();

		candidateBean
				.setCandidateEducationMapBean(ProfileHelper.addEducation("BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer("Test Automation Engineer", "Intuit"));
		candidateBean.setCandidateProjectMapBean(ProfileHelper.addProject("Acqura", "Accenture"));
		createProfileData.setCandidate(candidateBean);

		createProfileData.setSocial(null);
		createProfileData.setCrm(null);

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		String candidateId = null;

		candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		if (candidateId != null) {

			return candidateId;

		} else {
			return IdUtils.generateID("6002", "6009");
		}

	}

	/**
	 * Create profile with full candidate details, with Social and CRM as Lead
	 * 
	 * @return candidateId
	 */
	public static String createProfile(CRM crm) {

		Profile createProfileData = new Profile();

		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();
		candidateBean
				.setCandidateEducationMapBean(ProfileHelper.addEducation("BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer("Test Automation Engineer", "Intuit"));
		candidateBean.setCandidateProjectMapBean(ProfileHelper.addProject("Acqura", "Accenture"));
		candidateBean.setCandidateSkillMapBean(ProfileHelper.addSkill("WebService Automation"));
		createProfileData.setCandidate(candidateBean);

		createProfileData.setSocial(null);
		createProfileData.setCrm(crm);

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		String candidateId = null;

		candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		if (candidateId != null) {

			return candidateId;

		} else {
			return IdUtils.generateID("6002", "6009");
		}

	}

	public static String createProfile(String emailID) {

		Profile createProfileData = new Profile();
		CRM crm = new CRM();
		crm.setStatusName("Lead");

		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();
		candidateBean.setPrimaryEmailId(emailID);
		candidateBean
				.setCandidateEducationMapBean(ProfileHelper.addEducation("BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer("Test Automation Engineer", "Intuit"));
		candidateBean.setCandidateProjectMapBean(ProfileHelper.addProject("Acqura", "Accenture"));
		candidateBean.setCandidateSkillMapBean(ProfileHelper.addSkill("WebService Automation"));
		createProfileData.setCandidate(candidateBean);
		createProfileData.setSocial(null);
		createProfileData.setCrm(crm);
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		return profileBizServiceConsumer.createProfile(createProfileData);

	}
	/**
	 *update Candidate basic details
	 * 
	 * @return candidateBean
	 */
	public static CandidatePatchBean updateCandidateBasicDetails(String candidateId,String displayId) {
		CandidatePatchBean candidatePatchBean=new CandidatePatchBean();
		candidatePatchBean.setId(candidateId);
		candidatePatchBean.setDisplayId(displayId);
		candidatePatchBean.setFirstName("rohit");
		candidatePatchBean.setLastName("deshmukh");
		candidatePatchBean.setLocationName("mumbai");
		candidatePatchBean.setPrimaryEmailId("vista.services@gmail.com");
		candidatePatchBean.setTotalExperienceMnth((short) 10);
		return candidatePatchBean;
		
	}
	/**
	 *update Candidate basic details
	 * 
	 * @return candidateBean
	 */
	public static CandidatePatchBean updateCandidateEducationDetails(String candidateId, String displayId) {
		CandidatePatchBean candidatePatchBean = new CandidatePatchBean();
		candidatePatchBean.setId(candidateId);
		candidatePatchBean.setDisplayId(displayId);
		CandidateEducationMapBean candidateEducationMapBean = new CandidateEducationMapBean();
		candidateEducationMapBean.setCandidateId(candidateId);
		candidateEducationMapBean.setDegreeName("MBA");
		candidateEducationMapBean.setInstituteName("BIET");
		PatchObject<CandidateEducationMapBean> path = new PatchObject<>();
		path.setElement(candidateEducationMapBean);
		CollectionEntity<PatchObject<CandidateEducationMapBean>> collection = new CollectionEntity<>();
		collection.addItem(path);
		candidatePatchBean.setCandidateEducationMapBean(collection);
		return candidatePatchBean;

	}
	/**
	 *update Candidate Social details
	 * 
	 * @return candidateBean
	 */
	public static Social updateCandidateSocialDetails(String candidateId,String socialId) {
		Social social=new Social();
		Set<String>  interests=new HashSet<>();
		interests.add("hockey");
		interests.add("football");
		Set<String>  hobbies=new HashSet<>();
		hobbies.add("reading books");
		social.setCandidateId(candidateId);
		social.setId(socialId);
		social.setInterests(interests);
		social.setHobbies(hobbies);
		social.setLinkedinUrl("www.linkedin.com/in/sangeeta@spire2grow.com");
		return social;
	}
	/**
	 * Add Education details
	 * 
	 * @param degreeName
	 * @param instituteName
	 * @return candidateEducationMapBean
	 */
	public static CandidateEducationMapBean addNewEducation(CandidateEducationMapBean candidateEducationMapBean) {
		CandidateEducationMapBean education = new CandidateEducationMapBean();
		education.setDegreeName("btech");
		education.setInstituteName("BIET");
		return education;
		
	}
	/**
	 * Add Employer details
	 * 
	 * @param designationName
	 * @param companyName
	 * @return candidateEmployerMapBean
	 */
	public static CandidateEmployerMapBean addNewEmployer(CandidateEmployerMapBean candidateEmployerMapBean) {
		candidateEmployerMapBean.setDesignationName("developer");
		candidateEmployerMapBean.setEmployerName("spire");
		SimpleDateFormat simpledateform = new SimpleDateFormat("YYYY-MM-DD");

		java.util.Date now = null;
		try {
			now = simpledateform.parse("2010-01-01");
		} catch (ParseException e) {

			e.printStackTrace();
		}
		java.sql.Date startDate = new java.sql.Date(now.getTime());

		candidateEmployerMapBean.setStartDate(startDate);
		return candidateEmployerMapBean;
	}
	/**
	 * Add Skill details
	 * 
	 * @param skillName
	 * @return candidateSkillMapBean
	 * @throws Exception 
	 */
	public static  CandidateSkillMapBean addNewSkill(String candidateId,CandidateSkillMapBean candidateSkillMapBean) throws Exception {
		candidateSkillMapBean.setSkill("mysql");
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		profileBizServiceConsumer.addSkill(candidateId, candidateSkillMapBean);
		return candidateSkillMapBean;
	}
	
	

}
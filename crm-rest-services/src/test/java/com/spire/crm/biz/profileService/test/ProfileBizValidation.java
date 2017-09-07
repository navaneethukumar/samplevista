package com.spire.crm.biz.profileService.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;
import com.spire.crm.restful.util.ProfileDataPoints;

import crm.pipeline.beans.CRM;
import spire.commons.utils.IdUtils;
import spire.crm.profiles.bean.Profile;
import spire.crm.profiles.entity.CandidateAlreadyViewedVO;
import spire.crm.profiles.entity.RecruiterVO;
import spire.social.entity.Social;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidatePatchBean;
import spire.talent.entity.profileservice.beans.CandidateProjectMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

/**
 * @author Santosh C
 *
 */
public class ProfileBizValidation {

	static Gson gson = new Gson();

	static ProfileBizServiceConsumer profileBizServiceConsumer = null;
	static List<String> candidateIds = new ArrayList<String>();

	public static List<String> getCandidateIds() {
		return candidateIds;
	}

	public static void setCandidateIds(List<String> candidateIds) {
		ProfileBizValidation.candidateIds = candidateIds;
	}

	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param candidateId
	 */
	public static Profile createProfile_Validate(TestData data) {
		Profile createProfileData = new Profile();

		CandidateBean candidateBean = ProfileHelper.addCandidateBasicDetails();

		if (data.getData().equals("Education")) {

			candidateBean.setCandidateEducationMapBean(
					ProfileHelper.addEducation("BE", "Bangalore Institute Of Technology"));
		}

		if (data.getData().equals("Employer")) {
			candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer("Test Automation Engineer", "Intuit"));
		}
		if (data.getData().equals("Project")) {
			candidateBean.setCandidateProjectMapBean(ProfileHelper.addProject("Acqura", "Accenture"));
		}
		if (data.getData().equals("Skill")) {
			candidateBean.setCandidateSkillMapBean(ProfileHelper.addSkill("WebService Automation"));
		}

		createProfileData.setCandidate(candidateBean);

		if (data.getData().equals("Social")) {
			createProfileData.setSocial(ProfileHelper.setSocial());
		}
		if (data.getData().equals("CRM")) {
			createProfileData.setCrm(ProfileHelper.setCRM());
		}

		if (data.getData().equals("Social_CRM")) {
			createProfileData.setSocial(ProfileHelper.setSocial());
			createProfileData.setCrm(ProfileHelper.setCRM());
		}

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = profileBizServiceConsumer.createProfile(createProfileData);
		System.out.println("Created candidateId: " + candidateId);
		Logging.log("Created candidateId: " + candidateId);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");

		ProfileBizValidation.validateCreatedProfile(createProfileData, response, data);

		return createProfileData;
	}

	/**
	 * Validate created profile
	 * 
	 * @param request
	 * @param response
	 */
	public static void validateCreatedProfile(Profile request, Profile response, TestData data) {

		Assert.assertNotNull(request.getCandidate(), "Candidate details coming null !!");
		Assert.assertEquals(request.getCandidate().getFirstName(), response.getCandidate().getFirstName(),
				"Showing wrong FirstName !!");
		Assert.assertEquals(request.getCandidate().getLastName(), response.getCandidate().getLastName(),
				"Showing wrong LastName !!");
		Assert.assertEquals(request.getCandidate().getLastName(), response.getCandidate().getLastName(),
				"Showing wrong LastName !!");
		Assert.assertEquals(request.getCandidate().getLocationName(), response.getCandidate().getLocationName(),
				"Showing wrong LocationName !!");
		Assert.assertEquals(request.getCandidate().getPrimaryContactNumber(),
				response.getCandidate().getPrimaryContactNumber(), "Showing wrong PrimaryContactNumber !!");
		Assert.assertEquals(request.getCandidate().getPrimaryEmailId(), response.getCandidate().getPrimaryEmailId(),
				"Showing wrong PrimaryEmailId !!");

		if (data.getData().equals("Education")) {
			Assert.assertNotNull(request.getCandidate().getCandidateEducationMapBean(),
					"CandidateEducationMapBean is coming null in response");
		}

		if (data.getData().equals("Employer")) {
			Assert.assertNotNull(request.getCandidate().getCandidateEmployerMapBean(),
					"CandidateEmployerMapBean is coming null in response");
		}
		if (data.getData().equals("Project")) {
			Assert.assertNotNull(request.getCandidate().getCandidateProjectMapBean(),
					"CandidateProjectMapBean is coming null in response");
		}
		if (data.getData().equals("Skill")) {
			Assert.assertNotNull(request.getCandidate().getCandidateSkillMapBean(),
					"CandidateSkillMapBean is coming null in response");
		}

		if (data.getData().equals("Social")) {
			Assert.assertNotNull(request.getSocial(), "Social details coming null !!");
			Set<String> responseHobbies = response.getSocial().getHobbies();

			Set<String> requestHobbies = request.getSocial().getHobbies();
			for (String hobbie : requestHobbies) {

				Assert.assertTrue(responseHobbies.contains(hobbie), "Hobbies are not showing!!");

			}

			Set<String> responseInterests = response.getSocial().getInterests();

			Set<String> requestInterests = request.getSocial().getInterests();
			for (String interest : requestInterests) {

				Assert.assertTrue(responseInterests.contains(interest), "Interests are not showing!!");

			}
			Assert.assertEquals(request.getSocial().getProfessionalSummary(),
					response.getSocial().getProfessionalSummary(), "Showing wrong ProffessionalSummary");

			Assert.assertEquals(request.getSocial().getStackoverflowRank(), response.getSocial().getStackoverflowRank(),
					"Showing wrong StackoverflowRank");

		}

		if (data.getData().equals("CRM")) {

			Assert.assertNotNull(request.getCrm(), "CRM details coming null !!");
			Assert.assertEquals(request.getCrm().getStatusName(), response.getCrm().getStatusName(),
					"Showing wrong status name");
		}
		if (data.getData().equals("Social_CRM")) {
			Assert.assertNotNull(request.getSocial(), "Social details coming null !!");
			Assert.assertNotNull(request.getCrm(), "CRM details coming null !!");
		}

	}

	/**
	 * Create createFullProfile[with all details like education, emp, social and
	 * crm]
	 * 
	 * @param data
	 * @param Profile
	 */
	public static Profile createFullProfile_Validate(TestData data) {

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
		System.out.println("Created candidateId: " + candidateId);
		Logging.log("Created candidateId: " + candidateId);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");

		ProfileBizValidation.validateFullProfile(createProfileData, response);

		return createProfileData;
	}

	/**
	 * Validate created profile[basic details, education, employer, Skill,
	 * Social details and CRM details]
	 * 
	 * @param data
	 */
	public static void validateFullProfile(Profile request, Profile response) {

		Assert.assertNotNull(request.getCandidate(), "Candidate details coming null !!");
		Assert.assertEquals(request.getCandidate().getFirstName(), response.getCandidate().getFirstName(),
				"Showing wrong FirstName !!");
		Assert.assertEquals(request.getCandidate().getLastName(), response.getCandidate().getLastName(),
				"Showing wrong LastName !!");
		Assert.assertEquals(request.getCandidate().getLastName(), response.getCandidate().getLastName(),
				"Showing wrong LastName !!");
		Assert.assertEquals(request.getCandidate().getLocationName(), response.getCandidate().getLocationName(),
				"Showing wrong LocationName !!");
		Assert.assertEquals(request.getCandidate().getPrimaryContactNumber(),
				response.getCandidate().getPrimaryContactNumber(), "Showing wrong PrimaryContactNumber !!");
		Assert.assertEquals(request.getCandidate().getPrimaryEmailId(), response.getCandidate().getPrimaryEmailId(),
				"Showing wrong PrimaryEmailId !!");

		Assert.assertNotNull(request.getCandidate().getCandidateEducationMapBean(),
				"CandidateEducationMapBean is coming null in response");

		Assert.assertNotNull(request.getCandidate().getCandidateEmployerMapBean(),
				"CandidateEmployerMapBean is coming null in response");
		Assert.assertNotNull(request.getCandidate().getCandidateProjectMapBean(),
				"CandidateProjectMapBean is coming null in response");
		Assert.assertNotNull(request.getCandidate().getCandidateSkillMapBean(),
				"CandidateSkillMapBean is coming null in response");

		Assert.assertNotNull(request.getSocial(), "Social details coming null !!");
		Set<String> responseHobbies = response.getSocial().getHobbies();

		Set<String> requestHobbies = request.getSocial().getHobbies();
		for (String hobbie : requestHobbies) {

			Assert.assertTrue(responseHobbies.contains(hobbie), "Hobbies are not showing!!");

		}

		Set<String> responseInterests = response.getSocial().getInterests();

		Set<String> requestInterests = request.getSocial().getInterests();
		for (String interest : requestInterests) {

			Assert.assertTrue(responseInterests.contains(interest), "Interests are not showing!!");

		}
		Assert.assertEquals(request.getSocial().getProfessionalSummary(), response.getSocial().getProfessionalSummary(),
				"Showing wrong ProffessionalSummary");

		Assert.assertEquals(request.getSocial().getStackoverflowRank(), response.getSocial().getStackoverflowRank(),
				"Showing wrong StackoverflowRank");

		Assert.assertNotNull(request.getCrm(), "CRM details coming null !!");
		Assert.assertEquals(request.getCrm().getStatusName(), response.getCrm().getStatusName(),
				"Showing wrong status name");
		Assert.assertNotNull(request.getSocial(), "Social details coming null !!");
		Assert.assertNotNull(request.getCrm(), "CRM details coming null !!");

	}

	/**
	 * GetProfile by candidateId
	 * 
	 * @param data
	 */
	public static void getProfile(TestData data) {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		if (data.getData().equals("invalid")) {
			Profile response = profileBizServiceConsumer.getProfile(IdUtils.generateID("6001", "6002"), "full");

			System.out.println("invalid response: " + response);
		} else {
			String candidateId = ProfileHelper.createProfile();
			System.out.println("candidateId: " + candidateId);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
			Assert.assertEquals(response.getCandidate().getId(), candidateId, "Shoing wrong candidateId details");
		}
	}

	/**
	 * Create profile from file
	 */
	public void createProfile_File() {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		// profileBizServiceConsumer.createProfile(profile)

	}

	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param candidateId
	 */
	public static String profileCreation(ProfileDataPoints profileDataPoints) {
		CandidateBean candidateBean = profileDataPoints.getCandidateBean();
		CandidateEducationMapBean educationMapBean = profileDataPoints.getCandidateEducationMapBean();
		CandidateProjectMapBean candidateProjectMapBean = profileDataPoints.getCandidateProjectMapBean();
		CandidateSkillMapBean candidateSkillMapBean = profileDataPoints.getCandidateSkillMapBean();
		CandidateEmployerMapBean candidateEmployerMapBean = profileDataPoints.getCandidateEmployerMapBean();

		Profile candidateProfile = new Profile();

		Short exp = candidateBean.getTotalExperienceMnth();
		String sourceName = candidateBean.getSourceName();
		String sourceType = candidateBean.getSourceType();
		String location = candidateBean.getLocationName();

		candidateBean.setSourceName(sourceName);
		candidateBean.setSourceType(sourceType);
		candidateBean.setTotalExperienceMnth(exp);
		candidateBean.setLocationName(location);

		Logging.log("Experience >>" + exp);
		Logging.log("sourceName >>" + sourceName);
		Logging.log("sourceType >>" + sourceType);
		Logging.log("city >>" + location);

		/**
		 * CandidateEducation details setting
		 */
		CollectionEntity<CandidateEducationMapBean> collenEntityCndEducationBean = new CollectionEntity<CandidateEducationMapBean>();
		collenEntityCndEducationBean.addItem(educationMapBean);
		candidateBean.setCandidateEducationMapBean(collenEntityCndEducationBean);

		/**
		 * CandidateProject details setting
		 */
		CollectionEntity<CandidateProjectMapBean> collenEntityCndProjBean = new CollectionEntity<CandidateProjectMapBean>();
		collenEntityCndProjBean.addItem(candidateProjectMapBean);
		candidateBean.setCandidateProjectMapBean(collenEntityCndProjBean);

		/**
		 * Candidate skills details setting
		 */
		CollectionEntity<CandidateSkillMapBean> collenEntitySkillBean = new CollectionEntity<CandidateSkillMapBean>();
		// For no.of skills will split...
		if (candidateSkillMapBean.getSkill().contains(",")) {
			String[] skills = candidateSkillMapBean.getSkill().split(",");
			for (String skill : skills) {
				CandidateSkillMapBean canSkills = new CandidateSkillMapBean();
				canSkills.setSkill(skill);
				collenEntitySkillBean.addItem(canSkills);
			}
		} else {
			collenEntitySkillBean.addItem(candidateSkillMapBean);
		}
		candidateBean.setCandidateSkillMapBean(collenEntitySkillBean);
		/**
		 * candidate employer settings.
		 */
		CollectionEntity<CandidateEmployerMapBean> colleEntyEmployer = new CollectionEntity<CandidateEmployerMapBean>();
		colleEntyEmployer.addItem(candidateEmployerMapBean);
		candidateBean.setCandidateEmployerMapBean(colleEntyEmployer);

		/**
		 * Setting all the candidate profile details.
		 */
		candidateProfile.setCandidate(candidateBean);
		/**
		 * setting CRM stage
		 */
		CRM crm = new CRM();
		crm.setStatusName(profileDataPoints.getCrm().toString());
		candidateProfile.setCrm(crm);

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Logging.log("CANDIDATE json >>" + gson.toJson(candidateProfile));
		String candidateId = profileBizServiceConsumer.createProfile(candidateProfile);
		if (candidateId != null) {
			System.out.println("Created candidateId: " + candidateId);
			Logging.log("Created candidateId: " + candidateId);
			candidateIds.add(candidateId);
			return candidateId;
		} else {
			throw new RuntimeException("Profile creation failed !!!");
		}

	}

	/**
	 * update candidate basic details
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void updateBasicProfile(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = ProfileHelper.createProfile();
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		String displayId = response.getCandidate().getDisplayId();
		Logging.log("candidateId: " + candidateId);
		Logging.log("displayId: " + displayId);
		CandidatePatchBean candidatePatchBean = ProfileHelper.updateCandidateBasicDetails(candidateId, displayId);
		profileBizServiceConsumer.patchCandidate(candidatePatchBean);
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response1.getCandidate().getFirstName(), candidatePatchBean.getFirstName(),
				"Showing wrong candidateId details");
		Assert.assertEquals(response1.getCandidate().getLastName(), candidatePatchBean.getLastName(),
				"Showing wrong  details");
		Assert.assertEquals(response1.getCandidate().getLocationName(), candidatePatchBean.getLocationName(),
				"Showing wrong  details");
		Assert.assertEquals(response1.getCandidate().getPrimaryEmailId(), candidatePatchBean.getPrimaryEmailId(),
				"Showing wrong  details");
		Assert.assertEquals(response1.getCandidate().getTotalExperienceMnth(),
				candidatePatchBean.getTotalExperienceMnth(), "Showing wrong  details");
	}

	/**
	 * update candidate employee details
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void updateEducationDetails(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		String candidateId = ProfileHelper.createProfile();
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		String displayId = response.getCandidate().getDisplayId();
		Logging.log("candidateId: " + candidateId);
		Logging.log("displayId: " + displayId);
		CandidatePatchBean candidatePatchBean = ProfileHelper.updateCandidateEducationDetails(candidateId, displayId);
		profileBizServiceConsumer.patchCandidate(candidatePatchBean);
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response1.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
	}

	/**
	 * update candidate social details
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void updateSocialDetail(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Social socialBean = new Social();
		String candidateId = ProfileHelper.createFullProfile(data);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		String SocialId = response.getSocial().getId();
		Logging.log("candidateId: " + candidateId);
		Logging.log("SocialId: " + SocialId);
		socialBean = ProfileHelper.updateCandidateSocialDetails(candidateId, SocialId);
		profileBizServiceConsumer.updateSocialDetails(candidateId, socialBean);
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response1.getSocial().getId(), SocialId, "Showing wrong SocialId details");
		Assert.assertEquals(response1.getSocial().getHobbies(), socialBean.getHobbies(), "Showing wrong  details");
		Assert.assertEquals(response1.getSocial().getInterests(), socialBean.getInterests(), "Showing wrong  details");
		Assert.assertEquals(response1.getSocial().getLinkedinUrl(), socialBean.getLinkedinUrl(),
				"Showing wrong details");
	}

	/**
	 * Adding candidate new Education details
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void addEducationDetails(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		CandidateEducationMapBean candidateEducationMapBean = new CandidateEducationMapBean();
		String candidateId = ProfileHelper.createProfile1(data);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Logging.log("candidateId: " + candidateId);
		candidateEducationMapBean = ProfileHelper.addNewEducation(candidateEducationMapBean);
		profileBizServiceConsumer.addNewEducation(candidateId, candidateEducationMapBean);
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		CollectionEntity<CandidateEducationMapBean> candidateEducationMapBean2 = response1.getCandidate()
				.getCandidateEducationMapBean();
		Collection<CandidateEducationMapBean> candidateEdu = candidateEducationMapBean2.getItems();
		String degreeName, instituteName;
		degreeName = instituteName = null;
		for (CandidateEducationMapBean candidateEducationMapBean3 : candidateEdu) {
			degreeName = candidateEducationMapBean3.getDegreeName();
			instituteName = candidateEducationMapBean3.getInstituteName();
		}
		Assert.assertEquals(response1.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Assert.assertEquals(degreeName, candidateEducationMapBean.getDegreeName(), "Showing wrong candidateId details");
		Assert.assertEquals(instituteName, candidateEducationMapBean.getInstituteName(),
				"Showing wrong candidateId details");

	}

	/**
	 * Adding new employer details
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void addEmployerDetails(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		CandidateEmployerMapBean candidateEmployerMapBean = new CandidateEmployerMapBean();
		String candidateId = ProfileHelper.createProfile1(data);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Logging.log("candidateId: " + candidateId);
		candidateEmployerMapBean = ProfileHelper.addNewEmployer(candidateEmployerMapBean);
		profileBizServiceConsumer.addNewEmployer(candidateId, candidateEmployerMapBean);
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		CollectionEntity<CandidateEmployerMapBean> candidateEmployerMapBean1 = response1.getCandidate()
				.getCandidateEmployerMapBean();
		Collection<CandidateEmployerMapBean> candidateEmployerMapBean2 = candidateEmployerMapBean1.getItems();
		String employeeName, employeeDesg;
		employeeName = employeeDesg = null;
		for (CandidateEmployerMapBean candidateEmployerMapBean3 : candidateEmployerMapBean2) {
			employeeName = candidateEmployerMapBean3.getEmployerName();
			employeeDesg = candidateEmployerMapBean3.getDesignationName();
		}
		Assert.assertEquals(response1.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Assert.assertEquals(employeeName, candidateEmployerMapBean.getEmployerName(), "Showing wrong  details");
		Assert.assertEquals(employeeDesg, candidateEmployerMapBean.getDesignationName(), "Showing wrong  details");
	}

	/**
	 * Adding New skill to the candidate details
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void addNewSkill(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		CandidateSkillMapBean candidateSkillMapBean = new CandidateSkillMapBean();
		String candidateId = ProfileHelper.createFullProfile(data);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Logging.log("candidateId: " + candidateId);
		CandidateSkillMapBean skill=ProfileHelper.addNewSkill(candidateId, candidateSkillMapBean);
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		CollectionEntity<CandidateSkillMapBean> candidateSkillMapBean1 = response1.getCandidate()
				.getCandidateSkillMapBean();
		Collection<CandidateSkillMapBean> candidateSkillMapBean2 = candidateSkillMapBean1.getItems();
		List<String> skillnames=new ArrayList<>();
		for (CandidateSkillMapBean candidateSkillMapBean3 : candidateSkillMapBean2) {
			String skillname=candidateSkillMapBean3.getSkill();
			skillnames.add(skillname);
		}
		Assert.assertEquals(response1.getCandidate().getId(), candidateId, "Showing wrong details");
		Assert.assertTrue(skillnames.contains(skill.getSkill()));
		
	}

	/**
	 * removing skill of candidate
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void removeSkill(TestData data) throws Exception {
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		String candidateId = ProfileHelper.createFullProfile(data);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Logging.log("candidateId: " + candidateId);
		Collection<CandidateSkillMapBean> id = response.getCandidate().getCandidateSkillMapBean().getItems();
		String skillId = null;
		for (CandidateSkillMapBean candidateSkillMapBean : id) {
			skillId = candidateSkillMapBean.getId();
		}
		System.out.println("skillId" + skillId);
		profileBizServiceConsumer.removeCandidateSkill(candidateId, skillId);
		Profile response2 = profileBizServiceConsumer.getProfile(candidateId, "full");
		Collection<CandidateSkillMapBean> id1 = response2.getCandidate().getCandidateSkillMapBean().getItems();
		String skillId1 = null;
		for (CandidateSkillMapBean candidateSkillMapBean : id1) {
			skillId1 = candidateSkillMapBean.getId();
		}
		Assert.assertEquals(skillId1, null, "Showing wrong candidateId details");
	}

	/**
	 * Removing skill of the candidate who doesn't have skill
	 * 
	 * @param data
	 * @throws Exception
	 */
	public static void removeSkillForNoSkillCandidate(TestData data) throws Exception {

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		CandidateSkillMapBean candidateSkillMapBean = new CandidateSkillMapBean();
		String candidateId = ProfileHelper.createFullProfile(data);
		Profile response = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
		Logging.log("candidateId: " + candidateId);
		String skillId = candidateSkillMapBean.getId();
		if (skillId == null) {
			profileBizServiceConsumer.removeCandidateSkill(candidateId, skillId);
			throw new Exception("No skill attached to the candidate");
		}
		Profile response1 = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertEquals(response1.getCandidate().getId(), candidateId, "Showing wrong candidateId details");
	}

	public static String getHireAS(String fileName) {
		try {
			profileBizServiceConsumer = new ProfileBizServiceConsumer();
			Profile profileRequestJson = ProfileJson.getProfileJson(fileName);

			String candidateId = profileBizServiceConsumer.createProfile(profileRequestJson);
			Logging.log("candidateId: " + candidateId);
			profileBizServiceConsumer.getHireAs(candidateId);
			return candidateId;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Got Exception In Catch Block. Hence failing TC");
			return null;
		}

	}
	
	public static void getViewed(String fileName){
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Profile profileRequestJson = ProfileJson.getProfileJson(fileName);
		String candidateId = profileBizServiceConsumer.createProfile(profileRequestJson);
		profileBizServiceConsumer.alsoView(candidateId);
		
	}
	
	public static void validtaeAlreadyViewedCandidatesForRecruiter() {

		String candidateId = ProfileHelper.createProfile();
		profileBizServiceConsumer = new ProfileBizServiceConsumer();

		CandidateAlreadyViewedVO candidateAlreadyViewedVO = new CandidateAlreadyViewedVO();
		List<String> candidates = new ArrayList<String>();
		candidates.add(candidateId);
		candidateAlreadyViewedVO.setCandidateIds(candidates);

		Map<String, Boolean> response1 = profileBizServiceConsumer
				.alreadyViewedCandidatesForRecruiter(candidateAlreadyViewedVO);
		for (String key : response1.keySet()) {
			Assert.assertEquals(key, candidateId, "CandidateId not showing in response !!");
		}
		for (Boolean key : response1.values()) {
			Assert.assertFalse(key, "Candidate is not viewed but showing as already viewd as 'true'");
		}

		profileBizServiceConsumer.getProfile(candidateId, "full");

		Map<String, Boolean> response2 = profileBizServiceConsumer
				.alreadyViewedCandidatesForRecruiter(candidateAlreadyViewedVO);
		for (String key : response2.keySet()) {
			Assert.assertEquals(key, candidateId, "CandidateId not showing in response !!");
		}
		for (Boolean key : response2.values()) {
			Assert.assertTrue(key, "Candidate is viewed but showing as already viewd as 'false'");
		}
	}

	public static void validateGetRecruitersWhoViewed() {   
		String candidateId = ProfileHelper.createProfile();   
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		List<RecruiterVO> recruiterDetails = profileBizServiceConsumer.getRecruitersWhoViewed(candidateId);
		for (RecruiterVO recruiterVO : recruiterDetails) {
			Assert.assertNull(recruiterVO.getRecruiterId(),
					"RecruiterId should be null as the candidate is not viewed by any recruiters !!");
			Assert.assertNull(recruiterVO.getRecruiterName(),
					"RecruiterName should be null as the candidate is not viewed by any recruiters !!");
		}    
	}

	public static void validateIsCandidateViewed() {
		String candidateId = ProfileHelper.createProfile();
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Profile profileResponse_FirstView = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertFalse(profileResponse_FirstView.getAdditionalDetails().getIsViewed(),
				"isViewed is showing as true for the first time view !!");

		Profile profileResponse_SecondView = profileBizServiceConsumer.getProfile(candidateId, "full");
		Assert.assertTrue(profileResponse_SecondView.getAdditionalDetails().getIsViewed(),
				"isViewed is showing as false for the second time view !!");
	}
}

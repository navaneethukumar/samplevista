package com.spire.crm.biz.profileService.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

/**
 * @author Santosh C
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ProfileBizServiceTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;

	final static String DATAPROVIDER_NAME = "PROFILE";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/biz/profileService/test/";
	final static String CSV_FILENAME = "ProfileBizService_TestData.csv";
	final static String CSV_PATH = CSV_DIR + CSV_FILENAME;

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = CSV_PATH;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ProfileBizServiceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Create Profile[Sanity Tests]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createProfile_Validate_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createProfile_Validate_Sanity(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.createProfile_Validate(data);
	}

	/**
	 * Create Profile[SRG tests]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createProfile_Validate_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createProfile_Validate_SRG(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.createProfile_Validate(data);
	}

	/**
	 * Create full Profile[with all details]
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "validateCreatedProfile", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void validateCreatedProfile(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.createFullProfile_Validate(data);
	}

	/**
	 * Get Profile
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getProfile_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getProfile_Sanity(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.getProfile(data);
	}

	/**
	 * Get Profile
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getProfile_SRG", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getProfile_SRG(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.getProfile(data);
	}

	/**
	 * Get Profile
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "createSingleProfile_Json_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createSingleProfile_Json_Sanity(SpireTestObject testObject, TestData data) {

		ProfileJson.createProfile_Json(data.getData());
	}

	/**
	 * Create Bulk Profile[Sanity Tests]
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "createBulkProfile_Validate_sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void createBulkProfile_Validate_sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.createBulkProfile_Json(data.getData());
	}

	/**
	 * CreateBulk Profile[SRG Tests]
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "createBulkProfile_Validate_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createBulkProfile_Validate_SRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.createBulkProfile_Json(data.getData());
	}

	/**
	 * Create Bulk Profile[LRG tests]
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "createBulkProfile_Validate_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void createBulkProfile_Validate_LRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.createBulkProfile_Json(data.getData());
	}

	/**
	 * Get Bulk Profile Sanity
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "getBulkProfile_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getBulkProfile_Sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.getBulkProfile_Json(data.getData());
	}

	/**
	 * Get Bulk Profile Invalid LRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "getBulkProfileInvalid_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void getBulkProfileInvalid_LRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.getBulkProfileInvalid_Json(data.getData());

	}

	/**
	 * Get Bulk Profile LRG
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "getBulkProfile_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void getBulkProfile_LRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.getBulkProfile_Json(data.getData());

	}

	/**
	 * Update candidate Basic details Profile
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "update_candidateBasicDetails_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void update_candidateBasicDetails_Sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.updateBasicProfile(data);
	}

	/**
	 * Update candidate Education details Profile
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "update_candidateEducationDetails_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void update_candidateEducationDetails_Sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.updateEducationDetails(data);
	}

	/**
	 * Update candidate Social details Profile
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "update_candidateSocialDetails_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void update_candidateSocialDetails_Sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.updateSocialDetail(data);
	}

	/**
	 * Adding candidate new Education details
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exceptions
	 */
	@Test(groups = { "addNewEducationalDetails_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void addNewEducationalDetails_SRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.addEducationDetails(data);
	}

	/**
	 * Adding candidate Employee details
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exceptions
	 */
	@Test(groups = { "addNewEmployerDetails_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void addNewEmployerDetails_SRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.addEmployerDetails(data);
	}

	/**
	 * Adding new skill to the candidate
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exceptions
	 */
	@Test(groups = { "addNewSkillToCandidate_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void addNewSkillToCandidate_Sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.addNewSkill(data);
	}

	/**
	 * Removing skill of the candidate
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exceptions
	 */
	@Test(groups = { "removeSkillOfCandidate_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void removeSkillOfCandidate_SRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.removeSkill(data);
	}

	/**
	 * Removing skill of the candidate who doesn't have skill
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exceptions
	 */
	@Test(groups = { "removeSkillOfCandidate_LRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void removeSkillOfCandidate_LRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileBizValidation.removeSkillForNoSkillCandidate(data);
	}

	/**
	 * Get Profiles By CRM Filter
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "getProfilesByCrmFilter_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getProfilesByCrmFilter_Sanity(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.getProfilesByCrmFilter(data.getData());
	}

	/**
	 * Get Profiles By CRM Filter
	 * 
	 * @param testObject
	 * @param data
	 * @throws Exception
	 */
	@Test(groups = { "getProfilesByCrmFilter_SRG", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void getProfilesByCrmFilter_SRG(SpireTestObject testObject, TestData data) throws Exception {

		ProfileJson.getProfilesByCrmFilter(data.getData());
	}

	/**
	 * Get Profiles By CRM Filter
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "getProfilesByCrmFilter_LRG", "LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void getProfilesByCrmFilter_LRG(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.getProfile(data);
	}

	@Test(groups = { "getHireAs", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getHireAs(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.getHireAS(data.getData());
	}

	@Test(groups = { "GetAlsoViewed", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void GetAlsoViewed(SpireTestObject testObject, TestData data) {
		ProfileBizValidation.getViewed(data.getData());
	}
	
	/**
	 * alreadyViewedCandidatesForRecruiter_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "alreadyViewedCandidatesForRecruiter_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void alreadyViewedCandidatesForRecruiter_Sanity(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.validtaeAlreadyViewedCandidatesForRecruiter();
	}

	/**
	 * getRecruitersWhoViewed_Sanity
	 * 
	 * @param testObject
	 * @param data
	 */   
	@Test(groups = { "getRecruitersWhoViewed_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void getRecruitersWhoViewed_Sanity(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.validateGetRecruitersWhoViewed();
	}

	/**
	 * addCandidateView_Sanity  
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "isCandidateView_Sanity", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void isCandidateView_Sanity(SpireTestObject testObject, TestData data) {

		ProfileBizValidation.validateIsCandidateViewed();
	}

}

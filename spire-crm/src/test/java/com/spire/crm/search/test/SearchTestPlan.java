package com.spire.crm.search.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pages.SearchPage;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;
import com.spire.crm.restful.util.CreateProfile;
import com.spire.crm.restful.util.ProfileDataPoints;
import com.spire.crm.restful.util.RequisitionDataPointsBean;

import spire.crm.profiles.bean.Profile;
import spire.talent.entity.profileservice.beans.CandidateBean;

/**
 * 
 * @author Manaswini
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class SearchTestPlan extends UITestPlan {

	/* WebDriver driver = null; */
	@DataProvider(name = "SearchTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/search/test/SearchTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SearchBean", SearchBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					SearchTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@DataProvider(name = "AdvanceSearchTestData")
	public static Iterator<Object[]> getCandidateInfo1(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/search/test/AdvanceSearchTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SearchBean", SearchBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					SearchTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@DataProvider(name = "FreetextSearchTestData")
	public static Iterator<Object[]> getCandidateInfo2(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/search/test/FreetextSearchTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SearchBean", SearchBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					SearchTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}
	
	@DataProvider(name = "RequisitionSearchTestData")
	public static Iterator<Object[]> getCandidateInfo3(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/search/test/RequisitionSearchTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("RequisitionDataPointsBean", RequisitionDataPointsBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					SearchTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}
	@DataProvider(name = "profileData")
	public static Iterator<Object[]> getCandidateInfo4(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/search/test/ProfileData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ProfileDataPoints", ProfileDataPoints.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					SearchTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}
	/*
	 * @BeforeTest(alwaysRun = true)
	 * 
	 * @Parameters({ "username", "password", "URL" }) public void
	 * readingProperties(String username, String password, String URL) throws
	 * InterruptedException { this.USERNAME = username; this.PASSWORD =
	 * password; this.URL = URL; loginPageUtil = new LoginPageUtil(null, true);
	 * driver = loginPageUtil.launchApplication(this.URL);
	 * initializeDriver(driver, null); loginPageUtil.federationLogin(USERNAME,
	 * PASSWORD); }
	 */

	// To keep the session
	/*
	 * @Override
	 * 
	 * @AfterMethod(alwaysRun = true) public void afterTestMethod(Method method)
	 * { }
	 */

	@Test(groups = { "verifySearchResultSanity", "Sanity",
			"ValidateSearchResultFacetingProfileSanity" }, dataProvider = "SearchTestData")
	public void verifySearchResultSanity(SpireTestObject testObject,
			SearchBean searchBean) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);
		searchPage.searchCriteria("spireskill1", "Skill");
		searchPage.verifySearchResultPageContentIsNotNull();
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyCandidateRecord();
	}

	@Test(groups = { "verifyFacetingBySearchBarSanity", "Sanity",
			"ValidateSearchResultFacetingProfileSanity" }, dataProvider = "SearchTestData")
	public void verifyFacetingBySearchBarSanity(SpireTestObject testObject,
			SearchBean searchBean) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);
		searchPage.searchCriteria("spireskill1", "Skill");
		searchPage.verifySearchResultPageContentIsNotNull();
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyFacetingInSearchResults();

	}

	@Test(groups = { "verifySearchBySingleAttributeSanity", "Sanity" }, dataProvider = "SearchTestData")
	public void verifySearchBySingleAttributeSanity(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);

		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);

		if (searchbean.getSearchType().get(0).equals("crm Stage")) {
			searchPage.searchCriteria(searchbean.getSearchCriteria().get(0),
					searchbean.getSearchType().get(0).replace("crm", "CRM"));
		} else {
			searchPage.searchCriteria(searchbean.getSearchCriteria().get(0),
					searchbean.getSearchType().get(0));
		}
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.validateSearchBySingAttribute(searchbean);
	}

	@Test(groups = { "verifySearchBySingleAttributeSRG", "SRG" }, dataProvider = "SearchTestData")
	public void verifySearchBySingleAttributeSRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);

		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);

		if (searchbean.getSearchType().get(0).equals("crm Stage")) {
			searchPage.searchNOTOperation(searchbean.getSearchOperation()
					.get(0));
			searchPage.searchCriteriaForNot(
					searchbean.getSearchCriteria().get(0), searchbean
							.getSearchType().get(0).replace("crm", "CRM"));
		} else {
			searchPage.searchNOTOperation(searchbean.getSearchOperation()
					.get(0));
			searchPage.searchCriteriaForNot(
					searchbean.getSearchCriteria().get(0), searchbean
							.getSearchType().get(0));
		}
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.validateSearchBySingAttribute(searchbean);
	}

	@Test(groups = { "verifySearchByAttributesSanity", "Sanity" }, dataProvider = "SearchTestData")
	public void verifySearchByAttributesSanity(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifySearchByAttributes(searchbean);
	}

	@Test(groups = { "verifySearchByAttributesSRG", "SRG",
			"SearchBar_MultiAttribute2" }, dataProvider = "SearchTestData")
	public void verifySearchByAttributesSRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifySearchByAttributes(searchbean);
	}

	@Test(groups = { "verifySearchByAttributesLRG", "LRG" }, dataProvider = "SearchTestData")
	public void verifySearchByAttributesLRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifySearchByAttributes(searchbean);
	}

	@Test(groups = { "verifySearchByFullParanthesisSanity", "Sanity" }, dataProvider = "SearchTestData")
	public void verifySearchByFullParanthesisSanity(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.searchByFullParanthesis(searchbean);
	}

	@Test(groups = { "verifySearchByFullParanthesisSRG", "SRG" }, dataProvider = "SearchTestData")
	public void verifySearchByFullParanthesisSRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.searchByFullParanthesis(searchbean);
	}

	@Test(groups = { "verifySearchByFullParanthesisLRG", "SRG" }, dataProvider = "SearchTestData")
	public void verifySearchByFullParanthesisLRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.searchByFullParanthesis(searchbean);
	}

	@Test(groups = { "verifyProfileSkillLinkSanity", "Sanity",
			"ValidateSearchResultFacetingProfileSanity" }, dataProvider = "SearchTestData")
	public void verifyProfileSkillLinkSanity(SpireTestObject testObject,
			SearchBean searchbean) throws InterruptedException,
			JsonParseException, JsonMappingException, IOException {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		ProfileBizServiceConsumer profileBizServiceConsumer = new ProfileBizServiceConsumer(
				ContextManager.getGlobalContext().getUserid(), ContextManager
						.getGlobalContext().getPassword());
		Profile candProfile = new Profile();
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setTotalExperienceMnth((short) 26);
		candidateBean.setFirstName("vishal");
		candidateBean.setLastName("senha");
		candidateBean.setLocationName("Bangalore");
		candidateBean.setPrimaryContactNumber("9999999999");
		candidateBean.setPrimaryEmailId("vishal@gmail.com");
		candidateBean.setSourceName("openweb");
		candidateBean.setSourceType("openweb");
		candidateBean.setStatus("applied");
		candidateBean.setCandidateSkillMapBean(ProfileHelper
				.addSkill(searchbean.getSearchCriteria().get(0)));
		candidateBean.setCandidateEducationMapBean(ProfileHelper.addEducation(
				"BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer(
				"Automation Engineer", "Intuit"));
		candProfile.setCandidate(candidateBean);
		candProfile.setCrm(ProfileHelper.setCRM());
		String candidateId = profileBizServiceConsumer
				.createProfile(candProfile);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.profileSkillLink(candidateId,
				searchResultPageUtil.driver, searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByAttributesSanity", "Sanity" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByAttributesSanity(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyAdvanceSearchByAttributes(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByAttributesSRG", "SRG" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByAttributesSRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyAdvanceSearchByAttributes(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByAttributesLRG", "LRG" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByAttributesLRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyAdvanceSearchByAttributes(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByFullParanthesisSanity", "Sanity" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByFullParanthesisSanity(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.advanceSearchFullParanthesis(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByFullParanthesisSRG", "SRG" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByFullParanthesisSRG(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.advanceSearchFullParanthesis(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByFullParanthesisLRG", "LRG" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByFullParanthesisLRG(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.advanceSearchFullParanthesis(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByFreetextCombWithGuidedSanity", "Sanity" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByFreetextCombWithGuidedSanity(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.combinationOfFreetext(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByFreetextCombWithGuidedSRG", "SRG" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByFreetextCombWithGuidedSRG(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.combinationOfFreetext(searchbean);
	}

	@Test(groups = { "verifyAdvanceSearchByFreetextCombWithGuidedLRG", "LRG" }, dataProvider = "AdvanceSearchTestData")
	public void verifyAdvanceSearchByFreetextCombWithGuidedLRG(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.combinationOfFreetext(searchbean);
	}

	@Test(groups = { "verifyFreetextSearchBySearchBarSanity", "Sanity" }, dataProvider = "FreetextSearchTestData")
	public void verifyFreetextSearchBySearchBarSanity(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.freeTextSearchBySearchBar(searchbean);
	}

	@Test(groups = { "verifyFreetextSearchBySearchBarSRG", "SRG" }, dataProvider = "FreetextSearchTestData")
	public void verifyFreetextSearchBySearchBarSRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.freeTextSearchBySearchBar(searchbean);
	}

	@Test(groups = { "verifyFreetextSearchBySearchBarLRG", "LRG" }, dataProvider = "FreetextSearchTestData")
	public void verifyFreetextSearchBySearchBarLRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.freeTextSearchBySearchBar(searchbean);
	}

	@Test(groups = { "verifyFreetextSearchByAdvSearchSanity", "Sanity" }, dataProvider = "FreetextSearchTestData")
	public void verifyFreetextSearchByAdvSearchSanity(
			SpireTestObject testObject, SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.freeTextSearchByAdvSearch(searchbean);
	}

	@Test(groups = { "verifyFreetextSearchByAdvSearchSRG", "SRG" }, dataProvider = "FreetextSearchTestData")
	public void verifyFreetextSearchByAdvSearchSRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.freeTextSearchByAdvSearch(searchbean);
	}

	@Test(groups = { "verifyFreetextSearchByAdvSearchLRG", "LRG" }, dataProvider = "FreetextSearchTestData")
	public void verifyFreetextSearchByAdvSearchLRG(SpireTestObject testObject,
			SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.freeTextSearchByAdvSearch(searchbean);
	}
	
	@Test(groups = { "verifyRequisitionSearchSanity", "Sanity" }, dataProvider = "RequisitionSearchTestData")
	public void verifyRequisitionSearchSanity(
			SpireTestObject testObject, RequisitionDataPointsBean requisitionDataPointsBean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyRequsition(testObject,requisitionDataPointsBean);
	}
	
	@Test(groups = { "verifyRequisitionSearchSRG", "SRG" }, dataProvider = "RequisitionSearchTestData")
	public void verifyRequisitionSearchSRG(
			SpireTestObject testObject, RequisitionDataPointsBean requisitionDataPointsBean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyRequsition(testObject,requisitionDataPointsBean);
	}
	
	
	@Test(groups = { "verifycreateFullProfile", "SRG" }, dataProvider = "profileData")
	public void verifycreateFullProfile(
			SpireTestObject testObject, ProfileDataPoints profileDataPoints) throws Exception {
		CreateProfile.profileCreation(profileDataPoints);
	}
	
	@Test(groups = { "verifySearchRetainValuesInFaceting", "SRG" }, dataProvider = "SearchTestData")
	public void verifySearchRetainValuesInFaceting(
			SpireTestObject testObject,SearchBean searchbean) throws Exception {
		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);
			searchPage.searchCriteria(searchbean.getSearchCriteria().get(0),
					searchbean.getSearchType().get(0));
		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(
				loginPageUtil.driver, false);
		searchResultPageUtil.verifyRetainInFaceting(testObject);
	}

	/*
	 * @AfterMethod public void tearDown() { driver.quit(); }
	 */
}

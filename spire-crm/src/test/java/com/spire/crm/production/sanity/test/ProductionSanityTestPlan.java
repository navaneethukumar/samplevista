package com.spire.crm.production.sanity.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.ws.rs.core.Response;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import spire.match.entities.SearchResult;
import spire.search.commons.entities.SearchInput;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;
import com.spire.crm.pages.CandidateProfilePage;
import com.spire.crm.pages.SearchPage;
import com.spire.crm.restful.biz.consumers.CrmSearchBizServiceConsumer;
import com.spire.crm.restful.util.SearchBizBean;
import com.spire.crm.search.test.SearchBean;
import com.spire.crm.smoke.helper.SmokeTestHelper;


/**
 * 
 * @author Pradeep
 * 
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ProductionSanityTestPlan extends TestPlan {

	String USERNAME = null;
	String PASSWORD = null;
	String URL = null;
	String REALM = null;
	WebDriver driver = null;
	ObjectMapper objectMapper = new ObjectMapper();

	@DataProvider(name = "ProductionSanityTestPlan")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/production/sanity/test/ProductionSanityTestPlan.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SearchBizBean", SearchBizBean.class);
			entityClazzMap.put("SearchBean", SearchBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ProductionSanityTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@BeforeTest(alwaysRun = true)
	@Parameters({ "username", "password", "URL", "REALM" })
	public void readingProperties(String username, String password, String URL, String REALM) {
		this.USERNAME = username;
		this.PASSWORD = password;
		this.URL = URL;
		this.REALM = REALM;
	}

	// To keep the session
	@Override
	@AfterMethod(alwaysRun = true)
	public void afterTestMethod(Method method) {
	}

	@Test(groups = {
			"verifyUserService" }, dataProvider = "ProductionSanityTestPlan", priority = 0, invocationCount = 1, enabled = false)
	public void verifyUserService(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) {

		SmokeTestHelper helper = new SmokeTestHelper();
		helper.validateUserService(USERNAME, PASSWORD, REALM);

	}

	/**
	 * Verifying Login successfully done and verifying page elements.
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyLoginSuccess" }, priority = 1, dataProvider = "ProductionSanityTestPlan")
	public void verifyLoginSuccess(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) throws Exception {
		try {
			Logging.log("Start :: verifyLoginSuccess !!!");
			LoginPageUtil loginPageUtil = new LoginPageUtil(null, false);
			driver = loginPageUtil.launchApplication(this.URL);
			initializeDriver(driver, testObject);
			Logging.log("URL >>" + this.URL);
			Logging.log("USERNAME >>" + this.USERNAME);
			Logging.log("PWD >>" + this.PASSWORD);
			loginPageUtil.federationLogin(USERNAME, PASSWORD);
			Logging.log("End :: verifyLoginSuccess !!!");
		} catch (Exception e) {
			Assert.fail("verifyLoginSuccess failed !!!" + e.getMessage());
		}

	}

	/**
	 * Verifying Home page loaded successfully.
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyHomePageRenderedSuccessfully" }, priority = 2, dataProvider = "ProductionSanityTestPlan")
	public void verifyHomePageRenderedSuccessfully(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) throws Exception {

		Logging.log("Start :: verifyHomePageRenderedSuccessfully !!!");
		HomePageUtil homeUtil = new HomePageUtil(this.driver, false);
		homeUtil.validateHomePageElements();
		Logging.log("End :: verifyHomePageRenderedSuccessfully !!!");
	}

	/**
	 * Verifying able to Search and results are proper
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifySearchAndResults" }, priority = 3, dataProvider = "ProductionSanityTestPlan")
	public void verifySearchAndResults(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) throws Exception {

		Logging.log("Start :: verifySearchAndResults !!!");
		SearchPage searchPage = new SearchPage(this.driver, false);
		initializeDriver(driver, testObject);
		searchPage.searchCriteria("oracle", "Skill");
		searchPage.verifySearchResultPageContentIsNotNull();
		// click on profile link
		searchPage.clickOnProfileLinkFromSearchResult();
		Logging.log("End :: verifySearchAndResults !!!");
	}

	/**
	 * Verifying profile page is rendered properly
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = {
			"verifyProfilePageRenderedWithAllContents" }, priority = 4, dataProvider = "ProductionSanityTestPlan")
	public void verifyProfilePageRenderedWithAllContents(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) throws Exception {

		Logging.log("Start :: verifyProfilePageRenderedWithAllContents !!!");
		CandidateProfilePage candidateProfilePage = new CandidateProfilePage(this.driver, false, null);
		candidateProfilePage.verifyProfileSummaryDetailsNotNull();
		Logging.log("End :: verifyProfilePageRenderedWithAllContents !!!");
	}

	/**
	 * Verifying Notifications are showing in HomePage
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyNotifications" }, priority = 5, dataProvider = "ProductionSanityTestPlan")
	public void verifyNotifications(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) throws Exception {
		try {
			Logging.log("Start :: verifyNotifications !!!");
			LoginPageUtil loginPageUtil = new LoginPageUtil(null, false);
			driver = loginPageUtil.launchApplication(this.URL);
			initializeDriver(driver, testObject);
			loginPageUtil.federationLogin(USERNAME, PASSWORD);

			HomePageUtil homeUtil = new HomePageUtil(this.driver, false);
			homeUtil.verifyNotifications();
		} catch (Exception e) {
			Assert.fail("verifyNotifications failed !!!" + e.getMessage());
		}
	}

	@Test(groups = { "verifySearchForAcquraAndAll" }, priority = 6, dataProvider = "ProductionSanityTestPlan")
	public void verifySearchForAcquraAndAll(SpireTestObject testObject,SearchBizBean searchBizBean,SearchBean searchBean) throws JsonParseException, JsonMappingException, IOException {
		Logging.log("Start :: verifySearchResultsForAcquraAndAll !!!");
		CrmSearchBizServiceConsumer crmSearchBizServiceConsumer = new CrmSearchBizServiceConsumer(USERNAME,PASSWORD,REALM);
		String searchQueryString=searchBean.getSearchQuery().replaceAll("\"\"", "\"");
    	String searchAttribute=searchBean.getSearchAttributeMap().replaceAll("\"\"", "\"");
    	String searchQuery=searchQueryString+","+searchAttribute;
    	Logging.log("searchQuery:" + searchQuery);
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SearchInput searchInput = objectMapper.readValue(searchQuery,
				new TypeReference<SearchInput>() {
				});
		Response response = crmSearchBizServiceConsumer.searchQueryWithoutExpertSkill(searchInput,searchBizBean);
		Assert.assertEquals(response.getStatus(), 200,"Throwing wrong status");
		SearchResult searchResult = response.readEntity(SearchResult.class);
		Assert.assertTrue(searchResult.getTotal()>=0,"Not getting the serach results");
		if(searchResult.getTotal()>0)
			Assert.assertTrue(!(searchResult.getEntities().isEmpty()), "Not getting the candidates for searched query");
		Assert.assertTrue(searchResult.getSearchQuery().contains(searchBizBean.getSearchQueryString()),"search wrong query");		
	}
	
	@AfterTest(alwaysRun = true)
	public void tearDown() {
		try {
			if (driver != null) {
				new HomePageUtil(driver, false).logout();
				driver.close();
			}
		} catch (Exception e) {
			
		}
	}
}
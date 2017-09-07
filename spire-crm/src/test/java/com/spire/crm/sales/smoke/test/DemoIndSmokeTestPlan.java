package com.spire.crm.sales.smoke.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.crm.profiles.bean.Profile;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.DemoIndUITestPlan;
import com.spire.common.ProdUITestPlan;
import com.spire.crm.email.EmailBean;
import com.spire.crm.pageUtils.EngageCandidateDeatilsPageUtils;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;
import com.spire.crm.smoke.helper.SmokeTestHelper;
import com.spire.crm.smoke.helper.UiValidator;
import com.spire.jobpost.JobPostBean;
import com.spire.jobpost.JobPostsPageUtil;

/**
 * 
 * @author Santosh C
 *
 */
@Test(groups = { "SANITY_TESTS" }, retryAnalyzer = TestRetryAnalyzer.class)
public class DemoIndSmokeTestPlan extends DemoIndUITestPlan{

	Logger LOGGER = Logger.getLogger(DemoIndSmokeTestPlan.class.getName());

	@DataProvider(name = "DemoIndSmokeTestPlan")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/sales/smoke/test/SalesSmokeTestPlan.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("JobPostBean", JobPostBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(DemoIndSmokeTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	
	/**
	 * Verifying ProfileCreation, validate from DB and ES
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyProfileCreation","SANITY" }, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyProfileCreation(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		try{
			ProfileBizServiceConsumer consumer = new ProfileBizServiceConsumer(this.USERNAME, this.PASSWORD);
			consumer.HEADERS = false;
			Profile profileRequest = consumer.getProfile(this.CANDIDATE_ID, "full");
			// SmokeTestHelper.validateESData(profileRequest, esResponse);
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.validateCreatedProfile(homeUtil.driver, this.CANDIDATE_ID, profileRequest);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
					
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	
	}
	
	/**
	 * verifyActivityCreation, validate from DB and ES
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyActivityCreation" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyActivityCreation(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(login(), this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.validateActivityStream(this.CANDIDATE_ID);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	/**
	 * Verifying ProfileUpdation, validate from DB and ES
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyProfileUpdation" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan" , retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyProfileUpdation(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login(); 			
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.updateCandidateDetails(homeUtil.driver, this.CANDIDATE_ID);
			ui.validateUpdatedDetails(this.USERNAME, this.PASSWORD, this.CANDIDATE_ID, this.SEARCH_MATCH_HOST);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	/**
	 * verifyProfileDetailsDeletion, validate from DB and ES
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyProfileDetailsDeletion" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan" , retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyProfileDetailsDeletion(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.deleteProfileDetails(this.USERNAME, this.PASSWORD, this.CANDIDATE_ID, this.SEARCH_MATCH_HOST);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		

	}

	/**
	 * verifySkillsAddition, validate from DB and ES
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifySkillsAddition" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifySkillsAddition(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.validateSkillsAdd(this.USERNAME, this.PASSWORD, this.SEARCH_MATCH_HOST, this.CANDIDATE_ID);
			
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		

	}

	/**
	 * verifyActivityElements, DateRange Dropdowns
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyActivityElements","SANITY" }, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyActivityElements(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.validateActivityStreamElements();
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	

	/**
	 * Verifying Create Email template and delete from List
	 * 
	 * @param testObject
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(groups = { "verifyCreateEmailTemplate" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyCreateEmailTemplate(SpireTestObject testObject, JobPostBean job) throws InterruptedException {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			EmailBean email = ui.setUpTemplate();
			ui.createNewTemplate(email);

		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	

	/**
	 * Verifying clone Camp and delete from List
	 * 
	 * @param testObject
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(groups = { "verifyCloneCampaign","SANITY" }, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyCloneCampaign(SpireTestObject testObject, JobPostBean job) throws InterruptedException {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.cloneCampaign();
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		

	}

	/**
	 * 
	 * verifySearchResults, validate from DB and ES
	 * 
	 * Verifying Create Email template and delete from List
	 * 
	 * @param testObject
	 * @throws Exception
	 */

	@Test(groups = { "verifySearchResults" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifySearchResults(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.validateSearchResults();
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	/**
	 * verifyAttachLabel
	 */
	@Test(groups = { "verifyAttachLabel" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyAttachLabel(SpireTestObject testObject, JobPostBean job) throws InterruptedException {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.attachLabelInCandidateDetailPage(homeUtil.driver, this.CANDIDATE_ID);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	/**
	 * 
	 * Verifying Create Email template and delete from List
	 * 
	 * 
	 * @param testObject
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(groups = { "verifyHomePageElements" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyHomePageElements(SpireTestObject testObject, JobPostBean job) throws InterruptedException {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(driver);
			ui.validateHomePageElements(driver);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		

	}

	@Test(groups = { "verifyPipeline" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyPipeline(SpireTestObject testObject, JobPostBean job) throws InterruptedException {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			String currentUrl = homeUtil.driver.getCurrentUrl();
			homeUtil.driver.get(currentUrl + "Profile/" + this.CANDIDATE_ID);
			EngageCandidateDeatilsPageUtils engageCandidateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(
					homeUtil.driver, false, this.CANDIDATE_ID);
			try {
				Thread.sleep(6000);
			} catch (Exception ee) {
			}
			engageCandidateDeatilsPageUtils.validateStateChange("Rejected");
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		
	}

	@Test(groups = { "uploadFile" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void uploadFile(SpireTestObject testObject, JobPostBean job) throws Exception{

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			JobPostsPageUtil jobpost = new JobPostsPageUtil(homeUtil.driver, false);
			jobpost.uploadFile(job);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		

	}

	@Test(groups = { "createNewJobPost","SANITY" }, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void createNewJobPost(SpireTestObject testObject, JobPostBean job) throws Exception{

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			JobPostsPageUtil jobpost = new JobPostsPageUtil(homeUtil.driver, false);
			jobpost.createNewJobPost(job);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}

	@Test(groups = { "postJobFromURL" ,"SANITY"}, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void postJobFromURL(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			JobPostsPageUtil jobpost = new JobPostsPageUtil(driver, false);
			jobpost.postJobFromURL(job);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
	}
	
	/**
	 * 
	 * verifyEngagementScore, validate from DB and ES
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyEngagementScore","SANITY" }, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifyEngagementScore(SpireTestObject testObject, JobPostBean job) throws Exception {

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver,this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			Logging.log("CANDIDATE_ID_SCORE id is"+CANDIDATE_ID_SCORE);
			if (this.CANDIDATE_ID!=null && !this.CANDIDATE_ID.equalsIgnoreCase("123456"))
				ui.validateEngagementScore(this.CANDIDATE_ID);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
				
	}
	
	@Test(groups = { "verifySimilarProfiles","SANITY" }, dataProvider = "DemoIndSmokeTestPlan", retryAnalyzer = TestRetryAnalyzer.class)
	public void verifySimilarProfiles(SpireTestObject testObject, JobPostBean job)  throws Exception{

		WebDriver driver = null;
		
		try{
			driver=login();
			HomePageUtil homeUtil =new HomePageUtil(driver, this.URL);
			UiValidator ui = new UiValidator(homeUtil.driver);
			ui.validateSimilarProfiles(this.USERNAME, this.PASSWORD);
		}finally{
			try {
				if (driver!=null){
					new HomePageUtil(driver, false).logout();
					driver.quit();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
}

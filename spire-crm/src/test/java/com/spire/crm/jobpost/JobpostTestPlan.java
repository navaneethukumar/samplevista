package com.spire.crm.jobpost;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.jobpost.JobPostBean;
import com.spire.jobpost.JobPostsPageUtil;

/**
 * 
 * @author Sangeeta
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class JobpostTestPlan extends UITestPlan {

	@DataProvider(name = "JobpostTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/jobpost/JobpostTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("JobPostBean", JobPostBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(JobpostTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * 
	 * Verify all the elements are displayed in jobs page
	 *
	 */

	@Test(groups = { "validatePageElementsDisplayed" }, dataProvider = "JobpostTestData", priority=1)
	public void validatePageElementsDisplayed(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.validatePageElementsDisplayed();

	}

	/**
	 * Verify post a job from URL icon is working o not
	 * 
	 */

	@Test(groups = { "postJobFromURL" }, dataProvider = "JobpostTestData", priority=2)
	public void postJobFromURL(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.postJobFromURL(job);
	}

	/**
	 * 
	 * Verify upload file icon and post job
	 *
	 */
	/*@Test(groups = { "uploadFile" }, dataProvider = "JobpostTestData", priority=3)
	public void uploadFile(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.uploadFile(job);
	}
*/
	/**
	 * 
	 * create new job post by filling all fields
	 *
	 */
	@Test(groups = { "createNewJobPost" }, dataProvider = "JobpostTestData", priority=4)
	public void createNewJobPost(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.createNewJobPost(job);

	}

	/**
	 * Post a job and verify jobTitle is displayed in open positions and onclick
	 * of Jobtitle it should navigate to search page
	 * 
	 */
	@Test(groups = { "verifyJobTitle_OpenPosition" ,"SRG" }, dataProvider = "JobpostTestData", priority=5)
	public void verifyJobTitle_OpenPosition(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.verifyJobTitle_OpenPosition(job);

	}

	/**
	 * Post a job and verify RequisitionID is displayed in open positions and
	 * onclick of RequisitionID it should navigate to search page
	 * 
	 */

	@Test(groups = { "verifyRequisitionID_OpenPosition" }, dataProvider = "JobpostTestData", priority=6)
	public void verifyRequisitionID_OpenPosition(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.verifyRequisitionID_OpenPosition(job);

	}

	/**
	 * Post a job and verify plus icon is displayed in open positions and
	 * onclick of plus it should navigate to edit job page
	 * 
	 */

	/*@Test(groups = { "verifyMultipleJobPost_OpenPosition","SRG" }, dataProvider = "JobpostTestData", priority=7)
	public void verifyMultipleJobPost_OpenPosition(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.verifyMultipleJobPost_OpenPosition(job);

	}*/
	/*
	 * Verify OpenPositions RequisitionID link in homePage is navigating to
	 * "Search Results" page
	 */

	/*@Test(groups = { "verifyHomepage_OpenPositionRI" }, dataProvider = "JobpostTestData", priority=8)
	public void verifyHomepage_OpenPositionRI(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.verifyHomepage_OpenPositionRI(job);

	}
*/
	/*
	 * Verify OpenPositions JobTitle link in homePage is navigating to
	 * "Search Results" page
	 */

	/*@Test(groups = { "verifyHomepage_OpenPositionJobTitle" }, dataProvider = "JobpostTestData", priority=9)
	public void verifyHomepage_OpenPositionJobTitle(SpireTestObject testObject, JobPostBean job) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.verifyHomepage_OpenPositionJobTitle(job);

	}*/
	/*
	 * Verify OpenPositions Plus(+) link in homePage is navigating to
	 * "Preview Job" Page
	 */
	/*@Test(groups = { "verifyHomepageMultipleJobpost" ,"SRG"}, dataProvider = "JobpostTestData", priority=10)
	public void verifyHomepageMultipleJobpost(SpireTestObject testObject, JobPostBean job) throws Exception {
		new HomePageUtil(loginPageUtil.driver, false);

		JobPostsPageUtil jobpost = new JobPostsPageUtil(loginPageUtil.driver, false);

		jobpost.verifyHomepageMultipleJobpost(job);

	}
*/
	/*@Test(groups = { "closeRequisition" }, dataProvider = "JobpostTestData")
	public void closeRequisition(SpireTestObject testObject, JobPostBean job) throws Exception {

		LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		JobPostsPageUtil jobpost = new JobPostsPageUtil(this.driver, false);

		jobpost.closeRequisition(job);

	}
*/
	/*@Test(groups = { "closeRequisition" }, dataProvider = "JobpostTestData")
	public void closeRequisition(SpireTestObject testObject,JobPostBean job) throws Exception {

	
		 LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);
		
		  initializeDriver(loginPageUtil.driver, testObject);
		 
		  loginPageUtil.login();
		 
		  JobPostsPageUtil jobpost = new JobPostsPageUtil(this.driver, false);

			jobpost.closeRequisition(job);

		JobPost openJob = RequisitionHelper.getRequisitionIDbyName("AutomationTestEngineer38478");

		TalentBizLayerConsumer talentBizLayerConsumer = new TalentBizLayerConsumer();

		RequisitionBean requisition = new RequisitionBean();

		requisition.setId(openJob.getId());
		requisition.setDisplayId(openJob.getDisplayId());
		requisition.setStatusCode("CLOSED");
		requisition.setStatusDisplay("closed");
		talentBizLayerConsumer.updateRequisition(requisition);
	}*/
}

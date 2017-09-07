package com.spire.demoflows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.pageUtils.EngageCampaignPageUtil;
import com.spire.crm.pageUtils.EngageEmailTemplatePageUtil;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;
import com.spire.crm.pages.CandidateProfilePage;
import com.spire.crm.pages.SearchPage;
import com.spire.crm.restful.util.CreateProfile;
import com.spire.crm.restful.util.ProfileDataPoints;

@Test(groups = { "SANITY" })
public class ProductionWorkFlowSantityTestPlan extends TestPlan {

	String USERNAME = null;
	String PASSWORD = null;
	String URL = null;
	WebDriver driver = null;
	// String localPath = System.getProperty("user.dir") + File.separator +
	// "test-output" + File.separator + "screenshots"
	// + File.separator;
	final String productionDataPoints = "PRODUCTION";
	String stages[] = { "Lead", "Engaged", "Applicant" };
	HomePageUtil homeUtil = null;
	Map<String, String> profileWithStage = new HashMap<String, String>();
	int[] pipelineCountBfr = null;
	String skillProfileId[] = null;

	final static String PROFILE = "PROFILE";
	boolean DATA_EXECUTION;
	static List<String> profiles = new ArrayList<>();
	int i = 1;
	String label;
	String CAMPAING_NAME = "auto-campaign";
	Logger logger = Logger.getLogger(ProductionWorkFlowSantityTestPlan.class);
	public String TEMPLATE_NAME = "AUTO_EMAIL_TEMP";
	public String TEMPLATE_SUBJ = "AUTO_EMAIL_TEMP_SUBJECT";

	@DataProvider(name = productionDataPoints)
	public Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/demoflows/ProductionTestTntTestPlanData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ProductionWorkFlowSantityTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@DataProvider(name = PROFILE)
	public Iterator<Object[]> getProfilesData(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/demoflows/ProductionTestTenantProfileData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ProfileDataPoints", ProfileDataPoints.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ProductionTestTenantDataPoints.class, entityClazzMap,
					fileName, null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	// @BeforeSuite(alwaysRun = true)
	// public void setUp() {
	// File filePath = new File(localPath);
	// if (!filePath.exists()) {
	// if (filePath.mkdir()) {
	// System.out.println("Directory created !!!");
	// } else {
	// Logging.log("Not created Directory!!");
	// }
	// } else {
	// if (filePath.listFiles().length > 0) {
	// for (File file : filePath.listFiles()) {
	// if (file.delete()) {
	// System.err.println("deleted files");
	// }
	// }
	// }
	// }
	// }

	@BeforeTest(alwaysRun = true)
	@Parameters({ "username", "password", "URL", "ENABLE_DATA_EXECUTION" })
	public void readingProperties(String username, String password, String URL, boolean enable) {
		this.USERNAME = username;
		this.PASSWORD = password;
		this.URL = URL;
		this.DATA_EXECUTION = enable;
	}

	// To keep the session
	@Override
	@AfterMethod(alwaysRun = true)
	public void afterTestMethod(Method method) {
	}

	// @AfterMethod(alwaysRun = true)
	// public void takeScreenShotOnFailure(ITestResult testResult) throws
	// IOException {
	// if (testResult.getStatus() == ITestResult.FAILURE ||
	// testResult.getStatus() == ITestResult.SKIP
	// || testResult.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE) {
	// Logging.log("takeScreenShotOnFailure :: Capturing Screenshots !!!");
	// if (driver == null)
	// return;
	// File scrFile = ((TakesScreenshot)
	// driver).getScreenshotAs(OutputType.FILE);
	// String destPath = localPath + testResult.getMethod().getMethodName() +
	// "_" + this.USERNAME + ".png";
	// File destPathFile = new File(destPath);
	// FileUtils.copyFile(scrFile, destPathFile);
	// }
	//
	// }

	@Test(groups = { "profileData" }, priority = 0, dataProvider = PROFILE)
	public void createFullProfile(SpireTestObject testObject, ProfileDataPoints profileDataPoints) throws Exception {
		if (DATA_EXECUTION) {
			profileDataPoints.getCandidateBean().setFirstName("auto-" + UUID.randomUUID().toString().substring(0, 17));
			profileDataPoints.getCandidateSkillMapBean()
					.setSkill("auto-skill" + UUID.randomUUID().toString().substring(0, 17));
			String profId = CreateProfile.profileCreation(profileDataPoints);
			logger.info("profile id\t" + profId);
			if (profId != null && !profId.isEmpty()) {
				profiles.add(profId);
				Logging.log("Profile\t" + i + "------>" + profId);
				i++;
			}

			else {
				throw new Exception("Profile creation failed !!!");
			}
		}

	}

	/**
	 * Verifying Login successfully done and verifying page elements.
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyLoginSuccess" }, priority = 1, dataProvider = productionDataPoints)
	public void verifyLoginSuccess(SpireTestObject testObject) throws Exception {
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
	@Test(groups = { "verifyHomePageRenderedSuccessfully" }, priority = 2, dataProvider = productionDataPoints)
	public void verifyHomePageRenderedSuccessfully(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyHomePageRenderedSuccessfully !!!");
			homeUtil = new HomePageUtil(this.driver, false);
			homeUtil.validateHomePageElements();
			Logging.log("End :: verifyHomePageRenderedSuccessfully !!!");
		} catch (Exception e) {
			Assert.fail("verifyHomePageRenderedSuccessfully failed !!!" + e.getMessage());
		}

	}

	/**
	 * Verifying Home page loaded successfully.
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyProfileMoveToCRMStages" }, priority = 3, dataProvider = productionDataPoints)
	public void verifyProfileMoveToCRMStages(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyProfileMoveToCRMStages !!!");
			pipelineCountBfr = ProdTestTntValidation.getCRMPipelineCount();
			for (int i = 0; i < profiles.size(); i++) {
				CandidateProfilePage candidateProfilePage = new CandidateProfilePage(driver, true, profiles.get(i));
				Thread.sleep(2000);
				candidateProfilePage.markAsCRMStage(stages[i]);
				profileWithStage.put(stages[i], profiles.get(i));
			}

			Logging.log("End :: verifyProfileMoveToCRMStages !!!");
		} catch (Exception e) {
			Assert.fail("verifyProfileMoveToCRMStages failed !!!" + e.getMessage());
		}

	}

	/**
	 * After marking to CRM-Stages verifying count is increased with previous
	 * one.
	 *
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyProfilesAddedToCRMPipeline" }, priority = 4, dataProvider = productionDataPoints)
	public void verifyProfilesAddedToCRMPipeline(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyProfilesAddedToCRMPipeline !!!");
			// verifying from UI end.
			ProdTestTntValidation.verifyCRMPipelineCountOnUI(pipelineCountBfr, homeUtil);
			// verifying from service end
			ProdTestTntValidation.verifyCandidateMovedToCRMStage(profileWithStage);
		} catch (Exception e) {
			Assert.fail("verifyProfilesAddedToCRMPipeline failed !!!" + e.getMessage());
		}

	}

	/**
	 *
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyPopularTagResult" }, priority = 5, dataProvider = productionDataPoints)
	public void verifyPopularTagResult(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyPopularTagResult !!!");
			skillProfileId = ProdTestTntValidation.getNewProfileSkillAndId();
			logger.info("profile id\t" + skillProfileId[1]);
			label = ProdTestTntValidation.getNewLabel();
			CandidateProfilePage candidateProfilePage = new CandidateProfilePage(this.driver, true, skillProfileId[1]);
			candidateProfilePage.profileAttachToTag(label);
			candidateProfilePage.verifyTagIsExistedToProfile(label);
		} catch (Exception e) {
			Assert.fail("verifyPopularTagResult failed !!!" + e.getMessage());
		}

	}

	/**
	 *
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyViewAllTagResult" }, priority = 6, dataProvider = productionDataPoints)
	public void verifyViewAllTagResult(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyViewAllTagResult !!!");
			Assert.assertTrue(homeUtil.clickOnViewAllLink());
		} catch (Exception e) {
			Assert.fail("verifyViewAllTagResult failed !!!" + e.getMessage());
		}

	}

	/**
	 * 
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifySearchResults" }, priority = 7, dataProvider = productionDataPoints)
	public void verifySearchResults(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifySearchResults !!!");
			String profile[] = ProdTestTntValidation.getNewProfileSkillAndId();
			Map<String, String> profileWithStage = new HashMap<String, String>();
			profileWithStage.put(stages[0], profile[1]);
			logger.info("profile id\t" + profile[1]);
			SearchPage searchPage = new SearchPage(this.driver, false);
			searchPage.searchCriteria(profile[0], "Skill");
			searchPage.clickOnProfileLinkFromSearchResult();
			CandidateProfilePage candidateProfilePage = new CandidateProfilePage(this.driver, false, null);
			candidateProfilePage.verifyProfileSummaryDetailsNotNull();
			candidateProfilePage.markAsCRMStage(stages[0]);
			String label = ProdTestTntValidation.getNewLabel();
			ProdTestTntValidation.verifyCandidateMovedToCRMStage(profileWithStage);
			candidateProfilePage.profileAttachToTag(label);
			candidateProfilePage.verifyTagIsExistedToProfile(label);
		} catch (Exception e) {
			Assert.fail("verifySearchResults failed !!!" + e.getMessage());
		}

	}

	/**
	 *
	 * @param testObject
	 * @throws Exception
	 */
	@Test(groups = { "verifyProfileAttachedToCampaign" }, priority = 8, dataProvider = productionDataPoints)
	public void verifyProfileAttachedToCampaign(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyProfileAttachedToCampaign !!!");
			String profileId = ProdTestTntValidation.getNewProfileSkillAndId()[1];
			logger.info("profile id\t" + profileId);
			CandidateProfilePage candidateProfilePage = new CandidateProfilePage(driver, true, profileId);
			candidateProfilePage.profileAttachToCampaign(CAMPAING_NAME);
			candidateProfilePage.verifyProfileAttachedToCampaign(CAMPAING_NAME);
		} catch (Exception e) {
			Assert.fail("verifyProfileAttachedToCampaign failed !!!" + e.getMessage());
		}

	}

	@Test(groups = { "verifyCampaignPageResults" }, priority = 9, dataProvider = productionDataPoints)
	public void verifyCampaignPageResults(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyCampaignPageResults !!!");
			EngageCampaignPageUtil engageCampaignPageUtil = new EngageCampaignPageUtil(driver, true);
			engageCampaignPageUtil.validateCampaignsData();
			// engageCampaignPageUtil.createCampaign_sendEmail("automation_camp",
			// "automation_camp", "automation_camp", "sa");
		} catch (Exception e) {
			Assert.fail("verifyCampaignPageResults failed !!!" + e.getMessage());
		}

	}

	@Test(groups = { "verifyEmailTemplateResults" }, priority = 10, dataProvider = productionDataPoints)
	public void verifyEmailTemplateResults(SpireTestObject testObject) throws Exception {
		try {
			Logging.log("Start :: verifyEmailTemplateResults !!!");
			EngageEmailTemplatePageUtil engageEmailTemplatePageUtil = new EngageEmailTemplatePageUtil(driver, true);
			String emailTmp = engageEmailTemplatePageUtil.createNewUpdateEmailTemplate(TEMPLATE_NAME, TEMPLATE_SUBJ);
			engageEmailTemplatePageUtil.deleteEmailTemplate(emailTmp);
		} catch (Exception e) {
			Assert.fail("verifyEmailTemplateResults failed !!!" + e.getMessage());
		}

	}
}

package com.spire.demoflows;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.ContextManager;
import com.spire.base.helper.SpireProperties;
import com.spire.base.helper.WebPageHelper;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.common.ProfileHelper;
import com.spire.crm.pageUtils.EngageCandidateDeatilsPageUtils;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;
import com.spire.crm.pages.HomePage;

public class DemoFlowTest extends UITestPlan {

	@DataProvider(name = "demotestata")
	public Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/demoflows/ValidateElementsTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(DemoFlowTest.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "changeStage_Sanity", "Sanity" }, dataProvider = "demotestata")
	public void changeStage_Sanity(SpireTestObject testObject) throws InterruptedException {

		String ui_Host = ContextManager.getGlobalContext().getUIHostAddress()
				+ SpireProperties.loadEndPointProperties().getProperty("CRM_VISTA_UI");

		LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		HomePageUtil homePageUtil = new HomePageUtil(this.driver, false);

//		homePageUtil.validateEngageList().clicElement(driver, homePageUtil.candidateLink);

		Thread.sleep(7000);

		// should import from crm-rest-service project
		String candidateID = null;

		 String candidateID1 = ProfileHelper.createProfile();

		EngageCandidateDeatilsPageUtils candiateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(this.driver,
				true, candidateID1);

		candiateDeatilsPageUtils.changeStageTo("Lead");

	}

	@Test(groups = { "activityStreamlog_Sanity", "Sanity" }, dataProvider = "demotestata")
	public void activityStreamlog_Sanity(SpireTestObject testObject) throws InterruptedException {

		String ui_Host = ContextManager.getGlobalContext().getUIHostAddress()
				+ SpireProperties.loadEndPointProperties().getProperty("CRM_VISTA_UI");

		LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		HomePageUtil homePageUtil = new HomePageUtil(this.driver, false);

//		homePageUtil.validateEngageList().clicElement(driver, homePageUtil.candidateLink);

		// should import from crm-rest-service project
		String candidateID = null;

		// String candidateID = ProfileServiceHelper.createProfile();

		EngageCandidateDeatilsPageUtils candiateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(this.driver,
				true, candidateID);

		Thread.sleep(7000);

		candiateDeatilsPageUtils.changeStageTo("Lead");

		Thread.sleep(5000);
		candiateDeatilsPageUtils.validateActivity("Video call");
		Thread.sleep(5000);
		candiateDeatilsPageUtils.validateActivity("Instance Message");
		Thread.sleep(5000);
		// candiateDeatilsPageUtils.validateActivity("In-person Meeting");
		candiateDeatilsPageUtils.validateActivity("Voice call made");
		Thread.sleep(5000);
		candiateDeatilsPageUtils.validateActivity("Voice call received");

	}

	@Test(groups = { "updateEScore_Sanity", "Sanity" }, dataProvider = "demotestata")
	public void updateEScore_Sanity(SpireTestObject testObject) throws InterruptedException {

		String ui_Host = ContextManager.getGlobalContext().getUIHostAddress()
				+ SpireProperties.loadEndPointProperties().getProperty("CRM_VISTA_UI");

		LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		HomePage homePage = new HomePage(this.driver, false);
		HomePageUtil homePageUtil = new HomePageUtil(this.driver, false);

		homePageUtil.validateEngageList();
//		homePageUtil.clicElement(driver, homePage.candidateLink);

		Thread.sleep(7000);

		// should import from crm-rest-service project
		String candidateID = null;

		// String candidateID = ProfileServiceHelper.createProfile();

		EngageCandidateDeatilsPageUtils candiateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(this.driver,
				true, candidateID);

		String oldscore = candiateDeatilsPageUtils.getElementText(driver, candiateDeatilsPageUtils.engagementScore);

		candiateDeatilsPageUtils.validateActivity("Video call");
		Thread.sleep(10000);
		candiateDeatilsPageUtils.validateActivity("Instance Message");
		Thread.sleep(10000);
		// candiateDeatilsPageUtils.validateActivity("In-person Meeting");
		candiateDeatilsPageUtils.validateActivity("Voice call made");
		Thread.sleep(10000);
		candiateDeatilsPageUtils.validateActivity("Voice call received");
		Thread.sleep(10000);
		candiateDeatilsPageUtils.validateEnagementScore(oldscore);

	}

	@Test(groups = { "search_Sanity", "Sanity" }, dataProvider = "demotestata")
	public void search_Sanity(SpireTestObject testObject) throws InterruptedException {

		LoginPageUtil loginPageUtil = new LoginPageUtil(null, true);

		initializeDriver(loginPageUtil.driver, testObject);

		loginPageUtil.login();

		loginPageUtil.enterText(driver, By.xpath("//input[@type='text']"), "Java");

		loginPageUtil.clicElement(driver, By.cssSelector("button.btn.search-icon"));

		try {
			WebPageHelper.waitForElementToBeVisible(driver, By.xpath(".//*[@id='crmCandProfileList']/ul/li[1]"));
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false, "No search Resulst found");
		}

	}

}

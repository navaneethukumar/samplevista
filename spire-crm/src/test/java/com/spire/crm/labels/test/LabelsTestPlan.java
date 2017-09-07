package com.spire.crm.labels.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.labels.LabelsUtil;
import com.spire.crm.pageUtils.HomePageUtil;

@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class LabelsTestPlan extends UITestPlan {
	@DataProvider(name = "LabelsTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/labels/test/LabelsTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(LabelsTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "validateElementPresent" }, dataProvider = "LabelsTestData")
	public void validateElementPresent(SpireTestObject testObject) throws InterruptedException  {

		new HomePageUtil(loginPageUtil.driver, true);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.validateElementPresent();

	}

	/*
	 * @Test(groups = { "createLabelAndCheckInHomePage" }, dataProvider =
	 * "LabelsTestData") public void
	 * createLabelAndCheckInHomePage(SpireTestObject testObject) throws
	 * Exception {
	 * 
	 * new HomePageUtil(loginPageUtil.driver, false); LabelsUtil labelsUtil =
	 * new LabelsUtil(loginPageUtil.driver, false);
	 * 
	 * labelsUtil.createLabelAndCheckInHomePage();
	 * 
	 * 
	 * }
	 */
	@Test(groups = { "attattachLabelInDetailsPage" }, dataProvider = "LabelsTestData")
	public void attattachLabelInDetailsPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.attattachLabelInDetailsPage();

	}

	/*
	 * @Test(groups = { "createLabelAttachInHomePage" }, dataProvider =
	 * "LabelsTestData") public void createLabelAttachInHomePage(SpireTestObject
	 * testObject) throws Exception {
	 * 
	 * new HomePageUtil(loginPageUtil.driver, false); LabelsUtil labelsUtil =
	 * new LabelsUtil(loginPageUtil.driver, false);
	 * 
	 * labelsUtil.createLabelAttachInHomePage();
	 * 
	 * 
	 * }
	 */
	@Test(groups = { "attachLabelInEngagedPage" }, dataProvider = "LabelsTestData")
	public void attachLabelInEngagedPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, true);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.attachLabelInEngagedPage();

	}

	@Test(groups = { "bulkAttachLabelInEngagedPage" }, dataProvider = "LabelsTestData")
	public void bulkAttachLabelInEngagedPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, true);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.bulkAttachLabelInEngagedPage();

	}

	@Test(groups = { "attachLabelInTagCouldPage" }, dataProvider = "LabelsTestData")
	public void attachLabelInTagCouldPage(SpireTestObject testObject) throws InterruptedException  {

		new HomePageUtil(loginPageUtil.driver, true);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.attachLabelInTagCouldPage();

	}

	@Test(groups = { "attachLabelInSearchedPage" }, dataProvider = "LabelsTestData")
	public void attachLabelInSearchedPage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, true);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.attachLabelInSearchedPage();

	}
	@Test(groups = { "clickTagInHomepage" }, dataProvider = "LabelsTestData")
	public void clickTagInHomepage(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		LabelsUtil labelsUtil = new LabelsUtil(loginPageUtil.driver, false);

		labelsUtil.clickTagInHomepage();

	}
	
}

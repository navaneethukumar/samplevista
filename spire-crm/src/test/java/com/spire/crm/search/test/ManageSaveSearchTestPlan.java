package com.spire.crm.search.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.helper.WebPageHelper;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pages.SearchPage;

/**
 * 
 * @author Santosh
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ManageSaveSearchTestPlan extends UITestPlan {

	@DataProvider(name = "ManageSaveSearchTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/search/test/ManageSaveSearchTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SearchBean", SearchBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ManageSaveSearchTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;

	}

	@Test(groups = { "verifySaveSearch_SingleSkillSanity", "Sanity",
			"ManageSaveSearchSanity" }, dataProvider = "ManageSaveSearchTestData")
	public void verifySaveSearch_SingleSkillSanity(SpireTestObject testObject, SearchBean searchBean) throws InterruptedException {

		new HomePageUtil(loginPageUtil.driver, false);
		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);
		try {
			searchPage.searchCriteria(searchBean.getSearchCriteria().get(0), searchBean.getSearchType().get(0));
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(loginPageUtil.driver, false);
		searchResultPageUtil.validateSaveSearch();
		WebPageHelper.clicElement(loginPageUtil.driver, searchPage.home);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "verifySaveSearch_TwoAttributeSRG", "SRG",
			"ManageSaveSearchSanity" }, dataProvider = "ManageSaveSearchTestData")
	public void verifySaveSearch_TwoAttributeSRG(SpireTestObject testObject, SearchBean searchBean) throws InterruptedException {

		new HomePageUtil(loginPageUtil.driver, false);
		SearchPage searchPage = new SearchPage(loginPageUtil.driver, false);
		try {
			searchPage.searchCriterias(searchBean.getSearchCriteria().get(0), searchBean.getSearchType().get(0));
			searchPage.searchOperation(searchBean.getSearchOperation().get(0));
			searchPage.clickOnSearchedElement(searchBean.getSearchCriteria().get(1), searchBean.getSearchType().get(1));
			WebPageHelper.clicElement(loginPageUtil.driver, searchPage.clickOnSearchBtn);
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SearchResultPageUtil searchResultPageUtil = new SearchResultPageUtil(loginPageUtil.driver, false);
		searchResultPageUtil.validateSaveSearch();
		WebPageHelper.clicElement(loginPageUtil.driver, searchPage.home);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

package com.spire.acqura.search.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.acqura.search.pageUtils.AdvancedSearchPageUtils;
import com.spire.acqura.search.pageUtils.HomePageUtil;
import com.spire.acqura.search.pageUtils.ManageLabelsPageUtil;
import com.spire.acqura.search.pageUtils.SearchResultsPageUtils;
import com.spire.acqura.search.pageUtils.UploadCandidatesPageUtil;
import com.spire.base.controller.Assertion;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.AdvancedSearchPageUtil;

@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class HomePageTestPlan extends UITestPlan {

	@DataProvider(name = "HomePageTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/acqura/search/test/HomePageTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(HomePageTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	/**
	 * Steps: Login and verify Home page elements Validation : After login all
	 * the elements like Advanced Search Link, Dropdown menu.
	 * 
	 * @throws Exception
	 */

	@Test(groups = { "validateHomePageElements", "Sanity" }, dataProvider = "HomePageTestData")
	public void validateHomePageElements(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.validateHomepageElements();
		homeUtil.mouseHoverOnDropdownLink();
		Thread.sleep(6000);
		homeUtil.clickOnUploadCandidateLink();
		Thread.sleep(6000);
		homeUtil.mouseHoverOnDropdownLink();
		Thread.sleep(6000);
		homeUtil.clickOnManageLabelLink();
		Thread.sleep(6000);
	}

	/**
	 * Steps: Login and verify Home page elements Validation : After login,
	 * Navigation to Advanced Search Page and close
	 * 
	 * @throws Exception
	 */

	@Test(groups = { "validateNavigationToAdvanceSearchPage", "Sanity" }, dataProvider = "HomePageTestData")
	public void validateNavigationToAdvanceSearchPage(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.clickAdvancedSearchButton();
		AdvancedSearchPageUtils advancedSearchPageUtils = new AdvancedSearchPageUtils(loginPageUtil.driver, false);
		Assertion.assertEquals(advancedSearchPageUtils.getHeaderText(), "Advanced Search",
				"Advanced search Page Not Opening");
		advancedSearchPageUtils.clickCloseIcon();
		homeUtil.validateHomepageElements();
	}

	/**
	 * Steps: Login and validate navigation upload candidate page Validation :
	 * After login, Navigation to upload candidate page from homepage
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateNavigationToUploadCandidatePage", "Sanity" }, dataProvider = "HomePageTestData")
	public void validateNavigationToUploadCandidatePage(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.mouseHoverOnDropdownLink();
		homeUtil.clickOnUploadCandidateLink();
		UploadCandidatesPageUtil uploadPageUtils = new UploadCandidatesPageUtil(loginPageUtil.driver, false);
		Assertion.assertEquals(uploadPageUtils.returnHeaderText(), "Upload Candidates",
				"Navigation to Upload Candidates page failed");
		loginPageUtil.driver.navigate().back();
		homeUtil.validateHomepageElements();
	}

	/**
	 * Steps: Login and validate navigation Manage Label page Validation : After
	 * login, Navigation to Manage Label page from homepage
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateNavigationToManageLabelPage", "Sanity" }, dataProvider = "HomePageTestData")
	public void validateNavigationToManageLabelPage(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.mouseHoverOnDropdownLink();
		homeUtil.clickOnManageLabelLink();
		ManageLabelsPageUtil manageLabelPageUtils = new ManageLabelsPageUtil(loginPageUtil.driver, false);
		Assertion.assertEquals(manageLabelPageUtils.returnHeaderText(), "Manage Labels",
				"Navigation to Manage Labels page failed");
		loginPageUtil.driver.navigate().back();
		homeUtil.validateHomepageElements();
	}

	/**
	 * Steps: Login and validate navigation upload candidate page and then
	 * navigate to homepage Validation : After login, Navigation to upload
	 * candidate page from homepage, Again navigate to homepage.
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateNavigationToHomePageFromUploadCandidatePage",
			"Sanity" }, dataProvider = "HomePageTestData")
	public void validateNavigationToHomePageFromUploadCandidatePage(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.mouseHoverOnDropdownLink();
		homeUtil.clickOnUploadCandidateLink();
		UploadCandidatesPageUtil uploadPageUtils = new UploadCandidatesPageUtil(loginPageUtil.driver, false);
		Assertion.assertEquals(uploadPageUtils.returnHeaderText(), "Upload Candidates",
				"Navigation to Upload Candidates page failed");
		homeUtil.mouseHoverOnDropdownLink();
		homeUtil.clickOnHomeLink();
		homeUtil.validateHomepageElements();
	}

	/**
	 * Steps: Login and validate navigation Manage Label page and then navigate
	 * to homepage Validation : After login, Navigation to Manage Label page
	 * from homepage, Again navigate to homepage.
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateNavigationToHomePageFromManageLabelPage", "Sanity" }, dataProvider = "HomePageTestData")
	public void validateNavigationToHomePageFromManageLabelPage(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.mouseHoverOnDropdownLink();
		homeUtil.clickOnManageLabelLink();
		ManageLabelsPageUtil manageLabelPageUtils = new ManageLabelsPageUtil(loginPageUtil.driver, false);
		Assertion.assertEquals(manageLabelPageUtils.returnHeaderText(), "Manage Labels",
				"Navigation to Manage Labels page failed");
		homeUtil.mouseHoverOnDropdownLink();
		homeUtil.clickOnHomeLink();
		homeUtil.validateHomepageElements();
	}

	/**
	 * Steps: Login and perform universal search and navigate to search results
	 * page Validation : After login, Search Results page from homepage.
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "validateNavigationToSearchResultsPageFromHomePage", "Sanity" }, dataProvider = "HomePageTestData")
	public void validateNavigationToSearchResultsPageFromHomePage(SpireTestObject testObject) throws Exception {

		HomePageUtil homeUtil = new HomePageUtil(loginPageUtil.driver, false);
		homeUtil.enterTextInSearchBar("java");
		homeUtil.clickSearchButton();
		SearchResultsPageUtils searchResultsPageUtils = new SearchResultsPageUtils(loginPageUtil.driver, false);
	}
}

package com.spire.assets.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.AssetsPageUtil;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pages.AssetsPage;
import com.spire.crm.pages.SearchPage;
import com.spire.crm.search.test.SearchBean;
import com.spire.crm.search.test.SearchResultPageUtil;
import com.spire.pipeline.ValidatePipeLineDataUtil;

/**
 * 
 * @author Manaswini
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class AssetsTestPlan extends UITestPlan {
	@DataProvider(name = "AssetsTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/assets/test/AssetsTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					AssetsTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "verifyCreateAssetSanity", "Sanity" }, priority = 0, dataProvider = "AssetsTestData")
	public void verifyCreateAssetSanity(SpireTestObject testObject) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		AssetsPageUtil assetsPageUtil = new AssetsPageUtil(
				loginPageUtil.driver, false);
		assetsPageUtil.validateCreateAsset();
		
	}
	
	@Test(groups = { "verifyModifyAssetSanity", "Sanity" }, priority = 1, dataProvider = "AssetsTestData")
	public void verifyModifyAssetSanity(SpireTestObject testObject ) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		AssetsPageUtil assetsPageUtil = new AssetsPageUtil(
				loginPageUtil.driver, false);
		assetsPageUtil.validateModifyAsset();
	}
	
	@Test(groups = { "verifyDeleteAssetSanity", "Sanity" }, priority = 3, dataProvider = "AssetsTestData")
	public void verifyDeleteAssetSanity(SpireTestObject testObject ) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		AssetsPageUtil assetsPageUtil = new AssetsPageUtil(
				loginPageUtil.driver, false);
		assetsPageUtil.validatedeleteAssets();
	}
	
	@Test(groups = { "verifyListAssetsSanity", "Sanity" }, priority = 2, dataProvider = "AssetsTestData")
	public void verifyListAssetsSanity(SpireTestObject testObject ) throws Exception {

		HomePageUtil homePage = new HomePageUtil(loginPageUtil.driver, true);
		AssetsPageUtil assetsPageUtil = new AssetsPageUtil(
				loginPageUtil.driver, false);
		assetsPageUtil.validateListAssets();
		
	}
	
}

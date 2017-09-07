package com.spire.crm.hireassimilar.profiles;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.entity.social.test.SocialServiceEntityTestPlan;

/**
 * @author Manikant C
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class HireAsSimilarProfilesTestPlan extends TestPlan {
	
	private static Logger logger = LoggerFactory.getLogger(SocialServiceEntityTestPlan.class);
	
	
	@DataProvider(name = "DATAPROVIDER")
	public static Iterator<Object[]> getblobStoreInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/hireassimilar/profiles/HireAsSimilarProfileTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					HireAsSimilarProfilesTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}
	
	/**
	 * Create verify the status is displaying 200 for similar profiles 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "SimilarProfiles_Sanity", "Sanity" },dataProvider="DATAPROVIDER")
	public void SimilarProfiles_Sanity(SpireTestObject spireTestObject,TestData testData) {

		logger.info("Test Case Execution started >>> SimilarProfiles_Sanity !!!");
		
		HireAsSimilarProfilesValidation.verifySimilarProfilesStatus(spireTestObject,testData);

	}

}

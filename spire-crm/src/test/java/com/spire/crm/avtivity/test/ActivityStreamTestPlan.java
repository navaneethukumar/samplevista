package com.spire.crm.avtivity.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.activitytream.ValidateActivityStreamDataUtil;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;
import com.spire.crm.pageUtils.HomePageUtil;

/**
 * 
 * @author Sangeeta
 *
 */
@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
public class ActivityStreamTestPlan extends UITestPlan {
	@DataProvider(name = "ValidateActivityStreamTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "src/test/java/com/spire/crm/avtivity/test/ValidateActivityStreamTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(ActivityStreamTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}
	/*
	 * check all activityStreamElements(emailIcon,TagsIcon,CampaignsIcon,candidateImageIcon) are displaying or not.
	 */
	
	@Test(groups = { "ValidateActivityStreamData","SRG" }, dataProvider = "ValidateActivityStreamTestData",priority=0)
	public void ValidateActivityStreamData(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		ValidateActivityStreamDataUtil activityStream = new ValidateActivityStreamDataUtil(loginPageUtil.driver, false);

		activityStream.ValidateActivityStreamData();
		

	}
	
/*
 * click on email link in avtivityStream, it should display email which you sent to the candidate by opening new small window.
 */
	
	@Test(groups = { "ValidateEmailLink","SRG" }, dataProvider = "ValidateActivityStreamTestData",priority=1)
	public void ValidateEmailLink(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		ValidateActivityStreamDataUtil activityStream = new ValidateActivityStreamDataUtil(loginPageUtil.driver, false);

		activityStream.ValidateEmailLink();

	}
	/*
	 * click on candidate name link in activityStream it should navigate to candidate details page of the same candidate.
	 */
		

	@Test(groups = { "ValidateCandidateNameLink","SRG"}, dataProvider = "ValidateActivityStreamTestData",priority=2)
	public void ValidateCandidateNameLink(SpireTestObject testObject) {

		new HomePageUtil(loginPageUtil.driver, false);
		ValidateActivityStreamDataUtil activityStream = new ValidateActivityStreamDataUtil(loginPageUtil.driver, false);
		activityStream.ValidateCandidateNameLink();

	}

	/*
	 * check whether pagination is displayed or not, if displyed check whether on click of next Btn it is displying next page or not.
	 */
	@Test(groups = { "ValidatePagination","SRG"}, dataProvider = "ValidateActivityStreamTestData",priority=3)
	public void ValidatePagination(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		ValidateActivityStreamDataUtil activityStream = new ValidateActivityStreamDataUtil(loginPageUtil.driver, false);

		activityStream.ValidatePagination();

	}

	
}
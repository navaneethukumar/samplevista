package com.spire.crm.candidate_details_activity.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.pageUtils.EngageCandidateDeatilsPageUtils;
import com.spire.crm.pageUtils.HomePageUtil;

/**
 * 
 * @author Sangeeta
 *
 */
@Test(groups = { "SANITY" })
public class CandidateDetailsAcivityTestPlan extends UITestPlan {

	@DataProvider(name = "CandidateDetailsActivityStreamTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/candidate_details_activity/test/CandidateDetailsActivityStreamTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(CandidateDetailsAcivityTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "changeStageTo","Sanity" }, dataProvider = "CandidateDetailsActivityStreamTestData")
	public void changeStageTo(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		
		EngageCandidateDeatilsPageUtils detailsPage=new EngageCandidateDeatilsPageUtils(loginPageUtil.driver, false, null);
		detailsPage.changeStageTo("Lead");
	}
	@Test(groups = { "createActivity","Sanity" }, dataProvider = "CandidateDetailsActivityStreamTestData")
	public void createActivity(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		
		EngageCandidateDeatilsPageUtils detailsPage=new EngageCandidateDeatilsPageUtils(loginPageUtil.driver, false, null);
		detailsPage.createActivity();
	}
	@Test(groups = { "validateAllElementPresent","Sanity" }, dataProvider = "CandidateDetailsActivityStreamTestData")
	public void validateAllElementPresent(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);

		EngageCandidateDeatilsPageUtils detailsPage=new EngageCandidateDeatilsPageUtils(loginPageUtil.driver, false, null);
		detailsPage.validateAllElementPresent();
	}

	@Test(groups = { "attachAttachment" }, dataProvider = "CandidateDetailsActivityStreamTestData")
	public void attachAttachment(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		EngageCandidateDeatilsPageUtils detailsPage=new EngageCandidateDeatilsPageUtils(loginPageUtil.driver, false, null);
		detailsPage.attachAttachment();

	}
	@Test(groups = { "createRemindMeLater" }, dataProvider = "CandidateDetailsActivityStreamTestData")
	public void createRemindMeLater(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		EngageCandidateDeatilsPageUtils detailsPage=new EngageCandidateDeatilsPageUtils(loginPageUtil.driver, false, null);
		detailsPage.createRemindMeLater();

	}
	@Test(groups = { "validateAlreadyViewed" }, dataProvider = "CandidateDetailsActivityStreamTestData")
	public void validateAlreadyViewed(SpireTestObject testObject) throws Exception {

		new HomePageUtil(loginPageUtil.driver, false);
		EngageCandidateDeatilsPageUtils detailsPage=new EngageCandidateDeatilsPageUtils(loginPageUtil.driver, false, null);
		detailsPage.validateAlreadyViewed();

	}

}

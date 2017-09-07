package com.spire.demoflows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.restful.util.CreateProfile;
import com.spire.crm.restful.util.ProfileDataPoints;


public class ProductionTestTenantDataPoints extends TestPlan {

	final static String PROFILE = "PROFILE";
	boolean DATA_EXECUTION;
	static List<String> profiles = new ArrayList<>();
	int i = 1;

	@DataProvider(name = PROFILE)
	public Iterator<Object[]> getCandidateInfo(Method method) {

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

	@Parameters({ "ENABLE_DATA_EXECUTION" })
	@BeforeTest(alwaysRun = true)
	public void setUp(boolean enable) {
		this.DATA_EXECUTION = enable;
	}

	@Test(groups = { "profileData" },priority = 0, dataProvider = PROFILE)
	public void createFullProfile(SpireTestObject testObject, ProfileDataPoints profileDataPoints) throws Exception {
		if (DATA_EXECUTION) {
			profileDataPoints.getCandidateBean().setFirstName("auto-"+UUID.randomUUID().toString().substring(0, 17));
			profileDataPoints.getCandidateSkillMapBean().setSkill("auto-skill"+UUID.randomUUID().toString().substring(0, 17));
			String profId = CreateProfile.profileCreation(profileDataPoints);
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

}

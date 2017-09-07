package com.spire.crm.biz.authorization.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.DBHelper;
import com.spire.common.TestData;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class AuthorizationSanityTestPlan extends TestPlan {
	
	AuthorizationSanityValidation validator = new AuthorizationSanityValidation();

	@DataProvider(name = "AUTHORIZATION_DP")
	public static Iterator<Object[]> getAuthorizationInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/biz/authorization/test/AuthorizationTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					AuthorizationSanityTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectsFromCsv;
	}

	
	/**
	 * 
	 * Clean up any existing data 
	 */
	@AfterClass(alwaysRun = true)
	public void cleanUp(){
		
		DBHelper obj = new DBHelper();
		obj.deleteAutomationData("role_a", "role_name like \"Automation%\"");
		obj.deleteAutomationData("permission_a", "permission_name like \"Automation%\"");
		
	}
	
	/**
	 * 
	 * verify Tenant admin role is added to the batch user
	 */ 
    @BeforeClass(alwaysRun = true)
	public void setUp(){
    	
    	try {
			validator.tenantAdminSetUP();
		} catch (IOException e) {	
			e.printStackTrace();
			Assert.fail("Run time exception in tenant admin role setup");
		}
		
		
	}
	
	/**
	 * Verify create Role 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreateRole_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyCreateRole_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	
	/**
	 * Verify delete Role 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyDeleteRole_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyDeleteRole_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	
	/**
	 * Verify create permission 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreatePermission_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyCreatePermission_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	
	/**
	 * Add permission to a Role 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyAddPermissionToRole_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyAddPermissionToRole_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	
	/**
	 * remove permission from the role 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyremovePermissionfromRole_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyremovePermissionfromRole_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	/**
	 * Add role to a User 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyAddRoleToUser_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyAddRoleToUser_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	
	
	/**
	 * remove role from the User 
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyremoveRolefromUser_Sanity", "Sanity" }, dataProvider = "AUTHORIZATION_DP")
	public void verifyremoveRolefromUser_Sanity(SpireTestObject testObject,
			TestData data) {
		Logging.log("Test Steps >>" + data.getTestSteps());
		
		try {
			validator.verifyScenarios(testObject,data);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Run Time exception");
		}
	}
	
	
	
}
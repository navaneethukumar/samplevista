package com.spire.crm.biz.job.post.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;

@Test(groups = { "RG" }, retryAnalyzer = TestRetryAnalyzer.class)
public class JobPostServiceRGTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;

	@Test(groups = { "verifyErrorMsgForDuplicateServiceCreation",
			"RG" }, priority = 0, dataProvider = "", dataProviderClass = JobPostServiceTestPlan.class)
	public void verifyErrorMsg_DuplicateService_Creation(SpireTestObject testObject, TestData data) {
		try {
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyErrorMsgForDuplicateServiceCreation -> failed", e);
		}
	}

}
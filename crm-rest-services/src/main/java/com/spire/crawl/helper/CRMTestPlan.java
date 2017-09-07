package com.spire.crawl.helper;

import org.testng.annotations.AfterSuite;

import com.spire.base.controller.TestPlan;

public class CRMTestPlan extends TestPlan{

	@AfterSuite(alwaysRun = true)
	public void afterTestSuite() {
		
	}
	
}


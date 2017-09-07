package com.spire.talent.tests;

import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.crm.entity.social.test.SocialServiceEntityTestPlan;
import com.spire.talent.consumers.UserHelper;

import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

/**
 * @author Manikant C
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class UserServiceTalentTestPlan extends TestPlan {
	
	private static Logger logger = LoggerFactory.getLogger(SocialServiceEntityTestPlan.class);
	
	/**
	 * Create BulkSocialProfiles
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "authentication", "Sanity" })
	public void authentication() {

		logger.info("Test Case Execution started >>> authentication !!!");
		
		UserHelper.authentication();

	}

}

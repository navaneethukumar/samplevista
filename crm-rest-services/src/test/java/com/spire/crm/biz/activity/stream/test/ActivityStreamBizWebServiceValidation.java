package com.spire.crm.biz.activity.stream.test;

import java.util.List;

import org.testng.Assert;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;

import crm.activitystream.beans.CreateActivityInfo;

public class ActivityStreamBizWebServiceValidation {
	static String SERVICE_NAME = (String) ContextManager.getGlobalContext().getAttribute("SERVICE_NAME");
	static final String[] ACTIVITY_TYPES = { "Video call", "Instance Message", "In-person Meeting", "Voice call made",
			"Voice call received" };
	static final String[] RATING_TYPES = { "Benifits & Compensation Awareness", "Company,Product and Job Awareness",
			"Interest Level", "Fitment (Culture, Energy and Attitude)", "Voice call received" };
	public final static String[] dateRangeTypes = { "The Past 1 day", "The Past 1 week", "The Past 2 week",
			"The Past 4 week", "The Beginning of Time" };

	public static void validate_getActivityRatingTypes(CreateActivityInfo createActiviytInfo) {
		Logging.log("Start : validate_getActivityRatingTypes");
		try {
			List<String> activityTypes = createActiviytInfo.getActivityTypes();
			List<String> ratingTypes = createActiviytInfo.getRatingTypes();
			Logging.log("Size :" + activityTypes.size());
			Logging.log("Size :" + ratingTypes.size());
			if (activityTypes != null && ratingTypes != null) {
				for (int i = 0; i < activityTypes.size(); i++) {
					Logging.log(activityTypes.get(i));
					Assert.assertEquals(activityTypes.get(i), ACTIVITY_TYPES[i]);
				}
				for (int i = 0; i < ratingTypes.size(); i++) {
					Logging.log(ratingTypes.get(i));
					Assert.assertEquals(ratingTypes.get(i), RATING_TYPES[i]);
				}
			} else {
				throw new RuntimeException("failed @ validate_getActivityRatingTypes!!!");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetAllServices -> failed", e);
		}
	}

	public static void validate_listTimePeriod(List<String> timePeriods) {
		Logging.log("Start : validate_listTimePeriod");
		try {
			for (int i = 0; i < timePeriods.size(); i++) {
				Assert.assertEquals(timePeriods.get(i), dateRangeTypes[i]);
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> validate_listTimePeriod -> failed", e);
		}
	}

}

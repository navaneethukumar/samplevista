package com.spire.crm.restful.util;

import org.testng.Assert;

import com.spire.common.ProfileHelper;
import com.spire.crm.restful.biz.consumers.ActivityStreamBizServiceConsumer;

import crm.activitystream.beans.CRMCreateActivity;

/**
 * Creating all the Activities for each profile
 * 
 * @author Pradeep
 *
 */
public class CreateActivities {

	static ActivityStreamBizServiceConsumer activityStreamBizServiceConsumer = new ActivityStreamBizServiceConsumer();

	/**
	 * Here we are creating profile and adding video call activity to that
	 * profile.
	 * 
	 * @param benefitLevel
	 * @param companyLevel
	 * @param interestLevel
	 * @param fitmentLevel
	 */
	public static void createVideoCallActivity(int benefitLevel, int companyLevel, int interestLevel,
			int fitmentLevel) {

		createActivities("Video Call", benefitLevel, companyLevel, interestLevel, fitmentLevel);

	}

	/**
	 * Here we are creating profile and adding Instance Message activity to that
	 * profile.
	 * 
	 * @param benefitLevel
	 * @param companyLevel
	 * @param interestLevel
	 * @param fitmentLevel
	 */
	public static void instantMessageActivity(int benefitLevel, int companyLevel, int interestLevel, int fitmentLevel) {
		createActivities("Instance Message", benefitLevel, companyLevel, interestLevel, fitmentLevel);
	}

	/**
	 * Here we are creating profile and adding In-person Meeting activity to
	 * that profile.
	 * 
	 * @param benefitLevel
	 * @param companyLevel
	 * @param interestLevel
	 * @param fitmentLevel
	 */
	public static void inpersonMeeting(int benefitLevel, int companyLevel, int interestLevel, int fitmentLevel) {
		createActivities("In-person Meeting", benefitLevel, companyLevel, interestLevel, fitmentLevel);
	}

	/**
	 * Here we are creating profile and adding Voice call made activity to that
	 * profile.
	 * 
	 * @param benefitLevel
	 * @param companyLevel
	 * @param interestLevel
	 * @param fitmentLevel
	 */
	public static void voiceCallMade(int benefitLevel, int companyLevel, int interestLevel, int fitmentLevel) {
		createActivities("Voice call made", benefitLevel, companyLevel, interestLevel, fitmentLevel);
	}

	/**
	 * Here we are creating profile and adding Voice call received activity to
	 * that profile.
	 * 
	 * @param benefitLevel
	 * @param companyLevel
	 * @param interestLevel
	 * @param fitmentLevel
	 */
	public static void voiceCallReceived(int benefitLevel, int companyLevel, int interestLevel, int fitmentLevel) {
		createActivities("Voice call received", benefitLevel, companyLevel, interestLevel, fitmentLevel);
	}

	/**
	 * Here creating profile
	 * 
	 * @return
	 */
	public static String createProfile() {
		String profileId = ProfileHelper.createProfile();
		return profileId;
	}

	/**
	 * By passing all the ratings activity will be created
	 * 
	 * @param activity
	 * @param benefitLevel
	 * @param companyLevel
	 * @param interestLevel
	 * @param fitmentLevel
	 */
	public static void createActivities(String activity, int benefitLevel, int companyLevel, int interestLevel,
			int fitmentLevel) {
		try {
			CRMCreateActivity createActivity = new CRMCreateActivity();
			// get the candidate id by createProfile call
			String CANDIDATE_ID = createProfile();
			createActivity.setCandidateId(CANDIDATE_ID);
			createActivity.setActivityTypeInfo(activity);
			createActivity.setBenifitLevel(benefitLevel);
			createActivity.setCompanyLevel(companyLevel);
			createActivity.setInterestLevel(interestLevel);
			createActivity.setFitmentLevel(fitmentLevel);
			createActivity.setNotes("createVideoCallActivity");
			if (activityStreamBizServiceConsumer._createActivity(createActivity) == null) {
				throw new RuntimeException("verify_createActivity is null ");
			}
		} catch (Throwable e) {
			Assert.fail("TestCase >>> createVideoCallActivity -> failed", e);
		}
	}

	public static void main(String[] args) {

		// CreateActivities.createVideoCallActivity();

	}
}

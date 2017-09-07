package com.spire.demoflows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import spire.crm.labels.biz.bean.EntityLabel;
import spire.crm.labels.biz.bean.PopularLabel;
import spire.crm.profiles.bean.Profile;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

import com.spire.base.controller.Logging;
import com.spire.common.PipeLineHelper;
import com.spire.common.SoftAssertion_;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pages.CandidateProfilePage;
import com.spire.crm.restful.biz.consumers.CrmPipeLineBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.LabelsBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;

import crm.pipeline.beans.Pipeline;

public class ProdTestTntValidation {

	static String stages[] = { "Lead", "Engaged", "Applicant" };
	static Collection<Pipeline> collnPipeLine = null;
	static CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = null;
	static String LABEL_NAME = "AUTO_LABEL";

	public static String randomNameGenerator() {
		String[] peoples = { "mani", "santu", "pradeep", "raghav", "sangeeta", "jagadesh" };
		List<String> names = Arrays.asList(peoples);
		Collections.shuffle(names);
		int index = new Random().nextInt(names.size());
		String randomeString = names.get(index);
		return randomeString;

	}

	public static int getCRMPipelineCount(String stagename) {
		crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();
		Response response =	 crmPipeLineBizServiceConsumer.getCrmStageList();
		
		if (response.getStatus() == 200) {
			CollectionEntity<Pipeline> pipelines = response.readEntity(new GenericType<CollectionEntity<Pipeline>>() {
			});
			collnPipeLine = pipelines.getItems();
			
		} else {
			Assert.fail("getCrmStageList failed !!!");
			
		}

		if (stagename.equalsIgnoreCase(stages[0])) {
			return getCountOfCrmPipeLineLeadProfiles();
		} else if (stagename.equalsIgnoreCase(stages[1])) {
			return getCountOfCrmPipeLineEngagedProfiles();
		} else if (stagename.equalsIgnoreCase(stages[2])) {
			return getCountOfCrmPipeLineApplicantProfiles();
		} else {
			Assert.fail("Not returned count from CRM-Pipeline service !!");
			return 0;
		}
	}

	/**
	 * Lead count
	 * 
	 * @return
	 */
	public static int getCountOfCrmPipeLineLeadProfiles() {
		for (Pipeline stage : collnPipeLine) {
			if (stage.getStatusName().equalsIgnoreCase(stages[0])) {
				Integer count = stage.getCandidateCount();
				int leadCount = count.intValue();
				return leadCount;
			}

		}
		Assert.fail("No profiles for Stage Lead !!!");
		return 0;
	}

	/**
	 * Engaged count
	 * 
	 * @return
	 */
	public static int getCountOfCrmPipeLineEngagedProfiles() {
Response response =	 crmPipeLineBizServiceConsumer.getCrmStageList();
		
		if (response.getStatus() == 200) {
			CollectionEntity<Pipeline> pipelines = response.readEntity(new GenericType<CollectionEntity<Pipeline>>() {
			});
			collnPipeLine = pipelines.getItems();
			
		} else {
			Assert.fail("getCrmStageList failed !!!");
			
		}
		for (Pipeline stage : collnPipeLine) {
			if (stage.getStatusName().equalsIgnoreCase(stages[1])) {
				Integer count = stage.getCandidateCount();
				int leadCount = count.intValue();
				return leadCount;
			}

		}
		Assert.fail("No profiles for Stage Lead !!!");
		return 0;
	}

	/**
	 * Applicant count
	 * 
	 * @return
	 */
	public static int getCountOfCrmPipeLineApplicantProfiles() {
Response response =	 crmPipeLineBizServiceConsumer.getCrmStageList();
		
		if (response.getStatus() == 200) {
			CollectionEntity<Pipeline> pipelines = response.readEntity(new GenericType<CollectionEntity<Pipeline>>() {
			});
			collnPipeLine = pipelines.getItems();
			
		} else {
			Assert.fail("getCrmStageList failed !!!");
			
		}
		for (Pipeline stage : collnPipeLine) {
			if (stage.getStatusName().equalsIgnoreCase(stages[2])) {
				Integer count = stage.getCandidateCount();
				int leadCount = count.intValue();
				return leadCount;
			}

		}
		Assert.fail("No profiles for Stage Lead !!!");
		return 0;
	}

	/**
	 * get CRM-Stage count from service
	 * 
	 * 
	 * @param homePageUtil
	 * @return
	 * @throws InterruptedException
	 */
	public static int[] getCRMPipelineCount() {
		Logging.log("------------Before adding profiles to stage service call--------------");
		int[] pipelineArray = new int[stages.length];
		for (int i = 0; i < stages.length; i++) {
			pipelineArray[i] = getCRMPipelineCount(stages[i]);
			Logging.log(stages[i] + pipelineArray[i] + "\n");
		}
		return pipelineArray;

	}

	/**
	 * validating CRMPipeline count after adding.
	 * 
	 * @param homePageUtil
	 */
	public static void verifyCRMPipelineCountOnUI(int[] pipelineCountBfr, HomePageUtil homePageUtil) {
		Map<String, String> mapAfterAddingPipelineCount = null;
		try {
			mapAfterAddingPipelineCount = homePageUtil.getCRMPipelineCount(stages);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Logging.log("------------After adding profiles to stage----------------");
		for (int i = 0; i < mapAfterAddingPipelineCount.size(); i++) {
			SoftAssertion_.assertTrue(
					Integer.parseInt(mapAfterAddingPipelineCount.get(stages[i])) > pipelineCountBfr[i],
					stages[i] + "\t count is not increased !!");
			
		}
	}

	/**
	 * From service end validating profiles are moved to corresponding stages.
	 * 
	 * @param profileWithStage
	 * @throws Exception
	 */
	public static void verifyCandidateMovedToCRMStage(Map<String, String> profileWithStage) throws Exception {
		PipeLineHelper pipeLineHelper = new PipeLineHelper();
		// it returns stage name for candidate id.
		for (int i = 0; i < profileWithStage.size(); i++) {
			Thread.sleep(5000);
			SoftAssertion_.assertEquals(stages[i], pipeLineHelper.getstageName_(profileWithStage.get(stages[i])));
			Logging.log(stages[i] + "------>" + profileWithStage.get(stages[i]));
			SoftAssertion_.assertAll();
		}

	}

	public static boolean verifyLabelIsExistIfNotCreate(Map<String, String> profileWithStage) {
		EntityLabel response = null;
		LabelsBizServiceConsumer bizServiceConsumer = new LabelsBizServiceConsumer();
		spire.commons.labels.common.CollectionEntity<PopularLabel> colleEntPopularLabels = bizServiceConsumer
				.listPopularLabels("1000", "0");
		Collection<PopularLabel> collenPopularLabels = colleEntPopularLabels.getItems();
		Logging.log("List of popular labels -->" + collenPopularLabels.size());
		for (PopularLabel popularLabel : collenPopularLabels) {
			String labelName = popularLabel.getLabelName();
			if (labelName.equalsIgnoreCase(LABEL_NAME)) {
				return true;
			}
		}
		response = bizServiceConsumer.createLabel_(LABEL_NAME);
		if (response != null) {
			return true;
		}
		return false;
	}

	public static boolean attachProfileToTag(WebDriver driver, Map<String, String> profileWithStage) {
		// if Label is not existed it creates.
		Assert.assertTrue(verifyLabelIsExistIfNotCreate(profileWithStage), "Label creation is failed");
		for (int i = 0; i < profileWithStage.size(); i++) {
			CandidateProfilePage candidateProfilePage = new CandidateProfilePage(driver, true,
					profileWithStage.get(stages[i]));
			candidateProfilePage.profileAttachToTag(LABEL_NAME);
			Logging.log("profile id \t-->" + stages[i] + "Attached to the Label name \t-->" + LABEL_NAME);
		}
		return true;
	}

	public static String getNewLabel() {
		EntityLabel response = null;
		// if Label is not existed it creates.
		LabelsBizServiceConsumer bizServiceConsumer = new LabelsBizServiceConsumer();
		response = bizServiceConsumer.createLabel_(LABEL_NAME+UUID.randomUUID().toString().substring(0, 16));
		if (response != null) {
			LABEL_NAME = response.getLabelName();
			Logging.log("New Label created ---->"+LABEL_NAME);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return LABEL_NAME;
		} else {
			Assert.fail("Label creation failed !!!");
			return null;
		}
	}
	
	public static boolean profileAttachToTag(CandidateProfilePage candidateProfilePage){
		candidateProfilePage.profileAttachToTag(LABEL_NAME);
		return true;
	}
	
	/**
	 * Click on a tag and check the count of profiles loaded correctly.
	 * 
	 * @param homePageUtil
	 * @param profileWithStage
	 * @return
	 */
	public static boolean verifyTagResults(HomePageUtil homePageUtil, int result) {
		Assert.assertTrue(homePageUtil.clickOnATag(LABEL_NAME), "Click on a tag got failed !!!");
		homePageUtil.verifyProfileCountOnTaggedEntityPage(result);
		return true;
	}

	/**
	 * profile creation
	 * 
	 * @return
	 */
	public static String[] getNewProfileSkillAndId() {
		ProfileBizServiceConsumer profileBizServiceConsumer = null;
		Profile candidateProfile = new Profile();
		CollectionEntity<CandidateSkillMapBean> collenEntitySkillBean = new CollectionEntity<CandidateSkillMapBean>();
		CandidateBean candidateBean = new CandidateBean();
		CandidateSkillMapBean candidateSkillMapBean = new CandidateSkillMapBean();
		candidateSkillMapBean.setSkill("auto-" + UUID.randomUUID().toString().substring(0, 16));
		candidateBean.setFirstName("auto-skill" + UUID.randomUUID().toString().substring(0, 16));
		String[] skillId = new String[2];
		skillId[0] = candidateSkillMapBean.getSkill();

		collenEntitySkillBean.addItem(candidateSkillMapBean);
		candidateBean.setCandidateSkillMapBean(collenEntitySkillBean);
		candidateProfile.setCandidate(candidateBean);
		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		String id = profileBizServiceConsumer.createProfile(candidateProfile);
		if (id == null)
			throw new RuntimeException("Profile creation failed!!!");
		skillId[1] = id;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return skillId;
	}

}

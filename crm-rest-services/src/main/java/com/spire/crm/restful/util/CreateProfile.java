package com.spire.crm.restful.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;

import crm.pipeline.beans.CRM;
import spire.crm.profiles.bean.Profile;
import spire.social.entity.Social;
import spire.social.entity.SocialExt;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateProjectMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;
import spire.talent.entity.profileservice.beans.EntityAddressMapBean;

public class CreateProfile {
	static Gson gson = new Gson();

	static ProfileBizServiceConsumer profileBizServiceConsumer = null;
	static List<String> candidateIds = new ArrayList<String>();

	/**
	 * Create Profile
	 * 
	 * @param data
	 * @param candidateId
	 */
	public static String profileCreation(ProfileDataPoints profileDataPoints) {
		CandidateBean candidateBean = profileDataPoints.getCandidateBean();
		CandidateEducationMapBean educationMapBean = profileDataPoints.getCandidateEducationMapBean();
		CandidateProjectMapBean candidateProjectMapBean = profileDataPoints.getCandidateProjectMapBean();
		CandidateSkillMapBean candidateSkillMapBean = profileDataPoints.getCandidateSkillMapBean();
		CandidateEmployerMapBean candidateEmployerMapBean = profileDataPoints.getCandidateEmployerMapBean();
		EntityAddressMapBean entityAddressMapBean=profileDataPoints.getEntityAddressMapBean();
		
		Profile candidateProfile = new Profile();

		Short exp = candidateBean.getTotalExperienceMnth();
		String sourceName = candidateBean.getSourceName();
		String sourceType = candidateBean.getSourceType();
		String location = candidateBean.getLocationName();

		candidateBean.setSourceName(sourceName);
		candidateBean.setSourceType(sourceType);
		candidateBean.setTotalExperienceMnth(exp);
		candidateBean.setLocationName(location);

		Logging.log("Experience >>" + exp);
		Logging.log("sourceName >>" + sourceName);
		Logging.log("sourceType >>" + sourceType);
		Logging.log("city >>" + location);

		/**
		 * CandidateEducation details setting
		 */
		CollectionEntity<CandidateEducationMapBean> collenEntityCndEducationBean = new CollectionEntity<CandidateEducationMapBean>();
		collenEntityCndEducationBean.addItem(educationMapBean);
		candidateBean.setCandidateEducationMapBean(collenEntityCndEducationBean);

		/**
		 * CandidateProject details setting
		 */
		CollectionEntity<CandidateProjectMapBean> collenEntityCndProjBean = new CollectionEntity<CandidateProjectMapBean>();
		collenEntityCndProjBean.addItem(candidateProjectMapBean);
		candidateBean.setCandidateProjectMapBean(collenEntityCndProjBean);
		/**
		 * EntityAddress details setting
		 */
		CollectionEntity<EntityAddressMapBean> collenEntityAddressMapBean = new CollectionEntity<EntityAddressMapBean>();
		collenEntityAddressMapBean.addItem(entityAddressMapBean);
		candidateBean.setEntityAddressBean(collenEntityAddressMapBean);

		/**
		 * Candidate skills details setting
		 */
		CollectionEntity<CandidateSkillMapBean> collenEntitySkillBean = new CollectionEntity<CandidateSkillMapBean>();
		// For no.of skills will split...
		if (candidateSkillMapBean.getSkill().contains(",")) {
			String[] skills = candidateSkillMapBean.getSkill().split(",");
			for (String skill : skills) {
				CandidateSkillMapBean canSkills = new CandidateSkillMapBean();
				canSkills.setSkill(skill);
				collenEntitySkillBean.addItem(canSkills);
			}
		} else {
			collenEntitySkillBean.addItem(candidateSkillMapBean);
		}
		candidateBean.setCandidateSkillMapBean(collenEntitySkillBean);
		/**
		 * candidate employer settings.
		 */
		CollectionEntity<CandidateEmployerMapBean> colleEntyEmployer = new CollectionEntity<CandidateEmployerMapBean>();
		colleEntyEmployer.addItem(candidateEmployerMapBean);
		candidateBean.setCandidateEmployerMapBean(colleEntyEmployer);

		/**
		 * Setting all the candidate profile details.
		 */
		candidateProfile.setCandidate(candidateBean);
		/**
		 * setting CRM stage
		 */
		if(profileDataPoints.getCrm() !=null){
			CRM crm = new CRM();
			crm.setStatusName(profileDataPoints.getCrm().toString());
			candidateProfile.setCrm(crm);
		}else{
			Logging.log("CRM-Stage is not mentioned !!!");
		}
		/*if(profileDataPoints.getCandidateCrm()!=null){
			CRM crm = new CRM();
			//crm.setEngagementScore(profileDataPoints.getCandidateCrm().getEngagementScore());
			//crm.setFeedbackReplied(profileDataPoints.getCandidateCrm().getFeedbackReplied());
			//crm.setStatusChangeReason(profileDataPoints.getCandidateCrm().getStatusChangeReason());
			crm.setStatusName(profileDataPoints.getCrm().toString());
			candidateProfile.setCrm(crm);
		}else{
			Logging.log("CRM-Stage is not mentioned !!!");
		}*/
		/**
		 * setting employementType preffered candidate and vissa status
		 */
		if(profileDataPoints.getSocialExt() !=null){
			Social social = new Social();
			SocialExt socialExt=profileDataPoints.getSocialExt();
			List<String> preferrd=new ArrayList<String>();
			preferrd.add(profileDataPoints.getPreferred());
			socialExt.setPreferred(preferrd);
			socialExt.setEmploymentType(profileDataPoints.getSocialExt().getEmploymentType());
			socialExt.setVisaStatus(profileDataPoints.getSocialExt().getVisaStatus());
			social.setSocialExt(socialExt);
			candidateProfile.setSocial(social);
		}else{
			Logging.log("CRM-Stage is not mentioned !!!");
		}
		

		profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Gson json=new Gson();
		System.out.println(json.toJson(candidateProfile));
		String candidateId = profileBizServiceConsumer.createProfile(candidateProfile);
		if (candidateId != null) {
			System.out.println("Created candidateId: " + candidateId);
			candidateIds.add(candidateId);
			return candidateId;
		} else {
			throw new RuntimeException("Profile creation failed !!!");
		}

	}
}

package com.spire.crm.restful.util;

import java.util.List;

import crm.pipeline.beans.CRM;
import spire.social.entity.SocialExt;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateProjectMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;
import spire.talent.entity.profileservice.beans.EntityAddressMapBean;

public class ProfileDataPoints {
	CandidateProjectMapBean candidateProjectMapBean;
	CandidateBean candidateBean;
	CandidateEducationMapBean candidateEducationMapBean;
	CandidateSkillMapBean candidateSkillMapBean;
	CandidateEmployerMapBean candidateEmployerMapBean;
	EntityAddressMapBean entityAddressMapBean;
	SocialExt socialExt;
	String preferred;
	List<String> candidateIds;
	String crmStageName;
	CRM candidateCrm;

	public EntityAddressMapBean getEntityAddressMapBean() {
		return entityAddressMapBean;
	}

	public void setEntityAddressMapBean(EntityAddressMapBean entityAddressMapBean) {
		this.entityAddressMapBean = entityAddressMapBean;
	}

	
	public String getPreferred() {
		return preferred;
	}

	public void setPreferred(String preffered) {
		this.preferred = preffered;
	}

	
	public CRM getCandidateCrm() {
		return candidateCrm;
	}

	public void setCandidateCrm(CRM candidateCrm) {
		this.candidateCrm = candidateCrm;
	}

	public SocialExt getSocialExt() {
		return socialExt;
	}

	public void setSocialExt(SocialExt socialExt) {
		this.socialExt = socialExt;
	}

	public String getCrm() {
		return crmStageName;
	}

	public void setCrm(String crm) {
		this.crmStageName = crm;
	}

	public List<String> getCandidateIds() {
		return candidateIds;
	}

	public void setCandidateIds(List<String> candidateIds) {
		this.candidateIds = candidateIds;
	}

	public CandidateEmployerMapBean getCandidateEmployerMapBean() {
		return candidateEmployerMapBean;
	}

	public void setCandidateEmployerMapBean(CandidateEmployerMapBean candidateEmployerMapBean) {
		this.candidateEmployerMapBean = candidateEmployerMapBean;
	}

	public CandidateProjectMapBean getCandidateProjectMapBean() {
		return candidateProjectMapBean;
	}

	public void setCandidateProjectMapBean(CandidateProjectMapBean candidateProjectMapBean) {
		this.candidateProjectMapBean = candidateProjectMapBean;
	}

	public CandidateBean getCandidateBean() {
		return candidateBean;
	}

	public void setCandidateBean(CandidateBean candidateBean) {
		this.candidateBean = candidateBean;
	}

	public CandidateEducationMapBean getCandidateEducationMapBean() {
		return candidateEducationMapBean;
	}

	public void setCandidateEducationMapBean(CandidateEducationMapBean candidateEducationMapBean) {
		this.candidateEducationMapBean = candidateEducationMapBean;
	}

	public CandidateSkillMapBean getCandidateSkillMapBean() {
		return candidateSkillMapBean;
	}

	public void setCandidateSkillMapBean(CandidateSkillMapBean candidateSkillMapBean) {
		this.candidateSkillMapBean = candidateSkillMapBean;
	}

}

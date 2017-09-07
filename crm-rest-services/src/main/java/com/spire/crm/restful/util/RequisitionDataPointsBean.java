package com.spire.crm.restful.util;

import java.util.List;

import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.demandservice.beans.ReqJobFamilyMapBean;
import spire.talent.entity.demandservice.beans.ReqLocationMapBean;
import spire.talent.entity.demandservice.beans.ReqSkillMapBean;
import spire.talent.entity.demandservice.beans.ReqSourceMapBean;
import spire.talent.entity.demandservice.beans.ReqUserMapBean;
import spire.talent.entity.demandservice.beans.RequisitionBean;

public class RequisitionDataPointsBean {
	RequisitionBean requisitionBean;
	ReqSourceMapBean reqSourceMapBean;
	List<ReqSkillMapBean> reqSkillMapBean;
	ReqLocationMapBean reqLocationMapBean;
	ReqJobFamilyMapBean reqJobFamilyMapBean;
	ReqUserMapBean reqUserMapBean;
	public RequisitionBean getRequisitionBean() {
		return requisitionBean;
	}
	public void setRequisitionBean(RequisitionBean requisitionBean) {
		this.requisitionBean = requisitionBean;
	}
	public ReqSourceMapBean getReqSourceMapBean() {
		return reqSourceMapBean;
	}
	public void setReqSourceMapBean(ReqSourceMapBean reqSourceMapBean) {
		this.reqSourceMapBean = reqSourceMapBean;
	}
	public List<ReqSkillMapBean> getReqSkillMapBean() {
		return reqSkillMapBean;
	}
	public void setReqSkillMapBean(List<ReqSkillMapBean> reqSkillMapBean) {
		this.reqSkillMapBean = reqSkillMapBean;
	}
	public ReqLocationMapBean getReqLocationMapBean() {
		return reqLocationMapBean;
	}
	public void setReqLocationMapBean(ReqLocationMapBean reqLocationMapBean) {
		this.reqLocationMapBean = reqLocationMapBean;
	}
	public ReqJobFamilyMapBean getReqJobFamilyMapBean() {
		return reqJobFamilyMapBean;
	}
	public void setReqJobFamilyMapBean(ReqJobFamilyMapBean reqJobFamilyMapBean) {
		this.reqJobFamilyMapBean = reqJobFamilyMapBean;
	}
	public ReqUserMapBean getReqUserMapBean() {
		return reqUserMapBean;
	}
	public void setReqUserMapBean(ReqUserMapBean reqUserMapBean) {
		this.reqUserMapBean = reqUserMapBean;
	}
	
	public RequisitionBean requisitionCreateData() {
		RequisitionBean requisitionBean = getRequisitionBean();
		List<ReqSkillMapBean> reqSkillMapBean = getReqSkillMapBean();
		ReqLocationMapBean reqLocationMapBean = getReqLocationMapBean();
		CollectionEntity<ReqSkillMapBean> colleReqSkill = new CollectionEntity<ReqSkillMapBean>();
		if(reqSkillMapBean!=null){
		for (ReqSkillMapBean reqSkillMapBean2 : reqSkillMapBean) {
			colleReqSkill.addItem(reqSkillMapBean2);
		}
		}
		requisitionBean.setReqSkillMapBean(colleReqSkill);
		CollectionEntity<ReqLocationMapBean> colleReqLocation = new CollectionEntity<ReqLocationMapBean>();
		colleReqLocation.addItem(reqLocationMapBean);
		requisitionBean.setReqLocationMapBean(colleReqLocation);
		return requisitionBean;
	}
	
}

package com.spire.crm.activity.biz.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import spire.commons.activitystream.Object;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CRMActivity <O> extends spire.commons.activitystream.SingleEntity{

	
	private Object<O> objectInfo;
	
	@SuppressWarnings("rawtypes")
	private List<CRMActivityDetails> activityDetailsList;
	
	
	public Object<O> getObjectInfo() {
		return objectInfo;
	}
	public void setObjectInfo(Object<O> objectInfo) {
		this.objectInfo = objectInfo;
	}
	@SuppressWarnings("rawtypes")
	public List<CRMActivityDetails> getActivityDetailsList() {
		return activityDetailsList;
	}
	public void setActivityDetailsList(@SuppressWarnings("rawtypes") List<CRMActivityDetails> activityDetailsList) {
		this.activityDetailsList = activityDetailsList;
	}
}

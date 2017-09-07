package com.spire.common;

public class CreateRequisitionBean {
	private ReqLocationMapBean reqLocationMapBean;

	private String maxComp;

	private String hiredCandidates;

	private String minComp;

	private String totalOpenPositions;

	private String id;

	private String clientCreatedOn;

	private String interviewedCandidates;

	private String totalCount;

	private String openDate;

	private String jobTitle;

	private String offerReleasedCandidates;

	private String screenReject;

	private String closeDate;

	private String crntOpenPositions;

	private String jobDescriptionUrl;

	private String businessReject;

	private String clientUpdatedOn;

	private String screenSelect;

	private String businessSelect;

	private String yetToJoin;

	private ReqSkillMapBean reqSkillMapBean;

	private String displayId;

	private String flowId;

	public ReqLocationMapBean getReqLocationMapBean() {
		return reqLocationMapBean;
	}

	public void setReqLocationMapBean(ReqLocationMapBean reqLocationMapBean) {
		this.reqLocationMapBean = reqLocationMapBean;
	}

	public String getMaxComp() {
		return maxComp;
	}

	public void setMaxComp(String maxComp) {
		this.maxComp = maxComp;
	}

	public String getHiredCandidates() {
		return hiredCandidates;
	}

	public void setHiredCandidates(String hiredCandidates) {
		this.hiredCandidates = hiredCandidates;
	}

	public String getMinComp() {
		return minComp;
	}

	public void setMinComp(String minComp) {
		this.minComp = minComp;
	}

	public String getTotalOpenPositions() {
		return totalOpenPositions;
	}

	public void setTotalOpenPositions(String totalOpenPositions) {
		this.totalOpenPositions = totalOpenPositions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientCreatedOn() {
		return clientCreatedOn;
	}

	public void setClientCreatedOn(String clientCreatedOn) {
		this.clientCreatedOn = clientCreatedOn;
	}

	public String getInterviewedCandidates() {
		return interviewedCandidates;
	}

	public void setInterviewedCandidates(String interviewedCandidates) {
		this.interviewedCandidates = interviewedCandidates;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOfferReleasedCandidates() {
		return offerReleasedCandidates;
	}

	public void setOfferReleasedCandidates(String offerReleasedCandidates) {
		this.offerReleasedCandidates = offerReleasedCandidates;
	}

	public String getScreenReject() {
		return screenReject;
	}

	public void setScreenReject(String screenReject) {
		this.screenReject = screenReject;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCrntOpenPositions() {
		return crntOpenPositions;
	}

	public void setCrntOpenPositions(String crntOpenPositions) {
		this.crntOpenPositions = crntOpenPositions;
	}

	public String getJobDescriptionUrl() {
		return jobDescriptionUrl;
	}

	public void setJobDescriptionUrl(String jobDescriptionUrl) {
		this.jobDescriptionUrl = jobDescriptionUrl;
	}

	public String getBusinessReject() {
		return businessReject;
	}

	public void setBusinessReject(String businessReject) {
		this.businessReject = businessReject;
	}

	public String getClientUpdatedOn() {
		return clientUpdatedOn;
	}

	public void setClientUpdatedOn(String clientUpdatedOn) {
		this.clientUpdatedOn = clientUpdatedOn;
	}

	public String getScreenSelect() {
		return screenSelect;
	}

	public void setScreenSelect(String screenSelect) {
		this.screenSelect = screenSelect;
	}

	public String getBusinessSelect() {
		return businessSelect;
	}

	public void setBusinessSelect(String businessSelect) {
		this.businessSelect = businessSelect;
	}

	public String getYetToJoin() {
		return yetToJoin;
	}

	public void setYetToJoin(String yetToJoin) {
		this.yetToJoin = yetToJoin;
	}

	public ReqSkillMapBean getReqSkillMapBean() {
		return reqSkillMapBean;
	}

	public void setReqSkillMapBean(ReqSkillMapBean reqSkillMapBean) {
		this.reqSkillMapBean = reqSkillMapBean;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

}

class ReqLocationMapBean {
	private String hasMore;

	private String totalResults;

	private Items[] items;

	private Links[] links;

	public String getHasMore() {
		return hasMore;
	}

	public void setHasMore(String hasMore) {
		this.hasMore = hasMore;
	}

	public String getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}

	public Items[] getItems() {
		return items;
	}

	public void setItems(Items[] items) {
		this.items = items;
	}

	public Links[] getLinks() {
		return links;
	}

	public void setLinks(Links[] links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "ClassPojo [hasMore = " + hasMore + ", totalResults = " + totalResults + ", items = " + items
				+ ", links = " + links + "]";
	}
}

class Links {
	private String rel;

	private String href;

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "ClassPojo [rel = " + rel + ", href = " + href + "]";
	}
}

class ReqSkillMapBean {
	private String hasMore;

	private String totalResults;

	private Items[] items;

	private Links[] links;

	public String getHasMore() {
		return hasMore;
	}

	public void setHasMore(String hasMore) {
		this.hasMore = hasMore;
	}

	public String getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}

	public Items[] getItems() {
		return items;
	}

	public void setItems(Items[] items) {
		this.items = items;
	}

	public Links[] getLinks() {
		return links;
	}

	public void setLinks(Links[] links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "ClassPojo [hasMore = " + hasMore + ", totalResults = " + totalResults + ", items = " + items
				+ ", links = " + links + "]";
	}
}

class Items {
	private String reqId;

	private String skillCategory;

	private String skillPrfcncyFrom;

	private String skillExprncFromMnth;

	private String updatedBy;

	private String id;

	private String createdOn;

	private String skill;

	private String createdBy;

	private String skillIndex;

	private String skillOccurrence;

	private String updatedOn;

	private String skillExprncToMnth;

	private String flowId;

	private String skillPrfcncyTo;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getSkillCategory() {
		return skillCategory;
	}

	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}

	public String getSkillPrfcncyFrom() {
		return skillPrfcncyFrom;
	}

	public void setSkillPrfcncyFrom(String skillPrfcncyFrom) {
		this.skillPrfcncyFrom = skillPrfcncyFrom;
	}

	public String getSkillExprncFromMnth() {
		return skillExprncFromMnth;
	}

	public void setSkillExprncFromMnth(String skillExprncFromMnth) {
		this.skillExprncFromMnth = skillExprncFromMnth;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSkillIndex() {
		return skillIndex;
	}

	public void setSkillIndex(String skillIndex) {
		this.skillIndex = skillIndex;
	}

	public String getSkillOccurrence() {
		return skillOccurrence;
	}

	public void setSkillOccurrence(String skillOccurrence) {
		this.skillOccurrence = skillOccurrence;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getSkillExprncToMnth() {
		return skillExprncToMnth;
	}

	public void setSkillExprncToMnth(String skillExprncToMnth) {
		this.skillExprncToMnth = skillExprncToMnth;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getSkillPrfcncyTo() {
		return skillPrfcncyTo;
	}

	public void setSkillPrfcncyTo(String skillPrfcncyTo) {
		this.skillPrfcncyTo = skillPrfcncyTo;
	}

	@Override
	public String toString() {
		return "ClassPojo [reqId = " + reqId + ", skillCategory = " + skillCategory + ", skillPrfcncyFrom = "
				+ skillPrfcncyFrom + ", skillExprncFromMnth = " + skillExprncFromMnth + ", updatedBy = " + updatedBy
				+ ", id = " + id + ", createdOn = " + createdOn + ", skill = " + skill + ", createdBy = " + createdBy
				+ ", skillIndex = " + skillIndex + ", skillOccurrence = " + skillOccurrence + ", updatedOn = "
				+ updatedOn + ", skillExprncToMnth = " + skillExprncToMnth + ", flowId = " + flowId
				+ ", skillPrfcncyTo = " + skillPrfcncyTo + "]";
	}
}
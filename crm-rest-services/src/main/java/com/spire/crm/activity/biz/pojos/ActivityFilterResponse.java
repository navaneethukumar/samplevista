package com.spire.crm.activity.biz.pojos;

import java.util.List;

public class ActivityFilterResponse {
	
	private List<CRMActivity> crmActivities;
	private List<ActivityTypeCount> activityTypeCountSummary;
	
	public List<CRMActivity> getCrmActivities() {
		return crmActivities;
	}
	public void setCrmActivities(List<CRMActivity> crmActivities) {
		this.crmActivities = crmActivities;
	}
	public List<ActivityTypeCount> getActivityTypeCountSummary() {
		return activityTypeCountSummary;
	}
	public void setActivityTypeCountSummary(List<ActivityTypeCount> activityTypeCountSummary) {
		this.activityTypeCountSummary = activityTypeCountSummary;
	}

}

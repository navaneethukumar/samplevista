package com.spire.crm.activity.biz.pojos;

import java.util.List;

public class ActivitySearchFilter {
	
	private String timePeriod;
	private String searchText;
	List<String> activityTypes;
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public List<String> getActivityTypes() {
		return activityTypes;
	}
	public void setActivityTypes(List<String> activityTypes) {
		this.activityTypes = activityTypes;
	}
	
}

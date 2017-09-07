package com.spire.crm.restful.util;

import java.util.List;

public class SearchBizBean {

	public enum EXPECTED_DATAPOINT {
		ONE, TWO, THREE,MORE,NULL
	};

	private EXPECTED_DATAPOINT result;
	private String searchQueryString;
	private List<String> skills;
	private List<String> experience;
	private List<String> locations;
	private List<String> status;
	private List<String> qualifications;
	private List<String> institute;
	private List<String> companies;
	private List<String> role;
	private List<String> stageNames;
	private List<String> engagementScore;
	private List<String> sourceName;
	private List<String> sourceType;
	private List<String> preferred;
	private List<String> employmentType;
	private List<String> visaStatus;
	private List<String> country;
	private List<String> state;
	private List<String> currentRole;
	private List<String> currentEmployer;
	private String freeSearchQueryString;
	private String client;
	private List<String> forcedExperience;
	private List<String> forcedEngagementScore;
	
	public List<String> getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(List<String> currentRole) {
		this.currentRole = currentRole;
	}

	public List<String> getCurrentEmployer() {
		return currentEmployer;
	}

	public void setCurrentEmployer(List<String> currentEmployer) {
		this.currentEmployer = currentEmployer;
	}

	public List<String> getPreferred() {
		return preferred;
	}

	public void setPreferred(List<String> preferred) {
		this.preferred = preferred;
	}

	public List<String> getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(List<String> employmentType) {
		this.employmentType = employmentType;
	}

	public List<String> getVisaStatus() {
		return visaStatus;
	}

	public void setVisaStatus(List<String> visaStatus) {
		this.visaStatus = visaStatus;
	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
	}

	public List<String> getState() {
		return state;
	}

	public void setState(List<String> state) {
		this.state = state;
	}

	public List<String> getForcedExperience() {
		return forcedExperience;
	}

	public void setForcedExperience(List<String> forcedExperience) {
		this.forcedExperience = forcedExperience;
	}

	public List<String> getForcedEngagementScore() {
		return forcedEngagementScore;
	}

	public void setForcedEngagementScore(List<String> forcedEngagementScore) {
		this.forcedEngagementScore = forcedEngagementScore;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getFreeSearchQueryString() {
		return freeSearchQueryString;
	}

	public void setFreeSearchQueryString(String freeSearchQueryString) {
		this.freeSearchQueryString = freeSearchQueryString;
	}

	public EXPECTED_DATAPOINT getResult() {
		return result;
	}

	public void setResult(EXPECTED_DATAPOINT result) {
		this.result = result;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<String> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<String> qualifications) {
		this.qualifications = qualifications;
	}

	public List<String> getCompanies() {
		return companies;
	}

	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}

	public String getSearchQueryString() {
		return searchQueryString;
	}

	public void setSearchQueryString(String searchQueryString) {
		this.searchQueryString = searchQueryString;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<String> getExperience() {
		return experience;
	}

	public void setExperience(List<String> experience) {
		this.experience = experience;
	}

	public List<String> getLocation() {
		return locations;
	}

	public void setLocation(List<String> location) {
		this.locations = location;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getQualification() {
		return qualifications;
	}

	public void setQualification(List<String> qualification) {
		this.qualifications = qualification;
	}

	public List<String> getInstitute() {
		return institute;
	}

	public void setInstitute(List<String> institute) {
		this.institute = institute;
	}

	public List<String> getCompany() {
		return companies;
	}

	public void setCompany(List<String> company) {
		this.companies = company;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public List<String> getStageNames() {
		return stageNames;
	}

	public void setStageNames(List<String> stageNames) {
		this.stageNames = stageNames;
	}

	public List<String> getEngagementScore() {
		return engagementScore;
	}

	public void setEngagementScore(List<String> engagementScore) {
		this.engagementScore = engagementScore;
	}

	public List<String> getSourceName() {
		return sourceName;
	}

	public void setSourceName(List<String> sourceName) {
		this.sourceName = sourceName;
	}

	public List<String> getSourceType() {
		return sourceType;
	}

	public void setSourceType(List<String> sourceType) {
		this.sourceType = sourceType;
	}

}

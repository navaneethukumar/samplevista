package com.spire.base.bean;

import java.util.List;

public class ReportBean {

	private int id;
	private int suiteId;
	private String testName;
	private String testType;
	private int totalTestCases;
	private int passedTestCases;
	private int failedTestCases;
	private String passePercentage;
	private int testSkipped;
	private int timeTaken;
	private String includedGroups;
	private String excludedGroups;
	private String environment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSuiteId() {
		return suiteId;
	}

	public void setSuiteId(int suiteId) {
		this.suiteId = suiteId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public int getTotalTestCases() {
		return totalTestCases;
	}

	public void setTotalTestCases(int totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public int getPassedTestCases() {
		return passedTestCases;
	}

	public void setPassedTestCases(int passedTestCases) {
		this.passedTestCases = passedTestCases;
	}

	public int getFailedTestCases() {
		return failedTestCases;
	}

	public void setFailedTestCases(int failedTestCases) {
		this.failedTestCases = failedTestCases;
	}

	public String getPassePercentage() {
		return passePercentage;
	}

	public void setPassePercentage(String passePercentage) {
		this.passePercentage = passePercentage;
	}

	public int getTestSkipped() {
		return testSkipped;
	}

	public void setTestSkipped(int testSkipped) {
		this.testSkipped = testSkipped;
	}

	public int getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getIncludedGroups() {
		return includedGroups;
	}

	public void setIncludedGroups(String includedGroups) {
		this.includedGroups = includedGroups;
	}

	public String getExcludedGroups() {
		return excludedGroups;
	}

	public void setExcludedGroups(String excludedGroups) {
		this.excludedGroups = excludedGroups;
	}
	
	public static void printReportList(List<ReportBean> resultList){
		
		for (int i = 0; i < resultList.size(); i++) {
			
			ReportBean report=resultList.get(i);
			System.out.println(report.getTestName());
			System.out.println(report.getTestType());
			System.out.println(report.getTotalTestCases());
			System.out.println(report.getPassedTestCases());
			System.out.println(report.getFailedTestCases());
			System.out.println(report.getPassePercentage());
			System.out.println(report.getTestSkipped());
			System.out.println(report.getTimeTaken());
			System.out.println(report.getIncludedGroups());
			System.out.println(report.getExcludedGroups());
									
		}
		
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

}

package com.spire.pipeline;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.spire.base.controller.Assertion;
import com.spire.crm.pages.HomePage;

public class ValidatePipeLineDataUtil extends HomePage {

	public ValidatePipeLineDataUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);

	}

	public void validateElementPresent() {
		Assertion.assertTrue(isElementPreset(lead), "Lead field not showing in Pipeline");
		Assertion.assertTrue(isElementPreset(engage), "Engage field not showing in Pipeline");
		Assertion.assertTrue(isElementPreset(applicant), "Applicant field not showing in Pipeline");
		Assertion.assertTrue(isElementPreset(hold), "Hold field not showing in Pipeline");
		Assertion.assertTrue(isElementPreset(rejected), "Rejected field not showing in Pipeline");
		Assertion.assertTrue(isElementPreset(graphButton), "GraphButton is missing!!");
		/*Assertion.assertTrue(Integer.parseInt(getStageCount("Lead")) >= 0, "Not showing total Lead count");
		Assertion.assertTrue(Integer.parseInt(getStageCount("Engaged")) >= 0, "Not showing total Engaged count");
		Assertion.assertTrue(Integer.parseInt(getStageCount("Applicant")) >= 0, "Not showing total Applicants count");
		Assertion.assertTrue(Integer.parseInt(getStageCount("Hold")) >= 0, "Not showing total Hold count");
		Assertion.assertTrue(Integer.parseInt(getStageCount("Rejected")) >= 0, "Not showing total Rejected count");
*/
	}

	public String getStageCount(String stageName) {

		String count = "";
		switch (stageName) {
		case "Lead":
			count = getElementText(driver, leadCount);
			break;
		case "Engaged":
			count= getElementText(driver, engagedCount);
			break;
		case "Applicant":
			count = getElementText(driver, applicantCount);
			break;
		case "Hold":
			count= getElementText(driver, holdCount);
			break;
		case "Rejected":
			return getElementText(driver, rejectedCount);

		}
		return count;

	}

	public void validateLeadCandidates() {
		waitForElementToBeVisible(driver, lead);
		String totalLeadcandidates = getElementText(driver, leadCount);
		clicElement(driver, lead);
		validateLeadStageCheked();
		//engageLeadCount(totalLeadcandidates);

	}

	public void engageLeadCount(String totalLeadcandidates) {
		/*List<WebElement> allLeads = driver.findElements(listOfEngageLeadCandidate);
		int count1 = allLeads.size();
		String str = Integer.toString(count1);
		driver.navigate().back();
		waitForElementToBeVisible(driver, leadCount);
		str.equals(leadCount);*/
		
		clicElement(driver, homeTab);
		int LeadCandidates = Integer.parseInt(totalLeadcandidates);
		if(LeadCandidates>6)
		{
			Assertion.assertTrue(isElementPreset(paginationInEngagedPage), "Pagination is displayed");
		}
		else 
		{
			Assertion.assertFalse(isElementPreset(paginationInEngagedPage), "Pagination doesn't displayed");
		}
	}

	public void validateEngageCandidates() {
		waitForElementToBeVisible(driver, engaged);
		String totalEngagecandidates =getElementText(driver, engagedCount);
		clicElement(driver, engaged);
		validateEngageStageCheked();
		//engageEngageCount();
		
	}

	public void engageEngageCount() {
		List<WebElement> allEngages = driver.findElements(listOfEngageEngagedCandidates);
		int count2 = allEngages.size();
		String str = Integer.toString(count2);
		str.equals(engagedCount);
	}

	public void validateApplicantCandidates() {

		waitForElementToBeVisible(driver, applicant);
		String totalApplicantcandidates =getElementText(driver, applicantCount);
		clicElement(driver, applicant);
		validateApplicantStageCheked();
		//engageApplicantCount();
		
	}

	public void engageApplicantCount() {
		List<WebElement> allApplicants = driver.findElements(listOfEngageApplicantCandidates);
		int count3 = allApplicants.size();
		String str = Integer.toString(count3);
		str.equals(applicantCount);
	}

	public void validateHoldCandidates() {
	waitForElementToBeVisible(driver, hold);
	String totalHoldcandidates = getElementText(driver, holdCount);
		clicElement(driver, hold);
		validateHoldStageCheked();
		//engageHoldCount(totalHoldcandidates);
		
	}
	public void engageHoldCount(String totalHoldcandidates) {
		
	    clicElement(driver, nextBtn);
		int holdCandidates = Integer.parseInt(totalHoldcandidates);
		if(holdCandidates>6)
		{
			Assertion.assertTrue(isElementPreset(paginationInEngagedPage), "Pagination is displayed");
		}
		else 
		{
			Assertion.assertFalse(isElementPreset(paginationInEngagedPage), "Pagination doesn't displayed");
		}
	}
	 public static void scrollTo(WebDriver driver, By scrollBarInEngagePage) {
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView();", scrollBarInEngagePage);
	    }

	public void validateRejectedCandidates() {
		waitForElementToBeVisible(driver, rejected);
			getElementText(driver, rejectedCount);
			clicElement(driver, rejected);
			validateRejectedStageCheked();
		//	engageRejectedCount();
			
		}
	public void engageRejectedCount() {
		List<WebElement> allApplicants = driver.findElements(listOfRejectedCandidates);
		int count3 = allApplicants.size();
		String str = Integer.toString(count3);
		str.equals(rejectedCount);
	}

	public void validateGraphBtnLeadCandidates() {
		waitForElementToBeVisible(driver, graphButton);
		clicElement(driver, graphButton);
		waitForElementToBeVisible(driver, graphLead);
		clicElement(driver, graphLead);

	}

	public void validateGraphBtnEngagedCandidates() {
		waitForElementToBeVisible(driver, graphButton);
		clicElement(driver, graphButton);
		waitForElementToBeVisible(driver, graphEngaged);
		clicElement(driver, graphEngaged);

	}

	public void validateGraphBtnApplicantCandidates() {
		waitForElementToBeVisible(driver, graphButton);
		clicElement(driver, graphButton);
		waitForElementToBeVisible(driver, graphApplicant);
		clicElement(driver, graphApplicant);

	}

	public void validateDateRangeFilter() {
		Select dropdown = new Select(driver.findElement(dateRage));
		dropdown.selectByVisibleText("The Past 1 day");
		dropdown.selectByVisibleText("The Past 1 week");
		dropdown.selectByVisibleText("The Past 2 week");
		dropdown.selectByVisibleText("The Beginng of Time");

	}

	public void validateLeadStageCheked() {
		waitForElementToBeVisible(driver, stageIcon);
		clicElement(driver, stageIcon);
		waitForElementToBeVisible(driver, leadStageChecked);
		Assert.assertTrue(!driver.findElement(leadStageChecked).isSelected(), "Lead stage is selected");
		clicElement(driver, stageIcon);
	}

	public void validateEngageStageCheked() {
		waitForElementToBeVisible(driver, stageIcon);
		clicElement(driver, stageIcon);
		waitForElementToBeVisible(driver, engagedStageChecked);
		Assert.assertTrue(!driver.findElement(engagedStageChecked).isSelected(), "Engaged stage is selected");
		clicElement(driver, stageIcon);
	}

	public void validateApplicantStageCheked() {
		waitForElementToBeVisible(driver, stageIcon);
		clicElement(driver, stageIcon);
		waitForElementToBeVisible(driver, applicantStageChecked);
		Assert.assertTrue(!driver.findElement(applicantStageChecked).isSelected(), "Applicant stage is selected");
		clicElement(driver, stageIcon);
	}

	public void validateHoldStageCheked() {
		waitForElementToBeVisible(driver, stageIcon);
		clicElement(driver, stageIcon);
		waitForElementToBeVisible(driver, applicantStageChecked);
		Assert.assertTrue(!driver.findElement(applicantStageChecked).isSelected(), "Hold stage is selected");
		clicElement(driver, stageIcon);
	}
	
	public void validateRejectedStageCheked() {
		waitForElementToBeVisible(driver, stageIcon);
		clicElement(driver, stageIcon);
		waitForElementToBeVisible(driver, applicantStageChecked);
		Assert.assertTrue(!driver.findElement(applicantStageChecked).isSelected(), "Rejected stage is selected");
		clicElement(driver, stageIcon);
	}
	
	public void validateMinimizeStageBtn() {
		waitForElementToBeVisible(driver, lead);
		clicElement(driver, lead);
		waitForElementToBeVisible(driver, minimizeBtn);
		clicElement(driver, minimizeBtn);

	}
}
package com.spire.crm.pageUtils;

import org.openqa.selenium.WebDriver;

import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pages.AdvancedSearchPage;

public class AdvancedSearchPageUtil extends AdvancedSearchPage {

	public AdvancedSearchPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
	}

	/*
	 * 
	 * 
	 */

	public void searchCandidate(String enterSkill, String location,
			String role, String company, String fromYear, String fromMonth,
			String toYear, String toMonth, String education, String institute, String eScoreFrm) {

		WebPageHelper.waitForElementToBeVisible(driver, skillsTextBox);
		WebPageHelper.enterText(driver, skillsTextBox, enterSkill);	
		WebPageHelper.enterText(driver, locationTextBox, location);
		WebPageHelper.enterText(driver, roleTextBox, role);
		WebPageHelper.enterText(driver, companyTextBox, company);
		WebPageHelper.selectdropdownBytext(driver, yearsFromTextBox, fromYear);
		WebPageHelper.selectdropdownBytext(driver, monthsFromTextBox, fromMonth);
		WebPageHelper.selectdropdownBytext(driver, yearsToTextBox, toYear);
		WebPageHelper.selectdropdownBytext(driver, monthsToTextBox, toMonth);
	    WebPageHelper.enterText(driver, qualificationTextBox, education);
		WebPageHelper.enterText(driver, instituteTextBox, institute);
		WebPageHelper.enterText(driver, engScoreFrm, eScoreFrm);
	    WebPageHelper.clicElement(driver, searchBtn);
		
	}
	
}
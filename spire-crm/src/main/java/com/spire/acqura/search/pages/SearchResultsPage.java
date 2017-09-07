/**
 * @author Rajasekhar
 *
 */
package com.spire.acqura.search.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.base.controller.TestPlan;

public class SearchResultsPage extends TestPlan {
	
	
	public SearchResultsPage(WebDriver driver, String openurl) {

	}
	public SearchResultsPage(WebDriver driver, Boolean openurl) {
		
	}
	public By universalsearchcriteria = By.xpath(".//*[@id='tagslist']");
	public By searchbutton = By.xpath(".//*[@id='searchBtn']");
	public By searchresultsBar = By.xpath(".//*[contains(text(),'Search Results')]");
	public By advanceSearchbutton = By.xpath("//div/h3[contains(text(),'Advanced Search')]");
	public By savedSearchbutton = By.xpath("//*[contains(text(),'Saved Search')][@role='button']");
	public By facets = By.xpath(".//*[contains(@class,'facets-container ng-scope')]");
	public By collapseAll = By.xpath(".//*[@id='collapseAllBtn']");
	public By reset = By.xpath(".//*[@id='resetBtn']");
	public By skillFacet = By.xpath(".//*[@id='collapse_0']");
	public By locationFacet = By.xpath(".//*[@id='collapse_1']");
	public By experienceFacet = By.xpath(".//*[@id='collapse_2']");
	public By EmployerFacet = By.xpath(".//*[@id='collapse_3']");
	public By instituteFacet = By.xpath(".//*[@id='collapse_4']");
	public By engagementFacet = By.xpath(".//*[@id='collapse_5']");
	public By crmStageFacet = By.xpath(".//*[@id='collapse_6']");
	public By visasStatusFacet = By.xpath(".//*[@id='collapse_16']");
	public By employementTypeFacet = By.xpath(".//*[@id='collapse_17']");
	public By preferedFacet = By.xpath(".//*[@id='collapse_4']");
	public By currentRole = By.xpath(".//*[@id='collapse_19']");
	public By sourcenameFacet = By.xpath(".//*[@id='collapse_10']");
	public By sourcetypeFacet = By.xpath(".//*[@id='collapse_11']");
	public By qualificationFacet = By.xpath(".//*[@id='collapse_12']");
	public By roleFacet = By.xpath(".//*[@id='collapse_15']");
	public By companyFacet = By.xpath(".//*[@id='collapse_20']");
	public By verifycount = By.xpath(".//span[contains(@class,'pagination-summary')]");
	public By pagination = By.xpath(".//span[contains(@ng-disabled,'true')]");
	public By resultPage = By.xpath(".//div[contains(@class,'results-list')]");
	public By resumeDownlaod = By.xpath(".//li[1]/div[1]/div[4]/div[2]/span[contains(@class,'glyphicon glyphicon-download')]");
	public By label = By.xpath(".//*[@id='labelBtn']");
	public By moreSkills = By.xpath(".//*/li[1]/div/div[2]/div[4]/b[contains(@class,'ng-binding')]");
	public By candidatename = By.xpath(".//li[1]/div/div[2]/div[1]/h3[contains(@class,'ng-binding')]");
	public By candidatecheckbox = By.xpath(".//li[1]/span[contains(@class,'glyphicon glyphicon-ok select-icon')]");
	public By candidateEducationsymbol = By.xpath(".//li[1]/div/div[2]/div[2]/div[1]/span[contains(@class,'glyphicon glyphicon-book')]");
	public By candidateEducation = By.xpath(".//li[1]/div/div[2]/div[2]/div[1][contains(@class,'col-md-5 summary-text-wrap')]");
	public By candidatePortal = By.xpath(".//li[1]/div/div[2]/div[2]/div[2][contains(@class,'col-md-3 summary-text-wrap')]");
	public By candidatePortalsymbol = By.xpath(".//li[1]/div/div[2]/div[2]/div[2]/span[contains(@class,'glyphicon glyphicon-cloud')]");
	public By candidateEmailsymbol = By.xpath(".//li[1]/div/div[2]/div[2]/div[3]/span[contains(@class,'glyphicon glyphicon-envelope')]");
	public By candidateEmail = By.xpath(".//li[1]/div/div[2]/div[2]/div[3]/span[contains(@class,'summary-text ng-binding')]");
	public By candidateJobtitlesymbol = By.xpath(".//li[1]/div/div[2]/div[3]/div[1]/span[contains(@class,'glyphicon glyphicon-briefcase')]");
	public By candidateJobtitle = By.xpath(".//li[1]/div/div[2]/div[3]/div[1]/span[contains(@ng-show,'entity.currentEmployer')]");
    public By candidateExperiencesymbol = By.xpath(".//li[1]/div/div[2]/div[3]/div[2]/span[contains(@class,'glyphicon glyphicon-briefcase')]");
    public By candidateExperience = By.xpath(".//li[1]/div/div[2]/div[3]/div[2]/span[contains(@class,'summary-text ng-binding')]");
    
   
private void waitForElementToBeVisible(WebDriver driver,
		By universalsearchcriteria2) {
	
	
}
private boolean isElementVisiable(WebDriver driver, By universalsearchcriteria2) {
	
	return false;
}



public void verifyuniversalsearchcriteria(){
	waitForElementToBeVisible(driver, universalsearchcriteria);
	Assert.assertTrue(isElementVisiable(driver, universalsearchcriteria));
}

public void clickresumeDownnload(){
	this.driver.findElement(resumeDownlaod).click();
		
}

public void clickpagination(){
this.driver.findElement(pagination).click();

}

public void clickReset(){
	this.driver.findElement(reset).click();

}

public void clickCollapse(){
	this.driver.findElement(collapseAll).click();
}

public void clickmoreSkills(){
this.driver.findElement(moreSkills).click();
}
}
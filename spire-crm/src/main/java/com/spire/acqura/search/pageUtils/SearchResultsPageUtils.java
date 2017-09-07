/**
 * @author Rajasekhar
 *
 */
package com.spire.acqura.search.pageUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.acqura.search.pages.SearchResultsPage;
import com.spire.base.controller.Logging;

public class SearchResultsPageUtils extends SearchResultsPage  {
    
    public SearchResultsPageUtils(WebDriver driver, Boolean openurl) {

        super(driver, openurl);

    }
    
    public SearchResultsPageUtils(WebDriver driver, String openurl) {

        super(driver, openurl);

    }

    private void waitForElementToBeVisible(WebDriver driver,
    		By universalsearchcriteria) {};

public void validateSearchResultpageFacetvalues(){
    waitForElementToBeVisible(driver, universalsearchcriteria);
    Assert.assertTrue(isElementPreset(universalsearchcriteria), "universalsearchcriteria is not present");
    Logging.log("universalsearchcriteria is present");
    
    Assert.assertTrue(isElementPreset(searchresultsBar), "searchresultsBar is not present");
    Logging.log("searchresultsBar is present");
    
    Assert.assertTrue(isElementPreset(advanceSearchbutton), "advanceSearchbutton is not present");
    Logging.log("advanceSearchbutton is present");
    
    Assert.assertTrue(isElementPreset(savedSearchbutton), "savedSearchbutton is not present");
    Logging.log("savedSearchbutton is present");
    
    Assert.assertTrue(isElementPreset(resultPage), "resultPage is not present");
    Logging.log("resultPage is present");
    
    Assert.assertTrue(isElementPreset(facets), "facets is not present");
    Logging.log("facets is present");
    
    Assert.assertTrue(isElementPreset(collapseAll), "collapseAll is not present");
    Logging.log("collapseAll is present");
    
    Assert.assertTrue(isElementPreset(reset), "reset is not present");
    Logging.log("reset is present");
    
    Assert.assertTrue(isElementPreset(skillFacet), "skillFacet is not present");
    Logging.log("skillFacet is present");
    
    Assert.assertTrue(isElementPreset(locationFacet), "locationFacet is not present");
    Logging.log("locationFacet is present");
    
    Assert.assertTrue(isElementPreset(experienceFacet), "experienceFacet is not present");
    Logging.log("experienceFacet is present");
    
    Assert.assertTrue(isElementPreset(EmployerFacet), "EmployerFacet is not present");
    Logging.log("EmployerFacet is present");
    
    Assert.assertTrue(isElementPreset(instituteFacet), "instituteFacet is not present");
    Logging.log("instituteFacet is present");
    
    Assert.assertTrue(isElementPreset(engagementFacet), "engagementFacet is not present");
    Logging.log("engagementFacet is present");
    
    Assert.assertTrue(isElementPreset(crmStageFacet), "crmStageFacet is not present");
    Logging.log("crmStageFacet is present");
    
    Assert.assertTrue(isElementPreset(crmStageFacet), "crmStageFacet is not present");
    Logging.log("crmStageFacet is present");
    
    Assert.assertTrue(isElementPreset(visasStatusFacet), "visasStatusFacet is not present");
    Logging.log("visasStatusFacet is present");
    
    Assert.assertTrue(isElementPreset(employementTypeFacet), "employementTypeFacet is not present");
    Logging.log("employementTypeFacet is present");
    
    Assert.assertTrue(isElementPreset(sourcetypeFacet), "sourcetypeFacet is not present");
    Logging.log("sourcetypeFacet is present");
    
    Assert.assertTrue(isElementPreset(qualificationFacet), "qualificationFacet is not present");
    Logging.log("qualificationFacet is present");
    
    Assert.assertTrue(isElementPreset(roleFacet), "roleFacet is not present");
    Logging.log("roleFacet is present");
    
    Assert.assertTrue(isElementPreset(companyFacet), "companyFacet is not present");
    Logging.log("companyFacet is present");
    
    
    Assert.assertTrue(isElementPreset(preferedFacet), "preferedFacet is not present");
    Logging.log("preferedFacet is present");
    
    Assert.assertTrue(isElementPreset(currentRole), "currentRole is not present");
    Logging.log("currentRole is present");
    
    Assert.assertTrue(isElementPreset(verifycount), "verifycount is not present");
    Logging.log("verifycount is present");
    
    Assert.assertTrue(isElementPreset(pagination), "pagination is not present");
    Logging.log("pagination is present");
    
    
    
}


private boolean isElementPreset(By universalsearchcriteria) {

	return false;
}

public void verifySearchResultCandidateicons(){

	waitForElementToBeVisible(driver, universalsearchcriteria);
    Assert.assertTrue(isElementPreset(universalsearchcriteria), "universalsearchcriteria is not present");
    Logging.log("universalsearchcriteria is present");
    Assert.assertTrue(isElementPreset(candidatename), "candidatename is not present");
    Logging.log("candidatename is present");
    Assert.assertTrue(isElementPreset(candidatecheckbox), "candidatecheckbox is not present");
    Logging.log("candidatecheckbox is present");
    Assert.assertTrue(isElementPreset(candidateEducationsymbol), "candidateEducationsymbol is not present");
    Logging.log("candidateEducationsymbol is present");
    Assert.assertTrue(isElementPreset(candidatePortalsymbol), "candidatePortalsymbol is not present");
    Logging.log("candidatePortalsymbol is present");
    Assert.assertTrue(isElementPreset(candidateEmailsymbol), "candidateEmailsymbol is not present");
    Logging.log("candidateEmailsymbol is present");
    Assert.assertTrue(isElementPreset(candidateJobtitlesymbol), "candidateJobtitlesymbol is not present");
    Logging.log("candidateJobtitlesymbol is present");
    Assert.assertTrue(isElementPreset(candidateExperiencesymbol), "candidateExperiencesymbol is not present");
    Logging.log("candidateExperiencesymbol is present");
    Assert.assertTrue(isElementPreset(label), "label is not present");
    Logging.log("label is present");
    Assert.assertTrue(isElementPreset(resumeDownlaod), "resumeDownlaod is not present");
    Logging.log("resumeDownlaod is present");
}	


public void validateSearchResultCandidatedetails(){

	waitForElementToBeVisible(driver, universalsearchcriteria);
    Assert.assertTrue(isElementPreset(universalsearchcriteria), "universalsearchcriteria is not present");
    Logging.log("universalsearchcriteria is present");
    Assert.assertTrue(isElementPreset(candidatename), "candidatename is not present");
    Logging.log("candidatename is present");
    Assert.assertTrue(isElementPreset(candidateEducation), "candidateEducation is not present");
    Logging.log("candidateEducation is present");
    Assert.assertTrue(isElementPreset(candidatePortal), "candidatePortal is not present");
    Logging.log("candidatePortal is present");
    Assert.assertTrue(isElementPreset(candidateEmail), "candidateEmail is not present");
    Logging.log("candidateEmail is present");
    Assert.assertTrue(isElementPreset(candidateJobtitle), "candidateJobtitle is not present");
    Logging.log("candidateJobtitle is present");
    Assert.assertTrue(isElementPreset(candidateExperience), "candidateExperience is not present");
    Logging.log("candidateExperience is present");

}
}
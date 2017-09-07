package com.spire.crm.search.test;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.pages.SearchPage;
import com.spire.crm.restful.biz.consumers.CrmSearchBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;
import com.spire.crm.restful.util.RequisitionDataPointsBean;

import spire.crm.profiles.bean.Profile;
import spire.match.entities.SearchResult;
import spire.search.commons.entities.SearchInput;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.demandservice.beans.RequisitionBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

public class SearchResultPageUtil extends SearchPage {
	ProfileBizServiceConsumer profileBizServiceConsumer = new ProfileBizServiceConsumer();
	CrmSearchBizServiceConsumer crmSearchBizServiceConsumer = new CrmSearchBizServiceConsumer();
	Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();
	JavascriptExecutor jse = (JavascriptExecutor)driver;

	public SearchResultPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
	}

	/* 
	 **************
	 */

	public void verifyCandidateRecord() {
		waitForElementToBeVisible(driver, searchResults);
		Assert.assertTrue(isElementVisiable(driver, candProcandImg), "candidate profile image is not displaying");
		Assert.assertTrue(isElementVisiable(driver, searchResults), "candidate name was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, candSourceType), "candidate source type was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, candProLoc), "candidate location was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, totalExperienceMnth),
				"candidate totalExperienceMnth was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, candDesignationName),
				"candidate candDesignationName was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, employerName), "candidate employerName was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, candProSkill), "candProSkill was not displayed");
		Assert.assertTrue(isElementVisiable(driver, candProCandStage),
				"candidate profile candidate stage was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, candRIScore), "candidate RI score was not dispalyed");
		Assert.assertTrue(isElementVisiable(driver, candEmailImg), "EmailImg is not displayed in search results page");
		Assert.assertTrue(isElementVisiable(driver, candProPhoneImg),
				"phone image is not dispalyed in search results page");
		Assert.assertTrue(isElementVisiable(driver, tagOverLayImg),
				"tagOverLayImg is not dispalyed in search results page");
		Assert.assertTrue(isElementVisiable(driver, CampaignIcon),
				"CampaignIcon is not displayed in search results page");
		Assert.assertTrue(isElementVisiable(driver, detProEngSco),
				"candidate detProEngSco is not displayed in search results page");
		Logging.log("****************All the deatails in search result page are validated****************");
		getCandidateValues();
	}

	public void getCandidateValues() {
		WebElement element = driver.findElement(profileLink);
		String profile = element.getAttribute("href");
		String profileId[] = profile.split("/");
		String candidateId = profileId[8];
		Profile candidateProfile = profileBizServiceConsumer.getProfile(candidateId, "full");
		CollectionEntity<CandidateEmployerMapBean> employerdetails = candidateProfile.getCandidate()
				.getCandidateEmployerMapBean();
		CandidateEmployerMapBean candidateEmployerMapBean = employerdetails.getItems().iterator().next();
		CollectionEntity<CandidateSkillMapBean> skills = candidateProfile.getCandidate().getCandidateSkillMapBean();
		CandidateSkillMapBean candidateSkillMapBean = skills.getItems().iterator().next();
		Assert.assertNotNull(candidateProfile.getCandidate().getFirstName(), "failed to get candidate FirstName");
		Assert.assertNotNull(candidateProfile.getCandidate().getLastName(), "failed to get candidate LastName");
		Assert.assertNotNull(candidateProfile.getCandidate().getSourceType(), "failed to get SourceType");
		Assert.assertNotNull(candidateProfile.getCandidate().getLocationName(), "failed to get LocationName");
		Assert.assertNotNull(candidateProfile.getCandidate().getTotalExperienceMnth(),
				"failed to get TotalExperienceMnth");
		Assert.assertNotNull(candidateSkillMapBean.getSkill(), "failed to get Skills");
		Assert.assertNotNull(candidateEmployerMapBean.getEmployerName(), "failed to get EmployerName");
		Assert.assertNotNull(candidateEmployerMapBean.getDesignationName(), "failed to get DesignationName");
		Assert.assertNotNull(candidateProfile.getCrm().getStatusName(), "failed to get StatusName");
	}

	public void verifyFacetingInSearchResults() {
		waitForElementToBeVisible(driver, searchResults);
		Assert.assertTrue(isElementVisiable(driver, searchResults), "candidate name was not dispalyed");
		String Skill = driver.findElement(facetSkillAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetSkillAttribute),
				"Skill attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Skill.equalsIgnoreCase("Skill"),
				"skill attribute is not shown in faceting while displaying search results");
		String Location = driver.findElement(facetLocationAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetLocationAttribute),
				"Location attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Location.equalsIgnoreCase("Location"),
				"Location attribute is not shown in faceting while displaying search results");
		String ExpInYears = driver.findElement(facetExpInYearsAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetExpInYearsAttribute),
				"Experience in Years attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(ExpInYears.equalsIgnoreCase("Experience in Years"),
				"Experience in Years attribute is not shown in faceting while displaying search results");
		String Company = driver.findElement(facetCompanyAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetCompanyAttribute),
				"Company attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Company.equalsIgnoreCase("Company"),
				"Company attribute is not shown in faceting while displaying search results");
		String Institute = driver.findElement(facetInstituteAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetInstituteAttribute),
				"Institute attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Institute.equalsIgnoreCase("Institute"),
				"Institute attribute is not shown in faceting while displaying search results");
		String EngScore = driver.findElement(facetEngScoreAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetEngScoreAttribute),
				"Engagement Score attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(EngScore.equalsIgnoreCase("Engagement Score"),
				"Engagement Score attribute is not shown in faceting while displaying search results");
		String CRMStage = driver.findElement(facetCRMStageAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetCRMStageAttribute),
				"CRM Stage attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(CRMStage.equalsIgnoreCase("CRM Stage"),
				"CRM Stage attribute is not shown in faceting while displaying search results");
		jse.executeScript("window.scrollBy(0,250)", "");
		String FeedbackReplies = driver.findElement(facetFeedbackRepliesAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetFeedbackRepliesAttribute),
				"Feedback Replies attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(FeedbackReplies.equalsIgnoreCase("Feedback Replies"),
				"Feedback Replies attribute is not shown in faceting while displaying search results");
		String FeedbackStatusModifiedAt = driver.findElement(facetFeedbackStatusModifiedAtAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetFeedbackStatusModifiedAtAttribute),
				"Feedback Replies attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(FeedbackStatusModifiedAt.equalsIgnoreCase("Feedback Status Modified At"),
				"Feedback Replies attribute is not shown in faceting while displaying search results");
		String StageChangeReasons = driver.findElement(facetStageChangeReasonsAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetStageChangeReasonsAttribute),
				"Stage Change Reasons attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(StageChangeReasons.equalsIgnoreCase("Stage Change Reasons"),
				"Stage Change Reasons attribute is not shown in faceting while displaying search results");
		String SourceName = driver.findElement(facetSourceNameAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetSourceNameAttribute),
				"Source Name attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(SourceName.equalsIgnoreCase("Source Name"),
				"Source Name attribute is not shown in faceting while displaying search results");
		String SourceType = driver.findElement(facetSourceTypeAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetSourceTypeAttribute),
				"Source Type attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(SourceType.equalsIgnoreCase("Source Type"),
				"Source Type attribute is not shown in faceting while displaying search results");
		String Qualification = driver.findElement(facetQualificationAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetQualificationAttribute),
				"Qualification attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Qualification.equalsIgnoreCase("Qualification"),
				"Qualification attribute is not shown in faceting while displaying search results");
		jse.executeScript("window.scrollBy(0,500)", "");
		String Status = driver.findElement(facetStatusAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetStatusAttribute),
				"Status attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Status.equalsIgnoreCase("Status"),
				"Status attribute is not shown in faceting while displaying search results");
		String Freshness = driver.findElement(facetFreshnessAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetFreshnessAttribute),
				"Freshness attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Freshness.equalsIgnoreCase("Freshness"),
				"Freshness attribute is not shown in faceting while displaying search results");
		String Role = driver.findElement(facetRoleAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetRoleAttribute),
				"Role attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Role.equalsIgnoreCase("Role"),
				"Role attribute is not shown in faceting while displaying search results");
		String visaStatus = driver.findElement(facetVisaStatus).getText();
		Assert.assertTrue(isElementVisiable(driver, facetVisaStatus),
				"visaStatus attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(visaStatus.equalsIgnoreCase("Visa Status"),
				"visaStatus attribute is not shown in faceting while displaying search results");
		String employementType = driver.findElement(facetEmployementType).getText();
		Assert.assertTrue(isElementVisiable(driver, facetEmployementType),
				"employementType attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(employementType.equalsIgnoreCase("Employement Type"),
				"employementType attribute is not shown in faceting while displaying search results");
		String preferredCandidate = driver.findElement(facetPreferredCandidate).getText();
		Assert.assertTrue(isElementVisiable(driver, facetPreferredCandidate),
				"preferredCandidate attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(preferredCandidate.equalsIgnoreCase("Preferred Candidate"),
				"preferredCandidate attribute is not shown in faceting while displaying search results");
		String Contact = driver.findElement(facetContactAttribute).getText();
		Assert.assertTrue(isElementVisiable(driver, facetContactAttribute),
				"contact attribute is not shown in faceting while displaying search results");
		Assert.assertTrue(Contact.equalsIgnoreCase("Contact"),
				"Contact attribute is not shown in faceting while displaying search results");
		Logging.log("***********All the attributes in Faceting are showing in Search results Page************");
	}

    public void validateSearchBySingAttribute(SearchBean searchbean) throws JsonParseException, JsonMappingException, IOException{
		long noOfResults=pagination(searchbean);
		String searchQueryString=searchbean.getSearchQuery().replaceAll("\"\"", "\"");
    	String searchAttribute=searchbean.getSearchAttributeMap().replaceAll("\"\"", "\"");
    	String searchQuery=searchQueryString+","+searchAttribute;
    	SearchResult searchResult=searchQueryByService(searchQuery);
		//Assert.assertTrue(noOfResults>=searchResult.getTotal()-10,"Not shown same number of records in UI and service side for "+searchbean.getSearchType().get(0)+" "+searchbean.getSearchCriteria().get(0));
		verifyRecords(noOfResults, searchResult.getTotal());
    }
    
	public void verifySearchByAttributes(SearchBean searchbean)
			throws Exception {
		String searchAttribute = null;
		clicElement(driver, clickOnSearchBar);
		if (searchbean.getParanthesis() == null) {
			for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
				if (!searchbean.getSearchCriteria().get(i).equals("null")) {
					if (searchbean.getSearchType().get(i).equals("crm Stage")) {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i)
								.replace("crm", "CRM"));
					} else {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					}
				}
				if (i == searchbean.getSearchCriteria().size() - 1) {
					break;
				}
				if (!searchbean.getSearchOperation().get(i).equals("null")) {
					searchOperation(searchbean.getSearchOperation().get(i));
				}
			}
		} else if (searchbean.getParanthesis() != null) {
			for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
				if (!searchbean.getSearchCriteria().get(i).equals("null")) {
					searchOperation(searchbean.getParanthesis().get(0));
					if (searchbean.getSearchType().get(i).equals("crm Stage")) {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i)
								.replace("crm", "CRM"));
					} else {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					}
					searchOperation(searchbean.getParanthesis().get(1));
				}
				if (i == searchbean.getSearchCriteria().size() - 1) {
					break;
				}
				if (!searchbean.getSearchOperation().get(i).equals("null")) {
					searchOperation(searchbean.getSearchOperation().get(i));
				}
			}
		}

		clicElement(driver, clickOnSearchBtn);
		long noOfResults = pagination(searchbean);
		String searchQueryString = searchbean.getSearchQuery().replaceAll(
				"\"\"", "\"");
		searchAttribute = searchbean.getSearchAttributeMap().replaceAll("\"\"",
				"\"");
		searchAttribute = searchAttribute.replace("\"\"", "\",\"");
		String searchQuery = searchQueryString + "," + searchAttribute;
		SearchResult searchResult = searchQueryByService(searchQuery);
		/*Assert.assertEquals(noOfResults,
				searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());
	}

	public void searchByFullParanthesis(SearchBean searchbean) throws Exception {
		String searchAttribute = null;
		clicElement(driver, clickOnSearchBar);
		if (searchbean.getSearchOperation().size() == 1) {
			for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
				if (!searchbean.getSearchCriteria().get(i).equals("null")) {
					searchOperation(searchbean.getSearchOperation().get(0));
					searchOperation(searchbean.getParanthesis().get(0));
					if (searchbean.getSearchType().get(i).equals("crm Stage")) {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i)
								.replace("crm", "CRM"));
					} else {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					}
					searchOperation(searchbean.getParanthesis().get(1));
				}
				if (i == searchbean.getSearchCriteria().size() - 1) {
					break;
				}
			}
		} else if (!searchbean.getSearchOperation().get(0).equals("!")) {
			searchOperation(searchbean.getParanthesis().get(0));
			for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
				if (!searchbean.getSearchCriteria().get(i).equals("null")) {
					if (searchbean.getSearchType().get(i).equals("crm Stage")) {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i)
								.replace("crm", "CRM"));
					} else {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					}

				}
				if (i == searchbean.getSearchCriteria().size() - 1) {
					break;
				}
				if (!searchbean.getSearchOperation().get(i).equals("null")) {
					searchOperation(searchbean.getSearchOperation().get(i));
				}
			}
			searchOperation(searchbean.getParanthesis().get(1));
		}else {
			searchOperation(searchbean.getSearchOperation().get(0));
			searchOperation(searchbean.getParanthesis().get(0));
			for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
				if (!searchbean.getSearchCriteria().get(i).equals("null")) {
					if (searchbean.getSearchType().get(i).equals("crm Stage")) {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i)
								.replace("crm", "CRM"));
					} else {
						clickOnSearchedElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					}

				}
				if (i == searchbean.getSearchCriteria().size() - 1) {
					break;
				}
				if (!searchbean.getSearchOperation().get(i + 1).equals("null")) {
					searchOperation(searchbean.getSearchOperation().get(i + 1));
				}
			}
			searchOperation(searchbean.getParanthesis().get(1));
		}
		clicElement(driver, clickOnSearchBtn);
		long noOfResults = pagination(searchbean);
		String searchQueryString = searchbean.getSearchQuery().replaceAll(
				"\"\"", "\"");
		searchAttribute = searchbean.getSearchAttributeMap().replaceAll("\"\"",
				"\"");
		searchAttribute = searchAttribute.replace("\"\"", "\",\"");
		String searchQuery = searchQueryString + "," + searchAttribute;
		SearchResult searchResult = searchQueryByService(searchQuery);
		/*Assert.assertEquals(noOfResults, searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());
	}
    public void profileSkillLink(String candidateID,WebDriver driver,SearchBean searchbean) throws JsonParseException, JsonMappingException, IOException, InterruptedException{
    	String URL=driver.getCurrentUrl()+"Profile/"+candidateID;
    	driver.get(URL);
    	Thread.sleep(20000);
    	waitForElementToBeVisible(driver, profileSkillLink);
    	clicElement(driver, profileSkillLink);
    	long noOfResults=pagination(searchbean);
    	String searchQueryString=searchbean.getSearchQuery().replaceAll("\"\"", "\"");
    	String searchAttribute=searchbean.getSearchAttributeMap().replaceAll("\"\"", "\"");
    	String searchQuery=searchQueryString+","+searchAttribute;
    	SearchResult searchResult=searchQueryByService(searchQuery);
		Assert.assertEquals(noOfResults, searchResult.getTotal(),"Not showing same number of records in UI and service side while checking through profileSkillLink");
    }
    
	public void verifyAdvanceSearchByAttributes(SearchBean searchbean)
			throws Exception {
		String searchAttribute = null;
		waitForElementToBeVisible(driver, advanceSearchIcon);
		clicElement(driver, advanceSearchIcon);
		if (searchbean.getSearchOperation() == null) {
			if (searchbean.getParanthesis() == null) {
				for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
					if (!searchbean.getSearchCriteria().get(i).equals("null")) {
						advanceSearchElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					}
				}

			} else if (searchbean.getParanthesis() != null) {
				if (!searchbean.getSearchType().contains("Engagement Score")
						&& !searchbean.getSearchType().contains("Experience")) {
					for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
						if (!searchbean.getSearchCriteria().get(i)
								.equals("null")) {
							advanceSearchOperation(searchbean.getSearchType()
									.get(i), searchbean.getParanthesis().get(0));
							advanceSearchElement(searchbean.getSearchCriteria()
									.get(i), searchbean.getSearchType().get(i));
							advanceSearchOperation(searchbean.getSearchType()
									.get(i), searchbean.getParanthesis().get(1));
						}
					}
				}
			}
		} else {
			if (searchbean.getParanthesis() == null) {
				for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
					if (!searchbean.getSearchCriteria().get(i).equals("null")
							&& searchbean.getSearchOperation().get(0)
									.equals("!")) {
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getSearchOperation().get(i));
						advanceSearchElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
					} else if (!searchbean.getSearchCriteria().get(i)
							.equals("null")
							&& ((searchbean.getSearchOperation()
									.contains("AND")) || (searchbean
									.getSearchOperation().contains("OR")))) {
						advanceSearchElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
						if (i == searchbean.getSearchCriteria().size() - 1) {
							break;
						}
						if (searchbean.getSearchOperation().get(i)
								.equals("default")) {
							continue;
						}
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getSearchOperation().get(i));
					}
				}
			} else if (searchbean.getParanthesis() != null) {
				for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
					if (!searchbean.getSearchCriteria().get(i).equals("null")
							&& searchbean.getSearchOperation().get(0)
									.equals("!")
							&& !searchbean.getSearchType().contains(
									"Engagement Score")
							&& !searchbean.getSearchType().contains(
									"Experience")) {
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getSearchOperation().get(i));
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getParanthesis().get(0));
						advanceSearchElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getParanthesis().get(1));
					} else if (!searchbean.getSearchCriteria().get(i)
							.equals("null")
							&& ((searchbean.getSearchOperation()
									.contains("AND")) || (searchbean
									.getSearchOperation().contains("OR")))
							&& !searchbean.getSearchType().contains(
									"Engagement Score")
							&& !searchbean.getSearchType().contains(
									"Experience")) {
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getParanthesis().get(0));
						advanceSearchElement(searchbean.getSearchCriteria()
								.get(i), searchbean.getSearchType().get(i));
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getParanthesis().get(1));
						if (i == searchbean.getSearchCriteria().size() - 1) {
							break;
						}
						if (searchbean.getSearchOperation().get(i)
								.equals("default")) {
							continue;
						}
						advanceSearchOperation(searchbean.getSearchType()
								.get(i), searchbean.getSearchOperation().get(i));
					}
				}
			}
		}
		scrollInAdvance(searchbean);
		clicElement(driver, submitButtonInadvsearch);
		long noOfResults = pagination(searchbean);
		String searchQueryString = searchbean.getSearchQuery().replaceAll(
				"\"\"", "\"");
		searchAttribute = searchbean.getSearchAttributeMap().replaceAll("\"\"",
				"\"");
		searchAttribute = searchAttribute.replace("\"\"", "\",\"");
		String searchQuery = searchQueryString + "," + searchAttribute;
		SearchResult searchResult = searchQueryByService(searchQuery);
		/*Assert.assertEquals(noOfResults, searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());

	}

	public void advanceSearchFullParanthesis(SearchBean searchbean)
			throws Exception {
		String searchAttribute = null;
		waitForElementToBeVisible(driver, advanceSearchIcon);
		clicElement(driver, advanceSearchIcon);
		if (searchbean.getParanthesis().contains("(")) {
			if (searchbean.getSearchType().contains("Stage Name") 
					|| searchbean.getSearchType().contains("Source Type")
					|| searchbean.getSearchType().contains("Source Name") 
					|| searchbean.getSearchType().contains("Institute")) {
				jse.executeScript("window.scrollBy(0,300)", "");
			}
		}
		if (searchbean.getSearchOperation().get(0).equals("!")) {
			advanceSearchOperation(searchbean.getSearchType().get(0),
					searchbean.getSearchOperation().get(0));
		}
		advanceSearchOperation(searchbean.getSearchType().get(0), searchbean
				.getParanthesis().get(0));
		for (int i = 0; i < searchbean.getSearchCriteria().size(); i++) {
			if (!searchbean.getSearchCriteria().get(i).equals("null")
					&& ((searchbean.getSearchOperation().contains("AND")) || (searchbean
							.getSearchOperation().contains("OR")))
					&& !searchbean.getSearchType().contains("Engagement Score")
					&& !searchbean.getSearchType().contains("Experience")) {
				advanceSearchElement(searchbean.getSearchCriteria().get(i),
						searchbean.getSearchType().get(i));
				if (i == searchbean.getSearchCriteria().size() - 1) {
					break;
				}
				if (searchbean.getSearchOperation().get(i).equals("default")) {
					continue;
				}
				if (!searchbean.getSearchOperation().get(0).equals("!")) {
					advanceSearchOperation(searchbean.getSearchType().get(i),
							searchbean.getSearchOperation().get(i));
				} else {
					advanceSearchOperation(searchbean.getSearchType().get(i),
							searchbean.getSearchOperation().get(i + 1));
				}
			}
		}
		advanceSearchOperation(searchbean.getSearchType().get(0), searchbean
				.getParanthesis().get(1));

		scrollInAdvance(searchbean);
		clicElement(driver, submitButtonInadvsearch);
		long noOfResults = pagination(searchbean);
		String searchQueryString = searchbean.getSearchQuery().replaceAll(
				"\"\"", "\"");
		searchAttribute = searchbean.getSearchAttributeMap().replaceAll("\"\"",
				"\"");
		searchAttribute = searchAttribute.replace("\"\"", "\",\"");
		String searchQuery = searchQueryString + "," + searchAttribute;
		SearchResult searchResult = searchQueryByService(searchQuery);
		/*Assert.assertEquals(noOfResults, searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());

	}
	
	public void freeTextSearchBySearchBar(SearchBean searchbean) throws JsonParseException, JsonMappingException, IOException{
		waitForElementToBeVisible(driver, freeTextSearch);
		clicElement(driver, freeTextSearch);
		clicElement(driver, clickOnSearchBar);
		freeTextSearchBar(searchbean.getFreeTextData());
		long noOfResults = pagination(searchbean);
		String searchQueryString = searchbean.getFreeTextQuery().replaceAll(
				"\"\"", "\"");
		System.out.println(searchQueryString);
		SearchResult searchResult = searchQueryByService(searchQueryString);
		/*Assert.assertEquals(noOfResults, searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());
	}
	
	public void freeTextSearchByAdvSearch(SearchBean searchbean) throws JsonParseException, JsonMappingException, IOException{
		waitForElementToBeVisible(driver, advanceSearchIcon);
		clicElement(driver, advanceSearchIcon);
		freeTextAdvSearch(searchbean.getFreeTextData());
		long noOfResults = pagination(searchbean);
		String searchQueryString = searchbean.getFreeTextQuery().replaceAll(
				"\"\"", "\"");
		System.out.println(searchQueryString);
		SearchResult searchResult = searchQueryByService(searchQueryString);
		/*Assert.assertEquals(noOfResults, searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());
	}
	
	public void combinationOfFreetext(SearchBean searchbean) throws Exception{
		waitForElementToBeVisible(driver, advanceSearchIcon);
		clicElement(driver, advanceSearchIcon);
		advFreetextComb(searchbean.getFreeTextData());
		advanceSearchElement(searchbean.getSearchCriteria().get(0), searchbean.getSearchType().get(0));
		scrollInAdvance(searchbean);
		clicElement(driver, submitButtonInadvsearch);
		long noOfResults = pagination(searchbean);
		String searchFreeQueryString = searchbean.getFreeTextQuery().replaceAll(
				"\"\"", "\"");
		String searchQueryString = searchbean.getSearchQuery().replaceAll(
				"\"\"", "\"");
		String searchAttribute = searchbean.getSearchAttributeMap().replaceAll("\"\"",
				"\"");
		searchAttribute = searchAttribute.replace("\"\"", "\",\"");
		String searchQuery = searchQueryString + "," + searchAttribute + "," +searchFreeQueryString;
		System.out.println(searchQuery);
		SearchResult searchResult = searchQueryByService(searchQuery);
		/*Assert.assertEquals(noOfResults, searchResult.getTotal(),
				"Both service side and UI side result count is not equal");*/
		verifyRecords(noOfResults, searchResult.getTotal());
	}
	
	
	public void verifyRequsition(SpireTestObject testObject,
			RequisitionDataPointsBean requisitionDataPointsBean) {
		CrmSearchBizServiceConsumer crmSearchBizServiceConsumer = new CrmSearchBizServiceConsumer();
		RequisitionBean requisitionBean;
		requisitionBean = requisitionDataPointsBean.requisitionCreateData();
		requisitionBean.setDisplayId("REQ-" + System.currentTimeMillis());
		requisitionBean.setJobTitle("DEV-" + System.currentTimeMillis());
		System.out.println(requisitionBean.getJobTitle());
		Logging.log("********" + testObject.getTestData() + "*********");
		crmSearchBizServiceConsumer.createRequsition(requisitionBean);
		Logging.log("Requisition is created");
		driver.navigate().refresh();
		waitForElementToBeVisible(driver, openPositions);
		clickOnRquisition(requisitionBean.getJobTitle());
		waitForElementToBeVisible(driver, searchResults);
		waitForElementToBeVisible(driver, noOfRecords);
		String result = driver.findElement(noOfRecords).getText();
		String results[] = result.split(" ");
		long noOfResults = Long.parseLong(results[1]);
		if (testObject.getTestData().equals("createRequisitionWithSkill")
				|| testObject.getTestData().equals(
						"createRequisitionWithSkillAndExp")
				|| testObject.getTestData().equals(
						"createRequisitionWithSkillAndLoc")
				|| testObject.getTestData().equals(
						"createRequisitionWithSkillAndLocAndExp")
				|| testObject
						.getTestData()
						.equals("createRequisitionWithSkill10DesiredAnd10EssentialSkills")) {
			Assert.assertEquals(noOfResults, 1,
					"showing wrong Number of records");
		} else if (testObject.getTestData().equals(
				"createRequisitionWithLocation")
				|| testObject.getTestData().equals(
						"createRequisitionWithLocAndExp")) {
			Assert.assertEquals(noOfResults, 3,
					"showing wrong Number of records");
		} else if (testObject.getTestData().equals("createRequisitionWithExp")) {
			Assert.assertTrue(noOfResults > 1,
					"showing wrong Number of records");
		}
	}
	
	public void verifyRetainInFaceting(SpireTestObject testObject) throws InterruptedException {
		if(testObject.getTestData().equals("Company")||testObject.getTestData().equals("Institute")||testObject.getTestData().equals("StageNames")){
			jse.executeScript("window.scrollBy(0,250)", "");
		}else if(testObject.getTestData().equals("Source")||testObject.getTestData().equals("SourceType")||testObject.getTestData().equals("Qualification")){
			jse.executeScript("window.scrollBy(0,500)", "");
		}else if(testObject.getTestData().equals("Status")||testObject.getTestData().equals("Role")||testObject.getTestData().equals("visaStatus")){
			jse.executeScript("window.scrollBy(0,650)", "");
		}else if(testObject.getTestData().equals("employmentType")||testObject.getTestData().equals("preferred")||testObject.getTestData().equals("Contact")){
			jse.executeScript("window.scrollBy(0,800)", "");
		}
		List<String> values = facetingInSearchBeforeSelecting(testObject
				.getTestData());
		String facetCountValue = "//label[@for='" + testObject.getTestData()
				+ 2 + "']/i[1]";
		clicElement(driver, selectValueInFacet(testObject.getTestData() + "2"));
		countInFacet(facetCountValue);
		facetingInSearchAfterSelecting(testObject.getTestData(), values);
	}
	public void scrollInAdvance(SearchBean searchbean){
		if (!searchbean.getSearchType().equals("Engagement Score")
				&& !searchbean.getSearchType().equals("Stage Name")
				&& !searchbean.getSearchType().equals("Source Type") 
				&& !searchbean.getSearchType().equals("Source Name")
				&& !searchbean.getSearchType().equals("Institute")
				&& !searchbean.getSearchType().equals("Experience")) {
			jse.executeScript("window.scrollBy(0,300)", "");
		}
	}
	public long pagination(SearchBean searhbean) {
		long noOfResults;
		if (isElementVisiable(driver, paginationLast)) {
			jse.executeScript("window.scrollBy(0,300)", "");
			Assert.assertTrue(isElementVisiable(driver, paginationLast),
					" paginationLast is not shown in records page while displaying search results");
			waitForElementToBeVisible(driver, noOfRecords);
			String result = driver.findElement(noOfRecords).getText();
			String results[]=result.split(" ");
			long noOfResultsInFirstPage=Long.parseLong(results[1]);
			if(noOfResultsInFirstPage!=0){
				waitForElementToBeVisible(driver, searchResults);
				Assert.assertTrue(isElementVisiable(driver, searchResults), "search results are not displaying");
			}
			validateRI(noOfResultsInFirstPage,searhbean);
			clicElement(driver, paginationLast);
			waitForElementToBeVisible(driver, noOfRecords);
			String result1 = driver.findElement(noOfRecords).getText();
			String results1[]=result1.split(" ");
			long noOfResultsInLastPage=Long.parseLong(results1[1]);
			validateRI(noOfResultsInLastPage,searhbean);
			Assert.assertEquals(noOfResultsInFirstPage, noOfResultsInLastPage,"Not showing same number of records as in first page");
			Assert.assertTrue((noOfResultsInFirstPage>=noOfResultsInLastPage-5)||(noOfResultsInFirstPage<=noOfResultsInLastPage-5),"Not shown same number of records as in first page"+noOfResultsInFirstPage+"lastpage"+noOfResultsInLastPage);
			noOfResults=noOfResultsInLastPage;
			
		} else {
			waitForElementToBeVisible(driver, noOfRecords);
			String result = driver.findElement(noOfRecords).getText();
			String results[]=result.split(" ");
			noOfResults=Long.parseLong(results[1]);
			if(noOfResults!=0){
				waitForElementToBeVisible(driver, searchResults);
				Assert.assertTrue(isElementVisiable(driver, searchResults), "search results are not displaying");
			}
			validateRI(noOfResults,searhbean);
		}
		return noOfResults;
    }
    
    public void validateRI(long noOfResults,SearchBean searchbean){
		if (noOfResults >= 1) {
			if (searchbean.getSearchQuery() == null
					&& searchbean.getSearchAttributeMap() == null) {
				Assert.assertTrue(!(isElementVisiable(driver, candRIScore)),
						"RI is showing if we serach through freetext also");
			}else if (searchbean.getSearchQuery() != null
					&& searchbean.getSearchAttributeMap() != null 
					&& searchbean.getFreeTextQuery()!=null) {
				Assert.assertTrue(!(isElementVisiable(driver, candRIScore)),
						"RI is showing if we serach through freetext also");
			}
			else if (searchbean.getSearchOperation() == null) {
				if (searchbean.getSearchType().contains("Skill")) {
					waitForElementToBeVisible(driver, candRIScore);
					Assert.assertTrue(isElementVisiable(driver, candRIScore),
							" RI icon is not shown in records first page while displaying search results");
				}
			} else if (searchbean.getSearchOperation().size() >= 1
					&& !searchbean.getSearchOperation().get(0).equals("!")) {
				if (searchbean.getSearchType().contains("Skill")) {
					waitForElementToBeVisible(driver, candRIScore);
					Assert.assertTrue(isElementVisiable(driver, candRIScore),
							" RI icon is not shown in records first page while displaying search results");
				}
			} 
		}
	}

	public SearchResult searchQueryByService(String searchQuery)
			throws JsonParseException, JsonMappingException, IOException {
		Logging.log("searchQuery:" + searchQuery);
		System.out.println("searchQuery:" + searchQuery);
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SearchInput searchInput = objectMapper.readValue(searchQuery,
				new TypeReference<SearchInput>() {
				});
		Response response = crmSearchBizServiceConsumer.search(searchInput);
		SearchResult searchResult = response.readEntity(SearchResult.class);
		return searchResult;
	}

	public void validateSaveSearch() throws InterruptedException {

		SearchResultPageUtil util = new SearchResultPageUtil(driver, false);

		String saveSearchName = "SpireSearch" + System.currentTimeMillis();

		waitForElementToBeVisible(driver, totalResults);
		String totalSearchResults1 = getElementText(driver, totalResults);
		String searchResult1[] = totalSearchResults1.split(" ");
		String totalResult1 = searchResult1[1];
		System.out.println("totalResults1: " + totalSearchResults1);

		waitForElementToBeVisible(driver, saveSearch);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clicElement(driver, saveSearch);
		waitForElementToBeVisible(driver, saveSearchTitle);
		enterText(driver, saveSearchTitle, saveSearchName);
		clicElement(driver, save_SaveSearch);
		Thread.sleep(10000);
		clicElement(driver, savedSearches);
		String savedName = getElementText(driver, savedSearchName);

		Assert.assertEquals(savedName, saveSearchName,
				"SavedSearchName not saving !!");

		clicElement(driver, savedSearchName);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForElementToBeVisible(driver, totalResults);

		String totalSearchResults2 = getElementText(driver, totalResults);
		String searchResult2[] = totalSearchResults1.split(" ");
		String totalResult2 = searchResult2[1];
		System.out.println("totalResults2: " + totalSearchResults2);

		Assertion.assertTrue(Integer.parseInt(totalResult1) <= Integer
				.parseInt(totalResult2),
				"Search Results showing differently from SaveSearch !!");
	}
	
	public void verifyRecords(long noOfResults,long responseResults){
		
		Assert.assertTrue(noOfResults>=responseResults-5,"Not shown same number of records in UI and service side for "+noOfResults+" "+responseResults);
			
	}

}

package com.spire.crm.smoke.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import spire.commons.search.response.SearchResponse;
import spire.crm.profiles.bean.Profile;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.helper.WebPageHelper;
import com.spire.crawl.pages.BasePage;
import com.spire.crm.email.EmailBean;
import com.spire.crm.pageUtils.EngageEmailTemplatePageUtil;
import com.spire.crm.pages.CandidateDetailsPage;
import com.spire.crm.pages.EngageCampaignPage;
import com.spire.crm.pages.HomePage;
import com.spire.crm.pages.SearchPage;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;

/**
 * @author Santosh
 *
 */
public class UiValidator extends BasePage {

	public UiValidator(WebDriver driver) {
		super(driver);
	}

	String candidateNameToUpdate = "SpireTest" + System.currentTimeMillis();
	String candidateLinkedInUrlToUpdate = "";
	String candidateEmailIdToUpdate = "spire." + System.currentTimeMillis() + "@spire.com";
	String candidateContactToUpdate = "9980100100";
	String candidateLocationToUpdate = "Spire" + System.currentTimeMillis();
	String candidateTwitterUrlToUpdate = "http://twitter.com/update";
	String skill1 = "TestSkill1";
	String skill2 = "TestSkill2";

	/**
	 * Navigate to candidate-details page and validate the candidate data
	 * 
	 * @param candidateId
	 * @param profileRequest
	 * @throws InterruptedException
	 */
	public void validateCreatedProfile(WebDriver driver, String candidateId, Profile profileRequest)
			throws InterruptedException {

		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl + "Profile/" + candidateId);

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(driver);

		Thread.sleep(5000);

		Assert.assertTrue(getElementText(driver, candidateDetailsPage.name)
				.contains(profileRequest.getCandidate().getFirstName()), "Showing wrong FirstName!!");

		Assert.assertEquals(getElementText(driver, candidateDetailsPage.location),
				profileRequest.getCandidate().getLocationName(), "Showing wrong Location!!");

		Collection<CandidateSkillMapBean> skills = profileRequest.getCandidate().getCandidateSkillMapBean().getItems();
		List<String> ski = new ArrayList<String>();
		for (CandidateSkillMapBean candidateSkillMapBean : skills) {
			ski.add(candidateSkillMapBean.getSkill());
		}

		System.out.println("UI SKILLS: " + candidateDetailsPage.getSkills());
		for (String skillName : ski) {
			Assert.assertTrue(candidateDetailsPage.getSkills().contains(skillName),
					"Skill " + skillName + " is not showing in UI !!");
		}
		Assert.assertNotNull(getElementText(driver, candidateDetailsPage.experience), "Total Exp showing null !!");
		// Assert.assertEquals(getElementText(driver,
		// candidateDetailsPage.engScore),
		// profileRequest.getCrm().getEngagementScore().toString(), "Showing
		// wrong Eng Score in UI");

		Assert.assertEquals(getElementText(driver, candidateDetailsPage.linkedinUrl),
				profileRequest.getSocial().getLinkedinUrl(), "Showing wrong LinkedinURL");

		String expectedDesignation = null;
		Collection<CandidateEmployerMapBean> desigantion = profileRequest.getCandidate().getCandidateEmployerMapBean()
				.getItems();
		for (CandidateEmployerMapBean candidateEmployerMapBean : desigantion) {
			expectedDesignation = candidateEmployerMapBean.getDesignationName();
		}
		Assert.assertEquals(getElementText(driver, candidateDetailsPage.designations), expectedDesignation,
				"Showing wrong DesignationName !!");

		String expectedCompanyName = null;
		Collection<CandidateEmployerMapBean> company = profileRequest.getCandidate().getCandidateEmployerMapBean()
				.getItems();
		for (CandidateEmployerMapBean candidateEmployerMapBean : company) {
			expectedCompanyName = candidateEmployerMapBean.getEmployerName();
		}
		Assert.assertEquals(getElementText(driver, candidateDetailsPage.companyNames), expectedCompanyName,
				"Showing wrong CompanyName!!");

		String expectedDegreeName = null;
		Collection<CandidateEducationMapBean> degree = profileRequest.getCandidate().getCandidateEducationMapBean()
				.getItems();
		for (CandidateEducationMapBean candidateEducationMapBean : degree) {
			expectedDegreeName = candidateEducationMapBean.getDegreeName();
		}

		Assert.assertTrue(getElementText(driver, candidateDetailsPage.degreeName).contains(expectedDegreeName),
				"Showing wrong DegreeName!!");
	}

	/**
	 * Update candidate details
	 * 
	 * @param driver
	 * @param candidateId
	 */
	public void updateCandidateDetails(WebDriver driver, String candidateId) {

		String currentUrl = this.driver.getCurrentUrl();
		this.driver.get(currentUrl + "Profile/" + candidateId);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		candidateDetailsPage.editCandidateDetails(driver, "NAME", candidateNameToUpdate);
		candidateDetailsPage.editCandidateDetails(driver, "EMAIL", candidateEmailIdToUpdate);
		candidateDetailsPage.editCandidateDetails(driver, "CONTACT", candidateContactToUpdate);
		// candidateDetailsPage.editCandidateDetails(driver, "LOCATION",
		// candidateLocationToUpdate);
		System.out.println("TITLE: " + driver.getCurrentUrl());

		waitForElementToBeVisible(driver, candidateDetailsPage.editName);
		clicElement(driver, candidateDetailsPage.editName);
		waitForElementToBeVisible(driver, candidateDetailsPage.firstName);
		String firstName = getElementText(driver, candidateDetailsPage.firstName);
		System.out.println("firstName: " + firstName);
	}

	/**
	 * Validate updated candidate details[Name,Email,Contact]
	 */
	public void validateUpdatedDetails(String userName, String password, String candidateId, String search_match_host) {

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		String name_UI = getElementText(driver, candidateDetailsPage.name);
		Assertion.assertTrue(name_UI.contains(candidateNameToUpdate), "CandidateName not updating in UI !!");

		ProfileBizServiceConsumer consumer = new ProfileBizServiceConsumer(userName, password);
		consumer.HEADERS = false;
		Profile profileResponse = consumer.getProfile(candidateId, "full");

		Assertion.assertEquals(profileResponse.getCandidate().getFirstName(), candidateNameToUpdate,
				"Name not updated in DB !!");
		Assertion.assertEquals(profileResponse.getCandidate().getPrimaryEmailId(), candidateEmailIdToUpdate,
				"EmailID not updated in DB !!");
		Assertion.assertEquals(profileResponse.getCandidate().getPrimaryContactNumber(), candidateContactToUpdate,
				"ContactNumber not updated in DB !!");

		SearchResponse esResponse = consumer.getCandidateDetailsFromES(search_match_host, candidateId);
		SmokeTestHelper.validateESData(profileResponse, esResponse);
	}

	/**
	 * Delete profile details
	 */

	public void deleteProfileDetails(String userName, String password, String candidateId, String search_match_host) {

		String currentUrl = this.driver.getCurrentUrl();
		this.driver.get(currentUrl + "Profile/" + candidateId);

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		String designation = getElementText(driver, candidateDetailsPage.designations);
		System.out.println("designation " + designation);
		candidateDetailsPage.deleteEmployer();

		Assert.assertFalse(isElementPreset(candidateDetailsPage.designations), "Employer details not deleting !!");

		ProfileBizServiceConsumer consumer = new ProfileBizServiceConsumer(userName, password);
		consumer.HEADERS = false;
		Profile response = consumer.getProfile(candidateId, "full");
		Assert.assertNull(response.getCandidate().getCandidateEmployerMapBean(), "Employer details not deleted !!");

		candidateDetailsPage.deleteSkills();

		Profile responseAfterSkillDeletion = consumer.getProfile(candidateId, "full");
		Assert.assertNull(responseAfterSkillDeletion.getCandidate().getCandidateSkillMapBean(),
				"Skills not deleted !!");

		// SearchResponse esResponse =
		// consumer.getCandidateDetailsFromES(search_match_host, candidateId);
		// Assert.assertNull(esResponse.getCandidateResponse().getEntities().get(0).getSkills(),
		// "Skills not deleting from ES!!");

	}

	/**
	 * Add skills in UI and validate skills are added in UI, DB and ES
	 * 
	 * @param userName
	 * @param password
	 * @param search_match_host
	 * @param candidateId
	 */
	public void validateSkillsAdd(String userName, String password, String search_match_host, String candidateId) {

		String currentUrl = this.driver.getCurrentUrl();
		this.driver.get(currentUrl + "Profile/" + candidateId);

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		candidateDetailsPage.addSkills(skill1, skill2);

		ProfileBizServiceConsumer consumer = new ProfileBizServiceConsumer(userName, password);
		consumer.HEADERS = false;
		Profile response = consumer.getProfile(candidateId, "full");

		List<String> skills = new ArrayList<String>();
		Collection<CandidateSkillMapBean> skillMap = response.getCandidate().getCandidateSkillMapBean().getItems();
		for (CandidateSkillMapBean candidateSkillMapBean : skillMap) {
			skills.add(candidateSkillMapBean.getSkill());
		}

		Assert.assertTrue(skills.contains(skill1), "Skills are not adding in DB !!");
		Assert.assertTrue(skills.contains(skill2), "Skills are not adding in DB !!");
		//
		// SearchResponse esResponse =
		// consumer.getCandidateDetailsFromES(search_match_host, candidateId);
		// List<String> esSkills = new ArrayList<String>();
		// List<CandidateSummary> esCandidateResponse =
		// esResponse.getCandidateResponse().getEntities();
		// for (CandidateSummary candidateSummary : esCandidateResponse) {
		// esSkills.add(candidateSummary.getSkills());
		// }
		//
		// Assert.assertTrue(esSkills.contains(skill1), "Skills are not adding
		// in ES !!");
		// Assert.assertTrue(esSkills.contains(skill2), "Skills are not adding
		// in ES !!");

	}

	public void validateActivityStreamElements() {

		HomePage homePage = new HomePage(driver, false);
		waitForElementToBeVisible(driver, homePage.viewActivities);
		clicElement(driver, homePage.viewActivities);
		Assert.assertTrue(isElementPreset(homePage.activitySearchTextBox), "ActivitySearchTextbox is missing !!");
		Assert.assertTrue(isElementPreset(homePage.sortByDropdown), "ActivitySortByDropdown is missing !!");
		Assert.assertTrue(isElementPreset(homePage.dateRangeDropdown), "ActivityDateRangeDropdown is missing !!");

		List<String> dropdownValues = new ArrayList<String>();
		List<WebElement> elements = new Select(driver.findElement(homePage.dateRangeDropdown)).getOptions();
		for (WebElement webElement : elements) {
			dropdownValues.add(webElement.getText());
		}

		Assert.assertTrue(dropdownValues.contains("The Past 1 day"), "Not displaying DateRange Dropdown values");
		Assert.assertTrue(dropdownValues.contains("The Past 1 week"), "Not displaying DateRange Dropdown values");
		Assert.assertTrue(dropdownValues.contains("The Past 1 week"), "Not displaying DateRange Dropdown values");
		Assert.assertTrue(dropdownValues.contains("The Beginning of Time"), "Not displaying DateRange Dropdown values");

		Assert.assertTrue(isElementPreset(homePage.selectAllActivity), "SelectAll Activities checkbox is missing !!");
		Assert.assertTrue(isElementPreset(homePage.activityTypeCheckboxes), "activityTypeCheckboxes are missing !!");

		// CandidateDetailsPage candidateDetailsPage = new
		// CandidateDetailsPage(this.driver);

	}

	public void validateActivityStream(String candidateId) {

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		candidateDetailsPage.createActivity(candidateId, "Video call", 0);
		this.driver.navigate().refresh();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForElementToBeVisible(driver, candidateDetailsPage.activityStreamTab);
		clicElement(driver, candidateDetailsPage.activityStreamTab);
		waitForElementToBeVisible(driver, candidateDetailsPage.activityType);
		Assert.assertEquals(getElementText(driver, candidateDetailsPage.activityType), "call",
				"Activity not creating !!");
		waitForElementToBeVisible(driver, candidateDetailsPage.activityCreatedTime);
		Assert.assertEquals(getElementText(driver, candidateDetailsPage.activityCreatedTime), "a few seconds ago",
				"Activity not creating !!");
	}

	public void validateEngagementScore(String candidateId) {
		
		String currentUrl = this.driver.getCurrentUrl();
		this.driver.get(currentUrl + "Profile/" + candidateId);

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		candidateDetailsPage.createActivity(null, "Video call", 5);
		WebPageHelper.sleep(15000);
		String after = getElementText(driver, candidateDetailsPage.engScore);
		
		int i=0;
		
		while (true) {
			
			if (after!=null) {
				int value=Integer.parseInt(after);
				if (value>0) {
					break;
				}
			}
			driver.navigate().refresh();
			i++;			
			WebPageHelper.sleep(5000);
			after = getElementText(driver, candidateDetailsPage.engScore);
			
			if (i==50) {
				break;
			}
			
		}
		
		Assert.assertTrue(Integer.parseInt(after) > 0,
				"EngagementScore not increasing !! Created candidate: " + candidateId);

	}

	public void validateSearchResults() {
		SearchPage searchPage = new SearchPage(this.driver, false);
		try {
			searchPage.searchCriteria("oracle", "Skill");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		waitForElementToBeVisible(driver, searchPage.searchResults);

		waitForElementToBeVisible(driver, searchPage.sourceType);
		Assert.assertTrue(isElementVisiable(driver, searchPage.sourceType));

		waitForElementToBeVisible(driver, searchPage.location);
		Assert.assertTrue(isElementVisiable(driver, searchPage.location));

		waitForElementToBeVisible(driver, searchPage.totalExp);
		Assert.assertTrue(isElementVisiable(driver, searchPage.totalExp));

		searchPage.verifyPhoneIcon();

		waitForElementToBeVisible(driver, searchPage.labelIcon);
		Assert.assertTrue(isElementVisiable(driver, searchPage.labelIcon));

		waitForElementToBeVisible(driver, searchPage.bulkMailIcon);
		Assert.assertTrue(isElementVisiable(driver, searchPage.bulkMailIcon));

		waitForElementToBeVisible(driver, searchPage.totalResults);
		Assert.assertTrue(isElementVisiable(driver, searchPage.totalResults));

		waitForElementToBeVisible(driver, searchPage.searchedSkill);
		Assert.assertTrue(isElementVisiable(driver, searchPage.searchedSkill));

	}

	public void validateHomePageElements(WebDriver driver) {

		HomePage homePage = new HomePage(driver, false);
		
		waitForElementToBeVisible(driver, homePage.lead);
		waitForElementToBeVisible(driver, homePage.engaged);
		waitForElementToBeVisible(driver, homePage.applicant);

		Assert.assertTrue("Lead,Engaged,Applicant,Hold,Rejected".contains(getElementText(driver, homePage.lead).trim().split("\\n")[0]),
				"CRM Stage 'Lead' is not showing in HomePage,Rejected");
		Assert.assertTrue("Lead,Engaged,Applicant,Hold,Rejected".contains(getElementText(driver, homePage.engaged).trim().split("\\n")[0]),
				"CRM Stage 'Engaged' is not showing in HomePage");
		Assert.assertTrue("Lead,Engaged,Applicant,Hold,Rejected".contains(getElementText(driver, homePage.applicant).trim().split("\\n")[0]),
				"CRM Stage 'Applicant' is not showing in HomePage");
		Assert.assertTrue("Lead,Engaged,Applicant,Hold,Rejected".contains(getElementText(driver, homePage.hold).trim().split("\\n")[0]),
				"CRM Stage 'Hold' is not showing in HomePage");
		Assert.assertTrue("Lead,Engaged,Applicant,Hold,Rejected".contains(getElementText(driver, homePage.rejected).trim().split("\\n")[0]),
				"CRM Stage 'Rejected' is not showing in HomePage");
		
		
		
		/*Assert.assertTrue(getElementText(driver, homePage.lead).contains("Lead"),
				"CRM Stage 'Lead' is not showing in HomePage");
		Assert.assertTrue(getElementText(driver, homePage.engaged).contains("Engaged"),
				"CRM Stage 'Engaged' is not showing in HomePage");
		Assert.assertTrue(getElementText(driver, homePage.applicant).contains("Applicant"),
				"CRM Stage 'Applicant' is not showing in HomePage");
		Assert.assertTrue(getElementText(driver, homePage.hold).contains("Hold"),
				"CRM Stage 'Hold' is not showing in HomePage");
		Assert.assertTrue(getElementText(driver, homePage.rejected).contains("Rejected"),
				"CRM Stage 'Rejected' is not showing in HomePage");*/

		waitForElementToBeVisible(driver, homePage.spireLOGO);
		Assert.assertTrue(isElementVisiable(driver, homePage.spireLOGO));

		waitForElementToBeVisible(driver, homePage.homeTab);
		Assert.assertTrue(isElementVisiable(driver, homePage.homeTab));
		Assert.assertTrue(isElementVisiable(driver, homePage.engageTab));
		Assert.assertTrue(isElementVisiable(driver, homePage.jobsTab));
		Assert.assertTrue(isElementVisiable(driver, homePage.reportsTab));

		waitForElementToBeVisible(driver, homePage.searchTextfield);
		Assert.assertTrue(isElementVisiable(driver, homePage.searchTextfield));

		// waitForElementToBeVisible(driver, homePage.advancedLink);
		// Assert.assertTrue(isElementVisiable(driver, homePage.advancedLink));
		Assert.assertTrue(isElementVisiable(driver, homePage.notificationIcon));

		waitForElementToBeVisible(driver, homePage.viewActivities);
		Assert.assertTrue(isElementVisiable(driver, homePage.viewActivities));

		Assert.assertTrue(isElementVisiable(driver, homePage.tagIcon));
		Assert.assertTrue(isElementVisiable(driver, homePage.candidatteImageIcon));
		Assert.assertTrue(isElementVisiable(driver, homePage.emailIcon));
		Assert.assertTrue(isElementVisiable(driver, homePage.tagLinks));
		Assert.assertTrue(isElementVisiable(driver, homePage.popularTagScore));
		Assert.assertTrue(isElementVisiable(driver, homePage.firstCandidateEmailLink));
	}

	public EmailBean setUpTemplate() {
		EmailBean template = new EmailBean();
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		template.setTemplateName("SmokeTesting" + randomUUIDString);
		template.setSubject("Production Smoke Testing Template  subject");
		template.setBody("Production Smoke Testing Template  Body");
		template.setSignature("Production Smoke Testing Template  Signature");

		return template;

	}

	public void attachLabelInCandidateDetailPage(WebDriver driver, String candidateId) {
		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(driver);
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl + "Profile/" + candidateId);
		WebPageHelper.sleep(7000);
		clicElement(driver, candidateDetailsPage.addLabels);
		enterText(driver, candidateDetailsPage.labelsTypeAhead, "candidatedetailpage");
		driver.findElement(candidateDetailsPage.labelsTypeAhead).sendKeys(Keys.ENTER);
		clicElement(driver, candidateDetailsPage.tagOverlaySave);

	}

	/*
	 * Create a template and delete in serach results
	 */
	public void createNewTemplate(EmailBean email) {

		EngageEmailTemplatePageUtil engageEmailTemplatePageUtil = new EngageEmailTemplatePageUtil(driver, false);
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl + "Engage/Email-Templates");

		clicElement(driver, engageEmailTemplatePageUtil.newBtn);

		enterText(driver, engageEmailTemplatePageUtil.emailTemplateName, email.getTemplateName());
		enterText(driver, engageEmailTemplatePageUtil.emailSub, email.getSubject());
		clicElement(driver, engageEmailTemplatePageUtil.tempSave);
		String expected = getElementText(driver, engageEmailTemplatePageUtil.createdPopup);
		String actual = "Email Template Saved Successfully";

		Assertion.assertEquals(actual, expected, "Save email template message is as expected ");

		clicElement(driver, engageEmailTemplatePageUtil.templateCloseBtn);

		enterText(driver, engageEmailTemplatePageUtil.searchTextField, email.getTemplateName());
		clicElement(driver, engageEmailTemplatePageUtil.searchBtn);
		String newlyCreatedTemplate = getElementText(driver, engageEmailTemplatePageUtil.firstTemplate);
		if (newlyCreatedTemplate.equals(email.getTemplateName())) {
			Logging.log(" EmailTemplate is created successully !!!" + email.getTemplateName());
		} else {
			Logging.log(" Seems EmailTemplate is not created !!!!" + "EmailTemplate is " + email.getTemplateName());
		}

		// delete the first result from the list
		clicElement(driver, engageEmailTemplatePageUtil.firstDeleteBtn);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Assert.fail("Run time excption" + e.getMessage());
			e.printStackTrace();
		}
		clicElement(driver, engageEmailTemplatePageUtil.deleteConfirmation);
		String expected1 = getElementText(driver, engageEmailTemplatePageUtil.deletedPopup);
		String actual1 = "Email Template Deleted Successfully";

		Assertion.assertEquals(actual1, expected1, "Delete email template message is as expected ");

	}

	public void cloneCampaign() {
		EngageCampaignPage engageCampaignPage = new EngageCampaignPage(driver, false);
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl + "Engage/Campaign");

		clicElement(driver, engageCampaignPage.cloneCampaignbtn);

		enterText(driver, engageCampaignPage.cloneCampaignName, "SmokeTesting" + UUID.randomUUID().toString());

		clicElement(driver, engageCampaignPage.cloneSavebtn);

		String actual = getElementText(driver, engageCampaignPage.savedmsg);

		Assertion.assertEquals(actual, "Campaign saved successfully!", "Save email template message is as expected ");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Assert.fail("Run Time exception" + e.getMessage());
			e.printStackTrace();
		}

		// Delete the cloned Campaign

		clicElement(driver, engageCampaignPage.deleteCampaignBtn);
		clicElement(driver, engageCampaignPage.deleteConfirmationbtn);

		String deleteActualmsg = getElementText(driver, engageCampaignPage.savedmsg);
		System.out.println(deleteActualmsg);
		Assertion.assertEquals(deleteActualmsg, "Campaign deleted successfully!",
				"Save email template message is as expected ");

	}

	/**
	 * Validate Similar Profiles[Create 2profiles and go to one candidate
	 * details page, other candidate should be shown in SimilarProfiles]
	 * 
	 * @param userName
	 * @param password
	 */
	public void validateSimilarProfiles(String userName, String password) {

		String name1 = "SpireTest" + System.currentTimeMillis();
		String name2 = "SpireTest" + System.currentTimeMillis();
		String candidate1 = SmokeTestHelper.createCandidate(userName, password, name1, name1);
		String candidate2 = SmokeTestHelper.createCandidate(userName, password, name2, name2);

		WebPageHelper.sleep(10000);
		Logging.log(
				"Created candidateIds: " + candidate1 + " , " + candidate2 + " with skill: " + name1 + " and " + name2);
		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(driver);

		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl + "Profile/" + candidate1);

		int i=0;
		while(true){
			WebPageHelper.sleep(5000);
			driver.navigate().refresh();
			boolean isDisplayed=isElementVisiable(driver, candidateDetailsPage.similarProfile);
			if (isDisplayed) 
				break;
			i++;
			if(i==50)
				break;
		}
		Assert.assertTrue(isElementVisiable(driver, candidateDetailsPage.similarProfile),
				"Not showing SimilarProfiles!! ");
	}
	
	public static void main(String[] args) {
		
		System.out.println("Lead,Engaged,Applicant,Hold,Rejected".contains("Lead"));
		
	}

}

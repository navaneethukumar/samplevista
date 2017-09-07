package com.spire.acqura.search.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class CandidateProfilePage extends BasePage {

	public CandidateProfilePage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_host);
		}
	}

	public CandidateProfilePage(WebDriver driver, String openurl) {

		super(driver);
		this.driver.get(openurl);

	}

	public By spireLogo = By.xpath("//a/img[contains(@src,'logo.png')]");
	public By candidateName = By.xpath("//span[@class='candidate-name ng-binding']");
	public By socialIconList = By.className("icons icons-social");
	public By linkedinIcon = By.xpath("//i[contains(@class,'linkedin')]");
	public By twitterIcon = By.xpath("//i[contains(@class,'twitter')]");
	public By facebookIcon = By.xpath("//i[contains(@class,'facebook')]");
	public By candidateImage = By.className("img img-responsive");

	/***** xpath related to candidate data block */
	public By locationImg = By.xpath("//i[contains(@class,'location')]");
	public By locationData = By.xpath("//i[contains(@class,'location')]/following-sibling::span");
	// public By qualificationImg =
	// By.xpath("//i[contains(@class,'suitcase')]");
	// public By qualificationData =
	// By.xpath("//i[contains(@class,'suitcase')]/following-sibling::span");
	public By jobTitleImg = By.xpath("//i[contains(@class,'suitcase')]");
	public By jobTitleData = By.xpath("//i[contains(@class,'suitcase')]/following-sibling::span");
	public By sourceImg = By.xpath("//i[contains(@class,'pie-chart')]");
	public By sourceData = By.xpath("//i[contains(@class,'pie-chart')]/following-sibling::span");
	public By experienceImg = By.xpath("//i[contains(@class,'user')]");
	public By experienceData = By.xpath("//i[contains(@class,'user')]/following-sibling::span");
	// public By contactImg = By.xpath("//i[contains(@class,'phone')]");
	// public By contactData =
	// By.xpath("//i[contains(@class,'phone')]/following-sibling::span");
	public By richnessIndexLabel = By.xpath("//div[contains(text(),'Richness Index')]");
	public By resumeDownloadlink = By.xpath("//i[contains(@class,'download')]");
	public By shareLink = By.xpath("//i[contains(@class,'share')]");

	/***** xpath related to skills block */
	public By skillsLabel = By.xpath("//div[contains(text(),'Skills')]");
	public By skillsContents = By.xpath("//div[contains(text(),'Skills')]/following-sibling::div/div/span[1]");
	public By viewMoreSkilllink = By.id("showMoreSkills");
	public By viewLessSkillLink = By.id("showLessSkills");

	/***** xpath related to Experience block */
	public By experienceLabel = By.xpath("//div[contains(text(),'Experience')]");
	public By allExperienceContents = By
			.xpath("//div[contains(text(),'Experience')]/following-sibling::div[1]/div/div");
	public By experienceLimit = By.xpath("//div[contains(text(),'Experience')]/following-sibling::div[1]/div/div[1]");
	public By jobTitleExpBlock = By.xpath(
			"//div[contains(text(),'Experience')]/following-sibling::div[1]/div/div[1]/following-sibling::div/article/div[2]/div[1]");
	public By employerNameExpBlock = By.xpath(
			"//div[contains(text(),'Experience')]/following-sibling::div[1]/div/div[1]/following-sibling::div/article/div[2]/div[2]");
	public By viewMoreExperienceLink = By.id("experienceShowMore");
	public By viewLessExperienceLink = By.id("experienceShowLess");

	/***** xpath related to Education block */
	public By educationLabel = By.xpath("//div[contains(text(),'Education')]");
	public By instituteNameEducationBlock = By.xpath(
			"//div[contains(text(),'Education')]/following-sibling::div[1]/div/div[1]/following-sibling::div/article/div[2]/div[1]");
	public By qualificationEducationBlock = By.xpath(
			"//div[contains(text(),'Education')]/following-sibling::div[1]/div/div[1]/following-sibling::div/article/div[2]/div[2]");
	public By viewMoreEducationLink = By.id("educationShowMore");
	public By viewLessEducationLink = By.id("educationShowLess");

	/***** xpath related to Certifications block */
	public By certificationsLabel = By.xpath("//div[contains(text(),'Certifications')]");
	
	/***** xpath related to Interest,Group,Influences block */
	public By interestLabel = By.xpath("//div[contains(text(),'Interest,')]");
	
	
	/***** xpath related to Similar Profile Block */
	public By peopleSimilarToLabel = By.xpath("//span[contains(text(),'People Similar To')]");
	public By candidateNameSimilarProfileBlk = By
			.xpath("//span[contains(text(),'People Similar To')]/following-sibling::span");
	public By noSimilarProfileMessage = By.xpath("//div[contains(text(),'No Similar Profiles Found')]");

	/***** xpath related to Notes Block */
	public By notesLabel = By.xpath("//div[contains(text(),'Notes')]");
	public By allNotesTitles = By
			.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div/div[1]/span[1]");
	public By firstNotesTitle = By
			.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div[1]/div[1]/span[1]");
	public By secondNotesTitle = By
			.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div[2]/div[1]/span[1]");
	public By notesCreatedByUsrName = By
			.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div/div[1]/span[2]");
	public By firstNoteUserName = By
			.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div[1]/div[1]/span[2]");
	public By secondNoteUserName = By
			.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div[2]/div[1]/span[2]");
	public By allNotesComments = By.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div/div[2]");
	public By firstNoteComment = By.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div[1]/div[2]");
	public By secondNoteComment = By.xpath("//div[contains(text(),'Notes')]/following-sibling::div/div/div[2]/div[2]");

	/***** xpath related to Rating Block */
	public By rating = By.xpath("//span[contains(text(),'Rating')]");
	public By ratingDropDown = By.id("activityType");
	public By ratingEntities = By.id("entity");
	public By listOfRatingsStarIcon = By.xpath("//span[contains(@id,'entity')]/following-sibling::span/span/i");
	public By submitButton = By.xpath("//button[contains(text(),'Submit')]");

	/***** xpath related to Attachments Block */
	public By attachmentsLabel = By.xpath("//div[contains(text(),'Attachments')]");
	public By attachmentFileName = By
			.xpath("//div[contains(text(),'Attachments')]/following-sibling::div/div/div/div/div[1]/div[1]");
	public By attachmentFileSize = By
			.xpath("//div[contains(text(),'Attachments')]/following-sibling::div/div/div/div/div[1]/div[2]");
	public By attachmentDownloadLink = By
			.xpath("//div[contains(text(),'Attachments')]/following-sibling::div/div/div/div/div[2]");

	public String getCandidateName() {
		waitForElementToBeVisible(driver, candidateName);
		return this.driver.findElement(candidateName).getText();
	}

	public void clickLinkedinIcon() {
		waitForElementToBeVisible(driver, linkedinIcon);
		this.driver.findElement(linkedinIcon).click();
	}

	public void clickTwitterIcon() {
		waitForElementToBeVisible(driver, twitterIcon);
		this.driver.findElement(twitterIcon).click();
	}

	public void clickFacebookIcon() {
		waitForElementToBeVisible(driver, facebookIcon);
		this.driver.findElement(facebookIcon).click();
	}

	public String getLocationDetails() {
		waitForElementToBeVisible(driver, locationData);
		return this.driver.findElement(locationData).getText();
	}

	/*
	 * public String getQualificationDetails(){
	 * waitForElementToBeVisible(driver, qualificationData); return
	 * this.driver.findElement(qualificationData).getText(); }
	 */

	public String getJobTitleDetails() {
		waitForElementToBeVisible(driver, jobTitleData);
		return this.driver.findElement(jobTitleData).getText();
	}

	public String getSourceDetails() {
		waitForElementToBeVisible(driver, sourceData);
		return this.driver.findElement(sourceData).getText();
	}

	public String getexperienceDetails() {
		waitForElementToBeVisible(driver, experienceData);
		return this.driver.findElement(experienceData).getText();
	}

	/*
	 * public String getcontactDetails(){ waitForElementToBeVisible(driver,
	 * contactData); return this.driver.findElement(contactData).getText(); }
	 */

	public void clickResumeDownloadLink() {
		waitForElementToBeVisible(driver, resumeDownloadlink);
		this.driver.findElement(resumeDownloadlink).click();
	}

	public void clickShareLink() {
		waitForElementToBeVisible(driver, shareLink);
		this.driver.findElement(shareLink).click();
	}

	public String getAllSkillsContent() {
		waitForElementToBeVisible(driver, skillsContents);
		return this.driver.findElement(skillsContents).getText();
	}

	public void clickViewMoreSkillLink() {
		waitForElementToBeVisible(driver, viewMoreSkilllink);
		this.driver.findElement(viewMoreSkilllink).click();
	}

	public void clickViewLessSkillLink() {
		waitForElementToBeVisible(driver, viewLessSkillLink);
		this.driver.findElement(viewLessSkillLink).click();
	}

	public List<WebElement> getListOfInstitutions() {
		waitForElementToBeVisible(driver, instituteNameEducationBlock);
		List<WebElement> list = this.driver.findElements(instituteNameEducationBlock);
		return list;
	}

	public List<WebElement> getListOfEmployer() {
		waitForElementToBeVisible(driver, employerNameExpBlock);
		List<WebElement> list = this.driver.findElements(employerNameExpBlock);
		return list;
	}

	public void clickViewMoreExperienceLink() {
		waitForElementToBeVisible(driver, viewMoreExperienceLink);
		this.driver.findElement(viewMoreExperienceLink).click();
	}

	public void clickViewLessExperienceLink() {
		waitForElementToBeVisible(driver, viewLessExperienceLink);
		this.driver.findElement(viewLessExperienceLink).click();
	}

	public List<WebElement> getListOfJobTitle() {
		waitForElementToBeVisible(driver, jobTitleExpBlock);
		List<WebElement> list = this.driver.findElements(jobTitleExpBlock);
		return list;
	}

	public List<WebElement> getListOfQualification() {
		waitForElementToBeVisible(driver, qualificationEducationBlock);
		List<WebElement> list = this.driver.findElements(qualificationEducationBlock);
		return list;
	}

	public void clickViewMoreEducationLink() {
		waitForElementToBeVisible(driver, viewMoreEducationLink);
		this.driver.findElement(viewMoreEducationLink).click();
	}

	public void clickViewLessEducationLink() {
		waitForElementToBeVisible(driver, viewLessEducationLink);
		this.driver.findElement(viewLessEducationLink).click();
	}

	public String getCandidateNameInSimilarProfileBlock() {
		waitForElementToBeVisible(driver, candidateNameSimilarProfileBlk);
		return this.driver.findElement(candidateNameSimilarProfileBlk).getText();
	}

	public List<WebElement> getListOfNotesTitles() {
		waitForElementToBeVisible(driver, allNotesTitles);
		List<WebElement> list = this.driver.findElements(allNotesTitles);
		return list;
	}

	public List<WebElement> getListOfUsers() {
		waitForElementToBeVisible(driver, notesCreatedByUsrName);
		List<WebElement> list = this.driver.findElements(notesCreatedByUsrName);
		return list;
	}

	public List<WebElement> getListOfNotesComments() {
		waitForElementToBeVisible(driver, allNotesComments);
		List<WebElement> list = this.driver.findElements(allNotesComments);
		return list;
	}

}

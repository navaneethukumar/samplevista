package com.spire.crm.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.spire.base.helper.WebPageHelper;
import com.spire.crawl.pages.BasePage;

public class CandidateDetailsPage extends BasePage {

	public CandidateDetailsPage(WebDriver driver) {
		super(driver);
	}

	public By name = By.id("candFirstLastNameId");
	public By editName = By.id("detProNameEdit");
	public By firstName = By.id("detProfs");
	public By location = By.id("bindLocationId");
	public By experience = By
			.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[2]/div[2]/div[2]/span[3]/span[1]");
	public By shareUrl = By.id("ShareUrlSpan");
	public By addToCampaign = By.id("CampaignIconSpan");
	public By addLabels = By.cssSelector("#tagOverLayImg");
	public By crmStage = By.xpath(".//*[@id='detProStage crmStage']/span");
	public By engScore = By.xpath(".//*[@id='detProEngScoB']/span");
	public By goBack = By.xpath(".//*[@id='detProGoBackB']/span");
	public By emailIcon = By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[3]/ul/li[1]/span/span");
	public By phoneIcon = By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[3]/ul/li[2]/div");
	public By skillEdit = By.id("skillsEditIcon");
	public By enterSkill = By.id("skillsTypeAhead");
	public By saveSkill = By.id("skillsEditSave");
	public By designations = By.xpath(".//*[@id='detProDesiSpan']/span[1]");
	public By companyNames = By.xpath(".//*[@id='detProEmpName']/span");
	public By degreeName = By.id("detProEduDegree");
	public By linkedinUrl = By.xpath(".//*[@id='detProLinkedIn']/span");
	public By skills = By.cssSelector(".col-md-11.col-sm-11.col-xs-10");
	public By showAllSkills = By.id("skillsShowMore");
	public By allSkills = By.xpath(".//*[@id='skillChip']/span/a/span");
	public By labelsTypeAhead = By.id("labelsTypeAhead");
	public By tagOverlaySave = By.xpath(".//*[@id='tagOverlaySave']");
	public By saveDetails = By.id("detProNameSave");
	public By locationEdit = By.id("detProLocToggle");
	public By locationTextBox = By.id("locTypeAhead");
	public By lastName_Email_Contact = By.id("detProls");
	public By saveLocation = By.id("detProLocSave");
	public By linkedin = By.id("detProLinkedIn");
	public By editLinkedin = By.id("lindedInToggle");
	public By enterLinkedIn = By.xpath(".//*[@id='linkedinForm']/div/div[1]/input");
	public By saveLinkedin = By.id("detProSocSave");
	public By twitterURL = By.id("twitterToggle");
	public By addEmp = By.id("detProPlus");
	public By designation = By.id("designationTypeAhead");
	public By company = By.id("companyTypeAhead");
	public By empStartDate = By.id("detProStartDate");
	public By empToDate = By.id("detProEndDate");
	public By saveEmp = By.id("detProSaveBtn");
	public By deleteEmp = By.xpath(".//*[@id='detProDesi']/button");
	public By deleteEmpPopup = By.xpath("//md-dialog/div/button[2]");
	public By addEdu = By.id("detProEduEdit");
	public By degree = By.id("degreeTypeAhead");
	public By institute = By.id("instituteTypeAhead");
	public By eduStartDate = By.id("detProEduStartDate");
	public By eduEndDate = By.id("detProEduInput");
	public By saveEducation = By.id("detProEditSave");
	public By skillsNames = By.xpath(
			".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[3]/div/div[1]/div[2]/span[1]/span[1]/a/span");

	public By removeSkills = By.id("skillChipRemove");

	public By activityStreamTab = By.xpath(".//a[text()='Activity Stream']");
	public By selectActivityDropdown = By.id("selectActivity");
	public By notesTitle = By.id("asNotesTitle");
	public By notesDescription = By.id("asNotes");
	public By saveButton = By.id("asCreateActivitySave");
	public By activityType = By.xpath(".//*[@id='asViewList_0']/div[1]/a[4]/u");

	public By activityCreatedTime = By.xpath(".//*[@id='asViewList_0']/div[1]/span[3]");
	public By similarProfile = By.xpath(".//*[@id='similarPeopleInfo-name-id']/span");

	public void clickGoBack() {
		this.driver.findElement(goBack).click();
	}

	public List<String> getDesignations() {

		List<String> designations = new ArrayList<String>();
		List<WebElement> desi = this.driver.findElements(skills);
		for (WebElement webElement : desi) {
			designations.add(webElement.getText());
		}
		return designations;
	}

	public List<String> getCompanyNames() {
		List<String> companies = new ArrayList<String>();
		List<WebElement> comp = this.driver.findElements(companyNames);
		for (WebElement webElement : comp) {
			companies.add(webElement.getText());
		}
		return companies;
	}

	public String getSkills() {
		return this.driver.findElement(skills).getText();
	}

	public String getAllSkills() {
		String skills = null;
		try {
			clicElement(driver, showAllSkills);
			Thread.sleep(2000);
			clicElement(driver, skillEdit);
			Thread.sleep(2000);
			skills = getElementText(driver, allSkills);
		} catch (Exception e) {
			// skills = getElementText(driver, allSkills);
		}
		return skills;
	}

	/**
	 * MouseHover on the respective element, click on edit icon, enters given
	 * value to textbox and saves fieldNameToEdit[NAME/EMAIL/CONTACT/LOCATION]
	 * 
	 * @param driver
	 * @param fieldNameToEdit[NAME/EMAIL/CONTACT/LOCATION/LINKEDIN]
	 * @param valueToBeEdited
	 */
	public void editCandidateDetails(WebDriver driver, String fieldNameToEdit, String valueToBeEdited) {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (fieldNameToEdit.equals("NAME") || (fieldNameToEdit.equals("EMAIL"))
				|| (fieldNameToEdit.equals("CONTACT"))) {
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(name)).click().build().perform();
			builder.moveToElement(driver.findElement(editName)).click().build().perform();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (fieldNameToEdit.equals("NAME")) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				enterText(driver, firstName, valueToBeEdited);
				clicElement(driver, saveDetails);
			}
			if (fieldNameToEdit.equals("EMAIL")) {
				this.driver.findElements(lastName_Email_Contact).get(1).clear();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.driver.findElements(lastName_Email_Contact).get(1).sendKeys(valueToBeEdited);
				clicElement(driver, saveDetails);
			}
			if (fieldNameToEdit.equals("CONTACT")) {
				this.driver.findElements(lastName_Email_Contact).get(2).clear();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.driver.findElements(lastName_Email_Contact).get(2).sendKeys(valueToBeEdited);
				clicElement(driver, saveDetails);
			}
		}

		if (fieldNameToEdit.equals("LOCATION")) {

			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(location)).click().build().perform();
			builder.moveToElement(driver.findElement(locationEdit)).click().build().perform();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			enterText(driver, locationTextBox, valueToBeEdited);
			clicElement(driver, saveLocation);
		}

		if (fieldNameToEdit.equals("TWITTER")) {

			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(linkedin)).click().build().perform();
			builder.moveToElement(driver.findElement(editLinkedin)).click().build().perform();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			enterText(driver, enterLinkedIn, valueToBeEdited);
			clicElement(driver, saveLinkedin);

		}
		this.driver.navigate().refresh();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmployer() {
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(designations)).click().build().perform();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		builder.moveToElement(driver.findElement(deleteEmp)).click().build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clicElement(driver, deleteEmpPopup);
		try {
			Thread.sleep(13000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void deleteSkills() {
		waitForElementToBeVisible(driver, skillsNames);
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(skills)).click().build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		builder.moveToElement(driver.findElement(skillEdit)).click().build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> elements = driver.findElements(removeSkills);
		for (WebElement webElement : elements) {
			webElement.click();
		}
		clicElement(driver, saveSkill);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addSkills(String skill1, String skill2) {

		waitForElementToBeVisible(driver, skillsNames);
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(skills)).click().build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		builder.moveToElement(driver.findElement(skillEdit)).click().build().perform();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enterText(driver, enterSkill, skill1);
		driver.findElement(enterSkill).sendKeys(Keys.ENTER);
		enterText(driver, enterSkill, skill2);
		driver.findElement(enterSkill).sendKeys(Keys.ENTER);
		clicElement(driver, saveSkill);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String createActivity(String candidateId, String activityType, int ratings) {

		if (candidateId != null) {
			driver.get(driver.getCurrentUrl() + "Profile/" + candidateId);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForElementToBeVisible(driver, activityStreamTab);
		clicElement(driver, activityStreamTab);

		CandidateDetailsPage candidateDetailsPage = new CandidateDetailsPage(this.driver);
		clicElement(driver, candidateDetailsPage.activityStreamTab);
		WebPageHelper.waitForSeconds(driver, 5);
		selectdropdownBytext(driver, candidateDetailsPage.selectActivityDropdown, activityType);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (ratings != 0) {
			selectRatings(ratings);
		}

		if (activityType.equals("Notes")) {
			driver.findElement(candidateDetailsPage.notesTitle).sendKeys("Behaviour");
			driver.findElement(candidateDetailsPage.notesDescription).sendKeys("Good");
		}

		clicElement(driver, candidateDetailsPage.saveButton);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return activityType;
	}

	/**
	 * Selects all activityType ratings while creating activity
	 * 
	 * @param ratingCount
	 */
	public void selectRatings(int ratingCount) {
		selectBenefitRatings(ratingCount);
		selectAwareNessRatings(ratingCount);
		selectInterestRatings(ratingCount);
		selectFitmentRatings(ratingCount);
	}

	public void selectBenefitRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asBenifitLevel']/i[" + ratingCount + "]")).click();
	}

	public void selectAwareNessRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asCompanyLevel']/i[" + ratingCount + "]")).click();
	}

	public void selectInterestRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asInterestLevel']/i[" + ratingCount + "]")).click();
	}

	public void selectFitmentRatings(int ratingCount) {
		driver.findElement(By.xpath(".//*[@id='asFitmentLevel']/i[" + ratingCount + "]")).click();
	}

}

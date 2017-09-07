package com.spire.acqura.search.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class AdvancedSearchPage extends BasePage {

	public AdvancedSearchPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_host);
		}
	}
	
	public AdvancedSearchPage(WebDriver driver, String openurl) {

		super(driver);
		this.driver.get(openurl);
		
	}
	
	public By header = By.xpath("//h2");
	public By skillLabel = By.xpath("//label[contains(text(),'Skill')]");
	public By locationLabel = By.xpath("//label[contains(text(),'Location')]");
	public By candidateJobTitleLabel = By.xpath("//label[contains(text(),'Candidate Job Title')]");
	public By currentCompanyLabel = By.xpath("//label[contains(text(),'Current Company')]");
	public By educationLevelLabel = By.xpath("//label[contains(text(),'Education Level')]");
	public By instituteLabel = By.xpath("//label[contains(text(),'Institute')]");
    public By sourceTypeLabel = By.xpath("//label[contains(text(),'Source Type')]");
	public By sourceNameLabel = By.xpath("//label[contains(text(),'Source Name')]");
	public By stateLabel = By.xpath("//label[contains(text(),'State')]");
	public By countryLabel = By.xpath("//label[contains(text(),'Country')]");
	public By experienceLabel = By.xpath("//label[contains(text(),'Experience')]");
	public By locationAND = By.id("and_locationName");
	public By locationOR = By.id("or_locationName");
	public By jobTitleAND = By.id("and_jobTitle");
	public By jobTitleOR = By.id("or_jobTitle");
	public By currentCompanyAND = By.id("and_currentCompany");
	public By currentCompanyOR = By.id("or_currentCompany");
	public By educationLevelAND = By.id("and_educationLevel");
	public By educationLevelOR = By.id("or_educationLevel");
	public By instituteAND = By.id("and_instituteName");
	public By instituteOR = By.id("or_instituteName");
	public By sourceTypeAND = By.id("and_sourceType");
	public By sourceTypeOR = By.id("or_sourceType");
	public By sourceNameAND = By.id("and_sourceName");
	public By sourceNameOR = By.id("or_sourceName");
	public By stateAND = By.id("and_state");
	public By stateOR = By.id("or_state");
	public By countryAND = By.id("and_country");
	public By countryOR = By.id("or_country");
	public By experienceAND = By.id("and_totalExperienceMnth");
	public By experienceOR = By.id("or_totalExperienceMnth");
	public By skillField = By.xpath("//div/label[contains(text(),'Skill')]/following-sibling::div/div/input");
	public By locationField = By.xpath("//div/label[contains(text(),'Location')]/following-sibling::div/div/input");
	public By candidateJobTitleField = By.xpath("//div/label[contains(text(),'Candidate Job Title')]/following-sibling::div/div/input");
	public By currentCompanyField = By.xpath("//div/label[contains(text(),'Current Company')]/following-sibling::div/div/input");
	public By educationLevelField = By.xpath("//div/label[contains(text(),'Education Level')]/following-sibling::div/div/input");
	public By instituteField = By.xpath("//div/label[contains(text(),'Institute')]/following-sibling::div/div/input");
	public By sourceTypeField = By.xpath("//div/label[contains(text(),'Source Type')]/following-sibling::div/div/input");
	public By sourceNameField = By.xpath("//div/label[contains(text(),'Source Name')]/following-sibling::div/div/input");
	public By stateField = By.xpath("//div/label[contains(text(),'State')]/following-sibling::div/div/input");
	public By countryField = By.xpath("//div/label[contains(text(),'Country')]/following-sibling::div/div/input");
	public By experienceFromYearsField = By.xpath("//div/label[contains(text(),'Experience')]/following-sibling::div/div[2]/div[1]/div[1]/input");
	public By experienceFromMonthsField = By.xpath("//div/label[contains(text(),'Experience')]/following-sibling::div/div[2]/div[1]/div[2]/input");
	public By experienceToYearsField = By.xpath("//div/label[contains(text(),'Experience')]/following-sibling::div/div[2]/div[3]/div[1]/input");
	public By experienceTomonthsField = By.xpath("//div/label[contains(text(),'Experience')]/following-sibling::div/div[2]/div[3]/div[2]/input");
	public By submitButton = By.xpath("//form[@id='advancedSearchForm']/descendant::button[@id='searchBtn']");
	public By resetButton = By.xpath("//form[@id='advancedSearchForm']/descendant::button[@id='resetBtn']");
	public By advancedSearchClose = By.xpath("//span[@class='close-panel']");
	
	public void clickCloseIcon(){
		waitForElementToBeVisible(driver, advancedSearchClose);
		this.driver.findElement(advancedSearchClose).click();
	}
	
	public String getHeaderText(){
		waitForElementToBeVisible(driver, header);
		return this.driver.findElement(header).getText();
	}
}

package com.spire.acqura.search.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_host);
		}
	}
	
	public HomePage(WebDriver driver, String openurl) {

		super(driver);
		this.driver.get(openurl);
		
	}

	public By spireLogo = By.xpath("//a/img[contains(@src,'logo.png')]");
	public By dropdownMenu = By.xpath("//div[@class='dropdown']/span");
	public By homeLink = By.xpath("//div[@class='dropdown-content']/li/a[contains(text(),'Home')]");
	public By uploadCandidateLink = By
			.xpath("//div[@class='dropdown-content']/li/a[contains(text(),'Upload Candidates')]");
	public By manageLabels = By.xpath("//div[@class='dropdown-content']/li/a[contains(text(),'Manage Labels')]");
	public By contextualSearchAndIntelligenceLogo = By.xpath("//img[contains(@src,'brand.png')]");
	
	//public By searchBar = By.xpath("//input[@id='searchBoxMain']");
	public By searchBar = By.xpath("//div[@class='ng-scope']/descendant::input[@id='searchBoxMain']");
	public By searchButton = By.xpath("//div[@class='ng-scope']/descendant::button[@id='searchBtn']");
	public By advancedSearchLink = By.xpath("//div[@class='ng-scope']/div/div[3]/span[contains(text(),'Advanced Search')]");

	public By recentSearchLabel = By.xpath("//label[contains(text(),'Recent Searches')]");
	public By savedSearchLabel = By.xpath("//label[contains(text(),'Saved Searches')]");
	public By labelsLabel = By.xpath("//label[contains(text(),'Labels')]");

	public By recentSearchesLinks = By.xpath("//div[@id='recentSearches']/div/div");
	public By savedSearchLinks = By.xpath("//div[@id='savedSearches']/div/div");
	public By labelsLinks = By.xpath("//div[@id='lables']/div/div");

	public By viewAllRecentSearchLink = By.xpath("//div[@id='recentSearches']/div/div[@id='viewAllLables']");
	public By viewAllsavedSearchLink = By.xpath("//div[@id='savedSearches']/div/div[@id='viewAllLables']");
	public By viewAlllabelsLink = By.xpath("//div[@id='lables']/div/div[@id='viewAllLables']");
	
	
	public void clickAdvancedSearchButton(){
		waitForElementToBeVisible(driver, advancedSearchLink);
		this.driver.findElement(advancedSearchLink).click();
	}
	
	public void enterTextInSearchBar(String text){
		waitForElementToBeVisible(driver, searchBar);
		this.driver.findElement(searchBar).sendKeys(text);
	}
	
	public void clickSearchButton(){
		waitForElementToBeVisible(driver, searchButton);
		this.driver.findElement(searchButton).click();
	}
	
	public void mouseHoverOnDropdownLink(){
		Actions action = new Actions(this.driver);
		action.moveToElement(this.driver.findElement(dropdownMenu)).build().perform();
	}
	
	public void clickOnHomeLink(){
		waitForElementToBeVisible(driver, homeLink);
		this.driver.findElement(homeLink).click();
	}
	
	public void clickOnUploadCandidateLink(){
		waitForElementToBeVisible(driver, uploadCandidateLink);
		this.driver.findElement(uploadCandidateLink).click();
	}
	
	public void clickOnManageLabelLink(){
		waitForElementToBeVisible(driver, manageLabels);
		this.driver.findElement(manageLabels).click();
	}

}

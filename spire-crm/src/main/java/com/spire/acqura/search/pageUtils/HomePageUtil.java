package com.spire.acqura.search.pageUtils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.acqura.search.pages.HomePage;
import com.spire.base.controller.Logging;

public class HomePageUtil extends HomePage  {
	
	public HomePageUtil(WebDriver driver, Boolean openurl) {

		super(driver, openurl);

	}
	
	public HomePageUtil(WebDriver driver, String openurl) {

		super(driver, openurl);

	}

	public void validateHomepageElements(){
		waitForElementToBeVisible(driver, contextualSearchAndIntelligenceLogo);
		Assert.assertTrue(isElementPreset(contextualSearchAndIntelligenceLogo), "Contextual Search Intelligence Logo is not present");
		Logging.log("Contextual Search Intelligence Logo is present");
		waitForElementToBeVisible(driver, spireLogo);
		Assert.assertTrue(isElementPreset(spireLogo), "Spire logo is not present");
		Logging.log("Spire logo is present");
		waitForElementToBeVisible(driver, dropdownMenu);
		Assert.assertTrue(isElementPreset(dropdownMenu), "Home Page Menu is not present");
		Logging.log("Home Page Menu is present");
		waitForElementToBeVisible(driver, searchBar);
		Assert.assertTrue(isElementPreset(searchBar), "Search Bar is not present");
		Logging.log("universal Search Bar is present");
		waitForElementToBeVisible(driver, searchButton);
		Assert.assertTrue(isElementPreset(searchButton), "Search Button is not present");
		Logging.log("Search Button is present");
		waitForElementToBeVisible(driver, advancedSearchLink);
		Assert.assertTrue(isElementPreset(advancedSearchLink), "Advanced Search Link is not present");
		Logging.log("Advanced Search Link is present");
		Assert.assertTrue(isElementPreset(recentSearchLabel), "Recent search label is not present");
		Logging.log("Recent search label is present");
		Assert.assertTrue(isElementPreset(savedSearchLabel), "Saved search label is not present");
		Logging.log("Saved search label is present");
		Assert.assertTrue(isElementPreset(labelsLabel), "Labels label is not present");
		Logging.log("Labels label is present");
		/*Assert.assertTrue(isElementPreset(viewAllRecentSearchLink), "Recent search view all link is not present");
		Logging.log("Recent search view all link is present");
		Assert.assertTrue(isElementPreset(viewAllsavedSearchLink), "Saved search view all link is not present");
		Logging.log("Saved search view all link is present");
		Assert.assertTrue(isElementPreset(viewAlllabelsLink), "Labels view all link is not present");
		Logging.log("Labels view all link is present");*/
	}

	


}

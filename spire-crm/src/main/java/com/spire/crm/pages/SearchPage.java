package com.spire.crm.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.crawl.pages.BasePage;

public class SearchPage extends BasePage {

	public static String URL = "/UI/src/app/index.html#/Search-Results";
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	public By searchTextField = By.xpath("//div/input[@role='combobox']");
	public By clickOnSearchBtn = By.id("HeaderSearchBtn");
	public By clickOnJAVASkill = By.xpath("//form/div[@id='HeaderSearchDiv']/ul/li[1]");
	public By searchResults = By.xpath("//a[@id='candName']");
	public By searchpageresult = By.xpath("//div[@class='row candidate-action-bar ng-scope']/div/h4");
	public By searchdropDown = By.xpath("//ul/li/div/a//div");
	public By clickOnSearchBar = By.id("button-template-url");
	public By clearSearch = By.xpath("//a[@ng-click='clearSearch()']");
	public By profileLink = By.xpath("//a[@id='candName']");
	public By searchResultTag = By.xpath("//div[starts-with(@id,'candResult_')]/following::span[@title='Attach Tags']");
	public By enterTag = By.xpath(
			"//div[starts-with(@id,'candResult_')]/following::input[@placeholder='Enter a Tag and Press Enter']");
	public By tagSaveButton = By.xpath("//div[starts-with(@id,'candResult_')]/following::button[@id='tagOverlaySave']");
	public By candProcandImg = By.cssSelector("a#candProcandImgA");
	public By candSourceType = By.xpath(".//*[@id='candProLi']/div[2]/div[2]/div[1]/span/span[2]/strong");
	public By candProCandStage = By.xpath(
			"//div[@class='cand-name']//div[@id='candProCandStage crmStage' and @class='candProCandStage btn-group crm-overlay ng-isolate-scope dropdown']");
	public By candProLoc = By.xpath(".//*[@id='candProLoc']/span");
	public By totalExperienceMnth = By.xpath(".//*[@id='candProLi']/div[2]/div[2]/div[2]/span[3]");
	public By candDesignationName = By.xpath("//span[@ng-bind='employer.designationName']");
	public By employerName = By.xpath("//span[@ng-bind='employer.employerName']");
	public By candProSkill = By.xpath("//div[@class='skill-set']/span[@id='candProSkill_0']");
	public By candRIScore = By.xpath("//div[@id='candEngScoreDiv']/div[1]");
	public By candEmailImg = By.cssSelector("div#candEmailImg");
	public By candProPhoneImg = By.xpath("//div[@class='profile-action-icon sprite2 sprite-call icon-sm']");
	public By tagOverLayImg = By.id("tagOverLayImg");
	public By CampaignIcon = By
			.xpath("//div[@class='row row-last hidden-xs']//div[@id='campaignOverlay']/span[@id='CampaignIconSpan']");
	public By detProEngSco = By
			.xpath("//li[@id='candProLi']//div[@class='row row-last hidden-xs']//span[@title='Engagement Score']");
	public By facetSkillAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[1]/h4");
	public By facetLocationAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[2]/h4");
	public By facetExpInYearsAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[3]/h4");
	public By facetCompanyAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[4]/h4");
	public By facetInstituteAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[5]/h4");
	public By facetEngScoreAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[6]/h4");
	public By facetCRMStageAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[7]/h4");
	public By facetFeedbackRepliesAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[8]/h4");
	public By facetFeedbackStatusModifiedAtAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[9]/h4");
	public By facetStageChangeReasonsAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[10]/h4");
	public By facetSourceNameAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[11]/h4");
	public By facetSourceTypeAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[12]/h4");
	public By facetQualificationAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[13]/h4");
	public By facetStatusAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[14]/h4");
	public By facetFreshnessAttribute = By
			.xpath("//div[@id='advance-search-sidebar-left']//div[15]/h4");
	public By facetRoleAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[16]/h4");
	public By facetVisaStatus  = By.xpath("//div[@id='advance-search-sidebar-left']//div[17]/h4");
	public By facetEmployementType  = By.xpath("//div[@id='advance-search-sidebar-left']//div[18]/h4");
	public By facetPreferredCandidate = By.xpath("//div[@id='advance-search-sidebar-left']//div[19]/h4");
	public By facetContactAttribute = By.xpath("//div[@id='advance-search-sidebar-left']//div[20]/h4");
	public By facetScrollBar = By.xpath(".//*[@id='advance-search-sidebar-left']/div/div[2]/div[3]/div");

	public By sourceType = By.xpath(".//*[@id='candProLi']/div[2]/div[2]/div[1]/span/span[2]/strong");
	public By location = By.xpath(".//*[@id='candProLoc']/span");
	public By totalExp = By.xpath(".//*[@id='candProLi']/div[2]/div[2]/div[2]/span[3]");
	public By emailIcon = By.xpath(".//*[@id='candEmailImg']/span");
	public By phoneIcon = By.xpath("//div[@class = 'profile-action-icon sprite2 sprite-call icon-sm']");
	public By phoneIcon2 = By.xpath("//div[@class='profile-action-icon sprite2 sprite-call-grey icon-sm']");
	public By labelIcon = By.id("tagOverLayImg");
	public By campaignIcon = By.id("CampaignIconSpan");
	public By bulkMailIcon = By.id("sendEmails");
	public By totalResults = By.xpath("//span[@class='pull-right total-results hidden-xs ng-binding']");
	public By searchedSkill = By.xpath("//div[@class='col-md-7 col-sm-7 col-xs-7 chips-container']");
	public By riIcon = By.xpath("candEngSpane");
	public By noOfRecords = By.xpath("html/body/div[1]/div[2]/div/div[3]/div[4]/div/span");
	public By paginationLast = By.xpath("//li[@class='pagination-last ng-scope']/a");
	public By profileSkillLink = By.xpath("//div[@id='detailProfileColDiv']//a[@title='automation']");
	public By saveSearch = By.id("saveSearchIconSpan");
	public By saveSearchTitle = By.id("saveSearchTitle");
	public By save_SaveSearch = By.id("saveSearch");
	public By savedSearches = By.xpath("//li[@title='Manage Save Search']/span/span[1]");
	public By savedSearchName = By.xpath("//div/table/tbody/tr[1]/td[1]/a/strong");
	public By home = By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[1]/a");
	public By advanceSearchIcon = By.xpath("//a[@title='Advanced Search']");
	public By submitButtonInadvsearch = By.xpath("//div[@id='ngViewDiv']//button[@type='submit']");
	public By stageTypeInAdv = By
			.xpath("//form[@role='advancedSearchForm']/div//select[@ng-model='searchForm.StageNames']");
	public By engageFromInAdv = By.xpath("//input[@name='engageFrom']");
	public By engageToInAdv = By.xpath("//input[@name='engageTo']");
	public By expFromYears = By.xpath("//input[@name='expFromYears']");
	public By expToYears = By.xpath("//input[@name='expToYears']");
	public By expFromMonths = By.xpath("//input[@name='expFromMonths']");
	public By expToMonths = By.xpath("//input[@name='expToMonths']");
	public By skillInAdvanceSearch = By.xpath("//form[@role='advancedSearchForm']//label[@for='skill']");
	public By scrollBarInAdv = By.xpath("//div[@class='ps-scrollbar-y']");
	public By freeTextSearch = By.xpath("//div[@class='md-thumb-container']/div[@class='md-thumb']");
	public By freeTextSearchField = By.xpath("//div[@role='menu']/input[@ng-model='searchbox.freeText']");
	public By advFreetextField = By.xpath("//input[@name='freeText']");
	public By openPositions=By.xpath("//div[contains(text(),'Open Positions')]");
	public By locationInFaceting=By.xpath("//h4[contains(text(),'Location')]");
	public By scrollBarInFaceting=By.xpath("html/body/div[1]/div[2]/div/div[1]/div/div[2]/div[3]/div");

	public SearchPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {
			String ui_url = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_url);
		}

	}

	public void verifyPhoneIcon() {

		if (isElementVisiable(driver, phoneIcon) != true) {
			Assert.assertTrue(isElementVisiable(driver, phoneIcon2));
		}
	}

	public void clickOnProfileLinkFromSearchResult() {
		clicElement(driver, profileLink);
	}

	public void searchCriterias(String criteria, String type) throws Exception {
		Logging.log("enterSkillAndClickOnSearch");
		clickOnSearchedOne(criteria, type);

	}

	public void searchCriteria(String criteria, String type) throws Exception {
		Logging.log("enterSkillAndClickOnSearch");
		clickOnSearchedOne(criteria, type);
		clicElement(driver, clickOnSearchBtn);
	}

	public void searchCriteriaForNot(String criteria, String type) throws Exception {
		Logging.log("enterSkillAndClickOnSearch");
		clickOnSearchedElement(criteria, type);
		clicElement(driver, clickOnSearchBtn);
	}

	public void searchOperation(String operation) throws Exception {
		enterText(driver, searchTextField, operation);
		Thread.sleep(1000);
		String searchBarListOfElementToClick = "//ul[@role='listbox']//span[@title='" + operation + "']";
		By byXpath = By.xpath(searchBarListOfElementToClick);
		waitForElementToBeClickable(driver, byXpath);
		clicElement(driver, byXpath);
		Thread.sleep(1000);
	}

	public void searchNOTOperation(String operation) throws Exception {
		clicElement(driver, clickOnSearchBar);
		Logging.log("enterSkillAndClickOnSearch");
		enterText(driver, searchTextField, operation);
		Thread.sleep(1000);
		String searchBarListOfElementToClick = "//ul[@role='listbox']//span[@title='" + operation + "']";
		By byXpath = By.xpath(searchBarListOfElementToClick);
		waitForElementToBeClickable(driver, byXpath);
		clicElement(driver, byXpath);
		Thread.sleep(1000);
	}

	public By searchTypeInAdv(String type) {
		String advanceSearchListOfType = "//div/child::span[@placeholder='" + type
				+ "']/following-sibling::input[@role='combobox']";
		By placeHolderToType = By.xpath(advanceSearchListOfType);
		waitForElementToBeVisible(driver, placeHolderToType);
		return placeHolderToType;
	}

	public void advanceSearchElement(String criteria, String type) throws Exception {
		if (type.contains("Stage Name") || type.contains("Source Type") || type.contains("Source Name")
				|| type.contains("Engagement Score") || type.contains("Institute") || type.contains("Experience")) {
			jse.executeScript("window.scrollBy(0,400)", "");
		}
		if (type.contains("Engagement Score")) {
			waitForElementToBeVisible(driver, engageFromInAdv);
			driver.findElement(engageFromInAdv).sendKeys(criteria);
			driver.findElement(engageToInAdv).sendKeys("100");
		} else if (type.contains("Experience")) {
			waitForElementToBeVisible(driver, expFromMonths);
			driver.findElement(expFromYears).sendKeys(criteria);
			driver.findElement(expFromMonths).sendKeys("2");
			driver.findElement(expToYears).sendKeys("9");
			driver.findElement(expToMonths).sendKeys("5");
		} else {
			By placeHolderToType = searchTypeInAdv(type);
			enterText(driver, placeHolderToType, criteria);
			String advanceSearchListOfElement = "//div[@role='option']/a/span[contains(text(), \"" + criteria + "\")]";
			By criteriaToClick = By.xpath(advanceSearchListOfElement);
			waitForElementToBeVisible(driver, criteriaToClick);
			clicElement(driver, criteriaToClick);
		}
	}

	public void advanceSearchOperation(String type, String operation) throws Exception {
		if (operation.contains("!")) {
			if (type.contains("Stage Name") || type.contains("Source Type") || type.contains("Source Name")
					|| type.contains("Engagement Score") || type.contains("Institute")) {
				jse.executeScript("window.scrollBy(0,400)", "");
			}
		}
		By placeHolderToType = searchTypeInAdv(type);
		enterText(driver, placeHolderToType, operation);
		Thread.sleep(1000);
		String searchOperation = "//div[@role='option']/a/span[contains(text(), \"" + operation + "\")]";
		By opertaionToClick = By.xpath(searchOperation);
		waitForElementToBeClickable(driver, opertaionToClick);
		clicElement(driver, opertaionToClick);
	}

	public void freeTextSearchBar(String freeTextData) {
		enterText(driver, freeTextSearchField, freeTextData);
		clicElement(driver, clickOnSearchBtn);
		waitForElementToBeVisible(driver, noOfRecords);
	}

	public void freeTextAdvSearch(String freeTextData) {
		enterText(driver, advFreetextField, freeTextData);
		jse.executeScript("window.scrollBy(0,400)", "");
		waitForElementToBeVisible(driver, submitButtonInadvsearch);
		clicElement(driver, submitButtonInadvsearch);
		waitForElementToBeVisible(driver, noOfRecords);
	}

	public void advFreetextComb(String freeTextData) {
		enterText(driver, advFreetextField, freeTextData);
	}
	
	public void clickOnRquisition(String jobTitle){
		String requisition = "//div/span/strong[contains(text(),\"" + jobTitle + "\")]";
		By reqPath = By.xpath(requisition);
		waitForElementToBeVisible(driver, reqPath);
		clicElement(driver,reqPath);
	}

	/**
	 * It clicks on first suggested search thing from drop down
	 * 
	 * @param criteria
	 * @param type
	 * @throws Exception
	 */
	public void clickOnFirstSuggestedOne(String criteria, String type) throws Exception {
		Logging.log("clickOnFirstSuggestedOne");
		firstSuggestedSearchCriteria(criteria, type);
		clicElement(driver, clickOnSearchBtn);
	}

	public void verifySearchResultPageContentIsNotNull() throws Exception {
		waitForElementToBeVisible(driver, searchResults);
		Assert.assertTrue(isElementVisiable(driver, searchResults));
	}

	public void clickOnSearchedOne(String criteria, String type) throws Exception {
		// waitForElementToBeVisible(driver, searchdropDown);
		clicElement(driver, clickOnSearchBar);
		enterText(driver, searchTextField, criteria);
		Thread.sleep(1000);
		String searchBarListOfElementToClick = "//div/child::span[1][contains(text(),'" + criteria
				+ "')]/following-sibling::span[contains(text(),'" + type + "')]";
		By byXpath = By.xpath(searchBarListOfElementToClick);
		waitForElementToBeClickable(driver, byXpath);
		clicElement(driver, byXpath);
	}

	public void clickOnSearchedElement(String criteria, String type) throws Exception {
		enterText(driver, searchTextField, criteria);
		Thread.sleep(1000);
		String searchBarListOfElementToClick = "//div/child::span[1][contains(text(),'" + criteria
				+ "')]/following-sibling::span[contains(text(),'" + type + "')]";
		By byXpath = By.xpath(searchBarListOfElementToClick);
		waitForElementToBeClickable(driver, byXpath);
		clicElement(driver, byXpath);
	}
	public List<String> facetingInSearchBeforeSelecting(String value){
		List<String> values=new ArrayList<String>();
		for(int i=0;i<=3;i++){
		String val= driver.findElement(By.xpath("//label[@for='"+value+i+"']/i[1]")).getText();
		values.add(val);
		}
		return values;
	}
	public By selectValueInFacet(String value){
		By facetingvalue=By.xpath("//label[@for='"+value+"']");
		return facetingvalue;
	}
	public void countInFacet(String facetCountValue) throws InterruptedException{
		Thread.sleep(2500);
		WebElement element = driver.findElement(By.xpath(facetCountValue));
		String title = element.getAttribute("title");
		Thread.sleep(2500);
		String totalSearchResults = getElementText(driver, totalResults);
		String searchResult[] = totalSearchResults.split(" ");
		Thread.sleep(2500);
		Assert.assertTrue(title.contains(searchResult[1]),"count is not equal in facet and total results");
	}
	public void facetingInSearchAfterSelecting(String value,List<String> values) throws InterruptedException{
		for(int i=0;i<=3;i++){
			Thread.sleep(1500);
		Assert.assertEquals(driver.findElement(By.xpath("//label[@for='"+value+i+"']/i[1]")).getText(),values.get(i),"after selecting one value,values under one particular attribute are changing the order of values in faceting insted of displaying like before selecting");
		}
	}

	public void firstSuggestedSearchCriteria(String criteria, String type) throws Exception {
		// waitForElementToBeVisible(driver, searchdropDown);
		clicElement(driver, clickOnSearchBar);
		enterText(driver, searchTextField, criteria);
		Thread.sleep(1000);
		String searchBarListOfElementToClick = "//div/child::span[1][starts-with(),'" + criteria
				+ "')]/following-sibling::span[contains(text(),'" + type + "')]";
		By byXpath = By.xpath(searchBarListOfElementToClick);
		waitForElementToBeClickable(driver, byXpath);
		clicElement(driver, byXpath);
	}

	public void profileAttachedToTagInFromSearchResults(String name) {
		clicElement(driver, searchResultTag);
		TypeAndEnterKey(driver, enterTag, name);
		clicElement(driver, tagSaveButton);

	}

}

package com.spire.acqura.search.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class ManageLabelsPage extends BasePage {

	public ManageLabelsPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_host = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_host);
		}
	}
	
	public ManageLabelsPage(WebDriver driver, String openurl) {

		super(driver);
		this.driver.get(openurl);
		
	}

	public By spireLogo = By.xpath("//a/img[contains(@src,'logo.png')]");
	public By dropdownMenu = By.xpath("//div[@class='dropdown']/span");
	public By homeLink = By.xpath("//div[@class='dropdown-content']/li/a[contains(text(),'Home')]");
	public By uploadCandidateLink = By
			.xpath("//div[@class='dropdown-content']/li/a[contains(text(),'Upload Candidates')]");
	public By manageLabels = By.xpath("//div[@class='dropdown-content']/li/a[contains(text(),'Manage Labels')]");
	
	//public By searchBar = By.xpath("//input[@id='searchBoxMain']");
	public By searchBar = By.xpath("//div[@class='searchbar-container']/descendant::input[@id='searchBoxMain']");
	public By searchButton = By.xpath("//div[@class='searchbar-container']/descendant::button[@id='searchBtn']");
	/*public By advancedSearchLink = By.xpath("//button[contains(text(),'Advanced Search')]");
	public By savedSearchLink = By.xpath("//button[contains(text(),'Saved Search')]");
*/
	public By manageLabelsLabel = By.id("managelabelstitle");
	public By searchLabelBox = By.id("searchlabelbox");
	public By labelsDropdownMenu = By.id("dropdownMenu1");
	public By paginationSummary = By.xpath("//span[@class,'pagination-summary ng-binding')]");
	
	
	
	public By labelNameHeader = By.xpath("//div[@id='label-dashboard']/descendant::th[contains(text(),'LABEL NAME')]");
	public By createdByHeader = By.xpath("//div[@id='label-dashboard']/descendant::th[contains(text(),'CREATED BY')]");
	public By sharedWithHeader = By.xpath("//div[@id='label-dashboard']/descendant::th[contains(text(),'SHARED WITH')]");
	public By noOfCandidatesHeader = By.xpath("//div[@id='label-dashboard']/descendant::th[contains(text(),'NO OF CANDIDATES')]");
	public By actionsHeader = By.xpath("//div[@id='label-dashboard']/descendant::th[contains(text(),'ACTIONS')]");
	
	public By numberOfRowsLabelsTable = By.xpath("//div[@id='label-dashboard']/descendant::tbody/tr");
	public By listOfAllLabelNameInOnePage = By.id("labelName");
	public By listOfAllLabelDescription = By.xpath("//div[@id='labeldescription']/span[1]");
	public By listOfAllLabelCreatedBy = By.id("ownerName");
	public By listOfAllSharedWithUser = By.id("shareduserlist");
	public By listOfAllNumberOfCandidates = By.xpath("//td[4]/span");
	public By listOfAllShareIcon = By.xpath("//td[5]/div/div/button");
	public By listOfAllEditIcon = By.xpath("//td[5]/span[1]");
	public By listOfAllDeleteIcon = By.xpath("//td[5]/span[2]");
    public By header = By.id("managelabelstitle");
	
	public String returnHeaderText(){
		return this.driver.findElement(header).getText();
	}
	
}

package com.spire.crm.pageUtils;

import org.openqa.selenium.WebDriver;

import com.spire.crm.pages.GitPage;

public class GitPageUtil extends GitPage {

	public GitPageUtil(WebDriver driver, Boolean openurl,String URL) {
		super(driver, openurl,URL);
	}
	
	
	public void login(String userName,String password){
		loginGit(userName,password);
	}
	
	public void createNewBranch(String SourceBranch,String DestinationBranch) throws InterruptedException{
		String URL=driver.getCurrentUrl()+"/branches/new";
		String branchURL=driver.getCurrentUrl()+"/protected_branches";
		driver.get(URL);
		waitForElementToBeVisible(driver, branchName);
		driver.findElement(branchName).sendKeys(DestinationBranch);
		driver.findElement(createFrom).clear();
		driver.findElement(createFrom).sendKeys(SourceBranch);
		clicElement(driver, createBranch);
		Thread.sleep(5000);
		driver.get(branchURL);
		clicElement(driver, selectBranchDropdown);
		selectBranchName(DestinationBranch);
		clicElement(driver, protect);
	}
	
}

package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.crawl.pages.BasePage;

public class AssetsPage extends BasePage {
	
	public By engage=By.xpath("//span/a[@ui-sref='engage']");
	public By assets=By.xpath("//a[contains(text(),' Assets')]");
	public By createnewAsset=By.id("createnewAsset");
	public By createdMssg=By.xpath("//span[contains(text(),'New Asset Created Successfully.')]");
	public By assetUploaderInput = By.xpath("//input[@id='assetUploaderInput']");
	public By save=By.xpath("//button[contains(text(),'Save')]");
	public By createdAsset=By.xpath("//a[contains(text(),'SampleResumeFile.doc')]");
	public By modifiedAsset=By.xpath("//a[contains(text(),'SampleResume')]");
	public By updateMssg=By.xpath("//span[contains(text(),'Asset Name Updated Successfully.')]");
	public By assetEditBtn=By.id("assetEditBtn");
	public By editAsset=By.xpath("//input[@id='editAttachment']");
	public By assetsNameSave=By.id("assetsNameSave");
	public By deleteAsset=By.xpath("//tr[1]//button[@aria-label='Delete']");
	public By deletePopup=By.xpath("//button[@ng-click='dialog.hide()']");
	public By deletedMssg=By.xpath("//span[contains(text(),'Asset Deleted Successfully!')]");
	
	public AssetsPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {
			String ui_url = ContextManager.getGlobalContext().getUIHostAddress();
			this.driver.get(ui_url);
		}
	}

}

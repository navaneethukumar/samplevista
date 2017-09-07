package com.spire.crm.pageUtils;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.spire.crm.pages.AssetsPage;

public class AssetsPageUtil extends AssetsPage {

	public AssetsPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
	}
	
	public void assetPage(){
		waitForElementToBeVisible(driver, engage);
		clicElement(driver, engage);
		waitForElementToBeVisible(driver, assets);
		clicElement(driver, assets);
	}
	public void validateCreateAsset(){
		assetPage();
		waitForElementToBeVisible(driver, createnewAsset);
		clicElement(driver, createnewAsset);
		waitForElementToBeVisible(driver, assetUploaderInput);
		clicElement(driver, assetUploaderInput);
		WebElement element= driver.findElement(assetUploaderInput);
		element.sendKeys("D:\\SampleResumeFile.doc");
		clicElement(driver, save);
		waitForElementToBeVisible(driver, createdMssg);
		WebElement element1=driver.findElement(createdMssg);
	    String createdmssg=element1.getText();
	    waitForElementToBeVisible(driver, createdAsset);
		Assert.assertTrue(isElementVisiable(driver, createdAsset),"Asset is not created");
		Assert.assertEquals(createdmssg, "New Asset Created Successfully.","Asset is not created");
	}
	
	public void validateModifyAsset(){
		assetPage();
		waitForElementToBeVisible(driver, assetEditBtn);
		clicElement(driver, assetEditBtn);
		WebElement element= driver.findElement(editAsset);
		element.clear();
		element.sendKeys("SampleResume");
		clicElement(driver,assetsNameSave);
		waitForElementToBeVisible(driver, updateMssg);
		WebElement element1=driver.findElement(updateMssg);
	    String updatedMssg=element1.getText();
		waitForElementToBeVisible(driver, modifiedAsset);
		Assert.assertTrue(isElementVisiable(driver, modifiedAsset),"Asset name is not updated");
		Assert.assertEquals(updatedMssg,"Asset Name Updated Successfully.","Asset name is not updated");
	}
	public void validateListAssets(){
		assetPage();
		waitForElementToBeVisible(driver, modifiedAsset);
		Assert.assertTrue(isElementVisiable(driver, modifiedAsset));
	}
	
	public void validatedeleteAssets() throws InterruptedException{
		assetPage();
		clicElement(driver, deleteAsset);
		String currentWindow = driver.getWindowHandle();
        Set<String> set = driver.getWindowHandles();
        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
            String windowHandle = iterator.next().toString();
            driver.switchTo().window(windowHandle);
            clicElement(driver, deletePopup);
            waitForElementToBeVisible(driver, deletedMssg);
        }
        WebElement element= driver.findElement(deletedMssg);
        String deletedmssg=element.getText();
        driver.switchTo().window(currentWindow);
		driver.navigate().refresh();
		Assert.assertTrue(!(isElementVisiable(driver, modifiedAsset)),"Asset is not deleted");
		Assert.assertEquals(deletedmssg,"Asset Deleted Successfully!","Asset is not deleted");
	}
	
}
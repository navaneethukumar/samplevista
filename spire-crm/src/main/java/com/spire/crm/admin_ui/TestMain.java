package com.spire.crm.admin_ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.spire.base.helper.WebPageHelper;

public class TestMain {

	public static void main(String[] args) {
		WebDriver driver=new FirefoxDriver();
		driver.get("http://192.168.2.124/admin-ui/src/app/index.html#/");
		WebPageHelper.waitForElementAndReturnElement(driver, By.id("userName"));
		WebPageHelper.enterText(driver, By.id("userName"), "tester@logica.com");
		WebPageHelper.waitForElementAndReturnElement(driver, By.id("password"));
		WebPageHelper.enterText(driver, By.id("password"), "spire@123");
		WebPageHelper.waitForElementAndReturnElement(driver, By.xpath(".//*[@id='loginForm']/div[5]/button"));
		WebPageHelper.clicElement(driver, By.xpath(".//*[@id='loginForm']/div[5]/button"));
		EngagementScoreWeightUtil test=new EngagementScoreWeightUtil(driver, true);
		/*test.ValidateElementsPresent();
		test.createNewRule("Sangeeta", "Engagment Score", "Grater Than (>=)", "70", "CHANGE_CRM_STAGE","Lead");
        test.ValidateNewRule();
        test.deleteRule();*/
        test.verifyEngagementWeightFields();
	}

}

package com.spire.crm.candidate_activitystream.test;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.spire.base.helper.WebPageHelper;
import com.spire.common.ProfileHelper;
import com.spire.crm.pageUtils.EngageCandidateDeatilsPageUtils;
import com.spire.crm.pages.EngageCandidatesDetailsPage;

public class ValidateDropdownsUtil extends EngageCandidatesDetailsPage {

	public ValidateDropdownsUtil(WebDriver driver, Boolean openurl, String CandidateId) {
		super(driver, openurl, CandidateId);

	}

	public void validateDropdownElements() throws InterruptedException {
		
		String candidateID1 = ProfileHelper.createProfile();

		EngageCandidateDeatilsPageUtils candiateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(this.driver,
				true, candidateID1);
		driver.get(driver.getCurrentUrl() + "Profile/" + candidateID1);
		Thread.sleep(10000);
		candiateDeatilsPageUtils.changeStageTo("Lead");
		waitForSeconds(driver, 5000);
		//WebPageHelper.clicElement(driver, candidateNameLink);
		clicElement(driver, activityStreamTab);

		waitForSeconds(driver, 5000);
		clicElement(driver, createActivity);

		List<WebElement> dropDownList = driver.findElements(activityDropdown);

		String Item = null;

		List<String> Items = Arrays.asList("Video call", "Instance Message", "Voice call made", "Voice call received","Notes");

		for (WebElement webElement : dropDownList) {

			Item = webElement.getText().toString();
			Assert.assertEquals(true, Items.contains(Item), "found invalid name in dropdown list " + Item);
		}
	}

	public void validateElementPresent() {
		Assert.assertTrue(isElementPreset(activityStreamTab), "createActivity is missing");
		WebPageHelper.clicElement(driver, activityStreamTab);
		Assert.assertTrue(isElementPreset(createActivity), "createActivity is missing");
		Assert.assertTrue(isElementPreset(activitySearchBox), "SearchTextfield is missing");
		Assert.assertTrue(isElementPreset(activitySearchbtn), "activitySearchbtn is missing");
		Assert.assertTrue(isElementPreset(sortBy), "sortBy is missing");
		Assert.assertTrue(isElementPreset(timePeriod), "timePeriod is missing");
		Assert.assertTrue(isElementPreset(selectCheckBox), "selectCheckBox is missing");
		Assert.assertTrue(isElementPreset(emailCheckBox), "emailCheckBox is missing");
		Assert.assertTrue(isElementPreset(callsChechBox), "callsChechBox is missing");
		Assert.assertTrue(isElementPreset(smsChechBox), "smsChechBox is missing");
	}

	public void validateDropdownActivity() {

		clicElement(driver, createActivity);
		selectdropdownBytext(driver, createActivity, "Video call");
		clicElement(driver, saveActivity);
		
	}
}

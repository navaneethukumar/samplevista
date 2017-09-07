package com.spire.crm.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.crawl.pages.BasePage;

public class CandidateProfilePage extends BasePage {

	public static String URL = "/UI/src/app/index.html#/Profile/";
	public static String PROD_URL = "/UI/src/app/index.html#/Profile/";
	public static String ID = null;
	public By profileSkills = By.xpath("//a[@class='ng-binding']");
	public By profilePositions = By.xpath("//span[@id='detProDesiSpan']");
	public By profileEducation = By.xpath("// div[starts-with(@id,'detProEdu')]");
	public By summaryTab = By.xpath("//a[text()='Summary']");
	public By profileLink = By.xpath("//a[@id='candName']");
	public By candidateName = By.xpath("//span[@id='candFirstLastNameId']");
	public By activityStreamTab = By.xpath(".//*[@id='detailProfileColDiv']/div/div[1]/div/div/div[5]/div/div/ul/li[2]/a");
	public By selectActivityDropdown = By.id("selectActivity");
	public By activitySearchTextBox = By.id("asSearch");
	public By activitySearchIcon = By.id("asSearchIcon");
	public By sortByDropdown = By.id("sortBy");
	public By timePeriodDropdown = By.id("asTimePeriod");
	public By selectAllActivities = By.id("asBoldTxtLabel");
	public By selectActivityCheckBox = By.id("asCbLabel");
	public By createdActivitiesLog = By.xpath(".// *[@id='asViewList_0']/div[1]");
	public By benefitsRatings = By.xpath(".//*[@id='asBenifitLevel']/i[5]");
	public By companyLevelRatings = By.xpath(".//*[@id='asCompanyLevel']/i[5]");
	public By interestLevelRatings = By.xpath(".//*[@id='asInterestLevel']/i[5]");
	public By fitmentRatings = By.xpath(".//*[@id='asFitmentLevel']/i[5]");
	public By notesTitle = By.id("asNotesTitle");
	public By notesDescription = By.id("asNotes");
	public By saveButton = By.id("asCreateActivitySave");
	public By activitylog1 = By.xpath(".//*[@id='asViewList_0']/div[1]");
	public By callsCheckBox = By.xpath(".//*[@id='asCbList_1']/div");
	public By smsCheckBox = By.xpath(".//*[@id='asCbList_2']/div");
	public By notesCheckBox = By.xpath(".//*[@id='asCbList_3']/div");
	public By activityCheckBox = By.xpath(".//*[@id='asCbLabel']/span");
	public By crmStageDropDown = By.xpath("//div[@id='detProStage crmStage']");
	public By addToCRM = By.xpath("//span[@title='Add to CRM']");
	public By loadingImage = By.xpath("//img[@class='center-logo']");
	public By crmStageSaveBtn = By.id("crmStageOverlaySave");
	public final String SUCCESS_TEXT = "Saved Successfully";
	public By attachTag = By.xpath("//span[@title='Attach Tags']");
	public By textfldTypeTagEnter = By.xpath("//input[@placeholder='Enter a Tag and Press Enter']");
	public By tagSave = By.id("tagOverlaySave");
	public By campaignIcon = By.xpath("//div[@id='detProRichDD campaignOverlay']/span");
	public By listOfCampaigns = By.id("CampaignOptNameSpan");
	public By saveCampaign = By
			.xpath("//div[@id='detProRichDD campaignOverlay']//child::button[@id='campaignsOverlaySave']");
	public By checkTagIsAttached = By.xpath("//span[@class='info ng-binding']");
	public By refreshIcon=By.xpath(".//*[@id='activityStreamCollapse']/div[1]/div[2]");
	public By selectCampaign=By.id("selectCamp");
	Logger logger = Logger.getLogger(CandidateProfilePage.class);
	String ProfileURL;
	String TAG_SUCCESS_TEXT = "Tags added successfully!!";
	

	public CandidateProfilePage(WebDriver driver, Boolean openurl, String ID) {
		super(driver);
		if (openurl) {

			if (ContextManager.getGlobalContext().getAttribute("ENVIRONMENT").toString()
					.equalsIgnoreCase("production")) {
				ProfileURL = ContextManager.getThreadContext().getUIHostAddress() + PROD_URL + ID;
				logger.info("Production Profile page URL >>" + ProfileURL);
			} else {
				ProfileURL = ContextManager.getThreadContext().getUIHostAddress() + URL + ID;
				logger.info("Profile page URL >>" + ProfileURL);
			}
			this.driver.navigate().to(ProfileURL);
			waitForElementToBeDisappeared(driver, loadingImage);
			waitForElementToBeVisible(driver, By.xpath("//img[@class='img-responsive profile-image']"));
			Logging.log("Profile page loaded successfully !!!");
		}
	}

	public void clickActivityStreamTab() {
	clicElement(driver, activityStreamTab);
	}

	public void verifyProfileSkillNotNull() {
		Assert.assertTrue(isElementVisiable(driver, profileSkills));
	}

	public void verifyProfilePositionsNotNull() {
		Assert.assertTrue(isElementVisiable(driver, profileEducation));
	}

	public void verifyProfileEducationDetailsNotNull() {
		Assert.assertTrue(isElementVisiable(driver, profilePositions));
	}

	/**
	 * To verify profile content is displayed
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public void verifyProfileSummaryDetailsNotNull() throws Exception {
		waitForElementToBeVisible(driver, By.xpath("//img[@class='img-responsive profile-image']"));
		Thread.sleep(1000);
		String candiateName = getElementText(driver, candidateName);
		Logging.log("Candidate Name >>>" + candiateName);
		Assert.assertNotNull(candiateName, "Candidate name is not appearing !!!");
		Assert.assertTrue(!candiateName.isEmpty(), "Candidate name is not appearing !!!");
	}

	public void markAsCRMStage(String stage) {
		// String stageXpath =
		// "//div[@id='crmStageOverlay']//child::span[text()='" + stage + "']";
		String stageXpath = "//ul[@class='dropdown-overlay']//li//child::span[text()='" + stage + "']";
		Logging.log("To mark CRM-Stage--");
		// clicElement(driver, crmStageDropDown);
		// clicElement(driver, addToCRM);
		if (ContextManager.getThreadContext().getBrowser().equalsIgnoreCase("Chrome")) {
			try {

				//actionMoveToElement(driver, addToCRM);
				//actionMoveToElement(driver, By.xpath(stageXpath));

				// waitForElementToBeClickable(driver, );
				// WebElement element =
				// driver.findElement(By.xpath(stageXpath));
				// Actions actions = new Actions(driver);
				// actions.moveToElement(element).click().perform();
				waitForTextToBeDisappeared(driver, SUCCESS_TEXT);
				// dragAndDropBy(driver,By.xpath(stageXpath),element.getLocation().getX(),
				// element.getLocation().getY());
				// ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().x+")");
				// element.click();
			} catch (Exception e) {
				throw new RuntimeException("---CRM-Stage not clickable --");
			}

		} else {
			clicElement(driver, By.xpath(stageXpath));
		}
		clicElement(driver, crmStageSaveBtn);
		// Assert.assertTrue(isTextPresent(driver, SUCCESS_TEXT), "CRM-Stage is
		// not moved successfully");
	}

	public void clickOnProfileLinkFromSearchResult() {
		clicElement(driver, profileLink);
	}

	/**
	 * Select activityType Checkbox
	 * 
	 * @param activityType[email,calls,
	 *            sms,notes]
	 */
	public void selectActivity(String activityType) {
		List<WebElement> element = driver.findElements(activityCheckBox);

		for (WebElement webElement : element) {
			webElement.click();
		}
		if (activityType.equals("email")) {
			element.get(0).click();
		}
		if (activityType.equals("calls")) {
			element.get(1).click();
		}
		if (activityType.equals("sms")) {
			element.get(2).click();
		}
		if (activityType.equals("notes")) {
			element.get(3).click();
		}
	}

	/**
	 * Type Tag name and enter key
	 * 
	 * @param tagName
	 */
	public void profileAttachToTag(String tagName) {
		waitForElementToBeClickable(driver, attachTag);
		clicElement(driver, attachTag);
		TypeAndEnterKey(driver, textfldTypeTagEnter, tagName);
		waitForElementToBeClickable(driver, tagSave);
		clicElement(driver, tagSave);
		waitForTextToBeDisappeared(driver, TAG_SUCCESS_TEXT);
	}

	/**
	 * attaching profile to campaign.
	 * 
	 * @param campaign
	 * @return
	 */
	public boolean profileAttachToCampaign(String campaign) {
		clicElement(driver, campaignIcon);
		List<WebElement> listElmnts = getElements(driver, listOfCampaigns);
		if (listElmnts != null) {
			for (WebElement element : listElmnts) {
				String listedCampaign = element.getText();
				if (listedCampaign.equalsIgnoreCase(campaign)) {
					waitForVisibilityOfElement(driver, element);
					element.click();
					clicElement(driver, saveCampaign);
					waitForTextToBeDisappeared(driver, SUCCESS_TEXT);
					Logging.log("Attached profile to the campaign -->" + campaign);
					return true;
				}

			}
			return false;
		}
		return false;

	}

	public boolean verifyProfileAttachedToCampaign(String campaign) {
		// profilePageRefresh();
		clicElement(driver, campaignIcon);
		List<WebElement> listElmnts = getElements(driver, listOfCampaigns);
		if (listElmnts != null) {
			for (WebElement element : listElmnts) {
				String listedCampaign = element.getText();
				if (listedCampaign.equalsIgnoreCase(campaign)) {
					waitForVisibilityOfElement(driver, element);
					Logging.log("Attached profile to the campaign fine -->" + campaign);
					return true;
				}

			}
			return false;
		} else {
			Assert.fail("Profile is not attached to Campaign !!!");
		}
		return false;

	}

	public void verifyTagIsExistedToProfile(String labelName) {
		// profilePageRefresh();
		try {
			Thread.sleep(2000);
			clicElement(driver, attachTag);
			Thread.sleep(2000);
			String tag = getElementText(driver, checkTagIsAttached);
			Assert.assertTrue(getElements(driver, checkTagIsAttached).size() == 1,
					"Label is not attached to profile !!");
			Assert.assertEquals(tag.substring(0, 10), labelName.substring(0, 10), "Label is not attachec to profile");
		} catch (Exception e) {
			throw new RuntimeException("-----verifyTagIsExistedToProfile-------");
		}

	}

	public void profilePageRefresh() {
		driver.navigate().refresh();
		waitForElementToBeDisappeared(driver, loadingImage);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

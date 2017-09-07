package com.spire.jobpost;

import java.util.UUID;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.crm.pages.JobsPage;
import com.spire.jobpost.JobPostBean;

public class JobPostsPageUtil extends JobsPage {

	public JobPostsPageUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);
		// TODO Auto-generated constructor stub
	}

	/*
	 * verify all the jobpost elements are persent or not
	 */
	public void validatePageElementsDisplayed() {
		clicElement(driver, jobsTab);
		Assertion.assertTrue(isElementPreset(analyticsText), "analyticsText is missing");
		Assertion.assertTrue(isElementPreset(allJobs), "allJobs is missing");
		Assertion.assertTrue(isElementPreset(jobPostsText), "jobPostsText is missing");
		Assertion.assertTrue(isElementPreset(postFromUrl), "postFromUrl icon is missing");
		Assertion.assertTrue(isElementPreset(uploadFile), "uploadFile icon is missing");
		Assertion.assertTrue(isElementPreset(createNewJobPost), "createNewJobPost icon is missing");
		Assertion.assertTrue(isElementPreset(openPositionsText), "openPositionsText  is missing");
		Assertion.assertTrue(isElementPreset(postAJobText), "postAJobText  is missing");
		Assertion.assertTrue(isElementPreset(jobBoards), "jobBoards  is missing");

	}

	/*
	 * verify postJobFromURL icon by giving proper URL
	 */
	public void postJobFromURL(JobPostBean job) {
		clicElement(driver, jobsTab);
		clicElement(driver, postFromUrl);
		enterText(driver, postFromUrlTextField,
				"http://www.naukri.com/job-listings-QA-Software-Testing-Babychakra-Startup-Mumbai-3-to-5-years-100616901723?src=jobsearchDesk&sid=14655548204955&xp=1&qp=software%20testing&srcPage=s‎");
		clicElement(driver, fetchBtn);
		String PopUp = getElementText(driver, dataFetchedPopUp);
		Assertion.assertEquals(PopUp, "Data fetched successfully!", "Data fetched successfully!");
		clicElement(driver, preview);
		clicElement(driver, editLinkURL);
		String jobTitle = job.getJobTitle();
		enterText(driver, titleTextBox, jobTitle);

		enterText(driver, companyTextBox, job.getCompanyName());
		selectdropdownBytext(driver, educationTextField, job.getEducation());
		enterText(driver, descriptionTextArea, job.getDescription());
		if (isElementPreset(skillsTextField)) {
			enterText(driver, skillsTextField, job.getSkills());
			driver.findElement(skillsTextField).sendKeys(Keys.ENTER);
		}		
		clicElement(driver, nextBtn1);
		selectdropdownBytext(driver, countryTextField, job.getCountry());
		selectdropdownBytext(driver, stateTextField, job.getState());
		enterText(driver, cityTextField, job.getCity());
		enterText(driver, addressLane, job.getAddressLane());
		enterText(driver, postalCodeTextField, job.getPostalCode());
		clicElement(driver, newBtn2);
		enterText(driver, nameTextField, job.getName());
		enterText(driver, emailTextField, job.getEmail());
		clicElement(driver, newBtn3);
		clicElement(driver, previewJobBtn);
		clicElement(driver, postBtn);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//driver.navigate().refresh();
		/*
		 * verify newly created job is displayed in middle of jobsPage,
		 * openpositions of jobspage and homepage openpositions.
		 */

		String Newjobpost = getElementText(driver, recentlypostedJobTitleInCenterOfTheJobsPage);
		// String openPostion = getElementText(driver,
		// jobTitleInJobsPageOpenPosiion);
		Assertion.assertEquals(Newjobpost, jobTitle, "Newlycreated Job displayed in centre of the jobs page");
		// Assertion.assertEquals(openPostion, jobTitle,
		// "Newlycreated Job displayed in open positions in jobsPage");
		clicElement(driver, homePage);
		String HomePagejobpost = getElementText(driver, homePageRecentJobPOst);
		Assertion.assertEquals(HomePagejobpost, jobTitle, "Newlycreated Job displayed in homepage open positions");
	}

	/*
	 * verify uploadFile icon by Uploading complete JD text file
	 */
	public void uploadFile(JobPostBean job) {
		clicElement(driver, jobsTab);
		clicElement(driver, uploadFile);
		clicElement(driver, browseFile);
		
		String filePath = ("D://JobPost.txt");
		WebElement element = driver.findElement(browseFile);
		element.sendKeys(filePath);
		clicElement(driver, uploadBtn);
		clicElement(driver, uploadBtn);
		String popup = getElementText(driver, uploadedFilePopup);

		Assertion.assertEquals(popup, "File uploaded successfully!", "File uploaded successfully");

		clicElement(driver, previewBtn);
		clicElement(driver, editLinkURL);
		String jobTitle = job.getJobTitle();
		enterText(driver, titleTextBox, jobTitle);
		enterText(driver, companyTextBox, job.getCompanyName());
		selectdropdownBytext(driver, educationTextField, job.getEducation());
		enterText(driver, descriptionTextArea, job.getDescription());
		clicElement(driver, nextBtn1);
		selectdropdownBytext(driver, countryTextField, job.getCountry());
		selectdropdownBytext(driver, stateTextField, job.getState());
		enterText(driver, cityTextField, job.getCity());
		enterText(driver, addressLane, job.getAddressLane());
		enterText(driver, postalCodeTextField, job.getPostalCode());
		clicElement(driver, newBtn2);
		enterText(driver, nameTextField, job.getName());
		enterText(driver, emailTextField, job.getEmail());
		clicElement(driver, newBtn3);
		clicElement(driver, previewJobBtn);
		clicElement(driver, postBtn1);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * verify newly created job is displayed in middle of jobsPage,
		 * openpositions of jobspage and homepage openpositions.
		 */

		String Newjobpost = getElementText(driver, recentlypostedJobTitleInCenterOfTheJobsPage);
		// String openPostion = getElementText(driver,
		// jobTitleInJobsPageOpenPosiion);
		Assertion.assertEquals(Newjobpost, jobTitle, "Newlycreated Job displayed in centre of the jobs page");
		driver.navigate().refresh();
		// Assertion.assertEquals(openPostion, jobTitle,
		// "Newlycreated Job displayed in open positions in jobsPage");
		clicElement(driver, homePage);
		String HomePagejobpost = getElementText(driver, homePageRecentJobPOst);
		Assertion.assertEquals(HomePagejobpost, jobTitle, "Newlycreated Job displayed in homepage open positions");
	}

	/*
	 * verify postJob icon by filling all the mandatory fields.
	 */
	public void createNewJobPost(JobPostBean job) {
		clicElement(driver, jobsTab);
		clicElement(driver, createNewJobPost);
		String openJobTilte = job.getJobTitle();
		job.setTitle(openJobTilte);
		enterText(driver, titleTextBox, openJobTilte);
		enterText(driver, companyTextBox, job.getCompanyName());
		selectdropdownBytext(driver, educationTextField, job.getEducation());
		enterText(driver, descriptionTextArea, job.getDescription());
		enterText(driver, skillsTextField, job.getSkills());
		driver.findElement(skillsTextField).sendKeys(Keys.ENTER);
		clicElement(driver, nextBtn1);
		selectdropdownBytext(driver, countryTextField, job.getCountry());
		selectdropdownBytext(driver, stateTextField, job.getState());
		enterText(driver, cityTextField, job.getCity());
		enterText(driver, addressLane, job.getAddressLane());
		enterText(driver, postalCodeTextField, job.getPostalCode());
		clicElement(driver, newBtn2);
		enterText(driver, nameTextField, job.getName());
		enterText(driver, emailTextField, job.getEmail());
		clicElement(driver, newBtn3);
		clicElement(driver, previewJobBtn);
		clicElement(driver, postBtn);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().refresh();
		/*
		 * verify newly created job is displayed in middle of jobsPage,
		 * openpositions of jobspage and homepage openpositions.
		 */

		//String Newjobpost = getElementText(driver, recentlypostedJobTitleInCenterOfTheJobsPage);
		// String openPostion = getElementText(driver,
		// jobTitleInJobsPageOpenPosiion);
		//Assertion.assertEquals(Newjobpost, openJobTilte, "Newlycreated Job displayed in centre of the jobs page");
		// Assertion.assertEquals(openPostion, openJobTilte,
		// "Newlycreated Job displayed in open positions in jobsPage");
		clicElement(driver, homePage);
		String HomePagejobpost = getElementText(driver, homePageRecentJobPOst);
		Assertion.assertEquals(HomePagejobpost, openJobTilte, "Newlycreated Job displayed in homepage open positions");

	}

	/*
	 * post a job and verify it is displayed in open positions or not and
	 * onclick of the requisition ID it is navigating to search page or not.
	 */
	public void verifyRequisitionID_OpenPosition(JobPostBean job) {
		clicElement(driver, jobsTab);
		// createNewJobPost(job);
		clicElement(driver, firstRequisitionInOpenposition);
		waitForElementToBeVisible(driver, searchedResults);
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Search Results";

		Assertion.assertEquals(actualPageTitle, expectedPageTitle, "Search page is not dispalyed");

	}

	/*
	 * post a job and verify it is displayed in open positions or not and
	 * onclick of the jobTitle ID it is navigating to search page or not.
	 */
	public void verifyJobTitle_OpenPosition(JobPostBean job) {
		clicElement(driver, jobsTab);
		// createNewJobPost(job);
		clicElement(driver, jobTitleInJobsPageOpenPosiion);
		waitForElementToBeVisible(driver, searchedResults);
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Search Results";

		Assertion.assertEquals(actualPageTitle, expectedPageTitle, "Search page is not dispalyed");

	}

	/*
	 * post a job and verify it is displayed in open positions or not and
	 * onclick of the Plus(+) icon ID it is navigating to search page or not.
	 */
	public void verifyMultipleJobPost_OpenPosition(JobPostBean job) {
		clicElement(driver, jobsTab);
		// createNewJobPost(job);
		clicElement(driver, plusIconInOpenPositions);
		waitForElementToBeVisible(driver, fillJobDetailsText);
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Preview Job";
		Assertion.assertEquals(actualPageTitle, expectedPageTitle, "edit page is not dispalyed");
	}

	/*
	 * Verify OpenPositions RequisitionID link in homePage is navigating to
	 * "Search Results" page
	 */

	public void verifyHomepage_OpenPositionRI(JobPostBean job) {
		// createNewJobPost(job);
		clicElement(driver, homePageRequisitionId);
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Search Results";
		Assertion.assertEquals(actualPageTitle, expectedPageTitle, "Search page is not dispalyed");
	}

	/*
	 * Verify OpenPositions JobTitle link in homePage is navigating to
	 * "Search Results" page
	 */

	public void verifyHomepage_OpenPositionJobTitle(JobPostBean job) {
		// createNewJobPost(job);
		clicElement(driver, homePageOpenPositionsJobTitle);
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Search Results";
		Assertion.assertEquals(actualPageTitle, expectedPageTitle, "Search page is not dispalyed");
	}

	/*
	 * Verify OpenPositions Plus(+) link in homePage is navigating to
	 * "Preview Job" Page
	 */

	public void verifyHomepageMultipleJobpost(JobPostBean job) {
		// createNewJobPost(job);
		clicElement(driver, homepagePlusIconOpenPosition);
		waitForElementToBeVisible(driver, fillJobDetailsText);
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Preview Job";
		Assertion.assertEquals(actualPageTitle, expectedPageTitle, "Search page is not dispalyed");
	}

	/*
	 * post a job and Close the Requisition from open positions
	 */

	/*public void closeRequisition(JobPostBean job) throws JsonParseException, JsonMappingException, IOException {
		 createNewJobPost(job);
		job.setTitle("AutomationTestEngineer1f586");
		JobPost openJob = RequisitionHelper.getRequisitionIDbyName(job.getTitle());
		
		TalentBizLayerConsumer talentBizLayerConsumer = new TalentBizLayerConsumer();

		RequisitionBean requisition = new RequisitionBean();

		requisition.setId(openJob.getId());
		requisition.setDisplayId(openJob.getDisplayId());
		requisition.setStatusCode("CLOSED");
		requisition.setStatusDisplay("closed");
		talentBizLayerConsumer.updateRequisition(requisition);

	}*/
	/*
	 * Random string
	 */

	/*public String Title() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		Logging.log("Random UUID String = " + randomUUIDString);
		return randomUUIDString.substring(0, 5);
	}*/
}

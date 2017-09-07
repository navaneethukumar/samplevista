package com.spire.crm.activitytream;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.spire.base.helper.WebPageHelper;
import com.spire.common.ProfileHelper;
import com.spire.crm.pageUtils.EngageCandidateDeatilsPageUtils;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pages.HomePage;

public class ValidateActivityStreamDataUtil extends HomePage {

	public ValidateActivityStreamDataUtil(WebDriver driver, Boolean openurl) {
		super(driver, openurl);

	}

/* It validates activity data like, activity logs, email icon,campaign icon,tags icons are displayed in home page or not.
 * 
 */
	public void ValidateActivityStreamData() throws Exception {

		HomePageUtil hp = new HomePageUtil(driver, true);
		hp.ValidateActivityData();
	}

	/*
	 * create candidate using rest service, store the id in a string
	 * candidateID.
	 */
	public void CreateCandidate()
	{
	String candidateID = ProfileHelper.createProfile();
	EngageCandidateDeatilsPageUtils candiateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(this.driver, true,
			candidateID);
	}
	/*
	 * check on click of email link it is opening the sent email. create a
	 * candidate n send a mail to him, it will create a log in activity stream,
	 * click on the email link and chech it is displaying the sent mail by
	 * opening small window(View Message).
	 */
	public By go2home=By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[1]/a");
	public void ValidateEmailLink() {
		String candidateID =ProfileHelper.createProfile("sngt.m1@gmail.com");
		
		EngageCandidateDeatilsPageUtils candiateDeatilsPageUtils = new EngageCandidateDeatilsPageUtils(this.driver, true,
				candidateID);
		WebPageHelper.waitForElementToBeVisible(driver, candImg);
		//candiateDeatilsPageUtils.crmStage();
		candiateDeatilsPageUtils.sendEmail();
		
		
		WebPageHelper.clicElement(driver, go2home);
		WebPageHelper.clicElement(driver, go2home);
		WebPageHelper.clicElement(driver, go2home);
		WebPageHelper.waitForElementToBeVisible(driver, firstCandidateEmailLink);
		WebPageHelper.clicElement(driver, firstCandidateEmailLink);
		getCurrentWindowTitle();

		String title2 = "View Message";
		Assert.assertNotEquals(getCurrentWindowTitle(), title2);

	}

	/*
	 * after sending email to candidate we will get log in home page, in that
	 * log click on candidate name link, it should display same candidates
	 * details page. compare the name displayed in log and the name displayed in
	 * details page.
	 */
	public By candName=By.xpath(".//*[@id='detailProfileColDiv']//div[1]/strong");
	public By candImg=By.xpath("//img[@class='img-responsive profile-image']");
	
	public void ValidateCandidateNameLink() {

		WebPageHelper.waitForElementToBeVisible(driver, firstCandidateNameLink);
		String homePageCandidateName = WebPageHelper.getElementText(driver, firstCandidateNameLink);
		WebPageHelper.clicElement(driver, firstCandidateNameLink);
		WebPageHelper.waitForElementToBeVisible(driver, candImg);
		String title = driver.getTitle();
		String title2 = "Candidate Profile";
		Assert.assertEquals(title, title2);
		
		WebPageHelper.waitForElementToBeVisible(driver, candName);
		String detailPageCandidateName = WebPageHelper.getElementText(driver, candName);
		Assert.assertNotEquals(homePageCandidateName, detailPageCandidateName);
	}

	/*
	 * returns the title of the window in string.
	 */
	public String getCurrentWindowTitle() {
		String name = driver.getWindowHandle();
		return name;
	}

	/*
	 * returns the activities displayed count(max count should be 10) in
	 * homePage
	 */
	public int getactivityCount() {
		List<WebElement> els = driver.findElements(By.id("crmCandidateImg"));
		int count = els.size();
		Assert.assertNotEquals(count, 11);
		return count;

	}

	/*
	 * check whether the pagination btn is displayed o not. check in each page
	 * should have max 10 logs. should not display more than 10.
	 */
	public void ValidatePagination() throws InterruptedException {
		List<WebElement> allpages = driver.findElements(paginationBtnAllPages);
		System.out.println(allpages.size());
		if (allpages.size() > 0) {
			System.out.println("Pagination exists");
			for (int i = 0; i < allpages.size(); i++) {
				Thread.sleep(3000);
				allpages.get(i).click();
				driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
				getactivityCount();
				// System.out.println(i);
			}
		} else {
			System.out.println("Pagination doesn't exists");
		}
	}
}

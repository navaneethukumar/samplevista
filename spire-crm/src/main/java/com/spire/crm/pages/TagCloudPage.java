package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class TagCloudPage extends BasePage {

	public static String url = "/UI/src/app/index.html#/tagCloud";

	public TagCloudPage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext().getUIHostAddress() + url;
			this.driver.get(ui_url);
		}

	}

	public By viewAllLink = By.xpath(".//*[@id='tagsColDiv']//*[@id='detProTagCloud']");
	public By popularTagsHeader = By.xpath(".//*[@id='ngViewDiv']/div/div/div[1]/div[1]");
	public By tagCloudBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[1]//*[@id='tagMdSwitch']/div[1]/div[2]/div");
	public By createdOnBtn = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[4]/span[2]");
	public By newlyCreatedLabel = By.xpath(".//*[@id='label-dashboard']/div[1]/table/tbody/tr[1]/td[2]");
	public By actions = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[1]");
	public By labelName = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[2]");
	public By createdBy = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[3]");
	public By createdOn = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[4]/span[1]");
	public By users = By.xpath(".//th[text()='Users']");
	public By count = By.xpath(".//span[text()='Count']");
	public By countBtn = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[6]/span[2]");
	public By fullPaginationBtn = By.xpath(".//*[@id='label-dashboard']/div[1]/table/thead/tr/th[6]/span[2]");
	public By firstShareBtn = By.xpath(".//*[@id='label-dashboard']/div[1]/table/tbody/tr[1]/td[1]/span");
	public By previousBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div[1]/div/ul/li[1]/a");
	public By nextBtn = By.xpath(".//*[@id='ngViewDiv']/div/div/div[2]/div[1]/div/ul/li[2]/a");
	public By tagBtn = By.id("tagOverLayImg");
	public By enterLabelName = By.id("labelsTypeAhead");
	public By saveLabelBtn = By.id("tagOverlaySave");
	public By saveLabel=By.xpath(".//*[@id='candProLi']/div[3]/div//*[@id='tagOverlaySave']");
	public By labelSavedPopUp = By.xpath("html/body/md-toast/span");
	public By homePageTagBtn = By.id("tagOverLayImg");
	public By homePageEngage = By.id("HGraphA_1");
	public By tagIconInSelectAllEngagedpage = By.id("tagOverLayImg");
	public By selectAll = By.id("candSelectAllSpan");
	public By enterTagNameSelectAll =By.id("labelsTypeAhead");
	public By engagetagIcon = By.xpath(".//*[@id='candProLi']/div[3]/div//*[@id='tagOverLayImg']");
	public By enterLabelNameInEngaedPag = By.xpath(".//*[@id='candProLi']/div[3]/div//*[@id='labelsTypeAhead']");
	public By searchBtn = By.xpath(".//*[@id='tagMdSwitch']/div[1]/div[1]");
	public By freeTextBox = By.id("button-template-url");
	public By enterText = By.xpath(".//*[@id='SearchDropDown']/div[1]/input");
	public By clickSearchBtn = By.id("HeaderSearchBtn");
	public By tag = By.xpath(".//*[@id='candProLi']/div[3]/div//*[@id='tagOverLayImg']");
	public By firstTagInHomepage=By.id("crmPopularTagsBadge");
	

}

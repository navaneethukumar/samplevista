package com.spire.crm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.spire.base.controller.ContextManager;
import com.spire.crawl.pages.BasePage;

public class EngageCandidatePage extends BasePage {

	public static String url = "/UI/src/app/index.html#/Engage/Candidates";

	public EngageCandidatePage(WebDriver driver, Boolean openurl) {
		super(driver);

		if (openurl) {

			String ui_url = ContextManager.getGlobalContext()
					.getUIHostAddress() + url;
			this.driver.get(ui_url);
		}

	}

	public By home_Engage = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div/ul/li[1]/a");
	public By campaigns = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div/ul/li[2]/a");
	public By emailTemplates = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div/ul/li[3]/a");
	public By engmtScoreRules = By
			.xpath(".//*[@id='ngViewDiv']/div/div/div/ul/li[4]/a");
	public By crm_Stages = By
			.xpath(".//*[@id='crmPipelineListItem']/div/label");
	public By crm_Stages_Minimize = By
			.cssSelector(".collapse-icon.glyphicon.glyphicon-minus");
	public By engmtScoreIcon = By
			.cssSelector(".ngrs-range-slider.ng-isolate-scope.ngrs-horizontal");
	public By decreaseScore = By
			.cssSelector(".ngrs-handle.ngrs-handle-min.ngrs-over>i");
	public By lastEngagementTime = By
			.cssSelector(".col-sm-6.col-xs-12.last-engage>a");
	public By selectAll = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div[2]/div[1]/div[2]/div[2]/label/span");
	public By message = By.cssSelector(".icon-message>img");
	public By tag = By.cssSelector(".icon-tag-left>img");
	public By email = By.cssSelector(".icon-mail>img");
	public By profileNames = By
			.cssSelector(".col-sm-8.col-xs-12.cand-name.ng-binding");
	public By crmStageOfCandidate = By
			.cssSelector(".col-md-3.col-sm-4.col-md-offset-1.col-xs-12.stage-type.ng-binding");
	public By lastEngaged = By
			.cssSelector(".col-sm-6.col-md-offset-3.col-sm-offset-2.last-engaged.ng-binding");
	public By currentRole = By
			.cssSelector(".col-sm-12.col-xs-12.summary.ng-binding");
	public By experience = By.cssSelector(".experience.col-sm-12");
	public By candidateImages = By
			.cssSelector(".col-md-3.col-sm-4.action-btns>a>img");
	public By scores = By
			.cssSelector(".col-md-1.col-md-offset-1.col-sm-1.col-xs-3.engage-score");
	public By listCampaigns = By
			.cssSelector(".text-align-center.blue-text.list-first-col-name");
	public By campaignActivity = By
			.cssSelector(".col-md-3.text-align-center.grey-text.text-14");
	public By createCampaign = By
			.cssSelector(".header-btn-right.headerBtn.createBtn.ng-binding");

	public By templateHeader = By.cssSelector(".sub-heading");
	public By templateList = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div/ul/li[2]/div[1]");
	public By templateCreatedOn = By.cssSelector(".col-sm-2.col-xs-2");
	public By templateCreatedBy = By.cssSelector(".col-sm-2.col-xs-2");
	public By templateReadCount = By.cssSelector(".col-sm-2.col-xs-2");
	public By templateNewButton = By
			.cssSelector(".btn.btn-small.spire-btn-create.ng-binding");
	public By templateDeleteButton = By
			.cssSelector(".glyphicon.glyphicon-trash.spire-btn-delete");
	public By templateEditButton = By
			.cssSelector(".glyphicon.glyphicon-pencil.spire-btn-edit");
	public By engRules_listOfRules = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div[2]/div/div/ul/li[1]/div/div[1]/span");
	public By engRules_createdBy = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div[2]/div/div/ul/li[1]/div/div[3]/span");
	public By engRules_createdAt = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div[2]/div/div/ul/li[1]/div/div[4]/span");
	public By engRules_actionName = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div[2]/div/div/ul/li[1]/div/div[5]/span");
	public By engRules_points = By
			.xpath(".//*[@id='ngViewDiv']/ui-view/div/div/div[2]/div/div/ul/li[1]/div/div[2]/span");
	public By engRules_editRuleButton = By
			.cssSelector(".glyphicon.glyphicon-pencil.spire-btn-edit");
	public By engRules_deleteRuleButton = By
			.cssSelector(".glyphicon.glyphicon-remove.spire-disabled-btn-grid");
	public By engRules_newRuleButton = By
			.cssSelector(".btn.btn-small.spire-btn-create");

}

package com.spire.acqura.search.pageUtils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.acqura.search.pages.HomePage;
import com.spire.acqura.search.pages.UploadCandidatesPage;
import com.spire.base.controller.Logging;

public class UploadCandidatesPageUtil extends UploadCandidatesPage  {
	
	public UploadCandidatesPageUtil(WebDriver driver, Boolean openurl) {

		super(driver, openurl);

	}
	
	public UploadCandidatesPageUtil(WebDriver driver, String openurl) {

		super(driver, openurl);

	}

	


}

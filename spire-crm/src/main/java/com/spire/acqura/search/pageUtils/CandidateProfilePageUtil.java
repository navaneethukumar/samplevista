package com.spire.acqura.search.pageUtils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.acqura.search.pages.HomePage;
import com.spire.base.controller.Logging;

public class CandidateProfilePageUtil extends HomePage  {
	
	public CandidateProfilePageUtil(WebDriver driver, Boolean openurl) {

		super(driver, openurl);

	}
	
	public CandidateProfilePageUtil(WebDriver driver, String openurl) {

		super(driver, openurl);

	}

	
}

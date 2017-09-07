package com.spire.acqura.search.pageUtils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.acqura.search.pages.AdvancedSearchPage;
import com.spire.acqura.search.pages.HomePage;
import com.spire.base.controller.Logging;

public class AdvancedSearchPageUtils extends AdvancedSearchPage  {
	
	public AdvancedSearchPageUtils(WebDriver driver, Boolean openurl) {

		super(driver, openurl);

	}
	
	public AdvancedSearchPageUtils(WebDriver driver, String openurl) {

		super(driver, openurl);

	}

	


}

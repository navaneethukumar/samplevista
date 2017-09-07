package com.spire.acqura.search.pageUtils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.spire.acqura.search.pages.HomePage;
import com.spire.acqura.search.pages.ManageLabelsPage;
import com.spire.base.controller.Logging;

public class ManageLabelsPageUtil extends ManageLabelsPage  {
	
	public ManageLabelsPageUtil(WebDriver driver, Boolean openurl) {

		super(driver, openurl);

	}
	
	public ManageLabelsPageUtil(WebDriver driver, String openurl) {

		super(driver, openurl);

	}

	


}

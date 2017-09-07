package com.spire.crm.pageUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestUtilMain {

	public static void main(String[] args) throws Exception {
		
		WebDriver driver=new FirefoxDriver();
		
		LoginPageUtil obj2 = new LoginPageUtil(driver,true);
		obj2.login();
		HomePageUtil obj = new HomePageUtil(driver,true);
		obj.clickPopularTagsViewAllLink();


	}

}

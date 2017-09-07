package com.spire.common;

import org.openqa.selenium.TimeoutException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.helper.WebPageHelper;
import com.spire.crm.pageUtils.LoginPageUtil;

public class UITestPlan extends TestPlan {

	public LoginPageUtil loginPageUtil = null;
	
	public UITestPlan(){
		
	}
	
	@BeforeMethod(alwaysRun = true)
	@BeforeTest(alwaysRun = true)
	public void loginBeforeTest() {
		
		try {
			if (loginPageUtil==null) 			
				login();
					
			if (loginPageUtil!=null) {
				
				try{				
					System.out.println(loginPageUtil.getTitle(loginPageUtil.driver)); 
				}catch(Exception e){
					loginPageUtil.driver.quit();
					login();
				}
				
				
			}
			WebPageHelper.sleep(2000);
		} catch (InterruptedException | TimeoutException e) {
			e.printStackTrace();
			loginPageUtil=null;
		}
		
	}
	
	@AfterTest(alwaysRun = true)
	public void logoutAfterTest() throws InterruptedException {
		
		if (loginPageUtil!=null) {
			loginPageUtil.driver.quit();
		}
		
	}
	
	public void login() throws InterruptedException{
		
		String userId=null,password=null,url=null;
		
		userId=ContextManager.getThreadContext().getUserid();	
		password=ContextManager.getThreadContext().getPassword();
		url=ContextManager.getThreadContext().getUIHostAddress();
		
		System.out.println("------------------------------");
		System.out.println(userId);
		System.out.println(password);
		System.out.println(url);
		System.out.println("------------------------------");
		
		/*if (testContexInfo!=null && ContextManager.getTestLevelContext(testContexInfo.getCurrentXmlTest().getName())!=null) 
			userId=ContextManager.getTestLevelContext(testContexInfo.getCurrentXmlTest().getName()).getUserid();
		if (testContexInfo!=null && ContextManager.getTestLevelContext(testContexInfo.getCurrentXmlTest().getName())!=null) 
			password=ContextManager.getTestLevelContext(testContexInfo.getCurrentXmlTest().getName()).getPassword();
		if (testContexInfo!=null && ContextManager.getTestLevelContext(testContexInfo.getCurrentXmlTest().getName())!=null) 
			url=ContextManager.getTestLevelContext(testContexInfo.getCurrentXmlTest().getName()).getUIHostAddress();
				
		if (userId==null) 
			userId=ContextManager.getThreadContext().getUserid();	
		if (password==null) 
			password=ContextManager.getThreadContext().getPassword();
		if (url==null) 
			url=ContextManager.getThreadContext().getUIHostAddress();*/

		loginPageUtil = new LoginPageUtil(null, url);
		initializeDriver(driver, null);
		loginPageUtil.federationLogin(userId,password);
		Thread.sleep(2000);
	}

}

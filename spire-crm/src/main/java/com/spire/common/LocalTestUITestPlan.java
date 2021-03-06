package com.spire.common;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import spire.crm.profiles.bean.Profile;

import com.spire.base.controller.ContextManager;
import com.spire.base.controller.TestPlan;
import com.spire.crm.pageUtils.LoginPageUtil;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;
import com.spire.crm.smoke.helper.SmokeTestHelper;

public class LocalTestUITestPlan extends TestPlan {
	
	

	//public LoginPageUtil loginPageUtil = null;
	public String USERNAME = ContextManager.getThreadContext().getUserid();
	public String PASSWORD = ContextManager.getThreadContext().getPassword();
	public String URL = ContextManager.getThreadContext().getUIHostAddress();
/*	public String DB_HOST = "mysql-prod-db.clapjg0gnhyn.us-east-1.rds.amazonaws.com";
	public String DB_PASSWORD = "SpireAiZie1qu123";
	public String SCHEMA_NAME = "star_schema";*/
	public String SEARCH_MATCH_HOST = ContextManager.getThreadContext().getSEARCHHOSTADDRESS();
	public static String CANDIDATE_ID = null;
	public String CANDIDATE_ID_SCORE = null;
	
	public LocalTestUITestPlan(){
		
	}
	
	@BeforeTest(alwaysRun = true)
	public void createCandidateBeforeTest() throws InterruptedException {
				
			if(CANDIDATE_ID==null)
				CANDIDATE_ID= ContextManager.getThreadContext().getCandidateId();
			
			if (this.CANDIDATE_ID==null || this.CANDIDATE_ID.equalsIgnoreCase("123456")) {
				
				Profile request = SmokeTestHelper.getProfileJson("candidate1.json");

				/*int initialCount = SmokeTestHelper.getTotalCountFromDB(this.DB_HOST, "root", this.DB_PASSWORD,
						this.SCHEMA_NAME, "candidate");*/

				ProfileBizServiceConsumer consumer = new ProfileBizServiceConsumer(this.USERNAME, this.PASSWORD);
				consumer.HEADERS = false;
				this.CANDIDATE_ID = consumer.createProfile(request);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Created candidateId: " + this.CANDIDATE_ID);

				/*int countAferCandidateCreation = SmokeTestHelper.getTotalCountFromDB(this.DB_HOST, "root", this.DB_PASSWORD,
						this.SCHEMA_NAME, "candidate");*/
				Assert.assertTrue(this.CANDIDATE_ID !=null, "Created candidate not storing in DB");
			}
			
	}
	
	/*@BeforeMethod(alwaysRun = true)
	@BeforeTest(alwaysRun = true)*/
	public void loginBeforeTest() throws InterruptedException {
		
		/*if (loginPageUtil==null) 			
			login();
				
		if (loginPageUtil!=null) {
			
			try{				
				System.out.println(loginPageUtil.getTitle(loginPageUtil.driver)); 
			}catch(Exception e){
				loginPageUtil.driver.quit();
				login();
			}
			
			
		}
		
		Thread.sleep(2000);*/
		
	}
	
	/*@AfterTest(alwaysRun = true)
	public void logoutAfterTest() throws InterruptedException {
		
		if (loginPageUtil!=null) {
			loginPageUtil.driver.quit();
		}
		
	}*/
	
	@AfterMethod(alwaysRun = true)
	public void afterMethod() throws InterruptedException {
		if (this.driver!=null)
			this.driver.quit();
	}
	
	public WebDriver login() throws InterruptedException{
		
	/*	String userId=null,password=null,url=null;
		
		userId="user@star.com";	
		password="spire@123";
		url="http://test2.vista.corp.spire.com/UI/src/app/index.html#/";
		
		System.out.println("------------------------------");
		System.out.println(userId);
		System.out.println(password);
		System.out.println(url);
		System.out.println("------------------------------");*/
		
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

		LoginPageUtil loginPageUtil = new LoginPageUtil(null, URL);
		initializeDriver(driver, null);
		loginPageUtil.federationLogin(USERNAME,PASSWORD);
		Thread.sleep(2000);
		return loginPageUtil.driver;
	}

}

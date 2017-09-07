package com.spire.crm.email.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.UITestPlan;
import com.spire.crm.email.EmailBean;
import com.spire.crm.email.EmailsUtil;
import com.spire.crm.pageUtils.HomePageUtil;
import com.spire.crm.pageUtils.LoginPageUtil;


	/**
	 * 
	 * @author Sangeeta
	 *
	 */
	@Test(groups = { "Sanity" }, retryAnalyzer = TestRetryAnalyzer.class)
	public class EmailTestPlan extends UITestPlan {

	@DataProvider(name = "EmailTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/email/test/EmailTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("EmailBean", EmailBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(EmailTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

		@Test(groups = { "VerifyElementsPresent" ,"Sanity"}, dataProvider = "EmailTestData")
		public void VerifyElementsPresent(SpireTestObject testObject,EmailBean email) {

			new HomePageUtil(loginPageUtil.driver, false);
		EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

		emailTemp.VerifyElementsPresent();

	}

	@Test(groups = { "CreateNewTemplate" ,"Sanity"}, dataProvider = "EmailTestData")
	public void CreateNewTemplate(SpireTestObject testObject, EmailBean email) {

		new HomePageUtil(loginPageUtil.driver, false);
		EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

		emailTemp.CreateNewTemplate(email);

		}
	@Test(groups = { "CreateFeedbackTemplate" ,"Sanity"}, dataProvider = "EmailTestData")
	public void CreateFeedbackTemplate(SpireTestObject testObject,EmailBean email) throws InterruptedException {

		new HomePageUtil(loginPageUtil.driver, false);
		EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

		emailTemp.CreateFeedbackTemplate();

		}
	/*@Test(groups = { "CreateRichTextTemplate" ,"Sanity"}, dataProvider = "EmailTestData")
	public void CreateRichTextTemplate(SpireTestObject testObject, EmailBean email) {

		new HomePageUtil(loginPageUtil.driver, true);
		EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, false);

		emailTemp.CreateRichTextTemplateTemplate(email);

		}*/
		@Test(groups = { "DeleteTemplate" ,"Sanity" }, dataProvider = "EmailTestData")
		public void DeleteTemplate(SpireTestObject testObject,EmailBean email) throws InterruptedException {

			new HomePageUtil(loginPageUtil.driver, false);

			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

			emailTemp.DeleteTemplate(email);

		}
		@Test(groups = { "EditTemplate" ,"Sanity"}, dataProvider = "EmailTestData")
		public void EditTemplate(SpireTestObject testObject,EmailBean email) {

			new HomePageUtil(loginPageUtil.driver, false);
			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

			emailTemp.EditTemplate(email);

		}
/*@Test(groups = { "ValidateDynamicFieldOfEmail","SRG"}, dataProvider = "EmailTestData")
		public void ValidateDynamicFieldOfEmail(SpireTestObject testObject,EmailBean email) {

			new HomePageUtil(loginPageUtil.driver, true);
			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, false);

			emailTemp.ValidateDynamicFieldOfEmail(email);

		}*/
		@Test(groups = { "SendEmail" ,"Sanity"}, dataProvider = "EmailTestData")
		public void SendEmail(SpireTestObject testObject,EmailBean email) throws InterruptedException {

			new HomePageUtil(loginPageUtil.driver, false);
			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

			emailTemp.SendEmail(email);
		}
		@Test(groups = { "scheduleEmail" ,"Sanity"}, dataProvider = "EmailTestData")
		public void scheduleEmail(SpireTestObject testObject,EmailBean email) throws InterruptedException {

			new HomePageUtil(loginPageUtil.driver, false);
			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, true);

			emailTemp.scheduleEmail(email);
		}
	/*	
		@Test(groups = { "SendEmailWithFeedback" ,"Sanity"}, dataProvider = "EmailTestData", priority=7)
		public void SendEmailWithFeedback(SpireTestObject testObject,EmailBean email) {

			new HomePageUtil(loginPageUtil.driver, true);
			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, false);

			emailTemp.SendEmailWithFeedback();

		}*/
		/*@Test(groups = { "sentEmailNverifyES","LRG" }, dataProvider = "EmailTestData", priority=8)
		public void sentEmailNverifyES(SpireTestObject testObject,EmailBean email) {

			new HomePageUtil(loginPageUtil.driver, true);

			EmailsUtil emailTemp = new EmailsUtil(loginPageUtil.driver, false);

			emailTemp.sentEmailNverifyES(email);

		}*/
		
}

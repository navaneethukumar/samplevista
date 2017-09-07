package com.spire.crm.biz.emails.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.commons.config.entities.SpireConfiguration;
import spire.crm.biz.com.service.beans.SendCustomEmailVO;
import spire.crm.biz.com.service.beans.SendEmailWithTemplateVO;
import spire.crm.entity.com.entities.Template;

import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.EmailQueryParamPojo;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.EmailsBizConsumer;

import crm.activitystream.beans.ActivitySearchFilter;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class EmailsBizServiceTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;
	EmailsBizConsumer emailsBizConsumer = null;

	//private static Logger logger = LoggerFactory.getLogger(EmailsBizServiceTestPlan.class);
	SpireConfiguration spireConfiguration = null;
	String id = null;
	final static String DATAPROVIDER_NAME = "EMAIL_BIZ";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/biz/emails/test/";
	final static String CSV_FILENAME = "EmailsBizService_TestData.csv";
	final static String CSV_PATH = CSV_DIR + CSV_FILENAME;

	String candidateId = null;

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = CSV_PATH;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			entityClazzMap.put("Template", Template.class);
			entityClazzMap.put("EmailQueryParamPojo", EmailQueryParamPojo.class);
			entityClazzMap.put("SendEmailWithTemplateVO", SendEmailWithTemplateVO.class);
			entityClazzMap.put("SendCustomEmailVO", SendCustomEmailVO.class);
			entityClazzMap.put("ActivitySearchFilter", ActivitySearchFilter.class);
			
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(EmailsBizServiceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}
	
	@DataProvider(name = "EMAIL_DP")
	public static Iterator<Object[]> getEmailTestMethodsInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/emails/test/EmailsBizServiceSanityTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);						
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(EmailsBizServiceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@BeforeClass(alwaysRun = true)
	public void setUp() {

		emailsBizConsumer = new EmailsBizConsumer();
	}

	
	/**
	 * Create Template 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 * @param activity
	 */
	@Test(groups = { "verify_createTemplates", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_createTemplates(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailWithTemplateVO sendEmailInput,SendCustomEmailVO sendCustomEmailInput,
			ActivitySearchFilter activity) {
		
		ComBizValidationHelper comBizValidationHelper = new ComBizValidationHelper();
		comBizValidationHelper.validateEmailTemplate(template,testObject);
				
	}
	
	/**
	 *
	 Delete Template
	 */
	@Test(groups = { "verify_deleteTemplate", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_deleteTemplate(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailWithTemplateVO sendEmailInput,SendCustomEmailVO sendCustomEmailInput,ActivitySearchFilter activity) {
		
		ComBizValidationHelper comBizValidationHelper = new ComBizValidationHelper();
		comBizValidationHelper.validateDeleteTemplate(template,testObject);
		
	}
	
	
	@Test(groups = { "verify_listTemplate", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_listTemplate(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailWithTemplateVO sendEmailInput,
			SendCustomEmailVO sendCustomEmailInput, ActivitySearchFilter activity) {
		
		ComBizValidationHelper comBizValidationHelper = new ComBizValidationHelper();
		comBizValidationHelper.validatelistAllTemplate(template,testObject);
	}
	
	
	@Test(groups = { "verify_editTemplate", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_editTemplate(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailWithTemplateVO sendEmailInput,SendCustomEmailVO sendCustomEmailInput,ActivitySearchFilter activity) {
		ComBizValidationHelper comBizValidationHelper = new ComBizValidationHelper();
		comBizValidationHelper.validateEditTemplate(template,testObject);

	}
	

	@Test(groups = { "verify_sendEmail", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_sendEmail(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailWithTemplateVO sendEmailInput,SendCustomEmailVO sendCustomEmailInput,ActivitySearchFilter activity) {
		ComBizValidationHelper comBizValidationHelper = new ComBizValidationHelper();
		comBizValidationHelper.sendEmailSingle(template, emailQueryParam, sendEmailInput);
		
	}
	
	@Test(groups = { "verify_sendCustomEmail", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_sendCustomEmail(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailWithTemplateVO sendEmailInput,SendCustomEmailVO sendCustomEmailInput,ActivitySearchFilter activity) {
		ComBizValidationHelper comBizValidationHelper = new ComBizValidationHelper();
		comBizValidationHelper.sendCustomEmail(template, emailQueryParam, sendEmailInput);
		
	}
	
	@Test(groups = { "verify_RecruiterFields", "Sanity" }, dataProvider ="EMAIL_DP")
	public void verify_RecruiterFields(SpireTestObject testObject, TestData data) {
		ComBizValidationHelper validationHelper = new ComBizValidationHelper();
	
			try {
				validationHelper.verifyRecruiterFields(testObject,data);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				Assert.fail("Run Time Exception >>>" +e.getMessage());
			}
		
		
	}
	
	/**
	 * Method to verify notification created for unsubscribed Candidates 
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_NtoficationForUnsubscribe", "Sanity" }, dataProvider ="EMAIL_DP")
	public void verify_NtoficationForUnsubscribe(SpireTestObject testObject, TestData data) {
		
		ComBizValidationHelper validationHelper = new ComBizValidationHelper();
		try {
			validationHelper.validateNotification();
		} catch (SQLException | InterruptedException e) {		
			e.printStackTrace();
			Assert.fail("Run Time exception  "+e.getMessage());
		}
		
	}
	
	
	/**
	 * Method to verify notification created for remind me
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_remindMe", "Sanity" }, dataProvider ="EMAIL_DP")
	public void verify_remindMe(SpireTestObject testObject, TestData data) {		
		ComBizValidationHelper validationHelper = new ComBizValidationHelper();
		validationHelper.validateRemindMe();				
	}		
}

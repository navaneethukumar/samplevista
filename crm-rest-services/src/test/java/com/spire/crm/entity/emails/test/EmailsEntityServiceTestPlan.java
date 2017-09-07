package com.spire.crm.entity.emails.test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.commons.config.entities.SpireConfiguration;
import spire.crm.entity.com.entities.Email;
import spire.crm.entity.com.entities.SendCustomEmailInputVO;
import spire.crm.entity.com.entities.SendEmailInputVO;
import spire.crm.entity.com.entities.Template;
import spire.crm.entity.com.entities.TemplateSummary;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.EmailQueryParamPojo;
import com.spire.common.TestData;
import com.spire.crm.restful.entity.consumers.EmailsConsumer;

@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class EmailsEntityServiceTestPlan extends TestPlan {

	String SERVICE_ENDPOINT_URL = null;
	EmailsConsumer emailsConsumer = null;

	SpireConfiguration spireConfiguration = null;
	String id = null;
	final static String DATAPROVIDER_NAME = "ACTIVITY_STREAM";
	final static String CSV_DIR = "./src/test/java/com/spire/crm/entity/emails/test/";
	final static String CSV_FILENAME = "EmailsEntityService_TestData.csv";
	final static String CSV_PATH = CSV_DIR + CSV_FILENAME;
	String tempId = null;

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
			entityClazzMap.put("SendEmailInputVO", SendEmailInputVO.class);
			entityClazzMap.put("SendCustomEmailInputVO", SendCustomEmailInputVO.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(EmailsEntityServiceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@BeforeClass(alwaysRun = true)
	public void setUp() {

		emailsConsumer = new EmailsConsumer();
	}

	/**
	 * Method to verify ListTemplates
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_getListTemplates" }, priority = 1, dataProvider = DATAPROVIDER_NAME)
	public void verify_getListTemplates(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		Logging.log("Test Case Execution started >>> VerifyListTemplates !!!");
		TemplateSummary templateSummary = null;

		templateSummary = emailsConsumer.listEmailTemplate(emailQueryParam.getLimit(), emailQueryParam.getOffset());

		if (templateSummary.getTotalCount() == 0) {
			Logging.log("Template list count Zero , So creating New template.");
			String tempId = emailsConsumer.createEmailTemplate(template);
			emailsConsumer.verifyTemplateCreated(template, emailQueryParam, templateSummary);
			emailsConsumer.justDeleteTemp(tempId);
		}

	}

	/**
	 * Method to verify createTemplates
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_createTemplates", "Sanity" },priority = 0, dataProvider = DATAPROVIDER_NAME)
	public void verify_createTemplates(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		 tempId = emailsConsumer.createEmailTemplate(template);
		emailsConsumer.verifyTemplateCreated(template, emailQueryParam, templateSummary);
		//emailsConsumer.justDeleteTemp(tempId);
	}

	/**
	 * Method to verify deleteTemplate
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_deleteTemplate", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_deleteTemplate(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		String tempId = emailsConsumer.createEmailTemplate(template);
		emailsConsumer.justDeleteTemp(tempId);

	}

	/**
	 * Method to verify editTemplate
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_editTemplate", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_editTemplate(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		String tempName = emailsConsumer.editTemplate(template, emailQueryParam, templateSummary);
		String tempId = emailsConsumer.verifyEdit(template, emailQueryParam, templateSummary, tempName);
		emailsConsumer.justDeleteTemp(tempId);

	}

	/**
	 * Method to verify sendEmails
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_sendEmails", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_sendEmails(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		
		emailsConsumer.sendEmailSingle(template, emailQueryParam, templateSummary, sendEmailInput);
	}

	/**
	 * Method to verify sendBulkEmails
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_sendBulkEmails", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_sendBulkEmails(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		emailsConsumer.sendEmailBulk(template, emailQueryParam, templateSummary, sendEmailInput);
	}

	/**
	 * Method to verify sendCustomEmails
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_sendCustomEmails", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_sendCustomEmails(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		emailsConsumer.sendCustomEmail(template, emailQueryParam, templateSummary, sendCustomEmailInput);

	}

	/**
	 * Method to verify sendCustomEmailsBulk
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_sendCustomEmailsBulk", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_sendCustomEmailsBulk(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		emailsConsumer.sendCustomEmailBulk(template, emailQueryParam, templateSummary, sendCustomEmailInput);

	}

	/**
	 * Method to verify getMessageId
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_getMessageId", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_getMessageId(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		String msgId = emailsConsumer.sendEmailSingle(template, emailQueryParam, templateSummary, sendEmailInput);
		Email email = emailsConsumer.getMessage(template, emailQueryParam, templateSummary, sendEmailInput, msgId);
		emailsConsumer.messageContentValidation(email);
	}

	/**
	 * Method to verify getTemplate
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_getTemplate", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_getTemplate(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		emailsConsumer.getTemplate(template, emailQueryParam, templateSummary);
	}

	/**
	 * Method to verify markEmailAsRead
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_markEmailAsRead", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_markEmailAsRead(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		emailsConsumer.markEmailAsRead(template, emailQueryParam, templateSummary, sendEmailInput);
	}

	/**
	 * Method to verify unsubscribeCandidate
	 * 
	 * @param testObject
	 * @param data
	 * @param template
	 * @param emailQueryParam
	 * @param sendEmailInput
	 * @param sendCustomEmailInput
	 */
	@Test(groups = { "verify_unsubscribeCandidate1", "Sanity" }, dataProvider = DATAPROVIDER_NAME)
	public void verify_unsubscribeCandidate1(SpireTestObject testObject, TestData data, Template template,
			EmailQueryParamPojo emailQueryParam, SendEmailInputVO sendEmailInput,
			SendCustomEmailInputVO sendCustomEmailInput) {
		TemplateSummary templateSummary = null;
		String emailID = UUID.randomUUID().toString().replaceAll("-", "");
		String emailId = emailsConsumer.unsubscribeCandidate(emailID.substring(0, 6)+"@gmail.com");
	    emailsConsumer.veryfyUnsubscribe(template, emailQueryParam, templateSummary, sendEmailInput);
		emailsConsumer.subscribeCandidate(emailId);
		
	}
}

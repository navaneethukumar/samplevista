package com.spire.crm.biz.userservice.test;

import java.io.IOException;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.UserServiceConsumer;

import spire.crm.user.service.beans.CRMUserDetailsBean;
import spire.crm.user.service.beans.UserPreferenceBean;


/**
 * @author Manikanta Y
 *
 */
public class UserServiceBizValidation extends CRMTestPlan {

	Gson gson = new Gson();
	DataFactory factory = new DataFactory();
	Random randomGenerator = new Random();
	public String propertiesfilePath = "./src/main/resources/services-endpoints.properties";

	ObjectMapper mapper = new ObjectMapper().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule((Module) new JavaTimeModule());

	public String userId =  ReadingServiceEndPointsProperties
			.getServiceEndPoint("userId");

	public void validateUserDetails() {

		UserServiceConsumer userServiceConsumer = new UserServiceConsumer();
		Response response = userServiceConsumer.getUserDetails(userId);

		if (response.getStatus() == 200) {

			String strResponse = response.readEntity(String.class);
			try {

				CRMUserDetailsBean userDetailsBean = mapper.readValue(
						strResponse, CRMUserDetailsBean.class);

			} catch (IOException e) {

				e.printStackTrace();

				Assert.fail("exception while mapper ");
			}

		} else {

			Assert.fail("Get the userc details service is failing and status code is : "
					+ response.getStatus());
		}

	}

	public void validateUpdateUserDetails(TestData data, Integer bulkcount) {

		UserServiceConsumer userServiceConsumer = new UserServiceConsumer();
		Response getresponse = userServiceConsumer.getUserDetails(userId);
		UserPreferenceBean userPreference = setUpUserPreferenceBean(true);
		CRMUserDetailsBean userDetailsBean = null;
		CRMUserDetailsBean userDetailsBean2 = null;

		if (getresponse.getStatus() == 200) {

			String strResponse = getresponse.readEntity(String.class);
			try {

				userDetailsBean = mapper.readValue(strResponse,
						CRMUserDetailsBean.class);

				userDetailsBean.setUserPreference(userPreference);

				Response createResponse = userServiceConsumer
						.createUpdate(userDetailsBean);

				if (createResponse.getStatus() == 200) {

					Response getresponse2 = userServiceConsumer
							.getUserDetails(userId);

					if (getresponse2.getStatus() == 200) {

						String strresponse2 = getresponse2
								.readEntity(String.class);
						try {

							userDetailsBean2 = mapper.readValue(strresponse2,
									CRMUserDetailsBean.class);

						} catch (IOException e) {

							e.printStackTrace();

							Assert.fail("exception while mapper ");
						}

					} else {

						Assert.fail("Get the userc details service is failing and status code is : "
								+ getresponse2.getStatus());
					}

				} else {

					Assert.fail("create and update is failed and status code is : "
							+ createResponse.getStatus());
				}

			} catch (IOException e) {

				e.printStackTrace();

				Assert.fail("exception while mapper ");
			}

		} else {

			Assert.fail("Get the userc details service is failing and status code is : "
					+ getresponse.getStatus());
		}

		compareUserdetails(userDetailsBean, userDetailsBean2);

	}

	private void compareUserdetails(CRMUserDetailsBean userDetailsBean,
			CRMUserDetailsBean userDetailsBean2) {

		Assert.assertEquals(userDetailsBean.getUserPreference().getUserId(),
				userDetailsBean2.getUserPreference().getUserId(),
				"found mismatch in UserID ");

		Assert.assertEquals(userDetailsBean.getUserPreference()
				.getEmailSignature(), userDetailsBean2.getUserPreference()
				.getEmailSignature(), "found mismatch in Email Signature ");

		Assert.assertEquals(userDetailsBean.getUserPreference().getLocale(),
				userDetailsBean2.getUserPreference().getLocale(),
				"found mismatch in Locale ");

		Assert.assertEquals(userDetailsBean.getUserPreference().getPhotoUrl(),
				userDetailsBean2.getUserPreference().getPhotoUrl(),
				"found mismatch in Photo Url ");

		Assert.assertEquals(userDetailsBean.getUserPreference().getTimeZone(),
				userDetailsBean2.getUserPreference().getTimeZone(),
				"found mismatch in Photo Url ");

		Assert.assertEquals(
				userDetailsBean
						.getUserPreference()
						.getEmailsEnabled()
						.compareTo(
								userDetailsBean2.getUserPreference()
										.getEmailsEnabled()), 0,
				"found mismatch in Photo Url ");

	}

	private UserPreferenceBean setUpUserPreferenceBean(Boolean emailFlag) {
		UserPreferenceBean userPreference = new UserPreferenceBean();
		userPreference.setUserId(userId);
		userPreference.setEmailSignature(factory.getEmailAddress());
		userPreference.setLocale(factory.getCity());
		userPreference.setTimeZone("GMT");
		userPreference
				.setPhotoUrl("https://i.ytimg.com/vi/hzVPJUTSxCQ/maxresdefault.jpg");
		userPreference.setEmailsEnabled(emailFlag);
		return userPreference;
	}

}

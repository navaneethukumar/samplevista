package com.spire.common;

import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.spire.crm.restful.biz.consumers.CrmPipeLineBizServiceConsumer;

import crm.pipeline.beans.CRM;

public class PipeLineHelper {

	public String getstageName(String candidteID) {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();
		Response getResponse = null;
		for (int i = 0; i <= 15; i++) {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			getResponse = crmPipeLineBizServiceConsumer
					.getProfileByID(candidteID);
			if (getResponse.getStatus() == 200) {
				break;
			}

		}

		if (getResponse.getStatus() == 200) {

			CRM getProfile = getResponse.readEntity(CRM.class);

			return getProfile.getStatusName();
		} else {
			Assert.fail(" Get Profile by id is failed and status code is :"
					+ getResponse.getStatus());
			return null;

		}

	}

	public String getstageName_(String candidteID) {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();
		Response getResponse = null;
		getResponse = crmPipeLineBizServiceConsumer.getProfileByID(candidteID);
		if (getResponse.getStatus() == 200) {
			CRM getProfile = getResponse.readEntity(CRM.class);
			return getProfile.getStatusName();
		} else {
			Assert.fail("getStageName_ got failed-->" + getResponse.getStatus());
			return null;
		}

	}

	public void changeStageTo(String candidteID, String stageName) {

		CrmPipeLineBizServiceConsumer crmPipeLineBizServiceConsumer = new CrmPipeLineBizServiceConsumer();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		Response Response = crmPipeLineBizServiceConsumer
				.updateProfileStageName(candidteID, stageName, null);

		if (Response.getStatus() != 200) {

			Assert.fail(" change stage is failed and status code is :"
					+ Response.getStatus());

		}

	}

}

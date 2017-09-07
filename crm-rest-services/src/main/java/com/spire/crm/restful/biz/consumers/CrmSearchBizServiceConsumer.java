package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.util.SearchBizBean;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.notification.beans.NotificationBean;
import spire.search.commons.entities.SearchInput;
import spire.talent.entity.demandservice.beans.RequisitionBean;

/**
 * 
 * http://192.168.2.124:8085/crm-search-biz-web/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class CrmSearchBizServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(CrmSearchBizServiceConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("CRM_SEARCH_BIZ");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8085/crm-search-biz-web/api/profiles/_search
	 * 
	 * @param URL
	 */
	public CrmSearchBizServiceConsumer() {
		logger.info(Key.METHOD, "CrmNotificationWebBizServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);
	

	}
	
	public CrmSearchBizServiceConsumer(String username, String password,String realm) {
		Logging.log(" user name and password used is" + username + " "+password );
		getUserToken(username, password,realm);
	}
	
	/**
	 * Search
	 * 
	 * @param 
	 * @return
	 */
	public Response search(SearchInput searchInput) {
		endPointURL=endPointURL+"?searchBy=candidate&client=tv&skillsAsCSV=true";
		Logging.log(" Search endPointURL  >>>" + endPointURL);
		Logging.log(" Search Request  >>>" + gson.toJson(searchInput));
		System.out.println(" Search Request  >>>" + gson.toJson(searchInput));
		Response response = executePOST(endPointURL, true,
				Entity.entity(searchInput, MediaType.APPLICATION_JSON));
		return response;

	}
	
	/**
	 * Search
	 * 
	 * @param 
	 * @return
	 */
	public Response searchQuery(SearchInput searchInput,SearchBizBean searchBizBean) {
		String URL=endPointURL+"profiles/_searchNew?searchBy=candidate&isExpertSkill=true&skillsAsCSV=true&client="+searchBizBean.getClient();
		Logging.log(" Search endPointURL  >>>" + URL);
		Logging.log(" Search Request  >>>" + gson.toJson(searchInput));
		System.out.println(" Search Request  >>>" + gson.toJson(searchInput));
		Response response = executePOST(URL, true,
				Entity.entity(searchInput, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			Assert.assertEquals(response.getStatus(), 200,"failed to execute the search query");
		} else {
			Assert.fail("failed to execute the search query throwing status as :"
					+ response.getStatus());
		}
		return response;

	}
	
	/**
	 * searchQueryWithoutExpertSkill
	 * 
	 * @param 
	 * @return
	 */
	public Response searchQueryWithoutExpertSkill(SearchInput searchInput,SearchBizBean searchBizBean) {
		String URL=endPointURL+"?searchBy=candidate&skillsAsCSV=true&client="+searchBizBean.getClient();
		Logging.log(" Search endPointURL  >>>" + URL);
		Logging.log(" Search Request  >>>" + gson.toJson(searchInput));
		System.out.println(" Search Request  >>>" + gson.toJson(searchInput));
		Response response = executePOST(URL, true,
				Entity.entity(searchInput, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			Assert.assertEquals(response.getStatus(), 200,"failed to execute the search query");
		} else {
			Assert.fail("failed to execute the search query throwing status as :"
					+ response.getStatus());
		}
		return response;

	}
	
	public Response searchQuerybyEmptySearch(String empty,SearchBizBean searchBizBean) {
		String URL=endPointURL+"profiles/_searchNew?searchBy=candidate&isExpertSkill=true&skillsAsCSV=true&client="+searchBizBean.getClient();
		Logging.log(" Search endPointURL  >>>" + URL);
		Response response = executePOST(URL, true,
				Entity.entity(empty, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			Assert.assertEquals(response.getStatus(), 200,"failed to execute the search query");
		} else {
			Assert.fail("failed to execute the search query throwing status as :"
					+ response.getStatus());
		}
		return response;

	}

	/**
	 * Search
	 * 
	 * @param 
	 * @return
	 */
	public Response searchByKey(NotificationBean searchInput,SpireTestObject testObject) {
		endPointURL=endPointURL+"?searchBy=candidate&client="+testObject.getTestData();
		Logging.log(" searchByKey endPointURL  >>>" + endPointURL);
		Logging.log(" Search Request  >>>" + gson.toJson(searchInput));
		Response response = executePOST(endPointURL, true,
				Entity.entity(searchInput, MediaType.APPLICATION_JSON));
		return response;

	}
	/**
	 * createRequsition
	 * 
	 * requisitionBean 
	 * @return response
	 */
	public Response createRequsition(RequisitionBean requisitionBean) {
		String reqEndpoint =ReadingServiceEndPointsProperties.getServiceEndPoint("CRM_TALENT_ENTITY");
		String serviceEndPoint=reqEndpoint;
		Logging.log(" searchByKey endPointURL  >>>" + endPointURL);
		Logging.log(" Search Request  >>>" + gson.toJson(requisitionBean));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(requisitionBean, MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * searchByRequisition
	 * 
	 * @reqID
	 * @return
	 */
	public Response searchByRequisition(String reqID) {
		String URL=endPointURL+"profiles/_searchNew?client=tv&skillsAsCSV=true&isExpertSkill=true&requisitionId="+reqID+"&searchBy=requisition";
		Logging.log(" searchByKey endPointURL  >>>" + URL);
		Response response = executePOST(URL, true,
				Entity.entity("", MediaType.APPLICATION_JSON));
		return response;

	}
	
	/**
	 * searchByRIExpalin
	 * 
	 * @reqID
	 * @return
	 */
	public Response searchByRIExpalin(SearchInput searchRequest,SearchBizBean searchBizBean) {
		endPointURL=endPointURL+"profiles/_searchNew?searchBy=candidate&client="+searchBizBean.getClient();
		String URL=endPointURL.replaceAll("_searchNew","_explain");
		Logging.log(" searchByKey endPointURL  >>>" + URL);
		Gson json=new Gson();
		Logging.log("search request >>>"+json.toJson(searchRequest));
		Response response = executePOST(URL, true,
				Entity.entity(searchRequest, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			Assert.assertEquals(response.getStatus(), 200,"failed to execute the search Explain");
		} else {
			Assert.fail("failed to execute the search Explain throwing status as :"
					+ response.getStatus());
		}
		return response;
	}
	
}

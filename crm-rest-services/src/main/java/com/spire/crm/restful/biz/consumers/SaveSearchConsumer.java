package com.spire.crm.restful.biz.consumers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.commons.search.beans.ResponseBean;
import spire.commons.search.beans.SearchBean;

/**
 * 
 * http://192.168.2.124:8085/crm-search-biz-web/api/swagger.json
 * 
 * @author Manikanta
 *
 */
public class SaveSearchConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(SaveSearchConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("SAVE_SEARCH_SERVICE");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.75:8182/spire-biz/save-search/api/save_search
	 * 
	 * @param URL
	 */
	public SaveSearchConsumer() {
		logger.info(Key.METHOD, "CrmNotificationWebBizServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);
	}
	
	/**
	 * save search
	 * 
	 * @param 
	 * @return
	 */
	public ResponseBean saveSearch(SearchBean searchBean) {
		String URL=endPointURL;
		Logging.log(" Save Search endPointURL  >>>" + URL);
		Logging.log(" Save Search Request  >>>" + gson.toJson(searchBean));
		System.out.println(" Search Request  >>>" + gson.toJson(searchBean));
		Response response = executePOST(URL, true,
				Entity.entity(searchBean, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 201) {
			Assert.assertEquals(response.getStatus(), 201,"failed to save the search result");
		} else {
			Assert.fail("failed to execute the search query throwing status as :"
					+ response.getStatus());
		}
		ResponseBean responseBean=response.readEntity(ResponseBean.class);
		return responseBean;

	}
}

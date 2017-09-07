package com.spire.crm.restful.entity.consumers;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;

/**
 * 
 * http://192.168.2.185:8085/profiles-web/api/similarprofiles/
 * 
 * @author Manikanta
 *
 */
public class SimilarProfileEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(SimilarProfileEntityServiceConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("SIMILAR_PROFILES");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.185:8085/profiles-web/api/similarprofiles/
	 * 
	 * @param URL
	 */
	public SimilarProfileEntityServiceConsumer() {
		logger.info(Key.METHOD, "SimilarProfileEntityServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);
		

	}

	/** ------------------------Similar ProfileEntity ServiceConsumer ---------------------- **/

	
	/**
	 * getSimilarProfile 
	 * 
	 * @param createRule
	 * @return
	 */
	public Response getSimilarProfile(String candidateID) {

		this.endPointURL=this.endPointURL + candidateID;
		
		Logging.log(" Get Similar Profile endPointURL  >>>" + endPointURL);
	
		Response response = executeGET(this.endPointURL, true);
		
		return response;

	}


}

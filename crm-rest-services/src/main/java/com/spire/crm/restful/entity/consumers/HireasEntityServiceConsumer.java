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
 *  http://192.168.2.124:8080/profiles-web/api/hireas/6000%3A6005%3A9de028bfa0d84b8e8cde007d3bfb5e1e
 * 
 * @author Manikanta
 *
 */
public class HireasEntityServiceConsumer extends BaseServiceConsumerNew {

	private static Logger logger = LoggerFactory
			.getLogger(HireasEntityServiceConsumer.class);
	Response response = null;
	String endPointURL =ReadingServiceEndPointsProperties.getServiceEndPoint("HIRE_AS_SERVICE");
	public static Gson gson = new Gson();

	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.124:8080/profiles-web/api/hireas/
	 * 
	 * @param URL
	 */
	public HireasEntityServiceConsumer() {
		logger.info(Key.METHOD, "HireasEntityServiceConsumer constructor",
				Key.MESSAGE, "Service end point URL >>>" + this.endPointURL);
		

	}

	/** ------------------------Hire As ---------------------- **/

	
	/**
	 * getHireAs 
	 * 
	 * @param createRule
	 * @return
	 */
	public Response getHireAs(String candidateID) {
		
		this.endPointURL=this.endPointURL + candidateID;
		
		Logging.log(" Hire As endPointURL  >>>" + endPointURL);
	
		Response response = executeGET(this.endPointURL, true);
		
		return response;

	}


}

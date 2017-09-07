package com.spire.crm.restful.biz.consumers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.testng.Assert;
import org.testng.Reporter;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.core.extraction.jd.response.JobDescriptionDetailsResponse;
import spire.crm.jobpost.core.JobLocation;
import spire.crm.jobpost.core.JobPost;
import spire.crm.jobpost.core.JobPostAnalytics;
import spire.crm.jobpost.core.SeedDataWrapper;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.demandservice.beans.RequisitionBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

public class JobPostBizServiceConsumer extends BaseServiceConsumerNew {
	HashMap<String, String> values = new HashMap<String, String>();
	String resposneMsg = "Created";
	public static Gson gson = new Gson();
	private static Logger logger = LoggerFactory.getLogger(JobPostBizServiceConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("CRM_JOBPOST_BIZ");
	String endPointURLTalent = getServiceEndPoint("CRM_TALENT_BIZ");
	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://172.16.1.209:8080/crm-jobpost-web/api/v1/jobpost
	 * 
	 * @param URL
	 */
	public JobPostBizServiceConsumer() {
		logger.info(Key.METHOD, "JobPostBizServiceConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + endPointURL);
		// this.endPointURL = URL;

	}

	/** ------------------------GET Operations ---------------------- **/

	@SuppressWarnings("unchecked")
	public Collection<JobPost> getOpenJobDetails() throws JsonParseException, JsonMappingException, IOException{
		Collection collectionEntity = null ;
		logger.info(Key.MESSAGE, "getOpenJobDetails: ");
		String serviceEndPoint = this.endPointURL + "/" + "openjobdetails";
		Response response = executeGET(serviceEndPoint, true);
		
		logger.info("response code >>>" + response.getStatus());
		String resposneStr  = response.readEntity(String.class);
		if (response.getStatus() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			collectionEntity = mapper.readValue(resposneStr,
                    new TypeReference<Collection<JobPost>>() {
                    });
			//CollectionEntity<JobPost> collectionEntity1 =  response.readEntity(CollectionEntity.class);
			return collectionEntity;
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public spire.crm.jobpost.page.CollectionEntity<JobPost> getJobDetails(String limit, String offset) throws JsonParseException, JsonMappingException, IOException {
		logger.info(Key.MESSAGE, "getJobDetails: ");
		String serviceEndPoint = this.endPointURL + "/" + "jobdetails?limit=" + limit + "&offset=" + offset;
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		spire.crm.jobpost.page.CollectionEntity entity = null;
		 try {
		if (response.getStatus() == 200) {
			Assert.assertTrue(true, "Get Job Details gave success message");
			Logging.log("Test case gave success message with Status Code 200");
			
		}else{
			Assert.fail( "Get Job Details did not gave success message");
			Logging.log("Test case did not gave success message with Status Code "+response.getStatus());
		}
			ObjectMapper mapper = new ObjectMapper();
           // mapper.registerModule(new JavaTimeModule());
           
            	String json = response.readEntity(String.class);
            	Logging.log("Resposne :r" +json);
            	entity = mapper.readValue(json,
                        new TypeReference<spire.crm.jobpost.page.CollectionEntity<JobPost>>() {
                   });
            	
            	Logging.log("Entity:" + entity);
            	Logging.log("items : "+entity.getItems());
        //spire.crm.jobpost.page.CollectionEntity<JobPost> collectionEntity = response.readEntity(spire.crm.jobpost.page.CollectionEntity.class);
			return entity;
            }catch(Exception e) {
            	Assert.assertFalse(false, "Got Exception In Catch Block");
            	Logging.log("Got Exception In Catch Block with Exception "+e.getStackTrace());	
            }
		
		return entity;

		
		/*if (jobPost == null) {
		throw new RuntimeException("--verifyGetJobDetails ---");
	}
	Collection<JobPost> collectionJobPost = jobPost.getItems();
	Iterator<JobPost> iteJobPost = collectionJobPost.iterator();
	while (iteJobPost.hasNext()) {
		JobPost jobPostRes = iteJobPost.next();
		this.JOBPOST_ID = jobPostRes.getJobPostID();
		Logging.log("JPIdis : " + this.JOBPOST_ID);
		if (jobPostId == this.JOBPOST_ID) {

			Assert.assertTrue(true, "Post Job/Requisition is in Get Job Deatils List");
			Logging.log("Post Job/Requisition is in Get Job Deatils List");

		} else {
			Assert.assertFalse(false, "Couldn't find Posted Job/Requisition is in Get Job Deatils List");
			Logging.log("Couldn't find Posted Job/Requisition is in Get Job Deatils List ");
		}
	}*/
		
	}
	
	
	@SuppressWarnings("unchecked")
	public String  getJobDetailsAsString(String limit, String offset) throws JsonParseException, JsonMappingException, IOException {
		logger.info(Key.MESSAGE, "getJobDetails: ");
		String serviceEndPoint = this.endPointURL + "/" + "jobdetails?limit=" + limit + "&offset=" + offset;
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		String result  = null;
		 try {
		if (response.getStatus() == 200) {
			Assert.assertTrue(true, "Get Job Details gave success message");
			Logging.log("Test case gave success message with Status Code 200");
			
		}else{
			Assert.assertFalse(false, "Get Job Details did not gave success message");
			Logging.log("Test case did not gave success message with Status Code "+response.getStatus());
		}
			result = response.readEntity(String.class);
			return result;
            }catch(Exception e) {
            	Assert.assertFalse(false, "Got Exception In Catch Block");
            	Logging.log("Got Exception In Catch Block with Exception "+e.getStackTrace());	
            }
		
		return result;

	}

	/**
	 * This is the basic service call will do whethere service is returning all
	 * seeddata values
	 * 
	 * @return
	 */
	public List<SeedDataWrapper> getSeeddata() {
		logger.info(Key.MESSAGE, "getSeeddata: ");
		String serviceEndPoint = this.endPointURL + "/seeddata";
		Response response = executeGET(serviceEndPoint, true);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			List<SeedDataWrapper> collectionEntity = response.readEntity(new GenericType<List<SeedDataWrapper>>() {
			});
			return collectionEntity;
		} else {
			return null;
		}

	}

	/**
	 * Based on the open requisition id ,it extracts and gives the full
	 * requisition details
	 * 
	 * @param requisitionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CollectionEntity<JobPost> getPostOpenPositions(String requisitionId) {
		logger.info(Key.MESSAGE, "getOpenJobDetails: ");
		String serviceEndPoint = this.endPointURL + "/" + "postopenpositions?" + requisitionId;
		Response response = executeGET(serviceEndPoint, true);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<JobPost> collectionEntity = response.readEntity(CollectionEntity.class);
			return collectionEntity;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param groupBy
	 * @return
	 */
	public CollectionEntity<JobPostAnalytics> getJobPostAnalyticsData(String groupBy) {
		logger.info(Key.MESSAGE, "getJobPostAnalyticsData: ");
		String serviceEndPoint = this.endPointURL + "/" + "analytics?groupBy=" + groupBy;
		Response response = executeGET(serviceEndPoint, true);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<JobPostAnalytics> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<JobPostAnalytics>>() {
					});
			return collectionEntity;
		} else {
			return null;
		}

	}

	public CollectionEntity<JobPostAnalytics> getWeeklyJobPostAnalyticsData(String groupBy, String jobPostId) {
		logger.info(Key.MESSAGE, "getJobPostAnalyticsData: ");
		String serviceEndPoint = this.endPointURL + "/" + "weeklyanalytics?groupBy=" + groupBy + "&" + jobPostId;
		Response response = executeGET(serviceEndPoint, true);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<JobPostAnalytics> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<JobPostAnalytics>>() {
					});
			return collectionEntity;
		} else {
			return null;
		}

	}

	public CollectionEntity<JobPostAnalytics> getDateRangeJobPostAnalyticsData(String groupBy, String startDate,
			String endDate, String jobPostId) {
		logger.info(Key.MESSAGE, "getJobPostAnalyticsData: ");
		String jpId = values.get("JpID");
		String serviceEndPoint = this.endPointURL + "/" + "daterangeanalytics?groupBy=" + groupBy + "&startDate="
				+ startDate + "&endDate=" + endDate + "&jobPostId=" + jpId;
		try{
		Response response = executeGET(serviceEndPoint, true);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200 ) {
			
			CollectionEntity<JobPostAnalytics> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<JobPostAnalytics>>() {
					});
			return collectionEntity;
		} else if(response.getStatus() == 204){
			Logging.log("No Content Found");
			
		}else {
			Assert.fail("Got Wrong status code "+response.getStatus());
			Logging.log("Got Wrong status code "+response.getStatus());
		}
		}catch(Exception e){
			Assert.fail("Got Exception IN Catch Block");
			Logging.log("Got Exception IN Catch Block");
		}
		return null;
	}

	/**
	 * Based on requisition URL it extracts the content
	 * 
	 * @param URL
	 * @return
	 */
	public JobDescriptionDetailsResponse postURLExtraction(String URL) {
		logger.info(Key.MESSAGE, "URLExtraction: ");
		String serviceEndPoint = this.endPointURL + "/" + "_url";
		Entity<String> urlExtraction = Entity.entity(URL, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(serviceEndPoint, true, urlExtraction);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201 && response.getStatus() == 200) {
			JobDescriptionDetailsResponse jobDescriptionDetailsResponse = response
					.readEntity(JobDescriptionDetailsResponse.class);
			return jobDescriptionDetailsResponse;
		} else {
			return null;
		}

	}

	/**
	 * Requisition service to create open requisitions.
	 * 
	 * @param URL
	 * @return
	 */
	public String createRequisition(String URL) {
		logger.info(Key.MESSAGE, "createRequisition: ");
		Logging.log("URL >>" + URL);
		Entity<String> urlExtraction = Entity.entity(URL, MediaType.APPLICATION_JSON_TYPE);
		Response response = executePOST(URL, true, urlExtraction);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return "";
		} else {	
			return null;
		}

	}

	/**
	 * Upload jd file ,it extracts and gives the content
	 * 
	 * @param filePath
	 * @return
	 */
	public String fileUploadExtraction(String filePath) {
		logger.info(Key.MESSAGE, "FileUploadExtraction: ");
		String serviceEndPoint = this.endPointURL + "/" + "_fileupload";
		super.MULTI_FORM_DATA = true;
		Logging.info("SERVICE END POINT URL >>" + serviceEndPoint);
		readingProperties();
		// For file uploads have to give fileSize header
		setPropertyValue("fileSize", "5");
		removingProperty("Content-Type");
		final FileDataBodyPart filePart = new FileDataBodyPart("file", new File(filePath));
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);
		Entity<FormDataMultiPart> serviceEntity = Entity.entity(multipart, MediaType.MULTIPART_FORM_DATA);
		Response response = executePOST(serviceEndPoint, true, serviceEntity);
		logger.info("response code >>>" + response.getStatus());
		try {
			formDataMultiPart.close();
			multipart.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			
			String result = response.readEntity(String.class);
			Logging.log("Resposne is :" +result);
			//JobDescriptionDetailsResponse result = response.readEntity(JobDescriptionDetailsResponse.class);
			
			
			return result;
		} else {
			return null;
		}

	}

	/**
	 * posting jobdetails by userid & jobboardId where to upload the jobpost.
	 * 
	 * @param jobdetail
	 * @param userId
	 * @param jobBoardIds
	 * @return
	 */
	public String postJobDetail(JobPost jobdetail, String userId, String jobBoardIds,JobLocation jobLocation) {
		String REQUI_ID = null;
		logger.info(Key.MESSAGE, "postJobDetail: ");
		String user = readingProperties().getProperty("userId");
		String serviceEndPoint = this.endPointURL + "?" + "userId=" + user + "&jobBoardIds=" + jobBoardIds;

		Logging.log("Gson TO Json:"+ gson.toJson(jobdetail));
		Entity<JobPost> jobDetails = Entity.entity(jobdetail, MediaType.APPLICATION_JSON_TYPE);
		
		Response response = executePOST(serviceEndPoint, true, jobDetails);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			String result = response.readEntity(String.class);
			
			result = result.substring(result.indexOf(":") + 1);
			result = result.substring(0, result.indexOf(","));
			
			Pattern p = Pattern.compile("\"([^\"]*)\"");
			Matcher m = p.matcher(result);
			while (m.find()) {
				result = m.group(1);
			}
			REQUI_ID = result;
			
			if (REQUI_ID != null && !REQUI_ID.isEmpty()) {
				logger.info(" Find the Requisition id >>" + REQUI_ID);
			} else {
				Assert.fail("REQUI_ID is null");
		}
			values.put("JpID", REQUI_ID);
			return REQUI_ID;
		} else {
			return null;
		}
    }
	
	public String getReqId(JobPost jobdetail, String userId, String jobBoardIds,JobLocation jobLocation){
		String REQUI_ID = null;
		logger.info(Key.MESSAGE, "postJobDetail: ");
		String user = readingProperties().getProperty("userId");
		String serviceEndPoint = this.endPointURL + "?" + "userId=" + user + "&jobBoardIds=" + jobBoardIds;
		jobdetail.setStatusDisplay("open");
		Logging.log("Gson TO Json:"+ gson.toJson(jobdetail));
		Entity<JobPost> jobDetails = Entity.entity(jobdetail, MediaType.APPLICATION_JSON_TYPE);
		try{
		Response response = executePOST(serviceEndPoint, true, jobDetails);
		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			String result = response.readEntity(String.class);
			
			result = result.substring(result.indexOf(":") + 1);
			result = result.substring(0, result.indexOf(","));
			
			Pattern p = Pattern.compile("\"([^\"]*)\"");
			Matcher m = p.matcher(result);
			while (m.find()) {
				result = m.group(1);
			}
	
			REQUI_ID = result;
			
			if (REQUI_ID != null && !REQUI_ID.isEmpty()) {
				logger.info(" Find the Requisition id >>" + REQUI_ID);
			} else {
				Assert.fail("REQUI_ID is null");
		}
			values.put("JpID", REQUI_ID);
			return REQUI_ID;
		} else {
			return null;
		}
		
	}catch(Exception e){
			Logging.log("Got Exception In catch Block with Exception : " +e.getMessage());
			Assert.fail("Got Exception In catch Block");
			return null;
		}
	}
	
	public String closeRequisition(RequisitionBean requisitionBean, String reqId, String displayId){
	  
	    String result = "";
	    try{
		requisitionBean.setId(reqId);	
		requisitionBean.setDisplayId(displayId);
		requisitionBean.setStatusDisplay("closed");
		requisitionBean.setStatusCode("CLOSED");
		System.out.println("Gson To Json:" + gson.toJson(requisitionBean));
		String serviceEndPoint = endPointURLTalent + reqId;
		Entity<RequisitionBean> reqDetails = Entity.entity(requisitionBean, MediaType.APPLICATION_JSON_TYPE);
		setPropertyValue("Accept", "text/plain");
		Response responseGot = executePATCH(serviceEndPoint, true, reqDetails);
		setPropertyValue("Accept", "application/json");
		
		if(responseGot.getStatus() == 200){
			Assert.assertTrue(true, "Got Sussess Response");
			Logging.log("Got Sussess Response 200, Closed req " +reqId +"successfully");
		}else{
			Assert.fail("Did Not get response code with status code "+responseGot.getStatus());
		}
		result = responseGot.readEntity(String.class);
		return result;
	}catch(Exception e){
			e.printStackTrace();
			System.out.println("In catch Block with Exception : "+e.getStackTrace());
			return null;
		}
	}
	
	public String postJobDetailUS(JobPost jobdetail, String userId, String jobBoardIds, JobLocation jobLocation) {
		String REQUI_ID = null;
		String result = null;

		String user = readingProperties().getProperty("userId");
		String serviceEndPoint = this.endPointURL + "?" + "userId=" + user + "&jobBoardIds=" + jobBoardIds;

		jobLocation.setLocation_country(jobLocation.getLocation_country());
		jobLocation.setLocation_city(jobLocation.getLocation_city());
		jobLocation.setLocation_postalcode(jobLocation.getLocation_postalcode());
		jobLocation.setLocation_us_area_code(jobLocation.getLocation_us_area_code());
		jobLocation.setLocation_stateprovince(jobLocation.getLocation_stateprovince());
		jobdetail.setJobLocation(jobLocation);
		Logging.log("Gson TO Json:" + gson.toJson(jobdetail));
		Entity<JobPost> jobDetails = Entity.entity(jobdetail, MediaType.APPLICATION_JSON_TYPE);

	
		try {
			Response responseGot = executePOST(serviceEndPoint, true, jobDetails);
			result = responseGot.readEntity(String.class);
			Logging.log("response code >>>" + responseGot.getStatus());

			result = validateResponse( responseGot,result, REQUI_ID);

			return result;
		} catch (Exception e) {
			Logging.log("Got Exception :" + e);
			Assert.fail("Got Exception");
			return null;
		}
	}

	public String validateResponse(Response responseGot,String result, String REQUI_ID) {
		if ((responseGot.getStatus() == 201 || responseGot.getStatus() == 200) && result.contains(resposneMsg)) {

			Assert.assertTrue(true, "Got Resposne as: " + result);
			Reporter.log("Got Resposne as: " + result);
			result = result.substring(result.indexOf(":") + 1);
			result = result.substring(0, result.indexOf(","));

			Pattern p = Pattern.compile("\"([^\"]*)\"");
			Matcher m = p.matcher(result);
			while (m.find()) {
				result = m.group(1);
			}
			REQUI_ID = result;

			if (REQUI_ID != null && !REQUI_ID.isEmpty()) {
				Logging.log(" Find the Requisition id >>" + REQUI_ID);
			} else {
				Assert.fail("REQUI_ID is null");

			}
			values.put("JpID", REQUI_ID);
			return REQUI_ID;

		} else {
			Assert.fail( "Did not get Success Resposne");
			Logging.log("Got Resposen Message as :" + result);
			return null;
		}
	}
	
	public void postJobDetailLocValidation(JobPost jobdetail, String userId, String jobBoardIds, JobLocation jobLocation) {
		
		String result = null;

		String user = readingProperties().getProperty("userId");
		String serviceEndPoint = this.endPointURL + "?" + "userId=" + user + "&jobBoardIds=" + jobBoardIds;

		jobLocation.setLocation_country(jobLocation.getLocation_country());
		jobLocation.setLocation_city(jobLocation.getLocation_city());
		jobLocation.setLocation_postalcode(jobLocation.getLocation_postalcode());
		jobLocation.setLocation_us_area_code(jobLocation.getLocation_us_area_code());
		jobLocation.setLocation_stateprovince(jobLocation.getLocation_stateprovince());
		jobdetail.setJobLocation(jobLocation);
		Logging.log("Gson TO Json:" + gson.toJson(jobdetail));
		Entity<JobPost> jobDetails = Entity.entity(jobdetail, MediaType.APPLICATION_JSON_TYPE);

		try {
			Response response = executePOST(serviceEndPoint, true, jobDetails);
			result = response.readEntity(String.class);
			System.out.println(result);
			if(response.getStatus() == 400 && result.contains("[Country/City/State/Zip Code is wrong]")){
				Logging.log("response code >>>" + response.getStatus());
				Assert.assertTrue(true, "[Country/City/State/Zip Code is wrong] not displaying this message");
				Logging.log("Testcase Passed with Proper error message ,Error Message displayed is : "+result);
			}else{
				Assert.fail( "Test case did not give proper error code");
				Logging.log("Test case did not give proper error code for Location Validation, Hence Failing TC");
			}
		} catch (Exception e) {
			Logging.log("Got Exception :" + e);
			Assert.fail("Got Exception");
			
		}
	}
	

	/**
	 * Candiates will upload the profile based on reqDisplayId, jobBoardId.
	 * 
	 * @param requisitionDisplayId
	 * @param userId
	 * @param jobBoardId
	 * @param path
	 * @return
	 */
	public String profileUploadForJobPost(String requisitionDisplayId, String userId, String jobBoardId, String path) {
		logger.info(Key.MESSAGE, "postJobDetail: ");
		// setting multiformdata client.
		super.MULTI_FORM_DATA = true;
		String user = readingProperties().getProperty("userId");
		String serviceEndPoint = this.endPointURL + "/_profileupload?" + "requisitionDisplayId=" + requisitionDisplayId
				+ "&userId=" + user + "&jobBoardId=" + jobBoardId;

		Logging.info("SERVICE END POINT URL >>" + serviceEndPoint);
		// For file uploads have to give fileSize header
		setPropertyValue("fileSize", "5");
		removingProperty("Content-Type");

		final FileDataBodyPart filePart = new FileDataBodyPart("file", new File(path));
		FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
		final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);
		Entity<FormDataMultiPart> serviceEntity = Entity.entity(multipart, MediaType.MULTIPART_FORM_DATA);
		Response response = executePOST(serviceEndPoint, true, serviceEntity);
		try {
			formDataMultiPart.close();
			multipart.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("response code >>>" + response.getStatus());
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			String result = response.readEntity(String.class);
			return result;
		} else {
			return null;
		}

	}
}
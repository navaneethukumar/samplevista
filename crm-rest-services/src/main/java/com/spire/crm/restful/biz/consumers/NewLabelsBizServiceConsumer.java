package com.spire.crm.restful.biz.consumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.testng.Assert;

import spire.commons.labels.beans.DateRange;
import spire.commons.labels.beans.LabelUsers;
import spire.commons.labels.beans.LabelVO;
import spire.commons.labels.beans.User;
import spire.commons.labels.common.CollectionEntity;
import spire.crm.labels.biz.entities.LabelFilterPageInfo;
import spire.crm.labels.biz.entities.LabelNameEntity;
import spire.crm.labels.biz.entities.ResponseEntity;
import spire.crm.profiles.bean.Profile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.helper.FileHelper;
import com.spire.crm.activity.biz.pojos.LabelBean;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

public class NewLabelsBizServiceConsumer extends BaseServiceConsumerNew {

	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("NEW_LABELS_BIZ");
	Gson gson = new Gson();
	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule((Module) new JavaTimeModule());
	
	
	Response response = null;
	String userId = ReadingServiceEndPointsProperties.getServiceEndPoint("userId");
	Logger logger = Logger.getLogger(FileHelper.class);
	public boolean HEADERS = true;


	/**
	 * Need to pass service base URL .
	 * 
	 * Sample URL pattern >>>
	 * http://192.168.2.69:8182/spire-biz/labels-biz/api/v1/swagger.json
	 * 
	 * @param URL
	 */
	public NewLabelsBizServiceConsumer() {
		
	}

public NewLabelsBizServiceConsumer(String username, String password) {
	getUserToken(username, password);
		
	}
	
	/**
	 * Create single Label
	 * 
	 * @param labelName
	 * @param scope
	 * @return responseEntity
	 */
	public ResponseEntity createLabel(String labelName) {

		String serviceEndPoint = this.endPointURL + "/" + labelName;
		Logging.log(" create single Label endPointURL  >>>" + serviceEndPoint);
		System.out.println(serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelName, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for createLabel  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to create a label and Status code is >> " + response.getStatus());
		return null;

	}

	/**
	 * create Bulk Labels
	 * 
	 * @param labelNames
	 * @param scope
	 * @return responseEntity
	 */
	public ResponseEntity createBulkLabels(List<String> labelNames) {

		String serviceEndPoint = this.endPointURL + "/_bulk";
		Logging.log(" createBulkLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" Create bulk request input  >>>" + gson.toJson(labelNames));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelNames, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for createLabels  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to create bulk a label and Status code is >> " + response.getStatus());
		return null;

	}

	/*--------------   /_attach/entity/{entityType}/{entityId}/labels   --------------*/
	/**
	 * Attach entities to labels
	 * 
	 * @param entityId
	 * @param entityType
	 * @param labelNames
	 * @param scope
	 * @return responseEntity
	 */
	public ResponseEntity attachLabelsToEntity(String entityId, String entityType, List<LabelVO> labelVO) {

		String serviceEndPoint = this.endPointURL + "/_attach/entity/" + entityType + "/" + entityId + "/labels";
		Logging.log(" attachLabelsToEntity endPointURL  >>>" + serviceEndPoint);
		Logging.log("Request input for attach Labels To Entity >>>>" + gson.toJson(labelVO));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelVO, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for attachLabelsToEntity  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to create a label and Status code is >> " + response.getStatus());
		return null;

	}

	/*--------------   /_attach/entities/{entityType}/labels   --------------*/
	/**
	 * attachLabelsToEntities
	 * 
	 * @param entityType
	 * @param labelNameEntities
	 * @param scope
	 * @return responseEntity
	 */
	public ResponseEntity attachLabelsToEntities(String entityType, LabelNameEntity labelNameEntities) {

		String serviceEndPoint = this.endPointURL + "/_attach/entities/" + entityType + "/labels";
		Logging.log(" attachLabels To Entities endPointURL  >>>" + serviceEndPoint);
		Logging.log("Request input for attachLabelsToEntities>>>" + gson.toJson(labelNameEntities));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(labelNameEntities, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for attachLabelsToEntities  >>>" + responseEntity);
			return responseEntity;

		}
		Assert.fail("fail to create a label and Status code is >> " + response.getStatus());
		return null;
	}

	public void attachDuplicateLabelsToEntities(String entityType, LabelNameEntity labelNameEntities) {

		String serviceEndPoint = this.endPointURL + "/_attach/entities/" + entityType + "/labels";
		Logging.log(" attachLabels To Entities endPointURL  >>>" + serviceEndPoint);
		Logging.log("Request input for attachLabelsToEntities>>>" + gson.toJson(labelNameEntities));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(labelNameEntities, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 400) {

			Logging.log(" Duplicate label didn't attch to profileID  >>>");

		} else
			Assert.fail("Attached duplicate label >> ");

	}
	/*--------------   /_detach/entity/{entityType}/{entityId}/labels   --------------*/
	/**
	 * detachLabelsToEntity
	 * 
	 * @param entityId
	 * @param entityType
	 * @param labelNames
	 * @param scope
	 * @return responseEntity
	 */
	public ResponseEntity detachLabelsToEntity(String entityId, String entityType, List<String> labelNames,
			String scope) {

		String serviceEndPoint = this.endPointURL + "/_detach/entity/" + entityType + "/" + entityId + "/labels?scope="
				+ scope;
		Logging.log(" detachLabelsToEntity endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" detaching label by giving entityType,entityId and labelNames as >>>" + labelNames + scope
				+ entityId + entityType);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelNames, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for detachLabelsToEntity  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to create a label and Status code is >> " + response.getStatus());
		return null;

	}

	/*--------------   /_detach/entities/{entityType}/labels   --------------*/
	/**
	 * detachLabelsToEntities
	 * 
	 * @param entityType
	 * @param labelNameEntities
	 * @param scope
	 * @return responseEntity
	 */
	public ResponseEntity detachLabelsToEntities(String entityType, LabelNameEntity labelNameEntities) {

		String serviceEndPoint = this.endPointURL + "/_detach/entities/" + entityType + "/labels";
		Logging.log(" detachLabelsToEntities endPointURL  >>>" + serviceEndPoint);

		Logging.log(" Request input for Detach label >>" + gson.toJson(labelNameEntities));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(labelNameEntities, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for detachLabelsToEntities  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to detach a label and Status code is >> " + response.getStatus());
		return null;
	}

	/**
	 * listLabels
	 * 
	 * @return labelDetails
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public CollectionEntity<LabelBean> listLabels() throws JsonParseException, JsonMappingException, IOException {
		String serviceEndPoint = this.endPointURL + "/_list";
		Logging.log(" listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" list labels without parameters >>>");
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		String strLabelDetails = response.readEntity(String.class);
		Logging.log(strLabelDetails);
		CollectionEntity<LabelBean> labelDetails = mapper.readValue(strLabelDetails,
				new TypeReference<CollectionEntity<LabelBean>>() {
				});
		Logging.log(" response for listLabels  >>>" + labelDetails);
		Assert.assertEquals(response.getStatus(), 200, "Labels throwing status code: " + response.getStatus());
		return labelDetails;
	}

	/**
	 * listLabels
	 * 
	 * @param limit
	 * @param offset
	 * @param scope
	 * @param createdDateRange
	 * @return labelDetails
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public CollectionEntity<LabelBean> listLabelswithParms(int limit, int offset, String scope,
			DateRange createdDateRange) throws JsonParseException, JsonMappingException, IOException {
		String serviceEndPoint = this.endPointURL + "/_list?limit=" + limit + "&offset=" + offset + "&scope=" + scope;

		Logging.log(" listLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" list labels with parameters  limit,offset,scope,createdDateRange>>>" + limit + offset + scope
				+ createdDateRange);
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(createdDateRange, MediaType.APPLICATION_JSON));
		String strLabelDetails = response.readEntity(String.class);
		Logging.log(strLabelDetails);
		CollectionEntity<LabelBean> labelDetails = mapper.readValue(strLabelDetails,
				new TypeReference<CollectionEntity<LabelBean>>() {
				});
		Logging.log(" response for listLabels  >>>" + labelDetails);
		Assert.assertEquals(response.getStatus(), 200, "Labels throwing status code: " + response.getStatus());
		return labelDetails;

	}

	/**
	 * listLabels
	 * 
	 * @param labelFilterPageInfo
	 * @return labels
	 */
	public CollectionEntity<LabelBean> filterLabels(LabelFilterPageInfo labelFilterPageInfo) {

		String serviceEndPoint = this.endPointURL + "/_filter";
		Logging.log(" filterLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" filter labels by giving labelFilterPageInfo >>>" + gson.toJson(labelFilterPageInfo));
		Logging.log(gson.toJson(labelFilterPageInfo));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(labelFilterPageInfo, MediaType.APPLICATION_JSON));
		Assert.assertEquals(response.getStatus(), 200, "Labels throwing status code: " + response.getStatus());

		String strResponse = response.readEntity(String.class);

		CollectionEntity<LabelBean> labels = null;
		try {
			labels = mapper.readValue(strResponse, new TypeReference<CollectionEntity<LabelBean>>() {

					});
		} catch (IOException e) {

			e.printStackTrace();

			Assert.fail("Run Time exception");
		}
		Logging.log(" response for filterLabels  >>>" + labels);
		return labels;
	}

	/*--------------   /_list/profiles/labels/{labelId}  --------------*/
	/**
	 * listProfiles and projection as FULL
	 * 
	 * @param labelId
	 * @return profiles
	 */
	public CollectionEntity<Profile> listProfilesGeneric(String labelId, String projection) {

		String serviceEndPoint = this.endPointURL + "/_list/profiles/labels/" + labelId
				+ "?limit=50&offset=0&projection=FULL";
		Logging.log(" listProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" list profiles by giving labelId >>>" + labelId);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			CollectionEntity<Profile> profiles = null;
			if (projection.equalsIgnoreCase("FULL")) {
				profiles = response.readEntity(new GenericType<CollectionEntity<Profile>>() {
				});

			} else {
				String strresponse = response.readEntity(String.class);
				try {
					profiles = mapper.readValue(strresponse, new TypeReference<List<String>>() {
					});
				} catch (IOException e) {
					Assert.fail("Run time Exception >>>" + e.getMessage());

					e.printStackTrace();
				}
			}

			Logging.log(" response for listProfiles  >>>" + gson.toJson(profiles));
			Assert.assertEquals(response.getStatus(), 200, "Labels throwing status code: " + response.getStatus());
			return profiles;
		} else {
			Assert.fail("list Profiles under attched to label is failed and status code is");
			return null;
		}

	}

	/*--------------   /_list/profiles/labels/{labelId}  --------------*/
	/**
	 * listProfiles and projection as FULL
	 * 
	 * @param labelId
	 * @return profiles
	 */
	public List<Profile> listProfiles(String labelId) {

		String serviceEndPoint = this.endPointURL + "/_list/profiles/labels/" + labelId
				+ "?limit=10000&offset=0&projection=FULL";
		Logging.log(" listProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" list profiles by giving labelId >>>" + labelId);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {

			String strResponse = response.readEntity(String.class);
			System.out.println(strResponse);
			List<Profile> collectionEntity = null;
			try {
				collectionEntity = mapper.readValue(strResponse, new TypeReference<List<Profile>>() {
				});
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail("run time exception");
			}

			Logging.log(" response for listProfiles  >>>" + gson.toJson(collectionEntity));
			Assert.assertEquals(response.getStatus(), 200, "Labels throwing status code: " + response.getStatus());
			return collectionEntity;
		} else {
			Assert.fail("list Profiles under attched to label is failed and status code is");
			return null;
		}

	}

	/**
	 * listProfiles
	 * 
	 * @param limit
	 * @param offset
	 * @param labelId
	 * @param createdDateRange
	 * @return profiles
	 */
	public List<Profile> listProfilesWithParams(String limit, String offset, String labelId, DateRange createdDateRange,
			String Projection) {

		String serviceEndPoint = this.endPointURL + "/_list/profiles/labels/" + labelId + "?limit=" + limit + "&offset="
				+ offset;
		Logging.log(" listProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" list profiles by giving labelId,limit,offset,createdDateRange >>>" + labelId + limit + offset
				+ createdDateRange);
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(createdDateRange, MediaType.APPLICATION_JSON));

		List<Profile> profiles = response.readEntity(new GenericType<List<Profile>>() {

		});
		Logging.log(" response for listProfilesWithParams  >>>" + profiles);
		Assert.assertEquals(response.getStatus(), 200, "Labels throwing status code: " + response.getStatus());
		return profiles;

	}

	/**
	 * shareLabel POST /label/_shareLabe
	 * 
	 * @param labelId
	 * @return responseEntity
	 */
	public ResponseEntity shareLabel(LabelUsers labelUsers) {

		String serviceEndPoint = this.endPointURL + "/_shareLabel";
	
		Logging.log(" Shared Label endPointURL  >>" + serviceEndPoint);
		Logging.log(" request input for shared labels  >>>" + gson.toJson(labelUsers));
		Gson json = new Gson();
		System.out.println(json.toJson(labelUsers));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelUsers, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("fail to share the label and Status code is >> " + response.getStatus());
		return null;
	}

	/**
	 * removeLabel
	 * 
	 * @param labelId
	 * @return responseEntity
	 */
	public ResponseEntity deleteLabel(String labelId) {

		String serviceEndPoint = this.endPointURL + "/" + labelId;
		Logging.log(" removeLabel endPointURL  >>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" remove label by giving labelId >>>" + labelId);
		Response response = executeDELETE(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabel  >>>" + responseEntity);
			return responseEntity;
		} else {
			Assert.fail("fail to delete a label and Status code is >> " + response.getStatus());
			return null;
		}
	}

	public ResponseEntity deleteLabelSharedLabel(String labelId) {

		String serviceEndPoint = this.endPointURL + "/" + labelId;
		Logging.log(" removeLabel endPointURL  >>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" remove label by giving labelId >>>" + labelId);
		Response response = executeDELETE(serviceEndPoint, true);
		if (response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabel  >>>" + responseEntity);
			return responseEntity;
		} else {
			Assert.fail("fail to delete a label and Status code is >> " + response.getStatus());
			return null;
		}
	}

	/**
	 * removeLabels
	 * 
	 * @param labelIds
	 * @return responseEntity
	 */
	public ResponseEntity deleteLabels(List<String> labelIds) {

		String serviceEndPoint = this.endPointURL + "/_bulkdelete";
		Logging.log(" removeLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" remove labels by giving labelId >>>" + labelIds);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelIds, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabels  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to delete  a label and Status code is >> " + response.getStatus());
		return null;

	}

	public void clearAutomationLabel(List<String> labelIds) {

		String serviceEndPoint = this.endPointURL + "/_bulkdelete";
		Logging.log(" removeLabels endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" remove labels by giving labelId >>>" + labelIds);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelIds, MediaType.APPLICATION_JSON));

	}

	/**
	 * renameLabels
	 * 
	 * @param labelIds
	 * @return responseEntity
	 */
	public ResponseEntity renameLabel(String labelId, String newlabelName) {

		String serviceEndPoint = this.endPointURL + "/" + labelId + "/_rename";
		Logging.log(" rename Label endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" rename label to >>>" + newlabelName);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(newlabelName, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabels  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to rename the label and Status code is >> " + response.getStatus());
		return null;

	}

	public ResponseEntity renameSharedLabel(String labelId, String newlabelName) {

		String serviceEndPoint = this.endPointURL + "/" + labelId + "/_rename";
		Logging.log(" rename Label endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" rename label to >>>" + newlabelName);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(newlabelName, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 409) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabels  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to rename the label and Status code is >> " + response.getStatus());
		return null;

	}

	public ResponseEntity removeUsers(String labelId, List<User> users) {

		String serviceEndPoint = this.endPointURL + "/" + labelId + "/_removeUsers";
		Logging.log(" rename Label endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" rename label to >>>" + users);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(users, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabels  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to rename the label and Status code is >> " + response.getStatus());
		return null;

	}

	public void listProfilesForInvalidID(String labelId) {

		String serviceEndPoint = this.endPointURL + "/_list/profiles/labels/" + labelId
				+ "?limit=10000&offset=0&projection=FULL";
		Logging.log(" listProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" list profiles by giving labelId >>>" + labelId);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		Response response = executePOST(serviceEndPoint, true, Entity.entity(null, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {

			Logging.log(" No Profiles lised >>>");

		} else {
			Assert.fail("Listing profiles for invalid LabelID");
		}

	}

	public ResponseEntity deleteInvalidLabel(String labelId) {

		String serviceEndPoint = this.endPointURL + "/" + labelId;
		Logging.log(" removeLabel endPointURL  >>" + serviceEndPoint);
		Logging.log("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" remove label by giving labelId >>>" + labelId);
		Response response = executeDELETE(serviceEndPoint, true);
		if (response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for removeLabel  >>>" + responseEntity);
			return responseEntity;
		} else {
			Assert.fail("fail to delete a label and Status code is >> " + response.getStatus());
			return null;
		}
	}

	public ResponseEntity shareDuplicateLabel(LabelUsers labelUsers) {

		String serviceEndPoint = this.endPointURL + "/_shareLabel";
		Logging.log(" Shared Label endPointURL  >>" + serviceEndPoint);
		Logging.log(" request input for shared labels  >>>" + gson.toJson(labelUsers));
		Gson json = new Gson();
		System.out.println(json.toJson(labelUsers));
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelUsers, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 500 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("fail to share the label and Status code is >> " + response.getStatus());
		return null;
	}

	public ResponseEntity createLabelForTwoUsers(String labelName) {

		String serviceEndPoint = this.endPointURL + "/" + labelName;
		Logging.log(" create single Label endPointURL  >>>" + serviceEndPoint);
		System.out.println(serviceEndPoint);
		Response response = executePOST(serviceEndPoint, true, Entity.entity(labelName, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200 || response.getStatus() == 400) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			Logging.log(" response for createLabel  >>>" + responseEntity);
			return responseEntity;
		}
		Assert.fail("fail to create a label and Status code is >> " + response.getStatus());
		return null;

	}
	
	
	
	
	public String loginForBatchUser(String labelName) {
		
		  String username="6000:6003:441d893a45bb4356ba903c5a8959f47d"; String password="spire@123";
		 
		{
		 
		 NewLabelsBizServiceConsumer consumer=new
		 NewLabelsBizServiceConsumer(username, password);
		 

		 
		 ResponseEntity createResponse = consumer.createLabel(labelName);
		 
		 List<String> labelNames = new ArrayList<String>();
			labelNames.add(labelName);
			if (createResponse.getCode() == 200) {

				

			} else {

				
			}
			return labelName;
		  
		}
		 
	}
}

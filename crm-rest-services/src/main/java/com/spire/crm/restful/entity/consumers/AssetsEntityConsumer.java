package com.spire.crm.restful.entity.consumers;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;

import spire.commons.logger.Key;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.crm.entity.com.entities.Asset;
import spire.crm.entity.com.entities.AssetsEntities;
import spire.crm.entity.com.entities.ResponseEntity;

public class AssetsEntityConsumer extends BaseServiceConsumerNew {
	
	private static Logger logger = LoggerFactory.getLogger(AssetsEntityConsumer.class);
	Response response = null;
	String endPointURL = getServiceEndPoint("ASSET_SERVICE_ENTITY");
	public static Gson gson = new Gson();
	public AssetsEntityConsumer() {
		logger.info(Key.METHOD, "AssetsEntityConsumer constructor", Key.MESSAGE,
				"Service end point URL >>>" + this.endPointURL);
	}

	public ResponseEntity createAsset(String fileName, InputStream uploadInputStream,
			FormDataContentDisposition fileDetail) {
		String serviceEndPoint = this.endPointURL + "/" + fileName;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" createAsset endPointURL  >>>" + serviceEndPoint);

		MultiPart multiPart = new MultiPart();
		FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", new File(fileDetail.getFileName()),
				MediaType.APPLICATION_OCTET_STREAM_TYPE);

		multiPart.bodyPart(fileDataBodyPart);
		multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

		Response response = executePOST(serviceEndPoint, true, Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA),
				true);
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * listAssets
	 * 
	 * @param entityId
	 * @return response
	 */
	/*-------------------------Get OPERATION----------------------------*/
	public List<Asset> listAssets(String entityId) {
		String serviceEndPoint=null;
		if (entityId != null) {
			serviceEndPoint = this.endPointURL + "/_list?entityId=" + entityId;
		} else {
			serviceEndPoint = this.endPointURL + "/_list";
		}
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("listAssets endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			List<Asset> assets = response.readEntity(new GenericType<List<Asset>>() {
			});
			return assets;
		}
		return null;
	}

	/**
	 * Modify or updateAsset
	 * 
	 * @param asset
	 * @return response
	 */
	/*-------------------------PUT OPERATION----------------------------*/
	public ResponseEntity updateAsset(Asset asset) {
		String serviceEndPoint = this.endPointURL;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("updateAsset endPointURL  >>>" + serviceEndPoint);
		Logging.log("updateAsset Request  >>>" + gson.toJson(asset));
		Response response = executePUT(serviceEndPoint, true, Entity.entity(asset, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * deleteAsset
	 * 
	 * @param assetId
	 * @param forceDelete
	 * @return response
	 */
	/*-------------------------DELETE OPERATION----------------------------*/
	public ResponseEntity deleteAsset(String assetId, Boolean forceDelete) {
		String serviceEndPoint = this.endPointURL + "/" + assetId+"?forceDelete="+forceDelete;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log("deleteAsset endPointURL  >>>" + serviceEndPoint);
		Response response = executeDELETE(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * attachAssets
	 * 
	 * @param assetEntities
	 * @return response
	 */
	/*-------------------------POST OPERATION----------------------------*/
	public ResponseEntity attachAssets(AssetsEntities assetEntities) {
		String serviceEndPoint = this.endPointURL + "/_attachAssets";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" attachAssets endPointURL  >>>" + serviceEndPoint);
		Logging.log(" attachAssets Request  >>>" + gson.toJson(assetEntities));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(assetEntities, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * dettachAssets
	 * 
	 * @param assetEntities
	 * @return response
	 */
	/*-------------------------POST OPERATION----------------------------*/
	public ResponseEntity dettachAssets(AssetsEntities assetEntities) {
		String serviceEndPoint = this.endPointURL + "/_dettachAssets";
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" dettachAssets endPointURL  >>>" + serviceEndPoint);
		Logging.log(" dettachAssets Request  >>>" + gson.toJson(assetEntities));
		Response response = executePOST(serviceEndPoint, true,
				Entity.entity(assetEntities, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;

	}

	/**
	 * attachAsset
	 * 
	 * @param assetEntities
	 * @return response
	 */
	/*-------------------------GET OPERATION----------------------------*/
	public ResponseEntity attachAsset(String assetId, String entityId) {
		String serviceEndPoint = this.endPointURL + "/_attach/" + assetId + "/" + entityId;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" attachAsset endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;

	}

	/**
	 * dettachAsset
	 * 
	 * @param assetEntities
	 * @return response
	 */
	/*-------------------------GET OPERATION----------------------------*/
	public ResponseEntity dettachAsset(String assetId, String entityId) {
		String serviceEndPoint = this.endPointURL + "/_dettach/" + assetId + "/" + entityId;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" dettachAsset endPointURL  >>>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, true);
		if (response.getStatus() == 200) {
			ResponseEntity responseEntity = response.readEntity(ResponseEntity.class);
			return responseEntity;
		}
		Assert.fail("Throwing status: " + response.getStatus());
		return null;
	}

	/**
	 * getAsset
	 * 
	 * @param assetId
	 * @return response
	 */
	/*-------------------------GET OPERATION----------------------------*/
	public  File getAsset(String assetId) {
		String serviceEndPoint = this.endPointURL+"?assetId="+assetId;
		System.out.println("serviceEndPoint: " + serviceEndPoint);
		Logging.log(" getAsset endPointURL  >>>" + serviceEndPoint);
		setPropertyValue("Accept", "application/octet-stream");
		Response response = executeGET(serviceEndPoint, true);
		setPropertyValue("Accept", "application/json");
		if (response.getStatus() == 200) {
			File assets = response.readEntity(File.class);
			return assets;
		}
		return null;
	}
}

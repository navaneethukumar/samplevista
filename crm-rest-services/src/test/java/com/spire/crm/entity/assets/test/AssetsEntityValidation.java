package com.spire.crm.entity.assets.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.testng.Assert;

import com.spire.common.AssetFileDecode;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.entity.consumers.AssetsEntityConsumer;

import spire.crm.entity.com.entities.Asset;
import spire.crm.entity.com.entities.AssetsEntities;
import spire.crm.entity.com.entities.ResponseEntity;

/**
 * @author Manaswini
 *
 */
public class AssetsEntityValidation {

	static DataFactory factory = new DataFactory();
	final static AssetsEntityConsumer assetsEntityConsumer = new AssetsEntityConsumer();
	static ResponseEntity responseEntity = null;

	/**
	 * CreateAsset
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static ResponseEntity createAsset(TestData data) throws IOException {
		String path = null;
		File file;
		if (data.getData().equals("pdf")) {
			path = "./src/main/resources/Samplepdf.pdf";
		} else if (data.getData().equals("doc")) {
			path = "./src/main/resources/SampleDOCFile.doc";
		} else if (data.getData().equals("audio")) {
			path = "./src/main/resources/SampleAudio.mp3";
		} else if (data.getData().equals("video")) {
			path = "./src/main/resources/SampleVideo.mp4";
		}
		final FormDataContentDisposition fileDetail = FormDataContentDisposition
				.name(factory.getRandomWord() + System.currentTimeMillis()).fileName(path).build();
		file = new File(fileDetail.getFileName());
		InputStream uploadInputStream = new FileInputStream(file);

		responseEntity = assetsEntityConsumer.createAsset(fileDetail.getName(), uploadInputStream, fileDetail);
		
		File assets=assetsEntityConsumer.getAsset(responseEntity.getSuccess().values().iterator().next());
		validateCreateAsset(fileDetail,file,assets);
		return responseEntity;
	}

	/**
	 * Create Single Asset
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static ResponseEntity createSingleAsset(TestData data) throws IOException {
		String fileName = "SampleVideo.mp4";
		String path = "./src/main/resources" + File.separator + fileName;
		final FormDataContentDisposition fileDetail = FormDataContentDisposition
				.name(factory.getRandomWord() + System.currentTimeMillis()).fileName(path).build();
		File file = new File(fileDetail.getFileName());
		InputStream uploadInputStream = new FileInputStream(file);
		responseEntity = assetsEntityConsumer.createAsset(fileDetail.getName(), uploadInputStream, fileDetail);
		File assets=assetsEntityConsumer.getAsset(responseEntity.getSuccess().values().iterator().next());
		validateCreateAsset(fileDetail,file,assets);
		return responseEntity;

	}

	/**
	 * listAsset
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static void listAsset(TestData data) throws IOException {
		if (data.getData().equals("Empty")) {
			responseEntity = AssetsEntityValidation.createSingleAsset(data);
			Collection<String> id = responseEntity.getSuccess().values();
			String assetId = id.iterator().next();
			String profileId = ProfileHelper.createProfile();
			assetsEntityConsumer.attachAsset(assetId, profileId);
			String entityId = null;
			List<Asset> assets = assetsEntityConsumer.listAssets(entityId);
			validateListAssets(assetId, assets);
			Assert.assertNotNull(assets, "Not listing all the Assets");
		} else if (data.getData().equals("Existing")) {
			ResponseEntity responseEntity = AssetsEntityValidation.createSingleAsset(data);
			Collection<String> id = responseEntity.getSuccess().values();
			String assetId = id.iterator().next();
			String profileId = ProfileHelper.createProfile();
			ResponseEntity reponseEntity = assetsEntityConsumer.attachAsset(assetId, profileId);
			Assert.assertEquals(reponseEntity.getCode(), 201);
			List<Asset> assets = assetsEntityConsumer.listAssets(profileId);
			validateListAssets(assetId, assets);
		} else if (data.getData().equals("NonExisting")) {
			String profileId = ProfileHelper.createProfile();
			List<Asset> assets = assetsEntityConsumer.listAssets(profileId);
			validateListAssets(null, assets);
		}
	}

	/**
	 * modifyAsset
	 * 
	 * @param data
	 * @return labelResponse
	 * @throws IOException 
	 */
	public static void modifyAsset(TestData data) throws IOException {
		if (data.getData().equals("update")) {
			responseEntity = AssetsEntityValidation.createSingleAsset(data);
			Set<String> key = responseEntity.getSuccess().keySet();
			Collection<String> value = responseEntity.getSuccess().values();
			String fileName = key.iterator().next();
			String assetId = value.iterator().next();
			Asset asset = new Asset();
			asset.setName("TestFile");
			asset.setId(assetId);
			ResponseEntity responseEntity1 = assetsEntityConsumer.updateAsset(asset);
			Assert.assertTrue(responseEntity1.getSuccess().keySet().contains("TestFile"), "Not updating the fileName");
			Assert.assertNotEquals(fileName, asset.getName(), "Not updating the fileName");
		}
	}

	/**
	 * deleteAsset
	 * 
	 * @param data
	 * @return labelResponse
	 * @throws IOException 
	 */
	public static void deleteAsset(TestData data) throws IOException {
		ResponseEntity responseEntity1 = null;
		if (!(data.getData().equals("NonExisting"))) {
			responseEntity = AssetsEntityValidation.createAsset(data);
			Collection<String> value = responseEntity.getSuccess().values();
			String assetId = value.iterator().next();
			if (data.getData().equals("doc")) {
				responseEntity1 = assetsEntityConsumer.deleteAsset(assetId, true);
			} else if (data.getData().equals("pdf")) {
				responseEntity1 = assetsEntityConsumer.deleteAsset(assetId, true);
			} else if (data.getData().equals("audio")) {
				responseEntity1 = assetsEntityConsumer.deleteAsset(assetId, true);
			} else if (data.getData().equals("video")) {
				responseEntity1 = assetsEntityConsumer.deleteAsset(assetId, true);
			}
			Assert.assertNotNull(responseEntity1.getSuccess());
			Assert.assertTrue(responseEntity1.getMessage().contains("deleted"));
		} else {
			String assetId1 = "6002:6666:5f293af40ee84599aaa56377ac4e8";
			responseEntity1 = assetsEntityConsumer.deleteAsset(assetId1, true);
			Assert.assertFalse(responseEntity1.getFailure().isEmpty());
		}
	}

	/**
	 * attachSingleAsset
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static void attachSingleAsset(TestData data) throws IOException {
		ResponseEntity responseEntity1 = null;
		String profileId = ProfileHelper.createProfile();
		if (!(data.getData().equals("NonExisting"))) {
			responseEntity = AssetsEntityValidation.createAsset(data);
			Collection<String> value = responseEntity.getSuccess().values();
			String assetId = value.iterator().next();
			if (data.getData().equals("doc")) {
				responseEntity1 = assetsEntityConsumer.attachAsset(assetId, profileId);
			} else if (data.getData().equals("pdf")) {
				responseEntity1 = assetsEntityConsumer.attachAsset(assetId, profileId);
			} else if (data.getData().equals("audio")) {
				responseEntity1 = assetsEntityConsumer.attachAsset(assetId, profileId);
			} else if (data.getData().equals("video")) {
				responseEntity1 = assetsEntityConsumer.attachAsset(assetId, profileId);
			}
			validateAttachAssets(responseEntity1);
			List<Asset> assets = assetsEntityConsumer.listAssets(profileId);
			validateListAssets(assetId, assets);
		} else {
			String assetId = "6002:6666:86fa4e2b67a2bc73fd33a12c2";
			ResponseEntity reponseEntity = assetsEntityConsumer.attachAsset(assetId, profileId);
			Assert.assertFalse(reponseEntity.getFailure().isEmpty());
		}

	}

	/**
	 * detachSingleAsset
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static void detachSingleAsset(TestData data) throws IOException {
		if (data.getData().equals("singleDetach")) {
			responseEntity = AssetsEntityValidation.createSingleAsset(data);
			Collection<String> id = responseEntity.getSuccess().values();
			String assetId = id.iterator().next();
			String profileId = ProfileHelper.createProfile();
			ResponseEntity reponseEntity = assetsEntityConsumer.attachAsset(assetId, profileId);
			validateAttachAssets(reponseEntity);
			ResponseEntity response = assetsEntityConsumer.dettachAsset(assetId, profileId);
			validateDetachAssets(response);
			List<Asset> assets = assetsEntityConsumer.listAssets(profileId);
			validateListAssets(assetId, assets);
		}

	}

	/**
	 * attachMultipleAssets
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static void attachMultipleAssets(TestData data) throws IOException {
		AssetsEntities assetEntities = new AssetsEntities();
		responseEntity = AssetsEntityValidation.createSingleAsset(data);
		Collection<String> id = responseEntity.getSuccess().values();
		String assetId1 = id.iterator().next();
		ResponseEntity responseEntity1 = AssetsEntityValidation.createSingleAsset(data);
		Collection<String> id1 = responseEntity1.getSuccess().values();
		String assetId2 = id1.iterator().next();
		List<String> assetIds = new ArrayList<>();
		assetIds.add(assetId1);
		String profileId1 = ProfileHelper.createProfile();
		String profileId2 = ProfileHelper.createProfile();
		String profileId3 = ProfileHelper.createProfile();
		List<String> entityIds = new ArrayList<>();
		entityIds.add(profileId1);
		if (data.getData().equals("SingAssetSingEntity")) {
		} else if (data.getData().equals("MulAssetSingEntity")) {
			assetIds.add(assetId2);
		} else if (data.getData().equals("SingAssetMulEntity")) {
			entityIds.add(profileId2);
			entityIds.add(profileId3);
		} else if (data.getData().equals("MulAssetMulEntity")) {
			assetIds.add(assetId2);
			entityIds.add(profileId2);
			entityIds.add(profileId3);
		}
		assetEntities.setAssetIds(assetIds);
		assetEntities.setEntityIds(entityIds);
		ResponseEntity responseEntity2 = assetsEntityConsumer.attachAssets(assetEntities);
		validateAttachAssets(responseEntity2);
		List<Asset> assets = assetsEntityConsumer.listAssets(profileId1);
		validateMultipleListAssets(assetIds, assets);
		if (data.getData().equals("SingAssetMulEntity") || data.getData().equals("MulAssetMulEntity")) {
			List<Asset> assets1 = assetsEntityConsumer.listAssets(profileId2);
			List<Asset> assets2 = assetsEntityConsumer.listAssets(profileId3);
			validateMultipleListAssets(assetIds, assets1);
			validateMultipleListAssets(assetIds, assets2);
		}

	}

	/**
	 * detachMultipleAssets
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static void detachMultipleAssets(TestData data) throws IOException {
		AssetsEntities assetEntities = new AssetsEntities();
		responseEntity = AssetsEntityValidation.createSingleAsset(data);
		Collection<String> id = responseEntity.getSuccess().values();
		String assetId1 = id.iterator().next();
		ResponseEntity responseEntity1 = AssetsEntityValidation.createSingleAsset(data);
		Collection<String> id1 = responseEntity1.getSuccess().values();
		String assetId2 = id1.iterator().next();
		List<String> assetIds = new ArrayList<>();
		assetIds.add(assetId1);
		String profileId1 = ProfileHelper.createProfile();
		String profileId2 = ProfileHelper.createProfile();
		String profileId3 = ProfileHelper.createProfile();
		List<String> entityIds = new ArrayList<>();
		entityIds.add(profileId1);
		if (data.getData().equals("SingAssetSingEntity")) {
		} else if (data.getData().equals("MulAssetSingEntity")) {
			assetIds.add(assetId2);
		} else if (data.getData().equals("SingAssetMulEntity")) {
			entityIds.add(profileId2);
			entityIds.add(profileId3);
		} else if (data.getData().equals("MulAssetMulEntity")) {
			assetIds.add(assetId2);
			entityIds.add(profileId2);
			entityIds.add(profileId3);
		}
		assetEntities.setAssetIds(assetIds);
		assetEntities.setEntityIds(entityIds);
		ResponseEntity responseEntity2 = assetsEntityConsumer.attachAssets(assetEntities);
		validateAttachAssets(responseEntity2);
		ResponseEntity response = assetsEntityConsumer.dettachAssets(assetEntities);
		validateDetachAssets(response);
		List<Asset> assets = assetsEntityConsumer.listAssets(profileId1);
		Assert.assertEquals(assets.size(), 0, "entityId is not detached to the asset");
		if (data.getData().equals("SingAssetMulEntity") || data.getData().equals("MulAssetMulEntity")) {
			List<Asset> assets1 = assetsEntityConsumer.listAssets(profileId2);
			List<Asset> assets2 = assetsEntityConsumer.listAssets(profileId3);
			Assert.assertEquals(assets1.size(), 0, "entityId is not detached to the asset");
			Assert.assertEquals(assets2.size(), 0, "entityId is not detached to the asset");
		}
	}

	/**
	 * getAseets
	 * 
	 * @param data
	 * @throws IOException 
	 */
	public static File getAssets(TestData data) throws IOException {
		File assets =null;
		if (data.getData().equals("Existing")) {
			responseEntity = AssetsEntityValidation.createSingleAsset(data);
		} else if (data.getData().equals("NonExisting")) {
			String assetId = "6002:6666:14e91685fe754da732f0e11ffed62";
			assets = assetsEntityConsumer.getAsset(assetId);	
			Assert.assertNull(assets, "Existing file");

		}
		return assets;
	}

	/**
	 * validateCreateAsset
	 * 
	 * @param fileDetail
	 * @throws IOException 
	 */
	public static void validateCreateAsset(FormDataContentDisposition fileDetail, File file, File assets)
			throws IOException {
		Assert.assertTrue(responseEntity.getSuccess().containsKey(fileDetail.getName()), "Asset is not created");
		Assert.assertNotNull(responseEntity.getSuccess().values(), "Asset is not created");
		AssetFileDecode assetFileDecode = new AssetFileDecode();
		File decodeFile = assetFileDecode.decodeBase64File(assets);
		Assert.assertTrue(FileUtils.contentEquals(file, decodeFile),"The files differ!");
		Assert.assertEquals(FileUtils.readFileToString(file, "utf-8"),
				FileUtils.readFileToString(decodeFile, "utf-8"),"The files differ!");
	}

	/**
	 * validateListAssets
	 * 
	 * @param assetId
	 * @param assets
	 */
	public static void validateListAssets(String assetId, List<Asset> assets) {
		List<String> assId = new ArrayList<String>();
		for (Asset asset : assets) {
			String assetId1 = asset.getId();
			assId.add(assetId1);
		}
		if (assId.size() != 0) {
			Assert.assertTrue(assId.contains(assetId), "entityId is not attached to the asset");
		} else {
			Assert.assertEquals(assId.size(), 0, "listing Assets");

		}
	}

	/**
	 * validateMultipleListAssets
	 * 
	 * @param assetIds
	 * @param assets
	 */
	public static void validateMultipleListAssets(List<String> assetIds, List<Asset> assets) {
		List<String> assId = new ArrayList<String>();
		for (Asset asset : assets) {
			String assetId = asset.getId();
			assId.add(assetId);
		}
		Assert.assertTrue(assId.contains(assetIds.iterator().next()), "entityId is not attached to the asset");
	}

	/**
	 * validateAttachAssets
	 * 
	 * @param responseEntity
	 */
	public static void validateAttachAssets(ResponseEntity responseEntity) {
		Assert.assertEquals(responseEntity.getCode(), 201);
		Assert.assertNotNull(responseEntity.getSuccess(), "Asset is not attached");
		Assert.assertTrue(responseEntity.getFailure().isEmpty());
	}

	/**
	 * validateDetachAssets
	 * 
	 * @param response
	 */
	public static void validateDetachAssets(ResponseEntity response) {
		Assert.assertEquals(response.getCode(), 201);
		Assert.assertNotNull(response.getSuccess(), "Asset is not detached");
		Assert.assertTrue(response.getFailure().isEmpty());
	}

	public static void deleteAssets() {
		if(responseEntity==null){
			Assert.assertNull(responseEntity);
		}else{
			Collection<String> value = responseEntity.getSuccess().values();
			String assetId = value.iterator().next();
			assetsEntityConsumer.deleteAsset(assetId, true);
		}
	}

}

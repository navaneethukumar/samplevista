package com.spire.dfs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.testng.Assert;

import com.spire.base.controller.Assertion;

import spire.commons.dfs.gateway.JCloudDFSGateway;
import spire.commons.dfs.gateway.utils.Constants;
import spire.commons.dfs.gateway.utils.Settings;
import spire.commons.dfs.gateway.utils.SettingsFactory;

public class BlobHelper {

	public static JCloudDFSGateway createConnection(SpireDFSPojo dfsData)
			throws NoSuchElementException, IOException {

		Settings settings = null;

		settings = SettingsFactory.getInstance(Constants.TEST_ENV);

		return new JCloudDFSGateway(settings);

	}

	public static void createFile(SpireDFSPojo dfsData,
			JCloudDFSGateway jCloudDFSGateway) throws Exception {
		String fileName = dfsData.getFileName();
		String content = dfsData.getContent();
		// String container = dfsData.getContainer()
		// + UUID.randomUUID().toString();
		String container = dfsData.getContainer() + System.currentTimeMillis();
		String folderPath = dfsData.getFolderPath();

		if (fileName.equals("null")) {
			dfsData.setFileName(null);
		}
		if (folderPath.equals("null")) {
			dfsData.setFolderPath(null);
		}

		// add blob with 5 parameters
		if (dfsData.getParams().equals("5")) {

			jCloudDFSGateway.createContainer(container);

			if (container.contains("invalid")) {
				jCloudDFSGateway.addBlob(fileName, content, container + "asa",
						folderPath, null);
			} else if (dfsData.getFileName().equals("null")) {
				jCloudDFSGateway.addBlob(null, content, container, folderPath,
						null);
			} else if (dfsData.getFolderPath().equals("null")) {
				jCloudDFSGateway.addBlob(fileName, content, container, null,
						null);
			}

			else {

				jCloudDFSGateway.addBlob(fileName, content, container,
						folderPath, null);

				Assert.assertTrue(jCloudDFSGateway.isBlobExist(container,
						folderPath, fileName), "Blob not added");
			}
			if (jCloudDFSGateway.isContainerExist(container) == true) {
				jCloudDFSGateway.deleteContainer(container);
			}

		}

		// add blob with 4 parameters: file, containerName, folderPath,metadata
		if (dfsData.getParams().equals("4")) {

			boolean res = jCloudDFSGateway.createContainer(container);
			Assert.assertTrue(res, "Container not created!!"
					+ "Tried to create the container: " + container);

			File file = new File(fileName);

			jCloudDFSGateway.addBlob(file, container, folderPath, null);

			Assert.assertEquals(jCloudDFSGateway.getContainerSize(container), 1);

			Assert.assertTrue(
					jCloudDFSGateway.isBlobExist(container, folderPath,
							file.getName()), "Blob not created!!");

			if (jCloudDFSGateway.isContainerExist(container) == true) {
				jCloudDFSGateway.deleteContainer(container);
			}

		}

		// addBlobStream

		if (dfsData.getParams().equals("addBlobStream")
				|| dfsData.getParams().equals("getContentAsStream")
				|| dfsData.getParams().equals("removeBlob")) {

			boolean res = jCloudDFSGateway.createContainer(container);
			Assert.assertTrue(res, "Container not created!!"
					+ "Tried to create the container: " + container);

			Long contentSize = ((Integer) content.getBytes().length)
					.longValue();
			InputStream is = new ByteArrayInputStream(content.getBytes());
			Map<String, String> meta = new HashMap<String, String>();
			meta.put("content-length", contentSize.toString());

			if (container.contains("ContentSizeZero")) {
				jCloudDFSGateway.addBlobStream(fileName, is, 0, container,
						folderPath, meta);
			} else {

				jCloudDFSGateway.addBlobStream(fileName, is, contentSize,
						container, folderPath, meta);
			}

			Assert.assertTrue(jCloudDFSGateway.isBlobExist(container,
					folderPath, fileName));

			if (dfsData.getParams().equalsIgnoreCase("getContentAsStream")) {

				if (dfsData.getFileName().contains("Invalid")) {

					jCloudDFSGateway.getContentAsStream(container, folderPath,
							fileName + "jkjhjkh");

				} else {

					InputStream dataStraeam = jCloudDFSGateway
							.getContentAsStream(container, folderPath, fileName);

					System.out.println("Data stream: " + dataStraeam);

					Assert.assertTrue(dataStraeam != null);
				}

			}

			if (dfsData.getParams().equalsIgnoreCase("removeBlob")) {

				if (fileName.contains("InvalidFile")) {

					jCloudDFSGateway.removeBlob(container, folderPath, fileName
							+ "dfhidhi");

					Assert.assertTrue(jCloudDFSGateway.isBlobExist(container,
							folderPath, fileName),
							"Blob deleted after giving invalid file name");

				} else if (dfsData.getContainer().contains("InvalidContainer")) {

					jCloudDFSGateway.removeBlob(container + "jgig", folderPath,
							fileName);

					Assert.assertTrue(jCloudDFSGateway.isBlobExist(container,
							folderPath, fileName),
							"Blob deleted after giving invalid Container");

				} else if (fileName.contains("File_Folder")) {

					jCloudDFSGateway.removeBlob(container, folderPath + "hjgj",
							fileName + "hvj");
					Assert.assertTrue(jCloudDFSGateway.isBlobExist(container,
							folderPath, fileName),
							"Blob deleted after giving invalid file and folder name");

				} else if (fileName.contains("FileFolderContainer")) {

					jCloudDFSGateway.removeBlob(container + "aaa", folderPath
							+ "hjgj", fileName + "hvj");
					Assert.assertTrue(
							jCloudDFSGateway.isBlobExist(container, folderPath,
									fileName),
							"Blob deleted after giving invalid file, invalid folder name and invalid container name, ");
				}

				else {

					jCloudDFSGateway
							.removeBlob(container, folderPath, fileName);
					Assert.assertFalse(jCloudDFSGateway.isBlobExist(container,
							folderPath, fileName),
							"Blob existed after removing!!");
				}
			}
			if (jCloudDFSGateway.isContainerExist(container) == true) {
				jCloudDFSGateway.deleteContainer(container);
			}

		}
	}

	public static void retriveFile(SpireDFSPojo dfsData,
			JCloudDFSGateway jCloudDFSGateway) throws Exception {

		Blob blob = null;
		BlobMetadata blobMeta = null;

		if (dfsData.getIsGetBlob().equalsIgnoreCase("YES")) {

			blob = jCloudDFSGateway.getBlob(dfsData.getContainer(),
					dfsData.getFolderPath(), dfsData.getFileName());

			if (dfsData.getExpected().equalsIgnoreCase("SUCCESS")) {
				Assert.assertNotNull(blob);
				Assert.assertTrue(jCloudDFSGateway.isBlobExist(
						dfsData.getContainer(), dfsData.getFolderPath(),
						dfsData.getFileName()));
			} else {
				Assert.assertNull(blob);
				Assert.assertFalse(jCloudDFSGateway.isBlobExist(
						dfsData.getContainer(), dfsData.getFolderPath(),
						dfsData.getFileName()));
			}
		} else {

			blobMeta = jCloudDFSGateway.getBlobMetadata(dfsData.getContainer(),
					dfsData.getFolderPath(), dfsData.getFileName());

			if (dfsData.getExpected().equalsIgnoreCase("SUCCESS"))
				Assert.assertNotNull(blobMeta);
			else
				Assert.assertNull(blobMeta);

		}
		if (jCloudDFSGateway.isContainerExist(dfsData.getContainer()) == true) {
			jCloudDFSGateway.deleteContainer(dfsData.getContainer());
		}

	}

	public static void blobTest_Util(SpireDFSPojo dfsData) {
		JCloudDFSGateway jCloudDFSGateway = null;

		try {

			DFSHelper.logData(dfsData);

			jCloudDFSGateway = BlobHelper.createConnection(dfsData);

			BlobHelper.createFile(dfsData, jCloudDFSGateway);

			if (dfsData.getExpected().equalsIgnoreCase("EXCEPTION"))
				Assertion.assertTrue(false,
						"Exception is expected but did not thrown");

		} catch (Exception e) {

			if (!dfsData.getExpected().equalsIgnoreCase("EXCEPTION"))
				Assertion.assertTrue(false,
						"Exception is not expected but it thrown Exception:"
								+ e);

		} finally {

			if (jCloudDFSGateway != null) {

				try {
					jCloudDFSGateway.deleteContainer(dfsData.getContainer());
					jCloudDFSGateway.closeContext();
				} catch (Exception e) {
					//
					System.out.println(e.getMessage());
				}

			}
		}
	}

	public static void getBlobTest_Util(SpireDFSPojo dfsData) {
		JCloudDFSGateway jCloudDFSGateway = null;

		try {

			DFSHelper.logData(dfsData);

			jCloudDFSGateway = BlobHelper.createConnection(dfsData);

			BlobHelper.retriveFile(dfsData, jCloudDFSGateway);

		} catch (Exception e) {

			e.printStackTrace();
			Assertion.assertTrue(false, "exception thrown" + e.getMessage());

		} finally {

			if (jCloudDFSGateway != null) {

				try {
					jCloudDFSGateway.closeContext();
				} catch (Exception e) {
				}

			}
		}
	}

}

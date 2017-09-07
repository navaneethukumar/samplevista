package com.spire.dfs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.jclouds.blobstore.domain.StorageMetadata;
import org.testng.Assert;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;

import spire.commons.dfs.gateway.JCloudDFSGateway;
import spire.commons.dfs.gateway.utils.Constants;
import spire.commons.dfs.gateway.utils.Settings;

public class DFSHelper {

	public static JCloudDFSGateway createConnection(SpireDFSPojo dfsData)
			throws NoSuchElementException, IOException {

		Settings settings = null;


		if (dfsData.getIsInvalidCredentails().equalsIgnoreCase("YES"))
			;// settings = SettingsFactory.getInstance(Constants.DUMMY_ENV);
		else
			settings = new Settings("aws-s3","bKgJvHAjMGLSYOZlB54JzJFQxV+/3csLoHuKocT4","AKIAIIYLYIMYO2KC3OWQ",Constants.PROD_ENV);

		// Settings settings = SettingsFactory.getInstance(Constants.TEST_ENV);
		return new JCloudDFSGateway(settings);

	}

	public static void logData(SpireDFSPojo dfsData) {

		String[] data = dfsData.getTestSteps().split("#");

		for (int i = 0; i < data.length; i++) {

			if (i == 1)
				Logging.log("----------------------------------------------------");

			Logging.log(data[i]);

			if (i == data.length - 1)
				Logging.log("----------------------------------------------------");

		}

	}

	public static void createFile(SpireDFSPojo dfsData,
			JCloudDFSGateway jCloudDFSGateway) throws Exception {

		if (dfsData.getFileName_content_folderPath() != null
				|| !dfsData.getFileName_content_folderPath().equals("null")) {

			String[] data = dfsData.getFileName_content_folderPath().split(",");

			if (data.length == 3) {
				jCloudDFSGateway.addBlob(data[0], data[1],
						dfsData.getContainerName(), data[2], null);
			}

			/**
			 * jCloudDFSGateway.addBlob(file, containerName, folderPath,
			 * metadata);
			 */
			else if (data.length == 4) {
				System.out.println("4 args");
				File file = new File("src/main/resources/env/test.properties");
				jCloudDFSGateway.addBlob(file, data[1], data[2], null);

			}

			/**
			 * jCloudDFSGateway.addBlob(fileName, content, containerName,
			 * folderPath, metadata);
			 */
			else if (data.length == 5) {

				String fileName = data[0];
				String content = data[1];
				String containerName = data[2];
				String folderPath = data[3];
				if (!dfsData.getContainerName().equals("No")) {
					containerName = data[2];
					jCloudDFSGateway.createContainer(containerName);
					jCloudDFSGateway.addBlob(fileName, content, containerName,
							folderPath, null);
					Assert.assertTrue(jCloudDFSGateway.isBlobExist(
							containerName, folderPath, fileName),
							"Blob not added");
				} else {
					System.out.println("no....");
					jCloudDFSGateway.addBlob(fileName, content, containerName
							+ "z", folderPath, null);
					Assert.assertTrue(jCloudDFSGateway.isBlobExist(
							containerName + "z", folderPath, fileName),
							"Blob not added");
				}

			}

		}
	}

	public static void dfsTest_Util(SpireDFSPojo dfsData) {
		JCloudDFSGateway jCloudDFSGateway = null;

		try {
			DFSHelper.logData(dfsData);

			jCloudDFSGateway = DFSHelper.createConnection(dfsData);

			boolean result = false;

			if (!dfsData.getContainerName().equalsIgnoreCase("NO")) {

				if (dfsData.getContainerName() != null) {

					if (dfsData.getContainerName().trim()
							.equalsIgnoreCase("space"))
						dfsData.setContainerName(" ");
					else
						dfsData.setContainerName(dfsData.getContainerName()
								+ UUID.randomUUID().toString());

				}
				result = jCloudDFSGateway.createContainer(dfsData
						.getContainerName());

				if (dfsData.getExpected().equalsIgnoreCase("FAIL")) {

					Assertion
							.assertFalse(result,
									"Container created successfully though expected to fail");
					Assertion.assertFalse(jCloudDFSGateway
							.isContainerExist(dfsData.getContainerName()),
							"Container exist in S3");

				} else {

					Assertion.assertTrue(result, "Container creation failed");
					Assertion.assertTrue(jCloudDFSGateway
							.isContainerExist(dfsData.getContainerName()),
							"Container does not exist in S3");

				}

			}

			// Upload file based on CSV data

			if (dfsData.getFileName_content_folderPath() != null
					|| dfsData.getFileName_content_folderPath() != "null") {

				DFSHelper.createFile(dfsData, jCloudDFSGateway);
			}

			// DELETE starts from here

			if (dfsData.getOperation().equalsIgnoreCase("delete")) {

				if (!dfsData.getContainerName().equalsIgnoreCase("invalid")) {
					jCloudDFSGateway
							.deleteContainer(dfsData.getContainerName());
					Assert.assertFalse(jCloudDFSGateway
							.isContainerExist(dfsData.getContainerName()),
							"Delete is not happening!!");
				} else if (dfsData.getContainerName().equalsIgnoreCase(
						"invalid")) {
					jCloudDFSGateway.deleteContainer(dfsData.getContainerName()
							+ "329847");
				} else if (dfsData.getContainerName().equalsIgnoreCase(
						"SpecialChar")) {
					jCloudDFSGateway.deleteContainer(dfsData.getContainerName()
							+ "+-");
				}
			}

			if (dfsData.getOperation().equalsIgnoreCase(
					"deleteContainerIfEmpty")) {

				if (dfsData.getFileName_content_folderPath() != null
						|| dfsData.getFileName_content_folderPath()
								.equalsIgnoreCase("null")) {

					Assert.assertFalse(jCloudDFSGateway
							.deleteContainerIfEmpty(null));

					Assert.assertTrue(jCloudDFSGateway.isContainerExist(dfsData
							.getContainerName()),
							"Container not deleting using 'deleteContainerIfEmpty' interface!!");

					if (dfsData.getContainerName().contains("SpecialChar")) {
						Assert.assertFalse(jCloudDFSGateway
								.deleteContainerIfEmpty(dfsData
										.getContainerName() + "+,"));
					}
					if (dfsData.getContainerName().contains("null")) {
						Assert.assertFalse(jCloudDFSGateway
								.deleteContainerIfEmpty(null));
					}
					if (dfsData.getContainerName().contains("empty")) {
						Assert.assertFalse(jCloudDFSGateway
								.deleteContainerIfEmpty(""));
					}

				} else {

					Assert.assertTrue(jCloudDFSGateway
							.deleteContainerIfEmpty(dfsData.getContainerName()));

					Assert.assertFalse(jCloudDFSGateway
							.isContainerExist(dfsData.getContainerName()),
							"Container not deleting using 'deleteContainerIfEmpty' interface!!");
				}

			}

			// clearContainer
			if (dfsData.getOperation().equalsIgnoreCase("clearContainer")) {
				Assert.assertEquals(jCloudDFSGateway.getContainerSize(dfsData
						.getContainerName()), 1,
						"Container is not added to the bucket!!");

				jCloudDFSGateway.clearContainer(dfsData.getContainerName());

				Assert.assertEquals(jCloudDFSGateway.getContainerSize(dfsData
						.getContainerName()), 0,
						"Container not cleared after calling clearContainer!!");
			}

			// isContainerExist
			if (dfsData.getOperation().equalsIgnoreCase("isContainerExist")) {

				if (dfsData.getContainerName().equals("invalid")) {
					Assert.assertFalse(jCloudDFSGateway
							.isContainerExist(dfsData.getContainerName()
									+ "ksnd"));
				} else if (dfsData.getContainerName().equals("space")) {
					Assert.assertFalse(jCloudDFSGateway.isContainerExist(" "));
				} else if ((dfsData.getContainerName().equals("null"))) {
					Assert.assertFalse(jCloudDFSGateway.isContainerExist(null));
				} else {

					Assert.assertTrue(jCloudDFSGateway.isContainerExist(dfsData
							.getContainerName()));
				}

			}

			// listContainer
			if (dfsData.getOperation().equals("listContainer")) {

				if (dfsData.getContainerName().contains("invalid")) {
					jCloudDFSGateway.listContainer(dfsData.getContainerName()
							+ "hjihjih");
				} else if (dfsData.getContainerName().contains("null")) {
					jCloudDFSGateway.listContainer(null);
				} else if (dfsData.getContainerName().contains("empty")) {
					jCloudDFSGateway.listContainer("");
				} else {

					List<StorageMetadata> list = jCloudDFSGateway
							.listContainer();
				Assert.assertTrue(list.size() > 0,
							"listContainer is not showing containers!!");
				}
			}
			// getContainerSize
			if (dfsData.getOperation().equals("getContainerSize")) {

				if (dfsData.getContainerName().contains("invalid")) {
					jCloudDFSGateway.getContainerSize(dfsData
							.getContainerName() + "dsdh");
				} else if (dfsData.getContainerName().contains("empty")) {
					jCloudDFSGateway.getContainerSize("");
				}

				Assert.assertEquals(jCloudDFSGateway.getContainerSize(dfsData
						.getContainerName()), 1,
						"'getContainerSize' is not showing size");
			}
			// listContainer(String containerName, String folderName)
			if (dfsData.getOperation().equals("listContainer2Params")) {
				String[] data = dfsData.getFileName_content_folderPath().split(
						",");

				if (dfsData.getContainerName().contains("invalidFolder")) {
					List<StorageMetadata> list = jCloudDFSGateway
							.listContainer(dfsData.getContainerName(), data[2]
									+ "hfjfgjg");
					Assert.assertEquals(list.size(), 0,
							"List by invalid folder name is giving results!!");
				} else if (dfsData.getContainerName().contains(
						"invalidContainer")) {
					jCloudDFSGateway.listContainer(dfsData.getContainerName()
							+ "jhhghj", data[2]);
				} else if (dfsData.getContainerName().contains("null")) {
					jCloudDFSGateway.listContainer(null, data[2]);
				} else if (dfsData.getContainerName().equals("empty")) {
					jCloudDFSGateway.listContainer("", data[2]);
				} else if (dfsData.getContainerName().contains("nullFolder")) {
					jCloudDFSGateway.listContainer(dfsData.getContainerName(),
							null);

				} else if (dfsData.getContainerName().contains("emptyFolder")) {
					List<StorageMetadata> list = jCloudDFSGateway
							.listContainer(dfsData.getContainerName(), "");
					Assert.assertEquals(list.size(), 0,
							"listContainer by empty folder is giving results!!");

				} else {

					List<StorageMetadata> list = jCloudDFSGateway
							.listContainer(dfsData.getContainerName(), data[2]);
					Assert.assertTrue(
							list.size() > 0,
							"listContainer(String containerName, String folderName) is not giving results!!");
				}
			}
			// addBlob5Params
			if (dfsData.getContainerName().equalsIgnoreCase("NO")
					&& dfsData.getOperation().equals("addBlob5Params")) {

				DFSHelper.createFile(dfsData, jCloudDFSGateway);
			}

			if (dfsData.getIsDuplicate().equalsIgnoreCase("YES")) {

				result = jCloudDFSGateway.createContainer(dfsData
						.getContainerName());
				Assertion
						.assertFalse(result,
								"Container created successfully though expected to fail");
			}

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
					jCloudDFSGateway
							.deleteContainer(dfsData.getContainerName());
					jCloudDFSGateway.closeContext();
				} catch (Exception e) {
					//
					System.out.println(e.getMessage());
				}

			}

		}

	}
}

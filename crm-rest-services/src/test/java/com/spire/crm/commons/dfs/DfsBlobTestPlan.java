package com.spire.crm.commons.dfs;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.dfs.BlobHelper;
import com.spire.dfs.SpireDFSPojo;

@Test(/*groups = { "LRG" },*/ retryAnalyzer = TestRetryAnalyzer.class)
public class DfsBlobTestPlan extends CRMTestPlan {

	@DataProvider(name = "blobTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/dfs/blobTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SpireDFSPojo", SpireDFSPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					DfsBlobTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@DataProvider(name = "getBlobTestData")
	public static Iterator<Object[]> getBlobInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/dfs/getBlobTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SpireDFSPojo", SpireDFSPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					DfsBlobTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "blobTest_Sanity", "Sanity" }, dataProvider = "blobTestData")
	public void blobTest_Sanity(SpireTestObject testObject, SpireDFSPojo dfsData) {

		BlobHelper.blobTest_Util(dfsData);
	}

	@Test(groups = { "blobTest_SRG", "SRG" }, dataProvider = "blobTestData")
	public void blobTest_SRG(SpireTestObject testObject, SpireDFSPojo dfsData) {

		BlobHelper.blobTest_Util(dfsData);
	}

	@Test(groups = { "blobTest_LRG", "LRG" }, dataProvider = "blobTestData")
	public void blobTest_LRG(SpireTestObject testObject, SpireDFSPojo dfsData) {

		BlobHelper.blobTest_Util(dfsData);
	}

	@Test(groups = { "getBlobTest_Sanity", "Sanity" }, dataProvider = "getBlobTestData")
	public void getBlobTest_Sanity(SpireTestObject testObject,
			SpireDFSPojo dfsData) {

		BlobHelper.getBlobTest_Util(dfsData);

	}

	@Test(groups = { "getBlobTest_SRG", "SRG" }, dataProvider = "getBlobTestData")
	public void getBlobTest_SRG(SpireTestObject testObject, SpireDFSPojo dfsData) {

		BlobHelper.getBlobTest_Util(dfsData);

	}

	@Test(groups = { "getBlobTest_LRG", "LRG" }, dataProvider = "getBlobTestData")
	public void getBlobTest_LRG(SpireTestObject testObject, SpireDFSPojo dfsData) {

		BlobHelper.getBlobTest_Util(dfsData);

	}

}

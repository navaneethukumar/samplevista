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
import com.spire.dfs.DFSHelper;
import com.spire.dfs.SpireDFSPojo;

@Test(/*groups = { "LRG" },*/ retryAnalyzer = TestRetryAnalyzer.class)
public class DfsContainerTestPlan extends CRMTestPlan {

	@DataProvider(name = "dfsTestData")
	public static Iterator<Object[]> getCandidateInfo(Method method) {

		Iterator<Object[]> objectsFromCsv = null;

		try {
			String fileName = "./src/test/java/com/spire/crm/commons/dfs/dfsTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());

			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SpireDFSPojo", SpireDFSPojo.class);

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(
					DfsContainerTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "dfsContainer_Sanity", "Sanity" }, dataProvider = "dfsTestData")
	public void dfsContainer_Sanity(SpireTestObject testObject,
			SpireDFSPojo dfsData) {

		DFSHelper.dfsTest_Util(dfsData);

	}

	@Test(groups = { "dfsContainer_SRG", "SRG" }, dataProvider = "dfsTestData")
	public void dfsContainer_SRG(SpireTestObject testObject,
			SpireDFSPojo dfsData) {

		DFSHelper.dfsTest_Util(dfsData);

	}

	@Test(groups = { "dfsContainer_LRG", "LRG" }, dataProvider = "dfsTestData")
	public void dfsContainer_LRG(SpireTestObject testObject,
			SpireDFSPojo dfsData) {

		DFSHelper.dfsTest_Util(dfsData);

	}

}

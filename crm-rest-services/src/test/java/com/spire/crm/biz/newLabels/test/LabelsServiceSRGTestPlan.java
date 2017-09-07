package com.spire.crm.biz.newLabels.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.NewLabelsBizServiceConsumer;

@Test(groups = { "SRG" }, retryAnalyzer = TestRetryAnalyzer.class)
public class LabelsServiceSRGTestPlan extends TestPlan {
	String SERVICE_ENDPOINT_URL = null;
	NewLabelsBizServiceConsumer newLabelsBizServiceConsumer = null;

	final static String DATAPROVIDER_NAME = "NEW_LABELS_BIZ";

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/newLabels/test/NewLabelsBizSRGTestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("TestData", TestData.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(LabelsServiceSRGTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "CreateDuplicateLabel" }, dataProvider = DATAPROVIDER_NAME)
	public void CreateDuplicateLabel(SpireTestObject testObject, TestData data)

	{
		Logging.log("Test Case Execution started >>> CreateDuplicateLabel !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.CreateDuplicateLabel(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "Attach_OldLabel_To_ProfileID" }, dataProvider = DATAPROVIDER_NAME)
	public void Attach_OldLabel_To_ProfileID(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> Attach_OldLabel_To_ProfileID !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.Attach_OldLabel_To_ProfileID(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "Attach_SingleLabel_To_MultipleProfileIDs" }, dataProvider = DATAPROVIDER_NAME)
	public void Attach_SingleLabel_To_MultipleProfileIDs(SpireTestObject testObject, TestData data) {

		Logging.log(
				"Test Case Execution started >>> Attach_SingleLabel_To_MultipleProfileIDs !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.Attach_SingleLabel_To_MultipleProfileIDs(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "Attach_DuplicateLabel" }, dataProvider = DATAPROVIDER_NAME)
	public void Attach_DuplicateLabel(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> Attach_DuplicateLabel label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.Attach_DuplicateLabel(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "Detach_DuplicateLabel" }, dataProvider = DATAPROVIDER_NAME)
	public void Detach_DuplicateLabel(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> Detach_DuplicateLabel Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.Detach_DuplicateLabel(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "Detach_DuplicateLabel_Shared" }, dataProvider = DATAPROVIDER_NAME)
	public void Detach_DuplicateLabel_Shared(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> Detach_DuplicateLabel_Shared Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.Detach_DuplicateLabel_Shared(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "Detach_DuplicateLabel_Private_LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void Detach_DuplicateLabel_Private_LRG(SpireTestObject testObject, TestData data) {

		Logging.log(
				"Test Case Execution started >>> Detach_DuplicateLabel_Private_LRG Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.Detach_DuplicateLabel(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "RemoveLabel_LRG" }, dataProvider = DATAPROVIDER_NAME)
	public void RemoveLabel_LRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> RemoveLabel_LRG Label !!!" + data.getTestSteps());
		LabelSRGvalidation.RemoveLabel_LRG(data);
	}

	@Test(groups = { "ListLabels_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void ListLabels_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> ListLabels_SRG Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.ListLabels_SRG(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "ListProfiles_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void ListProfiles_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> ListProfiles_SRG Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.ListProfiles_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "ListProfiles_By_InvalidID_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void ListProfiles_By_InvalidID_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> ListProfiles_By_InvalidID_SRG Label !!!" + data.getTestSteps());
		LabelSRGvalidation.ListProfiles_By_InvalidID_SRG();
	}

	@Test(groups = { "filter_Label_By_LabelID_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filter_Label_By_LabelID_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> filter_Label_By_LabelID_SRG Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filter_Label_By_LabelID_SRG(data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filter_Labels_By_LabelIds_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filter_Labels_By_LabelIds_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> filter_Labels_By_LabelIds_SRG Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filter_Labels_By_LabelIds_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filter_Labels_By_LabelIds_with_one_invalidID_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filter_Labels_By_LabelIds_with_one_invalidID_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> filter_Labels_By_LabelIds_with_one_invalidID_SRG Label !!!"
				+ data.getTestSteps());
		try {
			LabelSRGvalidation.filter_Labels_By_LabelIds_with_one_invalidID_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filter_Label_By_Labelname_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filter_Label_By_Labelname_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filter_Label_By_Labelname_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filter_Labels_By_Labelnames_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filter_Labels_By_Labelnames_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filter_Labels_By_Labelnames_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filter_Labels_By_Labelnames_with_one_invalidName_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filter_Labels_By_Labelnames_with_one_invalidName_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filter_Labels_By_Labelnames_with_one_invalidName_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filterSharedLabelByLabelId_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filterSharedLabelByLabelId_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filterSharedLabelByLabelId_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filterSharedLabelsByLabelIds_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filterSharedLabelsByLabelIds_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filterSharedLabelsByLabelIds_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filterLabelsByLabelIdsNlabelNames_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filterLabelsByLabelIdsNlabelNames_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filterLabelsByLabelIdsNlabelNames_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "filterSharedLabelByLabelName_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void filterSharedLabelByLabelName_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.filterSharedLabelByLabelName_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "remove_Label_By_InvalidId_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void remove_Label_By_InvalidId_SRG(SpireTestObject testObject, TestData data) throws IOException {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		LabelSRGvalidation.remove_Label_By_InvalidId_SRG(data);
	}

	@Test(groups = { "bulk_remove_with_oneInvalidId_SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void bulk_remove_with_oneInvalidId_SRG(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.bulk_remove_with_oneInvalidId_SRG(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "createSameLabelForTwoUsers" }, dataProvider = DATAPROVIDER_NAME)
	public void createSameLabelForTwoUsers(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.createSameLabelForTwoUsers(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "createNShareUser2" }, dataProvider = DATAPROVIDER_NAME)
	public void createNShareUser2(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.createNShareUser2(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "createMultipleLabelsNShareUser2" }, dataProvider = DATAPROVIDER_NAME)
	public void createMultipleLabelsNShareUser2(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.createMultipleLabelsNShareUser2(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "deleteSharedLabel" }, dataProvider = DATAPROVIDER_NAME)
	public void deleteSharedLabel(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.deleteSharedLabel(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "shareLabelTwoTimes" }, dataProvider = DATAPROVIDER_NAME)
	public void shareLabelTwoTimes(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.shareLabelTwoTimes(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "renameSharedLabel" }, dataProvider = DATAPROVIDER_NAME)
	public void renameSharedLabel(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.renameSharedLabel(testObject, data);
		} catch (IOException e) {
			Assert.fail("Run Time Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(groups = { "removeUsers" }, dataProvider = DATAPROVIDER_NAME)
	public void removeUsers(SpireTestObject testObject, TestData data) {

		Logging.log("Test Case Execution started >>> verifyDetach Label !!!" + data.getTestSteps());
		try {
			LabelSRGvalidation.removeUsers(testObject, data);
		} catch (IOException e) {
			e.printStackTrace();

			Assert.fail("run time exception " + e.getMessage());
		}

	}

}

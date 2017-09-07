package com.spire.crm.biz.job.post.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.controller.TestRetryAnalyzer;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.TestData;
import com.spire.crm.restful.biz.consumers.JobPostBizServiceConsumer;

import spire.commons.config.entities.SpireConfiguration;
import spire.commons.logger.Logger;
import spire.commons.logger.LoggerFactory;
import spire.core.extraction.jd.response.JobDescriptionDetailsResponse;
import spire.crm.jobpost.core.JobLocation;
import spire.crm.jobpost.core.JobPost;
import spire.crm.jobpost.core.JobPostAnalytics;
import spire.crm.jobpost.core.SeedDataWrapper;
import spire.talent.common.beans.CollectionEntity;
import spire.talent.entity.demandservice.beans.RequisitionBean;

/**
 * 
 * @author Pradeep
 *
 */
@Test(groups = { "SANITY" }, retryAnalyzer = TestRetryAnalyzer.class)
public class JobPostServiceTestPlan extends TestPlan {
	HashMap<String, String> values = new HashMap<String, String>();
	final static String DATAPROVIDER_NAME = "JOBPOST";
	JobPostBizServiceConsumer jobPostBizServiceConsumer = null;
	private static Logger logger = LoggerFactory.getLogger(JobPostServiceTestPlan.class);
	SpireConfiguration spireConfiguration = null;
	private String JOBBOAR_ID = "6001:7001:3596101EA95344D1B824BA7BBD4A333A";
	private String REQ_DIS_ID = null;
	String GROUPBY = "JOB_BOARDS";
	String JOBPOST_ID = null;
	String startDate = "01/01/2015";
	String endDate = "12/12/2016";
	String jobBoardId = "1642";
	String filePath = "./src/main/resources/test_profile.txt";
	String limit = "100";
	String offset = "0";
	String REQUI_ID = null;
	String URL = "http://jobsearch.naukri.com/premium-jobs-for-iit-engineering-graduates";

	@DataProvider(name = DATAPROVIDER_NAME)
	public static Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = "./src/test/java/com/spire/crm/biz/job/post/test/JobPostService_TestData.csv";
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("data", TestData.class);
			entityClazzMap.put("JobLocation", JobLocation.class);
		//	entityClazzMap.put("requisitionBean", RequisitionBean.class);
			 

			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(JobPostServiceTestPlan.class, entityClazzMap,
					fileName, null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Parameters({ "JOBBOARD_ID" })
	@BeforeClass(alwaysRun = true)
	public void setUp() {
		this.JOBBOAR_ID = "6001:7001:3596101EA95344D1B824BA7BBD4A333A";
		jobPostBizServiceConsumer = new JobPostBizServiceConsumer();
		List<SeedDataWrapper> seedData = jobPostBizServiceConsumer.getSeeddata();
		if (seedData == null) {
			Logging.log(" --All The Test-Cases will be skipped-- ");
			Assert.fail("--SEED-DATA Failed !!!!");
		}

	}

	/**
	 * verifyGetSeeddata
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetSeeddata", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 3)
	public void verifyGetSeeddata(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			List<SeedDataWrapper> seedData = jobPostBizServiceConsumer.getSeeddata();
			if (seedData == null) {
				throw new RuntimeException("--verifyGetSeeddata ---");
			}
			Assert.assertTrue(true, "TestCase verifyGetSeeddata execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetSeeddata -> failed", e);
		}
	}

	/**
	 * verifyGetSeeddata
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyCreateRequisition" })
	@Parameters({ "REQUISITION_SERVICE_URL" })
	public void verifyCreateRequisition(String ReqSerUrl) {
		REQUI_ID = jobPostBizServiceConsumer.createRequisition(ReqSerUrl);
		System.out.println("Req ID is :" + REQ_DIS_ID);
	}

	/**
	 * verifyGetOpenJobDetails
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetOpenJobDetails", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 4)
	public void verifyGetOpenJobDetails(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			//create a req and verify in the list 
			Collection<JobPost> jobPost = jobPostBizServiceConsumer.getOpenJobDetails();
			for(JobPost job : jobPost) {
				
			}
			if (jobPost == null) {
				throw new RuntimeException("--verifyGetOpenJobDetails ---");
			}
			Assert.assertTrue(true, "TestCase verifyGetOpenJobDetails execution success !!!");
		} catch (Throwable e) {
			System.out.println(e);
			Assert.fail("TestCase >>> verifyGetOpenJobDetails -> failed", e);
		}
	}

	// Test

	@Test(groups = { "verifyGetJobDetailsOne", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 5)
	public void verifyGetJobDetailsOne(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			spire.crm.jobpost.page.CollectionEntity<JobPost> jobPost = jobPostBizServiceConsumer.getJobDetails(limit,
					offset);
			if (jobPost == null) {
				throw new RuntimeException("--verifyGetJobDetails ---");
			}

			List<JobPost> collectionJobPost = (List<JobPost>) jobPost.getItems();
			Iterator<JobPost> iteJobPost = collectionJobPost.iterator();
			while (iteJobPost.hasNext()) {
				JobPost jobPostRes = iteJobPost.next();
				this.JOBPOST_ID = jobPostRes.getJobPostID();
				if (!this.JOBBOAR_ID.isEmpty() && this.JOBBOAR_ID != null)
					break;
				else {
					Assert.fail("TestCase verifyGetJobDetails execution failed !!!");
				}
			}
			Assert.assertTrue(true, "TestCase verifyGetJobDetails execution success !!!");
		} catch (Exception e) {
			System.out.println(e);
			Assert.fail("TestCase >>> verifyGetJobDetails -> failed", e);
		}
	}

	/**
	 * verifyGetJobDetails
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test(groups = { "verifyGetJobDetails", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 6)
	public void verifyGetJobDetails(SpireTestObject testObject, TestData data, JobLocation jobLocation)
			throws JsonParseException, JsonMappingException, IOException {

		spire.crm.jobpost.page.CollectionEntity<JobPost> jobPost = jobPostBizServiceConsumer.getJobDetails(limit,
				offset);
	/*
		 * if (jobPost == null) { throw new RuntimeException(
		 * "--verifyGetJobDetails ---"); } Collection<JobPost> collectionJobPost
		 * = jobPost.getItems(); Iterator<JobPost> iteJobPost =
		 * collectionJobPost.iterator(); while (iteJobPost.hasNext()) { JobPost
		 * jobPostRes = iteJobPost.next(); this.JOBPOST_ID =
		 * jobPostRes.getJobPostID(); System.out.println("JPIdis : "
		 * +this.JOBPOST_ID); if (!this.JOBBOAR_ID.isEmpty() && this.JOBBOAR_ID
		 * != null) break; else { Assert.fail(
		 * "TestCase verifyGetJobDetails execution failed !!!"); } }
		 * Assert.assertTrue(true,
		 * "TestCase verifyGetJobDetails execution success !!!");
		 */
	}

	/**
	 * verifyGetJobDetails
	 * 
	 * @param testObject
	 * @param data
	 */
	/*@Test(groups = { "verifyDetailsOfOpenPostions", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 7)
	public void verifyDetailsOfOpenPostions(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			spire.talent.common.beans.CollectionEntity<JobPost> jobPost = jobPostBizServiceConsumer
					.getPostOpenPositions(REQUI_ID);
			if (jobPost == null) {
				throw new RuntimeException("--verifyGetJobDetails ---");
			}
			Assert.assertTrue(true, "TestCase verifyGetJobDetails execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetJobDetails -> failed", e);
		}
	}*/

	/**
	 * verifyGetJobPostAnalyticsData
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetJobPostAnalyticsData", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 8)
	public void verifyGetJobPostAnalyticsData(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			CollectionEntity<JobPostAnalytics> jobPostAnalytics = jobPostBizServiceConsumer
					.getJobPostAnalyticsData(GROUPBY);
			if (jobPostAnalytics == null) {
				throw new RuntimeException("--verifyGetJobPostAnalyticsData ---");
			}
			Assert.assertTrue(true, "TestCase verifyGetJobPostAnalyticsData execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetJobPostAnalyticsData -> failed", e);
		}
	}

	/**
	 * verifyGetWeeklyJobPostAnalyticsData
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetWeeklyJobPostAnalyticsData", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 9)
	public void verifyGetWeeklyJobPostAnalyticsData(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			CollectionEntity<JobPostAnalytics> jobPostAnalytics = jobPostBizServiceConsumer
					.getWeeklyJobPostAnalyticsData(GROUPBY, JOBPOST_ID);
			if (jobPostAnalytics == null) {
				throw new RuntimeException("--verifyGetWeeklyJobPostAnalyticsData ---");
			}
			Assert.assertTrue(true, "TestCase verifyGetWeeklyJobPostAnalyticsData execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetWeeklyJobPostAnalyticsData -> failed", e);
		}
	}

	/**
	 * verifyGetDateRangeJobPostAnalyticsData
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyGetDateRangeJobPostAnalyticsData","Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 2)
	public void verifyGetDateRangeJobPostAnalyticsData(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		spire.talent.common.beans.CollectionEntity<JobPostAnalytics> jobPostAnalytics = jobPostBizServiceConsumer
					.getDateRangeJobPostAnalyticsData(GROUPBY, startDate, endDate, JOBPOST_ID);
			/*if (jobPostAnalytics == null) {
				throw new RuntimeException("--verifyGetDateRangeJobPostAnalyticsData ---");
			}
			Assert.assertTrue(true, "TestCase verifyGetDateRangeJobPostAnalyticsData execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyGetDateRangeJobPostAnalyticsData -> failed", e);
		}*/
	}

	/* POST */
	/**
	 * verifyJobPost
	 * 
	 * @param testObject
	 * @param data
	 */

	@Test(groups = { "verifyJobPost","Sanity"}, dataProvider = DATAPROVIDER_NAME, priority = 1)
	public void verifyJobPost(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			String jobPostId = jobPostBizServiceConsumer.postJobDetail(JobPostServiceValidation.getJobPostDetails(),
					null, jobBoardId, jobLocation);
		
			if (jobPostId == null) {
				throw new RuntimeException("--verifyJobPost ---");
			}
			Assert.assertTrue(true, "TestCase verifyJobPost execution success !!!");
		} catch (Throwable e) {

			System.out.println(e);
			Assert.fail("TestCase >>> verifyJobPost -> failed", e);
		}
	}
	
	@Test(groups = { "closeJobPost","LRG"}, dataProvider = DATAPROVIDER_NAME)
	public void closeJobPost(SpireTestObject testObject, TestData data, JobLocation jobLocation) throws ParseException{
		RequisitionBean requisitionBean1 = new RequisitionBean();
		String jobPostId = jobPostBizServiceConsumer.getReqId(JobPostServiceValidation.getJobPostDetails(), null,
				jobBoardId, jobLocation);
		String displayId = JobPostServiceValidation.getJobPostDetails().getDisplayId();
		jobPostBizServiceConsumer.closeRequisition(requisitionBean1,jobPostId,displayId);
		
		
	}

	@Test(groups = { "verifyJPInOpenPosition", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyJPInOpenPosition(SpireTestObject testObject, TestData data, JobLocation jobLocation)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		String jobPostId = jobPostBizServiceConsumer.getReqId(JobPostServiceValidation.getJobPostDetails(), null,
				jobBoardId, jobLocation);
		
		Collection<JobPost> jobPost = jobPostBizServiceConsumer.getOpenJobDetails();
		for (JobPost job : jobPost) {
			
			if (jobPostId.equals(job.getId())) {
				Assert.assertTrue(true, "Posted Job is Avaialable in Open Positions");
				Logging.log("Posted Job is Avaialable in Open Positions");
				break;
			} else {
				Assert.fail("Posted Job is not Avaialable in Open Positions");
				Logging.log("Posted Job is not Avaialable in Open Positions");
			}
		}
	}
	
	@Test(groups = { "verifyPostedJobInJobdetails", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void verifyPostedJobInJobdetails(SpireTestObject testObject, TestData data, JobLocation jobLocation)
			throws JsonParseException, JsonMappingException, IOException {

		try {
			String jobPostId = jobPostBizServiceConsumer.postJobDetail(JobPostServiceValidation.getJobPostDetails(),
					null, jobBoardId, jobLocation);
			String jobPost = jobPostBizServiceConsumer.getJobDetailsAsString(limit, offset);

			if (jobPost.contains(jobPostId)) {
				Assert.assertTrue(true, "Posted Job ID " + jobPostId + "Available in Posted Jobs.");
				Logging.log("Posted Job ID " + jobPostId + "Available in Posted Jobs");
			} else {
				Assert.fail("Posted Job is not Available in Posted Jobs");
				Logging.log("Posted Job ID " + jobPostId + " is not Available in Posted Jobs");
			}
						
			
		} catch (ParseException e) {
			Assert.fail("Got Exception In Catch Block");
			Logging.log("Got Exception In Catch Block with Exception " + e.getStackTrace());
		}
	}

	@Test(groups = { "verifyJobPostUS" ,"Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 10)
	public void verifyJobPostUS(SpireTestObject testObject, TestData data, JobLocation jobLocation) throws ParseException {
			String jobPostId = jobPostBizServiceConsumer.postJobDetailUS(JobPostServiceValidation.getJobPostDetails(),
					null, jobBoardId, jobLocation);
			values.put("JPId", jobPostId);
	}

	@Test(groups = { "wrongJobLocationValidation", "SRG" }, dataProvider = DATAPROVIDER_NAME)
	public void wrongJobLocationValidation(SpireTestObject testObject, TestData data, JobLocation jobLocation)
			throws ParseException {
		jobPostBizServiceConsumer.postJobDetailLocValidation(JobPostServiceValidation.getJobPostDetails(), null,
				jobBoardId, jobLocation);
	}

	/**
	 * URL Extraction
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyJobPostByURL", "Sanity"}, dataProvider = DATAPROVIDER_NAME, priority = 11)
	public void verifyJobPostByURL(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			jobPostBizServiceConsumer.postURLExtraction(this.URL);
			Assert.assertTrue(true, "TestCase verifyJobPostByURL execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyJobPostByURL -> failed", e);
		}
	}

	/**
	 * FileUpload
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyFileUpload", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 12)
	public void verifyFileUpload(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		JobDescriptionDetailsResponse jobDescriptionDetailsResponse = null;
		String Resposne;
		try {
			// jobDescriptionDetailsResponse =
			// jobPostBizServiceConsumer.fileUploadExtraction(filePath);
			Resposne = jobPostBizServiceConsumer.fileUploadExtraction(filePath);

			if (Resposne == null)
				Assert.fail("jobDescriptionDetailsResponse is null");
			Assert.assertTrue(true, "TestCase verifyFileUpload execution success !!!");
		} catch (Throwable e) {

			System.out.println(e);
			Assert.fail("TestCase >>> verifyFileUpload -> failed", e);
		}
	}

	/**
	 * Profile upload
	 * 
	 * @param testObject
	 * @param data
	 */
	@Test(groups = { "verifyProfileUpload", "Sanity" }, dataProvider = DATAPROVIDER_NAME, priority = 13)
	public void verifyProfileUpload(SpireTestObject testObject, TestData data, JobLocation jobLocation) {
		try {
			// this.REQ_DIS_ID
			String jobPostAnalytics = jobPostBizServiceConsumer.profileUploadForJobPost(this.REQ_DIS_ID, null,
					this.JOBBOAR_ID, filePath);
			if (jobPostAnalytics == null) {
				throw new RuntimeException("--verifyProfileUpload ---");
			}
			Assert.assertTrue(true, "TestCase verifyProfileUpload execution success !!!");
		} catch (Throwable e) {
			Assert.fail("TestCase >>> verifyJobPost -> failed", e);
		}
	}

}
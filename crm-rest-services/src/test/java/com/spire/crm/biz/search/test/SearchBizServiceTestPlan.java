package com.spire.crm.biz.search.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import spire.search.commons.entities.CandidateExt;
import spire.search.commons.entities.SearchResult;
import spire.search.commons.exceptions.SystemException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.spire.base.controller.Logging;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.CrmSearchBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.ElasticSearchServiceConsumer;
import com.spire.crm.restful.util.CreateProfile;
import com.spire.crm.restful.util.ProfileDataPoints;
import com.spire.crm.restful.util.RequisitionDataPointsBean;
import com.spire.crm.restful.util.SearchBizBean;

/**
 * 
 * @author Pradeep
 *
 */
@Test(groups = { "SANITY" })
public class SearchBizServiceTestPlan {
	String SERVICE_ENDPOINT_URL = null;
	String redisHost =ReadingServiceEndPointsProperties.getServiceEndPoint("REDIS_HOST");
	CrmSearchBizServiceConsumer crmSearchBizServiceConsumer=new CrmSearchBizServiceConsumer();
	// profile data provider
	final static String PROFILE = "PROFILE";
	final static String SEARCH = "SEARCH";
	final static String PROFILE_PATH = "./src/test/java/com/spire/crm/biz/search/test/";
	final static String PROFILE_CSV = "ProfileData.csv";
	final static String PROFILE_DATA = PROFILE_PATH + PROFILE_CSV;
	// Search data points
	final static String SEARCH_PATH = "./src/test/java/com/spire/crm/biz/search/test/";
	final static String SEARCH_CSV = "SearchBizService.csv";
	final static String SEARCH_DATA = SEARCH_PATH + SEARCH_CSV;
	Object[] profiles = new Object[3];
	Iterator<Object[]> profiledataSearch = null;
	boolean DATA_EXECUTION = true;
	String SEARCH_QUERY = "skill:spireskill1 OR skill:spireskill2 OR skill:spireskill3";

	@DataProvider(name = PROFILE)
	public Iterator<Object[]> getCandidateInfo(Method method) {
		Iterator<Object[]> profiledata = null;
		try {
			String fileName = PROFILE_DATA;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("ProfileDataPoints", ProfileDataPoints.class);
			// if (DATA_EXECUTION == true)
			profiledata = SpireCsvUtil.getObjectsFromCsv(SearchBizServiceTestPlan.class, entityClazzMap, fileName, null,
					methodFilter);
			profiledataSearch = SpireCsvUtil.getObjectsFromCsv(SearchBizServiceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return profiledata;
	}

	@DataProvider(name = SEARCH)
	public static Iterator<Object[]> getSearchInput(Method method) {
		Iterator<Object[]> objectsFromCsv = null;
		try {
			String fileName = SEARCH_DATA;
			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("SearchBizBean", SearchBizBean.class);
			entityClazzMap.put("RequisitionDataPointsBean", RequisitionDataPointsBean.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(SearchBizServiceTestPlan.class, entityClazzMap, fileName,
					null, methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectsFromCsv;
	}

	@BeforeTest(alwaysRun = true)
	public void setUp() throws InterruptedException ,  SystemException{
		SearchBizBean searchBizBean = new SearchBizBean();
		List<String> skills = new ArrayList<String>();
		skills.add("spireskill1");
		skills.add("spireskill2");
		skills.add("spireskill3");
		searchBizBean.setSearchQueryString(SEARCH_QUERY);
		searchBizBean.setSkills(skills);
		searchBizBean.setClient("tv");
		SearchResult<CandidateExt> searchOutput = SearchBizValidation.executeSearchQuery(searchBizBean);
		
		while (searchOutput.getTotal()!=0) {
		
			List<CandidateExt> candidates=searchOutput.getEntities();
			
			for (Iterator iterator = candidates.iterator(); iterator.hasNext();) {
					
				ElasticSearchServiceConsumer esConsumer = new ElasticSearchServiceConsumer();
				CandidateExt candidateExt = (CandidateExt) iterator.next();
				esConsumer.deleteCandidate(candidateExt.getId());			
			
			}
			
			searchOutput = SearchBizValidation.executeSearchQuery(searchBizBean);
			
			Thread.sleep(40000);
			
		}
		
		if (searchOutput.getTotal() == 3) {
			this.DATA_EXECUTION = false;
		} else {
			Logging.info("No need of data creation !!!");
		}

	}

	/**
	 * Create Profile for data points
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 */

	@Test(groups = { "createFullProfile" }, dataProvider = PROFILE,alwaysRun=true)
	public void createFullProfile(SpireTestObject testObject, ProfileDataPoints profileDataPoints) throws InterruptedException, IOException {
		
		String candidateId=null;
		if (DATA_EXECUTION){
			candidateId=CreateProfile.profileCreation(profileDataPoints);
		}
		Thread.sleep(40000);
		
		int i=0;
		
		while (true) {
			
			ElasticSearchServiceConsumer esConsumer = new ElasticSearchServiceConsumer();
			Response response=esConsumer.getCandidate(candidateId);
			if(response.getStatus()==200){
				String profile = response.readEntity(String.class);
				String skill[]=profile.split("\"skill\"");
				skill[1]=skill[1].substring(2, 13);
				skill[2]=skill[2].substring(2, 13);
				ArrayList<String> skills=new ArrayList<String>();
				skills.add(skill[1]);
				skills.add(skill[2]);
				SearchBizValidation.addSkillExpertiseInES(candidateId, skills);
				if (profile.toUpperCase().contains(profileDataPoints.getCrm().toUpperCase())) {
					break;	
				}
							
			}
			
			Thread.sleep(40000);

/*			Jedis jedis = new Jedis(redisHost);
			jedis.flushAll();*/
			
			i++;
				
			if (i==1) {
				break;
			}
		}
	}

	/**
	 * 
	 * @param testObject
	 * @param searchBizBean
	 */
	@Test(groups = { "verifySearchQuery","Sanity" }, dataProvider = SEARCH, alwaysRun = true)
	public void verifySearchQuery(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws SystemException{
		SearchResult<CandidateExt> searchOutput = SearchBizValidation.executeSearchQuery(searchBizBean);
		if (searchOutput == null) {
			Assert.fail("search service is down !!!");
		} else {
			int i = 0;
			while (profiledataSearch.hasNext()) {
				Object[] csvData = profiledataSearch.next();
				ProfileDataPoints profileDataPoints = (ProfileDataPoints) csvData[1];
				profiles[i] = profileDataPoints;
				i++;
			}
			try {
				SearchBizValidation.searchResultValidation(searchOutput, profiles);
			} catch (Exception e) {
				Assert.fail("validation failed !!!");
			}

		}
	}
	
	/**
	 *search By elastic search 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */

	@Test(groups = { "searchByES","Sanity"}, dataProvider = SEARCH)
	public void searchByES(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException, JsonParseException, JsonMappingException, IOException , SystemException{
		Thread.sleep(10000);
		SearchResult<CandidateExt> searchOutput=SearchBizValidation.executeSearchQuery(searchBizBean);
		SearchBizValidation.verifySearchByES(searchOutput);
	}
	
	/**
	 *searchMatchFlowByIDWithES 
	 * 
	 * @param testObject
	 * @param data
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */

	@Test(groups = { "searchMatchFlowByIDWithES","Sanity"}, dataProvider = SEARCH)
	public void searchMatchFlowByIDWithES(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException, JsonParseException, JsonMappingException, IOException {
		SearchBizValidation.verifySearchByESForMatchFlow();
	}
	/**
	 *searchByStemmingSkills
	 * 
	 * @param testObject
	 * @param searchBizBean
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	@Test(groups = { "searchByStemmingWords","Sanity"}, dataProvider = SEARCH)
	public void searchByStemmingWords(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySearchByStemmingWords(searchBizBean);
	}
	
	@Test(groups = { "searchByHandlesinglecharacters","Sanity"}, dataProvider = SEARCH)
	public void searchByHandlesinglecharacters(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySearchByStemmingWords(searchBizBean);
	}
	
	@Test(groups = { "searchByRemovespecialcharacters","Sanity"}, dataProvider = SEARCH)
	public void searchByRemovespecialcharacters(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySearchByStemmingWords(searchBizBean);
	}
	
	@Test(groups = { "searchByConcattokens","Sanity"}, dataProvider = SEARCH)
	public void searchByConcattokens(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySearchByStemmingWords(searchBizBean);
	}
	
	@Test(groups = { "searchByLowercase","Sanity"}, dataProvider = SEARCH)
	public void searchByLowercase(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySearchByStemmingWords(searchBizBean);
	}
	
	@Test(groups = { "searchByRIExplain","Sanity"}, dataProvider = SEARCH)
	public void searchByRIExplain(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException{
		SearchBizValidation.verifyRIByExpalin(searchBizBean);
	}
	
	@Test(groups = { "searchBySpecialCharacterSkills","Sanity"}, dataProvider = SEARCH)
	public void searchBySpecialCharacterSkills(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException{
		SearchBizValidation.searchBySpecialCharacterSkills(searchBizBean);
	}
	@Test(groups = { "searchByFreeSearch","Sanity"}, dataProvider = SEARCH)
	public void searchByFreeSearch(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException{
		String empty=null;
		Response response=crmSearchBizServiceConsumer.searchQuerybyEmptySearch(empty,searchBizBean);
		SearchBizValidation.searchByFreeSearch(response);
	}
	
	@Test(groups = { "firstAndLastNameValidation","Sanity"}, dataProvider = SEARCH)
	public void firstAndLastNameValidation(SpireTestObject testObject, SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifyFirstAndLastNameValidation(searchBizBean);
	}
	
	@Test(groups = { "verifySearchByRequisition","Sanity"}, dataProvider = SEARCH)
	public void verifySearchByRequisition(SpireTestObject testObject,SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySearchByRequisition(testObject,requisitionDataPointsBean);
	}
	
	@Test(groups = { "verifyFacetingCountWhileSearch","Sanity"}, dataProvider = SEARCH)
	public void verifyFacetingCountWhileSearch(SpireTestObject testObject,SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifyFacetCount(testObject,searchBizBean);
	}
	
	@Test(groups = { "verifySaveSearch","Sanity"}, dataProvider = SEARCH)
	public void verifySaveSearch(SpireTestObject testObject,SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifySaveSearch(testObject,searchBizBean);
	}
	@Test(groups = { "verifyRI","Sanity"}, dataProvider = SEARCH)
	public void verifyRI(SpireTestObject testObject,SearchBizBean searchBizBean, RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException , SystemException, IOException{
		SearchBizValidation.verifyRIScore(testObject,searchBizBean);
	}
	
}

package com.spire.crm.biz.search.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import spire.commons.Context;
import spire.commons.search.beans.ResponseBean;
import spire.commons.search.beans.SearchBean;
import spire.commons.utils.ContextUtil;
import spire.crm.profiles.bean.Profile;
import spire.crm.profiles.entity.CRM;
import spire.match.entities.RiExplain;
import spire.match.entities.SearchFacets;
import spire.search.client.entity.CandidateSearchRequest;
import spire.search.client.entity.ClientConfig;
import spire.search.client.service.SearchClient;
import spire.search.client.service.impl.SearchClientImpl;
import spire.search.commons.entities.CandidateExt;
import spire.search.commons.entities.Field;
import spire.search.commons.entities.FieldCount;
import spire.search.commons.entities.Paging;
import spire.search.commons.entities.SearchInput;
import spire.search.commons.entities.SearchResult;
import spire.search.commons.entities.SearchResultOnFields;
import spire.search.commons.exceptions.SystemException;
import spire.search.commons.utils.Utils;
import spire.talent.entity.demandservice.beans.RequisitionBean;
import spire.talent.entity.profileservice.beans.CandidateBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.SoftAssertion_;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;
import com.spire.crm.restful.biz.consumers.CrmSearchBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.ElasticSearchServiceConsumer;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;
import com.spire.crm.restful.biz.consumers.SaveSearchConsumer;
import com.spire.crm.restful.util.ProfileDataPoints;
import com.spire.crm.restful.util.RequisitionDataPointsBean;
import com.spire.crm.restful.util.SearchBizBean;
import com.spire.crm.restful.util.SearchBizBean.EXPECTED_DATAPOINT;

/**
 * 
 * @author Pradeep
 *
 */
public class SearchBizValidation {
	static SearchBizBean search = null;
	public static Gson gson = new Gson();
	public static CrmSearchBizServiceConsumer consumer = new CrmSearchBizServiceConsumer();
	public static SaveSearchConsumer saveSearchConsumer=new SaveSearchConsumer();
	public static ElasticSearchServiceConsumer elasticSearchServiceConsumer = new ElasticSearchServiceConsumer();

	enum SEARCH_KEYS {
		skill, totalExperienceMnth, locationName, Status, degreeName, instituteName, employerName, roleName, stage_name, engagement_score, sourceName, sourceType
	}

	enum CRM_STAGES {
		LEAD, ENGAGED, APPLICANT
	}

	/**
	 * search service execution.
	 * 
	 * @param searchBizBean
	 * @return
	 * @throws SystemException
	 */
	public static SearchResult<CandidateExt> executeSearchQuery(
			SearchBizBean searchBizBean) throws SystemException {

		setContext();
		SearchClient searchClient = new SearchClientImpl(
				new ClientConfig(
						ReadingServiceEndPointsProperties
								.getServiceEndPoint("CRM_SEARCH_BIZ")));
		CandidateSearchRequest searchRequest = new CandidateSearchRequest();
		if(searchBizBean.getClient()!=null)
			searchRequest.setClient(searchBizBean.getClient());
		SearchInput searchInput = new SearchInput();
		searchInput.setSearchQueryString(searchBizBean.getSearchQueryString());
			
		searchInput.setSearchAttributeMap(frameSearchRequest(searchBizBean));
				
		if (searchInput.getSearchAttributeMap().isEmpty())
			searchInput.setFreeSearchQueryString(searchBizBean.getFreeSearchQueryString()); 
		
		searchRequest.setSearchInput(searchInput);
		Paging page = new Paging();
		searchRequest.setPaging(page);
		searchRequest.setIsExpertSkill("true");
		searchRequest.setSkillsAsCSV("true");
		Gson json =new Gson();
		System.out.println(json.toJson(searchRequest));
		Logging.log(json.toJson(searchRequest));
		/*Response response=consumer.searchQuery(searchInput, searchBizBean);
		SearchResult<CandidateExt> searchResult=response
				.readEntity(new GenericType<SearchResult<CandidateExt>>() {
				});	*/
		SearchResult<CandidateExt> searchResult = searchClient.search(searchRequest);
		System.out.println("---------------------");

		if (searchBizBean.getClient() != null) {
			if (searchBizBean.getClient().equals("tv")) {
				Assert.assertNotNull(searchResult.getAggregations(),
						"If client is tv aggregations are getting as null");
			} else if (searchBizBean.getClient().equals("acqura")
					|| searchBizBean.getClient().equals("all")) {
				Assert.assertTrue(searchResult.getAggregations().isEmpty(),
						"If client is acqura or all aggregations are giving values instaed of null");
			}
		}
		List<spire.search.commons.entities.CandidateExt> candidateSummaries = searchResult
				.getEntities();
		if (searchBizBean.getFreeSearchQueryString() == null) {
			if (searchBizBean.getSearchQueryString().contains("skill")) {
				for (spire.search.commons.entities.CandidateExt candidateExt : candidateSummaries) {
					Assert.assertTrue(candidateExt.getRi() >= 0,
							"RI is not showing if skill is involved in search also");
				}
			} else {
				for (spire.search.commons.entities.CandidateExt candidateExt : candidateSummaries) {
					Assert.assertNull(candidateExt.getRi(),
							"RI is showing for non skill based search also");
				}
			}
		}
		Logging.log(" Response JSON >>" + gson.toJson(searchResult));

		return searchResult;

		/*
		 * System.out.println(searchResult.getEntities().get(0).getFirstName());
		 * if (!Utils.isEmpty(searchResult) &&
		 * !Utils.isEmpty(searchResult.getEntities())) { //
		 * System.out.println("firstname ####" //
		 * +searchResult.getEntities().get(0).getFirstName();
		 * Assert.assertTrue(searchResult.getEntities().size() > 0); } else {
		 * throw new RuntimeException("Test Case Failed !!!"); }
		 */
		// return null;
	}

	private static Context setContext() {
		/*
		 * Need to implement the authorization and get userid/tenantid from
		 * authorization service
		 */		
		Utils.setContext(ReadingServiceEndPointsProperties.getServiceEndPoint("tenantId"), 
				ReadingServiceEndPointsProperties.getServiceEndPoint("realmName"),
				ReadingServiceEndPointsProperties.getServiceEndPoint("Authorization"),
				null, null);

		return ContextUtil.getContext();
	}

	/**
	 * Search query request creation.
	 * 
	 * @param searchBizBean
	 * @return
	 */
	public static Map<String, List<String>> frameSearchRequest(
			SearchBizBean searchBizBean) {
		search = searchBizBean;
		List<String> searchSkills = searchBizBean.getSkills();
		List<String> experience = searchBizBean.getExperience();
		List<String> company = searchBizBean.getCompany();
		List<String> locations = searchBizBean.getLocation();
		// List<String> status = searchBizBean.getStatus();
		List<String> qualification = searchBizBean.getQualification();
		List<String> institutes = searchBizBean.getInstitute();
		List<String> stageName = searchBizBean.getStageNames();
		List<String> sourceType = searchBizBean.getSourceType();
		List<String> engagementScore = searchBizBean.getEngagementScore();
		List<String> sourceName = searchBizBean.getSourceName();
		List<String> role = searchBizBean.getRole();
		List<String> preferred = searchBizBean.getPreferred();
		List<String> visaStatus = searchBizBean.getVisaStatus();
		List<String> currentRole = searchBizBean.getCurrentRole();
		List<String> employmentType = searchBizBean.getEmploymentType();

		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		if (searchSkills != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.SKILL
					.getMappedFieldName(), searchSkills);
		}
		if (locations != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.LOCATION
					.getMappedFieldName(), locations);
		}
		if (experience != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.EXPERIENCE
							.getMappedFieldName(), experience);
		}
		if (stageName != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.ENGAGEMENT_STAGE_NAME
							.getMappedFieldName(), stageName);
		}
		if (sourceType != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.SOURCE_TYPE
							.getMappedFieldName(), sourceType);
		}
		if (sourceName != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.SOURCE_NAME
							.getMappedFieldName(), sourceName);
		}
		if (company != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.EMPLOYER_NAME
							.getMappedFieldName(), company);
		}
		if (institutes != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.INSTITUTE_NAME
							.getMappedFieldName(), institutes);
		}
		if (qualification != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.DEGREE_NAME
							.getMappedFieldName(), qualification);
		}
		if (engagementScore != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.ENGAGEMENT_SCORE
							.getMappedFieldName(), engagementScore);
		}
		if (role != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.ROLE
					.getMappedFieldName(), role);
		}
		if (preferred != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.PREFERRED
					.getMappedFieldName(), preferred);
		}
		if (visaStatus != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.VISA_STATUS
					.getMappedFieldName(), visaStatus);
		}
		if (currentRole != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.DESIGNATION
					.getMappedFieldName(), currentRole);
		}
		if (employmentType != null) {
			searchAttributeMap.put(SearchFacets.SupportedAggregations.EMPLOYMENT_TYPE
					.getMappedFieldName(), employmentType);
		}
		return searchAttributeMap;
	}

	static SearchResult<CandidateExt> searchoutVerification = null;

	/**
	 * search Biz web service response validation
	 * 
	 * @param searchOutput
	 * @param profiles
	 * @throws Exception
	 */
	public static void searchResultValidation(
			SearchResult<CandidateExt> searchOutput, Object[] profiles)
			throws Exception {
		Logging.log("Executing searchResultValidation !! ");
		searchoutVerification = searchOutput;
		List<CandidateExt> candidateSummaries = searchOutput.getEntities();
		if (candidateSummaries != null) {
			if (search.getResult() == EXPECTED_DATAPOINT.NULL) {
				Logging.log("SWITCH statement >>"
						+ search.getResult().toString());
				Assert.assertTrue(candidateSummaries.size() == 0,
						"Candidates count should be null only but !!!\t"
								+ candidateSummaries.size());
				Logging.log("No Candidates returned fine!!!");
				return;
			} else if (candidateSummaries.size() > 0) {
				Logging.log("Canidate count is >>>" + candidateSummaries.size());
			} else {
				Logging.log("Canidate count is >>>" + candidateSummaries.size());
				Assert.fail("No candidates are returning !! failed !!!");
			}
			for (int i = 0; i < candidateSummaries.size(); i++) {
				switch (search.getResult()) {
				case ONE:
					Logging.log("SWITCH statement >>"
							+ search.getResult().toString());
					if (candidateSummaries.size() == 1) {
						ProfileDataPoints profileDataPoint = (ProfileDataPoints) profiles[i];
						verifyAllAttributes(candidateSummaries.get(i),
								profileDataPoint);
					} else {
						Assert.fail(" failed ONE!! result of candiates count is wrong");
					}
					break;
				case TWO:
					Logging.log("SWITCH statement >>"
							+ search.getResult().toString());
					if (candidateSummaries.size() == 2) {
						ProfileDataPoints profileDataPoint = (ProfileDataPoints) profiles[i];
						verifyAllAttributes(candidateSummaries.get(i),
								profileDataPoint);
					} else {
						Assert.fail(" failed TWO!!result of candiates count is wrong");
					}
					break;
				case THREE:
					Logging.log("SWITCH statement >>"
							+ search.getResult().toString());
					if (candidateSummaries.size() == 3) {
						ProfileDataPoints profileDataPoint = (ProfileDataPoints) profiles[i];
						verifyAllAttributes(candidateSummaries.get(i),
								profileDataPoint);
					} else {
						Assert.fail(" failed THREE!! result of candiates count is wrong");
					}
					break;
				case MORE:
					Logging.log("SWITCH statement >>"
							+ search.getResult().toString());
					if (candidateSummaries.size() > 0) {
					} else {
						Assert.fail("No candidates returned !!!");
					}

					break;
				default:
					Logging.log("SWITCH statement >>"
							+ search.getResult().toString());
					org.testng.Assert.fail("There are no search results !!!");
					break;
				}

			}
		} else

		{
			org.testng.Assert.fail("Theare are no search results !!!");
		}

		verifyAllAggregations(searchOutput, profiles);
	}

	/**
	 * validating all the attributes
	 * 
	 * @param candidates
	 * @param profileDataPoints
	 * @throws Exception
	 */
	public static void verifyAllAttributes(CandidateExt candidates,
			ProfileDataPoints profileDataPoints) throws Exception {
		Logging.log("------------verifying candidate response---------");

		String firstName = candidates.getFirstName();
		String lastName = candidates.getLastName();
		String sourceName = candidates.getSourceName();
		String sourceType = candidates.getSourceType();
		Short totalExpInMonths = candidates.getTotalExperienceMnth();
		// String primaryEmailId = candidates.getPrimaryEmailId();
		String locationName = candidates.getLocationName();
		Float ri = candidates.getRi();
		CRM crm = candidates.getCrm();
		SearchResultOnFields searchResultOnFields = candidates
				.getSearchResultOnFields();
		Map<String, Collection<Object>> matchedFlds = searchResultOnFields
				.getBaseMatched();
		Assert.assertNotNull(matchedFlds);

		Set<String> baseMatchKey = matchedFlds.keySet();
		for (String key : baseMatchKey) {
			if (key.equalsIgnoreCase("skill")) {
				if (matchedFlds.get("skill").size() > 0)
					Assert.assertTrue(ri > 0,
							"RI value should be morethan 100 !!!");
				break;
			}

		}

		Collection<Object> skills = matchedFlds.get("skill");

		for (Iterator iterator = skills.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();

			int length = object.toString().split("=")[1].length();

			System.out.println(object.toString().split("=")[1].substring(0,
					length - 1));

		}

		Assertion.assertTrue(baseMatchKey.size() > 0,
				"Candidates skill map bean is null !!!!");

		/*
		 * CollectionEntity<CandidateSkillMapBean> candidateSkillsMapBean =
		 * candidates.getCandidateSkillMapBean();
		 */
		/*
		 * Collection<CandidateSkillMapBean> candidateSkillMapBeanCollection =
		 * candidateSkillsMapBean.getItems();
		 */
		/*
		 * if(candidateSkillMapBeanCollection.size() == 0){
		 * SoftAssertion_.assertFail("Candidates skill map bean is null !!!!");
		 * }
		 */

		for (/* String skill : baseMatchKey */Iterator iterator = skills
				.iterator(); iterator.hasNext();) {
			// String skill = candidateSkillMapBean.getSkill();

			Object object = (Object) iterator.next();

			int length = object.toString().split("=")[1].length();

			String skill = object.toString().split("=")[1].substring(0,
					length - 1);

			if (crm.getStageName().equalsIgnoreCase("APPLICANT")) {

				if (skill.equalsIgnoreCase("SpireSkill3")
						|| skill.equalsIgnoreCase("SpireSkill4")) {
				} else {
					SoftAssertion_
							.assertFail("Skills not matched for applicant!!!");
				}
			} else if (crm.getStageName().equalsIgnoreCase("ENGAGED")) {

				if (skill.equalsIgnoreCase("SpireSkill2")
						|| skill.equalsIgnoreCase("SpireSkill3")) {

				} else {
					SoftAssertion_
							.assertFail("Skills not matched for ENGAGED!!!");
				}
			} else if (crm.getStageName().equalsIgnoreCase("LEAD")) {
				if (skill.equalsIgnoreCase("SpireSkill1")
						|| skill.equalsIgnoreCase("SpireSkill2")) {

				} else {
					SoftAssertion_.assertFail("Skills not matched for LEAD!!!");
				}
			}
		}

		// Map<String, Collection<Object>> unMatchedFlds =
		// searchResultOnFields.getUnMatchedFields();
		// Asserting null check
		SoftAssertion_.assertNotNull_(candidates.getId(),
				"candidate id is null !!");
		SoftAssertion_.assertNotNull_(firstName, "candidate firstname is null");
		SoftAssertion_.assertNotNull_(lastName, "candidate lastname is null ");
		SoftAssertion_.assertEquals(locationName, profileDataPoints
				.getCandidateBean().getLocationName());
		SoftAssertion_.assertEquals(sourceName, profileDataPoints
				.getCandidateBean().getSourceName());
		SoftAssertion_.assertEquals(sourceType, profileDataPoints
				.getCandidateBean().getSourceType());
		float actualExperience = totalExpInMonths / 12;
		SoftAssertion_.assertEquals(actualExperience, Float
				.valueOf(profileDataPoints.getCandidateBean()
						.getTotalExperienceMnth() / 12));
		long total = searchoutVerification.getTotal();
		SoftAssertion_.assertNotNull(total);
		// String query = searchoutVerification.getSearchQuery();
		// SoftAssertion_.assertNotNull_(query,"query should be returned");
		// SoftAssertion_.assertEquals(query, search.getSearchQueryString());

		SoftAssertion_.assertAll();
	}

	public static void verifyAllAggregations(
			SearchResult<CandidateExt> searchOutput, Object[] profiles)
			throws Exception {
		List<Field> candidateAggregations = searchOutput.getAggregations();
		for (int i = 0; i < candidateAggregations.size(); i++) {
			Assert.assertTrue(
					candidateAggregations.get(0).getName().equals("skill"),
					"skill attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(1).getName()
							.equals("locationName"),
					"locationName attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(2).getName()
							.equals("totalExperienceMnth"),
					"totalExperienceMnth attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(3).getName()
							.equals("employerName"),
					"employerName attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(4).getName()
							.equals("instituteName"),
					"instituteName attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(5).getName()
							.equals("engagement_score"),
					"engagement_score attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(6).getName().equals("stage_name"),
					"stage_name attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(7).getName()
							.equals("feedback_status"),
					"feedback_status attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(8).getName()
							.equals("feedback_status_modified_at"),
					"feedback_status_modified_at attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(9).getName()
							.equals("stage_change_reason"),
					"stage_change_reason attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(10).getName()
							.equals("sourceName"),
					"sourceName attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(11).getName()
							.equals("sourceType"),
					"sourceType attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(12).getName()
							.equals("degreeName"),
					"degreeName attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(13).getName().equals("status"),
					"status attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(14).getName()
							.equals("clientUpdatedOn"),
					"clientUpdatedOn attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(15).getName().equals("roleName"),
					"roleName attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(16).getName()
							.equals("visaStatus"),
					"visaStatus attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(17).getName()
							.equals("employmentType"),
					"employmentType attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(18).getName().equals("preferred"),
					"preferred attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(19).getName()
							.equals("currentRole"),
					"currentRole attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(20).getName()
							.equals("currentEmployer"),
					"currentEmployer attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(21).getName()
							.equals("contactInformationAvailable"),
					"contactInformationAvailable attribute is not displaying in aggregations");
			Assert.assertTrue(
					candidateAggregations.get(22).getName().equals("Contact"),
					"contact attribute is not displaying in aggregations");
			Assert.assertNotNull(candidateAggregations.get(0).getValues()
					.get(0).getKey(),
					"values are not displaying for Skill attribute");
			Assert.assertNotNull(candidateAggregations.get(1).getValues()
					.get(0).getKey(),
					"values are not displaying for locationName attribute");
			Assert.assertNotNull(candidateAggregations.get(2).getValues()
					.get(0).getKey(),
					"values are not displaying for totalExperienceMnth attribute");
			Assert.assertNotNull(candidateAggregations.get(3).getValues()
					.get(0).getKey(),
					"values are not displaying for employerName attribute");
			Assert.assertNotNull(candidateAggregations.get(4).getValues()
					.get(0).getKey(),
					"values are not displaying for instituteName attribute");
			Assert.assertNotNull(candidateAggregations.get(5).getValues()
					.get(0).getKey(),
					"values are not displaying for engagement_score attribute");
			Assert.assertNotNull(candidateAggregations.get(6).getValues()
					.get(0).getKey(),
					"values are not displaying for stage_name attribute");
			Assert.assertNotNull(
					candidateAggregations.get(10).getValues().get(0).getKey(),
					"values are not displaying for sourceName attribute");
			Assert.assertNotNull(
					candidateAggregations.get(11).getValues().get(0).getKey(),
					"values are not displaying for sourceType attribute");
			Assert.assertNotNull(
					candidateAggregations.get(12).getValues().get(0).getKey(),
					"values are not displaying for degreeName attribute");
			Assert.assertNotNull(
					candidateAggregations.get(13).getValues().get(0).getKey(),
					"values are not displaying for status attribute");
			Assert.assertNotNull(
					candidateAggregations.get(15).getValues().get(0).getKey(),
					"values are not displaying for roleName attribute");
			Assert.assertNotNull(
					candidateAggregations.get(16).getValues().get(0).getKey(),
					"values are not displaying for visaStatus attribute");
			Assert.assertNotNull(
					candidateAggregations.get(17).getValues().get(0).getKey(),
					"values are not displaying for employmentType attribute");
			Assert.assertNotNull(
					candidateAggregations.get(18).getValues().get(0).getKey(),
					"values are not displaying for preferred attribute");
			Assert.assertNotNull(
					candidateAggregations.get(22).getValues().get(0).getKey(),
					"values are not displaying for contact attribute");

			if (candidateAggregations != null) {
				long total = searchOutput.getTotal();
				validateCandidateCount(candidateAggregations, total);
			}
		}

	}


	public static void validateCandidateCount(
			List<Field> candidateAggregations, long total) {
		for (Field field : candidateAggregations) {
			if (total <= 3) {
				if (field.getName().equals("skill")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spireskill1")
									|| field.getValues().get(0).getKey()
											.equals("spireskill2")
									|| field.getValues().get(0).getKey()
											.equals("spireskill3")
									|| field.getValues().get(0).getKey()
											.equals("spireskill4"),
							"required skills are not displaying in the aggregations");
				} else if (field.getName().equals("locationName")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spirecity"),
							"required locationName is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("totalExperienceMnth")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey().equals("61.0-*"),
							"required totalExperienceMnth is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("employerName")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spireemployer"),
							"required employerName is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("instituteName")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spireinstitute"),
							"required instituteName is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("engagement_score")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("0.0-20.0"),
							"required engagement_score is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("stage_name")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey().equals("lead")
									|| field.getValues().get(0).getKey()
											.equals("engaged")
									|| field.getValues().get(0).getKey()
											.equals("applicant"),
							"required stage_name is not displaying in the aggregations");
				} else if (field.getName().equals("sourceName")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spiresourcename"),
							"required sourceName is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("sourceType")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spiresourcetype"),
							"required sourceType is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("degreeName")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spiredegree"),
							"required degreeName is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("status")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey().equals("applied"),
							"required status is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("roleName")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spirerole"),
							"required roleName is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("visaStatus")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spirevisastatus"),
							"required visaStatus is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("employmentType")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spireemploymenttype"),
							"required employmentType is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("preferred")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("spirepreferred"),
							"required preferred is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				} else if (field.getName().equals("Contact")) {
					Assert.assertTrue(
							field.getValues().get(0).getKey()
									.equals("PRIMARY_EMAIL_ID"),
							"required Contact is not displaying in the aggregations");
					Assert.assertEquals(total, field.getValues().get(0)
							.getCount());
				}
			}
		}
	}

	public static String createProfile() {
		ProfileBizServiceConsumer profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Profile candProfile = new Profile();
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setFirstName("vishal");
		candidateBean.setLocationName("UPTU");
		candidateBean.setTotalExperienceMnth((short) 28);
		candidateBean.setCandidateSkillMapBean(ProfileHelper
				.addSkill("java 9.0"));
		candidateBean.setCandidateEducationMapBean(ProfileHelper.addEducation(
				"BE", "Bangalore Institute Of Technology"));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer(
				"Test Automation Engineer", "Intuit"));
		candProfile.setCandidate(candidateBean);
		String profileId = profileBizServiceConsumer.createProfile(candProfile);
		return profileId;
	}

	/**
	 * Read the file and convert to json
	 * 
	 * @param fileName
	 * @return rerequisitionJson
	 */
	public static RequisitionBean createRequsition() {
		createProfile();
		RequisitionBean requisitionJson = null;
		String filePath = "./src/main/resources/requisition.json";
		File requisitionsJson = new File(filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			requisitionJson = objectMapper.readValue(requisitionsJson,
					new TypeReference<RequisitionBean>() {
					});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		requisitionJson.setDisplayId("REQ-" + System.currentTimeMillis());
		requisitionJson.setJobTitle("Developer-" + System.currentTimeMillis());
		return requisitionJson;
	}

	public static void verifySearchByES(SearchResult<CandidateExt> searchOutput)
			throws JsonParseException, JsonMappingException, IOException {
		String filePath = "./src/main/resources/ElasticSearchRequest.json";
		File EsJson = new File(filePath);
		String searchRequest = FileUtils.readFileToString(EsJson);
		System.out.println("ESRequest>>>>>>>>>>>>:" + searchRequest);
		Gson gson = new Gson();
		Logging.log("ESRequest>>>>>>>>>>>>" + gson.toJson(searchRequest));
		ElasticSearchServiceConsumer elasticSearchServiceConsumer = new ElasticSearchServiceConsumer();
		Response response = elasticSearchServiceConsumer
				.getCandidatesBySearch(searchRequest);
		String strResponse = response.readEntity(String.class);
		Logging.log("ESRespone>>>>>>>>>>>>>:" + strResponse);
		List<String> candidateIdsFromES = new ArrayList<String>();
		String[] esResponse = strResponse.split("_id");
		for (int i = 0; i < esResponse.length - 1; i++) {
			String candidateIdsInEs = esResponse[i + 1].substring(3, 45);
			candidateIdsFromES.add(candidateIdsInEs);
		}
		List<CandidateExt> listOfsearch = searchOutput.getEntities();
		List<String> candidateIdsFromSearch = new ArrayList<String>();
		for (CandidateExt candidateExt : listOfsearch) {
			candidateIdsFromSearch.add(candidateExt.getId());
		}
		Assert.assertEquals(candidateIdsFromES.size(),
				candidateIdsFromSearch.size(),
				"number of records are not matched from search service and Elasic search");
		for (String id : candidateIdsFromES) {
			Assert.assertTrue(candidateIdsFromSearch.contains(id),
					"showing wrong candidate details");
		}
		String[] totalInES = strResponse.split("total");
		String totalInEs = totalInES[2].substring(2, 3);
		Assert.assertEquals(Long.parseLong(totalInEs), searchOutput.getTotal(),
				"total is different in Search service and ES");
	}

	public static void verifySearchByESForMatchFlow()
			throws JsonParseException, JsonMappingException, IOException,
			InterruptedException {
		String filePath = "./src/main/resources/MatchFlowByIdInES.json";
		File EsJson = new File(filePath);
		String searchRequest = FileUtils.readFileToString(EsJson);
		String profileId = ProfileHelper.createProfile();
		Thread.sleep(30000);
		String searchReq[] = searchRequest.split("\"query\"");
		String request = searchReq[0] + "\"" + "query" + "\"" + searchReq[1]
				+ "\"" + "query" + "\"" + ": " + "\"" + "\\" + "\"" + profileId
				+ searchReq[2];
		System.out.println("ESRequest>>>>>>>>>>>>: " + request);
		Logging.log("ESRequest>>>>>>>>>>>>: " + request);
		ElasticSearchServiceConsumer elasticSearchServiceConsumer = new ElasticSearchServiceConsumer();
		Response response = elasticSearchServiceConsumer
				.getCandidatesBySearch(request);
		String strResponse = response.readEntity(String.class);
		Logging.log("ESRespone>>>>>>>>>>>>>:" + strResponse);
		String[] esResponseID = strResponse.split("_id");
		String candidateIdFromES = esResponseID[1].substring(3, 45);
		Assert.assertEquals(candidateIdFromES, profileId,
				"Not giving proper Candidate record from elastic search");
		String[] totalInES = strResponse.split("total");
		String totalInEs = totalInES[2].substring(2, 3);
		Assert.assertEquals(
				Integer.parseInt(totalInEs),
				1,
				"Not giving count as 1 if we serach with match flow by Id through elastic serach");
	}

	public static List<String> createProfiles(SearchBizBean searchBizBean) throws SystemException, IOException{
		SearchResult<CandidateExt> searchResult = executeSearchQuery(searchBizBean);
		List<CandidateExt> entities = searchResult.getEntities();
		for (CandidateExt candidateExt : entities) {
			elasticSearchServiceConsumer.deleteCandidate(candidateExt.getId());
		}
		List<String> skills = new ArrayList<String>();
		if (searchBizBean.getSkills().contains("click")
				|| searchBizBean.getSkills().contains("clicks")
				|| searchBizBean.getSkills().contains("clicked")
				|| searchBizBean.getSkills().contains("clicking")) {
			skills.add("click");
			skills.add("clicks");
			skills.add("clicked");
			skills.add("clicking");
		} else if (searchBizBean.getSkills().contains("send")
				|| searchBizBean.getSkills().contains("SEND")) {
			skills.add("send");
			skills.add("SEND");
		} else if (searchBizBean.getSkills().contains("instant$$$$$string")
				|| searchBizBean.getSkills().contains("instantstring")) {
			skills.add("instant$$$$$string");
			skills.add("instantstring");
		} else if (searchBizBean.getSkills().contains("j-mat")
				|| searchBizBean.getSkills().contains("jmat")) {
			skills.add("j-mat");
			skills.add("jmat");
		} else if (searchBizBean.getSkills().contains("inventroute")
				|| searchBizBean.getSkills().contains("invented route")) {
			skills.add("inventroute");
			skills.add("invented route");
		}
		for (int i = 0; i < skills.size(); i++) {
			ProfileBizServiceConsumer profileBizServiceConsumer = new ProfileBizServiceConsumer();
			Profile candProfile = new Profile();
			CandidateBean candidateBean = new CandidateBean();
			candidateBean.setTotalExperienceMnth((short) 26);
			candidateBean.setFirstName("robert");
			candidateBean.setLocationName("Bangalore");
			candidateBean.setSourceName("openweb");
			candidateBean.setSourceType("openweb");
			candidateBean.setStatus("applied");
			candidateBean.setCandidateSkillMapBean(ProfileHelper
					.addSkill(skills.get(i)));
			candidateBean.setCandidateEducationMapBean(ProfileHelper
					.addEducation("BE", "Bangalore Institute Of Technology"));
			candProfile.setCandidate(candidateBean);
			candProfile.setCrm(ProfileHelper.setCRM());
			String candidateID = profileBizServiceConsumer
					.createProfile(candProfile);
			addSkillExpertiseInES(candidateID,skills);
			List<String> candidateIds = new ArrayList<String>();
			candidateIds.add(candidateID);
		}
		return skills;
	}
	
	public static void verifySearchByStemmingWords(SearchBizBean searchBizBean)
			throws InterruptedException ,SystemException, IOException {
		List<String> skills = createProfiles(searchBizBean);
		Thread.sleep(40000);
		SearchResult<CandidateExt> searchResult = executeSearchQuery(searchBizBean);
		List<CandidateExt> entities = searchResult.getEntities();
		if (searchBizBean.getSkills().contains("click")
				|| searchBizBean.getSkills().contains("clicks")
				|| searchBizBean.getSkills().contains("clicked")
				|| searchBizBean.getSkills().contains("clicking")) {
			Assert.assertEquals(searchResult.getEntities().size(), 4,
					"wrong candidates are displaying than expected");
		} else if (searchBizBean.getSkills().contains("SEND")
				|| searchBizBean.getSkills().contains("send")) {
			Assert.assertEquals(searchResult.getEntities().size(), 2,
					"wrong candidates are displaying than expected");
		} else if (searchBizBean.getSkills().contains("instant$$$$$string")
				|| searchBizBean.getSkills().contains("instantstring")) {
			Assert.assertEquals(searchResult.getEntities().size(), 2,
					"wrong candidates are displaying than expected");
		} else if (searchBizBean.getSkills().contains("jmat")
				|| searchBizBean.getSkills().contains("j-mat")) {
			Assert.assertEquals(searchResult.getEntities().size(), 2,
					"More candidates are displaying than expected");
		} else if (searchBizBean.getSkills().contains("inventroute")
				|| searchBizBean.getSkills().contains("invented route")) {
			Assert.assertEquals(searchResult.getEntities().size(), 2,
					"wrong candidates are displaying than expected");
		}
		for (CandidateExt candidateExt : entities) {
			ArrayList<Object> baseMatchedRes = (ArrayList<Object>) candidateExt.getSearchResultOnFields()
					.getBaseMatched().get("skillsCSV");
			Assert.assertTrue(skills.contains(baseMatchedRes.get(0).toString()),
					"Invalid candidate records");
			int RI = candidateExt.getRi().intValue();
			Assert.assertEquals(RI, 100,
					"RI is showing different for stemming words");
			elasticSearchServiceConsumer.deleteCandidate(candidateExt.getId());
		}
	}

	public static void verifyRIByExpalin(SearchBizBean searchBizBean) throws SystemException{
		SearchInput searchRequest = new SearchInput();
		searchRequest
				.setSearchQueryString(searchBizBean.getSearchQueryString());
		searchRequest.setSearchAttributeMap(frameSearchRequest(searchBizBean));
		CrmSearchBizServiceConsumer crmSearchBizServiceConsumer = new CrmSearchBizServiceConsumer();
		Response response = crmSearchBizServiceConsumer.searchByRIExpalin(
				searchRequest, searchBizBean);
		Collection<ArrayList<RiExplain>> searchOutput = response
				.readEntity(new GenericType<Collection<ArrayList<RiExplain>>>() {
				});
		Logging.log("searchOutput >>>>" + searchOutput);
		if (searchOutput == null) {
			Assert.assertTrue(searchOutput == null,
					"No candidates are there for the searched query");
		} else {
			for (ArrayList<RiExplain> explainArrayList : searchOutput) {
				for (RiExplain riExplain : explainArrayList) {
					if (riExplain.getAttributeValue().equalsIgnoreCase(
							searchBizBean.getSkills().get(0))) {
						Assert.assertTrue(riExplain.getReqWeight() >= 49
								&& riExplain.getReqWeight() <= 51);
					} else if (riExplain.getAttributeValue().equalsIgnoreCase(
							searchBizBean.getSkills().get(1))) {
						Assert.assertTrue(riExplain.getReqWeight() >= 33
								&& riExplain.getReqWeight() <= 34);
					} else if (riExplain.getAttributeValue().equalsIgnoreCase(
							searchBizBean.getSkills().get(2))) {
						Assert.assertTrue(riExplain.getReqWeight() >= 16
								&& riExplain.getReqWeight() <= 17);
					}
				}
			}
		}
	}

	public static void searchBySpecialCharacterSkills(
			SearchBizBean searchBizBean) throws SystemException{
		SearchResult<CandidateExt> searchResult = executeSearchQuery(searchBizBean);
		Assert.assertTrue(searchResult.getTotal() >= 0,
				"Not getting the serach results");
		if (searchResult.getTotal() > 0)
			Assert.assertTrue(!(searchResult.getEntities().isEmpty()),
					"Not getting the candidates for searched query");
		Assert.assertTrue(
				searchResult.getSearchQuery().contains(
						searchBizBean.getSearchQueryString()),
				"search wrong query");
	}
	
	public static void searchByFreeSearch(Response response){
		SearchResult<CandidateExt> searchResult=response
				.readEntity(new GenericType<SearchResult<CandidateExt>>() {
				});	
		Gson json=new Gson();
		Logging.log("searchResult>>>>"+json.toJson(searchResult));
		Assert.assertNotNull(searchResult.getEntities(), "Not giving candidates while searching with Empty request search");
		Assert.assertNotNull(searchResult.getAggregations(), "Not giving aggregations while searching with Empty request for tv client");
		Assert.assertTrue(searchResult.getTotal()>0, "Not giving candidates while searching with Empty request search");
		
	}
	
	public static void verifyFirstAndLastNameValidation(SearchBizBean searchBizBean) throws SystemException, IOException{
		ProfileBizServiceConsumer profileBizServiceConsumer = new ProfileBizServiceConsumer();
		Profile candProfile = new Profile();
		CandidateBean candidateBean = new CandidateBean();
		candidateBean.setTotalExperienceMnth((short) 26);
		candidateBean.setLocationName("mumbai");
		candidateBean.setSourceName("openweb");
		candidateBean.setSourceType("openweb");
		candidateBean.setStatus("applied");
		candidateBean.setCandidateSkillMapBean(ProfileHelper
				.addSkill(searchBizBean.getSkills().get(0)));
		candidateBean.setCandidateEmployerMapBean(ProfileHelper.addEmployer(
				"Automation Engineer", "Intuit"));
		candProfile.setCandidate(candidateBean);
		candProfile.setCrm(ProfileHelper.setCRM());
		String candidateId = profileBizServiceConsumer
				.createProfile(candProfile);
		List<String> skill=new ArrayList<String>();
		skill.add(searchBizBean.getSkills().get(0));
		addSkillExpertiseInES(candidateId, skill);
		SearchResult<CandidateExt> searchResult = executeSearchQuery(searchBizBean);
		Assert.assertFalse(searchResult.getEntities().isEmpty(), "Not getting search results if created the profile without first or lastname");
		Assert.assertTrue(searchResult.getTotal()>0, "Not getting search results if created the profile without first or lastname");
	}

	public static void verifySearchByRequisition(SpireTestObject testObject,
			RequisitionDataPointsBean requisitionDataPointsBean) throws InterruptedException {
		CrmSearchBizServiceConsumer crmSearchBizServiceConsumer = new CrmSearchBizServiceConsumer();
		RequisitionBean requisitionBean;
		requisitionBean = requisitionDataPointsBean.requisitionCreateData();
		requisitionBean.setDisplayId("REQ-" + System.currentTimeMillis());
		requisitionBean.setJobTitle("DEV-" + System.currentTimeMillis());
		System.out.println(requisitionBean.getJobTitle());
		Logging.log("********" + testObject.getTestData() + "*********");
		Response response = crmSearchBizServiceConsumer
				.createRequsition(requisitionBean);
		Assert.assertEquals(response.getStatus(), 201, "Throwing wrong status:"
				+ response.getStatus());
		String requisition = response.readEntity(String.class);
		String requisitionID[] = requisition.split("id");
		String reqID = requisitionID[1].substring(3, 45);
		//Thread.sleep(10000);
		Response response1 = consumer.searchByRequisition(reqID);
		Assert.assertEquals(response1.getStatus(), 200,
				"Failed to search by requisition");
		SearchResult<CandidateExt> searchResult = response1
				.readEntity(new GenericType<SearchResult<CandidateExt>>() {
				});
		List<CandidateExt> entities = searchResult.getEntities();
		Logging.log(" Response JSON >>" + gson.toJson(searchResult));
		if (testObject.getDescription().equals("createRequisitionWithLocation")
				|| testObject.getDescription().equals(
						"createRequisitionWithLocAndExp")) {
			Assert.assertEquals(searchResult.getTotal(), 3,
					"showing wrong Number of records");
			for (int i = 0; i < entities.size(); i++) {
				Assert.assertNull(entities.get(i).getRi(),
						"showing RI for the non skill bassed search:"
								+ testObject.getDescription());
			}
		} else if (testObject.getDescription().equals(
				"createRequisitionWithExp")) {
			Assert.assertTrue(searchResult.getTotal() > 1,
					"showing wrong Number of records");
			for (CandidateExt candidateExt : entities) {
				Assert.assertNull(candidateExt.getRi(),
						"showing RI for the non skill bassed search:"
								+ testObject.getDescription());
			}
		} else if (testObject.getDescription().equals(
				"createRequisitionWithSkill10DesiredAnd10EssentialSkills")) {
			Assert.assertTrue(searchResult.getTotal() == 0,
					"showing wrong Number of records");
		} else if (testObject.getDescription().equals(
				"hitting same requisition by search service")) {
			long total = searchResult.getTotal();
			for (int i = 0; i < 5; i++) {
				Response response2 = consumer.searchByRequisition(reqID);
				SearchResult<CandidateExt> searchResult1 = response2
						.readEntity(new GenericType<SearchResult<CandidateExt>>() {
						});
				Assert.assertTrue(total >= searchResult1.getTotal()-5,
						"showing different count for every hit of a particular requisition");
			}
		} else {
			Assert.assertEquals(searchResult.getTotal(), 1,
					"showing wrong Number of records");
			ArrayList<Object> baseMatchedRes = (ArrayList<Object>) entities
					.get(0).getSearchResultOnFields().getBaseMatched()
					.get("skillsCSV");
			Assert.assertTrue(
					requisitionDataPointsBean.getReqSkillMapBean().get(0)
							.getSkill()
							.contains(baseMatchedRes.get(0).toString()),
					"Invalid candidate records");
			if (requisitionDataPointsBean.getReqLocationMapBean() != null)
				Assert.assertTrue(entities.get(0).getRi() > 0,
						"Not displaying RI if skill is involved also for the testcase:"
								+ testObject.getDescription());
		}
	}
	
	public  static void verifyFacetCount(SpireTestObject testObject,SearchBizBean searchBizBean){
		SearchInput searchInput = new SearchInput();
		searchInput.setSearchQueryString(searchBizBean.getSearchQueryString());
		searchInput.setSearchAttributeMap(frameSearchRequest(searchBizBean));
		Response response=consumer.searchQuery(searchInput, searchBizBean);
		SearchResult<CandidateExt> searchResult=response
				.readEntity(new GenericType<SearchResult<CandidateExt>>() {
				});	
		long count=0;
		count=addingFieldCount(count,testObject,searchResult);
		String search=gson.toJson(searchInput);
		String searchRequest[]=search.split("html");
		String request=searchRequest[0]+"html) AND (_missing_:"+testObject.getDescription()+searchRequest[1]+"html\"]},"+"\"forcedFacets"+"\":{"+"\""+testObject.getDescription()+"\":[null]}}";
		Logging.log("searchRequest>>>>>>>>>>>"+request);
		Response response1=consumer.searchQuerybyEmptySearch(request, searchBizBean);
		SearchResult<CandidateExt> searchResult1=response1
				.readEntity(new GenericType<SearchResult<CandidateExt>>() {
				});	
		long count1=addingFieldCount(count,testObject,searchResult1);
		Assert.assertEquals(count1, searchResult.getTotal(),"sum of all the values in aggregation is not equlas to total result");

	}
	
	public static void verifySaveSearch(SpireTestObject testObject,SearchBizBean searchBizBean) throws SystemException{
		SearchBean searchBean=new SearchBean();
		long id=System.currentTimeMillis();
		int ID = (int) id;
		searchBean.setId(ID);
		searchBean.setName("saveSearchAuto-" + System.currentTimeMillis());
		searchBean.setSearchQuery(searchBizBean.getSkills().get(0));
		crm.search.service.beans.SearchInput searchInput=new crm.search.service.beans.SearchInput();
		searchInput.setSearchQueryString(searchBizBean.getSearchQueryString());
		searchInput.setSearchAttributeMap(frameSearchRequest(searchBizBean));
		String search=gson.toJson(searchInput);
		//search=search.replace("\"","\\\"");
		searchBean.setSearchFilter(search);
		System.out.println(gson.toJson(searchBean));
		ResponseBean responseBean=saveSearchConsumer.saveSearch(searchBean);
		Assert.assertEquals(responseBean.getCode(),"OK","Not able to save the search result");
		Assert.assertTrue(responseBean.getMessage().contains("saveSearch: "+searchBean.getName()+" inserted successfully"));
		SearchResult<CandidateExt> searchResult=executeSearchQuery(searchBizBean);
		Assert.assertFalse(searchResult.getEntities().isEmpty(), "Not giving candidates while searching with save search");
		Assert.assertTrue(searchResult.getTotal()>0, "Not giving candidates while searching with save search");
	}
	
	public static void verifyRIScore(SpireTestObject testObject,
			SearchBizBean searchBizBean) throws SystemException {
		SearchResult<CandidateExt> searchResult = executeSearchQuery(searchBizBean);
		if (searchResult.getTotal() > 0) {
			List<CandidateExt> entities = searchResult.getEntities();
			for (CandidateExt candidateExt : entities) {
				Assert.assertTrue(
						candidateExt.getRi() <= 100 && candidateExt.getRi() > 0,
						"RI should not be morethan 100");
			}
		}
	}
	
	public static long addingFieldCount(long count,SpireTestObject testObject,SearchResult<CandidateExt> searchResult){
		List<Field> aggregations=searchResult.getAggregations();
		for (Field field : aggregations) {
			if(field.getName().equals(testObject.getDescription())){
				List<FieldCount> values=field.getValues();
				for (FieldCount fieldCount : values) {
					count=count+fieldCount.getCount();
				}
			}
		}
		return count;
	}
	
	/**
	 * Search query request creation.
	 * 
	 * @param searchBizBean
	 * @return
	 */
	public static Map<String, List<String>> frameSearchRequestForForcedFacets(
			SearchBizBean searchBizBean) {
				
		search = searchBizBean;
		List<String> experience = searchBizBean.getForcedExperience();
		List<String> engagementScore = searchBizBean.getForcedEngagementScore();
				

		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		if (experience != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.EXPERIENCE
							.getMappedFieldName(), experience);
		}if (engagementScore != null) {
			searchAttributeMap.put(
					SearchFacets.SupportedAggregations.ENGAGEMENT_SCORE
							.getMappedFieldName(), engagementScore);
		}
		return searchAttributeMap;
	}
	public static Response addSkillExpertiseInES(String candidateId,
			List<String> skills) throws IOException {
		Response response = elasticSearchServiceConsumer
				.getCandidate(candidateId);
		if (response.getStatus() == 200) {
			String profile = response.readEntity(String.class);
			String profiles[] = profile.split("_source");
			String ESProfile = profiles[1];
			ESProfile = ESProfile.substring(2, ESProfile.length() - 1);
			String filePath = "./src/main/resources/skillExpertise.json";
			File EsJson = new File(filePath);
			String expertiseSkill = FileUtils.readFileToString(EsJson);
			String expertiseSkills[] = expertiseSkill.split("\"skill\"");
			expertiseSkill = expertiseSkills[0];
			for (int i = 0; i < skills.size(); i++) {
				expertiseSkill = expertiseSkill + "{" + "\"skill\":" + "\""
						+ skills.get(i) + "\"}";
				if (i + 1 < skills.size())
					expertiseSkill = expertiseSkill + ",";
			}
			expertiseSkill = expertiseSkill + expertiseSkills[1];
			String profileWithExpertiseSkill[] = ESProfile.split("\"applied\"");
			ESProfile = profileWithExpertiseSkill[0] + "\"applied\""
					+ expertiseSkill + profileWithExpertiseSkill[1];
			System.out.println("Profile>>>>>>>>" + ESProfile);
			response = elasticSearchServiceConsumer.updateCandidate(
					candidateId, ESProfile);
		}
		return response;
	}
	public static void main(String[] args) throws Exception{
		
		//verifySerachByRequisition();
		
		SearchBizBean bean = new SearchBizBean();
		bean.setSearchQueryString("skill:javascript OR skil:html OR skill:investment banking");
		bean.setClient("tv");
		ArrayList<String> skills = new ArrayList<String>();
		skills.add("javascript");
		skills.add("html");
		skills.add("investment banking");
		bean.setSkills(skills);
		verifyRIByExpalin(bean);
		
		System.out.println("completed");
		
		/*setContext();
		SearchClient searchClient = new SearchClientImpl(
				new ClientConfig(
						ReadingServiceEndPointsProperties
								.getServiceEndPoint("CRM_SEARCH_BIZ")));
		CandidateSearchRequest searchRequest = new CandidateSearchRequest();
		searchRequest.setClient("tv");
		SearchInput searchInput = new SearchInput();
		searchInput.setSearchQueryString("skill:java");
		
		Map<String, List<String>> searchAttributeMap = new HashMap<String, List<String>>();
		searchAttributeMap.put("skill", Arrays.asList("java"));
		searchInput.setSearchAttributeMap(searchAttributeMap);
		
		//searchInput.setSearchAttributeMap(frameSearchRequest(searchBizBean));
		
		searchRequest.setSearchInput(searchInput);
		Paging page = new Paging();
		searchRequest.setPaging(page);
		SearchResult<CandidateExt> searchResult = searchClient.search(searchRequest);
		verifyAllAggregations(searchResult, null);*/
		
}
	
	
}

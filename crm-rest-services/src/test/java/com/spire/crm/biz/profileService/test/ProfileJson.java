package com.spire.crm.biz.profileService.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.testng.Assert;

import spire.crm.profiles.bean.Profile;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spire.base.controller.Logging;
import com.spire.crm.restful.biz.consumers.ProfileBizServiceConsumer;

import crm.pipeline.beans.CRMFilter;
import crm.pipeline.entities.CollectionEntity;

public class ProfileJson {

	static ProfileBizServiceConsumer profileBizServiceConsumer = new ProfileBizServiceConsumer();

	/**
	 * Read the file and convert to json
	 * 
	 * @param fileName
	 * @return profileJson
	 */
	public static Profile getProfileJson(String fileName) {

		Profile profileJson = null;
		String filePath = "./src/main/resources/" + fileName;
		File profilesJson = new File(filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			profileJson = objectMapper.readValue(profilesJson, new TypeReference<Profile>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		CandidateBean candidate = new CandidateBean();
		DataFactory dataFactory = new DataFactory();
		candidate.setFirstName(dataFactory.getFirstName());
		candidate.setLastName(dataFactory.getLastName());
		profileJson.setCandidate(candidate);
		return profileJson;
	}

	/**
	 * Read the bulk file and convert to json
	 * 
	 * @param fileName
	 * @return profileJson
	 */
	public static List<Profile> getBulkProfileJson(String fileName) {
		List<Profile> profileJson = null;
		String filePath = "./src/main/resources/" + fileName;
		File profilesJson = new File(filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			profileJson = objectMapper.readValue(profilesJson, new TypeReference<List<Profile>>() {
			});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return profileJson;
	}

	/**
	 * 1. Create a profile using json file. 2. get by candidateId. 3. validate
	 * the response
	 */
	public static void createProfile_Json(String fileName) {

		Profile profileRequestJson = ProfileJson.getProfileJson(fileName);

		String candidateId = profileBizServiceConsumer.createProfile(profileRequestJson);
		Logging.log("candidateId: " + candidateId);

		Profile getByCandidateIdResponse = profileBizServiceConsumer.getProfile(candidateId, "full");
		ProfileJson.profileValidator(profileRequestJson, getByCandidateIdResponse);

	}

	/**
	 * 1. Create a bulk profile using json file. 2. get by candidateId. 3.
	 * validate the response
	 * 
	 * @throws Exception
	 */
	public static void createBulkProfile_Json(String fileName) throws Exception {

		List<Profile> profileRequestJson = ProfileJson.getBulkProfileJson(fileName);

		CollectionEntity<String> candidateId = profileBizServiceConsumer.createBulkProfiles(profileRequestJson);
		Collection<String> cids = candidateId.getItems();
		for (String ids : cids) {
			Logging.log("ids: " + ids);
		}
		Logging.log("candidateId: " + candidateId);

		CollectionEntity<Profile> getByCandidateIdResponse = profileBizServiceConsumer.getBulkProfiles(candidateId,
				"full");

		ProfileJson.profileValidator(profileRequestJson, getByCandidateIdResponse);

	}

	/**
	 * 1. Create a profile using json file. 2. get by candidateId. 3. validate
	 * the response
	 * 
	 * @throws Exception
	 */
	public static void getBulkProfile_Json(String fileName) throws Exception {
		List<Profile> profileRequestJson = ProfileJson.getBulkProfileJson(fileName);

		CollectionEntity<String> candidateId = profileBizServiceConsumer.createBulkProfiles(profileRequestJson);
		Collection<String> cids = candidateId.getItems();
		for (String ids : cids) {
			Logging.log("ids: " + ids);
		}
		Logging.log("candidateId: " + candidateId);

		CollectionEntity<Profile> getByCandidateIdResponse = profileBizServiceConsumer.getBulkProfiles(candidateId,
				"full");

		ProfileJson.profileValidator(profileRequestJson, getByCandidateIdResponse);

	}

	/**
	 * call getBulkProfile by giving invalid profileIds the response
	 * 
	 * @throws Exception
	 */
	public static void getBulkProfileInvalid_Json(String fileName) throws Exception {

		CollectionEntity<String> candidateId = new CollectionEntity<String>();
		candidateId.addItem("6002:6005:fffb805e4f01a29779f5e72e85d7");
		candidateId.addItem("6002:6005:fffb805e4f25a29779f5e72e85d7");
		candidateId.addItem("6002:6005:fffb805e42896a29779f5e72e85d7");
		candidateId.addItem("6002:6005:fffb805e4781a29779f57e72e85d7");
		candidateId.addItem("6002:6005:fffb8053698c797793f5e72e85d7");
		candidateId.addItem("6002:6005:fffb80258961a297c79f5e72e85d7");
		candidateId.addItem("6002:6005:fffb802358566a29779f5e72e85d7");
		candidateId.addItem("candidateId: " + candidateId);

		CollectionEntity<Profile> getByCandidateIdResponse = profileBizServiceConsumer.getBulkProfiles(candidateId,
				"full");
		Assert.assertEquals(getByCandidateIdResponse, null, "valid profiles");

	}

	/**
	 * get profiles by giving crm stages
	 * 
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void getProfilesByCrmFilter(String fileName) throws Exception {

		List<Profile> profileRequestJson = ProfileJson.getBulkProfileJson(fileName);
		CollectionEntity<String> candidateId = profileBizServiceConsumer.createBulkProfiles(profileRequestJson);
		Collection<String> cids = candidateId.getItems();
		for (String ids : cids) {
			System.out.println(ids);
		}
		profileBizServiceConsumer.getBulkProfiles(candidateId, "full");
		CRMFilter filter = new CRMFilter();
		CollectionEntity<String> statusName = new CollectionEntity<String>();
		statusName.addItem("Engaged");
		filter.setStatusNames(statusName);
		CollectionEntity<Profile> getByCandidateIdResponse = profileBizServiceConsumer.listProfiles("", "", filter);
		if (getByCandidateIdResponse != null) {
			Collection<Profile> candIds = getByCandidateIdResponse.getItems();
			List<String> cids1 = new ArrayList<>();
			for (Profile profile : candIds) {
				String id = profile.getCrm().getCandidateId();
				cids1.add(id);
			}
			for (String reqId : cids) {
				for (String resId : cids1) {
					if (reqId.equals(resId)) {
						Assert.assertNotNull(reqId, "CandidateId showing as null !!");
						Assert.assertEquals(reqId, resId, "Showing wrong Candidates !!");
						Logging.log("-----------pass------------");
					}
				}
			}
		}
	}

	/**
	 * Validate Created profile
	 * 
	 * @param request
	 * @param response
	 */
	public static void profileValidator(Profile request, Profile response) {

		if (request.getCandidate() != null) {

			if (request.getCandidate().getFirstName() != null) {
				Logging.log("Validating FirstName");
				Assert.assertNotNull(response.getCandidate().getFirstName(), "FirstName showing as null !!");
				Assert.assertEquals(request.getCandidate().getFirstName(), response.getCandidate().getFirstName(),
						"Showing wrong FirstName !!");
			}
			if (request.getCandidate().getMiddleName() != null) {
				Logging.log("Validating MiddleName");
				Assert.assertNotNull(response.getCandidate().getMiddleName(), "MiddleName showing as null !!");
				Assert.assertEquals(request.getCandidate().getMiddleName(), response.getCandidate().getMiddleName(),
						"Showing wrong MiddleName !!");
			}
			if (request.getCandidate().getLastName() != null) {
				Logging.log("Validating LastName");
				Assert.assertNotNull(response.getCandidate().getLastName(), "LastName showing as null !!");
				Assert.assertEquals(request.getCandidate().getLastName(), response.getCandidate().getLastName(),
						"Showing wrong LastName !!");
			}
			if (request.getCandidate().getCurrentDomain() != null) {
				Logging.log("Validating CurrentDomain");
				Assert.assertNotNull(response.getCandidate().getCurrentDomain(), "CurrentDomain showing as null !!");
				Assert.assertEquals(request.getCandidate().getCurrentDomain(),
						response.getCandidate().getCurrentDomain(), "Showing wrong CurrentDomain !!");
			}
			// check
			if (request.getCandidate().getTotalExperienceMnth() != null) {
				Logging.log("Validating TotalExperienceMnth");
				Assert.assertNotNull(response.getCandidate().getTotalExperienceMnth(),
						"TotalExperienceMnth showing as 0 !!");
				Assert.assertEquals(request.getCandidate().getTotalExperienceMnth(),
						response.getCandidate().getTotalExperienceMnth(), "Showing wrong TotalExperienceMnth !!");
			}
			if (request.getCandidate().getLocationName() != null) {
				Logging.log("Validating LocationName");
				Assert.assertNotNull(response.getCandidate().getLocationName(), "LocationName showing as null !!");
				Assert.assertEquals(request.getCandidate().getLocationName(), response.getCandidate().getLocationName(),
						"Showing wrong LocationName !!");
			}
			if (request.getCandidate().getPrimaryEmailId() != null) {
				Logging.log("Validating PrimaryEmailId");
				Assert.assertNotNull(response.getCandidate().getPrimaryEmailId(), "PrimaryEmailId showing as null !!");
				Assert.assertEquals(request.getCandidate().getPrimaryEmailId(),
						response.getCandidate().getPrimaryEmailId(), "Showing wrong PrimaryEmailId !!");
			}
			if (request.getCandidate().getPrimaryContactNumber() != null) {
				Logging.log("Validating PrimaryContactNumber");
				Assert.assertNotNull(response.getCandidate().getPrimaryContactNumber(),
						"PrimaryContactNumber showing as null !!");
				Assert.assertEquals(request.getCandidate().getPrimaryContactNumber(),
						response.getCandidate().getPrimaryContactNumber(), "Showing wrong PrimaryContactNumber !!");
			}
			if (request.getCandidate().getSourceName() != null) {
				Logging.log("Validating SourceName");
				Assert.assertNotNull(response.getCandidate().getSourceName(), "SourceName showing as null !!");
				Assert.assertEquals(request.getCandidate().getSourceName(), response.getCandidate().getSourceName(),
						"Showing wrong SourceName !!");
			}
			if (request.getCandidate().getSourceType() != null) {
				Logging.log("Validating SourceType");
				Assert.assertNotNull(response.getCandidate().getSourceType(), "SourceType showing as null !!");
				Assert.assertEquals(request.getCandidate().getSourceType(), response.getCandidate().getSourceType(),
						"Showing wrong SourceType !!");
			}
			if (request.getCandidate().getFlowId() != null) {
				Logging.log("Validating FlowId");
				Assert.assertNotNull(response.getCandidate().getFlowId(), "FlowId showing as null !!");
				Assert.assertEquals(request.getCandidate().getFlowId(), response.getCandidate().getFlowId(),
						"Showing wrong FlowId !!");
			}

			// validating education bean
			if (request.getCandidate().getCandidateEducationMapBean() != null) {
				Logging.log("Validating CandidateEducationMapBean");
				Assert.assertNotNull(response.getCandidate().getCandidateEducationMapBean(),
						"CandidateEducationMapBean showing as null !!");

				if (request.getCandidate().getCandidateEducationMapBean().getItems() != null) {

					Assert.assertNotNull(response.getCandidate().getCandidateEducationMapBean().getItems(),
							"CandidateEducationMapBean showing as null !!");

					Collection<CandidateEducationMapBean> responseCandidateEducationBean = response.getCandidate()
							.getCandidateEducationMapBean().getItems();

					/*
					 * response education map bean is not in same order as given
					 * in response, so storing response in a list and validating
					 */
					List<String> responseDegreeNames = new ArrayList<String>();

					for (CandidateEducationMapBean responseEducationMapBean : responseCandidateEducationBean) {
						responseDegreeNames.add(responseEducationMapBean.getDegreeName());
					}

					List<String> responseInstituteNames = new ArrayList<String>();

					for (CandidateEducationMapBean responseEducationMapBean : responseCandidateEducationBean) {
						responseInstituteNames.add(responseEducationMapBean.getInstituteName());
					}

					Collection<CandidateEducationMapBean> requestCandidateEducationBean = request.getCandidate()
							.getCandidateEducationMapBean().getItems();
					for (CandidateEducationMapBean candidateEducationMapBean : requestCandidateEducationBean) {
						if (candidateEducationMapBean.getDegreeName() != null) {
							Assert.assertTrue(responseDegreeNames.contains(candidateEducationMapBean.getDegreeName()),
									"DegreeName " + candidateEducationMapBean.getDegreeName()
											+ " not showing in response !!");
						}
						if (candidateEducationMapBean.getInstituteName() != null) {
							Assert.assertTrue(
									responseInstituteNames.contains(candidateEducationMapBean.getInstituteName()),
									"InstituteName " + candidateEducationMapBean.getInstituteName()
											+ " not showing in response !!");
						}
					}

				}
			}
			// validating employer bean
			if (request.getCandidate().getCandidateEmployerMapBean() != null) {
				Logging.log("Validating CandidateEmployerMapBean");
				Assert.assertNotNull(response.getCandidate().getCandidateEmployerMapBean(),
						"CandidateEmployerMapBean showing as null !!");

				if (request.getCandidate().getCandidateEmployerMapBean().getItems() != null) {

					Assert.assertNotNull(response.getCandidate().getCandidateEmployerMapBean().getItems(),
							"CandidateEmployerMapBean showing as null !!");

					Collection<CandidateEmployerMapBean> responseCandidateEmployerBean = response.getCandidate()
							.getCandidateEmployerMapBean().getItems();

					List<String> responseDesignations = new ArrayList<String>();

					for (CandidateEmployerMapBean responseEmployerMapBean : responseCandidateEmployerBean) {
						responseDesignations.add(responseEmployerMapBean.getDesignationName());
					}

					List<String> responseEmployers = new ArrayList<String>();

					for (CandidateEmployerMapBean responseEmployerMapBean : responseCandidateEmployerBean) {
						responseEmployers.add(responseEmployerMapBean.getEmployerName());
					}

					Collection<CandidateEmployerMapBean> requestCandidateEmployerBean = request.getCandidate()
							.getCandidateEmployerMapBean().getItems();
					for (CandidateEmployerMapBean candidateEmployerMapBean : requestCandidateEmployerBean) {
						// write equalIgnoreCase
						Assert.assertTrue(
								responseDesignations.toString().toUpperCase()
										.contains(candidateEmployerMapBean.getDesignationName().toUpperCase()),
								"DesignationName " + candidateEmployerMapBean.getDesignationName()
										+ " not showing in response !!");

						Assert.assertTrue(responseEmployers.contains(candidateEmployerMapBean.getEmployerName()),
								"EmployerName " + candidateEmployerMapBean.getEmployerName()
										+ " not showing in response !!");
					}

				}
			}

			// validating skillMapBean
			if (request.getCandidate().getCandidateSkillMapBean() != null) {
				Logging.log("Validating CandidateSkillMapBean");
				Assert.assertNotNull(response.getCandidate().getCandidateSkillMapBean(),
						"CandidateSkillMapBean showing as null !!");

				if (request.getCandidate().getCandidateSkillMapBean().getItems() != null) {

					Collection<CandidateSkillMapBean> responseSkillMapBean = response.getCandidate()
							.getCandidateSkillMapBean().getItems();

					Assert.assertNotNull(responseSkillMapBean, "CandidateSkillMapBean showing as null !!");

					Logging.log("Response Skills");
					List<String> responseSkills = new ArrayList<String>();
					for (CandidateSkillMapBean candidateSkillMapBean : responseSkillMapBean) {
						Logging.log(candidateSkillMapBean.getSkill());
						responseSkills.add(candidateSkillMapBean.getSkill());
					}

					Collection<CandidateSkillMapBean> requestSkillMapBean = request.getCandidate()
							.getCandidateSkillMapBean().getItems();
					for (CandidateSkillMapBean responseSkillBean : requestSkillMapBean) {
						Assert.assertTrue(responseSkills.contains(responseSkillBean.getSkill()),
								"Not showing " + responseSkillBean.getSkill() + " in response !!");
					}

				}
			}
			// validating SocialBean
			if (request.getSocial() != null) {
				Logging.log("Validating Social fields");

				if (request.getSocial().getBlogLink() != null) {
					Assert.assertNotNull(response.getSocial().getBlogLink(), "Showing Social_BlobLink as null !!");
					Assert.assertEquals(request.getSocial().getBlogLink(), response.getSocial().getBlogLink(),
							"Showing wrong Social_BlobLink !!!");
				}
				if (request.getSocial().getFacebookUrl() != null) {
					Assert.assertNotNull(response.getSocial().getFacebookUrl(),
							"Showing Social_FacebookUrl as null !!");
					Assert.assertEquals(request.getSocial().getFacebookUrl(), response.getSocial().getFacebookUrl(),
							"Showing wrong Social_FacebookUrl !!!");
				}
				if (request.getSocial().getImageUrl() != null) {
					Assert.assertNotNull(response.getSocial().getImageUrl(), "Showing Social_ImageURL as null !!");
					Assert.assertEquals(request.getSocial().getImageUrl(), response.getSocial().getImageUrl(),
							"Showing wrong Social_ImageURL !!!");
				}
				if (request.getSocial().getMaritalStatus() != null) {
					Assert.assertNotNull(response.getSocial().getMaritalStatus(),
							"Showing Social_MaritalStatus as null !!");
					Assert.assertEquals(request.getSocial().getMaritalStatus(), response.getSocial().getMaritalStatus(),
							"Showing wrong Social_MaritalStatus !!!");
				}
				if (request.getSocial().getObjective() != null) {
					Assert.assertNotNull(response.getSocial().getObjective(), "Showing Social_Objective as null !!");
					Assert.assertEquals(request.getSocial().getObjective(), response.getSocial().getObjective(),
							"Showing wrong Social_Objective !!!");
				}
				if (request.getSocial().getProfessionalSummary() != null) {
					Assert.assertNotNull(response.getSocial().getProfessionalSummary(),
							"Showing Social_ProfessionalSummary as null !!");
					Assert.assertEquals(request.getSocial().getProfessionalSummary(),
							response.getSocial().getProfessionalSummary(),
							"Showing wrong Social_ProfessionalSummary !!!");
				}
				if (request.getSocial().getSocialExt() != null) {

					Assert.assertNotNull(response.getSocial().getSocialExt(), "Showing SocialExt field as null !!");

					if (request.getSocial().getSocialExt().getAskUbuntuUserId() != null) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getAskUbuntuUserId(),
								"Showing SocialExt field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getAskUbuntuUserId(),
								response.getSocial().getSocialExt().getAskUbuntuUserId(),
								"Showing wrong SocialExt_AskUbuntuUserId !!!");
					}
					if (request.getSocial().getSocialExt().getGithubFollowersCount() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getGithubFollowersCount(),
								"Showing SocialExt_GithubFollowersCount field as zero !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getGithubFollowersCount(),
								response.getSocial().getSocialExt().getGithubFollowersCount(),
								"Showing wrong SocialExt_GithubFollowersCount !!!");
					}
					if (request.getSocial().getSocialExt().getGithubFollowingCount() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getGithubFollowingCount(),
								"Showing SocialExt_GithubFollowingCount field as 0 !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getGithubFollowingCount(),
								response.getSocial().getSocialExt().getGithubFollowingCount(),
								"Showing wrong SocialExt_GithubFollowingCount !!!");
					}
					if (request.getSocial().getSocialExt().getGithubPublicRepos() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getGithubPublicRepos(),
								"Showing SocialExt_GithubPublicRepos field as 0 !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getGithubPublicRepos(),
								response.getSocial().getSocialExt().getGithubPublicRepos(),
								"Showing wrong SocialExt_GithubPublicRepos !!!");
					}
					if (request.getSocial().getSocialExt().getGithubUserId() != null) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getGithubUserId(),
								"Showing SocialExt_GithubUserId field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getGithubUserId(),
								response.getSocial().getSocialExt().getGithubUserId(),
								"Showing wrong SocialExt_GithubUserId !!!");
					}
					if (request.getSocial().getSocialExt().getLinkedInConnectionsCount() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getLinkedInConnectionsCount(),
								"Showing SocialExt_LinkedInConnectionsCount field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getLinkedInConnectionsCount(),
								response.getSocial().getSocialExt().getLinkedInConnectionsCount(),
								"Showing wrong SocialExt_LinkedInConnectionsCount !!!");
					}
					if (request.getSocial().getSocialExt().getOriginalGithubRepoCount() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getOriginalGithubRepoCount(),
								"Showing SocialExt_OriginalGithubRepoCount field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getOriginalGithubRepoCount(),
								response.getSocial().getSocialExt().getOriginalGithubRepoCount(),
								"Showing wrong SocialExt_OriginalGithubRepoCount !!!");
					}
					if (request.getSocial().getSocialExt().getStackoverflowBadgeCount() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getStackoverflowBadgeCount(),
								"Showing SocialExt_StackoverflowBadgeCount field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getStackoverflowBadgeCount(),
								response.getSocial().getSocialExt().getStackoverflowBadgeCount(),
								"Showing wrong SocialExt_StackoverflowBadgeCount !!!");
					}
					if (request.getSocial().getSocialExt().getStackoverflowReputationPoints() != 0) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getStackoverflowReputationPoints(),
								"Showing SocialExt_StackoverflowReputationPoints field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getStackoverflowReputationPoints(),
								response.getSocial().getSocialExt().getStackoverflowReputationPoints(),
								"Showing wrong SocialExt_StackoverflowReputationPoints !!!");
					}
					if (request.getSocial().getSocialExt().getStackoverflowUserId() != null) {

						Assert.assertNotNull(response.getSocial().getSocialExt().getStackoverflowUserId(),
								"Showing SocialExt_StackoverflowUserId field as null !!");
						Assert.assertEquals(request.getSocial().getSocialExt().getStackoverflowUserId(),
								response.getSocial().getSocialExt().getStackoverflowUserId(),
								"Showing wrong SocialExt_StackoverflowUserId !!!");
					}

				}

				if (request.getSocial().getLinkedinUrl() != null) {
					Assert.assertNotNull(response.getSocial().getLinkedinUrl(),
							"Showing Social_LinkedInURL as null !!");
					Assert.assertEquals(request.getSocial().getLinkedinUrl(), response.getSocial().getLinkedinUrl(),
							"Showing wrong Social_LinkedInURL !!!");
				}
			}
			// Validating CRM
			if (request.getCrm() != null) {
				if (request.getCrm().getStatusName() != null) {
					Assert.assertNotNull(response.getCrm().getStatusName(), "Showing Social_LinkedInURL as null !!");
					Assert.assertEquals(request.getCrm().getStatusName(), response.getCrm().getStatusName(),
							"Showing wrong Social_LinkedInURL !!!");
				}

			}

		}
	}

	/**
	 * Validate Created bulk profile
	 * 
	 * @param request
	 * @param response
	 */
	public static void profileValidator(List<Profile> request1, CollectionEntity<Profile> response1) {
		Collection<Profile> res = response1.getItems();
		Profile response = null;
		for (Profile request : request1) {
			for (Profile response2 : res) {
				if (request.getCandidate() != null) {
					if (request.getCandidate().getFirstName().equals(response2.getCandidate().getFirstName())) {
						response = response2;
						ProfileJson.profileValidator(request, response);
						break;
					}
				}
			}

		}
	}

}

package com.spire.crm.restful.biz.consumers;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.base.helper.FileHelper;
import com.spire.crm.restful.base.service.BaseServiceConsumerNew;
import com.spire.crm.restful.base.service.ReadingServiceEndPointsProperties;

import crm.pipeline.beans.CRMFilter;
import crm.pipeline.entities.CollectionEntity;
import spire.commons.search.response.SearchResponse;
import spire.crm.profiles.bean.CRMFilterProfilesResponse;
import spire.crm.profiles.bean.CreateProfileDetail;
import spire.crm.profiles.bean.Profile;
import spire.crm.profiles.entity.CandidateAlreadyViewedVO;
import spire.crm.profiles.entity.RecruiterVO;
import spire.social.entity.Social;
import spire.talent.entity.profileservice.beans.CandidateBean;
import spire.talent.entity.profileservice.beans.CandidateEducationMapBean;
import spire.talent.entity.profileservice.beans.CandidateEmployerMapBean;
import spire.talent.entity.profileservice.beans.CandidatePatchBean;
import spire.talent.entity.profileservice.beans.CandidateSkillMapBean;

/**
 * @author Santosh
 */
public class ProfileBizServiceConsumer extends BaseServiceConsumerNew {

	Response response = null;
	String endPointURL = ReadingServiceEndPointsProperties.getServiceEndPoint("PROFILE_WEB_BIZ");
	String userId = ReadingServiceEndPointsProperties.getServiceEndPoint("userId");
	Gson gson = new Gson();
	Logger logger = Logger.getLogger(FileHelper.class);
	public boolean HEADERS = true;

	public ProfileBizServiceConsumer() {

	}

	public ProfileBizServiceConsumer(String username, String password) {
		getUserToken(username, password);
	}

	/**
	 * Create Profile
	 * 
	 * @param profile
	 * @return candidateId
	 */
	public String createProfile(Profile profile) {

		String serviceEndPoint = this.endPointURL;
		logger.info("service endpoint -->" + serviceEndPoint);
		Logging.log(" CreateProfile endPointURL  >>>" + serviceEndPoint);
		Logging.log("CreateProfileRequest json: " + gson.toJson(profile));
		Response response = executePOST(serviceEndPoint, HEADERS, Entity.entity(profile, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			CreateProfileDetail createProfileDetail = response.readEntity(CreateProfileDetail.class);
			Logging.log("Created candidateId: " + createProfileDetail.getCandidateId());
			return createProfileDetail.getCandidateId();
		} else {
			logger.info("status code -->" + response.getStatus());
			Assert.fail("Profile service got failed !!! Status Code: " + response.getStatus() + " Message: "
					+ response.getStatusInfo());
			return null;
		}
	}

	public Profile getProfiles(String projection) throws Exception {
		response = executeGET(this.endPointURL, HEADERS);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			Profile profile = response.readEntity(Profile.class);
			return profile;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	public Profile getProfile(String candidateId, String projectionType) {
		String serviceEndPoint = this.endPointURL + "/" + candidateId + "?projectionType=" + projectionType;
		System.out.println("serviceEndPoint " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, HEADERS);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			Profile profile = response.readEntity(Profile.class);
			Logging.log("Response: " + gson.toJson(profile));
			return profile;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	public Response addNewEmployer(String profileId, CandidateEmployerMapBean employer) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId + "/employer";
		Entity<CandidateEmployerMapBean> serviceEntity = Entity.entity(employer, MediaType.APPLICATION_JSON);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	public CollectionEntity<String> listProfilesByStageTypes(String limit, String offset, CRMFilter filters)
			throws Exception {
		String serviceEndPoint = this.endPointURL + "/_listProfiles";
		Entity<CRMFilter> serviceEntity = Entity.entity(filters, MediaType.MULTIPART_FORM_DATA_TYPE);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<String> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<String>>() {
					});
			return collectionEntity;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	public Response updateCandidate(String profileId, CandidateBean candidateBean) throws Exception {
		String serviceEndPoint = this.endPointURL + "/_listProfiles";
		Entity<CandidateBean> serviceEntity = Entity.entity(candidateBean, MediaType.MULTIPART_FORM_DATA_TYPE);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else if (response.getStatus() == 304) {
			Logging.log("------ErrorEntity----------");
			return null;
		}
		return null;

	}

	public Response addEmployer(String profileId, CandidateEmployerMapBean employer) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId;
		Entity<CandidateEmployerMapBean> serviceEntity = Entity.entity(employer, MediaType.MULTIPART_FORM_DATA_TYPE);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else if (response.getStatus() == 304) {
			Logging.log("------ErrorEntity----------");
			return null;
		}
		return null;

	}

	public Response addNewEducation(String profileId, CandidateEducationMapBean education) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId + "/education";
		Entity<CandidateEducationMapBean> serviceEntity = Entity.entity(education, MediaType.APPLICATION_JSON);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else if (response.getStatus() == 304) {
			Logging.log("------ErrorEntity----------");
			return null;
		}
		return null;

	}

	/*--------------------------Post Operation--------------------------*/
	/* /{profileId}/skills */
	public Response addSkill(String profileId, CandidateSkillMapBean skills) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId + "/skills";
		Entity<CandidateSkillMapBean> serviceEntity = Entity.entity(skills, MediaType.APPLICATION_JSON);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else if (response.getStatus() == 304) {
			Logging.log("------ErrorEntity----------");
			return null;
		}
		return null;

	}

	/*--------------------------Post Operation--------------------------*/
	/* /{profileId}/education/{educationId} */
	public Response updateEducationDetails(String profileId, String educationId, CandidateEducationMapBean candidateEdu)
			throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId + "/education/" + educationId;
		Entity<CandidateEducationMapBean> serviceEntity = Entity.entity(candidateEdu,
				MediaType.MULTIPART_FORM_DATA_TYPE);
		Response response = executePOST(serviceEndPoint, HEADERS, serviceEntity);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else if (response.getStatus() == 304) {
			Logging.log("------ErrorEntity----------");
			return null;
		}
		return null;

	}

	/*--------------------------Post Operation--------------------------*/
	public CollectionEntity<String> createBulkProfiles(List<Profile> profiles) throws Exception {
		String serviceEndPoint = this.endPointURL + "/_bulk";
		Logging.log(" createBulkProfiles endPointURL  >>>" + serviceEndPoint);
		Logging.log("CreateBulkProfileRequest json: " + gson.toJson(profiles));
		Response response = executePOST(serviceEndPoint, HEADERS, Entity.entity(profiles, MediaType.APPLICATION_JSON));
		List<CreateProfileDetail> list;
		if (response.getStatus() == 200) {
			list = response.readEntity(new GenericType<List<CreateProfileDetail>>() {
			});

			CollectionEntity<String> ids = new CollectionEntity<>();
			for (CreateProfileDetail createProfileDetail : list) {
				String candidateIds = createProfileDetail.getCandidateId();
				ids.addItem(candidateIds);
			}

			return ids;
		} else {
			Assert.fail("Profile service got failed !!!");
			return null;
		}

	}

	/*--------------------------Post Operation--------------------------*/
	public CollectionEntity<Profile> getBulkProfiles(CollectionEntity<String> profileIds, String projection)
			throws Exception {
		String serviceEndPoint = this.endPointURL + "/_getProfiles?projection=" + projection;
		Response response = executePOST(serviceEndPoint, HEADERS,
				Entity.entity(profileIds, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CollectionEntity<Profile> collectionEntity = response
					.readEntity(new GenericType<CollectionEntity<Profile>>() {
					});
			Logging.log("Response: " + gson.toJson(collectionEntity));
			return collectionEntity;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	/*--------------------------Post Operation--------------------------*/
	public Response patchCandidate(CandidatePatchBean candidatePatchBean) throws Exception {
		String serviceEndPoint = this.endPointURL + "/_patchCandidate";
		Response response = executePOST(serviceEndPoint, HEADERS,
				Entity.entity(candidatePatchBean, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	/*--------------------------Post Operation--------------------------*/
	/* /{profileId}/skills/{skillId} */
	public Response removeCandidateSkill(String profileId, String skillId) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId + "/skills/" + skillId;
		Response response = executePOST(serviceEndPoint, HEADERS, Entity.entity(skillId, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	/*--------------------------Post Operation--------------------------*/
	public CollectionEntity<Profile> listProfiles(String limit, String offset, CRMFilter filters) throws Exception {
		String serviceEndPoint = this.endPointURL + "/_listProfiles?limit=" + limit + "&offset=" + offset;
		Response response = executePOST(serviceEndPoint, HEADERS, Entity.entity(filters, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			CRMFilterProfilesResponse crmFilterProfilesResponse = response.readEntity(CRMFilterProfilesResponse.class);
			CollectionEntity<Profile> collectionEntity = crmFilterProfilesResponse.getProfileDetails();
			Logging.log("Response: " + gson.toJson(collectionEntity));
			return collectionEntity;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	/*--------------------------Post Operation--------------------------*/
	public Response updateSocialDetails(String profileId, Social socialBean) throws Exception {
		String serviceEndPoint = this.endPointURL + "/" + profileId + "/social";
		Response response = executePOST(serviceEndPoint, HEADERS,
				Entity.entity(socialBean, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			return response;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}

	}

	/**
	 * Get candidate details stored in ES
	 * 
	 * @param search_match_host
	 * @param candidateId
	 * @return candidateSummary
	 */
	public SearchResponse getCandidateDetailsFromES(String search_match_host, String candidateId) {

		String serviceEndPoint = "http://" + search_match_host
				+ "/search-and-match-service-talent/api/v1/_search?source_id=" + candidateId + "&target_type=candidate";
		System.out.println("serviceEndPoint " + serviceEndPoint);
		Response response = executePOST(serviceEndPoint, HEADERS, Entity.entity(null, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			SearchResponse candidateSummary = response.readEntity(SearchResponse.class);
			Logging.log("Response: " + gson.toJson(candidateSummary));
			return candidateSummary;
		} else {
			Logging.log("------ErrorEntity----------");
			return null;
		}
	}

	public void getHireAs(String candidateId) {
		String serviceEndPoint = this.endPointURL.replace("i/profiles", "i/hireas/") + candidateId;
		Response response = executeGET(serviceEndPoint, HEADERS);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			String s = response.readEntity(String.class);
			if (s.contains("items")) {
				Logging.log("Response contains items are : " + s);
				Assert.assertTrue(true);
			} else {
				Assert.fail("Resposne does not contains items");
				Logging.log("Resposne does not contains items,Instead we got :" + s);
			}

		} else {
			Assert.fail("Did not get Expected Responce code , Instead we got : " + response.getStatus());
			Logging.log("Did not get Expected Responce code , Instead we got : " + response.getStatus());
		}

	}

	public void alsoView(String candidateId) {
		String serviceEndPoint = this.endPointURL + "/alsoviewed/candidate/" + candidateId;
		Response response = executeGET(serviceEndPoint, HEADERS);
		Logging.log("response code >>>" + response.getStatus());
		if (response.getStatus() == 200) {
			String s = response.readEntity(String.class);
			if (s.contains("items")) {
				Logging.log("Response contains items are : " + s);
				Assert.assertTrue(true);
			} else {
				if (s.contains("items") && s.contains("null")) {
					Logging.log("Got Empty Response");
				} else
					Assert.fail("Resposne does not contains items");
				Logging.log("Resposne does not contains items,Instead we got :" + s);
			}

		} else {
			Assert.fail("Did not get Expected Responce code , Instead we got : " + response.getStatus());
			Logging.log("Did not get Expected Responce code , Instead we got : " + response.getStatus());
		}
	}

	public Map<String, Boolean> alreadyViewedCandidatesForRecruiter(CandidateAlreadyViewedVO candidateAlreadyViewedVO) {
		String serviceEndPoint = this.endPointURL + "/alreadyviewed";
		candidateAlreadyViewedVO.setRecruiterId(userId);

		Logging.log("Endpoint: " + serviceEndPoint);
		Logging.log("Request: " + gson.toJson(candidateAlreadyViewedVO));

		Response response = executePOST(serviceEndPoint, HEADERS,
				Entity.entity(candidateAlreadyViewedVO, MediaType.APPLICATION_JSON));
		Logging.log("response code >>>" + response.getStatus());

		if (response.getStatus() == 200) {
			Map<String, Boolean> entitiesFromResponse = response.readEntity(new GenericType<Map<String, Boolean>>() {
			});
			Logging.log("Response: " + gson.toJson(entitiesFromResponse));
			return entitiesFromResponse;
		} else {
			Assert.fail("AlreadyViewedCandidatesForRecruiter failed!! Status code: " + response.getStatus());
			Logging.log("------ErrorEntity----------");
			return null;
		}
	}

	public List<RecruiterVO> getRecruitersWhoViewed(String candidateId) {
		String serviceEndPoint = this.endPointURL + "/recruiters/candidate/" + candidateId;
		Logging.log("serviceEndPoint " + serviceEndPoint);
		Response response = executeGET(serviceEndPoint, HEADERS);
		Logging.log("response code >>>" + response.getStatus());

		if (response.getStatus() == 200) {
			List<RecruiterVO> recruiterDetails = response.readEntity(new GenericType<List<RecruiterVO>>() {
			});
			Logging.log("Response: " + gson.toJson(recruiterDetails));
			return recruiterDetails;
		} else {
			Assert.fail("Failed to getAllRecruiters!! Status code: " + response.getStatus());
			Logging.log("------ErrorEntity----------");
			return null;
		}
	}

}

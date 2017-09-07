package com.spire.crm.entity.social.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;

import spire.commons.utils.IdUtils;
import spire.social.entity.Connect;
import spire.social.entity.Social;
import spire.social.entity.SocialExt;

import com.google.gson.Gson;
import com.spire.base.controller.Logging;
import com.spire.common.TestData;
import com.spire.crawl.helper.CRMTestPlan;
import com.spire.crm.restful.entity.consumers.SocialEntityServiceConsumer;

/**
 * @author Santosh C
 *
 */
public class SocialServiceValidation extends CRMTestPlan {

	Gson gson = new Gson();
	/**
	 * Create SocialProfile, validate that it returns a socialId
	 * 
	 * @return candidateId
	 * @throws InterruptedException
	 */
	public static String createSocialProfile(TestData data) {

		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();

		Social createSocialData = new Social();

		String candidateId = IdUtils.generateID("6066", "6444");
		createSocialData.setCandidateId(candidateId);

		createSocialData.setProfessionalSummary("Good knowlwdge in Automation");
		createSocialData.setImageUrl("http://www.theloop.ca/wp-content/uploads/2016/01/billions.jpg");
		createSocialData.setFacebookUrl("http://facebook.com");
		createSocialData.setBlogLink("http://facebook.org");
		createSocialData.setStackoverflowRank(100);
		createSocialData.setLinkedinUrl("http://www.linkedin.com");
		createSocialData.setMaritalStatus("single");

		Set<String> hobbies = new HashSet<String>();
		hobbies.add("CRICKET");
		hobbies.add("HOCKEY");
		createSocialData.setHobbies(hobbies);

		Set<String> interests = new HashSet<String>();
		interests.add("LISTENING MUSIC");
		interests.add("WATCHING MOVIES");
		createSocialData.setInterests(interests);

		if (!data.getData().equals("FollowersNull")) {

			List<Connect> followers = new ArrayList<Connect>();
			Connect connectFollowers = new Connect();
			connectFollowers.setConnectName("Spire");
			connectFollowers.setConnectType("LinkedIn");
			connectFollowers.setConnectURL("http://facebook.com");
			connectFollowers.setCreatedBy("Spire");
			connectFollowers.setEmail("abcd@spire.com");
			connectFollowers.setMobileNumber("1234567890");
			connectFollowers.setModifiedBy("Spire");
			followers.add(connectFollowers);
			createSocialData.setFollowers(followers);

		}

		if (!data.getData().equals("FollowingNull")) {

			List<Connect> following = new ArrayList<Connect>();
			Connect connectFollowing = new Connect();
			connectFollowing.setConnectName("Spire");
			connectFollowing.setConnectType("LinkedIn");
			connectFollowing.setConnectURL("http://facebook.com");
			connectFollowing.setCreatedBy("Spire");
			connectFollowing.setEmail("abcd@spire.com");
			connectFollowing.setMobileNumber("1234567890");
			connectFollowing.setModifiedBy("Spire");
			following.add(connectFollowing);
			createSocialData.setFollowing(following);

		}

		if (!data.getData().equals("SocialExtNull")) {

			SocialExt socialExtRequest = new SocialExt();
			socialExtRequest.setStackoverflowBadgeCount(10);
			socialExtRequest.setAskUbuntuUserId("100");
			socialExtRequest.setGithubFollowersCount(90);
			socialExtRequest.setGithubFollowersCount(50);
			socialExtRequest.setGithubFollowingCount(88);
			socialExtRequest.setGithubPublicRepos(78);
			socialExtRequest.setLinkedInConnectionsCount(360);
			socialExtRequest.setOriginalGithubRepoCount(422);
			socialExtRequest.setStackoverflowBadgeCount(58);
			socialExtRequest.setStackoverflowReputationPoints(789);
			socialExtRequest.setStackoverflowUserId("stack-21");
			createSocialData.setSocialExt(socialExtRequest);

		}

		String socialId = socialEntityServiceConsumer.createSocialProfile(createSocialData);

		Assert.assertNotNull(socialId, "Social is returning null!!");

		SocialServiceValidation.validatedCreatedSocailProfile(candidateId, createSocialData, data);
		return candidateId;

	}

	/**
	 * Validate created SocialProfile
	 * 
	 * @param candidateId
	 * @param createdSocialRequest
	 */
	public static void validatedCreatedSocailProfile(String candidateId, Social createdSocialRequest, TestData data) {
		
		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();

		Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");

		Assert.assertEquals(socialResponse.getBlogLink(), createdSocialRequest.getBlogLink(),
				"Showing wrong BlobLink!!");

		Assert.assertEquals(socialResponse.getCandidateId(), createdSocialRequest.getCandidateId(),
				"Showing wrong CandidateId!!");

		Assert.assertEquals(socialResponse.getFacebookUrl(), createdSocialRequest.getFacebookUrl(),
				"Showing wrong FacebookURL!!");

		Assert.assertEquals(socialResponse.getHobbies(), createdSocialRequest.getHobbies(), "Showing wrong Hobbies!!");

		Assert.assertEquals(socialResponse.getImageUrl(), createdSocialRequest.getImageUrl(),
				"Showing wrong ImageUrl!!");

		Assert.assertEquals(socialResponse.getInterests(), createdSocialRequest.getInterests(),
				"Showing wrong Interests!!");

		Assert.assertEquals(socialResponse.getLinkedinUrl(), createdSocialRequest.getLinkedinUrl(),
				"Showing wrong LinkedInURL!!");

		Assert.assertEquals(socialResponse.getMaritalStatus(), createdSocialRequest.getMaritalStatus(),
				"Showing wrong MaritalStatus!!");

		Assert.assertEquals(socialResponse.getObjective(), createdSocialRequest.getObjective(),
				"Showing wrong Objective!!");

		Assert.assertEquals(socialResponse.getProfessionalSummary(), createdSocialRequest.getProfessionalSummary(),
				"Showing wrong ProfessionalSummary!!");

		Assert.assertEquals(socialResponse.getTwitterUrl(), createdSocialRequest.getTwitterUrl(),
				"Showing wrong TwitterUrl!!");

		if (!data.getData().equals("FollowersNull")) {

			SocialServiceValidation.validateFollowers(createdSocialRequest, socialResponse);

		}

		if (!data.getData().equals("FollowingNull")) {

			SocialServiceValidation.validateFollowing(createdSocialRequest, socialResponse);

		}

		Assert.assertEquals(socialResponse.getStackoverflowRank(), createdSocialRequest.getStackoverflowRank(),
				"Showing wrong StackoverflowRank!!");

		if (!data.getData().equals("SocialExtNull")) {

			Assert.assertNotNull(socialResponse.getSocialExt(), "SocialExt field is coming null!!");

			Assert.assertEquals(socialResponse.getSocialExt().getAskUbuntuUserId(),
					createdSocialRequest.getSocialExt().getAskUbuntuUserId(), "Showing wrong AskUbuntuUserId!!");

			Assert.assertEquals(socialResponse.getSocialExt().getGithubUserId(),
					createdSocialRequest.getSocialExt().getGithubUserId(), "Showing wrong GithubUserId!!");

			Assert.assertEquals(socialResponse.getSocialExt().getStackoverflowUserId(),
					createdSocialRequest.getSocialExt().getStackoverflowUserId(),
					"Showing wrong StackoverflowUserId!!");

			Assert.assertEquals(socialResponse.getSocialExt().getGithubFollowersCount(),
					createdSocialRequest.getSocialExt().getGithubFollowersCount(),
					"Showing wrong GithubFollowersCount!!");

			Assert.assertEquals(socialResponse.getSocialExt().getGithubFollowingCount(),
					createdSocialRequest.getSocialExt().getGithubFollowingCount(),
					"Showing wrong GithubFollowingCount!!");

			Assert.assertEquals(socialResponse.getSocialExt().getGithubPublicRepos(),
					createdSocialRequest.getSocialExt().getGithubPublicRepos(), "Showing wrong GithubPublicRepos!!");

			Assert.assertEquals(socialResponse.getSocialExt().getLinkedInConnectionsCount(),
					createdSocialRequest.getSocialExt().getLinkedInConnectionsCount(),
					"Showing wrong LinkedInConnectionsCount!!");

			Assert.assertEquals(socialResponse.getSocialExt().getOriginalGithubRepoCount(),
					createdSocialRequest.getSocialExt().getOriginalGithubRepoCount(),
					"Showing wrong OriginalGithubRepoCount!!");

			Assert.assertEquals(socialResponse.getSocialExt().getStackoverflowBadgeCount(),
					createdSocialRequest.getSocialExt().getStackoverflowBadgeCount(),
					"Showing wrong StackoverflowBadgeCount!!");

			Assert.assertEquals(socialResponse.getSocialExt().getStackoverflowReputationPoints(),
					createdSocialRequest.getSocialExt().getStackoverflowReputationPoints(),
					"Showing wrong StackoverflowReputationPoints!!");

		}

	}

	/**
	 * Create BasicSocialProfile[By giving few fields]
	 * 
	 * @param data
	 */
	public static void createBasicSocialProfile(TestData data) {
		String candidateId = IdUtils.generateID("6066", "6444");
		Social createSocialData = new Social();
		createSocialData.setCandidateId(candidateId);

		if (data.getData().equals("Interests")) {

			Set<String> hobbies = new HashSet<String>();
			hobbies.add("CRICKET");
			hobbies.add("HOCKEY");
			createSocialData.setHobbies(hobbies);

		}

		if (data.getData().equals("Hobbies")) {

			Set<String> interests = new HashSet<String>();
			interests.add("LISTENING MUSIC");
			interests.add("WATCHING MOVIES");
			createSocialData.setInterests(interests);

		}

		if (data.getData().equals("Followers")) {

			List<Connect> followers = new ArrayList<Connect>();
			Connect connectFollowers = new Connect();
			connectFollowers.setConnectName("Spire");
			connectFollowers.setConnectURL("http://facebook.com");
			connectFollowers.setCreatedBy("Spire");
			connectFollowers.setEmail("abcd@spire.com");
			connectFollowers.setMobileNumber("1234567890");
			connectFollowers.setModifiedBy("Spire");
			followers.add(connectFollowers);
			createSocialData.setFollowers(followers);

		}

		if (data.getData().equals("Following")) {
			List<Connect> following = new ArrayList<Connect>();
			Connect connectFollowing = new Connect();
			connectFollowing.setConnectName("Spire");
			connectFollowing.setConnectURL("http://facebook.com");
			connectFollowing.setCreatedBy("Spire");
			connectFollowing.setEmail("abcd@spire.com");
			connectFollowing.setMobileNumber("1234567890");
			connectFollowing.setModifiedBy("Spire");
			following.add(connectFollowing);
			createSocialData.setFollowing(following);
		}

		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();

		String socialId = socialEntityServiceConsumer.createSocialProfile(createSocialData);

		Assert.assertNotNull(socialId, "Not returning SocialId");

		Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");

		Assert.assertEquals(socialResponse.getCandidateId(), createSocialData.getCandidateId(),
				"Showing wrong candidateId");

		if (data.getData().equals("Interests"))

		{
			Assert.assertEquals(socialResponse.getHobbies(), createSocialData.getHobbies(), "Showing wrong Hobbies!!");
		}

		if (data.getData().equals("Hobbies"))

		{
			Assert.assertEquals(socialResponse.getInterests(), createSocialData.getInterests(),
					"Showing wrong Interests!!");
		}

		if (data.getData().equals("Followers")) {

			SocialServiceValidation.validateFollowers(createSocialData, socialResponse);

		}

		if (data.getData().equals("Following")) {

			SocialServiceValidation.validateFollowing(createSocialData, socialResponse);

		}

	}

	/**
	 * Validate SocialFollowers
	 * 
	 * @param socialRequest
	 * @param socialResponse
	 */
	public static void validateFollowers(Social socialRequest, Social socialResponse) {

		if (socialRequest.getFollowers() != null) {
			Assert.assertNotNull(socialResponse.getFollowers(), "Followers is coming null !!");

			Assert.assertEquals(socialRequest.getFollowers().get(0).getConnectName(),
					socialResponse.getFollowers().get(0).getConnectName(), "Showing wrong FollowersConnectName!!");

			Assert.assertEquals(socialRequest.getFollowers().get(0).getConnectURL(),
					socialResponse.getFollowers().get(0).getConnectURL(), "Showing wrong FollowersConnectURL!!");

			Assert.assertEquals(socialRequest.getFollowers().get(0).getImageURL(),
					socialResponse.getFollowers().get(0).getImageURL(), "Showing wrong FollowersImageURL!!");

			Assert.assertEquals(socialRequest.getFollowers().get(0).getMobileNumber(),
					socialResponse.getFollowers().get(0).getMobileNumber(), "Showing wrong FollowersMobileNumber!!");
		}

	}

	/**
	 * Validate SocialFollowing
	 * 
	 * @param socialRequest
	 * @param socialResponse
	 */
	public static void validateFollowing(Social socialRequest, Social socialResponse) {

		if (socialRequest.getFollowing() != null) {

			Assert.assertNotNull(socialResponse.getFollowing(), "Following is coming null !!");

			Assert.assertEquals(socialRequest.getFollowing().get(0).getConnectName(),
					socialResponse.getFollowing().get(0).getConnectName(), "Showing wrong FollowingConnectName!!");

			Assert.assertEquals(socialRequest.getFollowing().get(0).getConnectURL(),
					socialResponse.getFollowing().get(0).getConnectURL(), "Showing wrong FollowingConnectURL!!");

			Assert.assertEquals(socialRequest.getFollowing().get(0).getImageURL(),
					socialResponse.getFollowing().get(0).getImageURL(), "Showing wrong FollowingImageURL!!");

			Assert.assertEquals(socialRequest.getFollowing().get(0).getMobileNumber(),
					socialResponse.getFollowing().get(0).getMobileNumber(), "Showing wrong FollowingMobileNumber!!");
		}

	}

	/**
	 * Create MiniSocialProfile [By giving single field]
	 * 
	 * @param data
	 */
	public static void createMiniSocialProfile(TestData data) {
		
		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();
		Social createSocialData = new Social();
		
		String candidateId = IdUtils.generateID("6066", "6444");
		createSocialData.setCandidateId(candidateId);

		if (data.getData().equals("ProfessionalSummary")) {
			createSocialData.setProfessionalSummary("Good in WebService Automation");
		}

		if (data.getData().equals("StackOverFlowrank")) {
			createSocialData.setStackoverflowRank((152));
		}

		if (data.getData().equals("Obective")) {
			createSocialData.setObjective("Automation");
		}

		String socialId = socialEntityServiceConsumer.createSocialProfile(createSocialData);

		Assert.assertNotNull(socialId, "Not returning SocialId");

		Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");

		if (data.getData().equals("ProfessionalSummary")) {

			Assert.assertEquals(socialResponse.getProfessionalSummary(), createSocialData.getProfessionalSummary(),
					"Showing wrong ProfessionalSummary !!");
		}

		if (data.getData().equals("StackOverFlowrank")) {
			Assert.assertEquals(socialResponse.getStackoverflowRank(), createSocialData.getStackoverflowRank(),
					"Showing wrong StackoverflowRank !!");
		}

		if (data.getData().equals("Obective")) {
			Assert.assertEquals(socialResponse.getObjective(), createSocialData.getObjective(),
					"Showing wrong Objective !!");
		}

	}

	/**
	 * Create GetSocialProfile by giving candidateId Validate
	 * projectionTypes[full, basic, intermediate]
	 * 
	 * @param data
	 */
	public static void getSocialProfile(TestData data) {
		
		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();

		String candidateId = SocialServiceValidation.createSocialProfile(data);

		Social socialResponse = null;

		if (data.getData().equals("full")) {
			socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");
			// validate that all fields are not null
			Assert.assertNotNull(socialResponse.getFollowers(), "Followers are showing null !!");
			Assert.assertNotNull(socialResponse.getSocialExt(), "SocialExt are showing null !!");
		}

		if (data.getData().equals("basic")) {
			socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "basic");
			// validate that basic fields are not null
		}

		if (data.getData().equals("intermediate")) {
			socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "intermediate");
			// validate that intermediate fields are not null
		}
		Assert.assertNotNull(socialResponse.getCandidateId(), "CandidateId not showing!!");
	}

	/**
	 * SocialService LRG tests[Try to create duplicate social profile to same
	 * candidateId,, GetByInvalid candidateId]
	 * 
	 * @param data
	 */
	public static void socialProfile_LRG(TestData data) {
		if (data.getData().equals("invalidCandidateId")) {

			SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();
			// generating new candidateId[which doesnot has social details, it
			// should throw proper exception]
			try {
				socialEntityServiceConsumer.getSocialProfile(IdUtils.generateID("6044", "6002"), "full");
				Assert.fail("Not throwing exception for invalid candidateId");
			} catch (Exception e) {

				Logging.log("Exception excpected!! >>> Testcase Passed!!");
			}

		} else {

			String candidateId = SocialServiceValidation.createSocialProfile(data);

			Social createSocialData = new Social();
			// setting already created candidateId[shouldnot create
			// socialProfile, should throw proper exception]
			createSocialData.setCandidateId(candidateId);

			createSocialData.setProfessionalSummary("Good knowlwdge in Automation");
			createSocialData.setStackoverflowRank(100);
			createSocialData.setMaritalStatus("single");

			SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();
			String responseMsg = socialEntityServiceConsumer.createSocialProfile(createSocialData);

			Assert.assertTrue(responseMsg.contains("candidate ID already exists"),
					"Not throwing proper exception for duplicate candidateId!!");

		}
	}

	/**
	 * Create BulkSocialProfiles and returns candidateIds
	 * 
	 * @param data
	 * @return candidateIds
	 */
	public static List<String> createBulkSocialProfiles(TestData data) {
		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();

		List<String> candidateIds = new ArrayList<String>();

		String candidateId1 = IdUtils.generateID("6066", "6444");
		String candidateId2 = IdUtils.generateID("6066", "6444");
		String candidateId3 = IdUtils.generateID("6066", "6444");
		String candidateId4 = IdUtils.generateID("6066", "6444");
		String candidateId5 = IdUtils.generateID("6066", "6444");

		candidateIds.add(candidateId1);
		candidateIds.add(candidateId2);
		candidateIds.add(candidateId3);
		candidateIds.add(candidateId4);
		candidateIds.add(candidateId5);

		Social social1 = SocialServiceValidation.createSocialData(candidateId1);
		Social social2 = SocialServiceValidation.createSocialData(candidateId2);
		Social social3 = SocialServiceValidation.createSocialData(candidateId3);
		Social social4 = SocialServiceValidation.createSocialData(candidateId4);
		Social social5 = SocialServiceValidation.createSocialData(candidateId5);

		List<Social> socialBulkRequest = new ArrayList<Social>();
		socialBulkRequest.add(social1);
		socialBulkRequest.add(social2);
		socialBulkRequest.add(social3);
		socialBulkRequest.add(social4);
		socialBulkRequest.add(social5);

		socialEntityServiceConsumer.createBulkSocialProfiles(socialBulkRequest);
		SocialServiceValidation.validatedCreatedSocailProfile(candidateId1, social1, data);
		SocialServiceValidation.validatedCreatedSocailProfile(candidateId2, social2, data);

		return candidateIds;

	}

	/**
	 * Create social data
	 * 
	 * @param candidateId
	 * @return candidateId
	 */
	public static Social createSocialData(String candidateId) {

		Social social1 = new Social();
		social1.setCandidateId(candidateId);
		social1.setProfessionalSummary("Good knowledge in Java");
		social1.setStackoverflowRank(96);
		social1.setLinkedinUrl("https://www.linkedin.com/");

		List<Connect> followers = new ArrayList<Connect>();
		Connect connectFollowers = new Connect();
		connectFollowers.setConnectName("Spire");
		connectFollowers.setConnectType("LinkedIn");
		connectFollowers.setConnectURL("http://facebook.com");
		connectFollowers.setCreatedBy("Spire");
		connectFollowers.setEmail("abcd@spire.com");
		connectFollowers.setMobileNumber("1234567890");
		connectFollowers.setModifiedBy("Spire");
		followers.add(connectFollowers);
		social1.setFollowers(followers);

		List<Connect> following = new ArrayList<Connect>();
		Connect connectFollowing = new Connect();
		connectFollowing.setConnectName("Spire");
		connectFollowing.setConnectType("LinkedIn");
		connectFollowing.setConnectURL("http://facebook.com");
		connectFollowing.setCreatedBy("Spire");
		connectFollowing.setEmail("abcd@spire.com");
		connectFollowing.setMobileNumber("1234567890");
		connectFollowing.setModifiedBy("Spire");
		following.add(connectFollowing);
		social1.setFollowing(following);

		SocialExt socialExtRequest = new SocialExt();
		socialExtRequest.setStackoverflowBadgeCount(10);
		socialExtRequest.setAskUbuntuUserId("100");
		socialExtRequest.setGithubFollowersCount(90);
		socialExtRequest.setGithubFollowersCount(50);
		socialExtRequest.setGithubFollowingCount(88);
		socialExtRequest.setGithubPublicRepos(78);
		socialExtRequest.setLinkedInConnectionsCount(360);
		socialExtRequest.setOriginalGithubRepoCount(422);
		socialExtRequest.setStackoverflowBadgeCount(58);
		socialExtRequest.setStackoverflowReputationPoints(789);
		socialExtRequest.setStackoverflowUserId("stack-21");
		social1.setSocialExt(socialExtRequest);
		return social1;
	}

	/**
	 * validateBulkGetSocial
	 */
	public static void validateBulkGetSocial(TestData data) {
		
		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();
		
		List<String> socialCreatedCandidateIds = SocialServiceValidation.createBulkSocialProfiles(data);

		List<Social> socialBulkResponse = socialEntityServiceConsumer.getBulkSocialProfiles(socialCreatedCandidateIds);

		for (Social social : socialBulkResponse) {
			Assert.assertTrue(socialCreatedCandidateIds.contains(social.getCandidateId()),
					"Not listing created candidateId in bulk response !!");
		}
	}

	public static void patchSocialProfile(TestData data) {

		String candidateId = IdUtils.generateID("6066", "6444");
		System.out.println("candidateId: " + candidateId);
		Social socialRequest = SocialServiceValidation.createSocialData(candidateId);
		SocialEntityServiceConsumer socialEntityServiceConsumer = new SocialEntityServiceConsumer();
		socialEntityServiceConsumer.createSocialProfile(socialRequest);

		Social patchRequest = new Social();

		if (data.getData().equals("Interests")) {
			System.out.println("Patching Interests");
			Set<String> interests = new HashSet<String>();
			interests.add("READING BOOKS");
			interests.add("WATCHING MOVIES");
			patchRequest.setInterests(interests);
			patchRequest.setInterests(interests);
			socialEntityServiceConsumer.patchSocialProfile(candidateId, patchRequest);

			Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");
			Set<String> responseInterests = socialResponse.getInterests();
			Assert.assertTrue(responseInterests.contains("READING BOOKS"), "Not showing updated Interests !!");
			Assert.assertTrue(responseInterests.contains("WATCHING MOVIES"), "Not showing updated Interests !!");
		}
		if (data.getData().equals("Hobbies")) {
			System.out.println("Patching Hobbies");
			Set<String> hobbies = new HashSet<String>();
			hobbies.add("PLAYING HOCKEY");
			hobbies.add("PLAYING SNOOKER");
			patchRequest.setInterests(hobbies);
			patchRequest.setInterests(hobbies);
			socialEntityServiceConsumer.patchSocialProfile(candidateId, patchRequest);

			Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");
			Set<String> responseHobbies = socialResponse.getInterests();
			Assert.assertTrue(responseHobbies.contains("PLAYING HOCKEY"), "Not showing updated Hobbies !!");
			Assert.assertTrue(responseHobbies.contains("PLAYING SNOOKER"), "Not showing updated Hobbies !!");
		}
		if (data.getData().equals("ProfessionalSummary")) {
			System.out.println("Patching ProfessionalSummary");
			patchRequest.setProfessionalSummary("A seasoned IT Professional who loves to automate tasks");
			socialEntityServiceConsumer.patchSocialProfile(candidateId, patchRequest);

			Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");
			Assert.assertEquals(socialResponse.getProfessionalSummary(), patchRequest.getProfessionalSummary(),
					"Not showing updated ProfessionalSummary !!");
		}
		if (data.getData().equals("LinkedInURL")) {
			System.out.println("Patching LinkedInURL");
			patchRequest.setLinkedinUrl("https://www.linkedin.com/santosh");
			socialEntityServiceConsumer.patchSocialProfile(candidateId, patchRequest);

			Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");
			Assert.assertEquals(socialResponse.getLinkedinUrl(), patchRequest.getLinkedinUrl(),
					"Not showing updated LinkedinUrl !!");
		}
		if (data.getData().equals("LinkedInConnectionCount")) {
			System.out.println("Patching LinkedInConnectionCount");
			SocialExt ext = new SocialExt();
			ext.setLinkedInConnectionsCount(999);
			patchRequest.setSocialExt(ext);
			socialEntityServiceConsumer.patchSocialProfile(candidateId, patchRequest);

			Social socialResponse = socialEntityServiceConsumer.getSocialProfile(candidateId, "full");
			Assert.assertEquals(socialResponse.getSocialExt().getLinkedInConnectionsCount(),
					patchRequest.getSocialExt().getLinkedInConnectionsCount(),
					"Not showing updated LinkedInConnectionCount !!");
		}
	}
}

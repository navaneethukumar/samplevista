package com.spire.crm.hireassimilar.profiles;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.testng.Assert;

import spire.commons.search.response.CandidateSummary;
import spire.commons.search.response.RequisitionSummary;

import com.spire.base.controller.Logging;
import com.spire.base.util.internal.entity.SpireTestObject;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.entity.consumers.HireasEntityServiceConsumer;
import com.spire.crm.restful.entity.consumers.SimilarProfileEntityServiceConsumer;

import crm.pipeline.entities.CollectionEntity;

public class HireAsSimilarProfilesValidation {

	public static void verifyHireAs() {

		HireasEntityServiceConsumer client = new HireasEntityServiceConsumer();

		String candidateID = ProfileHelper.createProfile();

		Response response = client.getHireAs(candidateID);

		if (response.getStatus() == 200) {

			CollectionEntity<RequisitionSummary> hireAsResults = response
					.readEntity(new GenericType<CollectionEntity<RequisitionSummary>>() {
					});

			Assert.assertNotNull(hireAsResults.getItems(),
					"Items null for given candiadte ID");

		} else {
			Assert.fail("Hire As is failed and the status code is : "
					+ response.getStatus());
		}

	}

	public static void verifySimilarProfiles(SpireTestObject spireTestObject,
			TestData testData) {

		SimilarProfileEntityServiceConsumer similarProfile = new SimilarProfileEntityServiceConsumer();

		String candidateID = ProfileHelper.createProfile();
		String similarcandidateID = ProfileHelper.createProfile();

		Response response = similarProfile.getSimilarProfile(candidateID);

		if (response.getStatus() == 200) {

			CollectionEntity<CandidateSummary> similarResults = response
					.readEntity(new GenericType<CollectionEntity<CandidateSummary>>() {
					});

			Assert.assertTrue(similarResults.getItems().size() > 1,
					"No similar profiles found for the canidate : "
							+ candidateID);
			List<CandidateSummary> similarProfilesList = new ArrayList<CandidateSummary>(
					similarResults.getItems());

			validateSimilarProfileResults(similarProfilesList, candidateID,
					similarcandidateID);

		} else if (response.getStatus() == 204) {

		} else {
			Assert.fail("Similar Profiles is failed and the status code is : "
					+ response.getStatus());
		}

	}

	private static void validateSimilarProfileResults(
			List<CandidateSummary> similarProfilesList, String candidateID,
			String similarcandidateID) {

		Boolean candidateIdFlag = false;

		for (CandidateSummary candidateSummary : similarProfilesList) {

			if (candidateSummary.getId().equals(similarcandidateID)) {

				candidateIdFlag = true;

			}

		}

		Assert.assertTrue(candidateIdFlag, "Expcted canidate ID :"
				+ similarcandidateID + " not fount in Similar profiles results");

	}

	public static void verifySimilarProfilesStatus(
			SpireTestObject spireTestObject, TestData testData) {
		SimilarProfileEntityServiceConsumer similarProfile = new SimilarProfileEntityServiceConsumer();

		String candidateID = ProfileHelper.createProfile();
		String similarcandidateID = ProfileHelper.createProfile();
		Logging.log("Profile iD " + candidateID + " and similar profile id "
				+ similarcandidateID);
		Response response = similarProfile.getSimilarProfile(candidateID);

		if (response.getStatus() != 200) {

			Assert.fail("Similar Profiles is failed and the status code is : "
					+ response.getStatus());
		}

	}

}

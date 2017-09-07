package com.spire.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import spire.commons.userservice.bean.CollectionEntity;
import spire.commons.userservice.bean.UserDetailsBean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FindDuplicateUsers {

	public static void main(String[] args) {

		CollectionEntity<UserDetailsBean> userDetailsList = null;
		String filePath = "./src/main/resources/users.json";
		File userDetailsJson = new File(filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			userDetailsList = objectMapper.readValue(userDetailsJson,
					new TypeReference<CollectionEntity<UserDetailsBean>>() {
					});
		} catch (IOException e) {

			e.printStackTrace();
		}

		int count = 0;
		List<UserDetailsBean> userDetails = (List<UserDetailsBean>) userDetailsList
				.getItems();
		for (UserDetailsBean userDetailsBean : userDetails) {
			count = 0;
			for (UserDetailsBean user : userDetails) {

				if (user.getEmail().equals(userDetailsBean.getEmail())) {
					count++;

				}

				if (count > 1) {

					System.out.println(userDetailsBean.getUserId() + ","
							+ userDetailsBean.getEmail());
					break;

				}

			}

		}

	}

}

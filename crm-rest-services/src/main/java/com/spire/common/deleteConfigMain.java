package com.spire.common;

import java.util.Arrays;
import java.util.List;

public class deleteConfigMain {

	public static void main(String[] args) {
		ConfigHelper helper = new ConfigHelper();

		List<String> serviceNames = Arrays.asList("Delete-Script-Test","Delete-Script-Test2");

		for (String serviceName : serviceNames) {
			try {

				helper.deleteCofigService(serviceName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}

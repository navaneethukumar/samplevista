package com.spire.common;

import org.testng.asserts.SoftAssert;

import com.spire.base.controller.Logging;

public class SoftAssertion_ {

	private static SoftAssert softAssert = new SoftAssert();

	public static void assertEquals(Object obj1, Object obj2) throws Exception {
		String actual = String.valueOf(obj1);
		String expected = String.valueOf(obj2);
		softAssert.assertEquals(actual, expected);
		Logging.log("ACTUAL >>" + actual + "\tEXPECTED >>" + expected);
	}

	public static void assertNotNull(Object obj1) throws Exception {
		softAssert.assertNotNull(obj1);
	}
	public static void assertNotNull_(Object obj1,String message) throws Exception {
		softAssert.assertNotNull(obj1, message);
	}

	public static void assertTrue(boolean obj1) throws Exception {
		softAssert.assertTrue(obj1);
	}

	public static void assertTrue(boolean obj1,String msg) {
		softAssert.assertTrue(obj1,msg);
	}

	public static void assertAll() {
		softAssert.assertAll();
	}

	public static void assertFail(String msg) {
		softAssert.fail(msg);
	}
}

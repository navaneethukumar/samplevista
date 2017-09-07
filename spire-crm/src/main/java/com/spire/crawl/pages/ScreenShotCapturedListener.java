//package com.spire.crawl.pages;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
///**
// * 
// * @author Pradeep
// *
// */
//public class ScreenShotCapturedListener implements ITestListener {
//
//	String localPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;
//
//	public void onTestStart(ITestResult result) {
//
//	}
//
//	public void onTestSuccess(ITestResult result) {
//
//	}
//
//	public void onTestFailure(ITestResult result) {
//		takeScreenshot(result);
//	}
//
//	public void onTestSkipped(ITestResult result) {
//		takeScreenshot(result);
//	}
//
//	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//		takeScreenshot(result);
//	}
//
//	public void onStart(ITestContext context) {
//
//	}
//
//	public void onFinish(ITestContext context) {
//
//	}
//
//	/**
//	 * capturing screenshot
//	 * 
//	 * @param result
//	 */
//	public void takeScreenshot(ITestResult result) {
//		File scrFile = ((TakesScreenshot) BasePage.getdriver()).getScreenshotAs(OutputType.FILE);
//		String destPath = localPath + result.getMethod().getMethodName() + ".png";
//		File destPathFile = new File(destPath);
//		try {
//			FileUtils.copyFile(scrFile, destPathFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}

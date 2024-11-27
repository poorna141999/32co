package Utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Utilities.constans.Values;

public class TestListener implements ITestListener {
	public static ExtentReport extent;

	@Override
	public void onTestStart(ITestResult result) {
		extent.testScenarioStart(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		Util.createOutputDirectory();
		Util.readProperty();
		extent = new ExtentReport();
		extent.start();
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.testScenarioEnd();

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if(Values.testfailed) {
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(new RuntimeException("Test Case Failed "+result.getThrowable()));
		}
		extent.testScenarioEnd();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try{
			extent.log("fail", result.getThrowable().getMessage());
		}catch(Exception e){
			System.out.println("FAIL ::: Testcase Failed"+result.getThrowable().getMessage());
		}
		extent.testScenarioEnd();
	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	public static void test() {
		extent = new ExtentReport();
		extent.start();
	}

}

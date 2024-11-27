package Utilities;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Utilities.constans.Values;

public class ExtentReport {

	static ExtentReports extent;
	static int thread = 0;
	static ExtentTest parent;
	static ExtentTest child;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public void start() {
		try {
			thread = thread + 1;
			System.out.println("Called " + thread);
			extent = new ExtentReports();
			ExtentSparkReporter spark = new ExtentSparkReporter(Values.outputDirectory + "/Results.html");
			ExtentSparkReporter sparkFail = new ExtentSparkReporter(Values.outputDirectory+"/FailedResults.html")
					  .filter()
					    .statusFilter()
					    .as(new Status[] { Status.FAIL })
					  .apply();
			spark.config().setReportName("32Co Automation");
			spark.config().setDocumentTitle("32Co Automation Report");
			extent.attachReporter(spark,sparkFail);
			extent.setSystemInfo("URL", Values.frameworkProperty.getProperty("URL",""));
			extent.setSystemInfo("Browser", Values.frameworkProperty.getProperty("Browser","Chrome"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized ExtentTest getTest() {
		return extentTest.get();
	}
	
	 public void datasetStart(String testDataSet) {
			child = getTest().createNode(testDataSet, testDataSet);
			extentTest.set(child);
		}
	  
	  public void datasetEnd() {	
			extentTest.set(parent);			
		}

	public void testScenarioStart(ITestResult result) {
		Values.testfailed = false;
		parent = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(parent);
		parent.assignDevice(result.getTestContext().getName());
		 String[] groups = result.getMethod().getGroups();
		 for(int i=0;i<groups.length;i++) {
			 parent.assignCategory(groups[i]); 
		 }
		System.out.println("Report Started For " + result.getMethod().getMethodName());
	}

	public void log(String status, String msg) {
		switch (status) {
		case "pass":
			getTest().pass(msg);
			break;
		case "info":
			getTest().info(msg);
			break;
		case "fail":
			getTest().fail(msg);
			break;
		case "warn":
			getTest().warning(msg);
			break;
		}
	}

	public void logJson(String json) {
		getTest().info(MarkupHelper.createCodeBlock(json));
	}
	
	public void logJsonFormat(String json) {
		getTest().info(MarkupHelper.createCodeBlock(json,CodeLanguage.JSON));
	}

	public void logXml(String msg, String xml) {
		getTest().info(MarkupHelper.createCodeBlock(xml));
	}

	public void logFailScreenshot(String errMsg, String path) {
		getTest().addScreenCaptureFromPath(path).fail(errMsg,
				MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}

	public void logScreenshot(String path) {
		getTest().addScreenCaptureFromPath(path).info(MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}

	public void logScreenshotbase(String base64) {
		getTest()// .addScreenCaptureFromBase64String(base64)
				.info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
	}

	public static synchronized void testScenarioEnd() {
		extent.flush();
		System.out.println("Report Ended the current test cases");
	}

}

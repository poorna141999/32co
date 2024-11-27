package Utilities.constans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import Utilities.Common;

public class Values extends Common {
	public static WebDriver driver;
	public static int textLoadWaitTime = 10; //in seconds
	public static int elementLoadWaitTime = 10;
	public static int implicitlyWaitTime=10;
	public static int pageLoadWaitTime = 30;
	public static int EmailWaitTime = 20000;// in milisec
	public static int sleepTime = 1000;// in milisec
	
	public static final ArrayList<String> testCaseNames = new ArrayList();
	public static ArrayList<String> testCaseDataSets = new ArrayList();
	public static boolean testCaseExecutionStatus = false;
	public static String outputDirectory;
	public static HashMap<String, String> configData = new HashMap<String, String>();
	public static List<HashMap<String, String>> executionData = new ArrayList<HashMap<String, String>>();
	public static List<HashMap<String, String>> devicesData = new ArrayList<HashMap<String, String>>();
	public static int failureNo;
	public static Properties frameworkProperty;
	public static boolean testfailed = false;
	public static String dentistEmail = "";
	public static String specialistEmail = "";
	public static String designerEmail = "";
	public static String patientEmail = "";
	public static String patientFirstName = "";
	public static String patientLastName = "";
	public static String manufacturerEmail = "";
	public static Date currentDateTime = null;
	public static HashMap<String, String> dentistFirstDetails = new HashMap<String, String>();
	public static HashMap<String, String> dentistFinalDetails = new HashMap<String, String>();
	public static HashMap<String, String> groupDetails = new HashMap<String, String>();
	public static HashMap<String, String> adminDentistLiveRecord = new HashMap<String, String>();
	public  static String eduCoverTitle = "";
	public  static String cpdPoint = "";
	public  static String designerFee = "";
	public  static String specialistFee = "";
	public  static String dentistFee = "";
	
	
	public  static int aboutToBeDueCount = 0;
	public  static int notAcceptedCount = 0;
	public  static int notSuitableReviewPendingCount = 0;
	public  static int overdueCount = 0;
	public  static int pendingLiteSubmissionReviewCount = 0;
	public  static int pendingProSubmissionReviewCount = 0;
	public  static int pendingToManufacturerCount = 0;
	public  static int priorityCount = 0;
	public  static int stlRequiredCount = 0;
	
	public static String  PracticeName = "";
	
	
}

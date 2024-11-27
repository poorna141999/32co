package api.user;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import Utilities.RestAPI;
import Utilities.constans.Values;
import api.AdminAPIModule;
import api.DentistAPIModule;
import api.UserAPIModule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistPatientsPage;
import pages.dentist.DentistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class UserAPITestCases  extends Common{

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistRegisterPage DentistRegisterPage;
	public DentistDashboardPage DentistDashboardPage;
	public DentistPatientsPage DentistPatientsPage;
	public Email email = new Email();

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("API");
		datasets = data.getDataSets(name);
	}

	
	@Test(groups = {"API", "Admin"})
	public void userSignInAndSignOut() throws IOException {
		datasetStart("userSignInAndSignOut");

		UserAPIModule UserAPIModule = new UserAPIModule();
		RestAPI SignOutAPI = new RestAPI("dentist");
		UserAPIModule.userSignOutPost(SignOutAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void getUserProfile() throws IOException {
		datasetStart("getUserProfile");

		UserAPIModule UserAPIModule = new UserAPIModule();	
		RestAPI UserAPI = new RestAPI("dentist");
		UserAPIModule.getUserProfile(UserAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void userPasswordResetRequest() throws IOException {
		datasetStart("userPasswordResetRequest");

		UserAPIModule UserAPIModule = new UserAPIModule();	
		RestAPI UserAPI = new RestAPI("dentist");
		UserAPIModule.userPasswordResetRequest(UserAPI);
	}

}
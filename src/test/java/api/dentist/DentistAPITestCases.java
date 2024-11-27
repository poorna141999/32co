package api.dentist;

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
import api.DentistAPIModule;
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
public class DentistAPITestCases  extends Common{

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	RestAPI dentistAPI = null;

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("API");
		datasets = data.getDataSets(name);
		dentistAPI = new RestAPI("dentist");
	}
	
	@Test(groups = {"API", "dentist"})
	public void createSoloCaseAPI() {
		datasetStart("createSoloCaseAPI");

		DentistAPIModule DentistAPIModule = new DentistAPIModule();
		DentistAPIModule.postCreateSolo(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solo.json");
		DentistAPIModule.postCreateSolo_1(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solo_1.json");

		/*
		 * dentistAPI = new RestAPI("dentist");
		 * DentistAPIModule.postCreateSolo(dentistAPI,
		 * ".\\src\\test\\resources\\json\\dentistapi\\solo2.json");
		 */	
	}
	/*
	 * @Test(groups = {"API", "dentist"}) public void createDuoCaseAPI() {
	 * datasetStart("createDuoCaseAPI");
	 * 
	 * DentistAPIModule DentistAPIModule = new DentistAPIModule();
	 * DentistAPIModule.postCreateDuo(dentistAPI,
	 * ".\\src\\test\\resources\\json\\dentistapi\\duo.json");
	 * 
	 * dentistAPI = new RestAPI("dentist");
	 * DentistAPIModule.postCreateDuo(dentistAPI,
	 * ".\\src\\test\\resources\\json\\dentistapi\\duo2.json");
	 * 
	 * }
	 */
}
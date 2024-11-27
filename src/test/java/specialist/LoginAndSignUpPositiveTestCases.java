package specialist;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;
import pages.specialist.SpecialistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignUpPositiveTestCases  extends Common{
	
	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public SpecialistApplication SpecialistApplication;
	public SpecialistLoginPage SpecialistLoginPage;
	public SpecialistDashboardPage SpecialistDashboardPage;
	public SpecialistRegisterPage SpecialistRegisterPage;
	
	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Specialist");
		datasets = data.getDataSets(name);
	}

	@Test(groups = {"Regression", "SpecialistLoginSignUp","Positive"})
	public void SpecialistLoginToApplication() {

			datasetStart("SpecialistLoginToApplication");
			
			SpecialistApplication = new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistLoginPage.loginToApplication();

			SpecialistApplication.close();

	}
	
	@Test(groups = {"Functional", "SpecialistLoginSignUp","Positive"})
	public void SpecialistSignUpToApplication() {
		data.setColIndex("SpecialistSignUpToApplication");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			SpecialistApplication = new SpecialistApplication(data);
			SpecialistRegisterPage = SpecialistApplication.openSpecialistJoinus();
			SpecialistRegisterPage.registerNewUser(data);
			SpecialistRegisterPage.validateRegister();
			
			SpecialistApplication.close();
			datasetEnd();
		}
	}
	
}

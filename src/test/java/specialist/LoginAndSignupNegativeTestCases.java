package specialist;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;
import pages.specialist.SpecialistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignupNegativeTestCases extends Common{

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
	
	

	@Test(groups = {"Regression", "SpecialistLoginSignUp","Negative"})
	public void SpecialistSignUpMandatoryFieldValidationFirstSection() {

			datasetStart("SpecialistSignUpMandatoryFieldValidationFirstSection");
			
			SpecialistApplication = new SpecialistApplication(data);
			SpecialistRegisterPage = SpecialistApplication.openSpecialistJoinus();
			SpecialistRegisterPage.validateMandatoryFieldFirstSection();
			
			SpecialistApplication.close();

	}
	
	@Test(groups = {"Regression", "SpecialistLoginSignUp","Negative"})
	public void SpecialistSignUpNegativeFieldValidationFirstSection() {

			datasetStart("SpecialistSignUpNegativeFieldValidationFirstSection");
			
			SpecialistApplication = new SpecialistApplication(data);
			SpecialistRegisterPage = SpecialistApplication.openSpecialistJoinus();
			SpecialistRegisterPage.validateNegativeFieldFirstSection();
			
			SpecialistApplication.close();
	}
	
	@Test(groups = {"Regression", "SpecialistLoginSignUp","Negative"})
	@Ignore
	public void SpecialistValidateLoginNegativeTestCase() {
		data.setColIndex("SpecialistValidateLoginNegativeTestCase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			SpecialistApplication = new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistLoginPage.loginNegativeValidation();
			
			SpecialistApplication.close();
			datasetEnd();
		}
	}
}

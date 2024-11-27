package dentist;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignupNegativeTestCases extends Common{

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistDashboardPage DentistHomePage;
	public DentistRegisterPage DentistRegisterPage;

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Dentist");
		datasets = data.getDataSets(name);
	}



	@Test(groups = {"Regression", "Dentist","Negative"})
	public void DenitstSignUpMandatoryFieldValidationFirstSection() {

		datasetStart("DenitstSignUpMandatoryFieldValidationFirstSection");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistRegisterPage = DentistLoginPage.navigateToRegisterPage();
		DentistRegisterPage.clickOnGetStarted();
		DentistRegisterPage.validateMandatoryFieldFirstSection();

		DentistApplication.close();

	}

	@Test(groups = {"Regression", "Dentist","Negative"})
	public void DenitstSignUpNegativeFieldValidationFirstSection() {
		datasetStart("DenitstSignUpNegativeFieldValidationFirstSection");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistRegisterPage = DentistLoginPage.navigateToRegisterPage();
		DentistRegisterPage.clickOnGetStarted();
		DentistRegisterPage.validateNegativeFieldFirstSection();

		DentistApplication.close();
	}

	@Test(groups = {"Regression", "Dentist","Negative"})
	public void DenitstSignUpMandatoryFieldValidationFinalSection() {
		datasetStart("DenitstSignUpMandatoryFieldValidationFinalSection");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistRegisterPage = DentistLoginPage.navigateToRegisterPage();
		DentistRegisterPage.clickOnGetStarted();
		DentistRegisterPage.validateMandatoryFieldFinalSection();

		DentistApplication.close();
	}

	@Test(groups = {"Regression", "Dentist","Negative"})
	public void DenitstValidateLoginNegativeTestCase() {
		data.setColIndex("DenitstValidateLoginNegativeTestCase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistLoginPage.loginNegativeValidation();

			DentistApplication.close();
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Dentist","Negative","Register"})
	public void DentistValidateRegisterExistingEmail() {
		datasetStart("DentistValidateRegisterExistingEmail");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistRegisterPage = DentistLoginPage.navigateToRegisterPage();
		DentistRegisterPage.clickOnGetStarted();
		DentistRegisterPage.registerExistingUser();

		DentistApplication.close();
	}
	
	@Test(groups = {"Regression", "Dentist","ForgotPassword"})
	public void ValidateForgotPasswordNegativeValidation() {
		data.setColIndex("ValidateForgotPasswordNegativeValidation");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistLoginPage.navigateToForgotPasswordPage();
			DentistLoginPage.validateForgotPasswordInvalidEmail(data);
			DentistApplication.close();
			datasetEnd();
		}
	}

}

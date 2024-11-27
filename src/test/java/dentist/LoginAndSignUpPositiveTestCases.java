package dentist;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import pages.admin.AdminApplcation;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminDentistsPage;
import pages.admin.AdminLoginPage;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignUpPositiveTestCases extends Common {

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistRegisterPage DentistRegisterPage;
	public DentistDashboardPage DentistDashboardPage;
	public AdminApplcation AdminApplcation;
	public AdminLoginPage AdminLoginPage;
	public AdminDashboardPage AdminDashboardPage;
	public AdminDentistsPage AdminDentistsPage;
	public Email email = new Email();

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Dentist");
		datasets = data.getDataSets(name);
	}

	/*
	 * @Test(groups = { "Regression", "Dentist", "Positive" }) public void
	 * DenitstLoginLogoutToApplication() {
	 * datasetStart("DenitstLoginLogoutToApplication");
	 * 
	 * DentistApplication = new DentistApplication(data); DentistLoginPage =
	 * DentistApplication.open32CoApplication(); DentistDashboardPage =
	 * DentistLoginPage.loginToApplication();
	 * DentistDashboardPage.logoutApplication(); DentistApplication.close();
	 * 
	 * }
	 */

	@Test(groups = { "Functional", "Dentist", "Positive" })
	public void DenitstSignUpToApplicationAndOnboardToLive() {
		data.setColIndex("DenitstSignUpToApplicationAndOnboardToLive");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			DentistApplication = new DentistApplication(data);
			DentistRegisterPage = DentistApplication.openDentistJoinus();
			// DentistRegisterPage
			// DentistLoginPage.navigateToRegisterPage();
			// DentistRegisterPage.clickOnGetStarted();
			// DentistRegisterPage.clickOnGetStarted();
			DentistRegisterPage.registerNewUser(data);
			DentistRegisterPage.validateRegisterAndGetStarted();
			DentistApplication.close();
			// TODO : failing due to invalid credentials
			// email.verifyDentistEmailAfterRegister(data.get("Email Address"));
			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplicationnewUser();
			// DentistLoginPage.selectInitialDetailsYesFlow();
			DentistDashboardPage.clickOnCompleteYourProfile();
			DentistDashboardPage.validateCompleteProfilePage();
			DentistApplication.close();
			// TODO : add validation to complete profile here
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage = AdminLoginPage.loginToApplication();
			AdminDentistsPage = AdminDashboardPage.navigateToDentistsPage();
			if (AdminDentistsPage.sendEmailInvite()) {
				email.verifyDentistEmailAfterInviteSent(data.get("Email Address"));
				if (AdminDentistsPage.assignPractise()) {
					AdminDentistsPage.onBoardDentist();
					AdminDentistsPage.completePending();
					AdminDentistsPage.verifyDentistLive();
				}
			}
			AdminApplcation.close();
			datasetEnd();
		}
	}

	/*
	 * @Test(groups = {"Functional", "Dentist","Positive"}) public void
	 * DenitstSignUpToApplication2() {
	 * data.setColIndex("DenitstSignUpToApplication2");
	 * 
	 * for (String dataset : datasets) { data.setIndex(dataset);
	 * datasetStart(dataset);
	 * 
	 * DentistApplication = new DentistApplication(data); DentistLoginPage
	 * =DentistApplication.open32CoApplication(); DentistRegisterPage
	 * =DentistLoginPage.navigateToRegisterPage();
	 * DentistRegisterPage.clickOnGetStarted();
	 * DentistRegisterPage.registerNewUser(data);
	 * DentistRegisterPage.validateRegisterAndGetStarted();
	 * DentistApplication.close();
	 * email.verifyDentistEmailAfterRegister(data.get("Email Address"));
	 * //DentistApplication.close(); DentistApplication = new
	 * DentistApplication(data); DentistLoginPage =
	 * DentistApplication.open32CoApplication(); DentistDashboardPage =
	 * DentistLoginPage.loginToApplicationnewUser();
	 * DentistLoginPage.selectInitialDetailsNoFlow(); } DentistApplication.close();
	 * datasetEnd(); }
	 * 
	 * @Test(groups = {"Regression", "Dentist","Positive"}) public void
	 * DenitstValidateUISingUpAndLoginPage() {
	 * datasetStart("DenitstValidateUISingUpAndLoginPage");
	 * 
	 * DentistApplication = new DentistApplication(data); DentistLoginPage =
	 * DentistApplication.open32CoApplication(); DentistRegisterPage =
	 * DentistLoginPage.navigateToRegisterPage();
	 * DentistRegisterPage.validateTheCarouselAndRegisterPageUI();
	 * DentistApplication.close();
	 * 
	 * }
	 * 
	 * 
	 * // TODO: Not ready to join pop up is not coming up during the automation
	 * 
	 * @Test(groups = {"Regression", "Dentist"})
	 * 
	 * @Ignore public void DenitstValidateNotReadyToJoinPopup() {
	 * datasetStart("DenitstValidateNotReadyToJoinPopup");
	 * 
	 * DentistApplication = new DentistApplication(data); DentistLoginPage =
	 * DentistApplication.open32CoApplication(); DentistRegisterPage =
	 * DentistLoginPage.navigateToRegisterPage();
	 * DentistRegisterPage.clickOnGetStarted();
	 * DentistRegisterPage.ValidateNotReadyToJoinPopup();
	 * 
	 * DentistApplication.close();
	 * 
	 * }
	 * 
	 * @Test(groups = {"Regression", "Dentist","ForgotPassword"}) public void
	 * ValidateForgotPasswordFunctionality() {
	 * data.setColIndex("ValidateForgotPasswordFunctionality");
	 * 
	 * for (String dataset : datasets) { data.setIndex(dataset);
	 * datasetStart(dataset);
	 * 
	 * DentistApplication = new DentistApplication(data); DentistLoginPage =
	 * DentistApplication.open32CoApplication();
	 * DentistLoginPage.navigateToForgotPasswordPage();
	 * DentistLoginPage.validateForgotPassword(data); DentistApplication.close();
	 * email.verifyEmailAfterForgotPassword(data.get("Email Address"));
	 * datasetEnd(); } }
	 */

}

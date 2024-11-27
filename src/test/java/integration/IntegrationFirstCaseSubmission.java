package integration;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import Utilities.RestAPI;
import Utilities.constans.CaseStatus;
import Utilities.constans.Values;
import api.DentistAPIModule;
import pages.admin.AdminApplcation;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminDentistsPage;
import pages.admin.AdminLoginPage;
import pages.admin.AdminOperationsPage;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistPatientsPage;
import pages.dentist.DentistRegisterPage;
import pages.designer.DesignerApplication;
import pages.designer.DesignerDashboardPage;
import pages.designer.DesignerLoginPage;
import pages.manufacturer.ManufacturerApplication;
import pages.manufacturer.ManufacturerDashboardPage;
import pages.manufacturer.ManufacturerLoginPage;
import pages.manufacturer.ManufacturerRegisterPage;
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;

@Listeners({ Utilities.TestListener.class })
public class IntegrationFirstCaseSubmission extends Common{
	
	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistRegisterPage DentistRegisterPage;
	public DentistDashboardPage DentistDashboardPage;
	public DentistPatientsPage DentistPatientsPage;
	public AdminApplcation AdminApplcation;
	public AdminOperationsPage AdminOperationsPage;
	public AdminDashboardPage AdminDashboardPage;
	public AdminLoginPage AdminLoginPage;
	public DesignerApplication DesignerApplication;
	public DesignerDashboardPage DesignerDashboardPage;
	public DesignerLoginPage DesignerLoginPage;

	public SpecialistApplication SpecialistApplication;
	public SpecialistDashboardPage SpecialistDashboardPage;
	public SpecialistLoginPage SpecialistLoginPage;

	public ManufacturerApplication ManufacturerApplication;
	public ManufacturerLoginPage ManufacturerLoginPage;
	public ManufacturerDashboardPage ManufacturerDashboardPage;
	public ManufacturerRegisterPage ManufacturerRegisterPage;

	public AdminDentistsPage AdminDentistsPage;

	public Email email = new Email();

	RestAPI dentistAPI = null;
	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Integration");
		datasets = data.getDataSets(name);
	}

	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void SoloFirstCaseSubmission() throws ParseException {
		data.setColIndex("SoloFirstCaseSubmission");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			DentistApplication = new DentistApplication(data);
			DentistRegisterPage =DentistApplication.openDentistJoinus(); 
			//DentistRegisterPage =DentistLoginPage.navigateToRegisterPage();
			DentistRegisterPage.clickOnGetStarted();
			DentistRegisterPage.registerNewUser(data);
			DentistRegisterPage.validateRegisterAndGetStarted();
			DentistApplication.close();
			email.verifyDentistEmailAfterRegister(data.get("Email Address"));
			//DentistApplication.close();
			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplicationnewUser();
			DentistLoginPage.selectInitialDetailsYesFlow();
			DentistDashboardPage.clickOnCompleteYourProfile();
			DentistDashboardPage.validateCompleteProfilePage();

			//TODO : add validation to complete profile here
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =  AdminApplcation.open32CoApplication();
			AdminDashboardPage = AdminLoginPage.loginToApplication();
			AdminDentistsPage =  AdminDashboardPage.navigateToDentistsPage();
			if(AdminDentistsPage.sendEmailInvite()) {
				email.verifyDentistEmailAfterInviteSent(data.get("Email Address"));
				if(AdminDentistsPage.assignPractise()) {
					AdminDentistsPage.onBoardDentist();
					AdminDentistsPage.completePending();
					AdminDentistsPage.verifyDentistLive();
				}
			}
			AdminApplcation.close();

			dentistAPI = new RestAPI("dentistnew"); 
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminDashboardPage.validateCasesUnAssigned();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.assignDesignerForSoloCase();
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data);
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage =DesignerLoginPage.loginToApplication();
//			DesignerDashboardPage.validateAcceptCaseSolo(); 
//			DesignerApplication.close();

		}
	}

	

	@Test (priority=6,groups = {"Regression", "Integration","duo"})
	public void DuoFirstCaseSubmission() {
		data.setColIndex("DuoFirstCaseSubmission");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			DentistApplication = new DentistApplication(data);
			DentistRegisterPage =DentistApplication.openDentistJoinus(); 
			//DentistRegisterPage =DentistLoginPage.navigateToRegisterPage();
			DentistRegisterPage.clickOnGetStarted();
			DentistRegisterPage.registerNewUser(data);
			DentistRegisterPage.validateRegisterAndGetStarted();
			DentistApplication.close();
			email.verifyDentistEmailAfterRegister(data.get("Email Address"));
			//DentistApplication.close();
			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplicationnewUser();
			DentistLoginPage.selectInitialDetailsYesFlow();
			DentistDashboardPage.clickOnCompleteYourProfile();
			DentistDashboardPage.validateCompleteProfilePage();

			//TODO : add validation to complete profile here
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =  AdminApplcation.open32CoApplication();
			AdminDashboardPage = AdminLoginPage.loginToApplication();
			AdminDentistsPage =  AdminDashboardPage.navigateToDentistsPage();
			if(AdminDentistsPage.sendEmailInvite()) {
				email.verifyDentistEmailAfterInviteSent(data.get("Email Address"));
				if(AdminDentistsPage.assignPractise()) {
					AdminDentistsPage.onBoardDentist();
					AdminDentistsPage.completePending();
					AdminDentistsPage.verifyDentistLive();
				}
			}
			AdminApplcation.close();


			dentistAPI = new RestAPI("dentistnew");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage =  AdminLoginPage.loginToApplication();
			AdminOperationsPage =  AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.assignDesignerForDuoCase();
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.validateAcceptCaseDuo();
		//	DesignerApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
		//	SpecialistApplication.close();

		}
	}


}

package admin;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import Utilities.constans.Values;
import pages.admin.AdminApplcation;
import pages.admin.AdminLoginPage;
import pages.admin.AdminPracticesPage;
import pages.admin.AdminReleaseNotesPage;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminDentistsPage;
import pages.admin.AdminEducationContentPage;
import pages.admin.AdminGroupManagementPage;
import pages.admin.AdminThirdPartiesPage;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistRegisterPage;
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;
import pages.dentist.DentistEducationPage;

@Listeners({ Utilities.TestListener.class })
public class AdminEducationContentTestCases  extends Common{

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public AdminApplcation AdminApplcation;
	public AdminLoginPage AdminLoginPage;
	public AdminDashboardPage AdminDashboardPage;
	public AdminReleaseNotesPage AdminReleaseNotesPage;
	public AdminPracticesPage AdminPracticesPage;
	public AdminThirdPartiesPage AdminThirdPartiesPage;
	public AdminGroupManagementPage AdminGroupManagementPage;
	public AdminDentistsPage AdminDentistsPage;
	public AdminEducationContentPage AdminEducationContentPage;

	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistRegisterPage DentistRegisterPage;
	public DentistDashboardPage DentistDashboardPage;
	public DentistEducationPage DentistEducationPage;
	
	public SpecialistApplication SpecialistApplication;
	public SpecialistDashboardPage SpecialistDashboardPage;
	public SpecialistLoginPage SpecialistLoginPage;

	public Email email = new Email();
	
	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Admin");
		datasets = data.getDataSets(name);
	}

	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminValidateEducationContentUI() {
		datasetStart("AdminValidateEducationContentUI");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.validateEducationPageUI();
		AdminEducationContentPage.validateSearchFunctionality();
		AdminDashboardPage.logoutApplication();
		AdminApplcation.close();
		datasetEnd();

	}


	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminCreateEducationContent() {
		datasetStart("AdminCreateEducationContent");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
		AdminDashboardPage.logoutApplication();
		AdminApplcation.close();
		datasetEnd();

	}
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminUpdateEducationContent() {
		datasetStart("AdminUpdateEducationContent");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
		AdminEducationContentPage.validateEdit();
		AdminApplcation.close();
		datasetEnd();

	}
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminPreviewAndReloadEducationContent() {
		datasetStart("AdminPreviewAndReloadEducationContent");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
		AdminEducationContentPage.validateReloadNotionContent();
		AdminEducationContentPage.validatePreviewButton();
		AdminApplcation.close();
		datasetEnd();

	}
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminContentValidateTriggerAllDentist() {
		datasetStart("AdminContentValidateTriggerAllDentist");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
		//Values.eduCoverTitle = "Auto Cover IwbUfR";
		AdminEducationContentPage.validateTriggerAllDentist();
		AdminApplcation.close();
		
		DentistApplication DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistEducationPage = DentistDashboardPage.NavigateToEducationPage();
		DentistEducationPage.viewEducationContent();
		DentistEducationPage.validateEducationContent();
		
		email.verifyEmailAfterContentCourse(Values.frameworkProperty.getProperty("dentist.email"));
		
		datasetEnd();

	}
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminContentValidateTriggerOneDentist() {
		datasetStart("AdminContentValidateTriggerOneDentist");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
	//	Values.eduCoverTitle ="Auto Cover fVSqLD";
		AdminEducationContentPage.validateTriggerOneDentist();
		AdminApplcation.close();
		datasetEnd();
		
		DentistApplication DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistEducationPage = DentistDashboardPage.NavigateToEducationPage();
		DentistEducationPage.viewEducationContent();
		DentistEducationPage.validateEducationContent();
		
		email.verifyEmailAfterContentCourse(Values.frameworkProperty.getProperty("dentist.email"));
		

	}
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminCreateEducationContentCaseStudy() {
		datasetStart("AdminCreateEducationContentCaseStudy");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createCaseStudy();
		AdminEducationContentPage.validateTriggerOneDentist();
		AdminApplcation.close();
		datasetEnd();
		
		DentistApplication DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistEducationPage = DentistDashboardPage.NavigateToEducationPage();
		DentistEducationPage.viewEducationContent();
		DentistEducationPage.validateEducationContent();
		
		email.verifyEmailAfterContentCourse(Values.frameworkProperty.getProperty("dentist.email"));
		

	}
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminCreateEducationContentCourseMaterial() {
		datasetStart("AdminCreateEducationContentCourseMaterial");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createCourseMaterial();
		AdminEducationContentPage.validateTriggerOneDentist();
		AdminApplcation.close();
		datasetEnd();
		
		DentistApplication DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistEducationPage = DentistDashboardPage.NavigateToEducationPage();
		DentistEducationPage.viewEducationContent();
		DentistEducationPage.validateEducationContent();
		
		email.verifyEmailAfterContentCourse(Values.frameworkProperty.getProperty("dentist.email"));
		

	}
	
	
	
	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminContentValidateTriggerOneGroup() {
		datasetStart("AdminContentValidateTriggerOneGroup");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
		AdminEducationContentPage.validateTriggerOneGroup();
		
		AdminApplcation.close();
		
		DentistApplication DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistEducationPage = DentistDashboardPage.NavigateToEducationPage();
		DentistEducationPage.viewEducationContent();
		DentistEducationPage.validateEducationContent();
		DentistApplication.close();
		email.verifyEmailAfterContentCourse(Values.frameworkProperty.getProperty("dentist.email"));
		
		datasetEnd();

	}
	

	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminContentValidateTriggerMultipleGroup() {
		datasetStart("AdminContentValidateTriggerMultipleGroup");
		
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createNewContent();
		AdminEducationContentPage.validateTriggerMultipleGroup();		
		AdminApplcation.close();
		
		datasetEnd();
	}

	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminEducationContentCourseMaterialSpecialist() {
		datasetStart("AdminEducationContentCourseMaterialSpecialist");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminEducationContentPage = AdminDashboardPage.navigateToEducationContent();
		AdminEducationContentPage.createCourseMaterial();
		AdminEducationContentPage.validateTriggerOneDentist();
		AdminApplcation.close();
		datasetEnd();
		
		SpecialistApplication =  new SpecialistApplication(data);
		SpecialistLoginPage = SpecialistApplication.open32CoApplication();
		SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
		SpecialistDashboardPage.navigateToPatientsPage();
		

	}



}

package admin;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.admin.AdminApplcation;
import pages.admin.AdminLoginPage;
import pages.admin.AdminOutboundBillsPage;
import pages.admin.AdminPaymentsPage;
import pages.admin.AdminPracticesPage;
import pages.admin.AdminReleaseNotesPage;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminDentistsPage;
import pages.admin.AdminDiscountsPage;
import pages.admin.AdminGroupManagementPage;
import pages.admin.AdminThirdPartiesPage;

@Listeners({ Utilities.TestListener.class })
public class AdminTestCases  extends Common{

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
	public AdminOutboundBillsPage AdminOutboundBillsPage;
	public AdminPaymentsPage AdminPaymentsPage;
	public AdminDiscountsPage AdminDiscountsPage;
	

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Admin");
		datasets = data.getDataSets(name);
	}

	@Test(groups = {"Regression", "Admin","Poistive"})
	public void AdminLoginToApplication() {
		datasetStart("AdminLoginToApplication");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage = AdminLoginPage.loginToApplication();
		AdminDashboardPage.logoutApplication();
		AdminApplcation.close();

	}

	@Test(groups = {"Regression", "Admin","Negative"})
	public void AdminValidateLoginNegativeTestCase() {
		data.setColIndex("AdminValidateLoginNegativeTestCase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminLoginPage.loginNegativeValidation();

			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test(groups = {"Functional", "Admin","Positive"})
	public void AdminValidateReleaseNotes() {
		data.setColIndex("AdminValidateReleaseNotes");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage= AdminLoginPage.loginToApplication();
			AdminReleaseNotesPage = AdminDashboardPage.navigateToReleaseNotes();
			AdminReleaseNotesPage.createNewReleaseNotes();
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test(groups = {"Regression", "Admin","Negative"})
	public void AdminReleaseNotesMandatoryValidation() {
		datasetStart("AdminReleaseNotesMandatoryValidation");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminReleaseNotesPage = AdminDashboardPage.navigateToReleaseNotes();
		AdminReleaseNotesPage.validateReleaseNotes();
		AdminReleaseNotesPage.validateMandatoryFields();
		AdminApplcation.close();
	}

	@Test(groups = {"Regression", "Admin","Positive"})
	public void AdminPracticesUIValidation() {
		datasetStart("AdminPracticesUIValidation");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminPracticesPage = AdminDashboardPage.navigateToPracticesPage();
		AdminPracticesPage.validatePracticesPageUI();

		AdminApplcation.close();
	}

	@Test(groups = {"Regression", "Admin","Negative"})
	public void AdminNewPracticesmandatoryFieldValidation() {
		datasetStart("AdminNewPracticesmandatoryFieldValidation");

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminPracticesPage = AdminDashboardPage.navigateToPracticesPage();
		AdminPracticesPage.validateMandatoryFieldValidation();

		AdminApplcation.close();
	}

	@Test(groups = {"Regression", "Admin","Positive","Third Parties"})
	public void AdminThirdPartiesUIFieldValidation() {
		datasetStart("AdminThirdPartiesDesignerUIFieldValidation");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Designers", "DESIGNER");
		AdminThirdPartiesPage.validateDesignersPageUI();		
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Manufacturers", "MANUFACTURER");
		AdminThirdPartiesPage.validateManufacturersPageUI();
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Specialists", "SPECIALIST");
		AdminThirdPartiesPage.validateSpecialistPageUI();
		AdminApplcation.close();
	}

	@Test(groups = {"Functional", "Admin","Positive","Third Parties"})
	public void AdminThirdPartiesDesignerActiveInactive() {
		datasetStart("AdminThirdPartiesDesignerActiveInactive");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Designers", "DESIGNER");
		AdminThirdPartiesPage.changeStatus("inactive");
		AdminThirdPartiesPage.changeStatus("active");
		AdminApplcation.close();
	}
	
	@Test(groups = {"Functional", "Admin","Positive","Third Parties"})
	public void AdminThirdPartiesDesignerUpdateFees() {
		datasetStart("AdminThirdPartiesDesignerActiveInactive");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Designers", "DESIGNER");
		AdminThirdPartiesPage.updateFees();
		AdminApplcation.close();
	}
	

	@Test(groups = {"Functional", "Admin","Positive","Third Parties"})
	public void AdminThirdPartiesSpecialistUpdateFees() {
		datasetStart("AdminThirdPartiesDesignerActiveInactive");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Specialists", "SPECIALIST");
		AdminThirdPartiesPage.updateFees();
		AdminApplcation.close();
	}

	@Test(groups = {"Functional", "Admin","Positive","Third Parties"})
	public void AdminThirdPartiesSpecialistActiveInactive() {
		datasetStart("AdminThirdPartiesSpecialistActiveInactive");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminThirdPartiesPage = AdminDashboardPage.navigateToThirdPartiesPage("Specialists", "SPECIALIST");
		AdminThirdPartiesPage.changeStatus("inactive");
		AdminThirdPartiesPage.changeStatus("active");
		AdminApplcation.close();
	}

	@Test(groups = {"Regression", "Admin","Positive","Groups"})
	public void AdminGroupsValidateUIAndSearchFunc() {
		datasetStart("AdminGroupsValidateUIAndSearchFunc");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.validateGroupUI();
		AdminGroupManagementPage.validateSeachFunctionality();
		AdminApplcation.close();
	}
	
	@Test(groups = {"Regression", "Admin","Positive","Groups"})
	public void AdminGroupsSearchDentistFuncInCreateGroupPage() {
		datasetStart("AdminGroupsSearchDentistFuncInCreateGroupPage");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminDentistsPage =  AdminDashboardPage.navigateToDentistsPage();
		AdminDentistsPage.retrieveLiveRecordDetailsFromDentistTable();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.searchFunctionalityInCreateGroupPage();
		AdminApplcation.close();
	}

	@Test(groups = {"Functional", "Admin","Positive","Groups"})
	public void createGroupsWithAllUsers() {
		datasetStart("createGroupsWithAllUsers");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.createNewGroupWithAllDentist();
		AdminGroupManagementPage.validateNewGroupDisplayedInTable();
		AdminApplcation.close();
	}

	//TODO : Add validation to validate the Dentists displayed in group Info page
	@Test(groups = {"Functional", "Admin","Positive","Groups"})
	public void createGroupsWithFewDentist() {
		datasetStart("createGroupsWithFewDentist");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.createNewGroupWithFewDentist();
		AdminGroupManagementPage.EditTheGroupAndValidate();
		AdminApplcation.close();
	}

	@Test(groups = {"Regression", "Admin","Negative","Groups"})
	public void createGroupsValidateMandatoryFields() {
		datasetStart("createGroupsValidateMandatoryFields");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.validateMandatoryFields();
		AdminApplcation.close();
	}
	
	
	@Test(groups = {"Regression", "Admin","Negative","Groups"})
	public void archiveGroup() {
		datasetStart("archiveGroup");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.validateArchiveGroups();
		AdminApplcation.close();
	}
	
	@Test(groups = {"Regression", "Admin","Negative","Groups"})
	public void validateDuplicateGroups() {
		datasetStart("validateDuplicateGroups");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminGroupManagementPage =  AdminDashboardPage.navigateToGroupsPage();
		AdminGroupManagementPage.validateDuplicateGroups();
		AdminApplcation.close();
	}


	@Test(groups = {"Functional", "Admin","Positive","AdminDentist"})
	public void DentistArchiveAndUnarchiveUsers() {
		datasetStart("DentistArchiveAndUnarchiveUsers");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminDentistsPage =  AdminDashboardPage.navigateToDentistsPage();
		AdminDentistsPage.validateArchiveAndUnarchiveDentist("Applied");
		AdminDentistsPage.validateArchiveAndUnarchiveDentist("Invited");
		AdminDentistsPage.validateArchiveAndUnarchiveDentist("Finalisation");
		AdminDentistsPage.validateArchiveAndUnarchiveDentist("Pending package");
		AdminApplcation.close();
	}

	@Test(groups = {"Functional", "Admin","Positive","AdminDentist"})
	public void DentistValidateCheckboxSelectedIsDeselectedAfterChangingDropdownValue() {
		datasetStart("DentistValidateCheckboxSelectedIsDeselectedAfterChangingDropdownValue");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminDentistsPage =  AdminDashboardPage.navigateToDentistsPage();
		AdminDentistsPage.validateByChangingDropDownValue("Applied","Invited");

		AdminApplcation.close();
	}


	@Test(groups = {"Functional", "Admin","Positive","Practice"})
	public void DentistCreateNewPractices() {
		datasetStart("DentistCreateNewPractices");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminPracticesPage =  AdminDashboardPage.navigateToPracticesPage();
		AdminPracticesPage.createNewPractice();	
		AdminApplcation.close();
	}
	
	@Test(groups = {"Regression", "Admin","Positive","Dashboard"})
	public void ValidateAdminDashboard() {
		datasetStart("ValidateAdminDashboard");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminDashboardPage.validateDashboardNumbers();			
		AdminApplcation.close();
	}
	
}

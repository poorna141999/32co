package integration;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import Utilities.RestAPI;
import Utilities.constans.CaseStatus;
import Utilities.constans.Design_Status;
import Utilities.constans.Values;
import api.DentistAPIModule;
import pages.admin.AdminApplcation;
import pages.admin.AdminDashboardPage;
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
import pages.designer.DesignerRegisterPage;
import pages.manufacturer.ManufacturerApplication;
import pages.manufacturer.ManufacturerDashboardPage;
import pages.manufacturer.ManufacturerLoginPage;
import pages.manufacturer.ManufacturerRegisterPage;
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;

@Listeners({ Utilities.TestListener.class })
public class IntegrationTestCases  extends Common{

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

	public Email email = new Email();

	RestAPI dentistAPI = null;

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Integration");
		datasets = data.getDataSets(name);
	}

	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void SoloCasePositiveFlow() throws ParseException {
		data.setColIndex("SoloCasePositiveFlow");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist"); DentistAPIModule DentistAPIModule = new
					DentistAPIModule();
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
			DesignerDashboardPage.validateAcceptCaseSolo(); 
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesign.getStatus()); 
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage =DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminDashboardPage.validateCasesReadyForReviewSolo();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("1",
					"Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();

			DentistApplication DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1",
					"Review Design");
			DentistPatientsPage.createPatientProposal();
			DentistApplication.close();

			email.verifyEmailAfterPateintProposal(Values.frameworkProperty.getProperty(
					"patient.email"),"Approved");


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAligners();
			DentistApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.Approved.getStatus()); 
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.uploadSTLs();
			DesignerApplication.close();


			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminDashboardPage.validateCasesNeedSTLFiles();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.ReviewSTLFiles.getStatus()); 
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.sendToManufacturer();
			AdminApplcation.close();

			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerLoginPage = ManufacturerApplication.open32CoApplication();
			ManufacturerDashboardPage = 	ManufacturerLoginPage.loginToApplication();
			ManufacturerDashboardPage.selectRecordFromSpecifiedSection("NEW ORDER");
			ManufacturerDashboardPage.AddOrEditShippingLink();
			ManufacturerApplication.close();


			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.WithManufacturer.getStatus());
			AdminOperationsPage.selectCheckBoxFirstPatientFromTable();
			AdminOperationsPage.MarkMaterialReceived();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.With32Co.getStatus());
			AdminOperationsPage.selectCheckBoxFirstPatientFromTable();
			AdminOperationsPage.confirmShipped();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.InTreatment.getStatus());
			AdminApplcation.close();

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startTreatmentMonitoring();
			DentistPatientsPage.enterDetailsInTreatmentCheckInSettingsPage();
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Sent To Patient");
			DentistApplication.close();
			
			email.verifyEmailAfterTreatmentCheckIn(Values.frameworkProperty.getProperty(
					"patient.email"));
			
			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Not Viewed");
			DentistPatientsPage.ViewTreatmentCheckIns("1");
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Viewed");
			DentistPatientsPage.validateUpdateTreatmentCheckIn();
			DentistPatientsPage.validateCancelTreatmentCheckIn();
			DentistPatientsPage.ValidateTreatmentCheckInsCancelled("1");
			datasetEnd();
		}
	}

	@Test (priority=2,groups = {"Regression", "Integration","solo"})
	public void SoloCaseValidateMandatoryError() throws ParseException {
		data.setColIndex("SoloCaseValidateMandatoryError");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist"); DentistAPIModule DentistAPIModule = new
					DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.assignDesignerForSoloCase();
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage = DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.validateAcceptCaseSolo(); 
			DesignerDashboardPage.navigateToDashboardMenu();
			DesignerDashboardPage.validateMandatoryError();
			DesignerDashboardPage.navigateToDashboardMenu();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("1",
					"Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();

			DentistApplication DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1","Review Design");
			DentistPatientsPage.createPatientProposalValidateError();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1","Review Design");
			DentistPatientsPage.createPatientProposal();
			DentistApplication.close();

			email.verifyEmailAfterPateintProposal(Values.frameworkProperty.getProperty(
					"patient.email"),"Approved");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAligners();
			DentistApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.Approved.getStatus()); 
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.uploadSTLs();
			DesignerApplication.close();


			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.ReviewSTLFiles.getStatus()); 
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.sendToManufacturer();
			AdminApplcation.close();

			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerLoginPage = ManufacturerApplication.open32CoApplication();
			ManufacturerDashboardPage = 	ManufacturerLoginPage.loginToApplication();
			ManufacturerDashboardPage.selectRecordFromSpecifiedSection("NEW ORDER");
			ManufacturerDashboardPage.AddOrEditShippingLink();
			ManufacturerApplication.close();


			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.WithManufacturer.getStatus());
			AdminOperationsPage.selectCheckBoxFirstPatientFromTable();
			AdminOperationsPage.MarkMaterialReceived();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.With32Co.getStatus());
			AdminOperationsPage.selectCheckBoxFirstPatientFromTable();
			AdminOperationsPage.confirmShippedValidateMandatoryError();
			AdminApplcation.close();

			datasetEnd();
		}
	}

	@Test (priority=3,groups = {"Regression", "Integration","solo"})
	public void SoloCaseValidateAdminRevisionRequestFlow() throws ParseException {
		data.setColIndex("SoloCaseValidateAdminRevisionRequestFlow");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist"); DentistAPIModule DentistAPIModule = new
					DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.assignDesignerForSoloCase(); 
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage = DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.validateAcceptCaseSolo(); 
			DesignerApplication.close();
			
			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage = DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Review Design", "-", "No");
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("1",
					"Review Design"); 
			AdminOperationsPage.revisionRequest();
			AdminApplcation.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesign.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Revision Requested", "-", "Yes");
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication();
			DesignerDashboardPage =DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.selectPatientFromDashboard(4);
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Revision Requested");
			DesignerDashboardPage.submitDesign();
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Awaiting Approval");
			DesignerDashboardPage.submitDesign();
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("3", "Awaiting Approval");
			DesignerDashboardPage.submitDesign();
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("4", "Awaiting Approval");
			DesignerDashboardPage.submitDesign();
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("5", "Awaiting Approval");
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("2",
					"Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDentistApproval.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Approved", "Review Design", "No");
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("3", "Review Design", "-", "No");
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("4", "Review Design", "-", "No");
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("5", "Review Design", "-", "No");
			AdminApplcation.close();
			
			DentistApplication DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1",
					"Review Design");
			DentistPatientsPage.createPatientProposal();
			DentistApplication.close();

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAligners();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Approved.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Approved", "Approved", "No");
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test (priority=4,groups = {"Regression", "Integration","solo"})
	public void SoloCaseValidateDentistRevisionRequestFlow() throws ParseException {
		data.setColIndex("SoloCaseValidateDentistRevisionRequestFlow");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist"); DentistAPIModule DentistAPIModule = new
					DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.assignDesignerForSoloCase();
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage = DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.validateAcceptCaseSolo(); 
			DesignerApplication.close();
			
			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication(); 
			DesignerDashboardPage = DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Review Design", "-", "No");
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("1",
					"Review Design"); 			
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDentistApproval.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(false);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Approved", "Review Design", "No");
			AdminApplcation.close();

			DentistApplication DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );

			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1",
					"Review Design");
			DentistPatientsPage.revisionRequest();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("SUBMITTED",
					Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndValidateStatus("1", "SOLO", "Revision Requested", "Yes");
			DentistApplication.close();
			
			//TODO : validate Admin and designer status 
			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication();
			DesignerDashboardPage =DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.selectPatientFromDashboard(4);
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Revision Requested");
			DesignerDashboardPage.submitDesign();
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("2",
					"Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDentistApproval.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(false);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Approved", "Review Design", "No");
			AdminApplcation.close();

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndValidateStatus("2", "SOLO", "Review Design", "No");
			DentistPatientsPage.createPatientProposal();
			DentistApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDentistApproval.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(false);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Approved", "Review Design", "No");
			AdminApplcation.close();
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication();
			DesignerDashboardPage =DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.validateStatusOfPatients(patientName, Design_Status.AWAITING_DESIGN_APPROVAL.toString(), "AWAITING REVIEW");
			DesignerDashboardPage.selectRecordFromCurrentSection(Design_Status.AWAITING_DESIGN_APPROVAL.toString());
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Awaiting Approval");
			DesignerApplication.close();
			
			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAligners();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Approved.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToSoloTreatmentDesignsAndValidateStatus("2", "Approved", "Approved", "No");
			AdminApplcation.close();

			datasetEnd();
		}
	}

	@Test (priority=5,groups = {"Regression", "Integration","solo"})
	public void SoloCaseNegativeFlowDesignerReject() {
		data.setColIndex("SoloCaseNegativeFlowDesignerReject");

		for (String dataset : datasets) {			
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage =
					AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =
					AdminLoginPage.loginToApplication();
			AdminOperationsPage =
					AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.assignDesignerForSoloCase(); 
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.validateRejectCase();
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.ReAssign.getStatus());
			datasetEnd();
		}
	}

	@Test (priority=6,groups = {"Regression", "Integration","duo"})
	public void DuoCasePositiveFlowEndToEnd() {
		data.setColIndex("DuoCasePositiveFlowEndToEnd");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.AwaitingSpecialistAcceptance.getStatus());
			AdminApplcation.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.AwaitingInstruction.getStatus());
			AdminApplcation.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateCaseSuitable();
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.AwaitingDesign.getStatus());
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();


			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.submitAdvice("1");
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data); AdminLoginPage
			=AdminApplcation.open32CoApplication(); AdminDashboardPage
			=AdminLoginPage.loginToApplication(); AdminOperationsPage
			=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("1","Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();

			DentistApplication DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1",
					"Review Design");
			DentistPatientsPage.createPatientProposal();
			DentistApplication.close();

			email.verifyEmailAfterPateintProposal(Values.frameworkProperty.getProperty(
					"patient.email"),"Approved");


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAligners();
			DentistApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.Approved.getStatus()); 
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.uploadSTLs();
			DesignerApplication.close();


			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.ReviewSTLFiles.getStatus()); 
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.sendToManufacturer();
			AdminApplcation.close();

			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerLoginPage = ManufacturerApplication.open32CoApplication();
			ManufacturerDashboardPage = 	ManufacturerLoginPage.loginToApplication();
			ManufacturerDashboardPage.selectRecordFromSpecifiedSection("NEW ORDER");
			ManufacturerDashboardPage.AddOrEditShippingLink();
			ManufacturerApplication.close();


			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage =AdminLoginPage.loginToApplication();
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.WithManufacturer.getStatus());
			AdminOperationsPage.selectCheckBoxFirstPatientFromTable();
			AdminOperationsPage.MarkMaterialReceived();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.With32Co.getStatus());
			AdminOperationsPage.selectCheckBoxFirstPatientFromTable();
			AdminOperationsPage.confirmShipped();
			AdminOperationsPage.validateStatusOfCurrentRecordInSpecifcDropdown(CaseStatus.InTreatment.getStatus());
			AdminApplcation.close();
			
			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startTreatmentMonitoring();
			DentistPatientsPage.enterDetailsInTreatmentCheckInSettingsPage();
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Sent To Patient");
			DentistApplication.close();
			
			email.verifyEmailAfterTreatmentCheckIn(Values.frameworkProperty.getProperty(
					"patient.email"));
			
			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Not Viewed");
			DentistPatientsPage.ViewTreatmentCheckIns("1");
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Viewed");
			DentistPatientsPage.validateUpdateTreatmentCheckIn();
			DentistPatientsPage.validateCancelTreatmentCheckIn();
			DentistPatientsPage.ValidateTreatmentCheckInsCancelled("1");
			datasetEnd();

		}
	}

	@Test (priority=7,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowDesignerReject() {
		data.setColIndex("DuoCaseNegativeFlowDesignerReject");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerDashboardPage.validateRejectCase();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.ReAssign.getStatus());
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test (priority=8,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowSpecialistReject() {
		data.setColIndex("DuoCaseNegativeFlowDesignerReject");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateRejectCase();
			SpecialistApplication.close();
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.Assign.getStatus());
			AdminApplcation.close();

			datasetEnd();
		}
	}

	@Test (priority=9,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowSpecialistPutOnHold() {
		data.setColIndex("DuoCaseNegativeFlowSpecialistPutOnHold");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validatePutOnHold("Require clarification from the Dentist");
			SpecialistApplication.close();
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.OnHold.getStatus());
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test (priority=10,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowSpecialistCaseNotSuitable() {
		data.setColIndex("DuoCaseNegativeFlowSpecialistCaseNotSuitable");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateCaseNotSuitable();//TODO update the success message and tabs details after case not suitalbe
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminDashboardPage.validateCasesNotSuitable();
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.NotSuitablePending.getStatus()); //TODO update status with valid one
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test (priority=11,groups = {"Regression", "Integration","duo"})
	public void DuoCasePositiveFlowSpecialistCaseSuitableSaveAsDraft() {
		data.setColIndex("DuoCasePositiveFlowSpecialistCaseSuitableSaveAsDraft");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateCaseSaveAsDraft();
			SpecialistApplication.close();

			datasetEnd();
		}
	}

	@Test (priority=12,groups = {"Regression", "Integration","duo"})
	public void DuoCaseSpecialistValidateMandatoryErrorCaseNotSuitable() {
		data.setColIndex("DuoCaseSpecialistValidateMandatoryErrorCaseNotSuitable");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateMandatoryCaseSuitableForm();
			SpecialistApplication.close();

			datasetEnd();
		}
	}

	@Test (priority=13,groups = {"Regression", "Integration","duo"})
	public void DuoCaseSpecialistValidateMandatoryErrorCaseSuitable() {
		data.setColIndex("DuoCaseSpecialistValidateMandatoryErrorCaseSuitable");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateMandatoryCaseSuitableForm();
			SpecialistApplication.close();

			datasetEnd();
		}
	}

	@Test (priority=14,groups = {"Regression", "Integration","solo"})
	public void ValidateSaveAsDraftSoloCaseInAdmin() {
		datasetStart("ValidateSaveAsDraftSoloCaseInAdmin");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistPatientsPage = DentistDashboardPage.startSoloCase();
		DentistPatientsPage.validateSaveAsDraftSolo();
		DentistApplication.close();

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage =  AdminLoginPage.loginToApplication();
		AdminOperationsPage =  AdminDashboardPage.navigateToOperationsPage();
		AdminOperationsPage.searchAndValidateDraft();
		AdminApplcation.close();
	}

	@Test (priority=15,groups = {"Regression", "Integration","duo"})
	public void ValidateSaveAsDraftDuoCaseInAdmin() {
		datasetStart("ValidateSaveAsDraftDuoCaseInAdmin");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistPatientsPage = DentistDashboardPage.startDuoCase();
		DentistPatientsPage.validateSaveAsDraftDuo();
		DentistApplication.close();

		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage =  AdminLoginPage.loginToApplication();
		AdminOperationsPage =  AdminDashboardPage.navigateToOperationsPage();
		AdminOperationsPage.searchAndValidateDraft();
		AdminApplcation.close();
	}

	@Test (priority=16,groups = {"Regression", "Integration","duo"})
	public void DuoCaseValidateAdminRevisionRequestFlow() {
		data.setColIndex("DuoCaseValidateAdminRevisionRequestFlow");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateCaseSuitable();
			SpecialistApplication.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.submitAdvice("1");
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data); AdminLoginPage
			=AdminApplcation.open32CoApplication(); AdminDashboardPage
			=AdminLoginPage.loginToApplication(); AdminOperationsPage
			=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "Review Design","Approved", "-", "No");
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("1","Review Design"); 
			AdminOperationsPage.revisionRequest();
			AdminApplcation.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesign.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "Revision Requested","Approved", "-", "Yes");
			AdminApplcation.close();

			DesignerApplication = new DesignerApplication(data); 
			DesignerLoginPage =DesignerApplication.open32CoApplication();
			DesignerDashboardPage =DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.selectPatientFromDashboard(4);
			DesignerDashboardPage.navigateToSoloTreatmentDesignsAndValidateStatus("1", "Revision Requested");
			DesignerDashboardPage.submitDesign();
			DesignerApplication.close();
			
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.submitAdviceAgainSecond();
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "Revision Requested","Approved", "-", "Yes");
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("2", "Review Design","Approved", "-", "No");
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("2",
					"Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();
			
			DentistApplication DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentDesignsAndSelectDesgin("1",
					"Review Design");
			DentistPatientsPage.createPatientProposal();
			DentistApplication.close();

			email.verifyEmailAfterPateintProposal(Values.frameworkProperty.getProperty(
					"patient.email"),"Approved");


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAligners();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Approved.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "Revision Requested","Approved", "-", "Yes");
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("2", "Approved","Approved", "Approved", "No");
			AdminApplcation.close();

			datasetEnd();
		}
	}

	@Test (priority=17,groups = {"Regression", "Integration","duo"})
	public void DuoCaseValidateSpecialistRevisionRequestFlow() {
		data.setColIndex("DuoCaseValidateSpecialistRevisionRequestFlow");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.AwaitingSpecialistAcceptance.getStatus());
			AdminApplcation.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.AwaitingInstruction.getStatus());
			AdminApplcation.close();

			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateCaseSuitable();
			SpecialistApplication.close();

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.AwaitingDesign.getStatus());
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitNewDesign();
			DesignerApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingAdvice.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "-","Review Design", "-", "No");
			AdminApplcation.close();


			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.selectRecordFromDashboard(3, Values.patientFirstName + " " + Values.patientLastName);
			SpecialistDashboardPage.navigateToTreatmentProposalsAndSelectDesgin("1");
			SpecialistDashboardPage.revisionRequest();
			SpecialistDashboardPage.navigateToPatientsPage();
			SpecialistDashboardPage.selectRecordFromSpecifiedSection("AWAITING_DESIGN",
					Values.patientFirstName + " "+Values.patientLastName );
			SpecialistDashboardPage.navigateToTreatmentDesignsAndValidateStatus("1", "DUO", "Revision Requested", "Yes");
			SpecialistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesign.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "-","Revision Requested", "-", "Yes");
			AdminApplcation.close();

			DesignerApplication  = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerDashboardPage =  DesignerLoginPage.loginToApplication();
			DesignerDashboardPage.submitSecondDesign();
			DesignerApplication.close();
			
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
	
			SpecialistDashboardPage.submitAdvice("2");
			SpecialistDashboardPage.navigateToPatientsPage();
			SpecialistDashboardPage.selectRecordFromSpecifiedSection("APPROVED",
					Values.patientFirstName + " "+Values.patientLastName );
			SpecialistDashboardPage.navigateToTreatmentDesignsAndValidateStatus("2", "DUO", "Approved", "No");
			SpecialistApplication.close();


			AdminApplcation = new AdminApplcation(data); AdminLoginPage
			=AdminApplcation.open32CoApplication(); AdminDashboardPage
			=AdminLoginPage.loginToApplication(); AdminOperationsPage
			=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					ReviewDesign.getStatus());
			AdminOperationsPage.verifyExclamationForFirstRecord(true);
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "-","Revision Requested", "-", "Yes");
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("2", "Review Design","Approved", "-", "No");
			AdminOperationsPage.navigateToTreatmentDesignsAndSelectDesign("2","Review Design"); 
			AdminOperationsPage.sendToDentist();
			AdminApplcation.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication();
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDentistApproval.getStatus());
			AdminOperationsPage.selectFirstPatientFromTable();
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("1", "-","Revision Requested", "-", "Yes");
			AdminOperationsPage.navigateToDuoTreatmentDesignsAndValidateStatus("2", "Approved","Approved", "Review Design", "No");
			AdminApplcation.close();

			datasetEnd();
		}
	}

	
	@Test (priority=9,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowSpecialistPutOnHoldRequireClarificationFromTheDentist() {
		data.setColIndex("DuoCaseNegativeFlowSpecialistPutOnHoldRequireClarificationFromTheDentist");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validatePutOnHold("Require clarification from the Dentist");
			SpecialistApplication.close();
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.OnHold.getStatus());
			AdminApplcation.close();
			datasetEnd();
		}
	}
	

	@Test (priority=9,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowSpecialistPutOnHoldWaitingForFilesFromDentist() {
		data.setColIndex("DuoCaseNegativeFlowSpecialistPutOnHoldWaitingForFilesFromDentist");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validatePutOnHold("Waiting for files from dentist");
			SpecialistApplication.close();
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.OnHold.getStatus());
			AdminApplcation.close();
			datasetEnd();
		}
	}
	
	

	@Test (priority=9,groups = {"Regression", "Integration","duo"})
	public void DuoCaseNegativeFlowSpecialistPutOnHoldPatientRequiresProcedureFirst() {
		data.setColIndex("DuoCaseNegativeFlowSpecialistPutOnHoldPatientRequiresProcedureFirst");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			dentistAPI = new RestAPI("dentist");
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
			DesignerApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validateAcceptCase();
			SpecialistApplication.close();
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.validatePutOnHold("Patient requires a procedure first");
			SpecialistApplication.close();
			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage =AdminApplcation.open32CoApplication(); 
			AdminDashboardPage =AdminLoginPage.loginToApplication(); 
			AdminOperationsPage =AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.OnHold.getStatus());
			AdminApplcation.close();
			
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			
			SpecialistApplication =  new SpecialistApplication(data);
			SpecialistLoginPage = SpecialistApplication.open32CoApplication();
			SpecialistDashboardPage = SpecialistLoginPage.loginToApplication();
			SpecialistDashboardPage.navigateToPatientsPage();
			SpecialistDashboardPage.validateStatusOfPatients(patientName, "ON_HOLD", "On Hold");
			SpecialistDashboardPage.searchAndSelectPatientInAllSection(patientName);
			SpecialistDashboardPage.sendChatMessage("dentist");
			datasetEnd();
		}
	}
}
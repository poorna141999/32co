package integration;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import Utilities.RestAPI;
import Utilities.constans.CaseStatus;
import Utilities.constans.Values;
import api.AdminAPIModule;
import api.DentistAPIModule;
import api.DesignerAPIModule;
import api.ManufacturerAPIModule;
import api.SpecialistAPIModule;
import api.UserAPIModule;
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
public class IntegrationRefinementTestCases  extends Common{

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

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Refinement");
		datasets = data.getDataSets(name);
	}

	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void RefinementSolotoDuoWithSTL() throws ParseException {
		data.setColIndex("RefinementSolotoDuoWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSolo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus());
			AdminOperationsPage.assignDesignerForDuoCase(false);
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
			datasetEnd();
		}
	}
	
	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void RefinementSolotoDuoWithoutSTL() throws ParseException {
		data.setColIndex("RefinementSolotoDuoWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSolo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus());
			AdminOperationsPage.assignDesignerForDuoCase(true);
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
			datasetEnd();
		}
	}
	
	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void RefinementSolotoSoloWithSTL() throws ParseException {
		data.setColIndex("RefinementSolotoSoloWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSolo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesignerAcceptance.getStatus()); 
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

			datasetEnd();
		}
	}
	
	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void RefinementSolotoSoloWithoutSTL() throws ParseException {
		data.setColIndex("RefinementSolotoSoloWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSolo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus()); 
			AdminOperationsPage.refinementAdminUploadStls();
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

			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void RefinementDuotoDuoWithSTL() {
		data.setColIndex("RefinementDuotoDuoWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesignerAcceptance.getStatus());
			AdminOperationsPage.assignDesignerForDuoCase(false);
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
			
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void RefinementDuotoDuoWithoutSTL() {
		data.setColIndex("RefinementDuotoDuoWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus());
			AdminOperationsPage.refinementAdminUploadStls();
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
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void RefinementDuotoSoloWithoutSTL() {
		data.setColIndex("RefinementDuotoSoloWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus()); 
			AdminOperationsPage.refinementAdminUploadStls();
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

			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void RefinementDuotoSoloWithSTL() {
		data.setColIndex("RefinementDuotoSoloWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuo(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					AwaitingDesignerAcceptance.getStatus()); 
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

			datasetEnd();
	
		}
	}

	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void InActiveRefinementSolotoDuoWithSTL() throws ParseException {
		data.setColIndex("InActiveRefinementSolotoDuoWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");
			
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSoloRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus());
			AdminApplcation.close();
			
		}
	}
	
	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void InActiveRefinementSolotoDuoWithoutSTL() throws ParseException {
		data.setColIndex("InActiveRefinementSolotoDuoWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSoloRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus());
			AdminOperationsPage.refinementAdminUploadStls();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus());
			AdminApplcation.close();
			
			
			datasetEnd();
		}
	}
	
	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void InActiveRefinementSolotoSoloWithSTL() throws ParseException {
		data.setColIndex("InActiveRefinementSolotoSoloWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");

			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSoloRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus()); 
			AdminApplcation.close();
			
			datasetEnd();
		}
	}
	
	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void InActiveRefinementSolotoSoloWithoutSTL() throws ParseException {
		data.setColIndex("InActiveRefinementSolotoSoloWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");

			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignSoloRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));

			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus()); 
			AdminOperationsPage.refinementAdminUploadStls();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus());
			AdminApplcation.close();
		
			datasetEnd();
		}
	}
	
	public void InActiveRefinementDuotoDuoWithSTL() {
		data.setColIndex("InActiveRefinementDuotoDuoWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuoRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus());
			AdminApplcation.close();
			
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void InActiveRefinementDuotoDuoWithoutSTL() {
		data.setColIndex("InActiveRefinementDuotoDuoWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuoRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementDuo();
			DentistPatientsPage.createRefinementDuoCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus());
			AdminOperationsPage.refinementAdminUploadStls();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus());
			AdminApplcation.close();
			
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void InActiveRefinementDuotoSoloWithoutSTL() {
		data.setColIndex("InActiveRefinementDuotoSoloWithoutSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuoRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					UploadSTLs.getStatus()); 
			AdminOperationsPage.refinementAdminUploadStls();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus()); 
			AdminApplcation.close();

			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Integration","duo"})
	public void InActiveRefinementDuotoSoloWithSTL() {
		data.setColIndex("InActiveRefinementDuotoSoloWithSTL");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			RestAPI adminAPI = new RestAPI("admin");
			UserAPIModule UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "Active");

			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			AdminAPIModule.putAdminSubmissionStls(adminAPI, "."+data.get("STLSPATH"));			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.postAdminSubmissionAssignDuoRefinement(adminAPI);

			//Designer accepts and Designer Submits new Design
			RestAPI designerAPI = new RestAPI("designer");
			DesignerAPIModule DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.acceptOrRejectCase(designerAPI, "ACCEPTED");
			DesignerAPIModule.updateSTl3DUrl(designerAPI);
			
			RestAPI specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule SpecialistAPIModule = new SpecialistAPIModule();
			SpecialistAPIModule.acceptOrRejectCase(specialistAPI, "ACCEPTED");
			SpecialistAPIModule.caseSuitable(specialistAPI, "."+data.get("CASE_SUITABLE"));
			
			designerAPI = new RestAPI("designer");
			DesignerAPIModule.submitNewDesign(designerAPI, "."+data.get("DESIGN_PATH"));
			
			specialistAPI = new RestAPI("specialist");
			SpecialistAPIModule.submitAdvice(specialistAPI, "."+data.get("ADVICE_PATH"));
			
			
			//Admin Send to Dentist
			AdminAPIModule.putAdminApproveDesignSendToDentist(adminAPI);
			AdminAPIModule.getMySkuId(adminAPI);
			//Denstist Create patient Poposal	
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.submitPatienProposalPost(dentistAPI, "."+data.get("PATIENT_PROPOSAL"));

			//Dentsit Create Order Alligner
			dentistAPI = new RestAPI("dentist");
			DentistAPIModule.dentistOrderClearAligners(dentistAPI);

			designerAPI = new RestAPI("designer");
			DesignerAPIModule = new DesignerAPIModule();			
			DesignerAPIModule.uploadSTls(designerAPI, "."+data.get("UPLOAD_STLS"));
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.sendToManufacturerPut(adminAPI);
			
			RestAPI manufacturerAPI = new RestAPI("manufacturer");
			ManufacturerAPIModule ManufacturerAPIModule = new ManufacturerAPIModule();	
			ManufacturerAPIModule.moveToinProgressAndAddShipppingLinkPut(manufacturerAPI);
			
			adminAPI = new RestAPI("admin");
			AdminAPIModule.markMaterialRecieved(adminAPI);
			AdminAPIModule.confirmShipping(adminAPI);
			
			adminAPI = new RestAPI("admin");
			UserAPIModule = new UserAPIModule();
			UserAPIModule.validateUsersStatusAndUpdate(adminAPI, "InActive");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.startRefinementSolo();
			DentistPatientsPage.createRefinementSoloCase();
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication(); 
			AdminOperationsPage=AdminDashboardPage.navigateToOperationsPage();
			AdminOperationsPage.validateStatusOfCurrentRecordInAllDropdown(CaseStatus.
					Assign.getStatus()); 
			AdminApplcation.close();

			datasetEnd();
	
		}
	}

}
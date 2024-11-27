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
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;

@Listeners({ Utilities.TestListener.class })
public class IntegrationAPITestCases  extends Common{

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
	public Email email = new Email();

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("API");
		datasets = data.getDataSets(name);
	}

	@Test (priority=1,groups = {"Regression", "Integration","solo"})
	public void SoloCaseAPIEnd2EndFlow() throws ParseException {
		data.setColIndex("SoloCaseAPIEnd2EndFlow");

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
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Viewed");
			DentistPatientsPage.validateUpdateTreatmentCheckIn();
			DentistPatientsPage.validateCancelTreatmentCheckIn();
			DentistPatientsPage.ValidateTreatmentCheckInsCancelled("1");
			DentistApplication.close();
			
			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			datasetEnd();
		}
	}

	@Test(groups = {"Regression", "Integration","duo"})
	public void DuoCaseAPIEndToEndFlow() {
		data.setColIndex("DuoCaseAPIEndToEndFlow");

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
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("IN_TREATMENT", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToTreatmentCheckInPage();
			DentistPatientsPage.ValidateTreatmentCheckInsStatus("1", "Viewed");
			DentistPatientsPage.validateUpdateTreatmentCheckIn();
			DentistPatientsPage.validateCancelTreatmentCheckIn();
			DentistPatientsPage.ValidateTreatmentCheckInsCancelled("1");
			datasetEnd();
		}
	}


}
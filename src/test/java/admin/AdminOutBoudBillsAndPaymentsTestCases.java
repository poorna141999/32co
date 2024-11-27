package admin;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.RestAPI;
import api.AdminAPIModule;
import api.DentistAPIModule;
import api.DesignerAPIModule;
import api.ManufacturerAPIModule;
import pages.admin.AdminApplcation;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminDentistsPage;
import pages.admin.AdminDiscountsPage;
import pages.admin.AdminGroupManagementPage;
import pages.admin.AdminLoginPage;
import pages.admin.AdminOperationsPage;
import pages.admin.AdminOutboundBillsPage;
import pages.admin.AdminPaymentsPage;
import pages.admin.AdminPracticesPage;
import pages.admin.AdminReleaseNotesPage;
import pages.admin.AdminThirdPartiesPage;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistPatientsPage;
import pages.dentist.DentistRegisterPage;
import pages.designer.DesignerApplication;
import pages.designer.DesignerDashboardPage;
import pages.designer.DesignerLoginPage;
import pages.specialist.SpecialistApplication;
import pages.specialist.SpecialistDashboardPage;
import pages.specialist.SpecialistLoginPage;


@Listeners({ Utilities.TestListener.class })
public class AdminOutBoudBillsAndPaymentsTestCases  extends Common{


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

	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistRegisterPage DentistRegisterPage;
	public DentistDashboardPage DentistDashboardPage;
	public DentistPatientsPage DentistPatientsPage;
	public AdminOperationsPage AdminOperationsPage;
	public DesignerApplication DesignerApplication;
	public DesignerDashboardPage DesignerDashboardPage;
	public DesignerLoginPage DesignerLoginPage;

	public SpecialistApplication SpecialistApplication;
	public SpecialistDashboardPage SpecialistDashboardPage;
	public SpecialistLoginPage SpecialistLoginPage;


	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Admin");
		datasets = data.getDataSets(name);
	}




	@Test(groups = {"Functional", "Admin","Positive","OutBoudBills"})
	public void AdminValidateOutBoudBillsUI() {
		datasetStart("AdminValidateOutBoudBillsUI");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminOutboundBillsPage =  AdminDashboardPage.navigateToOutBoundBills();
		AdminOutboundBillsPage.validateOutBoudBillsUI();
		AdminApplcation.close();
	}

	@Test(groups = {"Functional", "Admin","Positive","OutBoudBills"})
	public void AdminOutBoundBillsValidateSearchAndFilter() {
		datasetStart("AdminOutBoundBillsValidateSearchAndFilter");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminOutboundBillsPage =  AdminDashboardPage.navigateToOutBoundBills();
		AdminOutboundBillsPage.validateSearchAndFilter();
		AdminApplcation.close();
	}

	//TODO: Has Applciation issue
	@Test(groups = {"Functional", "Admin","Positive","OutBoudBills"})
	public void AdminOutBoundBillsDownloadCsv() {
		datasetStart("AdminOutBoundBillsDownloadCsv");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminOutboundBillsPage =  AdminDashboardPage.navigateToOutBoundBills();
		AdminOutboundBillsPage.validateDownloadCsv();
		AdminApplcation.close();
	}


	@Test(groups = {"Functional", "Admin","Positive","OutBoudBills"})
	public void AdminOutBoundBillsIntegrationSolo() {
		data.setColIndex("AdminOutBoundBillsIntegrationSolo");

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

			adminAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(adminAPI);

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage= AdminLoginPage.loginToApplication();
			AdminOutboundBillsPage =  AdminDashboardPage.navigateToOutBoundBills();
			AdminOutboundBillsPage.validateFees("solo");
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test(groups = {"Functional", "Admin","Positive","OutBoudBills"})
	public void AdminOutBoundBillsIntegrationDuo() {
		data.setColIndex("AdminOutBoundBillsIntegrationDuo");

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

			adminAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(adminAPI);

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage= AdminLoginPage.loginToApplication();
			AdminOutboundBillsPage =  AdminDashboardPage.navigateToOutBoundBills();
			AdminOutboundBillsPage.validateFees("duo");
			AdminApplcation.close();
			datasetEnd();
		}
	}
	
	@Test(groups = {"Functional", "Admin","Positive","Payments"})
	public void AdminValidatePaymentsUI() {
		datasetStart("AdminValidatePaymentsUI");
		AdminApplcation = new AdminApplcation(data);
		AdminLoginPage = AdminApplcation.open32CoApplication();
		AdminDashboardPage= AdminLoginPage.loginToApplication();
		AdminPaymentsPage=  AdminDashboardPage.navigateToPayments();
		AdminPaymentsPage.validatePaymentsUI();
		AdminApplcation.close();
	}
	
	@Test(groups = {"Functional", "Admin","Positive","Payments"})
	public void AdminPaymentsIntegrationSolo() {
		data.setColIndex("AdminPaymentsIntegrationSolo");

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

			adminAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(adminAPI);

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage= AdminLoginPage.loginToApplication();
			AdminPaymentsPage =  AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			AdminApplcation.close();
			datasetEnd();
		}
	}

	@Test(groups = {"Functional", "Admin","Positive","OutBoudBills"})
	public void AdminOutPaymentsIntegrationDuo() {
		data.setColIndex("AdminOutPaymentsIntegrationDuo");

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

			adminAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(adminAPI);

			AdminApplcation = new AdminApplcation(data);
			AdminLoginPage = AdminApplcation.open32CoApplication();
			AdminDashboardPage= AdminLoginPage.loginToApplication();
			AdminPaymentsPage =  AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			AdminApplcation.close();
			datasetEnd();
		}
	}
	
}

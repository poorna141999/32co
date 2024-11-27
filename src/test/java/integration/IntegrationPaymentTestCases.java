package integration;

import java.io.IOException;
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
import Utilities.constans.Constants;
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
import pages.admin.AdminPaymentsPage;
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
public class IntegrationPaymentTestCases  extends Common{

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
	public AdminPaymentsPage AdminPaymentsPage;

	public SpecialistApplication SpecialistApplication;
	public SpecialistDashboardPage SpecialistDashboardPage;
	public SpecialistLoginPage SpecialistLoginPage;
	public Email email = new Email();

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Integration");
		datasets = data.getDataSets(name);
	}



	@Test(groups = {"API", "Admin"})
	public void SoloSKUPriceValidationNoRefinementWithAttachment() throws IOException {
		data.setColIndex("SoloSKUPriceValidationNoRefinementWithAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_NO_REFINEMENT_NAME,Constants.SKU_NO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("solo", true, 16);
			
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_NO_REFINEMENT_NAME, Constants.SKU_NO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	
	
	@Test(groups = {"API", "Admin"})
	public void SoloSKUPriceValidationNoRefinementNoAttachment() throws IOException {
		data.setColIndex("SoloSKUPriceValidationNoRefinementNoAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_NO_REFINEMENT_NAME,Constants.SKU_NO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("solo", false, 16);
			
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_NO_REFINEMENT_NAME, Constants.SKU_NO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	
	

	@Test(groups = {"API", "Admin"})
	public void SoloSKUPriceValidationOneRefinementWithAttachment() throws IOException {
		data.setColIndex("SoloSKUPriceValidationOneRefinementWithAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_ONE_REFINEMENT_NAME,Constants.SKU_ONE_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_one_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("solo", true, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("solo", true, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1));
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_ONE_REFINEMENT_NAME, Constants.SKU_ONE_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	

	@Test(groups = {"API", "Admin"})
	public void SoloSKUPriceValidationOneRefinementNoAttachment() throws IOException {
		data.setColIndex("SoloSKUPriceValidationOneRefinementNoAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_ONE_REFINEMENT_NAME,Constants.SKU_ONE_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_one_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("solo", false, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("solo", false, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1));
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_ONE_REFINEMENT_NAME, Constants.SKU_ONE_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	

	@Test(groups = {"API", "Admin"})
	public void SoloSKUPriceValidationTwoRefinementNoAttachment() throws IOException {
		data.setColIndex("SoloSKUPriceValidationTwoRefinementNoAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_TWO_REFINEMENT_NAME,Constants.SKU_TWO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_two_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("solo", false, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("solo", false, 16);
			String amountRef2 =  AdminAPIModule.calculateSKUAmountTwoRefinement("solo", false, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1)+ Integer.parseInt(amountRef2));
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_TWO_REFINEMENT_NAME, Constants.SKU_TWO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	
	@Test(groups = {"API", "Admin"})
	public void SoloSKUPriceValidationTwoRefinementWithAttachment() throws IOException {
		data.setColIndex("SoloSKUPriceValidationTwoRefinementWithAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_TWO_REFINEMENT_NAME,Constants.SKU_TWO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_two_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("solo", false, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("solo", true, 16);
			String amountRef2 =  AdminAPIModule.calculateSKUAmountTwoRefinement("solo", true, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1)+ Integer.parseInt(amountRef2));
			//Create Solo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateSolo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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

			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_TWO_REFINEMENT_NAME, Constants.SKU_TWO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	

	@Test(groups = {"API", "Admin"})
	public void DuoSKUPriceValidationNoRefinementWithAttachment() throws IOException {
		data.setColIndex("DuoSKUPriceValidationNoRefinementWithAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_NO_REFINEMENT_NAME,Constants.SKU_NO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("duo", true, 16);
			
			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_NO_REFINEMENT_NAME, Constants.SKU_NO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}
	

	@Test(groups = {"API", "Admin"})
	public void DuoSKUPriceValidationNoRefinementNoAttachment() throws IOException {
		data.setColIndex("DuoSKUPriceValidationNoRefinementNoAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_NO_REFINEMENT_NAME,Constants.SKU_NO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);
			
			String amount =  AdminAPIModule.calculateSKUAmount("duo", false, 16);
			
			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_NO_REFINEMENT_NAME, Constants.SKU_NO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}


	@Test(groups = {"API", "Admin"})
	public void DuoSKUPriceValidationOneRefinementNoAttachment() throws IOException {
		data.setColIndex("DuoSKUPriceValidationOneRefinementNoAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_ONE_REFINEMENT_NAME,Constants.SKU_ONE_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);

			String amount =  AdminAPIModule.calculateSKUAmount("duo", false, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("duo", false, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1));
			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_ONE_REFINEMENT_NAME, Constants.SKU_ONE_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}

	

	@Test(groups = {"API", "Admin"})
	public void DuoSKUPriceValidationTwoRefinementNoAttachment() throws IOException {
		data.setColIndex("DuoSKUPriceValidationTwoRefinementNoAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_TWO_REFINEMENT_NAME,Constants.SKU_TWO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);

			String amount =  AdminAPIModule.calculateSKUAmount("duo", false, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("duo", false, 16);
			String amountRef2 =  AdminAPIModule.calculateSKUAmountTwoRefinement("duo", false, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1)+ Integer.parseInt(amountRef2));
			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_TWO_REFINEMENT_NAME, Constants.SKU_TWO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}


	@Test(groups = {"API", "Admin"})
	public void DuoSKUPriceValidationTwoRefinementWithAttachment() throws IOException {
		data.setColIndex("DuoSKUPriceValidationTwoRefinementWithAttachment");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			AdminAPIModule AdminAPIModule = new AdminAPIModule();
			
			RestAPI admAPI = new RestAPI("admin");
			String skuid = AdminAPIModule.validateSKUExists(admAPI,Constants.SKU_TWO_REFINEMENT_NAME,Constants.SKU_TWO_REFINEMENT_DESC);
			if(skuid==null) {
				admAPI = new RestAPI("admin");
				skuid = AdminAPIModule.CreateSKU(admAPI, "./src/test/resources/json/manufacturer/sku_no_ref.json");
			}
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getSKUPriceDetails(admAPI, skuid);
			
			admAPI = new RestAPI("admin");
			AdminAPIModule.getDesignerSpecialistFeeDetails(admAPI);

			String amount =  AdminAPIModule.calculateSKUAmount("duo", true, 16);
			String amountRef1 =  AdminAPIModule.calculateSKUAmountOneRefinement("duo", true, 16);
			String amountRef2 =  AdminAPIModule.calculateSKUAmountTwoRefinement("duo", true, 16);
			amount = String.valueOf(Integer.parseInt(amount) + Integer.parseInt(amountRef1)+ Integer.parseInt(amountRef2));
			//Create Duo Case
			RestAPI dentistAPI = new RestAPI("dentist");
			DentistAPIModule DentistAPIModule = new DentistAPIModule();
			DentistAPIModule.postCreateDuo(dentistAPI,"."+data.get("JSONPATH"));
			RestAPI adminAPI = new RestAPI("admin");

			//Admin upload STLs and assign designer
			AdminAPIModule = new AdminAPIModule();
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


			DentistApplication = new DentistApplication(data);
			DentistLoginPage =  DentistApplication.open32CoApplication();
			DentistDashboardPage =  DentistLoginPage.loginToApplication();
			DentistPatientsPage =  DentistDashboardPage.NavigateToPatientsPage();
			DentistPatientsPage.selectRecordFromSpecifiedSection("REVIEW_DESIGN", Values.patientFirstName + " "+Values.patientLastName );
			DentistPatientsPage.navigateToPatientProposal();
			DentistPatientsPage.orderClearAlignersAndValidateAmount(Constants.SKU_TWO_REFINEMENT_NAME, Constants.SKU_TWO_REFINEMENT_DESC,amount);
			DentistApplication.close();
			
			AdminApplcation = new AdminApplcation(data); 
			AdminLoginPage=AdminApplcation.open32CoApplication(); 
			AdminDashboardPage=AdminLoginPage.loginToApplication();
			AdminPaymentsPage=AdminDashboardPage.navigateToPayments();
			AdminPaymentsPage.validateFees();
			datasetEnd();
		}
	}

}
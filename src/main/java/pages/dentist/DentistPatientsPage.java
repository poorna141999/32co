package pages.dentist;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Values;

public class DentistPatientsPage extends Page{

	@FindBy(id = "patientDetail_firstName")
	private WebElement txtFirstName;

	@FindBy(id = "patientDetail_lastName")
	private WebElement txtLastName;

	@FindBy(id = "patientDetail_dob")
	private WebElement txtDOB;

	@FindBy(id = "patientDetail_email")
	private WebElement txtEmail;

	@FindBy(id = "//input[contains(@placeholder,'phone number')]")
	private WebElement txtMobileNumber;

	@FindBy(id = "practice")
	private WebElement lstClinicName;

	@FindBy(id = "dentalAndMedicalFit")
	private WebElement lstDentalAndMedicalFit;

	@FindBy(xpath = "//div[@aria-describedby='txnObjective_help']")
	private WebElement mlstTreatmentPlanObjectives;

	@FindBy(id = "furtherInstructions")
	private WebElement txtFurtherInstructions;

	@FindBy(id = "archesUnderConsideration")
	private WebElement rbArchesToTreat;

	@FindBy(id = "spacing_typeOfSpacing")
	private WebElement rbSpacingCrowding;

	@FindBy(id = "crowding_resolveUpper_expand")
	private WebElement rbCrowdingResolveUpperExpand;

	@FindBy(id = "crowding_resolveUpper_procline")
	private WebElement rbCrowdingResolveUpperProcline;

	@FindBy(id = "crowding_resolveUpper_iprAnterior")
	private WebElement rbCrowdingResolveUpperIprAnterior;

	@FindBy(id = "crowding_resolveUpper_iprPosterior")
	private WebElement rbCrowdingResolveUpperIprPosterior;

	@FindBy(id = "crowding_resolveLower_expand")
	private WebElement rbCrowdingResolveLowerExpand;

	@FindBy(id = "crowding_resolveLower_procline")
	private WebElement rbCrowdingResolveLowerProcline;

	@FindBy(id = "crowding_resolveLower_iprAnterior")
	private WebElement rbCrowdingResolveLowerIprAnterior;

	@FindBy(id = "crowding_resolveLower_iprPosterior")
	private WebElement rbCrowdingResolveLowerIprPosterior;

	@FindBy(id = "overBite_typeOfOverBite")
	private WebElement rbOverBite;

	@FindBy(id = "overBite_additionalDetailsWhenCorrectBite")
	private WebElement txtOverBiteAdditionalDetails;

	@FindBy(xpath = "//span[text()='Bite Ramps']")
	private WebElement weBiteRamps;

	@FindBy(id = "biteRamps_typeOfBiteRamps")
	private WebElement rbBiteRampsType;

	@FindBy(id = "biteRamps_additionalDetailsWhenPlaceBiteRamps")
	private WebElement txtBiteRampsTypeAdditionalDetails;

	@FindBy(id = "overjet")
	private WebElement rbOverjet;

	@FindBy(id = "anteriorPosteriorRelationship_maintainCurrent")
	private WebElement cbAnteriorPosteriorMaintainCurrent;

	@FindBy(id = "anteriorPosteriorRelationship_correctionToClass")
	private WebElement cbAnteriorPosteriorCorrectionClass;

	@FindBy(id = "anteriorPosteriorRelationship_improveCanineAndMolarRelationship")
	private WebElement cbAnteriorPosteriorImproveCanineMolarRel;

	@FindBy(id = "anteriorPosteriorRelationship_additionalDetails")
	private WebElement txtAnteriorPosteriorAdditionalDetails;

	@FindBy(id = "anteriorPosteriorRelationship_toothMovementOptions")
	private WebElement cbToothMovementOptions;

	@FindBy(id = "anteriorPosteriorRelationship_precisionCuts")
	private WebElement rbPrecisionCuts;

	@FindBy(id = "anteriorPosteriorRelationship_buttonCutOuts")
	private WebElement rbButtonCutouts;

	@FindBy(xpath = "//span[text()='Posterior Crossbite']")
	private WebElement wePosteriorCrossbite;

	@FindBy(id = "posteriorCrossBite")
	private WebElement rbPosteriorCrossBite;

	@FindBy(id = "posteriorCrossBiteDetail")
	private WebElement txtPosteriorCrossBiteAdditionalDetails;

	@FindBy(id = "midline_upper")
	private WebElement rbMidlineUpper;

	@FindBy(id = "midline_lower")
	private WebElement rbMidlineLower;

	@FindBy(id = "midline_additionalDetails")
	private WebElement txtMidlineAdditionalDetails;

	@FindBy(xpath = "//span[text()='Extractions']")
	private WebElement weExtractions;


	//div[text()='Extract the following teeth']/following-sibling::div//span[text()='UR8']/following-sibling::label//input

	@FindBy(id = "extractions_additionalDetails")
	private WebElement txtExtractionsAdditionalDetails;

	@FindBy(id = "attachments_typeOfAttachments")
	private WebElement rbAttachments;

	@FindBy(id = "attachments_additonalDetailsWhenTeethShouldNotHaveAttachments")
	private WebElement txtAttachementsDetails;

	@FindBy(id = "ipr_typeOfIPR")
	private WebElement rbTypeOfIPR;

	@FindBy(id = "ipr_additonalDetailsWhenTeethShouldNotHaveIPR")
	private WebElement txtIPRDetails;



	@FindBy(id = "phasingPreference")
	private WebElement rbPhasingPreference;

	@FindBy(id = "phasingPreferenceDetails")
	private WebElement txtPhasingPreferenceDetails;

	@FindBy(xpath = "//span[text()='Tooth Movement Restrictions']")
	private WebElement weToothMovementRestrictions;

	//div[text()='Restrict Movement on the following teeth']/following-sibling::div//span[text()='UR8']/following-sibling::label//input

	@FindBy(id = "toothMovementRestrictions_additionalDetails")
	private WebElement txtMovementRestrictionsAdditionalDetails;

	@FindBy(xpath = "//span[text()='Passive Aligners']")
	private WebElement wePassiveAligners;

	@FindBy(id = "passiveAligners")
	private WebElement txtPassiveAligners;

	@FindBy(xpath = "//span[text()='Extra-Oral orthodontic photographs']")
	private WebElement weExtraOralOrthodonticPhotographs;

	@FindBy(id = "extraOralImages")
	private WebElement imgInputExtraOralImages;

	@FindBy(xpath = "//span[text()='Intra-Oral orthodontic photographs']")
	private WebElement weIntraOralOrthodonticPhotographs;

	@FindBy(id = "intraOralImages")
	private WebElement imgInputIntraOralImages;

	@FindBy(id = "priorFacialAestheticsDetails")
	private WebElement txtPriorFacialAestheticsDetails;

	@FindBy(xpath="//button/span[text()=\"Don't show this again\"]")
	private WebElement btnDontShowAgain;

	@FindBy(xpath = "//span[text()='Radiographs']")
	private WebElement weRadiographs;

	@FindBy(xpath = "//span[text()='Optional radiograph support']")
	private WebElement weRadiographsDuo;

	@FindBy(id = "radioGraphImgs")
	private WebElement imgInputRadiographsImages;

	@FindBy(xpath = "//span[text()='Impressions']")
	private WebElement weImpressions;

	@FindBy(id = "impressionType")
	private WebElement lstImpressionType;

	@FindBy(id = "postageDate")
	private WebElement txtPostageDate;


	@FindBy(id = "impressionScannerType")
	private WebElement lstImpressionScannerType;

	@FindBy(id = "otherScannerType")
	private WebElement txtOtherScannerType;

	@FindBy(id = "imprImages")
	private WebElement imgImpressionScannerTypeStl;

	@FindBy(xpath = "//*[@id='anonymisedInfo']/..")
	private WebElement cbAgreeTerms;

	@FindBy(xpath = "//button/span[text()='Save as draft']")
	private WebElement btnSaveAsdraft;

	@FindBy(xpath = "//button/span[text()='Submit Case']")
	private WebElement btnSubmitCase;
	
	@FindBy(xpath = "//*[@role='dialog']//button/span[text()='Next']")
	private WebElement btnDialogNext;
	
	@FindBy(xpath = "//*[@role='dialog']//button/span[text()='Submit']")
	private WebElement btnDialogSubmit;
	
	@FindBy(xpath = "//div[@data-node-key='case-info']")
	private WebElement tabSubmissions;
	
	@FindBy(id = "revision_request")
	private WebElement txtRevisionRequest;

	@FindBy(xpath = "//button/span[text()='Submit']")
	private WebElement btnSubmit;

	@FindBy(id = "submit-notes-to-self")
	private WebElement btnSaveNote;
	
	@FindBy(id = "note")
	private WebElement txtNote;

	@FindBy(xpath = "//button/span[text()='Continue']")
	private WebElement btnContinue;

	@FindBy(xpath = "//button/span[text()='Delete files']")
	private WebElement btnDeleteFiles;

	//Duo elements

	@FindBy(id="priorExtensiveGeneralDentalTreatment")
	private WebElement lstPreviousTreatmentExtensiveGeneralDental;

	@FindBy(id="priorOrthodontics")
	private WebElement lstPreviousTreatmentOrthodontics;

	@FindBy(id="priorFacialAesthetics")
	private WebElement lstPreviousTreatmentFacialAesthetics;

	@FindBy(id="relevantMedicalSocialAndDrugHistory")
	private WebElement txtRelevantMedicalSocialAndDrugHistory;

	@FindBy(id="patientOccupation")
	private WebElement txtRelevantPatientOccupation;

	@FindBy(id="otherModifyingFactors")
	private WebElement txtRelevantOtherModifyingFactors;

	@FindBy(id="dietaryFactors")
	private WebElement txtRelevantDietaryFactors;

	@FindBy(id="diagnosis_arches")
	private WebElement lstTreatmentAimsDiagnosis_arches;

	@FindBy(id="patientMotivationAndConcern")
	private WebElement txtTreatmentAimsPatientMotivationAndConcern;

	@FindBy(id="statedTimings")
	private WebElement txtTreatmentAimsStartedDate;

	@FindBy(id="statedTimingsNotes")
	private WebElement txtTreatmentAimsStartedDateNotes;


	@FindBy(xpath = "//div[@aria-describedby='widerTreatmentPlan_help']")
	private WebElement lstTreatMentAimsFurtherTreatments;

	@FindBy(id="widerTreatmentPlanDetails")
	private WebElement txtTreatMentAimsFurtherTreatmentsNotes;

	@FindBy(id="patientSuitability")
	private WebElement lstTreatMentAimsConsent;

	@FindBy(xpath = "//span[text()='Download consent form']")
	private WebElement btnDownloadConsentForm;

	@FindBy(xpath = "//button/span[text()='Next']")
	private WebElement btnNext;

	@FindBy(id="dentalHealthStatus")
	private WebElement lstClinicalDentalHealthStatus;

	@FindBy(id="oralHygiene")
	private WebElement lstClinicalOralHygiene;

	@FindBy(id="BPE")
	private WebElement txtClinicalBPE;

	@FindBy(id="gingivalBiotype")
	private WebElement lstClinicalGingivalBiotype;

	@FindBy(id="toothSurfaceLoss")
	private WebElement lstClinicalToothSurfaceLoss;

	@FindBy(id="TMJ")
	private WebElement lstClinicalTMJDysFunction;

	@FindBy(id="paraFuctionalActivity")
	private WebElement lstClinicalParaFuctionalActivity;

	@FindBy(id="bruxer")
	private WebElement lstClinicalBruxer;

	@FindBy(id="oralHealthPathology")
	private WebElement txtClinicalOralHealthPathologyDesc;

	@FindBy(id="radiographQuery")
	private WebElement btnClinicalRadiography;

	@FindBy(id="orthodonticAssessmentNow")
	private WebElement btnOrthodonticAssessmentNow;
	
	@FindBy(id="skeletalPatternAntero")
	private WebElement btnClinicalSkeletalPatternAntero;

	@FindBy(id="skeletalPatternVertical")
	private WebElement btnClinicalSkeletalPatternVertical;

	@FindBy(id="skeletalPatternTransverse")
	private WebElement btnClinicalSkeletalPatternTransverse;

	@FindBy(id="crowdingInUpperArch")
	private WebElement btnClinicalArchAssessmentCrowdingUpperArch;

	@FindBy(id="crowdingInLowerArch")
	private WebElement btnClinicalArchAssessmentCrowdingLowerArch;

	//label[text()='Annotate rotations present for both arches*']/../following-sibling::div//span[text()='UR8']/following-sibling::label//input

	@FindBy(id="overjetIncisorRelationship")
	private WebElement btnClinicalOcclusalIncisorRelationship;

	@FindBy(id="overjetInMM_lower")
	private WebElement btnClinicalOcclusalIncisorOverjetLower;

	@FindBy(id="overjetInMM_upper")
	private WebElement btnClinicalOcclusalIncisorOverjetUpper;

	@FindBy(id="overjetOverbiteOrAnterior")
	private WebElement btnClinicalOcclusalOverbite;

	@FindBy(id="diagnosis_upperDentalCenterLineOfFace")
	private WebElement btnClinicalOcclusalUpperDental;

	@FindBy(id="lowerDentalCenterLineOfFace")
	private WebElement btnClinicalOcclusalLowerDental;

	@FindBy(id="molarRelations")
	private WebElement btnClinicalOcclusalMolarRelationship;

	//*[text()='Look at the anterior and posterior regions for the presence of a cross-bite']/../div//span[text()='UR8']/following-sibling::label//input

	@FindBy(id="dentistPerspectiveOne")
	private WebElement txtDentistInstrPerspectiveOne;

	@FindBy(id="leftMolarRelations")
	private WebElement btnleftMolarRelations;
	
	@FindBy(id="rightMolarRelations")
	private WebElement btnrightMolarRelations;
	
	@FindBy(id="dentistPerspectiveTwo")
	private WebElement txtDentistInstrPerspectiveTwo;

	@FindBy(id="dentistPerspectiveThree")
	private WebElement txtDentistInstrPerspectiveThree;

	@FindBy(id="comprehensiveCompromisedOutcomes")
	private WebElement txtDentistInstrComprehensiveCompromisedOutcomes;

	@FindBy(id="patientAcceptableInterventions_IPR")
	private WebElement rbDentistInstrTreatmentPrefIPR;

	@FindBy(id="patientAcceptableInterventions_attachments")
	private WebElement rbDentistInstrTreatmentPrefAttachments;

	@FindBy(id="patientAcceptableInterventions_auxiliaries")
	private WebElement rbDentistInstrTreatmentPrefAuxiliaries;

	@FindBy(id="patientAcceptableInterventions_extraction")
	private WebElement rbpatientAcceptableInterventions_extraction;
	
	@FindBy(xpath="//a/span[text()='Download Consent Form']")
	private WebElement btnDownloadAlignerFittingConsent;

	@FindBy(id="diagnosis_phasingPreference")
	private WebElement lstDentistInstrPhasing;

	@FindBy(xpath="//button/span[text()='Download Case Form']")
	private WebElement btnDownloadCaseForm;

	@FindBy(xpath="//div[@role='tab']//div[contains(normalize-space(),'Patient Summary')]")
	private WebElement tabPatientSummary;

	@FindBy(xpath="//div[@role='tab']//div[contains(normalize-space(),'Treatment Aims')]")
	private WebElement tabTreatmentAims;

	@FindBy(xpath="//div[@role='tab']//div[contains(normalize-space(),'Clinical Examination')]")
	private WebElement tabClinicalExamination;

	@FindBy(xpath="//div[@role='tab']//div[contains(normalize-space(),\"Dentist’s Instructions\")]")
	private WebElement tabDentistInstructions;

	@FindBy(xpath = "//div/*[@data-node-key='treatment-designs']")
	private WebElement tabTreatmentDesgins;
	

	@FindBy(xpath = "//span[text()='Treatment Design']")
	private WebElement weTreatmentDesignHeader;

	@FindBy(id = "create-patient-proposal")
	private WebElement btnCreatePatientProposal;
	
	@FindBy(id = "edit-patient-proposal")
	private WebElement btnEditPatientProposal;
	
	@FindBy(id = "numberofAligner")
	private WebElement txtNumberofAligner;
	
	@FindBy(id = "duration")
	private WebElement txtEstDuration;
	
	@FindBy(id = "price")
	private WebElement txtPrice;
	
	@FindBy(xpath = "//textarea[@placeholder='Treatment Goals']")
	private WebElement txtTreatmentGoals;
	
	@FindBy(xpath = "//button/span[text()='+ Add']")
	private WebElement btnAddTreatmentGoals;
	
	@FindBy(id = "add-to-quote")
	private WebElement btnAddToQoute;
	
	@FindBy(xpath = "//button/span[text()='Delete quote']")
	private WebElement btnDeleteQoute;
	
	@FindBy(xpath = "//div[@class='ant-popover-content']//button/span[text()='Delete']")
	private WebElement btnConfirmDelete;
	
	@FindBy(xpath = "//div[@class='ant-popover-content']//button/span[text()='Cancel']")
	private WebElement btnConfirmCancel;
	
	@FindBy(xpath = "//button/span[text()='Add new quote']")
	private WebElement btnAddNewQoute;
	
	@FindBy(id = "submit-proposal")
	private WebElement btnSendToPatient;
	
	@FindBy(id = "preview-proposal")
	private WebElement btnPreviewProposal;
	
	//@FindBy(id = "addDentistInMail")
	@FindBy(xpath = "//*[text()='Send copy to me']")
	private WebElement cbSendCopyToMe;
	
	@FindBy(xpath = "//h4[text()='Patient proposal preview']")
	private WebElement wePatientPropsalPreviewHeader;
	
	@FindBy(xpath = "//div[@class='ant-drawer-body']//*[@aria-label='close']")
	private WebElement btnClosePatientProposalPreview;
	
	@FindBy(xpath = "//button/*[@aria-label='edit']")
	private WebElement btnEditTreatmentQouteName;
	
	//@FindBy(xpath = "//textarea[contains(text(),'Treatment Quote')]")
	@FindBy(xpath = "//div[@class='ant-space-item']//textarea[@class='ant-input']")
	private WebElement txttreatmentQouteName;
	
	@FindBy(xpath = "//button/*[text()='+ Add']")
	private WebElement btnAddQoute;
	
	

	@FindBy(xpath = "//div/*[@data-node-key='patient-proposal']")
	private WebElement tabPatientProposal;
	
	@FindBy(xpath = "//button/span[text()='View and Edit']")
	private WebElement btnViewEdit;
	
	@FindBy(xpath = "//button/span[text()='Order Clear Aligners']")
	private WebElement btnOrderClearAligners;
	
	@FindBy(id = "view-sku-card-details")
	private WebElement btnViewskuCardDetails;
	
	@FindBy(id = "add-to-basket")
	private WebElement btnAddToBasket;
	
	//@FindBy(xpath = "//a[contains(text(),'I have read and accept the terms and conditions')]")
	@FindBy(xpath = "//input[@id='isAgreeTermCondition']/..")
	private WebElement cbAggreeTermsSku;
	
	@FindBy(xpath = "//button/span[text()='Go To Order Summary']")
	private WebElement btnGoToOrderSummary;
	
	@FindBy(xpath = "//button/span[text()='Go back']")
	private WebElement btnGoback;
	
	@FindBy(xpath = "//div[@class='ant-notification-notice-description']")
	private WebElement weToasterMsgDescription;
	
	@FindBy(id = "accept-and-order")
	private WebElement btnAcceptAndOrder;
	
	@FindBy(xpath = "//label[@for='crownsOrVeneers']")
	private WebElement imgCrownsOrVeeners;
	
	@FindBy(xpath = "//button/span[text()='Skip']")
	private WebElement btnSKIP;
	
	@FindBy(id = "specialistPrioritiseDetail")
	private WebElement weSpecialistPrioritiseDetail;
	
	@FindBy(id = "goodOutComes")
	private WebElement txtGoodOutComes;
	
	@FindBy(id = "specialistDePrioritiseDetail")
	private WebElement txtSpecialistDePrioritiseDetail;
	
	
	@FindBy(xpath = "//div/*[@data-node-key='order-summary']")
	private WebElement tabOrderSummary;
	
	public DentistPatientsPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {
	}

	public  HashMap<String, String> createHashMapfromJsonFileForSolo(String file) {
		ObjectMapper mapper = new ObjectMapper();  
		File fileObj = new File("D:\\32Co\\workspace\\e2e\\src\\test\\resources\\json\\solo.json");  
		HashMap<String, String> details = new HashMap<String, String>();
		try {  

			String json = new String(Files.readAllBytes(fileObj.toPath()));
			details.put("firstName", getValueFromJson(json,  "$.patientDetail.firstName"));
			details.put("lastName", getValueFromJson(json,  "$.patientDetail.lastName"));
			details.put("dob", getValueFromJson(json,  "$.patientDetail.dob"));
			String email = getValueFromJson(json,  "$.patientDetail.email").trim();
			if(email.equals("")) {
				details.put("email", generateRandomString(8, "alpha")+"@gmail.com");
			}else if(validateEmail(email)){
				Values.dentistEmail =  generateRandomEmail(email, "dentist");
				details.put("email",Values.dentistEmail);
			}else{
				Values.dentistEmail =  generateRandomEmail(email, "dentist");
				details.put("email",Values.dentistEmail);
			}
			details.put("phonenumber", getValueFromJson(json,  "$.patientDetail.phonenumber"));
			details.put("practice", getValueFromJson(json,  "$.practice"));
			details.put("dentalAndMedicalFit", getValueFromJson(json,  "$.dentalAndMedicalFit"));
			details.put("txnObjective", getValueFromJson(json,  "$.txnObjective"));	
			details.put("furtherInstructions", getValueFromJson(json,  "$.furtherInstructions"));				
			details.put("archesUnderConsideration", getValueFromJson(json,  "$.archesUnderConsideration"));	
			details.put("spacing.typeOfSpacing", getValueFromJson(json,  "$.spacing.typeOfSpacing"));
			//one more for image $.spacing.archSpacing
			details.put("spacing.additionalDetailsWhenLeaveSpaces", getValueFromJson(json,  "$.spacing.additionalDetailsWhenLeaveSpaces"));
			details.put("resolveUpper.expand", getValueFromJson(json,  "$.crowding.resolveUpper.expand"));
			details.put("resolveUpper.procline", getValueFromJson(json,  "$.crowding.resolveUpper.procline"));
			details.put("resolveUpper.iprAnterior", getValueFromJson(json,  "$.crowding.resolveUpper.iprAnterior"));
			details.put("resolveUpper.iprPosterior", getValueFromJson(json,  "$.crowding.resolveUpper.iprPosterior"));
			details.put("resolveLower.expand", getValueFromJson(json,  "$.crowding.resolveLower.expand"));
			details.put("resolveLower.procline", getValueFromJson(json,  "$.crowding.resolveLower.procline"));
			details.put("resolveLower.iprAnterior", getValueFromJson(json,  "$.crowding.resolveLower.iprAnterior"));
			details.put("resolveLower.iprPosterior", getValueFromJson(json,  "$.crowding.resolveLower.iprPosterior"));
			details.put("typeOfOverBite", getValueFromJson(json,  "$.overBite.typeOfOverBite"));
			details.put("additionalDetailsWhenCorrectBite", getValueFromJson(json,  "$.overBite.additionalDetailsWhenCorrectBite"));
			details.put("typeOfBiteRamps", getValueFromJson(json,  "$.biteRamps.typeOfBiteRamps"));
			details.put("additionalDetailsWhenPlaceBiteRamps", getValueFromJson(json,  "$.biteRamps.additionalDetailsWhenPlaceBiteRamps"));
			details.put("overjet", getValueFromJson(json,  "$.overjet"));
			details.put("maintainCurrent", getValueFromJson(json,  "$.anteriorPosteriorRelationship.maintainCurrent"));
			details.put("correctionToClass", getValueFromJson(json,  "$.anteriorPosteriorRelationship.correctionToClass"));
			details.put("improveCanineAndMolarRelationship", getValueFromJson(json,  "$.anteriorPosteriorRelationship.improveCanineAndMolarRelationship"));
			details.put("additionalDetails", getValueFromJson(json,  "$.anteriorPosteriorRelationship.additionalDetails"));
			details.put("toothMovementOptions", getValueFromJson(json,  "$.anteriorPosteriorRelationship.toothMovementOptions"));
			details.put("precisionCuts", getValueFromJson(json,  "$.anteriorPosteriorRelationship.precisionCuts"));
			details.put("buttonCutOuts", getValueFromJson(json,  "$.anteriorPosteriorRelationship.buttonCutOuts"));

			details.put("impressionType", getValueFromJson(json,  "$.impressionType"));		
			details.put("postageDate", getValueFromJson(json,  "$.postageDate"));		

		} catch (Exception e) {  
			e.printStackTrace();  
		} 
		return details;
	}



	public void createSoloCase(Data data) {
		HashMap<String, Object> details = convertJsonToMap("."+data.get("JSONPATH"));
		enterSoloCaseDetails(details);
		clickOn(btnSubmitCase, "SubmitCase Button");
		waitForElement(By.xpath("//span[text()='Patient Info']"));
		sleep(3000);
		if(isElementPresent(By.xpath("//span[text()='Patient Info']"))) {
			passed("Patient Info page is displayed after clicking on submit button");
		}else {
			fail("Patient Info page is not displayed after clicking on submit button");
		}
		takeScreenshot();
		enterText(txtNote, "Note", "Automation Testing Note");
		clickOn(btnSubmit, "Submit Button");
		sleep(3000);
		takeScreenshot();
	}

	public void createDuoCase() {
		HashMap<String, Object> details = convertJsonToMap("."+data.get("JSONPATH"));
		enterDuoCaseDetails(details);
		clickOn(btnSubmitCase, "SubmitCase Button");
		sleep(3000);
		waitForElement(btnDialogSubmit);
		if(isElementPresent(btnDialogSubmit)) {
			clickOn(btnDialogSubmit, "Submit Button");
		}
		waitForElement(By.xpath("//span[text()='Patient Info']"));
		if(isElementPresent(By.xpath("//span[text()='Patient Info']"))) {
			passed("Patient Info page is displayed after clicking on submit button");
		}else {
			fail("Patient Info page is not displayed after clicking on submit button");
		}
		takeScreenshot();
		enterText(txtNote, "Note", "Automation Testing Note");
		clickOn(btnSaveNote, "Save Note Button");

		takeScreenshot();

	}

	public void enterSoloCaseDetails(HashMap<String, Object> details) {
		Values.patientFirstName = getValue(details,"patientDetail.firstName") + "_" + generateRandomString(4, "alpha");
		Values.patientLastName = getValue(details,"patientDetail.lastName") + "_" + generateRandomString(4, "alpha");
		enterText(txtFirstName, "First Name", Values.patientFirstName);
		enterText(txtLastName, "Last Name", Values.patientLastName);
		enterText(txtDOB, "Date Of Birth", getValue(details,"patientDetail.dob"));		
		Values.patientEmail = generateRandomEmail(getValue(details,"patientDetail.email"), "patient");
		enterText(txtEmail, "Email Address", Values.patientEmail);
		sleep(1000);
		//enterText(txtMobileNumber, "Phone Number", getValue(details,"patientDetail.phonenumber"));

		selectOptionDropDown(lstClinicName, "Clinic Name", getValue(details,"practice"));
		selectOptionDropDown(lstDentalAndMedicalFit, "Dental Medical Fit", getValue(details,"dentalAndMedicalFit"));

		selectMultiOptionDropDown(mlstTreatmentPlanObjectives, "TreatmentPlanObjectives", getValue(details,"txnObjective"));

		enterText(txtFurtherInstructions, "FurtherInstructions",  getValue(details,"furtherInstructions"));
		selectRadioButton(rbArchesToTreat,"Arches to treat",getValue(details,"archesUnderConsideration"));
		selectRadioButton(rbSpacingCrowding,"SpacingCrowding",getValue(details,"spacing.typeOfSpacing"));

		selectRadioButton(rbCrowdingResolveUpperExpand,"CrowdingResolveUpperExpand",getValue(details,"crowding.resolveUpper.expand"));
		selectRadioButton(rbCrowdingResolveUpperProcline,"CrowdingResolveUpperProcline",getValue(details,"crowding.resolveUpper.procline"));
		selectRadioButton(rbCrowdingResolveUpperIprAnterior,"CrowdingResolveUpperIprAnterior",getValue(details,"crowding.resolveUpper.iprAnterior"));
		selectRadioButton(rbCrowdingResolveUpperIprPosterior,"CrowdingResolveUpperIprPosterior",getValue(details,"crowding.resolveUpper.iprPosterior"));

		selectRadioButton(rbCrowdingResolveLowerExpand,"CrowdingResolveLowerExpand",getValue(details,"crowding.resolveLower.expand"));
		selectRadioButton(rbCrowdingResolveLowerProcline,"CrowdingResolveLowerProcline",getValue(details,"crowding.resolveLower.procline"));
		selectRadioButton(rbCrowdingResolveLowerIprAnterior,"CrowdingResolveLowerIprAnterior",getValue(details,"crowding.resolveLower.iprAnterior"));
		selectRadioButton(rbCrowdingResolveLowerIprPosterior,"CrowdingResolveLowerIprPosterior",getValue(details,"crowding.resolveLower.iprPosterior"));

		selectRadioButton(rbOverBite,"OverBite",getValue(details,"overBite.typeOfOverBite"));
		enterText(txtOverBiteAdditionalDetails, "OverBiteAdditionalDetails", getValue(details,"overBite.additionalDetailsWhenCorrectBite"));

		expandSection(weBiteRamps, "Bite Ramps Section");
		sleep(2000);
		selectRadioButton(rbBiteRampsType,"BiteRampsType",getValue(details,"biteRamps.typeOfBiteRamps"));
		enterText(txtBiteRampsTypeAdditionalDetails, "BiteRampsTypeAdditionalDetails", getValue(details,"biteRamps.additionalDetailsWhenPlaceBiteRamps"));

		selectRadioButton(rbOverjet,"Overjet",getValue(details,"overjet"));

		selectCheckBox(cbAnteriorPosteriorMaintainCurrent,"AnteriorPosteriorMaintainCurrent",getValue(details,"anteriorPosteriorRelationship.maintainCurrent[0]"));
		selectCheckBox(cbAnteriorPosteriorCorrectionClass,"AnteriorPosteriorCorrectionClass",getValue(details,"anteriorPosteriorRelationship.correctionToClass[0]"));
		selectCheckBox(cbAnteriorPosteriorImproveCanineMolarRel,"AnteriorPosteriorImproveCanineMolarRel",getValue(details,"anteriorPosteriorRelationship.improveCanineAndMolarRelationship[0]"));

		enterText(txtAnteriorPosteriorAdditionalDetails, "AnteriorPosteriorAdditionalDetails", getValue(details,"anteriorPosteriorRelationship.additionalDetails"));

		selectCheckBox(cbToothMovementOptions,"ToothMovementOptions",getValue(details,"anteriorPosteriorRelationship.toothMovementOptions[0]"));

		//check above
		selectRadioButton1(rbPrecisionCuts,"PrecisionCuts",getValue(details,"anteriorPosteriorRelationship.precisionCuts"));
		selectRadioButton1(rbButtonCutouts,"ButtonCutouts",getValue(details,"anteriorPosteriorRelationship.buttonCutOuts"));
		waitForElement(wePosteriorCrossbite);
		sleep(1000);
		moveToElement(wePosteriorCrossbite);
		expandSection(wePosteriorCrossbite, "Posterior Crossbite Section");
		waitForElement(rbPosteriorCrossBite);
		sleep(2000);
		selectRadioButton(rbPosteriorCrossBite,"PosteriorCrossBite",getValue(details,"posteriorCrossBite"));
		enterText(txtPosteriorCrossBiteAdditionalDetails, "PosteriorCrossBiteAdditionalDetails", getValue(details,"posteriorCrossBiteDetail"));

		selectRadioButton(rbMidlineUpper,"MidlineUpper",getValue(details,"midline.upper"));
		selectRadioButton(rbMidlineLower,"MidlineLower",getValue(details,"midline.lower"));
		enterText(txtMidlineAdditionalDetails, "MidlineAdditionalDetails", getValue(details,"midline.additionalDetails"));

		expandSection(weExtractions, "Extractions Section");
		//call methods to select image tooth
		sleep(2000);
		enterText(txtExtractionsAdditionalDetails, "ExtractionsAdditionalDetails", getValue(details,"extractions.additionalDetails"));
		selectRadioButton(rbAttachments,"Attachments",getValue(details,"attachments.typeOfAttachments"));
		if(getValue(details,"attachments.typeOfAttachments").contains("These specific teeth should not have attachments")) {
			waitForElement(txtAttachementsDetails);
			enterText(txtAttachementsDetails, "AttachementsDetails", getValue(details,"attachments.additionalDetails"));
		}
		selectRadioButton(rbTypeOfIPR,"TypeOfIPR",getValue(details,"ipr.typeOfIPR"));
		if(getValue(details,"ipr.typeOfIPR").contains("Limited")) {
			waitForElement(txtIPRDetails);
			enterText(txtIPRDetails,"IPR Additional details ",getValue(details,"ipr.additionalDetails"));
		}
		selectRadioButton(rbPhasingPreference,"PhasingPreference",getValue(details,"phasingPreference"));
		enterText(txtPhasingPreferenceDetails, "PhasingPreferenceDetails", getValue(details,"phasingPreferenceDetails"));

		expandSection(weToothMovementRestrictions, "ToothMovementRestrictions");
		sleep(1000);
		enterText(txtMovementRestrictionsAdditionalDetails, "MovementRestrictionsAdditionalDetails", getValue(details,"toothMovementRestrictions.additionalDetails"));

		expandSection(wePassiveAligners, "PassiveAligners");
		waitForElement(txtPassiveAligners);
		enterText(txtPassiveAligners, "PassiveAligners", getValue(details,"passiveAligners"));

		//upload files
		if(getValue(details, "extraOralImages[0]")!=null && !getValue(details, "extraOralImages[0]").equals("")) {
			clickOn(weExtraOralOrthodonticPhotographs, "ExtraOralOrthodonticPhotographs");
			sleep(2000);
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[0]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[1]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[2]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[3]"));
		}
		if(getValue(details, "intraOralImages[0]")!=null && !getValue(details, "intraOralImages[0]").equals("")) {
			clickOn(weIntraOralOrthodonticPhotographs, "IntraOralOrthodonticPhotographs");
			sleep(2000);
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[0]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[1]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[2]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[3]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[4]"));
		}
		if(isElementPresent(btnDontShowAgain)) {
			clickOn(btnDontShowAgain, "Dont Show Again");
		}
		if(getValue(details, "radioGraphImgs[0]")!=null && !getValue(details, "radioGraphImgs[0]").equals("")) {
			clickOn(weRadiographs, "Radiographs");
			sleep(Values.sleepTime*2);
			uploadImage(imgInputRadiographsImages, getValue(details, "radioGraphImgs[0]"));
			sleep(Values.sleepTime*5);
		}		
		clickOn(weImpressions, "Impressions");
		sleep(Values.sleepTime*3);
		selectOptionDropDown(lstImpressionType, "ImpressionType", getValue(details,"impressionType"));
		if(getValue(details,"impressionType").contains("Silicone Impressions")) {
			enterText(txtPostageDate, "PostageDate", getValue(details,"postageDate"));
		}else if(getValue(details,"impressionType").contains("Digital Intra")) {
			waitForElement(lstImpressionScannerType);
			selectOptionDropDown(lstImpressionScannerType, "ImpressionScannerType", getValue(details,"ImpressionScannerType"));
			waitForElement(imgImpressionScannerTypeStl);
			uploadImage(imgImpressionScannerTypeStl, getValue(details, "imprImages") );
			waitForElement(cbAgreeTerms);
			if(isElementPresent(By.xpath("//div[@role='dialog']"))) {
				String popupTitle = getText(By.xpath("//div[@role='dialog']//h2"));
				passed("Warning message is displayed as expected "+popupTitle);				
				isElementDisplayed(btnContinue, "Continue Button in Warning dialog");
				isElementDisplayed(btnDeleteFiles, "Delete Files Button in Warning dialog");
				clickOn(btnContinue, "Continue Button");
				sleep(1000);
			}
			//clickOn(cbAgreeTerms, "AgreeTerms Checkbox");
		}else {

		}
		takeScreenshot();

	}


	public void enterDuoCaseDetails(HashMap<String, Object> details) {
		Values.patientFirstName = getValue(details,"patientDetail.firstName") + "_" + generateRandomString(4, "alpha");
		Values.patientLastName = getValue(details,"patientDetail.lastName") + "_" + generateRandomString(4, "alpha");
		enterText(txtFirstName, "First Name", Values.patientFirstName);
		enterText(txtLastName, "Last Name", Values.patientLastName);
		enterText(txtDOB, "Date Of Birth", getValue(details,"patientDetail.dob"));		
//		Values.patientEmail = generateRandomEmail(getValue(details,"patientDetail.email"), "patient");
//		enterText(txtEmail, "Email Address", Values.patientEmail);
		sleep(1000);
		//enterText(txtMobileNumber, "Phone Number", getValue(details, "patientDetail.phone"));
		selectFirstOptionFromDropDown(lstClinicName, "Clinic Name");
		enterText(txtRelevantPatientOccupation, "patientOccupation", getValue(details, "patientOccupation"));
		selectOptionDropDown(lstTreatMentAimsConsent, "patientSuitability", getValue(details, "patientSuitability"));	
		takeScreenshot();
		clickOn(btnNext, "Next Button");
		
		//Clinical Examination
		enterText(txtClinicalBPE, "BPE", getValue(details, "BPE"));	
		selectOptionDropDown(lstClinicalGingivalBiotype, "gingivalBiotype", getValue(details, "gingivalBiotype"));	
		selectOptionDropDown(lstClinicalToothSurfaceLoss, "toothSurfaceLoss", getValue(details, "toothSurfaceLoss"));
		selectOptionDropDown(lstClinicalTMJDysFunction, "TMJ", getValue(details, "TMJ"));
		selectOptionDropDown(lstClinicalParaFuctionalActivity, "paraFuctionalActivity", getValue(details, "paraFuctionalActivity"));
		enterText(txtClinicalOralHealthPathologyDesc, "oralHealthPathology", getValue(details, "oralHealthPathology"));
		enterText(txtRelevantMedicalSocialAndDrugHistory, "relevantMedicalSocialAndDrugHistory", getValue(details, "relevantMedicalSocialAndDrugHistory"));
		//selectOptionDropDown(lstPreviousTreatmentOrthodontics, "priorOrthodontics", getValue(details, "priorOrthodontics"));
		selectButtonFromMultiple(lstPreviousTreatmentOrthodontics, "priorOrthodontics", getValue(details, "priorOrthodontics"));
		selectOptionCheckboxFromDentalImage(imgCrownsOrVeeners, "Crowns Or Veeners", getValue(details, "crownsOrVeneers"));
		selectButtonFromMultiple(btnClinicalRadiography, "radiographQuery", getValue(details, "radiographQuery"));
		selectButtonFromMultiple(btnOrthodonticAssessmentNow, "Orthodontic AssessmentNow", getValue(details, "orthodonticAssessmentNow"));
		if(getValue(details, "orthodonticAssessmentNow").equals("Yes")) {
			if(isElementPresent(btnClinicalSkeletalPatternAntero)) {
			selectButtonFromMultiple(btnClinicalSkeletalPatternAntero, "skeletalPatternAntero", getValue(details, "skeletalPatternAntero"));
			selectButtonFromMultiple(btnClinicalSkeletalPatternVertical, "skeletalPatternVertical", getValue(details, "skeletalPatternVertical"));
			selectButtonFromMultiple(btnClinicalSkeletalPatternTransverse, "skeletalPatternTransverse", getValue(details, "skeletalPatternTransverse"));
			selectButtonFromMultiple(btnClinicalArchAssessmentCrowdingUpperArch, "crowdingInUpperArch", getValue(details, "crowdingInUpperArch"));
			selectButtonFromMultiple(btnClinicalArchAssessmentCrowdingLowerArch, "crowdingInLowerArch", getValue(details, "crowdingInLowerArch"));
			takeScreenshot();
			selectButtonFromMultiple(btnClinicalOcclusalIncisorRelationship, "overjetIncisorRelationship", getValue(details, "overjetIncisorRelationship"));
			enterText(btnClinicalOcclusalIncisorOverjetLower, "overjetInMMLower", getValue(details, "overjetInMM.lower"));
			enterText(btnClinicalOcclusalIncisorOverjetUpper, "overjetInMMUpper", getValue(details, "overjetInMM.upper"));
			selectButtonFromMultiple(btnClinicalOcclusalOverbite, "overjetOverbiteOrAnterior", getValue(details, "overjetOverbiteOrAnterior"));
			selectButtonFromMultiple(btnClinicalOcclusalUpperDental, "upperDentalCenterLineOfFace", getValue(details, "diagnosis_upperDentalCenterLineOfFace"));
			selectButtonFromMultiple(btnClinicalOcclusalLowerDental, "lowerDentalCenterLineOfFace", getValue(details, "lowerDentalCenterLineOfFace"));
			
			selectButtonFromMultiple(btnleftMolarRelations, "leftMolarRelations", getValue(details, "leftMolarRelations"));
			selectButtonFromMultiple(btnrightMolarRelations, "rightMolarRelations", getValue(details, "rightMolarRelations"));
			}else {
				fail("Skeletal Pattern and othe options are not displayed when Orthodontic Assessment now is Yes");
			}
		}else {
			if(isElementPresent(btnClinicalSkeletalPatternAntero)) {
				fail("Skeletal Pattern and othe options are displayed when Orthodontic Assessment now is No");
			}
		}
		takeScreenshot();
		clickOn(btnNext, "Next Button");
		
		if(isElementPresent(btnDialogNext)) {
			clickOn(btnDialogNext, "Next Button");
		}
		
		//Treatment Aims
		waitForElement(lstTreatmentAimsDiagnosis_arches);
		selectOptionDropDown(lstTreatmentAimsDiagnosis_arches, "diagnosisarches", getValue(details, "diagnosis.arches"));	
		
		String val = getValue(details, "specialistPrioritiseDetail");
		WebElement SpecialistPrioritiseDetailVal = weSpecialistPrioritiseDetail.findElement(By.xpath(".//label//input[@value='"+val+"']/.."));
		clickOn(SpecialistPrioritiseDetailVal, val);
		waitForElement(txtGoodOutComes);
		enterText(txtGoodOutComes, "GoodOutComes text box", "Automation Testing");
		enterText(txtSpecialistDePrioritiseDetail, "specialist DePrioritise Detail textbox", "Automation Testing");
		selectMultiOptionDropDown(lstTreatMentAimsFurtherTreatments, "widerTreatmentPlan", getValue(details, "widerTreatmentPlan"));
		enterText(txtTreatmentAimsStartedDate, "statedTimings", getValue(details, "statedTimings"));
		enterText(txtTreatmentAimsStartedDateNotes, "statedTimingsNotes", getValue(details, "statedTimingsNotes"));
		takeScreenshot();
		moveToElement(rbDentistInstrTreatmentPrefIPR);
		selectRadioButton(rbDentistInstrTreatmentPrefIPR,"patientAcceptableInterventionsIPR" ,getValue(details, "patientAcceptableInterventions.IPR") );
		selectRadioButton(rbDentistInstrTreatmentPrefAttachments,"patientAcceptableInterventionsattachments" ,getValue(details, "patientAcceptableInterventions.attachments") );
		selectRadioButton(rbDentistInstrTreatmentPrefAuxiliaries,"patientAcceptableInterventionsauxiliaries" ,getValue(details, "patientAcceptableInterventions.auxiliaries") );
		selectRadioButton(rbpatientAcceptableInterventions_extraction,"patientAcceptableInterventions_extraction" ,getValue(details, "patientAcceptableInterventions.extractions") );
		selectOptionDropDown(lstDentistInstrPhasing, "diagnosis_phasingPreference", getValue(details, "phasingPreference"));
		if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
			clickOn(btnDownloadCaseForm, "Download Case Form Button");
		}
		
		if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
			clickOn(btnDownloadAlignerFittingConsent, "Aligner Fitting consent form Download Button");
			String parentwidnow = browser.getWindowHandle();
			boolean status = navigateToSecondWindow();
			if(status==true) {
				browser.close();
				navigatoToParentWindow(parentwidnow);
			}
		}
		
		//upload files
		if(getValue(details, "extraOralImages[0]")!=null && !getValue(details, "extraOralImages[0]").equals("")) {
			clickOn(weExtraOralOrthodonticPhotographs, "ExtraOralOrthodonticPhotographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("Are my photos hitting the mark?","Are your extra-oral photos hitting the mark?");
			}
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[0]"));
			validateUploadFiles("Upload 4 beautiful extra-oral photographs.", 1, 4);
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[1]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[2]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[3]"));
			validateUploadFiles("Upload 4 beautiful extra-oral photographs.", 4, 4);
		}
		if(getValue(details, "intraOralImages[0]")!=null && !getValue(details, "intraOralImages[0]").equals("")) {
			clickOn(weIntraOralOrthodonticPhotographs, "IntraOralOrthodonticPhotographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("Are my photos up to scratch?","Are your photos up to scratch?");
			}
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[0]"));
			validateUploadFiles(" intraoral photographs.", 1, 5);
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[1]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[2]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[3]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[4]"));
			validateUploadFiles(" intraoral photographs.", 5, 5);
		}
		if(isElementPresent(btnDontShowAgain)) {
			clickOn(btnDontShowAgain, "Dont Show Again");
		}
		if(getValue(details, "radioGraphImgs[0]")!=null && !getValue(details, "radioGraphImgs[0]").equals("")) {
			clickOn(weRadiographsDuo, "Optional Radiographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("your radiographs please capture these in your clinical","What should I be looking for in my radiographs for orthodontics?");
			}
			uploadImage(imgInputRadiographsImages, getValue(details, "radioGraphImgs[0]"));
			sleep(2000);
		}	
		clickOn(weImpressions, "Impressions");
		sleep(2000);
		selectOptionDropDown(lstImpressionType, "ImpressionType", getValue(details,"impressionType"));
		if(getValue(details,"impressionType").contains("Silicone Impressions")) {
			enterText(txtPostageDate, "PostageDate", getValue(details,"postageDate"));
		}else if(getValue(details,"impressionType").contains("Digital Intra")) {
			waitForElement(lstImpressionScannerType);
			selectOptionDropDown(lstImpressionScannerType, "ImpressionScannerType", getValue(details,"ImpressionScannerType"));
			waitForElement(imgImpressionScannerTypeStl);
			uploadImage(imgImpressionScannerTypeStl, getValue(details, "imprImages") );
			waitForElement(cbAgreeTerms);
			if(isElementPresent(By.xpath("//div[@role='dialog']"))) {
				String popupTitle = getText(By.xpath("//div[@role='dialog']//h2"));
				passed("Warning message is displayed as expected "+popupTitle);				
				isElementDisplayed(btnContinue, "Continue Button in Warning dialog");
				isElementDisplayed(btnDeleteFiles, "Delete Files Button in Warning dialog");
			}
			//clickOn(cbAgreeTerms, "AgreeTerms Checkbox");
		}else {

		}
		takeScreenshot();

	}
	
	public void enterDuoCaseDetailsOld(HashMap<String, Object> details) {
		Values.patientFirstName = getValue(details,"patientDetail.firstName") + "_" + generateRandomString(4, "alpha");
		Values.patientLastName = getValue(details,"patientDetail.lastName") + "_" + generateRandomString(4, "alpha");
		enterText(txtFirstName, "First Name", Values.patientFirstName);
		enterText(txtLastName, "Last Name", Values.patientLastName);
		enterText(txtDOB, "Date Of Birth", getValue(details,"patientDetail.dob"));		
		Values.patientEmail = generateRandomEmail(getValue(details,"patientDetail.email"), "patient");
		enterText(txtEmail, "Email Address", Values.patientEmail);
		sleep(1000);
		//enterText(txtMobileNumber, "Phone Number", getValue(details, "patientDetail.phone"));
		selectOptionDropDown(lstClinicName, "Clinic Name", getValue(details, "practice"));

		selectOptionDropDown(lstPreviousTreatmentExtensiveGeneralDental, "priorExtensiveGeneralDentalTreatment", getValue(details, "priorExtensiveGeneralDentalTreatment"));
		selectOptionDropDown(lstPreviousTreatmentOrthodontics, "priorOrthodontics", getValue(details, "priorOrthodontics"));
		selectOptionDropDown(lstPreviousTreatmentFacialAesthetics, "priorFacialAesthetics", getValue(details, "priorFacialAesthetics"));			
		enterText(txtPriorFacialAestheticsDetails, "priorFacialAestheticsDetails", getValue(details, "priorFacialAestheticsDetails"));
		enterText(txtRelevantMedicalSocialAndDrugHistory, "relevantMedicalSocialAndDrugHistory", getValue(details, "relevantMedicalSocialAndDrugHistory"));
		enterText(txtRelevantPatientOccupation, "patientOccupation", getValue(details, "patientOccupation"));		
		enterText(txtRelevantOtherModifyingFactors, "otherModifyingFactors", getValue(details, "otherModifyingFactors"));
		enterText(txtRelevantDietaryFactors, "dietaryFactors", getValue(details, "dietaryFactors"));
		takeScreenshot();
		clickOn(btnNext, "Next Button");

		selectOptionDropDown(lstTreatmentAimsDiagnosis_arches, "diagnosisarches", getValue(details, "diagnosis.arches"));	
		enterText(txtTreatmentAimsPatientMotivationAndConcern, "patientMotivationAndConcern", getValue(details, "patientMotivationAndConcern"));
		enterText(txtTreatmentAimsStartedDate, "statedTimings", getValue(details, "statedTimings"));
		enterText(txtTreatmentAimsStartedDateNotes, "statedTimingsNotes", getValue(details, "statedTimingsNotes"));
		selectMultiOptionDropDown(lstTreatMentAimsFurtherTreatments, "widerTreatmentPlan", getValue(details, "widerTreatmentPlan"));
		enterText(txtTreatMentAimsFurtherTreatmentsNotes, "widerTreatmentPlanDetails", getValue(details, "widerTreatmentPlanDetails"));
		selectOptionDropDown(lstTreatMentAimsConsent, "patientSuitability", getValue(details, "patientSuitability"));		
		//add to download consent
		takeScreenshot();
		clickOn(btnNext, "Next Button");

		selectOptionDropDown(lstClinicalDentalHealthStatus, "dentalHealthStatus", getValue(details, "dentalHealthStatus"));	
		selectOptionDropDown(lstClinicalOralHygiene, "oralHygiene", getValue(details, "oralHygiene"));	
		enterText(txtClinicalBPE, "BPE", getValue(details, "BPE"));	
		selectOptionDropDown(lstClinicalGingivalBiotype, "gingivalBiotype", getValue(details, "gingivalBiotype"));	
		selectOptionDropDown(lstClinicalToothSurfaceLoss, "toothSurfaceLoss", getValue(details, "toothSurfaceLoss"));	
		selectOptionDropDown(lstClinicalTMJDysFunction, "TMJ", getValue(details, "TMJ"));	
		selectOptionDropDown(lstClinicalParaFuctionalActivity, "paraFuctionalActivity", getValue(details, "paraFuctionalActivity"));
		selectOptionDropDown(lstClinicalBruxer, "bruxer", getValue(details, "bruxer"));
		enterText(txtClinicalOralHealthPathologyDesc, "oralHealthPathology", getValue(details, "oralHealthPathology"));
		selectButtonFromMultiple(btnClinicalRadiography, "radiographQuery", getValue(details, "radiographQuery"));
		selectButtonFromMultiple(btnClinicalSkeletalPatternAntero, "skeletalPatternAntero", getValue(details, "skeletalPatternAntero"));
		selectButtonFromMultiple(btnClinicalSkeletalPatternVertical, "skeletalPatternVertical", getValue(details, "skeletalPatternVertical"));
		selectButtonFromMultiple(btnClinicalSkeletalPatternTransverse, "skeletalPatternTransverse", getValue(details, "skeletalPatternTransverse"));
		selectButtonFromMultiple(btnClinicalArchAssessmentCrowdingUpperArch, "crowdingInUpperArch", getValue(details, "crowdingInUpperArch"));
		selectButtonFromMultiple(btnClinicalArchAssessmentCrowdingLowerArch, "crowdingInLowerArch", getValue(details, "crowdingInLowerArch"));
		takeScreenshot();
		//iamge toooth selection	
		clickOn(By.xpath("//div[text()='Rotations']/following-sibling::div//span[text()=' present in either arch']"), "Checkbox");
		selectButtonFromMultiple(btnClinicalOcclusalIncisorRelationship, "overjetIncisorRelationship", getValue(details, "overjetIncisorRelationship"));
		enterText(btnClinicalOcclusalIncisorOverjetLower, "overjetInMMLower", getValue(details, "overjetInMM.lower"));
		enterText(btnClinicalOcclusalIncisorOverjetUpper, "overjetInMMUpper", getValue(details, "overjetInMM.upper"));
		selectButtonFromMultiple(btnClinicalOcclusalOverbite, "overjetOverbiteOrAnterior", getValue(details, "overjetOverbiteOrAnterior"));
		selectButtonFromMultiple(btnClinicalOcclusalUpperDental, "upperDentalCenterLineOfFace", getValue(details, "diagnosis_upperDentalCenterLineOfFace"));
		selectButtonFromMultiple(btnClinicalOcclusalLowerDental, "lowerDentalCenterLineOfFace", getValue(details, "lowerDentalCenterLineOfFace"));
		selectButtonFromMultiple(btnClinicalOcclusalMolarRelationship, "molarRelations", getValue(details, "molarRelations"));
		//iamge toooth selection
		clickOn(By.xpath("//*[text()='Which teeth are in cross-bite?*']/following-sibling::div//span[text()=' present in either arch']"), "Checkbox Button");
		takeScreenshot();
		clickOn(btnNext, "Next Button");

		enterText(txtDentistInstrPerspectiveOne, "dentistPerspectiveOne", getValue(details, "dentistPerspectiveOne"));
		enterText(txtDentistInstrPerspectiveTwo, "dentistPerspectiveTwo", getValue(details, "dentistPerspectiveTwo"));
		enterText(txtDentistInstrPerspectiveThree, "dentistPerspectiveThree", getValue(details, "dentistPerspectiveThree"));
		enterText(txtDentistInstrComprehensiveCompromisedOutcomes, "comprehensiveCompromisedOutcomes", getValue(details, "comprehensiveCompromisedOutcomes"));
		takeScreenshot();
		moveToElement(rbDentistInstrTreatmentPrefIPR);
		selectRadioButton(rbDentistInstrTreatmentPrefIPR,"patientAcceptableInterventionsIPR" ,getValue(details, "patientAcceptableInterventions.IPR") );
		selectRadioButton(rbDentistInstrTreatmentPrefAttachments,"patientAcceptableInterventionsattachments" ,getValue(details, "patientAcceptableInterventions.attachments") );
		selectRadioButton(rbDentistInstrTreatmentPrefAuxiliaries,"patientAcceptableInterventionsauxiliaries" ,getValue(details, "patientAcceptableInterventions.auxiliaries") );
		if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
			clickOn(btnDownloadAlignerFittingConsent, "Aligner Fitting consent form Download Button");
			String parentwidnow = browser.getWindowHandle();
			boolean status = navigateToSecondWindow();
			if(status==true) {
				browser.close();
				navigatoToParentWindow(parentwidnow);
			}
		}
		//add validation for download

		selectOptionDropDown(lstDentistInstrPhasing, "diagnosis_phasingPreference", getValue(details, "phasingPreference"));
		if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
			clickOn(btnDownloadCaseForm, "Download Case Form Button");
		}

		//upload files

		//upload files
		if(getValue(details, "extraOralImages[0]")!=null && !getValue(details, "extraOralImages[0]").equals("")) {
			clickOn(weExtraOralOrthodonticPhotographs, "ExtraOralOrthodonticPhotographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("Are my photos hitting the mark?","Are your extra-oral photos hitting the mark?");
			}
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[0]"));
			validateUploadFiles("Upload 4 beautiful extra-oral photographs.", 1, 4);
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[1]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[2]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[3]"));
			validateUploadFiles("Upload 4 beautiful extra-oral photographs.", 4, 4);
		}
		if(getValue(details, "intraOralImages[0]")!=null && !getValue(details, "intraOralImages[0]").equals("")) {
			clickOn(weIntraOralOrthodonticPhotographs, "IntraOralOrthodonticPhotographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("Are my photos up to scratch?","Are your photos up to scratch?");
			}
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[0]"));
			validateUploadFiles(" intraoral photographs.", 1, 5);
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[1]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[2]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[3]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[4]"));
			validateUploadFiles(" intraoral photographs.", 5, 5);
		}
		if(isElementPresent(btnDontShowAgain)) {
			clickOn(btnDontShowAgain, "Dont Show Again");
		}
		if(getValue(details, "radioGraphImgs[0]")!=null && !getValue(details, "radioGraphImgs[0]").equals("")) {
			clickOn(weRadiographsDuo, "Optional Radiographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("your radiographs please capture these in your clinical","What should I be looking for in my radiographs for orthodontics?");
			}
			uploadImage(imgInputRadiographsImages, getValue(details, "radioGraphImgs[0]"));
			sleep(2000);
		}	
		clickOn(weImpressions, "Impressions");
		sleep(2000);
		selectOptionDropDown(lstImpressionType, "ImpressionType", getValue(details,"impressionType"));
		if(getValue(details,"impressionType").contains("Silicone Impressions")) {
			enterText(txtPostageDate, "PostageDate", getValue(details,"postageDate"));
		}else if(getValue(details,"impressionType").contains("Digital Intra")) {
			waitForElement(lstImpressionScannerType);
			selectOptionDropDown(lstImpressionScannerType, "ImpressionScannerType", getValue(details,"ImpressionScannerType"));
			waitForElement(imgImpressionScannerTypeStl);
			uploadImage(imgImpressionScannerTypeStl, getValue(details, "imprImages") );
			waitForElement(cbAgreeTerms);
			if(isElementPresent(By.xpath("//div[@role='dialog']"))) {
				String popupTitle = getText(By.xpath("//div[@role='dialog']//h2"));
				passed("Warning message is displayed as expected "+popupTitle);				
				isElementDisplayed(btnContinue, "Continue Button in Warning dialog");
				isElementDisplayed(btnDeleteFiles, "Delete Files Button in Warning dialog");
			}
			//clickOn(cbAgreeTerms, "AgreeTerms Checkbox");
		}else {

		}
		takeScreenshot();

	}

	public void validateHelp(String linkText,String helpTitle) {
		if(isElementPresent(By.xpath("//div[@role='tabpanel']//*[contains(text(),'"+linkText+"')]"))) {
			WebElement weHelpText = browser.findElement(By.xpath("//div[@role='tabpanel']//*[contains(text(),'"+linkText+"')]"));
			WebElement weHelp = weHelpText.findElement(By.xpath("./following-sibling::span/img"));
			clickOn(weHelp, "Help Icon");
			waitForElement(By.xpath("//div[@class='ant-popover-title']"));
			sleep(2000);
			if(isElementPresent(By.xpath("//div[contains(text(),'"+helpTitle+"')]"))){
				passed("Help section is displayed as expected with title "+helpTitle);
			}else {
				fail("Detailed help section is not displayed "+helpTitle);
			}
			takeScreenshot();
			if(isElementPresent(By.xpath("//div[contains(text(),'"+helpTitle+"')]/following-sibling::div/img"))){
				clickOn(By.xpath("//div[contains(text(),'"+helpTitle+"')]/following-sibling::div/img"), "Close Help section");
			}
		}else {
			fail("Help Link is displayed as expected "+linkText );
		}
	}

	public void validateUploadFiles(String section,int current,int total) {
		if(isElementPresent(By.xpath("//div[text()='"+section+"']"))) {
			WebElement we = browser.findElement(By.xpath("//div[text()='"+section+"']"));
			String text = we.findElement(By.xpath("./../following-sibling::div/span")).getText();
			if(text.equals(current+"/"+ total)) {
				passed("The Uploaded file count is as expected "+current+"/"+ total);
			}else {
				fail("The uploaded file count is wrong, Actual is "+text + ", But expected is "+current+"/"+ total);
			}
			takeScreenshot();
		}else {
			fail("Follwong section is not displayed "+ section);
		}
	}


	public void validateMandatoryFields() {
		waitForElement(txtFirstName);
		clickOn(btnSubmitCase, "Submit Case Button");
		sleep(3000);
		validateErrorMessageOfField(txtFirstName, "First Name", "Please enter the patient's first name");
		validateErrorMessageOfField(txtLastName, "Last Name", "Please enter the patient's last name");
		validateErrorMessageOfField(txtDOB, "Date Of Birth", "Please enter the patient's date of birth");
		validateErrorMessageOfField(lstClinicName, "Clinic Name", "Please select clinic");
		validateErrorMessageOfField(lstDentalAndMedicalFit, "Dental Medical Fit", "Please confirm");
		validateErrorMessageOfField(mlstTreatmentPlanObjectives, "TreatmentPlanObjectives", "Please select at least one treatment plan objective");
		validateErrorMessageOfField(rbArchesToTreat, "Arches to treat", "Arches in Treatment is required");

	}

	public void validateOtherMandatoryFields() {
		waitForElement(txtFirstName);
		HashMap<String, Object> details = convertJsonToMap("./src/test/resources/json/solo/soloMandatory.json");
		enterSoloCaseDetails(details);
		clickOn(btnSubmitCase, "Submit Case Button");
		sleep(3000);
		validateErrorMessageOfField(txtBiteRampsTypeAdditionalDetails, "BiteRampsTypeAdditionalDetails", "Please enter details");
		validateErrorMessageOfField(txtAnteriorPosteriorAdditionalDetails, "AnteriorPosteriorAdditionalDetails", "Please enter details");
		validateErrorMessageOfField(txtPosteriorCrossBiteAdditionalDetails, "PosteriorCrossBiteAdditionalDetails", "Please enter details");
		validateErrorMessageOfField(txtPostageDate, "Postage Date", "Please select postage date");		
	}
	@FindBy(xpath="//*[@role='dialog']//button/span[text()='Archive']")
	private WebElement btnArchive;

	@FindBy(xpath="//*[@role='dialog']//*[@id='reason']")
	private WebElement txtReason;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weSuccessMessage;

	@FindBy(xpath = "//div[text()='Draft Submission']")
	private WebElement weDraftSubmission;



	public void validateSaveAsDraftSolo() {
		waitForElement(txtFirstName);
		HashMap<String, Object> details = convertJsonToMap("./src/test/resources/json/solo/soloDraft.json");
		enterSoloCaseDetails(details);
		clickOn(btnSaveAsdraft, "Save As Draft Button");
		sleep(3000);
		waitForElement(weDraftSubmission);
		clickOn(weDraftSubmission, "Draft Submission tab");
		waitForElement(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']/div/div/div/div/div[2]/span[2]"));
		if(isElementPresent(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']/div/div/div/div/div[2]/span[2]"))) {
			String str = getText(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']/div/div/div/div/div[2]/span[2]"));
			if(str.contains(Values.patientFirstName + " "+ Values.patientLastName)) {
				takeScreenshot();
				passed(Values.patientFirstName + " "+ Values.patientLastName + " patient is saved in draft section successully");
				clickOn(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']//button/span[text()='Edit']"), "Edit Button");
				waitForElement(txtFirstName);
				String actFirstName = txtFirstName.getAttribute("value").trim();
				String actLastName = txtLastName.getAttribute("value").trim();
				if(actFirstName.equals(Values.patientFirstName)&& actLastName.equals(Values.patientLastName)) {
					passed("Details are saved successfully");
					takeScreenshot();
				}else {
					fail("Saved details are not present in the record");
					takeScreenshot();
				}
				clickOn(btnSaveAsdraft, "Save As Draft Button");				
			}else {
				fail(Values.patientFirstName + " "+ Values.patientLastName + " patient is not saved in draft section");
			}
		}else {
			fail(Values.patientFirstName + " "+ Values.patientLastName + " patient is not saved in draft section");
		}
	}
	
	public void validateArchive() {
		waitForElement(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']//button/span[text()='Archive']"));
		clickOn(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']//button/span[text()='Archive']"), "Archive Button");
		waitForElement(btnArchive);
		if (isElementPresent(btnArchive)) {
			passed("Dialog pop up opened as expected");
			clickOn(btnArchive, "Archive Button");
			sleep(2000);
			validateErrorMessageOfField(txtReason, "Reason", "Please enter why you’re archiving this case");
			takeScreenshot();
			enterText(txtReason, "Reason", "As part of the Automation testing");
			clickOn(btnArchive, "Archive Button");
			sleep(2000);
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Toaster message is displayed as expected");
				String actSuccMsg = getText(weSuccessMessage);
				if(actSuccMsg.contains(Constants.DENTIST_SAVE_AS_DRAFT_ARCHIVE)) {
					passed("Success Toaster Message is displayed as expected "+actSuccMsg);
				}else {
					fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_SAVE_AS_DRAFT_ARCHIVE);
				}
				takeScreenshot();
			}else {
				fail("Success toaster message is not displayed");
			}
			takeScreenshot();
		}
	}


	public void validateMandatoryUploadFilesSolo() {
		waitForElement(txtFirstName);
		HashMap<String, Object> details = convertJsonToMap("./src/test/resources/json/solo/soloDraft.json");
		enterSoloCaseDetails(details);
		clickOn(btnSubmitCase, "Submit Case Button");
		sleep(2000);
		if(weExtraOralOrthodonticPhotographs.getAttribute("class").contains("text-red")) {
			passed("Mandatory Field : ExtraOralOrthodonticPhotographs link is displayed in red colour as expected");
		}else {
			fail("Mandatory Field : ExtraOralOrthodonticPhotographs link is not displayed in red colour");
		}
		if(weIntraOralOrthodonticPhotographs.getAttribute("class").contains("text-red")) {
			passed("Mandatory Field : IntraOralOrthodonticPhotographs link is displayed in red colour as expected");
		}else {
			fail("Mandatory Field : IntraOralOrthodonticPhotographs link is not displayed in red colour");
		}
		if(weRadiographs.getAttribute("class").contains("text-red")) {
			fail("Non Mandatory Field : Radiographs link is not displayed in red colour as expected");
		}else {
			passed("Non Mandatory Field : Radiographs link is displayed in red colour");
		}
		if(weImpressions.getAttribute("class").contains("text-red")) {
			passed("Mandatory Field : Impressions link is displayed in red colour as expected");
		}else {
			fail("Mandatory Field : Impressions link is not displayed in red colour");
		}
	}

	public void validateMandatoryFieldsForDuo() {
		waitForElement(txtFirstName);
		clickOn(btnSubmitCase, "Submit Case Button");
		sleep(3000);
		validateErrorMessageOfField(txtFirstName, "First Name", "Please enter the patient's first name");
		validateErrorMessageOfField(txtLastName, "Last Name", "Please enter the patient's last name");
		validateErrorMessageOfField(txtDOB, "Date Of Birth", "Please enter the patient's date of birth");
		validateErrorMessageOfField(lstClinicName, "Clinic Name", "Please select clinic");

		validateErrorMessageOfField(lstPreviousTreatmentExtensiveGeneralDental, "PreviousTreatmentExtensiveGeneralDental", "Please select one option");
		validateErrorMessageOfField(lstPreviousTreatmentOrthodontics, "PreviousTreatmentOrthodontics", "Please select one option");
		validateErrorMessageOfField(lstPreviousTreatmentFacialAesthetics, "PreviousTreatmentFacialAesthetics", "Please select one option");

		validateErrorMessageOfField(lstTreatmentAimsDiagnosis_arches, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(txtTreatmentAimsPatientMotivationAndConcern, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(lstTreatMentAimsFurtherTreatments, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(lstTreatMentAimsConsent, "PreviousTreatmentFacialAesthetics", "Please select one option");

		validateErrorMessageOfField(lstClinicalDentalHealthStatus, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(lstClinicalOralHygiene, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(txtClinicalBPE, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(lstClinicalGingivalBiotype, "PreviousTreatmentFacialAesthetics", "Please select one option");
		validateErrorMessageOfField(lstClinicalToothSurfaceLoss, "PreviousTreatmentFacialAesthetics", "Please select one option");
		//add more validation for clinical examination

		validateErrorMessageOfField(txtDentistInstrComprehensiveCompromisedOutcomes, "PreviousTreatmentFacialAesthetics", "Please select one option");
		//add more validation for Dentist Instructions
		validateErrorMessageOfField(lstDentistInstrPhasing, "PreviousTreatmentFacialAesthetics", "Please select one option");


	}

	public void validateMandatoryUploadFilesDuo() {
		waitForElement(txtFirstName);
		HashMap<String, Object> details = convertJsonToMap("./src/test/resources/json/duo/duo_draft.json");
		enterDuoCaseDetails(details);
		clickOn(btnSubmitCase, "Submit Case Button");
		sleep(2000);
		if(weExtraOralOrthodonticPhotographs.getAttribute("class").contains("text-red")) {
			passed("Mandatory Field : ExtraOralOrthodonticPhotographs link is displayed in red colour as expected");
		}else {
			fail("Mandatory Field : ExtraOralOrthodonticPhotographs link is not displayed in red colour");
		}
		if(weIntraOralOrthodonticPhotographs.getAttribute("class").contains("text-red")) {
			passed("Mandatory Field : IntraOralOrthodonticPhotographs link is displayed in red colour as expected");
		}else {
			fail("Mandatory Field : IntraOralOrthodonticPhotographs link is not displayed in red colour");
		}
		if(weRadiographsDuo.getAttribute("class").contains("text-red")) {
			fail("Non Mandatory Field : Radiographs link is not displayed in red colour as expected");
		}else {
			passed("Non Mandatory Field : Radiographs link is displayed in red colour");
		}
		if(weImpressions.getAttribute("class").contains("text-red")) {
			passed("Mandatory Field : Impressions link is displayed in red colour as expected");
		}else {
			fail("Mandatory Field : Impressions link is not displayed in red colour");
		}
	}

	public void validateSaveAsDraftDuo() {
		waitForElement(txtFirstName);
		HashMap<String, Object> details = convertJsonToMap("./src/test/resources/json/duo/duo_draft.json");
		enterDuoCaseDetails(details);
		clickOn(btnSaveAsdraft, "Save As Draft Button");
		sleep(3000);
		waitForElement(weDraftSubmission);
		clickOn(weDraftSubmission, "Draft Submission tab");
		//waitForElement(By.xpath("//div[contains(@id,'panel-DRAFT')]//div[@class='patient-list-container']/div/div/div/div/div[2]/span[2]"));
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		waitForElement(By.xpath("//div[contains(@id,'panel-DRAFT')]//span[text()='"+patientName+"']"));
		if(isElementPresent(By.xpath("//div[contains(@id,'panel-DRAFT')]//span[text()='"+patientName+"']"))) {
			String str = getText(By.xpath("//div[contains(@id,'panel-DRAFT')]//span[text()='"+patientName+"']"));
			if(str.contains(Values.patientFirstName + " "+ Values.patientLastName)) {
				takeScreenshot();
				passed(Values.patientFirstName + " "+ Values.patientLastName + " patient is saved in draft section successully");
				clickOn(By.xpath("//div[contains(@id,'panel-DRAFT')]//span[text()='"+patientName+"']/../following-sibling::div//button/span[text()='Edit']"), "Edit Button");

				waitForElement(tabPatientSummary);
				clickOn(tabPatientSummary, "Patient Summary Tab");
				waitForElement(txtFirstName);
				sleep(1000);
				String actFirstName = txtFirstName.getAttribute("value").trim();
				String actLastName = txtLastName.getAttribute("value").trim();
				if(actFirstName.equalsIgnoreCase(Values.patientFirstName)&& actLastName.equalsIgnoreCase(Values.patientLastName)) {
					passed("First name and Last name are saved successfully");
					takeScreenshot();
				}else {
					fail("First name and Last name are not same as saved");
					takeScreenshot();
				}
				String patientSummary = tabPatientSummary.getAttribute("class");
				String TreatmentAims = tabTreatmentAims.getAttribute("class");
				String ClinicalExamination = tabClinicalExamination.getAttribute("class");
				String DentistInstructions = tabDentistInstructions.getAttribute("class");
				if(patientSummary.contains("finished-step")) {
					passed("Patient Summary : all the details are saved successfully");
				}else {
					fail("Patient Summary : all the details are not saved");
				}
				if(TreatmentAims.contains("finished-step")) {
					passed("Treatment Aims : all the details are saved successfully");
				}else {
					fail("Treatment Aims : all the details are not saved");
				}
				if(ClinicalExamination.contains("finished-step")) {
					passed("Clinical Examination : all the details are saved successfully");
				}else {
					fail("Clinical Examination : all the details are not saved");
				}
				if(DentistInstructions.contains("finished-step")) {
					passed("Dentist Instructions : all the details are saved successfully");
				}else {
					fail("Dentist Instructions : all the details are not saved");
				}

				clickOn(btnSaveAsdraft, "Save As Draft Button");
				
			}else {
				fail(Values.patientFirstName + " "+ Values.patientLastName + " patient is not saved in draft section");
			}
		}else {
			fail(Values.patientFirstName + " "+ Values.patientLastName + " patient is not saved in draft section");
		}
	}
	
	public void validateArchiveDuo(){
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		waitForElement(By.xpath("//div[contains(@id,'panel-DRAFT')]//span[text()='"+patientName+"']/../following-sibling::div//button/span[text()='Archive']"));
		clickOn(By.xpath("//div[contains(@id,'panel-DRAFT')]//span[text()='"+patientName+"']/../following-sibling::div//button/span[text()='Archive']"), "Archive Button");
		waitForElement(btnArchive);
		if (isElementPresent(btnArchive)) {
			passed("Dialog pop up opened as expected");
			clickOn(btnArchive, "Archive Button");
			sleep(2000);
			validateErrorMessageOfField(txtReason, "Reason", "Please enter why you’re archiving this case");
			takeScreenshot();
			enterText(txtReason, "Reason", "As part of the Automation testing");
			clickOn(btnArchive, "Archive Button");
			sleep(2000);
			sleep(2000);
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Toaster message is displayed as expected");
				String actSuccMsg = getText(weSuccessMessage);
				if(actSuccMsg.contains(Constants.DENTIST_SAVE_AS_DRAFT_ARCHIVE)) {
					passed("Success Toaster Message is displayed as expected "+actSuccMsg);
				}else {
					fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_SAVE_AS_DRAFT_ARCHIVE);
				}
				takeScreenshot();
			}else {
				fail("Success toaster message is not displayed");
			}
			takeScreenshot();
		}
	}
	

	public void selectRecordFromSpecifiedSection(String dataNodeKey, String patientName) {
		if(isElementPresent(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"))) {
			clickOn(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"), dataNodeKey + " Section");
			waitForElement(By.xpath("//div[contains(@id,'"+dataNodeKey+"')]//span[text()='"+patientName+"']/../following-sibling::div//button[@id='view-patient']"));
			clickOn(By.xpath("//div[contains(@id,'"+dataNodeKey+"')]//span[text()='"+patientName+"']/../following-sibling::div//button[@id='view-patient']"), patientName+" View Button");
			waitForElement(By.xpath("//*[@data-node-key='treatment-designs']"));
			if(isElementPresent(By.xpath("//*[@data-node-key='treatment-designs']"))) {
				passed("Patient details is displayed as expected");
			}else {
				fail("Patient details is not displayed ");
			}
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Required section is not displayed, data-node-key passed is not valid "+dataNodeKey);
		}
	}
	
	public void navigateToTreatmentDesignsAndValidateStatus(String designNum,String caseType,String Status,String revisionRequested) {
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.id("view-treatment-design"));
			sleep(Values.sleepTime*3);
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath("./div[1]/span[2]")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =true;
					passed("Design Number is found "+designNum);
					String actCaseType = weDesign.findElement(By.xpath("./div[3]/span[2]")).getText();
					if(actCaseType.equalsIgnoreCase(caseType)) {
						passed("Case type is as expected "+actCaseType);
					}else {
						fail("Case type is not valid actual is "+actCaseType + ", But expected is "+caseType);
					}
					String actDesignStatus = weDesign.findElement(By.xpath("./div[4]/div")).getText();
					if(actDesignStatus.equalsIgnoreCase(Status)) {
						passed("Design status is as expected "+actDesignStatus);
					}else {
						fail("Design status is not valid actual is "+actDesignStatus + ", But expected is "+Status);
					}
					String actRevisionRequested = weDesign.findElement(By.xpath("./div[5]/div")).getText();
					if(actRevisionRequested.equalsIgnoreCase(revisionRequested)) {
						passed("Is Revision Requested is as expected "+actRevisionRequested);
					}else {
						fail("Is Revision Requested is not valid actual is "+actRevisionRequested + ", But expected is "+revisionRequested);
					}
					takeScreenshot();
					WebElement btnView = weDesign.findElement(By.xpath("./div[6]/button"));
					clickOn(btnView, "View Button");
					waitForElement(weTreatmentDesignHeader);
					if(isElementPresent(weTreatmentDesignHeader)) {
						passed("Treatment Design details page is displayed as expected");
					}else {
						takeScreenshot();
						failAssert("Treatment Design details page is not displayed");
					}
					takeScreenshot();
					break;
				}
			}
			if(!blnFound) {
				takeScreenshot();
				failAssert("Design with expected design number is not found , Design Number "+designNum);
			}
		}else {
			failAssert("Unable to navigate to Treatment Designs tab");
		}
	}
	
	public void navigateToTreatmentDesignsAndSelectDesgin(String designNum,String designStatus) {
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.id("view-treatment-design"));
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath("./div[1]/span[2]")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
					passed("Design Number is found "+designNum);
					String actDesignStatus = weDesign.findElement(By.xpath("./div[4]/div")).getText();
					if(actDesignStatus.equalsIgnoreCase(designStatus)) {
						passed("Design status is as expected");
					}else {
						fail("Design status is not valid actual is "+actDesignStatus + ", But expected is "+designStatus);
					}
					takeScreenshot();
					WebElement btnView = weDesign.findElement(By.xpath("./div[6]/button"));
					clickOn(btnView, "View Button");
					waitForElement(weTreatmentDesignHeader);
					if(isElementPresent(weTreatmentDesignHeader)) {
						passed("Treatment Design details page is displayed as expected");
					}else {
						takeScreenshot();
						failAssert("Treatment Design details page is not displayed");
					}
					takeScreenshot();
				}
			}
			if(blnFound) {
				takeScreenshot();
				failAssert("Design with expected design number is not found , Design Number "+designNum);
			}
		}else {
			failAssert("Unable to navigate to Treatment Designs tab");
		}
	}

	
	public void createPatientProposal() {
		waitForElement(btnCreatePatientProposal);
		if (isElementPresent(btnCreatePatientProposal)) {
			clickOn(btnCreatePatientProposal, "Create Patient Proposal button");
			waitForElement(txtEstDuration);
			enterText(txtEstDuration, "Estimated Duration", "5");
			enterText(txtTreatmentGoals, "Treatment Goals", "created for Automation Testing ");
			addNewTreatmentQoute("Whitening,Retainers removable only,Composite Bonding");
			clickOn(btnAddNewQoute, "Add New Qoute Button");
			addNewTreatmentQoute("Invisible Aligners,Replacement Aligners,Retainers removable only");
			clickOn(btnPreviewProposal, "Preview Proposal Button");
			waitForElement(wePatientPropsalPreviewHeader);
			if(isElementPresent(wePatientPropsalPreviewHeader)) {
				passed("Preview Patient Proposal section  is displayed as expected");
				clickOn(btnClosePatientProposalPreview, "Close button");
			}else {
				fail("Preview Patient Proposal section is not displayed");
			}
			takeScreenshot();
			Values.currentDateTime = new Date();
			clickOn(cbSendCopyToMe, "Send Copy to me Checkbox");
			clickOn(btnSendToPatient, "Send to Patient Button");
			takeScreenshot();
			waitForElement(weSuccessMessage);
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Toaster message is displayed as expected");
				String actSuccMsg = getText(weSuccessMessage);
				if(actSuccMsg.contains(Constants.DENTIST_PATIENT_PROPOSAL_SUCC_MSG)) {
					passed("Success Toaster Message is displayed as expected "+actSuccMsg);
				}else {
					fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_PATIENT_PROPOSAL_SUCC_MSG);
				}
				takeScreenshot();
			}else {
				fail("Success toaster message is not displayed");
			}
		}else {
			fail("Create Patient Proposal button is not displayed");
		}
		
	}
	
	public void updatePatientProposal() {
		waitForElement(btnEditPatientProposal);
		if (isElementPresent(btnEditPatientProposal)) {
			clickOn(btnEditPatientProposal, "Edit Patient Proposal button");
			waitForElement(txtEstDuration);
			txtEstDuration.clear();
			enterText(txtEstDuration, "Estimated Duration", "6");
			txtTreatmentGoals.clear();
			enterText(txtTreatmentGoals, "Treatment Goals", "created for Automation Testing Updated");
			List<WebElement> wesEdit = browser.findElements(By.xpath("//button/span[text()='Edit']"));
			for(int i=0;i<wesEdit.size();i++) {
				wesEdit = browser.findElements(By.xpath("//button/span[text()='Edit']"));
				clickOn(wesEdit.get(i), "Edit Link");
				waitForElement(txtPrice);
				enterText(txtPrice, "Price Text Box", generateRandomString(3, "numeric"));
				waitForElement(By.xpath("//button/span[text()='Save']"));
				WebElement btnSave = browser.findElement(By.xpath("//button/span[text()='Save']"));
				clickOn(btnSave, "Save Button");
			}
			clickOn(btnPreviewProposal, "Preview Proposal Button");
			waitForElement(wePatientPropsalPreviewHeader);
			if(isElementPresent(wePatientPropsalPreviewHeader)) {
				passed("Preview Patient Proposal section  is displayed as expected");
				clickOn(btnClosePatientProposalPreview, "Close button");
			}else {
				fail("Preview Patient Proposal section is not displayed");
			}
			takeScreenshot();
			Values.currentDateTime = new Date();
			clickOn(cbSendCopyToMe, "Send Copy to me Checkbox");
			clickOn(btnSendToPatient, "Send to Patient Button");
			takeScreenshot();
			waitForElement(weSuccessMessage);
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Toaster message is displayed as expected");
				String actSuccMsg = getText(weSuccessMessage);
				if(actSuccMsg.contains(Constants.DENTIST_PATIENT_PROPOSAL_SUCC_MSG)) {
					passed("Success Toaster Message is displayed as expected "+actSuccMsg);
				}else {
					fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_PATIENT_PROPOSAL_SUCC_MSG);
				}
				takeScreenshot();
			}else {
				fail("Success toaster message is not displayed");
			}
		}else {
			fail("Create Patient Proposal button is not displayed");
		}
		
	}
	
	public void addNewTreatmentQoute(String QouteOptions) {
		waitForElement(btnEditTreatmentQouteName);
		List<WebElement> weEditButton = browser.findElements(By.xpath("//button/*[@aria-label='edit']"));
		clickOn(weEditButton.get(weEditButton.size()-1), "Edit Button");
		enterTextAndSendEnterKey(txttreatmentQouteName, "Treatment Qoute Name", "Qoute "+ generateRandomString(5, "alpha"));
		List<WebElement> weAddButton = browser.findElements(By.xpath("//button/*[text()='+ Add']"));
		clickOn(weAddButton.get(weAddButton.size()-1), "Add Button");
		waitForElement(By.xpath("//div[@role='dialog']//table"));
		sleep(Values.sleepTime*2);
		WebElement weTable = browser.findElement(By.xpath("//div[@role='dialog']//table"));
		selectCheckboxFromTable(weTable, QouteOptions);
		clickOn(btnAddToQoute, "Add To Qoute Button");
	}
	
	public void navigateToPatientProposal() {
		waitForElement(tabPatientProposal);
		boolean blnFound =false;
		if(isElementPresent(tabPatientProposal)) {
			clickOn(tabPatientProposal, "Patients Proposal Tab");
			waitForElement(By.id("patient-proposal"));
			passed("Navigated to Patients proposal page successfully");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Unable to navigate to Treatment Designs tab");
		}
	}
	
	public void validateRewardsIfAvailable() {
		List<WebElement> wesRewards =browser.findElements(By.xpath("//*[@name='skus']"));
		List<WebElement> wesTotalPrice =browser.findElements(By.xpath("//h5[text()='Total Price:']/following-sibling::div/h5"));
		String totalPriceBeforeOffer = wesTotalPrice.get(0).getText();
		if(wesRewards.size()>0) {
			info("Total rewards displayed are "+wesRewards.size());
			clickOn(wesRewards.get(0), "First Reward CheckBox");
			sleep(5000);
			wesTotalPrice =browser.findElements(By.xpath("//h5[text()='Total Price:']/following-sibling::div/h5"));
			String totalPriceAfterOffer = wesTotalPrice.get(1).getText();
			if(!(totalPriceAfterOffer.equals(totalPriceBeforeOffer))) {
				passed("Rewards Applied successfully , Before reward total amount is "+totalPriceBeforeOffer + ", and after is "+totalPriceAfterOffer);
			}else {
				fail("Rewards not Applied , Before and after reward applied total amount is same "+totalPriceBeforeOffer );
			}
		}else {
			info("Rewards not displayed for the manufacturer selected, Unable to validate Rewards Functionality");
		}
		takeScreenshot();
	}
	
	public void orderClearAligners() {
		String expSKUname =  Values.frameworkProperty.getProperty("manufacturer.sku.name");
		String expSKUdesc = Values.frameworkProperty.getProperty("manufacturer.sku.desc");
		boolean blnFound = false;
		waitForElement(btnOrderClearAligners);
		if(isElementPresent(btnOrderClearAligners)) {
			clickOn(btnOrderClearAligners, "Order Clear Aligners Button");
			waitForElement(btnViewskuCardDetails);
			List<WebElement> wesSkucardName = browser.findElements(By.xpath("//div[contains(@class,'sku-card-market-container')]"));
			for(int i=0;i<wesSkucardName.size();i++) {
				String skuname = wesSkucardName.get(i).getText();
				if(skuname.contains(expSKUname)&&skuname.contains(expSKUdesc)) {
					blnFound = true;
					List<WebElement> btnSkucardDetails = browser.findElements(By.id("view-sku-card-details"));
					clickOn(btnSkucardDetails.get(i), "SKU Card Details Button");
				}
			}
			if(!blnFound) {
				takeScreenshot();
				failAssert("Expected SKU not found with name as "+ expSKUname + ", and description as "+expSKUdesc);
			}
			waitForElement(cbAggreeTermsSku);
			String strAmount =  getText(By.xpath("//div[@role='dialog']//span[text()='Price']/following-sibling::div"));
			strAmount = strAmount.substring(1);
			strAmount = strAmount.split(".")[0];
			Values.dentistFee = strAmount;
			clickOn(cbAggreeTermsSku, "Aggree Terms and Conditions");
			takeScreenshot();
			validateRewardsIfAvailable();
			clickOn(btnAddToBasket, "Add To Basket Button");
			waitForElement(By.xpath("//div[@role='dialog']//div[text()='Straight Edges']/following-sibling::div//button"));
			sleep(Values.sleepTime);
			clickOn(By.xpath("//div[@role='dialog']//div[text()='Straight Edges']/following-sibling::div//button"), "Straight Edges type");
			waitForElement(btnGoToOrderSummary);
			takeScreenshot();
			clickOn(btnGoToOrderSummary, "Go To Order Summary button");
			waitForElement(btnAcceptAndOrder);
			takeScreenshot();
			clickOn(btnAcceptAndOrder, "Accept and Order button");
			waitForElement(btnSKIP);
			if(isElementPresent(btnSKIP)) {
				clickOn(btnSKIP, "Skip Button");
			}
			waitForElement(tabOrderSummary);
			if(isElementPresent(tabOrderSummary)) {
				passed("Order Summary page is displayed as expected");
			}else {
				fail("Order Summary page is not displayed as expected");
			}
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Order Clear Alligners button is not displayed");
		}
	}
	
	public void orderClearAlignersAndValidateAmount(String expSKUname,String expSKUdesc,String expAmount) {
		//String expSKUname =  Values.frameworkProperty.getProperty("manufacturer.sku.name");
		//String expSKUdesc = Values.frameworkProperty.getProperty("manufacturer.sku.desc");
		boolean blnFound = false;
		waitForElement(btnOrderClearAligners);
		if(isElementPresent(btnOrderClearAligners)) {
			clickOn(btnOrderClearAligners, "Order Clear Aligners Button");
			waitForElement(btnViewskuCardDetails);
			List<WebElement> wesSkucardName = browser.findElements(By.xpath("//div[contains(@class,'sku-card-market-container')]"));
			for(int i=0;i<wesSkucardName.size();i++) {
				String skuname = wesSkucardName.get(i).getText();
				if(skuname.contains(expSKUname)&&skuname.contains(expSKUdesc)) {
					blnFound = true;
					List<WebElement> btnSkucardDetails = browser.findElements(By.id("view-sku-card-details"));
					clickOn(btnSkucardDetails.get(i), "SKU Card Details Button");
				}
			}
			if(!blnFound) {
				takeScreenshot();
				failAssert("Expected SKU not found with name as "+ expSKUname + ", and description as "+expSKUdesc);
			}
			
			waitForElement(cbAggreeTermsSku);
			takeScreenshot();
			String strAmount =  getText(By.xpath("//div[@role='dialog']//span[text()='Price']/following-sibling::div"));
			strAmount = strAmount.substring(1);
			strAmount = strAmount.split("[.]")[0];
			if(strAmount.equals(expAmount)) {
				passed("Amount displayed is as expected "+ strAmount);
			}else {
				fail("Amount displayed is not valid, Expected is "+ expAmount + " , But actual is "+strAmount);
			}
			Values.dentistFee = expAmount;
			takeScreenshot();
			clickOn(cbAggreeTermsSku, "Aggree Terms and Conditions");
			takeScreenshot();
			validateRewardsIfAvailable();
			clickOn(btnAddToBasket, "Add To Basket Button");
			waitForElement(By.xpath("//div[@role='dialog']//div[text()='Straight Edges']/following-sibling::div//button"));
			sleep(Values.sleepTime);
			clickOn(By.xpath("//div[@role='dialog']//div[text()='Straight Edges']/following-sibling::div//button"), "Straight Edges type");
			waitForElement(btnGoToOrderSummary);
			takeScreenshot();
			clickOn(btnGoToOrderSummary, "Go To Order Summary button");
			waitForElement(btnAcceptAndOrder);
			takeScreenshot();
			clickOn(btnAcceptAndOrder, "Accept and Order button");
			waitForElement(btnSKIP);
			if(isElementPresent(btnSKIP)) {
				clickOn(btnSKIP, "Skip Button");
			}
			waitForElement(tabOrderSummary);
			if(isElementPresent(tabOrderSummary)) {
				passed("Order Summary page is displayed as expected");
			}else {
				fail("Order Summary page is not displayed as expected");
			}
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Order Clear Alligners button is not displayed");
		}
	}
	
	public void createPatientProposalValidateError() {
		waitForElement(btnCreatePatientProposal);
		if (isElementPresent(btnCreatePatientProposal)) {
			clickOn(btnCreatePatientProposal, "Create Patient Proposal button");
			waitForElement(txtEstDuration);
			clickOn(btnSendToPatient, "Send to Patient Button");
			takeScreenshot();
			waitForElement(weToasterMsgDescription);
			if(isElementPresent(weToasterMsgDescription)) {
				passed("Toaster Error message is displayed as expected");
				String actSuccMsg = getText(weToasterMsgDescription);
				if(actSuccMsg.contains(Constants.DENTIST_PATIENT_PROPOSAL_ERROR_MANDATORY_MSG)) {
					passed("Error Toaster Message is displayed as expected "+actSuccMsg);
				}else {
					fail("Error Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_PATIENT_PROPOSAL_ERROR_MANDATORY_MSG);
				}
				takeScreenshot();
			}else {
				fail("Error toaster message is not displayed");
			}
		}else {
			fail("Create Patient Proposal button is not displayed");
		}
		
	}
	
	public void revisionRequest() {
		waitForElement(txtRevisionRequest);
		enterText(txtRevisionRequest, "Revision Request", "Revision Requested By Dentist");
		if(isElementPresent(btnSubmit)) {
			clickOn(btnSubmit, "Submit Button");
			
			//TODO : Success message not displaying
			waitForElement(By.xpath("//h5[text()='Revision Requested By Dentist']"));
			if(isElementPresent(By.xpath("//h5[text()='Revision Requested By Dentist']"))) {
				passed("Requested Revision is displayed in page");
				takeScreenshot();
			}else {
				fail("Requested Revision is not displayed in page");
				takeScreenshot();
			}			
		}else {
			takeScreenshot();
			failAssert("Submit button is not displayed in Revision Requests");
		}
	}
	
	@FindBy(id = "case-in-treatment-actions")
	private WebElement btnTreatmentActions;

	@FindBy(xpath = "//h2[text()='Treatment Check In Settings']")
	private WebElement weTreatmentCheckInSettings;
	
	@FindBy(xpath = "//*[@id='confirmConsent']/..")
	private WebElement cbConfirmConsent;

	@FindBy(id = "interval")
	private WebElement txtInterval;
	
	@FindBy(id = "checkInCount")
	private WebElement txtCheckInCount;
	
	@FindBy(id = "notificationType")
	private WebElement lstNotificationType;
	
	@FindBy(id = "start-treatment-checkin")
	private WebElement btnStartCheckIn;
	
	@FindBy(id = "update-treatment-checkin")
	private WebElement btnUpdateCheckIn;
	
	@FindBy(xpath = "//span[@aria-label='setting']")
	private WebElement btnEditTreatmentCheckIn;
	
	@FindBy(xpath = "//button/span[text()='Cancel check ins']")
	private WebElement btnCancelCheckIns;
	
	@FindBy(xpath = "//button/span[text()='Confirm']")
	private WebElement btnConfirm;
	
	@FindBy(xpath = "//button/span[text()='Close']")
	private WebElement btnClose;
	
	
	@FindBy(xpath = "//div/*[@data-node-key='treatment-checkins']")
	private WebElement tabTreatmentCheckIns;
	
	@FindBy(xpath = "//div[contains(@id,'panel-treatment-checkins')]")
	private WebElement panelTreatmentCheckIns;
	
	@FindBy(xpath = "//li[contains(@data-menu-id,'start_treatment_monitoring')]/span")
	private WebElement lnkStartTreatmentMoitoring;
	
	@FindBy(xpath = "//li[contains(@data-menu-id,'refinement_pro')]/span")
	private WebElement lnkStartRefinementDuo;
	
	@FindBy(xpath = "//li[contains(@data-menu-id,'refinement_lite')]/span")
	private WebElement lnkStartRefinementSolo;
	
	
	
	
	public void navigateToTreatmentCheckInPage() {
		waitForElement(tabTreatmentCheckIns);
		clickOn(tabTreatmentCheckIns, "Treatment Check Ins");
		waitForElement(panelTreatmentCheckIns);
		if(isElementPresent(panelTreatmentCheckIns)) {
			passed("Navigated to Treatment Check Ins page successfuly");
		}
		takeScreenshot();
	}
	
	public void startTreatmentMonitoring() {
		waitForElement(btnTreatmentActions);
		if(isElementPresent(btnTreatmentActions)) {
			clickOnSubMenu(btnTreatmentActions, lnkStartTreatmentMoitoring);
			waitForElement(weTreatmentCheckInSettings);
			if(isElementPresent(weTreatmentCheckInSettings)) {
				passed("Treatment Check In dialog is displayed as expected");
			}else {
				fail("Treatment Check In dialog is not displayed as expected");
			}
			takeScreenshot();
		}else {
			failAssert("Unable to click on Action button, Element not displayed");
		}
		takeScreenshot();
	}
	
	public void startRefinementSolo() {
		waitForElement(btnTreatmentActions);
		if(isElementPresent(btnTreatmentActions)) {
			clickOnSubMenu(btnTreatmentActions, lnkStartRefinementSolo);
			//clickOn(btnTreatmentActions, "Actions Link");
			//clickOn((By.xpath("//li[contains(@data-menu-id,'start_treatment_monitoring')]/span")), "Start Treatment Monitoring Link");
			waitForElement(btnSubmitCase);
			if(isElementPresent(btnSubmitCase)) {
				passed("Refinement Details form page is displayed");
			}else {
				fail("Refinement Details form page is not displayed");
			}
			takeScreenshot();
		}else {
			failAssert("Unable to click on Action button, Element not displayed");
		}
		takeScreenshot();
	}
	
	public void startRefinementDuo() {
		waitForElement(btnTreatmentActions);
		if(isElementPresent(btnTreatmentActions)) {
			clickOnSubMenu(btnTreatmentActions, lnkStartRefinementDuo);
			waitForElement(btnSubmitCase);
			if(isElementPresent(btnSubmitCase)) {
				passed("Refinement Details form page is displayed");
			}else {
				fail("Refinement Details form page is not displayed");
			}
			takeScreenshot();
		}else {
			failAssert("Unable to click on Action button, Element not displayed");
		}
		takeScreenshot();
	}
	
	public void enterDetailsInTreatmentCheckInSettingsPage() {
		
		clickOn(cbConfirmConsent, "Confirm Consent CheckBox");
		enterText(txtInterval, "Treatment Check In Interval Textbox ", "2");
		enterText(txtCheckInCount, "Treatment Check In Count ", "3");
		selectOptionDropDown(lstNotificationType, "Notifcation Type", "for every check In");
		Values.currentDateTime = new Date();
		clickOn(btnStartCheckIn, "Start Button");
		//success message
		takeScreenshot();
		waitForElement(weToasterMsgDescription);
		if(isElementPresent(weToasterMsgDescription)) {
			passed("Toaster Success message is displayed as expected");
			String actSuccMsg = getText(weToasterMsgDescription);
			if(actSuccMsg.contains(Constants.DENTIST_PATIENT_TREATMENT_CHECKIN_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_PATIENT_TREATMENT_CHECKIN_MSG);
			}
			takeScreenshot();
		}else {
			fail("Success toaster message is not displayed");
		}
	}

	
	public void ValidateTreatmentCheckInsStatus(String checkInNumber,String Status) {
		waitForElement(By.xpath("//div[contains(@id,'panel-treatment-checkins')]"));
		List<WebElement> wesActiveRecords = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-checkins')]//tr[@class='checkin-status-active']"));
		boolean blnFound = false;
		for(int i=0;i<wesActiveRecords.size();i++) {
			wesActiveRecords = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-checkins')]//tr[@class='checkin-status-active']"));
			String actCheckInNum = wesActiveRecords.get(i).findElement(By.xpath("./td[2]")).getText();
			if(actCheckInNum.equalsIgnoreCase(checkInNumber)) {
				blnFound = true;
				String actStatus = wesActiveRecords.get(i).findElement(By.xpath("./td[5]")).getText();
				if(actStatus.trim().equalsIgnoreCase(Status)) {
					passed("CheckIn Number "+checkInNumber +", Status is valid "+Status);
				}else {
					fail("CheckIn Number "+checkInNumber +", Status is not valid, Actual is "+actStatus + ",  But expected is "+Status);
				}
			}
		}
		if(!blnFound) {
			fail("Active Check In Number is not found "+ checkInNumber);
		}
		
	}
	
	public void ValidateTreatmentCheckInsCancelled(String checkInNumber) {
		waitForElement(By.xpath("//div[contains(@id,'panel-treatment-checkins')]"));
		List<WebElement> wesCancelledRecords = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-checkins')]//tr[@class='checkin-status-canceled']"));
		boolean blnFound = false;
		for(int i=0;i<wesCancelledRecords.size();i++) {
			wesCancelledRecords = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-checkins')]//tr[@class='checkin-status-canceled']"));
			String actCheckInNum = wesCancelledRecords.get(i).findElement(By.xpath("./td[2]")).getText();
			if(actCheckInNum.equalsIgnoreCase(checkInNumber)) {
				blnFound = true;
				passed("CheckIn Number is displayed in Cancelled Row "+checkInNumber +" as expected");
			}
		}
		if(!blnFound) {
			fail("Canceled Check In Number is not found "+ checkInNumber);
		}
	}
	
	public void ViewTreatmentCheckIns(String checkInNumber) {
		waitForElement(By.xpath("//div[contains(@id,'panel-treatment-checkins')]"));
		List<WebElement> wesActiveRecords = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-checkins')]//tr[@class='checkin-status-active']"));
		boolean blnFound = false;
		for(int i=0;i<wesActiveRecords.size();i++) {
			wesActiveRecords = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-checkins')]//tr[@class='checkin-status-active']"));
			String actCheckInNum = wesActiveRecords.get(i).findElement(By.xpath("./td[2]")).getText();
			if(actCheckInNum.equalsIgnoreCase(checkInNumber)) {
				blnFound = true;
				WebElement btnView = wesActiveRecords.get(i).findElement(By.xpath("./td[7]/button"));
				clickOn(btnView, "View Button");
				waitForElement(btnClose);
				clickOn(btnClose, "Close Button");
			}
		}
		if(!blnFound) {
			fail("Active Check In Number is not found "+ checkInNumber);
		}		
	}
	
	public void validateUpdateTreatmentCheckIn() {
		clickOn(btnEditTreatmentCheckIn, "Edit Treatment CheckIn");
		waitForElement(lstNotificationType);
		selectOptionDropDown(lstNotificationType, "Notifcation Type", "only if an issue");
		clickOn(btnUpdateCheckIn, "Update Check In");
		//success message
		takeScreenshot();
		waitForElement(weToasterMsgDescription);
		if(isElementPresent(weToasterMsgDescription)) {
			passed("Toaster Success message is displayed as expected");
			String actSuccMsg = getText(weToasterMsgDescription);
			if(actSuccMsg.contains(Constants.DENTIST_PATIENT_TREATMENT_CHECKIN_UPDATE_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_PATIENT_TREATMENT_CHECKIN_UPDATE_MSG);
			}
			sleep(5000);
			takeScreenshot();
		}else {
			fail("Success toaster message is not displayed");
		}
	}
	
	public void validateCancelTreatmentCheckIn() {
		clickOn(btnEditTreatmentCheckIn, "Edit Treatment CheckIn");
		clickOn(btnCancelCheckIns, "Cancel Check In");
		waitForElement(btnConfirm);
		clickOn(btnConfirm, "Confirm Button");
		takeScreenshot();
		waitForElement(weToasterMsgDescription);
		if(isElementPresent(weToasterMsgDescription)) {
			passed("Toaster Success message is displayed as expected");
			String actSuccMsg = getText(weToasterMsgDescription);
			if(actSuccMsg.contains(Constants.DENTIST_PATIENT_TREATMENT_CHECKIN_CANCEL_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DENTIST_PATIENT_TREATMENT_CHECKIN_CANCEL_MSG);
			}
			takeScreenshot();
		}else {
			fail("Success toaster message is not displayed");
		}
	}

	@FindBy(id = "refinementInfo_arches")
	private WebElement lstArchesInTreatment;
	
	@FindBy(id = "refinementInfo_stepReached")
	private WebElement lstRefinementInfo_stepReached;
	
	@FindBy(id = "refinementInfo_stepReachedComments")
	private WebElement txtRefinementInfo_stepReachedComments;
	
	@FindBy(id = "refinementInfo_treatmentBackground")
	private WebElement txtRefinementInfo_treatmentBackground;
	
	@FindBy(id = "refinementInfo_treatmentPlanAdditionalComments")
	private WebElement txtRefinementInfo_treatmentPlanAdditionalComments;
	
	public void enterRefinementDuoDetails(HashMap<String, Object> details) {
		selectOptionDropDown(lstArchesInTreatment, "ArchesInTreatment",getValue(details,"ArchesInTreatment"));
		selectMultiOptionDropDown(lstRefinementInfo_stepReached, "RefinementInfo_stepReached",getValue(details,"RefinementInfo_stepReached"));
		enterText(txtRefinementInfo_stepReachedComments, "RefinementInfo_stepReachedComments", getValue(details,"RefinementInfo_stepReachedComments"));
		enterText(txtRefinementInfo_treatmentBackground, "RefinementInfo_treatmentBackground", getValue(details,"RefinementInfo_treatmentBackground"));
		enterText(txtRefinementInfo_treatmentPlanAdditionalComments, "RefinementInfo_treatmentPlanAdditionalComments",getValue(details,"RefinementInfo_treatmentPlanAdditionalComments"));
		
		
		if(getValue(details, "extraOralImages[0]")!=null && !getValue(details, "extraOralImages[0]").equals("")) {
			clickOn(weExtraOralOrthodonticPhotographs, "ExtraOralOrthodonticPhotographs");
			sleep(2000);
			if(isElementPresent(By.xpath("//button/span[text()=\"Don't show this again\"]"))) {
				clickOn(By.xpath("//button/span[text()=\"Don't show this again\"]"), "Dont show this again button");
			}
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("Are my photos hitting the mark?","Are your extra-oral photos hitting the mark?");
			}
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[0]"));
			validateUploadFiles("Upload extra-oral photos to record progress to date!", 1, 4);
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[1]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[2]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[3]"));
			validateUploadFiles("Upload extra-oral photos to record progress to date!", 4, 4);
		}
		if(getValue(details, "intraOralImages[0]")!=null && !getValue(details, "intraOralImages[0]").equals("")) {
			clickOn(weIntraOralOrthodonticPhotographs, "IntraOralOrthodonticPhotographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("Are my photos up to scratch?","Are your photos up to scratch?");
			}
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[0]"));
			validateUploadFiles("Please upload ", 1, 8);
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[1]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[2]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[3]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[4]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[5]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[6]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[7]"));
			validateUploadFiles("Please upload ", 8, 8);
		}
		if(isElementPresent(btnDontShowAgain)) {
			clickOn(btnDontShowAgain, "Dont Show Again");
		}
		if(getValue(details, "radioGraphImgs[0]")!=null && !getValue(details, "radioGraphImgs[0]").equals("")) {
			clickOn(weRadiographsDuo, "Optional Radiographs");
			sleep(2000);
			if(getValue(details, "validateDownload").equalsIgnoreCase("yes")) {
				validateHelp("your radiographs please capture these in your clinical","What should I be looking for in my radiographs for orthodontics?");
			}
			uploadImage(imgInputRadiographsImages, getValue(details, "radioGraphImgs[0]"));
			sleep(2000);
		}	
		clickOn(weImpressions, "Impressions");
		sleep(2000);
		selectOptionDropDown(lstImpressionType, "ImpressionType", getValue(details,"impressionType"));
		if(getValue(details,"impressionType").contains("Silicone Impressions")) {
			enterText(txtPostageDate, "PostageDate", getValue(details,"postageDate"));
		}else if(getValue(details,"impressionType").contains("Digital Intra")) {
			waitForElement(lstImpressionScannerType);
			selectOptionDropDown(lstImpressionScannerType, "ImpressionScannerType", getValue(details,"ImpressionScannerType"));
			waitForElement(imgImpressionScannerTypeStl);
			uploadImage(imgImpressionScannerTypeStl, getValue(details, "imprImages") );
			waitForElement(btnContinue);
			sleep(1000);
			if(isElementPresent(btnContinue)) {
				String popupTitle = getText(By.xpath("//div[@role='dialog']//h2"));
				passed("Warning message is displayed as expected "+popupTitle);				
				isElementDisplayed(btnContinue, "Continue Button in Warning dialog");
				isElementDisplayed(btnDeleteFiles, "Delete Files Button in Warning dialog");
				waitForElement(btnContinue);
				clickOn(btnContinue, "Continue button");
				sleep(1000);
			}
			
		}else {

		}
		takeScreenshot();
	}
	
	public void enterRefinementSoloDetails(HashMap<String, Object> details) {
		selectOptionDropDown(lstDentalAndMedicalFit, "Dental Medical Fit", getValue(details,"dentalAndMedicalFit"));

		selectMultiOptionDropDown(mlstTreatmentPlanObjectives, "TreatmentPlanObjectives", getValue(details,"txnObjective"));

		enterText(txtFurtherInstructions, "FurtherInstructions",  getValue(details,"furtherInstructions"));
		selectRadioButton(rbArchesToTreat,"Arches to treat",getValue(details,"archesUnderConsideration"));
		selectRadioButton(rbSpacingCrowding,"SpacingCrowding",getValue(details,"spacing.typeOfSpacing"));

		selectRadioButton(rbCrowdingResolveUpperExpand,"CrowdingResolveUpperExpand",getValue(details,"crowding.resolveUpper.expand"));
		selectRadioButton(rbCrowdingResolveUpperProcline,"CrowdingResolveUpperProcline",getValue(details,"crowding.resolveUpper.procline"));
		selectRadioButton(rbCrowdingResolveUpperIprAnterior,"CrowdingResolveUpperIprAnterior",getValue(details,"crowding.resolveUpper.iprAnterior"));
		selectRadioButton(rbCrowdingResolveUpperIprPosterior,"CrowdingResolveUpperIprPosterior",getValue(details,"crowding.resolveUpper.iprPosterior"));

		selectRadioButton(rbCrowdingResolveLowerExpand,"CrowdingResolveLowerExpand",getValue(details,"crowding.resolveLower.expand"));
		selectRadioButton(rbCrowdingResolveLowerProcline,"CrowdingResolveLowerProcline",getValue(details,"crowding.resolveLower.procline"));
		selectRadioButton(rbCrowdingResolveLowerIprAnterior,"CrowdingResolveLowerIprAnterior",getValue(details,"crowding.resolveLower.iprAnterior"));
		selectRadioButton(rbCrowdingResolveLowerIprPosterior,"CrowdingResolveLowerIprPosterior",getValue(details,"crowding.resolveLower.iprPosterior"));

		selectRadioButton(rbOverBite,"OverBite",getValue(details,"overBite.typeOfOverBite"));
		enterText(txtOverBiteAdditionalDetails, "OverBiteAdditionalDetails", getValue(details,"overBite.additionalDetailsWhenCorrectBite"));

		expandSection(weBiteRamps, "Bite Ramps Section");
		sleep(2000);
		selectRadioButton(rbBiteRampsType,"BiteRampsType",getValue(details,"biteRamps.typeOfBiteRamps"));
		enterText(txtBiteRampsTypeAdditionalDetails, "BiteRampsTypeAdditionalDetails", getValue(details,"biteRamps.additionalDetailsWhenPlaceBiteRamps"));

		selectRadioButton(rbOverjet,"Overjet",getValue(details,"overjet"));

		selectCheckBox(cbAnteriorPosteriorMaintainCurrent,"AnteriorPosteriorMaintainCurrent",getValue(details,"anteriorPosteriorRelationship.maintainCurrent[0]"));
		selectCheckBox(cbAnteriorPosteriorCorrectionClass,"AnteriorPosteriorCorrectionClass",getValue(details,"anteriorPosteriorRelationship.correctionToClass[0]"));
		selectCheckBox(cbAnteriorPosteriorImproveCanineMolarRel,"AnteriorPosteriorImproveCanineMolarRel",getValue(details,"anteriorPosteriorRelationship.improveCanineAndMolarRelationship[0]"));

		enterText(txtAnteriorPosteriorAdditionalDetails, "AnteriorPosteriorAdditionalDetails", getValue(details,"anteriorPosteriorRelationship.additionalDetails"));

		selectCheckBox(cbToothMovementOptions,"ToothMovementOptions",getValue(details,"anteriorPosteriorRelationship.toothMovementOptions[0]"));

		//check above
		selectRadioButton1(rbPrecisionCuts,"PrecisionCuts",getValue(details,"anteriorPosteriorRelationship.precisionCuts"));
		selectRadioButton1(rbButtonCutouts,"ButtonCutouts",getValue(details,"anteriorPosteriorRelationship.buttonCutOuts"));
		waitForElement(wePosteriorCrossbite);
		sleep(1000);
		moveToElement(wePosteriorCrossbite);
		expandSection(wePosteriorCrossbite, "Posterior Crossbite Section");
		waitForElement(rbPosteriorCrossBite);
		sleep(2000);
		selectRadioButton(rbPosteriorCrossBite,"PosteriorCrossBite",getValue(details,"posteriorCrossBite"));
		enterText(txtPosteriorCrossBiteAdditionalDetails, "PosteriorCrossBiteAdditionalDetails", getValue(details,"posteriorCrossBiteDetail"));

		selectRadioButton(rbMidlineUpper,"MidlineUpper",getValue(details,"midline.upper"));
		selectRadioButton(rbMidlineLower,"MidlineLower",getValue(details,"midline.lower"));
		enterText(txtMidlineAdditionalDetails, "MidlineAdditionalDetails", getValue(details,"midline.additionalDetails"));

		expandSection(weExtractions, "Extractions Section");
		//call methods to select image tooth
		sleep(2000);
		enterText(txtExtractionsAdditionalDetails, "ExtractionsAdditionalDetails", getValue(details,"extractions.additionalDetails"));
		selectRadioButton(rbAttachments,"Attachments",getValue(details,"attachments.typeOfAttachments"));
		if(getValue(details,"attachments.typeOfAttachments").contains("These specific teeth should not have attachments")) {
			waitForElement(txtAttachementsDetails);
			enterText(txtAttachementsDetails, "AttachementsDetails", getValue(details,"attachments.additionalDetails"));
		}
		selectRadioButton(rbTypeOfIPR,"TypeOfIPR",getValue(details,"ipr.typeOfIPR"));
		if(getValue(details,"ipr.typeOfIPR").contains("Limited")) {
			waitForElement(txtIPRDetails);
			enterText(txtIPRDetails,"IPR Additional details ",getValue(details,"ipr.additionalDetails"));
		}
		selectRadioButton(rbPhasingPreference,"PhasingPreference",getValue(details,"phasingPreference"));
		enterText(txtPhasingPreferenceDetails, "PhasingPreferenceDetails", getValue(details,"phasingPreferenceDetails"));

		expandSection(weToothMovementRestrictions, "ToothMovementRestrictions");
		sleep(1000);
		enterText(txtMovementRestrictionsAdditionalDetails, "MovementRestrictionsAdditionalDetails", getValue(details,"toothMovementRestrictions.additionalDetails"));

		expandSection(wePassiveAligners, "PassiveAligners");
		waitForElement(txtPassiveAligners);
		enterText(txtPassiveAligners, "PassiveAligners", getValue(details,"passiveAligners"));

		//upload files
		if(getValue(details, "extraOralImages[0]")!=null && !getValue(details, "extraOralImages[0]").equals("")) {
			clickOn(weExtraOralOrthodonticPhotographs, "ExtraOralOrthodonticPhotographs");
			sleep(2000);
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[0]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[1]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[2]"));
			uploadImage(imgInputExtraOralImages, getValue(details, "extraOralImages[3]"));
		}
		if(getValue(details, "intraOralImages[0]")!=null && !getValue(details, "intraOralImages[0]").equals("")) {
			clickOn(weIntraOralOrthodonticPhotographs, "IntraOralOrthodonticPhotographs");
			sleep(2000);
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[0]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[1]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[2]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[3]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[4]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[5]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[6]"));
			uploadImage(imgInputIntraOralImages, getValue(details, "intraOralImages[7]"));
		}
		if(isElementPresent(btnDontShowAgain)) {
			clickOn(btnDontShowAgain, "Dont Show Again");
		}
		if(getValue(details, "radioGraphImgs[0]")!=null && !getValue(details, "radioGraphImgs[0]").equals("")) {
			clickOn(weRadiographs, "Radiographs");
			sleep(Values.sleepTime*2);
			uploadImage(imgInputRadiographsImages, getValue(details, "radioGraphImgs[0]"));
			sleep(Values.sleepTime*5);
		}		
		clickOn(weImpressions, "Impressions");
		sleep(Values.sleepTime*3);
		selectOptionDropDown(lstImpressionType, "ImpressionType", getValue(details,"impressionType"));
		if(getValue(details,"impressionType").contains("Silicone Impressions")) {
			enterText(txtPostageDate, "PostageDate", getValue(details,"postageDate"));
		}else if(getValue(details,"impressionType").contains("Digital Intra")) {
			waitForElement(lstImpressionScannerType);
			selectOptionDropDown(lstImpressionScannerType, "ImpressionScannerType", getValue(details,"ImpressionScannerType"));
			waitForElement(imgImpressionScannerTypeStl);
			uploadImage(imgImpressionScannerTypeStl, getValue(details, "imprImages") );
			waitForElement(btnContinue);
			sleep(1000);
			if(isElementPresent(btnContinue)) {
				String popupTitle = getText(By.xpath("//div[@role='dialog']//h2"));
				passed("Warning message is displayed as expected "+popupTitle);				
				isElementDisplayed(btnContinue, "Continue Button in Warning dialog");
				isElementDisplayed(btnDeleteFiles, "Delete Files Button in Warning dialog");
				clickOn(btnContinue, "Continue Button");
				sleep(1000);
			}
			//clickOn(cbAgreeTerms, "AgreeTerms Checkbox");
		}else {

		}
		takeScreenshot();
	}
	
	public void createRefinementDuoCase() {	
		HashMap<String, Object> details = convertJsonToMap("."+data.get("REF_JSONPATH"));
		enterRefinementDuoDetails(details);
		sleep(2000);
		clickOn(btnSubmitCase, "SubmitCase Button");
		sleep(3000);		
		takeScreenshot();
		waitForElement(tabSubmissions);
		clickOn(tabSubmissions, "Submissions tab");
		browser.navigate().refresh();
		waitForElement(By.xpath("//span[text()='Patient Info']"));
		if(isElementPresent(By.xpath("//span[text()='Patient Info']"))) {
			passed("Patient Info page is displayed after clicking on submit button");
		}else {
			fail("Patient Info page is not displayed after clicking on submit button");
		}
		takeScreenshot();
		enterText(txtNote, "Note", "Automation Testing Note");
		clickOn(btnSaveNote, "Save Note Button");
		takeScreenshot();
	}
	
	public void createRefinementSoloCase() {
		HashMap<String, Object> details = convertJsonToMap("."+data.get("REF_JSONPATH"));
		enterRefinementSoloDetails(details);
		clickOn(btnSubmitCase, "SubmitCase Button");
		sleep(3000);		
		takeScreenshot();
		waitForElement(tabSubmissions);
		clickOn(tabSubmissions, "Submissions tab");
		browser.navigate().refresh();
		waitForElement(By.xpath("//span[text()='Patient Info']"));
		if(isElementPresent(By.xpath("//span[text()='Patient Info']"))) {
			passed("Patient Info page is displayed after clicking on submit button");
		}else {
			fail("Patient Info page is not displayed after clicking on submit button");
		}
		takeScreenshot();
		enterText(txtNote, "Note", "Automation Testing Note");
		clickOn(btnSaveNote, "Save Note Button");
		takeScreenshot();
	}
	
}


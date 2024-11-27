package pages.specialist;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Design_Status;
import Utilities.constans.Specialist_Status;
import Utilities.constans.Values;

public class SpecialistDashboardPage extends Page {

	@FindBy(xpath = "//a/button[text()='Host a meeting']")
	private WebElement lnkHostMeeting;

	@FindBy(xpath = "//div/*[@alt='logoutUser']")
	private WebElement btnLogout;

	@FindBy(id = "basic_email")
	private WebElement txtEmail;

	@FindBy(xpath = "//div[@data-node-key='case-info']")
	private WebElement tabCaseInfo;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	private WebElement weSuccessMessage;
	
	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weSuccessMessage1;
	
	@FindBy(xpath = "//button/span[text()='Put On Hold']")
	private WebElement btnPutOnHold;

	@FindBy(xpath = "//button//article[text()='Case Not Suitable']")
	private WebElement btnCaseNotSuitable;
	
	@FindBy(xpath = "//button//article[text()='Case Suitable']")
	private WebElement btnCaseSuitable;
	
	@FindBy(id = "reason")
	private WebElement rbReasonForHold;
	
	@FindBy(id = "resolutionTime")
	private WebElement rbResolutionTime;
	
	@FindBy(xpath = "//button/span[text()='Confirm']")
	private WebElement btnConfirm;
	
	@FindBy(xpath = "//button/span[text()='Cancel']")
	private WebElement btnCancel;
	
	
	//Case Not Suitable
	@FindBy(id = "commentsToPatient")
	private WebElement txtCommentsToPatient;
	
	@FindBy(id = "complexity")
	private WebElement txtComplexity;
	
	@FindBy(id = "patientGoesAhead")
	private WebElement txtPatientGoesAhead;
	
	@FindBy(id = "implicationsForPatient")
	private WebElement txtImplicationsForPatient;
	
	@FindBy(id = "orthoSuggestions")
	private WebElement txtOrthoSuggestions;
	
	@FindBy(id = "suggestedNextSteps")
	private WebElement txtSuggestedNextSteps;
	
	@FindBy(xpath = "//button/span[text()='Submit']")
	private WebElement btnSubmit;
	
	@FindBy(id = "revision_request")
	private WebElement txtRevisionRequest;
	
	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement txtSearchPatients;
	
	@FindBy(id = "patients-menu")
	private WebElement menuPatients;
	
	
	//Case Suitable
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
	
	@FindBy(id = "note")
	private WebElement txtNote;
	
	@FindBy(xpath = "//button/span[text()='Save as draft']")
	private WebElement btnSaveAsDraft;
	
	@FindBy(xpath = "//*[@data-node-key='treatment-proposals']")
	private WebElement tabTreatmentProposals;
	
	@FindBy(xpath = "//span[text()='Treatment Design']")
	private WebElement weTreatmentDesignHeader;
	
	@FindBy(xpath = "//button/a/span[text()='Submit Advice']")
	private WebElement btnSubmitAdvice;
	
	@FindBy(id = "personalNote")
	private WebElement txtPersonalNote;
	
	@FindBy(id = "recordsAdvice")
	private WebElement txtRecordsAdvice;
	
	@FindBy(id = "outcomes")
	private WebElement txtTreatmentOutcomes;
	
	@FindBy(id = "complexity")
	private WebElement txtComplexityTreatment;
	
	@FindBy(id = "diagnosisAndProblemList")
	private WebElement txtDiagnosisAndProblemList;
	
	@FindBy(id = "estimatedTreatmentRange")
	private WebElement txtEstimatedTreatmentRange;
	
	@FindBy(id = "aimsOfTreatmentAndSummary")
	private WebElement txtAimsOfTreatmentAndSummary;
	
	@FindBy(id = "alternativeTreatmentPlan")
	private WebElement txtAlternativeTreatmentPlan;
	
	@FindBy(id = "specificToPatientRadiograph")
	private WebElement txtSpecificToPatientRadiograph;
	
	@FindBy(id = "submissionCallOut_positive")
	private WebElement txtSubmissionCallOut_positive;
	
	@FindBy(id = "submissionCallOut_problematic")
	private WebElement txtSubmissionCallOut_problematic;
	
	@FindBy(id = "submissionCallOut_potentialBlocker")
	private WebElement txtSubmissionCallOut_potentialBlocker;
	
	@FindBy(id = "attachmentPlan")
	private WebElement txtAttachmentPlan;
	
	@FindBy(id = "iprPlan")
	private WebElement txtIprPlan;
	
	@FindBy(id = "elasticType")
	private WebElement txtElasticType;
	
	@FindBy(id = "elasticWearSchedule")
	private WebElement txtElasticWearSchedule;
	
	@FindBy(id = "recommendedButtons")
	private WebElement txtRecommendedButtons;
	
	@FindBy(id = "otherAuxillaries")
	private WebElement txtOtherAuxillaries;
	
	@FindBy(id = "consent")
	private WebElement txtGettingStartedConsent;
	
	@FindBy(id = "touchpoints")
	private WebElement txtGettingStartedTouchpoints;
	
	@FindBy(id = "suggestedWearSchedule")
	private WebElement txtGettingStartedSuggestedWearSchedule;
	
	@FindBy(id = "refinementAdvice")
	private WebElement txtFinalAdviceRefinementAdvice;
	
	@FindBy(id = "restorative")
	private WebElement txtFinalAdviceRestorative;
	
	@FindBy(id = "retentionAdvice")
	private WebElement txtFinalAdviceRetentionAdvice;
	
	@FindBy(id = "submit-advice")
	private WebElement btnSubmitAdviceSubmit;
	
	@FindBy(xpath = "//button/span[text()='View treatment proposal']")
	private WebElement btnViewTreatmentProposal;
	
	@FindBy(xpath = "//div[@data-node-key='case-info']")
	private WebElement weCaseInfo;
	
	@FindBy(id = "send-content")
	private WebElement btnSendContent;
	
	@FindBy(id = "rc_select_7_list")
	private WebElement lstEducationSortBy;
	
	@FindBy(xpath = "//div[@class='fixed chat-closed']")
	private WebElement weChatOpen;
	
	@FindBy(xpath = "//div[contains(@data-placeholder,'Message')]")
	private WebElement txtChatMessage;
	
	@FindBy(xpath = "//button[contains(@class,'sendBtn enabled')]")
	private WebElement btnChatSend;

	

	
	
	
	public SpecialistDashboardPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}
	
	public void navigateToPatientsPage() {
		clickOn(menuPatients, "Patients Menu");
		waitForElement(By.xpath("//div[@data-node-key='ALL_CASES_STATUS']"));
		if(isElementPresent(By.xpath("//div[@data-node-key='ALL_CASES_STATUS']"))) {
			passed("Patients List page is displayed as expected");
		}else {
			fail("Patients List page is not displayed");
		}
		takeScreenshot();
	}

	public void logoutApplication() {
		clickOn(btnLogout, "LogOut Button");
		waitForElement(txtEmail);
		if(isElementPresent(txtEmail)) {
			passed("logged out of the application successfully");
		}else {
			takeScreenshot();
			failAssert("Unable to logout from the Application");
		}
		takeScreenshot();
	}


	public void acceptOrRejectCases(String patientName, String acceptOrReject) {
		waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));
		List<WebElement> wesSection = browser.findElements(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));		
		WebElement weCurrentCase = null;
		if(wesSection.size()==3) {
			passed("All the 3 sections are displayed in designer dashboard page");
			waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]//div[@class='ant-space-item']"));
			List<WebElement> weAllNewPatient = wesSection.get(0).findElements(By.xpath(".//div[@class='ant-space-item']"));
			for(int i=weAllNewPatient.size()-1;i>=0;i--) {
				weAllNewPatient = wesSection.get(0).findElements(By.xpath(".//div[@class='ant-space-item']"));
				moveToElement(weAllNewPatient.get(i));
				String strEachCase = weAllNewPatient.get(i).getText();
				if(strEachCase.contains(patientName)) {
					weCurrentCase = weAllNewPatient.get(i);
					break;
				}
			}
			if(weCurrentCase!=null) {
				if(acceptOrReject.equalsIgnoreCase("accept")) {
					try {
						WebElement btnAccept = weCurrentCase.findElement(By.xpath(".//button/span[text()='Accept']"));
						clickOn(btnAccept, "Accept Button");
						waitForElement(tabCaseInfo);
						isElementDisplayed(tabCaseInfo, "Case Info Tab");
						if(isElementPresent(tabCaseInfo)) {
							passed("Successfully accepted the case, Navigated to Case Info page");
						}else {
							fail("After accepting the case in Specialist dashboard did not navigate to Case Info page");
						}
						takeScreenshot();
					} catch (Exception e) {
						fail("Exception caught while accept or rejecting new cases "+e.getMessage());
					}
				}else if(acceptOrReject.equalsIgnoreCase("reject")) {
					try {
						WebElement btnReject = weCurrentCase.findElement(By.xpath(".//button/span[text()='Reject']"));
						clickOn(btnReject, "Reject Button");
						waitForElement(weSuccessMessage1);
						if(isElementPresent(weSuccessMessage1)) {
							passed("Toaster message is displayed as expected");
							String actSuccMsg = getText(weSuccessMessage1);
							if(actSuccMsg.contains(Constants.SPECIALIST_REJECT_SUCCESS_MSG)) {
								passed("Success Toaster Message is displayed as expected "+actSuccMsg);
							}else {
								fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_REJECT_SUCCESS_MSG);
							}
							takeScreenshot();
						}else {
							fail("Success toaster message is not displayed");
						}
					} catch (Exception e) {
						fail("Exception caught while accept or rejecting new cases"+e.getMessage());
					}
				}else {
					fail("Invalid input paramter for this function, only accept or reject can be passed, instead of "+acceptOrReject);
				}
			}else {
				fail("New Case assigned is not displayed in Specialist dashboard "+ patientName);
			}
		}else {
			fail("All the 3 sections are not displayed in Specialist dashboard page");
		}
		takeScreenshot();
	}

	public void validateAcceptCase(){
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		acceptOrRejectCases(patientName, "accept");
	}
	
	public void validateRejectCase(){
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		acceptOrRejectCases(patientName, "reject");
	}
	
	public void selectRecordFromReadyForInstructionsDashboard(String patientName) {
		waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));
		List<WebElement> wesSection = browser.findElements(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));		
		WebElement weCurrentCase = null;
		if(wesSection.size()==3) {
			passed("All the 3 sections are displayed in designer dashboard page");
			waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]//div[@class='ant-space-item']"));
			List<WebElement> weAllNewPatient = wesSection.get(1).findElements(By.xpath(".//div[@class='ant-space-item']"));
			for(WebElement weEachNewCase : weAllNewPatient) {
				String strEachCase = weEachNewCase.getText();
				if(strEachCase.contains(patientName)) {
					weCurrentCase = weEachNewCase;
				}
			}
			clickOn(weCurrentCase, patientName + " New Case");
		}else {
			fail("All the 3 sections are not displayed in designer dashboard page");
		}
	}
	
	public void validatePutOnHold(String type) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(2,patientName);
		takeScreenshot();
		waitForElement(btnPutOnHold);
		clickOn(btnPutOnHold, "PUT ON HOLD Button");
		waitForElement(rbReasonForHold);
		selectRadioButton(rbReasonForHold, "Reason For Hold", type);
		selectRadioButton(rbResolutionTime, "Resolution Time", "Slow - Complex question or patient input needed");
		takeScreenshot();
		clickOn(btnConfirm, "Confirm Button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Toaster message is displayed as expected");
			String actSuccMsg = getText(weSuccessMessage);
			if(actSuccMsg.contains(Constants.SPECIALIST_CASEONHOLD_SUCCESS_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_CASEONHOLD_SUCCESS_MSG);
			}
			takeScreenshot();
			clickOn(menuPatients, "Patients Menu");
			takeScreenshot();
			//TODO unable to navigate to On Hold section
			validateStatusOfPatients(patientName, Specialist_Status.ON_HOLD.toString(), "On Hold");
		}else {
			fail("Success toaster message is not displayed");
		}
	}
	
	public void selectRecordFromDashboard(int colNum,String patientName) {
		waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));
		List<WebElement> wesSection = browser.findElements(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));		
		WebElement weCurrentCase = null;
		if(wesSection.size()==3) {
			passed("All the 3 sections are displayed in designer dashboard page");
			waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]//div[@class='ant-space-item']"));
			List<WebElement> weCases = wesSection.get(colNum-1).findElements(By.xpath(".//div[@class='ant-space-item']"));
			for(int i=weCases.size()-1;i>0;i--) {
				weCases = wesSection.get(colNum-1).findElements(By.xpath(".//div[@class='ant-space-item']"));
				moveToElement(weCases.get(i));
				String strEachCase = weCases.get(i).getText();
				if(strEachCase.contains(patientName)) {
					weCurrentCase = weCases.get(i);
					break;
				}
			}
			takeScreenshot();
			if(weCurrentCase!=null) {
				clickOn(weCurrentCase, patientName+" Case");
				waitForElement(tabCaseInfo);
				isElementDisplayed(tabCaseInfo, "Case Info Tab");
				if(isElementPresent(tabCaseInfo)) {
					passed("Successfully selected the case, Navigated to Case Info page");
				}else {
					fail("Did not navigate to Case Details page after selecting the record");
				}
			}else {
				fail("Case with patient name "+ patientName + " is not found in the column num "+colNum);
			}
		}else {
			fail("All the 3 sections are not displayed in Specialist dashboard page");
		}
		takeScreenshot();
	}
	
	public void selectRecordFromSpecifiedSection(String dataNodeKey, String patientName) {
		if(isElementPresent(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"))) {
			clickOn(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"), dataNodeKey + " Section");
			waitForElement(By.xpath("//div[contains(@id,'"+dataNodeKey+"')]//span[text()='"+patientName+"']/../following-sibling::div//button"));
			clickOn(By.xpath("//div[contains(@id,'"+dataNodeKey+"')]//span[text()='"+patientName+"']/../following-sibling::div//button"), patientName+" View Button");
			waitForElement(tabTreatmentProposals);
			if(isElementPresent(tabTreatmentProposals)) {
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

	public void validateStatusOfPatients(String patientName,String tabName,String status) {
		if(!isElementPresent(txtSearchPatients)) {
			clickOn(menuPatients, "Patients Menu");
		}		
		waitForElement(txtSearchPatients);
		takeScreenshot();
		enterText(txtSearchPatients, "Search textbox", patientName);
		clickOn(By.xpath("//div[@data-node-key='"+tabName+"']/div"), tabName+" Tab");
		waitForElement(By.xpath("//div[contains(@id,'"+tabName+"')]/div/div/div"));
		takeScreenshot();
		sleep(Values.sleepTime*6);
		By byPatientList = By.xpath("//div[contains(@id,'"+tabName+"')]/div/div/div");
		List<WebElement> wePatientList = browser.findElements(byPatientList);
		if(wePatientList.size()>0) {
			passed("Patient "+ patientName +" is displayed in the section "+tabName);
			takeScreenshot();
			String  actStatus = wePatientList.get(0).findElement(By.xpath(".//div[4]")).getText();
			//WebElement btnView = wePatientList.get(0).findElement(By.xpath("./div/div/button"));
			if(actStatus.toLowerCase().contains(status.toLowerCase())) {
				passed("Status is as expected "+status);
			}else {
				fail("Status is not as expected , expected is "+status +" ,But actual is "+actStatus);
			}
		}else {
			fail("Patient "+ patientName +" is not displayed in the section "+tabName);
		}
		takeScreenshot();
	}
	
	public void searchAndSelectPatientInAllSection(String patientName) {
		if(!isElementPresent(txtSearchPatients)) {
			clickOn(menuPatients, "Patients Menu");
		}		
		waitForElement(txtSearchPatients);
		takeScreenshot();
		enterText(txtSearchPatients, "Search textbox", patientName);
		sleep(Values.sleepTime*6);
		By byPatientList = By.xpath("//div[contains(@id,'ALL_CASES_STATUS')]/div/div/div");
		List<WebElement> wePatientList = browser.findElements(byPatientList);
		WebElement wePatient = wePatientList.get(0).findElement(By.xpath(".//button"));
		clickOn(wePatient, "View Button");
//		waitForElement()
		
	}
	
	public void validateCaseNotSuitable() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(2,patientName);
		takeScreenshot();
		waitForElement(btnCaseNotSuitable);
		clickOn(btnCaseNotSuitable, "Case Not Suitable Button");
		sleep(Values.sleepTime);
		String strParentTitle = browser.getTitle();
		navigateToSecondWindow();
		waitForElement(txtCommentsToPatient);
		
		enterText(txtCommentsToPatient, "Comments to Patient", "Automation Case is not suitable");
		enterText(txtComplexity,"Complexity","Automation Complexity of the case details");
		enterText(txtPatientGoesAhead,"Patient Goes Ahead","Patinet Goes ahead will face the problems");
		enterText(txtImplicationsForPatient, "Implications for Patient", "All Implications");
		enterText(txtOrthoSuggestions, "Ortho Suggestions ", "Suggestions");
		enterText(txtSuggestedNextSteps, "Suggested Next Steps", "Next Steps suggested");
		takeScreenshot();
		clickOn(btnSubmit, "Submit Button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Toaster message is displayed as expected");
			String actSuccMsg = getText(weSuccessMessage);
			if(actSuccMsg.contains(Constants.SPECIALIST_CASENOTSUITABLE_SUCCESS_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_CASENOTSUITABLE_SUCCESS_MSG);
			}
			takeScreenshot();
			clickOn(menuPatients, "Patients Menu");
			takeScreenshot();
			//TODO Issue Exists now
			validateStatusOfPatients(patientName, Specialist_Status.NOT_SUITABLE.toString(), "Not Suit");
		}else {
			fail("Success toaster message is not displayed");
			takeScreenshot();
		}
	}
	
	public void enterDetailsForCaseSuitable(HashMap<String, Object> details) {
		waitForElement(txtFurtherInstructions);
		enterText(txtFurtherInstructions, "FurtherInstructions",  getValue(details,"furtherInstructions"));
		selectRadioButton(rbArchesToTreat,"Arches to treat",getValue(details,"archesUnderConsideration"));
		selectRadioButton(rbSpacingCrowding,"SpacingCrowding",getValue(details,"spacing.typeOfSpacing"));

		selectRadioButton(rbCrowdingResolveUpperExpand,"CrowdingResolveUpperExpand",getValue(details,"crowding.resolveUpper.expand"));
		selectRadioButton(rbCrowdingResolveUpperProcline,"CrowdingResolveUpperProcline",getValue(details,"crowding.resolveUpper.procline"));
		selectRadioButton(rbCrowdingResolveUpperIprAnterior,"CrowdingResolveUpperIprAnterior",getValue(details,"crowding.resolveUpper.iprAnterior"));
		selectRadioButton(rbCrowdingResolveUpperIprPosterior,"CrowdingResolveUpperIprPosterior",getValue(details,"crowding.resolveUpper.iprPosterior"));
		moveToElement(rbCrowdingResolveLowerExpand);
		selectRadioButton(rbCrowdingResolveLowerExpand,"CrowdingResolveLowerExpand",getValue(details,"crowding.resolveLower.expand"));
		selectRadioButton(rbCrowdingResolveLowerProcline,"CrowdingResolveLowerProcline",getValue(details,"crowding.resolveLower.procline"));
		selectRadioButton(rbCrowdingResolveLowerIprAnterior,"CrowdingResolveLowerIprAnterior",getValue(details,"crowding.resolveLower.iprAnterior"));
		selectRadioButton(rbCrowdingResolveLowerIprPosterior,"CrowdingResolveLowerIprPosterior",getValue(details,"crowding.resolveLower.iprPosterior"));
		moveToElement(rbOverBite);
		selectRadioButton(rbOverBite,"OverBite",getValue(details,"overBite.typeOfOverBite"));
		enterText(txtOverBiteAdditionalDetails, "OverBiteAdditionalDetails", getValue(details,"overBite.additionalDetailsWhenCorrectBite"));

		expandSection(weBiteRamps, "Bite Ramps Section");
		sleep(2000);
		takeScreenshot();
		selectRadioButton(rbBiteRampsType,"BiteRampsType",getValue(details,"biteRamps.typeOfBiteRamps"));
		enterText(txtBiteRampsTypeAdditionalDetails, "BiteRampsTypeAdditionalDetails", getValue(details,"biteRamps.additionalDetailsWhenPlaceBiteRamps"));
		moveToElement(rbOverjet);
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
		takeScreenshot();
		moveToElement(wePosteriorCrossbite);
		expandSection(wePosteriorCrossbite, "Posterior Crossbite Section");
		waitForElement(rbPosteriorCrossBite);
		sleep(2000);
		selectRadioButton(rbPosteriorCrossBite,"PosteriorCrossBite",getValue(details,"posteriorCrossBite"));
		enterText(txtPosteriorCrossBiteAdditionalDetails, "PosteriorCrossBiteAdditionalDetails", getValue(details,"posteriorCrossBiteDetail"));
		moveToElement(rbMidlineUpper);
		selectRadioButton(rbMidlineUpper,"MidlineUpper",getValue(details,"midline.upper"));
		selectRadioButton(rbMidlineLower,"MidlineLower",getValue(details,"midline.lower"));
		enterText(txtMidlineAdditionalDetails, "MidlineAdditionalDetails", getValue(details,"midline.additionalDetails"));

		expandSection(weExtractions, "Extractions Section");
		//call methods to select image tooth
		takeScreenshot();
		sleep(2000);
		enterText(txtExtractionsAdditionalDetails, "ExtractionsAdditionalDetails", getValue(details,"extractions.additionalDetails"));
		selectRadioButton(rbAttachments,"Attachments",getValue(details,"attachments.typeOfAttachments"));
		if(getValue(details,"attachments.typeOfAttachments").contains("These specific teeth should not have attachments")) {
			waitForElement(txtAttachementsDetails);
			enterText(txtAttachementsDetails, "AttachementsDetails", getValue(details,"attachments.additionalDetails"));
		}
		moveToElement(rbTypeOfIPR);
		selectRadioButton(rbTypeOfIPR,"TypeOfIPR",getValue(details,"ipr.typeOfIPR"));
		if(getValue(details,"ipr.typeOfIPR").contains("Limited")) {
			waitForElement(txtIPRDetails);
			enterText(txtIPRDetails,"IPR Additional details ",getValue(details,"ipr.additionalDetails"));
		}
		selectRadioButton(rbPhasingPreference,"PhasingPreference",getValue(details,"phasingPreference"));
		enterText(txtPhasingPreferenceDetails, "PhasingPreferenceDetails", getValue(details,"phasingPreferenceDetails"));

		expandSection(weToothMovementRestrictions, "ToothMovementRestrictions");
		sleep(1000);
		moveToElement(txtMovementRestrictionsAdditionalDetails);
		enterText(txtMovementRestrictionsAdditionalDetails, "MovementRestrictionsAdditionalDetails", getValue(details,"toothMovementRestrictions.additionalDetails"));
		takeScreenshot();
		expandSection(wePassiveAligners, "PassiveAligners");
		waitForElement(txtPassiveAligners);
		enterText(txtPassiveAligners, "PassiveAligners", getValue(details,"passiveAligners"));
		enterText(txtNote, "Notes", "Automation Testing");
		clickOn(btnSubmit, "Submit Button");
		takeScreenshot();
	}
	
	public void validateCaseSuitable() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(2,patientName);
		takeScreenshot();
		waitForElement(btnCaseSuitable);
		clickOn(btnCaseSuitable, "Case Suitable Button");
		sleep(Values.sleepTime);
		HashMap<String, Object> details = convertJsonToMap("."+data.get("SPECIALIST_JSONPATH"));
		enterDetailsForCaseSuitable(details);
		//List<WebElement> btnSubmit = browser.findElements(By.xpath("//button/span[text()='Submit']"));
		//clickOn(btnSubmit.get(1), "Submit Case Button");
		waitForElement(weSuccessMessage);
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Toaster message is displayed as expected");
			String actSuccMsg = getText(weSuccessMessage);
			if(actSuccMsg.contains(Constants.SPECIALIST_CASESUITABLE_SUCCESS_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_CASESUITABLE_SUCCESS_MSG);
			}
			takeScreenshot();
			clickOn(menuPatients, "Patients Menu");
			takeScreenshot();
			//TODO Issue Exists now
			validateStatusOfPatients(patientName, Specialist_Status.AWAITING_DESIGN.toString(), "awaiting design");
		}else {
			fail("Success toaster message is not displayed");
		}
		takeScreenshot();
	}
	
	public void validateCaseSaveAsDraft() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(2,patientName);
		takeScreenshot();
		waitForElement(btnCaseSuitable);
		clickOn(btnCaseSuitable, "Case Suitable Button");
		sleep(Values.sleepTime);
		HashMap<String, Object> details = convertJsonToMap("."+data.get("SPECIALIST_JSONPATH"));
		enterDetailsForCaseSuitable(details);
		clickOn(btnSaveAsDraft, "Save As Draft Button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Toaster message is displayed as expected");
			String actSuccMsg = getText(weSuccessMessage);
			if(actSuccMsg.contains(Constants.SPECIALIST_CASESUITABLE_SAVEASDRAFT_SUCCESS_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_CASESUITABLE_SAVEASDRAFT_SUCCESS_MSG);
			}
			takeScreenshot();
			clickOn(menuPatients, "Patients Menu");
			takeScreenshot();
			//TODO Issue Exists now
			validateStatusOfPatients(patientName, Specialist_Status.SUBMIT_INSTRUCTION.toString(), "submit Instruction");
		}else {
			fail("Success toaster message is not displayed");
		}
		takeScreenshot();
	}
	
	
	public void validateMandatoryCaseNotSuitableForm() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(2,patientName);
		takeScreenshot();
		waitForElement(btnCaseNotSuitable);
		clickOn(btnCaseNotSuitable, "Case Not Suitable Button");
		sleep(Values.sleepTime);
		clickOn(btnSubmit, "Submit Button");
		sleep(Values.sleepTime);
		validateErrorMessageOfField(txtCommentsToPatient, "Comments to patient", "Please add your comments");
		validateErrorMessageOfField(txtSuggestedNextSteps, "Suggested Next Steps", "This field is required");
		takeScreenshot();
	}
	
	public void validateMandatoryCaseSuitableForm() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(2,patientName);
		takeScreenshot();
		waitForElement(btnCaseSuitable);
		clickOn(btnCaseSuitable, "Case Suitable Button");
		waitForElement(txtFurtherInstructions);
		enterText(txtFurtherInstructions, "Further Instructions", "test");	
		sleep(Values.sleepTime);
		clickOn(btnSubmit, "Submit Button");
		sleep(Values.sleepTime);
		validateErrorMessageOfField(rbArchesToTreat, "Arhces to Treat", "Arches in Treatment is required");
		takeScreenshot();
	}
	
	public void navigateToTreatmentProposalsAndSelectDesgin(String designNum) {
		waitForElement(tabTreatmentProposals);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentProposals)) {
			clickOn(tabTreatmentProposals, "Treatment Proposals Tab");
			waitForElement(By.xpath("//div[contains(@id,'panel-treatment-proposals')]/div/div"));
			sleep(Values.sleepTime*3);
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-proposals')]/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-proposals')]/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath("./div[1]/span[2]")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
					passed("Design Number is found "+designNum);
					takeScreenshot();
					WebElement btnView = weDesign.findElement(By.xpath(".//button"));
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
			if(blnFound) {
				takeScreenshot();
				failAssert("Design with expected design number is not found , Design Number "+designNum);
			}
		}else {
			failAssert("Unable to navigate to Treatment Designs tab");
		}
	}

	
	
	public void submitAdvice(String designNum) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(3,patientName);
		navigateToTreatmentProposalsAndSelectDesgin(designNum);
		waitForElement(btnSubmitAdvice);
		clickOn(btnSubmitAdvice, "Submit Advice");
		sleep(2000);
		navigateToSecondWindow();
		enterSpecialistAdviceForm();
		sleep(Values.sleepTime);
		takeScreenshot();
		clickOn(btnSubmitAdviceSubmit, "Submit Advice");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Toaster message is displayed as expected");
			String actSuccMsg = getText(weSuccessMessage);
			if(actSuccMsg.contains(Constants.SPECIALIST_SUBMITADVICE_SUCCESS_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_SUBMITADVICE_SUCCESS_MSG);
			}
			takeScreenshot();
			sleep(Values.sleepTime*3);
		if(isElementPresent(btnViewTreatmentProposal)) {
			clickOn(btnViewTreatmentProposal, "View Treatment Proposal Button");
		}			
		}else {
			fail("Success toaster message is not displayed");
		}
	}
	
	public void submitAdviceAgainSecond() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(3,patientName);
		navigateToTreatmentProposalsAndSelectDesgin("2");
		waitForElement(btnSubmitAdvice);
		clickOn(btnSubmitAdvice, "Submit Advice");
		sleep(2000);
		navigateToSecondWindow();
		enterSpecialistAdviceForm();
		sleep(Values.sleepTime);
		takeScreenshot();
		clickOn(btnSubmitAdviceSubmit, "Submit Advice");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Toaster message is displayed as expected");
			String actSuccMsg = getText(weSuccessMessage);
			if(actSuccMsg.contains(Constants.SPECIALIST_SUBMITADVICE_SUCCESS_MSG)) {
				passed("Success Toaster Message is displayed as expected "+actSuccMsg);
			}else {
				fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.SPECIALIST_SUBMITADVICE_SUCCESS_MSG);
			}
			takeScreenshot();
			takeScreenshot();
		}else {
			fail("Success toaster message is not displayed");
		}
	}
	
	public void enterSpecialistAdviceForm() {
		waitForElement(txtPersonalNote);
		enterText(txtPersonalNote, "PersonalNote", "Hi Dr "+generateRandomString(20, "alpha"));
		enterText(txtRecordsAdvice, "RecordsAdvice", "The extra oral photos look amazing "+generateRandomString(20, "alpha"));
		selectRadioButton1(txtTreatmentOutcomes, "TreatmentOutcomes", "Compromised");
		selectRadioButton1(txtComplexityTreatment, "Complexity", "Mild");
		enterText(txtDiagnosisAndProblemList, "DiagnosisAndProblemList", "All types");
		enterText(txtEstimatedTreatmentRange, "EstimatedTreatmentRange", "8 months");
		enterText(txtAimsOfTreatmentAndSummary, "AimsOfTreatmentAndSummary", "Automation Test");
		enterText(txtAlternativeTreatmentPlan, "AlternativeTreatmentPlan", "No Treatment at all");
		takeScreenshot();
		enterText(txtSpecificToPatientRadiograph, "SpecificToPatientRadiograph", "As part of the autoamtion tseting");
		enterText(txtSubmissionCallOut_positive, "SubmissionCallOut_positive", "As part of the autoamtion tseting");
		enterText(txtSubmissionCallOut_potentialBlocker, "SubmissionCallOut_potentialBlocker", "As part of the autoamtion tseting");
		enterText(txtSubmissionCallOut_problematic, "SubmissionCallOut_problematic", "As part of the autoamtion tseting");
		sleep(Values.sleepTime);
		enterText(txtAttachmentPlan, "AttachmentPlan", "automation testing");
		enterText(txtIprPlan, "IPR Plan", "automation testing");
		takeScreenshot();
		selectFirstOptionFromDropDown(txtElasticType, "Elastic Type");
		enterText(txtElasticWearSchedule, "ElasticWearSchedule", "automation testing");
		enterText(txtRecommendedButtons, "RecommendedButtons", "automation testing");
		enterText(txtOtherAuxillaries, "OtherAuxillaries", "automation testing");
		sleep(Values.sleepTime);
		enterText(txtGettingStartedConsent, "Consent", "My Consent");
		if(isElementPresent(txtGettingStartedTouchpoints)) {
			enterText(txtGettingStartedTouchpoints, "Touch Points", "My Consent");
		}
		enterText(txtGettingStartedSuggestedWearSchedule, "SuggestedWearSchedule", "My Consent");
		
		sleep(Values.sleepTime);
		enterText(txtFinalAdviceRefinementAdvice, "FinalAdviceRefinementAdvice", "My Consent");
		enterText(txtFinalAdviceRestorative, "Restorative", "My Consent");
		enterText(txtFinalAdviceRetentionAdvice, "RetentionAdvice", "My Consent");
		
	}
	
	public void submitAdviceValidateMandatoryError() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(3,patientName);
		navigateToTreatmentProposalsAndSelectDesgin("1");
		waitForElement(btnSubmitAdvice);
		clickOn(btnSubmitAdvice, "Submit Advice");
		sleep(2000);
		navigateToSecondWindow();
		sleep(Values.sleepTime);
		takeScreenshot();
		clickOn(btnSubmitAdviceSubmit, "Submit Advice");
		
		validateErrorMessageOfField(txtPersonalNote, "personalNote", "Please enter personal note to dentist");
		validateErrorMessageOfField(txtDiagnosisAndProblemList, "diagnosisAndProblemList", "Please enter details");
		validateErrorMessageOfField(txtAimsOfTreatmentAndSummary, "aimsOfTreatmentAndSummary", "");
		takeScreenshot();
		validateErrorMessageOfField(txtGettingStartedConsent, "consent", "");
		validateErrorMessageOfField(txtGettingStartedSuggestedWearSchedule, "suggestedWearSchedule", "");
		validateErrorMessageOfField(txtFinalAdviceRetentionAdvice, "retentionAdvice", "");
		takeScreenshot();
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
	
	public void navigateToTreatmentDesignsAndValidateStatus(String designNum,String caseType,String Status,String revisionRequested) {
		waitForElement(tabTreatmentProposals);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentProposals)) {
			clickOn(tabTreatmentProposals, "Treatment Proposals Tab");
			waitForElement(By.xpath("//*[contains(@id,'treatment-proposals')]"));
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-proposals')]/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-proposals')]/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath("./div[1]/span[2]")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
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
			if(blnFound) {
				takeScreenshot();
				failAssert("Design with expected design number is not found , Design Number "+designNum);
			}
		}else {
			failAssert("Unable to navigate to Treatment Designs tab");
		}
	}
	
	public void sendChatMessage(String user) {
		waitForElement(weChatOpen);
		clickOn(weChatOpen, "Chat Button");
		waitForElement(txtChatMessage);
		List<WebElement> wesChatUsers = browser.findElements(By.xpath("//div[@id='CHAT_TOOL']//small"));
		for(WebElement we:wesChatUsers) {
			if(we.getText().equalsIgnoreCase(user)) {
				clickOn(we, user +" section of chat");
			}
		}
	}

}
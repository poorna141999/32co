package pages.designer;

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
import Utilities.constans.Values;

public class DesignerDashboardPage extends Page {

	@FindBy(xpath = "//a/button[text()='Host a meeting']")
	private WebElement lnkHostMeeting;

	@FindBy(xpath = "//div/*[@alt='logoutUser']")
	private WebElement btnLogout;

	@FindBy(xpath = "//div[@role='tab' and text()='Case Info']")
	private WebElement tabCaseInfo;

	@FindBy(xpath = "//div[@role='tab' and text()='Treatment Designs']")
	private WebElement tabTreatmentDesigns;
	
	@FindBy(id = "dashboard-menu")
	private WebElement menuDashboard;
	
	@FindBy(id = "basic_email")
	private WebElement txtEmail;

	@FindBy(id = "animationUrl")
	private WebElement txtToothMovementUrl;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weSuccessMessage;

	@FindBy(xpath = "//button/span[text()='SAVE STL 3D1 URL']")
	private WebElement btnSaveSTL3DURL;

	@FindBy(xpath = "//button/span[text()='UPDATE STL 3D1 URL']")
	private WebElement btnUpdateSTL3DURL;

	@FindBy(xpath = "//button/span[text()='SUBMIT DESIGN +']")
	private WebElement btnSubmitDesign;

	@FindBy(xpath = "//h5[text()='IPR']/following-sibling::div//input[@id='react-csv-reader-input']")
	private WebElement imgCsvIPRData;

	@FindBy(id = "arches")
	private WebElement lstArches;

	@FindBy(id = "totalSteps")
	private WebElement txtTotalSteps;

	@FindBy(id = "havingAttachment")
	private WebElement lstAttachments;

	@FindBy(id = "attachmentStages")
	private WebElement mlstAttachmentStages;

	@FindBy(id = "upperStageNumber")
	private WebElement txtUpperStageNumber;

	@FindBy(id = "lowerStageNumber")
	private WebElement txtLowerStageNumber;

	@FindBy(id = "havingIpr")
	private WebElement lstIPR;

	@FindBy(id = "iprStages")
	private WebElement mlstIPRStages;

	@FindBy(id = "havingElastic")
	private WebElement lstAuxilary;

	@FindBy(id = "elastics")
	private WebElement mlstAuxilaryDetails;

	@FindBy(id = "toothAnimationUrl")
	private WebElement txtToothAnimationUrl;

	@FindBy(id = "auxiliaryDetail")
	private WebElement txtAuxiliaryDetail;

	@FindBy(id = "comment")
	private WebElement txtComments;

	@FindBy(xpath = "//h5[text()='Tooth movement table']/following-sibling::div//input[@id='react-csv-reader-input']")
	private WebElement imgCsvToothMovementTable;

	@FindBy(id = "additionalAttachments")
	private WebElement imgAdditionalAttachments;

	@FindBy(xpath = "//button/span[text()='Submit']")
	private WebElement btnSubmit;

	@FindBy(xpath = "//span[text()='Treatment Design']")
	private WebElement weTreatmentDesign;
	
	@FindBy(xpath = "//div[contains(@class,'menuContext')]/span[text()='Patients']")
	private WebElement menuPatients;
	
	@FindBy(xpath = "//input[@placeholder='search patients']")
	private WebElement txtSearchPatients;
	
	@FindBy(xpath = "//div/*[@data-node-key='treatment-designs']")
	private WebElement tabTreatmentDesgins;
	
	@FindBy(xpath = "//span[text()='Treatment Design']")
	private WebElement weTreatmentDesignHeader;
	
	@FindBy(id = "upload-stl-files")
	private WebElement btnUploadStls;

	@FindBy(xpath = "//div[@role='dialog']//button/span[text()='cancel']")
	private WebElement btnCancel;

	@FindBy(xpath = "//div[@role='dialog']//button/span[text()='confirm']")
	private WebElement btnConfirm;

	@FindBy(xpath = "//span[text()='Upper arch']/following-sibling::span")
	private WebElement weUpperArchCount;

	@FindBy(xpath = "//span[text()='Lower arch']/following-sibling::span")
	private WebElement weLowerArchCount;
	
	@FindBy(xpath = "//input[@type='file']")
	private WebElement txtUploadSTLFile;

	@FindBy(xpath = "//button/span[text()='Continue']")
	private WebElement btnContinue;

	@FindBy(xpath = "//button/span[text()='Delete files']")
	private WebElement btnDeleteFiles;
	
	
	public DesignerDashboardPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

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
	
	public void navigateToDashboardMenu() {
		waitForElement(menuDashboard);
		clickOn(menuDashboard, "Menu Dashboard");
		waitForElement(By.id("kanban-board-container"));
		if(isElementPresent(By.id("kanban-board-container"))) {
			passed("Dashboard menu is displayed as expected ");
		}else {
			fail("Dashboard menu is not displayed ");
		}
	}

	public void selectRecordFromDashboard(int colNum,String patientName) {
		waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));
		List<WebElement> wesSection = browser.findElements(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));		
		WebElement weCurrentCase = null;
		if(wesSection.size()==5) {
			passed("All the 5 sections are displayed in designer dashboard page");
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
					takeScreenshot();
					failAssert("Did not navigate to Case Details page after selecting the record");
				}
			}else {
				takeScreenshot();
				failAssert("Case with patient name "+ patientName + " is not found in the column num "+colNum);
			}
		}else {
			fail("All the 5 sections are not displayed in designer dashboard page");
		}
		takeScreenshot();
	}

	public void acceptOrRejectCases(String patientName, String acceptOrReject) {
		waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));
		List<WebElement> wesSection = browser.findElements(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]"));		
		WebElement weCurrentCase = null;
		if(wesSection.size()==5) {
			passed("All the 5 sections are displayed in designer dashboard page");
			waitForElement(By.xpath("//*[@class='ant-card-body']//div[contains(@class,'kanbanColumnContainer')]//div[@class='ant-space-item']"));
			List<WebElement> weAllNewPatient = wesSection.get(0).findElements(By.xpath(".//div[@class='ant-space-item']"));
//			for(int i=0;i< weAllNewPatient.size();i++) {
//				weAllNewPatient = wesSection.get(0).findElements(By.xpath(".//div[@class='ant-space-item']"));
//				moveToElement(weAllNewPatient.get(i));
//				String strEachCase = weAllNewPatient.get(i).getText();
//				if(strEachCase.contains(patientName)) {
//					weCurrentCase = weAllNewPatient.get(i);
//				}
//			}
			for(int i=weAllNewPatient.size()-1;i>0;i--) {
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
							fail("After accepting the case in designer dashboard did not navigate to Case Info page");
						}
						takeScreenshot();
					} catch (Exception e) {
						fail("Exception caught while accept or rejecting new cases "+e.getMessage());
					}
				}else if(acceptOrReject.equalsIgnoreCase("reject")) {
					try {
						WebElement btnReject = weCurrentCase.findElement(By.xpath(".//button/span[text()='Reject']"));
						clickOn(btnReject, "Reject Button");
						waitForElement(weSuccessMessage);
						if(isElementPresent(weSuccessMessage)) {
							passed("Toaster message is displayed as expected");
							String actSuccMsg = getText(weSuccessMessage);
							if(actSuccMsg.contains(Constants.DESIGNER_REJECT_SUCCESS_MSG)) {
								passed("Success Toaster Message is displayed as expected "+actSuccMsg);
							}else {
								fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DESIGNER_REJECT_SUCCESS_MSG);
							}
							takeScreenshot();
						}else {
							takeScreenshot();
							failAssert("Success toaster message is not displayed");
						}
					} catch (Exception e) {
						takeScreenshot();
						failAssert("Exception caught while accept or rejecting new cases"+e.getMessage());
					}
				}else {
					takeScreenshot();
					failAssert("Invalid input paramter for this function, only accept or reject can be passed, instead of "+acceptOrReject);
				}
			}else {
				takeScreenshot();
				failAssert("New Case assigned is not displayed in designer dashboard "+ patientName);
			}
		}else {
			fail("All the 5 sections are not displayed in designer dashboard page");
		}
		takeScreenshot();
	}

	public void submitSTL3D1Url() {
		if (isElementPresent(txtToothMovementUrl)) {
			enterText(txtToothMovementUrl, "Toothe Movement URL", Constants.DESIGNER_STL_URL);
			clickOn(btnSaveSTL3DURL, "Save STL 3D1 URL Button");
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Toaster message is displayed as expected");
				String actSuccMsg = getText(weSuccessMessage);
				if(actSuccMsg.contains(Constants.DESIGNER_SUBMIT_STL_3D1_URL_SUCC_MSG)) {
					passed("Success Toaster Message is displayed as expected "+actSuccMsg);
				}else {
					fail("Success Toaster Message Validation , Actual is "+actSuccMsg +", But expected is "+Constants.DESIGNER_SUBMIT_STL_3D1_URL_SUCC_MSG);
				}				
			}else {
				takeScreenshot();
				fail("Success toaster message is not displayed");
			}
		}else {
			takeScreenshot();
			failAssert("Unable to enter the STL 3D1 URL, not in the valid page");
		}
		takeScreenshot();
	}

	public void validateAcceptCaseSolo(){
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		acceptOrRejectCases(patientName, "accept");
	}

	public void validateAcceptCaseDuo(){
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		acceptOrRejectCases(patientName, "accept");
		submitSTL3D1Url();
	}

	public void validateRejectCase(){
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		acceptOrRejectCases(patientName, "reject");
	}

	public void submitNewDesign() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(3, patientName);
		HashMap<String, Object> details = convertJsonToMap("."+data.get("DESIGNER_JSONPATH"));
		waitForElement(btnSubmitDesign);
		clickOn(btnSubmitDesign, "Submit Design button");
		waitForElement(txtTotalSteps);
		enterDetailsInSubmitNewDesign(details);
		clickOn(btnSubmit, "Submit Button");
		waitForElement(weTreatmentDesign);
		if(isElementPresent(weTreatmentDesign)) {
			passed("Treatment design overiew page displayed after submitting the design");
		}else {
			fail("Treatment design overiew page is not displayed after submitting the design");
		}
		clickOn(menuPatients, "Patients Menu");
		takeScreenshot();
		validateStatusOfPatients(patientName, Design_Status.AWAITING_DESIGN_APPROVAL.toString(), "AWAITING REVIEW");
	}
	
	public void submitSecondDesign() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(4, patientName);
		HashMap<String, Object> details = convertJsonToMap("."+data.get("DESIGNER_JSONPATH"));
		waitForElement(btnSubmitDesign);
		clickOn(btnSubmitDesign, "Submit Design button");
		waitForElement(txtTotalSteps);
		enterDetailsInSubmitNewDesign(details);
		clickOn(btnSubmit, "Submit Button");
		waitForElement(weTreatmentDesign);
		if(isElementPresent(weTreatmentDesign)) {
			passed("Treatment design overiew page displayed after submitting the design");
		}else {
			fail("Treatment design overiew page is not displayed after submitting the design");
		}
		clickOn(menuPatients, "Patients Menu");
		takeScreenshot();
		validateStatusOfPatients(patientName, Design_Status.AWAITING_DESIGN_APPROVAL.toString(), "AWAITING REVIEW");
	}
	
	public void selectPatientFromDashboard(int colNum) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(colNum, patientName);
	}
	
	public void submitDesign() {
		HashMap<String, Object> details = convertJsonToMap("."+data.get("DESIGNER_JSONPATH"));
		waitForElement(btnSubmitDesign);
		clickOn(btnSubmitDesign, "Submit Design button");
		waitForElement(txtTotalSteps);
		enterDetailsInSubmitNewDesign(details);
		clickOn(btnSubmit, "Submit Button");
		waitForElement(weTreatmentDesign);
		if(isElementPresent(weTreatmentDesign)) {
			passed("Treatment design overiew page displayed after submitting the design");
		}else {
			fail("Treatment design overiew page is not displayed after submitting the design");
		}
	}

	public void enterDetailsInSubmitNewDesign(HashMap<String, Object> details){
		//TODO select CSV data for IPR
		uploadFile(imgCsvIPRData, getValue(details, "iprcsv[0]"), btnConfirm);
		waitForElement(lstArches);
		sleep(2000);
		selectOptionDropDown(lstArches, "Arches In Treatment", getValue(details,"arches"));
		clearText(txtTotalSteps);
		enterText(txtTotalSteps, "Total Steps",  getValue(details,"totalSteps"));
		selectOptionDropDown(lstAttachments, "Attachments", getValue(details,"havingAttachment"));
		selectMultiOptionDropDown(mlstAttachmentStages, "Attachments Stages", getMultiValue(details,"attachmentStages"));
		enterText(txtUpperStageNumber, "No Of Upper Alligners",  getValue(details,"upperStageNumber"));
		enterText(txtLowerStageNumber, "No Of Lower Alligners",  getValue(details,"lowerStageNumber"));
		selectOptionDropDown(lstIPR, "IPR", getValue(details,"havingIpr"));
		selectMultiOptionDropDown(mlstIPRStages, "IPR Stages", getMultiValue(details,"iprStages"));		
		selectOptionDropDown(lstAuxilary, "Auxiliary", getValue(details,"havingElastic"));
		if(getValue(details,"havingElastic").equalsIgnoreCase("Yes")) {
			selectMultiOptionDropDown(mlstAuxilaryDetails, "Auxiliary Details", getMultiValue(details,"elastics"));
		}
		enterText(txtToothAnimationUrl, "Tooth Movement URL",  getValue(details,"toothAnimationUrl"));
		enterText(txtAuxiliaryDetail, "Auxiliary Detail",  getValue(details,"auxiliaryDetail"));
		enterText(txtComments, "Commments",  getValue(details,"comment"));

		uploadFile(imgCsvToothMovementTable, getValue(details, "toothMovementTablecsv[0]"));
		uploadImage(imgAdditionalAttachments, getValue(details, "additionalAttachments[0]"));
		sleep(Values.sleepTime*2);
		takeScreenshot();
	}
	
	public void validateStatusOfPatients(String patientName,String tabName,String status) {
		if(!isElementPresent(txtSearchPatients)) {
			clickOn(menuPatients, "Patients Menu");
		}		
		waitForElement(txtSearchPatients);
		takeScreenshot();
		enterText(txtSearchPatients, "Search textbox", patientName);
		clickOn(By.xpath("//div[@data-node-key='"+tabName+"']/div"), tabName+" Tab");
		sleep(Values.sleepTime*3);
		By byPatientList = By.xpath("//div[contains(@id,'"+tabName+"')]/div[@class='patient-list-container']/div/div");
		List<WebElement> wePatientList = browser.findElements(byPatientList);
		if(wePatientList.size()>1) {
			passed("Patient "+ patientName +" is displayed in the section "+tabName);
			String  actStatus = wePatientList.get(0).findElement(By.xpath("./div/div[3]")).getText();
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
	
	public void selectRecordFromCurrentSection(String tabName) {
		if(isElementPresent(By.xpath("//div[contains(@id,'"+tabName+"')]/div[@class='patient-list-container']/div/div//button/span[text()='View']"))) {
			clickOn(By.xpath("//div[contains(@id,'"+tabName+"')]/div[@class='patient-list-container']/div/div//button/span[text()='View']"), "View Button");
			waitForElement(tabCaseInfo);
		}else {
			fail("No Patients listed in this section");
			takeScreenshot();
		}
	}
	
	public void validateMandatoryError() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(3, patientName);
		HashMap<String, Object> details = convertJsonToMap("."+data.get("DESIGNER_JSONPATH"));
		waitForElement(btnSubmitDesign);
		clickOn(btnSubmitDesign, "Submit Design button");
		waitForElement(txtTotalSteps);
		clickOn(btnSubmit, "Submit Button");
		sleep(Values.sleepTime);
		validateErrorMessageOfField(lstArches, "Arches", "Arches in treatment is required");
		validateErrorMessageOfField(txtTotalSteps, "Total Steps", "Please enter total steps");
		validateErrorMessageOfField(lstAttachments, "Attachments", "Attachments is required");
		validateErrorMessageOfField(txtUpperStageNumber, "Upper Alligners", "Upper aligners is required");
		validateErrorMessageOfField(txtLowerStageNumber, "Lower Alligners", "Lower aligners is required");
		validateErrorMessageOfField(lstIPR, "IPR", "IPR is required");
		validateErrorMessageOfField(lstAuxilary, "Auxilary", "Elastics is required");
		validateErrorMessageOfField(txtToothAnimationUrl, "Tooth Movement Url", "Tooth movement URL is required");
		takeScreenshot();
		
		selectOptionDropDown(lstAuxilary, "Auxiliary List", "Yes");
		sleep(Values.sleepTime);
		clickOn(btnSubmit, "Submit Button");
		validateErrorMessageOfField(mlstAuxilaryDetails, "Auxiliary List", "Auxiliary details is required");
		takeScreenshot();
	}
	
	public void uploadSTLs() {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		selectRecordFromDashboard(5, patientName);
		navigateToTreatmentDesignsAndSelectDesgin("1", "Approved");
		takeScreenshot();
		waitForElement(btnUploadStls);
		
		clickOn(btnUploadStls, "Upload Files Button");
		waitForElement(weUpperArchCount);
		sleep(Values.sleepTime*2);
		String strUpper = weUpperArchCount.getText();
		String strLower = weLowerArchCount.getText();
		int total = Integer.parseInt(strUpper) + Integer.parseInt(strLower);
		for(int i=0;i<total;i++) {
			uploadSTLFile(txtUploadSTLFile, ".\\files\\stlFiles.stl", btnContinue);
		}
		clickOn(btnConfirm, "Confirm Button");	
		validateStatusOfPatients(patientName, Design_Status.COMPLETED.toString(), Design_Status.COMPLETED.toString());
	}
	
	public void navigateToTreatmentDesignsAndSelectDesgin(String designNum,String designStatus) {
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div/div"));
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div/div"));
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
					WebElement btnView = weDesign.findElement(By.xpath("./div[5]/button"));
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
	
	public void navigateToSoloTreatmentDesignsAndValidateStatus(String designNum,String status) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(By.xpath("//span[@class='ant-breadcrumb-link']/*[text()='"+patientName+"']"))) {
			clickOn(By.xpath("//span[@class='ant-breadcrumb-link']/*[text()='"+patientName+"']"), patientName +" breadcrumb Link");
		}
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			sleep(Values.sleepTime*2);
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath(".//div[1]/span[2]")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
					passed("Design Number is found "+designNum);
					String actStatus = weDesign.findElement(By.xpath(".//div[4]/div")).getText();
					if(actStatus.equalsIgnoreCase(status)) {
						passed("Admin Design status is as expected "+actStatus);
					}else {
						fail("Admin Design status is not valid, actual is "+actStatus + ", But expected is "+status);
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

}

package pages.admin;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableStyleTextStyle;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Values;

public class AdminOperationsPage extends Page {

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "operations_refresh-button")
	private WebElement btnReload;

	@FindBy(id = "operations-filters_search-input")
	private WebElement txtSearch;

	@FindBy(xpath = "//div[@role='dialog']//input[@placeholder='Search...']")
	private WebElement txtSearchInDialog;

	@FindBy(xpath = "//span[@aria-label='search']")
	private WebElement btnSearch;

	@FindBy(xpath = "//div[@role='dialog']//span[@aria-label='search']")
	private WebElement btnSearchInDialog;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	private WebElement weSuccessMessage;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement txtUploadSTLFile;

	@FindBy(xpath = "//button/span[text()='Continue']")
	private WebElement btnContinue;

	@FindBy(xpath = "//button/span[text()='Delete files']")
	private WebElement btnDeleteFiles;

	@FindBy(xpath = "//button/span[text()='Confirm']")
	private WebElement btnConfirm;

	@FindBy(xpath = "//button/span[text()='ASSIGN DESIGN LAB']")
	private WebElement btnAssignDesignLab;

	@FindBy(xpath = "//button/span[text()='ASSIGN SPECIALIST']")
	private WebElement btnAssignSpecialist;

	@FindBy(xpath = "//div/*[@data-node-key='treatment-designs']")
	private WebElement tabTreatmentDesgins;

	@FindBy(xpath = "//span[text()='Treatment Design']")
	private WebElement weTreatmentDesignHeader;

	@FindBy(id = "revision_request")
	private WebElement txtRevisionRequest;

	@FindBy(xpath = "//button/span[text()='Submit']")
	private WebElement btnSubmit;

	@FindBy(xpath = "//button/span[text()='Send to dentist']")
	private WebElement btnSendToDentist;

	@FindBy(xpath = "//button/span[text()='Send to manufacturer']")
	private WebElement btnSendToManufacturer;

	@FindBy(xpath = "//button/span[text()='Mark Material Received']")
	private WebElement btnMarkMaterialReceived;
	
	@FindBy(xpath = "//span[text()='Confirm Shipped']")
	private WebElement btnConfirmShipped;
	
	@FindBy(id = "register_shippingLink")
	private WebElement txtShippingLink;
	
	@FindBy(xpath = "//button/span[text()='CONFIRM']")
	private WebElement btnConfirmShipping;
	




	public AdminOperationsPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void validateDesignersPageUI() {
		waitForElement(By.xpath("//table/thead//tr/th"));
		sleep(3000);
		isElementDisplayed(btnReload, "Reload Button");
		isElementDisplayed(btnSearch, "Search Button");
		isElementDisplayed(txtSearch, "Search TextBox");
		isElementDisplayed(btnNextPage, "Next Page Button");
		isElementDisplayed(btnPreviousPage, "Previous Page Button");
		isElementDisplayed(wePagination, "Records per page dropdown");
		String strPagination = wePagination.getText();
		if(strPagination.equals("10 / page")) {
			passed("By default 10 / page records are displayed ");
		}else {
			fail("By default 10 / page records are not displayed, Actual is "+strPagination);
		}
		String[] expHeaders = {"Name","Cases Currently Live","Cases This Month","Cases Total","Cases Rejected","Active"};
		List<WebElement> weHeaders = browser.findElements(By.xpath("//table/thead//tr/th"));

		for(int i=1;i<weHeaders.size();i++) {
			String str = weHeaders.get(i).getText();
			if(expHeaders[i-1].equals(str)) {
				passed("Column Header is displayed as expected "+str);
			}else {
				fail("Expected column header is not displayed, Expected is "+expHeaders[i-1] +", but actual is "+str);
			}
		}
		takeScreenshot();
	}
	
	public void searchAndValidateStatusInOpearionsPage(String expStatus) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		searchPatientCase("All", patientName);
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
	}

	public void assignDesignerForSoloCase() {
		try {
			HashMap<String, Object> details = convertJsonToMap("."+data.get("JSONPATH"));
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			searchPatientCase("All", patientName);
			List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
			if(weSearchResults.size()>1) {
				if(getValue(details,"impressionType").contains("Silicone Impressions")) {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("Upload STLs")) {
						adminPerformOperations("UPLOADSTLS");
						adminPerformOperations("ASSIGNSOLO");
					}else {
						fail("Upload STLs Button is not displayed for the Impression type - Silicone Impressions (via Post)");
					}
				}else {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("ASSIGN")) {
						adminPerformOperations("ASSIGNSOLO");
					}else {
						fail("ASSIGN Button is not displayed for the Impression type - Digital Intra-Oral Scans (.STLs)");
					}
				}
			}else {
				fail("No Records found in the table with name "+patientName +", Unable to Continue");
			}
		} catch (Exception e) {
			fail("Exception caught "+e.getMessage());
		}
	}

	public void assignDesignerForDuoCase() {
		try {
			HashMap<String, Object> details = convertJsonToMap("."+data.get("JSONPATH"));
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			searchPatientCase("All", patientName);
			List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
			if(weSearchResults.size()>1) {
				if(getValue(details,"impressionType").contains("Silicone Impressions")) {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("Upload STLs")) {
						adminPerformOperations("UPLOADSTLS");
						adminPerformOperations("ASSIGNDUO");
					}else {
						fail("Upload STLs Button is not displayed for the Impression type - Silicone Impressions (via Post)");
					}
				}else {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("ASSIGN")) {
						adminPerformOperations("ASSIGNDUO");
					}else {
						fail("ASSIGN Button is not displayed for the Impression type - Digital Intra-Oral Scans (.STLs)");
					}
				}
			}else {
				fail("No Records found in the table with name "+patientName +", Unable to Continue");
			}
		} catch (Exception e) {
			fail("Exception caught "+e.getMessage());
		}
	}
	
	public void assignDesignerForDuoCase(boolean blnUploadStl) {
		try {
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			searchPatientCase("All", patientName);
			List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
			if(weSearchResults.size()>1) {
				if(blnUploadStl) {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("Upload STLs")) {
						adminPerformOperations("UPLOADSTLS");
						adminPerformOperations("ASSIGNDUO");
					}else {
						fail("Upload STLs Button is not displayed for the Impression type - Silicone Impressions (via Post)");
					}
				}else {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("ASSIGN")) {
						adminPerformOperations("ASSIGNDUO");
					}else {
						fail("ASSIGN Button is not displayed for the Impression type - Digital Intra-Oral Scans (.STLs)");
					}
				}
			}else {
				fail("No Records found in the table with name "+patientName +", Unable to Continue");
			}
		} catch (Exception e) {
			fail("Exception caught "+e.getMessage());
		}
	}
	
	public void assignDesignerForSoloCase(boolean blnUploadStl) {
		try {
			HashMap<String, Object> details = convertJsonToMap("."+data.get("JSONPATH"));
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			searchPatientCase("All", patientName);
			List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
			if(weSearchResults.size()>1) {
				if(blnUploadStl) {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("Upload STLs")) {
						adminPerformOperations("UPLOADSTLS");
						adminPerformOperations("ASSIGNSOLO");
					}else {
						fail("Upload STLs Button is not displayed for the Impression type - Silicone Impressions (via Post)");
					}
				}else {
					WebElement btnUploadSTLs =   weSearchResults.get(1).findElement(By.xpath("./div"));
					String btnName = btnUploadSTLs.getText();
					if(btnName.equals("ASSIGN")) {
						adminPerformOperations("ASSIGNSOLO");
					}else {
						fail("ASSIGN Button is not displayed for the Impression type - Digital Intra-Oral Scans (.STLs)");
					}
				}
			}else {
				fail("No Records found in the table with name "+patientName +", Unable to Continue");
			}
		} catch (Exception e) {
			fail("Exception caught "+e.getMessage());
		}
	}


	public void searchPatientCase(String status,String name) {
		waitForElement(txtSearch);
		if(isElementPresent(txtSearch)) {
			WebElement we = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
			selectOptionLoadingDropDown(we, "Search Filter", status);
			sleep(1000);
			clearText(txtSearch);
			enterText(txtSearch, "Search Textbox", name);
			clickOn(btnSearch, "Search Button");
			if(status.equalsIgnoreCase("Draft")) {
				waitForElement(By.xpath("//table/tbody/tr/td[2]//a"));
				sleep(3000);
				List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[2]//a"));
				if(weSearchResults.size()>0) {
					passed("Search Results are displayed as expected");
				}else {
					fail("Search Results are not displayed");
				}
			}else {
				waitForElement(By.xpath("//table/tbody/tr/td[3]"));
				sleep(3000);
				List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[3]"));
				if(weSearchResults.size()>0) {
					passed("Search Results are displayed as expected");
				}else {
					fail("Search Results are not displayed");
				}
			}
			takeScreenshot();
		}else {
			fail("Search text box is not displayed");
		}
	}

	public void validateStatusOfCurrentRecordInAllDropdown(String expStatus) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		clickOn(btnReload, "Reload Button");
		searchPatientCase("All", patientName);
		verifyStatusOfPatientFirstRecord(expStatus);
	}


	public void validateStatusOfCurrentRecordInSpecifcDropdown(String DropDown) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		searchPatientCase(DropDown, patientName);
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[3]"));
		if(weSearchResults.size()>1) {
			passed("Patient is displayed in the status dropdown "+DropDown);
		}else {
			fail(patientName+" Patient is not displayed in the status dropdown "+DropDown );
		}
	}

	public void selectFirstPatientFromTable() {
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[3]//a"));
		if(weSearchResults.size()>0) {
			clickOn(weSearchResults.get(0), weSearchResults.get(0).getText());
			waitForElement(By.xpath("//*[contains(@id,'case-info')]"));
		}else {
			fail("No Records found in the table, Unable to select");
		}
		takeScreenshot();
	}
	
	public void selectCheckBoxFirstPatientFromTable() {
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[1]//input/following-sibling::span"));
		if(weSearchResults.size()>0) {
			clickOn(weSearchResults.get(0), "Check box");
		}else {
			fail("No Records found in the table, Unable to select checkbox");
		}
		takeScreenshot();
	}

	public void verifyStatusOfPatientFirstRecord(String expStatus) {
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
		if(weSearchResults.size()>1) {
			String actStatus =  weSearchResults.get(1).getText();
			if(actStatus.equalsIgnoreCase(expStatus)) {
				passed("Status of the patient is as expected "+actStatus);
			}else {
				fail("Status of the patient is not as expected , Actual is "+ actStatus + ", But expected is "+expStatus);
			}
			takeScreenshot();
		}else {
			fail("No Records found in the table, Unable to select");
		}
	}

	public void verifyExclamationForFirstRecord(boolean blnExcl) {
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[3]"));
		if(weSearchResults.size()>1) {
			List<WebElement> wesExcl =  weSearchResults.get(1).findElements(By.xpath("//span[@class='ant-badge']/span[@aria-label='exclamation']"));
			if(blnExcl) {
				if(wesExcl.size()>0) {
					passed("Exclamation Mark is displayed for the record as expected");
				}else {
					fail("Exclamation Mark is not displayed for the record");
				}
			}else {
				if(wesExcl.size()>0) {
					fail("Exclamation Mark is displayed for the record, which is not expected");
				}else {
					passed("Exclamation Mark is not displayed for the record as expected");					
				}
			}
			takeScreenshot();
		}else {
			fail("No Records found in the table, Unable to select");
		}
	}
	
	public void refinementAdminUploadStls() {
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]/div"));
		if(weSearchResults.size()>1) {
			String actStatus =  weSearchResults.get(1).getText();
			if(actStatus.equalsIgnoreCase("Upload STLs")) {
				passed("Status of the patient is as expected "+actStatus);
				clickOn(weSearchResults.get(1), "Upload STLs");
				//File Uplaod here
				waitForElement(btnConfirm);
				uploadSTLFile(txtUploadSTLFile, ".\\files\\stlFiles.stl", btnContinue);
				clickOn(btnConfirm, "Confirm Button");					
				waitForElement(btnReload);
				sleep(4000);
				clickOn(btnReload, "Reload Button");
				weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]//div"));
				if(weSearchResults.size()>1) {
					String newStatus =  weSearchResults.get(1).getText();
				}else {
					fail("After uploading the STLs caase is not auto assigned" );
					takeScreenshot();
				}
			}else {
				fail("Unable to perform the operation Upload STLs  , Because the patient is in status as "+ actStatus );
			}
			takeScreenshot();
		}else {
			fail("No Records found in the table, Unable to select");
		}
	}

	public void adminPerformOperations(String operation) {
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]/div"));
		switch (operation) {		
		case "UPLOADSTLS":			
			if(weSearchResults.size()>1) {
				String actStatus =  weSearchResults.get(1).getText();
				if(actStatus.equalsIgnoreCase("Upload STLs")) {
					passed("Status of the patient is as expected "+actStatus);
					clickOn(weSearchResults.get(1), "Upload STLs");
					//File Uplaod here
					waitForElement(btnConfirm);
					uploadSTLFile(txtUploadSTLFile, ".\\files\\stlFiles.stl", btnContinue);
					clickOn(btnConfirm, "Confirm Button");					
					waitForElement(btnReload);
					sleep(4000);
					clickOn(btnReload, "Reload Button");
					weSearchResults = browser.findElements(By.xpath("//table/tbody/tr/td[6]/div"));
					String newStatus =  weSearchResults.get(1).getText();
					if(newStatus.equalsIgnoreCase("ASSIGN")) {
						passed("Case status is changed to ASSIGN successfully");
					}else {
						fail("Case status is not changed to ASSIGN status, Actual is "+newStatus);
					}
				}else {
					fail("Unable to perform the operation Upload STLs  , Because the patient is in status as "+ actStatus );
				}
				takeScreenshot();
			}else {
				fail("No Records found in the table, Unable to select");
			}
			break;
		case "ASSIGNSOLO":			
			if(weSearchResults.size()>1) {
				String actStatus =  weSearchResults.get(1).getText();
				if(actStatus.equalsIgnoreCase("ASSIGN")) {
					passed("Status of the patient is as expected "+actStatus);
					clickOn(weSearchResults.get(1), "ASSIGN");
					searchInDialog(data.get("Designer Name"));
					selectFirstRecordAndAssignDesignLab("solo");
				}else {
					fail("Unable to perform the operation ASSIGN  , Because the patient is in status as "+ actStatus );
				}
				takeScreenshot();
			}else {
				fail("No Records found in the table, Unable to select");
			}	
			break;
		case "ASSIGNDUO":
			if(weSearchResults.size()>1) {
				String actStatus =  weSearchResults.get(1).getText();
				if(actStatus.equalsIgnoreCase("ASSIGN")) {
					passed("Status of the patient is as expected "+actStatus);
					clickOn(weSearchResults.get(1), "ASSIGN");
					searchInDialog(data.get("Designer Name"));
					selectFirstRecordAndAssignDesignLab("duo");
					waitForElement(btnAssignSpecialist);
					searchInDialog(data.get("Specialist Name"));
					selectFirstRecordAndAssignSpecialist();
				}else {
					fail("Unable to perform the operation ASSIGN  , Because the patient is in status as "+ actStatus );
				}
				takeScreenshot();
			}else {
				fail("No Records found in the table, Unable to select");
			}	
			break;
		default:
			break;
		}
		takeScreenshot();
	}

	public void searchAndValidateDraft() {
		searchPatientCase("All",Values.patientFirstName + " "+ Values.patientLastName);
		WebElement weTable = browser.findElement(By.xpath("//table/tbody"));
		validateTableRecordsInColumn(weTable, Values.patientFirstName + " "+ Values.patientLastName, 3, 2);
		validateTableRecordsInColumn(weTable, "DRAFT", 6, 2);
		searchPatientCase("Draft",Values.patientFirstName + " "+ Values.patientLastName);
	}

	public void searchInDialog(String name) {
		waitForElement(txtSearchInDialog);
		if(isElementPresent(txtSearchInDialog)) {
			enterText(txtSearchInDialog, "Search Textbox", name);
			clickOn(btnSearchInDialog, "Search Button");
			waitForElement(By.xpath("//div[@role='dialog']//table/tbody/tr/td[2]"));
			sleep(Values.sleepTime*3);
			List<WebElement> weSearchResults = browser.findElements(By.xpath("//div[@role='dialog']//table/tbody/tr/td[2]"));
			if(weSearchResults.size()>0) {
				passed("Search Results are displayed as expected");
			}else {
				fail("Search Results are not displayed");
			}
			takeScreenshot();
		}else {
			fail("Search text box is not displayed");
		}
	}

	public void selectFirstRecordAndAssignDesignLab(String type) {
		waitForElement(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]"));
		sleep(Values.sleepTime*3);
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]"));
		if(weSearchResults.size()>0) {
			clickOn(weSearchResults.get(1), "Selected First Record");
			clickOn(btnAssignDesignLab, "Assign Design Lab");
			if(type.equalsIgnoreCase("solo")) {
				waitForElement(weSuccessMessage);
				if(isElementPresent(weSuccessMessage)) {
					passed("Success message is displayed as expected");
					String strSuccMsg = weSuccessMessage.getText();
					String expMessage = "Successfully Assigned";
					if(strSuccMsg.contains(expMessage)) {
						passed("Success message is as expected "+expMessage);
						takeScreenshot();
					}else {
						fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
					}
				}else {
					fail("Success message is not displayed ");
				}
			}
		}else {
			fail("No Design Lab  found in the table, Unable to select");
		}
		takeScreenshot();
	}

	public void selectFirstRecordAndAssignSpecialist() {
		waitForElement(btnAssignSpecialist);
		sleep(Values.sleepTime*3);
		List<WebElement> weSearchResults = browser.findElements(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]"));
		if(weSearchResults.size()>0) {
			clickOn(weSearchResults.get(1), "Selected First Record");
			clickOn(btnAssignSpecialist, "Assign Specialist");			
			waitForElement(By.xpath("//div[@class='ant-notification-notice-message']"));
			sleep(2000);
			if (isElementPresent(By.xpath("//div[@class='ant-notification-notice-message']"))) {
				passed("Toaster Message is displayed as expected");
				String actHeader = getText(By.xpath("//div[@class='ant-notification-notice-message']"));
				if(actHeader.contains(Constants.ADMIN_OPEARIONS_ASSIGN_SPECIALIST)) {
					passed("Toaster Success message header is displayed as expected "+actHeader);
					takeScreenshot();
				}else {
					fail("Toaster Success message header is not valid , Actual is "+actHeader +", But expected is "+Constants.ADMIN_OPEARIONS_ASSIGN_SPECIALIST);
				}
			}else {
				fail("Toaster Success Message is not displayed");
			}	
		}else {
			fail("No Specialist  found in the table, Unable to select");
		}
		takeScreenshot();
	}
	
	public void navigateToSoloTreatmentDesignsAndValidateStatus(String designNum,String AdminStatus,String dentistStatus,String isRevisionRequested) {
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			sleep(Values.sleepTime*2);
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath("./div[1]/span[2]")).getText();
				//String actDesginNum = weDesign.findElement(By.xpath("./div[1]/a/span")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
					passed("Design Number is found "+designNum);
					String actAdminStatus = weDesign.findElement(By.xpath("./div[3]/div")).getText();
					if(actAdminStatus.equalsIgnoreCase(AdminStatus)) {
						passed("Admin Design status is as expected "+actAdminStatus);
					}else {
						fail("Admin Design status is not valid, actual is "+actAdminStatus + ", But expected is "+AdminStatus);
					}
					String actDentistStatus = weDesign.findElement(By.xpath("./div[4]/div")).getText();
					if(actDentistStatus.equalsIgnoreCase(dentistStatus)) {
						passed("Admin Dentist status is as expected "+actDentistStatus);
					}else {
						fail("Admin Dentist status is not valid, actual is "+actDentistStatus + ", But expected is "+dentistStatus);
					}
					String actRevisionRequested = weDesign.findElement(By.xpath("./div[5]/div")).getText();
					if(actRevisionRequested.equalsIgnoreCase(isRevisionRequested)) {
						passed("Is Revision Requested is as expected "+actRevisionRequested);
					}else {
						fail("Is Revision Requested is not valid, actual is "+actRevisionRequested + ", But expected is "+isRevisionRequested);
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
	
	public void navigateToDuoTreatmentDesignsAndValidateStatus(String designNum,String AdminStatus,String specialistStatus,String dentistStatus,String isRevisionRequested) {
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			sleep(Values.sleepTime*2);
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			for(WebElement weDesign : wesDesigns) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
				String actDesginNum = weDesign.findElement(By.xpath("./div[1]/span[2]")).getText();
				//String actDesginNum = weDesign.findElement(By.xpath("./div[1]/a/span")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
					passed("Design Number is found "+designNum);
					String actAdminStatus = weDesign.findElement(By.xpath("./div[3]/div")).getText();
					if(actAdminStatus.equalsIgnoreCase(AdminStatus)) {
						passed("Admin Design status is as expected "+actAdminStatus);
					}else {
						fail("Admin Design status is not valid, actual is "+actAdminStatus + ", But expected is "+AdminStatus);
					}
					String actSpecialistStatus = weDesign.findElement(By.xpath("./div[4]/div")).getText();
					if(actSpecialistStatus.equalsIgnoreCase(specialistStatus)) {
						passed("Admin Specialist status is as expected "+actSpecialistStatus);
					}else {
						fail("Admin Specialist status is not valid, actual is "+actSpecialistStatus + ", But expected is "+specialistStatus);					
					}
					String actDentistStatus = weDesign.findElement(By.xpath("./div[5]/div")).getText();
					if(actDentistStatus.equalsIgnoreCase(dentistStatus)) {
						passed("Admin Dentist status is as expected "+actDentistStatus);
					}else {
						fail("Admin Dentist status is not valid, actual is "+actDentistStatus + ", But expected is "+dentistStatus);
					}
					String actRevisionRequested = weDesign.findElement(By.xpath("./div[6]/div")).getText();
					if(actRevisionRequested.equalsIgnoreCase(isRevisionRequested)) {
						passed("Is Revision Requested is as expected "+actRevisionRequested);
					}else {
						fail("Is Revision Requested is not valid, actual is "+actRevisionRequested + ", But expected is "+isRevisionRequested);
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


	public void navigateToTreatmentDesignsAndSelectDesign(String designNum,String designStatus) {
		waitForElement(tabTreatmentDesgins);
		boolean blnFound =false;
		if(isElementPresent(tabTreatmentDesgins)) {
			clickOn(tabTreatmentDesgins, "Treatment Designs Tab");
			waitForElement(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			sleep(Values.sleepTime*4);
			List<WebElement> wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
			for(int i=0;i< wesDesigns.size();i++) {
				wesDesigns = browser.findElements(By.xpath("//div[contains(@id,'panel-treatment-designs')]/div/div/div"));
				String actDesginNum = wesDesigns.get(i).findElement(By.xpath("./div[1]/span[2]")).getText();
				//String actDesginNum = wesDesigns.get(i).findElement(By.xpath("./div[1]/span[2]")).getText();
				if(actDesginNum.equals(designNum)) {
					blnFound =false;
					passed("Design Number is found "+designNum);
					String actDesignStatus = wesDesigns.get(i).findElement(By.xpath("./div[3]/div")).getText();
					if(actDesignStatus.equalsIgnoreCase(designStatus)) {
						passed("Design status is as expected");
					}else {
						//fail("Design status is not valid actual is "+actDesignStatus + ", But expected is "+designStatus);
					}
					takeScreenshot();
					//WebElement btnView = wesDesigns.get(i).findElement(By.xpath("./div/button"));
					WebElement btnView = wesDesigns.get(i).findElement(By.xpath("./div[1]/span[2]"));
					clickOn(btnView, "View Link");
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

	public void sendToDentist() {
		waitForElement(btnSendToDentist);
		if(isElementPresent(btnSendToDentist)) {
			clickOn(btnSendToDentist, "Send to Dentist Button");
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Success message is displayed as expected");
				String strSuccMsg = weSuccessMessage.getText();
				String expMessage = Constants.ADMIN_OPEARIONS_SEND_TO_DENTIST_SUCC;
				if(strSuccMsg.contains(expMessage)) {
					passed("Success message is as expected "+expMessage);					
				}else {
					fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
				}
				takeScreenshot();
			}else {
				fail("Success message is not displayed ");
				takeScreenshot();
			}
			
		}else {
			takeScreenshot();
			failAssert("Send to dentist button is not displayed ");
		}
	}
	
	public void revisionRequest() {
		waitForElement(txtRevisionRequest);
		enterText(txtRevisionRequest, "Revision Request", "Revision Requested By Admin");
		if(isElementPresent(btnSubmit)) {
			clickOn(btnSubmit, "Submit Button");
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Success message is displayed as expected");
				String strSuccMsg = weSuccessMessage.getText();				
				String expMessage = Constants.ADMIN_OPEARIONS_REVISION_REQUEST;
				if(strSuccMsg.contains(expMessage)) {
					passed("Success message is as expected "+expMessage);					
				}else {
					fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
				}
				takeScreenshot();
			}else {
				fail("Success message is not displayed ");
				takeScreenshot();
			}			
		}else {
			takeScreenshot();
			failAssert("Submit button is not displayed in Revision Requests");
		}
	}

	public void sendToManufacturer() {
		waitForElement(btnSendToManufacturer);
		if(isElementPresent(btnSendToManufacturer)) {
			clickOn(btnSendToManufacturer, "Send to Manufacturer Button");
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Success message is displayed as expected");
				String strSuccMsg = weSuccessMessage.getText();
				String expMessage = Constants.ADMIN_OPEARIONS_SEND_TO_MANUFACTURER_SUCC;
				if(strSuccMsg.contains(expMessage)) {
					passed("Success message is as expected "+expMessage);
					takeScreenshot();
				}else {
					fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
				}
			}else {
				fail("Success message is not displayed ");
			}
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Send to Manufacturer button is not displayed ");
		}
	}

	public void MarkMaterialReceived() {
		waitForElement(btnMarkMaterialReceived);
		if(isElementPresent(btnMarkMaterialReceived)) {
			clickOn(btnMarkMaterialReceived, "MarkMaterialReceived Button");
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				passed("Success message is displayed as expected");
				String strSuccMsg = weSuccessMessage.getText();
				String expMessage = Constants.ADMIN_OPEARIONS_MARK_MATERIAL_RECIEVED;
				if(strSuccMsg.contains(expMessage)) {
					passed("Success message is as expected "+expMessage);					
				}else {
					fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
				}
				takeScreenshot();
			}else {
				fail("Success message is not displayed ");
				takeScreenshot();
			}
			
		}else {
			takeScreenshot();
			failAssert("Confirm Shipped button is not displayed ");
		}
	}

	
	public void confirmShipped() {
		waitForElement(btnConfirmShipped);
		if(isElementPresent(btnConfirmShipped)) {
			clickOn(btnConfirmShipped, "ConfirmShipped Button");
			waitForElement(txtShippingLink);
			sleep(Values.sleepTime);
			enterText(txtShippingLink, "Shipping Link", "http://google.com");
			sleep(Values.sleepTime);
			clickOn(btnConfirmShipping, "Shipping Confrim");
			waitForElement(weSuccessMessage);
			sleep(Values.sleepTime);
			if(isElementPresent(weSuccessMessage)) {
				passed("Success message is displayed as expected");
				String strSuccMsg = weSuccessMessage.getText();
				String expMessage = Constants.ADMIN_OPEARIONS_CONFIRM_SHIPPED;
				if(strSuccMsg.contains(expMessage)) {
					passed("Success message is as expected "+expMessage);					
				}else {
					fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
				}
				takeScreenshot();
			}else {
				fail("Success message is not displayed ");
				takeScreenshot();
			}
			
		}else {
			takeScreenshot();
			failAssert("Confirm Shipped button is not displayed ");
		}
	}
	
	public void confirmShippedValidateMandatoryError() {
		waitForElement(btnConfirmShipped);
		if(isElementPresent(btnConfirmShipped)) {
			clickOn(btnConfirmShipped, "ConfirmShipped Button");
			waitForElement(txtShippingLink);
			clickOn(btnConfirmShipping, "Shipping Confrim");
			validateErrorMessageOfField(txtShippingLink, "Please paste the shipping link", "shippingLink must be a URL address");			
			takeScreenshot();			
		}else {
			takeScreenshot();
			failAssert("Confirm Shipped button is not displayed ");
		}
	}

}


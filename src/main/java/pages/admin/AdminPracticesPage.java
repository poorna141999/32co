package pages.admin;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class AdminPracticesPage extends Page {

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;
	
	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(xpath = "//span[@aria-label='reload']")
	private WebElement btnReload;

	@FindBy(id = "practices_add-new-practice-button")
	private WebElement btnAddNewPractice;
	
	@FindBy(id = "practices_filters-search-input")
	private WebElement txtSearch;
	
	@FindBy(xpath = "//span[@aria-label='search']")
	private WebElement btnSearch;
	
	@FindBy(id = "register_name")
	private WebElement txtClinicName;
	
	@FindBy(xpath = "//input[@placeholder='Enter phone number']")
	private WebElement txtPhoneNumber;
	
	@FindBy(id = "register_manager_name")
	private WebElement txtClinicAdminName;
	
	@FindBy(id = "register_address_address1")
	private WebElement txtPracticeAddress;
	
	@FindBy(id = "register_address_postCode")
	private WebElement txtZipCode;
	
	@FindBy(id = "register_manager_email")
	private WebElement txtClinicAdminEmail1;
	
	@FindBy(id = "register_manager_additionalEmail")
	private WebElement txtClinicAdminEmail12;
	
	@FindBy(id = "register_isScannerAvailable")
	private WebElement txtScannerAvailable;
	
	@FindBy(xpath = "//input[@id='register_scanners']")
	private WebElement txtScannerType;
	
	@FindBy(id = "register_taxId")
	private WebElement txtTaxId;
	
	@FindBy(id = "register_isOfferClearAligners")
	private WebElement txtIsOfferClearAligners;
	
	@FindBy(id = "register_Logo1")
	private WebElement txtLogo1;
	
	@FindBy(id = "register_Logo2")
	private WebElement txtLogo2;
	
	@FindBy(xpath = "//button/span[text()='ADD TO DATABASE']")
	private WebElement btnAddToDatabase;
	
	@FindBy(xpath = "//button/span[text()='CREATE']")
	private WebElement btnCreate;
	
	@FindBy(xpath = "//button/span[text()='UPDATE']")
	private WebElement btnUpdate;
	
	
	
	
	
	public AdminPracticesPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}
	
	public void validatePracticesPageUI() {
		isElementDisplayed(btnAddNewPractice, "ADD NEW PRACTICE BUTTON");
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
		int i=0;
		String[] expHeaders = {"Name","Number of Dentists","Number of Patients","Invoicing Emails","Address"};
		List<WebElement> weHeaders = browser.findElements(By.xpath("//table/thead//tr/th"));
		
		for(WebElement weHeader:weHeaders) {
			String str = weHeader.getText();
			if(expHeaders[i].equals(str)) {
				passed("Column Header is displayed as expected "+str);
			}else {
				fail("Expected column header is not displayed, Expected is "+expHeaders[i] +", but actual is "+str);
			}
			i++;
		}
		List<WebElement> weSortAsc = browser.findElements(By.xpath("//table/thead//tr/th//span[@aria-label='caret-up']"));
		List<WebElement> weSortDesc = browser.findElements(By.xpath("//table/thead//tr/th//span[@aria-label='caret-down']"));
		
		if(weSortAsc.size()==5 && weSortDesc.size()==5) {
			passed("Sort Button is displayed for all the columns as expected");
		}else {
			fail("Sort button is not displayed in all the columns");
		}
		takeScreenshot();
	}
	
	public void validateMandatoryFieldValidation() {
		clickOn(btnAddNewPractice, "Add new Practice Button");
		clickOn(btnCreate, "Create Button");
		validateErrorMessageOfField(txtClinicName, "Clinic Name", "Please enter clinic name");
		validateErrorMessageOfField(txtClinicAdminName, "Clinic Admin Name", "Please enter clinic admin name");
		validateErrorMessageOfField(txtPracticeAddress, "Practice Address", "Please enter practice address");
		validateErrorMessageOfField(txtZipCode, "Zip Code", "Please enter Zip code");
		//validateErrorMessageOfField(txtLogo1, "Logo Button 1", "Please upload logo file");
	}
	
	public void enterDetailsPractisePage(HashMap<String, String> details) {
		waitForElement(txtClinicName);
		enterText(txtClinicName, "Clinic Name", details.get("Clinic Name"));
		enterText(txtPhoneNumber, "Phone Number", details.get("Phone Number"));
		enterText(txtClinicAdminName, "Admin Name", details.get("Admin Name"));
		enterText(txtPracticeAddress, "Practice Address", details.get("Practice Address"));
		enterText(txtZipCode, "Zip Code", details.get("Zip Code"));
		enterText(txtClinicAdminEmail1, "Admin Email1", details.get("Admin Email1"));
		enterText(txtClinicAdminEmail12, "Admin Email2", details.get("Admin Email2"));
		if(details.get("Scanner").equalsIgnoreCase("No")) {
			selectOptionDropDown(txtScannerAvailable, "Scanner Available", "No");
		}else {
			selectOptionDropDown(txtScannerAvailable, "Scanner Available", "Yes");
			waitForElement(txtScannerType);
			selectMultiOptionDropDown(txtScannerType, "Scanner Type", "ITero,Medit");
		}
		enterText(txtTaxId, "Clinic Tax ID", details.get("Clinic Tax ID"));
		selectOptionDropDown(txtIsOfferClearAligners, "Offer Clear Aligners", "Yes");
		
		uploadImage(txtLogo1, ".\\files\\practicelogo1.jpg");
		uploadImage(txtLogo2, ".\\files\\practicelogo2.jpg");
	}
	
	public void searchAndSelectPractice() {
		enterText(txtSearch, "Search Textbox", Values.PracticeName);
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		waitForElement(By.xpath("//table/tbody/tr/td/span"));
		List<WebElement> wesPractiseLink = browser.findElements(By.xpath("//table/tbody/tr/td/span"));
		clickOn(wesPractiseLink.get(0), "Practice Name");		
	}
	
	public void createNewPractice() {
		clickOn(btnAddNewPractice, "Add New Practice");
		HashMap<String, String> details = new HashMap<String, String> ();
		Values.PracticeName = "AutoClinic_"+generateRandomString(5, "alpha");
		details.put("Clinic Name", Values.PracticeName);
		details.put("Phone Number", "+919876543215");
		details.put("Admin Name", "Auto Admin");
		details.put("Practice Address", "Bangalore India");
		details.put("Zip Code", "560023");
		details.put("Admin Email1", "auto@gmail.com");
		details.put("Admin Email2", "auto2@gmail.com");
		details.put("Scanner", "Yes");
		enterDetailsPractisePage(details);
		clickOn(btnCreate, "Create Button");
		waitForElement(By.xpath("//*[text()='Practice Saved Successfully']"));
		if(isElementPresent(By.xpath("//*[text()='Practice Saved Successfully']"))) {
			passed("Practice Saved Successfully , Success Message is displayed");
		}else {
			fail("Practice Saved Successfully message is not displayed");
		}
		takeScreenshot();
		updateNewPractice();
	}
	
	public void updateNewPractice() {
		searchAndSelectPractice();
		HashMap<String, String> details = new HashMap<String, String> ();
		details.put("Clinic Name", "AutoClinic_"+generateRandomString(5, "alpha"));
		details.put("Phone Number", "+919876543215");
		details.put("Admin Name", "Auto Admin");
		details.put("Practice Address", "Bangalore India");
		details.put("Zip Code", "560023");
		details.put("Admin Email1", "auto12@gmail.com");
		details.put("Admin Email2", "auto243@gmail.com");
		details.put("Scanner", "Yes");
		clearText(txtClinicName);
		enterText(txtClinicName, "Clinic Name", details.get("Clinic Name"));
		clearText(txtPhoneNumber);
		enterText(txtPhoneNumber, "Phone Number", details.get("Phone Number"));
		clearText(txtClinicAdminName);
		enterText(txtClinicAdminName, "Admin Name", details.get("Admin Name"));
		clearText(txtPracticeAddress);
		enterText(txtPracticeAddress, "Practice Address", details.get("Practice Address"));
		clearText(txtZipCode);
		enterText(txtZipCode, "Zip Code", details.get("Zip Code"));
		clearText(txtClinicAdminEmail1);
		enterText(txtClinicAdminEmail1, "Admin Email1", details.get("Admin Email1"));
		clearText(txtClinicAdminEmail12);
		enterText(txtClinicAdminEmail12, "Admin Email2", details.get("Admin Email2"));
		clickOn(btnUpdate, "UPDATE Button");
		waitForElement(By.xpath("//*[text()='Practice Saved Successfully']"));
		if(isElementPresent(By.xpath("//*[text()='Practice Saved Successfully']"))) {
			passed("Practice Saved Successfully , Success Message is displayed");
		}else {
			fail("Practice Saved Successfully message is not displayed");
		}
		takeScreenshot();
	}

}

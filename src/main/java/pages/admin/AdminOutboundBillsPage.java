package pages.admin;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class AdminOutboundBillsPage extends Page {


	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "outbound-bill_refresh-button")
	private WebElement btnReload;

	@FindBy(id = "outbound-bill_download-csv-button")
	private WebElement btnCsvDownload;

	@FindBy(id = "outbound-bill_filter")
	private WebElement lstFiler;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	private WebElement txtSearch;

	@FindBy(xpath = "//div[text()='Third party type']/..")
	private WebElement rbThirdPartyType;

	@FindBy(xpath = "//div[text()='Payment type']/..")
	private WebElement rbPaymentType;

	@FindBy(xpath = "//button/span[@aria-label='search']")
	private WebElement btnSearch;

	@FindBy(xpath = "//button/span[text()='Clear'']")
	private WebElement btnClear;


	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;
	
	@FindBy(xpath = "//*[@class='ant-pagination-total-text']")
	private WebElement weTotalRecordCount;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//span[text()='Marked as Paid']")
	private WebElement lnkMarkAsPaid;

	@FindBy(xpath = "//table/tbody/tr[2]/td")
	private List<WebElement> wesTableRow;

	@FindBy(id = "register_deductType")
	private WebElement lstDeductType;

	@FindBy(id = "register_manufacturer")
	private WebElement txtManufacturer;

	@FindBy(id = "register_skuType")
	private WebElement txtSkuType;

	@FindBy(id = "register_validityType")
	private WebElement lstValidityType;

	@FindBy(id = "register_sumUp")
	private WebElement cbSumUp;

	@FindBy(id = "register_usageLimit")
	private WebElement txtNoOfUses;

	@FindBy(id = "register_discountType")
	private WebElement lstDiscountType;

	@FindBy(id = "register_discountValue")
	private WebElement txtDiscountValue;

	@FindBy(id = "register_externalDiscountCode")
	private WebElement txtDiscountCodeName;

	@FindBy(id = "register_internalDiscountCode")
	private WebElement lstInternalRefName;

	@FindBy(id = "register_externalDiscountDescription")
	private WebElement txtExternalDescription;

	@FindBy(id = "register_internalDiscountDescription")
	private WebElement txtInternalDescription;

	@FindBy(id = "register_addCondition")
	private WebElement rbConditions;

	@FindBy(id = "register_conditionType")
	private WebElement mlstCondition;

	@FindBy(id = "register_sendToAllUsers")
	private WebElement lstAudience;

	@FindBy(xpath = "//button/span[text()='Select Audience']")
	private WebElement btnSelectAudience;

	@FindBy(id = "discount-form-submit-button")
	private WebElement btnSaveUpdate;



	public AdminOutboundBillsPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void validateOutBoudBillsUI() {
		isElementDisplayed(btnCsvDownload, "Download CSV BUTTON");
		isElementDisplayed(btnReload, "Reload Button");
		isElementDisplayed(btnSearch, "Search Button");
		isElementDisplayed(lstFiler, "Filter DropDown");
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
		String[] expHeaders = {"","Patient name","Case ID","Dentist name","Practice","Date completed by","Type","Payee","Payment Status","Fee"};
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
		takeScreenshot();
	}

	public void validateSearchAndFilter() {
		String[] types = {"Specialist","Designer","Manufacturer"};
		for(String type : types) {
			waitForElement(lstFiler);
			clickOn(lstFiler, "Filter Dropdown");
			waitForElement(rbThirdPartyType);
			selectRadioButton(rbThirdPartyType, "Third Party Type", type);
			sleep(3000);
			List<WebElement> wesType = browser.findElements(By.xpath("//table/tbody/tr/td[7]"));
			if(wesType.size()>1) {
				boolean blnValid = true;
				for(int i=1;i<wesType.size();i++) {
					wesType = browser.findElements(By.xpath("//table/tbody/tr/td[7]"));
					String actType = wesType.get(i).getText();
					if(!(actType.trim().equalsIgnoreCase(type))) {
						blnValid = false;
						fail(" ThirdParty Type column in the row "+i+" has the invalid Value Expected is "+type + " ,But Expected is "+actType);
					}
				}
				if(blnValid) {
					passed("All the records are displayed are of expected ThirdParty type "+type);
					takeScreenshot();
				}

			}else {
				info("No Records Found in the table with the ThirdParty Type "+type);
			}
			clickOn(btnReload, "Reload Button");
			sleep(2000);
		}
		String[] paymentTypes = {"Paid","UnPaid"};
		for(String type : paymentTypes) {
			waitForElement(lstFiler);
			clickOn(lstFiler, "Filter Dropdown");
			waitForElement(rbPaymentType);
			selectRadioButton(rbPaymentType, "Payment Type", type);
			sleep(3000);
			List<WebElement> wesType = browser.findElements(By.xpath("//table/tbody/tr/td[9]"));
			if(wesType.size()>1) {
				boolean blnValid = true;
				for(int i=1;i<wesType.size();i++) {
					wesType = browser.findElements(By.xpath("//table/tbody/tr/td[9]"));
					String actType = wesType.get(i).getText();
					if(!(actType.trim().equalsIgnoreCase(type))) {
						blnValid = false;
						fail("Payment Type column in the row "+i+" has the invalid Value Expected is "+type + " ,But Expected is "+actType);
					}
				}
				if(blnValid) {
					passed("All the records are displayed are of expected Payment  type "+type);
					takeScreenshot();
				}

			}else {
				info("No Records Found in the table with the Payment  Type "+type);
			}
			clickOn(btnReload, "Reload Button");
			sleep(2000);
		}
		waitForElement(lstFiler);
		clickOn(lstFiler, "Filter Dropdown");
		clickOn(btnClear, "Clear Button");
		sleep(2000);
		
		List<WebElement> wesPatientName = browser.findElements(By.xpath("//table/tbody/tr/td[2]"));
		if(wesPatientName.size()>1) {
			String strName = wesPatientName.get(1).getText();
			enterText(txtSearch, "Search textbox", strName.trim());
			clickOn(btnSearch, "Search Button");
			sleep(2000);
			wesPatientName = browser.findElements(By.xpath("//table/tbody/tr/td[2]"));
			if(wesPatientName.size()>1) {
				passed("Search results are displayed as expected");
			}else {
				fail("No Records Found in the table after searching with valid Patient name "+strName);
			}
			takeScreenshot();
		}else {
			fail("No Records Found in the table ,Unable to validate search Functionality");
		}
	}
	
	
	public void validateDownloadCsv() {
		waitForElement(btnCsvDownload);
		String text = getText(weTotalRecordCount);
		String[] rec = text.split(" ");
		String totalRecordCount = rec[2];
		int expRows = Integer.parseInt(totalRecordCount);
		clickOn(btnCsvDownload, "Download Csv");
		waitForElement(btnCsvDownload);
		sleep(5000);
		String home = System.getProperty("user.home");
		File csvFile =  getLatestFile(home +"/Downloads/");
		if(csvFile.getName().endsWith(".csv")) {
			fail("CSV File is downloaded");
			int actRow = getRowCount(csvFile);
			if(expRows==(actRow-1)) {
				passed("Row count is matching in the downloaded CSV file");
			}else {
				fail("Row count is not matching in the downloaded CSV file");
			}
			takeScreenshot();
		}else {
			fail("CSV File is not downloaded");
		}
		takeScreenshot();
	}
	
	public void validateFees(String caseType) {
		List<WebElement> wesFees;
		String patientName = Values.patientFirstName + " "+Values.patientLastName;
		if(caseType.toLowerCase().equals("duo")) {
			waitForElement(lstFiler);
			clickOn(lstFiler, "Filter Dropdown");
			waitForElement(rbThirdPartyType);
			selectRadioButton(rbThirdPartyType, "Third Party Type", "Specialist");
			sleep(3000);
			enterText(txtSearch, "Search textbox", patientName);
			clickOn(btnSearch, "Search Button");
			sleep(2000);
			wesFees = browser.findElements(By.xpath("//table/tbody/tr/td[10]"));
			if(wesFees.size()>1) {
				String strFee = wesFees.get(1).getText();
				String expFee = "£"+Values.specialistFee;
				if(strFee.equals(expFee)) {
					passed("Fee displayed for the specialist as expected "+expFee);
				}else {
					fail("Fee displayed for the specialist is not valid , Expected is "+expFee + ", But actual is "+strFee);
				}
				takeScreenshot();
			}else {
				fail("No Records Found in the table ,New Patient Created is not reflected in Outbound Bills Page");
			}
		}
		
		waitForElement(lstFiler);
		clickOn(lstFiler, "Filter Dropdown");
		waitForElement(rbThirdPartyType);
		selectRadioButton(rbThirdPartyType, "Third Party Type", "Designer");
		sleep(3000);
		enterText(txtSearch, "Search textbox", patientName);
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		wesFees = browser.findElements(By.xpath("//table/tbody/tr/td[10]"));
		if(wesFees.size()>1) {
			String strFee = wesFees.get(1).getText();
			String expFee = "£"+Values.designerFee;
			if(strFee.equals(expFee)) {
				passed("Fee displayed for the Designer as expected "+expFee);
			}else {
				fail("Fee displayed for the Designer is not valid , Expected is "+expFee + ", But actual is "+strFee);
			}
			takeScreenshot();
		}else {
			fail("No Records Found in the table ,New Patient Created is not reflected in Outbound Bills Page");
		}
	}
}

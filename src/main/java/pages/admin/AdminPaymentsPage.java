package pages.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class AdminPaymentsPage extends Page {


	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "payments-refresh-button")
	private WebElement btnReload;
	
	@FindBy(id = "payments-filters")
	private WebElement lstFiler;
	
	@FindBy(xpath = "//div[text()='Payment type']/..")
	private WebElement rbPaymentType;
	
	@FindBy(id = "payment-filter-search-input")
	private WebElement txtSearch;
	
	@FindBy(xpath = "//button/span[@aria-label='search']")
	private WebElement btnSearch;
	
	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	public AdminPaymentsPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}
	
	public void validatePaymentsUI() {
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
		String[] expHeaders = {"Patient name","Case ID","Dentist name","Invoice create Date","Payment Status","Fee"};
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
		
		clickOn(btnNextPage, "Next Page Button");
		sleep(2000);
		clickOn(btnPreviousPage, "Previous Page Button");
		takeScreenshot();
	}
	
	public void validateSearchAndFilter() {
		String[] types = {"WAITING PAYMENT","COMPLETED","PROCESSING","CANCELED","DECLINED","REFUNDED"};
		for(String type : types) {
			waitForElement(lstFiler);
			clickOn(lstFiler, "Filter Dropdown");
			selectRadioButton(rbPaymentType, "Payment Type", type);
			sleep(3000);
			List<WebElement> wesType = browser.findElements(By.xpath("//table/tbody/tr/td[5]"));
			if(wesType.size()>1) {
				boolean blnValid = true;
				for(int i=1;i<11;i++) {
					wesType = browser.findElements(By.xpath("//table/tbody/tr/td[7]"));
					String actType = wesType.get(i).getText();
					if(!(actType.trim().equalsIgnoreCase(type))) {
						blnValid = false;
						fail(" Payment Type column in the row "+i+" has the invalid Value Expected is "+type + " ,But Expected is "+actType);
					}
				}
				if(blnValid) {
					passed("All the records are displayed are of expected Payment type "+type);
					takeScreenshot();
				}

			}else {
				info("No Records Found in the table with the Payment Type "+type);
			}
			clickOn(btnReload, "Reload Button");
			sleep(2000);
		}
		clickOn(lstFiler, "Filter Dropdown");
		selectRadioButton(rbPaymentType, "Payment Type", "ALL");
		sleep(3000);
		List<WebElement> wesPatientName = browser.findElements(By.xpath("//table/tbody/tr/td[1]"));
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
	
	public void validateFees() {
		String patientName = Values.patientFirstName + " "+Values.patientLastName;
		enterText(txtSearch, "Search textbox", patientName.trim());
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		List<WebElement> wesPatientName = browser.findElements(By.xpath("//table/tbody/tr/td[1]"));
		if(wesPatientName.size()>1) {
			passed("Search results are displayed as expected");
			String strName = wesPatientName.get(1).getText();
			if(strName.trim().equalsIgnoreCase(patientName)) {
				passed("New Patient Created is not reflected in Payments Page");
				takeScreenshot();
				List<WebElement> wesFees = browser.findElements(By.xpath("//table/tbody/tr/td[6]"));
					String strFee = wesFees.get(1).getText();
					String expFee = "£"+Values.dentistFee;
					if(strFee.equals(expFee)) {
						passed("Fee displayed for the specialist as expected "+expFee);
					}else {
						fail("Fee displayed for the specialist is not valid , Expected is "+expFee + ", But actual is "+strFee);
					}
			}else {
				fail("No Records Found in the table ,New Patient Created is not reflected in Payments Page");
				takeScreenshot();
			}
		}else {
			fail("No Records Found in the table after searching with valid Patient name "+patientName);
		}
		takeScreenshot();
	}

}

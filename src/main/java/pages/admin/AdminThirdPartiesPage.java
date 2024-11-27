package pages.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;

public class AdminThirdPartiesPage extends Page {

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "third-parties_DESIGNER-refresh-button")
	private WebElement btnReload;

	@FindBy(id = "third-parties_DESIGNER-filters-search-input")
	private WebElement txtSearch;

	@FindBy(xpath = "//span[@aria-label='search']")
	private WebElement btnSearch;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	private WebElement weSuccessMessage;
	
	@FindBy(id = "user-detail_DESIGNER-edit-fee-button")
	private WebElement lnkEditFees;
	
	@FindBy(id = "user-detail_DESIGNER-fee-input")
	private WebElement txtFeePerCase;
	
	@FindBy(id = "user-detail_DESIGNER-edit-fee-submit-button")
	private WebElement lnkUpdateFeesOkay;
	
	@FindBy(id = "user-detail_DESIGNER-edit-cancel-button")
	private WebElement lnkUpdateFeesClose;
	
	//SKU
	@FindBy(id = "register_name")
	private WebElement txtSkuRegisterName;
	
	@FindBy(id = "register_materialType")
	private WebElement txtSkuRegisterMaterialType;
	
	@FindBy(id = "register_finishOptions")
	private WebElement txtSkuRegisterFinishType;
	
	@FindBy(id = "register_country")
	private WebElement txtSkuRegisterCountry;
	
	@FindBy(id = "register_planPrice_pricePerAligner")
	private WebElement txtSkuRegister_planPrice_PricePerAligner;
	
	@FindBy(id = "register_planPrice_packagingFee")
	private WebElement txtSkuRegister_planPrice_PackagingFee;
	
	@FindBy(id = "register_planPrice_shippingFee")
	private WebElement txtSkuRegister_planPrice_ShippingFee;
	
	@FindBy(id = "register_planPrice_template")
	private WebElement txtSkuRegister_planPrice_Template;
	
	@FindBy(id = "register_planPrice_passiveAligner")
	private WebElement txtSkuRegister_planPrice_PassiveAligner;
	
	@FindBy(id = "register_planPrice_retainer")
	private WebElement txtSkuRegister_planPrice_Retainer;
	
	@FindBy(id = "register_planPrice_fee32Co")
	private WebElement txtSkuRegister_planPrice_fee32co;
	
	@FindBy(id = "register_planPrice_maxDeliveryDays")
	private WebElement txtSkuRegister_planPrice_MaxDeliveryDays;
	
	
	@FindBy(id = "register_refinementPrice_pricePerAligner")
	private WebElement txtSkuRegister_refinementPrice_PricePerAligner;
	
	@FindBy(id = "register_refinementPrice_packagingFee")
	private WebElement txtSkuRegister_refinementPrice_PackagingFee;
	
	@FindBy(id = "register_refinementPrice_shippingFee")
	private WebElement txtSkuRegister_refinementPrice_ShippingFee;
	
	@FindBy(id = "register_refinementPrice_template")
	private WebElement txtSkuRegister_refinementPrice_Template;
	
	@FindBy(id = "register_refinementPrice_passiveAligner")
	private WebElement txtSkuRegister_refinementPrice_PassiveAligner;
	
	@FindBy(id = "register_refinementPrice_retainer")
	private WebElement txtSkuRegister_refinementPrice_Retainer;
	
	@FindBy(id = "register_refinementPrice_fee32Co")
	private WebElement txtSkuRegister_refinementPrice_fee32co;
	
	@FindBy(id = "register_refinementPrice_maxDeliveryDays")
	private WebElement txtSkuRegister_refinementPrice_MaxDeliveryDays;
	
	@FindBy(xpath = "//*[@id='register_refinementsIncluded']/..")
	private WebElement txtSkuRegister_refinementIncluded;
	
	@FindBy(xpath = "//*[@id='register_default']/..")
	private WebElement txtSkuRegister_Default;

	@FindBy(id = "register_description")
	private WebElement txtSkuRegister_Description;
	
	@FindBy(xpath = "//label[text()='SKU Image']/../following-sibling::div//input")
	private WebElement inputSkuImage;
	
	@FindBy(xpath = "//label[text()='SKU Logo']/../following-sibling::div//input")
	private WebElement inputSkuLogo;
	
	@FindBy(id = "sku-details_submit-button")
	private WebElement btnCreateSKU;
	
	@FindBy(id = "sku-details_preview-button")
	private WebElement btnPreviewSKU;
	

	public AdminThirdPartiesPage(WebDriver browser, Data data) {
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

	public void validateSpecialistPageUI() {
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

	public void validateManufacturersPageUI() {
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
		String[] expHeaders = {"Name","Email","Location"};
		List<WebElement> weHeaders = browser.findElements(By.xpath("//table/thead//tr/th"));

		for(int i=1;i<weHeaders.size();i++) {
			String str = weHeaders.get(i-1).getText();
			if(expHeaders[i-1].equals(str)) {
				passed("Column Header is displayed as expected "+str);
			}else {
				fail("Expected column header is not displayed, Expected is "+expHeaders[i-1] +", but actual is "+str);
			}
		}
		takeScreenshot();
	}

	public void changeStatus(String status) {
		waitForTableToLoad(By.xpath("//table/tbody/tr[2]/td"));
		sleep(1000);
		List<WebElement> weRows = browser.findElements(By.xpath("//table/tbody/tr[2]/td"));
		if(weRows.size()>1) {
			String strName = weRows.get(1).getText();
			WebElement weActiveInactive = weRows.get(6).findElement(By.xpath("./button"));
			String checked = weActiveInactive.getAttribute("aria-checked");
			if(status.equalsIgnoreCase("active")) {
				sleep(5000);
				if(checked.contains("false")) {
					String expMessage = "Update user "+strName+" to ACTIVE successfully!";
					clickOn(weActiveInactive, "Toggle InActive button");
					waitForElement(weSuccessMessage);
					if(isElementPresent(weSuccessMessage)) {
						String succMsg = weSuccessMessage.getText();
						if(succMsg.contains(expMessage)) {
							passed("Successfully Activated the user "+weSuccessMessage);
							takeScreenshot();
						}else {
							takeScreenshot();
							fail("Success Message is not as expected, actual is "+succMsg + ", but expected is "+expMessage);
						}
					}else {
						takeScreenshot();
						fail("Unable to active the user "+strName);
					}
				}else {
					info(strName+" User is already active ");
				}

			}else if(status.equalsIgnoreCase("inactive")) {
				if(checked.contains("true")) {
					String expMessage = "Update user "+strName+" to INACTIVE successfully!";
					clickOn(weActiveInactive, "Toggle Active button");
					waitForElement(weSuccessMessage);
					if(isElementPresent(weSuccessMessage)) {
						String succMsg = weSuccessMessage.getText();
						if(succMsg.contains(expMessage)) {
							passed("Successfully Activated the user "+succMsg);
							takeScreenshot();
						}else {
							takeScreenshot();
							fail("Success Message is not as expected, actual is "+succMsg + ", but expected is "+expMessage);
						}
					}else {
						takeScreenshot();
						fail("Unable to inactive the user "+strName);
					}
				}else {
					info(strName+" User is already Inactive");
				}
			}else {
				fail("Status provided is not valid , valid options are active and inactive");
			}
		}else {
			takeScreenshot();
			fail("No of Rows present is zero, please add records and reexecute the test case");
		}
	}
	
	public void updateFees() {
		
		String fees = generateRandomString(3, "numeric");
		waitForTableToLoad(By.xpath("//table/tbody/tr[2]/td"));
		sleep(1000);
		List<WebElement> weRows = browser.findElements(By.xpath("//table/tbody/tr[2]/td"));
		if(weRows.size()>1) {
			String strName = weRows.get(1).getText();
			WebElement lnkName = weRows.get(1).findElement(By.xpath("./span/span/span"));
			clickOn(lnkName, "ThirdParties Name "+strName);
			String oldFees = getText(By.xpath("//form[@id='register']//span[text()='Fee per case']/following-sibling::span"));
			clickOn(lnkEditFees, "Edit Fees Link");
			waitForElement(txtFeePerCase);
			isElementDisplayed(lnkUpdateFeesClose, "Edit Fees Close Link");
			takeScreenshot();
			clearText(txtFeePerCase);
			enterText(txtFeePerCase, "Fees Text Field", fees);
			takeScreenshot();
			clickOn(lnkUpdateFeesOkay, "Okay Link");	
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				String succMsg = weSuccessMessage.getText();
				if(succMsg.contains(Constants.ADMIN_THIRDPARTIES_FEE_UPDATE_SUCCESS)) {
					passed("Successfully Updated the Fees");
					takeScreenshot();
				}else {
					takeScreenshot();
					fail("Success Message is not as expected after updating fees, actual is "+succMsg + ", but expected is "+Constants.ADMIN_THIRDPARTIES_FEE_UPDATE_SUCCESS);
				}
			}else {
				takeScreenshot();
				fail("Unable to Updated the Fees "+strName);
			}
			sleep(3000);
			info("-------Updating  the Fees back to old value----");
			clickOn(lnkEditFees, "Edit Fees Link");
			waitForElement(txtFeePerCase);
			isElementDisplayed(lnkUpdateFeesClose, "Edit Fees Close Link");
			takeScreenshot();
			clearText(txtFeePerCase);
			enterText(txtFeePerCase, "Fees Text Field", oldFees);
			takeScreenshot();
			clickOn(lnkUpdateFeesOkay, "Okay Link");	
			waitForElement(weSuccessMessage);
			if(isElementPresent(weSuccessMessage)) {
				String succMsg = weSuccessMessage.getText();
				if(succMsg.contains(Constants.ADMIN_THIRDPARTIES_FEE_UPDATE_SUCCESS)) {
					passed("Successfully Updated the Fees");
					takeScreenshot();
				}else {
					takeScreenshot();
					fail("Success Message is not as expected after updating fees, actual is "+succMsg + ", but expected is "+Constants.ADMIN_THIRDPARTIES_FEE_UPDATE_SUCCESS);
				}
			}else {
				takeScreenshot();
				fail("Unable to Updated the Fees "+strName);
			}
		}
		
	}


}

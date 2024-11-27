package pages.manufacturer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Values;

public class ManufacturerDashboardPage extends Page {

	@FindBy(xpath = "//a/button[text()='Host a meeting']")
	private WebElement lnkHostMeeting;
	
	@FindBy(xpath = "//div/*[@alt='logoutUser']")
	private WebElement btnLogout;
	
	@FindBy(id = "basic_email")
	private WebElement txtEmail;
	
	@FindBy(id = "dashboard-menu")
	private WebElement lnkDashboard;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	private WebElement weSuccessMessage;


	public ManufacturerDashboardPage(WebDriver browser, Data data) {
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
	

	public void selectRecordFromSpecifiedSection(String dataNodeKey) {
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		if(isElementPresent(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"))) {
			clickOn(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"), dataNodeKey + " Section");
			waitForElement(By.xpath("//span[text()='"+patientName+"']"));
			waitForElement(By.xpath("//span[text()='"+patientName+"']"));
			sleep(2000);
			clickOn(By.xpath("//span[text()='"+patientName+"']"), patientName+" View Button");
			waitForElement(By.xpath("//*[text()='Patient Info']"));
			if(isElementPresent(By.xpath("//*[text()='Patient Info']"))) {
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
	
	public void AddOrEditShippingLink() {
		if(isElementPresent(By.xpath("//*[text()='Patient Info']"))) {
			passed("Patient details is displayed as expected");
			WebElement we = browser.findElement(By.xpath("//input"));
			enterText(we, "Shipping Link url text box", "http://www.google.com");
			clickOn(By.id("add-shipping-link"), "Add Shipping Link");
			waitForElement(weSuccessMessage);
			takeScreenshot();
			if(isElementPresent(weSuccessMessage)) {
				passed("Success message is displayed as expected");
				String strSuccMsg = weSuccessMessage.getText();
				String expMessage = Constants.MANUFACTURER_SUCC_SHIPPING_LINK;
				if(strSuccMsg.contains(expMessage)) {
					passed("Success message is as expected "+expMessage);
					takeScreenshot();
				}else {
					fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
				}
			}else {
				fail("Success message is not displayed ");
			}
		}else {
			fail("Patient details is not displayed ");
		}
	}
	
	public void validatePatientInSpecificStatus(String dataNodeKey) {
		clickOn(lnkDashboard, "Dashboard Link");
		String patientName = Values.patientFirstName + " " + Values.patientLastName;
		if(isElementPresent(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"))) {
			clickOn(By.xpath("//*[@data-node-key='"+dataNodeKey+"']"), dataNodeKey + " Section");
			waitForElement(By.xpath("//span[text()='"+patientName+"']"));
			if(isElementPresent((By.xpath("//span[text()='"+patientName+"']")))) {
				passed("Patient  is displayed  in section "+dataNodeKey);
			}else {
				fail("Patient is not displayed in the section "+dataNodeKey);
			}
			
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Required section is not displayed, data-node-key passed is not valid "+dataNodeKey);
		}
	}

}

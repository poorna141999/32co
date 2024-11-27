package pages.dentist;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Values;

public class DentistLoginPage extends Page {

	@FindBy(id = "email")
	private WebElement txtEmail;

	@FindBy(id = "password")
	private WebElement txtPassword;
	
	@FindBy(id = "add-bookmarks-yes")
	private WebElement btnBookmarksYes;
	
	@FindBy(id = "add-bookmarks-no")
	private WebElement btnBookmarksNo;

	@FindBy(xpath = "//button/span[text()='Login']")
	private WebElement btnLogin;

	@FindBy(linkText = "Not Registered?")
	private WebElement linkNotRegisteredJoinus;

	@FindBy(linkText = "Forgot Password?")
	private WebElement linkForgotPassword;

	@FindBy(xpath = "//div//span[text()='Yes']")
	private WebElement btnYes;

	@FindBy(xpath = "//div//span[text()='No']")
	private WebElement btnNo;

	@FindBy(xpath = "//div//span[text()='Next']")
	private WebElement btnNext;

	@FindBy(xpath = "//div//span[text()='Get Going']")
	private WebElement btnGetGoing;

	@FindBy(xpath = "//button//span[text()='Submit']")
	private WebElement btnSubmit;

	@FindBy(xpath = "//div[@class='ant-notification-notice-description']")
	private WebElement weToasterMessageDesc;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weToasterMessageHeader;
	
	@FindBy(xpath = "//a[@id='dashboard-menu']")
	private WebElement menuDashboard;

	@FindBy(xpath = "//div[@role='dialog']//div[@class='ant-alert-message']//button")
	private WebElement lnkViewReleaseNote;

	@FindBy(xpath = "//div[@role='dialog']//button[@class='ant-modal-close']")
	private WebElement btnCloseReleaseNoteDialog;
	
    

	public DentistLoginPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public DentistDashboardPage loginToApplication() {
		info("************************************ Launched Dentist Application ************************************");
		waitForElement(txtEmail);
		enterText(txtEmail, "Username Textbox",Values.frameworkProperty.getProperty("dentist.username"));
		enterText(txtPassword, "Password Textbox",Values.frameworkProperty.getProperty("dentist.password"));
		clickOn(btnLogin, "Login Button");
		waitForElement(menuDashboard);
		if(isElementPresent(menuDashboard)) {
			waitForElement(btnBookmarksNo);
			if(isElementPresent(btnBookmarksNo)) {
				clickOn(btnBookmarksNo, "BookMarks No Thank you button");
			}
			waitForElement(lnkViewReleaseNote,10);
			if(isElementPresent(lnkViewReleaseNote)) {
				clickOn(lnkViewReleaseNote, "Click here to see What's New in the 32Co Platform Link");
				sleep(2000);
				waitForElement(btnCloseReleaseNoteDialog);
				if(isElementPresent(btnCloseReleaseNoteDialog)) {
					clickOn(btnCloseReleaseNoteDialog, "Close Button of Dialog");
				}
			}
			passed("Logged into the application successfully");
			waitForElement(By.xpath("//div[@class='add-patients-card']"));
			if(isElementPresent(By.xpath("//div[@class='add-patients-card']"))) {
				passed("Dentist Dashboard page is displayed successfully");
			}else {
				fail("Dentist Dashboard page is not opened");
			}
			takeScreenshot();
		}else {
			failAssert("Unable to login to application");
		}
		return new DentistDashboardPage(browser, data);
	}

	public DentistDashboardPage loginToApplicationnewUser() {
		waitForElement(txtEmail);
		enterText(txtEmail, "Username Textbox",Values.dentistFirstDetails.get("Email Address"));
		enterText(txtPassword, "Password Textbox",Values.dentistFirstDetails.get("Password"));
		clickOn(btnLogin, "Login Button");
		waitForElement(By.id("dashboard-menu"));
		if(isElementPresent(By.id("dashboard-menu"))) {			
			passed("Logged into the application successfully");
			waitForElement(By.xpath("//p[contains(text(),'Start New Case')]"));
			if(isElementPresent(By.xpath("//p[contains(text(),'Start New Case')]"))) {
				passed("Dentist Dashboard page is displayed successfully");
				//selectInitialDetails();
			}else {
				fail("Dentist Dashboard page is not opened");
			}
			takeScreenshot();
		}else {
			failAssert("Unable to login to application");
		}
		return new DentistDashboardPage(browser, data);
	}

	public void selectInitialDetailsYesFlow() {		
		waitForElement(By.xpath("//div[@role='dialog']"));
		sleep(1000);
		if(isElementPresent(By.xpath("//div[@role='dialog']"))) {
			passed("Initial Dialog is displayed for the new user");
			takeScreenshot();
			sleep(1000);
			clickOn(btnYes, "Yes Button");
			clickOn(btnNext, "Next Button");
			sleep(1000);
			clickOn(By.xpath("//div[@class='ant-row']//div[text()='Tackle more complex cases']"), "First Question");
			clickOn(btnNext, "Next Button");
			sleep(1000);
			clickOn(By.xpath("//div[@class='ant-row']//div[text()='Complex cases']"), "Second Question");
			clickOn(btnNext, "Next Button");
			sleep(1000);
			waitForElement(btnGetGoing);
			clickOn(btnGetGoing, "Get Going Button");
			waitForElement(weToasterMessageDesc);
			String expSuccMsg = Constants.DENTIST_REGISTER_EDUCATION_SUCCESS_MSG;
			if(isElementPresent(weToasterMessageDesc)) {
				String actSuccMessage = weToasterMessageDesc.getText();
				passed("Success message is displayed after successfully answered question in dialog");
				takeScreenshot();
				if(actSuccMessage.contains(expSuccMsg)) {
					passed("Success message is displayed as expected "+expSuccMsg);
				}else {
					fail("Success message is not displayed as expected, Expected is "+expSuccMsg + ", But Actual is "+ actSuccMessage);
				}
			}else {
				fail("Success message is not displayed after successfully answered question in dialog");
				takeScreenshot();
			}			
		}else {
			fail("Initial Dialog not displayed for the new user");
		}
	}

	public void selectInitialDetailsNoFlow() {		
		waitForElement(By.xpath("//div[@role='dialog']"));
		sleep(1000);
		if(isElementPresent(By.xpath("//div[@role='dialog']"))) {
			passed("Initial Dialog is displayed for the new user");
			takeScreenshot();
			sleep(1000);
			clickOn(btnNo, "No Button");
			clickOn(btnNext, "Next Button");
			sleep(1000);
			clickOn(By.xpath("//div[@class='ant-row']//div[text()='Improve my orthodontic knowledge']"), "First Question");
			clickOn(btnNext, "Next Button");
			sleep(1000);
			clickOn(By.xpath("//div[@class='ant-row']//div[text()='Treatment Planning']"), "Second Question");
			clickOn(btnNext, "Next Button");
			sleep(1000);
			waitForElement(btnGetGoing);
			clickOn(btnGetGoing, "Get Going Button");
			waitForElement(weToasterMessageDesc);
			String expSuccMsg = Constants.DENTIST_REGISTER_EDUCATION_SUCCESS_MSG;
			if(isElementPresent(weToasterMessageDesc)) {
				String actSuccMessage = weToasterMessageDesc.getText();
				passed("Success message is displayed after successfully answered question in dialog");
				takeScreenshot();
				if(actSuccMessage.contains(expSuccMsg)) {
					passed("Success message is displayed as expected "+expSuccMsg);
				}else {
					fail("Success message is not displayed as expected, Expected is "+expSuccMsg + ", But Actual is "+ actSuccMessage);
				}
			}else {
				fail("Success message is not displayed after successfully answered question in dialog");
				takeScreenshot();
			}			
		}else {
			fail("Initial Dialog not displayed for the new user");
		}		
	}


	public DentistRegisterPage navigateToRegisterPage() {
		clickOn(linkNotRegisteredJoinus, "Not Registered Link");
		return new DentistRegisterPage(browser, data);
	}

	public void navigateToForgotPasswordPage() {
		clickOn(linkForgotPassword, "Forgot Password Link");
		waitForElement(By.xpath("//p[text()='Forgot Password']"));
		if(isElementPresent(By.xpath("//p[text()='Forgot Password']"))) {
			passed("Forgot Password page is displayed successfully");
		}else {
			fail("Unable to navigate to forgot password page");
		}
		takeScreenshot();
	}

	public void validateForgotPassword(Data data) {
		Values.currentDateTime = new Date();
		enterText(txtEmail, "Email Address", data.get("Email"));
		clickOn(btnSubmit, "Submit Button");
		waitForElement(weToasterMessageDesc);
		validateToasterMessage(Constants.FORGOT_PASSWORD_SUCC_HEADER, Constants.FORGOT_PASSWORD_SUCC_DESC);
		takeScreenshot();
	}

	public void validateForgotPasswordInvalidEmail(Data data) {
		String email = data.get("Email");
		if(validateEmail(email)) {
			enterText(txtEmail, "Email Address", data.get("Email"));
			clickOn(btnSubmit, "Submit Button");
			waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
			sleep(1000);
			validateToasterMessage(Constants.FORGOT_PASSWORD_ERROR_HEADER, Constants.FORGOT_PASSWORD_ERROR_DESC);
		}else {
			enterText(txtEmail, "Email Address", data.get("Email"));
			clickOn(btnSubmit, "Submit Button");
			sleep(1000);
			validateErrorMessageOfField(txtEmail, "Email Address", "Please enter your full email address");
			takeScreenshot();
		}
	}

	public void loginNegativeValidation() {
		String username = data.get("Username");
		String password = data.get("Password");
		String type = data.get("Type");
		waitForElement(txtEmail);
		sleep(1000);
		if(!username.trim().equals("")) {
			enterText(txtEmail, "Username Textbox",username);
		}
		if(!password.trim().equals("")) {
			enterText(txtPassword, "Password Textbox",password);
		}
		clickOn(btnLogin, "Login Button");
		sleep(2000);
		if(username.trim().equals("")) {
			validateErrorMessageOfField(txtEmail, "Email", "Please enter your email address");
		}
		if(password.trim().equals("")) {
			validateErrorMessageOfField(txtPassword, "Password", "You'll need to enter your password");
		}
		if(!username.trim().equals("")) {
			if(!validateEmail(username)) {
				validateErrorMessageOfField(txtEmail, "Email", "Please enter your full email address");
			}
		}
		if(password.length()>0 && password.length()<8) {
			validateErrorMessageOfField(txtPassword, "Password", "Please enter a password with at least 8 characters");
		}
		if(type.trim().equalsIgnoreCase("WrongUsername")) {
			waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
			sleep(2000);
			validateToasterMessage("Not Found", "User not found");
		}
		if(type.trim().equalsIgnoreCase("WrongPassword")) {
			waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
			sleep(2000);
			validateToasterMessage("Unauthorized", "Password is not correct");
		}

		takeScreenshot();
	}

}

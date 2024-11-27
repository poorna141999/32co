package pages.specialist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class SpecialistLoginPage extends Page {

	@FindBy(id = "basic_email")
	private WebElement txtEmail;

	@FindBy(id = "basic_password")
	private WebElement txtPassword;

	@FindBy(xpath = "//button/span[text()='Login']")
	private WebElement btnLogin;

	@FindBy(linkText = "Not Registered?")
	private WebElement linkNotRegisteredJoinus;

	@FindBy(id = "add-bookmarks-yes")
	private WebElement btnBookmarksYes;
	
	@FindBy(id = "add-bookmarks-no")
	private WebElement btnBookmarksNo;

	@FindBy(xpath = "//div[@role='dialog']//div[@class='ant-alert-message']//button")
	private WebElement lnkViewReleaseNote;

	@FindBy(xpath = "//div[@role='dialog']//button[@class='ant-modal-close']")
	private WebElement btnCloseReleaseNoteDialog;
	

	public SpecialistLoginPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public SpecialistDashboardPage loginToApplication() {
		info("************************************ Launched Specialist Application ************************************");
		waitForElement(txtEmail);
		enterText(txtEmail, "Username Textbox",Values.frameworkProperty.getProperty("specialist.username"));
		enterText(txtPassword, "Password Textbox",Values.frameworkProperty.getProperty("specialist.password"));
		clickOn(btnLogin, "Login Button");
		waitForElement(By.xpath("//span[text()='Dashboard']"));
		if(isElementPresent(By.xpath("//span[text()='Dashboard']"))) {		
			waitForElement(btnBookmarksNo);
			if(isElementPresent(btnBookmarksNo)) {
				clickOn(btnBookmarksNo, "BookMarks No Thank you button");
			}
			waitForElement(lnkViewReleaseNote,5);
			if(isElementPresent(lnkViewReleaseNote)) {
				clickOn(lnkViewReleaseNote, "Click here to see What's New in the 32Co Platform Link");
				waitForElement(btnCloseReleaseNoteDialog);
				if(isElementPresent(btnCloseReleaseNoteDialog)) {
					clickOn(btnCloseReleaseNoteDialog, "Close Button of Dialog");
				}
			}
			passed("Logged into the application successfully");
			if(isElementPresent(By.xpath("//div/span[text()=\"My To-do's For Today\"]"))) {
				passed("Specialist Dashboard page is displayed successfully");
			}else {
				fail("Specialist Dashboard page is not opened");
			}
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Unable to login to application");
		}
		return new SpecialistDashboardPage(browser, data);
	}

	public void loginNegativeValidation() {
		String username = data.get("Username");
		String password = data.get("Password");
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
		takeScreenshot();
	}

}

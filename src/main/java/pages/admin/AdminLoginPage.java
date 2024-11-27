package pages.admin;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class AdminLoginPage extends Page {

	private static Data data;

	@FindBy(id = "basic_email")
	private WebElement txtEmail;

	@FindBy(id = "basic_password")
	private WebElement txtPassword;

	@FindBy(xpath = "//button/span[text()='LOGIN']")
	private WebElement btnLogin;

	@FindBy(xpath = "//div[@class='ant-notification-notice-description']")
	private WebElement weErrorDescription;

	@FindBy(xpath = "(//input[@type='tel'])[1]")
	private WebElement OTP1;

	@FindBy(xpath = "(//input[@type='tel'])[2]")
	private WebElement OTP2;

	@FindBy(xpath = "(//input[@type='tel'])[3]")
	private WebElement OTP3;

	@FindBy(xpath = "(//input[@type='tel'])[4]")
	private WebElement OTP4;

	@FindBy(xpath = "(//input[@type='tel'])[5]")
	private WebElement OTP5;

	@FindBy(xpath = "(//input[@type='tel'])[6]")
	private WebElement OTP6;

	@FindBy(xpath = "//span[contains(text(),'Verify OTP')]")
	private WebElement verifyOTP;

	public AdminLoginPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public AdminDashboardPage loginToApplication() {
		info("************************************ Launched Admin Application ************************************");
		waitForElement(txtEmail);
		enterText(txtEmail, "Username Textbox", Values.frameworkProperty.getProperty("admin.username"));
		enterText(txtPassword, "Password Textbox", Values.frameworkProperty.getProperty("admin.password"));
		clickOn(btnLogin, "Login Button");
		waitForElement(OTP1);
		enterText(OTP1, "OTP First Digit", "1");
		enterText(OTP2, "OTP Second Digit", "2");
		enterText(OTP3, "OTP Third Digit", "3");
		enterText(OTP4, "OTP Fourth Digit", "4");
		enterText(OTP5, "OTP Fifth Digit", "5");
		enterText(OTP6, "OTP sixth Digit", "6");
		clickOn(verifyOTP, "Verify OTP");
		waitForElement(By.xpath("(//a[text()='Dashboard'])[2]"));
		if (isElementPresent(By.xpath("(//a[text()='Dashboard'])[2]"))) {
			passed("Logged into the application successfully");
			takeScreenshot();
		} else {
			failAssert("Unable to login to application");
		}
		return new AdminDashboardPage(browser, data);
	}

	public void loginNegativeValidation() {
		waitForElement(txtEmail);
		String username = data.get("Username");
		String password = data.get("Password");
		String type = data.get("Type");
		if (!username.trim().equals("")) {
			enterText(txtEmail, "Username Textbox", username);
		}
		if (!password.trim().equals("")) {
			enterText(txtPassword, "Password Textbox", password);
		}
		clickOn(btnLogin, "Login Button");
		sleep(2000);
		if (username.trim().equals("")) {
			validateErrorMessageOfField(txtEmail, "Email", "Please input your email!");
		}
		if (password.trim().equals("")) {
			validateErrorMessageOfField(txtPassword, "Password", "Please input your password!");
		}
		if (type.trim().equalsIgnoreCase("WrongUsername")) {
			waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
			sleep(2000);
			validateToasterMessage("Error", "Please check username or password");
		}
		if (type.trim().equalsIgnoreCase("WrongPassword")) {
			waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
			sleep(2000);
			validateToasterMessage("Error", "Please check your password");
		}
		takeScreenshot();
	}

}

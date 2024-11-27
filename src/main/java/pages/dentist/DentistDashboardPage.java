package pages.dentist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class DentistDashboardPage extends Page {

	@FindBy(xpath = "//button/span[text()='Start Case']/..")
	private WebElement btnStartCase;

	@FindBy(xpath = "//button/span[text()='Start SOLO Case']")
	private WebElement btnStartSoloCase;

	@FindBy(xpath = "//button/span[text()='Start DUO Case']")
	private WebElement btnStartDuoCase;

	@FindBy(xpath = "//div/*[@alt='userLogout']")
	private WebElement btnLogout;

	@FindBy(id = "email")
	private WebElement txtEmail;

	@FindBy(xpath ="(//a[text()='Complete your profile'])[1]")
	private WebElement lnkCompleteProfile;

	@FindBy(xpath = "//span[contains(text(),'Screen name')]")
	private WebElement weScreenName;

	@FindBy(xpath = "//div[span[text()='Name']]/following-sibling::*[1]")
	private WebElement weName;

	@FindBy(xpath = "//div[span[text()='General Dental Council Number']]/following-sibling::*[1]")
	private WebElement weDentalLicenseNumber;

	@FindBy(xpath ="//div[span[text()='Email']]/following-sibling::*[1]")
	private WebElement weEmail;

	@FindBy(xpath ="//div[span[text()='Phone number']]/following-sibling::*[1]")
	private WebElement wePhonenumber;

	@FindBy(xpath = "//span[text()='A bit about me']/following-sibling::span")
	private WebElement weAbitaboutme;

	@FindBy(id = "dashboard-menu")
	private WebElement lnkDashboard;
	
	@FindBy(id = "patients-menu")
	private WebElement lnkPatients;

	@FindBy(id = "education-menu")
	private WebElement lnkEducation;
	
	@FindBy(id = "guides-menu")
	private WebElement lnkGuides;
	
	@FindBy(id = "pricing-menu")
	private WebElement lnkPricing;
	
	
	
	
	
	
	public DentistDashboardPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}


	public DentistPatientsPage startSoloCase() {
		waitForElement(btnStartCase);
		if(isElementPresent(btnStartCase)) {
			jsClick(btnStartCase, "Start Case Button");
			clickOn(btnStartSoloCase, "Start Solo Case Button");
			waitForElement(By.id("patientDetail_firstName"));
			if(isElementPresent(By.id("patientDetail_firstName"))) {
				passed("Solo case details page is displayed as expected");
			}else {
				failAssert("Solo Case details page is not displayed");
			}
		}else {
			failAssert("Start Case button is not displayed");
		}
		return new DentistPatientsPage(browser, data);
	}

	public DentistPatientsPage startDuoCase() {
		waitForElement(btnStartCase);
		if(isElementPresent(btnStartCase)) {
			jsClick(btnStartCase, "Start Case Button");
			clickOn(btnStartDuoCase, "Start Duo Case Button");
			waitForElement(By.id("patientDetail_firstName"));
			if(isElementPresent(By.id("patientDetail_firstName"))) {
				passed("Duo case details page is displayed as expected");
			}else {
				failAssert("Duo Case details page is not displayed");
			}
		}else {
			failAssert("Start Case button is not displayed");
		}
		return new DentistPatientsPage(browser, data);
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

	public void clickOnCompleteYourProfile() {
		clickOn(lnkCompleteProfile, "Complete your profile");
		waitForElement(weScreenName);
		if(isElementPresent(weScreenName)) {
			passed("Navigated to Complete your profile page successfully");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Complete your profile page is not displayed");
		}
	}

	public void validateCompleteProfilePage() {
		try {
			String screenName = weScreenName.getText();
			String name = weName.getText();
			String licenseNumber = weDentalLicenseNumber.getText();
			String email = weEmail.getText();
			String phoneNumber = wePhonenumber.getText();
			//String bitAboutMe = weAbitaboutme.getText();
			//validateActualEqualsExpected(screenName, Values.dentistFirstDetails.get(""), name);
			validateActualEqualsExpected(name, Values.dentistFirstDetails.get("First Name") + " "+Values.dentistFirstDetails.get("Last Name"), "Name");
			validateActualEqualsExpected(licenseNumber, Values.dentistFinalDetails.get("License Number"), "License Number");
			validateActualEqualsExpected(email, Values.dentistFirstDetails.get("Email Address"), "Email Address");
			validateActualEqualsExpected(phoneNumber, Values.dentistFirstDetails.get("Mobile Number"), "Mobile Number");
			//validateActualEqualsExpected(bitAboutMe, "-", "A bit about Me");
		}catch(Exception e) {
			fail("Exception caught "+e.getMessage());
		}
		takeScreenshot();
	}
	
	public DentistPatientsPage NavigateToPatientsPage() {
		waitForElement(lnkPatients);
		clickOn(lnkPatients, "Patients Menu");
		waitForElement(By.xpath("//*[@data-node-key='ALL_CASES_STATUS']"));
		if(isElementPresent(By.xpath("//*[@data-node-key='ALL_CASES_STATUS']"))) {
			passed("Successfully Navigated to Patients page");
		}else {
			takeScreenshot();
			failAssert("Patients page is not displayed");
		}
		takeScreenshot();
		return new DentistPatientsPage(browser, data);
	}
	
	public DentistEducationPage NavigateToEducationPage() {
		waitForElement(lnkEducation);
		clickOn(lnkEducation, "Education Menu");
		waitForElement(By.xpath("//*[@data-node-key='All']"));
		if(isElementPresent(By.xpath("//*[@data-node-key='All']"))) {
			passed("Successfully Navigated to Educations page");
		}else {
			takeScreenshot();
			failAssert("Educations page is not displayed");
		}
		takeScreenshot();
		return new DentistEducationPage(browser, data);
	}


}

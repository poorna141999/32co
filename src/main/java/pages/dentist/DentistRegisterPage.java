package pages.dentist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class DentistRegisterPage extends Page {

	HashMap<String, String> firstDetails = new HashMap<String, String>();

	@FindBy(xpath = "//span[text()='Get Started']")
	private WebElement btnGetStartedJoinus;

	@FindBy(id = "firstName")
	private WebElement txtFirstNameJoinus;

	@FindBy(xpath = "//button/span[text()='Exit']")
	private WebElement btnWaitExit;

	@FindBy(id = "lastName")
	private WebElement txtLastNameJoinus;

	@FindBy(id = "country")
	private WebElement txtCountryJoinus;

	@FindBy(xpath = "//div[text()='India']")
	private WebElement txtIndiaJoinus;

	@FindBy(xpath = "//div[@class='flag 0']")
	private WebElement telArrowJoinus;

	@FindBy(xpath = "//span[text()='+91']")
	private WebElement teldialCodeJoinus;

	@FindBy(xpath = "//input[@placeholder='Enter your phone number']")
	private WebElement txtMobileNumberJoinus;

	@FindBy(id = "email")
	private WebElement txtEmailJoinus;

	@FindBy(id = "password")
	private WebElement txtPasswordJoinus;

	@FindBy(id = "confirmPassword")
	private WebElement txtConfirmPasswordJoinus;

	@FindBy(id = "referredCode")
	private WebElement txtReferalCodeJoinus;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement btnNextAndSubmitJoinus;

	@FindBy(id = "licenseNumber")
	private WebElement txtDentistLicenseJoinus;

	@FindBy(xpath = "//input[@placeholder='Name of primary practice']")
	private WebElement txtDentistPrimaryPracticeName;

	@FindBy(xpath = "//input[@placeholder='Postcode of primary practice']")
	private WebElement txtDentistPincode;

	@FindBy(xpath = "(//div[@class='ant-select-selector'])[1]")
	private WebElement roleinPracticeDropdown;

	@FindBy(xpath = "(//div[@class='ant-select-item ant-select-item-option'])[1]")
	private WebElement roleinPracticeDropdownOption;

	@FindBy(xpath = "(//div[@class='ant-select-selector'])[2]")
	private WebElement alignerCasesDropdown;

	@FindBy(id = "67076027869d27cb573a7d24")
	private WebElement alignerCasesDropdownOption;

	@FindBy(xpath = "(//div[@class='ant-select-selector'])[3]")
	private WebElement hereAboutUsDropdown;

	@FindBy(id = "66fe08dda78e68c9959e7cb9")
	private WebElement hereAboutUsDropdownOption;

	@FindBy(id = "get-started-dentist-signup")
	private WebElement dentistGetStartrd;

	@FindBy(id = "practicesYear")
	private WebElement txtPracticeYearJoinus;

	// @FindBy(xpath = "//div[@aria-describedby='totalCasesExp_help']")

	// @FindBy(id = "totalCasesExp")
	@FindBy(id = "totalSubmissionsExp")
	private WebElement SearchTotalCasesExpJoinus;

	@FindBy(xpath = "(//div[text()='More Than 10'])[2]")
	private WebElement SearchTotalCaseOptionJoinus;

	@FindBy(id = "clinics_0_name")
	private WebElement txtClinicNameJoinus;

	@FindBy(id = "clinics_0_address_address1")
	private WebElement txtClinicAddressJoinus;

	@FindBy(xpath = "(//div[text()='India Gate Kartavya Path, India Gate, New Delhi, Delhi, India'])[2]")
	private WebElement txtClinicAddressOptionJoinus;

	@FindBy(id = "clinics_0_email")
	private WebElement txtClinicEmailJoinus;

	@FindBy(id = "clinics_0_address_postCode")
	private WebElement txtClinicPostCodeJoinus;

	@FindBy(id = "clinics_0_isScannerAvailable")
	private WebElement SearchScannerJoinus;

	@FindBy(xpath = "//div[text()='Yes']")
	private WebElement SearchScannerOptionJoinus;

	@FindBy(className = "ant-select-selection-overflow")
	private WebElement SearchScannerTypeJoinus;

	@FindBy(xpath = "(//div[text()='iTero'])[2]")
	private WebElement SearchScannerTypeOptionJoinus;

	@FindBy(xpath = "//span[text()='+ Add another clinic']")
	private WebElement btnAddAnotherClinicTypeOptionJoinus;

	// clinics_1_name

	@FindBy(id = "clinics_1_name")
	private WebElement txtClinicName1Joinus;

	@FindBy(id = "clinics_1_address_address1")
	private WebElement txtClinicAddress1Joinus;

	@FindBy(id = "clinics_1_email")
	private WebElement txtClinicEmail1Joinus;

	@FindBy(id = "clinics_1_address_postCode")
	private WebElement txtClinicPostCode1Joinus;

	@FindBy(id = "clinics_1_isScannerAvailable")
	private WebElement SearchScanner1Joinus;

	@FindBy(xpath = "(//div[text()='No'])[2]")
	private WebElement SearchScannerOption1Joinus;

	@FindBy(xpath = " //div[@class='text-[32px] font-semibold leading-10 text-[#141311]']")
	private WebElement weDialog;

	@FindBy(xpath = "//button/span[text()='Sign in']")
	private WebElement btnSignIn;

	@FindBy(xpath = "//div[@class='ant-notification-notice-content']")
	private WebElement weSuccErrorPopUp;

	@FindBy(xpath = "//p[contains(text(),'Welcome to 32Co')]")
	private WebElement welcomeMessage;

	@FindBy(xpath = "//div[@class='ant-select-selector']")
	private WebElement bestDescribeDrop;

	@FindBy(xpath = "(//div[contains(@class, 'ant-select-item-option-content')])[1]")
	private WebElement bestDescribeDropoption;
	
	@FindBy(xpath = "//div[contains(text(),'I want to treat a broader range of cases')]")
	private WebElement pleaseLetusKnowOption;
	
	@FindBy(xpath = "(//button[@type='button'])[7]")
	private WebElement notSureYet;
	
	@FindBy(xpath ="(//button[@type='button'])[10]")
	private WebElement nextBtn;
	
	@FindBy(xpath ="(//button[@type='button'])[11]")
	private WebElement canWeHelpnextBtn;
	
	 
	

	public DentistRegisterPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void clickOnGetStarted() {
		clickOn(btnGetStartedJoinus, "Get Started Button");
	}

	public void validateWaitDialog() {
		clickOn(txtFirstNameJoinus, "FirstName textbox");
		clickOn(txtLastNameJoinus, "FirstName textbox");
		sleep(8000);
		waitForElement(btnWaitExit);
		if (isElementPresent(btnWaitExit)) {
			clickOn(btnWaitExit, "Exit Button");
		}
	}

	public void enterFirstDetailsForRegister(HashMap<String, String> details) {
		if (isElementPresent(txtFirstNameJoinus)) {
			enterText(txtFirstNameJoinus, "First Name", details.get("First Name"));
			enterText(txtLastNameJoinus, "Last Name", details.get("Last Name"));
			enterText(txtEmailJoinus, "Email Address", details.get("Email Address"));
			enterText(txtMobileNumberJoinus, "Mobile Number", details.get("Mobile Number"));
			// enterTextAndSelectOption(txtCountryJoinus, "Country",
			// details.get("Country"));
			enterText(txtPasswordJoinus, "Password", details.get("Password"));
			// enterText(txtConfirmPasswordJoinus, "Confirm Password", details.get("Confirm
			// Password"));
			// enterText(txtReferalCodeJoinus, "Referal Code", details.get("Referal Code"));
			waitForElement(bestDescribeDrop);
			clickOn(bestDescribeDrop, "bestDescribeDropdown");
			waitForElement(bestDescribeDrop);
			clickOn(bestDescribeDropoption, "bestDescribeDropoption");
		} else {
			failAssert("Enter Details for Register First Page is not displayed");
		}
		takeScreenshot();

	}

	public void enterFinalDetailsForRegister(HashMap<String, String> details) {
		if (isElementPresent(txtDentistLicenseJoinus)) {
			sleep(2000);
			enterText(txtDentistPrimaryPracticeName, "name of primary practice",
					details.get("Name of primary practice"));
			enterText(txtDentistPincode, "pincode", details.get("Pincode of primary practice"));
			clickOn(roleinPracticeDropdown, "roleinPracticeDropdown");
			clickOn(roleinPracticeDropdownOption, "roleinPracticeDropdownOption");
			clickOn(alignerCasesDropdown, "alignerCasesDropdown");
			sleep(2000);
			clickOn(alignerCasesDropdownOption, "alignerCasesDropdownOption");
			enterText(txtDentistLicenseJoinus, "License Number", details.get("License Number"));
			clickOn(hereAboutUsDropdown, "hereAboutUsDropdown");
			clickOn(hereAboutUsDropdownOption, "hereAboutUsDropdownOption");
			clickOn(dentistGetStartrd, "dentistGetStartrd");
			/*
			 * //enterText(txtPracticeYearJoinus, "Practice Year",
			 * details.get("Practice Year"));
			 * selectOptionDropDown(SearchTotalCasesExpJoinus, "Total Case Experience",
			 * details.get("Total Case Experience")); enterText(txtClinicNameJoinus,
			 * "Clinic Name", details.get("Clinic Name")); enterText(txtClinicAddressJoinus,
			 * "Clinic Address", details.get("Clinic Address"));
			 * enterText(txtClinicEmailJoinus, "Clinic Email", details.get("Clinic Email"));
			 * enterText(txtClinicPostCodeJoinus, "Clinic PostCode",
			 * details.get("Clinic PostCode")); selectOptionDropDown(SearchScannerJoinus,
			 * "Scanner Available", details.get("Scanner Available")); if
			 * (details.get("Clinic Name1") != null) { if
			 * (!details.get("Clinic Name1").equals("")) {
			 * clickOn(btnAddAnotherClinicTypeOptionJoinus, "Add Another Clinic Button");
			 * enterText(txtClinicName1Joinus, "Clinic Name1", details.get("Clinic Name1"));
			 * enterText(txtClinicAddress1Joinus, "Clinic Address1",
			 * details.get("Clinic Address1")); enterText(txtClinicEmail1Joinus,
			 * "Clinic Email1", details.get("Clinic Email1"));
			 * enterText(txtClinicPostCode1Joinus, "Clinic PostCode1",
			 * details.get("Clinic PostCode1")); selectOptionDropDown(SearchScanner1Joinus,
			 * "Scanner Available1", details.get("Scanner Available1")); } }
			 */
		} else {
			failAssert("Enter Details for Register First Page is not displayed");
		}
		takeScreenshot();
	}

	public void registerNewUser(Data data) {
		if (data.get("First Name").trim().equals("")) {
			firstDetails.put("First Name", "Auto_" + generateRandomString(5, "alpha"));
		} else {
			firstDetails.put("First Name", data.get("First Name"));
		}
		if (data.get("Last Name").trim().equals("")) {
			firstDetails.put("Last Name", generateRandomString(8, "alpha"));
		} else {
			firstDetails.put("Last Name", data.get("Last Name"));
		}

		firstDetails.put("Country", data.get("Country"));
		firstDetails.put("Mobile Number", data.get("Mobile Number"));
 		if (data.get("Email Address").trim().equals("")) {
			firstDetails.put("Email Address", generateRandomString(8, "alpha") + "@gmail.com");
		} else if (validateEmail(data.get("Email Address"))) {
			Values.dentistEmail = generateRandomEmail(data.get("Email Address"), "dentist");
			firstDetails.put("Email Address", Values.dentistEmail);
		} else {
			firstDetails.put("Email Address", data.get("Email Address"));
		}
		firstDetails.put("Password", data.get("Password"));
		firstDetails.put("Confirm Password", data.get("Confirm Password"));
		firstDetails.put("Referal Code", data.get("Referal Code"));
		Values.dentistFirstDetails = firstDetails;
		enterFirstDetailsForRegister(firstDetails);
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		HashMap<String, String> finalDetails = new HashMap<String, String>();
		finalDetails.put("Name of primary practice", "Auto_" + generateRandomString(5, "alpha"));
		finalDetails.put("Pincode of primary practice", "572216");
		finalDetails.put("License Number", data.get("License Number"));
		finalDetails.put("Practice Year", data.get("Practice Year"));
		finalDetails.put("Total Case Experience", data.get("Total Case Experience"));
		finalDetails.put("Clinic Name", data.get("Clinic Name"));
		finalDetails.put("Clinic Address", data.get("Clinic Address"));
		finalDetails.put("Clinic Email", data.get("Clinic Email"));
		finalDetails.put("Clinic PostCode", data.get("Clinic PostCode"));
		finalDetails.put("Scanner Available", data.get("Scanner Available"));
		finalDetails.put("Clinic Name1", data.get("Clinic Name1"));
		finalDetails.put("Clinic Address1", data.get("Clinic Address1"));
		finalDetails.put("Clinic Email1", data.get("Clinic Email1"));
		finalDetails.put("Clinic PostCode1", data.get("Clinic PostCode1"));
		finalDetails.put("Scanner Available1", data.get("Scanner Available1"));
		Values.dentistFinalDetails = finalDetails;
		enterFinalDetailsForRegister(finalDetails);
		Values.currentDateTime = new Date();
		jsClick(btnNextAndSubmitJoinus, "Submit Button");
	}

	public void registerExistingUser() {
		firstDetails.put("First Name", "Test");
		firstDetails.put("Last Name", "Last");
		firstDetails.put("Country", "India");
		firstDetails.put("Mobile Number", "9876543210");
		firstDetails.put("Email Address", Values.frameworkProperty.getProperty("dentist.username", ""));
		firstDetails.put("Password", "Test@12345");
		firstDetails.put("Confirm Password", "Test@12345");
		firstDetails.put("Referal Code", "DRCT434");
		enterFirstDetailsForRegister(firstDetails);
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		HashMap<String, String> finalDetails = new HashMap<String, String>();
		finalDetails.put("License Number", "9856234");
		finalDetails.put("Practice Year", "12");
		finalDetails.put("Total Case Experience", "Fewer Than 10");
		finalDetails.put("Clinic Name", "Testing");
		finalDetails.put("Clinic Address", "auto test address");
		finalDetails.put("Clinic Email", "");
		finalDetails.put("Clinic PostCode", "589654");
		finalDetails.put("Scanner Available", "No");
		enterFinalDetailsForRegister(finalDetails);
		clickOn(btnNextAndSubmitJoinus, "Submit Button");
		waitForElement(weSuccErrorPopUp);
		if (isElementPresent(weSuccErrorPopUp)) {
			passed("Toaster message is displayed as expected");
			String text = weSuccErrorPopUp.getText();
			// Also add the validation for error header
			if (text.contains("User already exists")) {
				passed("User already exists message is displayed as expected");
			} else {
				fail("Error message is not displayed as expected, Expected is User already exists,But actual is "
						+ text);
			}
		} else {
			passed("Toaster message is not displayed");
		}
		takeScreenshot();
	}

	public DentistDashboardPage validateRegisterAndGetStarted() {
		waitForElement(weDialog);
		if (isElementPresent(weDialog)) {
			String msg = getText(weDialog);
			if (msg.contains(
					"Welcome " + firstDetails.get("First Name") + ". Please let us know why you joined 32Co?")) {
				passed("Registerd the new user successfully");
				takeScreenshot();
				sleep(2000);
				clickOn(pleaseLetusKnowOption, "please Let us Know Option");
				sleep(2000);
				clickOn(notSureYet, "I'm not sure yet");
				clickOn(nextBtn, "Next Button");
				clickOn(canWeHelpnextBtn, "can we help Next Button");
				clickOn(canWeHelpnextBtn, "why behind every plan Next Button");
				clickOn(canWeHelpnextBtn, "Book your intro call");
				takeScreenshot();
			} else {
				failAssert(
						"Expected success message is Welcome " + firstDetails.get("First Name") + ". Please let us know why you joined 32Co? , but actual is "
								+ msg);
			}
		} else {
			takeScreenshot();
			failAssert("New user is not registered successfully");
		}
		return new DentistDashboardPage(browser, data);
	}
	
	 
	public void validateMandatoryFieldFirstSection() {
		HashMap<String, String> firstDetails = new HashMap<String, String>();
		firstDetails.put("First Name", "");
		firstDetails.put("Last Name", "");
		firstDetails.put("Country", "");
		firstDetails.put("Mobile Number", "");
		firstDetails.put("Email Address", "");
		firstDetails.put("Password", "");
		firstDetails.put("Confirm Password", "");
		firstDetails.put("Referal Code", "");
		enterFirstDetailsForRegister(firstDetails);
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		sleep(5000);
		validateErrorMessageOfField(txtFirstNameJoinus, "First Name",
				"Please tell us your name so we know what to call you");
		// validateErrorMessageOfField(txtFirstNameJoinus, "First Name", "Please provide
		// your first name");
		validateErrorMessageOfField(txtLastNameJoinus, "Last Name", "We need your last name as well please");
		validateErrorMessageOfField(txtCountryJoinus, "Country", "Please tell us where you work");
		validateErrorMessageOfField(txtMobileNumberJoinus, "Mobile Number",
				"We need your phone number in case we need to reach you");
		validateErrorMessageOfField(txtEmailJoinus, "Email Address", "Please enter your email address");
		validateErrorMessageOfField(txtPasswordJoinus, "Password", "Please enter valid password");
		validateErrorMessageOfField(txtConfirmPasswordJoinus, "Confirm Password",
				"Please enter the same password again, just to be sure");
		takeScreenshot();
	}

	public void validateNegativeFieldFirstSection() {
		HashMap<String, String> firstDetails = new HashMap<String, String>();
		firstDetails.put("First Name", "Test");
		firstDetails.put("Last Name", "123");
		firstDetails.put("Country", "India");
		firstDetails.put("Mobile Number", "54367");
		firstDetails.put("Email Address", "test@t");
		firstDetails.put("Password", "test");
		firstDetails.put("Confirm Password", "Test456");
		firstDetails.put("Referal Code", "");
		enterFirstDetailsForRegister(firstDetails);
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		sleep(5000);
		validateErrorMessageOfField(txtMobileNumberJoinus, "Mobile Number",
				"We need your phone number in case we need to reach you");
		validateErrorMessageOfField(txtEmailJoinus, "Email Address", "Please enter your full email address");
		validateErrorMessageOfField(txtPasswordJoinus, "Password", "Please enter valid password");
		validateErrorMessageOfField(txtConfirmPasswordJoinus, "Confirm Password",
				"Sorry, those passwords do not match");
		takeScreenshot();
	}

	public void validateMandatoryFieldFinalSection() {
		HashMap<String, String> firstDetails = new HashMap<String, String>();
		firstDetails.put("First Name", "Test");
		firstDetails.put("Last Name", "Last");
		firstDetails.put("Country", "India");
		firstDetails.put("Mobile Number", "9876543210");
		firstDetails.put("Email Address", "auto@gmail.com");
		firstDetails.put("Password", "Test@12345");
		firstDetails.put("Confirm Password", "Test@12345");
		firstDetails.put("Referal Code", "DRCT434");
		enterFirstDetailsForRegister(firstDetails);
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		HashMap<String, String> finalDetails = new HashMap<String, String>();
		finalDetails.put("License Number", "");
		finalDetails.put("Practice Year", "");
		finalDetails.put("Total Case Experience", "");
		finalDetails.put("Clinic Name", "");
		finalDetails.put("Clinic Address", "");
		finalDetails.put("Clinic Email", "");
		finalDetails.put("Clinic PostCode", "");
		finalDetails.put("Scanner Available", "");
		enterFinalDetailsForRegister(finalDetails);
		clickOn(btnNextAndSubmitJoinus, "Submit Button");
		sleep(5000);
		validateErrorMessageOfField(txtDentistLicenseJoinus, "Dental License Number",
				"Please enter your dental licence number");
		validateErrorMessageOfField(txtPracticeYearJoinus, "Practice Year",
				"Please tell us how long you’ve been practicing");
		validateErrorMessageOfField(SearchTotalCasesExpJoinus, "Total Case Experience",
				"Please select number of cases completed");
		validateErrorMessageOfField(txtClinicNameJoinus, "Clinic Name", "Please tell us where you work");
		validateErrorMessageOfField(txtClinicAddressJoinus, "Clinic Address",
				"Please add the clinic address so we know where to send things");
		validateErrorMessageOfField(txtClinicPostCodeJoinus, "Clinic PostCode",
				"Please enter your clinic post code or city");
		validateErrorMessageOfField(SearchScannerJoinus, "Scanner", "Please select option");
		takeScreenshot();
	}

	public void validateTheCarouselAndRegisterPageUI() {
		try {
			String[] titles = { "Duran by Scheu", "Zendura FLX by Straumann", "Essix by Dentsply" };
			String[] refinements = { "1 refinement included", "All refinements included", "2 refinements included" };
			String[] details = { "Specialist Orthodontist Supported Design and Plan",
					"Specialist Orthodontist Supported Design and Plan",
					"Specialist Orthodontist Supported Design and Plan" };
			List<WebElement> wesCarousel = browser.findElements(By.xpath("//div[@class='ant-carousel']//ul/li/button"));
			for (int i = 0; i < 3; i++) {
				wesCarousel = browser.findElements(By.xpath("//div[@class='ant-carousel']//ul/li/button"));
				clickOn(wesCarousel.get(i), i + 1 + " Carousel Link");
				sleep(1000);
				WebElement acttitle = browser.findElement(
						By.xpath("//div[@class='slick-track']/div[contains(@class,'slick-active')]/div/div/div[1]"));
				WebElement actmedium = browser.findElement(
						By.xpath("//div[@class='slick-track']/div[contains(@class,'slick-active')]/div/div/div[2]"));
				WebElement actdetail = browser.findElement(
						By.xpath("//div[@class='slick-track']/div[contains(@class,'slick-active')]/div/div/div[3]"));
				String strTitle = acttitle.getText();
				String strMedium = actmedium.getText();
				String strDetails = actdetail.getText();
				if (strTitle.equals(titles[i])) {
					passed(i + 1 + ": Carousel Title is as expected " + strTitle);
				} else {
					fail(i + 1 + " : Carousel Title is not as expected, Actual is " + strTitle + ", But expected is"
							+ titles[i]);
				}
				if (strMedium.equals(refinements[i])) {
					passed(i + 1 + ": Carousel Title is as expected " + strMedium);
				} else {
					fail(i + 1 + " : Carousel Title is not as expected, Actual is " + strMedium + ", But expected is"
							+ refinements[i]);
				}
				if (strDetails.equals(details[i])) {
					passed(i + 1 + ": Carousel Title is as expected " + strDetails);
				} else {
					fail(i + 1 + " : Carousel Title is not as expected, Actual is " + strDetails + ", But expected is"
							+ details[i]);
				}
				takeScreenshot();
			}

			WebElement weRight = browser.findElement(By.xpath("//div[contains(@class,'signup-page')]/div/div/div[2]"));
			String strRight = weRight.getText();
			String strWelcomeFirst = "I’m Dr. Ceren, one of the many dentists that work for 32Co. It’s time to get you providing Clear Aligners differently.";
			String strWelcomeSec = "Get started by telling us a little bit about yourself to gain instant access to the platform and our educational content. Don’t worry it only takes 2 minutes";
			String strWelcomeThird = "If you get stuck along the way contact us at support@32co.com, we’re always here to help";
			if (strRight.contains(strWelcomeFirst)) {
				passed("Welcome page is displayed as expected " + strWelcomeFirst);
			} else {
				;
				fail("Welcome Details are not displayed as expected in Register Page");
			}
			if (strRight.contains(strWelcomeSec)) {
				passed("Welcome page is displayed as expected " + strWelcomeSec);
			} else {
				;
				fail("Welcome Details are not displayed as expected in Register Page");
			}
			if (strRight.contains(strWelcomeThird)) {
				passed("Welcome page is displayed as expected " + strWelcomeThird);
			} else {
				;
				fail("Welcome Details are not displayed as expected in Register Page");
			}

			if (isElementPresent(btnSignIn)) {
				passed("Already Have account Sign In Button is displayed in register Page");
				clickOn(btnSignIn, "Sign In Button");
				if (isElementPresent(By.id("basic_email"))) {
					passed("Log in page is displayed as expected");
					takeScreenshot();
				} else {
					fail("Login page is not displayed");
					takeScreenshot();
				}
			} else {
				fail("Already Have account Sign In Button is not displayed in register Page");
			}

		} catch (Exception e) {
			fail("Exception caught while validating the carousel " + e.getMessage());
		}
	}

	public void ValidateNotReadyToJoinPopup() {
		clickOn(txtFirstNameJoinus, "First name Textbox");
		moveToElement(By.xpath("//div[@class='ant-carousel']//ul/li/button[1]"));
		Actions a = new Actions(browser);
		a.moveByOffset(0, 20).perform();
		a.click().perform();
		waitForElement(By.xpath("//button/span[text()='Remind Me']"));
		if (isElementPresent(By.xpath("//button/span[text()='Remind Me']"))) {
			passed("Not ready to join us pop up is displayed");
			takeScreenshot();
		} else {
			fail("Not ready to join us pop up is displayed");
		}
		System.out.println("");
	}

}

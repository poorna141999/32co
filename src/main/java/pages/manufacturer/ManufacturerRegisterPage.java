package pages.manufacturer;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;

public class ManufacturerRegisterPage extends Page{


	@FindBy(id = "manufacturer-signup_firstName")
	private WebElement txtFirstNameJoinus;  
	
	@FindBy(id = "manufacturer-signup_lastName")
	private WebElement txtLastNameJoinus;
	
	@FindBy(id = "manufacturer-signup_email")
	private WebElement txtEmailJoinus;
	
	@FindBy(id = "manufacturer-signup_country")
	private WebElement txtCountryJoinus;

	@FindBy(xpath = "//span[text()='+91']")
	private WebElement teldialCodeJoinus;

	@FindBy(xpath = "//input[@placeholder='Enter your mobile number']")
	private WebElement txtMobileNumberJoinus;

	@FindBy(id = "manufacturer-signup_password")
	private WebElement txtPasswordJoinus;

	@FindBy(id = "manufacturer-signup_confirmPassword")
	private WebElement txtConfirmPasswordJoinus;
	
	@FindBy(id = "manufacturer-signup_business_name")
	private WebElement txtBusinessName;
	
	@FindBy(id = "manufacturer-signup_business_address_address1")
	private WebElement txtBusinessAddress;
	
	@FindBy(id = "manufacturer-signup_business_address_postcode")
	private WebElement txtBusinessAddressPostCode;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement btnNextAndSubmitJoinus;
	
	@FindBy(xpath = "//span[@class='ant-upload']/input")
	private WebElement btnProfilePicJoinUs;
	
	@FindBy(xpath = "//button/span[text()='OK']")
	private WebElement btnConfirmOk;
	
	@FindBy(xpath = "//div[@role='dialog']")
	private WebElement weDialog;

	@FindBy(xpath = "//button/span[text()='Sign in']")
	private WebElement btnSignIn;
	
	


	public ManufacturerRegisterPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void enterFirstDetailsForRegister(HashMap<String, String> details) {
		enterText(txtFirstNameJoinus, "First Name", details.get("First Name"));
		enterText(txtLastNameJoinus, "Last Name", details.get("Last Name"));
		enterText(txtPasswordJoinus, "Password", details.get("Password"));
		enterText(txtConfirmPasswordJoinus, "Confirm Password", details.get("Confirm Password"));
		enterText(txtEmailJoinus, "Email Address", details.get("Email Address"));
		enterText(txtBusinessName, "Business Name", details.get("Business Name"));
		
		enterText(txtMobileNumberJoinus, "Mobile Number", details.get("Mobile Number"));
		enterText(txtBusinessAddress, "Business Address", details.get("Business Address"));
		enterText(txtBusinessAddressPostCode, "Business PostCode", details.get("Business PostCode"));
		
		enterTextAndSelectOption(txtCountryJoinus, "Country", details.get("Country"));

		sleep(3000);
		takeScreenshot();

	}

	public void registerNewUser(Data data) {
		HashMap<String, String> firstDetails = new HashMap<String, String>();
		firstDetails.put("First Name", data.get("First Name"));
		firstDetails.put("Last Name", data.get("Last Name"));
		firstDetails.put("Password", data.get("Password"));
		firstDetails.put("Confirm Password", data.get("Confirm Password"));
		if(data.get("Email Address").trim().equals("")) {
			firstDetails.put("Email Address", generateRandomString(8, "alpha")+"@gmail.com");
		}else {
			firstDetails.put("Email Address", data.get("Email Address"));
		}
		
		firstDetails.put("Mobile Number", data.get("Mobile Number"));
		firstDetails.put("Business Name", data.get("Business Name"));
		firstDetails.put("Business Address", data.get("Business Address"));
		firstDetails.put("Business PostCode", data.get("Business PostCode"));
		firstDetails.put("Country", data.get("Country"));
		enterFirstDetailsForRegister(firstDetails);		
		uploadFile(btnProfilePicJoinUs, ".\\files\\dp.jpg", btnConfirmOk);
		clickOn(btnNextAndSubmitJoinus, "Next Button");
	}

	public void validateRegisterOld() {
		String actFirst = "Seal the deal with a click..(by verifying your email)";
		String actSecond = "Please check your email and spam folders to verify your email and log straight into your platform to book your onboarding!";
		String actThird = "Please contact support@32co.com if you have any questions.";
		waitForElement(By.xpath("//div[contains(@class,'page-container')]//p"));
		List<WebElement> wes = browser.findElements(By.xpath("//div[contains(@class,'page-container')]//p"));
		if(wes.size()==3) {
			String first = wes.get(0).getText();
			String second = wes.get(1).getText();
			String third = wes.get(2).getText();
			if(actFirst.equals(first)) {
				passed("Validating the page after successfull Register : "+ actFirst);
			}else {
				fail("Register Success Page, Expected is "+actFirst + ", But expected is "+first);
			}
			if(actSecond.equals(second)) {
				passed("Validating the page after successfull Register : "+ actSecond);
			}else {
				fail("Register Success Page, Expected is "+actSecond + ", But expected is "+second);
			}
			if(actThird.equals(third)) {
				passed("Validating the page after successfull Register : "+ actThird);
			}else {
				fail("Register Success Page, Expected is "+actThird + ", But expected is "+third);
			}
		}else {
			fail("All the details are not displayed after registering the new designer");
		}
		takeScreenshot();
	}
	
	public void validateRegister() {
		waitForElement(By.xpath("//span[text()='Dashboard']"));

		if(isElementPresent(By.xpath("//span[text()='Dashboard']"))) {
			passed("Validating the page after successfull Register : Dashboard page is displayed");

		}else {
			fail("Dashboard page is not displayed after registering the new user");
		}
		takeScreenshot();
	}


	public void validateMandatoryFieldFirstSection() {
		HashMap<String, String> firstDetails = new HashMap<String, String>();
		firstDetails.put("First Name", "");
		firstDetails.put("Last Name", "");
		firstDetails.put("Password", "");
		firstDetails.put("Confirm Password", "");
		firstDetails.put("Email Address", "");
		firstDetails.put("Mobile Number", "");
		
		firstDetails.put("Business Name", "");
		firstDetails.put("Business Address", "");
		firstDetails.put("Business PostCode", "");
		firstDetails.put("Country", "");
		enterFirstDetailsForRegister(firstDetails);		
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		sleep(5000);
		validateErrorMessageOfField(txtFirstNameJoinus, "First Name", "Please input first name");
		validateErrorMessageOfField(txtLastNameJoinus, "Last Name", "Please input last name");
		validateErrorMessageOfField(txtPasswordJoinus, "Password", "Please enter valid password");
		validateErrorMessageOfField(txtConfirmPasswordJoinus, "Confirm Password", "Please input confirm password");
		validateErrorMessageOfField(txtEmailJoinus, "Email Address", "Please enter your email address");
		validateErrorMessageOfField(txtMobileNumberJoinus, "Mobile Number", "We need your phone number in case we need to reach you");
		validateErrorMessageOfField(txtBusinessAddress, "Business Address", "Please enter your business address");
		takeScreenshot();
	}
	
	public void validateNegativeFieldFirstSection() {
		HashMap<String, String> firstDetails = new HashMap<String, String>();
		firstDetails.put("First Name", "Test");
		firstDetails.put("Last Name", "123");
		firstDetails.put("Password", "test");
		firstDetails.put("Confirm Password", "Test456");
		firstDetails.put("Email Address", "test@t");
		firstDetails.put("Mobile Number", "54367");
		firstDetails.put("Business Address", "test");
		firstDetails.put("Country", "India");
		enterFirstDetailsForRegister(firstDetails);		
		clickOn(btnNextAndSubmitJoinus, "Next Button");
		sleep(5000);
		validateErrorMessageOfField(txtMobileNumberJoinus, "Mobile Number", "We need your phone number in case we need to reach you");
		validateErrorMessageOfField(txtEmailJoinus, "Email Address", "Please enter your full email address");
		validateErrorMessageOfField(txtPasswordJoinus, "Password", "Please enter valid password");
		validateErrorMessageOfField(txtConfirmPasswordJoinus, "Confirm Password", "The two passwords that you entered do not match!");
		takeScreenshot();
	}

}

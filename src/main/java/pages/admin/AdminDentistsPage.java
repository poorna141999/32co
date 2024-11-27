package pages.admin;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class AdminDentistsPage extends Page {

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//li[@class='ant-pagination-options']//span")
	private WebElement wePagination;

	@FindBy(id = "dentists_refresh-button")
	private WebElement btnReload;

	@FindBy(xpath = "(//span[@aria-label='search'])[1]")
	private WebElement txtSearch;

	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement txtSearchInput;

	@FindBy(id = "dentists-filter_status_list")
	private WebElement lstSearch;

	@FindBy(xpath = "(//span[@aria-label='search'])[2]")
	private WebElement btnSearch;

	@FindBy(xpath = "//span[text()='Send Invite Email']")
	private WebElement lnkSendInviteEmail;

	@FindBy(xpath = "//span[text()='Confirm GDC verified']")
	private WebElement confirnGDCVerification;

	@FindBy(xpath = "//span[text()='Archive']")
	private WebElement lnkArchive;

	@FindBy(xpath = "//span[text()='Unarchive']")
	private WebElement lnkUnarchive;

	@FindBy(xpath = "//span[text()='Assign Practice(s)']")
	private WebElement lnkAssignPractice;

	@FindBy(xpath = "//button/span[text()='ASSIGN PRACTICE']")
	private WebElement btnAssignPractise;

	@FindBy(xpath = "//span[text()='Onboarding Complete']")
	private WebElement lnkOnboardingComplete;

	@FindBy(xpath = "//span[text()='Complete Pending']")
	private WebElement lnkCompletePending;

	public AdminDentistsPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void validateDentistsPageUI() {
		isElementDisplayed(btnReload, "Reload Button");
		isElementDisplayed(btnSearch, "Search Button");
		isElementDisplayed(txtSearch, "Search TextBox");
		isElementDisplayed(btnNextPage, "Next Page Button");
		isElementDisplayed(btnPreviousPage, "Previous Page Button");
		isElementDisplayed(wePagination, "Records per page dropdown");
		String strPagination = wePagination.getText();
		if (strPagination.equals("10 / page")) {
			passed("By default 10 / page records are displayed ");
		} else {
			fail("By default 10 / page records are not displayed, Actual is " + strPagination);
		}
		int i = 0;
		String[] expHeaders = { "Dentist", "Status", "Onboarding date", "Practice", "Number Cases Ordered",
				"Number of Cases ordered in past 30 days" };
		List<WebElement> weHeaders = browser.findElements(By.xpath("//table/thead//tr/th"));

		for (WebElement weHeader : weHeaders) {
			String str = weHeader.getText();
			if (expHeaders[i].equals(str)) {
				passed("Column Header is displayed as expected " + str);
			} else {
				fail("Expected column header is not displayed, Expected is " + expHeaders[i] + ", but actual is "
						+ str);
			}
		}
		takeScreenshot();
	}

	public boolean sendEmailInvite() {
		boolean sent = false;
		waitForElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		WebElement we = browser.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		selectOptionDropDown(we, "Search Filter", "Applied");
		sleep(3000);
		clickOn(txtSearch, "Search Button");
		sleep(3000);
		enterText(txtSearchInput, "Search Textbox",
				Values.dentistFirstDetails.get("First Name") + " " + Values.dentistFirstDetails.get("Last Name"));
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			Values.currentDateTime = new Date();
			wesCheckbox.get(0).click();
			clickOn(confirnGDCVerification, "Confirm GDC Verification");
			/*
			 * // clickOn(lnkSendInviteEmail, "Send Invite Email Link");
			 * waitForElement(By.xpath("//*[text()='Dentist Invited Successfully']")); if
			 * (isElementPresent(By.xpath("//*[text()='Dentist Invited Successfully']"))) {
			 * passed("Sent an Invite Successfully , Success Message is displayed"); sent =
			 * true; } else { fail("Sent an Invite success message is not displayed"); } }
			 * else { fail("No Dentists are listed in Applied search filters with name " +
			 * Values.dentistFirstDetails.get("First Name"));
			 */
		}
		takeScreenshot();
		return sent;
	}

	public boolean assignPractise() {
		boolean sent = false;
		waitForElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		WebElement we = browser.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		selectOptionDropDown(we, "Search Filter", "Verified");
		sleep(3000);
		clickOn(txtSearch, "Search Button");
		sleep(3000);
		enterText(txtSearchInput, "Search Textbox",
				Values.dentistFirstDetails.get("First Name") + " " + Values.dentistFirstDetails.get("Last Name"));
		clickOn(btnSearch, "Search Button");
		waitForElement(By.xpath("//table/tbody/tr/td//input[1]"));
		sleep(2000);
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			wesCheckbox.get(0).click();
			clickOn(lnkAssignPractice, "Assign Practice Link");
			waitForElement(By.xpath("//div[@role='dialog']"));
			sleep(2000);
			List<WebElement> wecheckBoxPractise = browser
					.findElements(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]//input"));
			wecheckBoxPractise.get(0).click();
			clickOn(btnAssignPractise, "Assign Practise");
			waitForElement(By.xpath("//*[text()='Practice Assigned Successfully']"));
			if (isElementPresent(By.xpath("//*[text()='Practice Assigned Successfully']"))) {
				passed("Practice Asigned Successfully , Success Message is displayed");
				takeScreenshot();
				sent = true;
			} else {
				fail("Practice Asigned Successfully is not displayed");
				takeScreenshot();
			}
		} else {
			fail("No Dentists are listed in Applied search filters with name "
					+ Values.dentistFirstDetails.get("First Name"));
		}
		takeScreenshot();
		return sent;
	}

	public boolean onBoardDentist() {
		boolean sent = false;
		waitForElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		WebElement we = browser.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		selectOptionDropDown(we, "Search Filter", "Finalisation");
		sleep(3000);
		clickOn(txtSearch, "Search Button");
		sleep(3000);
		enterText(txtSearchInput, "Search Textbox",
				Values.dentistFirstDetails.get("First Name") + " " + Values.dentistFirstDetails.get("Last Name"));
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			Values.currentDateTime = new Date();
			wesCheckbox.get(0).click();
			clickOn(lnkOnboardingComplete, "Onboarding Complete Link");
			waitForElement(By.xpath("//*[text()='Dentist On Board Successfully']"));
			if (isElementPresent(By.xpath("//*[text()='Dentist On Board Successfully']"))) {
				passed("Dentist On Board Successfully , Success Message is displayed");
				sent = true;
			} else {
				fail("Dentist On Board Successfully message is not displayed");
			}
		} else {
			fail("No Dentists are listed in Applied search filters with name "
					+ Values.dentistFirstDetails.get("First Name"));
		}
		takeScreenshot();
		return sent;
	}

	public boolean completePending() {
		boolean sent = false;
		waitForElement(By.xpath("//span[@class='ant-select-selection-item']"));
		WebElement we = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
		selectOptionDropDown(we, "Search Filter", "All");
		sleep(2000);
		selectOptionDropDown(we, "Search Filter", "Pending package");
		sleep(3000);
		txtSearch.clear();
		enterText(txtSearch, "Search Textbox",
				Values.dentistFirstDetails.get("First Name") + " " + Values.dentistFirstDetails.get("Last Name"));
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			Values.currentDateTime = new Date();
			wesCheckbox.get(0).click();
			clickOn(lnkCompletePending, "Complete Pending Link");
			waitForElement(By.xpath("//*[text()='Dentist is Live!!!']"));
			if (isElementPresent(By.xpath("//*[text()='Dentist is Live!!!']"))) {
				passed("Dentist is Live!!! , Success Message is displayed");
				sent = true;
			} else {
				fail("Dentist is Live!!! message is not displayed");
			}
		} else {
			fail("No Dentists are listed in Applied search filters with name "
					+ Values.dentistFirstDetails.get("First Name"));
		}
		takeScreenshot();
		return sent;
	}

	public boolean verifyDentistLive() {
		boolean sent = false;
		waitForElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		WebElement we = browser.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[1]"));
		selectOptionDropDown(we, "Search Filter", "Live");
		sleep(3000);
		clickOn(txtSearch, "Search Button");
		sleep(3000);
		enterText(txtSearchInput, "Search Textbox",
				Values.dentistFirstDetails.get("First Name") + " " + Values.dentistFirstDetails.get("Last Name"));
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td[2]"));
		/*
		 * if (wesCheckbox.size() > 1) {
		 * 
		 * passed("Dentist is listed in Live");
		 * 
		 * } else { fail("Dentists are not listed in Live with name " +
		 * Values.dentistFirstDetails.get("First Name")); }
		 */
		boolean isDentistListed = false;
		String dentistFullName = Values.dentistFirstDetails.get("First Name") + " "
				+ Values.dentistFirstDetails.get("Last Name");
		for (WebElement element : wesCheckbox) {
			String elementText = element.getText().trim(); 
			if (elementText.equals(dentistFullName)) {
				isDentistListed = true;
				break;
			}
		}
		if (isDentistListed) {
		    passed("Dentist is listed in Live: " + dentistFullName);
		} else {
		    fail("Dentists are not listed in Live with name: " + dentistFullName);
		}
		takeScreenshot();
		return sent;
	}

	public boolean searchDentist(String name) {
		txtSearch.clear();
		enterText(txtSearch, "Search Textbox", name);
		clickOn(btnSearch, "Search Button");
		sleep(2000);
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td[2]"));
		if (wesCheckbox.size() > 1) {
			passed("Search results are displayed for " + name);
			takeScreenshot();
			return true;
		} else {
			fail("Search results are not displayed for " + name);
			takeScreenshot();
			return false;
		}
	}

	public void archiveUser() {
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			wesCheckbox.get(0).click();
			clickOn(lnkArchive, "Archive Link");
			waitForElement(By.xpath("//*[text()='Dentist Archived Successfully']"));
			if (isElementPresent(By.xpath("//*[text()='Dentist Archived Successfully']"))) {
				passed("Dentist Archived Successfully , Success Message is displayed");
				takeScreenshot();
			} else {
				fail("Dentist Archived Successfully message is not displayed");
			}
		} else {
			fail("Search results are not displayed ");
		}
		takeScreenshot();
	}

	public void unArchiveUser() {
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			wesCheckbox.get(0).click();
			clickOn(lnkUnarchive, "UnArchive Link");
			waitForElement(By.xpath("//*[text()='Dentist Unarchived!!!']"));
			if (isElementPresent(By.xpath("//*[text()='Dentist Unarchived!!!']"))) {
				passed("Dentist Unarchived!!! , Success Message is displayed");
			} else {
				fail("Dentist Unarchived!!! message is not displayed");
			}
		} else {
			fail("Search results are not displayed ");
		}
		takeScreenshot();
	}

	public void validateArchiveAndUnarchiveDentist(String status) {
		String name = "";

		waitForElement(By.xpath("//span[@class='ant-select-selection-item']"));
		WebElement we = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
		selectOptionDropDown(we, "Search Filter", status);
		sleep(4000);
		waitForElement(By.xpath("//table/tbody/tr/td[2]/a"));
		List<WebElement> wedentists = browser.findElements(By.xpath("//table/tbody/tr/td[2]/a"));
		if (wedentists.size() > 0) {
			name = wedentists.get(0).getText();
			archiveUser();
			sleep(4000);
			waitForElement(By.xpath("//span[@class='ant-select-selection-item']"));
			WebElement weArchive = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
			selectOptionDropDown(weArchive, "Search Filter", "Archived");
			sleep(4000);
			waitForElement(By.xpath("//table/tbody/tr/td[2]/a"));
			wedentists = browser.findElements(By.xpath("//table/tbody/tr/td[2]/a"));
			if (wedentists.size() > 0) {
				if (searchDentist(name)) {
					unArchiveUser();
					sleep(4000);
				} else {
					fail("Dentist is not moved to Archived state as expected - " + name);
				}

			} else {
				fail("No records found in Archived Status, Unable to validate the UnArchive feature in Archived status");
			}
		} else {
			info("No records found in " + status + " Status, Unable to validate the Archive feature in " + status
					+ " status");
		}
		takeScreenshot();
	}

	public void validateByChangingDropDownValue(String status, String status2) {
		String name = "";

		waitForElement(By.xpath("//span[@class='ant-select-selection-item']"));
		WebElement we = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
		selectOptionDropDown(we, "Search Filter", status);
		sleep(4000);
		waitForElement(By.xpath("//table/tbody/tr/td[2]/a"));
		List<WebElement> wesCheckbox = browser.findElements(By.xpath("//table/tbody/tr/td//input[1]"));
		if (wesCheckbox.size() > 0) {
			wesCheckbox.get(0).click();
			waitForElement(By.xpath("//div[contains(@class,'message-wrapper')]/span[1]"));
			String text = browser.findElement(By.xpath("//div[contains(@class,'message-wrapper')]/span[1]")).getText();
			if (text.contains("You have selected 1 Item.")) {
				passed("You have selected 1 Item. message is displayed after selecting the checkbox");
			} else {
				fail("You have selected 1 Item. message is not displayed after selecting the checkbox");
			}
			takeScreenshot();
			we = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
			selectOptionDropDown(we, "Search Filter", status2);
			sleep(2000);
			waitForElement(By.xpath("//div[contains(@class,'message-wrapper')]/span[1]"));
			if (isElementPresent(By.xpath("//div[contains(@class,'message-wrapper')]/span[1]"))) {
				text = browser.findElement(By.xpath("//div[contains(@class,'message-wrapper')]/span[1]")).getText();
				if (text.contains("You have selected 1 Item.")) {
					fail("You have selected 1 Item. message still is displayed after changing the dropdown value");
				} else {
					fail("You have selected 1 Item. message is not displayed But still following message is displayed "
							+ text);
				}
			} else {
				passed("You have selected 1 Item. message is not displayed after changing the dropdown value as expected");
			}
			takeScreenshot();
		} else {
			fail("No records found in " + status + " Status, Unable to validateByChangingDropDownValue in " + status
					+ " status");
		}
		takeScreenshot();
	}

	public void retrieveLiveRecordDetailsFromDentistTable() {
		waitForElement(By.xpath("//span[@class='ant-select-selection-item']"));
		WebElement we = browser.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
		selectOptionDropDown(we, "Search Filter", "Live");
		sleep(3000);
		List<WebElement> wesDentist = browser.findElements(By.xpath("//table/tbody/tr[2]/td"));
		if (wesDentist.size() > 0) {
			Values.adminDentistLiveRecord.put("Dentist Name", wesDentist.get(0).getText().trim());
			Values.adminDentistLiveRecord.put("Onboarding Date", wesDentist.get(1).getText().trim());
			Values.adminDentistLiveRecord.put("Practice", wesDentist.get(2).getText().trim());
			Values.adminDentistLiveRecord.put("Number Cases", wesDentist.get(3).getText().trim());
		} else {
			fail("No records found in Live Status in dentist Screen");
		}

	}

}

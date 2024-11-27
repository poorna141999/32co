package pages.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;

public class AdminReleaseNotesPage extends Page {


	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "release-notes_refresh-button")
	private WebElement btnReload;

	@FindBy(id = "release-notes_create-new-button")
	private WebElement btnCreateNewReleaseNote;

	@FindBy(xpath = "//div[@class='ant-modal-body']//button/span[text()='Create new release note']")
	private WebElement btnCreateNewReleaseNoteInModal;

	@FindBy(xpath = "//div[@class='ant-modal-body']//button/span[text()='Preview']")
	private WebElement btnPreviewModal;

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//span[text()='Preview']")
	private WebElement btnPreview;

	@FindBy(xpath = "//span[text()='Go to notion']")
	private WebElement btnGotonotion;

	@FindBy(xpath = "//span[text()='Edit']")
	private WebElement btnEdit;

	@FindBy(xpath = "//span[text()='Preview']")
	private WebElement btnRemove;

	@FindBy(id = "version")
	private WebElement txtVersion;

	@FindBy(id = "notionUrl")
	private WebElement txtNotionUrl;

	@FindBy(id = "description")
	private WebElement txtDescription;

	@FindBy(xpath = "//div[@class='ant-modal-body']//*[@class='ant-select-selector']")
	private WebElement txtuserType;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weSuccessMessage;

	public AdminReleaseNotesPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void validateReleaseNotes() {
		List<WebElement> weHeaders = browser.findElements(By.xpath("//div[@class='ant-card-head']"));
		if(weHeaders.size()>0) {
			passed(weHeaders.size() + " Release notes are displayed in page");
			isElementDisplayed(btnPreview, "Preview Button");
			isElementDisplayed(btnGotonotion, "Go to notion Button");
			isElementDisplayed(btnEdit, "Edit Button");
			isElementDisplayed(btnRemove, "Preview Button");
		}else {
			info("No Release notes are displayed in the page");
		}
		isElementDisplayed(wePagination, "Total Records dropdown");
		String strPagination = wePagination.getText();
		if(strPagination.equals("20 / page")) {
			passed("By default 20 / page records are displayed ");
		}else {
			fail("By default 20 / page records are not displayed, Actual is "+strPagination);
		}
		isElementDisplayed(btnPreviousPage, "Previous Page Button");
		isElementDisplayed(btnNextPage, "Next Page Button");
	}


	public void createNewReleaseNotes() {
		List<WebElement> weHeaders = browser.findElements(By.xpath("//div[@class='ant-card-head']"));
		int sizeBefore = weHeaders.size();
		clickOn(btnCreateNewReleaseNote, "Create new release note");
		waitForElement(txtVersion);
		String version = generateRandomString(4, "alpha") +" "  +data.get("Version");
		enterText(txtVersion, "Version", version);
		enterText(txtNotionUrl, "Notion URL", data.get("Notion URL"));
		clickOn(btnPreviewModal, "Preview Button on Modal");
		List<WebElement> wePreviewDialog = browser.findElements(By.xpath("//div[@class='ant-modal-body']"));
		if(wePreviewDialog.size()>1) {
			passed("Preview Modal is displayed as expected");
			if(isElementPresent(By.xpath("//div[@role='dialog']//div[@class='notion-page-scroller']"))){
				passed("Preview Modal is displayed as expected for valid URL");
				takeScreenshot();
			}else if(isElementPresent(By.xpath("//div[@class='ant-modal-body']//div[text()='Content URL is invalid!']"))){
				fail("Preview Modal is not displayed , Content URL is invalid error");
				takeScreenshot();
			}
			takeScreenshot();
			List<WebElement> btnClose = browser.findElements(By.xpath("//div[@class='ant-modal-content']//span[@aria-label='close']"));
			clickOn(btnClose.get(1), "Modal Close Button");			
		}else {
			fail("Preview Modal is not displayed");
		}
		enterText(txtDescription, "Description", data.get("Description"));
		selectOptionDropDown(txtuserType, "User Type", data.get("User Type"));
		jsClick(btnCreateNewReleaseNoteInModal, "Create new release note on modal");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			takeScreenshot();
			String str = weSuccessMessage.getText();
			if(str.equals("Created")) {
				passed("Release notes Record created successfully");
			}else {
				fail("Release notes record not created");
			}
			clickOn(btnReload, "Reload Button");
			List<WebElement> weHeaders1 = browser.findElements(By.xpath("//div[@class='ant-card-head']"));
			int sizeAfter = weHeaders1.size();
			if(sizeBefore<20) {
				if(sizeAfter>sizeBefore) {
					passed("New Record created is displayed in list as expected");
				}else {
					fail("New Record created is not displayed as expected");
				}
			}
			takeScreenshot();
		}
	}

	public void validateMandatoryFields() {
		clickOn(btnCreateNewReleaseNote, "Create new release note");
		clickOn(btnCreateNewReleaseNoteInModal, "Create new release note on modal");
		validateErrorMessageOfField(txtVersion, "Version", "This field is required");
		validateErrorMessageOfField(txtNotionUrl, "Notion URL", "This field is required");
		validateErrorMessageOfField(txtDescription, "Description", "This field is required");
		validateErrorMessageOfField(txtuserType, "User Type", "This field is required");		
	}
}

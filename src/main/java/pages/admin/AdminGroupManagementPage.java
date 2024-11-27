package pages.admin;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Values;

public class AdminGroupManagementPage extends Page {


	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "groups-list_refresh-button")
	private WebElement btnReload;

	@FindBy(id = "groups-list_create-new-group-button")
	private WebElement btnCreateNewGroup;


	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weSuccessMessage;

	@FindBy(xpath = "//span[text()='Search and filter']/parent::div")
	private WebElement btnSearchExpand;

	@FindBy(id = "register_q")
	private WebElement txtSearchGroupName;

	@FindBy(xpath = "//*[@id='register_tags']/../..")
	private WebElement txtSearchGroupTag;

	@FindBy(id = "register_status")
	private WebElement txtSearchGroupStatus;

	@FindBy(id = "groups-filter_search-button")
	private WebElement btnSearch;

	@FindBy(id = "groups-filter_clear-button")
	private WebElement btnClear;

	@FindBy(id = "name")
	private WebElement txtCreateName;

	@FindBy(id = "tags")
	private WebElement txtCreateTag;

	@FindBy(id = "description")
	private WebElement txtCreateDescription;

	@FindBy(id = "dentistName")
	private WebElement txtDentistName;

	@FindBy(id = "onBoardingDate")
	private WebElement txtCreateOnBoardingDateFrom;

	@FindBy(xpath = "//input[@placeholder='To']")
	private WebElement txtCreateOnBoardingDateTo;

	@FindBy(id = "practiceIds")
	private WebElement txtCreatePracticeIds;

	//@FindBy(id = "minCases")
	@FindBy(id = "minSubmissions")
	private WebElement txtCreateMinCases;

	//	@FindBy(id = "maxCases")
	@FindBy(id = "maxSubmissions")
	private WebElement txtCreateMaxCases;

	@FindBy(xpath = "//input[@id='country']")
	private WebElement txtCountry;

	@FindBy(id = "practiceIds")
	private WebElement txtCreate;

	//@FindBy(xpath = "//div[@role='dialog']//table/thead//input[@type='checkbox']")
	@FindBy(xpath = "//div[@role='dialog']//table/thead//span")
	private WebElement cbSelectAllDentist;

	@FindBy(xpath = "//button/span[text()='Create Group']")
	private WebElement btnCreateGroup;

	@FindBy(xpath = "//button/span[text()='Update Group']")
	private WebElement btnUpdateGroup;


	@FindBy(id = "group-detail_archive-button")
	private WebElement btnArchive;

	@FindBy(id = "group-detail_edit-button")
	private WebElement btnEdit;

	@FindBy(xpath = "//button/span[text()='Search users']")
	private WebElement btnSearchUsers;

	@FindBy(xpath = "//button/span[text()='Reset filter']")
	private WebElement btnResetFilter;
	
	@FindBy(xpath = "//table/tbody")
	private WebElement pageTable;

	@FindBy(xpath = "//*[@role='dialog']//table/tbody")
	private WebElement dialogTable;
	
	public AdminGroupManagementPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void validateGroupUI() {
		isElementDisplayed(wePagination, "Total Records dropdown");
		String strPagination = wePagination.getText();
		if(strPagination.equals("20 / page")) {
			passed("By default 20 / page records are displayed ");
		}else {
			fail("By default 20 / page records are not displayed, Actual is "+strPagination);
		}
		isElementDisplayed(btnPreviousPage, "Previous Page Button");
		isElementDisplayed(btnNextPage, "Next Page Button");
		isElementDisplayed(btnCreateNewGroup, "Create new Group");
	}

	public void validateSeachFunctionality() {
		waitForElement(By.xpath("//table/tbody/tr[1]/td"));
		List<WebElement> wesRecords = browser.findElements(By.xpath("//table/tbody/tr[2]/td"));
		if(wesRecords.size()>4) {
			String name = wesRecords.get(0).getText();
			String tag = wesRecords.get(3).getText();
			String status = wesRecords.get(5).getText();
			waitForElement(txtSearchGroupName);
			enterText(txtSearchGroupName, "Name Search textbox", name);
			selectOptionDropDown(txtSearchGroupTag, "Group tags Search textbox", tag);
			selectOptionDropDown(txtSearchGroupStatus, "Status Search textbox", status.toUpperCase());
			clickOn(btnSearch, "Search Button");
			sleep(3000);
			wesRecords = browser.findElements(By.xpath("//table/tbody/tr[1]/td"));
			if(wesRecords.size()>4) {
				passed("Search results are displayed as expected");
				takeScreenshot();
				validateTableRecordsInTwoColumns(pageTable, name,1 ,2, 1);
				validateTableRecordsInColumn(pageTable, tag,4 , 1);
				validateTableRecordsInColumn(pageTable, status,6 , 1);
			}else {
				fail("Search results are not displayed");
				takeScreenshot();
			}
			clickOn(btnClear, "Clear Button");
			sleep(2000);
			takeScreenshot();
		}else {
			fail("No Records found, Unable to validate search functionality");
		}
	}

	public void searchGroup(HashMap<String, String> details) {
		List<WebElement> wesRecords = browser.findElements(By.xpath("//table/tbody/tr[1]/td"));
		if(wesRecords.size()>4) {
			waitForElement(txtSearchGroupName);
			enterText(txtSearchGroupName, "Name Search textbox", details.get("Name"));
			//selectOptionDropDown(txtSearchGroupTag, "Group tags Search textbox", details.get("Tag Name"));
			selectOptionDropDown(txtSearchGroupStatus, "Status Search textbox", details.get("Status").toUpperCase());
			clickOn(btnSearch, "Search Button");
			sleep(3000);
			wesRecords = browser.findElements(By.xpath("//table/tbody/tr[1]/td"));
			if(wesRecords.size()>4) {
				passed("Search results are displayed as expected");
				takeScreenshot();
			}else {
				fail("Search results are not displayed");
				takeScreenshot();
			}
			sleep(2000);
			takeScreenshot();
		}else {
			fail("No Records found, Unable to validate search functionality");
		}
	}


	public void clickOnCreateNewGroup() {
		clickOn(btnCreateNewGroup, "Create a new Group");
		waitForElement(txtCreateName);
		if(isElementPresent(txtCreateName)) {
			passed("Create New group dialog is displayed");
		}else {
			takeScreenshot();
			failAssert("Create New Group page is not displayed");
		}
	}


	public void enterDetailsInCreateGroup(HashMap<String, String> details) {
		waitForElement(txtCreateName);
		if(isElementPresent(txtCreateName)) {
			sleep(2000);
			enterText(txtCreateName, "Name", details.get("Name"));
			selectFirstOptionFromDropDown(txtCreateTag, "Tag Name");
			enterText(txtCreateDescription, "Description", details.get("Description"));
			if(details.get("Total Dentist")!=null) {
				if(details.get("Total Dentist").equalsIgnoreCase("All")) {
					clickOn(cbSelectAllDentist, "Select All Checkbox");
				}else if(isNumeric(details.get("Total Dentist"))) {
					int totalDentist = Integer.parseInt(details.get("Total Dentist"));
					for(int i=0;i<totalDentist;i++) {
						List<WebElement> wes = browser.findElements(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]//input"));
						wes.get(i).click();
					}
				}
			}
		}else {
			takeScreenshot();
			failAssert("Create New Group page is not displayed");
		}
	}


	public void createNewGroupWithAllDentist() {
		clickOnCreateNewGroup();
		HashMap<String, String> details = new HashMap<String, String>();
		details.put("Name", "Auto_"+generateRandomString(5, "alpha"));
		details.put("Description", "Created by automation script");
		details.put("Total Dentist", "All");
		details.put("Status", "Active");
		Values.groupDetails = details;
		enterDetailsInCreateGroup(details);
		clickOn(btnCreateGroup, "Create Group Button");

		waitForElement(By.xpath("//*[text()='Group Created Successfully']"));
		if(isElementPresent(By.xpath("//*[text()='Group Created Successfully']"))) {
			passed("Group Created Successfully , Success Message is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Group Created Successfully message is not displayed");
		}
	}

	public void createNewGroupWithFewDentist() {
		clickOnCreateNewGroup();
		HashMap<String, String> details = new HashMap<String, String>();
		details.put("Name", "Auto_"+generateRandomString(5, "alpha"));
		details.put("Description", "Created by automation script");
		details.put("Total Dentist", "3");
		details.put("Status", "Active");
		Values.groupDetails = details;
		enterDetailsInCreateGroup(details);
		sleep(1000);
		String str = browser.findElement(By.xpath("//div[@role='dialog']//div[@class='ant-table-wrapper']/preceding-sibling::div")).getText();
		String expMsg = "You have selected 3 Member";
		if(str.contains(expMsg)) {
			passed("Expected message is displayed "+str);
		}else {
			fail("Expceted is "+expMsg + ", But actual is "+str);
		}
		clickOn(btnCreateGroup, "Create Group Button");

		waitForElement(By.xpath("//*[text()='Group Created Successfully']"));
		if(isElementPresent(By.xpath("//*[text()='Group Created Successfully']"))) {
			passed("Group Created Successfully , Success Message is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Group Created Successfully message is not displayed");
		}
	}

	public void validateMandatoryFields() {
		clickOnCreateNewGroup();
		waitForElement(txtCreateName);
		selectFirstOptionFromDropDown(txtCreateTag, "Tag Name");	
		sleep(1000);
		clickOn(btnCreateGroup, "Create Group Button");
		sleep(2000);
		validateErrorMessageOfField(txtCreateName, "Name", "This field is required");
		validateErrorMessageOfField(txtCreateDescription, "Description", "This field is required");
		WebElement we = browser.findElement(By.xpath("//div[@role='dialog']//div[@class='ant-table-wrapper']"));
		validateErrorMessageOfField(we, "Select Dentist Table", "At least 1 dentist selected!");
		String str = browser.findElement(By.xpath("//div[@role='dialog']//div[@class='ant-table-wrapper']/preceding-sibling::div")).getText();
		String expMsg = "You have selected 0 Member";
		if(str.contains(expMsg)) {
			passed("Expected message is displayed "+str);
		}else {
			fail("Expceted is "+expMsg + ", But actual is "+str);
		}
	}



	@FindBy(xpath = "//div[@class='ant-popover-message']/div")
	private WebElement weConfirmMessage;

	@FindBy(xpath = "//div[@class='ant-popover-buttons']/button/span[text()='Cancel']")
	private WebElement btnConfirmCancel;

	@FindBy(xpath = "//div[@class='ant-popover-buttons']/button/span[text()='OK']")
	private WebElement btnConfirmOk;


	public void validateArchiveGroups() {
		HashMap<String, String> details = new HashMap<String, String>();
		details.put("Status", "Active");
		searchGroup(details);
		waitForElement(By.xpath("//table/tbody/tr/td[1]/a"));
		List<WebElement> wesGroupName = browser.findElements(By.xpath("//table/tbody/tr/td[1]/a"));
		if(wesGroupName.size()>0) {
			String strGroupName = wesGroupName.get(0).getText();
			clickOn(wesGroupName.get(0), strGroupName+ " Group Link");
			isElementDisplayed(btnArchive, "Archive Button");
			isElementDisplayed(btnEdit, "Edit Button");
			sleep(1000);
			clickOn(btnArchive, "Archive Button");
			waitForElement(btnConfirmOk);
			sleep(1000);
			clickOn(btnConfirmOk, "Confirm OK Button");
			waitForElement(By.xpath("//*[text()='Group Archived Successfully']"));
			if(isElementPresent(By.xpath("//*[text()='Group Archived Successfully']"))) {
				passed("Group Archived Successfully , Success Message is displayed");
			}else {
				fail("Group Archived Successfully message is not displayed");
			}
			sleep(2000);
		//	if(isElementPresent(btnSearchExpand)) {
		//		String strExpand = btnSearchExpand.getAttribute("aria-expanded");
		//		if(strExpand.equals("false")) {
		//			clickOn(btnSearchExpand, "Search and Filter Expand");			
		//		}
				List<WebElement> wesRecords = browser.findElements(By.xpath("//table/tbody/tr[1]/td"));
				if(wesRecords.size()>4) {
					waitForElement(txtSearchGroupName);
					enterText(txtSearchGroupName, "Name Search textbox", strGroupName);
					//selectOptionDropDown(txtSearchGroupTag, "Group tags Search textbox", details.get("Tag Name"));
					selectOptionDropDown(txtSearchGroupStatus, "Status Search textbox", "ARCHIVE");
					clickOn(btnSearch, "Search Button");
					sleep(3000);
					wesRecords = browser.findElements(By.xpath("//table/tbody/tr[1]/td"));
					if(wesRecords.size()>4) {
						passed("Archived Record is displayed in the table successfully");
						takeScreenshot();
					}else {
						fail("Archived record is not displayed in the table");
						takeScreenshot();
					}
				}else {
					fail("No Records found, Unable to validate search functionality");				
				}
			}else {
				fail("After archiving the group, Group management home page is not displayed");
			}
	//	}else {
	//		takeScreenshot();
	//		failAssert("No Active group found in the table, Create a active group and re execute the test case");
	//	}
	}


	public void validateNewGroupDisplayedInTable() {
		searchGroup(Values.groupDetails);
	}

	//TODO : Add validation to validate the Dentists displayed in group Info page
	public void EditTheGroupAndValidate() {
		searchGroup(Values.groupDetails);
		sleep(2000);
		waitForElement(By.xpath("//table/tbody/tr/td[1]/a"));
		List<WebElement> wesGroupName = browser.findElements(By.xpath("//table/tbody/tr/td[1]/a"));
		String strGroupName = wesGroupName.get(0).getText();
		clickOn(wesGroupName.get(0), strGroupName+ " Group Link");
		takeScreenshot();
		waitForElement(btnEdit);
		clickOn(btnEdit, "Edit Button");
		waitForElement(txtCreateDescription);
		takeScreenshot();
		enterText(txtCreateDescription, "Description", "Updated");
		clickOn(btnUpdateGroup, "Update Group Button");
		waitForElement(By.xpath("//*[text()='Group Updated Successfully']"));
		if(isElementPresent(By.xpath("//*[text()='Group Updated Successfully']"))) {
			passed("Group Updated Successfully , Success Message is displayed");
		}else {
			fail("Group Updated Successfully message is not displayed");
		}
		takeScreenshot();
	}

	public void enterDetailsInSearchDentistCreateGroup(HashMap<String, String> details) {
		enterText(txtDentistName, "Dentist Name", details.get("Dentist Name"));
		enterText(txtCreateOnBoardingDateFrom, "OnBoarding Date From", details.get("OnBoarding Date From"));
		enterText(txtCreateOnBoardingDateTo, "OnBoarding Date To", details.get("OnBoarding Date To"));
		txtCreateOnBoardingDateTo.sendKeys(Keys.TAB);
		txtCreateOnBoardingDateTo.sendKeys(Keys.TAB);
		selectMultiOptionDropDown(txtCreatePracticeIds, "Practice", details.get("Practice"));
		enterText(txtCreateMinCases, "Case Submitted Minimum", details.get("Case Submitted Minimum"));
		enterText(txtCreateMaxCases, "Case Submitted Maximum", details.get("Case Submitted Maximum"));
		sleep(1000);
		enterTextAndSelectOption(txtCountry, "Country", details.get("Country"));		
	}


	public void searchFunctionalityInCreateGroupPage() {
		clickOnCreateNewGroup();
		HashMap<String, String> details = new HashMap<String, String>();
		details.put("Name", "Auto_"+generateRandomString(5, "alpha"));
		details.put("Description", "Created by automation script");
		Values.groupDetails = details;
		enterDetailsInCreateGroup(details);
		int cases =0;
		try {
			cases = Integer.parseInt(Values.adminDentistLiveRecord.get("Number Cases"));
		}catch(Exception e) {
			//do nothing
		}
		String onBoardingDate = Values.adminDentistLiveRecord.get("Onboarding Date");
		String formatOnBoardingDate = dateFormat("dd/mm/yy","yyyy-mm-dd",onBoardingDate);
		String onBoardingDateFrom = addDate(formatOnBoardingDate,-2);
		onBoardingDateFrom = dateFormat("yyyy-mm-dd","dd/mm/yyyy",onBoardingDateFrom);
		String onBoardingDateTo = addDate(formatOnBoardingDate,2);
		onBoardingDateTo = dateFormat("yyyy-mm-dd","dd/mm/yyyy",onBoardingDateTo);
		HashMap<String, String> searchDetails = new HashMap<String, String>();
		searchDetails.put("Dentist Name", Values.adminDentistLiveRecord.get("Dentist Name"));
		searchDetails.put("Practice", Values.adminDentistLiveRecord.get("Practice"));
		searchDetails.put("OnBoarding Date From", onBoardingDateFrom);
		searchDetails.put("OnBoarding Date To", onBoardingDateTo);
		searchDetails.put("Case Submitted Minimum", String.valueOf(cases));
		searchDetails.put("Case Submitted Maximum", String.valueOf(cases+10));
		//searchDetails.put("Country", "India");
		enterDetailsInSearchDentistCreateGroup(searchDetails);
		clickOn(btnSearchUsers, "Search Users");
		sleep(2000);

		waitForElement(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]//input"));

		List<WebElement> results = browser.findElements(By.xpath("//div[@role='dialog']//table/tbody/tr/td[1]//input"));
		if(results.size()>0) {
			passed("Search results are displayed as expected");
			validateTableRecordsInColumn(dialogTable, Values.adminDentistLiveRecord.get("Dentist Name"),2 , 1);
			validateTableRecordsInColumn(dialogTable, Values.adminDentistLiveRecord.get("Practice"),5 , 1);
		}else {
			fail("Search results are not displayed");
		}
		takeScreenshot();
	}

	public void validateDuplicateGroups() {
		waitForElement(By.xpath("//table/tbody/tr/td[1]/a"));
		List<WebElement> wesGroupName = browser.findElements(By.xpath("//table/tbody/tr/td[1]/a"));
		String strGroupName = wesGroupName.get(0).getText();
		clickOnCreateNewGroup();
		HashMap<String, String> details = new HashMap<String, String>();
		details.put("Name", strGroupName);
		details.put("Description", "Created by automation script");
		details.put("Total Dentist", "3");
		details.put("Status", "Active");
		Values.groupDetails = details;
		enterDetailsInCreateGroup(details);

		clickOn(btnCreateGroup, "Create Group Button");
		sleep(2000);
		waitForElement(By.xpath("//div[@class='ant-notification-notice-message']"));
		if (isElementPresent(By.xpath("//div[@class='ant-notification-notice-message']"))) {
			passed("Toaster Message is displayed as expected");
			String actHeader = getText(By.xpath("//div[@class='ant-notification-notice-message']"));
			if(actHeader.contains(Constants.ADMIN_GROUP_CREATE_DUPLICATE_ERROR)) {
				passed("Toaster Error message header is displayed as expected "+actHeader);
			}else {
				fail("Toaster Error message header is not as expected , Actual is "+actHeader +", But expected is "+Constants.ADMIN_GROUP_CREATE_DUPLICATE_ERROR);
			}
		}else {
			fail("Toaster Message is not displayed");
		}	
		takeScreenshot();

	}

}

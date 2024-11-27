package pages.admin;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Values;

public class AdminDashboardPage extends Page {
	
	@FindBy(id = "sidebar-menu-operations")
	private WebElement lnkOperations;

	@FindBy(id = "sidebar-menu-release-notes")
	private WebElement lnkReleaseNotes;

	@FindBy(xpath = "//span[text()='Release notes']")
	private WebElement weReleaseNotes;

	@FindBy(xpath = "//span[text()='Education Content']")
	private WebElement weEducationContent;
	
	@FindBy(id = "sidebar-menu-practices")
	private WebElement lnkPractices;
	
	@FindBy(id = "sidebar-menu-dashboard")
	private WebElement lnkDashboard;

	@FindBy(xpath = "//span[text()='Practice']")
	private WebElement wePractices;

	@FindBy(xpath = "//*[@class='ant-pagination-total-text']")
	private WebElement weTotalRecordCount;
	
	@FindBy(id = "sidebar-menu-dentists")
	private WebElement lnkDentists;

	@FindBy(id = "sidebar-menu-bills")
	private WebElement lnkOutBoudBills;
	
	@FindBy(id = "sidebar-menu-payments")
	private WebElement lnkPayments;
	
	@FindBy(id = "sidebar-menu-discounts")
	private WebElement lnkDiscounts;
	
	@FindBy(xpath = "(//a[text()='Dentists'])[2]")
	private WebElement weDentists;

	@FindBy(xpath = "//span[text()='Outbound Bills']")
	private WebElement weOutboundBills;
	
	@FindBy(xpath = "//span[text()='Payments']")
	private WebElement wePayments;
	
	@FindBy(xpath = "//span[text()='Discounts']")
	private WebElement weDiscounts;
	
	@FindBy(id = "sidebar-menu-third-parties")
	private WebElement lnkThirdParties;

	@FindBy(id = "sidebar-menu-manufacturer")
	private WebElement lnkManufacturers;

	@FindBy(id = "sidebar-menu-designer")
	private WebElement lnkDesigners;
	
	@FindBy(id = "sidebar-menu-education-content")
	private WebElement lnkEducation;

	@FindBy(id = "sidebar-menu-specialist")
	private WebElement lnkSpecialists;
	
	@FindBy(id = "sidebar-menu-groups")
	private WebElement lnkGroups;

	@FindBy(xpath = "//span[text()='Groups']")
	private WebElement weGroupManagement;
	
	@FindBy(xpath = "//span[text()='Operations']")
	private WebElement weOperations;
	
	@FindBy(xpath = "//div[contains(@class,'profile ')]//img")
	private WebElement btnProfile;
	
	@FindBy(id = "logout-button")
	private WebElement lnkLogout;
	
	@FindBy(id = "basic_email")
	private WebElement txtEmail;
	
	@FindBy(id = "aboutToBeDueCount")
	private WebElement weaboutToBeDueCount;
	
	@FindBy(id = "notAcceptedCount")
	private WebElement wenotAcceptedCount;
	
	@FindBy(id = "notAssignedCount")
	private WebElement wenotAssignedCount;
	
	@FindBy(id = "notSuitableReviewPendingCount")
	private WebElement wenotSuitableReviewPendingCount;
	
	@FindBy(id = "overdueCount")
	private WebElement weoverdueCount;
	
	@FindBy(id = "pendingLiteSubmissionReviewCount")
	private WebElement wependingLiteSubmissionReviewCount;
	
	@FindBy(id = "pendingProSubmissionReviewCount")
	private WebElement wependingProSubmissionReviewCount;
	
	@FindBy(id = "pendingToManufacturerCount")
	private WebElement wependingToManufacturerCount;
	
	@FindBy(id = "priorityCount")
	private WebElement wepriorityCount;
	
	@FindBy(id = "stlRequiredCount")
	private WebElement westlRequiredCount;
	
	
	
	
	public AdminDashboardPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public AdminReleaseNotesPage navigateToReleaseNotes() {
		clickOn(lnkReleaseNotes, "Release Notes Link");
		waitForElement(weReleaseNotes);
		if(isElementPresent(weReleaseNotes)) {
			passed("Release Notes Page is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Release Notes Page is not displayed");
		}
		return new AdminReleaseNotesPage(browser, data);
	}

	public AdminPracticesPage navigateToPracticesPage() {
		clickOn(lnkPractices, "Practices Link");
		waitForElement(wePractices);
		if(isElementPresent(wePractices)) {
			passed("Navigated to Practices Page successfully, Practices section is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Unable to Navigate Practices Page ,Practices section is not displayed");
		}
		return new AdminPracticesPage(browser, data);
	}

	public AdminDentistsPage navigateToDentistsPage() {
		clickOn(lnkDentists, "Dentists Link");
		waitForElement(weDentists);
		if(isElementPresent(weDentists)) {
			passed("Navigated to Dentists Page successfully, Dentists section is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Unable to Navigate Dentists Page ,Dentists section is not displayed");
		}
		return new AdminDentistsPage(browser, data);
	}

	public AdminThirdPartiesPage navigateToThirdPartiesPage(String strThirdPartyLink,String breadcumb) {
		if(isElementPresent(By.xpath("//li[@role='menuitem']/span[text()='"+strThirdPartyLink+"']"))) {
			clickOn(By.xpath("//li[@role='menuitem']/span[text()='"+strThirdPartyLink+"']"), strThirdPartyLink+" Link");
			waitForElement(By.xpath("//ol/li/a[text()='"+breadcumb+"']"));			
			if(isElementPresent(By.xpath("//ol/li/a[text()='"+breadcumb+"']"))) {
				passed("Navigated to "+strThirdPartyLink+" Page successfully, "+strThirdPartyLink+" section is displayed");
				takeScreenshot();
			}else {
				takeScreenshot();
				failAssert("Unable to Navigate "+strThirdPartyLink+" Page ,"+strThirdPartyLink+" section is not displayed");
			}
		}else {
			clickOn(lnkThirdParties, "ThirdParties Link");
			waitForElement(By.xpath("//li[@role='menuitem']/span[text()='"+strThirdPartyLink+"']"));
			sleep(2000);
			if(isElementPresent(By.xpath("//li[@role='menuitem']/span[text()='"+strThirdPartyLink+"']"))) {
				clickOn(By.xpath("//li[@role='menuitem']/span[text()='"+strThirdPartyLink+"']"), strThirdPartyLink+" Link");
				waitForElement(By.xpath("//ol/li/a[text()='"+breadcumb+"']"));			
				if(isElementPresent(By.xpath("//ol/li/a[text()='"+breadcumb+"']"))) {
					passed("Navigated to "+strThirdPartyLink+" Page successfully, "+strThirdPartyLink+" section is displayed");
					takeScreenshot();
				}else {
					takeScreenshot();
					failAssert("Unable to Navigate "+strThirdPartyLink+" Page ,"+strThirdPartyLink+" section is not displayed");
				}
			}else {
				takeScreenshot();
				failAssert("Unable to Navigate "+strThirdPartyLink+" Page ,Link is not displayed");
			}
		}
		return new AdminThirdPartiesPage(browser, data);
	}
	
	public AdminGroupManagementPage navigateToGroupsPage() {
		clickOn(lnkGroups, "Groups Link");
		waitForElement(weGroupManagement);
		if(isElementPresent(weGroupManagement)) {
			passed("Navigated to Group Management Page successfully, GroupManagement section is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Unable to Navigate GroupManagement Page ,GroupManagement section is not displayed");
		}
		return new AdminGroupManagementPage(browser, data);
	}
	
	
	public AdminOperationsPage navigateToOperationsPage() {
		clickOn(lnkOperations, "Operations Link");
		waitForElement(weOperations);
		if(isElementPresent(weOperations)) {
			passed("Navigated to weOperations Page successfully, weOperations section is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Unable to Navigate weOperations Page ,weOperations section is not displayed");
		}
		return new AdminOperationsPage(browser, data);
	}
	
	public AdminEducationContentPage navigateToEducationContent() {
		clickOn(lnkEducation, "Education Content Link");
		waitForElement(weEducationContent);
		if(isElementPresent(weEducationContent)) {
			passed("Education Content Page is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Education Content Page is not displayed");
		}
		return new AdminEducationContentPage(browser, data);
	}
	
	public void logoutApplication() {
		clickOn(btnProfile, "Profile Button");
		clickOn(lnkLogout, "Logout Button");
		waitForElement(txtEmail);
		if(isElementPresent(txtEmail)) {
			passed("Logged out of the application successfully");
		}else {
			fail("Login page is not displayed as expected");
		}
	}
	
	public AdminOutboundBillsPage navigateToOutBoundBills() {
		clickOn(lnkOutBoudBills, "OutBoud Bills Link");
		waitForElement(weOutboundBills);
		if(isElementPresent(weOutboundBills)) {
			passed("Outbound Bills Page is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Outbound Bills Page is not displayed");
		}
		return new AdminOutboundBillsPage(browser, data);
	}
	
	public AdminPaymentsPage navigateToPayments() {
		clickOn(lnkPayments, "Payments Link");
		waitForElement(wePayments);
		if(isElementPresent(wePayments)) {
			passed("Payments Page is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Payments Page is not displayed");
		}
		return new AdminPaymentsPage(browser, data);
	}
	
	public AdminDiscountsPage navigateToDiscounts() {
		clickOn(lnkDiscounts, "Discounts Link");
		waitForElement(weDiscounts);
		if(isElementPresent(weDiscounts)) {
			passed("Discounts Page is displayed");
			takeScreenshot();
		}else {
			takeScreenshot();
			failAssert("Discounts Page is not displayed");
		}
		return new AdminDiscountsPage(browser, data);
	}
	
	public void validateDashboardNumbers() {
		List<WebElement> listOfAllWebElements = new ArrayList<WebElement>();
		listOfAllWebElements.add(weaboutToBeDueCount);
		listOfAllWebElements.add(wenotAcceptedCount);
		listOfAllWebElements.add(wenotSuitableReviewPendingCount);
		listOfAllWebElements.add(weoverdueCount);
		listOfAllWebElements.add(wependingLiteSubmissionReviewCount);
		listOfAllWebElements.add(wependingProSubmissionReviewCount);
		listOfAllWebElements.add(wependingToManufacturerCount);
		listOfAllWebElements.add(wepriorityCount);
		listOfAllWebElements.add(westlRequiredCount);
		for(WebElement element : listOfAllWebElements) {
			clickOn(lnkDashboard, "Dashboard Link");
			String strActual = getText(element);
			String[] str = strActual.split("\\n");
			if(!str[0].equals("0")) {
				info("Count of the "+str[1] + " is "+str[0]);
				takeScreenshot();
				clickOn(element, str[1]+ " Link");
				waitForElement(weOperations);
				if(isElementPresent(weOperations)) {
					passed("navigated to Opearions page after clicking on link in Dashboard page");
					String text = getText(weTotalRecordCount);
					String[] rec = text.split(" ");
					String totalRecordCount = rec[2];
					if(totalRecordCount.equals(str[0])) {
						passed("Total record Count displayed in Operaitons page is matching with dashboard count");
					}else {
						fail("Total record Count displayed in Operaitons page is not matching with dashboard count, Dasboard Count is "+str[0] + ", But Operations count is "+totalRecordCount);
					}
					takeScreenshot();
				}else {
					fail("Did not navigate to OPearions page after clickin on link in Dashboard page");
				}
				System.out.println(strActual);
			}else {
				info("Count of the "+str[1] + " is "+str[0]);
			}
		}
	}
	
	public void storeAllCountsInDashboardPage() {
		Values.aboutToBeDueCount = Integer.parseInt(getText(weaboutToBeDueCount).split("\\n")[0]);
		Values.notAcceptedCount=  Integer.parseInt(getText(wenotAcceptedCount).split("\\n")[0]);
		Values.notSuitableReviewPendingCount = Integer.parseInt(getText(wenotSuitableReviewPendingCount).split("\\n")[0]);
		Values.overdueCount = Integer.parseInt(getText(weoverdueCount).split("\\n")[0]);
		Values.pendingLiteSubmissionReviewCount = Integer.parseInt(getText(wependingLiteSubmissionReviewCount).split("\\n")[0]);
		Values.pendingProSubmissionReviewCount = Integer.parseInt(getText(wependingProSubmissionReviewCount).split("\\n")[0]);
		Values.pendingToManufacturerCount =  Integer.parseInt(getText(wependingToManufacturerCount).split("\\n")[0]);
		Values.priorityCount = Integer.parseInt(getText(wepriorityCount).split("\\n")[0]);
		Values.stlRequiredCount =  Integer.parseInt(getText(westlRequiredCount).split("\\n")[0]);
		info("Stored all the Dashboard Values in the variable for future validation");
		takeScreenshot();
	}
	
	public void validateCasesUnAssigned() {
		int currValue = Integer.parseInt(getText(weaboutToBeDueCount).split("\\n")[0]);
		String section= getText(weaboutToBeDueCount).split("\\n")[1];
		if((Values.aboutToBeDueCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}
	
	public void validateCasesNotSuitable() {
		int currValue = Integer.parseInt(getText(wenotSuitableReviewPendingCount).split("\\n")[0]);
		String section= getText(wenotSuitableReviewPendingCount).split("\\n")[1];
		if((Values.notSuitableReviewPendingCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}
	
	public void validateCasesReadyForReviewSolo() {
		int currValue = Integer.parseInt(getText(wependingLiteSubmissionReviewCount).split("\\n")[0]);
		String section= getText(wependingLiteSubmissionReviewCount).split("\\n")[1];
		if((Values.pendingLiteSubmissionReviewCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}
	
	public void validateCasesReadyForReviewDuo() {
		int currValue = Integer.parseInt(getText(wependingProSubmissionReviewCount).split("\\n")[0]);
		String section= getText(wependingProSubmissionReviewCount).split("\\n")[1];
		if((Values.pendingProSubmissionReviewCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}
	
	public void validateCasesReadyToSendManufacturer() {
		int currValue = Integer.parseInt(getText(wependingToManufacturerCount).split("\\n")[0]);
		String section= getText(wependingToManufacturerCount).split("\\n")[1];
		if((Values.pendingToManufacturerCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}
	
	public void validateCasesPriority() {
		int currValue = Integer.parseInt(getText(wepriorityCount).split("\\n")[0]);
		String section= getText(wepriorityCount).split("\\n")[1];
		if((Values.priorityCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}
	
	public void validateCasesNeedSTLFiles() {
		int currValue = Integer.parseInt(getText(westlRequiredCount).split("\\n")[0]);
		String section= getText(westlRequiredCount).split("\\n")[1];
		if((Values.stlRequiredCount+1)==currValue) {
			passed("Count is increased by 1 for section "+section);
		}else {
			fail("Count is not increased by 1 for section "+section);
		}
	}

}

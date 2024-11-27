package pages.admin;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import Utilities.Data;
import Utilities.Page;
import Utilities.constans.Constants;
import Utilities.constans.Values;

public class AdminEducationContentPage extends Page {

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement btnNextPage;

	@FindBy(xpath = "//li[@title='Previous Page']")
	private WebElement btnPreviousPage;

	@FindBy(xpath = "//li[@class='ant-pagination-options']//span[2]")
	private WebElement wePagination;

	@FindBy(id = "edu-content_refresh-button")
	private WebElement btnReload;

	@FindBy(id = "edu-content-filter_-search-input")
	private WebElement txtSearch;

	@FindBy(id = "edu-content-filter_-sort-by")
	private WebElement lstSortBy;

	@FindBy(id = "edu-content-filter_-tags")
	private WebElement lstTags;

	@FindBy(id = "edu-content-filter_-search-button")
	private WebElement btnSearch;

	@FindBy(id = "edu-content-filter_-clear-button")
	private WebElement btnClear;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	private WebElement weSuccessMessage;

	@FindBy(xpath = "//div[@class='ant-notification-notice-message']")
	private WebElement weSuccessInfoMessage;


	@FindBy(id = "edu-content_create-new-content-button")
	private WebElement btnCreateNewContent;

	@FindBy(id = "title")
	private WebElement txtTitle;

	@FindBy(id = "description")
	private WebElement txtDescription;

	@FindBy(id = "coverTitle")
	private WebElement txtCoverTitle;

	@FindBy(id = "coverImage")
	private WebElement txtCoverImage;

	@FindBy(id = "coverSubTitle")
	private WebElement txtCoverSubTitle;

	//@FindBy(id = "coverTextColor_list")
	@FindBy(id = "coverTextColor")
	private WebElement lstCoverTextColor;

	//@FindBy(id = "tags_list")
	@FindBy(id = "tags")
	private WebElement lstCreateTags;

	@FindBy(id = "contentType")
	private WebElement rbContentType;

	@FindBy(id = "difficultyLevel")
	private WebElement rbDifficultyLevel;

	@FindBy(id = "estimatedReadingTime")
	private WebElement txtEstimatedReadingTime;

	@FindBy(id = "contentUrl")
	private WebElement txtContentUrl;

	@FindBy(id = "cpdPoint")
	private WebElement txtCPDpoint;	

	@FindBy(xpath = "//button/span[text()='Add question']")
	private WebElement btnAddQuestion;

	@FindBy(xpath = "//span[contains(@class,'ant-tag')]")
	private List<WebElement> weEduTags;

	@FindBy(xpath = "//h2[contains(@class,'coverTitle')]")
	private List<WebElement> weEducationTitle;
	
	@FindBy(xpath = "//div[@class='ant-card-body']//div[contains(@class,'title')]")
	private List<WebElement> weEducationTitleName;

	@FindBy(xpath = "//button/span[text()='Proceed']")
	private WebElement btnProceed;

	@FindBy(id = "edu-content-item_preview-button")
	private WebElement btnPreview;	

	@FindBy(id = "edu-content-item_edit-button")
	private WebElement btnEdit;	

	@FindBy(id = "edu-content-item_trigger-button")
	private WebElement btnTrigger;	

	@FindBy(id = "edu-content-item_reload-content-button")
	private WebElement btnReloadEduContent;	

	@FindBy(id = "dentistName")
	private WebElement txtDentistName;	

	@FindBy(id = "q")
	private WebElement txtGroupName;	
	
	@FindBy(xpath = "//button/span[text()='Reload Notion Content']")
	private WebElement btnReloadNotionContent;	

	@FindBy(xpath = "//div[@data-node-key='groups']")
	private WebElement tabGroups;	
	
	@FindBy(xpath = "//button/span[text()='Search']")
	private WebElement btnSearchDentist;	
	
	@FindBy(xpath = "//div[contains(@id,'panel-groups')]//button/span[text()='Search']")
	private WebElement btnSearchGroup;	
	
	@FindBy(xpath = "//div[contains(@id,'panel-groups')]//button/span[text()='Trigger content']")
	private WebElement btnTriggerGroups;	
	
	@FindBy(xpath = "//button/span[text()='Trigger content']")
	private WebElement btnTriggercontent;	

	@FindBy(xpath = "//button/span[text()='Yes - Send']")
	private WebElement btnYesSend;	


	public AdminEducationContentPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}

	public void validateEducationPageUI() {
		waitForElement(btnCreateNewContent);
		sleep(3000);
		isElementDisplayed(btnReload, "Reload Button");
		isElementDisplayed(btnSearch, "Search Button");
		isElementDisplayed(txtSearch, "Search TextBox");
		isElementDisplayed(btnNextPage, "Next Page Button");
		isElementDisplayed(btnPreviousPage, "Previous Page Button");
		isElementDisplayed(lstSortBy, "Sort By dropdown");
		isElementDisplayed(lstTags, "Tags dropdown");
		isElementDisplayed(btnClear, "Clear Button");
		isElementDisplayed(wePagination, "Records per page dropdown");
		String strPagination = wePagination.getText();
		if(strPagination.equals("10 / page")) {
			passed("By default 10 / page records are displayed ");
		}else {
			fail("By default 10 / page records are not displayed, Actual is "+strPagination);
		}
		takeScreenshot();
	}

	public void validateSearchFunctionality() {
		int sizeBefore = weEducationTitle.size();
		String tag = weEduTags.get(0).getText();
		String title = weEducationTitleName.get(0).getText();
		selectOptionDropDown(lstTags, "Search By Tag", tag);
		enterText(txtSearch, "Search textbox", title);
		clickOn(btnSearch, "Search Button");
		sleep(3000);
		if(weEducationTitleName.size()>0) {
			passed("Search Results are displayed as expected");
		}else {
			fail("Search Results are not displayed ");
		}
		takeScreenshot();
		clickOn(btnClear, "Clear Button");
		sleep(3000);
		int sizeAfter = weEducationTitleName.size();
		if(sizeAfter==sizeBefore) {
			passed("Search Results are Reset after clicking on Clear button");
		}else {
			fail("Search Results are not Reset after clicking on Clear button");
		}
		takeScreenshot();
	}
	static String tagname = "";
	public void createNewContent() {
		tagname = weEduTags.get(0).getText();
		clickOn(btnCreateNewContent, "Create new Content Button");
		waitForElement(txtTitle);
		enterDetailsEducationContent("General","General");
		clickOn(btnProceed, "Proceed button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Content created successfully";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}
	
	public void createCaseStudy() {
		tagname = weEduTags.get(0).getText();
		clickOn(btnCreateNewContent, "Create new Content Button");
		waitForElement(txtTitle);
		enterDetailsEducationContent("Case study","Intermediate");
		clickOn(btnProceed, "Proceed button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Content created successfully";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}
	
	public void createCourseMaterial() {
		tagname = weEduTags.get(0).getText();
		clickOn(btnCreateNewContent, "Create new Content Button");
		waitForElement(txtTitle);
		enterDetailsEducationContent("Course material","Advanced");
		clickOn(btnProceed, "Proceed button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Content created successfully";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}

	public void enterDetailsEducationContent(String type,String level) {
		Values.eduCoverTitle = "Auto Cover "+generateRandomString(6, "alpha");
		Values.cpdPoint = "5";
		enterText(txtTitle, "Title Textbox", "Auto Test "+generateRandomString(6, "alpha"));
		enterText(txtDescription, "Description Textbox", "Description of the Education content "+generateRandomString(6, "alpha"));
		enterText(txtCoverTitle, "CoverTitle Textbox", Values.eduCoverTitle);
		uploadImage(txtCoverImage,  ".\\files\\edu\\edu"+randInt(1, 5)+".jpg");
		enterText(txtCoverSubTitle, "Cover Sub Title", "Sub Title of education content");
		selectFirstOptionFromDropDown(lstCoverTextColor, "Cover Text Color");
		selectMultiOptionDropDown(lstCreateTags, "Tags List",tagname);
		selectRadioButton(rbContentType, "Content Type",type);
		selectRadioButton(rbDifficultyLevel, "Difficulty Level", level);
		enterText(txtEstimatedReadingTime, "Estimated Reading time", String.valueOf(randInt(30, 90)));
		enterText(txtContentUrl, "Content URL", "https://www.notion.so/32co/49e8787e5bd14b1cb57ae907ab14c8f3?v=b082c418847c4284b0749626c0f3843e&p=16cef236ee874b888bd86a929a5e5631");
		info("-----Add Multi Choice Questions-----");
		for(int i=0;i<3;i++) {
			clickOn(btnAddQuestion, "Add Question Button");
			waitForElement(By.id("questions_"+i+"_title"));
			WebElement question = browser.findElement(By.id("questions_"+i+"_title"));
			WebElement option1 = browser.findElement(By.id("questions_"+i+"_options_0_title"));
			WebElement option2 = browser.findElement(By.id("questions_"+i+"_options_1_title"));
			WebElement option3 = browser.findElement(By.id("questions_"+i+"_options_2_title"));
			WebElement option4 = browser.findElement(By.id("questions_"+i+"_options_3_title"));
			enterText(question, "Question "+(i+1), "This is the Question number "+(i+1));
			enterText(option1, "Option 1", String.valueOf(randInt(1, 100)));
			enterText(option2, "Option 2", String.valueOf(randInt(1, 100)));
			enterText(option3, "Option 3", String.valueOf(randInt(1, 100)));
			enterText(option4, "Option 4", String.valueOf(randInt(1, 100)));
			WebElement answer = browser.findElement(By.id("questions_"+i+"_answer"));
			selectOptionDropDown(answer, "Answer", "B");
		}
		enterText(txtCPDpoint, "CPD Point", Values.cpdPoint);
	}

	public void validateEdit() {
		selectButtonfromEducationContent(Values.eduCoverTitle, "edit");
		clearText(txtDescription);
		enterText(txtDescription, "Description Textbox", "Updated Description of the Education content "+generateRandomString(6, "alpha"));
		clickOn(btnProceed, "Proceed Button");
		System.out.println();
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Content Updated successfully";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}

	public void validateReloadNotionContent() {
		selectButtonfromEducationContent(Values.eduCoverTitle, "reload");
	}

	public void validatePreviewButton() {
		selectButtonfromEducationContent(Values.eduCoverTitle, "preview");
	}

	public void validateTriggerAllDentist() {
		sleep(3000);
		selectButtonfromEducationContent(Values.eduCoverTitle, "trigger");
		sleep(3000);
		List<WebElement> wesRadio = browser.findElements(By.xpath("//input[@class='ant-radio-input']/.."));
		clickOn(wesRadio.get(0), "All dentist Radio Button");
		clickOn(btnTriggercontent, "Trigger Content Button");
		waitForElement(By.xpath("//div[text()=\"You're trying to send this to over 20 dentists. Is this on purpose?\"]"));
		waitForElement(btnYesSend);
		sleep(2000);
		Values.currentDateTime = new Date();
		if(isElementPresent(By.xpath("//div[text()=\"You're trying to send this to over 20 dentists. Is this on purpose?\"]"))) {
			passed("Confirmation pop up is displayed as expected");
			takeScreenshot();
			clickOn(btnYesSend, "Yes Send Button");
		}
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Trigger content to dentists successfully.";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}

		//input[@class='ant-radio-input']/..
	}

	public void validateTriggerOneDentist() {
		selectButtonfromEducationContent(Values.eduCoverTitle, "trigger");
		sleep(3000);
		waitForElement(txtDentistName);
		List<WebElement> wesRadio = browser.findElements(By.xpath("//input[@class='ant-radio-input']/.."));
		clickOn(wesRadio.get(1), "Few dentist Radio Button");
		
		enterText(txtDentistName, "Dentist Name", Values.frameworkProperty.getProperty("dentist.name"));
		clickOn(btnSearchDentist, "Search Button");
		sleep(3000);
		waitForElement(By.xpath("//table/tbody/tr/td[1]//input"));
		List<WebElement> wesDentists = browser.findElements(By.xpath("//table/tbody/tr/td[1]//input/.."));
		if(wesDentists.size()>0) {
			clickOn(wesDentists.get(0), "Dentist ");
		}else {
			fail("Unable to trigger content, No Dentists displayed");
		}
		Values.currentDateTime = new Date();
		clickOn(btnTriggercontent, "Trigger Content Button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Trigger content to dentists successfully.";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}
	
	public void validateTriggerMultipleGroup() {
		selectButtonfromEducationContent(Values.eduCoverTitle, "trigger");
		sleep(3000);
		waitForElement(tabGroups);
		clickOn(tabGroups, "To Groups Tab");
		waitForElement(txtGroupName);

		enterText(txtGroupName, "Group Name", "Automation");
		clickOn(btnSearchGroup, "Search Button");
		sleep(3000);
		waitForElement(By.xpath("//table/tbody/tr/td[1]//input"));
		List<WebElement> wesGroups = browser.findElements(By.xpath("//div[contains(@id,'panel-groups')]//table/tbody/tr/td[1]//input/.."));
		if(wesGroups.size()>1) {
			clickOn(wesGroups.get(0), "Groups ");
			clickOn(wesGroups.get(1), "Groups ");
		}else if(wesGroups.size()==1) {
			clickOn(wesGroups.get(0), "Groups ");			
		}else {
			fail("Unable to trigger content, No Groups displayed");
		}
		Values.currentDateTime = new Date();
		takeScreenshot();
		clickOn(btnTriggerGroups, "Trigger Content Button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Trigger content to dentists successfully.";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}
	
	public void validateTriggerOneGroup() {
		selectButtonfromEducationContent(Values.eduCoverTitle, "trigger");
		sleep(3000);
		waitForElement(tabGroups);
		clickOn(tabGroups, "To Groups Tab");
		waitForElement(txtGroupName);
		
		enterText(txtGroupName, "Group Name", "Automation");
		clickOn(btnSearchGroup, "Search Button");
		sleep(3000);
		waitForElement(By.xpath("//table/tbody/tr/td[1]//input"));
		List<WebElement> wesGroups = browser.findElements(By.xpath("//div[contains(@id,'panel-groups')]//table/tbody/tr/td[1]//input/.."));
		if(wesGroups.size()>0) {
			clickOn(wesGroups.get(0), "Groups ");
		}else {
			fail("Unable to trigger content, No Groups displayed");
		}
		Values.currentDateTime = new Date();
		takeScreenshot();
		clickOn(btnTriggerGroups, "Trigger Content Button");
		waitForElement(weSuccessMessage);
		if(isElementPresent(weSuccessMessage)) {
			passed("Success message is displayed as expected");
			String strSuccMsg = weSuccessMessage.getText();
			String expMessage = "Trigger content to dentists successfully.";
			if(strSuccMsg.contains(expMessage)) {
				passed("Success message is as expected "+expMessage);
				takeScreenshot();
			}else {
				fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
			}
		}else {
			fail("Success message is not displayed ");
		}
	}

	public void selectButtonfromEducationContent(String eduName,String btnName) {
		for(int i=0;i<weEducationTitle.size();i++) {
			String actTitle = weEducationTitle.get(i).getText();
			if(actTitle.equalsIgnoreCase(eduName)) {
				moveToElement1(weEducationTitle.get(i));
				switch(btnName.toLowerCase()) {
				case "preview":
					clickOn(btnPreview, "PreviewButton");
					waitForElement(btnReloadNotionContent);
					if(isElementPresent(btnReloadNotionContent)) {
						passed("Preview page is displayed as expected");
						clickOn(btnReloadNotionContent, "Reload Notion Content Button");
						waitForElement(btnReloadNotionContent);
						if(isElementPresent(btnReloadNotionContent)) {
							passed("Preview page is displayed as expected");
						}else {
							fail("Preview page is not displayed");
						}
					}else {
						fail("Preview page is not displayed");
					}
					takeScreenshot();
					break;

				case "edit":
					clickOn(btnEdit, "Edit Button");
					waitForElement(txtTitle);
					if(isElementPresent(txtTitle)) {
						passed("Edit page is displayed ");
					}else {
						fail("Edit page is not displayed ");
					}
					takeScreenshot();
					break;

				case "trigger":
					clickOn(btnTrigger, "Trigger Button");
					waitForElement(txtDentistName);
					waitForElement(By.xpath("//input[@class='ant-radio-input']/.."));
					if(isElementPresent(txtDentistName)) {
						passed("Trigger page pop up is displayed");
					}else {
						fail("Trigger page pop up is not displayed");
					}
					takeScreenshot();
					break;

				case "reload":
					clickOn(btnReload, "Reload Button");
					waitForElement(weSuccessInfoMessage);
					if(isElementPresent(weSuccessInfoMessage)) {
						passed("Success message is displayed as expected");
						String strSuccMsg = weSuccessInfoMessage.getText();
						String expMessage = "Reload content successfully";
						if(strSuccMsg.contains(expMessage)) {
							passed("Success message is as expected "+expMessage);
							takeScreenshot();
						}else {
							fail("Success message is not valid , Actual is " +strSuccMsg + ", But expected is "+expMessage);
						}
					}else {
						fail("Success message is not displayed ");
					}
					break;
				}
				break;
			}
		}
	}

}

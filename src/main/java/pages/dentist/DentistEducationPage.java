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

public class DentistEducationPage extends Page{


	@FindBy(xpath = "//input[@placeholder='Search']")
	private WebElement txtSearch;

	@FindBy(xpath = "//div[@class='notion-frame']")
	private WebElement weEduContentPage;
	
	@FindBy(xpath = "//div[text()='How useful did you find this content?']/following-sibling::div/div/div")
	private List<WebElement> wesRating;
	
	@FindBy(xpath = "//button/span[text()='Begin the quiz']")
	private WebElement btnBeginthequiz;
	
	@FindBy(xpath = "//button/span[text()='Next']")
	private WebElement btnNext;
	
	@FindBy(xpath = "//button/span[text()='Finish']")
	private WebElement btnFinish;
	
	@FindBy(xpath = "//div[@role='dialog']//button[@aria-label='Close']")
	private WebElement btnCloseDialog;
	
	
	

	public DentistEducationPage(WebDriver browser, Data data) {
		super(browser, data);
	}

	@Override
	protected boolean isValidPage() {
		return false;
	}

	@Override
	protected void waitForPageLoad() {

	}
	
	public void viewEducationContent() {
		if(isElementPresent(By.xpath("//*[@data-node-key='All']"))) {
			clickOn(By.xpath("//*[@data-node-key='All']"), "All Section");
			sleep(1000);
			enterText(txtSearch, "Search Textbox", Values.eduCoverTitle);
			sleep(1000);
			List<WebElement> weTitle = browser.findElements(By.xpath("//h2[text()='"+Values.eduCoverTitle+"']"));
			if(weTitle.size()>0) {
				passed("Education content Triggered from admin is displayed dentist Education page");
				clickOn(weTitle.get(0), Values.eduCoverTitle + " Education Content");
				waitForElement(weEduContentPage);
				sleep(1000);
				boolean bln = isElementDisplayed(weEduContentPage, "Education Content Page");
				if(!bln) {
					takeScreenshot();
					failAssert("Unable to open the Education Content Page");;
				}				
			}else {
				fail("Education content Triggered from admin is not displayed dentist Education page");
			}
			
		}else {
			fail("Education Content Page is not displayed");
		}
		takeScreenshot();
	}
	
	public void validateEducationContent() {
		if(wesRating.size()>0) {
			passed("Rate Education Content is displayed as expected");
			moveToElement(wesRating.get(0));
			clickOn(wesRating.get(randInt(1, 4)), "Rate the Content Number");
			clickOn(btnBeginthequiz, "Begin the Quiz button");
			waitForElement(By.xpath("//div[@id='activity-section']//span[text()='A']"));
			if(isElementPresent(By.xpath("//div[@id='activity-section']//span[text()='A']"))) {
				passed("Question answer page is displayed ");
				takeScreenshot();
				for(int i=0;i<3;i++) {
					WebElement weA = browser.findElement(By.xpath("//div[@id='activity-section']//span[text()='A']"));
					WebElement weB = browser.findElement(By.xpath("//div[@id='activity-section']//span[text()='B']"));
					clickOn(weA, "Answer A");
					if(isElementPresent(By.xpath("//div[@id='activity-section']//span[text()='Wrong']"))) {
						passed("Wrong answer message is displayed for the option A");
					}else {
						fail("Wrong answer message is not displayed for the option A");
					}
					
					clickOn(weB, "Answer B");
					if(isElementPresent(By.xpath("//div[@id='activity-section']//span[text()='Correct']"))) {
						passed("Correct answer message is displayed for the option B");
					}else {
						fail("Correct answer message is not displayed for the option B");
					}
					takeScreenshot();
					if(i==2) {
						clickOn(btnFinish, "Finish Button");
					}else {
						moveToElement(btnNext);
						clickOn(btnNext, "Next Button");	
					}					
				}
				waitForElement(btnCloseDialog,20);
				sleep(2000);
				String strCPDpoint = getText(By.xpath("//div[@role='dialog']//span[contains(@class,'avatar-string')]"));
				double actcpdPoint = Double.parseDouble(strCPDpoint);
				double expcpdPoint = Double.parseDouble(Values.cpdPoint);
				if(actcpdPoint == expcpdPoint) {
					passed("Dentist earned the CPD points as expected "+Values.cpdPoint);
				}else {
					fail("CPD point earnt is not as expected , Actual is "+actcpdPoint + ", But expected is "+expcpdPoint);
				}
				takeScreenshot();
				clickOn(btnCloseDialog, "Close Dialog Button");
			}else {
				fail("Question answer page is not displayed ");
			}
		}else {
			fail("Rate Education Content is not displayed");
		}
		takeScreenshot();
	}

}

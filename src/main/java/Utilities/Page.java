package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.jayway.jsonpath.JsonPath;

import Utilities.constans.Values;

/**
 * Base class for all the pages.
 *
 */
public abstract class Page extends Common {
	protected WebDriver browser;
	protected final int defaultElementLoadWaitTime = 20;

	protected abstract boolean isValidPage();

	protected abstract void waitForPageLoad();

	protected Data data;

	/**
	 * Constructor for Page class
	 * 
	 * @param browser
	 * @param report
	 */
	protected Page(WebDriver browser, Data data) {
		this.browser = browser;
		PageFactory.initElements(browser, this);
		waitForPageLoad();
		verifyApplicationInCorrectPage();
		this.data = data;
	}

	/**
	 * Verify Application in Correct Page.
	 * 
	 * @param Nil
	 * @return Nil
	 */
	private void verifyApplicationInCorrectPage() {
		if (!isValidPage()) {
			String stepName = "Navigation to Page";
			String message = "The application is not in the expected page , current page: " + browser.getTitle()
					+ " Page.";
		}
	}

	/**
	 * Check if the element is present in the page
	 * 
	 * @param element WebElement need to check
	 * @return True if present
	 */
	protected boolean isElementPresent(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		} catch (Exception ex2) {
			return false;
		}
	}

	/**
	 * To generate Random Number
	 * 
	 * @param int min and max numbers
	 * @return random Number in Integer
	 */

	public static int randInt(int min, int max) {
		// Usually this can be a field rather than a method variable
		Random rand = new Random();
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * Check if the element is displayed in the page
	 * 
	 * @param element WebElement need to check
	 * @return True if present
	 */
	protected boolean isElementDisplayed(WebElement element, String str) {
		try {
			boolean blnDisplay = element.isDisplayed();
			passed(str + " is Displayed");
			return blnDisplay;
		} catch (NoSuchElementException ex) {
			fail(str + " is Not Displayed");
			return false;
		}
	}

	/**
	 * Check if the element is displayed in the page
	 * 
	 * @param element WebElement need to check
	 * @return True if present
	 */
	protected boolean isElementDisplayed(By by, String str) {
		try {
			boolean blnDisplay = browser.findElement(by).isDisplayed();
			passed(str + " is Displayed");
			return blnDisplay;
		} catch (NoSuchElementException ex) {
			fail(str + " is Not Displayed");
			return false;
		}
	}

	/**
	 * Check if the element is present in the page
	 * 
	 * @param xpath of WebElement need to check
	 * @return True if present
	 */
	public boolean isElementPresent(By by) {
		try {
			return browser.findElement(by).isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		} catch (Exception ex2) {
			return false;
		}
	}

	/***
	 * Method to select recentPopupSelect_without_window_nameWebdriver
	 * 
	 * @return : Null
	 ***/
	public void recentPopupSelect_without_window_nameWebdriver() {
		String mainwindow;
		mainwindow = browser.getWindowHandle();
		sleep(2000);
		Iterator<String> popwindow = browser.getWindowHandles().iterator();
		while (popwindow.hasNext()) {
			String window = popwindow.next();
			browser.switchTo().window(window);

		}

	}

	/***
	 * Method to click on a link(WebElement button)
	 * 
	 * @param : we WebElement to be clicked
	 * @param : elem Name of the WebElement
	 ***/
	public void clickOn(WebElement we, String elem) {
		try {
			waitForElement(we);
			if (isElementPresent(we)) {
				try {
					we.click();
					passed("Clicked on WebElement-" + elem);
				} catch (ElementClickInterceptedException e) {
					jsClick(we, elem);
				}
			} else {
				fail(elem + " Element is not displayed");
			}
		} catch (Exception ex) {
			fail("Unable to click on Element-" + elem + "Exception is " + ex.getMessage());
		}
	}

	/***
	 * Method to click on a link(WebElement button)
	 * 
	 * @param : we WebElement to be clicked
	 * @param : elem Name of the WebElement
	 ***/
	public void clickOn(By by, String elem) {
		try {
			waitForElement(by);
			if (isElementPresent(by)) {
				// moveToElement(by);
				browser.findElement(by).click();
				passed("Clicked on WebElement-" + elem);
			} else {
				fail(elem + " Element is not displayed");
			}
		} catch (Exception ex) {
			fail("Unable to click on Element-" + elem);
		}
	}

	/**
	 * Method to jsclick on a link(WebElement link)
	 * 
	 * @param : we WebElement to be Clicked
	 * @param : elem Name of the webElement
	 */
	protected void jsClick(WebElement we, String elem) {
		try {
			// scrollPageDown(we);
			((JavascriptExecutor) browser).executeScript("arguments[0].click();", we);
			passed("Clicked on -" + elem + "- Element");
		} catch (RuntimeException ex) {
			fail("Uanble to click on -" + elem + "- Element");
		}
	}

	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Webelement
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterText(WebElement we, String elemName, String text) {
		boolean blnFlag;
		blnFlag = false;
		try {
			if (text != null && !text.trim().equals("")) {
				if (isElementPresent(we)) {
					we.sendKeys(text);
					passed("Entered value - " + text + " in the text field- " + elemName);
					blnFlag = true;
				} else {
					fail(elemName + " element is not present");
				}
			}
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
		return blnFlag;
	}

	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Webelement
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterTextAndSendEnterKey(WebElement we, String elemName, String text) {
		boolean blnFlag;
		blnFlag = false;
		try {
			if (text != null && !text.trim().equals("")) {
				if (isElementPresent(we)) {
					we.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
					we.sendKeys(text);
					we.sendKeys(Keys.ENTER);
					passed("Entered value - " + text + " in the text field- " + elemName);
					blnFlag = true;
				} else {
					fail(elemName + " element is not present");
				}
			}
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
		return blnFlag;
	}

	public void jsEnterTextValue(WebElement we, String elemName, String text) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) browser;
			// set the text
			jsExecutor.executeScript("arguments[0].value='" + text + "'", we);
			passed("Entered value - " + text + " in the text field- " + elemName);
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
	}

	public void jsEnterText(WebElement we, String elemName, String text) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) browser;
			// set the text
			jsExecutor.executeScript("arguments[0].text='" + text + "'", we);
			passed("Entered value - " + text + " in the text field- " + elemName);
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
	}

	public void selectOptionDropDownUtil(WebElement txtDropdown) {
		try {
			Select dropdown = new Select(txtDropdown);
			dropdown.selectByIndex(2);
		} catch (Exception ex) {
			fail("Unable to select dropdown for locator " + txtDropdown);
		}
	}

	/***
	 * Method to hover over an element
	 * 
	 * @param : weMainMenuElement,weSubMenuElement
	 * @return : Modified By :
	 ***/
	public void selectOptionDropDown(WebElement txtDropdown, String elemName, String option) {
		String strElem = null;
		if (!option.equals("")) {
			try {
				Actions action = new Actions(browser);
				action.moveToElement(txtDropdown).click().perform();
				passed("Clicked on element " + elemName);
			} catch (Exception Ex) {
				fail("Unable to click on the element " + elemName);
			}
			try {
				waitForElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='" + option + "']"));
				sleep(1000);
				List<WebElement> wes = browser.findElements(
						By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='" + option + "']"));
				for (WebElement we : wes) {
					if (isElementPresent(we)) {
						we.click();
						break;
					}
				}
				// browser.findElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='"+option+"']")).click();
				passed("Selected the option " + option);
			} catch (Exception ex) {
				fail("Unable to select the option " + option);
			}
		}
	}

	/*
	 * public void selectOptionDropDown(WebElement txtDropdown,String
	 * elemName,String option) { try { sleep(2000); clickOn(txtDropdown, elemName);
	 * clickOn(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='"
	 * +option+"']"), option); } catch (Exception ex) {
	 * fail("Unable to enter text in the text field->" + elemName); } }
	 */

	/***
	 * Method to hover over an element
	 * 
	 * @param : weMainMenuElement,weSubMenuElement
	 * @return : Modified By :
	 ***/
	public void selectOptionCheckboxFromDentalImage(WebElement weFullImageElementLabel, String elemName,
			String multiOption) {
		String strElem = null;
		String[] options = multiOption.split(",");
		if (options.length > 0) {
			try {
				for (int i = 0; i < options.length; i++) {
					sleep(1000);
					List<WebElement> wes = weFullImageElementLabel
							.findElements(By.xpath("./../following-sibling::div//*[text()='" + options[i]
									+ "']/following-sibling::label//input/.."));
					for (WebElement we : wes) {
						if (isElementPresent(we)) {
							we.click();
							passed("Selected the option from dental Image " + options[i]);
							break;
						}
					}
				}
			} catch (Exception ex) {
				fail("Unable to select the options from dental Image" + multiOption);
			}
		}
	}

	public void selectOptionLoadingDropDown(WebElement txtDropdown, String elemName, String option) {
		String strElem = null;
		if (!option.equals("")) {
			try {
				Actions action = new Actions(browser);
				action.moveToElement(txtDropdown).click().perform();
				passed("Clicked on element " + elemName);
			} catch (Exception Ex) {
				fail("Unable to click on the element " + elemName);
			}
			try {
				waitForElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']/div/div"));
				sleep(1000);
				List<WebElement> wes = browser
						.findElements(By.xpath("//div[@class='rc-virtual-list-holder-inner']/div/div"));
				int j = 0;
				boolean blnFound = false;
				for (int i = 0; i < 10; i++) {
					wes = browser.findElements(By.xpath("//div[@class='rc-virtual-list-holder-inner']/div/div"));
					String str = wes.get(i).getText();
					System.out.println(str);
					if (str.equals(option)) {
						wes.get(i).click();
						;
						passed("Selected the option " + option);
						blnFound = true;
						break;
					}
					if (i == 9) {
						moveToElement(wes.get(i));
						i = 0;
						j++;
					}
					if (j > 2) {
						break;
					}
				}
				if (!blnFound) {
					fail("Unable to select the option, Option not present in the dropdown " + option);
				}

			} catch (Exception ex) {
				fail("Unable to select the option " + option);
			}
		}
	}

	/***
	 * Method to hover over an element
	 * 
	 * @param : weMainMenuElement,weSubMenuElement
	 * @return : Modified By :
	 ***/
	public void selectFirstOptionFromDropDown(WebElement txtDropdown, String elemName) {
		String strElem = null;
		try {
			Actions action = new Actions(browser);
			action.moveToElement(txtDropdown).click().perform();
			passed("Clicked on element " + elemName);
		} catch (Exception Ex) {
			fail("Unable to click on the element " + elemName);
		}
		try {
			waitForElement(By.xpath(
					"//div[@class='rc-virtual-list-holder-inner']//div[@class='ant-select-item-option-content']"));
			sleep(1000);
			List<WebElement> wes = browser.findElements(By.xpath(
					"//div[@class='rc-virtual-list-holder-inner']//div[@class='ant-select-item-option-content']"));
			if (wes.size() > 0) {
				wes.get(0).click();
			} else {
				fail("Unable to select the option,No options are present in the dropdown");
			}
			// browser.findElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='"+option+"']")).click();
			passed("Selected the first option from dropdown" + elemName);
		} catch (Exception ex) {
			fail("Unable to select the option from dropdown" + elemName);
		}
	}

	public void selectFirstOptionMultiOptionDropDown(WebElement txtDropdown, String elemName) {
		String strElem = null;
		try {
			Actions action = new Actions(browser);
			action.moveToElement(txtDropdown).click().perform();
			passed("Clicked on element " + elemName);
		} catch (Exception Ex) {
			fail("Unable to click on the element " + elemName);
		}
		try {
			waitForElement(By.xpath(
					"//div[@class='rc-virtual-list-holder-inner']//div[@class='ant-select-item-option-content']"));
			sleep(1000);
			List<WebElement> wes = browser.findElements(By.xpath(
					"//div[@class='rc-virtual-list-holder-inner']//div[@class='ant-select-item-option-content']"));
			if (wes.size() > 0) {
				wes.get(1).click();
			} else {
				fail("Unable to select the option,No options are present in the dropdown");
			}
			// browser.findElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='"+option+"']")).click();

		} catch (Exception ex) {
			fail("Unable to select the First options from dropdown " + ex.getMessage());
		}

	}

	public void selectMultiOptionDropDown(WebElement txtDropdown, String elemName, String multiOption) {
		String strElem = null;
		String[] options = multiOption.split(",");
		if (options.length > 0) {
			try {
				Actions action = new Actions(browser);
				action.moveToElement(txtDropdown).click().perform();
				passed("Clicked on element " + elemName);
			} catch (Exception Ex) {
				fail("Unable to click on the element " + elemName);
			}
			try {
				for (int i = 0; i < options.length; i++) {
					waitForElement(
							By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='" + options[i] + "']"));
					sleep(1000);
					List<WebElement> wes = browser.findElements(
							By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='" + options[i] + "']"));
					for (WebElement we : wes) {
						if (isElementPresent(we)) {
							we.click();
							passed("Selected the option " + options[i]);
							break;
						}
					}
				}
				// browser.findElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='"+option+"']")).click();

			} catch (Exception ex) {
				fail("Unable to select the options " + multiOption);
			}
		}
	}

	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Webelement
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterTextAndSelectOption(WebElement we, String elemName, String text) {
		boolean blnFlag;
		blnFlag = false;
		try {
			if (text != null) {
				if (!text.equals("")) {
					waitForElement(we);
					if (isElementPresent(we)) {
						we.sendKeys(text);
						passed("Entered value - " + text + " in the text field- " + elemName);
						waitForElement(
								By.xpath("//div[@class='rc-virtual-list-holder-inner']/div//*[text()='" + text + "']"));
						sleep(1000);
						clickOn(By.xpath("//div[@class='rc-virtual-list-holder-inner']/div//*[text()='" + text + "']"),
								text);
					} else {
						fail(elemName + " element is not present");
					}
				}
			}
			blnFlag = true;
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
		return blnFlag;
	}

	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Webelement
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterText(WebElement we, String elemName, String text, int waitTime) {
		boolean blnFlag;
		blnFlag = false;
		try {
			waitForElement(we, waitTime);
			if (isElementPresent(we)) {
				we.clear();
				we.sendKeys(text);
				passed("Entered value - " + text + " in the text field- " + elemName);
				blnFlag = true;
			} else {
				fail(elemName + " element is not displayed");
			}
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
		return blnFlag;
	}

	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Webelement
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterPassword(WebElement we, String elemName, String text) {
		boolean blnFlag;
		blnFlag = false;
		try {
			waitForElement(we);
			if (isElementPresent(we)) {
				we.clear();
				we.sendKeys(text);
				passed("Entered value - *********** in the text field- " + elemName);
				blnFlag = true;
			} else {
				fail(elemName + " element is not displayed");
			}
		} catch (Exception ex) {
			fail("Unable to enter text in the text field->" + elemName);
		}
		return blnFlag;
	}

	/***
	 * Method to clear text in a textbox
	 * 
	 * @param : Element Name
	 * @return :
	 ***/
	public void clearText(WebElement we) {
		try {
			if (isElementPresent(we)) {
				we.clear();
				we.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			}
		} catch (Exception ex) {
			fail("Unable to clear text in the text field");
		}
	}

	/***
	 * Method to switch to child window
	 * 
	 * @param : pageTitle Title of the Child window
	 * @return : true if navigation is success
	 ***/
	public boolean navigateToNewWindow(String pageTitle) {
		boolean loopstatus = false;
		int timeout = Values.pageLoadWaitTime;
		for (int i = 1; i <= timeout; i++) {
			loopstatus = false;
			if (i == timeout) {
				fail("Unable to Navigate to the page -" + pageTitle);
			}

			Set<String> AllHandle = browser.getWindowHandles();
			for (String han : AllHandle) {
				browser.switchTo().window(han);
				String getTitle = browser.getTitle();
				if (getTitle.trim().equalsIgnoreCase(pageTitle)) {
					loopstatus = true;
					break;
				}
			}
			if (loopstatus) {
				passed("Navigated to the page -" + pageTitle + "- successfully");
				break;
			}
			sleep(1000);
		}
		return loopstatus;
	}

	/***
	 * Method to switch to child window
	 * 
	 * @param : pageTitle Title of the Child window
	 * @return : true if navigation is success
	 ***/
	public boolean navigateToSecondWindow() {
		boolean loopstatus = false;
		String getTitle = null;
		String strParentTitle = browser.getTitle();
		Set<String> AllHandle = browser.getWindowHandles();
		for (String han : AllHandle) {
			browser.switchTo().window(han);
			getTitle = browser.getTitle();
			if (!getTitle.trim().equalsIgnoreCase(strParentTitle)) {
				loopstatus = true;
				break;
			}
		}
		if (loopstatus) {
			passed("Navigated to the page -" + getTitle + "- successfully");
			browser.manage().window().maximize();
		}
		sleep(1000);
		return loopstatus;
	}

	/***
	 * Method to switch to parent window
	 * 
	 * @param : parentWindow Window handle of the parent window
	 ***/
	public void navigatoToParentWindow(String parentWindow) {
		browser.switchTo().window(parentWindow);
	}

	/***
	 * Javascript to hover over WebElement
	 * 
	 * @param elem Webelement to hover over
	 */
	public void jsmoveToElement(WebElement elem) {

		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript(mouseOverScript, elem);

	}

	/***
	 * Method to close a webpage
	 * 
	 * @return : Null
	 ***/
	public void closeCurrentPage() {
		String str = browser.getTitle();
		try {
			browser.close();
			Set<String> windows = browser.getWindowHandles();
			for (String window : windows) {
				browser.switchTo().window(window);
				String getTitle = browser.getTitle();
				if (!getTitle.trim().equalsIgnoreCase(str)) {
					break;
				}
			}
			sleep(5000);
			passed("Closed the current page with title->" + str);
		} catch (Exception e) {
			fail("Unable to Close the current page with title->" + str);
		}
	}

	// *****************************************************************************************************************//
	// Alert pop ups
	// *****************************************************************************************************************//
	/***
	 * Method to accept and close alert and return the text within the alert
	 * 
	 * @param :
	 * @return :
	 ***/
	public String closeAlertAndReturnText() {
		String alertMessage = null;
		try {
			WebDriverWait wait = new WebDriverWait(browser, Values.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alertMessage = alert.getText();
			passed("alertMessage displayed is->" + alertMessage);
			alert.accept();
			passed("Alert is closed successfully");
		} catch (Exception Ex) {
			fail("Unable to Close Alert, Error Message is->" + Ex.getMessage());
		}
		return alertMessage;
	}

	/***
	 * Method to Cancel the alert
	 * 
	 * @param :
	 * @return :
	 ***/
	public void alertCancel() {
		try {
			WebDriverWait wait = new WebDriverWait(browser, Values.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alert.dismiss();
			passed("Clicked on Alert Cancel successfully");
		} catch (Exception ex) {
			fail("Unable to Cancel Alert, Error Message is->" + ex.getMessage());
		}
	}

	/***
	 * Method to verify if alert is Present
	 * 
	 * @param :
	 * @return :
	 ***/
	public boolean isAlertWindowPresent() {
		try {
			browser.switchTo().alert();
			return true;
		} catch (Exception E) {

		}
		return false;
	}

	// *****************************************************************************************************************//
	// waits
	// *****************************************************************************************************************//

	/**
	 * Method to wait for element to load in the page
	 * 
	 * @param WebElement
	 * @return true or false
	 */
	protected Boolean waitForElement(By by) {
		try {
			new WebDriverWait(browser, Values.elementLoadWaitTime)
					.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public void waitForTableToLoad(By by) {
		List<WebElement> weRows = browser.findElements(by);
		for (int i = 0; i < Values.elementLoadWaitTime; i++) {
			if (weRows.size() > 1) {
				break;
			}
		}
	}

	protected Boolean waitForElementInvisible(By by) {
		try {
			new WebDriverWait(browser, Values.elementLoadWaitTime)
					.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Method to wait for element to load in the page for default time specified in
	 * framework configuration
	 * 
	 * @param WebElement
	 * @return true or false
	 */

	protected Boolean waitForElement(WebElement we) {
		try {
			new WebDriverWait(browser, Values.elementLoadWaitTime).until(ExpectedConditions.elementToBeClickable(we));
			return true;
		} catch (RuntimeException ex) {
			return false;
		}
	}

	public static File getLatestFile(String directoryFilePath) {
		File directory = new File(directoryFilePath);
		File[] files = directory.listFiles(File::isFile);
		long lastModifiedTime = Long.MIN_VALUE;
		File chosenFile = null;

		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					chosenFile = file;
					lastModifiedTime = file.lastModified();
				}
			}
		}

		return chosenFile;
	}

	public int getRowCount(File file) {
		int rowCount = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			// Read the first line (header) and discard it
			br.readLine();

			// Count the number of lines (rows) in the CSV file
			String line;
			while ((line = br.readLine()) != null) {
				rowCount++;
			}

			info("Total number of rows in CSV file: " + rowCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	/**
	 * Method to wait for element to load in the page for particular time specified
	 * 
	 * @param WebElement
	 * @return true or false
	 */

	protected Boolean waitForElement(WebElement we, int waitTime) {
		try {
			new WebDriverWait(browser, waitTime).until(ExpectedConditions.elementToBeClickable(we));
			return true;
		} catch (RuntimeException ex) {
			return false;
		}
	}

	/**
	 * Method to wait for Alert present in the page
	 * 
	 * @param
	 * @return true or false
	 */
	protected Boolean waitForAlert() {
		try {
			new WebDriverWait(browser, Values.elementLoadWaitTime).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	/***
	 * Method to get current time in minutes
	 * 
	 * @param : Element Name
	 * @return : Modified By :
	 ***/
	public int getTimeInMin(String time) {
		// String time=new SimpleDateFormat("HH:mm").format(new Date());
		String[] splitTime = time.split(":");
		int hr = Integer.parseInt(splitTime[0]);
		int mn = Integer.parseInt(splitTime[1].substring(0, 2));
		if (hr > 12) {
			hr = hr - 12;
		}
		int timStamp = (hr * 60) + mn;
		return timStamp;
	}

	/***
	 * Method to check for an alert for 5 seconds
	 * 
	 * @param : Element Name
	 * @return : Modified By :
	 ***/
	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(browser, Values.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * 
	 * @param : we1,we2
	 * @return :
	 * @author : Prakash Shetty Modified By :
	 ***/
	public boolean waitForAnyElement(WebElement we1, WebElement we2) {
		try {
			for (int i = 0; i < Values.elementLoadWaitTime; i++) {
				if (isElementPresent(we1) || isElementPresent(we2)) {
					break;
				} else {
					sleep(1000);
				}
			}
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * 
	 * @param : we1,we2
	 * @return :
	 * @author : Prakash Shetty Modified By :
	 ***/
	public boolean waitForTwoElements(WebElement we1, WebElement we2) {
		try {
			for (int i = 0; i < Values.elementLoadWaitTime; i++) {
				if (isElementPresent(we1) || isElementPresent(we2)) {
					break;
				} else {
					sleep(1000);
				}
			}
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * 
	 * @param : we1,we2,we3
	 * @return :
	 * @author : Prakash Shetty Modified By :
	 ***/
	public boolean waitForThreeElements(WebElement we1, WebElement we2, WebElement we3) {
		try {
			for (int i = 0; i < Values.elementLoadWaitTime; i++) {
				if (isElementPresent(we1) || isElementPresent(we2) || isElementPresent(we3)) {
					break;
				} else {
					sleep(1000);
				}
			}
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	/**
	 * method to make a thread sleep for customized time in milliseconds
	 * 
	 * @param milliseconds
	 */
	protected void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectRadioButton(WebElement we, String radioButtonName, String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				WebElement weRadioValue = we.findElement(By.xpath("./div//span[text()='" + value + "']"));
				try {
					weRadioValue.click();
					passed(radioButtonName + " Radio button value selected is->" + value);
				} catch (ElementClickInterceptedException e) {
					jsClick(weRadioValue, radioButtonName);
				}
			}
		} catch (NoSuchElementException ex) {
			try {
				WebElement weRadioValue = we.findElement(By.xpath("./label//span[text()='" + value + "']"));
				try {
					weRadioValue.click();
					passed(radioButtonName + " Radio button value selected is->" + value);
				} catch (ElementClickInterceptedException e) {
					jsClick(weRadioValue, radioButtonName);
				}
			} catch (NoSuchElementException e) {
				fail(radioButtonName + "Radio Button option is not present in UI " + value);
			}
		} catch (Exception ex) {
			fail("Exception Caught in selectRadioButton ,Message is->" + ex.getMessage());
		}
	}

	public void selectRadioButtonByValue(WebElement we, String radioButtonName, String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				WebElement weRadioValue = we.findElement(By.xpath("./div//input[@value='" + value + "']"));
				try {
					weRadioValue.click();
					passed(radioButtonName + " Radio button value selected is->" + value);
				} catch (ElementClickInterceptedException e) {
					jsClick(we, radioButtonName);
				}
			}
		} catch (NoSuchElementException ex) {
			try {
				WebElement weRadioValue = we.findElement(By.xpath("./label//input[@value='" + value + "']"));
				try {
					weRadioValue.click();
					passed(radioButtonName + " Radio button value selected is->" + value);
				} catch (ElementClickInterceptedException e) {
					jsClick(we, radioButtonName);
				}
			} catch (NoSuchElementException e) {
				fail(radioButtonName + "Radio Button option is not present in UI " + value);
			}
		} catch (Exception ex) {
			fail("Exception Caught in selectRadioButton ,Message is->" + ex.getMessage());
		}
	}

	public void selectRadioButton1(WebElement we, String radioButtonName, String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				WebElement weRadioValue = we.findElement(By.xpath("./label//span[text()='" + value + "']"));
				weRadioValue.click();
				passed(radioButtonName + " Radio button value selected is->" + value);
			}
		} catch (NoSuchElementException e) {
			fail(radioButtonName + "Radio Button option is not present in UI " + value);
		} catch (Exception ex) {
			fail("Exception Caught in selectRadioButton ,Message is->" + ex.getMessage());
		}
	}

	public void selectRadioButton(By by, String radioButtonName, String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				WebElement we = browser.findElement(by);
				WebElement weRadioValue = we.findElement(By.xpath("./div//span[text()='" + value + "']"));
				weRadioValue.click();
				passed(radioButtonName + " Radio button value selected is->" + value);
			}

		} catch (NoSuchElementException ex) {
			fail(radioButtonName + "Radio Button option is not present in UI " + value);
		} catch (Exception ex) {
			fail("Exception Caught in selectRadioButton ,Message is->" + ex.getMessage());
		}
	}

	public void selectButtonFromMultiple(WebElement we, String buttonName, String buttonValue) {
		try {
			if (buttonValue != null && !buttonValue.trim().equals("")) {
				WebElement weButtonValue = we
						.findElement(By.xpath("./label//span[text()='" + buttonValue.trim() + "']"));
				weButtonValue.click();
				passed(buttonName + " Button value selected is->" + buttonValue.trim());
			}
		} catch (NoSuchElementException ex) {
			fail(buttonName + " Button option is not present in UI " + buttonValue.trim());
		} catch (Exception ex) {
			fail("Exception Caught in selectButtonFromMultiple ,Message is->" + ex.getMessage());
		}
	}

	public void selectButtonFromMultiple(By by, String buttonName, String buttonValue) {
		try {
			if (buttonValue != null && !buttonValue.trim().equals("")) {
				WebElement we = browser.findElement(by);
				WebElement weButtonValue = we
						.findElement(By.xpath("./label//span[text()='" + buttonValue.trim() + "']"));
				weButtonValue.click();
				passed(buttonName + " Button value selected is->" + buttonValue.trim());
			}
		} catch (NoSuchElementException ex) {
			fail(buttonName + " Button option is not present in UI " + buttonValue.trim());
		} catch (Exception ex) {
			fail("Exception Caught in selectButtonFromMultiple ,Message is->" + ex.getMessage());
		}
	}

	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * 
	 * @param : By,By
	 * @return :
	 * @author : Prakash Shetty Modified By :
	 ***/
	public boolean waitForAnyElement(By we1, By we2) {
		try {
			for (int i = 0; i < Values.elementLoadWaitTime; i++) {
				if (isElementPresent(we1) || isElementPresent(we2)) {
					break;
				} else {
					sleep(1000);
				}
			}
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	/***
	 * Method to hover over an element
	 * 
	 * @param : weMainMenuElement,weSubMenuElement
	 * @return : Modified By :
	 ***/
	public void clickOnSubMenu(WebElement weMain, WebElement weSub) {
		String strElem = null;
		try {
			Actions action = new Actions(browser);
			action.moveToElement(weMain).click().perform();
			passed("Hover over the Main menu item successfully");
		} catch (Exception Ex) {
			fail("Unable to hover Over main menu Item");
		}
		try {
			waitForElement(weSub);
			weSub.click();
			passed("Clicked on the Sub menu item successfully");
		} catch (Exception ex) {
			fail("Unable to Click on the sub menu item");
		}
	}

	/***
	 * Method to hover over an element
	 * 
	 * @param : WebElement we
	 * @return : Modified By :
	 ***/

	public void moveToElement(WebElement we) {
		try {
			/*
			 * Actions action = new Actions(browser);
			 * action.moveToElement(we).build().perform();
			 */
			scrollPageDown(we);
		} catch (Exception e) {
			fail("Error Occurred while Move to Element --> " + e.getMessage());
		}
	}

	public void moveToElement1(WebElement we) {
		try {

			Actions action = new Actions(browser);
			action.moveToElement(we).click().build().perform();
		} catch (Exception e) {
			fail("Error Occurred while Move to Element --> " + e.getMessage());
		}
	}

	public void moveToElement(By by) {
		try {
			Actions action = new Actions(browser);
			action.moveToElement(browser.findElement(by)).click().build().perform();
		} catch (Exception e) {
			fail("Error Occurred while Move to Element --> " + e.getMessage());
		}
	}

	/***
	 * Method to Select value from dropdown by visible text
	 * 
	 * @param : we,strElemName,strVisibleText
	 * @return : Modified By :
	 ***/
	public void selectByVisisbleText(WebElement we, String strElemName, String strVisibleText) {
		try {
			Select sel = new Select(we);
			sel.selectByVisibleText(strVisibleText);

			passed("selected value -" + strVisibleText + " from dropdown->" + strElemName);
		} catch (Exception Ex) {
			fail("Unable to select value from the dropdown " + Ex.getMessage());
		}
	}

	/***
	 * Method to Select value from dropdown by index
	 * 
	 * @param : we,strElemName,index
	 * @return : Modified By :
	 ***/

	public void selectByIndex(WebElement we, String strElemName, int index) {
		try {
			Select sel = new Select(we);
			sel.selectByIndex(index);
			passed("Selected " + index + "option from dropdown->" + strElemName);
		} catch (Exception Ex) {
			fail("Unable to select value from the dropdown " + Ex.getMessage());
		}
	}

	/***
	 * Method to Select value from dropdown by index
	 * 
	 * @param : we,strElemName,strValue
	 * @return : Modified By :
	 ***/
	public void selectByValue(WebElement we, String strElemName, String strValue) {
		try {
			Select sel = new Select(we);
			sel.selectByValue(strValue);
			passed("Selected " + strValue + "option from dropdown->" + strElemName);
		} catch (Exception Ex) {
			fail("Unable to select value from the dropdown " + Ex.getMessage());
		}
	}

	/***
	 * Method to get the Selected value from dropdown
	 * 
	 * @param : weDropdown
	 * @return : selectText Modified By :
	 ***/
	public String getTextSelectedOption(WebElement weDropDown) {
		waitForElement(weDropDown);
		String selectText = "";
		try {
			Select select = new Select(weDropDown);
			selectText = select.getFirstSelectedOption().getText().toString();

		} catch (Exception ex) {
			fail("Unable to get the selected value from dropdown->" + ex.getMessage());
		}
		return selectText;
	}

	/***
	 * Method to verify if the WebElement has the expected text
	 * 
	 * @param : we,expectedText
	 * @return : Modified By :
	 ***/
	public void verifyElementText(WebElement we, String expectedText) {
		waitForElement(we);
		if (isElementPresent(we)) {
			for (int i = 1; i <= Values.elementLoadWaitTime; i++) {
				try {
					if (we.getText().trim().equalsIgnoreCase(expectedText.trim())) {
						passed("Element contains the Expected Text->" + expectedText);

					} else {
						fail("Element does not contain the expected text" + expectedText);
					}
				} catch (Exception e) {
					sleep(1000);
				}
				if (i == Values.elementLoadWaitTime) {
					fail("Element not found within " + Values.elementLoadWaitTime + " timeouts");
				}
			}
		}
	}

	/***
	 * Method to get the ElementLoadWaitTime
	 * 
	 * @param :
	 * @return : ElementLoadWaitTime Modified By :
	 ***/
	public int getElementLoadWaitTime() {
		try {
			String waitTime = Values.configData.get("ElementLoadWaitTime");
			int i = Integer.parseInt(waitTime);
			if (i < 1) {
				return defaultElementLoadWaitTime;
			} else {
				return i;
			}
		} catch (Exception ex) {
			return defaultElementLoadWaitTime;
		}

	}

	/***
	 * Method to close the browser with title provided
	 * 
	 * @param : windowTitle
	 * @return : Modified By :
	 ***/
	public void closeChildBrowser(String windowTitle) {
		try {
			for (String winHandle : browser.getWindowHandles()) {
				browser.switchTo().window(winHandle);
				if (browser.getTitle().equalsIgnoreCase(windowTitle)) {
					browser.close();
					passed("Closed the browser with page title->" + windowTitle);
					break;
				}
			}
		} catch (Exception e) {
			fail("Unable to Close Browser");
		}
	}

	/***
	 * Method to wait until element is invisible
	 * 
	 * @param :
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 ***/
	protected Boolean waitUntilElementInvisible(String path) {
		try {
			new WebDriverWait(browser, Values.elementLoadWaitTime)
					.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(path)));
			return true;
		} catch (RuntimeException ex) {
			return false;
		}
	}

	/***
	 * Method to select the checkbox
	 * 
	 * @param : cbElement
	 * @return : Modified By :
	 ***/
	public void selectCheckBox(WebElement cbElement, String checkboxName, String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				WebElement weRadioValue = cbElement.findElement(By.xpath(".//label//span[text()='" + value + "']"));
				weRadioValue.click();
				passed(checkboxName + " Checkbox button value selected is->" + value);
			}
		} catch (NoSuchElementException ex) {
			fail(checkboxName + " Checkbox Button option is not present in UI " + value);
		} catch (Exception ex) {
			fail("Exception Caught in selectCheckbox,Message is->" + ex.getMessage());
		}
	}

	/***
	 * Method to UnSelect the checkbox
	 * 
	 * @param : cbElement
	 * @return : Modified By :
	 ***/
	public void unSelectCheckBox(WebElement cbElement) {
		waitForElement(cbElement);
		if (isElementPresent(cbElement)) {
			try {
				if (cbElement.isSelected()) {
					cbElement.click();
				}
				passed("Unchecked the checkbox");
			} catch (Exception e) {
				fail("Unable to check the checkbox->" + e.getMessage());
			}
		}
	}

	/***
	 * Method to verify the checkbox if checked
	 * 
	 * @param : cbElement
	 * @return : Modified By :
	 ***/
	public boolean verifyCheckBoxIsChecked(WebElement cbElement) {
		waitForElement(cbElement);
		if (isElementPresent(cbElement)) {
			try {
				if (cbElement.isSelected()) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				fail("Unable to verify the checkbox->" + e.getMessage());
				return false;
			}
		} else {
			fail("Unable to verify the checkbox");
			return false;
		}
	}

	public void hoverMenu(WebElement menu, String subMenuId) {
		try {
			Actions actions = new Actions(browser);
			actions.moveToElement(menu).perform();
			By locator = By.id(subMenuId);
			browser.findElement(locator).click();
			passed("Clicked on Sub menu successfully");
		} catch (Exception ex) {
			fail("Hover Failed!!");
		}
	}

	public boolean verifyPage(String pageTitle) {
		if (browser.getTitle().contains(pageTitle)) {
			passed("Successfully Navigated to " + pageTitle);
			return true;
		} else {
			fail("Unexpected Navigation!! Expected Navigation to " + pageTitle);
			return false;
		}
	}

	public void scrollPageDown(By by) {
		try {
			WebElement we = browser.findElement(by);
			((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", we);
			sleep(2000);
		} catch (Exception e) {
			fail("Exception caught while scrolling Page down " + e.getMessage());
		}
	}

	public void scrollPageDown(WebElement we) {
		try {
			((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", we);
			sleep(2000);
		} catch (Exception e) {
			fail("Exception caught while scrolling Page down " + e.getMessage());
		}
	}

	/***
	 * Method to double click on a web element
	 * 
	 * @param : we,strElemName,strVisibleText
	 * @return : Modified By :
	 ***/
	public void doubleClick(WebElement we, String elem) {
		try {
			Actions action = new Actions(browser);
			waitForElement(we);
			if (isElementPresent(we)) {
				action.moveToElement(we).doubleClick().perform();
				passed("Double Clicked on WebElement-" + elem);
			} else {
				fail(elem + " Element is not displayed");
			}
		} catch (Exception e) {
			fail("Unable to douoble click - " + e.getMessage());
		}
	}

	/***
	 * Method to check for an alert for 5 seconds
	 * 
	 * @param : Element Name
	 * @return : Modified By :
	 ***/
	public boolean AlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * Method to retrieve Sale number
	 * 
	 * @param : Element Name
	 * @return : Modified By :
	 ***/

	public String saleNumber;

	public String getSaleNumber(String str) {
		String s1 = str;
		String[] ref = s1.split("-");
		String s2 = ref[1].trim().substring(0, 6);
		// s1.substring(10,17);
		s2.trim();
		System.out.println("here:" + s2);
		return s2;
	}

	/***
	 * Method to Select value from dropdown by partial visible text
	 * 
	 * @param : we,strElemName,strVisibleText
	 * @return : Modified By :
	 ***/
	public boolean selectByPartialText(WebElement we, String strElemName, String strVisibleText) {
		Select elSel = new Select(we);
		List<WebElement> opts = elSel.getOptions();
		for (WebElement elOpt : opts) {
			if (elOpt.getText().contains(strVisibleText)) {
				elOpt.click();
				passed("selected value -" + elOpt.getText() + " from dropdown->" + strElemName);
				return true;
			}
		}
		return false;
	}

	/***
	 * Method to retrieve Allowance number
	 * 
	 * @param : Element Name
	 * @return : Modified By :
	 ***/
	public static String allowanceNumber;

	public String getAllowanceNumber(String str) {
		String s1 = str;
		String s2 = s1.substring(6);
		s2.trim();
		System.out.println("here:" + s2);
		allowanceNumber = s2;
		return s2;
	}

	public void navigatetobackscreen(String Pagename) {
		try {
			browser.navigate().back();
			passed("Navigated to the back page" + Pagename);
		} catch (Exception e) {
			fail("Could not navigate to new page");
		}

	}

	public void navigatetoforwardscreen(String Pagename) {
		try {
			browser.navigate().forward();
			passed("Navigated to the forwared page" + Pagename);
		} catch (Exception e) {
			fail("Could not navigate to new forward page");
		}

	}

	/***
	 * Method to converted month name to month value
	 * 
	 * @param :
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 ***/

	public String monthToInt(String month) {
		int i;
		String mm = "";
		String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		for (i = 0; i < 12; i++) {
			if (months[i].equalsIgnoreCase(month.trim())) {
				i = i + 1;
				System.out.println("month is " + i);
				break;
			}
		}
		if (i < 10) {
			mm = "0" + i;
		} else {
			mm = Integer.toString(i);
		}
		return mm;
	}

	/****
	 * Method to compare Two Strings
	 * 
	 * @param :
	 * @return : void
	 * @author : suntechUser(userId)
	 * @Modified By :
	 ****/

	public boolean compareTwoStrings(String str1, String str2) {
		try {
			if (str1.equalsIgnoreCase(str2)) {
				return true;
			} else {
			}
		} catch (Exception e) {
			fail("Error occurred while comparing Two Strings --> " + e.getMessage());
		}
		return false;
	}

	/****
	 * Method to wait for element not present
	 * 
	 * @param :
	 * @return : void
	 * @author : suntechUser(userId)
	 * @Modified By :
	 ****/
	public void waitForElementNotPresent(WebElement element) throws Exception {
		for (int second = 0;; second++) {
			try {// try catch block is used handle 'Permission Denied Error' when waiting for
					// element
				if (second >= 120) {
					break;
					// passed("Element was found after waiting for 2 Minute");
				}
				if (!isElementPresent(element)) {
					break;
				}
			} catch (Exception e) {

			}

		}
	}

	/****
	 * Method to generate random string
	 * 
	 * @param : length of string to be generated
	 * @return : void
	 * @author : suntechUser(userId)
	 * @Modified By :
	 ****/
	public String generateRandomString(int len, String type) {
		Random rng = new Random();
		String characters = null;
		if (type.equalsIgnoreCase("numeric")) {
			characters = "1237890456";
		} else if (type.equalsIgnoreCase("alpha")) {
			characters = "abcdefghijklmnopqrstuvwxyz";
		} else if (type.equalsIgnoreCase("alphanumeric")) {
			characters = "abc1238974560defghijklmno1238974560NOPQRSTUVWXYZpqrst1238974560uvwxyz1238974560ABCDEFGHIJ1238974560KLM";
		}
		char[] text = new char[len];
		for (int i = 0; i < len; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public WebElement webElement(String object) {

		String elementLocator = object;// "xpath>>//input[@name='username']";
		By byLocator = null;
		WebElement element = null;
		try {
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			element = browser.findElement(byLocator);
		} catch (Exception ex) {
			fail("Uanble to locate element-" + ex);
		}
		return element;
	}

	public List<WebElement> webElements(String object) {

		String elementLocator = object;// "xpath>>//input[@name='username']";
		By byLocator = null;
		List<WebElement> elements = null;
		try {
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			elements = browser.findElements(byLocator);
		} catch (Exception ex) {
			fail("Uanble to locate element-" + ex);
		}
		return elements;
	}

	public void waitForPageElementLoad(String object) {

		try {
			String elementLocator = object;// "xpath>>//input[@name='username']";
			By byLocator = null;
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			new WebDriverWait(browser, 30).until(ExpectedConditions.presenceOfElementLocated(byLocator));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void alertAccept() {
		try {
			WebDriverWait wait = new WebDriverWait(browser, Values.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alert.accept();
			passed("Clicked on Alert Ok/Accept successfully");
		} catch (Exception ex) {
			fail("Unable to Acdept Alert, Error Message is->" + ex.getMessage());
		}
	}

	public String getText(By by) {
		String elementText = "";
		try {
			elementText = browser.findElement(by).getText().trim();
		} catch (Exception e) {
			fail("Unable to get the text, Exception is " + e.getMessage());
		}
		return elementText;
	}

	public String getText(WebElement we) {
		String elementText = "";
		try {
			elementText = we.getText().trim();
		} catch (Exception e) {
			fail("Unable to get the text, Exception is " + e.getMessage());
		}
		return elementText;

	}

	public void clickElementWithEnterKey(WebElement we, String clickedElement) {
		try {
			waitForElement(we);
			if (isElementPresent(we)) {
				Actions actions = new Actions(browser);
				actions.moveToElement(we).build().perform();
				we.sendKeys(Keys.TAB);
				we.sendKeys(Keys.ENTER);
				passed("Clicked on WebElement-" + clickedElement);
			} else {
				fail(clickedElement + " Element is not displayed");
			}
		} catch (Exception e) {
			// fail("Error Occurred While Clicking
			// "+clickedElement+" in clickElementWithEnterKey -->
			// "+e.getMessage());
		}
	}

	public void captureScreenShot(String filename) {
		File scrFile = null;
		String scrPath = Values.outputDirectory + "/Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			scrFile = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(scrPath + "/" + filename + ".png"));
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (scrFile == null) {
				System.out.println("This WebDriver does not support screenshots");
				return;
			}
		}
	}

	public void captureScreenShotbase() {
		try {
			String base = ((TakesScreenshot) browser).getScreenshotAs(OutputType.BASE64);
			logScreenshotbase(base);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void takeScreenshot() {
		captureScreenShotbase();
	}

	public void focusWindow() {
		((JavascriptExecutor) browser).executeScript("window.focus();");
	}

	public void validateErrorMessageOfField(WebElement we, String fieldName, String expErrorMessage) {
		try {
			List<WebElement> wes = we.findElements(
					By.xpath("./ancestor::div/following-sibling::div/div/div[@class='ant-form-item-explain-error']"));
			if (wes.size() > 0) {
				String actError = wes.get(0).getText();
				if (actError.contains(expErrorMessage)) {
					passed("Error message is displayed as expected for the field:  " + fieldName + ", Error message is "
							+ expErrorMessage);
				} else {
					fail("Error message for the filed is not as expected, Field Name is " + fieldName);
					fail("Expected Error " + expErrorMessage + ", But actual is " + actError);
				}
			} else {
				fail("Following Error message is not displayed :" + expErrorMessage);
			}
		} catch (Exception e) {
			fail("Expection caught while validating the error message " + expErrorMessage);
		}
	}

	public void validateToasterMessage(String expMesssageHeader, String expMessageDesc) {
		waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
		if (isElementPresent(By.xpath("//div[@class='ant-notification-notice-description']"))) {
			passed("Toaster Message is displayed as expected");
			String actHeader = getText(By.xpath("//div[@class='ant-notification-notice-message']"));
			String actDesc = getText(By.xpath("//div[@class='ant-notification-notice-description']"));
			if (!expMesssageHeader.equals("")) {
				if (actHeader.contains(expMesssageHeader)) {
					passed("Toaster message header is displayed as expected " + actHeader);
				} else {
					fail("Toaster message header is not as expected , Actual is " + actHeader + ", But expected is "
							+ expMesssageHeader);
				}
			}

			if (!expMessageDesc.equals("")) {
				if (actDesc.contains(expMessageDesc)) {
					passed("Toaster message header is displayed as expected " + actDesc);
				} else {
					fail("Toaster message header is not as expected , Actual is " + actDesc + ", But expected is "
							+ expMessageDesc);
				}
			}
		} else {
			fail("Toaster Message is not displayed");
		}
		takeScreenshot();
	}

	public boolean validateEmail(String username) {
		System.out.println("Email Address: " + data.get("Email Address"));
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9]+)+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}

	public void uploadFile(WebElement btnUpload, String imgPath, WebElement btnConfirm) {
		String absolute = "";
		boolean file = true;
		try {
			File b = new File(imgPath);
			if (!b.isFile()) {
				file = false;
			}
			absolute = b.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file) {
			btnUpload.sendKeys(absolute);
			waitForElement(btnConfirm);
			sleep(Values.sleepTime * 5);
			clickOn(btnConfirm, "Confirm Button");
		} else {
			fail("File not found : " + absolute);
		}
	}

	public void uploadFile(WebElement btnUpload, String imgPath) {
		String absolute = "";
		boolean file = true;
		try {
			File b = new File(imgPath);
			if (!b.isFile()) {
				file = false;
			}
			absolute = b.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file) {
			btnUpload.sendKeys(absolute);
		} else {
			fail("File not found : " + absolute);
		}
	}

	public void uploadSTLFile(WebElement btnUpload, String imgPath, WebElement btnContinue) {
		String absolute = "";
		boolean file = true;
		try {
			File b = new File(imgPath);
			if (!b.isFile()) {
				file = false;
			}
			absolute = b.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file) {
			btnUpload.sendKeys(absolute);
			waitForElement(btnContinue, 8);
			sleep(1000);
			if (isElementPresent(btnContinue)) {
				isElementDisplayed(btnContinue, "Continue Button in Warning dialog");
				clickOn(btnContinue, "Continue Button");
				sleep(1000);
			}
		} else {
			fail("File not found : " + absolute);
		}
	}

	public void uploadImage(WebElement btnUpload, String imgPath) {
		String absolute = "";
		boolean file = true;
		if (imgPath != null && !imgPath.equals("")) {
			try {
				File b = new File(imgPath);
				if (!b.isFile()) {
					file = false;
				}
				absolute = b.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (file) {
				btnUpload.sendKeys(absolute);
				sleep(1000);
			} else {
				fail("File not found : " + absolute);
			}
		}
	}

	public String generateRandomEmail(String validEmail, String userType) {
		String email = "";
		String[] split = validEmail.split("@");
		String rand = generateRandomString(5, "alphanumeric");
		switch (userType) {
		case "dentist":
			email = split[0] + "+" + rand + "_dent@";
			break;
		case "manufacturer":
			email = split[0] + "+" + rand + "_man@";
			break;
		case "designer":
			email = split[0] + "+" + rand + "des@";
			break;
		case "specialist":
			email = split[0] + "+" + rand + "_spl@";
			break;
		case "patient":
			email = split[0] + "+" + rand + "_pat@";
			break;
		default:
			email = split[0] + "+" + rand + "@";
			break;
		}
		return email + split[1];
	}

	public void validateActualEqualsExpected(String actual, String expected, String name) {
		actual = actual.trim();
		if (actual == null) {
			actual = "";
		}
		if (expected == null) {
			expected = "";
		}
		expected = expected.trim();
		if (actual.toLowerCase().contains(expected.toLowerCase())) {
			passed("Actual and expected values are same for " + name);
		} else {
			fail(name + ": Actual and expected values are not same, Actual is " + actual + " ,But expected is "
					+ expected);
		}
	}

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void validateTableRecordsInColumn(WebElement weTable, String data, int col, int startRowIndex) {
		try {
			boolean blnMatch = true;
			List<WebElement> weCols = weTable.findElements(By.xpath("./tr/td[" + col + "]"));
			for (int i = startRowIndex; i < weCols.size(); i++) {
				String strValue = weCols.get(i).getText().toLowerCase();
				data = data.toLowerCase();
				if (!strValue.contains(data)) {
					blnMatch = false;
					fail("Column -" + col + " ,Row -" + i + " Expected value is " + data + " , But Actual is "
							+ strValue);
				}
			}
			if (blnMatch) {
				passed("All the values in the column contains the search data as expected " + data);
			}

		} catch (Exception e) {
			fail("Unable to validate the Search results for cloumn " + col);
		}
	}

	public void validateTableRecordsInTwoColumns(WebElement weTable, String data, int col1, int col2,
			int startRowIndex) {
		try {
			boolean blnMatch = true;
			List<WebElement> weCols = weTable.findElements(By.xpath("./tr/td[" + col1 + "]"));
			for (int i = startRowIndex; i < weCols.size(); i++) {
				List<WebElement> weCols2 = weTable.findElements(By.xpath("./tr/td[" + col2 + "]"));
				String strValue = weCols.get(i).getText().toLowerCase();
				data = data.toLowerCase();
				String strValue2 = weCols2.get(i).getText().toLowerCase();
				data = data.toLowerCase();
				if (!strValue.contains(data)) {
					if (!strValue2.contains(data)) {
						blnMatch = false;
						fail("Column -" + col1 + " ,Row -" + i + " Expected value is " + data + " , But Actual is "
								+ strValue);
					}
				}
			}
			if (blnMatch) {
				passed("All the values in the column contains the search data as expected " + data);
			}

		} catch (Exception e) {
			fail("Unable to validate the Search results for cloumn " + col1);
		}
	}

	public String dateFormat(String fromFormat, String toFormat, String actualDate) {
		try {
			DateFormat originalFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
			DateFormat targetFormat = new SimpleDateFormat(toFormat);
			Date date = originalFormat.parse(actualDate);
			String formattedDate = targetFormat.format(date);
			return formattedDate;
		} catch (Exception e) {
			// fail("Exception while formatting the date "+e.getMessage());
			return "";
		}
	}

	public String addDate(String currDate, int days) {
		try {
			LocalDate date = LocalDate.parse(currDate);
			LocalDate newDate = date.plusDays(days);
			return newDate.toString();
		} catch (Exception e) {
			fail("Exception while Modifying the date " + e.getMessage());
			return "";
		}
	}

	public String getValueFromJson(String json, String keys) {
		String widgetTitle = "";
		try {
			widgetTitle = JsonPath.read(json, keys);
			System.out.println(widgetTitle);
		} catch (Exception e) {
			return "";
		}
		return widgetTitle;
	}

	public String getArrayValueFromJson(String json, String keys) {
		String widgetTitle = "";
		try {
			widgetTitle = JsonPath.read(json, keys);

			System.out.println(widgetTitle);
		} catch (Exception e) {
			return "";
		}
		return widgetTitle;
	}

	public HashMap<String, Object> convertJsonToMap(String filePath) {
		HashMap<String, Object> flattenedJsonMap = new HashMap<String, Object>();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			String flattenedJson = JsonFlattener.flatten(jsonObject.toString());
			flattenedJsonMap = (HashMap<String, Object>) JsonFlattener.flattenAsMap(jsonObject.toString());
		} catch (Exception e) {
			failAssert("Unable to convert json to hashmap, Exception is " + e.getMessage());
		}
		return flattenedJsonMap;
	}

	public String getValue(HashMap<String, Object> details, String key) {
		String value = null;
		try {
			value = details.get(key).toString();
			if (value == null) {
				return "";
			}
			return value;
		} catch (Exception e) {
			return "";
		}
	}

	public String getMultiValue(HashMap<String, Object> details, String key) {
		String value = "";
		try {
			for (int i = 0; i < 20; i++) {
				String str = details.get(key + "[" + i + "]").toString();
				if (value.equals("")) {
					value = str;
				} else {
					value = value + "," + str;
				}
			}
			return value;
		} catch (Exception e) {
			return value;
		}
	}

	public void expandSection(WebElement weSection, String sectionName) {
		try {
			WebElement weSectionHeader = weSection.findElement(By.xpath("./.."));
			String expanded = weSectionHeader.getAttribute("aria-expanded");
			if (expanded.toLowerCase().contains("false")) {
				clickOn(weSection, sectionName);
				passed(sectionName + " Section is expanded");
			} else {
				passed(sectionName + " Section is already expanded");
			}
		} catch (Exception e) {
			fail("Unable to expand the Section, Exception caught " + e.getMessage());
		}
	}

	public void selectCheckboxFromTable(WebElement table, String checkboxOptions) {
		String[] strCheckBoxOptions = checkboxOptions.split(",");
		for (int i = 0; i < strCheckBoxOptions.length; i++) {
			List<WebElement> weTexts = table.findElements(By.xpath("./tbody/tr/td[2]"));
			List<WebElement> allCheckboxs = table.findElements(By.xpath("./tbody/tr/td[1]//input"));
			boolean blnFound = false;
			for (int j = 0; j < weTexts.size(); j++) {
				allCheckboxs = table.findElements(By.xpath("./tbody/tr/td[1]//input"));
				if (weTexts.get(j).getText().trim().equalsIgnoreCase(strCheckBoxOptions[i])) {
					allCheckboxs.get(j).click();
					passed("Selected the check box " + strCheckBoxOptions[i]);
					blnFound = true;
				}
			}
			if (!blnFound) {
				fail("Check box with text " + strCheckBoxOptions[i] + " not found");
			}
		}

	}

}

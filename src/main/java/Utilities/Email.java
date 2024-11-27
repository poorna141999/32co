package Utilities;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.constans.Values;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Email  extends Common{

	static Folder inbox = null;
	static Store store = null;
	static WebDriver browser= null;
	public  Message[] check(String user) {
		Message[] messages = null;
		try {
			String host = Values.frameworkProperty.getProperty("mail.imap.host", "imap.gmail.com");
			String password = Values.frameworkProperty.getProperty("mail.password."+user, "");
			if(password.equals("")) {
				fail("Password  is not provided in the config file for the user "+user);
			}
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.starttls.enable", "true");
			properties.put("mail.imap.ssl.trust", host);

			Session emailSession = Session.getDefaultInstance(properties);

			// create the imap store object and connect to the imap server
			store = emailSession.getStore("imaps");

			store.connect(host, user, password);

			// create the inbox object and open it
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			messages = inbox.getMessages();
			System.out.println("messages.length---" + messages.length);


		} catch (NoSuchProviderException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		return messages;
	}

	public  String getTheLatestEmailMessage(Message[] messages,String subject,String from,Date sentAfter) {
		boolean found = false;
		try {
			for (int i = messages.length-1; i >0; i--) {
				Message message = messages[i];
				Date date1 = message.getReceivedDate();
				if(sentAfter.compareTo(date1)==-1) {
					String actSubject = message.getSubject();
					String actFrom = message.getFrom()[0].toString();
					if(actSubject.contains(subject)&&actFrom.contains(from)) {
						found = true;
						return getTextFromMessage(messages[i]);
					}
				}else {
					break;
				}
			}
			if(!found) {
				fail("No new email found in the inbox with the subject "+subject + " and from "+from );
			}
		} catch (NoSuchProviderException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		return null;
	}

	public static void closeEmail() {
		try {
			inbox.close(false);
			store.close();
		}catch(Exception e) {

		}
	}

	public void verifyDentistEmailAfterRegister(String userEmail){
		try {
			Thread.sleep(Values.EmailWaitTime);
			String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
			//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Message[] emails = check(userEmail);
			String emailBodyText = getTheLatestEmailMessage(emails, "Verify your email address", "hello@32co.com", Values.currentDateTime);
			//String emailBodyText = getTheLatestEmailMessage(emails, "Verify your email address", "hello@32co.com", formatter.parse("19-01-2023"));
			if(emailBodyText!=null) {
				String url = extractUrls(emailBodyText).get(0);
				if(url.endsWith("Book")) {
					url = url.substring(0, url.length()-4);
				}
				browser = getDriver("Chrome");
				browser.get(url);
				if(isElementPresent(By.id("basic_email"))) {
					passed("Validated the Email successfully , Dentist Login page is displayed as expected");
					takeScreenshot();
				}else {
					fail("Unable to validate the email, Login page is not displayed after clicking on verify link");
					takeScreenshot();
				}
				browser.quit();
			}else {
				fail("No Email recieved after waiting for "+Values.EmailWaitTime + " miliseconds");
			}
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}

	public void verifyDentistEmailAfterInviteSent(String userEmail){
		try {
			Thread.sleep(Values.EmailWaitTime);
			String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
			//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Message[] emails = check(userEmail);
			String emailBodyText = getTheLatestEmailMessage(emails, "Welcome to 32Co!! Book your onboarding session now", "hello@32co.com", Values.currentDateTime);
			//String emailBodyText = getTheLatestEmailMessage(emails, "Verify your email address", "hello@32co.com", formatter.parse("19-01-2023"));
			if(emailBodyText!=null) {
				List<String> urls = extractUrls(emailBodyText);
				List<String> coUrls = urls.stream().filter(url->url.contains("32co")).collect(Collectors.toList());
				if(coUrls.size()>0) {
					String expEnv = Values.frameworkProperty.getProperty("env", "alpha");
					if(!coUrls.get(0).contains(expEnv)) {
						fail("Email does not contain the url of expected environment, Expected environment is "+expEnv + ", but actual is "+coUrls.get(0));
					}
					browser = getDriver("Chrome");
					browser.get(coUrls.get(0));
					if(isElementPresent(By.id("basic_email"))) {
						passed("Validate the EMAIL Link : Login page is displayed as expected");
						takeScreenshot();
					}else {
						fail("Validate the EMAIL Link : Login page is not displayed as expected");
						takeScreenshot();
					}
				}
				browser.quit();
			}else {
				fail("No Email recieved after waiting for "+Values.EmailWaitTime + " miliseconds");
			}
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}

	public void verifyEmailAfterForgotPassword(String userEmail){
		try {
			passed("******************************************Validating the Email ForgotPassword******************************************");
			Thread.sleep(Values.EmailWaitTime);
			String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
			//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Message[] emails = check(userEmail);
			String emailBodyText = getTheLatestEmailMessage(emails, "Reset your password", "hello@32co.com", Values.currentDateTime);
			//String emailBodyText = getTheLatestEmailMessage(emails, "Verify your email address", "hello@32co.com", formatter.parse("19-01-2023"));
			if(emailBodyText!=null) {
				String url = extractUrls(emailBodyText).get(0);
				if(url.endsWith("Book")) {
					url = url.substring(0, url.length()-4);
				}
				browser = getDriver("Chrome");
				browser.get(url);
				Thread.sleep(2000);
				if(isElementPresent(By.id("basic_confirmPassword"))) {
					String password = generateRandomString(10, "alpha") + "#1";
					browser.findElement(By.id("basic_password")).sendKeys(password);
					browser.findElement(By.id("basic_confirmPassword")).sendKeys(password);
					browser.findElement(By.xpath("//button/span[text()='Reset password']")).click();
					passed("Validated the Email successfully , Dentist Reset Password page is displayed");
					takeScreenshot();
					//waitForElement(By.xpath("//div[@class='ant-notification-notice-description']"));
					Thread.sleep(2000);
					if (isElementPresent(By.xpath("//div[@class='ant-notification-notice-description']"))) {
						passed("Toaster Message is displayed as expected");
						String actHeader = browser.findElement(By.xpath("//div[@class='ant-notification-notice-message']")).getText();
						String actDesc =  browser.findElement(By.xpath("//div[@class='ant-notification-notice-description']")).getText();
						String expMesssageHeader = "Password reset";
						String expMessageDesc = "Password updated successfully!, Please login with new password";
						if(!expMesssageHeader.equals("")) {
							if(actHeader.contains(expMesssageHeader)) {
								passed("Toaster message header is displayed as expected "+actHeader);
							}else {
								fail("Toaster message header is not as expected , Actual is "+actHeader +", But expected is "+expMesssageHeader);
							}
						}

						if(!expMessageDesc.equals("")) {
							if(actDesc.contains(expMessageDesc)) {
								passed("Toaster message header is displayed as expected "+actDesc);
							}else {
								fail("Toaster message header is not as expected , Actual is "+actDesc +", But expected is "+expMessageDesc);
							}
						}
					}else {
						fail("Toaster Message is not displayed");
					}	
					takeScreenshot();

				}else {
					fail("Unable to validate the email, Dentist Reset Password page is not displayed");
					takeScreenshot();
				}
				browser.quit();
			}else {
				fail("No Email recieved after waiting for "+Values.EmailWaitTime + " miliseconds");
			}
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	public void verifyEmailAfterPateintProposal(String userEmail,String msg){
		try {
			passed("******************************************Validating the Email in Patient Email******************************************");
			Thread.sleep(Values.EmailWaitTime);
			String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
			Message[] emails = check(userEmail);
			String emailBodyText = getTheLatestEmailMessage(emails, "Your Personal Treatment Proposal", "hello@32co.com", Values.currentDateTime);
			//String emailBodyText = getTheLatestEmailMessage(emails, "Verify your email address", "hello@32co.com", formatter.parse("19-01-2023"));
			String url = "";
			if(emailBodyText!=null) {
				List<String> urls = extractUrls(emailBodyText);
				for(String currurl : urls) {
					if(currurl.contains(Values.frameworkProperty.getProperty("env","alpha")) && currurl.contains("patients/proposal")) {
						url = currurl;
						break;
					}
				}
				if(url.endsWith("Book")) {
					url = url.substring(0, url.length()-4);
				}
				
				if(url.endsWith("Your")) {
					url = url.substring(0, url.length()-4);
				}
				if(!url.equals("")) {
					browser = getDriver("Chrome");
					browser.get(url);
					Thread.sleep(4000);
					
					if(isElementPresent(By.xpath("//button/span[text()='Reply']"))) {
						List<WebElement> wesRadio = browser.findElements(By.xpath("//span[text()='Select this quote']"));
						if(wesRadio.size()>0) {
							wesRadio.get(0).click();
						}
						browser.findElement(By.id("message")).sendKeys(msg);
						browser.findElement(By.xpath("(//button/span[text()='Reply'])[2]")).click();
						takeScreenshot();
						passed("Entered Message "+msg);
						passed("Clicked on the Reply button");
						takeScreenshot();
					}else {
						fail("Personal Treatment Proposal approve page is not displayed");
						takeScreenshot();
					}
					browser.quit();
				}else {
					fail("URL Not found in the email to click and accept");
				}
			}else {
				fail("No Email recieved after waiting for "+Values.EmailWaitTime + " miliseconds");
			}
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	public void verifyEmailAfterContentCourse(String userEmail){
		try {
			passed("******************************************Validating the Email in Patient Email******************************************");
			Thread.sleep(Values.EmailWaitTime/3);
			String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
			Message[] emails = check(userEmail);
			String emailBodyText = getTheLatestEmailMessage(emails, "New Personalised Course", "education@32co.com", Values.currentDateTime);
			String url = "";
			if(emailBodyText!=null) {
				List<String> urls = extractUrls(emailBodyText);
				for(String currurl : urls) {
					if(currurl.contains(Values.frameworkProperty.getProperty("env","alpha")) && currurl.contains("dentist/education")) {
						url = currurl;
						break;
					}
				}
				if(url.endsWith("Book")) {
					url = url.substring(0, url.length()-4);
				}
				
				if(url.endsWith("Your")) {
					url = url.substring(0, url.length()-4);
				}
				if(!url.equals("")) {
					browser = getDriver("Chrome");
					browser.get(url);
					Thread.sleep(4000);
					takeScreenshot();
					browser.quit();
				}else {
					fail("URL Not found in the email to click and accept");
				}
			}else {
				fail("No Email recieved after waiting for "+Values.EmailWaitTime + " miliseconds");
			}
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}

	public void verifyEmailAfterTreatmentCheckIn(String userEmail){
		try {
			passed("******************************************Validating the Email in Patient Email******************************************");
			Thread.sleep(Values.EmailWaitTime);
			String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
			Message[] emails = check(userEmail);
			String emailBodyText = getTheLatestEmailMessage(emails, "First Clear Aligner", "hello@32co.com", Values.currentDateTime);
			//String emailBodyText = getTheLatestEmailMessage(emails, "Verify your email address", "hello@32co.com", formatter.parse("19-01-2023"));
			String url = "";
			if(emailBodyText!=null) {
				List<String> urls = extractUrls(emailBodyText);
				for(String currurl : urls) {
					if(currurl.contains(Values.frameworkProperty.getProperty("env","alpha")) && currurl.contains("/patients/check-in")) {
						url = currurl;
						break;
					}
				}
				if(url.endsWith("Book")) {
					url = url.substring(0, url.length()-4);
				}
				if(!url.equals("")) {
					browser = getDriver("Chrome");
					browser.get(url);
					Thread.sleep(4000);
					
					if(isElementPresent(By.id("submit-treatment-checkins"))) {
						browser.findElement(By.id("alignerStage")).sendKeys("5");
						browser.findElement(By.id("wearingDuration")).sendKeys("2");
						browser.findElement(By.xpath("//div[@id='havingConcerns']//span[text()='Yes']")).click();
						browser.findElement(By.id("concerns")).sendKeys("My Concerns are");
						takeScreenshot();
						browser.findElement(By.id("submit-treatment-checkins")).click();
						takeScreenshot();
						passed("Entered al the details and Clicked on the Submit button");
						takeScreenshot();
						waitForElement(By.xpath("//div[@class='success-message-container']"));
						if(isElementPresent(By.xpath("//div[@class='success-message-container']"))) {
							passed("Treatment Check In Successfull messge is displayed ");
						}else {
							fail("Treatment Check In Successfull messge is not displayed ");
						}
					}else {
						fail("Check In page is not displayed");
						takeScreenshot();
					}
					browser.quit();
				}else {
					fail("URL Not found in the email to click and accept");
				}
			}else {
				fail("No Email recieved after waiting for "+Values.EmailWaitTime + " miliseconds");
			}
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}

	protected Boolean waitForElement(By by) {
		try {
			new WebDriverWait(browser, Values.elementLoadWaitTime)
			.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public static boolean isElementPresent(By by) {
		try {
			return browser.findElement(by).isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		} catch (Exception ex2) {
			return false;
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

	private static String getTextFromMessage(Message message) throws MessagingException, IOException {
		if (message.isMimeType("text/plain")) {
			return message.getContent().toString();
		} 
		if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			return getTextFromMimeMultipart(mimeMultipart);
		}
		if (message.isMimeType("TEXT/HTML; charset=UTF-8")) {
			return message.getContent().toString();
		}
		return "";
	}

	private static String getTextFromMimeMultipart(
			MimeMultipart mimeMultipart) throws IOException, MessagingException {

		int count = mimeMultipart.getCount();
		if (count == 0)
			throw new MessagingException("Multipart with no body parts not supported.");
		boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
		// if (multipartAlt) {
		// alternatives appear in an order of increasing 
		// faithfulness to the original content. Customize as req'd.
		//return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
		String result = "";
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			result += getTextFromBodyPart(bodyPart);
		}
		return result;
	}

	private static String getTextFromBodyPart(
			BodyPart bodyPart) throws IOException, MessagingException {

		String result = "";
		if (bodyPart.isMimeType("text/plain")) {
			result = (String) bodyPart.getContent();
		} else if (bodyPart.isMimeType("text/html")) {
			String html = (String) bodyPart.getContent();
			result = org.jsoup.Jsoup.parse(html).text();
		} else if (bodyPart.getContent() instanceof MimeMultipart){
			result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
		}
		return result;
	}

	public static List<String> extractUrls(String text)
	{
		List<String> containedUrls = new ArrayList<>();
		String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher urlMatcher = pattern.matcher(text);

		while (urlMatcher.find())
		{
			containedUrls.add(text.substring(urlMatcher.start(0),
					urlMatcher.end(0)));
		}

		return containedUrls;
	}


	public static WebDriver getDriver(String browserName) {
		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			System.out.println("Executing on FireFox on Server");
		}
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions optionsC = new ChromeOptions();
			//			optionsC.addArguments(Arrays.asList("disable-infobars", "ignore-certificate-errors", "start-maximized",
			//					"use-fake-ui-for-media-stream"));
			driver = new ChromeDriver(optionsC);
			System.out.println("Executing on CHROME on Server");

		}
		if (browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability("ACCEPT_SSL_CERTS", true);

			driver = new InternetExplorerDriver(capabilities);
			System.out.println("Executing on Internet Explorer on Server");
		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		return driver;
	}
}

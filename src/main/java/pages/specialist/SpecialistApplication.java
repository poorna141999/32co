package pages.specialist;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import Utilities.Data;
import Utilities.constans.Values;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SpecialistApplication {

	private static WebDriver browser;
	private String url;
	private Data data;

	public SpecialistApplication(Data data) {
		String strbrowser = Values.frameworkProperty.getProperty("Browser","Chrome");
		browser = getDriver(strbrowser);
		this.url = Values.frameworkProperty.getProperty("URL","");
		Values.driver = browser;
		this.data = data;
	}

	public SpecialistLoginPage open32CoApplication() {
		try {
			browser.get(url);
			browser.manage().window().maximize();
			
		} catch (Exception e) {
			System.out.println("Problem Opening the URL" + e.getMessage());
		}
		return new SpecialistLoginPage(browser, data);
	}
	
	public SpecialistRegisterPage openSpecialistJoinus() {
		try {
			String url = Values.frameworkProperty.getProperty("specialist.url.joinus","");
			browser.get(url);
			browser.manage().window().maximize();
			
		} catch (Exception e) {
			System.out.println("Problem Opening the URL" + e.getMessage());
		}
		return new SpecialistRegisterPage(browser, data);
	}

	public WebDriver getDriver(String browserName) {
		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("Executing on FireFox on Server");
		}
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions optionsC = new ChromeOptions();
			optionsC.addArguments(Arrays.asList("disable-infobars", "ignore-certificate-errors", "start-maximized",
					"use-fake-ui-for-media-stream"));
			driver = new ChromeDriver(optionsC);
			System.out.println("Executing on CHROME on Server");

		}
		if (browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability("ACCEPT_SSL_CERTS", true);

			driver = new InternetExplorerDriver(capabilities);
			System.out.println("Executing on Internet Explorer on Server");
		}
		if (browserName.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.setCapability("useAutomationExtension", false);
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(options);
			System.out.println("Executing on Edge browser on Server");
		}
		driver.manage().timeouts().implicitlyWait(Values.implicitlyWaitTime, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Values.pageLoadWaitTime, TimeUnit.SECONDS);
		return driver;
	}

	public void close() {
		browser.quit();
	}
}

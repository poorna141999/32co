package manufacturer;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.manufacturer.ManufacturerApplication;
import pages.manufacturer.ManufacturerDashboardPage;
import pages.manufacturer.ManufacturerLoginPage;
import pages.manufacturer.ManufacturerRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignUpPositiveTestCases  extends Common{
	
	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public ManufacturerApplication ManufacturerApplication;
	public ManufacturerLoginPage ManufacturerLoginPage;
	public ManufacturerDashboardPage ManufacturerDashboardPage;
	public ManufacturerRegisterPage ManufacturerRegisterPage;
	
	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Manufacturer");
		datasets = data.getDataSets(name);
	}

	@Test(groups = {"Regression", "ManufacturerLoginSignUp","Positive"})
	public void ManufacturerLoginToApplication() {

			datasetStart("ManufacturerLoginToApplication");
			
			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerLoginPage = ManufacturerApplication.open32CoApplication();
			ManufacturerLoginPage.loginToApplication();

			ManufacturerApplication.close();
	}
	
	@Test(groups = {"Functional", "ManufacturerLoginSignUp","Positive"})
	public void ManufacturerSignUpToApplication() {
		data.setColIndex("ManufacturerSignUpToApplication");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerRegisterPage = ManufacturerApplication.openManufacturerJoinus();
			ManufacturerRegisterPage.registerNewUser(data);
			ManufacturerRegisterPage.validateRegister();
			
			ManufacturerApplication.close();
			datasetEnd();
		}
	}
	
}

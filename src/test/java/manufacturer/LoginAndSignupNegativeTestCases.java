package manufacturer;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.manufacturer.ManufacturerApplication;
import pages.manufacturer.ManufacturerDashboardPage;
import pages.manufacturer.ManufacturerLoginPage;
import pages.manufacturer.ManufacturerRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignupNegativeTestCases extends Common{

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
	
	

	@Test(groups = {"Regression", "ManufacturerLoginSignUp","Negative"})
	public void ManufacturerSignUpMandatoryFieldValidationFirstSection() {
			datasetStart("ManufacturerSignUpMandatoryFieldValidationFirstSection");
			
			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerRegisterPage = ManufacturerApplication.openManufacturerJoinus();
			ManufacturerRegisterPage.validateMandatoryFieldFirstSection();
			
			ManufacturerApplication.close();

	}
	
	@Test(groups = {"Regression", "ManufacturerLoginSignUp","Negative"})
	public void ManufacturerSignUpNegativeFieldValidationFirstSection() {

			datasetStart("ManufacturerSignUpNegativeFieldValidationFirstSection");
			
			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerRegisterPage = ManufacturerApplication.openManufacturerJoinus();
			ManufacturerRegisterPage.validateNegativeFieldFirstSection();
			
			ManufacturerApplication.close();
	}
	
	@Test(groups = {"Regression", "ManufacturerLoginSignUp","Negative"})
	@Ignore
	public void ManufacturerValidateLoginNegativeTestCase() {
		data.setColIndex("ManufacturerValidateLoginNegativeTestCase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			ManufacturerApplication = new ManufacturerApplication(data);
			ManufacturerLoginPage = ManufacturerApplication.open32CoApplication();
			ManufacturerLoginPage.loginNegativeValidation();
			
			ManufacturerApplication.close();
			datasetEnd();
		}
	}
}

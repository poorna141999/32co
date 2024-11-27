package designer;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.designer.DesignerApplication;
import pages.designer.DesignerDashboardPage;
import pages.designer.DesignerLoginPage;
import pages.designer.DesignerRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignupNegativeTestCases extends Common{

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public DesignerApplication DesignerApplication;
	public DesignerLoginPage DesignerLoginPage;
	public DesignerDashboardPage DesignerDashboardPage;
	public DesignerRegisterPage DesignerRegisterPage;
	
	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Designer");
		datasets = data.getDataSets(name);
	}
	
	

	@Test(groups = {"Regression", "Designer","Negative"})
	public void DesignerSignUpMandatoryFieldValidationFirstSection() {

			datasetStart("DesignerSignUpMandatoryFieldValidationFirstSection");
			
			DesignerApplication = new DesignerApplication(data);
			DesignerRegisterPage = DesignerApplication.openDesignerJoinus();
			DesignerRegisterPage.validateMandatoryFieldFirstSection();
			
			DesignerApplication.close();
	}
	
	@Test(groups = {"Regression", "Designer","Negative"})
	public void DesignerSignUpNegativeFieldValidationFirstSection() {

			datasetStart("DesignerSignUpNegativeFieldValidationFirstSection");
			
			DesignerApplication = new DesignerApplication(data);
			DesignerRegisterPage = DesignerApplication.openDesignerJoinus();
			DesignerRegisterPage.validateNegativeFieldFirstSection();
			
			DesignerApplication.close();
	}
	
	@Test(groups = {"Regression", "Designer","Negative"})
	@Ignore
	public void DesignerValidateLoginNegativeTestCase() {
		data.setColIndex("DesignerValidateLoginNegativeTestCase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			DesignerApplication = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerLoginPage.loginNegativeValidation();
			
			DesignerApplication.close();
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Designer","Negative"})
	public void DesignerValidateRegisterExistingEmail() {

			datasetStart("DesignerValidateRegisterExistingEmail");
			
			DesignerApplication = new DesignerApplication(data);
			DesignerRegisterPage = DesignerApplication.openDesignerJoinus();
			DesignerRegisterPage.registerExistingUser();
			
			DesignerApplication.close();
	}
}

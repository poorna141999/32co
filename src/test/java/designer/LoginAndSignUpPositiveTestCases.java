package designer;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import pages.designer.DesignerApplication;
import pages.designer.DesignerDashboardPage;
import pages.designer.DesignerLoginPage;
import pages.designer.DesignerRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class LoginAndSignUpPositiveTestCases  extends Common{
	
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
	
	

	@Test(groups = {"Regression", "Designer","Positive"})
	public void DesignerLoginToApplication() {

			datasetStart("DesignerLoginToApplication");
			
			DesignerApplication = new DesignerApplication(data);
			DesignerLoginPage = DesignerApplication.open32CoApplication();
			DesignerLoginPage.loginToApplication();

			DesignerApplication.close();

	}
	
	@Test(groups = {"Functional", "Designer","Positive"})
	public void DesignerSignUpToApplication() {
		data.setColIndex("DesignerSignUpToApplication");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);
			
			DesignerApplication = new DesignerApplication(data);
			DesignerRegisterPage = DesignerApplication.openDesignerJoinus();
			DesignerRegisterPage.registerNewUser(data);
			DesignerRegisterPage.validateRegister();
			
			DesignerApplication.close();
			datasetEnd();
		}
	}
	
}

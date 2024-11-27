package dentist;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistPatientsPage;
import pages.dentist.DentistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class DentistPatientsTestCases  extends Common{

	public static int count = 1;
	public Data data;
	public ArrayList<String> datasets;
	public DentistApplication DentistApplication;
	public DentistLoginPage DentistLoginPage;
	public DentistRegisterPage DentistRegisterPage;
	public DentistDashboardPage DentistDashboardPage;
	public DentistPatientsPage DentistPatientsPage;
	public Email email = new Email();

	@BeforeMethod
	public void testStart(Method method) {
		String name = method.getName();
		data = new Data("Dentist");
		datasets = data.getDataSets(name);
	}

	@Test(groups = {"Regression", "Dentist","solo"})
	public void DenitstCreateSoloTestcase() {
		data.setColIndex("DenitstCreateSoloTestcase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.startSoloCase();
			//DentistPatientsPage.createHashMapfromJsonFile(dataset);
			DentistPatientsPage.createSoloCase(data);
			DentistApplication.close();
			datasetEnd();
		}
	}

	@Test(groups = {"Regression", "Dentist","solo"})
	public void DenitstValidateMandatoryFieldsSoloCase() {
		datasetStart("DenitstValidateMandatoryFieldsSoloCase");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistPatientsPage = DentistDashboardPage.startSoloCase();
		DentistPatientsPage.validateMandatoryFields();
		DentistApplication.close();
	}

	@Test(groups = {"Regression", "Dentist","solo"})
	public void DenitstValidateOtherMandatoryFieldsSoloCase() {
		datasetStart("DenitstValidateOtherMandatoryFieldsSoloCase");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistPatientsPage = DentistDashboardPage.startSoloCase();
		DentistPatientsPage.validateOtherMandatoryFields();
		DentistApplication.close();
	}

	@Test(groups = {"Regression", "Dentist","solo"})
	public void DenitstValidateSaveAsDraftSoloCase() {
		datasetStart("DenitstValidateSaveAsDraftSoloCase");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistPatientsPage = DentistDashboardPage.startSoloCase();
		DentistPatientsPage.validateSaveAsDraftSolo();
		DentistPatientsPage.validateArchive();
		DentistApplication.close();
	}

	@Test(groups = {"Regression", "Dentist","solo"})
	public void DenitstValidateMandatoryUploadFiles() {
		datasetStart("DenitstValidateMandatoryUploadFiles");

		DentistApplication = new DentistApplication(data);
		DentistLoginPage = DentistApplication.open32CoApplication();
		DentistDashboardPage = DentistLoginPage.loginToApplication();
		DentistPatientsPage = DentistDashboardPage.startSoloCase();
		DentistPatientsPage.validateMandatoryUploadFilesSolo();
		DentistApplication.close();
	}


	@Test(groups = {"Regression", "Dentist","duo"})
	public void DenitstCreateDuoTestcase() {
		data.setColIndex("DenitstCreateDuoTestcase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.startDuoCase();
			DentistPatientsPage.createDuoCase();
			DentistApplication.close();
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Dentist","duo"})
	public void DenitstValidateMandatoryFieldsDuoCase() {
		data.setColIndex("DenitstValidateMandatoryFieldsDuoCase");

		for (String dataset : datasets) {
			data.setIndex(dataset);
			datasetStart(dataset);

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.startDuoCase();
			DentistPatientsPage.validateMandatoryFieldsForDuo();
			DentistApplication.close();
			datasetEnd();
		}
	}
	
	@Test(groups = {"Regression", "Dentist","duo"})
	public void DenitstDuoValidateMandatoryUploadFiles() {
			datasetStart("DenitstDuoValidateMandatoryUploadFiles");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.startDuoCase();
			DentistPatientsPage.validateMandatoryUploadFilesDuo();
			DentistApplication.close();
			
	}
	
	@Test(groups = {"Regression", "Dentist","duo"})
	public void DenitstDuoValidateSaveAsDraft() {
			datasetStart("DenitstDuoValidateSaveAsDraft");

			DentistApplication = new DentistApplication(data);
			DentistLoginPage = DentistApplication.open32CoApplication();
			DentistDashboardPage = DentistLoginPage.loginToApplication();
			DentistPatientsPage = DentistDashboardPage.startDuoCase();
			DentistPatientsPage.validateSaveAsDraftDuo();
			DentistApplication.close();
			
	}
}
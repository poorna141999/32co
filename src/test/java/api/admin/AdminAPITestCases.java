package api.admin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.Data;
import Utilities.Email;
import Utilities.RestAPI;
import Utilities.constans.Values;
import api.AdminAPIModule;
import api.DentistAPIModule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pages.dentist.DentistApplication;
import pages.dentist.DentistDashboardPage;
import pages.dentist.DentistLoginPage;
import pages.dentist.DentistPatientsPage;
import pages.dentist.DentistRegisterPage;

@Listeners({ Utilities.TestListener.class })
public class AdminAPITestCases  extends Common{

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
		data = new Data("API");
		datasets = data.getDataSets(name);
	}

	
	@Test(groups = {"API", "Admin"})
	public void GetadminSubmissions() throws IOException {
		datasetStart("GetadminSubmissions");
		RestAPI RestAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminSubmissions(RestAPI);		
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetadminSubmissionPatientDetails() throws IOException {
		datasetStart("GetadminSubmissionPatientDetails");
		RestAPI RestAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminPatientDetails(RestAPI);		
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetadminSubmissionDetails() throws IOException {
		datasetStart("GetadminSubmissionDetails");
		RestAPI RestAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminSubmissionDetails(RestAPI);		
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminSubmissionTimelines() throws IOException {
		datasetStart("GetAdminSubmissionTimelines");
		RestAPI RestAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminSubmissionTimelines(RestAPI);		
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminSubmissionOrders() throws IOException {
		datasetStart("GetAdminSubmissionOrders");
		RestAPI RestAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminSubmissionOrders(RestAPI);		
	}

	
	@Test(groups = {"API", "Admin"})
	public void GetAdminHealth() {
		datasetStart("GetAdminHealth");
		RestAPI RestAPI = new RestAPI("admin");
		Response response =RestAPI.getResponse("/health");		
		System.out.println(response.getStatusCode());
		String strBody = response.getBody().asString();
		System.out.println(strBody);
		
	}

	@Test(groups = {"API", "Admin"})
	public void PostAdminSubmissionArchive() throws IOException {
		datasetStart("PostAdminSubmissionArchive");
		RestAPI dentistAPI = new RestAPI("dentist");
		DentistAPIModule DentistAPIModule = new DentistAPIModule();
		DentistAPIModule.postCreateSolo(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solodraft.json");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.postAdminSubmissionArchive(adminAPI);
		
	}
	
	
	@Test(groups = {"API", "Admin"})
	public void PostAdminSubmissionAssignAndChangeStatus() throws IOException {
		datasetStart("PostAdminSubmissionStatus");
		RestAPI dentistAPI = new RestAPI("dentist");
		DentistAPIModule DentistAPIModule = new DentistAPIModule();
		DentistAPIModule.postCreateSolo(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solo.json");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.postAdminSubmissionAssignDuo(adminAPI);
		AdminAPIModule.putAdminSubmissionStatus(adminAPI);		
	}
	
	@Test(groups = {"API", "Admin"})
	public void PostAdminSubmissionSTLs() throws IOException {
		datasetStart("PostAdminSubmissionSTLs");
		RestAPI dentistAPI = new RestAPI("dentist");
		DentistAPIModule DentistAPIModule = new DentistAPIModule();
		DentistAPIModule.postCreateSolo(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solo.json");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.putAdminSubmissionStls(adminAPI, ".\\src\\test\\resources\\json\\adminapi\\stls.json");
	}

	@Test(groups = {"API", "Admin"})
	public void PostAdminSubmissionPriority() throws IOException {
		datasetStart("PostAdminSubmissionPriority");
		RestAPI dentistAPI = new RestAPI("dentist");
		DentistAPIModule DentistAPIModule = new DentistAPIModule();
		DentistAPIModule.postCreateSolo(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solo.json");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.putAdminSubmissionPriority(adminAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void PostAdminSubmissionArchiveSTLs() throws IOException {
		datasetStart("PostAdminSubmissionArchiveSTLs");
		RestAPI dentistAPI = new RestAPI("dentist");
		DentistAPIModule DentistAPIModule = new DentistAPIModule();
		DentistAPIModule.postCreateSolo(dentistAPI,".\\src\\test\\resources\\json\\dentistapi\\solo.json");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.putAdminSubmissionStls(adminAPI, ".\\src\\test\\resources\\json\\adminapi\\stls.json");
		AdminAPIModule.putAdminSubmissionArchiveSTLs(adminAPI);
	}
	
	//TODO Approve Unsuitable Need more information
	
	//TODO admin register - Need more information whether this can be automated

	//TODO admin signout
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminAllUsers() throws IOException {
		datasetStart("GetAdminAllUsers");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminAllUsers(adminAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminUsersAssignable() throws IOException {
		datasetStart("GetAdminUsersAssignable");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminUsersAssignable(adminAPI);
	}
	
	//TODO PUT /v1/admin/users/{id} 

	@Test(groups = {"API", "Admin"})
	public void PutAdminUpdateUserStatus() throws IOException {
		datasetStart("PutAdminUpdateUserStatus");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.putAdminUpdateUserStatus(adminAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void PutAdminInviteDentist() throws IOException {
		datasetStart("PutAdminInviteDentist");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.putAdminInviteDentist(adminAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminSubmissions() throws IOException {
		datasetStart("GetAdminSubmissions");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminUserSubmissions(adminAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminusersOrders() throws IOException {
		datasetStart("GetAdminusersOrders");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminUserOrders(adminAPI);
	}
	
	/*
	 * Below API covered in this test case
	 * /v1/admin/users/{id}/education-contents
	 * /v1/admin/users/{id}/education-contents/{contentId}
	 */
	
	@Test(groups = {"API", "Admin"})
	public void GetAdminusersEducationContentsAndPut() throws IOException {
		datasetStart("GetAdminusersEducationContentsAndPut");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminUserEducationContents(adminAPI);
	}
	
	/*
	 * Below API covered in this test case
	 * /v1/admin/practices GET
	 * /v1/admin/practices/assign POST
	 */
	
	@Test(groups = {"API", "Admin"})
	public void AdminPracticesAssign() throws IOException {
		datasetStart("AdminPracticesAssign");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.assignPractice(adminAPI);
	}


	@Test(groups = {"API", "Admin"})
	public void GetAdminPracticesScanner() throws IOException {
		datasetStart("GetAdminPracticesScanner");
		RestAPI RestAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminPracticesScanner(RestAPI);
	}
	
	/* /v1/admin/practices POST   
	 * /v1/admin/practices/{id} PUT
	 * /v1/admin/practices/{id} DELETE
	 * */
	
	@Test(groups = {"API", "Admin"})
	public void crudOpeartionsPractice() throws IOException {
		datasetStart("crudOpeartionsPractice");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.createNewPractice(adminAPI,".\\src\\test\\resources\\json\\adminapi\\practice.json");
		
		RestAPI adminAPIput = new RestAPI("admin");
		AdminAPIModule.updateNewPractice(adminAPIput,".\\src\\test\\resources\\json\\adminapi\\practiceput.json");
		
		RestAPI adminAPIdel = new RestAPI("admin");
		AdminAPIModule.deletePractice(adminAPIdel);

	}
	
	@Test(groups = {"API", "Admin"})
	public void crudOpeartionsReleases() throws IOException {
		datasetStart("crudOpeartionsPractice");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.createRelease(adminAPI,".\\src\\test\\resources\\json\\adminapi\\release.json");
		
		RestAPI adminAPIget = new RestAPI("admin");
		AdminAPIModule.getReleases(adminAPIget);
		
		
		RestAPI adminAPIput = new RestAPI("admin");
		AdminAPIModule.updateRelease(adminAPIput,".\\src\\test\\resources\\json\\adminapi\\release.json");
		
		RestAPI adminAPIdel = new RestAPI("admin");
		AdminAPIModule.deleteRelease(adminAPIdel);

	}
	
	@Test(groups = {"API", "Admin"})
	public void crudGroups() throws IOException {
		datasetStart("crudGroups");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.createGroup(adminAPI);
		
		RestAPI adminAPIput = new RestAPI("admin");
		AdminAPIModule.updateGroup(adminAPIput);
		
		RestAPI adminAPIdel = new RestAPI("admin");
		AdminAPIModule.deleteGroup(adminAPIdel);

	}
	
	
	@Test(groups = {"API", "Admin"})
	public void getGroups() throws IOException {
		datasetStart("searchGroups");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getGroups(adminAPI);

		RestAPI tagAPI = new RestAPI("admin");
		AdminAPIModule.getGroupTag(tagAPI);
		
		RestAPI memberAPI = new RestAPI("admin");
		AdminAPIModule.getGroupMembers(memberAPI);
	}
	
	@Test(groups = {"API", "Admin"})
	public void crudSkus() throws IOException {
		datasetStart("crudSkus");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.postAdminSkus(adminAPI,".\\src\\test\\resources\\json\\adminapi\\skuspost.json");
		
		RestAPI adminAPIput = new RestAPI("admin");
		AdminAPIModule.putAdminSkus(adminAPI,".\\src\\test\\resources\\json\\adminapi\\skuspost.json");
		
		RestAPI adminAPIdelete= new RestAPI("admin");
		AdminAPIModule.deleteSkus(adminAPIdelete);		
	}
	

	@Test(groups = {"API", "Admin"})
	public void getSkus() throws IOException {
		datasetStart("getSkus");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getSkus(adminAPI);

	}
	
	@Test(groups = {"API", "Admin"})
	public void getAdminForm() throws IOException {
		datasetStart("getAdminForm");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getForms(adminAPI);

	}
	
	@Test(groups = {"API", "Admin"})
	public void crudLikeAssignEducationContent() throws IOException {
		datasetStart("crudLikeAssignEducationContent");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.postEducationContent(adminAPI,".\\src\\test\\resources\\json\\adminapi\\educontent.json");
		
		RestAPI adminAPIput = new RestAPI("admin");
		AdminAPIModule.putEducationContent(adminAPIput,".\\src\\test\\resources\\json\\adminapi\\educontent.json");
		
		RestAPI adminAPIlike = new RestAPI("admin");
		AdminAPIModule.postEducationContentLike(adminAPIlike);
		
		RestAPI adminAPIassign = new RestAPI("admin");
		AdminAPIModule.assignEducationContentPost(adminAPIassign);

		RestAPI adminAPIdelete= new RestAPI("admin");
		AdminAPIModule.deleteEducationContent(adminAPIdelete);	
		
		RestAPI adminAPIrestore= new RestAPI("admin");
		AdminAPIModule.restoreEducationContent(adminAPIrestore);	

	}
	
	
	@Test(groups = {"API", "Admin"})
	public void getEducationContent() throws IOException {
		datasetStart("getEducationContent");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getEduContents(adminAPI);
	}

	//TODO Confirm and automate /v1/admin/forms/notions/invalidate PUT
	@Test(groups = {"API", "Admin"})
	public void getAdminForms() throws IOException {
		datasetStart("getAdminForms");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminForms(adminAPI);
	}
	
	//TODO Admin/TreatmentDesign is pending
	
	@Test(groups = {"API", "Admin"})
	public void postAdmiSignedUrl() throws IOException {
		datasetStart("postAdmiSignedUrl");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.postSignedUrl(adminAPI);
	}
	
	//TODO Admin/Instruction
	
	
	//TODO Admin/RevisionRequest
	
	//TODO Admin/Advice
	
	//TODO Admin/Order
	@Test(groups = {"API", "Admin"})
	public void getAdminDashboard() throws IOException {
		datasetStart("getAdminDashboard");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminDashboard(adminAPI);
	}	
	
	
	@Test(groups = {"API", "Admin"})
	public void getAdminPayments() throws IOException {
		datasetStart("getAdminPayments");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.getAdminPayments(adminAPI);
	}	
	
	
	@Test(groups = {"API", "Admin"})
	public void createUpdateAdminNotes() throws IOException {
		datasetStart("createUpdateAdminNotes");
		RestAPI adminAPI = new RestAPI("admin");
		AdminAPIModule AdminAPIModule = new AdminAPIModule();
		AdminAPIModule.postAdminNotes(adminAPI);
		
		RestAPI putadminAPI = new RestAPI("admin");
		AdminAPIModule.putAdminNotes(adminAPI);
	}	
	
	
	
}
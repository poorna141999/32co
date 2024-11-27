package api;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Utilities.Common;
import Utilities.Data;
import Utilities.RestAPI;
import Utilities.constans.APIConstans;
import Utilities.constans.Values;
import io.restassured.response.Response;

public class AdminAPIModule extends Common {

	public static String submissionID = "";
	public static String patientID = "";
	public static String PRACTICES_ID = "";
	public static String RELEASES_ID = "";
	public static String MEMBER_ID = "";
	public static String SKUS_ID = "";
	public static String EDU_ID = "";
	
	public static HashMap<String, String> SKU_DETAILS = new HashMap<String, String>(); 

	public void getAdminSubmissions(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_SUBMISSIONS_GET);
		adminAPI.validateResponseCode(response, 200);
		submissionID = adminAPI.getValueFromjson(response, "$.docs[0].id");
		patientID = adminAPI.getValueFromjson(response, "$.docs[0].patient.id");
		System.out.println(submissionID);
	}

	public void getAdminPatientDetails(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_SUBMISSIONS_GET);
		adminAPI.validateResponseCode(response, 200);
		patientID = adminAPI.getValueFromjson(response, "$.docs[0].patient.id");
		String getURL = APIConstans.ADMIN_SUBMISSIONS_PATIENTS_GET.replace("{id}", patientID);
		Response responsePatient =adminAPI.getResponse(getURL);
		adminAPI.validateResponseCode(responsePatient, 200);
		System.out.println(submissionID);
	}

	public void getAdminSubmissionDetails(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_SUBMISSIONS_GET);
		adminAPI.validateResponseCode(response, 200);
		submissionID = adminAPI.getValueFromjson(response, "$.docs[0].id");
		String getURL = APIConstans.ADMIN_SUBMISSION_GET.replace("{id}", submissionID);
		Response response2 =adminAPI.getResponse(getURL);
		adminAPI.validateResponseCode(response2, 200);
		System.out.println(submissionID);
	}

	public void getAdminSubmissionTimelines(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_SUBMISSIONS_GET);
		adminAPI.validateResponseCode(response, 200);
		submissionID = adminAPI.getValueFromjson(response, "$.docs[0].id");
		String getURL = APIConstans.ADMIN_SUBMISSION_TIMELINES_GET.replace("{id}", submissionID);
		Response response2 =adminAPI.getResponse(getURL);
		adminAPI.validateResponseCode(response2, 200);
		System.out.println(submissionID);
	}


	public void getAdminSubmissionOrders(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_SUBMISSIONS_GET_APPROVED);
		adminAPI.validateResponseCode(response, 200);
		submissionID = adminAPI.getValueFromjson(response, "$.docs[0].id");
		String getURL = APIConstans.ADMIN_SUBMISSION_ORDERS_GET.replace("{id}", submissionID);
		Response response2 =adminAPI.getResponse(getURL);
		adminAPI.validateResponseCode(response2, 200);
		System.out.println(submissionID);
	}

	public void postAdminSubmissionArchive(RestAPI adminAPI) throws IOException {
		String id = DentistAPIModule.SUBMISSION_ID;
		String body = "{\"ids\": [\""+id+"\"]}";
		Response response = adminAPI.post(APIConstans.ADMIN_SUBMISSION_ARCHIVE_POST, body);
		adminAPI.validateResponseCode(response, 201);
	}
	
	public void postAdminSubmissionAssignDuoRefinement(RestAPI adminAPI) {
		String userId = Values.frameworkProperty.getProperty("DESIGNER_REF_NAME");
		String specialist = Values.frameworkProperty.getProperty("SPECIALIST_REF_NAME");
		String getURL = APIConstans.ADMIN_SUBMISSION_ASSIGN_POST.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"designer\": \""+userId+"\",\"specialist\": \""+specialist+"\"}";
		Response response = adminAPI.post(getURL, body);
		adminAPI.validateResponseCode(response, 201);
	}

	
	public void postAdminSubmissionAssignSoloRefinement(RestAPI adminAPI){
		String userId = Values.frameworkProperty.getProperty("DESIGNER_REF_NAME");
		String getURL = APIConstans.ADMIN_SUBMISSION_ASSIGN_POST.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"designer\": \""+userId+"\"}";
		Response response = adminAPI.post(getURL, body);
		adminAPI.validateResponseCode(response, 201);
	}


	public void postAdminSubmissionAssignDuo(RestAPI adminAPI) {
		String userId = Values.frameworkProperty.getProperty("designer.id");
		String specialist = Values.frameworkProperty.getProperty("specialist.id");
		String getURL = APIConstans.ADMIN_SUBMISSION_ASSIGN_POST.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"designer\": \""+userId+"\",\"specialist\": \""+specialist+"\"}";
		Response response = adminAPI.post(getURL, body);
		adminAPI.validateResponseCode(response, 201);
	}

	
	public void postAdminSubmissionAssignSolo(RestAPI adminAPI){
		String userId = Values.frameworkProperty.getProperty("designer.id");
		String getURL = APIConstans.ADMIN_SUBMISSION_ASSIGN_POST.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"designer\": \""+userId+"\"}";
		Response response = adminAPI.post(getURL, body);
		adminAPI.validateResponseCode(response, 201);
	}

	public void putAdminSubmissionStatus(RestAPI adminAPI) {
		String getURL = APIConstans.ADMIN_SUBMISSION_STATUS_PUT.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"status\": \"DRAFT\"}";
		Response response = adminAPI.put(getURL, body);
		adminAPI.validateResponseCode(response, 200);
	}

	public void putAdminSubmissionStls(RestAPI adminAPI,String jsonFilePath) {
		String getURL = APIConstans.ADMIN_SUBMISSION_STLS_PUT.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = readJsonFileAsString(jsonFilePath);
		Response response = adminAPI.put(getURL, body);
		adminAPI.validateResponseCode(response, 200);
	}

	public void putAdminSubmissionPriority(RestAPI adminAPI) {
		String getURL = APIConstans.ADMIN_SUBMISSION_STLS_PUT.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		Response response = adminAPI.put(getURL);
		adminAPI.validateResponseCode(response, 200);
	}

	public void putAdminSubmissionArchiveSTLs(RestAPI adminAPI) throws IOException {
		String getURL = APIConstans.ADMIN_SUBMISSION_ARCHIVE_STLS_PUT.replace("{id}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"isArchived\": true,\"url\": \"https://32co-files-upload-development.s3.eu-west-2.amazonaws.com/stls/2aec3440-3396-4b97-ae19-c3630ad972fe-stlFiles.stl\"}";
		Response response = adminAPI.put(getURL,body);
		adminAPI.validateResponseCode(response, 200);
	}

	public void getAdminPracticesScanner(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_PRACTICES_SCANNER);
		adminAPI.validateResponseCode(response, 200);
	}

	public void getAdminAllUsers(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");
		Response responseUser =adminAPI.getResponse(APIConstans.ADMIN_USER_GET.replace("{id}", userId));
		adminAPI.validateResponseCode(responseUser, 200);
	}

	public void getAdminUsersAssignable(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_SUBMISSIONS_GET);
		adminAPI.validateResponseCode(response, 200);
		submissionID = adminAPI.getValueFromjson(response, "$.docs[0].id");
		String[] arr = {"MANUFACTURER","DESIGNER","SPECIALIST","DENTIST","ADMIN"};		
		for(int i=0;i<arr.length;i++) {
			RestAPI adminAPI1 = new RestAPI("admin");
			String strUrl = APIConstans.ADMIN_ASSIGNABLE_GET.replace("{id}", submissionID).replace("{role}", arr[i]);
			Response responseAssignable =adminAPI1.getResponse(strUrl);
			adminAPI1.validateResponseCode(responseAssignable, 200);
		}
	}

	public void putAdminUpdateUserStatus(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");
		String strUrl = APIConstans.ADMIN_USER_UPDATE_STATUS_PUT.replace("{id}", userId);
		String body = "{\"status\": \"ACTIVE\"}";
		Response responseAssignable =adminAPI.put(strUrl, body);
		adminAPI.validateResponseCode(responseAssignable, 200);		
	}

	public void putAdminInviteDentist(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET_DENTIST);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");
		String body = "{\"ids\": [\""+userId+"\"]}";
		Response responseAssignable =adminAPI.put(APIConstans.ADMIN_USER_INVITE_DENTIST_PUT, body);
		adminAPI.validateResponseCode(responseAssignable, 200);

	}

	public void getAdminUserSubmissions(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");
		String strUrl = APIConstans.ADMIN_USER_SUBMISSIONS_GET.replace("{id}", userId);
		RestAPI adminAPI1 = new RestAPI("admin");
		Response responseAssignable =adminAPI1.getResponse(strUrl);
		adminAPI1.validateResponseCode(responseAssignable, 200);		
	}

	public void getAdminUserOrders(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");
		String strUrl = APIConstans.ADMIN_USER_ORDERS_GET.replace("{id}", userId);
		RestAPI adminAPI1 = new RestAPI("admin");
		Response responseAssignable =adminAPI1.getResponse(strUrl);
		adminAPI1.validateResponseCode(responseAssignable, 200);		
	}

	public void getAdminUserEducationContents(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET_MYDENTIST);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");
		String strUrl = APIConstans.ADMIN_USER_EDUCATION_CONTENT_GET.replace("{id}", userId);
		RestAPI adminAPI1 = new RestAPI("admin");
		Response responseEduContent =adminAPI1.getResponse(strUrl);
		adminAPI1.validateResponseCode(responseEduContent, 200);
		String contentId = adminAPI.getValueFromjson(responseEduContent, "$.docs[1].id");
		String strEduContentUrl = APIConstans.ADMIN_USER_EDUCATION_CONTENT_PUT.replace("{id}", userId).replace("{contentId}", contentId);
		RestAPI adminAPI2 = new RestAPI("admin");
		String body = "{\"note\": \"API testing\",\"notedBy\": \"API testing User\"}";
		Response responseEducationContent = adminAPI2.put(strEduContentUrl, body);
		adminAPI2.validateResponseCode(responseEducationContent, 200);
	}

	public void assignPractice(RestAPI adminAPI) throws IOException {
		Response response =adminAPI.getResponse(APIConstans.ADMIN_USERS_GET_DENTIST);
		adminAPI.validateResponseCode(response, 200);
		String userId = adminAPI.getValueFromjson(response, "$..id");

		RestAPI adminAPI1 = new RestAPI("admin");
		Response responsePractices =adminAPI1.getResponse(APIConstans.ADMIN_PRACTICES_GET);
		adminAPI1.validateResponseCode(responsePractices, 200);
		String practices = adminAPI1.getValueFromjson(responsePractices, "$.docs[0].id");

		RestAPI adminAPI2 = new RestAPI("admin");
		String body = "{\"dentists\": [\""+userId+"\"],\"practices\": [\""+practices+"\"],\"action\": \"ASSIGN\"}";
		Response responseAssigned =adminAPI2.post(APIConstans.ADMIN_PRACTICES_ASSIGN_POST,body);
		adminAPI2.validateResponseCode(responseAssigned, 201);		
	}

	public void createNewPractice(RestAPI adminAPI,String jsonFilePath) throws IOException {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			String name = "PracticeAPI" + "_" + generateRandomString(4, "alpha");
			body = updateJson(body,"$.name",name);
			response = adminAPI.post(APIConstans.ADMIN_PRACTICES_POST, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
				PRACTICES_ID = adminAPI.getValueFromjson(response, "$.id");
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}	
		}else {
			failAssert("Unable to Create new practice using API, Json file is not valid");
		}
	}

	public void updateNewPractice(RestAPI adminAPI,String jsonFilePath) throws IOException {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			String name = "PracticeAPI" + "_" + generateRandomString(4, "alpha");
			body = updateJson(body,"$.name",name);
			response = adminAPI.put(APIConstans.ADMIN_PRACTICES_PUT.replace("{id}", PRACTICES_ID), body);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
				PRACTICES_ID = adminAPI.getValueFromjson(response, "$.id");
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}	
		}else {
			failAssert("Unable to Create new practice using API, Json file is not valid");
		}
	}

	public void deletePractice(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.delete(APIConstans.ADMIN_PRACTICES_DELETE.replace("{id}", PRACTICES_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void createRelease(RestAPI adminAPI,String jsonFilePath) throws IOException {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			String name = "ReleaseAPI" + "_" + generateRandomString(4, "numeric");
			body = updateJson(body,"$.version",name);
			response = adminAPI.post(APIConstans.ADMIN_RELEASES_POST, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
				RELEASES_ID = adminAPI.getValueFromjson(response, "$.id");
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}	
		}else {
			failAssert("Unable to Create new release using API, Json file is not valid");
		}	
	}
	
	public void updateRelease(RestAPI adminAPI,String jsonFilePath) throws IOException {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			String name = "ReleaseAPI" + "_" + generateRandomString(4, "numeric");
			body = updateJson(body,"$.version",name);
			response = adminAPI.put(APIConstans.ADMIN_RELEASE_PUT.replace("{id}", RELEASES_ID), body);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
				RELEASES_ID = adminAPI.getValueFromjson(response, "$.id");
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}	
		}else {
			failAssert("Unable to Create new release using API, Json file is not valid");
		}	
	}
	
	public void deleteRelease(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.delete(APIConstans.ADMIN_RELEASE_DELETE.replace("{id}", RELEASES_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void getReleases(RestAPI adminAPI) throws IOException {
		Response response = null;
		String releaseId = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_RELEASES_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			releaseId = adminAPI.getValueFromjson(response, "$..id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_RELEASE_GET.replace("{id}", releaseId));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	static String tag = "";
	static String memberID = "";
	
	public void createGroup(RestAPI adminAPI) throws IOException {
		Response response = null;	
			//get tags
			RestAPI tagAPI = new RestAPI("admin");
			response = adminAPI.getResponse(APIConstans.ADMIN_GROUPS_TAGS_GET);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
				tag = adminAPI.getValueFromjsonArray(response, "$[0]");
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}	
			
			RestAPI userAPI = new RestAPI("admin");
			response = userAPI.getResponse(APIConstans.ADMIN_GROUP_DENTISTS_GET);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
				memberID = adminAPI.getValueFromjson(response, "$..id");
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}
			
			RestAPI groupAPI = new RestAPI("admin");
			String name = "Group" + "_" + generateRandomString(4, "numeric");
			String body = "{\"name\":\""+name+"\",\"description\":\"Create By API \",\"hasAllDentists\":false,\"members\":[\""+memberID+"\"],\"tags\":[\""+tag+"\"]}";;
			response = groupAPI.post(APIConstans.ADMIN_RELEASES_POST, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
				MEMBER_ID = adminAPI.getValueFromjson(response, "$.id");
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}	
		

	}
	
	public void updateGroup(RestAPI adminAPI) throws IOException {
		Response response = null;
		String nameUpdate = "Group" + "_" + generateRandomString(4, "numeric");
		String body = "{\"name\":\""+nameUpdate+"\",\"description\":\"Updated By API \",\"hasAllDentists\":false,\"members\":[\""+memberID+"\"],\"tags\":[\""+tag+"\"]}";;
		response = adminAPI.put(APIConstans.ADMIN_RELEASE_PUT.replace("{id}", MEMBER_ID), body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			MEMBER_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
	}
	
	public void deleteGroup(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.delete(APIConstans.ADMIN_GROUPS_DELETE.replace("{id}", RELEASES_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void getGroups(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_GROUPS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			MEMBER_ID = adminAPI.getValueFromjson(response, "$..id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_GROUP_GET.replace("{id}", MEMBER_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void getGroupTag(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_GROUPS_TAGS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			tag = adminAPI.getValueFromjsonArray(response, "$[0]");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void getGroupMembers(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_GROUP_MEMBER_GET.replace("{id}", MEMBER_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}

	public void postAdminSkus(RestAPI adminAPI,String jsonFilePath) throws IOException {
		String getURL = APIConstans.ADMIN_SKUS_POST;
		String body = readJsonFileAsString(jsonFilePath);
		String name = "SKUSAPI" + "_" + generateRandomString(4, "alpha");
		body = updateJson(body,"$.name",name);
		Response response = adminAPI.post(getURL, body);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
			SKUS_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
		}
	}

	public void putAdminSkus(RestAPI adminAPI,String jsonFilePath) throws IOException {
		String getURL = APIConstans.ADMIN_SKUS_PUT.replace("{id}", SKUS_ID);
		String body = readJsonFileAsString(jsonFilePath);
		String name = "UpdatedSKUSAPI" + "_" + generateRandomString(4, "alpha");
		body = updateJson(body,"$.name",name);
		Response response = adminAPI.put(getURL, body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			SKUS_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void deleteSkus(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.delete(APIConstans.ADMIN_SKUS_DELETE.replace("{id}", SKUS_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void getSkus(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_SKUS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			SKUS_ID = adminAPI.getValueFromjson(response, "$..id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_SKU_GET.replace("{id}", SKUS_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}


	public void getForms(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_FORMS_OPTIONS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_FORMS_COUNTRIES_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI3 = new RestAPI("admin");
		response = adminAPI3.getResponse(APIConstans.ADMIN_FORMS_NOTIONS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}

	public void postEducationContent(RestAPI adminAPI,String jsonFilePath) throws IOException {
		String getURL = APIConstans.ADMIN_EDU_CONTENT_POST;
		String body = readJsonFileAsString(jsonFilePath);
		String name = "EDUCATION API " + "_" + generateRandomString(8, "alpha");
		body = updateJson(body,"$.title",name);
		Response response = adminAPI.post(getURL, body);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
			EDU_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
		}
	}
	
	public void putEducationContent(RestAPI adminAPI,String jsonFilePath) throws IOException {
		String getURL = APIConstans.ADMIN_EDU_CONTENT_PUT.replace("{id}", EDU_ID);
		String body = readJsonFileAsString(jsonFilePath);
		String name = "Updated EDUCATION " + "_" + generateRandomString(4, "alpha");
		body = updateJson(body,"$.title",name);
		Response response = adminAPI.put(getURL, body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			EDU_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void deleteEducationContent(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.delete(APIConstans.ADMIN_EDU_CONTENT_DELETE.replace("{id}", EDU_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
	}
	
	public void restoreEducationContent(RestAPI adminAPI) throws IOException {
		String getURL = APIConstans.ADMIN_EDU_CONTENT_RESTORE_PUT.replace("{id}", EDU_ID);
		Response response = adminAPI.put(getURL);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void postEducationContentLike(RestAPI adminAPI) throws IOException {
		
		
		String getURL = APIConstans.ADMIN_EDU_CONTENT_LIKE_POST.replace("{id}", EDU_ID);
		Response response = adminAPI.post(getURL);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 201, But actual is "+response.getStatusCode());
		}
	}
	
	public void assignEducationContentPost(RestAPI adminAPI) throws IOException {
		Response response = null;
		String groupId ="";
		String dentist = "";
		response = adminAPI.getResponse(APIConstans.ADMIN_GROUPS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			groupId = adminAPI.getValueFromjson(response, "$..id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_USERS_GET_DENTIST);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			dentist = adminAPI2.getValueFromjson(response, "$..id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI3 = new RestAPI("admin");
		String getURL = APIConstans.ADMIN_EDU_CONTENT_ASSIGN_POST.replace("{id}", EDU_ID);
		String body = "{\"dentists\": [\""+dentist+"\"],\"groups\": [\""+groupId+"\"],\"sendToAllUsers\": true,\"isEmailNotify\": true}";
		response = adminAPI3.post(getURL,body);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 201, But actual is "+response.getStatusCode());
		}
	}
	
	
	
	public void getEduContents(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_EDU_CONTENTS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			EDU_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_EDU_CONTENT_GET.replace("{id}", EDU_ID));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI3 = new RestAPI("admin");
		response = adminAPI3.getResponse(APIConstans.ADMIN_EDU_CONTENT_TAGS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
	}
	
	public void getAdminForms(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_FORMS_OPTIONS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			EDU_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_FORMS_COUNTRIES_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
		
		RestAPI adminAPI3 = new RestAPI("admin");
		response = adminAPI3.getResponse(APIConstans.ADMIN_FORMS_NOTIONS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}		
	}	
	
	public void postSignedUrl(RestAPI adminAPI) throws IOException {
		String getURL = APIConstans.ADMIN_FILE_SIGNED_URL_POST;
		String body = "{\"fileName\":\"extra1.png\",\"mimeType\":\"image/png\",\"folderName\":\"images\"}";
		Response response = adminAPI.post(getURL, body);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
		}
	}	

	
	public void getAdminDashboard(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_DASHBOARD_SUBMISSION_COUNT_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			EDU_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_DASHBOARD_SUBMISSIONS_GET);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
	}
	
	
	public void getAdminPayments(RestAPI adminAPI) throws IOException {
		Response response = null;
		response = adminAPI.getResponse(APIConstans.ADMIN_PAYMENTS_INBOUND);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			EDU_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}	
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_PAYMENTS_OUTBOUND);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
	}
	
	public static String NOTE_ID = "";
	
	public void postAdminNotes(RestAPI adminAPI) throws IOException {
		Response getResponse = adminAPI.getResponse(APIConstans.ADMIN_ANYSUBMISSIONS_GET);
		String subid = adminAPI.getValueFromjson(getResponse, "$..id");
		String body = "{\"note\": \"My New Note\",\"submission\": \""+subid+"\",\"notedBy\": \"api\"}";
		Response response = adminAPI.post(APIConstans.ADMIN_NOTES_POST,body);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
			NOTE_ID = adminAPI.getValueFromjson(response, "$.id");
		}else {
			failAssert("Response status code is invalid, Expected is 201, But actual is "+response.getStatusCode());
		}
		
		RestAPI adminAPI2 = new RestAPI("admin");
		response = adminAPI2.getResponse(APIConstans.ADMIN_NOTES_GET.replace("{submissionid}", subid));
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
	}
	
	public void putAdminNotes(RestAPI adminAPI) throws IOException {
		String body = "{\"note\": \"My Note\",\"notedBy\": \"api\"}";
		Response response = adminAPI.put(APIConstans.ADMIN_NOTES_PUT.replace("{id}", NOTE_ID),body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void putAdminApproveDesignSendToDentist(RestAPI adminAPI) {
		String url = APIConstans.ADMIN_TREATMENTDEISGNS_APPROVE_PUT.replace("{id}", DesignerAPIModule.TREATMENT_DESGIN_ID);
		Response response = adminAPI.put(url);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	
	public void getMySkuId(RestAPI adminAPI) {
		String expSKUname =  Values.frameworkProperty.getProperty("manufacturer.sku.name");
		String expSKUdesc = Values.frameworkProperty.getProperty("manufacturer.sku.desc");
		String manufacturer = Values.frameworkProperty.getProperty("manufacturer.id");
		String url = APIConstans.ADMIN_SKUS_GET_MANUFACTURER.replace("{id}", manufacturer);
		Response response = adminAPI.getResponse(url);
		List<String > lst = adminAPI.getListValueFromjson(response, "$.docs");
		for(int i=0;i<lst.size();i++) {
			String desc = adminAPI.getValueFromjson(response, "$.docs["+i+"].description");
			String name = adminAPI.getValueFromjson(response, "$.docs["+i+"].name");
			String id = adminAPI.getValueFromjson(response, "$.docs["+i+"].id");
			if(name.contains(expSKUname)&&desc.contains(expSKUdesc)) {
				DentistAPIModule.SKU_ID = id;
				System.out.println("SKU ID "+id);
				break;
			}
		}
	}
	
	public void sendToManufacturerPut(RestAPI adminAPI) {
		String url = APIConstans.ADMIN_SEND_TO_MANUFACTURER_PUT.replace("{designid}", DesignerAPIModule.TREATMENT_DESGIN_ID);
		Response response = adminAPI.put(url);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void markMaterialRecieved(RestAPI adminAPI) {
		String url = APIConstans.ADMIN_DASHBOARD_MARK_MATERIAL_RECIEVED.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"status\":\"WITH_32CO\"}";
		Response response = adminAPI.put(url,body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}

	public void confirmShipping(RestAPI adminAPI) {
		//String url = APIConstans.ADMIN_DASHBOARD_CONFIRM_SHIPPING.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		String url = APIConstans.ADMIN_DASHBOARD_CONFIRM_SHIPPING;
		//String body = "{\"shippingLink\":\"http://www.google.com\"}";
		String body = "{\"submissions\":[{\"submissionId\":\""+DentistAPIModule.SUBMISSION_ID+"\",\"shippingLink\":\"https://www.google.com/\"}]}";
		Response response = adminAPI.put(url,body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void getDesignerSpecialistFeeDetails(RestAPI adminAPI) {
		//String userId = APIConstans.DESIGNER_NAME;
		String userId = Values.frameworkProperty.getProperty("designer.id");
		Response responseUser =adminAPI.getResponse(APIConstans.ADMIN_USER_GET.replace("{id}", userId));
		adminAPI.validateResponseCode(responseUser, 200);
		Values.designerFee = adminAPI.getValueFromjson(responseUser, "$.fee");
		
		//String specialist = APIConstans.SPECIALIST_NAME;
		String specialist = Values.frameworkProperty.getProperty("specialist.id");
		Response responseUser1 =adminAPI.getResponse(APIConstans.ADMIN_USER_GET.replace("{id}", specialist));
		adminAPI.validateResponseCode(responseUser1, 200);
		Values.specialistFee = adminAPI.getValueFromjson(responseUser1, "$.fee");
	}
	
	public String calculateSKUAmount(String type, boolean isAttachment,int TotalAlligner) {
		int designerFee = 0;
		int specialistfee = 0;
		int templateFee = 0;
		int TotalPriceAlligner = 0;
		int shippingFee = 0;
		int Fee32Co = 0;
		int packagingFee = 0;
		
		if(type.equalsIgnoreCase("solo")) {
			designerFee = Integer.parseInt(Values.designerFee);
		}else {
			designerFee = Integer.parseInt(Values.designerFee);
			specialistfee = Integer.parseInt(Values.specialistFee);
		}
		if(isAttachment) {
			templateFee = Integer.parseInt(SKU_DETAILS.get("planPrice.template")) *2;
		}
		TotalPriceAlligner = Integer.parseInt(SKU_DETAILS.get("planPrice.pricePerAligner"))*TotalAlligner;
		shippingFee = Integer.parseInt(SKU_DETAILS.get("planPrice.shippingFee"));
		Fee32Co = Integer.parseInt(SKU_DETAILS.get("planPrice.fee32Co"));
		packagingFee =  Integer.parseInt(SKU_DETAILS.get("planPrice.packagingFee"));
		int totalAmount = TotalPriceAlligner + templateFee +shippingFee + packagingFee + Fee32Co + specialistfee +designerFee;
		System.out.println("Total Amount Calculated is "+ totalAmount);
		info("**********Total Amount Calculated is "+ totalAmount);
		return String.valueOf(totalAmount);
	}
	
	public String calculateSKUAmountOneRefinement(String type, boolean isAttachment,int TotalAlligner) {
		int designerFee = 0;
		int specialistfee = 0;
		int templateFee = 0;
		int TotalPriceAlligner = 0;
		int shippingFee = 0;
		int Fee32Co = 0;
		int packagingFee = 0;
		int percentage = 0;
		if(type.equalsIgnoreCase("solo")) {
			designerFee = Integer.parseInt(Values.designerFee);
		}else {
			designerFee = Integer.parseInt(Values.designerFee);
			specialistfee = Integer.parseInt(Values.specialistFee);
		}
		if(isAttachment) {
			templateFee = Integer.parseInt(SKU_DETAILS.get("refinementPrice.template"))*2;
		}
		String val  = null;
		String[] vals = SKU_DETAILS.get("refinementDiscountFactor").split(",");
			val = vals[0];
			val = val.replaceAll("\\D", "");
		percentage = Integer.parseInt(val);
		//TotalAlligner =(int) Math.round((TotalAlligner * percentage )/100.0);
		TotalPriceAlligner = Integer.parseInt(SKU_DETAILS.get("refinementPrice.pricePerAligner"))*TotalAlligner;
		TotalPriceAlligner =(int) Math.round((TotalPriceAlligner * percentage )/100.0);
		shippingFee = Integer.parseInt(SKU_DETAILS.get("refinementPrice.shippingFee"));
		Fee32Co = Integer.parseInt(SKU_DETAILS.get("refinementPrice.fee32Co"));
		packagingFee =  Integer.parseInt(SKU_DETAILS.get("refinementPrice.packagingFee"));
		int totalAmount = TotalPriceAlligner + templateFee +shippingFee + packagingFee + Fee32Co + specialistfee +designerFee;
		System.out.println("Total Amount Calculated for One Refinement is "+ totalAmount);
		info("**********Total Amount Calculated for One Refinement is "+ totalAmount);
		return String.valueOf(totalAmount);
	}
	
	public String calculateSKUAmountTwoRefinement(String type, boolean isAttachment,int TotalAlligner) {
		int designerFee = 0;
		int specialistfee = 0;
		int templateFee = 0;
		int TotalPriceAlligner = 0;
		int shippingFee = 0;
		int Fee32Co = 0;
		int packagingFee = 0;
		int percentage = 0;
		if(type.equalsIgnoreCase("solo")) {
			designerFee = Integer.parseInt(Values.designerFee);
		}else {
			designerFee = Integer.parseInt(Values.designerFee);
			specialistfee = Integer.parseInt(Values.specialistFee);
		}
		if(isAttachment) {
			templateFee = Integer.parseInt(SKU_DETAILS.get("refinementPrice.template"))*2;
		}
		String val = SKU_DETAILS.get("refinementDiscountFactor").split(",")[1];
		val = val.replaceAll("\\D", "");
		percentage = Integer.parseInt(val);
		//TotalAlligner =(int) Math.round((TotalAlligner * percentage )/100.0);
		
		TotalPriceAlligner = Integer.parseInt(SKU_DETAILS.get("refinementPrice.pricePerAligner"))*TotalAlligner;
		TotalPriceAlligner =(int) Math.round((TotalPriceAlligner * percentage )/100.0);
		shippingFee = Integer.parseInt(SKU_DETAILS.get("refinementPrice.shippingFee"));
		Fee32Co = Integer.parseInt(SKU_DETAILS.get("refinementPrice.fee32Co"));
		packagingFee =  Integer.parseInt(SKU_DETAILS.get("refinementPrice.packagingFee"));
		int totalAmount = TotalPriceAlligner + templateFee +shippingFee + packagingFee + Fee32Co + specialistfee +designerFee;
		System.out.println("Total Amount Calculated for Two Refinement is "+ totalAmount);
		info("**********Total Amount Calculated for Two Refinement is "+ totalAmount);
		return String.valueOf(totalAmount);
	}


	public Response getSKUDetails(RestAPI manufacturerAPI) {
		String manufacturerID = Values.frameworkProperty.getProperty("manufacturer.id","");
		String getURL = APIConstans.MANUFACTURER_ALL_SKU_DETAILS_GET.replace("{id}", manufacturerID);
		Response response = manufacturerAPI.getResponse(getURL);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
		return response;
	}
	
	public String validateSKUExists(RestAPI adminAPI,String name,String description) {
		String skuid = null;
		Response response = getSKUDetails(adminAPI);
		String total = adminAPI.getValueFromjson(response, "$.total");
		int count = Integer.parseInt(total);
		for(int i=0;i<count;i++) {
			String actdesc = adminAPI.getValueFromjson(response, "$.docs["+i+"].description");
			String actname = adminAPI.getValueFromjson(response, "$.docs["+i+"].name");
			if(actname.equalsIgnoreCase(name) && actdesc.equalsIgnoreCase(description)) {
				passed("Already SKU with expected Refinement exists for the manufacturer");
				skuid = adminAPI.getValueFromjson(response, "$.docs["+i+"].id");
			}
		}
		return skuid;
	}
	
	public String CreateSKU(RestAPI adminAPI,String jsonFilePath) {
		Response response = null;
		String skuid = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			String manid = Values.frameworkProperty.getProperty("manufacturer.id","");
			body = updateJson(body,"$.manufacturer",manid);
			response = adminAPI.post(APIConstans.MANUFACTURER_CREATE_SKU_POST, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
				skuid = adminAPI.getValueFromjson(response, "$.id");
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}	
		}else {
			failAssert("Unable to Create new practice using API, Json file is not valid");
		}
		return skuid;
	}
	
	public void getSKUPriceDetails(RestAPI adminAPI,String skuID) {
		String getURL = APIConstans.MANUFACTURER_SKU_DETAIL_GET.replace("{skuid}", skuID);
		Response response = adminAPI.getResponse(getURL);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
			SKU_DETAILS = adminAPI.getMapFromJson(response);
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
		
	}
	

}

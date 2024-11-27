package api;

import java.io.File;

import Utilities.Common;
import Utilities.RestAPI;
import Utilities.constans.APIConstans;
import Utilities.constans.Values;
import io.restassured.response.Response;

public class UserAPIModule extends Common {

	public static String SUBMISSION_ID = "";	

	public Response userSignInPost(RestAPI userAPI) {
		String body = "{\"email\": \""+Values.frameworkProperty.getProperty("dentist.username")+"\",\"password\": \""+Values.frameworkProperty.getProperty("dentist.password")+"\"}";
		Response response = userAPI.post(APIConstans.USER_AUTH_SIGNIN, body);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
		}
		return response;	
	}
	
	public Response userSignOutPost(RestAPI userAPI) {
		Response response = userAPI.post(APIConstans.USER_AUTH_SIGNOUT);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
		}
		return response;	
	}
	
	public Response getUserProfile(RestAPI userAPI) {
		Response response = userAPI.getResponse(APIConstans.USER_AUTH_PROFILE);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
		}
		return response;	
	}
	
	public Response userPasswordResetRequest(RestAPI userAPI) {
		String user = "prakasha.shetty+H35H3_dent@32co.com";
		String body  = "{\"email\": \""+user+"\"}";
		Response response = userAPI.post(APIConstans.USER_AUTH_PASSWORD_RESET_REQUEST);
		if(response.getStatusCode()==201) {
			passed("Response status code 201 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
		}
		return response;	
	}
	
	public void validateUsersStatusAndUpdate(RestAPI userAPI,String status) {
		String designer = Values.frameworkProperty.getProperty("DESIGNER_REF_NAME");
		String specialist = Values.frameworkProperty.getProperty("SPECIALIST_REF_NAME");
		if(status.equalsIgnoreCase("Active")) {			
			Response response = null;
			String body = "{\"status\":\"ACTIVE\"}";;
			response = userAPI.put(APIConstans.ADMIN_USER_UPDATE_STATUS_PUT.replace("{id}", designer), body);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}
			response = userAPI.put(APIConstans.ADMIN_USER_UPDATE_STATUS_PUT.replace("{id}", specialist), body);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}
		}else {
			Response response = null;
			String body = "{\"status\":\"INACTIVE\"}";;
			response = userAPI.put(APIConstans.ADMIN_USER_UPDATE_STATUS_PUT.replace("{id}", designer), body);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}
			response = userAPI.put(APIConstans.ADMIN_USER_UPDATE_STATUS_PUT.replace("{id}", specialist), body);
			if(response.getStatusCode()==200) {
				passed("Response status code 200 is as expected " );
			}else {
				failAssert("Response status code is invalid, Expected is 200 , But actual is "+response.getStatusCode());
			}
		}
	}
}

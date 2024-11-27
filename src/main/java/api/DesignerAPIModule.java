package api;

import java.io.File;

import Utilities.Common;
import Utilities.RestAPI;
import Utilities.constans.APIConstans;
import Utilities.constans.Values;
import io.restassured.response.Response;

public class DesignerAPIModule extends Common {
	
	public static String TREATMENT_DESGIN_ID = "";	
	
	
	/**
	 * @param desginerAPI
	 * @param status REJECTED or ACCEPTED
	 */
	public void acceptOrRejectCase(RestAPI desginerAPI,String status) {
		String putURL = APIConstans.DESIGNER_PATEINTS_SUBMIT_INVITATIONS_PUT.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"status\":\""+status.toUpperCase()+"\"}";
		Response response = desginerAPI.put(putURL, body);
		desginerAPI.validateResponseCode(response, 200);	
	}
	
	public void submitNewDesign(RestAPI desginerAPI,String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			body = updateJson(body,"$.submission",DentistAPIModule.SUBMISSION_ID);
			response = desginerAPI.post(APIConstans.DESIGNER_PATEINTS_TREATMENT_DESGIN_PUT, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
				TREATMENT_DESGIN_ID = desginerAPI.getValueFromjson(response, "$..id");
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}
		}else {
			failAssert("Unable to Create new Design API, Json file is not valid");
		}
	}
	
	
	public void uploadSTls(RestAPI desginerAPI,String jsonFilePath) {
		String getURL = APIConstans.DESIGNER_UPLOAD_STLS_PUT.replace("{designid}", DesignerAPIModule.TREATMENT_DESGIN_ID);
		String body = readJsonFileAsString(jsonFilePath);
		Response response = desginerAPI.put(getURL, body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}
	
	public void updateSTl3DUrl(RestAPI desginerAPI) {
		String putURL = APIConstans.DESIGNER_SUBMISSION_ANIMATION_PUT.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"animationUrl\":\"https://webview.32-stories.com/?mlink=https://32s-prod-ftp.s3.eu-west-2.amazonaws.com/data/Client4070/Yi%20Lin%20Tan_18~612~621/221E675B4F8D4618BC6CA9BDD1523389.iiwgl&fg=ccc&bg=000&p=SEBWHS\"}";
		Response response = desginerAPI.put(putURL, body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
	}

}

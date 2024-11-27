package api;

import java.io.File;

import Utilities.Common;
import Utilities.RestAPI;
import Utilities.constans.APIConstans;
import Utilities.constans.Values;
import io.restassured.response.Response;

public class SpecialistAPIModule extends Common {
	
	public static String TREATMENT_DESGIN_ID = "";	
	
	
	/**
	 * @param desginerAPI
	 * @param status REJECTED or ACCEPTED
	 */
	public void acceptOrRejectCase(RestAPI desginerAPI,String status) {
		String putURL = APIConstans.SPECIALIST_PATEINTS_SUBMIT_INVITATIONS_PUT.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"status\":\""+status.toUpperCase()+"\"}";
		Response response = desginerAPI.put(putURL, body);
		desginerAPI.validateResponseCode(response, 200);	
	}
	
	
	public void caseSuitable(RestAPI desginerAPI,String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			body = updateJson(body,"$.submission",DentistAPIModule.SUBMISSION_ID);
			response = desginerAPI.post(APIConstans.SPECIALIST_PATEINTS_INSTRUCTIONS_PUT, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}
		}else {
			failAssert("Unable to make case suitable, Json file is not valid");
		}
	}
	
	public void submitAdvice(RestAPI desginerAPI,String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if(!body.equals("")) {
			body = updateJson(body,"$.submission",DentistAPIModule.SUBMISSION_ID);
			body = updateJson(body,"$.treatmentDesign",DesignerAPIModule.TREATMENT_DESGIN_ID);
			response = desginerAPI.post(APIConstans.SPECIALIST_PATEINTS_ADVICES_PUT, body);
			if(response.getStatusCode()==201) {
				passed("Response status code 201 is as expected " );
			}else {
				failAssert("Response status code is invalid, Expected is 201 , But actual is "+response.getStatusCode());
			}
		}else {
			failAssert("Unable to submit advice , Json file is not valid");
		}
	}
}

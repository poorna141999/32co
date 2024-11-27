package api;

import java.io.File;
import java.util.HashMap;

import Utilities.Common;
import Utilities.RestAPI;
import Utilities.constans.APIConstans;
import Utilities.constans.Constants;
import Utilities.constans.Values;
import io.restassured.response.Response;

public class ManufacturerAPIModule extends Common {
	
	public static String TREATMENT_DESGIN_ID = "";	
	
	
	
	public void moveToinProgressAndAddShipppingLinkPut(RestAPI manufacturerAPI) {
		String getURL = APIConstans.MANUFACTURER_MOVE_TO_INPROGRESS_PUT.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		String body = "{\"status\":\"IN_PROGRESS\"}";
		Response response = manufacturerAPI.put(getURL, body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
		
		 getURL = APIConstans.MANUFACTURER_ADD_EDIT_SHIPPING_LINK_PUT.replace("{submissionid}", DentistAPIModule.SUBMISSION_ID);
		body = "{\"manufacturerShippingUrl\":\"http://www.google.com\"}";
		response = manufacturerAPI.put(getURL, body);
		if(response.getStatusCode()==200) {
			passed("Response status code 200 is as expected " );
		}else {
			failAssert("Response status code is invalid, Expected is 200, But actual is "+response.getStatusCode());
		}
		
	}
	
	

	
}

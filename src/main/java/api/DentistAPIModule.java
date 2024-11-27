package api;

import java.io.File;

import Utilities.Common;
import Utilities.RestAPI;
import Utilities.constans.APIConstans;
import Utilities.constans.Values;
import io.restassured.response.Response;

public class DentistAPIModule extends Common {

	public static String SUBMISSION_ID = "";
	public static String SKU_ID = "";
	public static String ORDER_ID = "";
	public static String PATIENT_PROPOSAL_ID = "";

	public Response postCreateSolo(RestAPI dentistAPI, String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if (!body.equals("")) {
			Values.patientFirstName = "AutoAPI" + "_" + generateRandomString(4, "alpha");
			Values.patientLastName = "AutoAPI" + "_" + generateRandomString(4, "alpha");
			body = updateJson(body, "$.patientDetail.firstName", Values.patientFirstName);
			body = updateJson(body, "$.patientDetail.lastName", Values.patientLastName);
			response = dentistAPI.post(APIConstans.DENTIST_SUBM_POST_INITIAL_SOLO, body);
			System.out.println("Response:");
			response.getBody().prettyPrint();
			if (response.getStatusCode() == 201) {
				passed("Response status code 201 is as expected ");
				SUBMISSION_ID = dentistAPI.getValueFromjson(response, "$..id");
				System.out.println("SUBMISSION_ID::" + SUBMISSION_ID);
			} else {
				failAssert(
						"Response status code is invalid, Expected is 201 , But actual is " + response.getStatusCode());
			}
		} else {
			failAssert("Unable to Create Solo record using API, Json file is not valid");
		}
		return response;
	}

	public Response postCreateSolo_1(RestAPI dentistAPI, String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if (!body.equals("")) {
			Values.patientFirstName = "AutoAPI" + "_" + generateRandomString(4, "alpha");
			Values.patientLastName = "AutoAPI" + "_" + generateRandomString(4, "alpha");
			body = updateJson(body, "$.patientDetail.firstName", Values.patientFirstName);
			body = updateJson(body, "$.patientDetail.lastName", Values.patientLastName);
			response = dentistAPI.put(APIConstans.DENTIST_SUBM_POST_INITIAL_SOLO_1+SUBMISSION_ID, body);
			System.out.println("Response:");
			response.getBody().prettyPrint();
			if (response.getStatusCode() == 200) {
				passed("Response status code 201 is as expected ");
				SUBMISSION_ID = dentistAPI.getValueFromjson(response, "$..id");
				System.out.println("SUBMISSION_ID::" + SUBMISSION_ID);
			} else {
				failAssert(
						"Response status code is invalid, Expected is 201 , But actual is " + response.getStatusCode());
			}
		} else {
			failAssert("Unable to Create Solo record using API, Json file is not valid");
		}
		return response;
	}

	public Response postCreateDuo(RestAPI dentistAPI, String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if (!body.equals("")) {
			Values.patientFirstName = "AutoAPI" + "_" + generateRandomString(4, "alpha");
			Values.patientLastName = "AutoAPI" + "_" + generateRandomString(4, "alpha");
			body = updateJson(body, "$.patientDetail.firstName", Values.patientFirstName);
			body = updateJson(body, "$.patientDetail.lastName", Values.patientLastName);
			response = dentistAPI.post(APIConstans.DENTIST_SUBM_POST_INITIAL_DUO, body);
			if (response.getStatusCode() == 201) {
				passed("Response status code 201 is as expected ");
				SUBMISSION_ID = dentistAPI.getValueFromjson(response, "$..id");
			} else {
				failAssert(
						"Response status code is invalid, Expected is 201 , But actual is " + response.getStatusCode());
			}
		} else {
			failAssert("Unable to Create Solo record using API, Json file is not valid");
		}
		return response;
	}

	public void submitPatienProposalPost(RestAPI dentistAPI, String jsonFilePath) {
		Response response = null;
		String body = readJsonFileAsString(jsonFilePath);
		if (!body.equals("")) {
			String patientName = Values.patientFirstName + " " + Values.patientLastName;
			body = updateJson(body, "$.patientName", patientName);
			body = updateJson(body, "$.submission", DentistAPIModule.SUBMISSION_ID);
			body = updateJson(body, "$.treatmentDesign", DesignerAPIModule.TREATMENT_DESGIN_ID);
			response = dentistAPI.post(APIConstans.DENTIST_PATIENT_PROPOSAL_POST, body);
			if (response.getStatusCode() == 201) {
				passed("Response status code 201 is as expected ");
				PATIENT_PROPOSAL_ID = dentistAPI.getValueFromjson(response, "$..id");
			} else {
				failAssert(
						"Response status code is invalid, Expected is 201 , But actual is " + response.getStatusCode());
			}
		} else {
			failAssert("Unable to Create Patient Proposal using API, Json file is not valid");
		}
	}

	public void dentistOrderClearAligners(RestAPI dentistAPI) {
		Response response = null;
		String body = "{\"submission\":\"" + DentistAPIModule.SUBMISSION_ID + "\",\"sku\":\"" + DentistAPIModule.SKU_ID
				+ "\",\"treatmentDesign\":\"" + DesignerAPIModule.TREATMENT_DESGIN_ID
				+ "\",\"finishOption\":\"Scalloped\"}";
		response = dentistAPI.post(APIConstans.DENTIST_ORDERS_POST, body);
		if (response.getStatusCode() == 201) {
			passed("Response status code 201 is as expected ");
			ORDER_ID = dentistAPI.getValueFromjson(response, "$..id");
		} else {
			failAssert("Response status code is invalid, Expected is 201 , But actual is " + response.getStatusCode());
		}

	}

}

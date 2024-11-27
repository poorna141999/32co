package Utilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jayway.jsonpath.JsonPath;

import Utilities.constans.APIConstans;
import Utilities.constans.Values;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAPI extends Common{

	private RequestSpecification httpRequest;
	public String baseURI;

	 

	public RestAPI(String user) {
		String username = null;
		String password = null;
		String usertype = null;
		switch(user.toLowerCase()) {

		case "admin":
			username = Values.frameworkProperty.getProperty("admin.username");
			password = Values.frameworkProperty.getProperty("admin.password");
			usertype = "admin";
			break;
		case "dentist":
			username = Values.frameworkProperty.getProperty("dentist.username");
			password = Values.frameworkProperty.getProperty("dentist.password");
			usertype = "user";
			break;
		case "dentistnew":
			username = Values.dentistEmail;
			password = Values.frameworkProperty.getProperty("dentist.password");
			usertype = "user";
			break;
		case "specialist":
			username = Values.frameworkProperty.getProperty("specialist.username");
			password = Values.frameworkProperty.getProperty("specialist.password");
			usertype = "user";
			break;
		case "designer":
			username = Values.frameworkProperty.getProperty("designer.username");
			password = Values.frameworkProperty.getProperty("designer.password");
			usertype = "user";
			break;
		case "manufacturer":
			username = Values.frameworkProperty.getProperty("manufacturer.username");
			password = Values.frameworkProperty.getProperty("manufacturer.password");
			usertype = "user";
			break;
		}
		try {
			this.baseURI = Values.frameworkProperty.getProperty("api.baseurl");
			//HashMap<String, RequestSpecification> rest = new HashMap<String, RequestSpecification>();
			RestAssured.baseURI = baseURI;
			RequestSpecification loginRequest = RestAssured.given();
			Cookies cookie =loginRequest.contentType(ContentType.JSON).body("{\"email\": \""+username+"\",\"password\": \""+password+"\"}").post("/v1/"+usertype+"/auth/signin").then().extract().response().getDetailedCookies();
			httpRequest = loginRequest.cookies(cookie);
		} catch (Exception e) {
			fail("Exception caught , Message is "+e.getMessage());
		}
	}

	public Response getResponse(String get) {
		info("Request GET URL is "+ get );
		Response response = httpRequest.get(get);
		info("Response is below ");
		logJson(response.getBody().asString());
		return response;
	}

	public Response getResponse(String url,HashMap<String, String> params) {
		Response response = httpRequest.given().params(params).get(url);
		return response;
	}

	public Response post(String url, String body) {
		info("Request POST URL is "+ url +" and the request body is as below");
		logJson(body);
		Response response = httpRequest.body(body).post(url);
		info("Response is as below");
		logJsonFormat(response.getBody().asString());
		return response;
	}

	public Response post(String url) {
		info("Request POST URL is "+ url );
		Response response = httpRequest.post(url);
		info("Response is as below");
		logJsonFormat(response.getBody().asString());
		return response;
	}

	public Response post(String url, File jsonFilePath) {
		Response response = httpRequest.body(jsonFilePath).post(url);
		return response;
	}

	public Response put(String url, String body) {
		info("Request PUT URL is "+ url +" and the request body is as below");
		logJson(body);
		Response response = httpRequest.body(body).put(url);
		info("Response is as below");
		logJsonFormat(response.getBody().asString());
		return response;
	}

	public Response put(String url, File jsonFilePath) {
		info("Request PUT URL is "+ url);
		Response response = httpRequest.body(jsonFilePath).put(url);
		return response;
	}

	public Response put(String url) {
		info("Request PUT URL is "+ url);
		Response response = httpRequest.put(url);
		info("Response is as below");
		logJsonFormat(response.getBody().asString());
		return response;
	}

	public Response delete(String url, String body) {
		Response response = httpRequest.body(body).delete(url);
		return response;
	}

	public Response delete(String url) {
		info("Request DELETE URL is "+ url);
		Response response = httpRequest.delete(url);
		info("Response is as below");
		logJsonFormat(response.getBody().asString());
		return response;
	}

	public Response delete(String url, File jsonFilePath) {
		Response response = httpRequest.body(jsonFilePath).delete(url);
		return response;
	}

	public  int getStatusCode(Response response) {
		try {
			return response.getStatusCode();
		} catch (Exception e) {
			return -1;
		}
	}

	public  String getResponseHeader(Response response) {
		try {
			return response.getHeaders().toString();
		} catch (Exception e) {
			return null;
		}
	}

	public  String getResponseBody(Response response) {
		try {
			return response.getBody().asString();
		} catch (Exception e) {
			return null;
		}
	}

	public String getValueFromjson(Response response,String jsonPath) {
		String value = "";
		long val = -1;
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.getBody().asString());
			JSONObject jsonObject = (JSONObject) obj;
			try {
			value = JsonPath.read(jsonObject, jsonPath);
			}catch (Exception e) {
				val = JsonPath.read(jsonObject, jsonPath);	
				value = String.valueOf(val);
			}
		} catch (JsonSyntaxException e) {
			fail("Jsonpath sent is invalid "+ jsonPath+" ,Caught Exception JsonSyntaxException "+e.getMessage());
		}catch (Exception e) {
			try {
				List<String> arrValue = new ArrayList<>();
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(response.getBody().asString());
				JSONObject jsonObject = (JSONObject) obj;
				arrValue = JsonPath.read(jsonObject, jsonPath);
				value = arrValue.get(0);
			} catch (ParseException e1) {
				fail("Exception caught while getting value from response "+e.getMessage());
			}

		}
		return value;
	}

	public String getValueFromjsonArray(Response response,String jsonPath) {
		String value = "";
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.getBody().asString());
			JSONArray jsonObject = (JSONArray) obj;
			value = (String) jsonObject.get(0);		
		} catch (JsonSyntaxException e) {
			fail("Jsonpath sent is invalid "+ jsonPath+" ,Caught Exception JsonSyntaxException "+e.getMessage());
		}catch (Exception e) {		
			fail("Exception caught while getting value from response "+e.getMessage());
		}
		return value;
	}

	public List<String> getListValueFromjson(Response response,String jsonPath) {
		List<String> value = new ArrayList<>();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.getBody().asString());
			JSONObject jsonObject = (JSONObject) obj;
			value = JsonPath.read(jsonObject, jsonPath);
		} catch (JsonSyntaxException e) {
			fail("Jsonpath sent is invalid "+ jsonPath+" ,Caught Exception JsonSyntaxException "+e.getMessage());
		}catch (Exception e) {
			fail("Exception caught while getting value from response "+e.getMessage());
		}
		return value;
	}

	public void iterateJson(JSONObject json, HashMap<String, String> hashMap, String parentKey) {
		for (Object key : json.keySet()) {
			String fullKey = parentKey.isEmpty() ? (String) key : parentKey + "." + key;
			Object value = json.get(key);
			if (value instanceof JSONObject) {
				iterateJson((JSONObject)value, hashMap, fullKey);
			} else {
				hashMap.put(fullKey, value.toString());
			}
		}
	}

	public HashMap<String, String> getMapFromJson(Response response) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		try {
			JSONParser parser = new JSONParser();
			Object obj;
			obj = parser.parse(response.getBody().asString());
			JSONObject jsonObject = (JSONObject) obj;
			iterateJson(jsonObject, hashMap, "");
			System.out.println(hashMap);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashMap;
	}

}

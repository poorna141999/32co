package Utilities;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import Utilities.constans.Values;
import io.restassured.response.Response;

public class Common extends ExtentReport {

	public	Configuration config= Configuration.builder()
			.jsonProvider(new JacksonJsonNodeJsonProvider())
			.mappingProvider(new JacksonMappingProvider())
			.build();

	public void screenShot(String filename) {
		String scrPath = Values.outputDirectory + "/Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			Robot robot = new Robot();
			Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
			File outputfile = new File(scrPath + "/" + filename + ".png");
			ImageIO.write(bufferedImage, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void failAssert(String errMessage) {
		fail(errMessage);
		Assert.assertTrue(false, errMessage);
		System.out.println("FAIL::"+errMessage);
	}

	public void fail(String errMessage) {
		Values.testfailed = true;
		log("fail", errMessage);
		System.out.println("FAIL::"+errMessage);

	}

	public void passed(String logMessage) {
		log("pass", logMessage);
		System.out.println("PASS::"+logMessage);
	}

	public void info(String logMessage) {
		log("info", logMessage);
		System.out.println("INFO::"+logMessage);
	}

	/****
	 * Method to generate random string
	 * 
	 * @param : length of string to be generated
	 * @return : void
	 * @author : suntechUser(userId)
	 * @Modified By :
	 ****/
	public String generateRandomString(int len, String type) {
		Random rng = new Random();
		String characters = null;
		if (type.equalsIgnoreCase("numeric")) {
			characters = "1237890456";
		} else if (type.equalsIgnoreCase("alpha")) {
			characters = "abcdefghijklmnoNOPQRSTUVWXYZpqrstuvwxyzABCDEFGHIJKLM";
		} else if (type.equalsIgnoreCase("alphanumeric")) {
			characters = "abc1238974560defghijklmno1238974560NOPQRSTUVWXYZpqrst1238974560uvwxyz1238974560ABCDEFGHIJ1238974560KLM";
		}
		char[] text = new char[len];
		for (int i = 0; i < len; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static String readJsonFileAsString(String file){
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		}catch(Exception e) {
			return "";
		}
	}

	public String updateJson(String json,String jsonPath,String newValue) {	
		JsonNode newJson=JsonPath.using(config).parse(json).set(jsonPath,newValue).json();
		return newJson.toString();
	}


	public void validateResponseCode(Response response,int expCode) {
		int actCode = response.getStatusCode();
		if(response.getStatusCode()==expCode) {
			passed("Response Code is as expected "+expCode);
		}else {
			failAssert("Response code is not valid, Expected is "+expCode + ", But actual is "+actCode);
		}
	}
	/*
	 * public void validateJsonSchema(Response response,String filePath) { try {
	 * File schemaFile = new File(filePath);
	 * //JsonSchemaValidator.matchesJsonSchema(schemaFile); // Read the JSON schema
	 * from a file or resource //File file = new File(filePath); //InputStream
	 * schemaStream = JsonSchemaValidator.class.getResourceAsStream(filePath);
	 * 
	 * // Create an InputStream from the File object InputStream schemaStream = new
	 * FileInputStream(schemaFile); JSONObject rawSchema = new JSONObject(new
	 * JSONTokener(schemaStream)); Schema schema = SchemaLoader.load(rawSchema);
	 * 
	 * // Validate the JSON response against the JSON schema JSONArray jsonArray =
	 * new JSONArray(response.getBody().asString()); // jsonString is the JSON
	 * response as a string for (int i = 0; i < jsonArray.length(); i++) {
	 * JSONObject jsonObject = jsonArray.getJSONObject(i);
	 * schema.validate(jsonObject);
	 * passed("Response have the valid schema as expected"); }
	 * 
	 * } catch (ValidationException e) {
	 * fail("Reponse does not have the valid schema"); List<String> str =
	 * e.getAllMessages(); for(int i=0;i<str.size();i++) { fail(str.get(i)); } }
	 * catch (JSONException e) {
	 * fail("Exception caught while validating the schema "+e.getMessage()); } catch
	 * (IOException e) {
	 * fail("Exception caught while validating the schema "+e.getMessage()); } }
	 */

}

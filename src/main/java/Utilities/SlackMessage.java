package Utilities;

import com.aventstack.extentreports.model.Log;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;

import lombok.experimental.var;
import pages.dentist.DentistPatientsPage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackMessage {
	
	 static void findConversation(String name) {
	        // you can get this instance via ctx.client() in a Bolt app
	        MethodsClient client = Slack.getInstance().methods();
	        Logger logger = LoggerFactory.getLogger("my-awesome-slack-app");
	        try {
	            // Call the conversations.list method using the built-in WebClient
	            ConversationsListResponse result = client.conversationsList(r -> r
	                // The token you used to initialize your app
	                .token("xoxb-2014328433703-3643751381382-CsWtFKHpAdwxxnZyrfZlvm2r")
	            );
	            for (Conversation channel : result.getChannels()) {
	                if (channel.getName().equals(name)) {
	                    String conversationId = channel.getId();
	                    // Print result
	                    logger.info("Found conversation ID: {}", conversationId);
	                    // Break from for loop
	                    break;
	                }
	            }
	        } catch (IOException | SlackApiException e) {
	            logger.error("error: {}", e.getMessage(), e);
	        }
	    }

		/*
		 * public static void main(String[] args) throws Exception { // Find
		 * conversation with a specified channel `name` //
		 * findConversation("tester-channel"); //String url =
		 * "https://app-alpha.32co.com/verify?id=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InByYWthc2hhLnNoZXR0eSt5bWg4el9kZW50QDMyY28uY29tIiwiaWF0IjoxNjc2MDk5NTMyLCJleHAiOjE2NzY3MDQzMzJ9.Or2_QstlcxNzLafbtLrmFdvNWGyZvtL4YJD0mOlXmP0Book";
		 * // if(url.endsWith("Book")) { // url = url.substring(0, url.length()-4); // }
		 * //System.out.println(url); ObjectMapper mapper = new ObjectMapper(); //
		 * create instance of the File class File fileObj = new
		 * File("D:\\32Co\\workspace\\e2e\\src\\test\\resources\\json\\solo.json"); //
		 * use try-catch block to convert JSON data into Map try { // read JSON data
		 * from file using fileObj and map it using ObjectMapper and TypeReference
		 * classes Map<String, Object> userData = mapper.readValue( fileObj, new
		 * TypeReference<Map<String, Object>>() { }); // print all key-value pairs
		 * System.out.println(userData); } catch (Exception e) { // show error message
		 * e.printStackTrace(); }
		 * 
		 * DentistPatientsPage.createHashMapfromJsonFile(null); }
		 */
	    
	    
	    public static void main(String[] args) throws Exception {
			/*
			 * File file = new
			 * File("D:\\32Co\\workspace\\e2e\\src\\test\\resources\\json\\solo.json");
			 * String json = new String(Files.readAllBytes(file.toPath()));
			 * System.out.println(json); String widgetTitle = JsonPath.read(json,
			 * "$.patientDetail.firstName"); System.out.println(widgetTitle);
			 */
	    	
	   // 	String s = Page.dateFormat("dd/mm/yy","dd/mm/yyyy","14/02/23");
	    //	String s1 = Page.dateFormat("dd/mm/yy","yyyy-mm-dd","14/02/23");
	    //	Page.addDate(s1,2);
	    //	Page.addDate(s1,-2);
	    	findConversation("");
	    }

}

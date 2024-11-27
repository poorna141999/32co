package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;   
import java.util.Set;  

import com.fasterxml.jackson.databind.JsonNode;  
import com.fasterxml.jackson.databind.ObjectMapper;  

public class ValidateJSONSchema {
	// create inputStreamFromClasspath() method to load the JSON data from the class path    
	private static InputStream inputStreamFromClasspath( String path ) {  

		// returning stream  
		return Thread.currentThread().getContextClassLoader().getResourceAsStream( path );  
	}  

	public static void main(String[] args) throws IOException {
//		try {
			ObjectMapper objectMapper = new ObjectMapper();  
			java.io.File curdir = new java.io.File(".");
			String schemaPath = curdir.getCanonicalPath()+ "/src/test/resources/json/schema/admin/scanners.json" ;
			String filePath = curdir.getCanonicalPath()+ "/src/test/resources/json/schema/admin/delete.json" ;
			File schemaFile = new File(schemaPath);
			File real = new File(filePath);
			// create an instance of the JsonSchemaFactory using version flag  
//			JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012 );  


			InputStream schemaStream = new FileInputStream(schemaFile);
			InputStream jsonStream = new FileInputStream( real);


			// read data from the stream and store it into JsonNode  

			// get schema from the schemaStream and store it into JsonSchema  
//			JsonSchema schema = schemaFactory.getSchema(schemaStream);  
//			JsonNode json = objectMapper.readTree(jsonStream);  

			// create set of validation message and store result in it  
//			Set<ValidationMessage> validationResult = schema.validate( json );  

			// show the validation errors   
//			if (validationResult.isEmpty()) {  

				// show custom message if there is no validation error   
				System.out.println( "There is no validation errors" );  

//			} else {  

				// show all the validation error  
//				validationResult.forEach(vm -> System.out.println(vm.getMessage()));  
//			}
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
	}  
}  




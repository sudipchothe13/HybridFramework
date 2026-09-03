package JavaPrograms;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Rest {

	
	@Test
	public void m1() {
		
		RestAssured.baseURI = "baseURI";
		RequestSpecification httpRequest = RestAssured.given();
		Response resp = httpRequest.put("/test");
		
	}
}

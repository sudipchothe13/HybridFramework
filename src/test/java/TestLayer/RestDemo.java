package TestLayer;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestDemo {
	
	@Test
	public void m1() {
		
		RestAssured.baseURI = "http://localhost:8080/api/";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		
		Map<String, String> body = new HashMap<>();
		body.put("firstname", "Suraj");
		body.put("lastname", "Salunkhe");
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValueAsString(body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Response resp = httpRequest.get("/case_details/search?partyIdentifier=MYHBMB2024001");
		System.out.println(resp.getStatusCode());
		System.out.println(resp.asPrettyString());
	}
	
	@Test
	public void putCallMapObject() {

		System.out.println("===== PUT CASE DETAILS TEST STARTED =====");

		RestAssured.baseURI = "http://localhost:8080/api/";
		RequestSpecification httpRequest = RestAssured.given();

		httpRequest.header("Content-Type", "application/json");

		Map<String, String> body = new HashMap<>();
		body.put("caseOwnerName", "Suraj");

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonBody = mapper.writeValueAsString(body);
			System.out.println("Request Body:");
			System.out.println(jsonBody);
		} catch (JsonProcessingException e) {
			Assert.fail("Failed to convert request body to JSON", e);
		}

		httpRequest.body(body);

		Response resp = httpRequest.put("case_details/search?partyIdentifier=MYHBMB2024001");

		System.out.println("Response Status Code: " + resp.getStatusCode());
		System.out.println("Response Body:");
		System.out.println(resp.asPrettyString());

		// -------- ASSERTIONS --------
		Assert.assertEquals(resp.getStatusCode(), 200, "Expected HTTP status 200 but got " + resp.getStatusCode());

		Assert.assertNotNull(resp.getBody(), "Response body should not be null");

		// Handle both single-object and list response safely
		String responseString = resp.asString();

		Assert.assertTrue(responseString.contains("caseOwnerName"), "Response does not contain caseOwnerName field");

		Assert.assertTrue(responseString.contains("Suraj"), "Updated caseOwnerName not reflected in response");

		System.out.println("===== PUT CASE DETAILS TEST PASSED =====");
	}
	
	@Test
	public void putCallJSONObject() throws JSONException {

		System.out.println("===== PUT CASE DETAILS TEST STARTED =====");

		RestAssured.baseURI = "http://localhost:8080/api/";
		RequestSpecification httpRequest = RestAssured.given();

		httpRequest.header("Content-Type", "application/json");

		JSONObject body = new JSONObject();
		body.put("caseOwnerName", "Suraj");

		String jsonBody;
		try {
			jsonBody = body.toString(); // ✅ CORRECT
			System.out.println("Request Body:");
			System.out.println(jsonBody);
		} catch (Exception e) {
			Assert.fail("Failed to prepare JSON request body", e);
			return;
		}

		httpRequest.body(jsonBody); // ✅ SEND STRING JSON

		Response resp = httpRequest.put("case_details/search?partyIdentifier=MYHBMB2024001");

		System.out.println("Response Status Code: " + resp.getStatusCode());
		System.out.println("Response Body:");
		System.out.println(resp.asPrettyString());

		// -------- ASSERTIONS --------
		Assert.assertEquals(resp.getStatusCode(), 200, "Expected HTTP status 200 but got " + resp.getStatusCode());

		Assert.assertNotNull(resp.getBody(), "Response body should not be null");

		String responseString = resp.asString();

		Assert.assertTrue(responseString.contains("\"caseOwnerName\""),
				"Response does not contain caseOwnerName field");

		Assert.assertTrue(responseString.contains("Suraj"), "Updated caseOwnerName not reflected in response");

		System.out.println("===== PUT CASE DETAILS TEST PASSED =====");
	}


}

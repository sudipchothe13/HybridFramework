package TestLayer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import BaseLayer.BaseClass;
import CommonLayer.Context;
import UtilsLayer.UtilsApi;
import io.restassured.response.Response;
import pojoLayer.Companies;
import pojoLayer.CompanyDetails;
import pojoLayer.RootResponse;

public class CompaniesAndProductsDemo extends BaseClass {

	private Context context = new Context();

	@Test
	public void postCall() throws Exception {

		// ------------ Initializing Request specification----------------
		BaseClass.initRestAssured();

		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		BaseClass.addHeaders(headers);
		BaseClass.addPathParams("/companies");

		// ----------- Request body -----------
		CompanyDetails apple = new CompanyDetails();
		apple.setName("Apple");
		apple.setProducts(Arrays.asList("iPhone", "iPad", "MacBook"));
		apple.setHeadquarters("Cupertino, CA");
		apple.setCountry("USA");

		CompanyDetails samsung = new CompanyDetails();
		samsung.setName("Samsung");
		samsung.setProducts(Arrays.asList("Galaxy S23", "Galaxy Tab"));
		samsung.setHeadquarters("Seoul, South Korea");
		samsung.setCountry("Korea");

		Companies requestPayload = new Companies();

		requestPayload.setCompanies(Arrays.asList(apple, samsung));

		String requestBody = UtilsApi.serialization(requestPayload);
		BaseClass.addRequestBody(requestBody);

		// ----------------- Hit Request-------------

		Response response = BaseClass.sendRequest("POST");

		System.out.println("========== POST RESPONSE ==========");
		response.prettyPrint();

		// Deserialize
		RootResponse rootResponse = response.as(RootResponse.class);

		context.setRootResponse(rootResponse);

		// Status Code Assertion
		Assert.assertEquals(response.getStatusCode(), 201);

		// Company Assertions
		Assert.assertEquals(context.getRootResponse().getCompanies().get(0).getName(), "Apple");

		Assert.assertEquals(context.getRootResponse().getCompanies().get(1).getName(), "Samsung");

		Assert.assertEquals(context.getRootResponse().getCompanies().get(0).getProducts().get(0), "iPhone");

		Assert.assertEquals(context.getRootResponse().getCompanies().get(1).getHeadquarters(), "Seoul, South Korea");

	}

	@Test
	public void caseDetails_PostCall() throws Exception {

		// Initialize Rest Assured
		BaseClass.initRestAssured();

		// Headers
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		BaseClass.addHeaders(headers);

		// Endpoint
		BaseClass.addPathParams("/companies");

		// Query Param
		BaseClass.addQueryParams("country", "MYH");

		String filePath = System.getProperty("user.dir") + "/src/main/java/TestDataLayer/ExternalJsonPayload.json";

		// Read Request Payload
		String requestBody = UtilsApi.getPayload(filePath, "updateCompany");

		System.out.println("===== REQUEST =====");
		System.out.println(requestBody);

		// Add Request Body
		BaseClass.addRequestBody(requestBody);

		// Hit API
		Response response = BaseClass.sendRequest("POST");

		System.out.println("===== RESPONSE =====");
		response.prettyPrint();

		// Status Validation
		Assert.assertEquals(response.getStatusCode(), 201);

		// Read Expected JSON
		String expectedJson = UtilsApi.getPayload(filePath, "updateCompany");

		// Actual Response
		String actualJson = response.getBody().asString();

		// JSON Validation
		JSONAssert.assertEquals(expectedJson, actualJson, false);

		System.out.println("JSON Validation Passed");
	}

	@Test
	public void serializeAndDeserializeBody_PostCall() throws Exception {

		BaseClass.initRestAssured();

		BaseClass.addPathParams("student");

		JSONObject json = new JSONObject();

		json.put("id", 101);
		json.put("name", "Sudip");
		json.put("course", "Java");

		// Serialization
		ObjectMapper mapper = new ObjectMapper();
		String requestBody = mapper.writeValueAsString(json.toString());

		BaseClass.addRequestBody(requestBody);

		// Send Request
		Response response = BaseClass.sendRequest("POST");
	
/*		
		Student obj = new Student();

		obj.setId(101);
		obj.setName("Sudip");
		obj.setCourse("Java");

		// Serialization
		String json = new ObjectMapper().writeValueAsString(obj);

		// Deserialization
		Student student = response.as(Student.class);
*/		
		response.prettyPrint();
		// Validation from JSONObject
		Assert.assertEquals(json.getString("name"), "Sudip");
		Assert.assertEquals(json.getString("course"), "Java");
	}

	@Test
	public void readExternalJson_PostCall() throws IOException, JSONException {

		BaseClass.initRestAssured();
		BaseClass.addPathParams("student");

		String filePath = System.getProperty("user.dir") + "/src/main/java/TestDataLayer/ExternalJsonPayload.json";
		JsonNode root = new ObjectMapper().readTree(new File(filePath));
		String requestBody = root.get("updateCompany").toString();

		BaseClass.addRequestBody(requestBody);
		Response response = BaseClass.sendRequest("POST");

		JSONAssert.assertEquals(requestBody, response.asString(), false);
		System.out.println(response.asPrettyString());

	}
}

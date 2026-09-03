package stepDefinitions;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import BaseLayer.BaseClass;
import CommonLayer.Context;
import UtilsLayer.Log;
import UtilsLayer.UtilsApi;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojoLayer.Companies;
import pojoLayer.CompanyDetails;
import pojoLayer.RootResponse;

	public class CompaniesAPIStepDef extends BaseClass {

	    private Context context = new Context();

	    private Response response;
	    private String requestBody;
	    private String expectedJson;

	    @Given("User initializes company request using POJO")
	    public void user_initializes_company_request_using_pojo() throws Exception {

	        BaseClass.initRestAssured();

	        HashMap<String, String> headers = new HashMap<>();
	        headers.put("Content-Type", "application/json");
	        Log.info("BBBBBBBBB");
	        BaseClass.addHeaders(headers);
	        BaseClass.addPathParams("/companies");

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

	        Companies companies = new Companies();
	        companies.setCompanies(Arrays.asList(apple, samsung));

	        requestBody = UtilsApi.serialization(companies);

	        BaseClass.addRequestBody(requestBody);
	    }

	    @When("User sends POST request to companies endpoint")
	    public void user_sends_post_request_to_companies_endpoint() {

	        response = BaseClass.sendRequest("POST");
	    }

	    @Then("Company response should be validated")
	    public void company_response_should_be_validated() {

	        RootResponse rootResponse =
	                response.as(RootResponse.class);

	        context.setRootResponse(rootResponse);

	        Assert.assertEquals(response.getStatusCode(), 201);

	        Assert.assertEquals(
	                context.getRootResponse()
	                        .getCompanies()
	                        .get(0)
	                        .getName(),
	                "Apple");
	    }

	    @Given("User reads {string} payload from external JSON")
	    public void user_reads_payload_from_external_json(String payloadName)
	            throws Exception {

	        BaseClass.initRestAssured();
	        Log.info("BBBBBBBBB");
	        BaseClass.addPathParams("/companies");

	        BaseClass.addQueryParams(
	                "country",
	                "MYH");

	        String filePath =
	                System.getProperty("user.dir")
	                        + "/src/main/java/TestDataLayer/ExternalJsonPayload.json";

	        requestBody =
	                UtilsApi.getPayload(
	                        filePath,
	                        payloadName);

	        expectedJson = requestBody;

	        BaseClass.addRequestBody(requestBody);
	    }

	    @When("User sends POST request to companies endpoint with query parameter")
	    public void user_sends_post_request_to_companies_endpoint_with_query_parameter() {

	        response =
	                BaseClass.sendRequest("POST");
	    }

	    @Then("External JSON response should be validated")
	    public void external_json_response_should_be_validated()
	            throws Exception {

	        Assert.assertEquals(
	                response.getStatusCode(),
	                201);

	        JSONAssert.assertEquals(
	                expectedJson,
	                response.asString(),
	                false);
	    }

	    @Given("User creates student payload using JSONObject")
	    public void user_creates_student_payload_using_json_object()
	            throws Exception {

	        BaseClass.initRestAssured();

	        BaseClass.addPathParams("student");

	        JSONObject json =
	                new JSONObject();

	        json.put("id", 101);
	        json.put("name", "Sudip");
	        json.put("course", "Java");

	        requestBody =
	                json.toString();

	        BaseClass.addRequestBody(requestBody);
	    }

	    @When("User sends POST request to student endpoint")
	    public void user_sends_post_request_to_student_endpoint() {

	        response =
	                BaseClass.sendRequest("POST");
	    }

	    @Then("Student response should be validated")
	    public void student_response_should_be_validated() {

	        Assert.assertEquals(
	                response.getStatusCode(),
	                201);
	    }

	    @Given("User reads payload using JsonNode from external JSON")
	    public void user_reads_payload_using_json_node_from_external_json()
	            throws Exception {

	        BaseClass.initRestAssured();

	        BaseClass.addPathParams("student");

	        String filePath =
	                System.getProperty("user.dir")
	                        + "/src/main/java/TestDataLayer/ExternalJsonPayload.json";

	        JsonNode root =
	                new ObjectMapper()
	                        .readTree(new File(filePath));

	        requestBody =
	                root.get("updateCompany")
	                    .toString();
	        Log.info("BBBBBBBBB");
	        BaseClass.addRequestBody(requestBody);
	    }

	    @When("User sends POST request to student endpoint using external payload")
	    public void user_sends_post_request_to_student_endpoint_using_external_payload() {

	        response =
	                BaseClass.sendRequest("POST");
	    }

	    @Then("Request and response JSON should match")
	    public void request_and_response_json_should_match()
	            throws Exception {

	        JSONAssert.assertEquals(
	                requestBody,
	                response.asString(),
	                false);
	    }
	}


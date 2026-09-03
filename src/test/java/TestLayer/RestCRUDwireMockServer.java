package TestLayer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestCRUDwireMockServer {

    WireMockServer server;

    @BeforeMethod
    public void setup() {

        // Start WireMock Server
        server = new WireMockServer(3000);
        server.start();

        // Common Response Body
        String responseBody =
                "{\n" +
                "  \"id\": 101,\n" +
                "  \"firstName\": \"Sudip\",\n" +
                "  \"lastName\": \"Chothe\",\n" +
                "  \"email\": \"sudip.chothe@gmail.com\",\n" +
                "  \"mobile\": \"9876543210\",\n" +
                "  \"department\": \"QA Automation\",\n" +
                "  \"salary\": 85000,\n" +
                "  \"city\": \"Mumbai\",\n" +
                "  \"status\": \"Employee Created Successfully\",\n" +
                "  \"createdAt\": \"2026-05-19T10:45:30\"\n" +
                "}";

        // =========================================
        // GET
        // =========================================

        server.stubFor(
                get(urlEqualTo("/employees/3030"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody(responseBody)
                        ));

        // =========================================
        // POST
        // =========================================

        server.stubFor(
                post(urlEqualTo("/employees"))
                        .willReturn(
                                aResponse()
                                        .withStatus(201)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody(responseBody)
                        ));

        // =========================================
        // PUT
        // =========================================

        server.stubFor(
                put(urlEqualTo("/employees/3030"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody(responseBody)
                        ));

        // =========================================
        // PATCH
        // =========================================

        server.stubFor(
                patch(urlEqualTo("/employees/3030"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody(responseBody)
                        ));
    }
    @AfterMethod
    public void tearDown() {

        server.stop();
    }

    // ==================================================
    // POST API
    // ==================================================

    @Test
    public void postCall() throws JsonProcessingException {
/*
        // Base URI
        RestAssured.baseURI = "http://localhost:3000";

        // Request Specification
        RequestSpecification httpRequest =
                RestAssured.given();

        // Header
        httpRequest.header("Content-Type",
                "application/json");

        // Request Body
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("fname", "Sudeep");
        jsonBody.put("lastName", "Chothe");

        // Convert JSON to String
        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = objectMapper.writeValueAsString(jsonBody.toMap());

        // Add Body
        httpRequest.body(requestBody);

        // POST Request
        Response response = httpRequest.post("/employees");

        // Print Response
        System.out.println("POST Response:");
        System.out.println(response.getBody().asPrettyString());

        // Assertions
        Assert.assertEquals(
                response.getStatusCode(), 201);

        Assert.assertTrue(
                response.getStatusLine()
                        .contains("Created"));

        Assert.assertTrue(
                response.getBody()
                        .asString()
                        .contains("Employee Created"));

        Assert.assertTrue(
                response.getHeader("Content-Type")
                        .contains("application/json"));
        
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotEquals(response.getStatusCode(), 404);
        Assert.assertTrue(response.getBody().asString().contains("Sudip"));
        Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
                
        
        Assert.assertEquals(response.jsonPath().getString("firstName"), "Sudip");
        Assert.assertEquals(response.jsonPath().getString("city"), "Mumbai");
        Assert.assertEquals(response.jsonPath().getInt("salary"), 85000);

        System.out.println("POST Assertions Passed");
    */    
   //     String summonsExpiryDateOnUI = Wb.getText();
        
  //Assert.assertEquals(context.getRootResponse().getData().getCaseDetails().getSummonsExpiryDate(), summonsExpiryDateOnUI)   ;
        
  //      DbUtility dbUtility = new DbUtility();
        
        
   //     String summonQueryDB = "SELECT case_number, summons_issue_date, summons_expiry_date";
        
  //     String summonsDbDetails =  dbUtility.selectQuery_Map(summonQueryDB);
        
    }

    // ==================================================
    // GET API
    // ==================================================

    @Test
    public void getCall() {

        // Base URI
        RestAssured.baseURI = "http://localhost:3000";

        // Request Specification
        RequestSpecification httpRequest = RestAssured.given();
                

        // Header
        httpRequest.header("Content-Type", "application/json");
                

        // GET Request
        Response response = httpRequest.get("/employees/3030");
                

        // Print Response
        System.out.println("GET Response:");

        System.out.println(response.getBody().asPrettyString());
                

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(response.getStatusCode(), 404);
        Assert.assertTrue(response.getBody().asString().contains("Sudip"));
        Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
                
        
        Assert.assertEquals(response.jsonPath().getString("firstName"), "Sudip");
        Assert.assertEquals(response.jsonPath().getString("city"), "Mumbai");
        Assert.assertEquals(response.jsonPath().getInt("salary"), 85000);
              

        System.out.println("GET Assertions Passed");
    }
}
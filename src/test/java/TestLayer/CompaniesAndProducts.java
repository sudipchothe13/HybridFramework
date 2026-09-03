package TestLayer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import BaseLayer.BaseClass;
import CommonLayer.Context;
import io.restassured.response.Response;
import pojoLayer.Companies;
import pojoLayer.CompanyDetails;
import pojoLayer.RootResponse;

public class CompaniesAndProducts extends BaseClass {

    private Context context = new Context();
    private WireMockServer wireMockServer;

    @BeforeClass
    public void setup() throws InterruptedException {

    	
        wireMockServer = new WireMockServer(8081);
        wireMockServer.stop();
        Thread.sleep(3000);
        wireMockServer.start();

        System.out.println("WireMock Started");
        System.out.println("Running = " + wireMockServer.isRunning());

        // POST Stub
        wireMockServer.stubFor(
                post(urlEqualTo("/companies"))
                        .willReturn(
                                aResponse()
                                        .withStatus(201)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("""
                                        {
                                          "companies":[
                                            {
                                              "name":"Apple",
                                              "products":["iPhone","iPad","MacBook"],
                                              "headquarters":"Cupertino, CA"
                                            },
                                            {
                                              "name":"Samsung",
                                              "products":["Galaxy S23","Galaxy Tab", "Galaxy 123"],
                                              "headquarters":"Seoul, South Korea"
                                            }
                                          ]
                                        }
                                        """)));

        // GET Stub for Browser
        wireMockServer.stubFor(
                get(urlEqualTo("/companies"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("""
                                        {
                                          "companies":[
                                            {
                                              "name":"Apple",
                                              "products":["iPhone","iPad","MacBook"],
                                              "headquarters":"Cupertino, CA"
                                            },
                                            {
                                              "name":"Samsung",
                                              "products":["Galaxy S23","Galaxy Tab"],
                                              "headquarters":"Seoul, South Korea"
                                            }
                                          ]
                                        }
                                        """)));
    }

    @Test(priority = 1)
    public void postCall() throws Exception {

        BaseClass.initRestAssured();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        BaseClass.addHeaders(headers);
        BaseClass.addPathParams("/companies");

        CompanyDetails apple = new CompanyDetails();
        apple.setName("Apple");
        apple.setProducts(Arrays.asList("iPhone", "iPad", "MacBook"));
        apple.setHeadquarters("Cupertino, CA");

        CompanyDetails samsung = new CompanyDetails();
        samsung.setName("Samsung");
        samsung.setProducts(Arrays.asList("Galaxy S23", "Galaxy Tab", ""));
        samsung.setHeadquarters("Seoul, South Korea");

        Companies requestBody = new Companies();
        requestBody.setCompanies(Arrays.asList(apple, samsung));

        BaseClass.addRequestBody(requestBody);

        Response response = BaseClass.sendRequest("POST");

        System.out.println("========== POST RESPONSE ==========");
        response.prettyPrint();

        // Deserialize
        RootResponse rootResponse =
                response.as(RootResponse.class);

        context.setRootResponse(rootResponse);

        // Status Code Assertion
        Assert.assertEquals(response.getStatusCode(), 201);

        // Verify POST hit
        wireMockServer.verify(
                postRequestedFor(urlEqualTo("/companies")));

        // Company Assertions
        Assert.assertEquals(
                context.getRootResponse()
                       .getCompanies()
                       .get(0)
                       .getName(),
                "Apple");

        Assert.assertEquals(
                context.getRootResponse()
                       .getCompanies()
                       .get(1)
                       .getName(),
                "Samsung");

        Assert.assertEquals(
                context.getRootResponse()
                       .getCompanies()
                       .get(0)
                       .getProducts()
                       .get(0),
                "iPhone");

        Assert.assertEquals(
                context.getRootResponse()
                       .getCompanies()
                       .get(1)
                       .getHeadquarters(),
                "Seoul, South Korea");

        System.out.println("====================================");
        System.out.println("All Assertions Passed");
        System.out.println("WireMock Running = " + wireMockServer.isRunning());
        System.out.println();
        System.out.println("Open below URL in Chrome:");
        System.out.println("http://localhost:8080/companies");
        System.out.println("====================================");

        
    }
}
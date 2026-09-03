package TestLayer;

import org.testng.Assert;
import org.testng.annotations.Test;

import BaseLayer.BaseClass;
import io.restassured.response.Response;
import pojoLayer.CaseDetails;

public class CaseDetailsAPITest {
	
	@Test
	public void getCaseDetails() {
		BaseClass.initRestAssured();
		BaseClass.addPathParams("case_details/search");
		BaseClass.addQueryParams("partyIdentifier", "MYHBMB2024018");
		BaseClass.sendRequest("GET");
		
		Response response = BaseClass.getResponse();
		
        // Assertions
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        Assert.assertTrue(response.getTime() < 500, "Response took too long"); // response time < 5s

        // Deserialize JSON response to POJO
        CaseDetails caseDetails = response.as(CaseDetails.class);

        // Field assertions
        Assert.assertEquals(caseDetails.getLitigationCaseNumber(), "100001", "Case number mismatch");
        Assert.assertEquals(caseDetails.getCaseOwnerId(), "200123");
        
    
		
		
	}

}

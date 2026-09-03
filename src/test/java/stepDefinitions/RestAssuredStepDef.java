package stepDefinitions;

import org.testng.Assert;

import BaseLayer.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojoLayer.CaseDetails;
import pojoLayer.RootResponse;

public class RestAssuredStepDef extends BaseClass {

	private CaseDetails caseDetails;

	@Given("user sets request specification")
	public void user_sets_request_specification() {
		BaseClass.initRestAssured();
	}

	@When("user sends GET request")
	public void user_sends_get_request() {
		// Set endpoint
		BaseClass.addPathParams("/case_details");
//		BaseClass.addQueryParams("partyIdentifier", "MYHBMB2024001");

		// Send GET request
		BaseClass.sendRequest("GET");

		// Deserialize response into RootResponse
		RootResponse response = BaseClass.getResponse().as(RootResponse.class);

		caseDetails = response.getData().getCaseDetails();

	}

	@Then("user verify success message")
	public void user_verify_success_message() {
		// ---------- Assertions ----------
		Assert.assertEquals(BaseClass.getStatusCode(), 200);
		Assert.assertEquals(caseDetails.getCaseOwnerId(), "EMP5001");
		Assert.assertEquals(caseDetails.getCaseOwnerManager(), "Manoj Kulkarni");
		Assert.assertEquals(caseDetails.getCaseOwnerName(), "Rakesh Kumar");
		Assert.assertEquals(caseDetails.getLitigationCaseNumber(), 100001);
		Assert.assertEquals(caseDetails.getLitigationType(), "Loan Recovery");
		Assert.assertEquals(caseDetails.getOutstandingBalance(), 1523.75);
		Assert.assertEquals(caseDetails.getPartyIdentifier(), "MYHBMB2024001");
		Assert.assertEquals(caseDetails.getCreatedBy(), "SYSTEM");
		Assert.assertEquals(caseDetails.getUpdatedBy(), "SYSTEM");

		// Optional: print values
		System.out.println("CaseOwner: " + caseDetails.getCaseOwnerName());
	}
	
	@Then("user verify success message for GET call")
	public void user_verify_success_message_for_get_call() {

	}
}

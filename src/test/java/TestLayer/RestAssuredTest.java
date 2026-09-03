package TestLayer;

import java.util.HashMap;
import java.util.Map;

import BaseLayer.BaseClass;

public class RestAssuredTest {

	public static void main(String[] args) {

		RestAssuredTest t = new RestAssuredTest();
		t.PostCall();
		t.GetCall();

	}

	public void PostCall() {

		// --------- Request Body ----------
		Map<String, Object> payload = new HashMap<>();
		payload.put("id", 109);
		payload.put("partyIdentifier", "MYHBMB00010109");
		payload.put("name", "Jack Thompsons");
		payload.put("email", "jack.thompsons@example.com");
		payload.put("phone", "+1-202-555-10100");
		payload.put("address", "741 Poplar Ln, San Francisco, CAT, USA");

		// --------- POST Call ----------
		BaseClass.initRestAssured();
		BaseClass.addRequestBody(payload);

		BaseClass.sendRequest("POST");

		System.out.println("POST Status Code : " + BaseClass.getStatusCode());
		System.out.println("POST Response    : " + BaseClass.getResponseBody());

		BaseClass.clear();
	}

	public void GetCall() {
		BaseClass.initRestAssured();

		BaseClass.addQueryParams("party_identifier", "MYHBMB00010109");
		BaseClass.sendRequest("GET");

		System.out.println("GET Status Code : " + BaseClass.getStatusCode());
		System.out.println("GET Response    : " + BaseClass.getResponseBody());

		BaseClass.clear();
	}
}

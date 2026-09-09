package stepDefinitions;

import java.util.List;
import java.util.Map;

import BaseLayer.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DataTableConceptStepDefinition extends BaseClass {

	@Given("user is on Login page")
	public void user_is_on_login_page() {
		System.out.println("User is on Login page");
	}

/*
 * 		Data-driven testing using DataTable ---> asLists() method
 * 		Feature file:
 * 			When user enter username and password using asLists
 * 				| Admin | admin123 |
 */
	 @When("user enter username and password using asLists")
	public void user_enter_username_and_password_using_asLists(DataTable dataTable) {
		List<List<String>> list = dataTable.asLists();
		String username = list.get(0).get(0);
		String password = list.get(0).get(1);
		System.out.println("Username ---> " + username);
		System.out.println("Password ---> " + password);
	}

	 /*
	  * 		Data-driven testing using DataTable ---> cell() method
	  * 		Feature file:
	  * 			When user enter username and password using asLists
	  * 				| Admin | admin123 |
	  */
	 
	 @When("user enter username and password using cell")
	public void user_enter_username_and_password_using_cell(DataTable dataTable) {
		String username = dataTable.cell(0, 0);
		String password = dataTable.cell(0, 1);
		System.out.println("Username ---> " + username);
		System.out.println("Password ---> " + password);
	}

	 /*
	  * 		Data-driven testing using DataTable ---> asMap() method
	  * 		Feature file:
	  * 			When user enter username and password using asLists
	  * 				| username | password |
	  * 				| Admin    | admin123 |
	  */
	 @When("user enter username and password using asMaps")
	public void user_enter_username_and_password_using_asMaps(DataTable dataTable) {
		List<Map<String, String>> listMap = dataTable.asMaps();
		String username = listMap.get(0).get("username");
		String password = listMap.get(0).get("password");
		System.out.println("Username ---> " + username);
		System.out.println("Password ---> " + password);
	}

	@Then("user click on Login button")
	public void user_click_on_login_button() {
		System.out.println("User click on Login button");
	}

}

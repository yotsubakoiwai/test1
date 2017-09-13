package steps;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MercuryToursApiSteps {

	private RequestSpecification requestSpec;
	private Response response;
	Logger log =Logger.getLogger(MercuryToursApiSteps.class);
	
	@Given("^I create a request for URL \"(.+)\"$")
	public void i_create_a_request_for_URL(String url) throws Throwable {
		requestSpec = RestAssured.with();
		requestSpec.baseUri(url);
	}
	
	@When("^I send the registration request with this user input:$")
	public void i_send_the_request_with_this_user_input(Map<String, String> regMap) throws Throwable {
		String bodyString = "";

		Set<Map.Entry<String,String>> s = regMap.entrySet();
		Iterator<Map.Entry<String,String>> it = s.iterator();
		while(it.hasNext()) {
			Map.Entry me = (Map.Entry)it.next();
			bodyString = bodyString + me.getKey() + "=" + me.getValue();
			if (it.hasNext()) {
				bodyString = bodyString + "&";
			}
		}
		
		String encodedBodyString = URLEncoder.encode(bodyString, "UTF-8");

//		log.info("Completed x-www-form-urlencoded: '" + encodedBodyString + "'");
		requestSpec.body(encodedBodyString);
		response = requestSpec.post("/mercurycreate_account.php");
	}

	@Then("^the registration status is \"(\\d+)\"$")
	public void the_registration_status_is(int expectedStatus) throws Throwable {
//	    log.info("Response: " + response.asString());
	    Assert.assertEquals(expectedStatus, response.getStatusCode());
	}
}

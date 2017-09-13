package steps;

import org.apache.log4j.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;
import cucumber.api.java.en.*;  // Given, When, Then
import org.junit.Assert;
import runsupport.DriverFactory;

public class RestDemoSteps {
	
	private RequestSpecification requestSpec;
	private Response response;
	protected static Logger log = Logger.getLogger(RestDemoSteps.class);;

	@Given("^I create a demo request for URL \"(.+)\"$")
	public void i_create_a_request_for_URL(String url) throws Throwable {
		requestSpec = RestAssured.with();
		requestSpec.baseUri(url);
	}
	
	@Given("^I set the JSON validation string to:$")
	public void i_set_the_JSON_validation_string_to(String aPotentialJSONString) throws Throwable {
		requestSpec = RestAssured.with();
	    requestSpec.given().parameter("json", aPotentialJSONString);
	}

	@When("^I validate the string by invoking end point:$")
	public void i_validate_the_string_by_invoking_end_point(String targetRestUrl) throws Throwable {
		log.info("URL=" + targetRestUrl);
	    response = requestSpec.when().get(targetRestUrl);
	    
	}

	@Then("^the HTTP status code is \"(\\d+)\", object is \"([^\"]*)\", empty is \"([^\"]*)\" and validate is \"([^\"]*)\"$")
	public void the_HTTP_status_code_is_object_is_empty_is_and_validate_is(int expectedHttpStatus, String objectOrArray, 
			String emptyTrueOrFalse, String validateTrueOrFalse) throws Throwable {
		
		boolean empty = Boolean.parseBoolean(emptyTrueOrFalse);
		boolean validate = Boolean.parseBoolean(validateTrueOrFalse);
		
		log.info("Response=" + response.print());
		response.then().
			body("object_or_array", equalTo(objectOrArray)).and().
			body("empty", is(empty)).and().
			body("validate", is(validate)).and().
			statusCode(expectedHttpStatus);
	}

	@When("^I send that request$")
	public void i_send_that_request() throws Throwable {
	    response = requestSpec.when().get("/weather/today/l/20878:4:US");
	}

	@Then("^the response status is \"(\\d+)\"$")
	public void the_response_status_is(int expectedStatus) throws Throwable {
		Assert.assertEquals(expectedStatus, response.getStatusCode());
		//Headers allHeaders = response.getHeaders();
	}
	
	@Then("^the Content-Type is \"(.+)\"$")
	public void the_content_type_is(String allowedType) {
		Assert.assertEquals(allowedType, response.getHeaders().getValue("Content-Type"));
	}
	
	@Then("^the Content-Encoding is \"(.+)\"$")
	public void content_encoding_is(String encoder){
		Assert.assertEquals(encoder, response.getHeaders().getValue("Content-Encoding"));
	}
}

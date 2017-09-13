package steps;

import org.apache.log4j.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.internal.path.xml.NodeImpl;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.element.Node;
import com.jayway.restassured.path.xml.element.NodeChildren;
import static com.jayway.restassured.path.xml.XmlPath.*;
import static com.jayway.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;

import static org.hamcrest.Matchers.*;

import cucumber.api.PendingException;
import cucumber.api.java.en.*;  // Given, When, Then
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankCodesSteps {
	private RequestSpecification requestSpec;
	private Response response;
	private String soapBody = "";
	private String xmlResponse;
	protected static Logger log = Logger.getLogger(BankCodesSteps.class);;
	
	@Given("^I build a request to get bank codes from \"(.+)\"$")
	public void i_build_a_request_to_get_bank_codes_from(String url) throws Throwable {
		requestSpec = RestAssured.given().request();
		requestSpec.baseUri(url);
	}
	
	@Given("^the namespace is \"(.+)\"$")
	public void the_name_space_is(String nameSpace) {
		soapBody = CreateSoapVersion1_1_EnvelopStart(nameSpace);
	}

	@When("^I send the request to \"(.+)\" to get bank code \"(.+)\"$")
	public void i_send_the_request_to_get_bank_code(String postPath, String bankCode) throws Throwable {
		soapBody = soapBody + "<soap:Body> \n" +
                "  <tns:getBank> \n" +
                "    <tns:blz>" + bankCode + "</tns:blz> \n" +
                "  </tns:getBank> \n" +
                "</soap:Body> \n" +
                "</soap:Envelope>";
		System.out.println("** soapBody: " + soapBody);
		System.out.println("--------------------------------");
		xmlResponse = requestSpec.body(soapBody).when().post(postPath).andReturn().asString();
		String prettyXml = with(xmlResponse).prettify();
		System.out.println(prettyXml);
	}

	@Then("^Bezeichnung is \"(.+)\"$")
	public void bezeichnung_is(String arg1) throws Throwable {
		 XmlPath xmlPath = new XmlPath(xmlResponse);
		 assertThat(xmlPath.getString("Envelope.Body.getBankResponse.details.bezeichnung"), equalTo(arg1));
	}

	@Then("^bic is \"(.+)\"$")
	public void bic_is(String arg1) throws Throwable {
		 XmlPath xmlPath = new XmlPath(xmlResponse);
		 assertThat(xmlPath.getString("Envelope.Body.getBankResponse.details.bic"), equalTo(arg1));
	}

	@Then("^ort is \"(.+)\" and plz is \"(\\d+)\"$")
	public void ort_is_and_plz_is(String ort, int plz) throws Throwable {
		 XmlPath xmlPath = new XmlPath(xmlResponse);
		 assertThat(xmlPath.getString("Envelope.Body.getBankResponse.details.ort"), equalTo(ort));
		 assertThat(xmlPath.getInt("Envelope.Body.getBankResponse.details.plz"), equalTo(plz));
	}
	
	@Then("^name space \"(.+)\" is \"(.+)\"$")
	public void name_space_is(String arg1, String arg2) throws Throwable {
		//Verify URI of namespace
		XmlPath xmlPath = new XmlPath(xmlResponse).using(xmlPathConfig().namespaceAware(false)); //Need rest-assured 1.9.1
		String assertString = "soapenv:Envelope.soapenv:Body." + arg1 +":getBankResponse.@xmlns:" + arg1;
		assertThat(xmlPath.getString(assertString), equalTo(arg2));
		
		//Next, verify that rest-assured XML paring respects namespace; more for my own education. This would not
		//  normally be part of the test.be part of 
		xmlPath = new XmlPath(xmlResponse).using(xmlPathConfig().declaredNamespace(arg1, arg2));

        // Then
        assertThat(xmlPath.getString("Envelope.Body.getBankResponse." + arg1 + ":details." + arg1 + 
        		":bezeichnung.text()"), equalTo("ABK-Kreditbank"));
        
        xmlPath = new XmlPath(xmlResponse);  // No name space so specifying name space in the path yields nothing
        assertThat(xmlPath.getString("Envelope.Body.getBankResponse." + arg1 + ":details." + arg1 
        		+ ":bezeichnung.text()"), equalTo(""));
	}

	public String CreateSoapVersion1_1_EnvelopStart(String nameSpace) {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
			   "<soap:Envelope \n" +
	           "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" \n" +
			   "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
	           "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
			   "xmlns:tns=\"" + nameSpace +"\"> \n";
	}
}

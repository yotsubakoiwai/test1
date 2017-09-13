package steps;

import java.util.List;

import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.path.xml.element.Node;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static com.jayway.restassured.path.xml.XmlPath.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ODataSteps {
	String sampleXml = "";
	
	@Given("^I have a simple service document$")
	public void i_have_a_simple_service_document() {
		sampleXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\" ?>" +
	        "<service xml:base=\"http://services.odata.org/OData/OData.svc/\"" +
			"         xmlns:atom=\"http://www.w3.org/2005/Atom\"" +
	        "         xmlns:app=\"http://www.w3.org/2007/app\"" +
			"         xmlns=\"http://www.w3.org/2007/app\">" +
	        "  <workspace>" +
			"    <atom:title>Default</atom:title>" +
	        "    <collection href=\"Products\">" + 
			"      <atom:title>Products</atom:title>" +
	        "    </collection>" +
			"    <collection href=\"Categories\">" +
	        "      <atom:title>Categories</atom:title>" + 
			"    </collection>" +
	        "    <collection href=\"Suppliers\">" +
			"      <atom:title>Suppliers</atom:title>" +
	        "    </collection>" +
			"  </workspace>" +
	        "</service>";
	}
	
	@When("^I search for available collections I find \"(\\d+)\"$")
	public void i_search_for_available_collections_i_find(int expectedNumCollections) {
		XmlPath xmlPath = new XmlPath(sampleXml);
		final List<Node> collections = xmlPath.getList("service.workspace.collection", Node.class);
		assertThat(collections.size(), equalTo(expectedNumCollections));
		
		final List<String> collectionsStr = given(sampleXml).getList(
				"service.workspace.collection.findAll {it.title.text()}", String.class);
		assertThat(collectionsStr, hasItems("Products", "Categories", "Suppliers"));
	}

}

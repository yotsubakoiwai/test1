package steps;

import org.junit.Assert;

import cucumber.api.PendingException;
import pages.GoogleSearchPage;
import runsupport.DriverFactory;
import cucumber.api.java.en.*;  // Given, When, Then

public class SearchSteps extends DriverFactory{
	
	@Given("^I am on the main Google search webpage$")
	public void i_am_on_the_main_Google_search_webpage() throws Throwable {
		driver.get("http://www.google.com");
	}

	@Given("^I search for \"([^\"]*)\"$")
	public void i_search_for(String criteria) throws Throwable {
	    GoogleSearchPage searchPage = new GoogleSearchPage(driver);
	    searchPage.enterSearchCriteria(criteria);
	}

	@When("^I click the search button$")
	public void i_click_the_search_button() throws Throwable {
	    new GoogleSearchPage(driver).pressSearchButton(); // More compact for single line
	}

	@When("^I click the first result$")
	public void i_click_the_first_result() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should land on the qastuffs blog webpage$")
	public void i_should_land_on_the_qastuffs_blog_webpage() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
}
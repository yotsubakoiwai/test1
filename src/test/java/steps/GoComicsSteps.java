package steps;

import org.testng.Assert;

import cucumber.api.PendingException;
import pages.GoComicsPage;
import pages.GoComicsDetailPage;
import runsupport.DriverFactory;
import cucumber.api.java.en.*;  // Given, When, Then

public class GoComicsSteps extends DriverFactory {

	@Given("^I am on the GOCOMICS comics page$")
	public void i_am_on_the_GOCOMICS_comics_page() throws Throwable {
		driver.get("http://www.gocomics.com/explore/comics");
	    driver.manage().window().maximize();
	}

	@When("^I select \"([^\"]*)\"$")
	public void i_select(String comicName) throws Throwable {
	    new GoComicsPage(driver).i_select_comic_named(comicName);
	}

	@Then("^the \"([^\"]*)\" comic strip is displayed$")
	public void the_comic_strip_is_displayed(String comicName) throws Throwable {
	    Assert.assertTrue(new GoComicsDetailPage(driver).PageUrlIsFor(comicName),comicName + " not recognized");
	}
}

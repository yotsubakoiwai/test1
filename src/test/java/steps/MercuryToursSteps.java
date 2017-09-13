package steps;

import java.net.URLEncoder;
import java.util.*;

import org.apache.log4j.Logger;
import org.junit.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import Registration.ContactInformation;
import Registration.MailingInformation;
import Registration.UserInformation;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import helpers.Price;
import pages.MercuryToursHomePage;
import pages.RegisterNewUserPage;
import pages.RegisteredPage;
import runsupport.DriverFactory;

public class MercuryToursSteps extends DriverFactory{
	
	private RegisterNewUserPage newUserPage;
	
	protected static Logger log = Logger.getLogger(MercuryToursSteps.class);	
	@Given("^I am on the Mecury Tours web site$")
	public void i_am_on_the_Mecury_Tours_web_site() throws Throwable {
		driver.get("http://newtours.demoaut.com");
	    driver.manage().window().maximize();
	}

	@When("^I request to register a new account$")
	public void i_request_to_register_a_new_account() throws Throwable {
	    new MercuryToursHomePage(driver).register_a_new_account();
	    this.newUserPage = new RegisterNewUserPage(driver);
	}

	@Then("^I am asked for and enter Contact Information:$")
	public void i_am_asked_for_and_enter_Contact_Information(List<ContactInformation> contactInfo) throws Throwable {
	    Assert.assertEquals("Must supply one and only one contact information instance", contactInfo.size(), 1);
	    newUserPage.enter_Contact_Information(contactInfo.get(0));
	}

	@Then("^I am asked for and enter Mailing Information:$")
	public void i_am_asked_for_and_enter_Mailing_Information(List<MailingInformation> mailingInfo) throws Throwable {
		Assert.assertEquals("Must supply one and only one Mailing information instance", mailingInfo.size(), 1);
		this.newUserPage.enter_Mailing_Information(mailingInfo.get(0));
	}

	@Then("^I am asked for and enter User Information:$")
	public void i_am_asked_for_and_enter_User_Information(List<UserInformation> userInfo) throws Throwable {
		Assert.assertEquals("Must supply one and only one User information instance", userInfo.size(), 1);
		this.newUserPage.enter_user_info(userInfo.get(0));
		
	}

	@When("^I submit the new registration request$")
	public void i_submit_the_new_registration_request() throws Throwable {
		this.newUserPage.submit_the_new_registration_request();
	}
	
	@Then("^the registered page is displayed with salutations to \"(.*)\" and with user name \"(.*)\"$")
	public void the_registered_page_is_displayed_salutation_to_with_user_name(String firstLastName, String userName)  {
		RegisteredPage regedPage = new RegisteredPage(driver);
		Assert.assertTrue("Unexpected salutation", regedPage.compareSalutationTextFor(firstLastName));
		Assert.assertTrue("Unexpected start of messsage body", regedPage.compareMessageBodyStart());
		Assert.assertTrue("Unexpected embedded link text", regedPage.compareEmbeddedLink());
		Assert.assertTrue("Unexpected user name notification", regedPage.compareUserNameNote(userName));
	}
}

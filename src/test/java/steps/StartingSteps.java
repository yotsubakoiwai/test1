package steps;

import runsupport.DriverFactory;
import cucumber.api.java.en.*;

//import cucumber.api.PendingException;

public class StartingSteps extends DriverFactory{
	
	@Given("^the user is on landing page$")
	public void the_user_is_on_landing_page() throws Throwable {
	    driver.get("http://accountsdemo.herokuapp.com");
	    driver.manage().window().maximize();
	}
}

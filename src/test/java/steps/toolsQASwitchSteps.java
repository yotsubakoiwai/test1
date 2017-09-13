package steps;

import org.junit.Assert;
import org.openqa.selenium.Alert;

import cucumber.api.java.en.*;
import pages.AutomationPracticeFormPage;
import pages.QAToolsSwitchWindowPage;
import runsupport.DriverFactory;

public class toolsQASwitchSteps extends DriverFactory{

	@Given("^I am on the automation practice switch window$")
	public void i_am_on_the_automation_practice_switch_window() throws Throwable {
	    driver.get("http://toolsqa.com/automation-practice-switch-windows/");
	    driver.manage().window().maximize();
	}

	@When("^I start the timing alert$")
	public void i_start_the_timing_alert() throws Throwable {
	    new QAToolsSwitchWindowPage(driver).press_timing_alert();
	}

	@Then("^I accept the timing alert$")
	public void i_accept_the_timing_alert() throws Throwable {
	    QAToolsSwitchWindowPage qPage = new QAToolsSwitchWindowPage(driver);
	    Alert timingAlert = qPage.wait_for_timing_alert(5);
	    timingAlert.accept();
	}
	
	/*
	 *  Checkboxes and radio buttons
	 */
	
	@Given("^I am on the automation-practice-form$")
	public void i_am_on_the_automation_practice_form() throws Throwable {
	    driver.get("http://toolsqa.com/automation-practice-form/");
	    driver.manage().window().maximize();
	}

	@When("^I select \"(.*)\" for category Sex$")
	public void i_select_for_category_Sex(String gender) throws Throwable {
		AutomationPracticeFormPage fPage = new AutomationPracticeFormPage(driver);
	    if (gender.equalsIgnoreCase("other")) { // Select the unselected radio button
	    	Assert.assertTrue("Select the unselected sex failed", fPage.selectUnselectedGender());
	    } else if (gender.equalsIgnoreCase("male")) {
	    	Assert.assertTrue("Male failed to be selected", fPage.selectGender("male"));
	    } else { // female
	    	Assert.assertTrue("Female failed to be selected", fPage.selectGender("female"));
	    }
	}

	@When("^I select \"(\\d+)\" years of experience$")
	public void i_select_years_of_experience(int years) throws Throwable {
	    Assert.assertTrue(years + " is not between 1 and 7 inclusive", (years > 0 && years <= 7));
	    Assert.assertTrue(new AutomationPracticeFormPage(driver).select_years_experience(years));
	}

	@When("^I select as my profession \"(.*)\"$")
	public void i_select_as_my_profession(String profession) throws Throwable {
	    Assert.assertTrue("Selection for " + profession + " failed", 
	    		new AutomationPracticeFormPage(driver).select_profession(profession));
	}

	@When("^I select automation tool \"(.*)\"$")
	public void i_select_automation_tool(String tool) throws Throwable {
	    Assert.assertTrue("Selection of tool "+ tool + " failed",
	    		new AutomationPracticeFormPage(driver).select_automation_tool(tool));
	}
}

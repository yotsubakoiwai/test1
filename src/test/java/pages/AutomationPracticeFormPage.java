package pages;

import java.util.*;

import org.apache.log4j.Logger;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import runsupport.DriverFactory;

public class AutomationPracticeFormPage {

		protected WebDriver driver;
		protected Logger log;
		
		@FindBy(xpath = "//label[contains(text(),'Sex')]") private WebElement sexLabel;
		@FindBy(css = "#content > form > fieldset > div:nth-child(8) > input[type='radio']") private List<WebElement> experienceRadio;
		@FindBy(xpath = "//label[contains(text(),'Years of Experience')]") private WebElement yearsExperLabel;
		@FindBy(xpath = "//label[contains(text(),'Profession')]") private WebElement professionLabel;
		@FindBy(xpath = "//label[contains(text(),'Automation Tool')]") private WebElement toolLabel;
		
		public AutomationPracticeFormPage(WebDriver driver) {
			this.driver = driver;
			this.log = Logger.getLogger(AutomationPracticeFormPage.class);
					
			Assert.assertTrue("Page title '" + driver.getTitle() + "' doesn't match expected ", 
					driver.getTitle().equals("TOOLSQA | Free QA Test Automation Tools Tutorial | Demo Form for practicing Selenium Automation"));
			
			PageFactory.initElements(driver,  this);
		}
		
		public boolean selectUnselectedGender() {
			List<WebElement> radioButtons = getInputsBasedOnType(sexLabel,"radio");
			log.info("Number of gender radio buttons is " + radioButtons.size());

			if (radioButtons.get(0).isSelected()) { // Male
				log.info("Male was selected, now selecting female");
				radioButtons.get(1).click(); // Select female
				return radioButtons.get(1).isSelected();
			} else { // Female
				log.info("Female was selected, now selecting male");
				radioButtons.get(0).click(); // Select male
				return radioButtons.get(0).isSelected();
			}
		}
		
		public boolean selectGender(String gender){
			List<WebElement> radioButtons = getInputsBasedOnType(sexLabel,"radio");
			log.info("Number of gender radio buttons is " + radioButtons.size());
			if (gender.equals("male")) {
				radioButtons.get(0).click();
				return radioButtons.get(0).isSelected();
			} else if (gender.equalsIgnoreCase("female")){
				radioButtons.get(1).click();
				return radioButtons.get(1).isSelected();
			} else {
				log.error(gender + " is not a valid selection");
				return false;
			}
		}
		
		public boolean select_years_experience(int years) {
			List<WebElement> radioButtons = getInputsBasedOnType(yearsExperLabel,"radio");
			radioButtons.get((years - 1)).click();
			return radioButtons.get((years - 1)).isSelected();
		}
		
		public boolean select_profession(String profession) {
			List<WebElement> checkBoxes = getInputsBasedOnType(professionLabel,"checkbox");
			if (profession.equalsIgnoreCase("manual tester")) {
				checkBoxes.get(0).click();
				return checkBoxes.get(0).isSelected();
			} else if (profession.equalsIgnoreCase("automation tester")) {
				checkBoxes.get(1).click();
				return checkBoxes.get(1).isSelected();
			} else {
				log.error(profession + " is invalid; selection failed");
				return false;
			}
		}
		
		public boolean select_automation_tool(String tool){
			List<WebElement> checkBoxes = getInputsBasedOnType(toolLabel,"checkbox");
			int toolIndex = getAutomationToolIndex(tool);
			if (toolIndex == -1) {
				log.error(tool + " is not a valid selection");
				return false;
			}
			checkBoxes.get(toolIndex).click();
			return checkBoxes.get(toolIndex).isSelected();
		}
		
		private int getAutomationToolIndex(String tool) {
			String[] tools = {"QTP","Selenium IDE","Selenium WebDriver"};
			for(int i=0; i<tools.length; i++){
				if (tools[i].equalsIgnoreCase(tool)){
					return i;
				}
			}
			return -1; // tool is invalid
		}
		
		/*
		 *  The label is under a strong which is a peer of the input elements. Find the label then find its grandparent then find and
		 *  return the input elements as a list.
		 */
		private List<WebElement> getInputsBasedOnType(WebElement labelElement, String inputType) {
			WebElement grandParent = labelElement.findElement(By.xpath("../.."));
			return grandParent.findElements(By.cssSelector("input[type='" + inputType + "']"));
		}
}

package pages;

import java.util.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;

public class RegisteredPage {
	
	protected WebDriver driver;
	protected static Logger log;
	
	public RegisteredPage(WebDriver driver){
		this.driver = driver;
		this.log = Logger.getLogger(RegisteredPage.class);
		Assert.assertEquals("Unexpected page title: " + driver.getTitle(), 
				"Register: Mercury Tours", driver.getTitle());
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > p:nth-child(1) > font:nth-child(1) > b:nth-child(1)")));
		PageFactory.initElements(driver,  this);
	}
	               
	@FindBy(xpath = "//html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[1]/font") private List<WebElement> salutation;
	@FindBy(xpath  = "/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[2]/font") private List<WebElement> msgBody1;
	@FindBy(css = "body > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > p:nth-child(2) > font:nth-child(1) > a:nth-child(1)") private WebElement bodyLink;
	@FindBy(css = "body > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > p:nth-child(2) > font:nth-child(1) > a:nth-child(2)") private WebElement restOfBody;
	@FindBy(css = "body > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > p:nth-child(3) > a:nth-child(1) > font:nth-child(1) > b:nth-child(1)") private WebElement userNameParagraph;
	
	public boolean compareSalutationTextFor(String firstLastName){
		String actualSalutation = salutation.get(0).getText();
		String expectedSalutation = "Dear " + firstLastName + ",";	
		return actualSalutation.equals(expectedSalutation);
	}
	
	public boolean compareMessageBodyStart() {
		String messageBodyStart = msgBody1.get(0).getText();
		return messageBodyStart.equals("Thank you for registering. You may now sign-in using the user name and password you've just entered.");
	}
	
	public boolean compareEmbeddedLink() {
		String actualEmbeddedLinkText = bodyLink.getText();
		return actualEmbeddedLinkText.equals("sign-in");
	}

	public boolean compareUserNameNote(String userName) {
		String ActualNoteLineText = userNameParagraph.getText(); 
		String expectedString = "Note: Your user name is " + userName +".";
		return ActualNoteLineText.equals(expectedString);
	}
}

	
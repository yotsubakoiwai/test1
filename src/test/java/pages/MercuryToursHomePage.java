package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MercuryToursHomePage {
	
	public WebDriver driver;
	protected static Logger log;
	
	public MercuryToursHomePage(WebDriver driver){
		this.driver = driver;
		this.log = Logger.getLogger(RegisterNewUserPage.class);
		
		Assert.assertEquals(driver.getTitle(), "Welcome: Mercury Tours", "Unexpected page title");
		
		WebDriverWait wait = new WebDriverWait(this.driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("REGISTER")));
		PageFactory.initElements(this.driver, this);
	}
	
	@FindBy( linkText = "REGISTER") private WebElement registerLink;
	
	public void register_a_new_account() {
		registerLink.click();
	}
}

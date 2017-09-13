package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Registration.ContactInformation;
import Registration.MailingInformation;
import Registration.UserInformation;

public class RegisterNewUserPage {
	
	public WebDriver driver;
	protected static Logger log;
	
	public RegisterNewUserPage(WebDriver driver){
		this.driver = driver;
		this.log = Logger.getLogger(RegisterNewUserPage.class);
		
		WebDriverWait wait = new WebDriverWait(this.driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[name=country")));
		PageFactory.initElements(this.driver,this);
	}
	
	@FindBy(css = "input[name=firstName]") private WebElement firstName;
	@FindBy(css = "input[name=lastName") private WebElement lastName;
	@FindBy(css = "input[name=phone") private WebElement phone;
	@FindBy(css = "input[name=userName") private WebElement userEmail;
	@FindBy(css = "input[name=address1") private WebElement address1;
	@FindBy(css = "input[name=address2") private WebElement address2;
	@FindBy(css = "input[name=city") private WebElement city;
	@FindBy(css = "input[name=state") private WebElement state;
	@FindBy(css = "input[name=postalCode") private WebElement postalCode;
	@FindBy(css = "select[name=country") private WebElement country;
	@FindBy(css = "input[name=email") private WebElement userName;
	@FindBy(css = "input[name=password") private WebElement password;
	@FindBy(css = "input[name=confirmPassword") private WebElement confirmPassword;
	@FindBy(css = "input[name=register") private WebElement submitButton;
	
	public void enter_Contact_Information(ContactInformation contactInfo){
		firstName.sendKeys(contactInfo.getFirstName());
		lastName.sendKeys(contactInfo.getLastName());
		phone.sendKeys(contactInfo.getPhone());
		userEmail.sendKeys(contactInfo.getEmail());
	}

	public void enter_Mailing_Information(MailingInformation mailingInfo){
		address1.sendKeys(mailingInfo.getAddress1());
		address2.sendKeys(mailingInfo.getAddress2());
		city.sendKeys(mailingInfo.getCity());
		state.sendKeys(mailingInfo.getState());
		postalCode.sendKeys(mailingInfo.getPostalCode());
		Select countryDrop = new Select(country);
		countryDrop.selectByVisibleText(mailingInfo.getCountry().toUpperCase());
	}
	
	public void enter_user_info(UserInformation userInfo){
		userName.sendKeys(userInfo.getUserName());
		password.sendKeys(userInfo.getPassword());
		confirmPassword.sendKeys(userInfo.getConfirmPassword());
	}
	
	public void submit_the_new_registration_request() {
		submitButton.click();
	}
}

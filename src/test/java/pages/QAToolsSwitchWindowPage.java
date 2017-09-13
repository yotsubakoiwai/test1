package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QAToolsSwitchWindowPage {

	protected WebDriver driver;
	protected Logger log;
	
	@FindBy( id = "timingAlert") private WebElement tAlert;
	
	public QAToolsSwitchWindowPage(WebDriver driver){
		this.driver = driver;
		this.log = Logger.getLogger(QAToolsSwitchWindowPage.class);
		
		PageFactory.initElements(driver,  this);
	}
	
	public void press_timing_alert() {
		tAlert.click();
	}
	
	public Alert wait_for_timing_alert(int duration) {
		WebDriverWait wait = new WebDriverWait(driver, duration);
		return wait.until(ExpectedConditions.alertIsPresent());
	}
}

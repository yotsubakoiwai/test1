package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class GoogleSearchPage {

	public WebDriver driver;
	
	@FindBy(id = "lst-ib") private WebElement criteria;
	@FindBy(name = "btnG") private WebElement searchButton;

        public GoogleSearchPage(WebDriver driver){ // Class constructor
		this.driver = driver;
                // The following returns an instance GoogleSearchPage with additional page factory methods.
                // When a step executes new GoogleSearchPage that instance is made available to that step class.
		PageFactory.initElements(driver, this);
	}

	public void enterSearchCriteria(String criteriaString) {
		criteria.sendKeys(criteriaString);
	}
	
	public void pressSearchButton() {
		searchButton.click();
	}
}
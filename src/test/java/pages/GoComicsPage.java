package pages;

import helpers.HelpWithJavascriptLibraries;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import util.ElapsedTime;

public class GoComicsPage {

	public WebDriver driver;
	protected static Logger log;
	
	@FindBy(css = "a[href*='calvinandhobbes']") private WebElement calvinHobbes;
	
	public GoComicsPage(WebDriver driver) { // Class constructor
		this.driver = driver;
		log = Logger.getLogger(GoComicsPage.class);
		
		ElapsedTime etime = new ElapsedTime();
		etime.start();
		new HelpWithJavascriptLibraries().waitForJSandJQueryToLoad(this.driver);
		etime.stop();
		log.info("Elapsed time waiting for 'GoComics' page to load is " 
			+ etime.getElapsedTimeMilliseconds() + " Milliseconds");

		PageFactory.initElements(driver, this);
	}
	
	public void i_select_comic_named(String comicName){
		if (comicName.equalsIgnoreCase("Calvin and Hobbes")) {
			calvinHobbes.click();
			return;
		}
		Assert.assertTrue(false, comicName + " is not recognized");
	}
}

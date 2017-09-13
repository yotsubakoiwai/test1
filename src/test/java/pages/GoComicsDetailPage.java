package pages;

import helpers.HelpWithJavascriptLibraries;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import runsupport.Hooks;
import util.ElapsedTime;

public class GoComicsDetailPage {
	
	public WebDriver driver;
	protected static Logger log;
	
	public GoComicsDetailPage(WebDriver driver) { // Class constructor
		this.driver = driver;
		log = Logger.getLogger(GoComicsDetailPage.class);
		
		ElapsedTime etime = new ElapsedTime();
		etime.start();
		new HelpWithJavascriptLibraries().waitForJSandJQueryToLoad(this.driver, 90);
		etime.stop();
		log.info("Elapsed time waiting for 'GoComicsDetail' page to load is " 
				+ etime.getElapsedTimeSeconds() + " Seconds");
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean PageUrlIsFor(String comicName){
		if (comicName.equalsIgnoreCase("Calvin and Hobbes")) {
			return driver.getCurrentUrl().equals("http://www.gocomics.com/calvinandhobbes");
		}
		return false;
	}

}

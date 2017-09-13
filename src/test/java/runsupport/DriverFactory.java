package runsupport;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import util.PropertyReader;

public class DriverFactory {

	protected static WebDriver driver;
	protected static Logger log;
	protected static long implicitWaitTimeInSeconds;

	public DriverFactory() {
		log = Logger.getLogger(DriverFactory.class);
		initialize();
	}

	public void initialize() {
		if (driver == null)
			createNewDriverInstance();
	}

	private void createNewDriverInstance() {

		PropertyReader propReader = new PropertyReader();
		String browser = propReader.readProperty("browser");
		if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getenv("CHROMEDRIVER"));
			driver = new ChromeDriver();
		} else {
			log.error(propReader.propertyNotValidMsg("browser",
					browser));
		}
		Assert.assertNotNull("Driver failed initialization", driver);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void destroyDriver() {
		driver.quit();
		driver = null;
	}
	
	public void setImplicitWait(long waitTime) {
		DriverFactory.implicitWaitTimeInSeconds = waitTime;
	}
	
	public long getImplicitWait() {
		return implicitWaitTimeInSeconds;
	}
}

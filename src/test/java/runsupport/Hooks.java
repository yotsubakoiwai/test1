package runsupport;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks{
    public WebDriver driver;
    protected static Logger log;
    
    public Hooks() {
        log = Logger.getLogger(Hooks.class);
    }
    
    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests. Maximize the browser window and
     * set the selenium implicit wait.
     */
    public void openBrowser(Scenario scenario) throws MalformedURLException {
        if (isWeb(scenario)) { // Only ramp up a browser if we are testing web
            DriverFactory driveFact = new DriverFactory();
            driver = driveFact.getDriver();
            log.info("@Before hook will run before the actual scenario");
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driveFact.setImplicitWait(60); // Default value for rest of run
            driver.manage().timeouts().implicitlyWait(driveFact.getImplicitWait(), TimeUnit.SECONDS);
        }
    }

     
    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) {
        if (isWeb(scenario)) {
            try {
                if(scenario.isFailed()) {
                    try {
                        scenario.write("Current Page URL is " + driver.getCurrentUrl());
                        byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                        // screenshot = resizeByteArrayImage(screenshot); // Uncomment to shrink size of report
                        scenario.embed(screenshot, "image/png");
                    } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                        log.error(somePlatformsDontSupportScreenshots.getMessage());
            //                    System.err.println(somePlatformsDontSupportScreenshots.getMessage());
                    } catch (ClassCastException cce) {
                        cce.printStackTrace();
                    }
                }
            } finally {
                new DriverFactory().destroyDriver();
                log.info("@After hook will run after the scenario is finished, even if the scenario failed");
            }
        }
    }
    
    private boolean isWeb(Scenario scenario) {
        boolean isWeb = true;
        List<String> scenario_tag = (List<String>) scenario.getSourceTagNames();
        for (String aTag : scenario_tag) {
              System.out.println(aTag);
              if (aTag.equals("@api")) { // API tests by pass the UI
                  isWeb = false;
              }
        }
        return isWeb;
    }
    
    public byte[] resizeByteArrayImage(byte[] imageData) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            BufferedImage resizedImage = Scalr.resize(image, Scalr.Method.AUTOMATIC,
                    Scalr.Mode.AUTOMATIC, 500, Scalr.OP_ANTIALIAS);
            ImageIO.write(resizedImage, "png", bos);
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("** In resizeByteArrayImage, IOException: " + e.getMessage());
            return imageData;
        }

    }
}
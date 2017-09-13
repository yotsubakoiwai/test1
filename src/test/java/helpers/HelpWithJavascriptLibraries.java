package helpers;

import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/*
 * Manually inspect page source on your webpage as soon as it is loaded and search for 
 * "script" tags containing the keywords "jquery","angular" or "prototpye". Then pick 
 * the corresponding method below.  Call that method in that page's PageObject constructor
 * class to wait for that page to finish loading before continuing with the test. 
 */
public class HelpWithJavascriptLibraries {
	/*
	 *  waitForLoadingSpinner assumes that a loading spinner was implemented in a 
	 *  particular way.  This will not, in general be true for your project. You should 
	 *  check with your developers to determine how a loading spinner was implemented. 
	 *  Modify this code as appropriate. This code gracefully handles the case where a  
	 *  loading spinner hasn't been implemented at all. 
	 */
	
	protected static Logger log;
	
	public HelpWithJavascriptLibraries() {
		log = Logger.getLogger(HelpWithJavascriptLibraries.class);
	}
	
	public boolean waitForJSandJQueryToLoad(WebDriver driver) {
		return waitForJSandJQueryToLoad(driver, 30);  // Default to 30 seconds
	}
	
	public boolean waitForJSandJQueryToLoad(WebDriver driver, int waitTimeInSeconds) {
	    
	    HelpWithJavascriptLibraries loadHelp = new HelpWithJavascriptLibraries();
		return loadHelp.waitForJquery(driver, waitTimeInSeconds) && 
		  loadHelp.waitForLoadingSpinner(driver, waitTimeInSeconds) &&
		  loadHelp.waitForPageLoadToComplete(driver, waitTimeInSeconds);
	}
	
	public boolean waitForPrototypeToLoad(WebDriver driver) {
		return waitForPrototypeToLoad(driver, 30);
	}
	
	public boolean waitForPrototypeToLoad(WebDriver driver, int waitTimeInSeconds) {
	
		HelpWithJavascriptLibraries loadHelp = new HelpWithJavascriptLibraries();
		return loadHelp.waitForPrototypeAjax(driver, waitTimeInSeconds) &&
		  loadHelp.waitForPageLoadToComplete(driver, waitTimeInSeconds);
	}
	
	public boolean waitForAngularToLoad(WebDriver driver) {
		return waitForAngularToLoad(driver, 30);
	}
	
	public boolean waitForAngularToLoad(WebDriver driver, int waitTimeInSeconds) {

		HelpWithJavascriptLibraries loadHelp = new HelpWithJavascriptLibraries();
		return loadHelp.waitForAngular(driver, waitTimeInSeconds) &&
		  loadHelp.waitForPageLoadToComplete(driver, waitTimeInSeconds);
		
	}
	
	// DRY
	
	public boolean waitForJquery(WebDriver driver, int timeout) {
		return waitFor(driver, "return jQuery.active;", "0", timeout);
	}

	public boolean waitForPageLoadToComplete(WebDriver driver, int timeout) {
		return waitFor(driver, "var documentState = document.readyState; return documentState;", "complete", timeout);
	}

	public boolean waitForAngular(WebDriver driver, int timeout) {
		return waitFor(driver, "return angular.element(document.body).injector().get(\'$http\').pendingRequests.length;", "0", timeout);
	}

	public boolean waitForPrototypeAjax(WebDriver driver, int timeout) {
		return waitFor(driver, "return Ajax.activeRequestCount;", "0", timeout);
	}

//As of 2014, W3Techs reports that YUI is used on 0.7% of all websites and is out of support. 
//This method has not been tested (can't find a test site).
	public boolean waitForYahooUI(WebDriver driver, int timeout) {
		return waitFor(driver, "var inProgress=0; for(var i=0; i < YAHOO.util.Connect._transaction_id; i++) {if(YAHOO.util.Connect.isCallInProgress(i)) inProgress++;}" + 
	      "return inProgress;", "0", timeout);
	}
	
    public boolean waitFor(WebDriver driver, final String javascriptString, final String targetString, int timeout) {
	  WebDriverWait wait = new WebDriverWait(driver, timeout, 500L);
		
      /*
       * If you are curious about what follows see:
	\  * 	http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/ui/ExpectedCondition.html
	   * 
       * We are creating an anonymous class that inherits from ExpectedCondition and then implements interface
       * method apply(...)
       */
	   ExpectedCondition<Boolean> isLoaded = new ExpectedCondition<Boolean>() {
  	      
	      public Boolean apply(WebDriver driver) {
		    String jsReturnedValue = "";
	
	        try {
	            jsReturnedValue = String.valueOf(((JavascriptExecutor)driver).executeScript(javascriptString));
	            // log.info("** javascriptString=" + javascriptString + ", expected=" + targetString + ", actual=" + jsReturnedValue);
	            return (jsReturnedValue.equals(targetString));
	        } catch (Exception e) {
	          log.info("Looking for: " + javascriptString + ", e.message: " + e.getMessage());
	    	  return true;  // If Javascript not found then don't wait for it
	        }
	      }
	    }; // Terminates statement started by ExpectedCondition<Boolean> isLoaded = ...

  	  return wait.until(isLoaded);
    }
	
    /**
     * Should be customized for each application as loading spinner implementations are diverse.
     * Below has been customized for the demo associated with the @hobbes tag.
     * 
     * @param driver - browser instance
     * @param timeout - max in seconds, should take less (exits as soon as the loading spinner is no longer visible)
     * @return - true for success, false otherwise
     */
	public boolean waitForLoadingSpinner(WebDriver driver, int timeout) {
        try {
		  WebDriverWait wait = new WebDriverWait(driver, (long) timeout);
		  return wait.until(
            // ** You need to modify the next statement for your application
		    ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#progress_throbber > ul > li:nth-child(1) > img[alt='spinner']"))
          );
        } catch (Exception e) {
        	log.info("Loading spinner exception: " + e.getMessage());
        	return true; // If no loading spinner then don't wait for it
        }
	}
}

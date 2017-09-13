package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextNodes {
	
	/*
	 * Extract the text from an element's text nodes.  Beats using getAttribute("innerHTML").
	 * 
	 * Inputs:
	 *   driver - the active Selenium WebDriver driver for your text session
	 *   element - the WebElement instance from whose text nodes you wish to extract text
	 *   
	 * Returns the extracted text String
	 */
	public String getTextFromElementsTextNodes(WebDriver driver,WebElement element) throws IllegalArgumentException {
	    
	    String text = "";
	    if (driver instanceof JavascriptExecutor) {
	        text = (String)((JavascriptExecutor) driver).executeScript(
	                "var nodes = arguments[0].childNodes;" +
	                "var text = '';" +
	                "for (var i = 0; i < nodes.length; i++) {" +
	                "    if (nodes[i].nodeType == Node.TEXT_NODE) {" +
	                "        text += nodes[i].textContent;" +
	                "    }" +
	                "}" +
	                "return text;"
	                , element);
	    } else {
	    	throw new IllegalArgumentException("driver is not an instance of JavascriptExecutor");
	    }
	    return text;
	}

}

package UtilsLayer;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import BaseLayer.BaseClass;

public class ClickUtility extends BaseClass {

	
	public static void safeClick(WebDriver driver, WebElement element) {

	    try {

	        element.click();

	    } catch (ElementClickInterceptedException e) {

	        try {

	            new Actions(driver)
	                    .moveToElement(element)
	                    .click()
	                    .perform();

	        } catch (ElementClickInterceptedException e1) {

	            ((JavascriptExecutor) driver)
	                    .executeScript("arguments[0].click();", element);
	        }

	    } catch (ElementNotInteractableException e) {

	        try {

	            new Actions(driver)
	                    .moveToElement(element)
	                    .click()
	                    .perform();

	        } catch (ElementNotInteractableException e1) {

	            ((JavascriptExecutor) driver)
	                    .executeScript("arguments[0].click();", element);
	        }

	    } catch (StaleElementReferenceException e) {

	        throw new RuntimeException("Element is stale", e);
	    }
	}
	
	public static void safeClick(WebDriver driver, By locator) {

	    try {
	        WebElement element = driver.findElement(locator);
	        element.click();

	    } catch (StaleElementReferenceException e) {

	        WebElement element = driver.findElement(locator);
	        element.click();
	    }
	}
	    
	
}

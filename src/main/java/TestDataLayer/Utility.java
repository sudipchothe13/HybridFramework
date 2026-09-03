package TestDataLayer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import BaseLayer.BaseClass;


public class Utility extends BaseClass {

	public static void highLighterMethod(WebElement element, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//Highlight the element
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		try {
			Thread.sleep(300);
			//highlighter visible for 3 sec
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//remove highlighter
		js.executeScript("arguments[0].style.border=''; arguments[0].style.background='';", element);

	}

}

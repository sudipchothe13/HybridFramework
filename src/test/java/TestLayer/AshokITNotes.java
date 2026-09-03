package TestLayer;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AshokITNotes {
	
	@Test
	public void test() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver .get("https://www.ashokit.in/login");
		driver.manage().window().fullscreen();
		Thread.sleep(5000);
		WebElement username = driver.findElement(By.xpath("//input[@id='email']"));
		WebElement passwordTextBox = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement loginBtn = driver.findElement(By.xpath("//span[text()='Login Now']"));
		
		username.sendKeys("sudipchothe13@gmail.com");
		passwordTextBox.sendKeys("Sudip@123");
		Thread.sleep(3000);
		loginBtn.click();
		Thread.sleep(10000);
		
		WebElement JavaSection = driver.findElement(By.xpath("(//button[text()=' 📒 View Class Info '])[6]"));
		JavaSection.click();
		Thread.sleep(3000);
		
//		List<WebElement> allNotesJava = driver.findElements(By.xpath("//button[@class='download-btn']"));
//
//		for (int i = 0; i < allNotesJava.size(); i++) {
//
//		    WebElement a = driver.findElements(By.xpath("//button[@class='download-btn']")).get(i);
//
//		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", a);
//
//		    Thread.sleep(3000);
//
//		    driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));
//
//		    Thread.sleep(1000);
//		}
		
		
	//	driver.quit();
		
		WebElement backupVideosJava = driver.findElement(By.xpath("//button[@class='action-card action-button video-card']"));
		backupVideosJava.click();
		Thread.sleep(3000);
		driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("sudipchothe13@gmail.com");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@id='otp-login-btn']")).click();
		Thread.sleep(3000);
		
		List<WebElement> lectures = driver.findElements(By.xpath("//li[contains(@class,'section-item')]//a[contains(@id,'sidebar_link_')]"));

		for (WebElement lecture : lectures) {
		    lecture.click();
		    Thread.sleep(3000);
		    // perform permitted testing/validation here
		}
		
	}

}

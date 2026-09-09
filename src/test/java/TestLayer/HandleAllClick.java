package TestLayer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import UtilsLayer.ClickUtility;

public class HandleAllClick {

	public WebDriver driver;
	@Test
	public void test() throws InterruptedException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

		driver.get("https://www.google.com/");

		WebElement about = driver.findElement(By.xpath("(//div[@class='acUsEb Qi40Vc CoM3Df']//a[1])[1]"));
		driver.navigate().refresh();
		Thread.sleep(3000);
		
		ClickUtility.safeClick(driver, driver.findElement(By.xpath("(//div[@class='acUsEb Qi40Vc CoM3Df']//a[1])[1]")));
		
		
		Thread.sleep(3000);
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}

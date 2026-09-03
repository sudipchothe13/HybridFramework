package SeleniumSyntax;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LaunchApp {
	WebDriver driver;
	@Test(priority=1)
	public void setup() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
		driver.get("https://careers.mastercard.com/us/en/pune-india");
		
	}
	
	@Test(priority=2)
	public void handleDropdown() throws InterruptedException {
		
		WebElement alertPopup = driver.findElement(By.xpath("//div[@id='onetrust-group-container']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(alertPopup));
		driver.findElement(By.xpath("//button[@id='onetrust-reject-all-handler']")).click();
		
	}

}

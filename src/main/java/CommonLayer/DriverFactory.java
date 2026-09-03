package CommonLayer;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import BaseLayer.BaseClass;

public class DriverFactory {

    public static WebDriver initDriver(String browser) {

        WebDriver driver;

        boolean headless =
                Boolean.parseBoolean(
                        System.getProperty("headless", "false")
                );

        switch (browser.toLowerCase()) {

            case "chrome":

                if (headless) {

                    ChromeOptions options = new ChromeOptions();

                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");

                    driver = new ChromeDriver(options);

                } else {

                    driver = new ChromeDriver();
                }

                break;


            case "firefox":

                if (headless) {

                    FirefoxOptions options = new FirefoxOptions();

                    options.addArguments("-headless");
                    options.addArguments("--width=1920");
                    options.addArguments("--height=1080");

                    driver = new FirefoxDriver(options);

                } else {

                    driver = new FirefoxDriver();
                }

                break;


            case "edge":

                if (headless) {

                    EdgeOptions options = new EdgeOptions();

                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");

                    driver = new EdgeDriver(options);

                } else {

                    driver = new EdgeDriver();
                }

                break;


            case "safari":

                // Safari does not support the same headless
                // configuration as Chrome/Edge/Firefox.

                driver = new SafariDriver();

                break;


            default:

                throw new RuntimeException(
                        "Unsupported browser: " + browser
                );
        }


        // =====================================================
        // BROWSER WINDOW
        // =====================================================

        if (!headless) {

            driver.manage().window().maximize();
        }


        // =====================================================
        // TIMEOUTS
        // =====================================================

        driver.manage()
              .timeouts()
              .implicitlyWait(Duration.ofSeconds(10));

        driver.manage()
              .timeouts()
              .pageLoadTimeout(Duration.ofSeconds(30));


        // =====================================================
        // BASE CLASS
        // =====================================================

        BaseClass.setDriver(driver);

        BaseClass.setBrowser(browser);


        return driver;
    }
}
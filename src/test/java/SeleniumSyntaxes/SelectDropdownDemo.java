package SeleniumSyntaxes;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SelectDropdownDemo {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.example.com");

        WebElement dropdown = driver.findElement(By.id("country"));

        Select select = new Select(dropdown);

        // Selects option using visible text
        select.selectByVisibleText("India");

        // Selects option using value attribute
        select.selectByValue("IN");

        // Selects option using index position
        select.selectByIndex(0);

        // Returns all options from dropdown
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        // Returns the first selected option
        WebElement selected = select.getFirstSelectedOption();
        System.out.println("Selected: " + selected.getText());

        // Returns all selected options
        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        for (WebElement option : selectedOptions) {
            System.out.println("Selected: " + option.getText());
        }

        // Checks whether dropdown supports multiple selections
        boolean multiple = select.isMultiple();
        System.out.println("Multiple: " + multiple);

        // Deselect methods work only for multi-select dropdowns
        if (multiple) {

            // Deselects option using visible text
            select.deselectByVisibleText("India");

            // Deselects option using value attribute
            select.deselectByValue("IN");

            // Deselects option using index position
            select.deselectByIndex(0);

            // Deselects all selected options
            select.deselectAll();
        }

        driver.quit();
    }
}
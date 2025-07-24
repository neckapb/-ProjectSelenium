import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BingSearchTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() { driver.quit(); }

    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField;
        driver.navigate().refresh();
        searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        By link = By.xpath("//a[contains(@class, 'tilk')][contains(@href, 'selenium.dev')]");
        wait.until(ExpectedConditions.and(
                ExpectedConditions.attributeContains((link), "href", "selenium"),
                ExpectedConditions.elementToBeClickable(link)));

        List<WebElement> results = driver.findElements(By.cssSelector(".b_attribution"));

        clickElement(results, 0);
        assertEquals("https://www.selenium.dev/", driver.getCurrentUrl(), "открылась неверная ссылка");
    }

    @Test
    public void search1() {
        String input = "Selenium";

        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebElement searchPageField = driver.findElement(By.cssSelector("#sb_form_q"));
        assertEquals(input, searchPageField.getAttribute("value"));
    }

    public void clickElement(List<WebElement> results, int num) {
        results.get(num).click();
        ArrayList tabs = new ArrayList<> (driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1).toString());
    }
}
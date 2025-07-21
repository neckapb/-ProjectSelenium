import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://www.bing.com");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        By link = By.xpath("//a[contains(@class, 'tilk')][contains(@href, 'selenium.dev')]");

        wait.until(ExpectedConditions.and(
        ExpectedConditions.attributeContains((link), "href", "selenium"),
        ExpectedConditions.elementToBeClickable(link)));

        clickLink();
    }

    public void clickLink() {
        String a = "https://www.selenium.dev";
        int j = 0;
        List<WebElement> results = driver.findElements(By.cssSelector(".b_attribution"));
        for (WebElement i : results) {
            String b = i.getText();
            if (a.equals(b)) {
                results.get(j).click();
                System.out.println("переход на " + a + " произошел по " + (j +1) + "-й ссылке");
            }
            else j = j + 1;
        }
    }
}

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class SeleniumWebDriverHW1 {
//    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver",
//"src/test/resources/chromedriver");
//        WebDriver driver = new ChromeDriver();
//    }
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String USERNAME = "Student-8";
    private static final String PASSWORD = "2995958987";

    @BeforeAll
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }

    @BeforeEach
    public void setupTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testStandGroup() throws IOException {
        driver.get("https://test-stand.gb.ru/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.name("Username"))).sendKeys(USERNAME);
//                By.xpath("/*[@data-test='Username'] "))).sendKeys(USERNAME);
                By.cssSelector("form#login input[type='text']"))).sendKeys(USERNAME);
        driver.findElement(By.cssSelector("form#login input[type='password']")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("form#login button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(USERNAME)));

        String groupTestName = "New Group " + System.currentTimeMillis();
        driver.findElement(By.id("create-btn")).click();
        By groupNameField = By.xpath("//form//span[contains(text(), 'Group name')]/following-sibling::input");
        wait.until(ExpectedConditions.visibilityOfElementLocated(groupNameField)).sendKeys(groupTestName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form div.submit button")))
                .click();

        String tableTitlesXpath = "//table[@aria-label='Tutors list']//tbody/tr/td[text()='%s']";
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(String.format(tableTitlesXpath, groupTestName))));

        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Files.write(Path.of("src/test/resources/screenshot.png"), screenshotBytes);
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}

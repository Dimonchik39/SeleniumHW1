package org.example.pattern.autoTest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.pattern.LoginPage;
import org.example.pattern.MainPage;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeAll
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }

    @BeforeEach
    public void setupTest() {
        Selenide.open("https://test-stand.gb.ru/login");
        WebDriver driver = WebDriverRunner.getWebDriver();
        loginPage = new LoginPage(driver, new WebDriverWait(driver, Duration.ofSeconds(30)));
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        driver.get("https://test-stand.gb.ru/login");
//        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testLoginEmpty() {
        loginPage.clickLoginButton();
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());
    }

    @Test
    void testBlockingStudentInTableOnMainPage() {
//        String groupTestName = "New Test Group " + System.currentTimeMillis();
//        mainPage.createGroup(groupTestName);
//        mainPage.closeCreateGroupModalWindow();
        loginPage.login(USERNAME, PASSWORD);
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
        String groupTestName = "New Test Group " + System.currentTimeMillis();
        mainPage.createGroup(groupTestName);
        mainPage.closeCreateGroupModalWindow();
        int studentsCount = 3;
        mainPage.clickAddStudentsIconOnGroupWithTitle(groupTestName);
        mainPage.typeAmountOfStudentsInCreateStudentsForm(studentsCount);
        mainPage.clickSaveButtonOnCreateStudentsForm();
        mainPage.closeCreateStudentsModalWindow();
        mainPage.waitStudentsCount(groupTestName, studentsCount);
        mainPage.clickZoomInIconOnGroupWithTitle(groupTestName);
        String firstGeneratedStudentName = mainPage.getStudentNameByIndex(0);
        assertEquals("active", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
        mainPage.clickTrashIconOnStudentWithName(firstGeneratedStudentName);
        assertEquals("block", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
        mainPage.clickRestoreFromTrashIconOnStudentWithName(firstGeneratedStudentName);
        assertEquals("active", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
    }

//    private void checkLogin() {
//        loginPage.login(USERNAME, PASSWORD);
//        mainPage = new MainPage(driver, wait);
//        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
//    }

//    @Test
//    void testArchiveGroupOnMainPage() {
//        checkLogin();
//        String groupTestName = "New Test Group " + System.currentTimeMillis();
//        mainPage.createGroup(groupTestName);
//        // Требуется закрыть модальное окно
//        mainPage.closeCreateGroupModalWindow();
//        // Изменение созданной группы с проверками
//        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
//        mainPage.clickTrashIconOnGroupWithTitle(groupTestName);
//        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(groupTestName));
//        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(groupTestName);
//        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
//    }

//    @Test
//    public void testStandGroup() throws IOException {
//        driver.get("https://test-stand.gb.ru/login");
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.cssSelector("form#login input[type='text']"))).sendKeys(USERNAME);
//        driver.findElement(By.cssSelector("form#login input[type='password']")).sendKeys(PASSWORD);
//        driver.findElement(By.cssSelector("form#login button")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(USERNAME)));
//
//        String groupTestName = "New Group " + System.currentTimeMillis();
//        driver.findElement(By.id("create-btn")).click();
//        By groupNameField = By.xpath("//form//span[contains(text(), 'Group name')]/following-sibling::input");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(groupNameField)).sendKeys(groupTestName);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form div.submit button")))
//                .click();
//
//        String tableTitlesXpath = "//table[@aria-label='Tutors list']//tbody/tr/td[text()='%s']";
//        wait.until(ExpectedConditions
//                .visibilityOfElementLocated(By.xpath(String.format(tableTitlesXpath, groupTestName))));
//
//        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//        Files.write(Path.of("src/test/resources/screenshot.png"), screenshotBytes);
//    }

    @AfterEach
    public void teardown() {
        WebDriverRunner.closeWebDriver();
    }
//    public void teardown() {
//        driver.quit();
//    }
}

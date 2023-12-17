package org.example.pattern.autoTest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.pattern.LoginPage;
import org.example.pattern.MainPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumWebDriverHW1 {
    private static final String USERNAME = "Student-8";
    private static final String PASSWORD = "2995958987";
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeEach
    public void setupTest() {
        Selenide.open("https://test-stand.gb.ru/login");
        loginPage = Selenide.page(LoginPage.class);
    }

    @Test
    public void testLoginEmpty() {
        loginPage.clickLoginButton();
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());
    }

    @Test
    void testBlockingStudentInTableOnMainPage() {
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

    @AfterEach
    public void teardown() {
        WebDriverRunner.closeWebDriver();
    }
}

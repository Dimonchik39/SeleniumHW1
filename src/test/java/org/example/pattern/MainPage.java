package org.example.pattern;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.pattern.elements.GroupTableRow;
import org.example.pattern.elements.StudentTableRow;
import org.openqa.selenium.support.FindBy;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class MainPage {
    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    private SelenideElement usernameLinkInNavBar;
    @FindBy(xpath = "//nav//li[contains(@class,'mdc-menu-surface--anchor')]//span[text()='Profile']")
    private SelenideElement profileLinkInNavBar;
    private final SelenideElement createGroupButton = $("#create-btn");
    private final SelenideElement groupNameField = $x("//form//span[contains(text(), 'Group name')]/following-sibling::input");
    private final SelenideElement submitButtonOnModalWindow = $("form div.submit button");
    private final SelenideElement closeCreateGroupIcon = $x("//span[text()='Creating Study Group']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button");
    private final SelenideElement createStudentsFormInput = $("div#generateStudentsForm-content input");
    private final SelenideElement saveCreateStudentsForm = $("div#generateStudentsForm-content div.submit button");
    private final SelenideElement closeCreateStudentsFormIcon = $x("//h2[@id='generateStudentsForm-title']/../button");
    private final ElementsCollection rowsInGroupTable = $$x("//table[@aria-label='Tutors list']/tbody/tr");
    private final ElementsCollection rowsInStudentTable = $$x("//table[@aria-label='User list']/tbody/tr");

    public SelenideElement waitAndGetGroupTitleByText(String title) {
        return $x(String.format("//table[@aria-label='Tutors list']/tbody//td[text()='%s']", title)).shouldBe(visible);
    }

    public void createGroup(String groupName) {
        createGroupButton.shouldBe(visible).click();
        groupNameField.shouldBe(visible).setValue(groupName);
        submitButtonOnModalWindow.shouldBe(visible).click();
        waitAndGetGroupTitleByText(groupName);
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.shouldBe(visible).click();
    }

    public void typeAmountOfStudentsInCreateStudentsForm(int amount) {
        createStudentsFormInput.shouldBe(visible).setValue(String.valueOf(amount));
    }

    public void clickSaveButtonOnCreateStudentsForm() {
        saveCreateStudentsForm.shouldBe(visible).click();
    }

    public void closeCreateStudentsModalWindow() {
        closeCreateStudentsFormIcon.click();
    }

    public void clickUsernameLabel() {
        usernameLinkInNavBar.shouldBe(visible).click();
    }

    public void clickProfileLink() {
        profileLinkInNavBar.shouldBe(visible).click();
    }

    public String getUsernameLabelText() {
        return usernameLinkInNavBar.shouldBe(visible).getText().replace("\n", " ");
    }

    // Group Table Section
    public void clickTrashIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public void clickAddStudentsIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickAddStudentsIcon();
    }

    public void clickZoomInIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickZoomInIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getGroupRowByTitle(title).getStatus();
    }

    public void waitStudentsCount(String groupTestName, int studentsCount) {
        getGroupRowByTitle(groupTestName).waitStudentsCount(studentsCount);
    }

    private GroupTableRow getGroupRowByTitle(String title) {
        return rowsInGroupTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(GroupTableRow::new)
                .filter(row -> row.getTitle().equals(title))
                .findFirst().orElseThrow();
    }

    // Students Table Section

    public void clickTrashIconOnStudentWithName(String name) {
        getStudentRowByName(name).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentWithName(String name) {
        getStudentRowByName(name).clickRestoreFromTrashIcon();
    }

    public String getStatusOfStudentWithName(String name) {
        return getStudentRowByName(name).getStatus();
    }

    public String getStudentNameByIndex(int index) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(StudentTableRow::new)
                .toList().get(index).getName();
    }

    private StudentTableRow getStudentRowByName(String name) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(StudentTableRow::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }
//    private final WebDriverWait wait;
//    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
//    private WebElement usernameLinkInNavBar;
//    @FindBy(id = "create-btn")
//    private WebElement createGroupButton;
//    @FindBy(xpath = "//form//span[contains(text(), 'Group name')]/following-sibling::input")
//    private WebElement groupNameField;
//    @FindBy(css = "form div.submit button")
//    private WebElement submitButtonOnModalWindow;
//    @FindBy(xpath = "//span[text()='Creating Study Group']" +
//            "//ancestor::div[contains(@class, 'form-modal-header')]//button")
//    private WebElement closeCreateGroupIcon;
//    @FindBy(xpath = "//table[@aria-label='Tutors list']/tbody/tr")
//    private List<WebElement> rowsInGroupTable;
//
//    public MainPage(WebDriver driver, WebDriverWait wait) {
//        this.wait = wait;
//        PageFactory.initElements(driver, this);
//    }
//
//    public void waitGroupTitleByText(String title) {
//        String xpath = String.format("//table[@aria-label='Tutors list']/tbody//td[text()='%s']", title);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//    }
//
//    public void createGroup(String groupName) {
//        wait.until(ExpectedConditions.visibilityOf(createGroupButton)).click();
//        wait.until(ExpectedConditions.visibilityOf(groupNameField)).sendKeys(groupName);
//        wait.until(ExpectedConditions.textToBePresentInElementValue(groupNameField, groupName));
//        submitButtonOnModalWindow.click();
//        waitGroupTitleByText(groupName);
//    }
//
//    public void closeCreateGroupModalWindow() {
//        closeCreateGroupIcon.click();
//        wait.until(ExpectedConditions.invisibilityOf(closeCreateGroupIcon));
//    }
//
//    public String getUsernameLabelText() {
//        return wait.until(ExpectedConditions.visibilityOf(usernameLinkInNavBar))
//                .getText().replace("\n", " ");
//    }
//
//    public void clickTrashIconOnGroupWithTitle(String title) {
//        getRowByTitle(title).clickTrashIcon();
//    }
//
//    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
//        getRowByTitle(title).clickRestoreFromTrashIcon();
//    }
//
//    public String getStatusOfGroupWithTitle(String title) {
//        return getRowByTitle(title).getStatus();
//    }
//
//    private GroupTableRow getRowByTitle(String title) {
//        return rowsInGroupTable.stream()
//                .map(GroupTableRow::new)
//                .filter(row -> row.getTitle().equals(title))
//                .findFirst().orElseThrow();
//    }
}

package org.example.pattern;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    private final SelenideElement fullNameInAdditionalInfo = $x("//h3/following-sibling::div" +
            "//div[contains(text(), 'Full name')]/following-sibling::div");
    private final SelenideElement fullNameInAvatarSection = $("div.mdc-card h2");
    private final SelenideElement editButton = $("button[title='More options']");
    private final SelenideElement  formIsOpen = $("form#update-item");
    private final SelenideElement  birthdateField = formIsOpen.$("input[type='date']");
    private final SelenideElement saveField = $("button[type='submit']");
//    private final SelenideElement closeButton = $("data-mdc-dialog-action");

    public String getFullNameFromAdditionalInfo() {
        return fullNameInAdditionalInfo.shouldBe(visible).text();
    }
    public String getFullNameFromAvatarSection() {
        return fullNameInAvatarSection.shouldBe(visible).text();
    }
    public void editButtonClick() { editButton.shouldBe(visible).click(); }
    public void editBirthdateField(String birthdate) { birthdateField.shouldBe(visible).setValue(birthdate);}
    public void saveButtonClick() {saveField.shouldBe(visible).click();}
//    public void editCloseButton() {closeButton.shouldBe(visible).click();}
}

package org.example.pattern;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameField = $("form#login input[type='text']");
    private final SelenideElement passwordField = $("form#login input[type='password']");
    private final SelenideElement loginButton = $("form#login button");
    private final SelenideElement errorBlock = $("div.error-block");

    public void login(String username, String password) {
        typeUsernameInField(username);
        typePasswordInField(password);
        clickLoginButton();
    }

    public void typeUsernameInField(String username) {
        usernameField.shouldBe(visible).setValue(username);
    }
    public void typePasswordInField(String password) {
        passwordField.shouldBe(visible).setValue(password);
    }
    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
    }
    public String getErrorBlockText() {
        return errorBlock.shouldBe(visible).getText().replace("\n", " ");
    }
}

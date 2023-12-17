package org.example.pattern.elements;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;

public class GroupTableRow {
    private final SelenideElement root;
    public GroupTableRow(SelenideElement root) {
        this.root = root;
    }
    public String getTitle() {
        return root.$("td:nth-child(2)").shouldBe(visible).getText();
    }
    public String getStatus() {
        return root.$("td:nth-child(3)").shouldBe(visible).getText();
    }

    public void clickTrashIcon() {
        root.$x("./td/button[text()='delete']").shouldBe(visible).click();
        root.$x("./td/button[text()='restore_from_trash']").shouldBe(visible, Duration.ofSeconds(30));
    }

    public void clickRestoreFromTrashIcon() {
        root.$x("./td/button[text()='restore_from_trash']").shouldBe(visible).click();
        root.$x("./td/button[text()='delete']").shouldBe(visible, Duration.ofSeconds(30));
    }

    public void waitStudentsCount(int expectedCount) {
        root.$x("./td[4]//span[text()='%s']".formatted(expectedCount)).shouldBe(visible);
    }

    public void clickAddStudentsIcon() {
        root.$("td button i.material-icons").shouldBe(visible).click();
    }
    public void clickZoomInIcon() {
        root.$x(".//td/button[contains(., 'zoom_in')]").shouldBe(visible).click();
    }

}

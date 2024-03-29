package pages.projectPages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProjectPage extends BasePage {

    private static final String PROJECT_NAME_CSS = "h1[class]";

    private static final String ADD_CASE_BUTTON_ID = "create-case-button";

    @Override
    public boolean isPageOpened() {
        try {
            $(By.id(ADD_CASE_BUTTON_ID)).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Step("Open project page")
    public BasePage openPage(String id) {
        open("/projects/" + id);
        return this;
    }

    @Step("Get project name")
    public String getProjectName() {
        return $(PROJECT_NAME_CSS).getText().split("\\s+")[0];
    }
}
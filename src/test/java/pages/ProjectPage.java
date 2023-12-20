package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProjectPage extends BasePage{

    private static final String PROJECT_NAME_CSS = "h1[class]";

    private static final String ADD_CASE_BUTTON_ID = "create-case-button";

    @Override
    public boolean isPageOpened() {
        try {
            $(By.id(ADD_CASE_BUTTON_ID)).shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BasePage openPage(String id) {
        open("/projects/" + id);
        return this;
    }

    public String getProjectName() {
        return $(PROJECT_NAME_CSS).getText().split("\\s+")[0];
    }

    public TestCaseCreationPage clickAddTestCaseButton() {
        $(By.id(ADD_CASE_BUTTON_ID)).click();
        return new TestCaseCreationPage();
    }
}

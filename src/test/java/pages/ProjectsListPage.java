package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import wrappers.Input;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProjectsListPage extends BasePage {

    private static final String CREATE_NEW_PROJECT_BUTTON_ID = "createButton";

    private static final String PROJECT_CODE_ERROR_XPATH = "//*[@id='project-code']/../following-sibling::*";

    private static final String DESCRIPTION_TEXT_AREA_ID = "description-area";

    private static final String CREATE_PROJECT_BUTTON_CSS = "[type=submit]";

    private static final String ALERT_CSS = "[role=alert]";

    public ProjectsListPage openPage() {
        open("/projects");
        return this;
    }

    @Override
    public boolean isPageOpened() {
        try {
            $(By.id(CREATE_NEW_PROJECT_BUTTON_ID)).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Step("Click create new project button")
    public ProjectsListPage clickCreateNewProjectButton() {
        $(By.id(CREATE_NEW_PROJECT_BUTTON_ID)).click();
        return this;
    }

    @Step("Fill in project name {projectName}")
    public ProjectsListPage fillInProjectName(String projectName) {
        input = new Input();
        input.fillInInput("Project name", projectName);
        return this;
    }

    @Step("Fill in project code {projectCode}")
    public ProjectsListPage fillInProjectCode(String projectCode) {
        input = new Input();
        input.fillInInput("Project code", projectCode);
        return this;
    }

    @Step("Fill in description")
    public ProjectsListPage fillInDescription(String description) {
        $(By.id(DESCRIPTION_TEXT_AREA_ID)).setValue(description);
        return this;
    }

    @Step("Click create project button")
    public void clickCreateProjectButton() {
        $(CREATE_PROJECT_BUTTON_CSS).click();
    }

    @Step("Check if alert is displayed")
    public boolean isAlertDisplayed() {
        try {
            $(ALERT_CSS).shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get project code error text")
    public String getProjectCodeErrorText() {
        return $(By.xpath(PROJECT_CODE_ERROR_XPATH)).getText();
    }
}

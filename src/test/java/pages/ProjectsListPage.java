package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProjectsListPage extends BasePage{

    private static final String CREATE_NEW_PROJECT_BUTTON_ID = "createButton";

    private static final String PROJECT_NAME_INPUT_ID = "project-name";

    private static final String PROJECT_CODE_INPUT_XPATH = "//*[@id='project-code']";

    private static final String PROJECT_CODE_ERROR_XPATH = "/../following-sibling::*";

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
        } catch (Exception e) {
            return false;
        }
    }

    public ProjectsListPage clickCreateNewProjectButton() {
        $(By.id(CREATE_NEW_PROJECT_BUTTON_ID)).click();
        return this;
    }

    public ProjectsListPage fillInProjectName(String projectName) {
        $(By.id(PROJECT_NAME_INPUT_ID)).setValue(projectName);
        return this;
    }

    public ProjectsListPage fillInProjectCode(String projectCode) {
        $(By.xpath(PROJECT_CODE_INPUT_XPATH)).setValue(projectCode);
        return this;
    }

    public ProjectsListPage fillInDescription(String description) {
        $(By.id(DESCRIPTION_TEXT_AREA_ID)).setValue(description);
        return this;
    }

    public void clickCreateProjectButton() {
        $(CREATE_PROJECT_BUTTON_CSS).click();
    }

    public boolean isAlertDisplayed() {
        try {
            $(ALERT_CSS).shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getProjectCodeErrorText() {
        return $(By.xpath(PROJECT_CODE_INPUT_XPATH + PROJECT_CODE_ERROR_XPATH)).getText();
    }
}

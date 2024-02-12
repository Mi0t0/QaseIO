package pages.projectPages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideWait;
import helpers.InputHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProjectsListPage extends BasePage {

    private static final String CREATE_NEW_PROJECT_BUTTON_ID = "createButton";

    private static final String PROJECT_CODE_ERROR_XPATH = "//*[@id='project-code']/../following-sibling::*";

    private static final String DESCRIPTION_TEXT_AREA_ID = "description-area";

    private static final String CREATE_PROJECT_BUTTON_CSS = "[type=submit]";

    private static final String ALERT_CSS = "[role=alert]";

    private static final String DIALOG_CSS = "[role=dialog]";

    private static final String PROJECTS_NAMES_XPATH = "//*[contains(@href, '/project/') and @class]";

    private static final String ROWS_PER_PAGE_SELECT_XPATH = "//*[contains(text(),'Rows per page')]/../*[@class]";

    private static final String ROWS_PER_PAGE_OPTION_XPATH = "//*[contains(text(),'Rows per page')]/..//*[@aria-selected and text()='%s']";

    private static final String PREVIOUS_PAGE_BUTTON_XPATH = "//nav//button";

    private static final String NEXT_PAGE_BUTTON_XPATH = "(//nav//button)[last()]";

    private static final String PAGE_NUMBER_XPATH = "//nav//button//*[contains(text(),'%s')]";

    private static final String PROJECT_ACTION_BUTTON_XPATH = "//a[text()='%s']/ancestor::tr//*[@data-icon='ellipsis']";

    private static final String REMOVE_PROJECT_BUTTON_XPATH = "//button[text()='Remove']";

    private static final String CONFIRM_PROJECT_DELETION_BUTTON_XPATH = "//button//*[text()='Delete project']";

    private static final String CANCEL_PROJECT_DELETION_BUTTON_XPATH = "//button//*[text()='Cancel']";

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

    public ProjectsListPage clickCreateNewProjectButton() {
        $(By.id(CREATE_NEW_PROJECT_BUTTON_ID)).click();
        return this;
    }

    public ProjectsListPage fillInProjectName(String projectName) {
        input = new InputHelper();
        input.fillInInput("Project name", projectName);
        return this;
    }

    public ProjectsListPage fillInProjectCode(String projectCode) {
        input = new InputHelper();
        input.fillInInput("Project code", projectCode);
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
        return $x(PROJECT_CODE_ERROR_XPATH).getText();
    }

    public ArrayList<String> getProjectsNamesOnPage() {
        ArrayList<String> projectNames = new ArrayList<>();
        for (WebElement element : $$x(PROJECTS_NAMES_XPATH)) {
            projectNames.add(element.getText());
        }
        return projectNames;
    }

    public ProjectsListPage waitForProjectsNamesLoadUp() {
        try {
            SelenideWait wait = new SelenideWait(getWebDriver(), 10000L, 100L);
            wait.until((WebDriver driver) -> !$$x(PROJECTS_NAMES_XPATH).isEmpty());
        } catch (Exception ignored) {
        }
        return this;
    }

    public ProjectsListPage defineRowsPerPage(String rowsPerPage) {
        $x(ROWS_PER_PAGE_SELECT_XPATH).click();
        $x(String.format(ROWS_PER_PAGE_OPTION_XPATH, rowsPerPage)).click();
        return this;
    }

    public ProjectsListPage clickPreviousPageButton() {
        $x(PREVIOUS_PAGE_BUTTON_XPATH).click();
        return this;
    }

    public ProjectsListPage clickNextPageButton() {
        $x(NEXT_PAGE_BUTTON_XPATH).click();
        return this;
    }

    public ProjectsListPage clickPageNumber(int pageNumber) {
        $x(String.format(PAGE_NUMBER_XPATH, pageNumber)).click();
        return this;
    }

    public ProjectsListPage clickProjectActionButton(String projectName) {
        $x(String.format(PROJECT_ACTION_BUTTON_XPATH, projectName)).click();
        return this;
    }

    public ProjectsListPage clickRemoveProjectButton() {
        $x(REMOVE_PROJECT_BUTTON_XPATH).click();
        return this;
    }

    public ProjectsListPage clickConfirmProjectDeletionButton() {
        $x(CONFIRM_PROJECT_DELETION_BUTTON_XPATH).click();
        return this;
    }

    public ProjectsListPage clickCancelProjectDeletionButton() {
        $x(CANCEL_PROJECT_DELETION_BUTTON_XPATH).click();
        return this;
    }

    public ProjectsListPage waitUntilModalIsDisappeared() {
        try {
            $(DIALOG_CSS).shouldBe(Condition.disappear);
        } catch (Exception ignored) {
            throw new RuntimeException("Modal window is not disappeared");
        }
        return this;
    }
}
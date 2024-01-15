package steps;

import adapters.ApiRequests;
import dtos.Project;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.ProjectPage;
import pages.ProjectsListPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Log4j2
public class ProjectSteps {

    private static final String INCORRECT_PROJECT_CODE_ERROR_TEXT = "Project Code error text is not correct or not displayed";

    ProjectsListPage projectsListPage = new ProjectsListPage();

    ProjectPage projectPage = new ProjectPage();

    ApiRequests apiRequests = new ApiRequests();

    @Step("Open projects page")
    public ProjectSteps openPage() {
        log.info("Opening projects page");
        projectsListPage.
                openPage().
                isPageOpened();
        return this;
    }

    @Step("Create new project ")
    public ProjectSteps createProject(Project project) {
        log.info("Creating new project '{}'" , project.getProjectName());
        projectsListPage.
                clickCreateNewProjectButton().
                fillInProjectName(project.getProjectName()).
                fillInProjectCode(project.getProjectCode()).
                fillInDescription(project.getDescription()).
                clickCreateProjectButton();
        return this;
    }

    @Step("Check if project with correct code is created")
    public ProjectSteps checkThatProjectIsCreated(Project project) {
        log.info("Checking that project is created");
        assertTrue(projectPage.isPageOpened(), "Project is not created");
        assertTrue(apiRequests.doesProjectExist(project.getProjectCode().toUpperCase()));
        return this;
    }

    @Step("Check that alert is displayed")
    public ProjectSteps checkThatAlertIsDisplayed() {
        log.info("Checking that alert is displayed");
        assertTrue(projectsListPage.isAlertDisplayed(), "Alert is not displayed");
        return this;
    }

    @Step("Check that project code error text is correct")
    public ProjectSteps checkThatProjectCodeErrorTextIsCorrect(Project project) {
        log.info("Checking that project code error text is correct");
        switch (project.getProjectCode()) {
            case "12345678901":
                assertEquals(projectsListPage.getProjectCodeErrorText(), "The code may not be greater than 10 characters.", INCORRECT_PROJECT_CODE_ERROR_TEXT);
                break;
            case "1":
                assertEquals(projectsListPage.getProjectCodeErrorText(), "The code must be at least 2 characters.", INCORRECT_PROJECT_CODE_ERROR_TEXT);
                break;
            case "абвгд":
                assertEquals(projectsListPage.getProjectCodeErrorText(), "The code format is invalid.", INCORRECT_PROJECT_CODE_ERROR_TEXT);
                break;
            case "sameCode":
                assertEquals(projectsListPage.getProjectCodeErrorText(), "The selected project code is already in use.", INCORRECT_PROJECT_CODE_ERROR_TEXT);
                break;
            default:
                log.warn("Unhandled project code");
        }
        return this;
    }
}
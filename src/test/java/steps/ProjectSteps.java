package steps;

import adapters.ApiRequests;
import dtos.Project;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.ProjectPage;
import pages.ProjectsListPage;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

@Log4j2
public class ProjectSteps extends BaseStep {

    private static final String INCORRECT_PROJECT_CODE_ERROR_TEXT = "Project Code error text is not correct or not displayed";

    private final ProjectsListPage projectsListPage = new ProjectsListPage();

    private final ProjectPage projectPage = new ProjectPage();

    private final ApiRequests apiRequests = new ApiRequests();

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
        log.info("Creating new project '{}' with code '{}'" , project.getProjectName(), project.getProjectCode());
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

    @Step("Check that project name is correct")
    public ProjectSteps checkThatProjectNameIsCorrect(Project project) {
        log.info("Checking that project name is correct");
        assertEquals(projectPage.getProjectName(), project.getProjectName(), "Project name is not correct");
        return this;
    }

    @Step("Check that alert is displayed")
    public ProjectSteps checkThatAlertIsDisplayed() {
        log.info("Checking that alert is displayed");
        assertTrue(projectsListPage.isAlertDisplayed(), "Alert is not displayed");
        return this;
    }

    @Step("Check that error message is correct")
    public ProjectSteps errorMessageShouldBe(String errorMessage) {
        log.info("Checking that error message is correct");
        assertEquals(projectsListPage.getProjectCodeErrorText(), errorMessage, INCORRECT_PROJECT_CODE_ERROR_TEXT);
        return this;
    }

    @Step("Check if project with code '{project.projectCode}' should be displayed in projects list")
    public ProjectSteps checkIfProjectShouldBeDisplayedInProjectsList(Project project, boolean shouldExist) {
        log.info("Checking if project with code '{}' should be displayed in projects list", project.getProjectCode());
        projectsListPage.waitForProjectsNamesLoadUp();
        if (shouldExist)
            assertTrue(projectsListPage.getProjectsNamesOnPage().contains(project.getProjectName()), "Project is not displayed in projects list");
        else
            assertFalse(projectsListPage.getProjectsNamesOnPage().contains(project.getProjectName()), "Project is displayed in projects list");
        return this;
    }

    @Step("Define rows of projects to display per page")
    public ProjectSteps defineRowsOfProjectsToDisplayPerPage(int rows) {
        log.info("Defining rows of projects to display per page");
        projectsListPage.
                defineRowsPerPage(String.valueOf(rows));
        return this;
    }

    @Step("Check quantity of projects displayed per page")
    public ProjectSteps checkQuantityOfProjectsDisplayedPerPage(int expectedQuantity) {
        log.info("Checking quantity of projects displayed per page");
        projectsListPage.waitForProjectsNamesLoadUp();
        assertEquals(projectsListPage.getProjectsNamesOnPage().size(), expectedQuantity, "Quantity of projects displayed per page is not correct");
        return this;
    }

    @Step("Get projects names on page")
    public ArrayList<String> getProjectsNamesOnPage() {
        log.info("Getting projects names on page");
        projectsListPage.waitForProjectsNamesLoadUp();
        return projectsListPage.getProjectsNamesOnPage();
    }

    @Step("Check that new projects page contains different projects")
    public ProjectSteps checkIfListOfProjectsIsDifferent(ArrayList<String> projectsNamesOnPage, boolean shouldNamesBeDifferent) {
        log.info("Checking that new projects page contains different projects");
        projectsListPage.waitForProjectsNamesLoadUp();
        if (shouldNamesBeDifferent)
            assertNotEquals(projectsListPage.getProjectsNamesOnPage(), projectsNamesOnPage, "Projects on page are the same");
        else
            assertEquals(projectsListPage.getProjectsNamesOnPage(), projectsNamesOnPage, "Projects on page are different");
        return this;
    }

    @Step("Click next page button")
    public ProjectSteps clickNextPageButton() {
        log.info("Clicking next page button");
        projectsListPage.
                clickNextPageButton();
        return this;
    }

    @Step("Click previous page button")
    public ProjectSteps clickPreviousPageButton() {
        log.info("Clicking previous page button");
        projectsListPage.
                clickPreviousPageButton();
        return this;
    }

    @Step("Click page number")
    public ProjectSteps clickPageNumber(int pageNumber) {
        log.info("Clicking page number");
        projectsListPage.
                clickPageNumber(pageNumber);
        return this;
    }

    @Step("Delete project")
    public ProjectSteps deleteProject(Project project) {
        log.info("Deleting project '{}' with code '{}'", project.getProjectName(), project.getProjectCode());
        projectsListPage.
                clickProjectActionButton(project.getProjectName()).
                clickRemoveProjectButton();
        return this;
    }

    @Step("Confirm project deletion")
    public ProjectSteps confirmProjectDeletion(boolean confirmDeletion) {
        log.info("Confirming project deletion");
        if (confirmDeletion) {
            projectsListPage.clickConfirmProjectDeletionButton();
        } else {
            projectsListPage.clickCancelProjectDeletionButton();
        }
        return this;
    }
}
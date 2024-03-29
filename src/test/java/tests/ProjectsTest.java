package tests;

import dtos.Project;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.common.BaseTest;
import tests.common.Retry;

import java.util.ArrayList;

@Log4j2
public class ProjectsTest extends BaseTest {

    @BeforeMethod(description = "Login to Qase")
    public void positiveLogin() {
        apiSteps.
                login(USERNAME, PASSWORD);
        projectSteps.
                openPage();
    }

    @Test(description = "Create new project")
    public void projectShouldBeCreated() {
        String projectId = getRandomProjectId();
        Project project = Project.builder().
                projectName(projectId).
                projectCode(projectId).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatProjectIsCreated(project).
                checkThatProjectNameIsCorrect(project);
        apiSteps.
                deleteProjectByCodeIfExists(projectId);
    }

    @Test(description = "Created project displayed in projects list")
    public void createdProjectShouldBeDisplayedInProjectsList() {
        String projectId = getRandomProjectId();
        Project project = Project.builder().
                projectName(projectId).
                projectCode(projectId).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                openPage().
                checkIfProjectShouldBeDisplayedInProjectsList(project, true);
        apiSteps.
                deleteProjectByCodeIfExists(projectId);
    }

    @Test(retryAnalyzer = Retry.class, description = "Try to create new project with too long project code")
    public void projectCodeShouldBeLessThen11Characters() {
        String projectCode = "1234567890123";
        Project project = Project.builder().
                projectName(projectCode).
                projectCode(projectCode).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatAlertIsDisplayed().
                errorMessageShouldBe("The code may not be greater than 10 characters.");
    }

    @Test(retryAnalyzer = Retry.class, description = "Try to create new project with too short project code")
    public void projectCodeShouldBeMoreThen1Character() {
        String projectCode = "1";
        Project project = Project.builder().
                projectName(projectCode).
                projectCode(projectCode).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatAlertIsDisplayed().
                errorMessageShouldBe("The code must be at least 2 characters.");
    }

    @Test(retryAnalyzer = Retry.class, description = "Try to create new project with too long project code")
    public void incorrectProjectCodeFormat() {
        String projectCode = "абвгд123";
        Project project = Project.builder().
                projectName(projectCode).
                projectCode(projectCode).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatAlertIsDisplayed().
                errorMessageShouldBe("The code format is invalid.");
    }

    @Test(retryAnalyzer = Retry.class, description = "Try to create new project with existing project code in use")
    public void sameProjectCodeExists() {
        String projectCode = "sameCode";
        Project project = Project.builder().
                projectName(projectCode).
                projectCode(projectCode).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                openPage().
                createProject(project).
                checkThatAlertIsDisplayed().
                errorMessageShouldBe("The selected project code is already in use.");
        apiSteps.
                deleteProjectByCodeIfExists(projectCode.toUpperCase());
    }

    @Test(retryAnalyzer = Retry.class, description = "Rows per page defines number of projects displayed on page")
    public void rowsPerPageDefinesNumberOfProjectsDisplayedOnPage() {
        apiSteps.
                createSeveralRandomProjects(15);
        projectSteps.
                openPage();
        projectSteps.
                defineRowsOfProjectsToDisplayPerPage(5).
                checkQuantityOfProjectsDisplayedPerPage(5).
                defineRowsOfProjectsToDisplayPerPage(10).
                checkQuantityOfProjectsDisplayedPerPage(10).
                defineRowsOfProjectsToDisplayPerPage(15).
                checkQuantityOfProjectsDisplayedPerPage(15);
    }

    @Test(retryAnalyzer = Retry.class, description = "Page navigation should work correctly")
    public void pageNavigationButtonsShouldChangeListOfProjects() {
        apiSteps.
                createSeveralRandomProjects(6);
        projectSteps.
                openPage().
                defineRowsOfProjectsToDisplayPerPage(5);

        ArrayList<String> projectsNames = new ArrayList<>(projectSteps.getProjectsNamesOnPage());
        projectSteps.
                clickNextPageButton().
                checkIfListOfProjectsIsDifferent(projectsNames, true).
                clickPreviousPageButton().
                checkIfListOfProjectsIsDifferent(projectsNames, false).
                clickPageNumber(2).
                checkIfListOfProjectsIsDifferent(projectsNames, true);
    }

    @Test(description = "Project should be removed if it was deleted")
    public void projectShouldBeRemovedIfItWasDeleted() {
        String projectId = getRandomProjectId();
        Project project = Project.builder().
                projectName(projectId).
                projectCode(projectId).
                build();

        projectSteps.
                createProject(project).
                openPageWithSearch(projectId).
                deleteProject(project).
                confirmProjectDeletion(true).
                waitUntilModalIsDisappeared();
        apiSteps.
                checkIfProjectExists(project, false);
        projectSteps.
                checkIfProjectShouldBeDisplayedInProjectsList(project, false);
    }

    @Test(description = "Project should not be deleted if deletion confirmation was canceled")
    public void projectShouldNotBeRemovedIfDeletionConfirmationWasCanceled() {
        String projectId = getRandomProjectId();
        Project project = Project.builder().
                projectName(projectId).
                projectCode(projectId).
                build();

        projectSteps.
                createProject(project).
                openPageWithSearch(projectId).
                deleteProject(project).
                confirmProjectDeletion(false).
                waitUntilModalIsDisappeared();
        apiSteps.
                checkIfProjectExists(project, true);
        projectSteps.
                checkIfProjectShouldBeDisplayedInProjectsList(project, true);
    }
}
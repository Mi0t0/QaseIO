package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProjectsTest extends BaseTest {

    @BeforeMethod(description = "Login to Qase")
    public void positiveLogin() {
        loginPage.
                openPage().
                fillInUsername(USERNAME).
                fillInPassword(PASSWORD).
                clickLoginButton();
        assertTrue(projectsListPage.isPageOpened(), "Login page is not opened");
    }

    @Test(description = "Create new project")
    public void projectShouldBeCreated() {
        String projectId = String.valueOf(new Random().nextInt(1000000000));
        projectsListPage.
                clickCreateNewProjectButton().
                fillInProjectName(projectId).
                fillInProjectCode(projectId).
                fillInDescription("Description").
                clickCreateProjectButton();
        assertTrue(projectPage.isPageOpened(), "Project is not created");
        assertEquals(projectPage.getProjectName(), projectId.toUpperCase(), "Project name is not correct");
    }

    @Test(description = "Try to create new project with too long project code")
    public void projectCodeShouldBeLessThen10Characters() {
        String projectCode = "12345678901";
        projectsListPage.
                clickCreateNewProjectButton().
                fillInProjectName(faker.cat().name()).
                fillInProjectCode(projectCode).
                clickCreateProjectButton();
        assertTrue(projectsListPage.isAlertDisplayed(), "Alert is not displayed");
        assertEquals(projectsListPage.getProjectCodeErrorText(), "The code may not be greater than 10 characters.", "Project Code error text is not correct or not displayed");
    }

    @Test(description = "Try to create new project with too short project code")
    public void projectCodeShouldBeMoreThen1Character() {
        String projectCode = "1";
        projectsListPage.
                clickCreateNewProjectButton().
                fillInProjectName(faker.cat().name()).
                fillInProjectCode(projectCode).
                clickCreateProjectButton();
        assertTrue(projectsListPage.isAlertDisplayed(), "Alert is not displayed");
        assertEquals(projectsListPage.getProjectCodeErrorText(), "The code must be at least 2 characters.", "Project Code error text is not correct or not displayed");
    }

    @Test(description = "Try to create new project with too long project code")
    public void incorrectProjectCodeFormat() {
        String projectCode = "абвгд";
        projectsListPage.
                clickCreateNewProjectButton().
                fillInProjectName(faker.cat().name()).
                fillInProjectCode(projectCode).
                clickCreateProjectButton();
        assertTrue(projectsListPage.isAlertDisplayed(), "Alert is not displayed");
        assertEquals(projectsListPage.getProjectCodeErrorText(), "The code format is invalid.", "Project Code error text is not correct or not displayed");
    }

    @Test(description = "Try to create new project with existing project code in use")
    public void sameProjectCodeExists() {
        String projectCode = String.valueOf(new Random().nextInt(1000000000));
        projectsListPage.
                clickCreateNewProjectButton().
                fillInProjectName(faker.cat().name()).
                fillInProjectCode(projectCode).
                clickCreateProjectButton();
        projectsListPage.
                openPage().
                clickCreateNewProjectButton().
                fillInProjectName(faker.cat().name()).
                fillInProjectCode(projectCode).
                clickCreateProjectButton();
        assertTrue(projectsListPage.isAlertDisplayed(), "Alert is not displayed");
        assertEquals(projectsListPage.getProjectCodeErrorText(), "The selected project code is already in use.", "Project Code error text is not correct or not displayed");
    }
}
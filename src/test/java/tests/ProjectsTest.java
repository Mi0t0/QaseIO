package tests;

import dtos.Project;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import java.util.Random;

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
        String projectId = String.valueOf(new Random().nextInt(1000000000));
        Project project = Project.builder().
                projectName(projectId).
                projectCode(projectId).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatProjectIsCreated(project);
        apiSteps.
                deleteProjectByCodeIfExists(projectId);
    }

    @Test(description = "Try to create new project with too long project code")
    public void projectCodeShouldBeLessThen10Characters() {
        String projectCode = "12345678901";
        Project project = Project.builder().
                projectName(projectCode).
                projectCode(projectCode).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatAlertIsDisplayed().
                checkThatProjectCodeErrorTextIsCorrect(project);
    }

    @Test(description = "Try to create new project with too short project code")
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
                checkThatProjectCodeErrorTextIsCorrect(project);
    }

    @Test(description = "Try to create new project with too long project code")
    public void incorrectProjectCodeFormat() {
        String projectCode = "абвгд";
        Project project = Project.builder().
                projectName(projectCode).
                projectCode(projectCode).
                description("Description").
                build();

        projectSteps.
                createProject(project).
                checkThatAlertIsDisplayed().
                checkThatProjectCodeErrorTextIsCorrect(project);
    }

    @Test(description = "Try to create new project with existing project code in use")
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
                checkThatProjectCodeErrorTextIsCorrect(project);
        apiSteps.
                deleteProjectByCodeIfExists(projectCode);
    }
}
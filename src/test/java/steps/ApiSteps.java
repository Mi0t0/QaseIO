package steps;

import adapters.ApiRequests;
import com.github.javafaker.Faker;
import dtos.Project;
import dtos.TestSuite;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Cookie;

import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Log4j2
public class ApiSteps extends BaseStep {

    private final ApiRequests apiRequests = new ApiRequests();

    @Step("Login through API with username '{username}' and password '{password}'")
    public ApiSteps login(String username, String password) {
        log.info("Logging in through API with username '{}' and password '{}'", username, password);
        Map<String, String> cookies = apiRequests.getLoginCookies(username, password);
        getWebDriver().manage().addCookie(new Cookie("XSRF-TOKEN", cookies.get("XSRF-TOKEN")));
        getWebDriver().manage().addCookie(new Cookie("__Host-session", cookies.get("__Host-session")));
        return this;
    }

    @Step("Clean projects list")
    public ApiSteps cleanProjectsList() {
        log.info("Cleaning projects list");
        Set<Project> projectsSet = apiRequests.getProjectsSet();
        for (Project project : projectsSet) {
            apiRequests.deleteProjectByCode(project.getProjectCode());
        }
        return this;
    }

    @Step("Create project with code '{projectCode}'")
    public ApiSteps createProjectWithCode(String projectCode) {
        log.info("Creating new project with code '{}'", projectCode);
        String projectId = getRandonProjectId();
        Faker faker = new Faker();

        apiRequests.createProject(Project.builder().
                projectName(projectId).
                projectCode(projectCode).
                description(faker.weather().description()).
                projectAccessType("Private").
                memberAccess("group").
                group("Owner group").
                build());
        return this;
    }

    @Step("Delete project with code '{projectCode}'")
    public void deleteProjectByCode(String projectCode) {
        apiRequests.deleteProjectByCode(projectCode);
    }

    @Step("Delete project with code '{projectCode}' if it exists")
    public ApiSteps deleteProjectByCodeIfExists(String projectCode) {
        if (apiRequests.doesProjectExist(projectCode))
            deleteProjectByCode(projectCode);
        return this;
    }

    @Step("Create test suite '{suiteTitle}' in project with code '{projectCode}'")
    public ApiSteps createTestSuite(String projectCode, TestSuite suiteTitle) {
        apiRequests.createTestSuite(projectCode, suiteTitle);
        return this;
    }

    @Step("Create several random projects")
    public ApiSteps createSeveralRandomProjects(int quantityOfProjects) {
        Faker faker = new Faker();
        for (int i = 0; i < quantityOfProjects; i++) {
            String projectCode = getRandonProjectId();
            apiRequests.createProject(Project.builder().
                    projectName(faker.name().firstName()).
                    projectCode(projectCode).
                    description(faker.weather().description()).
                    build());
        }
        return this;
    }

    @Step("Check if project with code '{project.projectCode}' should exist")
    public ApiSteps checkIfProjectExists(Project project, boolean shouldExist) {
        log.info("Checking if project with code '{}' should exist", project.getProjectCode());
        if (shouldExist)
            assertTrue(apiRequests.doesProjectExist(project.getProjectCode()), "Project does not exist");
        else
            assertFalse(apiRequests.doesProjectExist(project.getProjectCode()), "Project exists");
        return this;
    }
}
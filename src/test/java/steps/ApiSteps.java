package steps;

import adapters.ApiRequests;
import com.github.javafaker.Faker;
import dtos.Project;
import dtos.TestSuite;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Cookie;

import java.util.Map;
import java.util.Random;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Log4j2
public class ApiSteps {

    private ApiRequests apiRequests = new ApiRequests();

    public ApiSteps login(String username, String password) {
        log.info("Logging in through API with username '{}' and password '{}'", username, password);
        Map<String, String> cookies = apiRequests.getLoginCookies(username, password);
        getWebDriver().manage().addCookie(new Cookie("XSRF-TOKEN", cookies.get("XSRF-TOKEN")));
        getWebDriver().manage().addCookie(new Cookie("__Host-session", cookies.get("__Host-session")));
        return this;
    }

    public ApiSteps createProjectWithCode(String projectCode) {
        log.info("Creating new project with code '{}'", projectCode);
        String projectId = String.valueOf(new Random().nextInt(1000000000));
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

    public void deleteProjectByCode(String projectCode) {
        apiRequests.deleteProjectByCode(projectCode);
    }

    public ApiSteps deleteProjectByCodeIfExists(String projectCode) {
        if (apiRequests.doesProjectExist(projectCode))
            deleteProjectByCode(projectCode);
        return this;
    }

    public ApiSteps createTestSuite(String projectCode, TestSuite suiteTitle) {
        apiRequests.createTestSuite(projectCode, suiteTitle);
        return this;
    }
}
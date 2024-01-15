package steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.LoginPage;
import pages.ProjectsListPage;

import static org.testng.Assert.assertTrue;

@Log4j2
public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    ProjectsListPage projectsListPage = new ProjectsListPage();

    @Step("Login to Qase with credentials: {username} // {password}")
    public LoginSteps login(String username, String password) {
        log.info("Logging in to Qase with credentials: {} // {}", username, password);

        loginPage.openPage().
                fillInUsername(username).
                fillInPassword(password).
                clickLoginButton();
        return this;
    }

    @Step("Check if user is logged in")
    public LoginSteps checkThatUserIsLoggedIn() {
        log.info("Checking that user is logged in");
        assertTrue(projectsListPage.isPageOpened(), "Login page is not opened");
        return this;
    }
}
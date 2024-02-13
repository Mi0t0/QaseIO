package steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.loginPages.LoginPage;
import pages.loginPages.PasswordResetPage;
import pages.projectPages.ProjectsListPage;
import utils.SensibleText;

import static org.testng.Assert.*;

@Log4j2
public class LoginSteps extends BaseStep {

    private final LoginPage loginPage = new LoginPage();

    private final PasswordResetPage resetPasswordPage = new PasswordResetPage();

    private final ProjectsListPage projectsListPage = new ProjectsListPage();

    @Step("Login to Qase with credentials: {username} // {password}")
    public LoginSteps login(SensibleText username, SensibleText password) {
        log.info("Logging in to Qase with credentials: {} // {}", username, password);

        loginPage.openPage().
                fillInUsername(username.getText()).
                fillInPassword(password.getText()).
                clickLoginButton();
        return this;
    }

    @Step("Check if user is logged in")
    public LoginSteps checkThatUserIsLoggedIn() {
        log.info("Checking that user is logged in");
        assertTrue(projectsListPage.isPageOpened(), "Login page is not opened");
        return this;
    }

    @Step("Check empty field validation")
    public LoginSteps checkEmptyFieldValidation() {
        log.info("Checking empty field validation");
        assertTrue(loginPage.isEmptyFieldValidationDisplayed(), "Empty field validation is not displayed");
        assertFalse(projectsListPage.isPageOpened(), "Login page is opened");
        return this;
    }

    @Step("Check if wrong credentials alert is displayed")
    public LoginSteps checkThatWrongCredentialsAlertIsDisplayed() {
        log.info("Checking if wrong credentials alert is displayed");
        assertEquals(loginPage.getAlertText(),
                "These credentials do not match our records.",
                "Wrong credentials alert is not displayed or the text is incorrect");
        return this;
    }

    @Step("Check that password was leaked alert is displayed")
    public LoginSteps checkThatPasswordWasLeakedAlertIsDisplayed() {
        log.info("Checking that password was leaked alert is displayed");
        assertEquals(loginPage.getAlertText(),
                "Security notice: The password entered has been found in a public data leak. Please reset your password to ensure the safety of your account",
                "The alert is not displayed or the text is incorrect");
        return this;
    }

    @Step("Check that the user was redirected to reser password page")
    public LoginSteps checkThatUserWasRedirectedToResetPasswordPage() {
        log.info("Checking that the user was redirected to reset password page");
        assertTrue(resetPasswordPage.isPageOpened(), "Reset Page is not opened");
        return this;
    }

    @Step("Check that email doesn't match email format alert is displayed")
    public LoginSteps checkThatEmailDoesntMatchEmailFormatAlertIsDisplayed(SensibleText username) {
        log.info("Checking that email doesn't match email format alert is displayed");
        assertEquals(loginPage.getAlertText(),
                "Value '" + username.getText() + "' does not match format email of type string",
                "The alert is not displayed or the text is incorrect");
        return this;
    }
}
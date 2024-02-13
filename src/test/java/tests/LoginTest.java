package tests;

import org.testng.annotations.Test;
import tests.common.BaseTest;
import utils.SensibleText;

public class LoginTest extends BaseTest {

    @Test(description = "Successful login to Qase")
    public void successfulLogin() {
        loginSteps.
                login(USERNAME, PASSWORD).
                checkThatUserIsLoggedIn();
    }

    @Test(description = "Email field should be mandatory")
    public void emailFieldShouldBeMandatory() {
        loginSteps.
                login(new SensibleText("", false), new SensibleText("", false)).
                checkEmptyFieldValidation();
    }

    @Test(description = "Password field should be mandatory")
    public void passwordFieldShouldBeMandatory() {
        loginSteps.
                login(USERNAME, new SensibleText("", false)).
                checkEmptyFieldValidation();
    }

    @Test(description = "Credentials should be correct")
    public void credentialsShouldBeCorrect() {
        loginSteps.
                login(USERNAME, new SensibleText("qwe123!@#$%^&*()123qwe", false)).
                checkThatWrongCredentialsAlertIsDisplayed().
                login(new SensibleText("sdfs4234#$@gmail.com", false), PASSWORD).
                checkThatWrongCredentialsAlertIsDisplayed();
    }

    @Test(description = "The user should be forced to change password if it's information is leaked")
    public void userShouldBeForcedToChangePasswordIfItsLeaked() {
        loginSteps.
                login(USERNAME, new SensibleText("1234", false)).
                checkThatPasswordWasLeakedAlertIsDisplayed().
                checkThatUserWasRedirectedToResetPasswordPage();
    }

    @Test(description = "Email must match email format")
    public void emailMustMatchEmailFormat() {
        SensibleText wrongEmail = new SensibleText(USERNAME.getText().replace("@", ""), true);
        loginSteps.
                login(wrongEmail, PASSWORD).
                checkThatEmailDoesntMatchEmailFormatAlertIsDisplayed(wrongEmail);
    }
}
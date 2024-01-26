package tests;

import org.testng.annotations.Test;
import tests.common.BaseTest;

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
                login("", "").
                checkEmptyFieldValidation();
    }

    @Test(description = "Password field should be mandatory")
    public void passwordFieldShouldBeMandatory() {
        loginSteps.
                login(USERNAME, "").
                checkEmptyFieldValidation();
    }

    @Test(description = "Credentials should be correct")
    public void credentialsShouldBeCorrect() {
        loginSteps.
                login(USERNAME, "qwe123!@#$%^&*()123qwe").
                checkThatWrongCredentialsAlertIsDisplayed().
                login("sdfs4234#$@gmail.com", PASSWORD).
                checkThatWrongCredentialsAlertIsDisplayed();
    }

    @Test(description = "The user should be forced to change password if it's information is leaked")
    public void userShouldBeForcedToChangePasswordIfItsLeaked() {
        loginSteps.
                login(USERNAME, "1234").
                checkThatPasswordWasLeakedAlertIsDisplayed().
                checkThatUserWasRedirectedToResetPasswordPage();
    }

    @Test(description = "Email must match email format")
    public void emailMustMatchEmailFormat() {
        String wrongEmail = USERNAME.replace("@", "");
        loginSteps.
                login(wrongEmail, PASSWORD).
                checkThatEmailDoesntMatchEmailFormatAlertIsDisplayed(wrongEmail);
    }
}
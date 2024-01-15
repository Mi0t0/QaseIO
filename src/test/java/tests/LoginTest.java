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
}
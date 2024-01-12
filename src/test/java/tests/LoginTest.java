package tests;

import org.testng.annotations.Test;
import tests.common.BaseTest;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(description = "Login to Qase")
    public void positiveLogin() {
        loginPage.openPage().
                fillInUsername(USERNAME).
                fillInPassword(PASSWORD).
                clickLoginButton();
        assertTrue(projectsListPage.isPageOpened(), "Login page is not opened");
    }
}

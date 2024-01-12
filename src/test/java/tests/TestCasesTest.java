package tests;

import dtos.TestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.common.BaseTest;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestCasesTest extends BaseTest {

    @BeforeMethod(description = "Login to Qase")
    public void positiveLogin() {
        loginPage.openPage().
                fillInUsername(USERNAME).
                fillInPassword(PASSWORD).
                clickLoginButton();
        assertTrue(projectsListPage.isPageOpened(), "Login page is not opened");

        String projectId = String.valueOf(new Random().nextInt(1000000000));
        projectsListPage.clickCreateNewProjectButton().
                fillInProjectName(projectId).
                fillInProjectCode(projectId).
                clickCreateProjectButton();
        assertTrue(projectPage.isPageOpened(), "Project is not created");

        projectPage.clickAddTestCaseButton();
        testCaseCreationPage.isPageOpened();
    }

    @Test(description = "Create new Test Case")
    public void testCaseShouldBeCreated() {
        testCaseCreationPage.
                createTestCase(
                        TestCase.builder().
                                title("Test Case").
                                status("Draft").
                                description("Description").
                                severity("Blocker").
                                priority("High").
                                type("Functional").
                                layer("API").
                                isFlaky(true).
                                behavior("Positive").
                                automationStatus("To be automated").
                                preconditions("Preconditions example").
                                postconditions("Postconditions example").
                                build());
        assertTrue(projectRepositoryPage.isPageOpened(), "Project Repository page is not opened");
        assertEquals(projectRepositoryPage.getTestCasesCount(), 1, "Test Case is not created or more than 1 test cases were created");
    }
}

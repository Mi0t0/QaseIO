package tests;

import dtos.TestCase;
import dtos.TestSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.common.BaseTest;

public class TestCasesTest extends BaseTest {

    private static final String PROJECT_CODE = "Test1337".toUpperCase();

    private static final String SUITE_NAME = "TestSuite";

    @BeforeMethod(description = "Login to Qase and create project")
    public void positiveLogin() {
        apiSteps.
                login(USERNAME, PASSWORD).
                deleteProjectByCodeIfExists(PROJECT_CODE).
                createProjectWithCode(PROJECT_CODE).
                createTestSuite(PROJECT_CODE, TestSuite.builder().title(SUITE_NAME).build());

        testCaseSteps.
                openCreatePage(PROJECT_CODE);
    }

    @DataProvider
    public Object[][] testCaseSpecs() {
        return new Object [][] {
                {
                    TestCase.builder().
                    title("Test Case 1").
                    status("Draft").
                    description("Description").
                    severity("Blocker").
                    priority("High").
                    type("Functional").
                    layer("API").
                    isFlaky(true).
                    behavior("Positive").
                    automationStatus("Manual").
                    preconditions("Pre-conditions example").
                    postconditions("Post-conditions example").
                    build()
                },
                {
                    TestCase.builder().
                    title("Test Case 2").
                    status("Actual").
                    severity("Major").
                    priority("Low").
                    type("Smoke").
                    layer("Unit").
                    behavior("Negative").
                    automationStatus("Automated").
                    build()
                },
                {
                    TestCase.builder().
                    title("Test Case 3").
                    status("Deprecated").
                    severity("Trivial").
                    priority("Low").
                    type("Smoke").
                    layer("Unit").
                    behavior("Destructive").
                    automationStatus("Manual").
                    build()
                }
        };
    }

    @Test(description = "Create Test Cases with different values", dataProvider = "testCaseSpecs")
    public void testCaseShouldBeCreated(TestCase testCase) {
        testCaseSteps.
                createTestCase(testCase).
                checkThatTestCaseIsCreated();
    }

    @Test(description = "Title field is required for Test Case creation")
    public void titleFieldShouldBeMandatory() {
        testCaseSteps.
                saveTestCaseWithEmptyTitle().
                checkThatTitleInputErrorDisplayed();
    }

    @Test(description = "Test Case should belong to it's test suite")
    public void testCaseShouldBelongToItsSuite() {
        String testCaseName = "Some Test Case";
        testCaseSteps.
                createTestCase(TestCase.builder().
                    title(testCaseName).
                    status("Deprecated").
                    suite(SUITE_NAME).
                    severity("Trivial").
                    priority("Low").
                    type("Smoke").
                    layer("Unit").
                    behavior("Destructive").
                    automationStatus("Manual").
                    build()).
                checkThatTestCaseBelongsToSuite(SUITE_NAME, testCaseName);
    }
}
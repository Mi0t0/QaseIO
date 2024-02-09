package tests;

import dtos.TestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.common.BaseTest;

public class TestPlanTest extends BaseTest {

    private static final String PROJECT_CODE = "Test2838".toUpperCase();

    @BeforeMethod
    public void setUp() {
        apiSteps.
                login(USERNAME, PASSWORD).
                deleteProjectByCodeIfExists(PROJECT_CODE).
                createProjectWithCode(PROJECT_CODE);

        TestCase testCase = TestCase.builder().
                title("Test Case 1").
                build();
        testCaseSteps.
                openCreatePage(PROJECT_CODE).
                createTestCase(testCase);
    }

    @Test
    public void testPlanCreationTest() {
        String testPlanTitle = "Test Plan 1";
        String testPlanDescription = "Description";
        testPlanSteps.
                openTestPlansListPage(PROJECT_CODE).
                createTestPlanWithAllCases(testPlanTitle, testPlanDescription).
                checkTestPlanIsCreated(testPlanTitle);
    }

}

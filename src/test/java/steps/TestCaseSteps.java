package steps;

import dtos.TestCase;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.ProjectRepositoryPage;
import pages.TestCaseCreationPage;
import pages.TestCaseEditPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Log4j2
public class TestCaseSteps extends BaseStep {

    private final TestCaseCreationPage testCaseCreationPage = new TestCaseCreationPage();

    private final ProjectRepositoryPage projectRepositoryPage = new ProjectRepositoryPage();

    private final TestCaseEditPage testCaseEditPage = new TestCaseEditPage();

    @Step("Open test case creation page")
    public TestCaseSteps openCreatePage(String projectCode) {
        log.info("Opening TestCaseCreationPage for project '{}'", projectCode);
        testCaseCreationPage.
                openPage(projectCode).
                isPageOpened();
        return this;
    }

    @Step("Create test case '{testCase.title}'")
    public TestCaseSteps createTestCase(TestCase testCase) {
        log.info("Creating test case '{}'", testCase.getTitle());
        testCaseCreationPage.
                fillInAllFields(testCase).
                clickSaveTestCaseButton();
        return this;
    }

    @Step("Check that test case is created")
    public TestCaseSteps checkThatTestCaseIsCreated() {
        log.info("Checking that test case is created");
        assertTrue(projectRepositoryPage.isPageOpened(), "Project Repository page is not opened");
        assertEquals(projectRepositoryPage.getTestCasesCount(), 1, "Test Case is not created or more than 1 test cases were created");
        return this;
    }

    @Step("Check that test case belongs to suite")
    public TestCaseSteps checkThatTestCaseBelongsToSuite(String suiteName, String testCaseName) {
        log.info("Checking that test case belongs to suite");
        assertTrue(projectRepositoryPage.isPageOpened(), "Project Repository page is not opened");
        assertTrue(projectRepositoryPage.doesTestCaseBelongToSuite(suiteName, testCaseName), "Test Case is not created or or belongs to another suite");
        return this;
    }

    @Step("Open test case number '{index}' of project '{projectCode}'")
    public TestCaseSteps openTestCase(String projectCode, int index) {
        log.info("Opening test case number '{}' of project '{}'", index, projectCode);
        testCaseEditPage.
                openPage(projectCode, index).
                isPageOpened();
        return this;
    }

    @Step("Check test case specs")
    public TestCaseSteps checkTestCaseSpecs(TestCase testCase) {
        log.info("Checking test case specs");
        assertEquals(testCaseEditPage.getTestCaseSpecs(), testCase, "Test Case specs has incorrect value(s)");
        return this;
    }
}
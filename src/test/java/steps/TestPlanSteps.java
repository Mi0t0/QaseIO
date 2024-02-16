package steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.testPlanPages.TestPlanCreationPage;
import pages.testPlanPages.TestPlansListPage;

import static org.testng.Assert.assertTrue;

@Log4j2
public class TestPlanSteps extends BaseStep{

    TestPlansListPage testPlansListPage = new TestPlansListPage();

    TestPlanCreationPage testPlanCreationPage = new TestPlanCreationPage();

    @Step("Open test plan page")
    public TestPlanSteps openTestPlansListPage(String projectCode) {
        log.info("Opening test plan page");
        testPlansListPage.
                openPage(projectCode).
                isPageOpened();
        return this;
    }

    @Step("Create test plan '{title}' with all cases selected")
    public TestPlanSteps createTestPlanWithAllCases(String title, String description) {
        log.info("Creating test plan with all cases");
        testPlansListPage.
                clickCreatePlan();
        testPlanCreationPage.
                enterTitle(title).
                enterDescription(description).
                clickAddCases().
                waitUntilSelectCasesModalIsAppeared().
                chooseAllCases();
        closeNotificationsIfExist();
        testPlanCreationPage.
                clickDoneButton().
                clickCreatePlan();
        return this;
    }

    @Step("Check that test plan '{title}' is created")
    public TestPlanSteps checkTestPlanIsCreated(String title) {
        log.info("Checking that test plan is created");
        testPlansListPage.isPageOpened();
        assertTrue(testPlansListPage.getTestPlansTitles().contains(title));
        return this;
    }
}
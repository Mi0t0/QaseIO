package pages;

import com.codeborne.selenide.Condition;
import dtos.TestCase;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import wrappers.Input;
import wrappers.Picklist;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class TestCaseCreationPage extends BasePage {

    private static final String TITLE_INPUT_ID = "title";

    private static final String SAVE_TEST_CASE_BUTTON_ID = "save-case";

    @Override
    public boolean isPageOpened() {
        log.info("Check that TestCaseCreationPage is opened");
        try {
            $(By.id(TITLE_INPUT_ID)).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Step("Create test case {testCase.title}")
    public void createTestCase(TestCase testCase) {
        log.info("Creating test case {}", testCase.getTitle());
        if (testCase != null) {
            input = new Input();
            picklist = new Picklist();

            input.fillInInput("Title", testCase.getTitle());
            picklist.select("Status", testCase.getStatus());
            input.fillInTextArea("Description", testCase.getDescription());
            picklist.select("Severity", testCase.getSeverity());
            picklist.select("Priority", testCase.getPriority());
            picklist.select("Type", testCase.getType());
            picklist.select("Layer", testCase.getLayer());
            if (testCase.isFlaky())
                picklist.select("Is flaky", "Yes"); // NO is default value
            picklist.select("Behavior", testCase.getBehavior());
            picklist.select("Automation status", testCase.getAutomationStatus());
            input.fillInTextArea("Pre-conditions", testCase.getPreconditions());
            input.fillInTextArea("Post-conditions", testCase.getPostconditions());

            clickSaveTestCaseButton();
        }
    }

    @Step("Click add attachment button")
    public void clickSaveTestCaseButton() {
        $(By.id(SAVE_TEST_CASE_BUTTON_ID)).click();
    }
}
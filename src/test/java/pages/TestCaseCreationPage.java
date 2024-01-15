package pages;

import com.codeborne.selenide.Condition;
import dtos.TestCase;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import wrappers.Input;
import wrappers.Picklist;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class TestCaseCreationPage extends BasePage {

    private static final String TITLE_INPUT_ID = "title";

    private static final String SAVE_TEST_CASE_BUTTON_ID = "save-case";

    public TestCaseCreationPage openPage(String projectCode) {
        open(String.format("/case/%s/create", projectCode));
        return this;
    }

    @Override
    public boolean isPageOpened() {
        try {
            $(By.id(TITLE_INPUT_ID)).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public TestCaseCreationPage fillInAllFields(TestCase testCase) {
        input = new Input();
        picklist = new Picklist();

        input.fillInInput("Title", testCase.getTitle());
        picklist.select("Status", testCase.getStatus());
        input.fillInTextArea("Description", testCase.getDescription());
        picklist.selectSuite(testCase.getSuite());
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

        return this;
    }

    public TestCaseCreationPage clickSaveTestCaseButton() {
        $(By.id(SAVE_TEST_CASE_BUTTON_ID)).click();
        return this;
    }

    public TestCaseCreationPage makeSaveButtonEnabled() {
        input = new Input();
        input.fillInInput("Title", "Test");
        input.cleanInput("Title");
        return this;
    }

    public boolean isTitleInputErrorDisplayed() {
        return Boolean.parseBoolean($(By.id(TITLE_INPUT_ID)).getAttribute("required"));
    }
}
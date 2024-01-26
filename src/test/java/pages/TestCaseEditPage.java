package pages;

import com.codeborne.selenide.Condition;
import dtos.TestCase;
import helpers.InputHelper;
import helpers.PicklistHelper;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class TestCaseEditPage extends BasePage {

    private static final String TITLE_INPUT_ID = "title";


    public TestCaseEditPage openPage(String projectCode, int testCaseIndex) {
        open(String.format("/case/%s/edit/%s", projectCode, testCaseIndex));
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

    public TestCase getTestCaseSpecs() {
        InputHelper input = new InputHelper();
        PicklistHelper picklist = new PicklistHelper();

        return TestCase.builder().
                title($(By.id(TITLE_INPUT_ID)).getValue()).
                status(picklist.getPickListText("Status")).
                description(input.getTextAreaText("Description")).
                severity(picklist.getPickListText("Severity")).
                priority(picklist.getPickListText("Priority")).
                type(picklist.getPickListText("Type")).
                layer(picklist.getPickListText("Layer")).
                isFlaky(picklist.getPickListText("Is flaky").equals("Yes")).
                behavior(picklist.getPickListText("Behavior")).
                automationStatus(picklist.getPickListText("Automation status")).
                preconditions(input.getTextAreaText("Pre-conditions")).
                postconditions(input.getTextAreaText("Post-conditions")).
                build();
    }
}
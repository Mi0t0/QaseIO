package pages;

import com.codeborne.selenide.Condition;
import dtos.TestCase;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class TestCaseEditPage extends BasePage {

    private static final String TITLE_INPUT_ID = "title";

    private static final String TEXTAREA_XPATH = "(//*[text()='%s']/following-sibling::*//p)[last()]";

    private static final String PICKLIST_TEXT_XPATH = "//*[text()='%s']/following-sibling::*//*[text()]";

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

    public String getPickListText(String pickListName) {
        return $x(String.format(PICKLIST_TEXT_XPATH, pickListName)).getText();
    }

    public String getTextAreaText(String textAreaName) {
        if ($x(String.format(TEXTAREA_XPATH, textAreaName)).isDisplayed())
            return $x(String.format(TEXTAREA_XPATH, textAreaName)).getText();
        else
            return "";
    }

    public TestCase getTestCaseSpecs() {
        String description = getTextAreaText("Description");
        String preconditions = getTextAreaText("Pre-conditions");
        String postconditions = getTextAreaText("Post-conditions");

        if (description.isEmpty())
            description = null;
        if (preconditions.isEmpty())
            preconditions = null;
        if (postconditions.isEmpty())
            postconditions = null;

        return TestCase.builder().
                title($(By.id(TITLE_INPUT_ID)).getValue()).
                status(getPickListText("Status")).
                description(description).
                severity(getPickListText("Severity")).
                priority(getPickListText("Priority")).
                type(getPickListText("Type")).
                layer(getPickListText("Layer")).
                isFlaky(getPickListText("Is flaky").equals("Yes")).
                behavior(getPickListText("Behavior")).
                automationStatus(getPickListText("Automation status")).
                preconditions(preconditions).
                postconditions(postconditions).
                build();
    }
}
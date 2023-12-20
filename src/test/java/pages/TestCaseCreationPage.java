package pages;

import com.codeborne.selenide.Condition;
import dtos.TestCase;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class TestCaseCreationPage extends BasePage{

    private static final String TITLE_INPUT_ID = "title";

    private static final String DESCRIPTION_TEXT_AREA_XPATH = "//*[contains(text(),'Description')]/..//p";

    private static final String PRECONDITIONS_TEXT_AREA_XPATH = "//*[contains(text(),'Pre-conditions')]/..//p";

    private static final String POSTCONDITIONS_TEXT_AREA_XPATH = "//*[contains(text(),'Post-conditions')]/..//p";

    private static final String ADD_ATTACHMENT_BUTTON_XPATH = "//*[contains(text(),'Add attachment')]/..";

    private static final String ADD_PARAMETER_BUTTON_XPATH = "//*[contains(text(),'Add parameter')]/..";

    private static final String ADD_STEP_BUTTON_XPATH = "//*[contains(text(),'Add step')]/..";

    private static final String ADD_SHARED_STEP_BUTTON_XPATH = "//*[contains(text(),'Add shared step')]/..";

    private static final String SAVE_TEST_CASE_BUTTON_ID = "save-case";

    @Override
    public boolean isPageOpened() {
        try {
            $(By.id(TITLE_INPUT_ID)).shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void createTestCase(TestCase testCase) {
        if (testCase.getTitle() != null) {
            $(By.id(TITLE_INPUT_ID)).setValue(testCase.getTitle());
        }
        if (testCase.getDescription() != null) {
            $(By.xpath(DESCRIPTION_TEXT_AREA_XPATH)).setValue(testCase.getDescription());
        }
        if (testCase.getPreconditions() != null) {
            $(By.xpath(PRECONDITIONS_TEXT_AREA_XPATH)).setValue(testCase.getPreconditions());
        }
        if (testCase.getPostconditions() != null) {
            $(By.xpath(POSTCONDITIONS_TEXT_AREA_XPATH)).setValue(testCase.getPostconditions());
        }
        clickSaveTestCaseButton();
    }

    public void clickSaveTestCaseButton() {
        $(By.id(SAVE_TEST_CASE_BUTTON_ID)).click();
    }
}

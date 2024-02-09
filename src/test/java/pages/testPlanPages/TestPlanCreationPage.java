package pages.testPlanPages;

import com.codeborne.selenide.Condition;
import helpers.InputHelper;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.*;

public class TestPlanCreationPage extends BasePage {

    private static final String DESCRIPTION_XPATH = "//p[@data-placeholder]";

    private static final String BUTTON_XPATH = "//*[contains(text(),'%s')]";

    private static final String SUITE_CHECKBOX_XPATH = "//p[contains(text(),'%s')]/preceding-sibling::span/input";

    public TestPlanCreationPage openPage(String projectCode) {
        open(String.format("/plan/%s/create", projectCode));
        return this;
    }

    @Override
    public boolean isPageOpened() {
        try {
            $x("//*[text()='Create test plan']").shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TestPlanCreationPage enterTitle(String title) {
        input = new InputHelper();
        input.fillInInput("Title", title);
        return this;
    }

    public TestPlanCreationPage enterDescription(String description) {
        $x(DESCRIPTION_XPATH).setValue(description);
        return this;
    }

    public TestPlanCreationPage clickCreatePlan() {
        $x(String.format(BUTTON_XPATH, "Create plan")).click();
        return this;
    }

    public TestPlanCreationPage clickAddCases() {
        $x(String.format(BUTTON_XPATH, "Add cases")).click();
        return this;
    }

    public TestPlanCreationPage chooseSuiteByName(int suiteName) {
        $x(String.format(SUITE_CHECKBOX_XPATH, suiteName)).click();
        return this;
    }

    public TestPlanCreationPage chooseAllCases() {
        $x(String.format(BUTTON_XPATH, "Select all")).click();
        return this;
    }

    public TestPlanCreationPage clickDoneButton() {
        $x(String.format(BUTTON_XPATH, "Done")).click();
        return this;
    }

    public TestPlanCreationPage waitUntilSelectCasesModalIsAppeared() {
        try {
            $x("//*[@type='checkbox']/..").shouldBe(Condition.visible);
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Select cases modal was not appeared");
        }
    }

    public TestPlanCreationPage addAllCases(){
        clickAddCases().
                waitUntilSelectCasesModalIsAppeared().
                chooseAllCases().
                clickDoneButton();
        return this;
    }
}
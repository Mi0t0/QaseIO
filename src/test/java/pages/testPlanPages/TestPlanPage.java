package pages.testPlanPages;

import com.codeborne.selenide.Condition;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class TestPlanPage extends BasePage {

    public TestPlanPage openPage(String projectCode, int testPlanIndex) {
        open(String.format("/plan/%s/%s", projectCode, testPlanIndex));
        return this;
    }

    @Override
    public boolean isPageOpened() {
        try {
            $x("//*[contains(text(),'Start a run')]/ancestor::button").shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
package pages.testPlanPages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class TestPlansListPage extends BasePage {

    private static final String BUTTON_XPATH = "//*[contains(text(),'%s')]";

    private static final String TEST_PLANS_XPATH = "//table//*[contains(@href,'/plan/')]";

    public TestPlansListPage openPage(String projectCode) {
        open(String.format("/plan/%s", projectCode));
        return this;
    }

    @Override
    public boolean isPageOpened() {
        try {
            $x("//table | //*[contains(text(),'you don’t have any test plans yet')]").shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getTestPlansTitles() {
        ArrayList<String> testPlansTitles = new ArrayList<>();
        for (WebElement testPlan : $$x(TEST_PLANS_XPATH)) {
            testPlansTitles.add(testPlan.getText());
        }
        return testPlansTitles;
    }

    public TestPlansListPage clickCreatePlan() {
        $x(String.format(BUTTON_XPATH, "Create plan")).click();
        return this;
    }
}

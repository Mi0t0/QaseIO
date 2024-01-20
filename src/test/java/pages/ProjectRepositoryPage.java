package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class ProjectRepositoryPage extends BasePage {

    private static final String TEST_CASES_AREA_CSS = "[data-suite-body-id]";

    private static final String TEST_CASES_LIST_CSS = TEST_CASES_AREA_CSS + "[draggable]";

    private static final String TEST_CASE_XPATH = "//*[text()='%s']//ancestor::*[@data-suite-body-id]/ancestor::*[@class][1]/following-sibling::*[1]//*[text()='%s']";

    @Override
    public boolean isPageOpened() {
        try {
            $(TEST_CASES_AREA_CSS).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Step("Get test cases count")
    public int getTestCasesCount() {
        return $$(TEST_CASES_LIST_CSS).size();
    }

    @Step("Check if test case belongs to suite")
    public boolean doesTestCaseBelongToSuite(String suiteTitle, String testCaseTitle) {
        return $x(String.format(TEST_CASE_XPATH, suiteTitle, testCaseTitle)).exists();
    }
}

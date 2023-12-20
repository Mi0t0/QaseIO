package pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectRepositoryPage extends BasePage{

    private static final String TEST_CASES_AREA_CSS = "[data-suite-body-id]";

    private static final String TEST_CASES_LIST_CSS = "[data-suite-body-id][draggable]";

    @Override
    public boolean isPageOpened() {
        try {
            $(TEST_CASES_AREA_CSS).shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTestCasesCount() {
        return $$(TEST_CASES_LIST_CSS).size();
    }
}

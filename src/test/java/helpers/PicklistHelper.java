package helpers;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class PicklistHelper {

    private static final String PICKLIST_XPATH = "//label[text()='%s']//following::input";

    private static final String PICKLIST_ITEM_XPATH = "(//*[@id='modals']/*)[last()]//*[text()='%s']";

    private static final String SUITE_PICKLIST_XPATH = "//*[contains(text(),'Suite') and @for]/..//*[text()='%s']";

    private static final String PICKLIST_TEXT_XPATH = "//*[text()='%s']/following-sibling::*//*[text()]";

    public void select(String label, String option) {
        if (option != null) {
            log.info("Selecting option '{}' from picklist with label '{}'", option, label);
            $x(String.format(PICKLIST_XPATH, label)).click();
            $x(String.format(PICKLIST_ITEM_XPATH, option)).click();
        }
    }

    public void selectSuite(String suiteTitle) {
        if (suiteTitle != null) {
            log.info("Filling in suite {}", suiteTitle);
            $x(String.format(PICKLIST_XPATH, "Suite")).click();
            $x(String.format(SUITE_PICKLIST_XPATH, suiteTitle)).click();
        }
    }

    public String getPickListText(String label) {
        return $x(String.format(PICKLIST_TEXT_XPATH, label)).getText();
    }
}
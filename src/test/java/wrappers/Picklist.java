package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class Picklist {

    private static final String PICKLIST_XPATH = "//label[text()='%s']//following::input";

    private static final String PICKLIST_ITEM_XPATH = "(//*[@id='modals']/*)[last()]//*[text()='%s']";

    private static final String SUITE_PICKLIST_XPATH = "//*[contains(text(),'Suite') and @for]/..//*[text()='%s']";

    public void select(String label, String option) {
        if (option != null) {
            log.info("Selecting option '{}' from picklist with label '{}'", option, label);
            $(By.xpath(String.format(PICKLIST_XPATH, label))).click();
            $(By.xpath(String.format(PICKLIST_ITEM_XPATH, option))).click();
        }
    }

    public void selectSuite(String suiteTitle) {
        if (suiteTitle != null) {
            log.info("Filling in suite {}", suiteTitle);
            $(By.xpath(String.format(PICKLIST_XPATH, "Suite"))).click();
            $(By.xpath(String.format(SUITE_PICKLIST_XPATH, suiteTitle))).click();
        }
    }
}
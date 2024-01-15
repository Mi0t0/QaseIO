package wrappers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class Input {

    private static final String TEXT_AREA_XPATH = "//*[contains(text(),'%s') and @for]//following::p";

    private static final String INPUT_XPATH = "//*[contains(text(),'%s') and @for]//following::input";

    public void fillInInput(String label, String text) {
        if (text != null) {
            log.info("Writing text '{}' into input with label '{}'", text, label);
            $(By.xpath(String.format(INPUT_XPATH, label))).setValue(text);
        }
    }

    public void fillInTextArea(String label, String text) {
        if (text != null) {
            log.info("Writing text '{}' into text area with label '{}'", text, label);
            $(By.xpath(String.format(TEXT_AREA_XPATH, label))).setValue(text);
        }
    }

    public void cleanInput(String label) {
        log.info("Cleaning an input with label '{}'", label);
        $(By.xpath(String.format(INPUT_XPATH, label))).clear();
    }

    public void cleanTextArea(String label) {
        log.info("Cleaning a text area with label '{}'", label);
        $(By.xpath(String.format(TEXT_AREA_XPATH, label))).clear();
    }
}
package steps;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BaseStep {

    private final String NOTIFICATION_CSS = ".intercom-authored-container";

    private final String CLOSE_NOTIFICATION_XPATH = "//*[contains(text(), 'Clear All')]";

    public void closeNotificationsIfExist() {
        while ($(NOTIFICATION_CSS).isDisplayed())
            $x(CLOSE_NOTIFICATION_XPATH).click();
    }

}
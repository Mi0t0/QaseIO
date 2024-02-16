package steps;

import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseStep {

    private static final String CONTAINER_IFRAME_XPATH = "//*[@id='intercom-container']//iframe";

    private final String CLOSE_NOTIFICATION_XPATH = "//*[contains(text(), 'Clear')]";

    public void closeNotificationsIfExist() {
        if ($x(CONTAINER_IFRAME_XPATH).isDisplayed()) {
            getWebDriver().switchTo().frame($x(CONTAINER_IFRAME_XPATH));
            new Actions(getWebDriver()).moveToElement($x(CLOSE_NOTIFICATION_XPATH)).build().perform();
            $x(CLOSE_NOTIFICATION_XPATH).click();
            getWebDriver().switchTo().defaultContent();
        }
    }
}
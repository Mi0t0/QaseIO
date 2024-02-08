package pages.loginPages;

import com.codeborne.selenide.Condition;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage {

    private static final String USERNAME_INPUT_CSS = "[name=email]";

    private static final String PASSWORD_INPUT_CSS = "[name=password]";

    private static final String LOGIN_BUTTON_CSS = "[type=submit]";

    private static final String REQUIRED_FIELD_VALIDATION_XPATH = "//small[contains(text(),'This field is required')]";

    private static final String ALERT_CSS = "[role=alert]";

    @Override
    public boolean isPageOpened() {
        try {
            $(LOGIN_BUTTON_CSS).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public LoginPage openPage() {
        open("/login");
        return this;
    }

    public LoginPage fillInUsername(String username) {
        $(USERNAME_INPUT_CSS).setValue(username);
        return this;
    }

    public LoginPage fillInPassword(String password) {
        $(PASSWORD_INPUT_CSS).setValue(password);
        return this;
    }

    public LoginPage clickLoginButton() {
        $(LOGIN_BUTTON_CSS).click();
        return this;
    }

    public boolean isEmptyFieldValidationDisplayed() {
        try {
            $x(REQUIRED_FIELD_VALIDATION_XPATH).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public String getAlertText() {
        return $(ALERT_CSS).getText();
    }
}
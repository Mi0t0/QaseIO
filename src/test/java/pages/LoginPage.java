package pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage{

    private static final String USERNAME_INPUT_CSS = "[name=email]";

    private static final String PASSWORD_INPUT_CSS = "[name=password]";

    private static final String LOGIN_BUTTON_CSS = "[type=submit]";

    @Override
    public boolean isPageOpened() {
        try {
            $(LOGIN_BUTTON_CSS).shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
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

    public void clickLoginButton() {
        $(LOGIN_BUTTON_CSS).click();
    }
}

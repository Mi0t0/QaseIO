package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

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

    @Step("Open login page")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Fill in username")
    public LoginPage fillInUsername(String username) {
        $(USERNAME_INPUT_CSS).setValue(username);
        return this;
    }

    @Step("Fill in password")
    public LoginPage fillInPassword(String password) {
        $(PASSWORD_INPUT_CSS).setValue(password);
        return this;
    }

    @Step("Click login button")
    public void clickLoginButton() {
        $(LOGIN_BUTTON_CSS).click();
    }
}

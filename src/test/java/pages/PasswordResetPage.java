package pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class PasswordResetPage extends BasePage{

    private static final String RESET_PASSWORD_LABEL_XPATH = "//*[contains(text(),'Reset your password')]";

    @Override
    public boolean isPageOpened() {
        try {
            $x(RESET_PASSWORD_LABEL_XPATH).shouldBe(Condition.visible);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
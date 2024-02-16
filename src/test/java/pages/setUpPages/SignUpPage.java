package pages.setUpPages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SignUpPage {

    public SignUpPage openPage() {
        open("/signup");
        return this;
    }

    public void createAccount(String username, String password) {
        openPage();
        fillInEmail(username);
        fillInPassword(password);
        fillInPasswordConfirmation(password);
        clickSubmit();
    }

    public SignUpPage fillInEmail(String username) {
        $("[name=email]").setValue(username);
        return this;
    }

    public SignUpPage fillInPassword(String password) {
        $("[name=password]").setValue(password);
        return this;
    }

    public SignUpPage fillInPasswordConfirmation(String password) {
        $("[name=passwordConfirmation]").setValue(password);
        return this;
    }

    public SignUpPage clickSubmit() {
        $("[type=submit]").click();
        return this;
    }

}

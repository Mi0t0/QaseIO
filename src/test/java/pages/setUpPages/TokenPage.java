package pages.setUpPages;

import static com.codeborne.selenide.Selenide.*;

public class TokenPage {

    public TokenPage openPage() {
        open("/user/api/token");
        return this;
    }

    public TokenPage createApiToken() {
        $x("//*[text()='Create a new API token']").click();
        $("input[value]").setValue("Token");
        $x("//*[text()='Create']").click();
        return this;
    }

    public String getToken() {
        return $("input[readonly]").getAttribute("value");
    }

}

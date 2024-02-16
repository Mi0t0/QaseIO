package pages.setUpPages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public class OnboardingPage {

    public OnboardingPage openPage() {
        open("/onboarding");
        return this;
    }

    public boolean isPageOpened() {
        try {
            $("[id=firstName]").shouldBe(Condition.visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public OnboardingPage fillInFirstName(String firstName) {
        $("[id=firstName]").setValue(firstName);
        return this;
    }

    public OnboardingPage fillInLastName(String lastName) {
        $("[id=lastName]").setValue(lastName);
        return this;
    }

    public OnboardingPage fillInJobTitle(String jobTitle) {
        $("[id=jobTitle]").setValue(jobTitle);
        return this;
    }

    public OnboardingPage specifyRoleDescription(int index) {
        index += 1;
        $x(String.format("(//*[@class='form-check']//input)[%s]", index)).click();
        return this;
    }

    public OnboardingPage fillInCompanyName(String companyName) {
        $("[id=companyName]").setValue(companyName);
        return this;
    }

    public OnboardingPage specifyCompanyDescription(int index) {
        index += 1;
        $x(String.format("(//*[@class='form-check']//input)[%s]", index)).click();
        return this;
    }

    public OnboardingPage specifyIndustry(int index) {
        index += 3;
        $x(String.format("(//*[@class='form-check']//input)[%s]", index)).click();
        return this;
    }

    public OnboardingPage clickSubmit() {
        $("[type=submit]").click();
        return this;
    }

    public OnboardingPage clickLater() {
        $(".onboarding-form-later-container [type=button]").click();
        return this;
    }
}

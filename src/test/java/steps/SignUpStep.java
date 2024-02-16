package steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.setUpPages.OnboardingPage;
import pages.setUpPages.SignUpPage;
import pages.setUpPages.TokenPage;

@Log4j2
public class SignUpStep {

    SignUpPage signUpPage = new SignUpPage();

    OnboardingPage onboardingPage = new OnboardingPage();

    TokenPage tokenPage = new TokenPage();

    @Step("Wait for {seconds} seconds")
    public SignUpStep waitForXSeconds(int seconds) {
        log.info("Waiting for {} seconds", seconds);
        try {
            Thread.sleep(seconds * 1000L);
            return this;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Create Qase account")
    public SignUpStep createQaseAccount(String email, String password) {
        log.info("Creating Qase account");
        signUpPage.
                createAccount(email, password);
        return this;
    }

    public SignUpStep openOnboardingPage() {
        log.info("Opening onboarding page");
        if (onboardingPage.openPage().isPageOpened())
            return this;
        else
            throw new RuntimeException("Onboarding page is not opened");
    }

    @Step("Complete onboarding")
    public SignUpStep completeOnboarding() {
        log.info("Completing onboarding");
        onboardingPage.
                fillInFirstName("John").
                fillInLastName("Doe").
                fillInJobTitle("QA").
                specifyRoleDescription(0).
                clickSubmit().
                fillInCompanyName("Qase").
                specifyCompanyDescription(0).
                specifyIndustry(0).
                clickSubmit().
                clickLater();
        return this;
    }

    @Step("Create API token")
    public String createApiToken() {
        log.info("Creating API token");
        return tokenPage.
                openPage().
                createApiToken().
                getToken();
    }
}

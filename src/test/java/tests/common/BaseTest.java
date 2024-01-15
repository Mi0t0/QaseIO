package tests.common;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import steps.ApiSteps;
import steps.LoginSteps;
import steps.ProjectSteps;
import steps.TestCaseSteps;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.PropertyReader.getProperty;

public class BaseTest {

    public static String USERNAME;

    public static String PASSWORD;

    protected Faker faker;

    protected ApiSteps apiSteps;

    protected LoginSteps loginSteps;

    protected ProjectSteps projectSteps;

    protected TestCaseSteps testCaseSteps;

    @Parameters({"browser"})
    @BeforeMethod(description = "Browser setup")
    public void setUp(@Optional("chrome") String browser, ITestContext testContext) {
        if (browser.equalsIgnoreCase("chrome")) {
            Configuration.browser = "chrome";
        } else if (browser.equalsIgnoreCase("edge")) {
            Configuration.browser = "edge";
        } else if (browser.equalsIgnoreCase("firefox")) {
            Configuration.browser = "firefox";
        } else {
            throw new IllegalArgumentException("Unknown browser: " + browser);
        }
        Configuration.headless = true;
        baseUrl = getProperty("qase.base.url");
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        open(baseUrl);
        testContext.setAttribute("driver", getWebDriver());

        faker = new Faker();
        apiSteps = new ApiSteps();
        loginSteps = new LoginSteps();
        projectSteps = new ProjectSteps();
        testCaseSteps = new TestCaseSteps();

        USERNAME = System.getProperty("user", getProperty("qase.user"));
        PASSWORD = System.getProperty("password", getProperty("qase.password"));
    }

    @AfterMethod(alwaysRun = true, description = "Browser teardown")
    public void tearDown() {
        closeWebDriver();
    }
}
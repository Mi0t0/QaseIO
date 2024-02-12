package tests.common;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.*;
import steps.*;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static utils.PropertyReader.getProperty;

@Log4j2
@Listeners(TestListener.class)
public class BaseTest {

    public static String USERNAME;

    public static String PASSWORD;

    protected Faker faker;

    protected ApiSteps apiSteps;

    protected LoginSteps loginSteps;

    protected ProjectSteps projectSteps;

    protected TestCaseSteps testCaseSteps;

    protected TestPlanSteps testPlanSteps;

    @BeforeSuite(description = "Suite setup")
    public void environmentSetup() {
        new ApiSteps().cleanProjectsList();
    }

    @Parameters({"browser"})
    @BeforeMethod(description = "Browser setup")
    public void setUp(@Optional("chrome") String browser) {

        log.info("Opening browser {}", browser);
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
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        baseUrl = getProperty("qase.base.url");
        open(baseUrl);

        faker = new Faker();
        apiSteps = new ApiSteps();
        loginSteps = new LoginSteps();
        projectSteps = new ProjectSteps();
        testCaseSteps = new TestCaseSteps();
        testPlanSteps = new TestPlanSteps();

        USERNAME = System.getProperty("user", getProperty("qase.user"));
        PASSWORD = System.getProperty("password", getProperty("qase.password"));
    }

    @AfterMethod(alwaysRun = true, description = "Browser teardown")
    public void tearDown() {
        log.info("Closing browser");
        closeWebDriver();
    }

    public static String getRandomProjectId() {
        return new Faker().numerify("##########");
    }
}
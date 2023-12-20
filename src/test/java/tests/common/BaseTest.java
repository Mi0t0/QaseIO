package tests.common;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.PropertyReader.getProperty;

public class BaseTest {

    public static String USERNAME;

    public static String PASSWORD;

    protected Faker faker;

    protected LoginPage loginPage;

    protected ProjectsListPage projectsListPage;

    protected ProjectPage projectPage;

    protected TestCaseCreationPage testCaseCreationPage;

    protected ProjectRepositoryPage projectRepositoryPage;

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
        Configuration.headless = false;
        Configuration.baseUrl = "https://app.qase.io";
        Configuration.timeout = 4000;
        Configuration.browserSize = "1920x1080";
        open();
        testContext.setAttribute("driver", getWebDriver());

        faker = new Faker();
        loginPage = new LoginPage();
        projectsListPage = new ProjectsListPage();
        projectPage = new ProjectPage();
        testCaseCreationPage = new TestCaseCreationPage();
        projectRepositoryPage = new ProjectRepositoryPage();

        USERNAME = System.getProperty("user", getProperty("qase_user"));
        PASSWORD = System.getProperty("password", getProperty("qase_password"));
    }

    @AfterMethod(alwaysRun = true, description = "Browser teardown")
    public void tearDown() {
        closeWebDriver();
    }
}

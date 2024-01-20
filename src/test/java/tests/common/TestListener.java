package tests.common;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

import static utils.AllureUtils.takeScreenshot;

@Log4j2
public class TestListener implements ITestListener {

    WebDriver driver;

//    @Override
//    public void onTestStart(ITestResult iTestResult) {
//        log.info(String.format("======================================== STARTING TEST %s ========================================", iTestResult.getName()));
//    }

//    @Override
//    public void onTestSuccess(ITestResult iTestResult) {
//        log.info(String.format("======================================== FINISHED TEST %s Duration: %ss ========================================", iTestResult.getName(),
//                getExecutionTime(iTestResult)));
//    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info(String.format("======================================== FAILED TEST %s Duration: %ss ========================================", iTestResult.getName(),
                getExecutionTime(iTestResult)));
        driver = (WebDriver) iTestResult.getTestContext().getAttribute("driver");
        takeScreenshot(driver);
    }

//    @Override
//    public void onTestSkipped(ITestResult iTestResult) {
//        log.info(String.format("======================================== SKIPPING TEST %s ========================================", iTestResult.getName()));
//    }

//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
//        log.info(String.format("======================================== FAILED WITH SUCCESS PERCENTAGE TEST %s Duration: %ss ========================================", iTestResult.getName(),
//                getExecutionTime(iTestResult)));
//    }

//    @Override
//    public void onStart(ITestContext iTestContext) {
//        log.info(String.format("======================================== STARTING TEST SUITE %s ========================================", iTestContext.getName()));
//    }

//    @Override
//    public void onFinish(ITestContext iTestContext) {
//        log.info(String.format("======================================== FINISHED TEST SUITE %s Duration: %ss ========================================", iTestContext.getName()));
//    }

    private long getExecutionTime(ITestResult iTestResult) {
        return TimeUnit.MILLISECONDS.toSeconds(iTestResult.getEndMillis() - iTestResult.getStartMillis());
    }
}
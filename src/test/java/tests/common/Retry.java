package tests.common;

import lombok.extern.log4j.Log4j2;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Log4j2
public class Retry implements IRetryAnalyzer {

    private int attempt = 1;
    private static final int MAX_RETRY = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        log.info("Retrying test method {} with status {} for {}/{} time", iTestResult.getMethod().getMethodName(), iTestResult.getStatus(), attempt, MAX_RETRY);
        if (!iTestResult.isSuccess()) {
            if (attempt <= MAX_RETRY) {
                attempt++;
                iTestResult.setStatus(ITestResult.FAILURE);
                return true;
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
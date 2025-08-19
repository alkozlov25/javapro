package com.edu.module1;

import com.edu.module1.enums.TestResult;

public class TestInfo {
    private TestResult testResult;

    private String testName;

    private Exception exception;

    public TestInfo(TestResult testResult, String testName) {
        this.testResult = testResult;
        this.testName = testName;
    }

    public TestInfo(TestResult testResult, String testName, Exception exception) {
        this.testResult = testResult;
        this.testName = testName;
        this.exception = exception;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public String getTestName() {
        return testName;
    }

    public Exception getException() {
        return exception;
    }
}

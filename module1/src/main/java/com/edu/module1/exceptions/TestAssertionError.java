package com.edu.module1.exceptions;

public class TestAssertionError extends RuntimeException {
    public TestAssertionError() {
    }

    public TestAssertionError(String message) {
        super(message);
    }
}

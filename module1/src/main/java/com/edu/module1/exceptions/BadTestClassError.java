package com.edu.module1.exceptions;

public class BadTestClassError extends RuntimeException {
    public BadTestClassError() {
    }

    public BadTestClassError(String message) {
        super(message);
    }
}

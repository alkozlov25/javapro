package com.example.paymentcore.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String statusText) {
        super(statusText);
    }
}

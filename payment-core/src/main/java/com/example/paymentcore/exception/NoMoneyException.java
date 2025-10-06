package com.example.paymentcore.exception;

public class NoMoneyException extends RuntimeException {
    public NoMoneyException(String error) {
        super(error);
    }
}

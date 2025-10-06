package edu.t1.exception;

public class NoMoneyException extends RuntimeException {
    public NoMoneyException(String error) {
        super(error);
    }
}

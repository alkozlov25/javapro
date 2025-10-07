package edu.t1.exception;

public class OperationNotFoundException extends RuntimeException {
    public OperationNotFoundException(Long id) {
        super("Operation with id " + id + " not found!");
    }

    public OperationNotFoundException(String message) {
        super(message);
    }
}

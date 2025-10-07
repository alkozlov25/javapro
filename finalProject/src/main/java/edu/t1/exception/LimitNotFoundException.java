package edu.t1.exception;

public class LimitNotFoundException extends RuntimeException {
    public LimitNotFoundException(Long id) {
        super("Limit for user_id " + id + " not found!");
    }
}

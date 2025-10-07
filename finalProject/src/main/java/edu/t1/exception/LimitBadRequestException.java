package edu.t1.exception;

public class LimitBadRequestException extends RuntimeException {
    public LimitBadRequestException(Long id) {
        super("Limit for user_id " + id + " already exists!");
    }

    public LimitBadRequestException(String message) {
        super(message);
    }
}

package org.example.exception;

public class UserAuthorizationException extends RuntimeException {
    public UserAuthorizationException(String message) {
        super(message);
    }
}

package com.agrigov.exception;

public class UserAlreadyExistsException extends AuthException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
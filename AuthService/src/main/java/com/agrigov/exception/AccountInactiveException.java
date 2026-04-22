package com.agrigov.exception;


public class AccountInactiveException extends AuthException {

    public AccountInactiveException() {
        super("Account is not active");
    }
}

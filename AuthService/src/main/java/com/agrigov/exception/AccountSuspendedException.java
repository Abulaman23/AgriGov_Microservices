package com.agrigov.exception;

public class AccountSuspendedException extends AuthException {
	public AccountSuspendedException() {
        super("Account is suspended. Please contact support."); 
    }
}

package com.agrigov.exception;

public class InvalidCredentialsException extends AuthException {
	public InvalidCredentialsException() {
		super("Invalid credentials");
	}
}

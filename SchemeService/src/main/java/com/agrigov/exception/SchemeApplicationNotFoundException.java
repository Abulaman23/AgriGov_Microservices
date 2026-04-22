package com.agrigov.exception;

@SuppressWarnings("serial")//used to suppress warning for serial number.
public class SchemeApplicationNotFoundException extends RuntimeException{

	public SchemeApplicationNotFoundException(String message) {
		super(message);
	}

}
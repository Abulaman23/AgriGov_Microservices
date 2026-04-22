package com.agrigov.exceptions;

@SuppressWarnings("serial")//used to suppress warning for serial number.
public class ReportResponseNotFoundException  extends RuntimeException{
	public ReportResponseNotFoundException(String message){
        super(message);
    }
}


package com.agrigov.exception;

@SuppressWarnings("serial") //used to suppress warning for serial number.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}

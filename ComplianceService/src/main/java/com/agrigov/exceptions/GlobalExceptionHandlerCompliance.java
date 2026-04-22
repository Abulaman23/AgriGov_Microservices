package com.agrigov.exceptions;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
import java.util.HashMap;
import java.util.Map;
 
@ControllerAdvice
//@Component("complianceGlobalExceptionHandler")
public class GlobalExceptionHandlerCompliance {
 
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("status", 404);
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }
 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex){
        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
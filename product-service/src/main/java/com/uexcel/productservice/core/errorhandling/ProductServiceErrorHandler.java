package com.uexcel.productservice.core.errorhandling;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class ProductServiceErrorHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse>
    handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(timestamp(), HttpStatus.CONFLICT.value(), ex.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getDescription(false).split("=")[1]),
                new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CommandExecutionException.class)
    public ResponseEntity<ErrorResponse>
    handleCommandExecutionException(CommandExecutionException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(timestamp(), HttpStatus.EXPECTATION_FAILED.value(), ex.getMessage(),
                HttpStatus.EXPECTATION_FAILED.getReasonPhrase(),
                request.getDescription(false).split("=")[1]),
                new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions( Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(timestamp(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                request.getDescription(false).split("=")[1]),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return sdf.format(new Date());
    }
}

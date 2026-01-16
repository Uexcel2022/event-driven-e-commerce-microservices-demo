package com.uexcel.productservice.core.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RestControllerAdvice
public class ProductServiceErrorHandler {

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(ProductAlreadyExistException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(timestamp(), 409, ex.getMessage(),
                request.getDescription(false).split("=")[1]), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions( Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(timestamp(), 500, ex.getMessage(),
                request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return sdf.format(new Date());
    }
}

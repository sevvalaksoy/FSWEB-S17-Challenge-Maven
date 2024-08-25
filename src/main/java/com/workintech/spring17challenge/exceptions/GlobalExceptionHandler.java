package com.workintech.spring17challenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(ApiException zooException){
        ApiErrorResponse response = new ApiErrorResponse( zooException.getHttpStatus().value(), zooException.getMessage(), System.currentTimeMillis());
        log.error("Exception occured: " + zooException);
        return new ResponseEntity<>(response, zooException.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception){
        ApiErrorResponse response = new ApiErrorResponse( HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
        log.error("Exception occured= " + exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

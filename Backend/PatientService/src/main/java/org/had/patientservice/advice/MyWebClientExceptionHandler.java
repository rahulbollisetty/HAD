package org.had.patientservice.advice;

import lombok.extern.slf4j.Slf4j;
import org.had.accountservice.exception.MyWebClientException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MyWebClientExceptionHandler {
    @ExceptionHandler(MyWebClientException.class)
    public ResponseEntity<?> handleOrderNotFoundException(MyWebClientException ex){
        log.error("MyWebClientExceptionHandler exception caught {}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatusCode.valueOf(ex.getStatus()));
    }

}

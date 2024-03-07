package org.had.accountservice.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class TokenRefreshExceptionHandler {
    @ExceptionHandler(value = org.had.accountservice.exception.TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleTokenRefreshException(org.had.accountservice.exception.TokenRefreshException ex) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", ex.getStatus());
        errorResponse.put("message", ex.getResponseBody());

        log.error("Validation failed: {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus()));
    }
}
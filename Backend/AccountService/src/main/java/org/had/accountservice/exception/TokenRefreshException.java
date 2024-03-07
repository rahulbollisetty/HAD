package org.had.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int status;
    private String responseBody;

    public TokenRefreshException(String token, String message, int value) {
        super(String.format(message));
        this.responseBody=message;
        this.status=value;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
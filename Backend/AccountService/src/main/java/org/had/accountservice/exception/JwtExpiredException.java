package org.had.accountservice.exception;

public class JwtExpiredException extends RuntimeException{
    private int status;
    private String responseBody;

    public JwtExpiredException(int status, String responseBody) {
        super(responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}

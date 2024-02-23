package org.had.accountservice.exception;

public class MyWebClientException extends RuntimeException {
    private int status;
    private String responseBody;

    public MyWebClientException(String message,int status) {
        super(message);
        this.responseBody = message;
        this.status= status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
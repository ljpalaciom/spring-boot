package com.company.demo.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class AppExceptionResponse {
    private final String message;

    private final HttpStatus error;

    private final ZonedDateTime timestamp;

    public AppExceptionResponse(String message, HttpStatus error, ZonedDateTime timestamp) {
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getError() {
        return error;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}

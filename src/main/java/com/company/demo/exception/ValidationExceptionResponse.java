package com.company.demo.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

public class ValidationExceptionResponse extends AppExceptionResponse {
    final Map<String, String> fieldErrors;

    public ValidationExceptionResponse(String message, HttpStatus statusCode, ZonedDateTime zonedDateTime, Map<String, String> fieldErrors) {
        super(message, statusCode, zonedDateTime);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}

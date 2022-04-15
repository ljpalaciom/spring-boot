package com.company.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<Object> handleAppRequestException(AppException e) {
        HttpStatus internalServerError = HttpStatus.BAD_REQUEST;
        AppExceptionResponse appExceptionResponse = new AppExceptionResponse(e.getMessage(),
                internalServerError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(appExceptionResponse, internalServerError);
    }

    @ExceptionHandler(value = {RecordNotFoundException.class})
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        AppExceptionResponse appExceptionResponse = new AppExceptionResponse(e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(appExceptionResponse, notFound);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ValidationExceptionResponse validationException = new ValidationExceptionResponse("Validation error",
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z")),
                fieldErrors);
        return new ResponseEntity<>(validationException, badRequest);
    }

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Object> handleGlobalException(Exception e) {
//        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
//        AppExceptionResponse appExceptionResponse = new AppExceptionResponse(e.getMessage(),
//                internalServerError,
//                ZonedDateTime.now(ZoneId.of("Z"))
//        );
//        return new ResponseEntity<>(appExceptionResponse, internalServerError);
//    }

}

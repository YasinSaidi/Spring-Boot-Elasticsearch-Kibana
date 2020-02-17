package com.course.practicaljava.common;

import com.course.practicaljava.exception.IllegalApiParamException;
import com.course.practicaljava.rest.domain.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);

    @ExceptionHandler(IllegalApiParamException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBrandException(IllegalApiParamException e) {
        String errorMessage = "MyGlobalExceptionHandler *** IllegalApiParamException: " + e.getMessage();
        log.warn(errorMessage);

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, System.currentTimeMillis());
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, null, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

}

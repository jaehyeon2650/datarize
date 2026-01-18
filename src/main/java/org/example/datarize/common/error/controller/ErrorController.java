package org.example.datarize.common.error.controller;

import org.example.datarize.common.error.BusinessException;
import org.example.datarize.common.error.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(final BusinessException exception) {
        final HttpStatus errorStatus = exception.getStatus();
        final ErrorResponse errorResponse = new ErrorResponse(errorStatus.value(), exception.getMessage());
        return ResponseEntity.status(errorStatus).body(errorResponse);
    }
}

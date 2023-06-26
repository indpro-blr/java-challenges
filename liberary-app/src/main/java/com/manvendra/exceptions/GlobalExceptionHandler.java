package com.manvendra.exceptions;

import com.manvendra.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookBorrowedException.class)
    public ResponseEntity<ErrorResponse> handleBookBorrowedException(BookBorrowedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

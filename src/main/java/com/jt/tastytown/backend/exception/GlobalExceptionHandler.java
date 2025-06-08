package com.jt.tastytown.backend.exception;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST) //Not required when ProblemDetail class is used
    public ProblemDetail handleNoSuchElementException(CategoryNotFoundException nsee) {
        // ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, nsee.getMessage()); // industry standard
        problemDetail.setTitle("Category Not Found");
        // problemDetail.setDetail(nsee.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handler(MethodArgumentNotValidException e){
        var details = new StringJoiner(",");
        e.getAllErrors().forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            var fieldName = ((FieldError) error).getField();
            details.add(fieldName + " : "+ errorMessage);
        });
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, details.toString());
        problemDetail.setTitle("Invalid Data");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
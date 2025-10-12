package com.daroch.tickets.controllers;

import com.daroch.tickets.domain.dtos.ErrorDto;
import com.daroch.tickets.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice // Tells Spring this class will globally handle exceptions for REST controllers
@Slf4j // Adds a logger named 'log' using Lombok
public class GlobalExceptionHandler {

    /**
     * Handles cases where a user is not found.
     * Custom exception thrown from your service layer (UserNotFoundException).
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("Caught UserNotFoundException exception", ex);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("User not found");

        // 400 = BAD_REQUEST → since the client requested a non-existent user
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors when @Valid or @Validated fails on request body.
     * (e.g. when POST body has invalid fields)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Caught MethodArgumentNotValidException ", ex);

        ErrorDto errorDto = new ErrorDto();

        // Extract all field errors (invalid fields from request)
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // Pick the first validation error message (you can collect all if you want)
        String errorMessage = fieldErrors.stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation Error Occurred");

        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles violations from @Validated on request parameters or path variables.
     * Example: when you validate @PathVariable or @RequestParam using annotations.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Caught ConstraintViolationException", ex);

        ErrorDto errorDto = new ErrorDto();

        // Pick the first violated constraint message
        String errorMessage = ex.getConstraintViolations().stream()
                .findFirst()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .orElse("Constraint violation occurred");

        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other unhandled exceptions (fallback).
     * Keeps your app from leaking ugly stack traces to clients.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error("Caught exception", ex);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("An unknown error occurred");

        // 500 = INTERNAL_SERVER_ERROR → Something unexpected went wrong
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

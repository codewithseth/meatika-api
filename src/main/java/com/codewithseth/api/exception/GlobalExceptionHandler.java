package com.codewithseth.api.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithseth.api.system.ResultResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResultResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ResultResponse response = new ResultResponse(false, 404, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();
        fieldErrorList.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ResultResponse response = new ResultResponse(false, 400, "Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResultResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolationSet = exception.getConstraintViolations();
        constraintViolationSet.forEach(constraintViolation ->
            errors.put(constraintViolation.getPropertyPath().toString(),
            constraintViolation.getMessage())
        );
        ResultResponse response = new ResultResponse(false, 400, "Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ResultResponse> handleAuthenticationExceptions(Exception exception) {
        ResultResponse response = new ResultResponse(false, 401, "username or password is incorrect", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}

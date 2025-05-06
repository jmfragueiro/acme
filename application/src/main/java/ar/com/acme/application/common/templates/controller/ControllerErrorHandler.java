package ar.com.acme.application.common.templates.controller;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ar.com.acme.application.common.ResponseError;
import ar.com.acme.application.common.templates.repository.ItemNotFoundException;

@RestControllerAdvice
public class ControllerErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleValidationErrors(ConstraintViolationException ex) {
        var errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return new ResponseError(errors.values().stream().map(v -> v.toString()).findFirst().orElse(ex.getClass().getName()));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseError ItemNotFoundExceptionHandler(Exception ex) {
        return new ResponseError(ex.getMessage());
    }
}

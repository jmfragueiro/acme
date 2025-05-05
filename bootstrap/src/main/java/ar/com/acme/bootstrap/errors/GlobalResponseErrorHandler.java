package ar.com.acme.bootstrap.errors;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ar.com.acme.application.common.ResponseError;
import ar.com.acme.application.common.templates.repository.ItemNotFoundException;
import ar.com.acme.bootstrap.exception.AuthException;
import ar.com.acme.bootstrap.jws.JWSException;

@RestControllerAdvice
public class GlobalResponseErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleValidationErrors(ConstraintViolationException ex) {
        var errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return new ResponseError(errors.values().stream().map(v -> v.toString()).findFirst().orElse(ex.getClass().getName()));
    }

    @ExceptionHandler(JWSException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseError JWSExceptionHandler(JWSException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseError AccessExceptionHandler(AccessDeniedException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseError AuthExceptionHandler(AuthException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseError AuthenticationExceptionHandler(AuthenticationException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseError SecurityExceptionHandler(SecurityException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseError ItemNotFoundExceptionHandler(Exception ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseError ConverterErrorsHandler(MethodArgumentTypeMismatchException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseError OtherExceptionHandler(Exception ex) {
        return new ResponseError(ex.getMessage());
    }
}

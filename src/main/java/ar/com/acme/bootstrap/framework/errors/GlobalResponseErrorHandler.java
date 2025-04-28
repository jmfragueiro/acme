package ar.com.acme.bootstrap.framework.errors;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ar.com.acme.base.exception.ItemNotFoundException;
import ar.com.acme.bootstrap.framework.http.HttpResponseError;

@RestControllerAdvice
public class GlobalResponseErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponseError handleValidationErrors(ConstraintViolationException ex, WebRequest req) {
        var errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return new HttpResponseError(errors.values().stream().map(v -> v.toString()).findFirst().orElse(ex.getClass().getName()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseError AccessExceptionHandler(AccessDeniedException ex, WebRequest req) {
        return new HttpResponseError(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseError AuthenticationExceptionHandler(AuthenticationException ex, WebRequest req) {
        return new HttpResponseError(ex.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseError SecurityExceptionHandler(SecurityException ex, WebRequest req) {
        return new HttpResponseError(ex.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final HttpResponseError ItemNotFoundExceptionHandler(Exception ex, WebRequest req) {
        return new HttpResponseError(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final HttpResponseError ConverterErrorsHandler(MethodArgumentTypeMismatchException ex, WebRequest req) {
        return new HttpResponseError(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final HttpResponseError OtherExceptionHandler(Exception ex, WebRequest req) {
        return new HttpResponseError(ex.getMessage());
    }
}

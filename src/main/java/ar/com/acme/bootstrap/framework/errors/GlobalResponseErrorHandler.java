package ar.com.acme.bootstrap.framework.errors;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ar.com.acme.adapter.exception.ItemNotFoundException;
import ar.com.acme.bootstrap.framework.http.HttpResponseBody;

@RestControllerAdvice
public class GlobalResponseErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponseBody handleValidationErrors(ConstraintViolationException ex, WebRequest req) {
        var errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.BAD_REQUEST,
                                    errors.values().stream().map(v -> v.toString()).findFirst().orElse(ex.getClass().getName()),
                                    ex.getClass().getName());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseBody AccessExceptionHandler(AccessDeniedException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.UNAUTHORIZED,
                                     ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseBody AuthenticationExceptionHandler(AuthenticationException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.UNAUTHORIZED,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseBody SecurityExceptionHandler(SecurityException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.UNAUTHORIZED,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final HttpResponseBody ItemNotFoundExceptionHandler(Exception ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.NOT_FOUND,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final HttpResponseBody ConverterErrorsHandler(MethodArgumentTypeMismatchException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.BAD_REQUEST,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final HttpResponseBody OtherExceptionHandler(Exception ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath());
    }
}

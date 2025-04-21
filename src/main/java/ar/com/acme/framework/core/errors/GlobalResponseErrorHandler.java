package ar.gov.posadas.mbe.framework.core.errors;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.core.http.EHttpAuthType;
import ar.gov.posadas.mbe.framework.core.http.HttpResponseBody;
import ar.gov.posadas.mbe.framework.core.security.SecurityService;
import ar.gov.posadas.mbe.framework.core.exception.ItemNotFoundException;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
                                    Constantes.MSJ_ERR_REST_VALIDATE,
                                    ex.getClass().getName(),
                                    errors,
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseBody AccessExceptionHandler(AccessDeniedException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.UNAUTHORIZED,
                                    Constantes.MSJ_SEC_INF_NOACCES,
                                    Optional.of(SecurityService.getAuthentication().getName()).orElse(Constantes.SYS_CAD_UNKNOW),
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseBody AuthenticationExceptionHandler(AuthenticationException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.UNAUTHORIZED,
                                    Constantes.MSJ_ERR_UNAUTHORIZED,
                                    Optional.of(SecurityService.getAuthentication().getName()).orElse(Constantes.SYS_CAD_UNKNOW),
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final HttpResponseBody SecurityExceptionHandler(SecurityException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.UNAUTHORIZED,
                                    Constantes.MSJ_SEC_ERR_USERCANTOP,
                                    Optional.of(SecurityService.getAuthentication().getName()).orElse(Constantes.SYS_CAD_UNKNOW),
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final HttpResponseBody ItemNotFoundExceptionHandler(Exception ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.NOT_FOUND,
                                    Constantes.MSJ_ERR_DB_NOITEM,
                                    null,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final HttpResponseBody ConverterErrorsHandler(MethodArgumentTypeMismatchException ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.BAD_REQUEST,
                                    Constantes.MSJ_ERR_BADFORMATREQUEST,
                                    null,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final HttpResponseBody OtherExceptionHandler(Exception ex, WebRequest req) {
        return new HttpResponseBody(LocalDateTime.now().toString(),
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    Constantes.MSJ_ERR_EXCEPCION,
                                    null,
                                    ex.getMessage(),
                                    ((ServletWebRequest) req).getRequest().getServletPath(),
                                    EHttpAuthType.BEARER);
    }
}

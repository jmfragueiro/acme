package ar.com.acme.application.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ar.com.acme.base.utils.http.HttpResponseError;

@RestControllerAdvice
public class UserResponseErrorHandler {
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public final HttpResponseError UserExceptionHandler(UserException ex) {
        return new HttpResponseError(ex.getMessage());
    }
}

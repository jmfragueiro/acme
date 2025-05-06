package ar.com.acme.application.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ar.com.acme.commons.ResponseError;

@RestControllerAdvice
public class UserErrorHandler {
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public final ResponseError UserExceptionHandler(UserException ex) {
        return new ResponseError(ex.getMessage());
    }
}

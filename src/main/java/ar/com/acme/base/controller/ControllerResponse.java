package ar.com.acme.base.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ControllerResponse<T> (Integer status, Map<String, List<String>> headers, T body) {
    public static <T> ControllerResponse<T> of(ResponseEntity<T> response) {
        return new ControllerResponse<>(response.getStatusCode().value(), (Map<String, List<String>>)null, response.getBody());
    }

    public static <T> ControllerResponse<T> of(Integer status, T body) {
        return new ControllerResponse<>(status, (Map<String, List<String>>)null, body);
    }

    public static <T> ControllerResponse<T> ok(T body) {
        return of(HttpStatus.OK.value(), body);
    }

    public static <T> ControllerResponse<T> badRequest(T body) {
        return of(HttpStatus.BAD_REQUEST.value(), body);
    }
}

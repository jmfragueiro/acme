package ar.com.acme.ports.control;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record CtrlResponse<T> (Integer status, Map<String, List<String>> headers, T body) {
    public static <T> CtrlResponse<T> of(ResponseEntity<T> response) {
        return new CtrlResponse<>(response.getStatusCode().value(), (Map<String, List<String>>)null, response.getBody());
    }

    public static <T> CtrlResponse<T> of(Integer status, T body) {
        return new CtrlResponse<>(status, (Map<String, List<String>>)null, body);
    }

    public static <T> CtrlResponse<T> ok(T body) {
        return of(HttpStatus.OK.value(), body);
    }

    public static <T> CtrlResponse<T> badRequest(T body) {
        return of(HttpStatus.BAD_REQUEST.value(), body);
    }
}

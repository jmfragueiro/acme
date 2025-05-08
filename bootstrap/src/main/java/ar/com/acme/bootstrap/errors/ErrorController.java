package ar.com.acme.bootstrap.errors;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.commons.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(Constants.SYS_CAD_ERROR_URL)
public class ErrorController {
    private final Map<String, String> errorResponse = Map.of(
            Constants.SYS_CAD_RESPONSE_ERROR_MSG, Constants.MSJ_SES_ERR_NOACTIVETOKEN);

    @GetMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> errorGet(HttpServletRequest request, HttpServletResponse response) {
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        return Map.of(
            Constants.SYS_CAD_RESPONSE_ERROR_MSG, exception.getMessage());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> errorPost(HttpServletRequest request, HttpServletResponse response) {
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        return Map.of(
            Constants.SYS_CAD_RESPONSE_ERROR_MSG, exception.getMessage());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> errorPut(HttpServletRequest request, HttpServletResponse response) {
        return errorResponse;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> errorDelete(HttpServletRequest request, HttpServletResponse response) {
        return errorResponse;
    }
}

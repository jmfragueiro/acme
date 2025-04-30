package ar.com.acme.bootstrap.framework.errors;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.bootstrap.common.BootstrapConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(BootstrapConstants.SYS_CAD_ERROR_URL)
public class ErrorController {
    private final Map<String, String> errorResponse = Map.of(
            BootstrapConstants.SYS_CAD_RESPONSE_ERROR_MSG, BootstrapConstants.MSJ_SES_ERR_NOACTIVETOKEN);

    @GetMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> errorGet(HttpServletRequest request, HttpServletResponse response) {
        return errorResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> errorPost(HttpServletRequest request, HttpServletResponse response) {
        return errorResponse;
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

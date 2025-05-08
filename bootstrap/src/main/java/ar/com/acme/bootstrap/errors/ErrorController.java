package ar.com.acme.bootstrap.errors;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(Constants.SYS_CAD_ERROR_URL)
public class ErrorController {
    private final String errMsgAttr = "jakarta.servlet.error.message";

    @GetMapping
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> errorGet(HttpServletRequest request, HttpServletResponse response) {
        return this.getErroMap(request.getAttribute(errMsgAttr).toString());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> errorPost(HttpServletRequest request, HttpServletResponse response) {
        return this.getErroMap(request.getAttribute(errMsgAttr).toString());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> errorPut(HttpServletRequest request, HttpServletResponse response) {
        return this.getErroMap(request.getAttribute(errMsgAttr).toString());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> errorDelete(HttpServletRequest request, HttpServletResponse response) {
        return this.getErroMap(request.getAttribute(errMsgAttr).toString());
    }

    private Map<String, String> getErroMap(String error) {
        return Map.of(Constants.SYS_CAD_RESPONSE_ERROR_MSG,
                      Tools.getCadenaErrorFormateada(Constants.MSJ_USR_ERR_UNPROCESSABLE, error,null));
    }
}

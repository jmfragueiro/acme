package ar.com.acme.bootstrap.framework.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.bootstrap.common.BootstrapConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(BootstrapConstants.SYS_CAD_ERROR_URL)
public class ErrorController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> error(HttpServletRequest request, HttpServletResponse response) {
        var errors = new HashMap<String, String>();

        response.getStatus();

        errors.put("mensaje", "ERROR DE AUTENTICACION!!!");

        return errors;
    }
}

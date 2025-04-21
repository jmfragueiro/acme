package ar.com.acme.framework.core.errors;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.http.EHttpAuthType;
import ar.com.acme.framework.core.http.HttpResponseBody;
import ar.com.acme.framework.core.http.HttpResponseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException aex) throws IOException {
        HttpResponseService.respondHandler(
            response,
            new HttpResponseBody(
                LocalDateTime.now().toString(),
				HttpStatus.UNAUTHORIZED,
                Constantes.MSJ_SES_ERR_BADREQAUTH,
				null,
				aex.getMessage(),
				request.getServletPath(),
				EHttpAuthType.BASIC));
    }
}

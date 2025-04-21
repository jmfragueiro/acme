package ar.com.acme.framework.core.errors;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.http.EHttpAuthType;
import ar.com.acme.framework.core.http.HttpResponseBody;
import ar.com.acme.framework.core.http.HttpResponseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AccessErrorHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException aex) throws IOException {
        HttpResponseService.respondHandler(
            response,
            new HttpResponseBody(
                LocalDateTime.now().toString(),
				HttpStatus.UNAUTHORIZED,
                Constantes.MSJ_SEC_INF_NOACCES,
				null,
				aex.getMessage(),
				request.getServletPath(),
				EHttpAuthType.BEARER));
    }
}

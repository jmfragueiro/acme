package ar.com.acme.bootstrap.errors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import ar.com.acme.bootstrap.http.HttpResponseBody;
import ar.com.acme.bootstrap.http.HttpResponseService;

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
				aex.getMessage(),
				request.getServletPath()));
    }
}

package ar.com.acme.bootstrap.framework.errors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import ar.com.acme.bootstrap.framework.http.HttpResponseBody;
import ar.com.acme.bootstrap.framework.http.HttpResponseService;

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
				aex.getMessage(),
				request.getServletPath()));
    }
}

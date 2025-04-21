package ar.com.acme.framework.core.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.com.acme.framework.core.security.SecurityService;

import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public AuthenticationFilter(IAuthenticationService authenticationService) {
        super(authenticationService);
        super.setPostOnly(false);
     }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return  ((IAuthenticationService)getAuthenticationManager()).thisRequestRequireAuthentication(request);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return ((IAuthenticationService)getAuthenticationManager()).authenticateFromLoggedRequest(request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authenticationResult) throws IOException, ServletException {
        SecurityService.setContextAuthentication(authenticationResult);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        SecurityService.clearContextAuthentication();

        response.sendError(HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
    }
}

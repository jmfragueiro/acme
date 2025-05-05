package ar.com.acme.bootstrap.framework.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public AuthenticationFilter(IAuthenticationService authenticationService) {
        super(authenticationService);
        super.setPostOnly(false);
     }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return !((IAuthenticationService)getAuthenticationManager()).getPublicPaths().matches(request);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return ((IAuthenticationService)getAuthenticationManager()).authenticateFromRequest(request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authenticationResult) throws IOException, ServletException {
        AutenticationContext.setContextAuthentication(authenticationResult);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        AutenticationContext.clearContextAuthentication();

        response.sendError(HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
    }
}

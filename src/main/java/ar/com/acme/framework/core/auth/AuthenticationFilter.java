package ar.com.acme.framework.core.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.com.acme.framework.core.extradata.IReqScopeExtraDataService;
import ar.com.acme.framework.core.security.SecurityService;

import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final IReqScopeExtraDataService reqScopeExtraDataService;

    public AuthenticationFilter(IAuthenticationService authenticationService, IReqScopeExtraDataService reqScopeExtraDataService) {
        super(authenticationService);
        super.setPostOnly(false);
        this.reqScopeExtraDataService = reqScopeExtraDataService;
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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authenticationResult) throws IOException, ServletException {
        SecurityService.setContextAuthentication(authenticationResult);

        // Si autentico OK => continua agregando datos extras que vienen en
        // el requerimiento (dentro del jws) y que pueden usarse mas adelante
        reqScopeExtraDataService.setScopeExtraDataFromRequest(request);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityService.clearContextAuthentication();

        response.sendError(HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
    }
}

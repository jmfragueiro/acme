package ar.com.acme.bootstrap.framework.auth;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import ar.com.acme.base.utils.passw.IPasswordService;
import ar.com.acme.base.utils.principal.IEntityPrincipal;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.common.BootstrapProperties;
import ar.com.acme.bootstrap.framework.auth.types.IAuthenticationType;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.http.HttpRequestAuthorizationHeader;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final RequestMatcher publicPaths;
    private final Map<String, IAuthenticationType> authTypesMap;
    private final IPasswordService passwordService;

    public AuthenticationService(BootstrapProperties propiedades,
        Map<String, IAuthenticationType> authTypesMap,
        IPasswordService passwordService) {
        this.publicPaths = new OrRequestMatcher(
                                    Arrays.stream(propiedades.getSecurity().get("public_paths").split(","))
                                          .map(AntPathRequestMatcher::new)
                                          .toArray(RequestMatcher[]::new));
        this.authTypesMap = authTypesMap;
        this.passwordService = passwordService;
    }

    @Override
    public Authentication authenticateFromRequest(HttpServletRequest request) throws AuthenticationException {
        var authHeader = HttpRequestAuthorizationHeader.from(request);

        IAuthenticationType authType = Optional.of(authTypesMap.get(authHeader.type()))
                                           .orElseThrow(() -> new AuthException(BootstrapConstants.MSJ_REQ_ERR_BADREQUEST));

        return authenticate(authType.generateAuthentication(request, authHeader.value()));
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        var principal = (IEntityPrincipal)auth.getPrincipal();

        if (principal == null || principal.getCredentials() == null) {
            throw new AuthException(BootstrapConstants.MSJ_SES_ERR_INVALIDTOKEN);
        }

        if (auth.getCredentials() != null
            && !passwordService.matches(auth.getCredentials().toString(), principal.getCredentials().toString())) {
            throw new AuthException(BootstrapConstants.MSJ_SES_ERR_BADCREDENTIAL);
        }

        auth.setAuthenticated(true);

        return auth;
    }

    @Override
    public RequestMatcher getPublicPaths() {
        return publicPaths;
    }
}
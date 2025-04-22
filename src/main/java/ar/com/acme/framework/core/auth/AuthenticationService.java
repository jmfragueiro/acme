package ar.com.acme.framework.core.auth;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Propiedades;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.security.SecurityService;
import ar.com.acme.framework.core.token.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IAuthenticationHelper authenticationHelper;
    private final RequestMatcher publicPaths;

    public AuthenticationService(IAuthenticationHelper authenticationHelper, Propiedades propiedades) {
        this.authenticationHelper = authenticationHelper;
        this.publicPaths = new OrRequestMatcher(
                                    Arrays.stream(propiedades.getSecurity().get("public_paths").split(","))
                                          .map(AntPathRequestMatcher::new)
                                          .toArray(RequestMatcher[]::new));
    }

    @Override
    public Authentication authenticateFromRequest(HttpServletRequest request) throws AuthenticationException {
        var authcad = AuthenticationType.getAuthorizationValueFromRequest(request);

        var auth = authcad.type().authenticate(request, authenticationHelper, authcad.value());

        return authenticate(auth);
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        if (auth.getPrincipal() == null) {
            throw new AuthException(Constantes.MSJ_SES_ERR_INVALIDTOKEN);
        }

        if (auth.getCredentials() != null
            && !SecurityService.passwordsMatch(
                auth.getCredentials().toString(),
                ((ITokenPrincipal)auth.getPrincipal()).getCredential())) {
            throw new AuthException(Constantes.MSJ_SES_ERR_BADCREDENTIAL);
        }

        ((ITokenPrincipal)auth.getPrincipal()).verifyCanOperate();

        auth.setAuthenticated(true);

        return auth;
    }

    @Override
    public RequestMatcher getPublicPaths() {
        return publicPaths;
    }

    @Override
    public boolean thisRequestRequireAuthentication(HttpServletRequest request) {
        return !getPublicPaths().matches(request);
    }
}
package ar.com.acme.framework.core.auth;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Propiedades;
import ar.com.acme.framework.core.auth.types.IAuthenticationType;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.http.HttpRequestAuthorizationHeader;
import ar.com.acme.framework.core.security.SecurityService;
import ar.com.acme.framework.core.token.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Map;

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
    private final Map<String, IAuthenticationType> authTypesMap;

    public AuthenticationService(IAuthenticationHelper authenticationHelper
        , Propiedades propiedades
        , Map<String, IAuthenticationType> authTypesMap) {
        this.authenticationHelper = authenticationHelper;
        this.publicPaths = new OrRequestMatcher(
                                    Arrays.stream(propiedades.getSecurity().get("public_paths").split(","))
                                          .map(AntPathRequestMatcher::new)
                                          .toArray(RequestMatcher[]::new));
        this.authTypesMap = authTypesMap;
    }

    @Override
    public Authentication authenticateFromRequest(HttpServletRequest request) throws AuthenticationException {
        var authHeader = getAuthorizationValueFromRequest(request);
        
        if (!authTypesMap.containsKey(authHeader.type())) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        var auth = authTypesMap.get(authHeader.type()).authenticate(request, authenticationHelper, authHeader.value());

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

    private HttpRequestAuthorizationHeader getAuthorizationValueFromRequest(HttpServletRequest request) {
        String reqauth = request.getHeader(Constantes.SYS_CAD_HTTP_AUTH);
        if (reqauth == null) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        var split = reqauth.split(Constantes.SYS_CAD_SPACE);
        if (split.length < 2) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        String authtype = split[0].trim().toUpperCase();
        String authcad = split[1].trim();

        if (authtype.isBlank() || authcad.isBlank()) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        return new HttpRequestAuthorizationHeader(authtype, authcad);
    }
}
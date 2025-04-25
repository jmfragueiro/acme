package ar.com.acme.bootstrap.framework.auth;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.token.IEntityToken;
import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.common.Encoder;
import ar.com.acme.bootstrap.common.Properties;
import ar.com.acme.bootstrap.framework.auth.types.IAuthenticationType;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.http.HttpRequestAuthorizationHeader;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IAuthenticationHelper authenticationHelper;
    private final RequestMatcher publicPaths;
    private final Map<String, IAuthenticationType> authTypesMap;

    public AuthenticationService(IAuthenticationHelper authenticationHelper
        , Properties propiedades
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
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST);
        }

        var auth = authTypesMap.get(authHeader.type()).generateAuthentication(request, authenticationHelper, authHeader.value());

        return authenticate(auth);
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        if (auth.getPrincipal() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_INVALIDTOKEN);
        }

        if (auth.getCredentials() != null
            && !Encoder.passwordsMatch(
                auth.getCredentials().toString(),
                ((IEntityToken)auth.getPrincipal()).getCredential())) {
            throw new AuthException(Constants.MSJ_SES_ERR_BADCREDENTIAL);
        }

        ((IEntityToken)auth.getPrincipal()).verifyCanOperate();

        auth.setAuthenticated(false);
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
        String reqauth = request.getHeader(Constants.SYS_CAD_HTTP_AUTH);
        if (reqauth == null) {
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        var split = reqauth.split(Constants.SYS_CAD_SPACE);
        if (split.length < 2) {
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        String authtype = split[0].trim().toUpperCase();
        String authcad = split[1].trim();

        if (authtype.isBlank() || authcad.isBlank()) {
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        return new HttpRequestAuthorizationHeader(authtype, authcad);
    }
}
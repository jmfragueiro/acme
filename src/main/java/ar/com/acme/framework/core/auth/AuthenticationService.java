package ar.com.acme.framework.core.auth;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Propiedades;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.http.IHttpRequestAuthorizationValueDecoder;
import ar.com.acme.framework.core.jws.JwsService;
import ar.com.acme.framework.core.jws.IJwsService;
import ar.com.acme.framework.core.security.SecurityService;
import ar.com.acme.framework.core.token.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final ITokenPrincipalService<? extends ITokenPrincipal> tokenPrincipalService;
    private final IHttpRequestAuthorizationValueDecoder httpRequestAuthorizationValueDecoder;
    private final IJwsService jwsService;
    private final RequestMatcher publicPaths;

    public AuthenticationService(ITokenPrincipalService<? extends ITokenPrincipal> tokenPrincipalService,
        IHttpRequestAuthorizationValueDecoder httpRequestAuthorizationValueDecoder,
        JwsService jwsService,
        Propiedades propiedades) {
        this.tokenPrincipalService = tokenPrincipalService;
        this.httpRequestAuthorizationValueDecoder = httpRequestAuthorizationValueDecoder;
        this.jwsService = jwsService;
        this.publicPaths = new OrRequestMatcher(
                                    Arrays.stream(propiedades.getSecurity().get("public_paths").split(","))
                                          .map(AntPathRequestMatcher::new)
                                          .toArray(RequestMatcher[]::new));
    }

    @Override
    public Authentication authenticateFromLogginRequest(HttpServletRequest request, String username, String password) throws AuthenticationException {
        httpRequestAuthorizationValueDecoder.validateAuthorizationValueFromRequest(request);

        var repoUser = tokenPrincipalService.findByName(username).orElseThrow(() -> new AuthException(Constantes.MSJ_SES_ERR_BADCREDENTIAL));

        return authenticate(new TokenAuthentication(repoUser, password));
    }

    @Override
    public Authentication authenticateFromLoggedRequest(HttpServletRequest request) throws AuthenticationException {
        var requestAuthorizationJws = httpRequestAuthorizationValueDecoder.getValidAuthorizationValueFromRequest(request);

        var token = jwsService.getIdFromJws(requestAuthorizationJws);

        var repoUser = tokenPrincipalService.findByToken(token).orElseThrow(() -> new AuthException(Constantes.MSJ_SES_ERR_NOTOKEN));

        return authenticate(new TokenAuthentication(repoUser));
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
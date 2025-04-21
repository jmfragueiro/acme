package ar.gov.posadas.mbe.impldefault;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Propiedades;
import ar.gov.posadas.mbe.framework.core.auth.IAuthenticationService;
import ar.gov.posadas.mbe.framework.core.exception.AuthException;
import ar.gov.posadas.mbe.framework.core.http.IHttpRequestAuthorizationValueDecoder;
import ar.gov.posadas.mbe.framework.core.token.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationService implements IAuthenticationService {
    private final ITokenPayloadService<? extends ITokenPayload> tokenPayloadService;
    private final DefaultTokenService tokenService;
    private final IHttpRequestAuthorizationValueDecoder httpRequestAuthorizationValueDecoder;
    private final RequestMatcher publicPaths;
    private final DefaultJwsService jwsService;

    public DefaultAuthenticationService(DefaultTokenService tokenService,
            List<ITokenPayloadService<? extends ITokenPayload>> tokenPayloadServicesList,
            IHttpRequestAuthorizationValueDecoder httpRequestAuthorizationValueDecoder,
            DefaultJwsService jwsService,
            Propiedades propiedades) {
        this.tokenService = tokenService;
        this.httpRequestAuthorizationValueDecoder = httpRequestAuthorizationValueDecoder;
        this.jwsService = jwsService;
        this.publicPaths = new OrRequestMatcher(
                                    Arrays.stream(propiedades.getSecurity().get("public_paths").split(","))
                                          .map(AntPathRequestMatcher::new)
                                          .toArray(RequestMatcher[]::new));
        this.tokenPayloadService =
            tokenPayloadServicesList.stream()
                                    .filter(e -> e.getClass().getName().equals(propiedades.getToken().get("payload_service")))
                                    .findFirst()
                                    .orElse(new DefaultTokenPayloadService());
    }

    @Override
    public Authentication authenticateFromLogginRequest(HttpServletRequest request, String username, String password) throws AuthenticationException {
        httpRequestAuthorizationValueDecoder.validateAuthorizationValueFromRequest(request);

        var repoUser = tokenPayloadService.findByName(username).orElseThrow(() -> new AuthException(Constantes.MSJ_SES_INF_BADCREDENTIAL));

        var token = tokenService.createToken(repoUser).getTokenIfUseful();

        return authenticate(new DefaultTokenAuthentication(token, username, password));
    }

    @Override
    public Authentication authenticateFromLoggedRequest(HttpServletRequest request) throws AuthenticationException {
        var requestAuthorizationJws = httpRequestAuthorizationValueDecoder.getValidAuthorizationValueFromRequest(request);

        var tokenId = jwsService.getIdFromJws(requestAuthorizationJws);

        var token = tokenService.getToken(tokenId).orElseThrow(() -> new AuthException(Constantes.MSJ_SEC_ERR_NOTOKEN)).getTokenIfUseful();

        return authenticate(new DefaultTokenAuthentication(token, requestAuthorizationJws));
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        // aquí se pasa true para que la implementación del ITokenAuthentication
        // (aqui DefaultTokenAuthentication) valide el objeto Authentication pasado
        // antes de proceder a marcarlo como realmente autenticado
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
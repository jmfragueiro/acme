package ar.gov.posadas.mbe.impldefault;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.core.exception.AuthException;
import ar.gov.posadas.mbe.framework.core.session.ISessionService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DefaultSessionService implements ISessionService {
    private final DefaultJwsService jwsService;
    private final DefaultTokenService tokenService;

    public DefaultSessionService(DefaultJwsService jwsService, DefaultTokenService tokenService) {
        this.jwsService = jwsService;
        this.tokenService = tokenService;
    }

    @Override
    public String loggin(Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_USERNOAUTH);
        }

        DefaultToken token = (DefaultToken) authentication.getPrincipal();

        tokenService.initToken(token);

        return jwsService.generateJws(token);
    }

    @Override
    public String loggout(Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_USERNOAUTH);
        }

        DefaultToken token = (DefaultToken) authentication.getPrincipal();

        tokenService.endToken(token);

        return authentication.getDetails().toString();

    }
}

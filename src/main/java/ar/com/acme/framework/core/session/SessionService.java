package ar.com.acme.framework.core.session;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.jws.JwsService;
import ar.com.acme.impldefault.DefaultToken;
import ar.com.acme.impldefault.DefaultTokenService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SessionService implements ISessionService {
    private final JwsService jwsService;
    private final DefaultTokenService tokenService;

    public SessionService(JwsService jwsService, DefaultTokenService tokenService) {
        this.jwsService = jwsService;
        this.tokenService = tokenService;
    }

    @Override
    public String login(Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new AuthException(Constantes.MSJ_SES_ERR_USERNOAUTH);
        }

        DefaultToken token = (DefaultToken) authentication.getPrincipal();

        tokenService.initToken(token);

        return jwsService.generateJws(token);
    }

    @Override
    public String logout(Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new AuthException(Constantes.MSJ_SES_ERR_USERNOAUTH);
        }

        DefaultToken token = (DefaultToken) authentication.getPrincipal();

        tokenService.endToken(token);

        return authentication.getDetails().toString();

    }
}

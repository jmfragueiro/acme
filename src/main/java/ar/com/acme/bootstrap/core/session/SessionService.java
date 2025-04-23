package ar.com.acme.bootstrap.core.session;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.com.acme.bootstrap.common.Constantes;
import ar.com.acme.bootstrap.core.exception.AuthException;
import ar.com.acme.bootstrap.core.jws.JwsService;
import ar.com.acme.bootstrap.core.token.ITokenPrincipal;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
    private final JwsService jwsService;

    @Override
    public String login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(Constantes.MSJ_SES_ERR_USERNOAUTH);
        }

        var principal = (ITokenPrincipal)authentication.getPrincipal();

        var token = jwsService.generateJws(principal);

        principal.setToken(token);

        return token;
    }

    @Override
    public void logout(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(Constantes.MSJ_SES_ERR_USERNOAUTH);
        }

        ((ITokenPrincipal)authentication.getPrincipal()).setToken(null);
    }
}

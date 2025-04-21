package ar.com.acme.framework.core.session;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.jws.JwsService;
import ar.com.acme.framework.core.token.ITokenPrincipal;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

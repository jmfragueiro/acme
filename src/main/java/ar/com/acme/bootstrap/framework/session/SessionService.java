package ar.com.acme.bootstrap.framework.session;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.token.IEntityToken;
import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.jws.JwsService;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
    private final JwsService jwsService;

    @Override
    public String login(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IEntityToken)authentication.getPrincipal();

        var token = jwsService.generateJws(principal);

        principal.setToken(token);

        return token;
    }

    @Override
    public void logout(Authentication authentication) {
        validateAuthentication(authentication);

        ((IEntityToken)authentication.getPrincipal()).setToken(null);
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOAUTH);
        }

        if ((IEntityToken)authentication.getPrincipal() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_ONAUTH);
        }
    }
}

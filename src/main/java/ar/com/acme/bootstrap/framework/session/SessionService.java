package ar.com.acme.bootstrap.framework.session;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.token.IEntityPrincipal;
import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.framework.auth.IAuthenticationHelper;
import ar.com.acme.bootstrap.framework.exception.AuthException;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
    private final IAuthenticationHelper authenticationHelper;

    @Override
    public String login(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IEntityPrincipal)authentication.getPrincipal();

        validateCanCreateSession(principal);

        var token = authenticationHelper.getJwsService().generateJws(principal);

        principal.setToken(token);
        principal.setLastLogin(LocalDateTime.now());
        authenticationHelper.getPrincipalService().updatePrincipal(principal);

        return token;
    }

    @Override
    public void logout(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IEntityPrincipal)authentication.getPrincipal();

        validateCanDeleteSession(principal);

        principal.setToken(null);
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOAUTH);
        }

        if ((IEntityPrincipal)authentication.getPrincipal() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_ONAUTH);
        }
    }

    private void validateCanCreateSession(IEntityPrincipal principal) {
        if (principal.getToken() != null) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERALREADYLOGGED);
        }

        principal.verifyCanOperate();
    }

    private void validateCanDeleteSession(IEntityPrincipal principal) {
        if (principal.getToken() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOTLOGGED);
        }
    }
}

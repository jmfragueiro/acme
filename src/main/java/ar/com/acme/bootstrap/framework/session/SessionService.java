package ar.com.acme.bootstrap.framework.session;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.com.acme.base.principal.IEntityPrincipal;
import ar.com.acme.base.principal.IEntityPrincipalService;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.common.BootstrapProperties;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.jws.IJwsService;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
        private final IEntityPrincipalService<? extends IEntityPrincipal> principalService;
        private final IJwsService jwsService;
        private final BootstrapProperties properties;

    @Override
    public String login(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IEntityPrincipal)authentication.getPrincipal();

        validateCanCreateSession(principal);

        var token = jwsService.generateJws(principal);

        principal.setToken(UUID.fromString(jwsService.getIdFromJws(token)));
        principal.setLastLogin(LocalDateTime.now());
        principalService.updatePrincipal(principal);

        return token;
    }

    @Override
    public void logout(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IEntityPrincipal)authentication.getPrincipal();

        validateCanDeleteSession(principal);

        principal.setToken(null);
        principalService.updatePrincipal(principal);
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(BootstrapConstants.MSJ_SES_ERR_USERNOAUTH);
        }

        if ((IEntityPrincipal)authentication.getPrincipal() == null) {
            throw new AuthException(BootstrapConstants.MSJ_SES_ERR_ONAUTH);
        }
    }

    private void validateCanCreateSession(IEntityPrincipal principal) {
        if (principal.getToken() != null && !properties.getSecurity().get("user_multisession").equalsIgnoreCase("true")) {
            throw new AuthException(BootstrapConstants.MSJ_SES_ERR_USERALREADYLOGGED);
        }

        principal.verifyCanOperate();
    }

    private void validateCanDeleteSession(IEntityPrincipal principal) {
        if (principal.getToken() == null) {
            throw new AuthException(BootstrapConstants.MSJ_SES_ERR_USERNOTLOGGED);
        }
    }
}

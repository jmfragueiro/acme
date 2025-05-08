package ar.com.acme.bootstrap.session;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.principal.IPrincipal;
import ar.com.acme.adapter.principal.IPrincipalService;
import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Properties;
import ar.com.acme.bootstrap.exception.AuthException;
import ar.com.acme.bootstrap.jws.IJwsService;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
        private final IPrincipalService<IPrincipal> principalService;
        private final IJwsService jwsService;
        private final Properties properties;

    @Override
    public String login(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IPrincipal)authentication.getPrincipal();

        validateCanCreateSession(principal);

        var tid = UUID.randomUUID();
        var now = LocalDateTime.now();
        var token = jwsService.generateJws(principal, tid, now);

        principal.setToken(tid);
        principal.setLastLogin(now);
        principalService.updatePrincipal(principal);

        return token;
    }

    @Override
    public void logout(Authentication authentication) {
        validateAuthentication(authentication);

        var principal = (IPrincipal)authentication.getPrincipal();

        validateCanDeleteSession(principal);

        principal.setToken(null);
        principalService.updatePrincipal(principal);
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOAUTH);
        }

        if ((IPrincipal)authentication.getPrincipal() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_ONAUTH);
        }
    }

    private void validateCanCreateSession(IPrincipal principal) {
        // permite o no multiples sesiones de usuario segun la propiedad user_multisession
        if (principal.getToken() != null && !properties.getSecurity().get("user_multisession").equalsIgnoreCase("true")) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERALREADYLOGGED);
        }

        if (!principal.canOperate()) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERCANTOP);
        };
    }

    private void validateCanDeleteSession(IPrincipal principal) {
        if (principal.getToken() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOTLOGGED);
        }
    }
}

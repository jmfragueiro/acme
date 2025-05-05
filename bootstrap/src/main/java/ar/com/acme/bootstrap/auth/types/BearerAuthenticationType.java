package ar.com.acme.bootstrap.auth.types;

import java.util.UUID;
import org.springframework.stereotype.Service;

import ar.com.acme.application.principal.IPrincipal;
import ar.com.acme.application.principal.IPrincipalService;
import ar.com.acme.bootstrap.auth.AuthenticationToken;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.exception.AuthException;
import ar.com.acme.bootstrap.jws.IJwsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service(BootstrapConstants.SYS_CAD_HTTPAUTH_BEARER)
@RequiredArgsConstructor
public class BearerAuthenticationType implements IAuthenticationType {
        private final IPrincipalService<? extends IPrincipal> principalService;
        private final IJwsService jwsService;

        @Override
        public AuthenticationToken generateAuthentication(HttpServletRequest request, String authcad) {
            jwsService.validateJws(authcad);

            var token = jwsService.getIdFromJws(authcad);

            var repoUser = principalService.findByToken(UUID.fromString(token))
                                           .orElseThrow(() -> new AuthException(BootstrapConstants.MSJ_SES_ERR_NOACTIVETOKEN));

            var authorities = principalService.getAuthorities(repoUser);

            return new AuthenticationToken(repoUser, authorities);
        }
}

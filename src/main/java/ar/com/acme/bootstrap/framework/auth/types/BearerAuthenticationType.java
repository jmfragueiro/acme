package ar.com.acme.bootstrap.framework.auth.types;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.base.principal.IEntityPrincipal;
import ar.com.acme.base.principal.IEntityPrincipalService;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.jws.IJwsService;
import ar.com.acme.bootstrap.framework.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service(BootstrapConstants.SYS_CAD_HTTPAUTH_BEARER)
@RequiredArgsConstructor
public class BearerAuthenticationType implements IAuthenticationType {
        private final IEntityPrincipalService<IEntityPrincipal> principalService;
        private final IJwsService jwsService;

        @Override
        public TokenAuthentication generateAuthentication(HttpServletRequest request, String authcad) {
            jwsService.validateJws(authcad);

            var token = jwsService.getIdFromJws(authcad);

            var repoUser = principalService.findByToken(UUID.fromString(token))
                                           .orElseThrow(() -> new AuthException(BootstrapConstants.MSJ_SES_ERR_NOACTIVETOKEN));

            var authorities = principalService.getAuthorities(repoUser);

            return new TokenAuthentication(repoUser, authorities);
        }
}

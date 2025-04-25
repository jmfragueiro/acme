package ar.com.acme.bootstrap.framework.auth.types;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.framework.auth.IAuthenticationHelper;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

@Service(Constants.SYS_CAD_HTTPAUTH_BEARER)
public class BearerAuthenticationType implements IAuthenticationType {
        @Override
        public TokenAuthentication generateAuthentication(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad) {
            authHelper.getJwsService().validateJws(authcad);

            var token = authHelper.getJwsService().getIdFromJws(authcad);

            var repoUser = authHelper.getPrincipalService()
                                     .findByToken(UUID.fromString(token))
                                     .orElseThrow(() -> new AuthException(Constants.MSJ_SES_ERR_NOACTIVETOKEN));

            var authorities = authHelper.getPrincipalService()
                                        .getAuthorities(repoUser);

            return new TokenAuthentication(repoUser, authorities);
        }
}

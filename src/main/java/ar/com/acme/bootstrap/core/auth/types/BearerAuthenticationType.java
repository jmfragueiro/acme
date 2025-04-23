package ar.com.acme.bootstrap.core.auth.types;

import org.springframework.stereotype.Service;

import ar.com.acme.bootstrap.common.Constantes;
import ar.com.acme.bootstrap.core.auth.IAuthenticationHelper;
import ar.com.acme.bootstrap.core.exception.AuthException;
import ar.com.acme.bootstrap.core.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

@Service(Constantes.SYS_CAD_HTTPAUTH_BEARER)
public class BearerAuthenticationType implements IAuthenticationType {
        @Override
        public TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad) {
            authHelper.getJwsService().validateJws(authcad);

            var token = authHelper.getJwsService().getIdFromJws(authcad);

            var repoUser = authHelper.getPrincipalService()
                                               .findByToken(token)
                                               .orElseThrow(() -> new AuthException(Constantes.MSJ_SES_ERR_NOACTIVETOKEN));

            return new TokenAuthentication(repoUser);
        }
}

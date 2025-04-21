package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.security.SecurityService;
import ar.com.acme.framework.core.token.AbstractTokenAuthentication;

public class DefaultTokenAuthentication extends AbstractTokenAuthentication {
    public DefaultTokenAuthentication(DefaultToken token, String username, String password) {
        super(token, username, password);
    }

    public DefaultTokenAuthentication(DefaultToken token, String details) {
        super(token, details);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    protected void validateAuthentication() throws IllegalArgumentException, AuthException {
        // para permitir la autenticación: el principal (aqui un IToken)
        // no puede ser nulo y además, si viene la credencial, la misma
        // debe coincidir con la que tiene el principal contenido aqui
        if (getPrincipal() == null) {
            throw new AuthException(Constantes.MSJ_SES_ERR_INVALIDTOKEN);
        }

        if (getCredentials() != null
            && !SecurityService.passwordsMatch(getCredentials().toString(), ((DefaultToken)getPrincipal()).getPayload().getCredential())) {
            throw new AuthException(Constantes.MSJ_SES_ERR_BADCREDENTIAL);
        }
    }
}

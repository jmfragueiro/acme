package ar.gov.posadas.mbe.impldefault;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.core.exception.AuthException;
import ar.gov.posadas.mbe.framework.core.security.SecurityService;
import ar.gov.posadas.mbe.framework.core.token.AbstractTokenAuthentication;

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
            throw new AuthException(Constantes.MSJ_SEC_ERR_INVALIDTOKEN);
        }

        if (getCredentials() != null
            && !SecurityService.passwordsMatch(getCredentials().toString(), ((DefaultToken)getPrincipal()).getPayload().getCredential())) {
            throw new AuthException(Constantes.MSJ_SES_INF_BADCREDENTIAL);
        }
    }
}

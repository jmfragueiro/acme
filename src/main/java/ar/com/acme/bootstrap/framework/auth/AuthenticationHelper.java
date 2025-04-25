package ar.com.acme.bootstrap.framework.auth;

import org.springframework.stereotype.Component;

import ar.com.acme.adapter.token.IEntityPrincipal;
import ar.com.acme.adapter.token.IEntityPrincipalService;
import ar.com.acme.bootstrap.common.Properties;
import ar.com.acme.bootstrap.framework.jws.IJwsService;

@Component
public class AuthenticationHelper implements IAuthenticationHelper {
        private final IEntityPrincipalService<? extends IEntityPrincipal> principalService;
        private final IJwsService jwsService;
        private final String clientid;
        private final String clientsecret;

        public AuthenticationHelper(IEntityPrincipalService<? extends IEntityPrincipal> principalService, IJwsService jwsService, Properties propiedades) {
            this.clientid = propiedades.getSecurity().get("jwt_client-id");
            this.clientsecret = propiedades.getSecurity().get("jwt_client-secret");
            this.principalService = principalService;
            this.jwsService = jwsService;
        }

        @Override
        public IEntityPrincipalService<? extends IEntityPrincipal> getPrincipalService() {
                return principalService;
        }

        @Override
        public IJwsService getJwsService() {
                return jwsService;
        }

        @Override
        public String getClientid() {
                return clientid;
        }

        @Override
        public String getClientsecret() {
                return clientsecret;
        }
}

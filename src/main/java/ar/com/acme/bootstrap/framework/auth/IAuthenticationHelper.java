package ar.com.acme.bootstrap.framework.auth;

import ar.com.acme.adapter.token.IEntityToken;
import ar.com.acme.adapter.token.IEntityTokenService;
import ar.com.acme.bootstrap.framework.jws.IJwsService;

public interface IAuthenticationHelper {
    IEntityTokenService<? extends IEntityToken> getPrincipalService();

    IJwsService getJwsService();

    String getClientid();

    String getClientsecret();
}

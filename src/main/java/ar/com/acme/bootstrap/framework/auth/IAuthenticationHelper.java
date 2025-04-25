package ar.com.acme.bootstrap.framework.auth;

import ar.com.acme.adapter.token.IEntityPrincipal;
import ar.com.acme.adapter.token.IEntityPrincipalService;
import ar.com.acme.bootstrap.framework.jws.IJwsService;

public interface IAuthenticationHelper {
    IEntityPrincipalService<? extends IEntityPrincipal> getPrincipalService();

    IJwsService getJwsService();

    String getClientid();

    String getClientsecret();
}

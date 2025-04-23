package ar.com.acme.bootstrap.framework.auth;

import ar.com.acme.adapter.token.ITokenPrincipal;
import ar.com.acme.adapter.token.ITokenPrincipalService;
import ar.com.acme.bootstrap.framework.jws.IJwsService;

public interface IAuthenticationHelper {
    ITokenPrincipalService<? extends ITokenPrincipal> getPrincipalService();

    IJwsService getJwsService();

    String getClientid();

    String getClientsecret();
}

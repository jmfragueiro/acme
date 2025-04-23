package ar.com.acme.bootstrap.core.auth;

import ar.com.acme.bootstrap.core.jws.IJwsService;
import ar.com.acme.bootstrap.core.token.ITokenPrincipal;
import ar.com.acme.bootstrap.core.token.ITokenPrincipalService;

public interface IAuthenticationHelper {
    ITokenPrincipalService<? extends ITokenPrincipal> getPrincipalService();

    IJwsService getJwsService();

    String getClientid();

    String getClientsecret();
}

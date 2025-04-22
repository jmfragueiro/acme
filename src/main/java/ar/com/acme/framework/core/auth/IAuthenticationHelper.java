package ar.com.acme.framework.core.auth;

import ar.com.acme.framework.core.jws.IJwsService;
import ar.com.acme.framework.core.token.ITokenPrincipal;
import ar.com.acme.framework.core.token.ITokenPrincipalService;

public interface IAuthenticationHelper {
    ITokenPrincipalService<? extends ITokenPrincipal> getPrincipalService();

    IJwsService getJwsService();

    String getClientid();

    String getClientsecret();
}

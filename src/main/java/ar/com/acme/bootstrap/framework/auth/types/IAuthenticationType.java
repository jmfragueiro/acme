package ar.com.acme.bootstrap.framework.auth.types;

import ar.com.acme.bootstrap.framework.auth.IAuthenticationHelper;
import ar.com.acme.bootstrap.framework.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationType {
    TokenAuthentication generateAuthentication(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad);
}
